package consensus.algorithms;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class Instance {
	public Set<Permutation> A; //ensemble de depart

	public int n;
	public int m;

	public int[][] tabD;//distance matrix ("right" table)
	public boolean[][] tabC;//matrice de contraintes: tabC[i][j]=true == (i<j) ds contraintes
	public boolean tabCprime[][]; //copy of the constraints tabC
	public int[][] tabPairStatus;// tabPairStatus[i-1][j-1]: i<j:  5 confirmed (constraint), 4 present in all SA median, 3 ambiguous, 2 abscent, 1 infirmed, 0?

	public boolean isSubInstance;
	public Instance parentInstance;
	public int[] tableSubPermu;
	public int[] tableInvertSubPermu;

	public int simple_lower_bound;
	public int add3cyles_lower_bound;

	public int SA_upper_bound;
	public int BestOfA_upper_bound;
	public int Borda_upper_bound;
	public int Copeland_upper_bound;
	public int Opt2_upper_bound;
	public int Opt3_upper_bound;
	public int Borda_opt3_upper_bound;
	public int Copeland_opt3_upper_bound;
	public Permutation Copeland_opt3_permutation;
	public int CircMvtLocalSearch_upper_bound;
	public int MedianGame_upper_bound;
	public int EucledianArccosMedianRn_upper_bound;

	public int natural_lower_bound;
	public Permutation lowerBoundBestOrderOfExploration;
	public int cycles3_lower_bound;
	public int cycles4_lower_bound;
	public int cyclesN_wConstraints_lower_bound;
	public int cyclesN_wConstraints_guided_lower_bound;
	public int cycles3_maxFlow_lower_bound;

	public int best_lower_bound;
	public int best_upper_bound;
	public int first_gap;
	public double first_relative_gap;
	public boolean isOptimal;
	public Set<Permutation> Medians; //ensemble mediane de A

	public int cplexMIPresult;
	public double cplexLPresult;

	//stats
	public int minDist;
	public int maxDist;
	public double avgDist;
	public double minDist_normalized;
	public double maxDist_normalized;
	public double avgDist_normalized;

	//for branch and bound
	public boolean[][] tabDuos;//matrice de contraintes par duos, equivalents des contraintes G/D, permet de savoir si x,y est permis dans la solution optimale
	public boolean[][][] tabTriplets;//matrice de contraintes par triplet, permet de savoir si x,y,z est permis dans la solution optimale
	public List<HashSet<Integer>> contraintesG;
	public List<HashSet<Integer>> contraintesD;
	public boolean[][] tabContraintesG;
	public boolean[][] tabContraintesD;
	public HashMap<BitVector, Integer> topScores;//top scores ascending
	public HashMap<BitVector, Integer> subScores;//sub scores
	public boolean[][] permissionSpatiale;
	public int nbExploredNodesBnB = 0;
	public int nbMaxNodesBnB = 0;

	public int[][][] tabTriangleAdd;
	public int[][] tabNbTriangleAssocie;
	public List<Integer>[][] tabTriangleAssocie;
	public int[] apport;
	//MOT3e
	public boolean[][] toCheck;
	public int[][] tabDelta;
	public int[][][] tabDELTA;
	public int[][] tabMaxDELTA;
	//fin MOT3e
	public double nbRejectGD = 0.0;
	public double nbRejectTriplets = 0.0;
	public double nbRejectMOT = 0.0;
	public double nbRejectMOT4 = 0.0;
	public double nbRejectSemiDistBI = 0.0;
	public double nbRejectSemiDistBIadd = 0.0;
	public double nbRejectTopScores = 0.0;
	public double nbRejectSubScores = 0.0;
	public double nbRejectSpatial = 0.0;
	public double nbReject = 0.0;

	//for elections names
	public List<String> rankingCandidatesNames;
	//public List<String> rankingNames;

	//constructeurs
	public Instance() {
		this.A = new HashSet<Permutation>();

		//beginningThinks();
	}

	public Instance(final int nbPermu, final int nbElements) {
		this.A = new HashSet<Permutation>();

		Permutation pi = null;
		while (this.A.size() < nbPermu) {
			pi = this.createARandom(nbElements);
			if (!this.A.contains(pi)) {
				this.A.add(pi);
			}
		}

		this.beginningThinks();
	}

	public Instance(final Set<Permutation> SetPermu) {
		this.A = new HashSet<Permutation>();
		for (final Permutation pi : SetPermu) {
			this.A.add(pi);
		}

		this.beginningThinks();
	}

	/*
	 * exemple {[7,1,5,4,9,8,3,2,10,6],[6,8,4,10,5,7,1,2,9,3],[2,1,8,5,7,4,10,3,9,6]}
	 */
	public Instance(final String line) {
		char u;
		int profondeur = 0;
		String nombreString = "";
		int[] a = null;
		List<Integer> permuEnCours = null;
		Permutation pi;

		//A = new HashSet<Permutation>();
		//a faire (format 1 et 2... puis save)
		if (line.length() != 0) {
			for (int i = 0; i < line.length(); i++) {
				u = line.charAt(i);
				//System.out.println(u);
				if (u == '{' || u == '[') {
					profondeur++;
					if (profondeur == 1) {
						this.A = new HashSet<Permutation>();
					}
					else if (profondeur == 2) {
						pi = null;
						permuEnCours = new ArrayList<Integer>();
						nombreString = "";
					}
					else {
						System.out.println("erreur de lecture");
					}

				}
				else if (u == '}' || u == ']') {
					profondeur--;
					if (profondeur == 0) {

						//Instance myInstance =new Instance(A);
						this.beginningThinks();

					}
					else if (profondeur == 1) {
						permuEnCours.add(Integer.parseInt(nombreString));
						a = new int[permuEnCours.size()];
						for (int j = 0; j < permuEnCours.size(); j++) {
							a[j] = permuEnCours.get(j);
						}
						pi = new Permutation(a);
						this.A.add(pi);
					}
					else if (profondeur == 2) {
						permuEnCours.add(Integer.parseInt(nombreString));
					}
					else {
						System.out.println("erreur de lecture");
					}
				}
				else if (u == ',') {
					if (profondeur == 2) {
						permuEnCours.add(Integer.parseInt(nombreString));
						nombreString = "";
					}
					else {
						//rien
					}
				}
				else {
					if (profondeur == 2) {
						nombreString += u;
					}
				}

			}

		}

	}

	public void addSolverPermutation(final Permutation solverPermutation) {
		if (this.Medians != null) {
			this.Medians.add(solverPermutation);
		}
		else {
			this.Medians = new HashSet<Permutation>();
			this.Medians.add(solverPermutation);
		}
	}

	//autres trucs

	//get info
	//insert info

	//info: n,m,dispertion,best lb,best ub,is optimal,

	//compressedInstance

	public void addSolverPermutations(
		final Set<Permutation> solverPermutations) {
		if (this.Medians != null) {
			this.Medians.addAll(solverPermutations);
		}
		else {
			this.Medians = new HashSet<Permutation>();
			this.Medians.addAll(solverPermutations);
		}
	}

	private void beginningThinks() {
		this.isSubInstance = false;
		this.m = this.A.size();
		this.n = this.pickARandom().getSize();
		this.creerTabD();
		this.SA_upper_bound = -1;
		this.BestOfA_upper_bound = -1;
		this.Borda_upper_bound = -1;
		this.Copeland_upper_bound = -1;
		this.Opt2_upper_bound = -1;
		this.Opt3_upper_bound = -1;
		this.Borda_opt3_upper_bound = -1;
		this.Copeland_opt3_upper_bound = -1;
		this.Copeland_opt3_permutation = null;
		this.CircMvtLocalSearch_upper_bound = -1;
		this.MedianGame_upper_bound = -1;
		this.natural_lower_bound = -1;
		this.lowerBoundBestOrderOfExploration = null;
		this.cycles3_lower_bound = -1;
		this.cycles4_lower_bound = -1;
		this.cyclesN_wConstraints_lower_bound = -1;
		this.cyclesN_wConstraints_guided_lower_bound = -1;
		this.cycles3_maxFlow_lower_bound = -1;
		this.best_lower_bound = -1;
		this.best_upper_bound = -1;
		this.simple_lower_bound = -1;
		this.add3cyles_lower_bound = -1;
		this.isOptimal = false;
		this.Medians = null;
		this.tabC = new boolean[this.n][this.n];
		this.tabPairStatus = new int[this.n][this.n];
		for (int i = 1; i <= this.n; i++) {
			for (int j = 1; j <= this.n; j++) {
				this.tabC[i - 1][j - 1] = false;
				this.tabPairStatus[i - 1][j - 1] = 0;
			}
		}
		this.nbExploredNodesBnB = 0;

		this.cplexMIPresult = 0;
		this.cplexLPresult = 0.0;

		//stats
		int dist = 0;
		this.minDist = 999999999;
		this.maxDist = 0;
		this.avgDist = 0.0;
		for (final Permutation pi1 : this.A) {
			for (final Permutation pi2 : this.A) {
				if (pi1 != pi2) {
					dist = pi1.distanceTo(pi2);
					if (dist < this.minDist) {
						this.minDist = dist;
					}
					if (dist > this.maxDist) {
						this.maxDist = dist;
					}
					this.avgDist += dist;
				}
			}
		}
		this.avgDist /= 2.0;
		this.avgDist /= this.m * (this.m - 1) / 2;

		this.minDist_normalized =
			this.minDist / (double) (this.n * (this.n - 1) / 2);
		this.maxDist_normalized =
			this.maxDist / (double) (this.n * (this.n - 1) / 2);
		this.avgDist_normalized = this.avgDist / (this.n * (this.n - 1) / 2);
	}

	/*public void setApproxPermutationst(Set<Permutation> ApproxP){
		ApproxPermutations = ApproxP;
	}*/
	public void computeFirstGap() {
		final int gap = this.best_upper_bound - this.best_lower_bound;
		final double relativeGap =
			100.0 * (this.best_upper_bound - this.best_lower_bound)
					/ this.best_lower_bound;
		this.first_gap = gap;
		this.first_relative_gap = relativeGap;
	}

	//methode qui prends une election et qui la transforme en ensemble de permutations, a la betzler
	/*
	 *  pomme>orange>banane>poire>toamte>broccoli>ananas>grenade>fraise
	 *  poire>tomate>pamplemousse>orange>pomme>bleuet
	 *  pomme>poire>orange>citron>tomate
	 *
	 *  1>2>0>3>4>0>0>0>0
	 *  3>4>0>2>1>0
	 *  1>3>2>0>4
	 *
	 *  [1,2,3,4]
	 *  [3,4,2,1]
	 *  [1,3,2,4]
	 *
	 */
	public void convertElectionsToPermutations(final String fichier) {
		final Map<String, Integer> candidats = new TreeMap<String, Integer>();
		this.rankingCandidatesNames = new ArrayList<String>();
		this.A = new HashSet<Permutation>();
		int n = 0;
		int m = 0;
		char u;
		String candidat = "";
		boolean ok = false;
		//lecture
		FileReader reader;
		try {
			reader = new FileReader(fichier);
			final Scanner in = new Scanner(reader);

			while (in.hasNext()) {
				final String line = in.nextLine();
				if (line.length() != 0) {
					//new ordering
					m++;
					for (int i = 0; i < line.length(); i++) {
						u = line.charAt(i);
						if (u == '>') {
							if (candidats.get(candidat) != null) {
								final int value = candidats.get(candidat);
								candidats.put(candidat, value + 1);
							}
							else {
								candidats.put(candidat, 1);
							}
							candidat = "";
						}
						else if (i == line.length() - 1) {
							candidat = candidat + u;
							//System.out.println(candidat);
							if (candidats.get(candidat) != null) {
								final int value = candidats.get(candidat);
								candidats.put(candidat, value + 1);
							}
							else {
								candidats.put(candidat, 1);
							}
							candidat = "";
						}
						else {
							candidat = candidat + u;
						}
					}

				}
			}
			ok = true;
			//reader.close();
			in.close();

		}
		catch (final FileNotFoundException e) {
			System.out
				.println("Fichier " + fichier + " n'a pas Ã©tÃ© trouvÃ©");
		}

		if (ok) {
			int ID = 1;
			for (final String candidat2 : candidats.keySet()) {
				if (candidats.get(candidat2) == m) {
					candidats.put(candidat2, ID);
					ID++;
					this.rankingCandidatesNames.add(candidat2);
				}
				else {
					candidats.put(candidat2, 0);
				}
			}
			n = ID - 1;

			//2e lecture
			try {
				reader = new FileReader(fichier);
				final Scanner in = new Scanner(reader);
				int t[] = new int[n];
				int compteur = 0;

				while (in.hasNext()) {
					final String line = in.nextLine();
					if (line.length() != 0) {
						t = new int[n];
						compteur = 0;
						//new ordering
						for (int i = 0; i < line.length(); i++) {
							u = line.charAt(i);
							if (u == '>') {
								if (candidats.get(candidat) != 0) {
									t[compteur] = candidats.get(candidat);
									compteur++;
									//System.out.print(" " + candidats.get(candidat));
								}
								candidat = "";
							}
							else if (i == line.length() - 1) {
								candidat = candidat + u;
								if (candidats.get(candidat) != 0) {
									t[compteur] = candidats.get(candidat);
									compteur++;
									//System.out.print(" " + candidats.get(candidat));
								}
								candidat = "";
							}
							else {
								candidat = candidat + u;
							}
						}
						final Permutation pi = new Permutation(t);
						//System.out.println(pi);
						this.A.add(pi);
					}
					//System.out.println("");
				}
				ok = true;
				//reader.close();
				in.close();

			}
			catch (final FileNotFoundException e) {
				System.out.println(
					"Fichier " + fichier + " n'a pas Ã©tÃ© trouvÃ©");
			}
		}

		this.beginningThinks();

	}

	//creer une permuttation au hasard
	public Permutation createARandom(final int n) {
		Permutation k2 = null;
		int num = 0, temp = 0;
		final int a[] = new int[n];

		//enumerer
		for (int i = 1; i <= n; i++) {
			a[i - 1] = i;
		}

		//a verifier...
		//brasser (Fisher-Yates shuffle)
		for (int i = 1; i <= n; i++) {
			num = (int) (Math.random() * i + 1);
			temp = a[num - 1];
			a[num - 1] = a[i - 1];
			a[i - 1] = temp;
		}

		//finaliser
		k2 = new Permutation(a);

		return k2;
	}

	//constraints
	public void createTabPairStatus() {
		//tabPairStatus= new int[n][n];
		for (int i = 1; i <= this.n; i++) {
			for (int j = 1; j <= this.n; j++) {
				this.tabPairStatus[i - 1][j - 1] = 0;
			}
		}

		//constraints
		for (int i = 1; i <= this.n; i++) {
			for (int j = 1; j <= this.n; j++) {
				if (this.tabC[i - 1][j - 1]) {
					this.tabPairStatus[i - 1][j - 1] = 5;
					this.tabPairStatus[j - 1][i - 1] = 1;
				}

			}
		}

		//"median" set should be trimmed
		for (final Permutation pi : this.Medians) {
			for (int i = 1; i <= this.n; i++) {
				for (int j = i + 1; j <= this.n; j++) {
					if (pi.getPosition(i) < pi.getPosition(j)) { //i<j
						if (this.tabPairStatus[i - 1][j - 1] == 0) {
							this.tabPairStatus[i - 1][j - 1] = 4;
							this.tabPairStatus[j - 1][i - 1] = 2;
						}
						else if (this.tabPairStatus[i - 1][j - 1] == 5
								|| this.tabPairStatus[i - 1][j - 1] == 4
								|| this.tabPairStatus[i - 1][j - 1] == 3) {
							//nothing
						}
						else if (this.tabPairStatus[i - 1][j - 1] == 2) {
							this.tabPairStatus[i - 1][j - 1] = 3;
							this.tabPairStatus[j - 1][i - 1] = 3;
						}
						else if (this.tabPairStatus[i - 1][j - 1] == 1) {
							//nothing
							//not real median or constraints on subset of medians
						}
					}
					else {//j<i
						if (this.tabPairStatus[j - 1][i - 1] == 0) {
							this.tabPairStatus[j - 1][i - 1] = 4;
							this.tabPairStatus[i - 1][j - 1] = 2;
						}
						else if (this.tabPairStatus[j - 1][i - 1] == 5
								|| this.tabPairStatus[j - 1][i - 1] == 4
								|| this.tabPairStatus[j - 1][i - 1] == 3) {
							//nothing
						}
						else if (this.tabPairStatus[j - 1][i - 1] == 2) {
							this.tabPairStatus[j - 1][i - 1] = 3;
							this.tabPairStatus[i - 1][j - 1] = 3;
						}
						else if (this.tabPairStatus[j - 1][i - 1] == 1) {
							//nothing
							//not real median or constraints on subset of medians
						}
					}
				}
			}
		}

	}

	//subInstance
	//creation d'un ensemble de permutations a partir d'un sous-ensemble d'elements
	public Instance creerSubInstance(final int elements[]) {//faire aussi a partir d'un set et d'un arraylist
		Instance subInstance = null;

		final int sizeSubInstance = elements.length;
		final Set<Permutation> SubA = new HashSet<Permutation>();

		final int[] newTableSubPermu = new int[sizeSubInstance]; //0-->13, 1-->24,...
		final int[] newTableInvertSubPermu = new int[this.n];//0-->0,1-->0,...,12-->1,...,23-->2,...

		for (int i = 0; i < this.n; i++) {
			newTableInvertSubPermu[i] = 0;
		}
		for (int i = 0; i < sizeSubInstance; i++) {
			newTableSubPermu[i] = elements[i];
			newTableInvertSubPermu[elements[i] - 1] = i + 1;
		}

		for (final Permutation pi : this.A) {
			final List<Integer> permuEnCours = new ArrayList<Integer>();

			for (int i = 0; i < this.n; i++) {
				if (newTableInvertSubPermu[pi.getTab()[i] - 1] != 0) {
					permuEnCours
						.add(newTableInvertSubPermu[pi.getTab()[i] - 1]);
				}
			}

			Permutation piSub = null;
			final int[] b = new int[permuEnCours.size()];
			for (int j = 0; j < permuEnCours.size(); j++) {
				b[j] = permuEnCours.get(j);
			}
			piSub = new Permutation(b);

			piSub.setSubPermu(newTableSubPermu);

			SubA.add(piSub);
		}

		subInstance = new Instance(SubA);
		subInstance
			.setAsSubInstanceOf(this, newTableSubPermu, newTableInvertSubPermu);

		return subInstance;
	}

	public void creerTabD() {
		//int n;//ordre des permutations
		//n=pickARandom(A).getSize();
		this.tabD = new int[this.n][this.n];
		int aDroite = 0;
		for (int i = 1; i <= this.n; i++) {
			for (int j = 1; j <= this.n; j++) {
				if (i != j) {
					// aGauche = 0;
					aDroite = 0;
					for (final Permutation p : this.A) {
						if (p.getPosition(j) < p.getPosition(i)) {
							// aGauche++;
						}
						else {
							aDroite++;
						}
					}
					this.tabD[j - 1][i - 1] = aDroite;
				}
			}
		}

	}
	public void decalreIsOptimal() {
		this.best_lower_bound = this.best_upper_bound;
		this.isOptimal = true;
	}

	public int getGap() {
		final int gap = this.best_upper_bound - this.best_lower_bound;
		return gap;
	}

	public double getRelativeGap() {
		//int gap = best_upper_bound -best_lower_bound;
		final double relativeGap =
			100.0 * (this.best_upper_bound - this.best_lower_bound)
					/ this.best_lower_bound;
		return relativeGap;
	}

	public void giveConstraints(final boolean[][] tabConstraints) {
		this.tabC = new boolean[this.n][this.n];
		for (int i = 1; i <= this.n; i++) {
			for (int j = 1; j <= this.n; j++) {
				this.tabC[i - 1][j - 1] = tabConstraints[i - 1][j - 1];
			}
		}
	}

	//imprime l'ensemble de permutations A
	public String linearStringA() {
		String resultat = "{";
		//affichage
		for (final Permutation p : this.A) {
			resultat = resultat + p.toString() + ",";
		}
		if (resultat.length() > 0) {
			resultat = resultat.substring(0, resultat.length() - 1);
		}
		resultat += "}";
		return resultat;
	}

	//first call the constructor then this, in the main code
	public void loadElectionFromFile(final String fichier) {

		char u;
		String nombreString = "";
		int[] a = null;
		List<Integer> permuEnCours = null;
		Permutation pi;

		//lecture
		FileReader reader;
		try {
			reader = new FileReader(fichier);
			final Scanner in = new Scanner(reader);

			this.A = new HashSet<Permutation>();
			while (in.hasNext()) {
				final String line = in.nextLine();
				if (line.length() != 0) {
					pi = null;
					permuEnCours = new ArrayList<Integer>();
					nombreString = "";
					for (int i = 0; i < line.length(); i++) {
						u = line.charAt(i);
						if (u == '>') {
							permuEnCours.add(Integer.parseInt(nombreString));
							nombreString = "";
						}
						else {
							nombreString += u;
						}
					}
					permuEnCours.add(Integer.parseInt(nombreString));
					a = new int[permuEnCours.size()];
					for (int j = 0; j < permuEnCours.size(); j++) {
						a[j] = permuEnCours.get(j);
					}
					pi = new Permutation(a);
					this.A.add(pi);
				}
			}
			this.beginningThinks();

			in.close();

		}
		catch (final FileNotFoundException e) {
			System.out.println("Fichier " + fichier + " n'a pas été trouvé");
		}
	}

	//exemple {[7,1,5,4,9,8,3,2,10,6],[6,8,4,10,5,7,1,2,9,3],[2,1,8,5,7,4,10,3,9,6]}
	//      001222222222222222222222112222222222222222222221122222222222222222222210
	//        Ap                    p p                    p p                    p*
	//           n n n n n n n n  n n   n n n  n n n n n n n   n n n n n n  n n n n
	public void loadFromFile(final String fichier, final int mode) {

		char u;
		int profondeur = 0;
		String nombreString = "";
		int[] a = null;
		List<Integer> permuEnCours = null;
		Permutation pi;

		//lecture
		FileReader reader;
		try {
			reader = new FileReader(fichier);
			final Scanner in = new Scanner(reader);
			while (in.hasNext()) {
				final String line = in.nextLine();
				//Integer.parseInt(line);
				//line.charAt(i);
				if (line.length() != 0) {
					for (int i = 0; i < line.length(); i++) {
						u = line.charAt(i);
						//System.out.println(u);
						if (u == '{' || u == '[') {
							profondeur++;
							if (profondeur == 1) {
								this.A = new HashSet<Permutation>();
							}
							else if (profondeur == 2) {
								pi = null;
								permuEnCours = new ArrayList<Integer>();
								nombreString = "";
							}
							else {
								System.out.println("erreur de lecture");
							}

						}
						else if (u == '}' || u == ']') {
							profondeur--;
							if (profondeur == 0) {

								//Instance myInstance =new Instance(A);
								this.beginningThinks();

							}
							else if (profondeur == 1) {
								permuEnCours
									.add(Integer.parseInt(nombreString));
								a = new int[permuEnCours.size()];
								for (int j = 0; j < permuEnCours.size(); j++) {
									a[j] = permuEnCours.get(j);
								}
								pi = new Permutation(a);
								this.A.add(pi);
							}
							else if (profondeur == 2) {
								permuEnCours
									.add(Integer.parseInt(nombreString));
							}
							else {
								System.out.println("erreur de lecture");
							}
						}
						else if (u == ',') {
							if (profondeur == 2) {
								permuEnCours
									.add(Integer.parseInt(nombreString));
								nombreString = "";
							}
							else {
								//rien
							}
						}
						else {
							if (profondeur == 2) {
								nombreString += u;
							}
						}

					}

				}

			}
			in.close();

		}
		catch (final FileNotFoundException e) {
			System.out.println("Fichier " + fichier + " n'a pas été trouvé");
		}
	}

	//first call the constructor then this, in the main code
	//has to be all in one line only for now (correct later)

	//choisi une permuttation au hasard dans A
	public Permutation pickARandom() {
		Permutation k2 = null;
		int num = 0, compteur = 0;
		num = (int) (Math.random() * this.m + 1);
		for (final Permutation k : this.A) {
			compteur++;
			if (compteur == num) {
				k2 = new Permutation(k.getTab());
				return k2;
			}
		}
		System.out.println("problem #201");
		return k2;
	}

	//choisi une permuttation au hasard dans M
	public Permutation pickARandomMedian() {
		Permutation k2 = null;
		int num = 0, compteur = 0;
		num = (int) (Math.random() * this.Medians.size() + 1);
		for (final Permutation k : this.Medians) {
			compteur++;
			if (compteur == num) {
				k2 = new Permutation(k.getTab());
				return k2;
			}
		}
		System.out.println("problem #201");
		return k2;
	}

	public void print() {
		System.out.println("Set A (" + this.A.size() + ") :");
		for (final Permutation p : this.A) {
			System.out.println(p);
		}
	}

	//imprime l'ensemble de permutations M
	public void printM(final int threshold) {

		//if (isSubPermu){
		if (this.isSubInstance) {
			for (final Permutation pi : this.Medians) {//si subPermu
				pi.setSubPermu(this.tableSubPermu);
			}
		}

		if (this.isOptimal) {
			System.out.println("Set M (" + this.Medians.size() + ") :");
			System.out.println("distMediane= " + this.best_upper_bound);
		}
		else {
			System.out.println("Set ~M (" + this.Medians.size() + ") :");
			System.out.println("distApproxMediane= " + this.best_upper_bound);
		}

		if (this.Medians.size() <= threshold) {
			for (final Permutation p : this.Medians) {
				//System.out.println(p + "  stabilite: " + evalStabilitePermu(A.size(), p,false));
				//System.out.println(p + " "+v.verifie(p));
				System.out.println(p);
				//System.out.println(p + " " + p.distanceToSetMatrix(tabD) + " check:"+check(p,tabD));
				//System.out.println(p + " " + p.distanceToSet(A));
				//System.out.println(p + " " + p.distanceToSetMatrix(tabD));
				//System.out.println(p.getCycles());
			}
		}
		else {
			System.out.println(
				"Plus de " + threshold
						+ " medianes...(pas la peine de  tout imprimer)");
			System.out.println("echantillon:");
			System.out.println(this.pickARandomMedian());
		}

	}

	public void printTabD() {
		//demonstration du tabD (nombre d'apparition de j a droite de i)
		System.out.println("tabD: ");
		for (int i = 0; i < this.n; i++) {
			for (int j = 0; j < this.n; j++) {
				System.out.print(this.tabD[j][i] + "  ");
			}
			System.out.println("");
		}

	}
	public void setAsSubInstanceOf(
		final Instance inst,
		final int[] tableSubPermuX,
		final int[] tableInvertSubPermuX) {
		this.isSubInstance = true;
		this.parentInstance = inst;
		this.tableSubPermu = tableSubPermuX;
		this.tableInvertSubPermu = tableInvertSubPermuX;

	}
	public void setLowerBound(final int bound) {
		if (bound > this.best_lower_bound) {
			this.best_lower_bound = bound;
			if (this.best_lower_bound == this.best_upper_bound) {
				this.isOptimal = true;
			}
		}
	}
	public void setMedianDist(final int medianDist) {
		this.best_lower_bound = medianDist;
		this.best_upper_bound = medianDist;
		this.isOptimal = true;
	}

	public void setUpperBound(final int bound) {
		if (this.best_upper_bound == -1) {
			this.best_upper_bound = bound;
			if (this.best_lower_bound == this.best_upper_bound) {
				this.isOptimal = true;
			}
		}
		else if (bound < this.best_upper_bound) {
			this.best_upper_bound = bound;
			if (this.best_lower_bound == this.best_upper_bound) {
				this.isOptimal = true;
			}
		}
	}

	public void trimMedianSet() {
		int distCurrent = 0;
		final Set<Permutation> trimmedMedians = new HashSet<Permutation>();
		for (final Permutation p : this.Medians) {
			distCurrent = p.distanceToSetMatrix(this.tabD);
			if (distCurrent == this.best_upper_bound) {
				trimmedMedians.add(p);
			}
			else if (distCurrent < this.best_upper_bound) {
				//rien
				System.out.println("error 23456843645");
			}
			else {
				//rien
			}

		}

		this.Medians.clear();
		this.Medians.addAll(trimmedMedians);

	}
	public void writeCplexLPIntoFile(
		final String fichier,
		final boolean writeConstraints) {
		final boolean appendMode = false;
		int compteur = 0;

		//traitement du dossier
		final File f = new File(fichier);
		if (!f.exists()) {
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
			}

		}
		else {
			//rien
		}

		//traitement du fichier
		try {
			// Create file
			final FileWriter fstream = new FileWriter(fichier, appendMode);//file access on append mode
			final BufferedWriter out = new BufferedWriter(fstream);
			String line = "";

			out.write("Minimize " + "\n");
			out.write(" obj: ");

			line = "";
			for (int i = 1; i <= this.n; i++) {
				for (int j = 1; j <= this.n; j++) {
					if (i != j) {
						line += this.tabD[i - 1][j - 1] + " x_" + i + "_" + j
								+ " + ";

						if (i == this.n && j == this.n - 1) {
							line = line.substring(0, line.length() - 2);
							out.write(line);
							out.write("\n");
							line = "";
						}
						else {
							out.write(line);
							out.write("\n");
							line = "";
						}
					}

				}

			}

			out.write("Subject To " + "\n");
			compteur = 0;
			for (int i = 1; i <= this.n; i++) {
				for (int j = i + 1; j <= this.n; j++) {
					if (writeConstraints) {
						if (this.tabC[i - 1][j - 1]) {
							out.write(
								" c" + compteur + ": x_" + i + "_" + j
										+ " = 1 \n");
						}
						else if (this.tabC[j - 1][i - 1]) {
							out.write(
								" c" + compteur + ": x_" + j + "_" + i
										+ " = 1 \n");
						}
						else {
							out.write(
								" c" + compteur + ": x_" + i + "_" + j + " + x_"
										+ j + "_" + i + " = 1 \n");
						}
					}
					else {
						out.write(
							" c" + compteur + ": x_" + i + "_" + j + " + x_" + j
									+ "_" + i + " = 1 \n");
					}
					compteur++;
				}
			}

			for (int i = 1; i <= this.n; i++) {
				for (int j = 1; j <= this.n; j++) {
					if (i != j) {
						for (int k = 1; k <= this.n; k++) {
							if (k != i && k != j) {
								out.write(
									" c" + compteur + ": x_" + i + "_" + j
											+ " + x_" + j + "_" + k + " + x_"
											+ k + "_" + i + " >= 1 \n");
								compteur++;
							}
						}
					}

				}
			}

			//out.write("Bounds "+"\n");

			out.write("Binary " + "\n");
			for (int i = 1; i <= this.n; i++) {
				for (int j = 1; j <= this.n; j++) {
					if (i != j) {
						out.write(" x_" + i + "_" + j + "\n");
					}
				}
			}
			out.write("End " + "\n");

			//Close the output stream
			out.close();
			fstream.close();
		}
		catch (final Exception e) {//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

	}
	public void writeCplexMSTIntoFile(final String fichier) {
		final boolean appendMode = false;
		final Permutation MSTsolution = this.pickARandomMedian();
		final int MSTscore = MSTsolution.distanceToSetMatrix(this.tabD);
		System.out.println(
			"Writing Cplex solution into MST file (score=" + MSTscore + ")");

		//traitement du dossier
		final File f = new File(fichier);
		if (!f.exists()) {
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
			}

		}
		else {
			//rien
		}

		//traitement du fichier
		try {
			// Create file
			final FileWriter fstream = new FileWriter(fichier, appendMode);//file access on append mode
			final BufferedWriter out = new BufferedWriter(fstream);
			// final String line = "";

			out.write(
				"<?xml version = \"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
						+ "\n");

			out.write("<CPLEXSolutions version=\"1.2\">" + "\n");
			out.write(" <CPLEXSolution version=\"1.2\">" + "\n");

			out.write("  <header" + "\n");

			out.write("   problemName=\"ex_2.lp\"" + "\n");
			out.write("   solutionName=\"m1\"" + "\n");
			out.write("   solutionIndex=\"0\"" + "\n");
			out.write("   MIPStartEffortLevel=\"0\"" + "\n");
			out.write("   writeLevel=\"2\"" + "/>\n");

			out.write("  <variables>" + "\n");

			int compteur = 0;
			for (int i = 1; i <= this.n; i++) {
				for (int j = i + 1; j <= this.n; j++) {

					out.write(
						"   <variable name=\"" + "x_" + i + "_" + j + "\" ");
					out.write("index=\"" + compteur + "\" ");
					if (MSTsolution.getPosition(i) < MSTsolution
						.getPosition(j)) {
						out.write("value=\"" + 1 + "\" ");
					}
					else {
						out.write("value=\"" + 0 + "\"");
					}

					out.write("/>\n");
					compteur++;
				}
			}

			out.write("  </variables>" + "\n");
			out.write(" </CPLEXSolution>" + "\n");
			out.write("</CPLEXSolutions>" + "\n");

			//Close the output stream
			out.close();
			fstream.close();
		}
		catch (final Exception e) {//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

		//System.out.println("Done");

	}
	public void writeElectionIntoFile(
		final String fichier,
		final boolean appendMode) {

		//traitement du dossier
		final File f = new File(fichier);
		if (!f.exists()) {
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
			}

		}
		else {
			//rien
		}

		//traitement du fichier
		try {
			// Create file
			final FileWriter fstream = new FileWriter(fichier, appendMode);//file access on append mode
			final BufferedWriter out = new BufferedWriter(fstream);

			//out.write(linearStringA());
			// int count = 0;
			String resultat = "";
			//affichage
			for (final Permutation p : this.A) {
				// count++;
				resultat = p.toElectionString();
				out.write(resultat + "\n");
				resultat = "";
			}

			out.write("\n");

			//Close the output stream
			out.close();
		}
		catch (final Exception e) {//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

		//System.out.println("Done");

	}
	public void writeElectionResults(final String fichier) {
		String result = "";
		final Permutation finalOrder = this.pickARandomMedian();
		try {
			// Create file
			final FileWriter fstream = new FileWriter(fichier, false);
			final BufferedWriter out = new BufferedWriter(fstream);

			result =
				this.rankingCandidatesNames.get(finalOrder.getTab()[0] - 1);
			for (int candidateNumber =
				1; candidateNumber < this.rankingCandidatesNames
					.size(); candidateNumber++) {
				result += ">" + this.rankingCandidatesNames
					.get(finalOrder.getTab()[candidateNumber] - 1);
			}
			out.write(result);
			out.write("\n");
			//Close the output stream
			out.close();
		}
		catch (final Exception e) {//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	public void writeIntoFile(final String fichier, final boolean appendMode) {

		//traitement du dossier
		final File f = new File(fichier);
		if (!f.exists()) {
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
			}

		}
		else {
			//rien
		}

		//traitement du fichier
		try {
			// Create file
			final FileWriter fstream = new FileWriter(fichier, appendMode);//file access on append mode
			final BufferedWriter out = new BufferedWriter(fstream);

			//out.write(linearStringA());
			int count = 0;
			String resultat = "{";
			//affichage
			for (final Permutation p : this.A) {
				count++;
				if (count != this.m) {
					resultat = resultat + p.toString() + ",";
				}
				else {
					resultat = resultat + p.toString() + "}";
				}
				out.write(resultat + "\n");
				resultat = "";
			}

			out.write("\n");

			//Close the output stream
			out.close();
		}
		catch (final Exception e) {//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

		//System.out.println("Done");

	}

	public void writeResultIntoFile(final String fichier) {
		final boolean appendMode = false;

		//traitement du dossier
		final File f = new File(fichier);
		if (!f.exists()) {
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
			}

		}
		else {
			//rien
		}

		//traitement du fichier
		try {
			// Create file
			final FileWriter fstream = new FileWriter(fichier, appendMode);//file access on append mode
			final BufferedWriter out = new BufferedWriter(fstream);

			out.write("m: " + this.m + "\n");
			out.write("n: " + this.n + "\n");
			out.write("\n");
			out.write("minDist: " + this.minDist + "\n");
			out.write("maxDist: " + this.maxDist + "\n");
			out.write("avgDist: " + this.avgDist + "\n");
			out.write("minDist_normalized: " + this.minDist_normalized + "\n");
			out.write("maxDist_normalized: " + this.maxDist_normalized + "\n");
			out.write("avgDist_normalized: " + this.avgDist_normalized + "\n");
			out.write("\n");
			out.write(
				"BestOfA_upper_bound: " + this.BestOfA_upper_bound + "\n");
			out.write("Borda_upper_bound: " + this.Borda_upper_bound + "\n");
			out.write(
				"Copeland_upper_bound: " + this.Copeland_upper_bound + "\n");
			out.write("Opt2_upper_bound: " + this.Opt2_upper_bound + "\n");
			out.write("Opt3_upper_bound: " + this.Opt3_upper_bound + "\n");
			out.write(
				"Borda_opt3_upper_bound: " + this.Borda_opt3_upper_bound
						+ "\n");
			out.write(
				"Copeland_opt3_upper_bound: " + this.Copeland_opt3_upper_bound
						+ "\n");
			out.write(
				"CircMvtLocalSearch_upper_bound: "
						+ this.CircMvtLocalSearch_upper_bound + "\n");
			out.write(
				"MedianGame_upper_bound: " + this.MedianGame_upper_bound
						+ "\n");
			out.write(
				"EucledianArccosMedianRn_upper_bound: "
						+ this.EucledianArccosMedianRn_upper_bound + "\n");
			out.write("SA_upper_bound: " + this.SA_upper_bound + "\n");
			out.write("\n");
			out.write(
				"natural_lower_bound: " + this.natural_lower_bound + "\n");
			out.write(
				"cycles3_lower_bound: " + this.cycles3_lower_bound + "\n");
			out.write(
				"cycles4_lower_bound: " + this.cycles4_lower_bound + "\n");
			out.write(
				"cyclesN_wConstraints_lower_bound: "
						+ this.cyclesN_wConstraints_lower_bound + "\n");
			out.write(
				"cyclesN_wConstraints_guided_lower_bound: "
						+ this.cyclesN_wConstraints_guided_lower_bound + "\n");
			out.write("\n");
			out.write("first_gap: " + this.first_gap + "\n");
			out.write("first_relative_gap: " + this.first_relative_gap + "%\n");
			out.write("\n");
			out.write("best_upper_bound: " + this.best_upper_bound + "\n");
			out.write("best_lower_bound: " + this.best_lower_bound + "\n");
			out.write("gap: " + this.getGap() + "\n");
			out.write("relative gap: " + this.getRelativeGap() + "%\n");
			out.write("nbExploredNodesBnB: " + this.nbExploredNodesBnB + "\n");
			out.write("nbMaxNodesBnB: " + this.nbMaxNodesBnB + "\n");
			out.write("isOptimal: " + this.isOptimal + "\n");
			if (this.Medians != null) {
				if (this.Medians.size() > 0) {
					if (this.isOptimal) {
						out.write("Median: " + this.pickARandomMedian() + "\n");
						out.write("Median size: " + this.Medians.size() + "\n");
					}
					else {
						out.write(
							"~Median: " + this.pickARandomMedian() + "\n");
						out.write(
							"~Median size: " + this.Medians.size() + "\n");
					}
				}
			}

			out.write("\n");

			//Close the output stream
			out.close();
		}
		catch (final Exception e) {//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

		//System.out.println("Done");

	}

}
