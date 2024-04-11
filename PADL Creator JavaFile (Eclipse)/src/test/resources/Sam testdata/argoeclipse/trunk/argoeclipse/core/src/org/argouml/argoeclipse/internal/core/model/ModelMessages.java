// $Id: ModelMessages.java 0.1.1 2006/09/15 00:00:00 b00__1 Exp $
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

import org.eclipse.osgi.util.NLS;

/**
 * Models message bundle.
 * @author Bogdan Pistol
 */
public class ModelMessages extends NLS {

    private static final String BUNDLE_NAME = 
        "org.argouml.argoeclipse.internal.core." //$NON-NLS-1$
        + "model.ModelMessages"; //$NON-NLS-1$
    
    public static String loadingMDRModel;
    public static String checkHost;
    public static String configProperties;
    public static String argoCritics;

    public static String initMainTask;
    public static String initSplash;
    public static String initProjectBrowser;
    public static String initModules;
    public static String initFinished;
    
    public static String errorTitle;
    public static String modelErrorIntro;
    public static String modelErrorDetails;
    public static String hostErrorIntro;
        
    static {
        NLS.initializeMessages(BUNDLE_NAME, ModelMessages.class);
    }
    
}
