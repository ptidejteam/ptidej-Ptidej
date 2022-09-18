package fr.emn.oadymppac.widgets;

interface BorderStyle {
	public static int NONE = 0;
	public static int FLAT = 1;
	public static int LOWERED = 2;
	public static int RAISED = 3;
	public static int GROOVED = 4;
	public static int RIDGED = 5;

	public int getBorderStyle();
	public void setBorderStyle(int style);

}
