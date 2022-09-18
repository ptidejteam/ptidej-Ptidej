package fr.emn.oadymppac.utils;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * @author Mohammad Ghoniem
 *
 * File filter for files storing clusterings (.clu files).
 */
public class ClusteringFileFilter extends FileFilter {

	/**
	 * @see javax.swing.filechooser.FileFilter#accept(File)
	 */
	public boolean accept(final File f) {
		if (f.isDirectory()) {
			return true;
		}
		else {
			final String name = f.getName();
			if (name.lastIndexOf(".") == -1) {
				return false;
			}
			final String extension = name.substring(name.lastIndexOf("."));
			return extension.equalsIgnoreCase(".clu");
		}
	}

	/**
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	public String getDescription() {
		return "clustering files (.clu)";
	}
}
