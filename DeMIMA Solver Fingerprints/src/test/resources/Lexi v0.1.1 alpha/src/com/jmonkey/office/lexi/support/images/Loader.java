package com.jmonkey.office.lexi.support.images;


// Java AWT Imports
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
//import com.jmonkey.core.util.Code;

public final class Loader extends Component {
	// Default image...
	private static final byte deafaultImage[] = {
		(byte)71,		(byte)73,		(byte)70,		(byte)56,		(byte)57,		(byte)97,
		(byte)16,		(byte)0,		(byte)16, 		(byte)0,		(byte)145,		(byte)255,
		(byte)0,		(byte)255,		(byte)255,		(byte)255,		(byte)128,		(byte)0, 
		(byte)24,		(byte)0,		(byte)0,		(byte)0,		(byte)192,		(byte)192,
		(byte)192,		(byte)33,		(byte)249,		(byte)4,		(byte)1,		(byte)0,
		(byte)0,		(byte)3,		(byte)0,		(byte)44,		(byte)0,		(byte)0, 
		(byte)0,		(byte)0,		(byte)16,		(byte)0,		(byte)16,		(byte)0,
		(byte)64,		(byte)2,		(byte)38,		(byte)156,		(byte)143,		(byte)105,
		(byte)193,		(byte)237,		(byte)129,		(byte)216,		(byte)146,		(byte)42,
		(byte)193,		(byte)65,		(byte)37,		(byte)157,		(byte)183,		(byte)98,
		(byte)251,		(byte)120,		(byte)152,		(byte)246,		(byte)137,		(byte)230,
		(byte)169,		(byte)144,		(byte)93,		(byte)42,		(byte)174,		(byte)19,
		(byte)231,		(byte)120,		(byte)205,		(byte)43,		(byte)71,		(byte)75,
		(byte)234,		(byte)222,		(byte)168,		(byte)82,		(byte)0,		(byte)0,
		(byte)59
		};
	
	private Loader() {
		super();
	}
	/**
	* This method attempts to load an image from the class directory of ImageLoader.<BR>
	* Exapmle:<BR>
	* <CODE>java.awt.Image image = Loader.load("somename.gif");</CODE><BR>
	* the image <I>comename.gif</I> is expected to be in <I>com/jmonkey/common/resource/image/somename.gif</I><P>
	* If not image is found, the ImageLoader will load a default image.<P>
	* All images stored in this directory must be lower case.
	* @param resource java.lang.String the name of the image.
	* @return java.awt.Image the loaded image.
	*/
	public static final Image load(String resource) {
		Loader loader = new Loader();
		System.out.println("Trying to load image: " + resource);
		try {
			Image img = loader.getToolkit().getImage(loader.getClass().getResource(resource.toLowerCase()));
			if(img == null) {
				System.out.println("Can't find image: " + resource.toLowerCase() + " (null)");
				return Toolkit.getDefaultToolkit().createImage(Loader.deafaultImage);
			}
			return img;
		} catch(Throwable t) {
			System.out.println("Can't find image: " + resource.toLowerCase() + " (Throwable)");
			return Toolkit.getDefaultToolkit().createImage(Loader.deafaultImage);
		}
	}
}
