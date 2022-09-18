package iwoca2017;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;


public class Permutation implements Comparable<Permutation> {
	private int[] tab = null;//tableau ordonne des elements ds la permutation
	private int[] tab2 = null;//tableau ordonne des elements ds la permutation
	private int[] tabSubPermu = null;//tableau ordonne des elements ds la permutation
	private int[] position = null;//tableau ordonne des elements ds la permutation
	private int size=0; //taille de la permutation
	private int dist=0; 
	private boolean isSubPermu =false;
	
	/*
	 *           i : 0 1 2 3 4 5 6  <-- indices
	 *      tab[i] : 1 3 4 5 2 7 6  <-- the permutation
	 * position[i] : 0 4 1 2 3 6 5  <-- the positions of elements, can be seen as the reverse permutation if add +1 :  1 5 2 3 4 7 6
	 *    ^element : 1 2 3 4 5 6 7  aka i+1
	 */
	
	//special for geography
	boolean hasDescendent = false;
	Permutation descendent =null;
	Permutation finalDescendent =null;
	Set<Permutation> finalDescendents = null;
	int sumAscendence = 0;
	
	
	//constructeur - n indetermine
	public Permutation (int t[]){
		//mort = false;
		size = t.length;
		tab = new int[size];
		tab2 = new int[size];
		position = new int[size];
		for (int i=0;i<size;i++){
			tab[i]=t[i];
			position[tab[i]-1]=i;
		}
		//gerer les mauvaises permutations ici
		gererMauvaisesPermu();
		isSubPermu =false;
	}
	
	public void setSubPermu(int t[]){
		isSubPermu =true;
		size = t.length;
		tabSubPermu = new int[size];
		for (int i=0;i<size;i++){
			tabSubPermu[i]=t[i];
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
	
	private void gererMauvaisesPermu(){
		Set<Integer> Nombres = new HashSet<Integer>();
		for (int i=0;i<size;i++){
			Nombres.add(tab[i]);
		}
		if (Nombres.size() != size){
			System.out.println("erreur: permutation incorrecte "+ toString());
		}
	}
	
	//imprime la permuttation
	public String toString (){
		if (!isSubPermu){
			String s = "[";
			s = s + tab[0];
			for (int i=1;i<size;i++)
				s = s + "," + tab[i];
			s = s + "]";
			return s;
		}else{
			String s = "[";
			s = s + tabSubPermu[tab[0]-1];
			for (int i=1;i<size;i++)
				s = s + "," + tabSubPermu[tab[i]-1];
			s = s + "]";
			return s;
		}
	}
	
	//imprime la permuttation
	public String toElectionString (){
		if (!isSubPermu){
			String s = "";
			s = s + tab[0];
			for (int i=1;i<size;i++)
				s = s + ">" + tab[i];
			s = s + "";
			return s;
		}else{
			String s = "";
			s = s + tabSubPermu[tab[0]-1];
			for (int i=1;i<size;i++)
				s = s + ">" + tabSubPermu[tab[i]-1];
			s = s + "";
			return s;
		}
	}
	
	//retourne la distance de Kendall-Tau entre 2 permutations
	public int distanceTo(Permutation other){
		int d=0;
		if (size == other.getSize()){
			for (int i=1;i<=size;i++)
				for (int j=i+1;j<=size;j++){
					if ((this.getPosition(i) > this.getPosition(j)) && (other.getPosition(i) < other.getPosition(j)))
						d++;
					else if ((this.getPosition(i) < this.getPosition(j)) && (other.getPosition(i) > other.getPosition(j)))
						d++;
				}
		}
		else
			d = -99999;
		
		return d;
	}
	
	//a faire
	//retourne la distance de Kendall-Tau entre 2 permutations
	//nlogn - calcul rapide
	public int fastDistanceTo(Permutation other){
		//en construction
		int d=0;
		if (size == other.getSize()){
			for (int i =0;i<size ;i++)
				tab2[i]=tab[i];
			d = recuDist(other , 0, size-1);
		}
		else
			d = -99999;

		return d;
	}
	
	//sorte de merge sort qui compte le nombre de swaps
	private int recuDist (Permutation other , int start, int stop){
		int reponse=0;
		int pt1=0, pt2=0, ptT=0;
		int moitie=0;
		int tabTemp[] = new int[stop - start + 1];
		if (stop - start == 0) 
			return 0;
		else{
			moitie = (int)(Math.floor((start + stop)/2)+0);
			reponse = recuDist(other , start, moitie) + recuDist (other , moitie+1, stop);
			//System.out.println(start + "-" + stop);
			pt2 = moitie+1;
			pt1=start;
			while (ptT!=tabTemp.length){
				if (pt1 <= moitie && pt2 <= stop){
					if (other.getPosition(tab2[pt1]) <= other.getPosition(tab2[pt2])){
						tabTemp[ptT] = tab2[pt1];
						pt1++;
					}else{
						tabTemp[ptT] = tab2[pt2];
						pt2++;
						reponse += (moitie - pt1 + 1);
						//System.out.println(moitie - pt1 + 1);
					}
				}else if (pt1 <= moitie && pt2 > stop){
					tabTemp[ptT] = tab2[pt1];
					pt1++;
				}else if (pt1 > moitie && pt2 <= stop){
					tabTemp[ptT] = tab2[pt2];
					pt2++;
				}
				ptT++;
			}
			for (int i = start; i<= stop;i++){
				tab2[i] = tabTemp[i- start];
				//System.out.println(tab2[i]);
			}
			return reponse;
		}
	}
	
	//retourne une approximation(borneInf) de la distance de Kendall-Tau entre 2 permutations
	public int approxDistanceTo(Permutation other){
		int d=0;
		int temp=0;
		if (size == other.getSize()){
			for (int i=1;i<=size;i++){
				temp = this.getPosition(i) - other.getPosition(i);
				if (temp>0)
					d+=temp;
			}	
		}
		else
			d = -99999;
		
		return d;
	}
	
	//retourne la distance de Kendall-Tau entre la permutation et toutes celles de l'autre ensemble
	public int distanceToSet(Set<Permutation> a){
		int distance = 0;
		for (Permutation p: a){
			distance += this.distanceTo(p);
		}
		return distance;
	}
	
	//a faire
	//retourne la distance de Kendall-Tau entre la permutation et toutes celles de l'autre ensemble
	public int fastDistanceToSet(Set<Permutation> a){
		int distance = 0;
		for (Permutation p: a){
			distance += this.fastDistanceTo(p);
		}
		return distance;
	}
	
	//retourne une approximation(borneInf) de la distance de Kendall-Tau entre la permutation et toutes celles de l'autre ensemble
	public int approxDistanceToSet(Set<Permutation> a){
		int distance = 0;
		for (Permutation p: a){
			distance += this.approxDistanceTo(p);
		}
		return distance;
	}
	
	//retourne la distance de Kendall-Tau entre la permutation et toutes celles de l'autre ensemble
	//calcul a base de la matrice de distance
	public int distanceToSetMatrix(int[][] tabD){
		int distance = 0;
		for (int i=0;i<size-1;i++){
			for (int j=i+1;j<size;j++){
				distance += tabD[tab[i]-1][tab[j]-1];
			}
		}
		return distance;
	}
	
	//renvoie la position dans la permutation de l'element passe en parametre
	public int getPosition(int element){
		return position[element-1];
		/*for (int k=0;k<size;k++)
			if (tab[k] == element) return k;	
		return -1;*/
	}
	
	//accesseur a Size
	public int getSize(){
		return size;
	}
	
	//modificateur a Size
	public void setSize(int s){
		if (s > 0) size = s;
	}
	
	//accesseur a dist
	public int getDist(){
		return dist;
	}
	
	//modificateur a dist
	public void setDist(int s){
		if (s > 0) dist = s;
	}
	
	//modificateur2 a dist
	public void addDist(int s){
		if (s > 0) dist += s;
	}

	//accesseur a tab
	public int[] getTab(){
		return tab;
	}
	
	//destructeur de la permutation, pour fin de test
	public void kill(){
		size = 1;
		tab = new int[size];
	}
	
	public String getCycles(){
		String reponse="";
		Set<Integer> nombres = new HashSet<Integer>();
		int nombre=0;
		int nbCycles=0;
		for (int i=1;i<=size;i++){
			nombre=i;
			if (!nombres.contains(nombre)){
				reponse += "(";
				reponse += nombre;
				nombres.add(nombre);
				while(tab[nombre-1]!=nombre && !nombres.contains(tab[nombre-1])){
					nombre = tab[nombre-1];
					reponse =reponse + ","+ nombre;
					nombres.add(nombre);
				}
				reponse += ")";
			}
		}
		
		for (int i =0; i< reponse.length();i++)
			if (reponse.charAt(i) == '(') nbCycles++;
		reponse += (" nbCycles= " + nbCycles);
		
		return reponse;
	}

	@Override
	public int compareTo(Permutation other) {
		return (dist - (other.getDist()));//pour le SortedSet dans heuristiqueCreerEV
		//return this.distanceTo(other);
	}
	
	/*public static Comparator<Permutation> PermutationComparator = new Comparator<Permutation>() {
		public int compare(Permutation permu1, Permutation permu2) {
			return (permu1.getDist() - (permu2.getDist()));
		}
	};*/
	
	@Override
	public boolean equals(Object that) {
		int[] tab2 = null;
		if (this ==that) return true;
		if (!(that instanceof Permutation)) return false;
		
		//return false; // pour methode rapprochement
		
		
		//methode a tester (rique?)
		Permutation that2 = (Permutation)that;
		tab2 = that2.getTab();
		//if (tab[0] != tab2[0]) return false;//je doute de l'effficacite de ceci
		//if (tab[1] != tab2[1]) return false;
		for (int i=0; i<size; i++){
			if (tab[i] != tab2[i]) return false;
		}
		return true;
		
	}
	
	@Override
	public int hashCode() {
		int valeur =0;
		for (int i=0;i<size;i++)
			valeur += tab[i]*Math.pow(size, i);
		return valeur;
	}

	public String showPair(int i, int j) {
		String s = "[";
		if (tab[0]== i){
			s = s + i;
		}else if (tab[0]== j){
			s = s  + j;
		}else{
			s = s + "-";
		}
		for (int k=1;k<size;k++){
			if (tab[k]== i){
				s = s + "," + i;
			}else if (tab[k]== j){
				s = s + "," + j;
			}else{
				s = s + "," + "-";
			}
		}
		s = s + "]";
		return s;
	}
	
	
	
	
	
	
	
	
	//special for geography
	
	public boolean hasDescendent(){
		return hasDescendent;
	}
	
	public void setDescendent(Permutation permu){
		descendent = permu;
		hasDescendent = true;
		if (!permu.hasDescendent()){
			finalDescendent = permu;
			permu.addAscendent();
		}else{
			finalDescendent = permu.getFinalDescendent();
			finalDescendent.addAscendent();
		}
	}
	
	public void setDescendents(Permutation permu){
		//descendent = permu;
		
		if (hasDescendent == true){
			//nothing
		}else{
			finalDescendents = new HashSet<Permutation>();
			hasDescendent = true;
		}
		
		if (!permu.hasDescendent()){
			if (!finalDescendents.contains(permu)){
				finalDescendents.add(permu);
				permu.addAscendent();
			}
		}else{
			//finalDescendents.addAll(permu.getFinalDescendents());
			for (Permutation p : permu.getFinalDescendents()){
				if (!finalDescendents.contains(p)){
					finalDescendents.add(p);
					p.addAscendent();
				}
			}	
				
		}

	}
	
	public Permutation getDescendent(){
		return descendent;
	}
	
	public Set<Permutation> getFinalDescendents(){
		//System.out.println("boom");
		return finalDescendents;
	}
	
	public Permutation getFinalDescendent(){
		return finalDescendent;
	}
	
	public void addAscendent(){
		sumAscendence++;
	}
	
	public int getSumAscendence(){
		return sumAscendence;
		
	}

	public int[] getCompInvTab() {
		int[] reponse = new int[size];
		for(int i=0;i<size;i++){
			reponse[i]=position[i]+1;
			//System.out.println(permutation_inverse[i]);
		}
		return reponse;
	}
	
	public int[] getOppositeTab() {
		int[] reponse = new int[size];
		for(int i=0;i<size;i++){
			reponse[i]=tab[size-i-1];
			//System.out.println(permutation_inverse[i]);
		}
		return reponse;
	}
}
