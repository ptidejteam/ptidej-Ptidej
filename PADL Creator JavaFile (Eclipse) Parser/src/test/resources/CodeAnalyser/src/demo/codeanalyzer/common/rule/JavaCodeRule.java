package demo.codeanalyzer.common.rule;

import demo.codeanalyzer.common.model.ClassFile;
import java.util.Collection;

/**
 * Interface to execute rule on a subject
 * 
 * @author Seema Richard (Seema.Richard@ust-global.com)
 * @author Deepa Sobhana (Deepa.Sobhana@ust-global.com)
 * @param E
 *            the TypeElement being visited
 */
public interface JavaCodeRule<E> {
	/**
	 * Executes the rule on the subject
	 * 
	 * @param subject
	 *            the element where the rule will be applied.
	 * @param ctx
	 *            the contexual information of the class being verified
	 * @return Rule violations detected after applying the rule
	 */
	public Collection execute(ClassFile clazzInfo);
}
