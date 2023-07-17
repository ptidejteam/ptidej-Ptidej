
package rules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import net.didion.jwnl.JWNLException;
import net.sourceforge.pmd.lang.apex.ast.ASTAssignmentExpression;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTAndExpression;
import net.sourceforge.pmd.lang.java.ast.ASTBlockStatement;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;
import net.sourceforge.pmd.lang.java.ast.ASTConditionalAndExpression;
import net.sourceforge.pmd.lang.java.ast.ASTConditionalExpression;
import net.sourceforge.pmd.lang.java.ast.ASTConditionalOrExpression;
import net.sourceforge.pmd.lang.java.ast.ASTEqualityExpression;
import net.sourceforge.pmd.lang.java.ast.ASTFieldDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTIfStatement;
import net.sourceforge.pmd.lang.java.ast.ASTInclusiveOrExpression;
import net.sourceforge.pmd.lang.java.ast.ASTInstanceOfExpression;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTName;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryPrefix;
import net.sourceforge.pmd.lang.java.ast.ASTPrimarySuffix;
import net.sourceforge.pmd.lang.java.ast.ASTRelationalExpression;
import net.sourceforge.pmd.lang.java.ast.ASTReturnStatement;
import net.sourceforge.pmd.lang.java.ast.ASTShiftExpression;
import net.sourceforge.pmd.lang.java.ast.ASTSwitchStatement;
import net.sourceforge.pmd.lang.java.ast.ASTThrowStatement;
import net.sourceforge.pmd.lang.java.ast.ASTVariableDeclarator;
import net.sourceforge.pmd.lang.java.ast.ASTVariableDeclaratorId;
import net.sourceforge.pmd.lang.java.ast.Comment;
import utils.CustomDictionary;
import utils.CustomParser;
import utils.Splitter;

public class MethodsLAPDRule extends AbstractLAPDRule {

    private static String getMethodType(final ASTMethodDeclaration method) {

        return getNodeType(method);
    }

    private static String getNameFromPrefix(final ASTPrimaryPrefix node) {
        String name = null;
        // should only be 1 child, if more I need more knowledge
        if (node.jjtGetNumChildren() == 1) { // safety check
            final Node child = node.jjtGetChild(0);
            if (child instanceof ASTName) {
                // just as easy as null check and it
                // should be an ASTName anyway
                name = ((ASTName) child).getImage();
            }
        }
        return name;
    }

    private static Vector<Comment> getMethodComments(final ASTMethodDeclaration method, final List<Comment> comments) {

        return getNodeComments(method, comments);
    }

    private static List<String> getCalledMethodsNames(final ASTMethodDeclaration method) {

        final List<String> calledMethodsNames = new ArrayList<String>();
        final List<ASTPrimaryExpression> primaryExpressions = method.findDescendantsOfType(ASTPrimaryExpression.class);
        int thisIndex = -1;
        boolean superFirst = false;

        for (final ASTPrimaryExpression primaryExpression : primaryExpressions) {

            final int childNum = primaryExpression.jjtGetNumChildren();

            if (childNum > 1) {

                FIND_SUPER_OR_THIS: {
                    for (int i = 0; i < childNum; i++) {

                        final Node child = primaryExpression.jjtGetChild(i);

                        if (child instanceof ASTPrimarySuffix) {
                            final ASTPrimarySuffix psChild = (ASTPrimarySuffix) child;
                            if (psChild.getImage() == null && psChild.jjtGetNumChildren() == 0) {
                                thisIndex = i;
                                break;
                            }
                        } else if (child instanceof ASTPrimaryPrefix) {
                            final ASTPrimaryPrefix ppChild = (ASTPrimaryPrefix) child;
                            if (getNameFromPrefix(ppChild) == null) {
                                if (ppChild.getImage() == null) {
                                    thisIndex = i;
                                    break;
                                } else {
                                    superFirst = true;
                                    thisIndex = i;
                                    break;
                                }
                            }
                        }
                    }
                }
                if (thisIndex != -1) {

                    if (superFirst) {

                        FIRSTNODE: {
                            final ASTPrimaryPrefix child = (ASTPrimaryPrefix) primaryExpression.jjtGetChild(0);
                            final String name = child.getImage(); // special
                                                                  // case

                            if (childNum == 2) { // last named node = method
                                                 // name
                                calledMethodsNames.add(name);
                            }
                        }
                        OTHERNODES: {
                            for (int i = 1; i < childNum - 1; i++) {
                                final Node child = primaryExpression.jjtGetChild(i);
                                final ASTPrimarySuffix psChild = (ASTPrimarySuffix) child;

                                if (!psChild.isArguments()) {
                                    final String name = ((ASTPrimarySuffix) child).getImage();

                                    if (i == childNum - 2) { // last node
                                        calledMethodsNames.add(name);
                                    }
                                }
                            }
                        }
                    } else {
                        // FIRSTNODE: {
                        // if (thisIndex == 1) { // qualifiers in node 0
                        // ASTPrimaryPrefix child = (ASTPrimaryPrefix)
                        // primaryExpression.jjtGetChild(0);
                        // String toSplit = getNameFromPrefix(child);
                        // // System.out.println("parsing for
                        // // class/package names in : " + toParse);
                        // java.util.StringTokenizer st = new
                        // java.util.StringTokenizer(toParse, ".");
                        // while (st.hasMoreTokens()) {
                        // packagesAndClasses.add(st.nextToken());
                        // }
                        // }
                        // }
                        OTHERNODES: {
                            // other methods called in this
                            // statement are grabbed here
                            // this is at 0, then no Qualifiers
                            // this is at 1, the node 0 contains qualifiers
                            for (int i = thisIndex + 1; i < childNum - 1; i++) {
                                // everything after this is var name or method
                                // name
                                final ASTPrimarySuffix child = (ASTPrimarySuffix) primaryExpression.jjtGetChild(i);
                                if (!child.isArguments()) {
                                    // skip the () of method calls
                                    final String name = child.getImage();
                                    // System.out.println("Found suffix: " +
                                    // suffixName);
                                    if (i == childNum - 2) {
                                        calledMethodsNames.add(name);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // if no this or super found, everything is method
                    // name or variable
                    // System.out.println("no this found:");
                    FIRSTNODE: {
                        // variable names are in the prefix + the
                        // first method call [a.b.c.x()]
                        final ASTPrimaryPrefix child = (ASTPrimaryPrefix) primaryExpression.jjtGetChild(0);
                        final String toSplit = getNameFromPrefix(child);
                        // System.out.println("parsing for var names in : "
                        // + toParse);
                        final String[] splitted = toSplit.split(".");
                        // String value = splitted[splitted.length];

                        for (int i = 0; i < splitted.length - 1; i++) {
                            final String value = splitted[i];
                            if (splitted[i + 1] == null) {

                                if (childNum == 2) {
                                    // if this expression is 2
                                    // nodes long, then the last
                                    // part of prefix is method
                                    // name
                                    calledMethodsNames.add(value);
                                }

                            }
                        }

                    }
                    OTHERNODES: {
                        // other methods called in this statement
                        // are grabbed here
                        for (int i = 1; i < childNum - 1; i++) {
                            final ASTPrimarySuffix child = (ASTPrimarySuffix) primaryExpression.jjtGetChild(i);
                            if (!child.isArguments()) {
                                final String name = child.getImage();
                                if (i == childNum - 2) {
                                    calledMethodsNames.add(name);
                                }
                            }
                        }
                    }
                }
            }
        }
        return calledMethodsNames;
    }

    public Object visit(ASTMethodDeclaration method, final Object data) {

        final List<Comment> comments = method.getFirstParentOfType(ASTCompilationUnit.class).getComments();
        final String className = method.getFirstParentOfType(ASTClassOrInterfaceDeclaration.class).getImage();
        String methodName;
        String methodType;
        List<String> result = new ArrayList<String>();

        init();

        methodName = method.getMethodName().toString();
        methodType = getMethodType(method);

        try {

            // // A1
            // if (this.detectComplexGet(method, methodName, methodType)) {
            //
            // addViolationWithMessage(data, method,
            // "LAPD - A1: getX() method should not be more than an accessor." +
            // SIGNATURE,
            // new Object[] { methodName });
            //
            // }

            // A2
            if (this.detectIsMethodNotBoolean(methodName, methodType)) {

                addViolationWithMessage(data, method,
                        "LAPD - A2: isX() method should not return more than a boolean. " + SIGNATURE,
                        new Object[] { className, methodName, methodType });
            }

            // A3
            if (this.detectSetMethodReturns(method, methodName, methodType)) {

                addViolationWithMessage(data, method, "LAPD - A3: setX() method should not return." + SIGNATURE,
                        new Object[] { className, methodName, methodType });
            }

            // A4
            if (this.detectCollectionTypeSingleNameMethod(methodName, methodType)) {

                addViolationWithMessage(data, method,
                        "LAPD - A4: Expecting but not getting single instance. " + SIGNATURE,
                        new Object[] { className, methodName, methodType });
            }

            // B1
            String notImplementedCondition = this.detectNotImplementedCondition(method, methodName, methodType,
                    comments);
            if (notImplementedCondition != null) {

                addViolationWithMessage(data, method,
                        "LAPD - B1: Method comments document a not implemented condition: ''{0} [...]'' " + SIGNATURE,
                        new Object[] { className, methodName, methodType, notImplementedCondition });

            }

            // B2
            if (this.detectValidationMethodDoesNotConfirm(method, methodName, methodType)) {

                addViolationWithMessage(data, method,
                        "LAPD - B2: Method performing validation should return." + SIGNATURE,
                        new Object[] { className, methodName, methodType });
            }

            // B3
            if (this.detectGetMethodDoesNotReturn(methodName, methodType)) {

                addViolationWithMessage(data, method, "LAPD - B3: getX() method should return. " + SIGNATURE,
                        new Object[] { className, methodName, methodType });
            }

            // B4
            if (this.detectFalseBooleanMethod(methodName, methodType)) {

                addViolationWithMessage(data, method,
                        "LAPD - B4: Method name is predicate but nothing is returned. " + SIGNATURE,
                        new Object[] { className, methodName, methodType });
            }

            // B5
            if (this.detectTransformMethodDoesNotreturn(methodName, methodType)) {

                addViolationWithMessage(data, method,
                        "LAPD - B5: Method transforming an object does not return the tranformed object. " + SIGNATURE,
                        new Object[] { className, methodName, methodType });
            }

            // B6
            if (this.detectSingleTypeCollectionNameMethod(methodName, methodType)) {

                addViolationWithMessage(data, method,
                        "LAPD - B6: Method type indicates single object but the name indicates multiple objects. "
                                + SIGNATURE,
                        new Object[] { className, methodName, methodType });
            }
            
            // B7 
            if (this.detectGetMethodDoesNotReturnAttribute(method, methodName, methodType)){
                addViolationWithMessage(data, method,
                        "LAPD - B7: getX() method should return the corresponding 'X' attribute. "
                        + SIGNATURE,
                        new Object[] { className, methodName, methodType });

            }
            // C1
            result = this.detectOppositeMethodNameAndMethodType(methodName, methodType);
            if (result != null) {

                addViolationWithMessage(data, method,
                        "LAPD - C1: Method name and type use antonyms: ''{0}'' vs ''{1}''. " + SIGNATURE,
                        new Object[] { className, methodName, methodType, result.get(0), result.get(1) });
            }

            // C2
            result = this.detectOppositeCommentAndMethodSignature(method, methodName, methodType, comments);
            if (result != null) {

                addViolationWithMessage(data, method,
                        "LAPD - C2: Method comments and signature use antonyms: {0} vs {1}" + SIGNATURE,
                        new Object[] { className, methodName, methodType, result.get(0), result.get(1) });
            }

        } catch (JWNLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // }
        return data;
    }

    // TODO: improve by identifying the different POS of terms in a
    // question/predicate

    private boolean detectFalseBooleanMethod(final String methodName, final String methodType) {

        for (final String word : predicateKeywords) {
            if (methodName.startsWith(word) && "void".equalsIgnoreCase(methodType)) {
                return true;
            }
        }
        return false;
    }

    // private boolean detectComplexGet(final ASTMethodDeclaration method, final
    // String methodName,
    // final String methodType) {
    //
    // if (!methodName.startsWith("get")) {
    // return false;
    // }
    //
    // final String potentialAttribute = methodName.substring(3);
    // boolean isAccessor = false;
    // final List<ASTFieldDeclaration> attributes = method.jjtGetParent()
    // .findDescendantsOfType(ASTFieldDeclaration.class);
    //
    // for (final ASTFieldDeclaration attribute : attributes) {
    // final String attributeName =
    // attribute.getFirstDescendantOfType(ASTVariableDeclaratorId.class).getImage();
    // if (attributeName != null &&
    // attributeName.equalsIgnoreCase(potentialAttribute)
    // && getNodeType(attribute).equalsIgnoreCase(methodType)) {
    //
    // isAccessor = true;
    // break;
    // }
    // }
    // final List<ASTReturnStatement> returnsStatements =
    // method.findDescendantsOfType(ASTReturnStatement.class);
    // boolean returnsAttribute = false;
    //
    // if (isAccessor && returnsStatements != null) {
    //
    // for (final ASTReturnStatement returnStatement : returnsStatements) {
    //
    // if (!returnsAttribute &&
    // returnStatement.getFirstDescendantOfType(ASTPrimaryPrefix.class) != null)
    // {
    // ASTPrimaryPrefix primaryPrefix =
    // returnStatement.getFirstDescendantOfType(ASTPrimaryPrefix.class);
    //
    // if (primaryPrefix.toString().equalsIgnoreCase(potentialAttribute)) {
    // returnsAttribute = true;
    // break;
    // } else if (primaryPrefix.toString().equalsIgnoreCase("this")) {
    // if
    // (primaryPrefix.jjtGetParent().getFirstChildOfType(ASTPrimarySuffix.class)
    // != null) {
    // final ASTPrimarySuffix primarySuffix = primaryPrefix.jjtGetParent()
    // .getFirstChildOfType(ASTPrimarySuffix.class);
    //
    // if (primarySuffix.toString().equalsIgnoreCase(potentialAttribute)) {
    // returnsAttribute = true;
    // break;
    // }
    // }
    // }
    // }
    // }
    // }
    //
    // if (!returnsAttribute) {
    // return false;
    // }
    //
    // return false;
    // }

    // B1
    private String detectNotImplementedCondition(final ASTMethodDeclaration method, final String methodName,
            final String methodType, final List<Comment> comments) {

        if (method.hasDescendantOfType(ASTBlockStatement.class)) {

            List<Comment> methodComments = getMethodComments(method, comments);

            if (methodComments != null) {

                String methodCommentString = toString(methodComments);

                for (final String word : ignoreForConditionKeywords) {
                    // replace keywords to ignore for condition
                    methodCommentString = methodCommentString.replaceAll(word, "");
                }

                final Vector<String> splittedMethodComment = Splitter.commentSplitter(methodCommentString);

                int index = splittedMethodComment.indexOf("if");

                if (index != -1) {

                    if (!method.hasDecendantOfAnyType(ASTIfStatement.class, ASTSwitchStatement.class)
                            && getCalledMethodsNames(method).size() == 0) {
                        if (!method.hasDecendantOfAnyType(ASTEqualityExpression.class, ASTRelationalExpression.class,
                                ASTShiftExpression.class, ASTConditionalOrExpression.class,
                                ASTInclusiveOrExpression.class, ASTConditionalAndExpression.class,
                                ASTAndExpression.class, ASTAssignmentExpression.class, ASTConditionalExpression.class,
                                ASTInstanceOfExpression.class)) {

                            String result = splittedMethodComment.get(index);
                            int i = 0;
                            while (splittedMethodComment.get(index) + 1 != null && i < 2) {
                                index++;
                                result = result + " " + splittedMethodComment.get(index);
                                i++;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    // A2
    private boolean detectIsMethodNotBoolean(final String methodName, final String methodType) {

        if (methodName.startsWith("is") && !methodType.equalsIgnoreCase("boolean")) {
            return true;
        }
        return false;
    }

    // A3
    private boolean detectSetMethodReturns(final ASTMethodDeclaration method, final String methodName,
            final String methodType) {

        if (!"void".equals(methodType) && !"boolean".equals(methodType) && !"status".contains(methodType)) {

            if (methodName.startsWith("set")) {

                /* if the method returns 'this' then it's OK */
                if (method.jjtGetParent().jjtGetParent().jjtGetParent().getImage().equalsIgnoreCase(methodType)) {
                    return false;
                }
                /* if the method returns an other documented type it is OK */

                if (method.comment() != null) {
                    if (Splitter.commentSplitter(method.comment().toString()).contains("return")) {
                        return false;
                    }
                }

                /* check if the method modifies an attribute */
                final List<ASTFieldDeclaration> attributes = method.jjtGetParent()
                        .findDescendantsOfType(ASTFieldDeclaration.class);
                boolean isModifier = false; // will be true if modification
                                            // occurs to an attribute
                final String potentialAttribute = methodName.substring(3);

                for (final ASTFieldDeclaration attribute : attributes) {
                    if (attribute.getVariableName().equalsIgnoreCase(potentialAttribute)) {
                        isModifier = true;
                        break;
                    }
                }

                if (isModifier) {
                    /*
                     * check if it returns only the modified attribute, then it
                     * is OK.
                     */
                    final List<ASTReturnStatement> returnStatements = method
                            .findDescendantsOfType(ASTReturnStatement.class);
                    if (returnStatements != null) {
                        for (final ASTReturnStatement returnStatement : returnStatements) {
                            if (returnStatement.jjtGetLastToken().toString().equalsIgnoreCase(potentialAttribute)) {
                                return false;
                            }
                        }
                    }
                    return true;
                }
                return true;
            }
        }
        return false;
    }

    // A4
    private boolean detectCollectionTypeSingleNameMethod(final String methodName, final String methodType) {

        if (isCollection(methodType) && !isCollection(methodName)) {

            final Vector<String> splittedMethodName = Splitter.methodSplitter(methodName);
            final String lastPOS = CustomParser.getPOS(splittedMethodName.lastElement(), splittedMethodName);

            if (lastPOS != null && "NN".equalsIgnoreCase(lastPOS) && !splittedMethodName.lastElement().endsWith("s")
                    && !CustomParser.hasPOS("NNS", splittedMethodName)) {

                return true;
            }
        }
        return false;
    }

    // B2
    private boolean detectValidationMethodDoesNotConfirm(final ASTMethodDeclaration method, final String methodName,
            final String methodType) {

        String exceptions = "";
        final ASTThrowStatement methodException = method.getFirstDescendantOfType(ASTThrowStatement.class);

        if (methodException != null && methodException.jjtGetNumChildren() > 0) {

            exceptions = methodException.jjtGetChild(0).getImage();
        }

        for (final String word : validatingKeywords) {

            if (methodName.startsWith(word)) {

                if ("void".equalsIgnoreCase(methodType) && (exceptions == null || "".equals(exceptions))
                        && method.findDescendantsOfType(ASTThrowStatement.class).size() == 0) {

                    // check if the method contains calls to Assert
                    boolean callsAssert = false;
                    final List<String> methodCalls = getCalledMethodsNames(method);

                    for (final String calledMethod : methodCalls) {

                        if (calledMethod.startsWith("assert")) {
                            callsAssert = true;
                            break;
                        }
                    }
                    if (!callsAssert) {
                        return true;
                    }

                }
            }
        }
        return false;
    }

    // B3
    private boolean detectGetMethodDoesNotReturn(final String methodName, final String methodType) {

        for (final String word : returningKeywords) {
            if (methodName.startsWith(word)) {
                if ("void".equalsIgnoreCase(methodType)) {
                    return true;
                }
            }
        }
        return false;
    }

    // B5
    private boolean detectTransformMethodDoesNotreturn(final String methodName, final String methodType) {

        boolean result = false;

        if ("void".equalsIgnoreCase(methodType) && !"test".startsWith(methodName)) {

            final Vector<String> methodNameVector = Splitter.methodSplitter(methodName);
            final String firstElement = methodNameVector.firstElement();

            if ("to".equalsIgnoreCase(firstElement)
                    && (!methodNameVector.get(methodNameVector.indexOf(firstElement + 1)).equalsIgnoreCase("do")
                            || methodNameVector.size() == 1 && !methodNameVector
                                    .get(methodNameVector.indexOf(firstElement) + 1).equalsIgnoreCase("be"))) {

                result = true;
            } else if (!"to".equalsIgnoreCase(firstElement) && !methodName.endsWith("To")) {

                if (!CustomParser.hasPOSInAnyForm("VB", methodNameVector) && (methodNameVector.contains("to")
                        || methodNameVector.size() > 1 && methodName.toLowerCase().matches("[a-z]+2[a-z]+"))) {

                    result = true;
                }

            }
        }
        return result;
    }

    // B6
    private boolean detectSingleTypeCollectionNameMethod(final String methodName, final String methodType) {

        if (isReturning(methodName) && !isCollection(methodType)) {

            final Vector<String> splittedMethodName = Splitter.methodSplitter(methodName);
            final String lastPOS = CustomParser.getPOS(splittedMethodName.lastElement(), splittedMethodName);

            if (splittedMethodName.lastElement().length() > 3) {
                if (lastPOS != null && lastPOS.equalsIgnoreCase("NNS")) {

                    return true;
                }
            }
        }
        return false;
    }
    
    //B7
    private boolean detectGetMethodDoesNotReturnAttribute(final ASTMethodDeclaration method, final String methodName, final String methodType){
        if (!methodName.startsWith("get")){
            return false;
        }
        
        final String potentialAttribute = methodName.substring(3);
        boolean isAccessor = false;
        
        final List<ASTVariableDeclarator> variables = method.findDescendantsOfType(ASTVariableDeclarator.class);
        for (ASTVariableDeclarator variable : variables){
            if (variable.getFirstChildOfType(ASTVariableDeclaratorId.class).getImage().equalsIgnoreCase(potentialAttribute)
                    && getNodeType(variable).equalsIgnoreCase(methodType)){
                isAccessor = true;
                break;
            }
        }
        
        if (isAccessor){
            List<ASTReturnStatement> returnStatements = method.findDescendantsOfType(ASTReturnStatement.class);
            
            if (returnStatements != null){
                for (ASTReturnStatement returnStatement : returnStatements){
                    if (!returnStatement.jjtGetLastToken().toString().equalsIgnoreCase(potentialAttribute)){
                        return true;
                    }
                    
                }
                        
            }
        }
        return false;
    }
            


    // C1
    private List<String> detectOppositeMethodNameAndMethodType(final String methodName, final String methodType)
            throws JWNLException {

        final Vector<String> splittedMethodName = Splitter.methodSplitter(methodName);
        final Vector<String> splittedMethodType = Splitter.methodSplitter(methodType);
        List<String> result = new ArrayList<>();

        for (final String termInName : splittedMethodName) {

            if (!shouldBeIgnoredFromAntonyms(termInName)) {

                for (final String termInType : splittedMethodType) {

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

    // C2
    private List<String> detectOppositeCommentAndMethodSignature(final ASTMethodDeclaration method,
            final String methodName, final String methodType, final List<Comment> comments) throws JWNLException {

        final List<String> result = new ArrayList<String>();

        final String methodComments = toString(getMethodComments(method, comments));
        final Vector<String> splittedMethodName = Splitter.methodSplitter(methodName);
        final Vector<String> splittedMethodType = Splitter.methodSplitter(methodType);
        final Set<String> splittedMethodSignature = new HashSet<String>();

        splittedMethodSignature.addAll(splittedMethodType);
        splittedMethodSignature.addAll(splittedMethodName);

        if ("".equals(methodComments) && methodComments != null) {

            final Vector<String> splittedMethodComments = Splitter.commentSplitter(methodComments);

            for (final String commentWord : splittedMethodComments) {

                if (!shouldBeIgnoredFromAntonyms(commentWord)) {

                    for (final String signatureWord : splittedMethodSignature) {

                        if (!shouldBeIgnoredFromAntonyms(signatureWord)
                                && CustomDictionary.haveAntonyms(CustomDictionary.stringToIndexWordSet(commentWord),
                                        CustomDictionary.stringToIndexWordSet(signatureWord))
                                && !commentWord.equalsIgnoreCase(signatureWord)) {

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
