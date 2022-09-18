package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the Activity database table.
 * 
 */
@Entity
@NamedQuery(name="Activity.findAll", query="SELECT a FROM Activity a")
public class Activity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idActivity;

	@Temporal(TemporalType.DATE)
	private Date date;

	private int numberOfHours;

	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="employee")
	private Employee employeeBean;

	//bi-directional many-to-one association to Project
	@ManyToOne
	@JoinColumn(name="project")
	private Project projectBean;

	public Activity() {
	}

	public int getIdActivity() {
		return this.idActivity;
	}

	public void setIdActivity(int idActivity) {
		this.idActivity = idActivity;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getNumberOfHours() {
		return this.numberOfHours;
	}

	public void setNumberOfHours(int numberOfHours) {
		this.numberOfHours = numberOfHours;
	}

	public Employee getEmployeeBean() {
		return this.employeeBean;
	}

	public void setEmployeeBean(Employee employeeBean) {
		this.employeeBean = employeeBean;
	}

	public Project getProjectBean() {
		return this.projectBean;
	}

	public void setProjectBean(Project projectBean) {
		this.projectBean = projectBean;
	}

}