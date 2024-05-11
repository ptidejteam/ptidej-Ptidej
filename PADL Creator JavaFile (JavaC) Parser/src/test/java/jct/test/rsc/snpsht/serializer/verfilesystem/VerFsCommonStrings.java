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

public class VerFsCommonStrings {
	public static final String FILES_REV_STRUCT_XML_FILE_NAME =
		"files_rev_struct.xml";
	public static final String FILES_INDEX_XML_FILE_NAME = "files_index.xml";
	public static final String BRANCHES_INDEX_XML_FILE_NAME =
		"branches_index.xml";
	public static final String ACTIONS_INDEX_XML_FILE_NAME =
		"actions_index.xml";
	public static final String AUTHORS_INDEX_XML_FILE_NAME =
		"authors_index.xml";
	public static final String TAGS_INDEX_XML_FILE_NAME = "tags_index.xml";
	public static final String COMMITS_INDEX_XML_FILE_NAME =
		"commits_index.xml";
	public static final String GENERAL_INFO_XML_FILE_NAME =
		"repo_general_info.xml";
	public static final String VALIDATION_DATA_XML_FILE_NAME =
		"validation_data.xml";

	public static final String XML_FILE_PATH_SEPARATOR = "/";

	public static final String META_INFO_XML_FILE_EXT = "meta-info.xml";
	public static final String META_INFO_XML_FILES_PATH = "files/";
	public static final String REVS_FILES_PATH = "files/data/";

	public static final String XML_HEADER = "<?xml version=\"1.0\"?>";
	public static final String DTD_MARKER = ""; // "<!DOCTYPE revml SYSTEM \"file:/home/dju/Bureau/Telechargement/output.dtd\">"

	public static final String FILES_MAKER = "files";
	public static final String FILE_MAKER = "file";
	public static final String FILE_LOCATION_MARKER = "file_meta_info_path";
	public static final String FILE_ID_MAKER = "file_id";
	public static final String FILE_ID_ATTRIBUTE = "id";

	public static final String NULL_REV_MAKER = "null_rev";

	public static final String REVISIONS_MAKER = "revisions";
	public static final String FILE_REV_MAKER = "file_rev";
	public static final String FILE_REV_ID_MAKER = "rev_id";
	public static final String FILE_REV_ID_ATTRIBUTE = "id";
	public static final String FILE_REV_NAME_MAKER = "rev_name";
	public static final String FILE_REV_FILE_NAME_MAKER = "file_name";
	public static final String FILE_REV_ACTION_MAKER = "action";
	public static final String FILE_REV_COMMENT_MAKER = "comment";
	public static final String FILE_REV_AUTHOR_MAKER = "author";
	public static final String FILE_REV_BRANCH_MAKER = "branch";
	public static final String FILE_REV_REV_MAKER = "rev";
	public static final String FILE_REV_TAGS_MAKER = "tags";
	public static final String FILE_REV_TAG_MAKER = "tag";
	public static final String FILE_REV_UPDATE_TIME_MAKER = "update_time";
	public static final String FILE_REV_UPDATE_TIME_TYPE_ATTRIBUTE = "type";
	public static final String FILE_REV_TIME_ATT_VAL = "time";
	public static final String FILE_REV_PERIOD_ATT_VAL = "period";
	public static final String FILE_REV_TIME_MAKER = "time";
	public static final String FILE_REV_PERIOD_START_MAKER = "start_time";
	public static final String FILE_REV_PERIOD_END_MAKER = "end_time";
	public static final String FILE_REV_FILE_LOCATION_MAKER = "file_rev_path";
	public static final String FILE_REV_NEXT_REV_MAKER = "next_files_rev";
	public static final String FILE_REV_PREV_REV_MAKER = "previous_file_rev";
	public static final String FILE_REV_PATH_MAKER = "path";

	public static final String REPOSITORY_MAKER = "repository";
	public static final String REPO_ID_ATTRIBUTE = "id";
	public static final String REPO_NAME_MAKER = "name";
	public static final String REPO_PATH_MAKER = "path";
	public static final String REPO_ROOT_MAKER = "root_repository";
	public static final String REPO_CONTENT_LIST_MAKER = "content_list";

	public static final String BRANCHES_MAKER = "branches";
	public static final String BRANCH_MAKER = "branch";
	public static final String BRANCH_NAME_MAKER = "branch_name";
	public static final String BRANCH_NAME_ATTRIBUTE = "name";

	public static final String ACTIONS_MAKER = "actions";
	public static final String ACTION_MAKER = "action";
	public static final String ACTION_TYPE_ATTRIBUTE = "type";
	public static final String ACTION_TYPE_MAKER = "action_type";

	public static final String AUTHORS_MAKER = "authors";
	public static final String AUTHOR_MAKER = "author";
	public static final String AUTHOR_NAME_ATTRIBUTE = "name";
	public static final String AUTHOR_NAME_MAKER = "author_name";

	public static final String TAGS_MAKER = "authors";
	public static final String TAG_MAKER = "author";
	public static final String TAG_ID_ATTRIBUTE = "id";
	public static final String TAG_ID_MAKER = "tag_id";

	public static final String COMMITS_MAKER = "commits";
	public static final String COMMIT_MAKER = "commit";

	public static final String GENERAL_INFO_MAKER = "repo_info";
	public static final String GENERAL_TYPE_MAKER = "source_type";
	public static final String GENERAL_TYPE_ATTRIBUTE = "type";

	public static final String VALIDATION_DATA_MARKER = "validation_data";
	public static final String VALID_DATA_DIR_MARKER = "dir";
	public static final String VALID_DATA_FILE_MARKER = "file";
	public static final String VALID_DATA_PATH_MARKER = "path";
	public static final String VALID_DATA_MD5_MARKER = "md5";
	public static final String VALID_DATA_CONTENT_MARKER = "content_list";
	public static final String VALID_DATA_NAME_ATTRIBUTE = "name";

	public static final String[][] ASCII2XML =
		{ { "&", "&amp;" }, { "<", "&lt;" }, { ">", "&gt;" },
				{ "\"", "&quot;" }, { "'", "&apos;" }, };

	public static String ASCII2XML(String ascii) {
		String toRet = ascii;

		for (String[] specialChar : ASCII2XML) {
			toRet = toRet.replaceAll(specialChar[0], specialChar[1]);
		}

		return toRet;
	}

	public static String XML2ASCII(String xml) {
		String toRet = xml;

		for (String[] specialChar : ASCII2XML) {
			toRet = toRet.replaceAll(specialChar[1], specialChar[0]);
		}

		return toRet;
	}

	public static String PATH2XML(String path) {
		if (File.separator.compareTo(XML_FILE_PATH_SEPARATOR) == 0)
			return ASCII2XML(path);
		else
			return ASCII2XML(path.replace(
				File.separator,
				XML_FILE_PATH_SEPARATOR));
	}

	public static String XML2PATH(String xml) {
		if (File.separator.compareTo(XML_FILE_PATH_SEPARATOR) == 0)
			return XML2ASCII(xml);
		else
			return XML2ASCII(xml.replace(
				XML_FILE_PATH_SEPARATOR,
				File.separator));
	}

	protected VerFsCommonStrings() {
	}
}
