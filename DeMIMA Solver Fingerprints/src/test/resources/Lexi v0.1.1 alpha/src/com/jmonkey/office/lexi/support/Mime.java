package com.jmonkey.office.lexi.support;

// Jmonkey API Imports
//import com.jmonkey.core.TextRegistry;
import java.io.File;
import java.io.IOException;

import com.jmonkey.export.Registry;

/**
 * this class should diea ASAP.
 * @author: 
 */
public final class Mime {
	private static Registry _REGISTRY = null;
	/**
	* Don't allow instances to be created.
	*/
	private Mime() {}
	/**
	* Add an association of extension to mimetype.
	* @param contentType java.lang.String
	* @param fileExtension java.lang.String
	*/
	public static final void addTypeForExtension(String contentType, String fileExtension) {
		Mime.ensureProperties();
		//Code.debug("Adding mime type for extension: " + fileExtension.trim().toLowerCase());
		
		getRegistry().setProperty("extensions", fileExtension.trim().toLowerCase(), contentType.trim().toLowerCase());
		try {
			getRegistry().commit();
		} catch(IOException ioe1) {
			throw new RuntimeException("Registry save faild. Mime unable to add association.");
		}
	}
	private static final void ensureProperties() {
		if(!getRegistry().isGroup("extensions")) {
			
			getRegistry().setProperty("extensions", "htm", "text/html");
			getRegistry().setProperty("extensions", "html", "text/html");
			getRegistry().setProperty("extensions", "shtml", "text/html");

			getRegistry().setProperty("extensions", "java", "text/plain");
			getRegistry().setProperty("extensions", "c", "text/plain");
			getRegistry().setProperty("extensions", "cc", "text/plain");
			getRegistry().setProperty("extensions", "cpp", "text/plain");
			getRegistry().setProperty("extensions", "h", "text/plain");
			getRegistry().setProperty("extensions", "txt", "text/plain");
			getRegistry().setProperty("extensions", "text", "text/plain");

			getRegistry().setProperty("extensions", "rtf", "text/rtf");

			//getRegistry()
			try {
				getRegistry().commit();
			} catch(IOException ioe1) {
				throw new RuntimeException("Mime unable to ensure extension properties exist.");
			}
		}
	}
/**
* This method forcable tries do find out 
* the content type of a particular file.
* If unable to do so, it return content/unknown.
* <P>
* The first step is to check the extension.
* Other possible ways are to read the content header,
* and try to  determin it that way, however that is
* not implemented at this time. 
*/
public static final String findContentType(File file) {
	try {
		Mime.ensureProperties();
		if (file != null) {
			String extn = (file.getName().substring((file.getName().lastIndexOf(".") + 1), file.getName().length())).toLowerCase();
			return Mime.findContentType(extn);
		} else {
			return "content/unknown";
		}
	} catch (StringIndexOutOfBoundsException sioobe0) {
		return "content/unknown";
	}
}
	/**
	* This method forcable tries do find out 
	* the content type of a particular extension string.
	* If unable to do so, it return content/unknown.
	* <P>
	* The first step is to check the extension.
	* Other possible ways are to read the content header,
	* and try to  determin it that way, however that is
	* not implemented at this time. 
	*/
	public static final String findContentType(String extension) {
		Mime.ensureProperties();
		//Code.debug("Checking mime type for extension: " + extension);
		

		return getRegistry().getString("extensions", extension, "content/unknown");
	}
	/**
	  * Gets our option registry
	  */
	protected static final Registry getRegistry() {
		if (_REGISTRY == null) {
			try {
				_REGISTRY = Registry.loadForClass(Mime.class);
			} catch (java.io.IOException ioe0) {
				System.err.println(ioe0.toString());
				//ioe0.printStackTrace(System.err);
				//Code.failed(ioe0);
			}
		}
		return _REGISTRY;
	}
}
