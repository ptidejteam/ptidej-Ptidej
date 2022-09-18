package gr.uom.java.pattern.gui;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class XMLFilter extends FileFilter {

    public boolean accept(File f) {
        if(f.isDirectory()) {
            return true;
        }

        String extension = getExtension(f);
        if(extension != null) {
            return extension.equalsIgnoreCase("xml");
        }
        return false;
    }

	public String getDescription() {
        return "XML";
    }

    private static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}
