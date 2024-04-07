
import java.util.*;

public class Session implements Comparable
{
	private int annee;
	private int code;
	private LinkedList lesInsc;

	public Session(int annee, int code)
	{
		this.annee = annee;
		this.code = code;
		lesInsc = new LinkedList();
	}

	public int getAnnee() { return annee; }
	public int getCode() { return code; }

	public int compareTo(Object autre)
	{
		Session obj = (Session) autre;
		if( obj.annee != annee )
			return Integer.valueOf(annee).compareTo(Integer.valueOf(obj.annee));
		else
			return Integer.valueOf(code).compareTo(Integer.valueOf(obj.code));
	}

	public boolean equals(Object autre)
	{
		if(autre instanceof Session)
		{
			Session obj = (Session)autre;
			return 	(obj.annee == annee && obj.code == code);
		}
		return false;
	}

	public String toString()
	{
		String info = "";

		switch(code)
		{
			case 1 : info += "Hiver ";
			break;
			case 2 : info += "Ete ";
			break;
			case 4 : info += "Automne ";
			break;
		}

		info += " " + annee + "\n\t[\n";

		for(int i = 0; i < 10; i++)
		{
			Inscription temp = (Inscription) lesInsc.get(i);
			info += temp + "\n";
		}


		info += "\t]";

		return info;
	}

	public void ajouter(Inscription ins)
	{
		int indice = lesInsc.indexOf(ins);
		if(indice == -1)
		{
			lesInsc.add(ins);
			Collections.sort(lesInsc);
		}
	}

	public 	int nbInscriptions()
	{
		return lesInsc.size();
	}

	public int nbSexe (char sexe)
	{
		int compteM = 0;

		for(int i = 0; i < lesInsc.size(); i++)
		{
			Inscription insc = (Inscription) lesInsc.get(i);
			if(insc.getCodePerm().charAt(6) == '0' || insc.getCodePerm().charAt(6) == '1')
				compteM++;
		}

		switch(Character.toUpperCase(sexe))
		{
			case 'M' : return compteM;
			case 'F' : return lesInsc.size() - compteM;
		}

		return compteM;
	}

	public int nbSigle (String cours)
	{
		int compteCours = 0;

		for(int i = 0; i < lesInsc.size(); i++)
		{
			Inscription temp = (Inscription) lesInsc.get(i);
			if(temp.getSigle().equalsIgnoreCase(cours))
				compteCours++;
		}

		return compteCours;
	}

	public void getInscriptions(Collection maColl, String codePerm)
	{
		for(int i = 0; i < lesInsc.size(); i++)
		{
			Inscription temp = (Inscription) lesInsc.get(i);
			if(temp.getCodePerm().equalsIgnoreCase(codePerm))
				maColl.add(temp);
		}
	}

	public Vector getInscriptions(String sigle)
	{
		Vector v = new Vector();
		for(int i = 0; i < lesInsc.size(); i++)
		{
			Inscription temp = (Inscription) lesInsc.get(i);
			if(temp.getSigle().equalsIgnoreCase(sigle))
				v.add(temp);
		}

		return v;
	}

	public void supprimerType(String type)
	{
		//ListIterator it = lesInsc.listIterator();
		for(int i = 0 ;i < lesInsc.size(); i++)
		{
			Inscription temp = (Inscription)lesInsc.get(i);
			if(temp.getType().equalsIgnoreCase(type))
			{
				lesInsc.remove(temp);
				i--;
			}
		}

	/*	while(it.hasNext())
		{
			Inscription temp = (Inscription)it.next();
			if(temp.getType().equalsIgnoreCase(type))
				lesInsc.remove(temp);
		}*/

	}
}