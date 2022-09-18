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
package jct.test.rsc.snpsht.serializer.verfilesystem.cvsimpl;

import java.io.IOException;

import jct.test.rsc.snpsht.serializer.verfilesystem.AbstractVerFsSerializer;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsActionsIndexSerializer;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsAuthorsIndexSerializer;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsBranchesIndexSerializer;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommitsIndexSerializer;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsFileSerializer;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsFilesIndexSerializer;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsStructureSerializer;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsTagsIndexSerializer;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsValidationDataSerializer;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager;

public class CvsFsCompleteSerializer extends AbstractVerFsSerializer {
	private CvsFsManager manager;

	/**
	 * Default constructor.
	 * 
	 * @param manager
	 * @param cvsRoot
	 */
	public CvsFsCompleteSerializer(CvsFsManager manager) {
		this.manager = manager;
	}

	/**
	 * Returns current manager
	 * 
	 * @return current manager
	 */
	@Override
	public CvsFsManager getManager() {
		return this.manager;
	}

	/**
	 * Allows to set another manager to serialize
	 * 
	 * @param manager
	 *        new manager
	 */
	public void setManager(CvsFsManager manager) {
		this.manager = manager;
	}

	@Override
	public void serialize() throws IOException {
		AbstractVerFsSerializer serializer;

		serializer = new VerFsFileSerializer(this.manager);
		serializer.serialize();
		
		serializer = new VerFsActionsIndexSerializer(this.manager); 
		serializer.serialize();
		
		serializer = new VerFsAuthorsIndexSerializer(this.manager); 
		serializer.serialize();
		
		serializer = new VerFsBranchesIndexSerializer(this.manager);
		serializer.serialize();
		
		serializer = new VerFsCommitsIndexSerializer(this.manager); 
		serializer.serialize();
		
		serializer = new VerFsFilesIndexSerializer(this.manager);
		serializer.serialize();
		
		serializer = new VerFsStructureSerializer(this.manager); 
		serializer.serialize();
		
		serializer = new VerFsTagsIndexSerializer(this.manager);
		serializer.serialize();
		
		serializer = new CvsFsGeneralInfoSerializer(this.manager);
		serializer.serialize();
		
		
		// This serializer should the last to be call because it list dir contents
		serializer = new VerFsValidationDataSerializer(this.manager);
		serializer.serialize();
		
		this.manager.getSourceManager().update();
	}

}
