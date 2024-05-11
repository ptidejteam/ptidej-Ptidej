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

public class Acteur {


	private int id;		// numero de l'acteur
	private String nom; // nom de l'acteur


	/**
	 * Constructeur qui reeoit l'identificateur
	 * le nom du film.
	 *
	 * @param id : numero identificateur de l'acteur
	 * @param nom : nom de l'acteur
	 *
	 */
	public Acteur (int id, String nom)
	{
		
		this.id = id;
		this.nom = nom;
	}

	
	/**
	 * Methodes d'acces et de modification.
	 */
	public void setID (int id) { this.id = id; }
	public void setNom (String nom) { this.nom = nom; }

	public int getID () { return this.id; }
	public String getNom () { return this.nom; }

	/**
	 * Redefinition de la methode toString pour fin d'affichage.
	 */
	public String toString ()
	{
		return this.nom;
	}

}
