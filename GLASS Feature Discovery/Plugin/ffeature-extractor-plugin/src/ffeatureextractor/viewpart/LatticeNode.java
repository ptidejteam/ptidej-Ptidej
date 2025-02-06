package ffeatureextractor.viewpart;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

public class LatticeNode extends ViewPart {

	private Text featureTypeText;
	private Text extentText;
	private Text intentText;
	private Text nodeIDText;
	 
	 
	@Override
	public void createPartControl(Composite parent) {
		parent = new Composite(parent, SWT.NONE);
        GridLayout mylayout = new GridLayout();
        mylayout.marginHeight = 1;
        mylayout.marginWidth = 1;
        parent.setLayout(mylayout);
        
        
        
        Label featureType = new Label(parent, SWT.NONE);
        featureType.setLayoutData(new GridData());
        featureType.setText("Feature type : ");
        featureTypeText = new Text(parent, SWT.BORDER);
        featureTypeText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        Label extent = new Label(parent, SWT.NONE);
        extent.setLayoutData(new GridData());
        extent.setText("Extent : ");
        extentText = new Text(parent, SWT.BORDER);
        extentText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        Label intent = new Label(parent, SWT.NONE);
        intent.setLayoutData(new GridData());
        intent.setText("Intent : ");
        intentText = new Text(parent, SWT.BORDER);
        intentText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        Label nodeID = new Label(parent, SWT.NONE);
        nodeID.setLayoutData(new GridData());
        nodeID.setText("node ID : ");
        nodeIDText = new Text(parent, SWT.BORDER);
        nodeIDText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}

	public void update (String Type, String intent, String extent, int ID) {
		
		featureTypeText.setText(Type);
		intentText.setText(intent);
		extentText.setText(extent);
		nodeIDText.setText(String.valueOf(ID));
	}


		
	}