package com.jfsd.CareerConnect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.jfsd.CareerConnect.models.Job;
import com.jfsd.CareerConnect.models.JobApplication;
import com.jfsd.CareerConnect.models.Recruiter;
import com.jfsd.CareerConnect.services.RecruiterService;

@Controller
@RequestMapping("recruiter")
@CrossOrigin(origins = "*")
public class RecruiterController {

    @Autowired
    private RecruiterService recruiterService;

    // Fetch recruiter details by recruiterId
    @GetMapping("/viewbyid/{id}")
    public ResponseEntity<Recruiter> getRecruiterById(@PathVariable("id") int recruiterId) {
        Recruiter recruiter = recruiterService.getRecruiterById(recruiterId);
        if (recruiter != null) {
            return new ResponseEntity<>(recruiter, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update recruiter profile by recruiterId
    @PutMapping("update")
    public ResponseEntity<Recruiter> updateRecruiter(@RequestBody Recruiter recruiterDetails) {
    	Recruiter recruiter = recruiterService.updateRecruiter(recruiterDetails);
        if (recruiter != null) {
            return new ResponseEntity<>(recruiter, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    
    @PostMapping("addjob")
    public ResponseEntity<Job> insertJob(@RequestBody Job job,@RequestParam int recruiterId)
    {
    	String s= recruiterService.addJob(job,recruiterId);
    	if(s.contains("successfully"))
    	{
    		return new ResponseEntity<>(job,HttpStatus.OK);
    	}
    	else
    	{
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    }
    
    
    @GetMapping("viewalljobs")
    public ResponseEntity<List<Job>> viewalljobs(@RequestParam("id") int recruiterId) {
        List<Job> list = recruiterService.viewalljobs(recruiterId);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Empty list
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("viewapplicationsbyid")
    public ResponseEntity<?> viewApplicationsById(@RequestParam("jobId") int jobId) {
        try {
            List<JobApplication> applications = recruiterService.viewapplicationsbyjobid(jobId);

            if (applications.isEmpty()) {
                return new ResponseEntity<>("No applications found for the given job ID.", HttpStatus.NO_CONTENT);
            }

            return ResponseEntity.ok(applications);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
}
