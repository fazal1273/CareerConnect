package com.jfsd.CareerConnect.services;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfsd.CareerConnect.models.Job;
import com.jfsd.CareerConnect.models.JobApplication;
import com.jfsd.CareerConnect.models.Recruiter;
import com.jfsd.CareerConnect.models.Student;
import com.jfsd.CareerConnect.repository.JobApplicationRepository;
import com.jfsd.CareerConnect.repository.JobRepository;
import com.jfsd.CareerConnect.repository.RecruiterRepository;
import com.jfsd.CareerConnect.repository.StudentRepository;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class RecruiterServiceImpl implements RecruiterService {

	@Autowired
    private RecruiterRepository recruiterrepo;
	
	@Autowired
	private JobRepository jobrepo;
	
	@Autowired
	private JobApplicationRepository jobapplicationrepo;
	
	@Autowired
	private  StudentRepository studentrepo;
	
	 @Autowired
	 private JavaMailSender mailSender;

    public Recruiter getRecruiterById(int recruiterId) {
        return recruiterrepo.findById(recruiterId).orElse(null);
    }

    public Recruiter updateRecruiter(Recruiter recruiterDetails) {
        return recruiterrepo.findById(recruiterDetails.getRecruiterId()).map(recruiter -> {
            recruiter.setName(recruiterDetails.getName());
            recruiter.setCompany(recruiterDetails.getCompany());
            recruiter.setEmail(recruiterDetails.getEmail());
            recruiter.setContactNumber(recruiterDetails.getContactNumber());
            recruiter.setIndustry(recruiterDetails.getIndustry());
            recruiter.setLocation(recruiterDetails.getLocation());
            recruiter.setWebsiteurl(recruiterDetails.getWebsiteurl());
            recruiter.setUsername(recruiterDetails.getUsername());
            recruiter.setPassword(recruiterDetails.getPassword());
            return recruiterrepo.save(recruiter);
        }).orElse(null);
    }

	@Override
	public String addJob(Job job, int recruiterId) {
        String out = "";
        Optional<Recruiter> recruiteroptional = recruiterrepo.findById(recruiterId);
        if (recruiteroptional.isEmpty()) {
            throw new RuntimeException("Recruiter not found with id: " + recruiterId);
        }
        Recruiter recruiter = recruiteroptional.get();
        try {
            job.setRecruiter(recruiter);
            jobrepo.save(job);
            System.out.println("Job Added..");

            // Fetch all students
            List<Student> students = studentrepo.findAll();
            if (!students.isEmpty()) {
                for (Student student : students) {
                    sendJobEmail(student.getEmail(), job);
                    System.out.println("Email Sent..");
                }
            }

            out = "Job inserted successfully and emails sent to students.";
        } catch (Exception e) {
            out = "Error: " + e.getMessage();
        }
        return out;
    }

    private void sendJobEmail(String toEmail, Job job) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-email@gmail.com");
        message.setTo(toEmail);
        message.setSubject("New Job Opportunity: " + job.getTitle());
        message.setText("Dear Student,\n\nA new job opportunity has been posted:\n\n" +
                        "Job Title: " + job.getTitle() + "\n" +
                        "Location: " + job.getLocation() + "\n" +
                        "Description: " + job.getDescription() + "\n\n" +
                        "Apply now on the portal.\n\nBest Regards,\nCareerConnect Team");

        mailSender.send(message);
    }

	@Override
	public String deleteJob(int id) {
		String out="";
		try {
		 jobrepo.deleteById(id);
		 out="Job with id "+id+" deleted successfully";
		}
		catch(Exception e)
		{
			out="Error: "+e.getMessage();
		}
		return out;
	}
	
	public List<Job> viewalljobs(int recruiterId)
	{
		Optional<Recruiter> recruiteroptional=recruiterrepo.findById(recruiterId);
	   if(recruiteroptional.isEmpty()) {
           throw new RuntimeException("Recruiter not found with id: " + recruiterId);
	   }
	   Recruiter recruiter =recruiteroptional.get();
	   
	   return jobrepo.findByRecruiter(recruiter);
	
	}

	@Override
	public List<JobApplication> viewapplicationsbyjobid(int jobId) {
		Optional<Job> j= jobrepo.findById(jobId);
		if(j.isEmpty())
		{
			throw new RuntimeException("Job Not Found with id:"+jobId);
		}
		 List<JobApplication> joblist=jobapplicationrepo.findByJob(j.get());
		return joblist;	
	}
	
	
    
}
