/* (c) Copyright 2001 and following years, Yann-Gaël Guéhéneuc ,
 * University of Montreal.
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * the author, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN,
 * ANY LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF THE AUTHOR IS ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package ptidej.viewer;

import java.awt.Dimension;
import java.awt.Toolkit;

public interface Constants {
	String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	int MODEL_AOL_SOURCE_CODE = 0;
	int MODEL_CPP_SOURCE_CODE = 1;
	int MODEL_CSH_SOURCE_CODE = 2;
	int MODEL_JAVA_BYTECODE = 4;
	int MODEL_JAVA_JAR_FILE = 5;
	int MODEL_JAVA_SOURCE_CODE = 3;
	String PROGRAM_AUTHOR = "Yann-Gaël Guéhéneuc , Naouel Moha, and Ptidej Team";
	String PROGRAM_FILE_NAME = "PtidejBlackbox";
	String PROGRAM_NAME = "Ptidej Blackbox";
	String PROGRAM_VERSION = "1.0 (Ptidej 4)";
	Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
}
