import java.util.*;
import java.io.*;

public class TestPerformance
{

	public static void main (String [] args)
		throws IOException
	{

		long c;

		// TACHE 1 :
		Vector v = new Vector();
		LinkedList l = new LinkedList();

		c = System.currentTimeMillis();
		listInscriptions(v, "inscript.txt");
		System.out.println("Temps requis pour executer l'operation listInscriptions (v): " +
							(System.currentTimeMillis() - c) + " ms");

		c = System.currentTimeMillis();
		listInscriptions(l, "inscript.txt");
		System.out.println("Temps requis pour executer l'operation listInscriptions (l): " +
							(System.currentTimeMillis() - c) + " ms");


		// TACHE 2 :



	}

	public static void listInscriptions(List c, String nomFichier)
		throws IOException
	{

		FileReader fr = null; // initialiser pour Java
		boolean existeFichier = true ; // à ajuster après
		LinkedList mesSessions = new LinkedList();

		// essayer de LOCALISER le fichier à partir de son nom
		try {
			fr = new FileReader (nomFichier) ;
		}
		// intercepter l'erreur si le fichier n'existe pas
		catch ( java.io.FileNotFoundException erreur) {
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

				c.add(new Inscription(codePerm, sigle, type));
				uneLigne = entree.readLine();
			}

			entree.close();
		}
	}


	public static void recherchesIndexOf(List c, String nomFichier)
		throws IOException
	{

		FileReader fr = null; // initialiser pour Java
		boolean existeFichier = true ; // à ajuster après
		LinkedList mesSessions = new LinkedList();

		// essayer de LOCALISER le fichier à partir de son nom
		try {
			fr = new FileReader (nomFichier) ;
		}
		// intercepter l'erreur si le fichier n'existe pas
		catch ( java.io.FileNotFoundException erreur) {
			System.out.println("Probleme d'ouvrir le fichier " + nomFichier);
			existeFichier = false ; // ajuster
		}

		if (existeFichier)
		{
			BufferedReader entree = new BufferedReader(fr);
			boolean finFichier = false ;
			String uneLigne = entree.readLine();

			while ( !finFichier && uneLigne != null)
			{
				String codePerm = une ligne;
				int indice = c.indexOf(new Inscription(codePerm));
				uneLigne = entree.readLine();
			}

			entree.close();
		}
	}

}