/*
 * @(#)NothingApplet.java 5.1
 *
 */

package CH.ifa.draw.samples.nothing;

import java.awt.Panel;

import CH.ifa.draw.applet.DrawApplet;
import CH.ifa.draw.contrib.PolygonTool;
import CH.ifa.draw.figures.ElbowConnection;
import CH.ifa.draw.figures.EllipseFigure;
import CH.ifa.draw.figures.LineConnection;
import CH.ifa.draw.figures.LineFigure;
import CH.ifa.draw.figures.RectangleFigure;
import CH.ifa.draw.figures.RoundRectangleFigure;
import CH.ifa.draw.figures.TextFigure;
import CH.ifa.draw.figures.TextTool;
import CH.ifa.draw.framework.Tool;
import CH.ifa.draw.standard.ConnectionTool;
import CH.ifa.draw.standard.CreationTool;

public class NothingApplet extends DrawApplet {

	//-- DrawApplet overrides -----------------------------------------

	protected void createTools(Panel palette) {
		super.createTools(palette);

		Tool tool = new TextTool(view(), new TextFigure());
		palette.add(createToolButton(IMAGES+"TEXT", "Text Tool", tool));

		tool = new CreationTool(view(), new RectangleFigure());
		palette.add(createToolButton(IMAGES+"RECT", "Rectangle Tool", tool));

		tool = new CreationTool(view(), new RoundRectangleFigure());
		palette.add(createToolButton(IMAGES+"RRECT", "Round Rectangle Tool", tool));

		tool = new CreationTool(view(), new EllipseFigure());
		palette.add(createToolButton(IMAGES+"ELLIPSE", "Ellipse Tool", tool));

		tool = new CreationTool(view(), new LineFigure());
		palette.add(createToolButton(IMAGES+"LINE", "Line Tool", tool));

		tool = new PolygonTool(view());
		palette.add(createToolButton(IMAGES+"POLYGON", "Polygon Tool", tool));

		tool = new ConnectionTool(view(), new LineConnection());
		palette.add(createToolButton(IMAGES+"CONN", "Connection Tool", tool));

		tool = new ConnectionTool(view(), new ElbowConnection());
		palette.add(createToolButton(IMAGES+"OCONN", "Elbow Connection Tool", tool));
	}

}
