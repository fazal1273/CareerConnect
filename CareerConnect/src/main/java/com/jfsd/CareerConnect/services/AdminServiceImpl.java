package com.jfsd.CareerConnect.services;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import com.jfsd.CareerConnect.models.Admin;
import com.jfsd.CareerConnect.models.Recruiter;
import com.jfsd.CareerConnect.models.Student;
import com.jfsd.CareerConnect.repository.AdminRepository;
import com.jfsd.CareerConnect.repository.RecruiterRepository;
import com.jfsd.CareerConnect.repository.StudentRepository;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminRepository adminrepo;
	
	@Autowired
	private StudentRepository studentrepo;
	
	
	@Autowired
	private RecruiterRepository recruiterrepo;

	@Override
	public String insertStudent(Student st) {
	    String out = "";
	    try {
	        boolean admvalue = adminrepo.existsByUsername(st.getUsername());
	        boolean recvalue = recruiterrepo.existsByUsername(st.getUsername());
	        boolean stvalue= studentrepo.existsByUsername(st.getUsername());
	        boolean emailExists = studentrepo.existsByEmail(st.getEmail());
	        boolean phoneExists = studentrepo.existsByPhoneNumber(st.getPhoneNumber());
	        boolean idExists = studentrepo.findById(st.getId()).isPresent();;

	        if (admvalue || recvalue || stvalue) {
	            return "Username already exists. Please choose a different username.";
	        }
	        if (emailExists) {
	            return "Email already exists. Please choose a different email.";
	        }
	        if (phoneExists) {
	            return "Phone number already exists. Please use a different phone number.";
	        }
	        if (idExists) {
	            return "Student ID already exists. Please choose a different ID.";
	        }
	        
	        studentrepo.save(st);
	        out = "Student Registered Successfully";
	    } catch (Exception e) {
	        out = "Error occurred while registering:\n " + e.getMessage();
	    }
	    return out;
	}

    
    
	
	@Override
	public String deleteStudent(int id) {
		String out="";
		try {
			studentrepo.deleteById(id);
			out="Student "+id+" deleted Successfully";			
		}
		catch(Exception e)	
		{
			out="Error occured while registering:\n"+e.getMessage();
		}
		return out;
	}
	
	@Override
	public String updateStudent(Student st) {
	    try {
	    	
	        Optional<Student> stu = studentrepo.findById(st.getId());
	        if (stu.isPresent()) {
	        	boolean admvalue = adminrepo.existsByUsername(st.getUsername());
		        boolean recvalue = recruiterrepo.existsByUsername(st.getUsername());
		        boolean stvalue= studentrepo.existsByUsername(st.getUsername());
		        boolean emailExists = studentrepo.existsByEmail(st.getEmail());
		        boolean phoneExists = studentrepo.existsByPhoneNumber(st.getPhoneNumber());
		        boolean idExists = studentrepo.findById(st.getId()).isPresent();;

		        System.out.println(admvalue+" "+recvalue);
		        if (admvalue || recvalue || stvalue) {
		            return "Username already exists. Please choose a different username.";
		        }
		        if (emailExists) {
		            return "Email already exists. Please choose a different email.";
		        }
		        if (phoneExists) {
		            return "Phone number already exists. Please use a different phone number.";
		        }
		        if (idExists) {
		            return "Student ID already exists. Please choose a different ID.";
		        }
		        
	            studentrepo.save(st);
	            return "Student updated successfully";
	        } else {
	            return "Student with ID " + st.getId() + " not found";
	        }
	    } catch (Exception e) {
	        return "Error occurred while updating: " + e.getMessage();
	    }
	}


	@Override
	public ResponseEntity<List<Student>> viewAllStudents() {
		
		try
		{
			List<Student> studentlist=studentrepo.findAll();
			if(studentlist.isEmpty())
			{
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(studentlist);
			}
			return ResponseEntity.ok(studentlist);
		}
		catch(ResponseStatusException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error occured while retreivng the data"+e.getMessage());
		}
    }

	@Override
	public ResponseEntity<Student> viewStudentById(int id) {
	    Optional<Student> studentdata = studentrepo.findById(id);
	    if (studentdata.isPresent()) {
	        Student student = studentdata.get();
	        if (student.getPhoto() != null) {
	            student.setPhoto(Base64.getEncoder().encodeToString(student.getPhoto()).getBytes());
	        }
	        return ResponseEntity.ok(student);
	    } else {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with ID " + id + " not found.");
	    }
	}


	
// recruiter===========================================================================================

	@Override
	public String insertRecruiter(Recruiter recruiter) {
	    String out = "";
	    try {
	        boolean admvalue = adminrepo.existsByUsername(recruiter.getUsername());
	        boolean stdvalue = studentrepo.existsByUsername(recruiter.getUsername());
	        boolean recvalue = recruiterrepo.existsByUsername(recruiter.getUsername());
	        boolean emailExists = recruiterrepo.existsByEmail(recruiter.getEmail());
	        boolean phoneExists = recruiterrepo.existsByContactNumber(recruiter.getContactNumber());

	        if (admvalue || stdvalue || recvalue) {
	            return "Username already exists. Please choose a different username";
	        }
	        if (emailExists) {
	            return "Email already exists. Please use a different email";
	        }
	        if (phoneExists) {
	            return "Contact number already exists. Please use a different contact number";
	        }

	        recruiterrepo.save(recruiter);
	        out = "Recruiter Registered Successfully";

	    } catch (Exception e) {
	        out = "Error occurred while registering recruiter: " + e.getMessage();
	    }
	    return out;
	}


    // Method to update recruiter details
    @Override
    public String updateRecruiter(Recruiter recruiter) {
        try {
            Optional<Recruiter> existingRecruiter = recruiterrepo.findById(recruiter.getRecruiterId());
            if (existingRecruiter.isPresent()) {
            	recruiterrepo.save(recruiter);
                return "Recruiter updated successfully";
            } else {
                return "Recruiter with ID " + recruiter.getRecruiterId() + " not found";
            }
        } catch (Exception e) {
            return "Error occurred while updating recruiter: " + e.getMessage();
        }
    }

    // Method to view all recruiters
    @Override
    public List<Recruiter> viewAllRecruiters() {
            List<Recruiter> recruiterList = recruiterrepo.findAll();
            return recruiterList;
    }

    // Method to view a recruiter by ID
    @Override
    public ResponseEntity<Recruiter> viewRecruiterById(int id) {
        try {
            Optional<Recruiter> recruiter = recruiterrepo.findById(id);
            if (recruiter.isPresent()) {
                return ResponseEntity.ok(recruiter.get());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recruiter with ID " + id + " not found.");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while retrieving recruiter: " + e.getMessage());
        }
    }

    // Method to delete a recruiter by ID
    @Override
    public String deleteRecruiter(int id) {
        String out = "";
        try {
            if (recruiterrepo.existsById(id)) {
                recruiterrepo.deleteById(id);  // Delete recruiter from database
                out = "Recruiter with ID " + id + " deleted successfully";
            } else {
                out = "Recruiter with ID " + id + " not found";
            }
        } catch (Exception e) {
            out = "Error occurred while deleting recruiter: " + e.getMessage();
        }
        return out;
    }

}

