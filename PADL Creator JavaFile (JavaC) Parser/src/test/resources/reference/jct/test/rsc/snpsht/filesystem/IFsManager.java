package jct.test.rsc.snpsht.filesystem;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
public interface IFsManager
{
final public static java.lang.String NULL_ENTITY_ID = "<NULL_ENTITY>";

public void load(java.io.File source) throws java.io.IOException
{

}

public void update() throws java.io.IOException
{

}

public java.io.File getSource()
{

}

public jct.test.rsc.snpsht.filesystem.IFsRealEntity[] getAllFilesEntity()
{

}

public jct.test.rsc.snpsht.filesystem.IFsFileEntity getFileEntity(java.lang.String id)
{

}

public jct.test.rsc.snpsht.filesystem.IFsNullEntity getANullEntity()
{

}

public jct.test.rsc.snpsht.filesystem.IFsRealEntity add(java.lang.String id) throws java.io.IOException
{

}

public jct.test.rsc.snpsht.filesystem.IFsRealEntity add(java.lang.String id, java.io.File entity) throws java.io.IOException
{

}

public jct.test.rsc.snpsht.filesystem.IFsRealEntity add(java.lang.String id, java.io.File entity, boolean isTemp) throws java.io.IOException
{

}

public jct.test.rsc.snpsht.filesystem.IFsRealEntity delete(java.lang.String id)
{

}

public jct.test.rsc.snpsht.filesystem.IFsRealEntity delete(jct.test.rsc.snpsht.filesystem.IFsFileEntity file)
{

}

public java.io.InputStream getInputStrem(jct.test.rsc.snpsht.filesystem.IFsRealEntity file) throws java.io.IOException
{

}

public java.io.OutputStream getOutputStrem(jct.test.rsc.snpsht.filesystem.IFsRealEntity file) throws java.io.IOException
{

}

public java.io.FileWriter getFileWriter(jct.test.rsc.snpsht.filesystem.IFsRealEntity file) throws java.io.IOException
{

}


}
