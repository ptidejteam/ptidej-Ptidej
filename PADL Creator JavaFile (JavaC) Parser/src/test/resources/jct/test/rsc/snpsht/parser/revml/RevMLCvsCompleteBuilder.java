package jct.test.rsc.snpsht.parser.revml;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.xml.parsers.ParserConfigurationException;
import jct.test.rsc.snpsht.filesystem.IFsFileEntity;
import jct.test.rsc.snpsht.parser.revml.modeler.BranchModeler;
import jct.test.rsc.snpsht.parser.revml.modeler.FileGrouper;
import jct.test.rsc.snpsht.parser.revml.modeler.FilePostProcessing;
import jct.test.rsc.snpsht.parser.revml.modeler.MovedFilesModeler;
import jct.test.rsc.snpsht.parser.revml.sax.RevMLContentSaxParser;
import jct.test.rsc.snpsht.parser.revml.sax.RevMLStructureSaxParser;
import jct.test.rsc.snpsht.parser.sax.SaxFsmParseException;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings;
import jct.test.rsc.snpsht.utils.cvsutils.CVSCheckOut;
import jct.test.rsc.snpsht.utils.cvsutils.CVSRevMLCheckOut;
import jct.test.rsc.snpsht.utils.cvsutils.CVSRoot;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.CommitGrouper;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.SameAuthorConstraint;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.SameCommentConstraint;
import jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.SlidingWindowConstraint;
import jct.test.rsc.snpsht.verfilesystem.flag.VerFsFile;
import jct.test.rsc.snpsht.verfilesystem.flag.action.AbstractVerFsAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsBranchAction;
import jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsDeleteAction;
import org.xml.sax.SAXException;
public class RevMLCvsCompleteBuilder
{
private jct.test.rsc.snpsht.verfilesystem.VerFsManager manager;

private jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.CommitGrouper grouper;

private jct.test.rsc.snpsht.parser.revml.sax.RevMLStructureSaxParser structParser;

private jct.test.rsc.snpsht.parser.revml.sax.RevMLContentSaxParser contentParser;

private jct.test.rsc.snpsht.utils.cvsutils.CVSRoot cvsRoot;

private jct.test.rsc.snpsht.utils.cvsutils.CVSCheckOut cvsCo;

private jct.test.rsc.snpsht.utils.cvsutils.CVSRevMLCheckOut revMLCo;

public void <init>(jct.test.rsc.snpsht.utils.cvsutils.CVSRoot cvsRoot)
{
this.<init>();
this.grouper = new jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.CommitGrouper();
this.grouper.addConstraint(new jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.SameCommentConstraint());
this.grouper.addConstraint(new jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.SameAuthorConstraint());
this.grouper.addConstraint(new jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.groupconstraint.SlidingWindowConstraint());
this.init(cvsRoot);

}

public void <init>(jct.test.rsc.snpsht.utils.cvsutils.CVSRoot cvsRoot, jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.CommitGrouper grouper)
{
this.<init>();
this.grouper = grouper;
this.init(cvsRoot);

}

private void init(jct.test.rsc.snpsht.utils.cvsutils.CVSRoot cvsRoot)
{
this.cvsRoot = cvsRoot;
this.structParser = new jct.test.rsc.snpsht.parser.revml.sax.RevMLStructureSaxParser();
this.contentParser = new jct.test.rsc.snpsht.parser.revml.sax.RevMLContentSaxParser();
this.revMLCo = new jct.test.rsc.snpsht.utils.cvsutils.CVSRevMLCheckOut(this.cvsRoot);
this.cvsCo = new jct.test.rsc.snpsht.utils.cvsutils.CVSCheckOut(this.cvsRoot);

}

public jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.CommitGrouper getCommitGrouper()
{
return this.grouper;

}

public void setCommitGrouper(jct.test.rsc.snpsht.verfilesystem.cvsimpl.modeler.CommitGrouper grouper)
{
this.grouper = grouper;

}

public jct.test.rsc.snpsht.verfilesystem.VerFsManager getManager()
{
return this.manager;

}

private jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager parse(java.io.File xmlFile, java.io.File saveFile) throws java.io.IOException, javax.xml.parsers.ParserConfigurationException, jct.test.rsc.snpsht.parser.sax.SaxFsmParseException, org.xml.sax.SAXException
{
this.manager = this.structParser.parseStructure(xmlFile, this.manager);
this.manager.setSource(saveFile);
this.manager = this.contentParser.parseContent(xmlFile, this.manager);
return (jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager)this.manager;

}

private void modlModel()
{
jct.test.rsc.snpsht.parser.revml.modeler.BranchModeler bm;
jct.test.rsc.snpsht.parser.revml.modeler.FilePostProcessing frdl;
jct.test.rsc.snpsht.parser.revml.modeler.MovedFilesModeler mfm;
jct.test.rsc.snpsht.parser.revml.modeler.FileGrouper fg;
frdl = new jct.test.rsc.snpsht.parser.revml.modeler.FilePostProcessing();
this.manager = frdl.modl(this.manager);
bm = new jct.test.rsc.snpsht.parser.revml.modeler.BranchModeler();
this.manager = bm.modl(this.manager);
this.manager = this.grouper.modl(this.manager);
mfm = new jct.test.rsc.snpsht.parser.revml.modeler.MovedFilesModeler();
this.manager = mfm.modl(this.manager);
fg = new jct.test.rsc.snpsht.parser.revml.modeler.FileGrouper();
this.manager = fg.modl(this.manager);

}

public jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager build(java.lang.String module, java.io.File saveFile) throws java.io.IOException, java.net.URISyntaxException, javax.xml.parsers.ParserConfigurationException, jct.test.rsc.snpsht.parser.sax.SaxFsmParseException, org.xml.sax.SAXException
{
if(saveFile.isDirectory()) 
{
throw new java.lang.IllegalArgumentException("Save file must be a file, but is a directory.");

}
java.io.File revMLFile = this.checkOutRevML(module);
revMLFile.deleteOnExit();
this.parse(revMLFile, saveFile);
this.modlModel();
return (jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager)this.manager;

}

private java.io.File checkOutRevML(java.lang.String module) throws java.io.IOException, java.net.URISyntaxException
{
java.io.File revMLFile = java.io.File.createTempFile("CVSRevMLTempFile", ".revml");
if(! this.revMLCo.checkOut(module, revMLFile)) 
{
throw new java.lang.Error("Error during RevML checkout. Can't checkout '" + module + "' on '" + this.cvsRoot.toString() + "'");

}
this.manager = new jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager(this.cvsRoot);
return revMLFile;

}

private void downloadFilesRev(java.io.File saveFile) throws java.io.IOException
{
java.lang.String fileRevPath;
java.lang.String targetFilePath;
java.io.File targetFile;
this.manager.setSource(saveFile);
for(jct.test.rsc.snpsht.verfilesystem.flag.VerFsFile file : this.manager.getAllFiles()) 
{
for(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev fileRev : file.getChildren()) 
{
if(! (fileRev.getAction() instanceof jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsBranchAction) && ! (fileRev.getAction() instanceof jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsDeleteAction)) 
{
targetFilePath = jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings.REVS_FILES_PATH + this.manager.toRelativePath(fileRev.getPath()).replace(((jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager)this.manager).getCvsRoot().getSeparator(), java.io.File.separator) + "." + fileRev.getRevID().getValue().replace('.', '_') + ".rev";
targetFile = java.io.File.createTempFile("tempRev", null);
fileRevPath = this.manager.getRoot().getName() + ((jct.test.rsc.snpsht.verfilesystem.cvsimpl.CvsFsManager)this.manager).getCvsRoot().getSeparator() + this.manager.toRelativePath(fileRev.getPath());
this.cvsCo.checkOut(fileRevPath, fileRev.getRevID().getValue(), targetFile);
jct.test.rsc.snpsht.filesystem.IFsFileEntity entity = this.manager.getSourceManager().add(targetFilePath, targetFile, true);
this.manager.setFileRevLocation(entity, fileRev);

}

}

}
this.linkFileRevWithAction2FileLocation(class);
this.setNullEntityOnDeleteRev();

}

private void setNullEntityOnDeleteRev()
{
for(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev fileRev : this.manager.getAction(class).getChildren()) 
{
this.manager.setFileRevLocation(this.manager.getSourceManager().getANullEntity(), fileRev);

}

}

private void linkFileRevWithAction2FileLocation(java.lang.Class c)
{
for(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev fileRev : this.manager.getAction(c).getChildren()) 
{
this.manager.setFileRevLocation(this.getFileRevNotModifContentFileLocation(fileRev), fileRev);

}

}

private jct.test.rsc.snpsht.filesystem.IFsFileEntity getFileRevNotModifContentFileLocation(jct.test.rsc.snpsht.verfilesystem.VerFsFileRev fileRev)
{
if(fileRev.getAction() instanceof jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsBranchAction || fileRev.getAction() instanceof jct.test.rsc.snpsht.verfilesystem.flag.action.VerFsDeleteAction) 
{
return this.getFileRevNotModifContentFileLocation(fileRev.getPrevRevision());

}
 else return fileRev.getFileRevLocation();

}


}
