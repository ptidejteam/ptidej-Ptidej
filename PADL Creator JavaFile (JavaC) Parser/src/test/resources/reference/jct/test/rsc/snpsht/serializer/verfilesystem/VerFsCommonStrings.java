package jct.test.rsc.snpsht.serializer.verfilesystem;
import java.io.File;
public class VerFsCommonStrings
{
final public static java.lang.String FILES_REV_STRUCT_XML_FILE_NAME = "files_rev_struct.xml";

final public static java.lang.String FILES_INDEX_XML_FILE_NAME = "files_index.xml";

final public static java.lang.String BRANCHES_INDEX_XML_FILE_NAME = "branches_index.xml";

final public static java.lang.String ACTIONS_INDEX_XML_FILE_NAME = "actions_index.xml";

final public static java.lang.String AUTHORS_INDEX_XML_FILE_NAME = "authors_index.xml";

final public static java.lang.String TAGS_INDEX_XML_FILE_NAME = "tags_index.xml";

final public static java.lang.String COMMITS_INDEX_XML_FILE_NAME = "commits_index.xml";

final public static java.lang.String GENERAL_INFO_XML_FILE_NAME = "repo_general_info.xml";

final public static java.lang.String VALIDATION_DATA_XML_FILE_NAME = "validation_data.xml";

final public static java.lang.String XML_FILE_PATH_SEPARATOR = "/";

final public static java.lang.String META_INFO_XML_FILE_EXT = "meta-info.xml";

final public static java.lang.String META_INFO_XML_FILES_PATH = "files/";

final public static java.lang.String REVS_FILES_PATH = "files/data/";

final public static java.lang.String XML_HEADER = "<?xml version=\"1.0\"?>";

final public static java.lang.String DTD_MARKER = "";

final public static java.lang.String FILES_MAKER = "files";

final public static java.lang.String FILE_MAKER = "file";

final public static java.lang.String FILE_LOCATION_MARKER = "file_meta_info_path";

final public static java.lang.String FILE_ID_MAKER = "file_id";

final public static java.lang.String FILE_ID_ATTRIBUTE = "id";

final public static java.lang.String NULL_REV_MAKER = "null_rev";

final public static java.lang.String REVISIONS_MAKER = "revisions";

final public static java.lang.String FILE_REV_MAKER = "file_rev";

final public static java.lang.String FILE_REV_ID_MAKER = "rev_id";

final public static java.lang.String FILE_REV_ID_ATTRIBUTE = "id";

final public static java.lang.String FILE_REV_NAME_MAKER = "rev_name";

final public static java.lang.String FILE_REV_FILE_NAME_MAKER = "file_name";

final public static java.lang.String FILE_REV_ACTION_MAKER = "action";

final public static java.lang.String FILE_REV_COMMENT_MAKER = "comment";

final public static java.lang.String FILE_REV_AUTHOR_MAKER = "author";

final public static java.lang.String FILE_REV_BRANCH_MAKER = "branch";

final public static java.lang.String FILE_REV_REV_MAKER = "rev";

final public static java.lang.String FILE_REV_TAGS_MAKER = "tags";

final public static java.lang.String FILE_REV_TAG_MAKER = "tag";

final public static java.lang.String FILE_REV_UPDATE_TIME_MAKER = "update_time";

final public static java.lang.String FILE_REV_UPDATE_TIME_TYPE_ATTRIBUTE = "type";

final public static java.lang.String FILE_REV_TIME_ATT_VAL = "time";

final public static java.lang.String FILE_REV_PERIOD_ATT_VAL = "period";

final public static java.lang.String FILE_REV_TIME_MAKER = "time";

final public static java.lang.String FILE_REV_PERIOD_START_MAKER = "start_time";

final public static java.lang.String FILE_REV_PERIOD_END_MAKER = "end_time";

final public static java.lang.String FILE_REV_FILE_LOCATION_MAKER = "file_rev_path";

final public static java.lang.String FILE_REV_NEXT_REV_MAKER = "next_files_rev";

final public static java.lang.String FILE_REV_PREV_REV_MAKER = "previous_file_rev";

final public static java.lang.String FILE_REV_PATH_MAKER = "path";

final public static java.lang.String REPOSITORY_MAKER = "repository";

final public static java.lang.String REPO_ID_ATTRIBUTE = "id";

final public static java.lang.String REPO_NAME_MAKER = "name";

final public static java.lang.String REPO_PATH_MAKER = "path";

final public static java.lang.String REPO_ROOT_MAKER = "root_repository";

final public static java.lang.String REPO_CONTENT_LIST_MAKER = "content_list";

final public static java.lang.String BRANCHES_MAKER = "branches";

final public static java.lang.String BRANCH_MAKER = "branch";

final public static java.lang.String BRANCH_NAME_MAKER = "branch_name";

final public static java.lang.String BRANCH_NAME_ATTRIBUTE = "name";

final public static java.lang.String ACTIONS_MAKER = "actions";

final public static java.lang.String ACTION_MAKER = "action";

final public static java.lang.String ACTION_TYPE_ATTRIBUTE = "type";

final public static java.lang.String ACTION_TYPE_MAKER = "action_type";

final public static java.lang.String AUTHORS_MAKER = "authors";

final public static java.lang.String AUTHOR_MAKER = "author";

final public static java.lang.String AUTHOR_NAME_ATTRIBUTE = "name";

final public static java.lang.String AUTHOR_NAME_MAKER = "author_name";

final public static java.lang.String TAGS_MAKER = "authors";

final public static java.lang.String TAG_MAKER = "author";

final public static java.lang.String TAG_ID_ATTRIBUTE = "id";

final public static java.lang.String TAG_ID_MAKER = "tag_id";

final public static java.lang.String COMMITS_MAKER = "commits";

final public static java.lang.String COMMIT_MAKER = "commit";

final public static java.lang.String GENERAL_INFO_MAKER = "repo_info";

final public static java.lang.String GENERAL_TYPE_MAKER = "source_type";

final public static java.lang.String GENERAL_TYPE_ATTRIBUTE = "type";

final public static java.lang.String VALIDATION_DATA_MARKER = "validation_data";

final public static java.lang.String VALID_DATA_DIR_MARKER = "dir";

final public static java.lang.String VALID_DATA_FILE_MARKER = "file";

final public static java.lang.String VALID_DATA_PATH_MARKER = "path";

final public static java.lang.String VALID_DATA_MD5_MARKER = "md5";

final public static java.lang.String VALID_DATA_CONTENT_MARKER = "content_list";

final public static java.lang.String VALID_DATA_NAME_ATTRIBUTE = "name";

final public static java.lang.String[][] ASCII2XML = [] { [] { "&", "&amp;" }, [] { "<", "&lt;" }, [] { ">", "&gt;" }, [] { "\"", "&quot;" }, [] { "'", "&apos;" } };

public static java.lang.String ASCII2XML(java.lang.String ascii)
{
java.lang.String toRet = ascii;
for(java.lang.String[] specialChar : jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML) 
{
toRet = toRet.replaceAll(specialChar[0], specialChar[1]);

}
return toRet;

}

public static java.lang.String XML2ASCII(java.lang.String xml)
{
java.lang.String toRet = xml;
for(java.lang.String[] specialChar : jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML) 
{
toRet = toRet.replaceAll(specialChar[1], specialChar[0]);

}
return toRet;

}

public static java.lang.String PATH2XML(java.lang.String path)
{
if(java.io.File.separator.compareTo(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML_FILE_PATH_SEPARATOR) == 0) return jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(path);
 else return jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.ASCII2XML(path.replace(java.io.File.separator, jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML_FILE_PATH_SEPARATOR));

}

public static java.lang.String XML2PATH(java.lang.String xml)
{
if(java.io.File.separator.compareTo(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML_FILE_PATH_SEPARATOR) == 0) return jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(xml);
 else return jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML2ASCII(xml.replace(jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.XML_FILE_PATH_SEPARATOR, java.io.File.separator));

}

protected void <init>()
{
this.<init>();

}


}
