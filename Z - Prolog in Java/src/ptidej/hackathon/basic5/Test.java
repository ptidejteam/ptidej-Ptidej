package ptidej.hackathon.basic5;

import org.junit.Assert;
import com.ugos.jiprolog.engine.JIPEngine;
import com.ugos.jiprolog.engine.JIPQuery;
import com.ugos.jiprolog.engine.JIPTerm;
import com.ugos.jiprolog.engine.JIPTermParser;
import junit.framework.TestCase;

public class Test extends TestCase {
	public void test1() {
		final JIPEngine engine = new JIPEngine();
		engine.consultFile("Solution.pl");
		final JIPTermParser parser = engine.getTermParser();
		final JIPTerm term =
			parser.parseTerm("solution(S, E, N, D, M, O, R, Y).");
		final JIPQuery query = engine.openSynchronousQuery(term);

		final JIPTerm solution = query.nextSolution();
		Assert.assertEquals(
			"solution(9,5,6,7,1,0,8,2)",
			solution.getValue().toString());
	}
}
