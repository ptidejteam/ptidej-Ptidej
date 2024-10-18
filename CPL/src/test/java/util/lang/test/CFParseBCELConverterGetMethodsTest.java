package util.lang.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.bcel.classfile.ClassParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ibm.toad.cfparse.ClassFile;

import util.lang.CFParseBCELConvertor;

/**
 * @since 2024/10/11
 * @author Rushin Dipak Makwana
 * @author Nicolas C. Rousse
 */
class CFParseBCELConvertorGetMethodsTest {
    private ClassFile classFile_CFParse_Original;
    private ClassFile classFile_CFParse_Converted;

    @BeforeEach
    void setUp() throws IOException {
        final String classFile_Path = "../CPL/target/test-classes/Random ClassFiles/NameDialog.class";

        this.classFile_CFParse_Original = new ClassFile(
                new FileInputStream(classFile_Path));

        this.classFile_CFParse_Converted = CFParseBCELConvertor
                .convertClassFile(
                        new ClassParser(new FileInputStream(classFile_Path), "")
                                .parse());
    }

    @Test
    void testGetAccess() {
        assertEquals(this.classFile_CFParse_Original.getAccess(),
                this.classFile_CFParse_Converted.getAccess());
    }

    @Test
    void testGetAttrs() {
        // assertEquals(this.classFile_CFParse_Original.getAttrs(), this.classFile_CFParse_Converted.getAttrs());
    }

    @Test
    void testGetName() {
        assertEquals(this.classFile_CFParse_Original.getName(),
                this.classFile_CFParse_Converted.getName());
    }

    @Test
    void testGetFields() {
        assertEquals(
                this.classFile_CFParse_Original.getFields().get(0).toString(),
                this.classFile_CFParse_Converted.getFields().get(0).toString());
    }
    @Test
    void testGetDesc() {
        int fieldCount = this.classFile_CFParse_Original.getFields().length();
        for (int i = 0; i < fieldCount; i++) {
            assertEquals(
                    this.classFile_CFParse_Original.getFields().get(i).getDesc(),
                    this.classFile_CFParse_Converted.getFields().get(i).getDesc());
        }
    }
    @Test
    void testGetMethods() {
        int fieldCount = this.classFile_CFParse_Original.getMethods().length();
        for (int i = 0; i < fieldCount; i++) {
            assertEquals(
                    this.classFile_CFParse_Original.getMethods().get(i).toString(),
                    this.classFile_CFParse_Converted.getMethods().get(i).toString());;
        }
    }
}