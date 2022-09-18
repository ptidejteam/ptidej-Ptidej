package demo.codeanalyzer.common;

import java.util.Collection;
import demo.codeanalyzer.common.model.ErrorDescription;
import demo.codeanalyzer.common.model.Location;
import demo.codeanalyzer.common.rule.RulesEngine;

/**
 * This class starts the code analyzing process
 * @author Deepa Sobhana, Seema Richard
 */
public class CodeAnalyzer {

    private static CodeAnalyzer verifyWorker = new CodeAnalyzer();

    private CodeAnalyzer() {
    }

    public void process(String className) {
        RulesEngine ruleEngine = new RulesEngine();
        ruleEngine.fireRules(className);
        Collection<ErrorDescription> problems = ruleEngine.getProblemsFound();
        if (problems != null) {
            System.out.println("********** "+problems.size()+ " Problems Found **********");
            for(ErrorDescription errorDetails : problems) {
                String errorMessage = errorDetails.getErrorMessages();
                Location locDetails = errorDetails.getErrorLocation();            
                System.out.println("Error Message: "+errorMessage +". Line Num: "+locDetails.getLineNumber() +" offset "+locDetails.getStartOffset()+" - "+locDetails.getEndOffset());
            }
            System.out.println("******************************");
        } else {
            System.out.println("********** No Violations detected **********");
        }
        
    }
    
        
    public static CodeAnalyzer getInstance() {
        return verifyWorker;
    }
}
