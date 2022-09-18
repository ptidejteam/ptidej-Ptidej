// $Id: ConfigurationFactory.java 116 2006-08-13 12:58:45Z b00__1 $
// Copyright (c) 1996-2006 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
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


package org.argouml.argoeclipse.internal.ui.preferences;

import org.argouml.application.configuration.ConfigurationHandler;
import org.argouml.application.configuration.IConfigurationFactory;

/**
 * A factory object that provides configuration information.
 *
 * @author Thierry Lach
 * @since 0.9.4
 */
public class ConfigurationFactory implements IConfigurationFactory {

    /**
     * The active configuration handler.
     */
    private static ConfigurationHandler handler = new ConfigurationEclipse();

    /**
     * Private constructor to not allow instantiation.
     */
    private ConfigurationFactory() {
        // Don't allow instantiation
    }

    /**
     * @see org.argouml.application.configuration.IConfigurationFactory#getConfigurationHandler()
     * @return the configuration handler
     */
    public ConfigurationHandler getConfigurationHandler() {
	return handler;
    }

}
