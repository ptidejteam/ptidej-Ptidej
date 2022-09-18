/*
 * Created on 18 juil. 2003
 */
package fr.emn.oadymppac.widgets;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 *          A utility panel providing various convenience functions such as
 *          saving and printing swing components e.g. visualizations.
 */
public class SwingUtilityPanel extends UtilityPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4551749018311971386L;

	public SwingUtilityPanel(final JComponent vis) {
		super(vis);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.emn.oadymppac.widgets.UtilityPanel#saveComponent(java.io.File)
	 */
	public void saveComponent(final File file) {
		final BufferedImage bi = new BufferedImage(this.component.getWidth(), this.component.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		final Graphics g = bi.createGraphics();

		this.component.paint(g);

		try {
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(bi, "jpg", bos);
			final byte[] jpgData = bos.toByteArray();
			final FileOutputStream fos = new FileOutputStream(file.getName());
			fos.write(jpgData);
			fos.close();
		} catch (final FileNotFoundException fnfe) {
			System.err.println("file not found");
		} catch (final IOException ioe) {
			System.err.println("io error");
		}
	}

}
