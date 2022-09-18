public class Inscription implements Comparable
{
	private String codePerm;
	private String sigle;
	private String type;

	public Inscription (String codePerm, String sigle, String type)
	{
		this.codePerm = codePerm;
		this.sigle = sigle;
		this.type = type;
	}

	public String getCodePerm () { return codePerm; }
	public String getType () { return type; }
	public String getSigle () { return sigle; }

	public String toString ()
	{
		String info = codePerm + ", " + sigle + " ";
		if(type.equalsIgnoreCase("OPT"))
			info += "a option";
		else if(type.equalsIgnoreCase("HP"))
			info += "hors prog.";
		else info += "au choix";

		return info;

	}

	public boolean equals (Object autre)
	{
		if(autre instanceof Inscription)
		{
			Inscription obj = (Inscription) autre;
			return ( obj.codePerm.equalsIgnoreCase(codePerm) &&
					 obj.sigle.equalsIgnoreCase(sigle) &&
					 obj.type.equalsIgnoreCase(type) );
		}
		return false;
	}

	public int compareTo (Object autre)
	{
		Inscription obj = (Inscription) autre;
		return codePerm.compareTo(obj.codePerm);
	}



}