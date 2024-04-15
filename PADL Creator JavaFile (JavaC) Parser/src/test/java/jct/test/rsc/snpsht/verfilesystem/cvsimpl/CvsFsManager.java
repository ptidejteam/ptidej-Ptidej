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
package jct.test.rsc.snpsht.verfilesystem.cvsimpl;

import jct.test.rsc.snpsht.utils.cvsutils.CVSRoot;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.SameAuthorConstraint;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.SameCommentConstraint;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.SlidingWindowConstraint;

/**
 * This class aims to manage commits of a CVS repository.<br>
 * This class is instantiate by
 * 
 * @author Julien Tantéri
 * 
 */
public class CvsFsManager extends VerFsManager {
	private ICvsFsCommitConstraint[] constraints;
	private CVSRoot cvsRoot;
	/**
	 * Brancher user's name
	 */
	public static final String BRANCHER_USER_NAME = "Brancher";

	public CvsFsManager() {
		this(null);
	}

	public CvsFsManager(CVSRoot cvsRoot) {
		super();

		setCvsRoot(cvsRoot);
		this.constraints = new ICvsFsCommitConstraint[0];
	}

	public void setConstraints(ICvsFsCommitConstraint[] constraints) {
		this.constraints = constraints;
	}

	public void addConstraint(String type) {
		addConstraint(type, new String[][] {});
	}

	public void addConstraint(String type, String[][] attributes) {
		ICvsFsCommitConstraint constr;
		String size = null;

		if (type.compareTo("sameComment") == 0) {
			constr = new SameCommentConstraint();
		} else if (type.compareTo("sameAuthor") == 0) {
			constr = new SameAuthorConstraint();
		} else if (type.compareTo("slidingWindows") == 0) {
			for (String[] attr : attributes) {
				if (attr[0].compareTo("size") == 0) {
					size = attr[1];
					break;
				}
			}

			if (size == null) {
				constr = new SlidingWindowConstraint();
			} else {
				constr = new SlidingWindowConstraint(Double.valueOf(size));
			}
		} else {
			throw new IllegalArgumentException(
				"Can't instanciate ICvsFsCommitConstraint. Unknow type " + type);
		}

		addConstraint(constr);
	}

	public void addConstraint(ICvsFsCommitConstraint constraint) {
		ICvsFsCommitConstraint[] newConstr =
			new ICvsFsCommitConstraint[this.constraints.length + 1];
		System.arraycopy(
			this.constraints,
			0,
			newConstr,
			0,
			this.constraints.length);
		
		newConstr[this.constraints.length] = constraint;
		
		this.constraints = newConstr;
	}

	public ICvsFsCommitConstraint[] getConstraints() {
		return this.constraints;
	}

	public void setCvsRoot(CVSRoot cvsRoot) {
		this.cvsRoot = cvsRoot;
	}

	public CVSRoot getCvsRoot() {
		return this.cvsRoot;
	}
}
