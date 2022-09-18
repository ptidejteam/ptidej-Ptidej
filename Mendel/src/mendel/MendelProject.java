/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package mendel;

import java.io.File;
import java.util.Properties;

import mendel.parser.JParser;


/**
 * Initializating Properties:
 * - projectname: name of project to be used to create key, output files...
 * - sources: comma separated list of classfiles or class folders to be explored
 * 
 * @author Simon Denier
 * @since Feb 14, 2008
 * 
 */
public class MendelProject {
	
	/*
	 * TODO: main technical points for Mendel improvments
	 * - include/exclude parameter to configure a by-package filter (JParser)
	 * - Ghost model
	 */

	private IParser parser;

	private IRepository repository;

	private Driver[] drivers;

	private String projectname;

	private ClassfileEnv env;
	

	public MendelProject() {
		setRepository(new EntityRepository());
	}
	


	public String getProjectname() {
		return this.projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	
	public IRepository getRepository() {
		return this.repository;
	}

	public void setRepository(IRepository repository) {
		this.repository = repository;
		this.parser = new JParser(this.repository);
	}
	
	public void setDrivers(Driver[] drivers) {
		this.drivers = drivers;
	}

	

	public File[] getClassfiles() {
		return this.env.getClassfiles();
	}

	public String[] getClassnames() {
		return this.env.getClassnames();
	}

	public String getClasspath() {
		return this.env.getClasspath();
	}

	public String[] getPackages() {
		return this.env.getPackages();
	}


	public void initializeEnv(String[] path) {
		this.env = new ClassfileEnv(path);
	}
	
	
	/**
	 * Programmatic configuration.
	 */
	public MendelProject initialize(String projectName, Driver[] drivers, String[] path) {
		setProjectname(projectName);
		setDrivers(drivers);
		initializeEnv(path);
		return this;
	}


	/**
	 * Batch configuration by properties file
	 */	
	public MendelProject initialize(String[] propertiesFiles) {
		// propertiesFile[0] => project properties
		initializeProject(propertiesFiles);
		// other propertiesFile => drivers chain
		initializeDrivers(propertiesFiles);
		return this;
	}

	protected void initializeProject(String[] propertiesFiles) {
		Properties projectProperties = Util.loadPropertiesFile(propertiesFiles[0]);
		// TODO: check non-null values
		setProjectname(projectProperties.getProperty("projectname"));
		String[] sources = Util.extractValues(projectProperties, "sources");
		initializeEnv(sources);
	}


	/**
	 * @param propertiesFile
	 */
	protected void initializeDrivers(String[] propertiesFiles) {
		Driver[] drivers = new Driver[propertiesFiles.length-1]; // TODO: check length?
		// shift: first item in array is general properties file for project
		for (int i = 1; i < propertiesFiles.length; i++) {
			drivers[i-1] = loadDriver(propertiesFiles[i]);
		}
		setDrivers(drivers);
	}

	protected Driver loadDriver(String propertiesFile) {
		Properties properties = Util.loadPropertiesFile(propertiesFile);
		String driverClassname = properties.getProperty("driver");
		Driver driver = (Driver) Util.createInstance(driverClassname);
		if (driver != null) {
			driver.initialize(this, properties);
		}
		return driver;
	}

	
	
	/*
	 * General Interface.
	 */
	
	
	/**
	 * Populate repository with class models.
	 */
	public void populate() {
		for (int i = 0; i < getClassnames().length; i++) {
			this.parser.start(getClassnames()[i]);
		}
	}

	/**
	 * Run drivers in sequence.
	 */
	public void batchRun() {
//		this.drivers[0].setInput(this.repository.getAllEntities());
		for (int i = 0; i < this.drivers.length; i++) {
			Driver driver = this.drivers[i];
			// TODO: in the current fashion, drivers run independently from each
			// other.
			driver.setInput(this.repository.getAllEntities());
			driver.start();
			driver.runAll();
			driver.end();
		}
	}
	
	
	public void launch() {
		populate();
		batchRun();
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		new MendelProject().initialize(args).launch();
	}

}
