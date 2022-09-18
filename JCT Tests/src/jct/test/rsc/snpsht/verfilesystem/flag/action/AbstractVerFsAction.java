/* (c) Copyright 2008 and following years, Julien Tanteri,
 * University of Montreal.
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * the author, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN,
 * ANY LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF THE AUTHOR IS ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package jct.test.rsc.snpsht.verfilesystem.flag.action;

import jct.test.rsc.snpsht.verfilesystem.flag.AbstractVerFsFlag;


public abstract class AbstractVerFsAction extends AbstractVerFsFlag implements IVerFsAction {

	/**
	 * Move action as String
	 */
	public static final String MOVE_FILE_ACTION = "move";
	/**
	 * Delete action as String
	 */
	public static final String DELETE_FILE_ACTION = "delete";
	/**
	 * Branch action as String
	 */
	public static final String BRANCH_FILE_ACTION = "branch";
	/**
	 * Edit action as String
	 */
	public static final String EDIT_FILE_ACTION = "edit";
	/**
	 * Create action as String
	 */
	public static final String CREATION_FILE_ACTION = "create";
	/**
	 * Resurrect action as String. A resurrect file, is a file witch was
	 * deleted, and re-created.
	 */
	public static final String RESURRECT_FILE_ACTION = "resurrect";

}
