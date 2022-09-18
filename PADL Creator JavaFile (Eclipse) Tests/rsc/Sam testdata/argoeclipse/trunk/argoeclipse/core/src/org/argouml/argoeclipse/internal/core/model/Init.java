// $Id: Init.java 0.1.1 2006/09/15 00:00:00 b00__1 Exp $
// Copyright (c) 2006 The Regents of the University of California. All
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

package org.argouml.argoeclipse.internal.core.model;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import org.argouml.application.StartCritics;
import org.argouml.application.api.Argo;
import org.argouml.application.api.Configuration;
import org.argouml.application.api.ProgressMonitor;
import org.argouml.application.modules.ModuleLoader;
import org.argouml.cognitive.AbstractCognitiveTranslator;
import org.argouml.cognitive.Designer;
import org.argouml.i18n.Translator;
import org.argouml.kernel.ProjectManager;
import org.argouml.model.Model;
import org.argouml.model.UmlException;
import org.argouml.model.mdr.MDRModelImplementation;
import org.argouml.moduleloader.ModuleLoader2;
import org.argouml.persistence.ProgressEvent;
import org.argouml.ui.ProjectBrowser;
import org.argouml.ui.SplashScreen;
import org.argouml.util.logging.SimpleTimer;

/**
 * This class is responsible for initialization.
 *
 * @author Bogdan Pistol
 */
public class Init {
    
    private static final int MAX_PROGRESS = 100;
    
    private static final int PHASE1_PROGRESS = 5;
    
    private static final int PHASE2_PROGRESS = 40;
    
    private static final int PHASE3_PROGRESS = 25;
    
    private static final int PHASE4_PROGRESS = 10;
    
    private static final int PHASE5_PROGRESS = 5;
    
    private static boolean initialized = false;
    
    private Init() {
    }
    
    /**
     * Verifies the initialization
     * @return true for initialized and false otherwise
     */
    public static boolean isInitilized() {
        return initialized;
    }
    
    /**
     * Initialize. 
     * 
     * @param monitor this will monitor the progress of the initialization
     */
    public static void initialize(ProgressMonitor monitor) {
        if (initialized) {
            return;
        }
        
        /*
         * If they didn't pass in a ProgressListener, they must not care
         * just create a null listener.
         */
        if (monitor == null) {
            monitor = getNullProgressListener();
        }
        monitor.setMaximumProgress(MAX_PROGRESS);
        int progress = 0;
        monitor.updateMainTask(ModelMessages.initMainTask);
        monitor.updateSubTask(ModelMessages.loadingMDRModel);
        
        /*
         * Because of the way the plugin dependency graph is organized,
         * we need to explicitly initialize the model subsystem because
         * there's no access from argouml.model to argouml.model.mdr.
         */
        try {
            MDRModelImplementation implementation =
                    new MDRModelImplementation();
            Model.setImplementation(implementation);
        } catch (UmlException e) {
            e.printStackTrace();
            return;
        }
        
        monitor.updateSubTask(ModelMessages.checkHost);
        
        SimpleTimer st = new SimpleTimer();
        st.mark("begin"); //$NON-NLS-1$

        // TODO: Do we need the static initialization from ArgoUML Main class?
        
        // JVM version is checked as a plugin dependency
        
        if (!checkHostsFile(monitor)) {
            return;
        }
        
        monitor.updateSubTask(ModelMessages.configProperties);
        // Synchronize the startup directory
        // TODO:  Is this needed?  Use workspace directory instead? - tfm
        String directory = Argo.getDirectory();
        org.tigris.gef.base.Globals.setLastDirectory(directory);

        // create an anonymous class as a kind of adaptor for the cognitive
        // System to provide proper translation/i18n.
        // TODO: Move this somewhere shared by ArgoUML & ArgoEclipse
        org.argouml.cognitive.Translator.setTranslator(
                new AbstractCognitiveTranslator() {
                    public String i18nlocalize(String key) {
                        return Translator.localize(key);
                    }

                    public String i18nmessageFormat(String key, 
                            Object[] iArgs) {
                        return Translator.messageFormat(key, iArgs);
                    }
                });

        /* set properties for application behaviour */
        System.setProperty("gef.imageLocation", //$NON-NLS-1$
                "/org/argouml/Images");  //$NON-NLS-1$

        // skip Swing look & feel - do we want one to match Eclipse?

        // Redirect the configuration factory to our adapter
        System.setProperty("argo.ConfigurationFactory",  //$NON-NLS-1$
            "org.argouml.argoeclipse.ui.preferences" //$NON-NLS-1$
                + ".ConfigurationFactory"); //$NON-NLS-1$
        boolean doSplash = Configuration.getBoolean(Argo.KEY_SPLASH, false);

        st.mark("create splash"); //$NON-NLS-1$
        SplashScreen splash = null;
        if (doSplash) {
            monitor.updateSubTask(ModelMessages.initSplash);
            splash = new SplashScreen();
            splash.setVisible(true);
        }

        // Initialize the Model subsystem
        st.mark("initialize model subsystem"); //$NON-NLS-1$
        progress += PHASE1_PROGRESS;
        monitor.updateProgress(progress);
        monitor.updateSubTask(Translator.
                localize("statusmsg.bar.model-subsystem")); //$NON-NLS-1$

        if (!Model.isInitiated()) {
            monitor.notifyMessage(ModelMessages.errorTitle,
                    ModelMessages.modelErrorIntro,
                    ModelMessages.modelErrorDetails);
            return;
        }
        
        progress += PHASE2_PROGRESS;
        monitor.updateProgress(progress);
        monitor.updateSubTask(ModelMessages.initProjectBrowser);
        
        // we want to start without the two default diagrams,
        // to map with the editor
        ProjectManager.getManager().makeEmptyProject(false);
        
        st.mark("initialize gui"); //$NON-NLS-1$
        ProjectBrowser projectBrowser = 
            ProjectBrowser.makeInstance(splash, false);
        JOptionPane.setRootFrame(projectBrowser);
        
        progress += PHASE3_PROGRESS;
        monitor.updateProgress(progress);
        
        // Reestablishing UI context uses Eclipse default mechanisms

        Designer.disableCritiquing();
        Designer.clearCritiquing();

        Designer.enableCritiquing();
        
        progress += PHASE4_PROGRESS;
        monitor.updateProgress(progress);
        monitor.updateSubTask(ModelMessages.initModules);

        // Initialize the module loader.
        st.mark("modules"); //$NON-NLS-1$

        ModuleLoader2.doLoad(false);
        // Old-style modules are not supported for ArgoEclipse
//        ModuleLoader.getInstance().initialize();
        progress += PHASE5_PROGRESS;
        monitor.updateProgress(progress);
        
        // we should init the actions listeners because the editor's local
        // toolbar is affected by the project listener
        ActionsListener.getInstance();

        st.mark("close splash"); //$NON-NLS-1$
        if (splash != null) {
            splash.setVisible(false);
            splash.dispose();
            splash = null;
        }

        // needs UI stuff, so it will be called in ui.model.InitUI
//        st.mark("start critics");
        
        monitor.updateProgress(MAX_PROGRESS);
        monitor.updateMainTask(ModelMessages.initFinished);
        monitor.updateSubTask(""); //$NON-NLS-1$
        initialized = true;

//        LOG.info("profile of load time ############");
//        for (Enumeration i = st.result(); i.hasMoreElements();) {
//            LOG.info(i.nextElement());
//        }
    }

    private static ProgressMonitor getNullProgressListener() {
        return new ProgressMonitor() {
            public void notifyMessage(final String title,
                    final String introduction, final String problem) {
            }

            public void setMaximumProgress(int max) {
            }

            public void updateProgress(int progress) {
            }

            public void progress(ProgressEvent event)
                    throws InterruptedException {
            }
            
            public void updateSubTask(String action) {
            }

            public boolean isCanceled() {
                return false;
            }

            public void updateMainTask(String task) {
            }

            public void notifyNullAction() {
            }
            
            public void close() {
            }

        };
    }
    
    /**
     * Initialize the critique.
     */
    public static void initCritics() {
        new StartCritics().run();
    }
    
    /**
     * Check that we can get the InetAddress for localhost.
     * This can fail on Unix if /etc/hosts is not correctly set up.
     */
    private static boolean checkHostsFile(ProgressMonitor monitor) {
        try {
            InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new java.io.PrintWriter(sw);
            e.printStackTrace(pw);
            monitor.notifyMessage(ModelMessages.errorTitle,
                    ModelMessages.hostErrorIntro,
                    sw.toString());
            return false;
        }
        return true;
    }

}
