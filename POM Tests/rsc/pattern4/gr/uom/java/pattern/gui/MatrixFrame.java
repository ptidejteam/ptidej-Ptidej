package gr.uom.java.pattern.gui;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.*;

import gr.uom.java.bytecode.*;
import gr.uom.java.pattern.*;
import gr.uom.java.pattern.gui.progress.ProgressListener;
import gr.uom.java.pattern.gui.progress.DetectionFinishedEvent;
import gr.uom.java.pattern.gui.progress.ProgressObserver;
import gr.uom.java.pattern.gui.progress.PatternDetectionSource;

public class MatrixFrame extends JFrame implements ActionListener, InternalFrameListener, ProgressListener {
	private static JDesktopPane desktop;
	private static JFileChooser fc;
	private JMenuItem openDir;
    private JMenuItem exportXML;
    private JRadioButtonMenuItem allPatternsMenuItem;
    private JRadioButtonMenuItem singletonMenuItem;
    private JRadioButtonMenuItem templateMenuItem;
    private JRadioButtonMenuItem factoryMethodMenuItem;
    private MatrixInternalFrame activeInternalFrame;
	//Map that has as key the internalFrame title
	//and as value the SystemObject of the corresponding system
	private Map<String,SystemGenerator> systemGeneratorMap;
    //Map that has as key the internalFrame title
	//and as value the Map of all the detected pattern instances
    //(key is the pattern name and value is a vector of PatternInstance objects)
    private Map<String,LinkedHashMap<String, Vector<PatternInstance>>> detectedPatternsMap;

    private ProgressObserver progressObserver;

    public static void main(String[] args) {
        if(args.length == 4) {
            if(args[0].equals("-target") && args[2].equals("-output")) {
                File inputDir = new File(args[1]);
                File outputXML = new File(args[3]);
                new Console(inputDir,outputXML);
            }
            else {
                System.out.println("Usage: java -Xms32m -Xmx512m -jar pattern2.jar -target \"c:\\foo\\myclasses\" -output \"c:\\foo\\build\\pattern-detector-output.xml\"");
            }
        }
        else {
            MatrixFrame frame = new MatrixFrame();
        }
    }
	
	public MatrixFrame() {
		super("Design Pattern detection v4.5");
		systemGeneratorMap = new HashMap<String,SystemGenerator>();
        detectedPatternsMap = new HashMap<String,LinkedHashMap<String, Vector<PatternInstance>>>();
        progressObserver = new ProgressObserver();
        progressObserver.addProgressListener(this);

        JFrame.setDefaultLookAndFeelDecorated(true);
		desktop = new JDesktopPane();
		fc = new JFileChooser();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(createMenuBar());
        setContentPane(desktop);
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle bounds = env.getMaximumWindowBounds();
		this.setSize(bounds.getSize());
        //this.setLocationRelativeTo(null);
        setVisible(true);
        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
	}
	
	private JMenuBar createMenuBar() {
		JMenuBar menuBar;
		JMenu fileMenu;
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		openDir = new JMenuItem("Open Directory");
		openDir.addActionListener(this);
        fileMenu.add(openDir);

        exportXML = new JMenuItem("Export as XML");
		exportXML.addActionListener(this);
        fileMenu.add(exportXML);

        JMenu patternMenu  = new JMenu("Patterns");
        menuBar.add(patternMenu);
        
        ButtonGroup patternGroup = new ButtonGroup();

        JMenu structuralPatternMenu = new JMenu("Structural");

        JRadioButtonMenuItem adapterMenuItem = new JRadioButtonMenuItem(PatternEnum.ADAPTER_COMMAND.toString());
        adapterMenuItem.addActionListener(this);
        patternGroup.add(adapterMenuItem);
        structuralPatternMenu.add(adapterMenuItem);

        JRadioButtonMenuItem compositeMenuItem = new JRadioButtonMenuItem(PatternEnum.COMPOSITE.toString());
        compositeMenuItem.addActionListener(this);
        patternGroup.add(compositeMenuItem);
        structuralPatternMenu.add(compositeMenuItem);

        JRadioButtonMenuItem decoratorMenuItem = new JRadioButtonMenuItem(PatternEnum.DECORATOR.toString());
        decoratorMenuItem.addActionListener(this);
        patternGroup.add(decoratorMenuItem);
        structuralPatternMenu.add(decoratorMenuItem);
        
        JRadioButtonMenuItem proxyMenuItem = new JRadioButtonMenuItem(PatternEnum.PROXY.toString());
        proxyMenuItem.addActionListener(this);
        patternGroup.add(proxyMenuItem);
        structuralPatternMenu.add(proxyMenuItem);
        
        JRadioButtonMenuItem proxy2MenuItem = new JRadioButtonMenuItem(PatternEnum.PROXY2.toString());
        proxy2MenuItem.addActionListener(this);
        patternGroup.add(proxy2MenuItem);
        structuralPatternMenu.add(proxy2MenuItem);
        
        /*JRadioButtonMenuItem redirectInFamilyMenuItem = new JRadioButtonMenuItem(PatternEnum.REDIRECT_IN_FAMILY.toString());
        redirectInFamilyMenuItem.addActionListener(this);
        patternGroup.add(redirectInFamilyMenuItem);
        structuralPatternMenu.add(redirectInFamilyMenuItem);*/

        JMenu behavioralPatternMenu = new JMenu("Behavioral");

        JRadioButtonMenuItem observerMenuItem = new JRadioButtonMenuItem(PatternEnum.OBSERVER.toString());
        observerMenuItem.addActionListener(this);
        patternGroup.add(observerMenuItem);
        behavioralPatternMenu.add(observerMenuItem);

        JRadioButtonMenuItem stateMenuItem = new JRadioButtonMenuItem(PatternEnum.STATE_STRATEGY.toString());
        stateMenuItem.addActionListener(this);
        patternGroup.add(stateMenuItem);
        behavioralPatternMenu.add(stateMenuItem);

        templateMenuItem = new JRadioButtonMenuItem(PatternEnum.TEMPLATE_METHOD.toString());
        templateMenuItem.addActionListener(this);
        patternGroup.add(templateMenuItem);
        behavioralPatternMenu.add(templateMenuItem);

        JRadioButtonMenuItem visitorMenuItem = new JRadioButtonMenuItem(PatternEnum.VISITOR.toString());
        visitorMenuItem.addActionListener(this);
        patternGroup.add(visitorMenuItem);
        behavioralPatternMenu.add(visitorMenuItem);

        JMenu creationalPatternMenu = new JMenu("Creational");

        factoryMethodMenuItem = new JRadioButtonMenuItem(PatternEnum.FACTORY_METHOD.toString());
        factoryMethodMenuItem.addActionListener(this);
        patternGroup.add(factoryMethodMenuItem);
        creationalPatternMenu.add(factoryMethodMenuItem);

        JRadioButtonMenuItem prototypeMenuItem = new JRadioButtonMenuItem(PatternEnum.PROTOTYPE.toString());
        prototypeMenuItem.addActionListener(this);
        patternGroup.add(prototypeMenuItem);
        creationalPatternMenu.add(prototypeMenuItem);

        singletonMenuItem = new JRadioButtonMenuItem(PatternEnum.SINGLETON.toString());
        singletonMenuItem.addActionListener(this);
        patternGroup.add(singletonMenuItem);
        creationalPatternMenu.add(singletonMenuItem);

        allPatternsMenuItem = new JRadioButtonMenuItem("ALL");
        allPatternsMenuItem.addActionListener(this);

        patternMenu.add(allPatternsMenuItem);
        patternMenu.add(creationalPatternMenu);
        patternMenu.add(structuralPatternMenu);
        patternMenu.add(behavioralPatternMenu);

        return menuBar;
	}
	
	public void actionPerformed(ActionEvent e) {		
		if(e.getSource() == openDir) {
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	        fc.setFileFilter(new DirectoryFilter());
            int returnVal = fc.showOpenDialog(desktop);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				ThreadMXBean bean = ManagementFactory.getThreadMXBean();
				long start = bean.getCurrentThreadCpuTime();
                BytecodeReader br = new BytecodeReader(file);

                SystemObject so = br.getSystemObject();
                SystemGenerator sg = new SystemGenerator(so);
                long end = bean.getCurrentThreadCpuTime();
                long time = end - start;
                System.out.println("System parsing: " + time/1000000.0 + " ms");
                //generateMatrixContainer() should be invoked only once
                //so.generateMatrixContainer();
                systemGeneratorMap.put(file.getPath(),sg);

            	activeInternalFrame = new MatrixInternalFrame(file.getPath());
            	activeInternalFrame.addInternalFrameListener(this);
            	
            	desktop.add(activeInternalFrame);
            	try {
            		activeInternalFrame.setMaximum(true);
        			activeInternalFrame.setSelected(true);
    			} catch (java.beans.PropertyVetoException pve) {}
			}
		}
        else if(e.getSource() == exportXML && activeInternalFrame != null) {
            fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            fc.setFileFilter(new XMLFilter());
            int returnVal = fc.showSaveDialog(desktop);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                LinkedHashMap<String, Vector<PatternInstance>> map = detectedPatternsMap.get(activeInternalFrame.getTitle());
                new XMLExporter(map,file);
            }
        }
        else if(e.getSource() == allPatternsMenuItem && activeInternalFrame != null) {
            SystemGenerator sg = systemGeneratorMap.get(activeInternalFrame.getTitle());
            LongTask longTask = new LongTask(sg,progressObserver);
            new ProgressBarDialog(this,longTask);
        }
        else if(activeInternalFrame != null) {
            SystemGenerator sg = systemGeneratorMap.get(activeInternalFrame.getTitle());
            String patternName = ((JRadioButtonMenuItem)e.getSource()).getText();
            MatrixContainer systemContainer = sg.getMatrixContainer();
            PatternDescriptor patternDescriptor = PatternGenerator.getPattern(patternName);
            if(patternDescriptor.getNumberOfHierarchies() == 0) {
            	ThreadMXBean bean = ManagementFactory.getThreadMXBean();
            	long start = bean.getCurrentThreadCpuTime();
                double[][] systemMatrix = null;
                BehavioralData behavioralData = null;
                if(e.getSource() == singletonMenuItem) {
                    systemMatrix = systemContainer.getSingletonMatrix();
                    behavioralData = systemContainer.getSingletonBehavioralData();
                }
                else if(e.getSource() == templateMenuItem) {
                    systemMatrix = systemContainer.getTemplateMethodMatrix();
                    behavioralData = systemContainer.getTemplateMethodBehavioralData();
                }
                else if(e.getSource() == factoryMethodMenuItem) {
                    systemMatrix = systemContainer.getFactoryMethodMatrix();
                    behavioralData = systemContainer.getFactoryMethodBehavioralData();
                }

                Vector<PatternInstance> patternInstanceVector = new Vector<PatternInstance>();
                for(int i=0; i<systemMatrix.length; i++) {
                    if(systemMatrix[i][i] == 1.0) {
                        PatternInstance patternInstance = new PatternInstance();
                        patternInstance.addEntry(patternInstance.new Entry(patternDescriptor.getClassNameList().get(0),systemContainer.getClassNameList().get(i),i));
                        if(behavioralData != null) {
                        	if(patternDescriptor.getFieldRoleName() != null) {
                        		Set<FieldObject> fields = behavioralData.getFields(i, i);
                        		if(fields != null) {
                        			for(FieldObject field : fields) {
                        				patternInstance.addEntry(patternInstance.new Entry(patternDescriptor.getFieldRoleName(), field.toString(), -1));
                        			}
                        		}
                        	}
                        	if(patternDescriptor.getMethodRoleName() != null) {
                        		Set<MethodObject> methods = behavioralData.getMethods(i, i);
                        		if(methods != null) {
                        			for(MethodObject method : methods) {
                        				patternInstance.addEntry(patternInstance.new Entry(patternDescriptor.getMethodRoleName(), method.getSignature().toString(), -1));
                        			}
                        		}
                        	}
                        }
                        patternInstanceVector.add(patternInstance);
                    }
                }
                long end = bean.getCurrentThreadCpuTime();
                long time = end - start;
                System.out.println("Detection time for " + patternName + ": " + time/1000000.0 + " ms");
                String activeInternalFrameTitle = activeInternalFrame.getTitle();
                if(detectedPatternsMap.containsKey(activeInternalFrameTitle)) {
                    LinkedHashMap<String, Vector<PatternInstance>> map = detectedPatternsMap.get(activeInternalFrameTitle);
                    map.put(patternName,patternInstanceVector);
                }
                else {
                    LinkedHashMap<String, Vector<PatternInstance>> map = new LinkedHashMap<String, Vector<PatternInstance>>();
                    map.put(patternName,patternInstanceVector);
                    detectedPatternsMap.put(activeInternalFrameTitle, map);
                }
                //activeInternalFrame.addRow(patternName, new JComboBox(patternInstanceVector));
                activeInternalFrame.addPatternNode(patternName, patternInstanceVector);
            }
            else if(patternDescriptor.getNumberOfHierarchies() == 1) {
                LongTask longTask = new LongTask(sg,patternDescriptor,progressObserver,patternName);
                new ProgressBarDialog(this,longTask);
            }
            else if(patternDescriptor.getNumberOfHierarchies() > 1) {
                LongTask longTask = new LongTask(sg,patternDescriptor,progressObserver,patternName);
                new ProgressBarDialog(this,longTask);
            }
		}
	}

    public void internalFrameActivated(InternalFrameEvent e) {
		activeInternalFrame = (MatrixInternalFrame)e.getInternalFrame();
		//System.out.println(e.getInternalFrame().getTitle() + " activated");
	}

 	public void internalFrameClosed(InternalFrameEvent e) {
 		//System.out.println(e.getInternalFrame().getTitle() + " closed");
 	}

 	public void internalFrameClosing(InternalFrameEvent e) {
 		//System.out.println(e.getInternalFrame().getTitle() + " closing");
 	}

 	public void internalFrameDeactivated(InternalFrameEvent e) {
 		//System.out.println(e.getInternalFrame().getTitle() + " deactivated");
 	}

 	public void internalFrameDeiconified(InternalFrameEvent e) {
 		//System.out.println(e.getInternalFrame().getTitle() + " deiconified");
 	}

 	public void internalFrameIconified(InternalFrameEvent e) {
 		//System.out.println(e.getInternalFrame().getTitle() + " iconified");
 	}

 	public void internalFrameOpened(InternalFrameEvent e) {
 		//System.out.println(e.getInternalFrame().getTitle() + " opened");
 	}

    public void detectionFinished(DetectionFinishedEvent event) {
        String activeInternalFrameTitle = activeInternalFrame.getTitle();
        PatternDetectionSource source = (PatternDetectionSource)event.getSource();
        if(detectedPatternsMap.containsKey(activeInternalFrameTitle)) {
            LinkedHashMap<String, Vector<PatternInstance>> map = detectedPatternsMap.get(activeInternalFrameTitle);
            map.put(source.getPatternName(),source.getPatternInstanceVector());
        }
        else {
            LinkedHashMap<String, Vector<PatternInstance>> map = new LinkedHashMap<String, Vector<PatternInstance>>();
            map.put(source.getPatternName(),source.getPatternInstanceVector());
            detectedPatternsMap.put(activeInternalFrameTitle, map);
        }
        //activeInternalFrame.addRow(source.getPatternName(), new JComboBox(source.getPatternInstanceVector()));
        activeInternalFrame.addPatternNode(source.getPatternName(), source.getPatternInstanceVector());
    }
}