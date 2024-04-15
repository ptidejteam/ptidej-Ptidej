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
package jct.test.rsc.snpsht.filesystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IFsManager {
	public final static String NULL_ENTITY_ID = "<NULL_ENTITY>";
	
	/**
	 * Load source file in manager.
	 * 
	 * @param source
	 *        Source file location
	 * @throws IOException
	 *         If file type can be handle by this manager
	 */
	public void load(File source) throws IOException;

	/**
	 * Finalize files modifications by updating source file.<br>
	 * After an update this manager still work with the same source, but all
	 * modifications are applied.
	 * 
	 * @throws IOException
	 */
	public void update() throws IOException;
	
	public File getSource();

	/**
	 * Returns all currents entities.
	 * 
	 * @return All currents entities.
	 */
	public IFsRealEntity[] getAllFilesEntity();

	/**
	 * Returns entity associated to the given id, or null if this id don't exist
	 * in manager.<br>
	 * If id=NULL_ENTITY_ID, a new null entity will be return
	 * 
	 * @param id
	 *        Searched entity id.
	 * @return Entity associated to the given id.
	 */
	public IFsFileEntity getFileEntity(String id);
	
	/**
	 * Always return a new null entity
	 * @return A null entity
	 */
	public IFsNullEntity getANullEntity();

	/**
	 * Add a new entity to manager with the given id.<br>
	 * New entity will be returns, or null if id already exist in manager
	 * (failed case).
	 * 
	 * @param id
	 *        New entity id
	 * @return New entity
	 * @throws IOException
	 *         If can't create new entity (entity may already exist)
	 */
	public IFsRealEntity add(String id) throws IOException;

	public IFsRealEntity add(String id, File entity) throws IOException;
	
	public IFsRealEntity add(String id, File entity, boolean isTemp) throws IOException;

	/**
	 * Delete an entity from manager (and real file system).<br>
	 * Deleted entity will be return, or null if the given id don't exist in
	 * manager.
	 * 
	 * @param id
	 *        Entity id to delete
	 * @return Deleted entity
	 */
	public IFsRealEntity delete(String id);

	/**
	 * Delete an entity from manager (and real file system).<br>
	 * Deleted entity will be return.
	 * 
	 * @param file
	 *        Entity to delete
	 * @return Deleted entity
	 */
	public IFsRealEntity delete(IFsFileEntity file);
	public InputStream getInputStrem(IFsRealEntity file) throws IOException;
	public OutputStream getOutputStrem(IFsRealEntity file) throws IOException;
	public FileWriter getFileWriter(IFsRealEntity file) throws IOException;
}
