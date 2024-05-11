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
package jct.test.rsc.snpsht.parser.revml;

public class RevMLDocCommonsStrings {
	/*
	 * Revml document commons Strings
	 */

	/*
	 * Revml commons markers
	 */

	/**
	 * RevML common marker:<br>
	 * Start/end RevML document
	 */
	public static final String REVML_DOCUMENT_MARKER = "revml";

	/**
	 * RevML common marker:<br>
	 * Start/end RevML document root repository.
	 */
	public static final String ROOT_REPOSITORY_MARKER = "rev_root";

	/**
	 * RevML common marker:<br>
	 * Start/end RevML document update time.
	 */
	public static final String ROOT_REPOSITORY_TIME_MARKER = "time";

	/**
	 * RevML common marker:<br>
	 * Start/end RevML document root repository description.
	 */
	public static final String ROOT_REPOSITORY_DESCR_MARKER = "rep_desc";

	public static final String FILE_REV_MARKER = "rev";
	public static final String FILE_REV_NAME_MARKER = "name";
	public static final String FILE_REV_COMMENT_MARKER = "comment";
	public static final String FILE_REV_ACTION_MARKER = "action";
	public static final String FILE_REV_AUTHOR_MARKER = "user_id";
	public static final String FILE_REV_BRANCH_MARKER = "branch_id";
	public static final String FILE_REV_FILE_NAME_MARKER = "source_name";
	public static final String FILE_REV_REV_ID_MARKER = "rev_id";
	public static final String FILE_REV_TAG_MARKER = "label";
	public static final String FILE_REV_PREV_VERSION_MARKER = "previous_id";
	public static final String FILE_REV_UPDATE_TIME_MARKER = "time";

	public static final String FILE_REV_DELTA_MARKER = "delta";
	public static final String FILE_REV_DELTA_TYPE_ATTRIBUTE = "type";
	public static final String FILE_REV_UDIFF_DELTA_ATTR_VALUE = "diff-u";
	
	public static final String FILE_REV_CONTENT_MARKER = "content";
	public static final String FILE_REV_ENCODING_ATTRIBUTE = "encoding";
	public static final String FILE_REV_BASE64_ENCODING_ATTR_VALUE = "base64";
	public static final String FILE_REV_NO_ENCODING_ATTR_VALUE = "none";

	public static final String CHAR_CODE_MARKER = "char";

	/*
	 * Revml commons attributes
	 */

	/**
	 * RevML common attribute:<br>
	 * RevML document version attribute. This attribute should be found in
	 * REVML_DOCUMENT_MARKER marker.
	 */
	public static final String REVML_DOCUMENT_VERSION_ATTRIBUTE = "version";

	/**
	 * RevML common attribute:<br>
	 * RevML document version attribute. This attribute should be found in
	 * REVML_DOCUMENT_MARKER marker.
	 */
	public static final String REVML_VERSION_ID_ATTRIBUTE = "id";

	public static final String REVML_CHAR_CODE_ATTRIBUTE = "code";

	public static final String REVML_BRANCH_ACTION = "branch";

}
