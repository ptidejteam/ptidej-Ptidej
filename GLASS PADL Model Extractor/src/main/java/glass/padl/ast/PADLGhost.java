package glass.padl.ast;


import padl.kernel.IGhost;

public class PADLGhost extends PADLType{

	public PADLGhost(IGhost padlType, PADLProject padlProject) {
		super(padlType, padlProject);
	}

	@Override
	public boolean isInterface() {
		// TODO Auto-generated method stub
		return false;
	}

}
