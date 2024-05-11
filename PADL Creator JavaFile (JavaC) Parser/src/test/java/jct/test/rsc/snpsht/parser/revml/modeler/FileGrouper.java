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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.IModeler;
import jct.test.rsc.snpsht.verfilesystem.flag.action.AbstractVerFsAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsCreateAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsResurrectAction;

/**
 * This class aims to group files revisions witch represent the same file
 * evolution.
 * 
 * @author Julien Tantéri
 * 
 */
public class FileGrouper implements IModeler {
	private AbstractVerFsAction create, resurect;

	@Override
	public VerFsManager modl(VerFsManager manager) {
		groupFilesRevInFile(manager);

		return manager;
	}

	private void groupFilesRevInFile(VerFsManager manager) {
		Set<VerFsFileRev> file;
		List<VerFsFileRev> createAndResurectFileRev =
			new ArrayList<VerFsFileRev>();

		this.create = manager.getAction(VerFsCreateAction.class);
		createAndResurectFileRev.addAll(this.create.getChildren());

		this.resurect = manager.getAction(VerFsResurrectAction.class);
		createAndResurectFileRev.addAll(this.resurect.getChildren());

		for (VerFsFileRev fileRev : createAndResurectFileRev) {
				file = new HashSet<VerFsFileRev>();
				groupAFile(fileRev, file);
				manager.setAsFile(file.toArray(new VerFsFileRev[0]));
		}
	}

	private void groupAFile(VerFsFileRev fileRev, Set<VerFsFileRev> file) {
		file.add(fileRev);

		for (VerFsFileRev nextRev : fileRev.getNextRevisions()) {
			if (nextRev.getAction() != this.resurect)
				groupAFile(nextRev, file);
		}
	}
}
