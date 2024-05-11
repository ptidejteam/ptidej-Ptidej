
public class Distance
{
	private String depart;
	private String arrivee;
	private int km;

	public Distance(String depart, String arrivee, int km)
	{
		this.depart = depart;
		this.arrivee = arrivee;
		this.km = km;
	}

	public String getDepart() { return depart; }
	public String getArrivee() { return arrivee; }
	public int getKm() { return km; }

	public void setDepart(String depart) { this.depart = depart; }
	public void setArrivee(String depart) { this.arrivee = arrivee; }
	public void setKm(int km) { this.km = km; }

	// e completer (equals(), toString())

	public boolean equals(Object autre)
	{
		if(autre == this)
			return true;
		else
		{
			Distance temp = (Distance) autre;
			if(temp.getKm() == km)
				return true;
			return false;
		}
	}


	public String toString()
	{
		return "Distance entre " + depart + " " + arrivee + ": " + km + " km.";
	}
}
