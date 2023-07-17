// $Id$
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

package org.argouml.language.php.generator;

import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.argouml.application.helpers.ResourceLoaderWrapper;
import org.argouml.moduleloader.ModuleInterface;
import org.argouml.uml.generator.GeneratorHelper;
import org.argouml.uml.generator.GeneratorManager;
import org.argouml.uml.generator.Language;

/**
 * Module manager for PHP4 generator.
 */
public class ModulePHP4 implements ModuleInterface {
    
    /**
     * The log4j logger to log messages to
     */
    private static final Logger LOG = Logger.getLogger(ModulePHP4.class);

    private Language myLang; 
    private int version;

    /**
     * The major version of the language this module generates source code for
     */
    static final int LANGUAGE_MAJOR_VERSION = 4;

    /**
     * The name of the language this module generates source code for
     */
    protected static final String LANGUAGE_NAME = "PHP";

    /**
     * Icon to represent this notation in the GUI
     */
    protected static final String ICON_NAME = "PHPNotation";
    
    /**
     * Info block already written to log file?
     */
    protected static final TreeMap TM_INFO_BLOCK_LOGGED = new TreeMap();

    /**
     * Constructor for PHP module.
     */
    public ModulePHP4() {
        this(LANGUAGE_MAJOR_VERSION);
    }
    
    /**
     * Constructor for language versions other than the default.
     * 
     * @param langVersion major version number of PHP
     */
    protected ModulePHP4(int langVersion) {
        version = langVersion;
    }
    
    /*
     * @see org.argouml.moduleloader.ModuleInterface#getName()
     */
    public String getName() {
        return "Generator" + LANGUAGE_NAME.toUpperCase()
                + version;
    }

    /*
     * @see org.argouml.moduleloader.ModuleInterface#getInfo(int)
     */
    public String getInfo(int type) {
        switch (type) {
        case DESCRIPTION:
            return "notation and source code generator for "
                    + LANGUAGE_NAME.toUpperCase() 
                    + version;
        case AUTHOR:
            return "Kai Schr\u00F6der";
        case VERSION:
            return "0.0.$Revision: 31 $";
        default:
            return null;
        }
    }

    /*
     * @see org.argouml.moduleloader.ModuleInterface#enable()
     */
    public boolean enable() {
        if (myLang == null) {
            myLang = GeneratorHelper.makeLanguage(
                    LANGUAGE_NAME + version,
                    ResourceLoaderWrapper.lookupIconResource(ICON_NAME));
        }
        GeneratorManager.getInstance().addGenerator(myLang, 
                new GeneratorPHP4(version));
        logModuleInfo();
        return true;
    }
    /*
     * @see org.argouml.moduleloader.ModuleInterface#disable()
     */
    public boolean disable() {
        GeneratorManager.getInstance().removeGenerator(myLang);
        return true;
    }

    /**
     * Logs module info block to logger
     */
    protected final void logModuleInfo() {
        logModuleInfo(0);
    }

    /**
     * Logs module info block to logger
     *
     * @param iMinWidth Minimum block width.
     */
    protected final void logModuleInfo(int iMinWidth) {
        if (!TM_INFO_BLOCK_LOGGED.containsKey(this.getClass().toString())) {
            StringBuffer sbHeadLine = new StringBuffer("| Module ");
            sbHeadLine.append(getName() + " "
                    + getInfo(ModuleInterface.VERSION) + " |");

            StringBuffer sbCopyLine =
                    new StringBuffer("| Copyright (c) 2006, ");
            sbCopyLine.append(getInfo(ModuleInterface.AUTHOR) + " |");

            StringBuffer sbDescLine = new StringBuffer("| ");
            sbDescLine.append(getInfo(ModuleInterface.DESCRIPTION) + " |");

            if (sbHeadLine.length() > iMinWidth) {
                iMinWidth = sbHeadLine.length();
            }
            if (sbCopyLine.length() > iMinWidth) {
                iMinWidth = sbCopyLine.length();
            }
            if (sbDescLine.length() > iMinWidth) {
                iMinWidth = sbDescLine.length();
            }

            String sRulerLine = "+";
            for (int i = 1; i <= iMinWidth - 2; i++) {
                sRulerLine += "-";
            }
            sRulerLine += "+";

            String sEmptyLine = "";
            for (int i = 1; i <= iMinWidth; i++) {
                sEmptyLine += " ";
            }

            while (sbHeadLine.length() < iMinWidth) {
                sbHeadLine.insert(sbHeadLine.length() - 2, " ");
                if (sbHeadLine.length() < iMinWidth) {
                    sbHeadLine.insert(2, " ");
                }
            }
            while (sbCopyLine.length() < iMinWidth) {
                sbCopyLine.insert(sbCopyLine.length() - 2, " ");
            }
            while (sbDescLine.length() < iMinWidth) {
                sbDescLine.insert(sbDescLine.length() - 2, " ");
            }

            LOG.info(sEmptyLine);
            LOG.info(sRulerLine);
            LOG.info(sbHeadLine.toString());
            LOG.info(sRulerLine);
            LOG.info(sbCopyLine.toString());
            LOG.info("| " + sEmptyLine.substring(4) + " |");
            LOG.info(sbDescLine.toString());
            LOG.info(sRulerLine);
            LOG.info(sEmptyLine);

            TM_INFO_BLOCK_LOGGED.put(this.getClass().toString(), "true");
        }
    }


}
