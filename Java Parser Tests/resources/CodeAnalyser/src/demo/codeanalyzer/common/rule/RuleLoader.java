package demo.codeanalyzer.common.rule;

import static demo.codeanalyzer.common.util.CodeAnalyzerConstants.RULE_CONFIG_FILE;
import static demo.codeanalyzer.common.util.CodeAnalyzerConstants.DEFAULT_RULES;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import demo.codeanalyzer.common.jaxbgen.Rule;
import demo.codeanalyzer.common.jaxbgen.Rules;

/**
 * Reads the rules defined for a class from rules.xml configuration file.
 *
 * @author Seema Richard (Seema.Richard@ust-global.com)
 * @author Deepa Sobhana (Deepa.Sobhana@ust-global.com)
 */
public class RuleLoader {

    /**
     * Create singleton instance of rule loader class
     */
    private static RuleLoader ruleLoader = new RuleLoader();
    /**
     * Root element of rules.xml file
     */
    private static Rules rules = null;

    static {
        /**
         * Initializes the JAXB context for the rules.xml file and retrieves the
         * root element of this xml file
         */
        try {
            JAXBContext jc = JAXBContext.newInstance(
                    Rules.class.getPackage().getName(),
                    RuleLoader.class.getClassLoader());

            Unmarshaller unmarshaller = jc.createUnmarshaller();
            // Load rules.xml file
            InputStream inStream = RuleLoader.class.getClassLoader().
                    getResourceAsStream(RULE_CONFIG_FILE);
            // Get the root element
            rules = (Rules) unmarshaller.unmarshal(inStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Private constructor
     */
    private RuleLoader() {

    }

    /**
     * Returns the instance of RuleLoader
     *
     * @return ruleLoader
     *              the instance of RuleLoader
     */
    public static RuleLoader getInstance() {
        return ruleLoader;
    }

    /**
     * Get the list of rules for the given module-type and class-tpe. This
     * method will also fetch default rules applicable to all modules.
     *
     * @param moduleName
     *            the module type of the source file
     * @param classTypeNames
     *            the class types of the source file
     * @return Set of rules applicable for the source file
     */
    public Set<String> fetchRules() {
        Set<String> ruleNameList = new HashSet<String>();
        try {
            // Get the rules for all the class types in the given module
            for (Rule rule : rules.getRule()) {
                 ruleNameList.add(rule.getRuleClass());
            }

        } catch (Exception exception) {
            return null;
        }
        return ruleNameList;
    }

   
}


