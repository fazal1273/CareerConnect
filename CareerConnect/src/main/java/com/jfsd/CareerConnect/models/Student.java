	package com.jfsd.CareerConnect.models;
	
	import jakarta.persistence.*;
	import jakarta.validation.constraints.*;
	import java.util.Arrays;
	import java.util.List;
	
	@Entity
	@Table(name = "students")
	public class Student {
	
	    @Id
	    private int id;
	
	    @Column(nullable = false)
	    private String name;
	
	    @Column(name = "cgpa", nullable = false)
	    private Double cgpa;
	
	    @Column(name = "phone_number", nullable = false,unique = true)
	    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number")
	    private String phoneNumber;
	
	    @Column(nullable = false)
	    private String program;
	
	    @Column(nullable = false, unique = true)
	    //@Email(message = "Invalid email format")
	    private String email;
	
	    @Column(nullable = false)
	    private String department;
	
	    @Column(name = "graduation_year")
	    private int graduationYear;
	
	    
	    @Column(name = "username",unique=true,nullable=false)
	    private String username;
	    
	    @Column(name = "password",nullable=false)
	    private String password;
	    
	    @Lob
	    @Column(columnDefinition = "LONGBLOB")
	    private byte[] photo;
	    
	    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	    private List<JobApplication> applications;
	
	
	    // Default constructor
	    public Student( ) {}
	    
	    public Student(byte[] photo) {
	    	this.photo=photo;
	    }
	
	    // Constructor with resume as byte[]
	    public Student(int id, String name, double cgpa, String phoneNumber, String program, String email,
	                   String department, int graduationYear, String username,String password) {
	        this.id = id;
	        this.name = name;
	        this.cgpa = cgpa;
	        this.phoneNumber = phoneNumber;
	        this.program = program;
	        this.email = email;
	        this.department = department;
	        this.graduationYear = graduationYear;
	        this.username=username;
	        this.password=password;
	    }
	
	    // Getters and Setters
	    public int getId() {
	        return id;
	    }
	
	    public void setId(int id) {
	        this.id = id;
	    }
	
	    public String getName() {
	        return name;
	    }
	
	    public void setName(String name) {
	        this.name = name;
	    }
	
	    public double getCgpa() {
	        return cgpa;
	    }
	
	    public void setCgpa(double cgpa) {
	        this.cgpa = cgpa;
	    }
	
	    public String getPhoneNumber() {
	        return phoneNumber;
	    }
	
	    public void setPhoneNumber(String phoneNumber) {
	        this.phoneNumber = phoneNumber;
	    }
	
	    public String getProgram() {
	        return program;
	    }
	
	    public void setProgram(String program) {
	        this.program = program;
	    }
	
	    public String getEmail() {
	        return email;
	    }
	
	    public void setEmail(String email) {
	        this.email = email;	
	    }
	
	    public String getDepartment() {
	        return department;
	    }
	
	    public void setDepartment(String department) {
	        this.department = department;
	    }
	
	    public int getGraduationYear() {
	        return graduationYear;
	    }
	
	    public void setGraduationYear(int graduationYear) {
	        this.graduationYear = graduationYear;
	    }
	
	    public String getUsername() {
			return username;
		}
	
		public void setUsername(String username) {
			this.username = username;
		}
	
	    public String getPassword() {
	        return password;
	    }
	
	    public void setPassword(String password) {
	        this.password = password;
	    }
	
	    // String representation
	    @Override
	    public String toString() {
	        return "Student{" +
	                "id=" + id +
	                ", name='" + name + '\'' +
	                ", cgpa=" + cgpa +
	                ", phoneNumber='" + phoneNumber + '\'' +
	                ", program='" + program + '\'' +
	                ", email='" + email + '\'' +
	                ", department='" + department + '\'' +
	                ", graduationYear=" + graduationYear +
	                '}';
	    }
	
		public byte[] getPhoto() {
			return photo;
		}
	
		public void setPhoto(byte[] photo) {
			this.photo = photo;
		}
	
		
	}
