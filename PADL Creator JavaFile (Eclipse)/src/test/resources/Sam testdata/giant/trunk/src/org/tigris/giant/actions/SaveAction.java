package org.tigris.giant.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;

import org.tigris.giant.persistance.GiantSaver;

public class SaveAction extends AbstractAction {
    
    private JComponent component;
    
    private static final Logger LOG = Logger.getLogger(SaveAction.class);
    
    public SaveAction(JComponent component) {
        super("Save");
        this.component = component;
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            JFileChooser fc = new JFileChooser();
            fc.showSaveDialog(component);
            File file = fc.getSelectedFile();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.getDeclaredConstructor().newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            FileOutputStream out = new FileOutputStream(file);
            GiantSaver gs = new GiantSaver();
            gs.save(out,doc);
        } catch (ParserConfigurationException e) {
            LOG.error("got a ParseConfigurationException ", e);
        } catch (FileNotFoundException e) {
            LOG.error("got a FileNotFoundException ", e);
        }
    }
}
