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
package metamodel.scenariodiagram;


public class Component implements Comparable {

	/** 
	 * @uml.property name="scenarioDiagram"
	 * @uml.associationEnd multiplicity="(1 1)" inverse="component:metamodel.scenarioDiagram.ScenarioDiagram"
	 */
	private ScenarioDiagram scenarioDiagram ;
	
	/** 
	 * Getter of the property <tt>scenarioDiagram</tt>
	 * @return  Returns the scenarioDiagram.
	 * @uml.property  name="scenarioDiagram"
	 */
	public ScenarioDiagram getScenarioDiagram() {
		return this.scenarioDiagram;
	}

	/** 
	 * Setter of the property <tt>scenarioDiagram</tt>
	 * @param scenarioDiagram  The scenarioDiagram to set.
	 * @uml.property  name="scenarioDiagram"
	 */
	public void setScenarioDiagram(ScenarioDiagram scenarioDiagram) {
		this.scenarioDiagram = scenarioDiagram;
	}

	/**
	 * @uml.property  name="operand"
	 * @uml.associationEnd  inverse="components:metamodel.scenarioDiagram.Operand"
	 */
	private Operand operand;

	/**
	 * Getter of the property <tt>operand</tt>
	 * @return  Returns the operand.
	 * @uml.property  name="operand"
	 */
	public Operand getOperand() {
		return this.operand;
	}

	/**
	 * Setter of the property <tt>operand</tt>
	 * @param operand  The operand to set.
	 * @uml.property  name="operand"
	 */
	public void setOperand(Operand operand) {
		this.operand = operand;
	}

	// ========================================================================
	
	
	//protected String originalTraceStatement ;
	private int header;
	protected int level;
	protected int index;
		
	
//	/**
//	 * Getter of the property <tt>originalTraceStatement</tt>
//	 * @return  Returns the originalTraceStatement.
//	 * @uml.property  name="originalTraceStatement"
//	 */
//	public String getOriginalTraceStatement() {
//		return originalTraceStatement;
//	}
	
	/**
	 * Getter of the property <tt>signature</tt>
	 * @return  Returns the signature.
	 * @uml.property  name="signature"
	 */
	public int getHeader() {
		return this.header;
	}
	
	/**
	 * Getter of the property <tt>signature</tt>
	 * @return  Returns the signature.
	 * @uml.property  name="signature"
	 */
	public int getLevel() {
		return this.level;
	}
	
	/**
	 * Getter of the property <tt>signature</tt>
	 * @return  Returns the signature.
	 * @uml.property  name="signature"
	 */
	public int getIndex() {
		return this.index;
	}
	
//	/**
//	 * Setter of the property <tt>operand</tt>
//	 * @param operand  The operand to set.
//	 * @uml.property  name="operand"
//	 */
//	public void setOriginalTraceStatement(String originalTraceStatement) {
//		this.originalTraceStatement = originalTraceStatement;
//	}
	
	/**
	 * Setter of the property <tt>operand</tt>
	 * @param operand  The operand to set.
	 * @uml.property  name="operand"
	 */
	public void setHeader(int header) {
		this.header = header;
	}
	
	/**
	 * Setter of the property <tt>operand</tt>
	 * @param operand  The operand to set.
	 * @uml.property  name="operand"
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	
	
	/**
	 * Setter of the property <tt>operand</tt>
	 * @param operand  The operand to set.
	 * @uml.property  name="operand"
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	
//	/**
//	 * Constructor
//	 * @param sd  the scenarioDiagram to which the message belongs.
//	 * @param type  determine whether a message is an entry or an exit
//	 */
//	public Component(ScenarioDiagram scenarioDiagram, int header, String originalTraceStatement)
//	{
//		this.scenarioDiagram = scenarioDiagram;
//		this.header = header;
//		this.originalTraceStatement = originalTraceStatement;
//	}

	/**
	 * Constructor
	 * @param sd  the scenarioDiagram to which the message belongs.
	 * @param type  determine whether a message is an entry or an exit
	 */
	public Component(int header/*, String originalTraceStatement*/)
	{
		this.header = header;
		//this.originalTraceStatement = originalTraceStatement;
	}
	
	/**
	 * Constructor
	 * @param sd  the scenarioDiagram to which the message belongs.
	 */	
	public Component (ScenarioDiagram scenarioDiagram)
	{
		this.scenarioDiagram = scenarioDiagram;
	}
	
	
//	/**
//	 * Constructor
//	 * @param originalTraceStatement 
//	 */	
//	public Component (String originalTraceStatement)
//	{
//		this.originalTraceStatement = originalTraceStatement ;
//	}

	/**
	 * Constructor
	 * @param originalTraceStatement 
	 */	
	public Component ()
	{
		
	}
	
	
//	/**
//	 * Overwrites equals of Object.
//	 * @param otherMessage  the message to 
//	 */
//	public boolean equals(Object otherComponent)
//	{
//		
//		if(! (otherComponent instanceof Component))
//			return false;
//		if(otherComponent == this)
//			return true;
//		
//		Component c =  (Component) otherComponent ;
//		
//		if(	originalTraceStatement.equals(c.getOriginalTraceStatement()) &&
//			header != c.getHeader())
//			return true;
//		return false;
//	}

	


	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object arg0) {
		
		return Integer.valueOf(((Component)arg0).getIndex()).compareTo(Integer.valueOf(this.index));
	}
	
	/*public String toString()
	{
		String tab = "";
		for(int i = 0 ; i < level ; i++)
			tab += "\t";
		return tab + originalTraceStatement + "\n" ;	
	}*/
	

}
