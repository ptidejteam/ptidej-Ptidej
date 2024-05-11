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
package jct.test.rsc.snpsht.parser.revml.sax.fsm.contentloader;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import jct.test.rsc.snpsht.filesystem.IFsRealEntity;
import jct.test.rsc.snpsht.parser.revml.RevMLDocCommonsStrings;
import jct.test.rsc.snpsht.parser.revml.sax.fsm.ErrorStateRevMLState;
import jct.test.rsc.snpsht.parser.sax.AbstractStateSaxFsm;
import jct.test.rsc.snpsht.parser.sax.SimpleSaxFsmParser;
import jct.test.rsc.snpsht.serializer.verfilesystem.VerFsCommonStrings;
import jct.test.rsc.snpsht.utils.Base64Coder;
import jct.test.rsc.snpsht.verfilesystem.VerFsFileRev;
import jct.test.rsc.snpsht.verfilesystem.VerFsManager;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class LoadDeltaState extends AbstractStringLoaderState {
	private IContentWriter writer;

	public LoadDeltaState(
		SimpleSaxFsmParser fsm,
		AbstractStateSaxFsm previewState,
		VerFsManager manager,
		VerFsFileRev file) {
		super(fsm, previewState, manager, file);
	}

	@Override
	public void startElement(
		String uri,
		String localName,
		String name,
		Attributes attributes) throws SAXException {
		String aName;
		boolean typeFound = false, encodingFound = false;

		if (name.trim().toLowerCase().compareTo(getWaitedMarker()) == 0) {
			System.out.println("Delta");
			// Check encoding type
			if (attributes != null) {
				for (int i = 0; i < attributes.getLength(); i++) {
					aName = attributes.getLocalName(i);

					if (RevMLDocCommonsStrings.FILE_REV_ENCODING_ATTRIBUTE
						.equals(aName.toLowerCase().trim())) {
						String type = attributes.getValue(i);

						if (type
							.compareTo(RevMLDocCommonsStrings.FILE_REV_NO_ENCODING_ATTR_VALUE) == 0) {
							System.out
								.println(RevMLDocCommonsStrings.FILE_REV_NO_ENCODING_ATTR_VALUE);
							this.writer = new CommonWriter();
						} else if (type
							.compareTo(RevMLDocCommonsStrings.FILE_REV_BASE64_ENCODING_ATTR_VALUE) == 0) {
							System.out
								.println(RevMLDocCommonsStrings.FILE_REV_BASE64_ENCODING_ATTR_VALUE);
							this.writer = new Base64Writer();
						} else {
							getFsm()
								.changeState(
									new ErrorStateRevMLState(
										getFsm(),
										this,
										getManager(),
										"Unexpected "
												+ RevMLDocCommonsStrings.FILE_REV_ENCODING_ATTRIBUTE
												+ " attribute value: " + type,
										ErrorStateRevMLState.MISFORMED_REVML_DOCUMENT));
							return;
						}
						encodingFound = true;
					} else if (RevMLDocCommonsStrings.FILE_REV_DELTA_TYPE_ATTRIBUTE
						.equals(aName.toLowerCase().trim())) {
						String type = attributes.getValue(i);

						if (type
							.compareTo(RevMLDocCommonsStrings.FILE_REV_UDIFF_DELTA_ATTR_VALUE) == 0) {
							System.out
								.println(RevMLDocCommonsStrings.FILE_REV_UDIFF_DELTA_ATTR_VALUE);
						} else {
							getFsm()
								.changeState(
									new ErrorStateRevMLState(
										getFsm(),
										this,
										getManager(),
										"Unexpected "
												+ RevMLDocCommonsStrings.FILE_REV_DELTA_TYPE_ATTRIBUTE
												+ " attribute value: " + type,
										ErrorStateRevMLState.MISFORMED_REVML_DOCUMENT));
							return;
						}
						typeFound = true;
					}
				}
			}

			if (!typeFound || !encodingFound) {
				getFsm().changeState(
					new ErrorStateRevMLState(
						getFsm(),
						this,
						getManager(),
						"Delta marker have no encoding or type attribute.",
						ErrorStateRevMLState.MISFORMED_REVML_DOCUMENT));
			}
		} else {
			super.startElement(uri, localName, name, attributes);
		}
	}

	@Override
	protected void processString(String loadedString) {
		System.out.println(loadedString);
		
		String targetFilePath =
			VerFsCommonStrings.REVS_FILES_PATH
					+ getManager().toRelativePath(getFile().getPath()).replace(
						"/",
						File.separator) + "."
					+ getFile().getRevID().getValue().replace('.', '_')
					+ ".diff";

		try {
			IFsRealEntity entity =
				getManager().getSourceManager().add(targetFilePath);
			this.writer.write(entity, loadedString);
		} catch (IOException e) {
			getFsm().changeState(
				new ErrorStateRevMLState(
					getFsm(),
					this,
					getManager(),
					"Catched ecpetion.\nType: " + e.getClass() + "\nMessage: " + e.getMessage(),
					ErrorStateRevMLState.EXCEPTION_CATCHED));
		}
	}

	@Override
	public String toString() {
		String toRet = "<LoadRevIDState>\nRev ID: " + getLoadedString();
		return toRet;
	}

	@Override
	protected String getWaitedMarker() {
		return RevMLDocCommonsStrings.FILE_REV_DELTA_MARKER;
	}

	private interface IContentWriter {
		public void write(IFsRealEntity entity, String toWrite) throws IOException;
	}

	private class Base64Writer implements IContentWriter {
		@Override
		public void write(IFsRealEntity entity, String toWrite) throws IOException {
			OutputStream os = entity.getOutputStream();

			String[] lines = toWrite.split("\n");
			for (String line : lines) {
				os.write(Base64Coder.decode(line));
			}
			os.close();
		}
	}

	private class CommonWriter implements IContentWriter {
		@Override
		public void write(IFsRealEntity entity, String toWrite) throws IOException {
			OutputStream os = entity.getOutputStream();
			os.write(toWrite.getBytes());
			os.close();
		}
	}

}
