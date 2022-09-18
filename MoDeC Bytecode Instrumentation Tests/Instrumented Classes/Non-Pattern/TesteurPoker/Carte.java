/* ============================================

	IFT1170 - Hiver05
	TP1 - Numéro 2
	Source : Carte.java

   ============================================*/

public class Carte
{

	private int valeur;   // entre 2 et 14
	private int couleur;  //   1  ,         2  ,        3     ou     4
	private String serie; // H (coeur), S (pique), C (trefle) ou D (carreau)


	// constructeur recevant la valeur et la couleur de la carte
	public Carte (int val, int coul)
	{
		valeur = val;
		couleur = coul;
		switch (couleur)
		{
			case 1  : serie = "H"; break; // Heart
			case 2  : serie = "S"; break; // Spade
			case 3  : serie = "C"; break; // Club
			case 4  : serie = "D"; break; // Diamond
			default : serie = "";
		}
	}

	// méthode pour afficher les informations de la carte
	public void afficher () {

		String information;

		switch (valeur)
		{
			case 2  : information = "2"; break;
			case 3  : information = "3"; break;
			case 4  : information = "4"; break;
			case 5  : information = "5"; break;
			case 6  : information = "6"; break;
			case 7  : information = "7"; break;
			case 8  : information = "8"; break;
			case 9  : information = "9"; break;
			case 10 : information = "10"; break;
			case 11 : information = "J"; break;
			case 12 : information = "Q"; break;
			case 13 : information = "K"; break;
			case 14 : information = "A"; break;
			default : information = "";
		}

		information += serie;
		System.out.print(information);

	}


	// méthodes d'accès
	public int getValeur () { return valeur; }
	public int getCouleur ()  { return couleur; }

	// méthodes modificatrices
	public void setValeur (int val) { valeur = val; }
	public void setCouleur (int coul) { couleur = coul; }


}