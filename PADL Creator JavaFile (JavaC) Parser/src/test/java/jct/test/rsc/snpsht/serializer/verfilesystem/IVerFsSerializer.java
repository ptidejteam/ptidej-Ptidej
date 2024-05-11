/*
 * (c) Copyright 2008 and following years, Julien Tanteri, University of
 * Montreal.
 * 
 * Use and copying of this software and preparation of derivative works based
 * upon this software are permitted. Any copy of this software or of any
 * derivative work must include the above copyright notice of the author, this
 * paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS ALL
 * WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE, AND NOT
 * WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN, ANY LIABILITY FOR DAMAGES
 * RESULTING FROM THE SOFTWARE OR ITS USE IS EXPRESSLY DISCLAIMED, WHETHER
 * ARISING IN CONTRACT, TORT (INCLUDING NEGLIGENCE) OR STRICT LIABILITY, EVEN IF
 * THE AUTHOR IS ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package jct.test.rsc.snpsht.serializer.verfilesystem;

import java.io.File;
import java.io.IOException;

import jct.test.rsc.snpsht.verfilesystem.IVerFsManager;

public interface IVerFsSerializer {
	/**
	 * Returns manager to serialize
	 * 
	 * @return
	 */
	public IVerFsManager getManager();

	/**
	 * Serialize to manager's file system.<br>
	 * A source file must be setted previously via
	 * {@linkplain jct.test.rsc.snpsht.verfilesystem.VerFsManager#setSourceFile(File)}
	 * 
	 * @throws IOException
	 *         If serialization fail.
	 */
	public void serialize() throws IOException;

	/**
	 * Serialize manager's info to the new manager file system denoted by the
	 * given file. This file can be a new file or a zip file witch already
	 * contains files.
	 * 
	 * @param dest
	 *        Manager's files system location
	 * @throws IOException
	 *         If serialization fail.
	 */
	public void serialize(File dest) throws IOException;
}
