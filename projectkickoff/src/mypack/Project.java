/**
 * Persistent class User
 */
package mypack;

import javax.persistence.Column;
import javax.persistence.Entity;  
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;  
import javax.persistence.Table;


/* POJO class to use in the project */

@Entity  
@Table(name= "Project")  
public class Project {  
	
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private int id ;



@Column(unique=true) 
private String projectIdentificationNumber=null; 

@Column(columnDefinition="TEXT")
private String redmineUrl;

public String getRedmineUrl() {
	return redmineUrl;
}
public void setRedmineUrl(String redmineUrl) {
	this.redmineUrl = redmineUrl;
}
@Column(columnDefinition="TEXT")
private String projectsumm;

@Column(columnDefinition="TEXT")
private String projectrisk;

@Column(columnDefinition="TEXT")
private String potentialopp,salesperson,natureofproject,paymentterms,sowdolar,hourlybillingrate;

boolean sowasignedday=true;
private boolean approve = false;

@Column(columnDefinition="TEXT")
private String sowcopy,delcontact,maildelcontact,comcontact,mailcomcontact,sresponse;

@Column(columnDefinition="TEXT")
private String verifiedBy;

private String sowstopday,sowappday,sowstartday;
public String getSowstopday() {
	return sowstopday;
}
public void setSowstopday(String sowstopday) {
	this.sowstopday = sowstopday;
}
public String getSowappday() {
	return sowappday;
}
public void setSowappday(String sowappday) {
	this.sowappday = sowappday;
}
public String getSowstartday() {
	return sowstartday;
}
public void setSowstartday(String sowstartday) {
	this.sowstartday = sowstartday;
}

public String getVerifiedBy() {
	return verifiedBy;
}
/*getters and setters*/
public void setVerifiedBy(String verifiedBy) {
	this.verifiedBy = verifiedBy;
}

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getProjectIdentificationNumber() {
	return projectIdentificationNumber;
}
public void setProjectIdentificationNumber(String projectIdentificationNumber) {
	this.projectIdentificationNumber = projectIdentificationNumber;
}

public boolean getApprove() {
	return approve;
}

public void setApprove(boolean approve) {
	this.approve = approve;
}

//getters and setters for private objects


public String getPotentialopp() {  
    return potentialopp;  
}  

public void setPotentialopp(String potentialopp) {  
    this.potentialopp = potentialopp;  
} 

public String getSalesperson() {  
    return salesperson;  
}  

public void setSalesperson(String salesperson) {  
    this.salesperson = salesperson;  
}

public String getNatureofproject() {  
    return natureofproject;  
}  

public void setNatureofproject(String natureofproject) {  
    this.natureofproject = natureofproject;  
} 

public String getProjectsumm() {  
    return projectsumm;  
}  

public void setProjectsumm(String projectsumm) {  
    this.projectsumm = projectsumm;  
}

public String getProjectrisk() {  
    return projectrisk;  
}  
public void setProjectrisk(String projectrisk) {  
    this.projectrisk = projectrisk;  
}



public String getPaymentterms() {  
    return paymentterms;  
}  

public void setPaymentterms(String paymentterms) {  
    this.paymentterms = paymentterms;  
}  

public String getSowdolar() {  
    return sowdolar;  
}  

public void setSowdolar(String sowdolar) {  
    this.sowdolar = sowdolar;  
} 

public String getHourlybillingrate() {  
    return hourlybillingrate;  
}  

public void setHourlybillingrate(String hourlybillingrate) {  
    this.hourlybillingrate = hourlybillingrate;  
} 

 

public boolean getSowasignedday() {  
    return sowasignedday;  
}  

public void setSowasignedday(boolean sowasignedday) {  
    this.sowasignedday = sowasignedday;  
} 


public String getSowcopy() {  
    return sowcopy;  
}  

public void setSowcopy(String sowcopy) {  
    this.sowcopy = sowcopy;  
} 



public String getDelcontact() {  
    return delcontact;  
}  

public void setDelcontact(String delcontact) {  
    this.delcontact = delcontact;  
}

public String getMaildelcontact() {  
    return maildelcontact;  
} 

public void setMaildelcontact(String maildelcontact) {  
    this.maildelcontact = maildelcontact;  
}

public String getComcontact() {  
    return comcontact;  
}  

public void setComcontact(String comcontact) {  
    this.comcontact = comcontact;  
}

public String getMailcomcontact() {  
    return mailcomcontact;  
}  

public void setMailcomcontact(String mailcomcontact) {  
    this.mailcomcontact = mailcomcontact;  
}

public String getSresponse() {  
    return sresponse;  
}  

public void setSresponse(String sresponse) {  
    this.sresponse = sresponse;  
}

@Override
public boolean equals(Object object){
	Project  project = (Project)object;
	if(id==project.getId()){
		return true;
	}
	return false;
	
}
@Override
public int hashCode(){

	return 1;
}
}  