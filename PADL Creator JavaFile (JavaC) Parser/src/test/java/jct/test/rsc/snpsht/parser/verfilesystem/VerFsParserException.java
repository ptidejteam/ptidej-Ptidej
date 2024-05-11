/* (c) Copyright 2008 and following years, Julien Tanteri,
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
package jct.test.rsc.snpsht.parser.verfilesystem;

public class VerFsParserException extends Exception {
	private static final long serialVersionUID = 8347620660800917034L;
	
	public static final short MARKER_NOT_FOUND = 0;
	public static final short ATTRIBUTE_NOT_FOUND = 1;
	public static final short SOURCE_NOT_FOUND = 2;
	public static final short PARSING_FAILED = 3;
	public static final short UNEXPECTED_NODE_VALUE = 4;
	public static final short ILLEGAL_MANAGER_TYPE = 5;
	public static final short MISFORMED_ELEMENT = 6;
	public static final short BAD_XPATH = 7;

	private short type;

	public VerFsParserException(short type, String message) {
		super(message);
		this.type = type;
	}

	public VerFsParserException(short type, Throwable e) {
		super(e);
		this.type = type;
	}

	public short getType() {
		return this.type;
	}
}
