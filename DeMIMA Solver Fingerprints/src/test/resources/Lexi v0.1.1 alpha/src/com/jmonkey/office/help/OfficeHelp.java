package com.jmonkey.office.help;


// Swing Imports
import java.awt.BorderLayout;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.jmonkey.office.lexi.support.images.Loader;



public class OfficeHelp extends JFrame  {
	//static URL backURL = null;
	//static URL forURL = null;
	//htmlFrame hFrame;
	private String start_loc = null;
	//protected Browser iceBrowser;
	private Class iceClass;
	private JComponent iceBrowser;
	
	public OfficeHelp(String helpFile) {
		super("Program Help");
		this.setIconImage(Loader.load("help_book16.gif"));
		this.getContentPane().setLayout(new BorderLayout());
		start_loc = "http://www.jmonkey.com/~mschmidt/help/" + helpFile + "/index.html";
	
		try {
		iceClass = Class.forName("ice.iblite.Browser");
		iceBrowser = (JComponent) iceClass.newInstance();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Can't find class ice.iblite.Browser\n" + " put icebrowserlist.jar in your CLASSPATH", "Can't find class ice.iblite.Browser", JOptionPane.WARNING_MESSAGE);
		}
		
		this.getContentPane().add("Center",iceBrowser);
		
		Object[] args = {start_loc};
		iceMethodCall("gotoLocation", args);
		
		//iceBrowser.gotoLocation(start_loc);
			
	}
		/**
		* Use this so that you don't need the iceBrowser classes at
		* compile.  This code is taken from the Jazilla project, but
		* might also be in the IceBrowser Demos
		*/
		private Object iceMethodCall(String methodName, Object[] args) {
		// Figure the Class of each element of args[]:
		Class[] parameterTypes = new Class[args.length];
		for (int i=0; i<args.length; i++) {
			parameterTypes[i] = args[i].getClass();
		}
		try {
			Method method = iceClass.getMethod(methodName, parameterTypes);
			return (method.invoke(iceBrowser, args));	
		}
		catch (NoSuchMethodException e) {
			JOptionPane.showMessageDialog(null, "No such method in ice.iblite.Browser: " + methodName, "Warning", JOptionPane.WARNING_MESSAGE);
		}
		catch (InvocationTargetException e) {
			JOptionPane.showMessageDialog(null, "InvocationTargetException in call to: " + methodName, "Warning", JOptionPane.WARNING_MESSAGE);
		}
		catch (IllegalAccessException e) {
			JOptionPane.showMessageDialog(null, "IllegalAccessException in call to: " + methodName, "Warning", JOptionPane.WARNING_MESSAGE);
		}
		return null;
	}
	private void redirectOutput() {
			try {
				FileOutputStream fileOut = new FileOutputStream("Output.txt", true);
				PrintStream thePrintStream = new PrintStream(fileOut);
				System.setOut(thePrintStream);
				System.setErr(thePrintStream);
			} catch (java.io.IOException ex) {
				System.out.println("Error writing: " + ex);
				//Code.failed(ex);
			}
	}
}
