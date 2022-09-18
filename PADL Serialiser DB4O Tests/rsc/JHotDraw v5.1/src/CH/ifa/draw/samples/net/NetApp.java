/*
 * @(#)NetApp.java 5.1
 *
 */

package CH.ifa.draw.samples.net;

import java.awt.Panel;

import CH.ifa.draw.application.DrawApplication;
import CH.ifa.draw.figures.LineConnection;
import CH.ifa.draw.figures.TextTool;
import CH.ifa.draw.framework.Tool;
import CH.ifa.draw.standard.ConnectionTool;
import CH.ifa.draw.standard.CreationTool;

public  class NetApp extends DrawApplication {

	NetApp() {
		super("Net");
	}

	protected void createTools(Panel palette) {
		super.createTools(palette);

		Tool tool = new TextTool(view(), new NodeFigure());
		palette.add(createToolButton(IMAGES+"TEXT", "Text Tool", tool));

		tool = new CreationTool(view(), new NodeFigure());
		palette.add(createToolButton(IMAGES+"RECT", "Create Org Unit", tool));

		tool = new ConnectionTool(view(), new LineConnection());
		palette.add(createToolButton(IMAGES+"CONN", "Connection Tool", tool));
	}

	//-- main -----------------------------------------------------------

	public static void main(String[] args) {
		DrawApplication window = new NetApp();
		window.open();
	}
}
