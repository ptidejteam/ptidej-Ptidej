package ptidej.viewer.ui.window;

import java.util.Set;

import javax.imageio.IIOException;

import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;
import padl.motif.IDesignMotifModel;
import ptidej.ui.canvas.event.ICanvasListener;
import ptidej.viewer.event.IGraphModelListener;
import ptidej.viewer.event.ISourceModelListener;
import ptidej.viewer.ui.DesktopPane;
import ptidej.viewer.ui.rulecard.IRuleCardListener;
import ptidej.viewer.ui.rulecard.RuleCardEvent;

public class DesktopPaneTest extends TestCase {
	/**
	 * @author Vishnu Rameshbabu
	 * @since 2024/05/14
	 */
	DesktopPane desktopPane = DesktopPane.getInstance();
	final ICanvasListener aCanvasListener = null;
	final IGraphModelListener aGraphModelListener = null;
	final IRuleCardListener aRuleCardListener = null;
	final ISourceModelListener aSourceModelListener = null;
	final RuleCardEvent aRuleCardEvent = null;
	final Set aDesignDefectList = null;
	final IDesignMotifModel aPattern = null;
	final char[] patternName = null;
	final int problem = 0;
	private int solver = 2;
	private SourcePlantUMLModelWindow plantUML = null;

	protected void setUp() {
		desktopPane.setRuleCardEvent(aRuleCardEvent);
		desktopPane.setDesignDefects(aDesignDefectList);
		desktopPane.setPattern(aPattern);
		desktopPane.setPatternName(patternName);
		desktopPane.setProblem(problem);
		desktopPane.setSolver(solver);
		desktopPane.createPlantUMLModelWindow();
		plantUML = new SourcePlantUMLModelWindow();
	}

	public void testCaseDesktopPane() {

		Assert.assertNotNull(desktopPane);

	}

	public void testCaseCreatePlantUML() {

		Assert.assertNotNull(plantUML);
	}

	@Test(expected = IIOException.class)
	public void testCaseCreatePlantUMLImage() {
		((SourcePlantUMLModelWindow) desktopPane.getInstance()
				.getAbstractRepresentationWindow())
				// TODO What's this weird path?
				.setImagePath("../InvalidPath.txt\\");
		plantUML = new SourcePlantUMLModelWindow();

	}
}
