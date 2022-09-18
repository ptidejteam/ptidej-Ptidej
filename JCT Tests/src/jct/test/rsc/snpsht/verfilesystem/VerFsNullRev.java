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
package jct.test.rsc.snpsht.verfilesystem;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represent the first revision of a file.<br>
 * This revision can be consider as a null revision, but can contains some
 * informations. For example, if the first revision of a file was resurrected
 * the first revision should contains a link to the previous deleted file
 * revision.
 * 
 * @author Julien Tantéri
 */
public class VerFsNullRev extends VerFsFileRev implements IVerFsNullRevision {
	public static final String FIRST_REV_DEFAULT_ID_NAME = "null_revision";

	private VerFsFileRev firstRev;

	/**
	 * Default constructor
	 * 
	 * @param firstRev
	 *        Next revision of this null revision
	 */
	protected VerFsNullRev(VerFsFileRev firstRev) {
		this(firstRev, null);
	}

	/**
	 * Build a CvsFsNullRev with the given next file, and a previous file.
	 * Previous file should be set in case of a resurrect file.
	 * 
	 * @param firstRev
	 *        Next revision of this null revision
	 * @param prevRev
	 *        Previous revision if it's a resurrect file
	 */
	protected VerFsNullRev(VerFsFileRev firstRev, VerFsFileRev prevRev) {
		super(null, null, null);

		addNextRevision(firstRev);
		setPrevRevision(prevRev);
	}

	@Override
	public String getId() {
		return FIRST_REV_DEFAULT_ID_NAME + "_"
				+ this.firstRev.getFile().getId();
	}

	@Override
	public String getName() {
		return FIRST_REV_DEFAULT_ID_NAME;
	}

	/**
	 * Because a null revision can have only one version (first file revision),
	 * so this method set (but not add) the next file.<br>
	 * If a next revision is already set this revision will be replace
	 */
	@Override
	protected void addNextRevision(VerFsFileRev nextRev) {
		this.firstRev = nextRev;
	}

	/**
	 * Returns a Set with only 1 element (first file revision)
	 */
	@Override
	public Set<VerFsFileRev> getNextRevisions() {
		Set<VerFsFileRev> nextRevs = new HashSet<VerFsFileRev>();
		nextRevs.add(this.firstRev);
		return nextRevs;
	}

	/**
	 * Returns first file revision
	 * 
	 * @return first file revision
	 */
	public VerFsFileRev getNextRevision() {
		return this.firstRev;
	}

}
