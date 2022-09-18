package demo.codeanalyzer.common.rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import javax.lang.model.element.TypeElement;

import demo.codeanalyzer.common.model.ErrorDescription;
import demo.codeanalyzer.common.model.ClassFile;
import demo.codeanalyzer.common.model.ClassModelMap;
import demo.codeanalyzer.common.model.ErrorDescription;
import demo.codeanalyzer.common.util.CodeAnalyzerUtil;

/**
 * RulesEngine encapsulates the details of Rules used for verificaion. This
 * class load the rules defined in <i>rules.xml</i> file and then invokes the
 * appropriate rule class for executing these rules.
 * 
 * @author Seema Richard (Seema.Richard@ust-global.com)
 * @author Deepa Sobhana (Deepa.Sobhana@ust-global.com)
 */
public class RulesEngine {

    /**
     * List of problems found in the source file
     */
    private Collection<ErrorDescription> problemsFound = new ArrayList<ErrorDescription>();

    /**
     * Method to get the details of rules used for verification and to invoke
     * the appropriate rule class to execute these rules.
     * 
     * @param javaClass
     *            The element to process
     */
    public void fireRules(String className) {

        ClassFile clazzInfo = ClassModelMap.getInstance().getClassInfo(className);
        for (JavaCodeRule rule : getRules()) {
                // apply class-level rules
                Collection<ErrorDescription> problems = rule.execute(clazzInfo);
                if (problems != null) {
                    problemsFound.addAll(problems);
                }
            }
    }

   

    /**
     * Retrieve the rule details based on the module-name and classtype
     * 
     * @param ctx
     *            The ProblemContext containing the module-name and classtype
     *            details
     * @return the list of rules to be executed
     */
    private Collection<JavaCodeRule<TypeElement>> getRules() {

        // Get the list of rules from rules.xml configuration file
        Set<String> ruleNameList = RuleLoader.getInstance().fetchRules();
        System.out.println("Rules are" + ruleNameList);
        // Convert the rule name to rule instances
        return CodeAnalyzerUtil.getRuleInstances(ruleNameList);
    }

    /**
     * Retrieves the details of problems identified in the source file after
     * executing the rules
     * 
     * @return List of problems found in the source file
     */
    public Collection<ErrorDescription> getProblemsFound() {
        return problemsFound;
    }
}
