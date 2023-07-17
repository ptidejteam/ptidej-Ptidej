
package rules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.POS;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceBodyDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;
import net.sourceforge.pmd.lang.java.ast.ASTType;
import net.sourceforge.pmd.lang.java.ast.ASTVariableDeclarator;
import net.sourceforge.pmd.lang.java.ast.ASTVariableDeclaratorId;
import net.sourceforge.pmd.lang.java.ast.Comment;
import net.sourceforge.pmd.lang.rule.properties.BooleanProperty;
import utils.CustomDictionary;
import utils.CustomParser;
import utils.Splitter;

public class AttributesLAPDRule extends AbstractLAPDRule {

    private boolean collectionTypeSingleNameAttribute;
    private boolean booleanAttributeNameNotType;
    private boolean singleTypeCollectionNameAttribute;
    private boolean oppositeAttributeNameAndAttributeType;

    private static final BooleanProperty COLLECTION_TYPE_SINGLE_NAME_ATTRIBUTE_DESCRIPTOR = new BooleanProperty(
            "collectionTypeSingleNameAttribute", "Checks attributes whose type is collection and name is single object",
            true, 1.0f);

    private static final BooleanProperty BOOLEAN_ATTRIBUTE_NAME_NOT_TYPE = new BooleanProperty(
            "booleanAttributeNameNotType", "Checks attributes whose name suggests boolean but type isn't", true, 2.0f);

    private static final BooleanProperty SINGLE_TYPE_COLLECTION_NAME_ATTRIBUTE = new BooleanProperty(
            "singleTypeCollectionNameAttribute",
            "Checks attributes whose type is single instance and name is collection object", true, 3.0f);

    private static final BooleanProperty OPPOSITE_ATTRIBUTE_NAME_AND_ATTRIBUTE_TYPE = new BooleanProperty(
            "oppositeAttributeNameAndAttributeType",
            "Checks antonym relations between terms in attribute type and attribute name", true, 4.0f);

    public AttributesLAPDRule() {
        definePropertyDescriptor(COLLECTION_TYPE_SINGLE_NAME_ATTRIBUTE_DESCRIPTOR);
        definePropertyDescriptor(BOOLEAN_ATTRIBUTE_NAME_NOT_TYPE);
        definePropertyDescriptor(SINGLE_TYPE_COLLECTION_NAME_ATTRIBUTE);
        definePropertyDescriptor(OPPOSITE_ATTRIBUTE_NAME_AND_ATTRIBUTE_TYPE);
        
    }

    @Override
    public void start(RuleContext ctx) {
        collectionTypeSingleNameAttribute = getProperty(COLLECTION_TYPE_SINGLE_NAME_ATTRIBUTE_DESCRIPTOR);
        booleanAttributeNameNotType = getProperty(BOOLEAN_ATTRIBUTE_NAME_NOT_TYPE);
    }

    private static Vector<Comment> getVariableComments(final ASTVariableDeclarator variable, List<Comment> comments) {
        return getNodeComments(variable, comments);
    }

    private static String getVariableType(final ASTVariableDeclarator variable) {

        return getNodeType(variable);
    }

    public Object visit(ASTVariableDeclarator variable, Object data) {

        String variableName;
        String variableType;
        final String className = variable.getFirstParentOfType(ASTClassOrInterfaceDeclaration.class).getImage();
        final List<Comment> comments = variable.getFirstParentOfType(ASTCompilationUnit.class).getComments();
        List<String> result = new ArrayList<String>();

        init();

        variableType = getVariableType(variable);
        variableName = variable.getFirstChildOfType(ASTVariableDeclaratorId.class).getImage();

        try {

            // D1
            if (detectCollectionTypeSingleNameAttribute(variableName, variableType)) {

                addViolationWithMessage(data, variable,
                        "LAPD - D1: Attribute type suggests multiple objects but the name suggests single objects. "
                                + SIGNATURE,
                        new Object[] { className, variableName, variableType });
            }

            // D2
            if (detectBooleanAttributeNameNotType(variableName, variableType)) {

                addViolationWithMessage(data, variable,
                        "LAPD - D2: Attribute name is predicate but type is not Boolean. " + SIGNATURE,
                        new Object[] { className, variableName, variableType });
            }

            // E1
            if (detectSingleTypeCollectionNameAttribute(variableName, variableType)) {

                addViolationWithMessage(data, variable,
                        "LAPD - E1: Attribute type suggests single object but the name suggests multiple objects. "
                                + SIGNATURE,
                        new Object[] { className, variableName, variableType });
            }

            // F1
            result = detectOppositeAttributeNameAndAttributeType(variableName, variableType);
            if (result != null) {

                addViolationWithMessage(data, variable,
                        "LAPD - F1: Antonym relation between '{3}' in attribute name and '{4}' in attribute type. " + SIGNATURE,
                        new Object[] { className, variableName, variableType, result.get(0), result.get(1) });
            }

            // F2
            result = detectOppositeCommentAndAttributeSignature(variable, variableName, variableType, comments);
            if (result != null) {

                addViolationWithMessage(data, variable,
                        "LAPD - F2: Antonym relation between variable signature and comment term: "
                                + "''{3}'' in comments is an antonym of ''{4}''." + SIGNATURE,
                        new Object[] { className, variableName, variableType, result.get(0), result.get(1) });
            }

        } catch (JWNLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return data;

    }

    // D1
    private boolean detectCollectionTypeSingleNameAttribute(final String variableName, final String variableType) {

        if (isCollection(variableType) && !isCollection(variableName)) {

            final Vector<String> splittedVariableName = Splitter.variableSplitter(variableName);

            if (splittedVariableName.size() > 0) {

                final String lastPOS = CustomParser.getPOS(splittedVariableName.lastElement(), splittedVariableName);

                if (lastPOS != null && lastPOS.equalsIgnoreCase("NN")
                        && !splittedVariableName.lastElement().endsWith("s")
                        && !CustomParser.hasPOS("NNS", splittedVariableName)) {

                    return true;
                }
            }
        }
        return false;
    }

    // D2
    private boolean detectBooleanAttributeNameNotType(final String variableName, final String variableType)
            throws JWNLException {

        if (!variableType.equalsIgnoreCase("boolean")) {

            final Vector<String> splittedVariableName = Splitter.variableSplitter(variableName);

            if (splittedVariableName.size() > 0) {

                final String firstPOS = CustomParser.getPOS(splittedVariableName.firstElement(), splittedVariableName);
                final String lastPOS = CustomParser.getPOS(splittedVariableName.lastElement(), splittedVariableName);

                if ("VBG".equalsIgnoreCase(lastPOS)
                        && !CustomDictionary.hasIndexWordForPos(splittedVariableName.lastElement(), POS.NOUN)
                        || firstPOS != null && "VBZ".equalsIgnoreCase(firstPOS) && !CustomDictionary
                                .hasIndexWordForPos(splittedVariableName.firstElement(), POS.NOUN)) {

                    return true;
                }
            }
            return false;
        }
        return false;
    }

    // E1
    private boolean detectSingleTypeCollectionNameAttribute(final String variableName, final String variableType) {

        if (!isCollection(variableType) && !isCollection(variableName)) {

            final Vector<String> splittedVariableName = Splitter.variableSplitter(variableName);
            final Vector<String> splittedVariableType = Splitter.variableSplitter(variableType);

            if (splittedVariableName.size() > 0 && splittedVariableType.size() > 0) {

                for (final String termInType : splittedVariableType) {
                    if (containsCollectionKeyword(termInType, true)) {
                        return false;
                    }
                }

                for (final String termInName : splittedVariableName) {
                    if (isAggregation(termInName)) {
                        return false;
                    }
                }

                if (!CustomParser.hasPOS("NNS", splittedVariableType)
                        && CustomParser.hasPOS("NNS", splittedVariableName)
                        && splittedVariableName.lastElement().endsWith("s") && !CustomParser
                                .getPOS(splittedVariableName.firstElement(), splittedVariableName).startsWith("VB")) {

                    return true;
                }
            }
            return false;
        }
        return false;
    }

    // F1
    private List<String> detectOppositeAttributeNameAndAttributeType(final String variableName, final String variableType)
            throws JWNLException {
        
        List<String> result = new ArrayList<String>();

        final Vector<String> splittedVariableType = Splitter.variableSplitter(variableType);
        final Vector<String> splittedVariableName = Splitter.variableSplitter(variableName);

        for (final String termInName : splittedVariableName) {

            if (!shouldBeIgnoredFromAntonyms(termInName)) {

                for (final String termInType : splittedVariableType) {

                    if (!shouldBeIgnoredFromAntonyms(termInType)
                            && CustomDictionary.haveAntonyms(CustomDictionary.stringToIndexWordSet(termInName),
                                    CustomDictionary.stringToIndexWordSet(termInType))) {
                        
                        result.add(termInName);
                        result.add(termInType);

                        return result;
                    }

                }
            }
        }
        return null;
    }

    // F2
    private List<String> detectOppositeCommentAndAttributeSignature(final ASTVariableDeclarator variable,
            final String variableName, final String variableType, List<Comment> comments) throws JWNLException {

        final List<String> result = new ArrayList<String>();
        final String variableComments = toString(getVariableComments(variable, comments));
        final Vector<String> splittedVariableName = Splitter.variableSplitter(variableName);
        final Vector<String> splittedVariableType = Splitter.variableSplitter(variableType);
        final Set<String> splittedVariableSignature = new HashSet<String>();

        splittedVariableSignature.addAll(splittedVariableType);
        splittedVariableSignature.addAll(splittedVariableName);

        if (!variableComments.equals("") && variableComments != null) {

            final Vector<String> splittedVariableComments = Splitter.commentSplitter(variableComments);

            for (final String commentWord : splittedVariableComments) {

                if (!shouldBeIgnoredFromAntonyms(commentWord)) {

                    for (final String signatureWord : splittedVariableSignature) {

                        if (CustomDictionary.haveAntonyms(CustomDictionary.stringToIndexWordSet(commentWord),
                                CustomDictionary.stringToIndexWordSet(signatureWord))
                                && !commentWord.equalsIgnoreCase(signatureWord)
                                && !shouldBeIgnoredFromAntonyms(signatureWord)) {

                            result.add(commentWord);
                            result.add(signatureWord);
                            return result;

                        }
                    }
                }
            }
        }
        return null;
    }

}
