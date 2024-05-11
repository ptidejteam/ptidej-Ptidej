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
package jct.test.rsc.snpsht.parser.revml.modeler;

import java.util.Collection;

import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.IModeler;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsCreateAction;

/**
 * This class makes some post-processing on files revisions.<br>
 * It create a link to next version of each files.<br>
 * It set action of fist file revision to 'create'.
 * 
 * @author Julien Tanteri
 * 
 */
public class FilePostProcessing implements IModeler {
	
	@Override
	public VerFsManager modl(VerFsManager manager) {
		makeDoubleLink(manager);
		setCreateAction(manager);

		return manager;
	}

	private void makeDoubleLink(VerFsManager manager) {
		VerFsFileRev prevRev;

		Collection<VerFsFileRev> allFilesRev = manager.getAllSimplesRevisions();

		for (VerFsFileRev fileRev : allFilesRev) {
			prevRev = fileRev.getPrevRevision();
			if (prevRev != null)
				manager.addNextRevision(fileRev, prevRev);
		}
	}
	
	private void setCreateAction(VerFsManager manager) {		
		for(VerFsFileRev fileRev : manager.getAllSimplesRevisions()) {
			if(fileRev.getPrevRevision() == null) {
				manager.setAction(VerFsCreateAction.class, fileRev);
			}
		}
	}
}
