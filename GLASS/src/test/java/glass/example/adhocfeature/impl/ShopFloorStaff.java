package glass.example.adhocfeature.impl;

import java.util.ArrayList;

import glass.example.adhocfeature.IRessource;

public class ShopFloorStaff extends Personel{

	MachineryTool mTool = new MachineryTool();
	
	public Integer capabilities() {
		return null;
	}
	
	public String schedule() {
		return null;
	}
	/*
	public IRessource compatibleStaff(ArrayList<Machinery> machines) {
		Integer nb = capabilities();
		this.mTool.assemblyLine();
		return new Ressource();
	}
	
	public void testMethod(IRessource ressource) {
	}
	*/
}
