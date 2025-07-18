package com.jfsd.CareerConnect.services;

import java.util.List;

import com.jfsd.CareerConnect.models.Job;
import com.jfsd.CareerConnect.models.JobApplication;
import com.jfsd.CareerConnect.models.Recruiter;

public interface RecruiterService {

	public Recruiter getRecruiterById(int recruiterId);

	public Recruiter updateRecruiter(Recruiter recruiterDetails);

	public String addJob(Job job,int recruiterId);

	public String deleteJob(int id);
	
	public List<Job> viewalljobs(int recruiterId);
	
	public List<JobApplication> viewapplicationsbyjobid(int jobId);


}
