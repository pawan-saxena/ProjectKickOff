# ResetProjectKickOff
ProjectKickOff Extension

# ProjectKickOff
@Author : Naveen Pratap Singh (naveenpratap.singh@optimusinfo.com)

@contributor : Pawan Saxena (pawan.saxena@optimusinfo.com)

@ProjectConfiguration : This application is deployed and tested in following environments :
 
 @Configuration 1 :
 
 JDK 1.8,
 Apache Tomact 8,
 Spring web-mvc 3.0,
 jquery 1.11.2,
 jquery validation.js
 
 
 @Configuration 2 :
 
 JDK 1.7,
 Apache Tomcat 7,
 Spring web-mvc 3.0,
 jquery 1.11.2,
 jquery validation.js
 
 
 Browsers tested : 
 
 Chrome:46.0,
 FireFox:42.0,
 Internet:Explorer 11.0
 
 
Notes : Kindly do make the following changes  in the source after cloning this project.

1.Set following properties in kickOff.properties under projectkickoff/src/resources
  ipAddress (in case if you are hosting it on domain other than localhost).
  port (default port , should be changed in case if running different projects simultaneously).
  userList(This will be used to populate the Adresse in the sent mail)
  UserEmail (The comma separated users email list who will be informed after the creation and updation of the project).
  hibernate.driver
  hibernate.url
  hibernate.user
  hibernate.pass
  hibernate.hbm2ddl.auto
  hibernate.dialect
  hibernate.show_sql
  
  
2.Update the content of verified.txt and NotVerified.txt as per your requirement under projectkickoff/src/resources
  
3.Update the beanid="mailSender" properties "username" and "password" in spring-servlet.xml under projectkickoff/WebContent/WEB-INF.
  These account details will be used to send the email to the people listed in the userList property in KickOff.properties.

4.The modifications could be done as per the requirement such as changing api endpoints and changing accesskey and many others as the application is extended.
  
