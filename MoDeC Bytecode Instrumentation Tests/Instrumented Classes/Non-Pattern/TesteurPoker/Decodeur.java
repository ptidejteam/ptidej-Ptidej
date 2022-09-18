public class Decodeur
{

	public static void main(String [] args)
	{
		String s1 = "Pf}nZf}u\\`";
		String temp;

		caracteresEnEntier(s1);
		caracteres64(s1);
		temp = caracteresBinaire(s1);
		imprimerEnBloc8(temp);

	}

	static void caracteresEnEntier(String s)
	{
		System.out.println("Chaque caractere en entier : ");
		for(int i = 0; i < s.length(); i++)
			System.out.print((int) s.charAt(i) + " ") ;
	}

	static void caracteres64(String s)
	{
		System.out.println("\nChaque entier - 64 : ");
		for(int i = 0; i < s.length(); i++)
			System.out.print((int) (s.charAt(i)) - 64 + " ");
	}

	static String caracteresBinaire(String s)
	{

			String binaire = "";
			String temp = Integer.toBinaryString((int) (s.charAt(0)) - 64);

			System.out.println("\nChaque entier en binaire : ");
			for(int i = 1; i < s.length(); i++)
			{
				while( temp.length() < 6 )
					temp = "0".concat(temp);
				System.out.print( temp + " ");
				binaire += temp;
				temp = Integer.toBinaryString((int) (s.charAt(i)) - 64);
				System.out.print(s.charAt(i));
			}

			return binaire;
	}

	static void imprimerEnBloc8 (String s)
	{

		final int NB_BLOC = 8;
		int nbBoucles = s.length() / NB_BLOC;
		int reste = s.length() % NB_BLOC;
		int position = 0;


		System.out.println("\nRegrouper en blocs de 8 bits");
		for(int i = 0; i < nbBoucles; i++)
		{
			System.out.print(s.substring(position, position+=NB_BLOC) + " ");
		}
		System.out.println(s.substring( s.length() - reste));
	}



}