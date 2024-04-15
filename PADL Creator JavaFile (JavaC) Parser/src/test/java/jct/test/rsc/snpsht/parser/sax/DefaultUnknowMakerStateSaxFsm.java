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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class DefaultUnknowMakerStateSaxFsm extends AbstractStateSaxFsm {
	private AbstractStateSaxFsm callBackState;
	private String uri, localName, name, content;
	private Attributes attributes;

	public DefaultUnknowMakerStateSaxFsm(
		SimpleSaxFsmParser fsm,
		AbstractStateSaxFsm previewState,
		AbstractStateSaxFsm callBackState,
		Attributes attributes,
		String localName,
		String name,
		String uri) {

		super(fsm, previewState);

		this.attributes = new AttributesImpl(attributes);
		this.callBackState = callBackState;
		this.localName = localName;
		this.name = name;
		this.uri = uri;
		this.content = "";
	}

	public AbstractStateSaxFsm getCallBackState() {
		return this.callBackState;
	}

	public String getUri() {
		return this.uri;
	}

	public String getLocalName() {
		return this.localName;
	}

	public String getName() {
		return this.name;
	}

	public String getContent() {
		return this.content;
	}

	public Attributes getAttributes() {
		return this.attributes;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		this.content += new String(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {

		if (this.name.compareTo(name.trim()) == 0) {
			getFsm().changeState(this.callBackState);
		} else {
			getFsm().changeState(
				new DefaultErrorStateSaxFsm(
					getFsm(),
					this,
					"Misformed xml document.\nEnd marker unexpected.\nReceived: "
							+ name + "\nExpected: " + this.name));
		}
	}

	@Override
	public void startElement(
		String uri,
		String localName,
		String name,
		Attributes attributes) throws SAXException {

		getFsm().changeState(
			new DefaultUnknowMakerStateSaxFsm(
				getFsm(),
				this,
				this,
				attributes,
				localName,
				name,
				uri));
	}

	@Override
	public String toString() {
		String toRet = "<DefaultUnknowMakerStateSaxFsm>\n" +
		"Unknow maker: <" + this.name;
		
		if (this.attributes != null) {
            for (int i = 0; i < this.attributes.getLength(); i++) {
                String aName = this.attributes.getLocalName(i); // Attr name 

                if ("".equals(aName)) {
                    aName = this.attributes.getQName(i);
                }

                toRet += " " + aName + "=\"" + this.attributes.getValue(i) + "\"";
            }
        }
		toRet += ">\nContent: " + this.content.trim();
		
		return toRet;
	}

}
