/*
	IFT1170 - Hiver 2005
	
	Classe pour l'evaluation du TP1 no.2
	(Voir resultat attendu au bas...)
	
	par Charles Lanteigne
	
	
	Jeu de poker simple
	
	(Encapsulation, tableaux d'objets)
*/

public class TesteurPoker
{
	/*
		Construction de quelques couples de Jeux
		Affichage + meilleur
	*/
	public static void main(String[] args)
	{
		Jeu[][] jeux =
		{
			{
				new Jeu(new Carte( 4, 1), new Carte( 7, 3),
						new Carte( 7, 4), new Carte(11, 2),
						new Carte(14, 4)),
		 		new Jeu(new Carte( 3, 1), new Carte( 6, 3),
				 		new Carte( 2, 2), new Carte( 5, 1),
				 		new Carte( 4, 4))
		 	},
		 	{	
		 		new Jeu(new Carte(14, 1), new Carte(10, 1),
			 			new Carte(13, 1), new Carte(11, 1),
			 			new Carte(12, 1)),
			 	new Jeu(new Carte( 9, 3), new Carte( 9, 2),
				 		new Carte( 9, 1), new Carte( 3, 1),
				 		new Carte(13, 2))
		 	},
		 	{	
			 	new Jeu(new Carte(11, 1), new Carte(11, 2),
				 		new Carte(11, 3), new Carte(11, 4),
				 		new Carte( 6, 4)),
			 	new Jeu(new Carte( 2, 4), new Carte( 6, 1),
				 		new Carte(14, 1), new Carte( 6, 3),
				 		new Carte( 2, 2))
		 	},
		 	{	
			 	new Jeu(new Carte( 2, 1), new Carte( 3, 1),
				 		new Carte( 3, 3), new Carte(13, 4),
				 		new Carte(14, 2)),
			 	new Jeu(new Carte(13, 2), new Carte(11, 1),
				 		new Carte( 5, 3), new Carte( 6, 2),
				 		new Carte(11, 4))
		 	},
		 	{	
			 	new Jeu(new Carte( 4, 1), new Carte( 5, 1),
				 		new Carte( 8, 1), new Carte(11, 1),
				 		new Carte(14, 1)),
			 	new Jeu(new Carte( 3, 2), new Carte( 7, 2),
				 		new Carte( 4, 2), new Carte( 5, 2),
				 		new Carte( 6, 2))
		 	},
		 	{	
			 	new Jeu(new Carte(12, 1), new Carte(11, 2),
				 		new Carte(11, 1), new Carte(11, 3),
				 		new Carte(12, 4)),
			 	new Jeu(new Carte( 6, 4), new Carte( 7, 2),
				 		new Carte(13, 3), new Carte(8, 1),
				 		new Carte( 9, 4))
		 	}
		};
		
		// pour chaque partie
		for (int partie = 0; partie < 6; partie++)
		{
			System.out.println("Partie #" + (partie + 1) + ":\n");

			// affichage des jeux
			for (int j = 0; j < 2; j++)
			{
				System.out.print("\tJeu #" + (j + 1) + ": ");
				jeux[partie][j].afficher();
			}
			
			// affichage du vainqueur
			if (jeux[partie][0].force() == jeux[partie][1].force())
				System.out.println("\nLes deux jeux sont egaux !\n");
			else
				System.out.println("\nLe meilleur des deux jeux est le #" +
				(jeux[partie][0].force() > jeux[partie][1].force() ? "1": "2") + "\n");
		}
	}
}

/* Resultat attendu:

Partie #1:

        Jeu #1: 4H, 7C, 7D, JS, AD - Paire
        Jeu #2: 3H, 6C, 2S, 5H, 4D - Quinte

Le meilleur des deux jeux est le #2

Partie #2:

        Jeu #1: AH, 10H, KH, JH, QH - Quite royale
        Jeu #2: 9C, 9S, 9H, 3H, KS - Brelan

Le meilleur des deux jeux est le #1

Partie #3:

        Jeu #1: JH, JS, JC, JD, 6D - Carre
        Jeu #2: 2D, 6H, AH, 6C, 2S - Double paire

Le meilleur des deux jeux est le #1

Partie #4:

        Jeu #1: 2H, 3H, 3C, KD, AS - Paire
        Jeu #2: KS, JH, 5C, 6S, JD - Paire

Les deux jeux sont egaux !

Partie #5:

        Jeu #1: 4H, 5H, 8H, JH, AH - Couleur
        Jeu #2: 3S, 7S, 4S, 5S, 6S - Quinte flush

Le meilleur des deux jeux est le #2

Partie #6:

        Jeu #1: QH, JS, JH, JC, QD - Main pleine
        Jeu #2: 6D, 7S, KC, 8H, 9D - Aucune combinaison

Le meilleur des deux jeux est le #1

*/
