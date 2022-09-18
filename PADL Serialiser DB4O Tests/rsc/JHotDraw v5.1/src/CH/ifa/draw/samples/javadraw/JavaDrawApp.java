/*
 * @(#)JavaDrawApp.java 5.1
 *
 */

package CH.ifa.draw.samples.javadraw;

import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import CH.ifa.draw.application.DrawApplication;
import CH.ifa.draw.contrib.PolygonTool;
import CH.ifa.draw.figures.BorderTool;
import CH.ifa.draw.figures.ConnectedTextTool;
import CH.ifa.draw.figures.ElbowConnection;
import CH.ifa.draw.figures.EllipseFigure;
import CH.ifa.draw.figures.InsertImageCommand;
import CH.ifa.draw.figures.LineConnection;
import CH.ifa.draw.figures.LineFigure;
import CH.ifa.draw.figures.RectangleFigure;
import CH.ifa.draw.figures.RoundRectangleFigure;
import CH.ifa.draw.figures.ScribbleTool;
import CH.ifa.draw.figures.TextFigure;
import CH.ifa.draw.figures.TextTool;
import CH.ifa.draw.framework.Drawing;
import CH.ifa.draw.framework.Tool;
import CH.ifa.draw.standard.ConnectionTool;
import CH.ifa.draw.standard.CreationTool;
import CH.ifa.draw.util.Animatable;
import CH.ifa.draw.util.CommandMenu;

public  class JavaDrawApp extends DrawApplication {

	private Animator            fAnimator;
	private static String       fgSampleImagesPath = "CH/ifa/draw/samples/javadraw/sampleimages/";
	private static String       fgSampleImagesResourcePath = "/"+fgSampleImagesPath;

	public JavaDrawApp() {
		this("JHotDraw");
	}
	public JavaDrawApp(String name) {
		super(name);
	}

	public void open() {
		super.open();
	}

	//-- application life cycle --------------------------------------------

	public void destroy() {
		super.destroy();
		endAnimation();
	}

	//-- DrawApplication overrides -----------------------------------------

	protected void createTools(Panel palette) {
		super.createTools(palette);

		Tool tool = new TextTool(view(), new TextFigure());
		palette.add(createToolButton(IMAGES+"TEXT", "Text Tool", tool));

		tool = new ConnectedTextTool(view(), new TextFigure());
		palette.add(createToolButton(IMAGES+"ATEXT", "Connected Text Tool", tool));

		tool = new URLTool(view());
		palette.add(createToolButton(IMAGES+"URL", "URL Tool", tool));

		tool = new CreationTool(view(), new RectangleFigure());
		palette.add(createToolButton(IMAGES+"RECT", "Rectangle Tool", tool));

		tool = new CreationTool(view(), new RoundRectangleFigure());
		palette.add(createToolButton(IMAGES+"RRECT", "Round Rectangle Tool", tool));

		tool = new CreationTool(view(), new EllipseFigure());
		palette.add(createToolButton(IMAGES+"ELLIPSE", "Ellipse Tool", tool));

		tool = new CreationTool(view(), new LineFigure());
		palette.add(createToolButton(IMAGES+"LINE", "Line Tool", tool));

		tool = new ConnectionTool(view(), new LineConnection());
		palette.add(createToolButton(IMAGES+"CONN", "Connection Tool", tool));

		tool = new ConnectionTool(view(), new ElbowConnection());
		palette.add(createToolButton(IMAGES+"OCONN", "Elbow Connection Tool", tool));

		tool = new ScribbleTool(view());
		palette.add(createToolButton(IMAGES+"SCRIBBL", "Scribble Tool", tool));

		tool = new PolygonTool(view());
		palette.add(createToolButton(IMAGES+"POLYGON", "Polygon Tool", tool));

		tool = new BorderTool(view());
		palette.add(createToolButton(IMAGES+"BORDDEC", "Border Tool", tool));
	}

	protected Tool createSelectionTool() {
		return new MySelectionTool(view());
	}

	protected void createMenus(MenuBar mb) {
		super.createMenus(mb);
		mb.add(createAnimationMenu());
		mb.add(createImagesMenu());
		mb.add(createWindowMenu());
	}

	protected Menu createAnimationMenu() {
		Menu menu = new Menu("Animation");
		MenuItem mi = new MenuItem("Start Animation");
		mi.addActionListener(
		    new ActionListener() {
		        public void actionPerformed(ActionEvent event) {
		            startAnimation();
		        }
		    }
		);
		menu.add(mi);

		mi = new MenuItem("Stop Animation");
		mi.addActionListener(
		    new ActionListener() {
		        public void actionPerformed(ActionEvent event) {
		            endAnimation();
		        }
		    }
		);
		menu.add(mi);
		return menu;
	}

	protected Menu createWindowMenu() {
		Menu menu = new Menu("Window");
		MenuItem mi = new MenuItem("New Window");
		mi.addActionListener(
		    new ActionListener() {
		        public void actionPerformed(ActionEvent event) {
		            openView();
		        }
		    }
		);
		menu.add(mi);
		return menu;
	}

	protected Menu createImagesMenu() {
		CommandMenu menu = new CommandMenu("Images");
		File imagesDirectory = new File(fgSampleImagesPath);
		try {
		    String[] list = imagesDirectory.list();
		    for (int i = 0; i < list.length; i++) {
		        String name = list[i];
		        String path = fgSampleImagesResourcePath+name;
		        menu.add(new InsertImageCommand(name, path, view()));
		    }
		} catch (Exception e) {}
		return menu;
	}

	protected Drawing createDrawing() {
		return new BouncingDrawing();
		//return new StandardDrawing();
	}

	protected Dimension defaultSize() {
		return new Dimension(430,436);
	}

	//---- animation support --------------------------------------------

	public void startAnimation() {
		if (drawing() instanceof Animatable && fAnimator == null) {
			fAnimator = new Animator((Animatable)drawing(), view());
			fAnimator.start();
		}
	}

	public void endAnimation() {
		if (fAnimator != null) {
			fAnimator.end();
			fAnimator = null;
		}
	}

	public void openView() {
		JavaDrawApp window = new JavaDrawApp();
		window.open();
		window.setDrawing(drawing());
		window.setTitle("JHotDraw (View)");

	}

	//-- main -----------------------------------------------------------

	public static void main(String[] args) {
		JavaDrawApp window = new JavaDrawApp();
		window.open();
	}
}
