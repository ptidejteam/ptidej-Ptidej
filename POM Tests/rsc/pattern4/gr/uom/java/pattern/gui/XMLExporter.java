package gr.uom.java.pattern.gui;

import gr.uom.java.pattern.PatternInstance;

import java.util.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jdom.Element;
import org.jdom.Document;
import org.jdom.output.XMLOutputter;
import org.jdom.output.Format;

public class XMLExporter {

    public XMLExporter(LinkedHashMap<String, Vector<PatternInstance>> map, File exportFile) {
        Element root = new Element("root");
		root.setName("system");
		Document doc = new Document(root);

        Set<String> keySet = map.keySet();

        for(String key : keySet) {
            Element pattern = new Element("pattern");
            pattern.setAttribute("name",key);
            root.addContent(pattern);
            
            Vector<PatternInstance> vector = map.get(key);
            for(PatternInstance patternInstance : vector) {
                Element instance = new Element("instance");
                pattern.addContent(instance);

                Iterator<PatternInstance.Entry> roleIterator = patternInstance.getRoleIterator();
                while(roleIterator.hasNext()) {
                    PatternInstance.Entry entry = roleIterator.next();
                    Element role = new Element("role");
                    instance.addContent(role);
                    role.setAttribute("name", entry.getRoleName());
                    role.setAttribute("element", entry.getElementName());
                    /*
                    Element className = new Element("class");
                    className.setText(entry.getClassName());
                    role.addContent(className);
                    */
                }
            }
        }
        Format f = Format.getRawFormat();
        f.setIndent("\t");
        XMLOutputter outp = new XMLOutputter(f);
        try {
            FileOutputStream fileStream = new FileOutputStream(exportFile);
            outp.output(doc, fileStream);
            fileStream.close();
        }
        catch(FileNotFoundException fnfe) { fnfe.printStackTrace(); }
        catch(IOException ioe) { ioe.printStackTrace(); }
    }
}
