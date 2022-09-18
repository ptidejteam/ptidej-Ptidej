/*
 * Created on 23 juil. 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package fr.emn.oadymppac.widgets;

import gl4java.GLEnum;
import gl4java.drawable.GLDrawable;
import gl4java.utils.textures.TGATextureGrabber;
import java.awt.Component;
import java.io.File;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.3 $
 * 
 * This class provides a utility panel. For now, it handles creating a TGA image
 * file corresponding to openGL components.
 */
public class GLUtilityPanel extends UtilityPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2454899485853684205L;

	/**
	 * @param vis
	 */
	public GLUtilityPanel(final Component vis) {
		super(vis);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.emn.oadymppac.widgets.UtilityPanel#saveComponent(java.io.File)
	 */
	public void saveComponent(final File file) {
		if (!(this.component instanceof GLDrawable)) {
			return;
		}

		final int w = this.component.getWidth();
		final int h = this.component.getHeight();
		if (((GLDrawable) this.component).getGLContext().gljMakeCurrent() == false) {
			System.out.println("cannot get gl context");
		}

		// PNGTextureGrabber needs further debugging
		//		PNGTextureGrabber tg = new PNGTextureGrabber(((GLDrawable) component)
		//				.getGL());
		//		tg.grabPixels(GLFunc.GL_FRONT, 0, 0, w, h);
		//		tg.write2File(file.toString());

		final TGATextureGrabber tg =
			new TGATextureGrabber(((GLDrawable) this.component).getGL());
		//tg.grabPixels(GLFunc.GL_FRONT, 0, 0, w, h);
		tg.grabPixels(GLEnum.GL_BACK, 0, 0, w, h);
		tg.write2File(file.toString());

	}

}