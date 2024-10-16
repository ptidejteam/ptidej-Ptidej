// $Id: SequenceDiagramModule.java 16924 2009-03-23 17:35:16Z thn $
// Copyright (c) 2007 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies. This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason. IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.argouml.sequence2;

import org.apache.log4j.Logger;
import org.argouml.moduleloader.ModuleInterface;
import org.argouml.notation.Notation;
import org.argouml.notation.NotationName;
import org.argouml.notation.NotationProviderFactory2;
import org.argouml.notation.providers.uml.SDMessageNotationUml;
import org.argouml.persistence.PersistenceManager;
import org.argouml.sequence2.diagram.SequenceDiagramFactory;
import org.argouml.uml.diagram.DiagramFactory;
import org.argouml.uml.diagram.DiagramFactoryInterface2;
import org.argouml.uml.diagram.DiagramFactory.DiagramType;
import org.argouml.uml.ui.PropPanelFactoryManager;

/**
 * The Sequence Diagram Module description.
 * 
 * @see org.argouml.moduleloader.ModuleInterface
 * @author penyaskito
 */
public class SequenceDiagramModule implements ModuleInterface {

    private static final Logger LOG = Logger
            .getLogger(SequenceDiagramModule.class);

    private SequenceDiagramPropPanelFactory propPanelFactory;
        
    public boolean enable() {
        
        propPanelFactory =
            new SequenceDiagramPropPanelFactory();
        PropPanelFactoryManager.addPropPanelFactory(propPanelFactory);
        // TODO: Remove the casting to DiagramFactoryInterface2
        // as soon as DiagramFactoryInterface is removed.
        DiagramFactory.getInstance().registerDiagramFactory(
                DiagramType.Sequence, 
                (DiagramFactoryInterface2) new SequenceDiagramFactory());

        NotationProviderFactory2 npf = NotationProviderFactory2.getInstance();
        NotationName nn = Notation.findNotation(Notation.DEFAULT_NOTATION);
        npf.addNotationProvider(NotationProviderFactory2.TYPE_SD_MESSAGE, 
                nn, SDMessageNotationUml.class);
        
        PersistenceManager persistanceManager =
            PersistenceManager.getInstance();
        
        // Translate any old style sequence diagrams
        persistanceManager.addTranslation(
                "org.argouml.uml.diagram.sequence.ui.UMLSequenceDiagram",
                "org.argouml.sequence2.diagram.UMLSequenceDiagram");
        persistanceManager.addTranslation(
                "org.argouml.uml.diagram.sequence.ui.FigCreateActionMessage",
                "org.argouml.sequence2.diagram.FigMessage");
        persistanceManager.addTranslation(
                "org.argouml.uml.diagram.sequence.ui.FigDeleteActionMessage",
                "org.argouml.sequence2.diagram.FigMessage");
        persistanceManager.addTranslation(
                "org.argouml.uml.diagram.sequence.ui.FigCallActionMessage",
                "org.argouml.sequence2.diagram.FigMessage");
        persistanceManager.addTranslation(
                "org.argouml.uml.diagram.sequence.ui.FigReturnActionMessage",
                "org.argouml.sequence2.diagram.FigMessage");
        persistanceManager.addTranslation(
                "org.argouml.uml.diagram.sequence.ui.FigClassifierRole",
                "org.argouml.sequence2.diagram.FigClassifierRole");

        // Translate any sequence diagrams create with any previous svn
        // work in progress
        persistanceManager.addTranslation(
                "org.argouml.uml.diagram.sequence2.ui.UMLSequenceDiagram",
                "org.argouml.sequence2.diagram.UMLSequenceDiagram");
        persistanceManager.addTranslation(
                "org.argouml.uml.diagram.sequence2.ui.FigMessage",
                "org.argouml.sequence2.diagram.FigMessage");
        persistanceManager.addTranslation(
                "org.argouml.uml.diagram.sequence2.ui.FigClassifierRole",
                "org.argouml.sequence2.diagram.FigClassifierRole");
        persistanceManager.addTranslation(
                "org.argouml.uml.diagram.sequence2.ui.FigMessageSpline",
                "org.argouml.sequence2.diagram.FigMessageSpline");
        LOG.info("SequenceDiagram Module enabled.");
        return true;
    }

    public boolean disable() {

        PropPanelFactoryManager.removePropPanelFactory(propPanelFactory);

        // TODO: Remove the casting to DiagramFactoryInterface2
        // as soon as DiagramFactoryInterface is removed.
        DiagramFactory.getInstance().registerDiagramFactory(
                DiagramType.Sequence, (DiagramFactoryInterface2) null);

        LOG.info("SequenceDiagram Module disabled.");
        return true;
    }

    public String getName() {
        return "ArgoUML-Sequence";
    }

    public String getInfo(int type) {
        switch (type) {
        case DESCRIPTION:
            return "The new sequence diagram implementation";
        case AUTHOR:
            return "Christian Lepez Espenola";
        case VERSION:
            return "0.28";
        case DOWNLOADSITE:
            return "http://argouml-sequence.tigris.org";
        default:
            return null;
        }
    }
}