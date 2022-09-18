package consensus.algorithms;

import java.util.HashSet;
import java.util.Set;

public class Permutation implements Comparable<Permutation> {
	private int[] tab = null;//tableau ordonne des elements ds la permutation
	private int[] tab2 = null;//tableau ordonne des elements ds la permutation
	private int[] tabSubPermu = null;//tableau ordonne des elements ds la permutation
	private int[] position = null;//tableau ordonne des elements ds la permutation
	private int size = 0; //taille de la permutation
	private int dist = 0;
	private boolean isSubPermu = false;

	/*
	 *           i : 0 1 2 3 4 5 6  <-- indices
	 *      tab[i] : 1 3 4 5 2 7 6  <-- the permutation
	 * position[i] : 0 4 1 2 3 6 5  <-- the positions of elements, can be seen as the reverse permutation if add +1 :  1 5 2 3 4 7 6
	 *    ^element : 1 2 3 4 5 6 7  aka i+1
	 */

	//special for geography
	boolean hasDescendent = false;
	Permutation descendent = null;
	Permutation finalDescendent = null;
	Set<Permutation> finalDescendents = null;
	int sumAscendence = 0;

	//constructeur - n indetermine
	public Permutation(final int t[]) {
		//mort = false;
		this.size = t.length;
		this.tab = new int[this.size];
		this.tab2 = new int[this.size];
		this.position = new int[this.size];
		for (int i = 0; i < this.size; i++) {
			this.tab[i] = t[i];
			this.position[this.tab[i] - 1] = i;
		}
		//gerer les mauvaises permutations ici
		this.gererMauvaisesPermu();
		this.isSubPermu = false;
	}

	public void addAscendent() {
		this.sumAscendence++;
	}

	//modificateur2 a dist
	public void addDist(final int s) {
		if (s > 0) {
			this.dist += s;
		}
	}

	//retourne une approximation(borneInf) de la distance de Kendall-Tau entre 2 permutations
	public int approxDistanceTo(final Permutation other) {
		int d = 0;
		int temp = 0;
		if (this.size == other.getSize()) {
			for (int i = 1; i <= this.size; i++) {
				temp = this.getPosition(i) - other.getPosition(i);
				if (temp > 0) {
					d += temp;
				}
			}
		}
		else {
			d = -99999;
		}

		return d;
	}

	//retourne une approximation(borneInf) de la distance de Kendall-Tau entre la permutation et toutes celles de l'autre ensemble
	public int approxDistanceToSet(final Set<Permutation> a) {
		int distance = 0;
		for (final Permutation p : a) {
			distance += this.approxDistanceTo(p);
		}
		return distance;
	}

	@Override
	public int compareTo(final Permutation other) {
		return this.dist - other.getDist();//pour le SortedSet dans heuristiqueCreerEV
		//return this.distanceTo(other);
	}

	//retourne la distance de Kendall-Tau entre 2 permutations
	public int distanceTo(final Permutation other) {
		int d = 0;
		if (this.size == other.getSize()) {
			for (int i = 1; i <= this.size; i++) {
				for (int j = i + 1; j <= this.size; j++) {
					if (this.getPosition(i) > this.getPosition(j)
							&& other.getPosition(i) < other.getPosition(j)) {
						d++;
					}
					else if (this.getPosition(i) < this.getPosition(j)
							&& other.getPosition(i) > other.getPosition(j)) {
						d++;
					}
				}
			}
		}
		else {
			d = -99999;
		}

		return d;
	}

	//retourne la distance de Kendall-Tau entre la permutation et toutes celles de l'autre ensemble
	public int distanceToSet(final Set<Permutation> a) {
		int distance = 0;
		for (final Permutation p : a) {
			distance += this.distanceTo(p);
		}
		return distance;
	}

	//retourne la distance de Kendall-Tau entre la permutation et toutes celles de l'autre ensemble
	//calcul a base de la matrice de distance
	public int distanceToSetMatrix(final int[][] tabD) {
		int distance = 0;
		for (int i = 0; i < this.size - 1; i++) {
			for (int j = i + 1; j < this.size; j++) {
				distance += tabD[this.tab[i] - 1][this.tab[j] - 1];
			}
		}
		return distance;
	}

	@Override
	public boolean equals(final Object that) {
		int[] tab2 = null;
		if (this == that) {
			return true;
		}
		if (!(that instanceof Permutation)) {
			return false;
		}

		//return false; // pour methode rapprochement

		//methode a tester (rique?)
		final Permutation that2 = (Permutation) that;
		tab2 = that2.getTab();
		//if (tab[0] != tab2[0]) return false;//je doute de l'effficacite de ceci
		//if (tab[1] != tab2[1]) return false;
		for (int i = 0; i < this.size; i++) {
			if (this.tab[i] != tab2[i]) {
				return false;
			}
		}
		return true;

	}

	//a faire
	//retourne la distance de Kendall-Tau entre 2 permutations
	//nlogn - calcul rapide
	public int fastDistanceTo(final Permutation other) {
		//en construction
		int d = 0;
		if (this.size == other.getSize()) {
			for (int i = 0; i < this.size; i++) {
				this.tab2[i] = this.tab[i];
			}
			d = this.recuDist(other, 0, this.size - 1);
		}
		else {
			d = -99999;
		}

		return d;
	}

	//a faire
	//retourne la distance de Kendall-Tau entre la permutation et toutes celles de l'autre ensemble
	public int fastDistanceToSet(final Set<Permutation> a) {
		int distance = 0;
		for (final Permutation p : a) {
			distance += this.fastDistanceTo(p);
		}
		return distance;
	}

	private void gererMauvaisesPermu() {
		final Set<Integer> Nombres = new HashSet<Integer>();
		for (int i = 0; i < this.size; i++) {
			Nombres.add(this.tab[i]);
		}
		if (Nombres.size() != this.size) {
			System.out
				.println("erreur: permutation incorrecte " + this.toString());
		}
	}

	public int[] getCompInvTab() {
		final int[] reponse = new int[this.size];
		for (int i = 0; i < this.size; i++) {
			reponse[i] = this.position[i] + 1;
			//System.out.println(permutation_inverse[i]);
		}
		return reponse;
	}

	public String getCycles() {
		String reponse = "";
		final Set<Integer> nombres = new HashSet<Integer>();
		int nombre = 0;
		int nbCycles = 0;
		for (int i = 1; i <= this.size; i++) {
			nombre = i;
			if (!nombres.contains(nombre)) {
				reponse += "(";
				reponse += nombre;
				nombres.add(nombre);
				while (this.tab[nombre - 1] != nombre
						&& !nombres.contains(this.tab[nombre - 1])) {
					nombre = this.tab[nombre - 1];
					reponse = reponse + "," + nombre;
					nombres.add(nombre);
				}
				reponse += ")";
			}
		}

		for (int i = 0; i < reponse.length(); i++) {
			if (reponse.charAt(i) == '(') {
				nbCycles++;
			}
		}
		reponse += " nbCycles= " + nbCycles;

		return reponse;
	}

	public Permutation getDescendent() {
		return this.descendent;
	}

	//accesseur a dist
	public int getDist() {
		return this.dist;
	}

	public Permutation getFinalDescendent() {
		return this.finalDescendent;
	}

	public Set<Permutation> getFinalDescendents() {
		//System.out.println("boom");
		return this.finalDescendents;
	}

	public int[] getOppositeTab() {
		final int[] reponse = new int[this.size];
		for (int i = 0; i < this.size; i++) {
			reponse[i] = this.tab[this.size - i - 1];
			//System.out.println(permutation_inverse[i]);
		}
		return reponse;
	}

	//renvoie la position dans la permutation de l'element passe en parametre
	public int getPosition(final int element) {
		return this.position[element - 1];
		/*for (int k=0;k<size;k++)
			if (tab[k] == element) return k;
		return -1;*/
	}

	//accesseur a Size
	public int getSize() {
		return this.size;
	}

	public int getSumAscendence() {
		return this.sumAscendence;

	}

	/*public static Comparator<Permutation> PermutationComparator = new Comparator<Permutation>() {
		public int compare(Permutation permu1, Permutation permu2) {
			return (permu1.getDist() - (permu2.getDist()));
		}
	};*/

	//accesseur a tab
	public int[] getTab() {
		return this.tab;
	}

	public boolean hasDescendent() {
		return this.hasDescendent;
	}

	@Override
	public int hashCode() {
		int valeur = 0;
		for (int i = 0; i < this.size; i++) {
			valeur += this.tab[i] * Math.pow(this.size, i);
		}
		return valeur;
	}

	//special for geography

	//destructeur de la permutation, pour fin de test
	public void kill() {
		this.size = 1;
		this.tab = new int[this.size];
	}

	//sorte de merge sort qui compte le nombre de swaps
	private int recuDist(
		final Permutation other,
		final int start,
		final int stop) {
		int reponse = 0;
		int pt1 = 0, pt2 = 0, ptT = 0;
		int moitie = 0;
		final int tabTemp[] = new int[stop - start + 1];
		if (stop - start == 0) {
			return 0;
		}
		else {
			moitie = (int) (Math.floor((start + stop) / 2) + 0);
			reponse = this.recuDist(other, start, moitie)
					+ this.recuDist(other, moitie + 1, stop);
			//System.out.println(start + "-" + stop);
			pt2 = moitie + 1;
			pt1 = start;
			while (ptT != tabTemp.length) {
				if (pt1 <= moitie && pt2 <= stop) {
					if (other.getPosition(this.tab2[pt1]) <= other
						.getPosition(this.tab2[pt2])) {
						tabTemp[ptT] = this.tab2[pt1];
						pt1++;
					}
					else {
						tabTemp[ptT] = this.tab2[pt2];
						pt2++;
						reponse += moitie - pt1 + 1;
						//System.out.println(moitie - pt1 + 1);
					}
				}
				else if (pt1 <= moitie && pt2 > stop) {
					tabTemp[ptT] = this.tab2[pt1];
					pt1++;
				}
				else if (pt1 > moitie && pt2 <= stop) {
					tabTemp[ptT] = this.tab2[pt2];
					pt2++;
				}
				ptT++;
			}
			for (int i = start; i <= stop; i++) {
				this.tab2[i] = tabTemp[i - start];
				//System.out.println(tab2[i]);
			}
			return reponse;
		}
	}

	public void setDescendent(final Permutation permu) {
		this.descendent = permu;
		this.hasDescendent = true;
		if (!permu.hasDescendent()) {
			this.finalDescendent = permu;
			permu.addAscendent();
		}
		else {
			this.finalDescendent = permu.getFinalDescendent();
			this.finalDescendent.addAscendent();
		}
	}

	public void setDescendents(final Permutation permu) {
		//descendent = permu;

		if (this.hasDescendent == true) {
			//nothing
		}
		else {
			this.finalDescendents = new HashSet<Permutation>();
			this.hasDescendent = true;
		}

		if (!permu.hasDescendent()) {
			if (!this.finalDescendents.contains(permu)) {
				this.finalDescendents.add(permu);
				permu.addAscendent();
			}
		}
		else {
			//finalDescendents.addAll(permu.getFinalDescendents());
			for (final Permutation p : permu.getFinalDescendents()) {
				if (!this.finalDescendents.contains(p)) {
					this.finalDescendents.add(p);
					p.addAscendent();
				}
			}

		}

	}

	//modificateur a dist
	public void setDist(final int s) {
		if (s > 0) {
			this.dist = s;
		}
	}

	//modificateur a Size
	public void setSize(final int s) {
		if (s > 0) {
			this.size = s;
		}
	}

	public void setSubPermu(final int t[]) {
		this.isSubPermu = true;
		this.size = t.length;
		this.tabSubPermu = new int[this.size];
		for (int i = 0; i < this.size; i++) {
			this.tabSubPermu[i] = t[i];
		}

	}
	/*
	//constructeur - 3
	public Permutation (int t1, int t2, int t3){
		//mort = false;
		size = 4;
		tab = new int[size];
		tab2 = new int[size];
		position = new int[size];
		tab[0]=t1;
		tab[1]=t2;
		tab[2]=t3;
		for (int i=0;i<size;i++){
			position[tab[i]-1]=i;
		}
		//gerer les mauvaises permutations ici
		gererMauvaisesPermu();
	}

	//constructeur - 4
	public Permutation (int t1, int t2, int t3, int t4){
		//mort = false;
		size = 4;
		tab = new int[size];
		tab2 = new int[size];
		position = new int[size];
		tab[0]=t1;
		tab[1]=t2;
		tab[2]=t3;
		tab[3]=t4;
		for (int i=0;i<size;i++){
			position[tab[i]-1]=i;
		}
		//gerer les mauvaises permutations ici
		gererMauvaisesPermu();
	}

	//constructeur - 5
	public Permutation (int t1, int t2, int t3, int t4, int t5){
		//mort = false;
		size = 5;
		tab = new int[size];
		tab2 = new int[size];
		position = new int[size];
		tab[0]=t1;
		tab[1]=t2;
		tab[2]=t3;
		tab[3]=t4;
		tab[4]=t5;
		for (int i=0;i<size;i++){
			position[tab[i]-1]=i;
		}
		//gerer les mauvaises permutations ici
		gererMauvaisesPermu();
	}

	//constructeur - 6
	public Permutation (int t1, int t2, int t3, int t4, int t5, int t6){
		//mort = false;
		size = 6;
		tab = new int[size];
		tab2 = new int[size];
		position = new int[size];
		tab[0]=t1;
		tab[1]=t2;
		tab[2]=t3;
		tab[3]=t4;
		tab[4]=t5;
		tab[5]=t6;
		for (int i=0;i<size;i++){
			position[tab[i]-1]=i;
		}
		//gerer les mauvaises permutations ici
		gererMauvaisesPermu();
	}
	//constructeur - 7
	public Permutation (int t1, int t2, int t3, int t4, int t5, int t6, int t7){
		//mort = false;
		size = 7;
		tab = new int[size];
		tab2 = new int[size];
		position = new int[size];
		tab[0]=t1;
		tab[1]=t2;
		tab[2]=t3;
		tab[3]=t4;
		tab[4]=t5;
		tab[5]=t6;
		tab[6]=t7;
		for (int i=0;i<size;i++){
			position[tab[i]-1]=i;
		}
		//gerer les mauvaises permutations ici
		gererMauvaisesPermu();
	}

	//constructeur - 8
	public Permutation (int t1, int t2, int t3, int t4, int t5, int t6, int t7, int t8){
		//mort = false;
		size = 8;
		tab = new int[size];
		tab2 = new int[size];
		position = new int[size];
		tab[0]=t1;
		tab[1]=t2;
		tab[2]=t3;
		tab[3]=t4;
		tab[4]=t5;
		tab[5]=t6;
		tab[6]=t7;
		tab[7]=t8;
		for (int i=0;i<size;i++){
			position[tab[i]-1]=i;
		}
		//gerer les mauvaises permutations ici
		gererMauvaisesPermu();
	}
	*/

	public String showPair(final int i, final int j) {
		String s = "[";
		if (this.tab[0] == i) {
			s = s + i;
		}
		else if (this.tab[0] == j) {
			s = s + j;
		}
		else {
			s = s + "-";
		}
		for (int k = 1; k < this.size; k++) {
			if (this.tab[k] == i) {
				s = s + "," + i;
			}
			else if (this.tab[k] == j) {
				s = s + "," + j;
			}
			else {
				s = s + "," + "-";
			}
		}
		s = s + "]";
		return s;
	}

	//imprime la permuttation
	public String toElectionString() {
		if (!this.isSubPermu) {
			String s = "";
			s = s + this.tab[0];
			for (int i = 1; i < this.size; i++) {
				s = s + ">" + this.tab[i];
			}
			s = s + "";
			return s;
		}
		else {
			String s = "";
			s = s + this.tabSubPermu[this.tab[0] - 1];
			for (int i = 1; i < this.size; i++) {
				s = s + ">" + this.tabSubPermu[this.tab[i] - 1];
			}
			s = s + "";
			return s;
		}
	}

	//imprime la permuttation
	public String toString() {
		if (!this.isSubPermu) {
			String s = "[";
			s = s + this.tab[0];
			for (int i = 1; i < this.size; i++) {
				s = s + "," + this.tab[i];
			}
			s = s + "]";
			return s;
		}
		else {
			String s = "[";
			s = s + this.tabSubPermu[this.tab[0] - 1];
			for (int i = 1; i < this.size; i++) {
				s = s + "," + this.tabSubPermu[this.tab[i] - 1];
			}
			s = s + "]";
			return s;
		}
	}
}