// $Id$
// Copyright (c) 2004-2006 The Regents of the University of California. All
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

package org.argouml.language.php.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.argouml.application.ArgoVersion;
import org.argouml.language.php.PHPDocumentor;
import org.argouml.model.Model;
import org.argouml.uml.UUIDHelper;
import org.argouml.uml.generator.CodeGenerator;
import org.argouml.uml.generator.TempFileUtils;


/**
 * Generator class for PHP 4.x & 5.x source code.
 * 
 * This class supports both PHP 4 & 5 conditionalized by the variable
 * iLanguageMajorVersion. Look for it to find where behavior differs.
 * 
 * @author Kai Schr&ouml;der
 * @since ArgoUML 0.15.5
 */
public class GeneratorPHP4 implements CodeGenerator {

    /**
     * Sets the indentation level to four spaces
     */
    protected static final String INDENT = "    ";

    /**
     * The major version of the language this module generates source code for
     */
    private int iLanguageMajorVersion;

    /**
     * source section handler
     */
    private static Section objSection = null;

    /**
     * The log4j logger to log messages to
     */
    private static final Logger LOG = Logger.getLogger(GeneratorPHP4.class);

    // ----- class constructors ------------------------------------------------

    /**
     * Zero-argument class constructor
     */
    GeneratorPHP4() {
        this(ModulePHP4.LANGUAGE_MAJOR_VERSION);
    }

    /**
     * Class constructor
     *
     * @param iLangMajorVersion The major version of the language this module
     *                          generates source code for.
     */
    protected GeneratorPHP4(int iLangMajorVersion) {
        iLanguageMajorVersion = iLangMajorVersion;

    }


    private String generateSubmachine(Object m) {
        Object c = Model.getFacade().getSubmachine(m);
        if (c == null) {
            return "include / ";
        }
        if (Model.getFacade().getName(c) == null) {
            return "include / ";
        }
        if (Model.getFacade().getName(c).length() == 0) {
            return "include / ";
        }
        return ("include / " + Model.getFacade().getName(c));
    }


    /*
     * Generates operation
     *
     * @param modelElement Model element to generate notation for.
     * @param bAddDocs     Add documentation in front of notation?
     *
     * @return Generated notation for model element.
     */
    private String generateOperation(Object modelElement, boolean bAddDocs) {
        if (!Model.getFacade().isAOperation(modelElement)) {
            throw new ClassCastException(modelElement.getClass()
                    + " has wrong object type, Operation required");
        }

        String sOperation = "";

        if (bAddDocs) {
            PHPDocumentor objPHPDoc = null;
            try {
                objPHPDoc = new PHPDocumentor(modelElement);
            } catch (Exception exp) {
                LOG.error("Generating operation DocBlock FAILED: "
                    + exp.getMessage());
            } finally {
                if (objPHPDoc != null) {
                    sOperation += objPHPDoc.toString();
                }
            }
        }

        String sVisibility =
            generateVisibility(Model.getFacade().getVisibility(modelElement));
        if (sVisibility != null && sVisibility != "") {
            sOperation += sVisibility + " ";
        }

        if (iLanguageMajorVersion > 4) {
            /* scope */
            Object ownerScope = Model.getFacade().getOwnerScope(modelElement);
            if (Model.getScopeKind().getClassifier().equals(ownerScope)) {
                sOperation += "static ";
            }

            /* changeability */
            if (Model.getFacade().isLeaf(modelElement)) {
                sOperation += "final ";
            }

            /* abstractness */
            if (Model.getFacade().isAbstract(modelElement)) {
                sOperation += "abstract ";
            }
        }

        boolean bReturnByReference = false;

        Iterator itTaggedValues =
            Model.getFacade().getTaggedValues(modelElement);
        if (itTaggedValues != null) {
            while (itTaggedValues.hasNext()) {
                Object objTaggedValue = itTaggedValues.next();
                if (Model.getFacade().getTagOfTag(objTaggedValue).equals("&")) {
                    if (Model.getFacade().getValueOfTag(objTaggedValue)
                            .equals("true")) {
                        bReturnByReference = true;

                        break;
                    }
                }
            }
        }

        String sOperationName = NameGenerator.generate(modelElement,
                iLanguageMajorVersion);

        if (bReturnByReference) {
            sOperationName = "&" + sOperationName;
        }

        sOperation += "function " + sOperationName + "(";

        Collection colParameters = 
            Model.getFacade().getParameters(modelElement);
        if (colParameters != null) {
            Iterator itParameters = colParameters.iterator();
            boolean bFirst = true;
            while (itParameters.hasNext()) {
                Object objParameter = itParameters.next();
                if (!Model.getFacade().isReturn(objParameter)) {
                    if (!bFirst) {
                        sOperation += ", ";
                    } else {
                        bFirst = false;
                    }

                    sOperation += generateParameter(objParameter);
                }
            }
        }

        sOperation += ")";

        return sOperation;
    }

    /*
     * Generates attribute
     *
     * @param modelElement Model element to generate notation for.
     * @param bAddDocs     Add documentation in front of notation?
     *
     * @return Generated notation for model element.
     */
    private String generateAttribute(Object modelElement, boolean bAddDocs) {
        if (!Model.getFacade().isAAttribute(modelElement)) {
            throw new ClassCastException(modelElement.getClass()
                    + " has wrong object type, Attribute required");
        }

        String sAttribute = "";

        if (bAddDocs) {
            PHPDocumentor objPHPDoc = null;
            try {
                objPHPDoc = new PHPDocumentor(modelElement);
            } catch (Exception exp) {
                LOG.error("Generating attribute DocBlock FAILED: "
                    + exp.getMessage());
            } finally {
                if (objPHPDoc != null) {
                    sAttribute += objPHPDoc.toString(INDENT);
                }
            }
        }

        String sVisibility = 
            generateVisibility(Model.getFacade().getVisibility(modelElement));
        if (sVisibility != null && sVisibility != "") {
            sAttribute += sVisibility + " ";
        }

        if (iLanguageMajorVersion > 4) {
            /* scope */
            Object ownerScope = Model.getFacade().getOwnerScope(modelElement);
            if (Model.getScopeKind().getClassifier().equals(ownerScope)) {
                sAttribute += "static ";
            }
        } else {
            sAttribute += "var ";
        }

        sAttribute += "$" + NameGenerator.generate(modelElement,
                iLanguageMajorVersion);

        String sInitialValue = null;
        Object exprInitialValue = 
            Model.getFacade().getInitialValue(modelElement);
        if (exprInitialValue != null) {
            sInitialValue = generateDefaultValue(
                    Model.getFacade().getType(modelElement),
                    generateExpression(exprInitialValue).trim(), false);
        } else {
            sInitialValue = generateDefaultValue(
                    Model.getFacade().getType(modelElement), null, false);
        }

        if (sInitialValue != null && sInitialValue.length() > 0) {
            sAttribute += " = " + sInitialValue;
        } else {
            sAttribute += "[ ";
            sAttribute += (exprInitialValue != null) ? "!= null" : "null";
            sAttribute += " | ";
            sAttribute += generateDefaultValue(
                Model.getFacade().getType(modelElement),
                    generateExpression(exprInitialValue).trim(), false);
            sAttribute += " | ";
            sAttribute += generateDefaultValue(
                Model.getFacade().getType(modelElement),
                    generateExpression(exprInitialValue).trim(), true);
            sAttribute += " ]";
        }

        sAttribute += ";";

        return sAttribute;
    }

    private static String generateExpression(Object expr) {
        if (Model.getFacade().isAExpression(expr))
            return generateUninterpreted(
                    (String) Model.getFacade().getBody(expr));
        else if (Model.getFacade().isAConstraint(expr))
            return generateExpression(Model.getFacade().getBody(expr));
        return "";
    }
    
    private static String generateUninterpreted(String un) {
        if (un == null)
            return "";
        return un;
    }
    
    /*
     * Generates parameter
     *
     * @param modelElement Model element to generate notation for.
     *
     * @return Generated notation for model element.
     */
    private String generateParameter(Object modelElement) {
        if (!Model.getFacade().isAParameter(modelElement)) {
            throw new ClassCastException(modelElement.getClass()
                    + " has wrong object type, Parameter required");
        }

        String sParameter = "";

        if (Model.getFacade().isReturn(modelElement)) {
            Object objType = Model.getFacade().getType(modelElement);
            if (Model.getFacade().getName(objType).equals("void")) {
                return "";
            } 
            String sType = convertType(objType);
            if (sType != null && !"".equals(sType.trim())) {
                return "return (" + sType + ") $returnValue;";
            } 
            return "return $returnValue;";
        }
        if (iLanguageMajorVersion < 5) {
                // TODO: Do we really need this for PHP5?
                // TODO: Implement this in Model subsystem?
                /* if OUT or INOUT, then pass by reference */
            if (Model.getFacade().getKind(modelElement).equals(
                    Model.getDirectionKind().getInOutParameter())
                        || Model.getFacade().getKind(modelElement).equals(
                                Model.getDirectionKind().getOutParameter())) {
                sParameter += "&";
            }
        }
        
        if (iLanguageMajorVersion >= 5) {
            String sTypeHint = null;
                
            try {
                sTypeHint = NameGenerator.generateClassifierName(
                            Model.getFacade().getType(modelElement));
            } catch (Exception exp) {
                LOG.error("Finding type hint FAILED: " + exp.getMessage());
            } finally {
                if (sTypeHint != null && sTypeHint != "" && convertType(
                    Model.getFacade().getType(modelElement)) == null) {
                    sParameter += " " + sTypeHint + " ";
                }
            }
        }
        
        sParameter += "$" + Model.getFacade().getName(modelElement);
        
        String sDefaultValue =
            generateExpression(Model.getFacade().getDefaultValue(modelElement));
        if (sDefaultValue != null && sDefaultValue.length() > 0) {
            sParameter += " = " + sDefaultValue;
        } else {
            boolean bAddDefaultValue = false;
                
            Collection colParameters = 
                Model.getFacade().getParameters(Model.getFacade()
                                        .getBehavioralFeature(modelElement));
            if (colParameters != null) {
                Iterator itParameters = colParameters.iterator();
                while (itParameters.hasNext()) {
                    Object objParameter = itParameters.next();
                    if (!Model.getFacade().isReturn(objParameter)) {
                        if (!modelElement.equals(objParameter)) {
                            if (Model.getFacade()
                                   .getDefaultValue(objParameter) != null) {
                                bAddDefaultValue = true;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
                
            if (bAddDefaultValue) {
                sParameter += " = " + generateDefaultValue(
                    Model.getFacade().getType(modelElement), null, false);
            }
        }
        
        return sParameter;
    }

    /*
     * Generates package
     *
     * @param modelElement Model element to generate notation for.
     *
     * @return Generated notation for model element.
     *
     * TODO: fix org.argouml.model.Facade#getType
     */
    private String generatePackage(Object modelElement) {
        String sPackage = "";

        if (!Model.getFacade().isAPackage(modelElement)) {
            throw new ClassCastException(modelElement.getClass()
                    + " has wrong object type, Package required");
        }

        String sPackageName =
                NameGenerator.generate(modelElement, iLanguageMajorVersion);

        PHPDocumentor objPHPDoc = null;
        try {
            objPHPDoc = new PHPDocumentor(modelElement);
        } catch (Exception exp) {
            LOG.error("Generating package DocBlock FAILED: "
                    + exp.getMessage());
        } finally {
            if (objPHPDoc != null) {
                sPackage += objPHPDoc.toString() + "\n";
            }
        }

        Collection colElements = 
            Model.getFacade().getOwnedElements(modelElement);
        if (colElements != null) {
            Iterator itElements = colElements.iterator();
            if (itElements != null) {
                while (itElements.hasNext()) {
                    Object objElement = itElements.next();
                    if (Model.getFacade().isAPackage(objElement)) {
                        sPackage += generatePackage(objElement) + "\n";
                    } else if (Model.getFacade().isAClassifier(objElement)) {
                        sPackage += generateClassifier(objElement) + "\n";
                    } else {
                        sPackage += "/*\n";
                        sPackage += "feature not supported by PHP:\n";
                        sPackage += "-----------------------------\n";
                        sPackage += Model.getFacade().getName(objElement);
                        sPackage += " [" + objElement + "]\n";
                        sPackage += "*/\n";
                    }
                    if (itElements.hasNext()) {
                        sPackage += "\n";
                    }
                }
            }
        } else {
            sPackage += "// this package contains no elements\n";
        }

        sPackage += "\n/* end of package " + sPackageName + " */";

        return sPackage;
    }

    /*
     * Generates class or interface
     *
     * @param modelElement Model element to generate notation for.
     *
     * @return Generated notation for model element.
     */
    private String generateClassifier(Object modelElement) {
        if (!Model.getFacade().isAClassifier(modelElement)) {
            throw new ClassCastException(modelElement.getClass()
                    + " has wrong object type, Classifier required");
        }

        String sClassifier = "";

        String sClassType = "";
        if (iLanguageMajorVersion > 4) {
            if (Model.getFacade().isAClass(modelElement)) {
                if (Model.getFacade().isLeaf(modelElement)) {
                    sClassType = "final class";
                } else {
                    sClassType = "class";
                }
                if (Model.getFacade().isAbstract(modelElement)) {
                    sClassType = "abstract " + sClassType;
                }
            } else if (Model.getFacade().isAInterface(modelElement)) {
                sClassType = "interface";
                if (Model.getFacade().isLeaf(modelElement)) {
                    sClassType = "final " + sClassType;
                }
            } else {
                return null;
            }
        } else {
            if (Model.getFacade().isAClass(modelElement)
                    || Model.getFacade().isAInterface(modelElement)) {
                sClassType = "class";
            } else {
                return null;
            }
        }

        PHPDocumentor objPHPDoc = null;
        try {
            objPHPDoc = new PHPDocumentor(modelElement);
        } catch (Exception exp) {
            LOG.error("Generating classifier DocBlock FAILED: "
                    + exp.getMessage());
        } finally {
            if (objPHPDoc != null) {
                sClassifier += objPHPDoc.toString();
            }
        }

        String sClassName =
                NameGenerator.generate(modelElement, iLanguageMajorVersion);

        sClassifier += sClassType + " " + sClassName + "\n";

        sClassifier += generateClassifierGeneralisations(modelElement);
        sClassifier += generateClassifierSpecifications(modelElement);

        sClassifier += "{\n";

        sClassifier += generateClassifierAttributes(modelElement);
        sClassifier += generateClassifierOperations(modelElement);

        sClassifier += "\n} /* end of " + sClassType + " " + sClassName + " */";

        return sClassifier;
    }

    /*
     * Generates association
     *
     * @param modelElement Model element to generate notation for.
     *
     * @return Generated notation for model element.
     */
    private String generateAssociation(Object modelElement) {
        // TODO: Auto-generated method stub
        LOG.debug("generateExtensionPoint(MExtensionPoint modelElement)");

        if (!Model.getFacade().isAAssociation(modelElement)) {
            throw new ClassCastException(modelElement.getClass()
                    + " has wrong object type, Association required");
        }

        return "generateAssociation(MAssociation modelElement)";
    }

    /*
     * Generates association end
     *
     * @param modelElement Model element to generate notation for.
     *
     * @return Generated notation for model element.
     */
    private String generateAssociationEnd(Object modelElement) {
        // TODO: Auto-generated method stub
        LOG.debug("generateAssociationEnd(MAssociationEnd modelElement)");

        if (!Model.getFacade().isAAssociationEnd(modelElement)) {
            throw new ClassCastException(modelElement.getClass()
                    + " has wrong object type, AssociationEnd required");
        }

        return "generateAssociationEnd(MAssociationEnd modelElement)";
    }

    /*
     * Generates multiplicity
     *
     * @param modelElement Model element to generate notation for.
     *
     * @return Generated notation for model element.
     */
    private String generateMultiplicity(Object modelElement) {
        // TODO: Auto-generated method stub
        LOG.debug("generateMultiplicity(Multiplicity modelElement)");

        if (!Model.getFacade().isAMultiplicity(modelElement)) {
            throw new ClassCastException(modelElement.getClass()
                    + " has wrong object type, Multiplicity required");
        }

        return "generateMultiplicity(Multiplicity modelElement)";
    }

    /*
     * Generates visibility
     *
     * @param modelElement Model element to generate notation for.
     *
     * @return Generated notation for model element.
     */
    private String generateVisibility(Object modelElement) {
        if (!Model.getFacade().isAVisibilityKind(modelElement)) {
            throw new ClassCastException(modelElement.getClass()
                    + " has wrong object type, VisibilityKind required");
        }

        if (iLanguageMajorVersion > 4) {
            if (Model.getVisibilityKind().getPublic().equals(modelElement)) {
                return "public";
            } else if (Model.getVisibilityKind().getProtected()
                    .equals(modelElement)) {
                return "protected";
            } else if (Model.getVisibilityKind().getPrivate()
                    .equals(modelElement)) {
                return "private";
            }
        }

        return "";
    }


    /*
     * Generate the file.
     *
     * @param modelElement Model element to generate notation for.
     * @param sPath        output base directory
     *
     * @return name of generated file on success;
     *         <code>null</code> otherwise.
     */
    private String generateFile(Object modelElement, String sPath) {
        if (!Model.getFacade().isAClassifier(modelElement)) {
            throw new ClassCastException(modelElement.getClass()
                    + " has wrong object type, Classifier required");
        }

        String sFilename = NameGenerator.generateFilename(modelElement, sPath,
                iLanguageMajorVersion);

        if (sFilename == null || sFilename.length() == 0) {
            LOG.error("Can't generate a nameless class");

            return null;
        }

        File f = new File(sFilename);
        if (f.exists()) {
            LOG.info(getName() + " updates " + f.getPath());
            try {
                updateFile(modelElement, f);
            } catch (Exception exp) {
                LOG.error("Update " + f.getPath() + " failed: "
                        + exp.getMessage());

                return null;
            }

            LOG.debug("Update " + f.getPath() + " successfull");

            return sFilename;
        }

        LOG.info(getName() + " creates " + f.getPath());

        File fPath = new File(sPath);
        if (!fPath.isDirectory()) {
            if (!fPath.mkdirs()) {
                LOG.error(getName() + " could not make directory "
                        + sPath);
                return null;
            }
        }

        if (createFile(modelElement, f)) {
            LOG.debug("Creating " + f.getPath() + " successfull");

            return sFilename;
        } 
        LOG.error("Creating " + f.getPath() + " failed");
        
        return null;
    }

    private  String getName() {
        return "PHP" + iLanguageMajorVersion;
    }
    

    // -------------------------------------------------------------------------

    /**
     * Converts a type model element to a PHP type
     *
     * @param modelElement The model element to convert to a PHP type.
     *
     * @return The PHP type converted from the model element.
     */
    protected final String convertType(Object modelElement) {
        String sName = Model.getFacade().getName(modelElement).trim();

        if (sName.equals("void")) {
            return null;
        }

        if (sName.equals("char")) {
            return "string";
        }

        if (sName.equals("boolean")) {
            return "bool";
        }
        if (sName.equals("bool")) {
            return "bool";
        }

        if (sName.equals("int")) {
            return "int";
        }
        if (sName.equals("byte")) {
            return "int";
        }
        if (sName.equals("short")) {
            return "int";
        }
        if (sName.equals("long")) {
            return "int";
        }

        if (sName.equals("float")) {
            return "float";
        }
        if (sName.equals("double")) {
            return "float";
        }

        /* user defined type string, not (java.lang.)String */
        if (sName.equals("string")) {
            return "string";
        }

        /* user defined type array */
        if (sName.equals("array")) {
            return "array";
        }

        return null;
    }

    /*
     * Generates the default value for a type
     *
     * @param modelElement classifier representing a type
     * @param sDefault     default value
     * @param bCast        prefix value with cast statement
     *
     * @return default value with explicite cast (if needed)
     */
    private final String generateDefaultValue(Object modelElement,
            String sDefault, boolean bCast) {
        if (modelElement == null) {
            return null;
        }

        if (!Model.getFacade().isAClassifier(modelElement)) {
            throw new ClassCastException(modelElement.getClass()
                    + " has wrong object type, Classifier required");
        }

        String sType = convertType(modelElement);
        if (sType == null) {
            return "null";
        } else if (sType.equals("string")) {
            String sReturn = bCast ? "(string) " : "";
            if (sDefault != null) {
                int iFirstApos = sDefault.indexOf("'");
                while (iFirstApos != -1) {
                    sDefault = sDefault.substring(0, iFirstApos)
                            + "\\" + sDefault.substring(iFirstApos);
                    iFirstApos = sDefault.indexOf("'", iFirstApos + 2);
                }
                return sReturn + "'" + sDefault + "'";
            }
            return sReturn + "''";
        } else if (sType.equals("bool")) {
            String sReturn = bCast ? "(bool) " : "";
            if (sDefault != null) {
            	sDefault = sDefault.trim();
                if (sDefault.length() > 0) {
                    if ("0".equals(sDefault)) {
                        return sReturn + "false";
                    } else if ("false".equals(sDefault)) {
			return sReturn + "false";
		    } else {
			return sReturn + "true";
		    }
                }
                return sReturn + "false";
            } 
            return sReturn + "false";
        } else if (sType.equals("int")) {
            String sReturn = bCast ? "(int) " : "";
            if (sDefault != null && sDefault.trim().length() > 0) {
                return sReturn + sDefault.trim();
            }
            return sReturn + String.valueOf(0);
        } else if (sType.equals("float")) {
            String sReturn = bCast ? "(float) " : "";
            if (sDefault != null && sDefault.trim().length() > 0) {
                return sReturn + sDefault.trim();
            }
            return sReturn + "0.0";
        } else if (sType.equals("array")) {
            if (sDefault != null && !"".equals(sDefault.trim())) {
                return "array(" + sDefault + ")";
            }
            return "array()";
        }

        return "null";
    }

    /*
     * Generates section for an operation element
     *
     * @param modelElement The model element to generate the section for.
     *
     * @return Generated section code for the model element.
     */
    private String generateSection(Object modelElement) {
        return generateSection(modelElement, INDENT, null);
    }

    /*
     * Generates section
     *
     * @param modelElement The model element to generate the section for.
     * @param sIndent      String to indent every section block line with.
     * @param sSuffix      Section identifier suffix
     *
     * @return Generated section code for the model element.
     */
    private String generateSection(Object modelElement, String sIndent,
                                   String sSuffix) {
        String uuid = UUIDHelper.getUUID(modelElement);

        if (sSuffix != null && sSuffix.trim() != "") {
            return Section.generate(uuid + "-" + sSuffix.trim(), sIndent);
        }
        return Section.generate(uuid, sIndent);
    }

    /*
     * Creates new source file.
     *
     * @param modelElement The class or interface.
     * @param file         The file object to write to.
     *
     * @return <code>true</code> on success,
     *         <code>false</code> otherwise;
     */
    private boolean createFile(Object modelElement, File file) {
        if (!Model.getFacade().isAClassifier(modelElement)) {
            throw new ClassCastException(modelElement.getClass()
                    + " has wrong object type, Classifier required");
        }

        LOG.info("    Generating new " + file.getPath());

        String sOutput = "<?php\n\n";

        sOutput += "error_reporting(E_ALL);\n\n";

        PHPDocumentor objPHPDoc = null;
        try {
            objPHPDoc = new PHPDocumentor(modelElement,
                    PHPDocumentor.BLOCK_TYPE_FILE);
        } catch (Exception exp) {
            LOG.error("Generating file DocBlock FAILED: "
                    + exp.getMessage());
        } finally {
            if (objPHPDoc != null) {
                try {
                    objPHPDoc.setFilename(NameGenerator
                        .generateFilename(modelElement, iLanguageMajorVersion));
                } catch (Exception exp) {
                    LOG.error("Setting filename for DocBlock FAILED: "
                            + exp.getMessage());
                } finally {
                    sOutput += objPHPDoc.toString() + "\n";
                }
            }
        }

        sOutput += "if (0 > version_compare(PHP_VERSION, '"
                + iLanguageMajorVersion + "')) {\n";
        sOutput += INDENT + "die('This file was generated for PHP "
                + iLanguageMajorVersion + "');\n" + "}\n\n";

        sOutput += generateRequired(modelElement);

        sOutput += "/* user defined includes */\n";
        sOutput += generateSection(modelElement, "", "includes") + "\n";

        sOutput += "/* user defined constants */\n";
        sOutput += generateSection(modelElement, "", "constants") + "\n";

        sOutput += generateClassifier(modelElement);
        sOutput += "\n\n?>";

        boolean bReturn = true;
        BufferedWriter bwOutput = null;
        try {
            File parentDir = new File(file.getParent());
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            bwOutput = new BufferedWriter(new FileWriter(file));
            bwOutput.write(sOutput);
        } catch (IOException exp) {
            LOG.error("    Catched IOException: " + exp
                    + ", for file " + file.getPath());
            bReturn = false;
        } finally {
            try {
                if (bwOutput != null) {
                    bwOutput.close();
                }
            } catch (IOException exp) {
                LOG.error("    Catched IOException: " + exp
                        + ", for file " + file.getPath());

                bReturn = false;
            }
        }

        return bReturn;
    }

    /*
     * Updates the output file for a model element.
     *
     * @param modelElement The model element to update file for.
     * @param fileOrig     The original (previous) output file.
     *
     * @throws Exception
     */
    private void updateFile(Object modelElement, File fileOrig)
        throws Exception {
        if (!Model.getFacade().isAClassifier(modelElement)) {
            throw new ClassCastException(modelElement.getClass()
                + " has wrong object type, Classifier required");
        }

        objSection = new Section();

        File fileNew    = new File(fileOrig.getAbsolutePath() + ".out");
        File fileBackup = new File(fileOrig.getAbsolutePath() + ".bak");

        LOG.debug("    Parsing sections from " + fileOrig.getPath());
        objSection.read(fileOrig.getAbsolutePath());

        if (fileBackup.exists()) {
            LOG.debug("    Delete (old) backup " + fileBackup.getPath());
            fileBackup.delete();
        }

        LOG.debug("    Backup " + fileOrig.getPath() + " to "
                + fileBackup.getPath());
        fileOrig.renameTo(fileBackup);

        if (this.createFile(modelElement, fileOrig)) {
            LOG.debug("    Merging sections into " + fileNew.getPath());
            objSection.write(fileOrig.getAbsolutePath(), INDENT, true);

            LOG.debug("    Renaming " + fileNew.getPath()
                    + " to " + fileOrig.getPath());
            fileOrig.delete();
            fileNew.renameTo(fileOrig);
        } else {
            if (fileBackup.exists()) {
                LOG.debug("    Renaming (restore) " + fileBackup.getPath()
                        + " to " + fileOrig.getPath());
                fileBackup.renameTo(fileOrig);
            }

            LOG.error("    Updating " + fileOrig.getPath() + " failed");
        }
    }

    /*
     * Generates classifier attributes
     *
     * @param modelElement classifier
     *
     * @return source code for class attributes
     */
    private String generateClassifierAttributes(Object modelElement) {
        if (!Model.getFacade().isAClassifier(modelElement)) {
            throw new ClassCastException(modelElement.getClass()
                    + " has wrong object type, Classifier required");
        }

        String sClsAttr = "";

        if (Model.getFacade().isAClass(modelElement)) {
            sClsAttr += INDENT + "// --- ATTRIBUTES ---\n";

            Collection colAttributes =
                Model.getFacade().getAttributes(modelElement);

            if (colAttributes != null) {
                Iterator itAttributes = colAttributes.iterator();
                while (itAttributes.hasNext()) {
                    Object attr = itAttributes.next();

                    sClsAttr += "\n";

                    PHPDocumentor objPHPDoc = null;
                    try {
                        objPHPDoc = new PHPDocumentor(attr);
                    } catch (Exception exp) {
                        LOG.error("Generating attribute DocBlock FAILED: "
                                + exp.getMessage());
                    } finally {
                        if (objPHPDoc != null) {
                            sClsAttr += objPHPDoc.toString(INDENT);
                        }
                    }

                    sClsAttr += INDENT + generateAttribute(attr, false) + "\n";
                }
            }

            sClsAttr += "\n";
        }

        return sClsAttr;
    }

    /*
     * Generates classifier generalisations
     *
     * @param modelElement classifier
     *
     * @return source code for extends part of class declaration
     */
    private String generateClassifierGeneralisations(Object modelElement) {
        if (!Model.getFacade().isAClassifier(modelElement)) {
            throw new ClassCastException(modelElement.getClass()
                    + " has wrong object type, Classifier required");
        }

        String sClsGen = "";

        Collection colGeneralizations =
            Model.getFacade().getGeneralizations(modelElement);
        if (colGeneralizations != null) {
            Iterator itGen = colGeneralizations.iterator();
            if (itGen.hasNext()) {
                if (colGeneralizations.size() == 1) {
                    sClsGen += INDENT + "extends ";
                } else {
                    sClsGen += INDENT + "/* multiple generalisations not"
                            + " supported by PHP: */\n";
                    sClsGen += INDENT + "/* extends ";
                }

                while (itGen.hasNext()) {
                    Object elmGen = Model.getFacade().getParent(itGen.next());
                    if (elmGen != null) {
                        sClsGen += NameGenerator.generate(elmGen,
                                iLanguageMajorVersion);
                        if (itGen.hasNext()) {
                            sClsGen += ",\n" + INDENT + "        ";
                        }
                    }
                }

                if (colGeneralizations.size() > 1) {
                    sClsGen += " */";
                }

                sClsGen += "\n";
            }
        }

        return sClsGen;
    }

    /*
     * Generates classifier operations
     *
     * @param modelElement classifier
     *
     * @return source code for all class methods
     */
    private String generateClassifierOperations(Object modelElement) {
        if (!Model.getFacade().isAClassifier(modelElement)) {
            throw new ClassCastException(modelElement.getClass()
                    + " has wrong object type, Classifier required");
        }

        String sClsOp = "";

        sClsOp += INDENT + "// --- OPERATIONS ---\n";

        /* generate constructor */
        Object objTaggedValue =
            Model.getFacade().getTaggedValue(modelElement, "constructor");
        if (objTaggedValue != null) {
            String sTaggedValueConstructor =
                Model.getFacade().getValueOfTag(objTaggedValue);

            if (sTaggedValueConstructor != null
                    && sTaggedValueConstructor.equals("true")) {
                if (findConstructor(modelElement) == null) {
                    String sConstructor = null;

                    if (iLanguageMajorVersion < 5) {
                        sConstructor = "function " + NameGenerator.generate(
                                modelElement, iLanguageMajorVersion);
                    } else {
                        sConstructor = "public function __construct";
                    }

                    sClsOp += "\n";
                    sClsOp += INDENT + "/**\n";
                    sClsOp += INDENT + " * Class constructor\n";
                    sClsOp += INDENT + " *\n";
                    sClsOp += INDENT + " * @access public\n";
                    sClsOp += INDENT + " *\n";
                    sClsOp += INDENT + " * @return void\n";
                    sClsOp += INDENT + " *\n";
                    sClsOp += INDENT + " * @author ArgoUML "
                            + ArgoVersion.getVersion() + "\n";
                    sClsOp += INDENT + " */\n";

                    sClsOp += INDENT + sConstructor + "()\n";
                    sClsOp += INDENT + "{\n";
                    sClsOp += generateSection(modelElement, INDENT,
                        sConstructor.substring(sConstructor.lastIndexOf(" ")));
                    sClsOp += INDENT + "}\n";
                }
            }
        }

        if (Model.getFacade().isAClass(modelElement)) {
            Collection colSpec = 
                Model.getFacade().getSpecifications(modelElement);
            if (colSpec != null) {
                Iterator itSpec = colSpec.iterator();
                while (itSpec.hasNext()) {
                    Collection colIfOps =
                        Model.getFacade().getOperations(itSpec.next());
                    if (colIfOps != null) {
                        Iterator itIfOps = colIfOps.iterator();
                        while (itIfOps.hasNext()) {
                            Object op = itIfOps.next();
                            sClsOp += "\n";

                            PHPDocumentor objPHPDoc = null;
                            try {
                                objPHPDoc = new PHPDocumentor(op);
                            } catch (Exception exp) {
                                LOG.error("Generating operation DocBlock "
                                        + "FAILED: " + exp.getMessage());
                            } finally {
                                if (objPHPDoc != null) {
                                    sClsOp += objPHPDoc.toString(INDENT);
                                }
                            }

                            sClsOp += INDENT + generateOperation(op, false);
                            sClsOp += generateMethodBody(op, true);
                        }
                    }
                }
            }
        }

        Collection colOperations =
            Model.getFacade().getOperations(modelElement);
        if (colOperations != null) {
            Iterator itOperations = colOperations.iterator();
            while (itOperations.hasNext()) {
                Object op = itOperations.next();

                sClsOp += "\n";
                PHPDocumentor objPHPDoc = null;
                try {
                    objPHPDoc = new PHPDocumentor(op);
                } catch (Exception exp) {
                    LOG.error("Generating operation DocBlock FAILED: "
                            + exp.getMessage());
                } finally {
                    if (objPHPDoc != null) {
                        sClsOp += objPHPDoc.toString(INDENT);
                    }
                }

                sClsOp += INDENT + generateOperation(op, false);

                if (Model.getFacade().isAClass(modelElement)) {
                    sClsOp += generateMethodBody(op, false);
                } else {
                    if (iLanguageMajorVersion < 5) {
                        sClsOp += "\n" + INDENT + "{\n";
                        sClsOp += INDENT + INDENT
                                + "die('abstract method called');\n";
                        sClsOp += INDENT + "}\n";
                    } else {
                        sClsOp += ";\n";
                    }
                }
            }
        }

        return sClsOp;
    }

    /*
     * Generates classifier specifications
     *
     * @param modelElement The model element to generate specifications for.
     *
     * @return source code for implements part of class declaration
     */
    private String generateClassifierSpecifications(Object modelElement) {
        if (!Model.getFacade().isAClassifier(modelElement)) {
            throw new ClassCastException(modelElement.getClass()
                    + " has wrong object type, Classifier required");
        }

        String sClsSpec = "";

        if (Model.getFacade().isAClass(modelElement)) {
            Collection colSpecifications =
                    Model.getFacade().getSpecifications(modelElement);
            if (colSpecifications != null) {
                Iterator itSpecifications = colSpecifications.iterator();
                if (itSpecifications.hasNext()) {
                    if (iLanguageMajorVersion < 5) {
                        sClsSpec += INDENT + INDENT + "/* specifications are "
                                + "not supported by PHP versions before 5.0 "
                                + "*/\n";
                    }
                    sClsSpec += INDENT + INDENT;
                    if (iLanguageMajorVersion < 5) {
                        sClsSpec += "/* ";
                    }
                    sClsSpec += "implements ";

                    while (itSpecifications.hasNext()) {
                        Object ifSpecification = itSpecifications.next();
                        sClsSpec += NameGenerator.generate(ifSpecification,
                                iLanguageMajorVersion);

                        if (itSpecifications.hasNext()) {
                            sClsSpec += ",\n" + INDENT + INDENT + "           ";
                        }
                    }

                    if (iLanguageMajorVersion < 5) {
                        sClsSpec += " */";
                    }

                    sClsSpec += "\n";
                }
            }
        }

        return sClsSpec;
    }

    /*
     * Generates single require_once statement for class or interface
     *
     * @param modelElement The required class or interface.
     * @param bAddDocs     Add DocBlock before the require_once statement.
     *
     * @return single require_once statement
     */
    private String generateRequireOnceStatement(Object modelElement,
                                                boolean bAddDocs) {
        String sRequired = "";

        if (bAddDocs) {
            PHPDocumentor objPHPDoc = null;
            try {
                objPHPDoc = new PHPDocumentor(modelElement,
                        PHPDocumentor.BLOCK_TYPE_INCLUDE);
            } catch (Exception exp) {
                LOG.error("Generating include DocBlock FAILED: "
                        + exp.getMessage());
            } finally {
                if (objPHPDoc != null) {
                    sRequired += objPHPDoc.toString();
                }
            }
        }

        String sFilename = NameGenerator.generateFilename(modelElement,
                iLanguageMajorVersion);

        if (FILE_SEPARATOR != "/") {
            int iFirstFS = sFilename.indexOf(FILE_SEPARATOR);
            while (iFirstFS != -1) {
                sFilename = sFilename.substring(0, iFirstFS) + "/"
                        + sFilename.substring(iFirstFS + 1);
                iFirstFS = sFilename.indexOf(FILE_SEPARATOR, iFirstFS + 1);
            }
        }

        sRequired += "require_once('" + sFilename + "');\n";

        return sRequired;
    }

    /*
     * Generates method body for an operation element
     *
     * @param modelElement    Model element to generate body notation for.
     * @param bIgnoreAbstract Ignore abstract to generate implementations of
     *                        abstract methods.
     *
     * @return Generated body notation for model element.
     *
     * TODO: add documentation to generated source
     */
    private String generateMethodBody(Object modelElement,
                                      boolean bIgnoreAbstract) {
        if (!Model.getFacade().isAOperation(modelElement)) {
            throw new ClassCastException(modelElement.getClass()
                    + " has wrong object type, Operation required");
        }

        String sMethodBody = "";

        if (!Model.getFacade().isAbstract(modelElement) || bIgnoreAbstract) {
            sMethodBody += "\n" + INDENT + "{\n";

            Collection colParameters = 
                Model.getFacade().getParameters(modelElement);
            if (colParameters != null) {
                Iterator itParameters = colParameters.iterator();
                while (itParameters.hasNext()) {
                    Object objParameter = itParameters.next();
                    if (Model.getFacade().isReturn(objParameter)) {
                        String sReturnInit = generateDefaultValue(
                            Model.getFacade().getType(objParameter), 
                            null, true);
                        String sReturnValue = generateParameter(objParameter);

                        if (sReturnInit != null && sReturnValue.trim() != "") {
                            sMethodBody += INDENT + INDENT + "$returnValue = "
                                    + sReturnInit + ";\n\n";
                        }

                        sMethodBody += generateSection(modelElement);

                        if (sReturnValue != null && sReturnValue != "") {
                            sMethodBody += "\n" + INDENT + INDENT
                                    + sReturnValue + "\n";
                        }

                        sMethodBody += INDENT + "}\n";

                        break;
                    }
                }
            }
        } else {
            if (iLanguageMajorVersion < 5) {
                sMethodBody += "\n" + INDENT + "{\n";
                sMethodBody += INDENT + INDENT
                    + "die('abstract method called');\n";
                sMethodBody += INDENT + "}\n";
            } else {
                sMethodBody += ";\n";
            }
        }

        return sMethodBody;
    }

    /*
     * Generates all required_once statements for the class header
     *
     * @param modelElement the class
     *
     * @return source code for all required_once statements
     *
     * TODO: fix the comparator code
     */
    private String generateRequired(Object modelElement) {
        String sRequired = "";

        if (!Model.getFacade().isAClassifier(modelElement)) {
            throw new ClassCastException(modelElement.getClass()
                    + " has wrong object type, Classifier required");
        }

        TreeSet tsRequired = new TreeSet(
            new Comparator() {
                public int compare(Object obj1, Object obj2) {
                    if (obj1 != null) {
                        if (!Model.getFacade().isAClassifier(obj1)) {
                            throw new ClassCastException(obj1.getClass()
                                    + " is not comparable as classifier");
                        }
                        if (!Model.getFacade().isAClassifier(obj2)) {
                            throw new ClassCastException(obj2.getClass()
                                    + " is not comparable as classifier");
                        }

                        String sFilename1 = NameGenerator.generateFilename(obj1,
                                iLanguageMajorVersion);
                        if (sFilename1 != null) {
                            return sFilename1.compareTo(NameGenerator
                                .generateFilename(obj2, iLanguageMajorVersion));
                        }
                        return (NameGenerator.generateFilename(obj2,
                                    iLanguageMajorVersion) != null) ? -1 : 0;
                    }
                    if (obj2 != null) {
                        return -1;
                    }
                    return 0;
                }
            }
        );

        /* generalisations */
        Collection colGens = Model.getFacade().getGeneralizations(modelElement);
        if (colGens != null) {
            Iterator itGens = colGens.iterator();
            while (itGens.hasNext()) {
                Object objGen = itGens.next();
                tsRequired.add(Model.getFacade().getParent(objGen));
            }
        }

        /* association ends */
        Collection colAssocEnds = 
            Model.getFacade().getAssociationEnds(modelElement);
        if (colAssocEnds != null) {
            Iterator itAssocEnds = colAssocEnds.iterator();
            while (itAssocEnds.hasNext()) {
                Object assocEnd = itAssocEnds.next();
                Object oppositeEnd = Model.getFacade().getOppositeEnd(assocEnd);
                if (Model.getFacade().isNavigable(oppositeEnd)) {
                    tsRequired.add(Model.getFacade().getType(oppositeEnd));
                }
            }
        }

        /* client dependencies */
        Collection colClientDeps =
                Model.getFacade().getClientDependencies(modelElement);
        if (colClientDeps != null) {
            Iterator itClientDeps = colClientDeps.iterator();
            while (itClientDeps.hasNext()) {
                Object dep = itClientDeps.next();
                Collection colDepSuppliers = 
                    Model.getFacade().getSuppliers(dep);
                Iterator itDepSuppliers = colDepSuppliers.iterator();
                while (itDepSuppliers.hasNext()) {
                    tsRequired.add(itDepSuppliers.next());
                }
            }
        }

        Iterator itRequired = tsRequired.iterator();
        if (itRequired != null) {
            while (itRequired.hasNext()) {
                Object objRequired = itRequired.next();
                if (!objRequired.equals(modelElement)) {
                    sRequired += generateRequireOnceStatement(objRequired,
                            true) + "\n";
                }
            }
        }

        return sRequired;
    }

    /**
     * Finds the model element that represents the class constructor
     *
     * @param modelElement The model element to find constructor for
     *
     * @return The constructor operation element;
     *         <code>null</code> otherwise;
     *
     * TODO: implement the lookup
     */
    private Object findConstructor(Object modelElement) {
        if (!Model.getFacade().isAClass(modelElement)) {
            throw new ClassCastException(modelElement.getClass()
                    + " has wrong object type, Class required");
        }

        return null;
    }

    /*
     * @see org.argouml.uml.generator.CodeGenerator#generate(java.util.Collection, boolean)
     */
    public Collection generate(Collection elements, boolean deps) {
        LOG.debug("generate() called");
        File tmpdir = null;
        try {
            tmpdir = TempFileUtils.createTempDir();
            if (tmpdir != null) {
                generateFiles(elements, tmpdir.getPath(), deps);
                return TempFileUtils.readAllFiles(tmpdir);
            }
            return Collections.EMPTY_LIST;
        } finally {
            if (tmpdir != null) {
                TempFileUtils.deleteDir(tmpdir);
            }
            LOG.debug("generate() terminated");
        }
    }

    /*
     * @see org.argouml.uml.generator.CodeGenerator#generateFiles(java.util.Collection, java.lang.String, boolean)
     */
    public Collection generateFiles(Collection elements, String path,
            boolean deps) {
        LOG.debug("generateFiles() called");
        // TODO: 'deps' is ignored here
        for (Iterator it = elements.iterator(); it.hasNext();) {
            generateFile(it.next(), path);
        }
        return TempFileUtils.readFileNames(new File(path));
    }

    /*
     * @see org.argouml.uml.generator.CodeGenerator#generateFileList(java.util.Collection, boolean)
     */
    public Collection generateFileList(Collection elements, boolean deps) {
        LOG.debug("generateFileList() called");
        // TODO: 'deps' is ignored here
        File tmpdir = null;
        try {
            tmpdir = TempFileUtils.createTempDir();
            for (Iterator it = elements.iterator(); it.hasNext();) {
                generateFile(it.next(), tmpdir.getName());
            }
            return TempFileUtils.readFileNames(tmpdir);
        } finally {
            if (tmpdir != null) {
                TempFileUtils.deleteDir(tmpdir);
            }
        }
    }

}
