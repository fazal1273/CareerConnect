package com.jfsd.CareerConnect.services;
//Java Program to Illustrate Creation Of
//Importing required classes

import com.jfsd.CareerConnect.models.EmailDetails;

//Interface
public interface EmailService {

 // To send a simple email
 String sendSimpleMail(EmailDetails details);

 // Method
 // To send an email with attachment
 String sendMailWithAttachment(EmailDetails details);
}
