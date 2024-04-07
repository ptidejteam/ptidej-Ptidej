/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package modec.test.nonpattern.example.TestFilms;

/*
 * Créé le 7 février 2005
 *
 * Auteur : Janice Ng
 * Source : Film.java
 */
 


public class Film {

	private final int MAX_ACTEURS = 10;
	private String titre; // titre du film
	private int annee;	  // annee du film
	private Acteur lesActeurs []; // tableau contenant les acteurs du film
	private int nbActeurs; // nombre actuel d'acteurs dans le film

	public Film(){
	}
	/**
	 * Contructeur qui reçoit le titre et
	 * l'année du film.
	 *
	 * @param titre : titre du film
	 * @param annee : année du film
	 */
	public Film (String titre, int annee)
	{		
//		FileReader fr = null;	
//		try {
//		    fr = new FileReader("toto.txt");		   
//		} catch (Exception e) {
//		    System.out.println("Probleme d'ouverture du fichier " + "toto.txt");
//		}
//		finally
//		{
//			int i = 0;
//			while(i < 2)
//			{	
						
				//setTitre(titre);
				this.titre = titre;
				this.annee = annee;
				this.lesActeurs = new Acteur [this.MAX_ACTEURS];
				this.nbActeurs = 0;
				//i++;
//			}	
//		}	
	}

	/**
	 * Méthodes d'accès et de modification
	 */
	public void setTitre (String titre) { this.titre = titre; }
	public void setAnnee (int annee) { this.annee = annee; }
	public void setNbActeurs (int nbActeurs) { this.nbActeurs = nbActeurs; }

	public String getTitre () { return this.titre; }
	public int getAnnee () { return this.annee; }
	public int getNbActeurs() { return this.nbActeurs; }

	/**
	 * Ajouter un acteur.
	 *
	 * @param a : acteur à ajouter au tableau des acteurs
	 */
	public void addActeur (Acteur a)
	{
	
		
		for(int i = 0; i < this.nbActeurs; i++)
		{
			if(this.lesActeurs[i].getID() == a.getID()) // s'assurer que l'acteur à ajouter
				return;							   // n'est pas déjà présent dans le tableau
		}
		this.lesActeurs[this.nbActeurs] = a;
		this.nbActeurs++;
	}

	/**
	 * Supprimer un acteur.
	 *
	 * @param id : numéro ID de l'acteur à supprimer
	 */
	public void deleteActeur (int id)
	{
	
		
		boolean trouve = false;

		for(int i = 0 ; !trouve && i < this.nbActeurs; i++)
		{
			if(this.lesActeurs[i].getID() == id)
			{
				trouve = true;
				for(int j = i; j < this.nbActeurs-1; j++)
					this.lesActeurs[j] = this.lesActeurs[j+1];
				this.nbActeurs--;
			}
		}
	}

	/**
	 * Déterminer si l'acteur dont le ID est reçu en
	 * paramètre a un rôle dans ce film.
	 *
	 * @param id : numéro de l'acteur à identifier
	 * @return true, si l'acteur est présent dans le film
	 * 		   false, sinon
	 */
	public boolean aUnRole (int id)
	{		
	
		boolean trouve = false;

		for(int i = 0; i < this.nbActeurs && !trouve; i++)
		{
			if(this.lesActeurs[i].getID() == id)
				trouve = true;
		}

		return trouve;
	}

	/**
	 * Redéfinition de la méthode toString pour fin d'affichage.
	 */
	public String toString()
	{
	
    		
		String infoFilm = this.titre + "(" + this.annee + ")\n";

		for(int i = 0; i < this.nbActeurs; i++)
			infoFilm += "- " + this.lesActeurs[i].getNom() + "\n";

		return infoFilm;
	}
}
