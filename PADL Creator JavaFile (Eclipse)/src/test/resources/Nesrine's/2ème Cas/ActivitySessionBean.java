package ejbModule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import model.Activity;
import model.Employee;
import model.Project;

@Stateless
@LocalBean
public class ActivitySessionBean {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@EJB
	private ProjectSessionBean projectSB;
	
	/**
	 * Get activities by date, employee and project
	 *
	 * @return A list of activities
	 */
    public List<Activity> getActivitiesByDateAndEmployeeAndProject(Date date, Employee employee, Project project) {
    	TypedQuery<Activity> query = entityManager.createNamedQuery(
    			"Activity.findActivitiesByDateAndEmployeeAndProject", Activity.class)
    			.setParameter("date", date)
    			.setParameter("idEmployee", employee.getIdEmployee())
    			.setParameter("idProject", project.getIdProject());
    	List<Activity> activities = query.getResultList();
    	return  activities;
    }
	
	/**
	 * Get current month activities by project
	 *
	 * @return A list of activities
	 */
    public List<Activity> getCurrentMonthTotalNumberOfHoursByProject(Project project) {
    	Calendar now = Calendar.getInstance();
    	try {
			Date minday = new SimpleDateFormat("dd/MM/yyyy").parse("01/" + now.get(Calendar.MONTH)+1 + "/" + now.get(Calendar.YEAR));		
	    	Date maxday = new SimpleDateFormat("dd/MM/yyyy").parse(now.getActualMaximum(Calendar.DAY_OF_MONTH) + "/" + now.get(Calendar.MONTH)+1 + "/" + now.get(Calendar.YEAR));
	    	TypedQuery<Activity> query = entityManager.createNamedQuery(
	    			"Activity.findCurrentMonthActivitiesAndProject", Activity.class)
	    			.setParameter("idProject", project.getIdProject())
	    			.setParameter("minDay", minday).setParameter("maxDay", maxday);
	    	List<Activity> activities =  query.getResultList();
	    	return activities;
    	} catch (ParseException e) {
			e.printStackTrace();
			return new ArrayList<Activity>();
		}
    }
    
	/**
	 * Get all current day activities by employee, 
	 * PLUS the projects he did not submit yet.
	 *
	 * @return A list of activities
	 */
    public List<Activity> getAllCurrentDayActivitiesByEmployee(Employee employee) {
    	List<Activity> activities = new ArrayList<Activity>();
    	List<Project> empProjects = projectSB.getOpenProjectsByEmployee(employee);
    	for (Project project : empProjects) {
    		List<Activity> act = getActivitiesByDateAndEmployeeAndProject(new Date(), employee, project);
    		// if the project time has already been submitted today, add it
    		if(act.size() > 0)
    			activities.add(act.get(0));
    		// if not, create a blank Activity object and add it
    		else {
    			Activity newAct = new Activity();
    			newAct.setDate(new Date());
    			newAct.setEmployeeBean(employee);
    			newAct.setNumberOfHours(0);
    			newAct.setProjectBean(project);
    			activities.add(newAct);
    		}
		}
    	return activities;
    }
    
    /**
     * Create or updates a Activity entity to the database.
     * 
     * @param act The Activity Entity to be inserted or updated
     */
    public void saveActivity(Activity act) {
    	try {
    		// if already in database, update
    		if(act.getIdActivity() != 0) {
    	    	entityManager.merge(act);
    		}
    		// if not in DB and new hours, insert
    		else if (act.getNumberOfHours() > 0) {
    			entityManager.persist(act);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
