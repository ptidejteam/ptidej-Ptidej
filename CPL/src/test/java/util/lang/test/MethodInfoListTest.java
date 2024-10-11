package util.lang.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.MethodInfo;
import com.ibm.toad.cfparse.MethodInfoList;

public class MethodInfoListTest {
	@Test
	void test1() throws FileNotFoundException, IOException {
		final String classFile_Path = "/Users/bramss/git/Ptidej/CPL/src/test/resources/Random ClassFiles/NameDialog.class";
		
		final ClassFile classFile_CFParse_Original = new ClassFile(
				new FileInputStream(classFile_Path));
		
		final MethodInfoList methodInfoList = classFile_CFParse_Original.getMethods();
		
		
		
		final MethodInfo[] methodInfoList_CFPARSE = new MethodInfo[methodInfoList.length()];
		
		for (int i= 0; i < methodInfoList.length();i++ ) {
			
			methodInfoList_CFPARSE[i] = methodInfoList.get(i);
		}
		
		final JavaClass classFile_BCEL = new ClassParser(
				new FileInputStream(classFile_Path), "").parse();

		final Method[] methodInfoList_BCEL = classFile_BCEL.getMethods();
		
		Assertions.assertEquals(methodInfoList_CFPARSE.length,
				methodInfoList_BCEL.length);
		
		//Arrays.sort(methodInfoList_CFPARSE);
		//Arrays.sort(methodInfoList_BCEL);
		
		for (int i=0; i< methodInfoList_CFPARSE.length; i++ ) {
			Assertions.assertEquals(methodInfoList_CFPARSE[i].getName(),
					methodInfoList_BCEL[i].getName());
		}

	}

}
