/*
 * Created on 18 juil. 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package fr.emn.oadymppac.widgets;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 * A utility panel providing various convenience functions
 * such as saving and printing visualizations.
 */
public abstract class UtilityPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1120144318673070804L;
	Component component;

	/**
	 * Various convenience functions such as printing.
	 * 
	 * @param panel
	 */
	public UtilityPanel(final Component vis) {
		this.component = vis;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		final JButton saveBtn = new JButton("save graphics");
		final JFileChooser jfc = new JFileChooser();
		jfc.setCurrentDirectory(new File(System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "images"
				+ System.getProperty("file.separator")));
		jfc.addChoosableFileFilter(new FileFilter() {
			public boolean accept(final File pathname) {
				if (pathname.isDirectory()) {
					return true;
				}
				else {
					final String name = pathname.getName();
					if (name.lastIndexOf(".") == -1) {
						return false;
					}

					final String ext = name.substring(name.lastIndexOf("."));
					return ext.equalsIgnoreCase("jpg")
							|| ext.equalsIgnoreCase("jpeg")
							|| ext.equalsIgnoreCase("gif")
							|| ext.equalsIgnoreCase("png");
				}
			}

			public String getDescription() {
				return "Images (jpg, jpeg, gif, png)";
			}
		});
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				final int ret = jfc.showSaveDialog(UtilityPanel.this.component);
				if (ret == JFileChooser.APPROVE_OPTION) {
					UtilityPanel.this.saveComponent(jfc.getSelectedFile());
				}
			}
		});
		saveBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
		saveBtn.setAlignmentY(Component.TOP_ALIGNMENT);
		this.add(saveBtn);
	}

	public abstract void saveComponent(File file);

}
