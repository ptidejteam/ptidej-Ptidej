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
package jct.test.rsc.snpsht.parser.sax;

public class SaxFsmParseException extends Exception {
	private static final long serialVersionUID = 1585065250990190682L;
	
	private AbstractStateSaxFsm failedSt;

	public SaxFsmParseException(AbstractStateSaxFsm failedSt) {
		super();

		this.failedSt = failedSt;
	}

	public SaxFsmParseException(String message, AbstractStateSaxFsm failedSt) {
		super(message);

		this.failedSt = failedSt;
	}
	
	public AbstractStateSaxFsm getFailedState() {
		return this.failedSt;
	}
}
