/* ============================================

	IFT1170 - Hiver05
	TP1 - Numéro 2
	Source : Jeu.java

   ============================================*/


public class Jeu
{

	private final int NB_CARTES = 5;
	private final int NB_VALEURS = 13;
	private final int NB_COULEURS = 4;

	private Carte [] lesCartes;      // tableau contenant les 5 cartes
	private int [] lesOccurrences;   // occurrences des 13 valeurs d'un paquet de cartes selon le jeu
	private int [] lesCouleurs;      // couleurs des 5 cartes formant le jeu

    // constructeur recevant les cinq cartes d'un jeu
	public Jeu (Carte c1, Carte c2, Carte c3, Carte c4, Carte c5)
	{
		lesOccurrences = new int[NB_VALEURS];
		lesCouleurs = new int [NB_CARTES];
		lesCartes = new Carte[NB_CARTES];

		// initialisation du tableau des occurrence
		for(int i = 0; i < NB_VALEURS; i++)
			lesOccurrences[i] = 0;

		// initialisation du tableau des couleurs des cartes
		for(int i = 0; i < NB_COULEURS; i++)
			lesCouleurs[i] = 0;

		// initialisation du tableau des cartes
		lesCartes[0] = c1;
		lesCartes[1] = c2;
		lesCartes[2] = c3;
		lesCartes[3] = c4;
		lesCartes[4] = c5;

		determinerOccurrences();
		determinerCouleurs();
	}

	// déterminer les occurrences de chaque valeur pour un jeu
	private void determinerOccurrences ()
	{

		for(int i = 0; i < NB_CARTES; i++)
		{
			int position = lesCartes[i].getValeur() - 2;
			lesOccurrences[position]++;
		}

	}

	// déterminer les couleurs présentes parmi les cinq cartes du jeu
	private void determinerCouleurs ()
	{

		for(int i = 0; i < NB_CARTES; i++)
		{
			int position = lesCartes[i].getCouleur() - 1;
			lesCouleurs[position]++;
		}
	}

	// déterminer si le jeu contient "paire"
	private boolean paire ()
	{
		boolean estPaire = false;
		for(int i = 0 ; i < NB_VALEURS && !estPaire; i++)
			if(lesOccurrences[i] == 2) // deux cartes de même valeur
				estPaire = true;
		return estPaire;
	}

	// déterminer si le jeu contient "doublePaire"
	private boolean doublePaire ()
	{
		int compteur = 0;

		for(int i = 0; i < NB_VALEURS && compteur < 2; i++)
			if(lesOccurrences[i] == 2)
				compteur++;
		if(compteur == 2)
			return true;
		return false;
	}

	// déterminer si le jeu contient "brelan"
	private boolean brelan ()
	{
		boolean estTriple = false;
		for(int i = 0; i < NB_VALEURS && !estTriple; i++)
			if(lesOccurrences[i] == 3)
				estTriple = true;
		return estTriple;
	}

	// déterminer si le jeu contient cinq cartes de valeur consécutive
	private boolean cinqConsecutif ()
	{

		// au moins une occurrence est supérieure à 1
		for(int i = 0; i < NB_VALEURS; i++)
			if(lesOccurrences[i] > 1)
				return false;

		// toutes les occurrences sont égales à 0 ou 1
		for(int i = 0; i < NB_VALEURS; i++)
		{
			if(lesOccurrences[i] == 1)
			{
				for(int j = i; j < i+5; j++)
					if(lesOccurrences[j] != 1)
						return false;
				return true; // une suite de cinq valeurs consécutives est présente
			}
		}

		return false;

	}

	// déterminer si le jeu contient "quinte"
	private boolean quinte ()
	{
		if(cinqConsecutif() && !couleur())
			return true;
		return false;
	}

	// déterminer si le jeu contient "couleur"
	private boolean couleur ()
	{
		for(int i=0; i < NB_COULEURS; i++)
			if(lesCouleurs[i] != 0 && lesCouleurs[i] != 5)
				return false;
		return true;
	}

	// déterminer si le jeu contient "main plaine"
	private boolean mainPlaine ()
	{
		if(paire() && brelan())
			return true;
		return false;
	}

	// déterminer si le jeu contient "carré"
	private boolean carre ()
		{
			boolean estCarre = false;
			for(int i = 0 ; i < NB_VALEURS && !estCarre; i++)
				if(lesOccurrences[i] == 4) // quatre cartes de même valeur
					estCarre = true;
			return estCarre;
	}


	// déterminer si le jeu contient "quinte flush"
	private boolean quinteFlush ()
	{
		if( cinqConsecutif() && couleur() && lesOccurrences[12] == 0 )
			return true;
		return false;
	}

	// déterminer si le jeu contient "royale"
	private boolean royale ()
	{
		if( cinqConsecutif() && couleur() && lesOccurrences[12] == 1)
			return true;
		return false;
	}

	// déterminer la force du jeu
	public int force ()
	{
		if( royale() ) return 9;
		else if( quinteFlush() ) return 8;
		else if( carre() ) return 7;
		else if( mainPlaine() ) return 6;
		else if( couleur() ) return 5;
		else if( quinte() ) return 4;
		else if( brelan() ) return 3;
		else if( doublePaire() ) return 2;
		else if( paire() ) return 1;
		else return 0;
	}

	// afficher les informations concernant le jeu
	public void afficher()
	{

		for(int i = 0; i < NB_CARTES; i++)
		{
			lesCartes[i].afficher();
			System.out.print(" ");
		}
		switch (force())
		{
			case 0: System.out.println(" - Aucune combinaison"); break;
			case 1: System.out.println(" - Paire"); break;
			case 2: System.out.println(" - Double paire"); break;
			case 3: System.out.println(" - Brelan"); break;
			case 4: System.out.println(" - Quinte"); break;
			case 5: System.out.println(" - Couleur"); break;
			case 6: System.out.println(" - Main plaine"); break;
			case 7: System.out.println(" - Carre"); break;
			case 8: System.out.println(" - Quinte flush"); break;
			case 9: System.out.println(" - Quinte royale"); break;
		}

	}

}