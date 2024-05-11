
import java.util.*;
import java.io.*;
import java.text.*;

public class TestSession
{

	public static void main(String [] args)
		throws IOException
	{
		LinkedList l = lireCreer("inscrip.dat");

		System.out.println("Le nombre d'inscriptions durant ETE 2001 : " +
							nbInscriptions(l, 2001, 2));

		System.out.println("Le nombre d'inscriptions durant HIVER 2002 : " +
							nbInscriptions(l, 2002, 1));


		DecimalFormat fmt = new DecimalFormat("0.##");
		System.out.println("Le pourcentage des inscriptions faites par les hommes : " +
							fmt.format(pourcentage (l, 'M') * 100) + "%");


		/*for(int i = 0; i < l.size() ; i++)
		{
			Session temp = (Session) l.get(i);
			System.out.println(temp);
		}*/

		supprimer (l, "HP");

		System.out.println("Le nombre d'inscriptions total au cours IFT1160 : " +
							nbInscriptions(l, "IFT1160"));

		System.out.println("\n***************************************\n" +
						   "Voici la liste des inscriptions au cours IFT1160 en HIVER 2001 :\n");
		afficherInscriptions(l, "IFT1160", 2001, 1);
		System.out.println("***************************************\n");


		listInscriptions(l, "VYSD02097206");
		listInscriptions(l, "CHEG08126907");
		listInscriptions(l, "MELK10557607");

		/*System.out.println("***************************************");
		for(int i = 0; i < l.size() ; i++)
		{
			Session temp = (Session) l.get(i);
			System.out.println(temp);
		}*/

	}

	public static LinkedList lireCreer(String nomFichier)
		throws IOException
	{

		FileReader fr = null; // initialiser pour Java
		boolean existeFichier = true ; // e ajuster apres
		LinkedList mesSessions = new LinkedList();

		// essayer de LOCALISER le fichier e partir de son nom
		try {
			fr = new FileReader (nomFichier) ;		
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Probleme d'ouvrir le fichier " + nomFichier);
			existeFichier = false ; // ajuster
		}	
			if (existeFichier)
			{
				BufferedReader entree = new BufferedReader(fr);
				boolean finFichier = false ;
				String uneLigne = entree.readLine();
	
				final int INDEX_SIGLE = 8;
				final int INDEX_ANNEE = 12;
				final int INDEX_CODE = 13;
				final int INDEX_CODE_PERM = 25;
	
				while ( !finFichier && uneLigne != null)
				{
					int annee = Integer.parseInt(uneLigne.substring(INDEX_SIGLE, INDEX_ANNEE ).trim());
					int code = Integer.parseInt(uneLigne.substring(INDEX_ANNEE, INDEX_CODE).trim());
					String sigle = uneLigne.substring(0, INDEX_SIGLE).trim();
					String codePerm = uneLigne.substring(INDEX_CODE, INDEX_CODE_PERM).trim();
					String type = uneLigne.substring(INDEX_CODE_PERM).trim();
	
					Session s = new Session (annee, code);
					int indice = mesSessions.indexOf(s);
					if(indice == -1)
						mesSessions.add(s);
					indice = mesSessions.indexOf(s);
	
					((Session) mesSessions.get(indice)).ajouter(new Inscription(codePerm, sigle, type));
					uneLigne = entree.readLine();
				}	
				entree.close();
			}
	//	}
		
		// intercepter l'erreur si le fichier n'existe pas
/*		catch ( Exception erreur) {
			System.out.println("Probleme d'ouvrir le fichier " + nomFichier);
			existeFichier = false ; // ajuster
		}*/


		return mesSessions;

	}


	public static int nbInscriptions(LinkedList l, int annee, int code)
	{
		Session s = new Session(annee, code);
		int indice = l.indexOf(s);

		if(indice != -1)
		{
			s = (Session) l.get(indice);
			return s.nbInscriptions();
		}

		return 0;
	}


	public static double pourcentage (LinkedList l, char sexe)
	{
		int nbMale = 0,
			totalInsc = 0;
		for (int i = 0 ; i < l.size(); i++)
		{
			Session temp = (Session) l.get(i);
			nbMale += temp.nbSexe (sexe);
			totalInsc += temp.nbInscriptions();
		}

		return (double) nbMale/totalInsc;
	}


	public static void supprimer (LinkedList l, String type)
	{
		for (int i = 0 ; i < l.size(); i++)
		{
			Session temp = (Session) l.get(i);
			temp.supprimerType(type);
		}
	}

	public static int nbInscriptions(LinkedList l, String sigle)
	{
		int compte = 0;
		for (int i = 0 ; i < l.size(); i++)
		{
			Session temp = (Session) l.get(i);
			compte += temp.nbSigle(sigle);
		}

		return compte;
	}


	public static void afficherInscriptions(LinkedList l, String sigle, int annee, int code)
	{

		Session s = new Session (annee, code);
		int indice = l.indexOf(s);

		if(indice != -1)
		{
			s = (Session) l.get(indice);
			Vector v = s.getInscriptions("IFT1160");

			for(int i=0; i<v.size(); i++)
				System.out.println((Inscription) v.get(i));
		}
	}

	public static void listInscriptions(LinkedList l, String codePerm)
	{
		LinkedList list = new LinkedList();

		for(int i = 0; i < l.size(); i++)
		{
			Session s = (Session) l.get(i);
			s.getInscriptions(list, codePerm);
		}

		if(list.size() != 0 )
		{
			System.out.println("\n************************************\n\n" +
							   "Voici les inscriptions de l'etudiant " + codePerm + " : ");

			for(int i= 0 ; i < list.size(); i++)
			{
				Inscription temp = (Inscription) list.get(i);
				System.out.println(temp);
			}
		}
		else
		{
			System.out.println("\n************************************");
			System.out.println("L'etudiant " + codePerm + " n'est inscrit a aucun cours.");
		}
	}

}