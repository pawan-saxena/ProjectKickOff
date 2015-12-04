package com.kickoff;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypack.Project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

import com.database.DatabaseManagement;
import com.mime.CustomMimeMessagePreparator;

/**
 * 
 * Controller Class
 * 
 * **/
@Controller
public class KickOffController {

	@Value("${ipAddress}")
	String ipAddress; /* Ip address of the host from property file */
	
	@Value("${port}")
	String port; /* port of the host from property file*/
	
	@Value("${project}")
	String project; /* project name from property file*/

	@Value("${projectCreatedSubject}")
	String projectCreatedSubject; /* Subject of the email if project is not verified from property file*/

	@Value("${projectVerifiedSubject}")
	String projectVerifiedSubject; /* Subject of the email if project is verified from property file */
	
	@Value("${userList}")
	String userNames; /* username's string from property file */
	List<String> userList = new ArrayList<String>(); /* list of username's to whom email will be sent */
	
	@Value("${UserEmail}")
	String UserEmail; /* email's as string from property file */
	List<String> userEmailList = new ArrayList<String>(); /* list of email id's to which email will be sent */ 

	@Resource
	@Qualifier("database")
	DatabaseManagement databaseManagement; /* creating database management object to use method */
	
	@Resource
	@Qualifier("mailSender")
	JavaMailSender mailSender; /* mail sender object */

	boolean redirect = false;
	String saveOrUpdate;

	
	/**
	 *  Method to show index page and appending attribute project to the page 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView start(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
	
		if(redirect){
			request.setAttribute("saveOrUpdate", saveOrUpdate);
			redirect = false;
		}
		return getModelAndView();

	}
	
	/**Method to create a new modelAndView for index page
	 * 
	 * @return modelandview
	 */
	private ModelAndView getModelAndView(){
		Project project = new Project();
		/*creating a model and view object*/
		ModelAndView modelAndView = new ModelAndView("index");
		/*mapping pojo object to the form 'frm' in the index page*/
		modelAndView = modelAndView.addObject("projectView", project);
		
		return modelAndView;
	}

	/**
	 * 
	 * method to save the record into the database
	 *
	 * @param project : model attribute that maps to the form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saves(@ModelAttribute("projectView") Project project,
			HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		
		
		if(userEmailList.size()==0)       /*check to see if no email address available */
		{         
			setEmailsAndNames();
		}
		

		ModelAndView modelAndView = new ModelAndView("index");    /* create model and view object */
		
		return saveOrUpdateProject("save",true,project,response,modelAndView,request);

	}
	
	
	/**Method to save or update in the database
	 * 
	 * @param save: to save or update the database
	 * @param project: project to be saved or updated
	 * @param response: 
	 * @param modelAndView
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	private ModelAndView saveOrUpdateProject(String saveOrUpdate,boolean save,Project project,HttpServletResponse response,ModelAndView modelAndView,HttpServletRequest request) throws IOException{
				
		/* If project is already saved and action is from edit */
		if ((!isProjectSaved(project.getProjectIdentificationNumber())||(!save))) { 
			
			if(saveOrUpdate==null){
				
				request.setAttribute("edit", "Yes"); 
				request.setAttribute("approvedBy", project.getVerifiedBy());
				request.setAttribute("projectAprroved", "yes");
				
				return projectAlreadyExists(modelAndView,request,project);
				
			} else if(saveOrUpdate.equals("save"))   /*if page is from save controller*/
			{
				
				this.saveOrUpdate = "save";	
				
				
				List<String> apiData=getApiData((String)project.getRedmineUrl());
				if(apiData.size()==1&&(apiData.get(0).equalsIgnoreCase("Invalid Url")||apiData.get(0).equalsIgnoreCase("Exception")))
				{
					modelAndView.addObject("projectView", project);
					request.setAttribute("checkUrl", "invalid");
					return modelAndView;
				}
				else if(apiData.size()==1&&apiData.get(0).equalsIgnoreCase("noWSR"))
				{
			/*		System.out.println("NO WSR");*/
					modelAndView.addObject("projectView", project);
					request.setAttribute("checkUrl", "noWSR");
					return modelAndView;
				}
    			/**
				 * get project id (WSR #id) and nature(name) from API
				 * 
				 */
			/*	System.out.println(apiData.get(0));*/
				project.setProjectIdentificationNumber("WSR #".concat(apiData.get(0)));
				project.setNatureofproject(apiData.get(1));
				try {
					databaseManagement.saveRecord(project);
				} catch (Exception e) {
					modelAndView.addObject("projectView", project);
					request.setAttribute("checkUrl", "duplicate");
					project.setProjectIdentificationNumber(null);
					project.setNatureofproject(null);
					return modelAndView;
				}  
				/* saving project object */	
			
			} 
			else {
				this.saveOrUpdate = "update";
				
				List<String> apiData=getApiData((String)project.getRedmineUrl());
				if(apiData.size()==1&&(apiData.get(0).equalsIgnoreCase("Invalid Url")||apiData.get(0).equalsIgnoreCase("Exception")))
				{
					modelAndView.addObject("projectView", project);
					request.setAttribute("checkUrl", "invalid");
					return modelAndView;
				}
				else if(apiData.size()==1&&apiData.get(0).equalsIgnoreCase("noWSR"))
				{
			/*		System.out.println("NO WSR");*/
					modelAndView.addObject("projectView", project);
					request.setAttribute("checkUrl", "noWSR");
					return modelAndView;
				}
			
				project.setProjectIdentificationNumber("WSR #".concat(apiData.get(0)));
				project.setNatureofproject(apiData.get(1));
				try {
					databaseManagement.updateRecord(project);
				} catch (Exception e) {
					modelAndView.addObject("projectView", project);
					request.setAttribute("checkUrl", "duplicate");
					project.setProjectIdentificationNumber(null);
					project.setNatureofproject(null);
					return modelAndView;
				} 
				
			} 
			
			sendEmails(this.saveOrUpdate,project.getId(), project, false);    /*sending email's method called*/
			
			try {
				
				redirect=true;
				response.sendRedirect("/"+this.project+"/");
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}  			
			return getModelAndView(); 
			/*getting model and view object*/

		} 
		
		else {
			return projectAlreadyExists(modelAndView,request,project);
		}
		
	}
		
	private ModelAndView projectAlreadyExists(ModelAndView modelAndView,HttpServletRequest request,Project project){
		modelAndView.addObject("projectView", project);
		
		if(request.getAttribute("projectAprroved")==null){
		
			request.setAttribute("ProjectExists", "Yes");
			/* make the project existence true */
		}
		return modelAndView;
	}
	
    /**
     * Method to update the record into the database
     * @param project:Pojo class object from the index page
     * @param request
     * @param response
     * @return index page model and view
     * @throws Exception
     */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(@ModelAttribute("projectView") Project project, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		ModelAndView modelAndView = new ModelAndView("index");    /* create model and view object */
		/*System.out.println("Inside Update...");*/
		String update="update";
		
		if(userEmailList.size()==0)  
			/*check to see if email list is available*/
		{
			setEmailsAndNames();
		}	
		
		boolean isProjectSaved = false;
		
		/*System.out.println(project.getApprove());*/
		
		if(project.getApprove()){
			update=null;
			
		} else {
/*			System.out.println(project.getId()+"\n"+"Project ident. number is :  "+project.getProjectIdentificationNumber());
*/			/*getting the project object for the given id for which data will be updated*/
			Project saveProject = databaseManagement.getRecordByid(project.getId());
			
			if(saveProject.getProjectIdentificationNumber().equals(project.getProjectIdentificationNumber())){
			
				isProjectSaved = false;
				
			} else {
				
			isProjectSaved = true;
			
			}
		}
		/*System.out.println("Exiting Update...");*/
		return saveOrUpdateProject(update,isProjectSaved,project,response,modelAndView,request);				
	}	
	
	
/**
 * Method to get a list of project names and their email's from property file
 */
	private void setEmailsAndNames() {
		
		for(String usr:userNames.split(",")){			
			userList.add(usr);			
		}
		
		for(String email:UserEmail.split(",")){			
			userEmailList.add(email);
		}
		
	}
	
	/**
	 * Method to check if project id exists or not
	 * @param projectID:the id for which we want to check existence in database
	 * @return
	 */
	private boolean isProjectSaved(String projectID) {
		
		Project user = databaseManagement.getRecordByProjectId(projectID); /* get project with given id */
		
		if (user != null) {			
			return true;
		}
		return false;
	}
	
	/**
	 * Method to send mails
	 * @param userID:primary key of the table
	 * @param user:project for which mail are to be send
	 * @param verified:boolean to know that text verified or not
	 */
	private void sendEmails(String saveUpdate,final int userID, Project user, boolean verified) {
		
		CustomMimeMessagePreparator customeMimeMessagePreparator = null;		
		String updatedSubject = new String();		
		List<Runnable> threads=new ArrayList<Runnable>();		
		int index = 0;		
		String emailData = getEmailData(verified); 
		/* getting email data according to the project is verified or not */
	
		emailData = emailData.replace("User", user.getSalesperson());  /* replace Project in the property file to the salesperson*/ 
		
		for (String email : userEmailList)  
			/* getting email's from the email list */
		{ 			
			 customeMimeMessagePreparator = new CustomMimeMessagePreparator(ipAddress, port, project);  /* create message */ 
			 threads.add(customeMimeMessagePreparator);
			 customeMimeMessagePreparator.setVerified(verified); /* set verified field if project is verified */
			 customeMimeMessagePreparator.setMailSender(mailSender);
			
			 if (verified) 
				 /* To check if project id verified */
			 { 			
				emailData = emailData.replace("Person", user.getVerifiedBy());				
				customeMimeMessagePreparator.setSubject(projectVerifiedSubject);   /* set subject of mail */		
			} else {				
			if(saveUpdate.equals("update")){				
				emailData = emailData.replace("created", "Updated");
				emailData = emailData.replace("new", "");
				updatedSubject = projectCreatedSubject.replace("New", "");
				updatedSubject = updatedSubject.replace("created", "Updated");				
			} else{				
				updatedSubject = projectCreatedSubject;
			}
				customeMimeMessagePreparator.setSubject(updatedSubject);
			}
			
			String emailFormed = emailData.replace("Name", userList.get(index));    /* replace Name with the user from property file */
			customeMimeMessagePreparator.setVerfiedBy(userList.get(index));    /* create message for the current user */
			index++;
			customeMimeMessagePreparator.setEmailFormed(emailFormed);    /* set email formed for the current user */
			customeMimeMessagePreparator.setProject(user);
			customeMimeMessagePreparator.setEmail(email);
			customeMimeMessagePreparator.start();
		}
	}
	
	
	/**
	 * Method for getting email body
	 * @param verified
	 * @return
	 * @throws IOException 
	 */
	public List<String> getApiData(String redmineUrl) throws IOException
	{
		List<String> outputAPiData = new ArrayList<String>();
		
		redmineUrl=redmineUrl.trim()+"/issues.json?tracker_id=26&key=abde3ae06a4eb5c46e51ff13f33953726b9e24c8";
		StringBuffer inputJson = new StringBuffer();
		 
		 try{
			 URL url = new URL(redmineUrl);
		 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		 conn.setRequestMethod("GET");
		 BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		 if(conn.getResponseCode()!=200)
		 {
			outputAPiData.add("Invalid Url"); 
			return outputAPiData;
		 }
		 
	      String line;
	      while ((line = rd.readLine()) != null) {
	    	  inputJson.append(line);
	      }
	
	      rd.close();
	      outputAPiData=parseJson(inputJson.toString());
	      	/**
		 * 
		 * setting up URL connection;
		 */
		
		 }
		 catch(Exception generalException)
		 {
			 outputAPiData.add("Exception");
		 }
		return outputAPiData;
		
		
	}
	private  List<String> parseJson(String json) throws JSONException 
	{
		List<String> resultOut=new ArrayList<String>();
		TreeMap<Integer, String> WSR=new TreeMap<Integer,String>();
		
		JSONObject object = new JSONObject(json);
		JSONArray issues = object.getJSONArray("issues");
		JSONObject temp = null;
		boolean isWsrfound=false;
		for (int count = 0; count < issues.length(); count++)
		{	
				temp = issues.getJSONObject(count);
			if (temp.has("tracker")) 
			{	
							JSONObject tracker = temp.getJSONObject("tracker");
					/*String subject = temp.getString("subject");
					subject=subject.trim().toLowerCase();*/
					if ((tracker.getString("name").equalsIgnoreCase("WSR"))/*&& (!subject.contains("buffer")*/)
					{   
						JSONObject project=temp.getJSONObject("project");
						WSR.put((Integer)temp.getInt("id"),project.getString("name"));
						isWsrfound=true;
										}
				}
		}
		if(isWsrfound)
		{
		resultOut.add(""+String.valueOf(WSR.firstEntry().getKey()));
		
		resultOut.add(WSR.firstEntry().getValue());
		}
		else
		{
			resultOut.add("noWSR");
				}
		/**
		 * This comparator method is written to compare different type of object instances.
		 */
		/*TreeMap<Integer, String> treeMap = new TreeMap<Integer, String>(
				new Comparator<Integer>() {

				@Override
				public int compare(Integer Object1, Integer Object2) {
					return Object1.compareTo(Object2);
				}

			});
		treeMap.putAll(WSR);*/
		/*if(isWsrfound)
		{
			JSONObject project=temp.getJSONObject("project");
			resultOut.add(""+temp.getInt("id"));
			resultOut.add(project.getString("name"));
			System.out.println("Id and Name :"+resultOut.toString());
		}
		else
		{
			resultOut.add("noWSR");
		}*/
		return resultOut;

	}
	private String getEmailData(boolean verified) { 

		/* getting text file on the basis of if project is verified or not */
		String emailData = "";
		InputStream inputStream = null;

		if (!verified)  /* if not verified then text is from NotVeried.txt */
		{ 			
			inputStream = getClass().getResourceAsStream("/resources/Email/NotVerified.txt");		
		} else {
			inputStream = getClass().getResourceAsStream("/resources/Email/verified.txt");
		}
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		try {		
			String read = bufferedReader.readLine();		
			/*reading file data*/
			while (read != null) {		
				emailData = emailData + "\n" + read;		
				read = bufferedReader.readLine();
			}
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return emailData; /* returning file data */
	}

	
	/**
	 * Controller run when the verification button is clicked that is present on the email body
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public ModelAndView verify(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
		ModelAndView modelAndView = new ModelAndView("show");
		if(userEmailList.size()==0){
			setEmailsAndNames();		
		}		
		
		try
		{
			int id = Integer.parseInt(request.getParameter("userId")); /* getting the id which is verified */
			String verifiedBy = request.getParameter("verifiedBy"); /* getting the mailer which verified the form */
		 /* creating show object */
		Project project = databaseManagement.getRecordByid(id); /* getting project on the basis of given id */
		if(project==null)
		{
			redirectView("show","notFound", request, response);
			return modelAndView;
		}
		if (project.getApprove() == false) {        /* if project is not already approved */
			project.setApprove(true); /* set approve field of the the table to be true */
			project.setVerifiedBy(verifiedBy); /* set verified by with the mailer who verified the field */
			databaseManagement.updateRecord(project);
			sendEmails(null,project.getId(), project, true);
			request.setAttribute("verified", "No"); /* setting verified to "no" for jsp */
		} else {
			request.setAttribute("verified", "Yes");
		}
		request.setAttribute("verifiedBy", project.getVerifiedBy()); /* getting the user name who verified the project */
		modelAndView.addObject("user", userList);
		return modelAndView; /* returning the model and view */
		}
		catch(Exception exception)
		{
			redirectView("show","invalidURL", request, response);
			return modelAndView;
			/*request.setAttribute("verified", "Show");
			modelAndView.addObject("project", null);
			return modelAndView;*/
		}
	}
	public String redirectView(String url,String param,HttpServletRequest request, HttpServletResponse response)
	{
		request.setAttribute("ifExists", param);
		return "redirect:/"+url;
	}
	/**
	 * Method to show the project available in the database for full text search
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */	 
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {
		ModelAndView modelAndView = new ModelAndView("showall"); 
		String search = request.getParameter("name");
		/*System.out.println(search);*/
		if(search=="")
		{
			ModelAndView modelAndView1 = new ModelAndView("show"); 
			project=null;
			request.setAttribute("verified", "Show");
			modelAndView.addObject("project", project);
			return modelAndView1;
			
		}
	
		List<Project> Projectlist = databaseManagement.getRecord(search.trim()); // getting  the list  of  available  user from DatabaseManagement 
		//setting show  
		
		
		
		
		if(request.getParameter("page")==null||request.getParameter("page").equals("") )
		{
	 PagedListHolder<Project> pageList = new PagedListHolder<Project>(Projectlist); 
	 request.getSession().setAttribute("projectpage", pageList); 
	 pageList.setPage(0);
	 pageList.setPageSize(10);       
	 
	 List<Project> pagedList = pageList.getPageList();
	 modelAndView.addObject("project", pagedList);
	 if(pageList.isFirstPage()&&pageList.isLastPage())
     {
     	  modelAndView.addObject("projectList","only");	
     }
	 else if(pageList.isFirstPage())
     {
     	  modelAndView.addObject("projectList","start");	
     }
	
	         
	}
	//}
	else 
	{ 
	        String page = request.getParameter("page"); 
	        PagedListHolder pageList = (PagedListHolder)request.getSession().getAttribute("projectpage"); 
	     
	        if ("next".equals(page)) 
	        	pageList.nextPage(); 

	        else if ("previous".equals(page))
	        	pageList.previousPage();
	        if(pageList.isLastPage())
	        {
	        	 modelAndView.addObject("projectList","last");
	        }
	        else if(pageList.isFirstPage())
	        {
	        	  modelAndView.addObject("projectList","start");	
	        }
	       
	        else
	        {
	        	modelAndView.addObject("projectList","middle");	
	        }
	        List<Project> pagedList = pageList.getPageList();
	        modelAndView.addObject("project", pagedList);
	}
		
		 return modelAndView;
		
	}

	/**
	 * Method to show all the user available
	 */
	@RequestMapping(value = "/showall", method = RequestMethod.GET)
	public ModelAndView showall(HttpServletRequest request, HttpServletResponse response ,HttpSession session) throws IOException {
		List<Project> Projectlist = databaseManagement.getRecordAll();
		ModelAndView modelAndView = new ModelAndView("showall");
		//if(Projectlist.size()!=0){
			if(request.getParameter("page")==null||request.getParameter("page").equals("") )
			{
		 PagedListHolder<Project> pageList = new PagedListHolder<Project>(Projectlist); 
		 request.getSession().setAttribute("projectpage", pageList); 
		 pageList.setPage(0);
		 pageList.setPageSize(10);       
		 
		 List<Project> pagedList = pageList.getPageList();
		 modelAndView.addObject("project", pagedList);
		// modelAndView.addObject("projectpage", pageList);
	/*	modelAndView.addObject("projectList","start");*/
		 if(pageList.isFirstPage()&&pageList.isLastPage())
	     {
	     	  modelAndView.addObject("projectList","only");	
	     }
		 else if(pageList.isFirstPage())
	     {
	     	  modelAndView.addObject("projectList","start");	
	     }
		
		         
		}
		//}
		else 
		{ 
		        String page = request.getParameter("page"); 
		        PagedListHolder pageList = (PagedListHolder)request.getSession().getAttribute("projectpage"); 
		     
		        if ("next".equals(page)) 
		        	pageList.nextPage(); 

		        else if ("previous".equals(page))
		        	pageList.previousPage();
		        if(pageList.isLastPage())
		        {
		        	 modelAndView.addObject("projectList","last");
		        }
		        else if(pageList.isFirstPage())
		        {
		        	  modelAndView.addObject("projectList","start");	
		        }
		        else
		        {
		        	modelAndView.addObject("projectList","middle");	
		        }
		        List<Project> pagedList = pageList.getPageList();
		        modelAndView.addObject("project", pagedList);
		        //modelAndView.addObject("projectpage", pageList);
		      
		}
		
	
		  return modelAndView;
		
		//return modelAndView;
	}

	/**
	 * Method to show the user with id available
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/showid", method = RequestMethod.GET)
	public ModelAndView showid(HttpServletRequest request, HttpServletResponse response ,HttpSession session) throws IOException {

		ModelAndView modelAndView = new ModelAndView("show");
		request.setAttribute("verified", "Show");
		Project  project=null;
		int id;
		try
		{
		 id = Integer.parseInt(request.getParameter("id"));/* getting id for which we have to get user */
		 project = databaseManagement.getRecordByid(id); /* getting projects  form the  DatabaseManagemet */
		}
		catch(Exception exception)
		{
			modelAndView.addObject("project", project);/* adding object to the view show.jsp*/
			return modelAndView;
		}
		
		
     	
		//List<Project> list = new ArrayList();	
		//list.add(project);
		
		modelAndView.addObject("project", project);/* adding object to the view show.jsp*/
		return modelAndView;
	}
	
	
	
	/**
	 * Controller to run after clicking the edit button on showall.jsp
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(HttpServletRequest request ,HttpSession session) throws Exception {
		/*System.out.println("Inside Edit..");*/
		ModelAndView modelAndView = new ModelAndView("index"); 
		Project project=new Project();
		try{
		int id = Integer.parseInt(request.getParameter("primaryId")); /* getting hidden field data */
		project = databaseManagement.getRecordByid(id);
		if(project==null)
		{
			ModelAndView modelAndView1 = new ModelAndView("show"); 
			project=null;
			request.setAttribute("verified", "Show");
			modelAndView.addObject("project", project);
			return modelAndView1;
		}
		}
		catch(Exception exception)
		{
			ModelAndView modelAndView1 = new ModelAndView("show"); 
			project=null;
			request.setAttribute("verified", "Show"); 
			modelAndView1.addObject("project", project);
			return modelAndView1;
		}
		/* rendering view */
	 
		modelAndView.addObject("projectView", project);
		/* setting edit field to be true to show that page is from edit link */
		request.setAttribute("edit", "Yes"); 
	/*	System.out.println("Exiting Edit...");*/
		return modelAndView;
	}
}

