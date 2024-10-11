package util.lang.test;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.FieldInfo;
import com.ibm.toad.cfparse.FieldInfoList;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FieldInfoListConversionTest {

    @Test
    void test1() throws FileNotFoundException, IOException {
        final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/NameDialog.class";

        final ClassFile classFile_CFParse_Original = new ClassFile(
                new FileInputStream(classFile_Path));
        final FieldInfoList fieldInfoList = classFile_CFParse_Original
                .getFields();
        final FieldInfoList fieldInfoList_CFParse = classFile_CFParse_Original.getFields();


        final JavaClass classFile_BCEL = new ClassParser(
                new FileInputStream(classFile_Path), "").parse();

        final Field[] fields_BCEL = classFile_BCEL.getFields();
        
        
        Assertions.assertEquals(fieldInfoList_CFParse.length(), fields_BCEL.length, "Field lists are of different sizes");

        for (int i = 0; i < fieldInfoList_CFParse.length(); i++) {
            final FieldInfo field_CFParse = fieldInfoList_CFParse.get(i);
            final Field field_BCEL = fields_BCEL[i];

            Assertions.assertEquals(fieldInfoList_CFParse.getFieldName(i), fields_BCEL[i].getName(), "Field names do not match");
            Assertions.assertEquals(fieldInfoList_CFParse.get(i).getType(), fields_BCEL[i].getType().getClassName(), "Field values types do not match");
            Assertions.assertEquals(field_CFParse.getAccess(), field_BCEL.getAccessFlags(), "Access flags do not match for field " + i);
            Assertions.assertEquals(field_CFParse.getType(), field_BCEL.getType().getClassName(), "Types do not match for field " + i);
        }

//        Assertions.assertEquals(field_CFPARSE.getAccess(),
//                field_BCEL.getAccessFlags());
//
//        Assertions.assertEquals(field_CFPARSE.getType(),
//                field_BCEL.getType().getClassName());
    }
}
