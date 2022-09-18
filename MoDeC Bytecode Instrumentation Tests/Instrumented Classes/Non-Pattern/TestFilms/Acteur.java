/*
 * Crée le 7 février 2005
 *
 * Auteur : Janice Ng
 * Source : Acteur.java
 */
import java.util.*;

public class Acteur {


	private int id;		// numéro de l'acteur
	private String nom; // nom de l'acteur


	/**
	 * Constructeur qui reçoit l'identificateur
	 * le nom du film.
	 *
	 * @param id : numéro identificateur de l'acteur
	 * @param nom : nom de l'acteur
	 *
	 */
	public Acteur (int id, String nom)
	{
		
		this.id = id;
		this.nom = nom;
	}

	
	/**
	 * Méthodes d'accès et de modification.
	 */
	public void setID (int id) { this.id = id; }
	public void setNom (String nom) { this.nom = nom; }

	public int getID () { return id; }
	public String getNom () { return nom; }

	/**
	 * Redéfinition de la méthode toString pour fin d'affichage.
	 */
	public String toString ()
	{
		return nom;
	}

}
