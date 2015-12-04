/**
 * 
 * Database Management class to implement  database logic
 * 
 * */
package com.database;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mypack.Project;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class DatabaseManagement {

	private SessionFactory sessionFactory; /* creating session factory object */
	/* getter an setter */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	public void setSessionFactory(SessionFactory sessionFactory) {
			this.sessionFactory = sessionFactory;
	}

	/**
	 * 
	 * method to search a full text search
	 * 
	 * @param search
	 *            : String object on the basis of which searching will be done
	 * @return List class object having all the Project object
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Project> getRecord(String search) {

		List projectListName = null, projectListNatureOfProject = null;
		List<Project> completeList = null;
		Session session = sessionFactory.openSession();
		/* query to run based on project name */
		Query query = session.createQuery("from Project where natureofproject like '%" + search + "%'");

		// saving result into a list
		projectListNatureOfProject = query.list();
		completeList = projectListNatureOfProject;
		session.beginTransaction().commit();
		session = sessionFactory.openSession();

		// query to run based on customer name
		Query query1 = session.createQuery("from Project where delcontact like '%" + search	+ "%'");
		projectListName = query1.list();
		completeList.addAll(projectListName);
		session.beginTransaction().commit();
		session.close();
		Set<Project> set = new HashSet<Project>();
		set.addAll(completeList);
		completeList.clear();
		completeList.addAll(set);
		ProjectComparator comparator = new ProjectComparator();
		Collections.sort(completeList, comparator);

	    return completeList;
	}

	/**
	 * Method to get all records of the database
	 * 
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Project> getRecordAll() {

		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Project"); /* Query to run on the database */
		List list = query.list(); /* getting result in the list */
		/*System.out.println(list.size());*/
		List<Project> allProjects = list; /* Type casting to Project object list */
		session.beginTransaction().commit();
		session.close();
		ProjectComparator comparator = new ProjectComparator();
		Collections.sort(allProjects, comparator);
		return allProjects;
	}

	/**
	 * Method to save data of the given project
	 * 
	 * @param project1: Project to save
	 */
	public void updateRecord(Project project1) {
		// creating session object for accessing the database table
		Session session = sessionFactory.openSession();
		
		if(project1.getProjectIdentificationNumber().length()>245){
		project1.setProjectIdentificationNumber(project1.getProjectIdentificationNumber().substring(0,244));
		}
		session.update(project1); /* saving project */

		/* creating transaction object an committing */
		session.beginTransaction().commit();
		session.close();

	}

	public void saveRecord(Project project1) throws Exception{
		// creating session object for accessing the database table
		Session session = sessionFactory.openSession();
		
		if(project1.getProjectIdentificationNumber().length()>245){
			project1.setProjectIdentificationNumber(project1.getProjectIdentificationNumber().substring(0,244));
		}
	
		session.save(project1); /* saving project */
	
		/* creating transaction object an committing */
		session.beginTransaction().commit();
		session.close();

	}

	/**
	 * Method to get Project object on the basis of id
	 * 
	 * @param search: id to search
	 * 
	 * */
	@SuppressWarnings("rawtypes")
	public Project getRecordByProjectId(String projectID) {

		Session session = sessionFactory.openSession();
		/* Query to extract the list of projects on the basis of id */
		Query query = session.createQuery("from Project where projectIdentificationNumber=?");
		query.setString(0, projectID); /* setting parameter in the query */

		/* saving result into a list */
		List list = query.list();
		Project project = null;
		if (list.size() >= 1) {
			project = (Project) list.get(0); /* getting project from the list */
		}
		session.close(); /* closing current session */		
		return project; /* returning project */
	}

	/**
	 * Getting records list on the basis of id
	 */
	@SuppressWarnings("unchecked")
	public Project getRecordByid(int id) {
		
		Session session = sessionFactory.openSession();
		/* Query to run */
		Query query = session.createQuery("from Project where id=?");
		query.setInteger(0, id); /* setting parameter in the query */
		/* saving result into a list */
		List<Project> list = query.list();
		session.close();
		if (list.size() >= 1) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	public class ProjectComparator implements Comparator<Project> {

	    public int compare(Project project1, Project project2) {
	    	return project1.getId() < project2.getId() ? 1 : -1;
	    }

	}
	
	
}
