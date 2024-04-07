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


public class Argument {

	/**
	 * @uml.property  name="value"
	 */
	private String value = "";

	/**
	 * Getter of the property <tt>value</tt>
	 * @return  Returns the value.
	 * @uml.property  name="value"
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * Setter of the property <tt>value</tt>
	 * @param value  The value to set.
	 * @uml.property  name="value"
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @uml.property  name="type"
	 */
	private String type = "";

	/**
	 * Getter of the property <tt>type</tt>
	 * @return  Returns the type.
	 * @uml.property  name="type"
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Setter of the property <tt>type</tt>
	 * @param type  The type to set.
	 * @uml.property  name="type"
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @uml.property  name="messageOfArgument"
	 * @uml.associationEnd  multiplicity="(1 1)" inverse="arguments:metamodel.scenarioDiagram.Message"
	 */
	private Message messageOfArgument ;

	/**
	 * Getter of the property <tt>messageOfArgument</tt>
	 * @return  Returns the messageOfArgument.
	 * @uml.property  name="messageOfArgument"
	 */
	public Message getMessageOfArgument() {
		return this.messageOfArgument;
	}

	/**
	 * Setter of the property <tt>messageOfArgument</tt>
	 * @param messageOfArgument  The messageOfArgument to set.
	 * @uml.property  name="messageOfArgument"
	 */
	public void setMessageOfArgument(Message messageOfArgument) {
		this.messageOfArgument = messageOfArgument;
	}


	// ========================================================================

	public Argument(Message messageOfArgument)
	{
		
		this.messageOfArgument = messageOfArgument;
	}
	
	public Argument(String type)
	{
		this.type = type;
	}
	
	public Argument(String type, String value)
	{
		this.type = type;
		this.value = value;
	}
	
	public String toString(){
		return this.type + " " + this.value;	
	}
}
