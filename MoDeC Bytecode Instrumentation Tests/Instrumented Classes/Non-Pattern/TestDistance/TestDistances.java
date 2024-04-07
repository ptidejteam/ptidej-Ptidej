import java.io.*;
import java.util.*;

public class TestDistances
{

	public static void main(String args[])
		throws IOException
	{

		Vector lesDistances = lireRemplir("distances.txt");
		lesDistances.add(new Distance("Quebec", "Montreal", 270));

		afficherVecteur(lesDistances);


	}

	public static Vector lireRemplir(String nomFichier)
		throws IOException
	{
		FileReader fr = null; // initialiser pour Java
		boolean existeFichier = true ; // à ajuster après
		Vector mesDistances = new Vector();

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

				int indice1 = uneLigne.indexOf(";");
				int indice2 =  uneLigne.indexOf(";", indice1+1);

				String depart = uneLigne.substring(0, indice1);
				String arrivee = uneLigne.substring(indice1+1, indice2);
				int km = Integer.parseInt(uneLigne.substring(indice2+1));

				Distance temp = new Distance(depart, arrivee, km);
				mesDistances.add(temp);

				uneLigne = entree.readLine();
			}
			entree.close();
	 	}

	 	return mesDistances;
	}

	public static void afficherVecteur(Vector vect)
	{
		for(int i=0;  i < vect.size(); i++)
			System.out.println(vect.get(i));
	}



}