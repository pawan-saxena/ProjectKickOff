/**
 * Class to create message to send
 * 
 * */
package com.mime;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import mypack.Project;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class CustomMimeMessagePreparator extends Thread implements MimeMessagePreparator{

	private String email; /* email to which we have to send the message */
	
	private String emailFormed;
	
	private Project user; /* user to which message is send */
	
	private String  ipAddress; /* ip address to return to after verification */
	
	private String port; /* port for ip address */
	
	private String project;
	
	private boolean verified;
	
	private String verfiedBy; /* name of the user who verify the form */
	
	private String subject; /* subject of the email */
	
	JavaMailSender mailSender;
	
	/* getter and setters */
	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getVerfiedBy() {
		return verfiedBy;
	}

	public void setVerfiedBy(String verfiedBy) {
		this.verfiedBy = verfiedBy;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailFormed() {
		return emailFormed;
	}

	public void setEmailFormed(String emailFormed) {
		this.emailFormed = emailFormed;
	}

	public Project getProject() {
		return user;
	}

	public void setProject(Project user) {
		this.user = user;
	}

	public  CustomMimeMessagePreparator() {
		super();
	}
	
	/**
	 * 
	 * @param ipAddress : ipAddress of the server on which the application will be deployed
	 * @param port : port of the server on which the application will be deployed
	 * @param project : name of the project
	 */
	public  CustomMimeMessagePreparator(String ipAddress, String port, String project) {
		
		this.ipAddress = ipAddress;
		this.port = port;
		this.project = project;
		
	}
	
	/**
	 * prepare message to send
	 */
	@Override
	public void prepare(MimeMessage mimeMessage) throws Exception {
		
		mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
		mimeMessage.setFrom("defaultoptimusinfo@gmail.com");
		String URL ="http://" + ipAddress + ":" + port + "/" + project+ "/verify?userId="+user.getId()+"&verifiedBy="+verfiedBy;
		
		// email body which is send
		String emailBody = "<p style='font-family:courier'>"+emailFormed+"</p><div style='font-family:courier;text-align:left;border: 1px solid #000;margin:0px auto; padding:15px'><br>"
				+ "<b>Nature of the project: </b>" + user.getNatureofproject() +"<br><br>"
				+ "<b>Unique project identification number:</b>" +user.getProjectIdentificationNumber() +"<br><br>"
				+ "<b>Name of the potential opportunity : </b>" + user.getPotentialopp() +"<br><br>"
				+ "<b>Sales person : </b>" + user.getSalesperson()  +"<br><br>"
				+ "<b>Project scope summary:</b> " + user.getProjectsumm() +"<br><br>"
				+ "<b>Any project risks or implied customer commitments: </b>" + user.getProjectrisk() +"<br><br>"
				+ "<b>Payment terms:</b>" + user.getPaymentterms() +"<br><br>"
				+ "<b>Total SOW dollar value :</b>" + user.getSowdolar() +"<br><br>"
				+ "<b>Hourly billing rate :</b>" + user.getHourlybillingrate() +"<br><br>"
				+ "<b>Location where the signed SOW soft copy is saved :</b>" + user.getSowcopy() +"<br><br>"
				+ "<b>Name of the customer project delivery contact :</b>" + user.getDelcontact() +"<br><br>"
				+ "<b>Mail ID of the customer project delivery contact: </b>" + user.getMaildelcontact() +"<br><br>"
				+ "<b>Name of the customer project commercial contact :</b>" + user.getComcontact() +"<br><br>"
				+"<b>Mail ID of the customer project commercial contact :</b>" + user.getMailcomcontact() +"<br><br>";
				
				mimeMessage.setSubject(subject);
				
				/* If not verified then a verify button is attached to the email body */
				if(!verified) {
					
					mimeMessage.setContent(emailBody+"<br><a href='"+URL+"'><button style='width:100px;height:10px;background-color:#3b5998;color:white'>Verify</button></a></div>","text/html");
				
				} else{ /* If  verified then a verify button is not attached to the email body */
					
					mimeMessage.setContent(emailBody+"</div>","text/html");
					
				}
	}
	
	/**
	 * Thread to run
	 */
	@Override
	public void run() {
		
		mailSender.send(this); /* send the message */
	}
	}


