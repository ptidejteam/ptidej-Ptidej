/*
 * Cree le 7 fevrier 2005
 *
 * Auteur : Janice Ng
 * Source : Film.java
 */
 
import java.io.*;

public class Film {

	private final int MAX_ACTEURS = 10;
	private String titre; // titre du film
	private int annee;	  // annee du film
	private Acteur lesActeurs []; // tableau contenant les acteurs du film
	private int nbActeurs; // nombre actuel d'acteurs dans le film

	public Film(){
	}
	/**
	 * Contructeur qui reeoit le titre et
	 * l'annee du film.
	 *
	 * @param titre : titre du film
	 * @param annee : annee du film
	 */
	public Film (String titre, int annee)
	{		
		FileReader fr = null;	
		try {
		    fr = new FileReader("toto.txt");		   
		} catch (Exception e) {
		    System.out.println("Probleme d'ouverture du fichier " + "toto.txt");
		}
		finally
		{
			int i = 0;
			while(i < 2)
			{	
						
				//setTitre(titre);
				this.titre = titre;
				this.annee = annee;
				lesActeurs = new Acteur [MAX_ACTEURS];
				nbActeurs = 0;
				i++;
			}	
		}	
	}

	/**
	 * Methodes d'acces et de modification
	 */
	public void setTitre (String titre) { this.titre = titre; }
	public void setAnnee (int annee) { this.annee = annee; }
	public void setNbActeurs (int nbActeurs) { this.nbActeurs = nbActeurs; }

	public String getTitre () { return titre; }
	public int getAnnee () { return annee; }
	public int getNbActeurs() { return nbActeurs; }

	/**
	 * Ajouter un acteur.
	 *
	 * @param a : acteur e ajouter au tableau des acteurs
	 */
	public void addActeur (Acteur a)
	{
	
		
		for(int i = 0; i < nbActeurs; i++)
		{
			if(lesActeurs[i].getID() == a.getID()) // s'assurer que l'acteur e ajouter
				return;							   // n'est pas deje present dans le tableau
		}
		lesActeurs[nbActeurs] = a;
		nbActeurs++;
	}

	/**
	 * Supprimer un acteur.
	 *
	 * @param id : numero ID de l'acteur e supprimer
	 */
	public void deleteActeur (int id)
	{
	
		
		boolean trouve = false;

		for(int i = 0 ; !trouve && i < nbActeurs; i++)
		{
			if(lesActeurs[i].getID() == id)
			{
				trouve = true;
				for(int j = i; j < nbActeurs-1; j++)
					lesActeurs[j] = lesActeurs[j+1];
				nbActeurs--;
			}
		}
	}

	/**
	 * Determiner si l'acteur dont le ID est reeu en
	 * parametre a un rele dans ce film.
	 *
	 * @param id : numero de l'acteur e identifier
	 * @return true, si l'acteur est present dans le film
	 * 		   false, sinon
	 */
	public boolean aUnRole (int id)
	{		
	
		boolean trouve = false;

		for(int i = 0; i < nbActeurs && !trouve; i++)
		{
			if(lesActeurs[i].getID() == id)
				trouve = true;
		}

		return trouve;
	}

	/**
	 * Redefinition de la methode toString pour fin d'affichage.
	 */
	public String toString()
	{
	
    		
		String infoFilm = titre + "(" + annee + ")\n";

		for(int i = 0; i < nbActeurs; i++)
			infoFilm += "- " + lesActeurs[i].getNom() + "\n";

		return infoFilm;
	}
}
