/*
 * Créé le 7 février 2005
 *
 * Auteur : Janice Ng
 * Source : TestFilms.java
 */

import java.io.*;
import java.util.*;

public class TestFilms {

	

	/**
	 * Lecture du fichier des acteurs et remplissage
	 * d'un tableau d'acteurs présents dans le fichier.
	 *
	 * @param filename : le nom du fichier à lire
	 * @param tabActeurs : le tableau qui contien les acteurs
	 *
	 * @return le nombre d'acteurs dans le fichier
	 * @throws IOException
	 */
	static int readFichierActeursAAAAAAAAAAAAAAAAAAA(String filename, Acteur [] tabActeurs)
		throws IOException
	{
		FileReader fr = null;
//		List<Acteur> l = new LinkedList<Acteur>();

		try {
		    fr = new FileReader(filename);
		} catch (Exception e) {
		    System.out.println("Probleme d'ouverture du fichier " + filename);
		}

		BufferedReader inFile = new BufferedReader(fr);
		String line = inFile.readLine().trim();

		int nbActeurs = 0;

		while (line != null)
		{
			StringTokenizer st = new StringTokenizer(line);
			int numID = Integer.parseInt(st.nextToken());
			String nomActeur = "";

			while(st.hasMoreTokens())
				nomActeur += st.nextToken() + " ";

			Acteur a = new Acteur (numID, nomActeur);

			tabActeurs[nbActeurs++] = a;
			line = inFile.readLine();
		}
		
		/*ActeurParent ap = new Acteur(555, "Janice");
		System.out.println("HERE IS THE NEW ACTEUR PARENT : " + ap);*/

		inFile.close();
		return nbActeurs;
	}

	/**
	 * Lire le fichier des films et remplissage d'un
	 * tableau des films présents dans le fichier.
	 *
	 * @param filename : nom du fichier à lire
	 * @param tabFilms : tableau des films à remplir
	 * @param tabActeurs : tableau des acteurs déjà rempli
	 * @param nbActeurs : nombre d'acteurs présents dans le fichier "acteurs.txt"
	 *
	 * @return le nombre de films présents dans le fichier
	 * @throws IOException
	 */
	static int readFichierFilms (String filename, Film tabFilms[],
							     Acteur tabActeurs[], int nbActeurs)
		throws IOException
	{
		FileReader fr = null;

		try {
		    fr = new FileReader(filename);
		} catch (FileNotFoundException e) {
		    System.out.println("Probleme d'ouverture du fichier " + filename);
		}

		BufferedReader inFile = new BufferedReader(fr);
		String line = inFile.readLine().trim(); // première ligne pour l'annee et le titre du film

		int nbFilms = 0;

		while (line != null)
		{
			StringTokenizer st = new StringTokenizer(line);
			int annee = Integer.parseInt(st.nextToken());
			String title = "";

			while(st.hasMoreTokens())
				title += st.nextToken() + " ";

			Film f = new Film (title, annee);

			line = inFile.readLine(); // deuxième ligne pour les acteurs du film
			st = new StringTokenizer(line, ",");

			while(st.hasMoreTokens())
			{
				int idTemp = Integer.parseInt(st.nextToken());
				if( ! f.aUnRole(idTemp))
				{
					Acteur a = obtenirActeur(tabActeurs, nbActeurs, idTemp);
					if(a != null)
						f.addActeur(a);
				}
			}

			tabFilms[nbFilms++] = f;
			line = inFile.readLine();
		}

		inFile.close();
		return nbFilms;
	}

	/**
	 * Rechercher un acteur dans le tableau des acteurs
	 * selon un numéro id.
	 *
	 * @param tabAct : le tableau des acteurs
	 * @param nbAct : le nombre effectif d'acteurs lu
	 * 				  dans le fichier "acteurs.txt"
	 * @param id : le num ID de l'acteur à rechercher
	 * @return l'Acteur représenté par le num ID reçu
	 */
	static Acteur obtenirActeur(Acteur tabAct [], int nbAct, int id)
	{
		for(int i = 0; i < nbAct; i++)
		{
			if(tabAct[i].getID() == id)
				return tabAct[i];
		}
		return null;

	}

	/**
	 * Trier le tableau des films selon leur années
	 * de sortie.
	 *
	 * @param lesFilms : le tableau des films
	 * @param nbFilms : le nombre effectif de films lu
	 * 					dans le fichier "films.txt"
	 */
	static void trierLesFilms (Film lesFilms[], int nbFilms)
	{
		for (int i = 0; i < nbFilms ; i++)
		{
			int indMin = i;
			for (int j = i+1; j < nbFilms; j++)
				if (lesFilms[j].getAnnee() < lesFilms[indMin].getAnnee())
					indMin = j;

			if (indMin!= i)
				permuter(lesFilms, i, indMin);
		}
	}

	/**
	 * Permuter deux élements d'un tableau.
	 *
	 * @param lesFilms : le tableau des films
	 * @param i : position 1 pour laquelle on veut inverser avec la position 1
	 * @param j : position 2 pour laquelle on veut inverser avec la position 2
	 */
	static void permuter (Film lesFilms [] , int i, int j) {

   
		Film temp = lesFilms[i];
		lesFilms[i] = lesFilms[j];
		lesFilms[j] = temp;

	} // end of permuter

	/**
	 * Corriger le nom d'un acteur qui comporte une faute
	 * dans le fichier "acteurs.txt".
	 *
	 * @param lesActeurs : le tableau des acteurs
	 * @param nbActeurs : le nombre effectif d'acteurs lu dans le fichier "acteurs.txt"
	 * @param nomAvecFaute : le nom avec erreur de l'acteur
	 * @param nomSansFaute : le nom sans erreur de l'acteur
	 */
	static void corrigerFaute (Acteur lesActeurs [], int nbActeurs,
							   String nomAvecFaute, String nomSansFaute)
	{
	
    		
		for(int i = 0; i < nbActeurs; i++)
		{
			if(lesActeurs[i].getNom().trim().equalsIgnoreCase(nomAvecFaute))
			{
				lesActeurs[i].setNom(nomSansFaute);
				return;
			}
		}
	}

	/**
	 * Rechercher le film qui comporte le plus d'acteurs.
	 *
	 * @param lesFilms : le tableau des films
	 * @param nbFilms : le nombre effectif de films lu dans le fichier "films.txt"
	 *
	 * @return l'objet film qui comporte le plus d'acteurs
	 */
	static Film rechercherFilmMax (Film lesFilms[], int nbFilms)
	{
		
		int position = 0;
		int nbMax = 0;

		for(int i = 0; i < nbFilms; i++)
		{
			if(lesFilms[i].getNbActeurs()  > nbMax) {
				nbMax = lesFilms[i].getNbActeurs();
				position = i;
			}
		}
		return lesFilms[position];
	}


/**
	 * La méthode main.
	 *
	 * @param args : le tableau des arguments
	 */
	public static void main(String[] args)
	{
		try {
			
		final int MAX_ACTEURS = 50;
		final int MAX_FILMS = 20;

		//System.out.println("11111111111111111 TESTING");

		Acteur tabActeurs [] = new Acteur[MAX_ACTEURS];
		Film tabFilms [] = new Film[MAX_FILMS];
		
		//System.out.println("222222222222222222222222 TESTING ");
		

	//	3.a) Remplir un tableau d'objets Acteur
		int nbAct = readFichierActeursAAAAAAAAAAAAAAAAAAA("acteurs.txt", tabActeurs);

		// 3.b) Remplir un tableau d'objets Film
	int nbMovies = readFichierFilms ("films.txt", tabFilms, tabActeurs, nbAct);

		// 3.c) Trier les films selon leur année de sortie
	//	trierLesFilms (tabFilms, nbMovies);

		// 3.d) Corriger le nom de l'actrice "Cameron Diaz"
		corrigerFaute(tabActeurs, nbAct, "Cameron Dioz", "Cameron Diaz");

		// 3.e) Afficher le film comportant le plus d'acteurs
		/*System.out.println("Le film comportant le plus d'acteurs :\n" +*/
				 		   //rechercherFilmMax(tabFilms, nbMovies)/*)*/;

		// 3.f) Afficher l'acteur qui n'apparaît pas dans aucun film
		/*System.out.println("L'acteur qui n'apparaît dans aucun film :\n" +*/
						   //rechercherActeurNul (tabActeurs, tabFilms, nbAct, nbMovies)/* + "\n")*/;

		// 3.g) Supprimer les actrices "Cameron Diaz" et "Uma Thurman"
	//	nbAct = supprimerActeur (tabActeurs, tabFilms, nbAct, nbMovies, "Cameron Diaz");
//	nbAct = supprimerActeur (tabActeurs, tabFilms, nbAct, nbMovies, "Uma Thurman");

		/*System.out.println("=====================================\nAFFICHAGE DU TABLEAU :");
		// 3.h) Afficher le contenu du tableau des films
		for(int i = 0; i < nbMovies; i++)
			System.out.println(tabFilms[i]);
		System.out.println("=====================================");*/
	//	trierLesFilms (tabFilms, nbMovies);
	//	nbAct = supprimerActeur (tabActeurs, tabFilms, nbAct, nbMovies, "Keanu Reeves");
		/*System.out.println("=====================================\nAFFICHAGE DU TABLEAU #2 :");
		// 3.h) Afficher le contenu du tableau des films
		for(int i = 0; i < nbMovies; i++)
			System.out.println(tabFilms[i]);
		System.out.println("=====================================");*/
			
			
			
		}
		catch (Exception ex) {
			System.out.println("Affichage de l'erreur *************");
                ex.printStackTrace(System.err);
            }
		
		
}





	/**
	 * Rechercher l'acteur qui ne joue dans aucun film.
	 *
	 * @param lesActeurs : le tableau des acteurs
	 * @param lesFilms : le tableau des films
	 * @param nbActeurs : le nombre effectif d'acteurs lu dans le fichier "acteurs.txt"
	 * @param nbFilms : le nombre effectif de films lu dans le fichier "films.txt"
	 *
	 * @return l'objet Acteur qui ne joue dans aucun film
	 */
	static Acteur rechercherActeurNul (Acteur lesActeurs[], Film lesFilms[],
									   int nbActeurs, int nbFilms)
	{
		boolean joueUnRole = false;

		for(int i = 0; i < nbActeurs; i++)
		{
			joueUnRole = false;

			for(int j = 0; j < nbFilms && !joueUnRole; j++)
			{
				if( lesFilms[j].aUnRole(lesActeurs[i].getID()) )
					joueUnRole = true;
			}

			if(!joueUnRole)
				return lesActeurs[i];
		}
		return null;
	}

	/**
	 * Supprimer l'acteur dont le nom est reçu en paramètre.
	 *
	 * @param lesActeurs : le tableau des acteurs
	 * @param lesFilms : le tableau des films
	 * @param nbActeurs : le nombre effectif d'acteurs dans le fichier "acteurs.txt"
	 * @param nbFilms : le nombre effectif de films dans le fichier "films.txt"
	 * @param nom : le nom de l'acteur à supprimer
	 *
	 * @return le nombre d'acteurs après suppression
	 */

	static int supprimerActeur (Acteur lesActeurs[], Film lesFilms[],
								int nbActeurs, int nbFilms, String nom)
	{
		boolean trouve = false;
		int idTemp = -1;

		for(int i = 0 ; !trouve && i < nbActeurs; i++)
		{
			if(lesActeurs[i].getNom().trim().equalsIgnoreCase(nom))
			{
				idTemp = lesActeurs[i].getID();
				for(int j = i; j < nbActeurs-1; j++)
					lesActeurs[j] = lesActeurs[j+1];
				nbActeurs--;
			}
		}

		if(idTemp != -1)
		{
			for(int i = 0; i < nbFilms; i++)
			{
				if( lesFilms[i].aUnRole(idTemp) )
					lesFilms[i].deleteActeur(idTemp);
			}
		}
		return nbActeurs;
	}

	

}
