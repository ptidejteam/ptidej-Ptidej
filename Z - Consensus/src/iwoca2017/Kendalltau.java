package iwoca2017;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
//import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
//import java.util.Iterator;
import java.util.List;
//import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Date;
import java.util.SortedMap;
//import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
//import java.util.BitSet;

//import ilog.cplex.IloCplex;
//import ilog.concert.*;
//import ilog.cplex.*;

class IntPair {
	  final int x;
	  final int y;
	  IntPair(int x, int y) {this.x=x;this.y=y;}
	  public String toString(){ String s = "("+ x + "," + y + ")";return s;}
	  public String toString2(){ String s = x + "<" + y ;return s;}
	  public int compareTo(IntPair other) {if (x == other.x) return y -other.y; else return x -other.x;}
	  public boolean equals(Object other) { if (this ==other) return true; if (!(other instanceof IntPair)) return false; IntPair other2 = (IntPair)other; if ((x == other2.x) && (y == other2.y)) return true; return false;}
	}

public class Kendalltau {
	//mesure du temps d'execution des algos, heuristiques, tests et etudes
	static long lStartTime=0;
	static long lEndTime=0;
	static long difference=0;
	
	static //affichage
	DecimalFormat df = new DecimalFormat("#.##");
		
	static Set<Permutation> Sn; //ensemble de toutes les permutations
	static Set<Permutation> SnUnderConstraints;// like Sn but with permutation that are respecting the constraints
	
	//static boolean[][] tabMOT4;//matrice de contraintes MOT: tabMOT[i][j]=true == (i<j) ds contraintes MOT
	//static int[][] tabMOT4dist;//matrice de contraintes MOT: tabMOT[i][j]=true == (i<j) ds contraintes MOT
	

	static int minSubScoresSize = 0;
	static int maxSubScoresSize = 0;
	static int memoryLimit;//max entries in the topScores collection
	
	//static Set<Node> arbreMOT3;
	//static Node rootArbreMOT3;
	

	
	
	

	//statistiques thm cool
	static double thm_tjrs_stats = 0.0;
	static double thm_cool_stats = 0.0;
	static double fermeture_stats = 0.0;
	static double thm_cool_extend_stats = 0.0;
	static double resolution_stats = 0.0;
	
	static double thm_always_stats = 0.0;
	static double thm_major_1_0_stats = 0.0;
	static double thm_major_2_0_stats = 0.0;
	static double thm_major_3_0_stats = 0.0;
	static double thm_MOT3_iterationAvg_stats = 0.0;
	static double thm_major_3_0p_stats = 0.0;
	static double thm_MOT3p_iterationAvg_stats = 0.0;
	static double thm_major_3_0pp_stats = 0.0;
	static double thm_MOT3pp_iterationAvg_stats = 0.0;
	static double thm_MOT3_iterationMax_stats = 0.0;
	static double thm_betzler_stats = 0.0;
	
	static boolean thm_betzler_application = false;
	static double thm_betzler_application_stats = 0.0;//combien de fois Betzler est applicable
	static double thm_major_3_0_revised_count = 0.0;//stats de Major 3.0 sur les donnees Betzler-applicable
	static double thm_betzler_revised_count = 0.0;//stats de Betzler sur les donnees Betzler-applicable
	static double thm_major_3_0_revised_stats = 0.0;//stats de Major 3.0 sur les donnees Betzler-applicable
	static double thm_betzler_revised_stats = 0.0;//stats de Betzler sur les donnees Betzler-applicable
	static double thms_inclusion_revised_stats = 0.0;//stats sur le nombre de fois ou major est inclu dans betzler
	
	static String resoluMOT = "";//pour algo, % paires ordonnees par MOT
	static double totalMedianes = 0.0;//pour stats
	static double nbMedianesPaires = 0.0;//pour stats
	static double nbMedianesImpaires = 0.0;//pour stats
	
	
	
	//test inclusion thms betzler ds cool
	static List<IntPair> contraintesCool = new ArrayList<IntPair>(); //liste des contraintes
	static List<IntPair> contraintesBetzler = new ArrayList<IntPair>(); //liste des contraintes
	static boolean[][] tabContraintesBetzler;
	
	
	
	//SA stats
	static int maxDeltaEnergy;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String fichier_a_tester = "";
		
		

	   

	    
	    //mesure du temps
		lStartTime = new Date().getTime();	
	    difference = (lStartTime); //check different
	    //System.out.println("Temps debut: " +((difference %(86400000))/3600000) + " heures " + +((difference %3600000)/60000) + " minutes " + ((difference % 60000)/1000) + " secondes ");
	    
	     

	    
	    //VsAntaki
	    //initialisateurVsAntaki(10, 3, 150);
	    //solverVsAntaki("randomSets_ite10_m3_n150.txt");
		
		
		
	    
	    
	    
 
	    //stats fev 2017
	    /*
	    int addProcesseur = 0;
	    addProcesseur = Integer.parseInt(args[0]);
	    double refroidissement = 0.0;
	    refroidissement = Double.parseDouble(args[1]);
	    for (int myN = 8+(addProcesseur*2); myN <= 40; myN+=14 ){
		    for (int myM = 3; myM <= 10; myM++ ){
		    	find_SA_parameters(myM,myN,refroidissement);
		    }
		    lEndTime = new Date().getTime(); //end time
		    difference = (lEndTime - lStartTime); //check different
		    System.out.println("Temps pour execution: " +(difference/60000) + " minutes " + ((difference % 60000)/1000) + " secondes " + (difference % 1000) + " millisecondes");
		    find_SA_parameters(15,myN,refroidissement);
		    find_SA_parameters(20,myN,refroidissement);
		    find_SA_parameters(25,myN,refroidissement);
		    find_SA_parameters(30,myN,refroidissement);
		    find_SA_parameters(35,myN,refroidissement);
		    find_SA_parameters(40,myN,refroidissement);
		    find_SA_parameters(45,myN,refroidissement);
		    find_SA_parameters(50,myN,refroidissement);
		    lEndTime = new Date().getTime(); //end time
		    difference = (lEndTime - lStartTime); //check different
		    System.out.println("Temps pour execution: " +(difference/60000) + " minutes " + ((difference % 60000)/1000) + " secondes " + (difference % 1000) + " millisecondes");
		    lStartTime = new Date().getTime();
	    }*/
	    
	    
	    //SA_average_solution_stat(1000);

	    /*
	    boolean eProcesseur = false;
	    int EProcesseur = 0;
	    EProcesseur = Integer.parseInt(args[0]);
	    if (EProcesseur == 1){
	    	eProcesseur = true;
	    }else if (EProcesseur == 0){
	    	eProcesseur = false;
	    } else{
	    	eProcesseur = false;
	    	System.out.println("erreur 5674165559");
	    }
	    int nProcesseur = 0;
	    nProcesseur = Integer.parseInt(args[1]);
	    int iteProcesseur = 0;
	    iteProcesseur = Integer.parseInt(args[2]);
	    lanceurStatsArticle3 (nProcesseur, iteProcesseur, 999, eProcesseur);
	    */
	    /*
	    lanceurStatsArticle3 (10, 1000, 999, false);
	    lanceurStatsArticle3 (15, 1000, 999, false);
	    lanceurStatsArticle3 (20, 1000, 999, false);
	    lanceurStatsArticle3 (30, 1000, 999, false);
	    lanceurStatsArticle3 (40, 500, 999, false);
	    */
	    
	    
	    
	    
	    
	    
	    //etudeInclusionThmBetzlerDansThmCool ();
	    
	    //geographySpaceSn();

	    //integerMedianGame();
	    
	    
	    
	    
	    

	    	
	    //lStartTime = new Date().getTime();	
	    
	    
	    //for (int ii = 1; ii<= 10; ii++){
	    //	System.out.println("ii"+ii+"ii");
	    
	    
	    //search engine result rankings
	    //convertSearchEngineResultsToElection("music_show_montreal",3);
		//A=convertElectionsToPermutations("music_show_montreal.election",0);
		
		//convertSearchEngineResultsToElection("jobs",3);
		//A=convertElectionsToPermutations("jobs.election",0);
		
		
		
	    //postIwocaStats();
		
		
	    
	    
	    //int[] a = {8,2,15,10};
	    //A = creerSubA(A,a); areSubPermu = true;
	    
	    int m = 3;
	    int n = 30;
	    Instance myInstance = new Instance(m,n);
	    
	    //Instance myInstance = new Instance(creerA());
	    //Instance myInstance = new Instance(3,60);
	    //Instance myInstance = new Instance();
	    //myInstance.loadFromFile("cplex/batch1/ex_"+m+"_"+n+".txt", 1);
	    //myInstance.loadElectionFromFile("lecture_ecriture/fichier_test1.election");
	    //myInstance.writeIntoFile("lecture_ecriture/fichier_test1.txt", false);
	    //myInstance.writeElectionIntoFile("lecture_ecriture/fichier_test1.election", false);
	    //myInstance.writeIntoFile("lecture_ecriture/fichier_test"+ii+".txt", false);
	    //myInstance.writeElectionIntoFile("lecture_ecriture/fichier_test"+ii+".election", false);
		myInstance.print();
		
		//myInstance.writeIntoFile("cplex/batch1/ex_"+m+"_"+n+".txt", false);
		//myInstance.writeCplexLPIntoFile("cplex/batch1/ex_"+m+"_"+n+".lp",false);

	  
	    //permutationMedianGame(A);
	    
	    

	    //A=convertElectionsToPermutations("elections/sport_ski_crosskiing06-09men.election",0);
	    //A=convertElectionsToPermutations("elections/sport_ski_jumping.election",0);
	    //A=convertElectionsToPermutations("elections/sport_F1_1970.election",0);
		//printA();
		
	    //testElectionsBetzler();
	    //etudeBorneInf(A);
	    //etudeBorneInf2(A);
	    
		
		
	  	
	  	//algorithme de recherche
			
		
		    heuristicsPack(myInstance, true);
		    //myInstance.writeCplexMSTIntoFile("cplex/batch1/ex_"+m+"_"+n+".mst");
			constraintsPack(myInstance,true,true);
			//myInstance.writeCplexLPIntoFile("cplex/batch1/ex_"+m+"_"+n+"_wC.lp",true);
			lowerBoundPack(myInstance,true);
			
			//solvers:
			BranchAndBound(myInstance, true);
			//cplexUsage(myInstance, true, true);

			
			//myInstance.printM(10);
			
			//myInstance.writeResultIntoFile("lecture_ecriture/fichier_test1.results");
			//myInstance.writeResultIntoFile("lecture_ecriture/fichier_test"+ii+".results");
			

		
	    
		//showBrokenMajorOrders();
		
		
		//affiche_contraintes_spatiales(15);
	    //}

	    //writeElectionResults("jobs.result",0);
	    
		
		lEndTime = new Date().getTime(); //end timeetudeEcartMax
	    difference = (lEndTime - lStartTime); //check different
	    System.out.println("Temps pour execution: " +(difference/60000) + " minutes " + ((difference % 60000)/1000) + " secondes " + (difference % 1000) + " millisecondes");
	    lStartTime = new Date().getTime();
	    
	    //}
	    //System.out.println(resoluMOT + "\t" + ((difference)/1000) + "." + (difference % 1000) + "");
	    //System.out.println(((difference)/1000) + "." + (difference % 1000) + " secondes");
	    
	    
	    //}
		

	
	}
	
	/*private static void postIwocaStats() {
		//int m = 3;
	    int n = 24;
	    System.out.println("m  n  %");
	    
	    
	    postIwocaStat( 3,  n);
	    postIwocaStat( 4,  n);
	    for (int m = 5; m <= 50; m+=5){
	    	postIwocaStat( m,  n);
	    }
	    
	}*/
	
	/*private static void postIwocaStat(int m,  int n) {
		
	    int SAmode = 3;//normal mode
	    
	    double result=0.0;
	    int counterYes=0;
	    int counterNo=0;
	    
		for (int i = 1; i <= 100;i++){
		    Instance myInstance = new Instance(m,n);
		    cplexUsage(myInstance, true, false);
		    
		    for (int j = 1; j <= 10;j++){
				heuristiqueCreerSA(myInstance, false ,false,SAmode);
				//heuristiqueCreerSA(myInstance, false ,verbose,SAmode);
				//heuristiqueCreerSA(myInstance, false ,verbose,SAmode);
				//heuristiqueCreerSA(myInstance, false ,verbose,SAmode);
				//heuristiqueCreerSA(myInstance, false ,verbose,SAmode);
				//if (verbose) System.out.println("Medians.size() "+myInstance.Medians.size());
				
				//myInstance.trimMedianSet();
				if (myInstance.cplexMIPresult == myInstance.SA_upper_bound){
					//System.out.println("yep");
					counterYes++;
				}else{
					//System.out.println("nope");
					counterNo++;
				}
		    }
		}
		
		//System.out.println("(m="+m+" n="+n+")  yes: "+ counterYes+ ", no: " + counterNo+ ", total:" + (counterYes + counterNo));
		result = 100.0*(double)counterYes/((double)(counterYes+counterNo));
		System.out.println(""+m+" "+n+" "+ result);
		
	}*/

	private static void lowerBoundPack(Instance myInstance, boolean verbose) {
		int n = myInstance.n;
		
		if (verbose) System.out.println("");
		if (verbose) System.out.println(" ***LOWER BOUND***");
		if (verbose) System.out.println("");
		
		// TODO Auto-generated method stub
		//********************FIRST LOWER BOUND
		int borneInf=0;//borne inferieure de la somme partielle de distance du au nombres pas encore places
		//calcule la borne inferieure de la distance mediane supp des nombres 'libres' a placer
		if (100 <= 500){
			//for (int j: nombres){
			for (int i=1; i<= n; i++){
				//for (int k: nombres){
				for (int j=1; j<= n; j++){
					if (j != i){
						borneInf += Math.min(myInstance.tabD[i-1][j-1], myInstance.tabD[j-1][i-1]);
						//borneInf += Math.min(myInstance.tabD[nombres.get(j)-1][nombres.get(k)-1], myInstance.tabD[nombres.get(k)-1][nombres.get(j)-1]);
					}
				}
			}
			borneInf /= 2;
		}
		//affichage des borneInf borneSup d'avant calcul
		//System.out.println("borneInf de la distance mediane (matrice de distance): " + borneInf);
		
		
		//********************STRONGER LOWER BOUND (WITH 3/4-CYCLES)
		
		int lowerBoundResult = 0;
		int maxLowerBoundResult =0;
		
		//randomized lower bound
		lowerBoundResult = borneInfSetRand(myInstance,null, 10);
		if (lowerBoundResult > maxLowerBoundResult) maxLowerBoundResult = lowerBoundResult;
		if (verbose) System.out.println("borneInfSetRand: "+ lowerBoundResult);
		
		
		//3-cycles bound : gives the 3-cycles for the BnB
		int borneInfAdd = add3CyclesToLowerBound(myInstance,false );//add the 3-cycles for the BnB method
		if (verbose) System.out.println("borneInf:"+borneInf+" (+"+borneInfAdd+")= "+ (borneInf+borneInfAdd));
		myInstance.setLowerBound(borneInf+borneInfAdd);
		myInstance.simple_lower_bound = borneInf;
		myInstance.add3cyles_lower_bound = borneInfAdd;
		myInstance.natural_lower_bound=borneInf+borneInfAdd;
		
		
		//regular 3-cycle lower bound
		lowerBoundResult = borneInfSet(myInstance,null, 3);
		myInstance.cycles3_lower_bound = lowerBoundResult;
		if (lowerBoundResult > maxLowerBoundResult) maxLowerBoundResult = lowerBoundResult;
		if (verbose) System.out.println("borneInf2.3: "+ lowerBoundResult );
		
		//regular 4-cycle lower bound
		lowerBoundResult = borneInfSet(myInstance,null, 4);
		myInstance.cycles4_lower_bound = lowerBoundResult;
		if (lowerBoundResult > maxLowerBoundResult) maxLowerBoundResult = lowerBoundResult;
		if (verbose) System.out.println("borneInf2.4: "+ lowerBoundResult);
		
		//3-cycles with constraints lower bound + cycles
		lowerBoundResult = borneInfSet_wConstraints(myInstance,null, 3, null);
		myInstance.cyclesN_wConstraints_lower_bound = lowerBoundResult;
		if (lowerBoundResult > maxLowerBoundResult) maxLowerBoundResult = lowerBoundResult;
		if (verbose) System.out.println("borneInf2.3c_wConstraints+cycles: "+ lowerBoundResult);
		
		//3-cycles with constraints lower bound + cycles (fractionnals)
		lowerBoundResult = borneInfSet_wConstraints_2(myInstance,null);
		myInstance.cyclesN_wConstraints_lower_bound = lowerBoundResult;
		if (lowerBoundResult > maxLowerBoundResult) maxLowerBoundResult = lowerBoundResult;
		if (verbose) System.out.println("borneInf2.3c_wConstraints_2+cycles: "+ lowerBoundResult);
		
		//3-cycles +max flow lower bound
		lowerBoundResult = borneInfSet_maxFlow(myInstance,null);
		myInstance.cycles3_maxFlow_lower_bound = lowerBoundResult;
		if (lowerBoundResult > maxLowerBoundResult) maxLowerBoundResult = lowerBoundResult;
		if (verbose) System.out.println("borneInf maxFlow: "+ lowerBoundResult );
		
		/*lowerBoundResult = borneInfSet_wConstraints(myInstance,null, 3, myInstance.pickARandomMedian());
		myInstance.cyclesN_wConstraints_guided_lower_bound = lowerBoundResult;
		if (lowerBoundResult > maxLowerBoundResult) maxLowerBoundResult = lowerBoundResult;
		if (verbose) System.out.println("borneInf2.3ca_wConstraints: "+ lowerBoundResult );*/
		
		
		
		myInstance.computeFirstGap();
		
		
		
		
		
		
		//branch and bound on the lower bound
		
		
		//BnB2(myInstance, verbose);
		
		
		//cplexUsage(myInstance, false, verbose);
		//cplexUsage(myInstance, true, verbose);
		
	}

	private static void constraintsPack(Instance myInstance, boolean verbose, boolean egalite) {
		int n = myInstance.n;
		
		if (verbose) System.out.println("");
		if (verbose) System.out.println(" ***CONSTRAINTS***");
		if (verbose) System.out.println("");
		
		//********************CONSTRAINTS : DUOS AND TRIPLES
		//create all optimal duos and triplets
		duos(myInstance,verbose);
		triplets(myInstance,verbose);
				
				
				
		//********************CONSTRAINTS MOT3.0e AND LUBC
		//thmMajorEgalite_matriciel(A, false, false, false, egalite);
		//MOT3(myInstance,A,false, false, verbose, egalite,distMedianApprox);
		MOT3_LUBC(myInstance,false, false, verbose, egalite);
		
		
		/*int compteurContrainteApproxPaire = 0;
		for (int i = 1; i<= n; i++){
			for (int j = i+1; j<= n; j++){
				if (tabC[i-1][j-1]){
					
				}else if (tabC[j-1][i-1]){
					
				}else{
					if (!contrainteApproxPaire (i, j, n, distMedianApprox,true)){
						compteurContrainteApproxPaire++;
						tabC[j-1][i-1]=true;
					}
					if (!contrainteApproxPaire (j, i, n, distMedianApprox,true)){
						compteurContrainteApproxPaire++;
						tabC[i-1][j-1]=true;
					}
				}
			}
		}
		resolution_exacte = 100.0*(compteurContrainteApproxPaire)/(n*(n-1)/2);
		System.out.println("compteurContrainteApproxPaire: "+compteurContrainteApproxPaire + "/" + (n*(n-1)/2)
				+ " ("+df.format(resolution_exacte)+"%)");
		*/
		
		//********************CONSTRAINTS LEFT/RIGHT
		//creation des contraintes
		//List<HashSet<Integer>> contraintesG = new ArrayList<HashSet<Integer>>();
		//List<HashSet<Integer>> contraintesD = new ArrayList<HashSet<Integer>>();
		myInstance.contraintesG = new ArrayList<HashSet<Integer>>();
		myInstance.contraintesD = new ArrayList<HashSet<Integer>>();
		Set<Integer> sousContraintesG;
		Set<Integer> sousContraintesD;
		int aGauche=0,aDroite=0;
		for (int i=1;i<=n;i++){
			sousContraintesG = new HashSet<Integer>();
			sousContraintesD = new HashSet<Integer>();
			for (int j=1; j<=n;j++){
				if (i != j){
					aGauche=0;
					aDroite=0;
					for (Permutation p: myInstance.A){
						if (p.getPosition(j) < p.getPosition(i)){
							aGauche++;
						}
						else{
							aDroite++;
						}
					}
					
					//System.out.println("aDroite:"+aDroite+" aGauche:"+aGauche);
					
					//optimisation elagage arbre
					//tabD[j-1][i-1]=aDroite;//?
					//fin optim
					

					if (aDroite < aGauche){
						sousContraintesG.add(j);
					}
					else {
						if(aDroite > aGauche){
							sousContraintesD.add(j);
						}
						else{
							sousContraintesG.add(j);
							sousContraintesD.add(j);
						}
					}
				}
				
			}
			myInstance.contraintesG.add((HashSet<Integer>) sousContraintesG);
			myInstance.contraintesD.add((HashSet<Integer>) sousContraintesD);
		}
		//transformation des contraintes G/D en version matricielle
		myInstance.tabContraintesG = new boolean [n][n];
		myInstance.tabContraintesD = new boolean [n][n];
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				if (myInstance.contraintesD.get(i-1).contains(j)){
					myInstance.tabContraintesD[i-1][j-1]=true;
				}else{
					myInstance.tabContraintesD[i-1][j-1]=false;
				}
				
				if (myInstance.contraintesG.get(i-1).contains(j)){
					myInstance.tabContraintesG[i-1][j-1]=true;
				}else{
					myInstance.tabContraintesG[i-1][j-1]=false;
				}
			}
		}
		
		
		
	}

	private static void heuristicsPack(Instance myInstance, boolean verbose) {
		// TODO Auto-generated method stub
		if (verbose) System.out.println("");
		if (verbose) System.out.println(" ***HEURISTICS***");
		if (verbose) System.out.println("");
		heuristicBestOfA(myInstance, verbose);
		heuristic2opt(myInstance, verbose);
		heuristic3opt(myInstance, verbose);
		heuristicBordaCount(myInstance, verbose);
		heuristicCopeland(myInstance, verbose);
		heuristicMedianGame(myInstance, verbose);
		heuristicBordaCount3opt(myInstance, verbose);
		heuristicCopeland3opt(myInstance, verbose);
		heuristicCircMvtLocalSearch(myInstance, verbose,false);
		//heuristicCircMvtLocalSearch(myInstance, verbose,true);
		int SAmode = 3;//normal mode
		heuristiqueCreerSA(myInstance, false ,verbose,SAmode);
		/*heuristiqueCreerSA(myInstance, false ,verbose,SAmode);
		heuristiqueCreerSA(myInstance, false ,verbose,SAmode);
		heuristiqueCreerSA(myInstance, false ,verbose,SAmode);
		heuristiqueCreerSA(myInstance, false ,verbose,SAmode);*/
		//if (verbose) System.out.println("Medians.size() "+myInstance.Medians.size());
		myInstance.trimMedianSet();
		//if (verbose) System.out.println("Medians.size() "+myInstance.Medians.size());
		
		
		//constraints table
		myInstance.createTabPairStatus();

		//more constraints?
		/*System.out.println("Constraints to find:");
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				//if (myInstance.tabPairStatus[i-1][j-1]==4)
				if (myInstance.tabPairStatus[i-1][j-1]==4 && (myInstance.tabD[i-1][j-1] < myInstance.tabD[j-1][i-1]))
					System.out.println(" "+i+"<"+j);
			}
		}*/
	}
	
	/*public static void cplexUsage(Instance myInstance, boolean MIP, boolean verbose){
		if (verbose) System.out.println("");
		if (verbose) System.out.println(" ***CPLEX***");
		if (verbose) System.out.println("");
		
		int n = myInstance.n;
		int compteur = 0;
		int coord[][]=new int[n][n];
		//int coord1[]=new int[(n*(n-1))];
		//int coord2[]=new int[(n*(n-1))];
		boolean success= false;
		
		try {
			IloCplex cplex = new IloCplex();
	         // create model and solve it
			
			cplex.setOut(null);//quiet mode
			
			IloNumVar[] x;
			if (MIP){
				x = cplex.intVarArray((n*(n-1)), 0, 1);//probleme original
			}else{
				x = cplex.numVarArray((n*(n-1)), 0.0, 1.0);//probleme relaxe
			}
			//IloNumVar[] x = cplex.numVarArray((n*(n-1)), 0.0, 1.0);//probleme relaxe
			//IloNumVar[] x = cplex.intVarArray((n*(n-1)), 0, 1);//probleme original
			IloLinearNumExpr expr = cplex.linearNumExpr();
			
			compteur = 0;
			for(int i=1;i<=n;i++){
				for(int j=1;j<=n;j++){
					if (i != j){
						coord[i-1][j-1] = compteur;
						expr.addTerm(myInstance.tabD[i-1][j-1], x[compteur]);
						compteur++;
					}
				}
			}
			cplex.addMinimize(expr);
			
			for(int i=1;i<=n;i++){
				for(int j=i+1;j<=n;j++){
					cplex.addEq(cplex.sum(x[coord[i-1][j-1]], x[coord[j-1][i-1]]), 1);
				}
			}
			
			for(int i=1;i<=n;i++){
				for(int j=1;j<=n;j++){
					if (i !=j){
						for(int k=1;k<=n;k++){
							if (k != i && k != j){
								cplex.addGe(cplex.sum(x[coord[i-1][j-1]], x[coord[j-1][k-1]], x[coord[k-1][i-1]]), 1);
								//out.write(" c"+compteur+": x_"+i+"_"+j+" + x_"+j+"_"+k+" + x_"+k+"_"+i+" >= 1 \n");
							}
						}
					}
					
				}
			}
			
			cplex.setParam(IloCplex.Param.Threads, 1);
			//cplex.setParam(IloCplex.Param.TimeLimit, 5.0);
			//cplex.setParam(IloCplex.Param.DetTimeLimit, 1000.0);
			cplex.setParam(IloCplex.Param.MIP.Tolerances.MIPGap, 0.0);
			
			success=cplex.solve();
			if (verbose) System.out.println("cplex success: "+success);
			
			double objval = cplex.getObjValue();
			//objval -= 0.001;//to cope with numerical residus
			if (verbose) System.out.println("cplex objval: "+objval + "  ("+Math.ceil(objval)+")");
			
			
			if (MIP){
				myInstance.cplexMIPresult = (int)objval;
			}else{
				myInstance.cplexLPresult = objval;
			}
			
			cplex.clearModel();
			cplex.end();
			
	    } catch (IloException e) {
	    	System.err.println("Concert exception caught: " + e);
	    }
	}*/
	
	public static void BnB2(Instance myInstance, boolean verbose){
		//lower bound 2??
		if (verbose) System.out.println("");
		if (verbose) System.out.println(" ***BnB2 on lower bound***");
		if (verbose) System.out.println("");
		SortedSet<BnB2Node> queue = new TreeSet<BnB2Node>();
		BnB2Node firstNode = new BnB2Node(myInstance.n);
		BnB2Node currentNode;
		int MAXITERATIONS=10000;
		int nbIterations=1;
		int the_lowest_bound;
		int rejectedBranches=0;
		firstNode.giveConstraints(myInstance.tabC);
		firstNode.calculateLowerBound(myInstance);
		queue.add(firstNode);
		the_lowest_bound=firstNode.best_lower_bound;
		
		while((nbIterations <= MAXITERATIONS) && (!queue.isEmpty()) && (the_lowest_bound < myInstance.best_upper_bound)){
			currentNode = queue.first();
			queue.remove(currentNode);
			if (nbIterations % 1000 ==0){
				System.out.println("("+nbIterations+")"+"   queue.size(): "+queue.size() +"   rejectedBranches: "+ rejectedBranches);
			}
			
			if (currentNode.best_lower_bound > the_lowest_bound){
				the_lowest_bound = currentNode.best_lower_bound;
				System.out.println("("+nbIterations+") lowest bound = " + currentNode.best_lower_bound);
			}
			
			BnB2Node branch1Node= new BnB2Node(currentNode.n);
			BnB2Node branch2Node= new BnB2Node(currentNode.n);
			
			branch1Node.giveConstraints(currentNode.getTab());
			branch2Node.giveConstraints(currentNode.getTab());
			int e1=-1,e2=-1;
			
			for (int i = 1; i<= currentNode.n; i++){//priority to the pairs always present in SA
				for (int j = 1; j<= currentNode.n; j++){
					if (!currentNode.tabC[i-1][j-1] && !currentNode.tabC[j-1][i-1] && (myInstance.tabPairStatus[i-1][j-1] == 4 || myInstance.tabPairStatus[i-1][j-1] == 2)){
						e1=i;
						e2=j;
					}
				}
			}
			if (e1==-1 && e2==-1){
				for (int i = 1; i<= currentNode.n; i++){
					for (int j = 1; j<= currentNode.n; j++){
						if (!currentNode.tabC[i-1][j-1] && !currentNode.tabC[j-1][i-1]){
							e1=i;
							e2=j;
						}
					}
				}
			}
			
			if (e1!=-1 && e2!=-1){
				branch1Node.addConstraint(e1, e2);
				branch2Node.addConstraint(e2, e1);
				branch1Node.calculateLowerBound(myInstance);
				branch2Node.calculateLowerBound(myInstance);
				
				if (branch1Node.best_lower_bound < myInstance.best_upper_bound){
					boolean u = queue.add(branch1Node);
					//System.out.println(u);
				}else{
					//if (verbose) System.out.println("branch1 rejected");
					rejectedBranches++;
				}
				if (branch2Node.best_lower_bound < myInstance.best_upper_bound){
					boolean u = queue.add(branch2Node);
					//System.out.println(u);
				}else{
					//if (verbose) System.out.println("branch2 rejected");
					rejectedBranches++;
				}
			}else{
				System.out.println("*******************************erreur?");
			}
			//System.out.println(queue);
			
			nbIterations++;
		}
		System.out.println("queue.size(): "+queue.size());
		if (queue.size() == 0){
			System.out.println("lower bound :" +myInstance.best_upper_bound);
		}else{
			System.out.println("lower bound :" +the_lowest_bound);
		}
		queue.clear();
	}
	
	private static void heuristicCircMvtLocalSearch(Instance myInstance, boolean verbose, boolean zeroMovesAllowed) {
		// TODO Auto-generated method stub
		Permutation pi = null;
		int score = 0;
		int nextScore = 0;
		Permutation nextPi = null;
		int numberOfSteps = 10;//10
		int stepsCount = 0;
		int numberOfCircMvts =1;//2
		boolean localyOptimal = false;
		
		//initial permutation and score
		pi = myInstance.Copeland_opt3_permutation;
		score = myInstance.Copeland_opt3_upper_bound;
		
		//pi = createIndentite(myInstance.n);//testing
		//score = pi.distanceToSetMatrix(myInstance.tabD);
		
		
		//if (verbose) System.out.print("Init "+" ");
		//if (verbose) System.out.print(" "+score+" \n");
		//upgrading steps
		while(!localyOptimal && stepsCount < numberOfSteps){
			stepsCount++;
			//if (verbose) System.out.print("Step "+stepsCount+" ");
			nextPi =  circMvtLocalSearch(myInstance, false, pi, score, numberOfCircMvts, score);
			nextScore = nextPi.distanceToSetMatrix(myInstance.tabD);
			
			//System.out.println("CircMvtLocalSearch**** ("+nextScore+") "+nextPi);
			
			if (nextScore < score){
				pi = new Permutation(nextPi.getTab());
				score= nextScore;
			}else if ((nextScore == score) && zeroMovesAllowed){
				pi = new Permutation(nextPi.getTab());
				score= nextScore;
			}else{
				localyOptimal = true;
			}
			//if (verbose) System.out.print(" "+score+" \n");
		}
		
		myInstance.CircMvtLocalSearch_upper_bound = score;
		myInstance.addSolverPermutation(pi);
		myInstance.setUpperBound(score);
		if (zeroMovesAllowed){
			if (verbose) System.out.println("CircMvtLocalSearch+0 ("+score+") "+pi);
			//if (verbose) System.out.println("CircMvtLocalSearch+0 params:  numberOfCircMvts "+numberOfCircMvts+",  numberOfSteps "+numberOfSteps);
		}else{
			if (verbose) System.out.println("CircMvtLocalSearch ("+score+") "+pi);
			//if (verbose) System.out.println("CircMvtLocalSearch params:  numberOfCircMvts "+numberOfCircMvts+",  numberOfSteps "+numberOfSteps);
		}
		
	}
	
	private static Permutation circMvtLocalSearch(Instance myInstance, boolean verbose, Permutation pi, int score, int numberOfCircMvts, int initialScore) {
		Permutation bestPermu = null;
		int bestScore = 999999999;
		Permutation nextPi=null;
		Permutation nextPi2=null;
		int n= myInstance.n;
		int[][] delta  = new int[n][n];
		int[] tabElements = new int[n];
		int temp = 0;
		
		/*for (int i=0; i<=n-1; i++){
			tabElements[i]=pi.getTab()[i];	
		}*/
		
		for (int j=0; j<=n-1; j++){
			delta[j][j] =0;
			//to the right (i<j)
			//System.out.print(" to the right \n");
			for (int i=0; i<=n-1; i++){
				tabElements[i]=pi.getTab()[i];	
			}
			
			for (int i=j-1; i>=0; i--){
				//delta[i][j] =delta[i+1][j]+(myInstance.tabD[tabElements[j]-1][tabElements[i]-1]-myInstance.tabD[tabElements[i]-1][tabElements[j]-1]);
				delta[i][j] =delta[i+1][j]+(myInstance.tabD[tabElements[i+1]-1][tabElements[i]-1]-myInstance.tabD[tabElements[i]-1][tabElements[i+1]-1]);
				
				temp = tabElements[i];
				tabElements[i] = tabElements[i+1];	
				tabElements[i+1] = temp;
				
				//System.out.print(" d("+i+","+j+") ");
				//System.out.print(" "+delta[i][j]+" ");
				//System.out.print(" "+myInstance.tabD[tabElements[j]-1][tabElements[i]-1] + " -" + myInstance.tabD[tabElements[i]-1][tabElements[j]-1] +" \n");
				
				
				if (score + delta[i][j] < bestScore){
					bestScore = score + delta[i][j];
					bestPermu = new Permutation(tabElements);
					
					/*System.out.print("- ("+bestScore+") ");
					System.out.print(" c("+i+","+j+") ");
					System.out.print(" "+bestPermu+" \n");
					System.out.print(" real score "+bestPermu.distanceToSetMatrix(myInstance.tabD)+" \n");*/
				}
				
				if (numberOfCircMvts > 1){
					nextPi = new Permutation(tabElements);
					nextPi2 = circMvtLocalSearch( myInstance, verbose, nextPi, score+delta[i][j],numberOfCircMvts-1, initialScore);
					if (nextPi2.distanceToSetMatrix(myInstance.tabD) < bestScore){
						bestScore = nextPi2.distanceToSetMatrix(myInstance.tabD);
						bestPermu = new Permutation(nextPi2.getTab());
					}
				}
			}
			
			//to the left (i>j)
			//System.out.print(" to the left \n");
			for (int i=0; i<=n-1; i++){
				tabElements[i]=pi.getTab()[i];	
			}
			
			for (int i=j+1; i<=n-1; i++){
				//delta[i][j] =delta[i-1][j]+(myInstance.tabD[tabElements[i]-1][tabElements[j]-1]-myInstance.tabD[tabElements[j]-1][tabElements[i]-1]);
				delta[i][j] =delta[i-1][j]+(myInstance.tabD[tabElements[i]-1][tabElements[i-1]-1]-myInstance.tabD[tabElements[i-1]-1][tabElements[i]-1]);
				
				temp = tabElements[i];
				tabElements[i] = tabElements[i-1];	
				tabElements[i-1] = temp;
				
				//System.out.print(" d("+i+","+j+") ");
				//System.out.print(" "+delta[i][j]+" ");
				//System.out.print(" "+myInstance.tabD[tabElements[i]-1][tabElements[j]-1] + " -" + myInstance.tabD[tabElements[j]-1][tabElements[i]-1] +" \n");
				
				if (score + delta[i][j] < bestScore){
					bestScore = score + delta[i][j];
					bestPermu = new Permutation(tabElements);
					
					/*System.out.print("- ("+bestScore+") ");
					System.out.print(" c("+i+","+j+") ");
					System.out.print(" "+bestPermu+" \n");
					System.out.print(" real score "+bestPermu.distanceToSetMatrix(myInstance.tabD)+" \n");*/
					
				}
				
				if (numberOfCircMvts > 1){
					nextPi = new Permutation(tabElements);
					nextPi2 = circMvtLocalSearch( myInstance, verbose, nextPi, score+delta[i][j],numberOfCircMvts-1, initialScore);
					if (nextPi2.distanceToSetMatrix(myInstance.tabD) < bestScore){
						bestScore = nextPi2.distanceToSetMatrix(myInstance.tabD);
						bestPermu = new Permutation(nextPi2.getTab());
					}
				}
			}
		}
		
		
		//afficher tableau?
		
		
		//System.out.print(" ("+bestScore+") ");
		//System.out.print(" "+bestPermu+" \n");
		return bestPermu;
	}

	private static void heuristic2opt(Instance myInstance, boolean verbose) {
		int bestScore = 99999999;
		int n= myInstance.n;
		int[] tabElements = new int[n];
		boolean ok = false;
		int pointer = 0;
		int temp= 0;
		for (int i=1; i<=n; i++){
			tabElements[i-1]=i;	
		}
		
		while (!ok){
			//System.out.println(pointer);
			//System.out.println(myInstance.tabD[tabElements[pointer]-1][tabElements[pointer +1]-1] + "vs" + myInstance.tabD[tabElements[pointer +1]-1][tabElements[pointer]-1]);
			if (myInstance.tabD[tabElements[pointer]-1][tabElements[pointer +1]-1] <= myInstance.tabD[tabElements[pointer +1]-1][tabElements[pointer]-1] ){
				pointer++;
				if (pointer == n-1){ //last element
					ok = true;
				}
			}else{
				temp = tabElements[pointer];
				tabElements[pointer] = tabElements[pointer+1];
				tabElements[pointer+1] = temp;
				pointer--;
				if (pointer == -1){ //first pair is set
					pointer = 1;
				}
			}
		}
		

		Permutation result = new Permutation(tabElements);
		bestScore = result.distanceToSetMatrix(myInstance.tabD);
		myInstance.Opt2_upper_bound = bestScore;
		myInstance.addSolverPermutation(result);
		myInstance.setUpperBound(bestScore);
		if (verbose) System.out.println("2-Opt ("+bestScore+") "+result);
	}
	
	private static void heuristic3opt(Instance myInstance, boolean verbose) {

		int bestScore = 99999999;
		int n= myInstance.n;
		int[] tabElements = new int[n];
		boolean ok = false;
		int pointer = 0;
		for (int i=1; i<=n; i++){
			tabElements[i-1]=i;	
		}
		int i1=0,i2=0,i3=0;
		
		triplets(myInstance,false);
		
		while (!ok){
			i1=tabElements[pointer];
			i2=tabElements[pointer+1];
			i3=tabElements[pointer+2];
			
			//if (myInstance.tabD[tabElements[pointer]-1][tabElements[pointer +1]-1] <= myInstance.tabD[tabElements[pointer +1]-1][tabElements[pointer]-1] ){
			if(myInstance.tabTriplets[i1-1][i2-1][i3-1]){
				pointer++;
				if (pointer == n-2){ //last element
					ok = true;
				}
			}else{
				
				if (myInstance.tabTriplets[i1-1][i3-1][i2-1]){
					tabElements[pointer] = i1;
					tabElements[pointer+1] = i3;
					tabElements[pointer+2] = i2;
				}else if (myInstance.tabTriplets[i2-1][i1-1][i3-1]){
					tabElements[pointer] = i2;
					tabElements[pointer+1] = i1;
					tabElements[pointer+2] = i3;
				}else if (myInstance.tabTriplets[i2-1][i3-1][i1-1]){
					tabElements[pointer] = i2;
					tabElements[pointer+1] = i3;
					tabElements[pointer+2] = i1;
				}else if (myInstance.tabTriplets[i3-1][i1-1][i2-1]){
					tabElements[pointer] = i3;
					tabElements[pointer+1] = i1;
					tabElements[pointer+2] = i2;
				}else if (myInstance.tabTriplets[i3-1][i2-1][i1-1]){
					tabElements[pointer] = i3;
					tabElements[pointer+1] = i2;
					tabElements[pointer+2] = i1;
				}else{
					System.out.println("error 3864263269654");
				}
				pointer--;
				if (pointer == -1){ //first triplet is set
					pointer = 1;
				}
			}
		}
		

		Permutation result = new Permutation(tabElements);
		bestScore = result.distanceToSetMatrix(myInstance.tabD);
		myInstance.Opt3_upper_bound = bestScore;
		myInstance.addSolverPermutation(result);
		myInstance.setUpperBound(bestScore);
		if (verbose) System.out.println("3-Opt ("+bestScore+") "+result);
	}
	
	private static void heuristicCopeland3opt(Instance myInstance, boolean verbose) {

		int bestScore = 99999999;
		int n= myInstance.n;
		int[] tabElements = new int[n];
		boolean ok = false;
		int pointer = 0;
		
		//copeland
		int[] tabValues = new int[n];
		for (int i=1; i<=n; i++){
			tabElements[i-1]=i;
			tabValues[i-1]=0;
			for (int j=1; j<=n; j++){
				if (myInstance.tabD[i-1][j-1] > myInstance.tabD[j-1][i-1]){//j<i
					tabValues[i-1]++;//i loss against j -> +1 defeat
				}else{
					//nothing
				}	
			}
		}
		
		//sort the elements
		MergeSort(tabElements,tabValues,0,n-1);
		
		
		
		//3opt
		int i1=0,i2=0,i3=0;
		
		triplets(myInstance,false);
		
		while (!ok){
			i1=tabElements[pointer];
			i2=tabElements[pointer+1];
			i3=tabElements[pointer+2];
			
			//if (myInstance.tabD[tabElements[pointer]-1][tabElements[pointer +1]-1] <= myInstance.tabD[tabElements[pointer +1]-1][tabElements[pointer]-1] ){
			if(myInstance.tabTriplets[i1-1][i2-1][i3-1]){
				pointer++;
				if (pointer == n-2){ //last element
					ok = true;
				}
			}else{
				
				if (myInstance.tabTriplets[i1-1][i3-1][i2-1]){
					tabElements[pointer] = i1;
					tabElements[pointer+1] = i3;
					tabElements[pointer+2] = i2;
				}else if (myInstance.tabTriplets[i2-1][i1-1][i3-1]){
					tabElements[pointer] = i2;
					tabElements[pointer+1] = i1;
					tabElements[pointer+2] = i3;
				}else if (myInstance.tabTriplets[i2-1][i3-1][i1-1]){
					tabElements[pointer] = i2;
					tabElements[pointer+1] = i3;
					tabElements[pointer+2] = i1;
				}else if (myInstance.tabTriplets[i3-1][i1-1][i2-1]){
					tabElements[pointer] = i3;
					tabElements[pointer+1] = i1;
					tabElements[pointer+2] = i2;
				}else if (myInstance.tabTriplets[i3-1][i2-1][i1-1]){
					tabElements[pointer] = i3;
					tabElements[pointer+1] = i2;
					tabElements[pointer+2] = i1;
				}else{
					System.out.println("error 3864263269654");
				}
				pointer--;
				if (pointer == -1){ //first triplet is set
					pointer = 1;
				}
			}
		}
		

		Permutation result = new Permutation(tabElements);
		bestScore = result.distanceToSetMatrix(myInstance.tabD);
		myInstance.Copeland_opt3_upper_bound = bestScore;
		myInstance.Copeland_opt3_permutation = result;
		myInstance.addSolverPermutation(result);
		myInstance.setUpperBound(bestScore);
		if (verbose) System.out.println("Copeland-3-Opt ("+bestScore+") "+result);
	}
	
	private static void heuristicBordaCount3opt(Instance myInstance, boolean verbose) {

		int bestScore = 99999999;
		int n= myInstance.n;
		int[] tabElements = new int[n];
		boolean ok = false;
		int pointer = 0;
		
		//borda
		int[] tabValues = new int[n];
		for (int i=1; i<=n; i++){
			tabElements[i-1]=i;
			tabValues[i-1]=0;
			for (int j=1; j<=n; j++){
				tabValues[i-1]+=myInstance.tabD[i-1][j-1];
			}
		}
		
		//sort the elements
		MergeSort(tabElements,tabValues,0,n-1);
		

		
		
		
		//3opt
		int i1=0,i2=0,i3=0;
		
		triplets(myInstance,false);
		
		while (!ok){
			i1=tabElements[pointer];
			i2=tabElements[pointer+1];
			i3=tabElements[pointer+2];
			
			//if (myInstance.tabD[tabElements[pointer]-1][tabElements[pointer +1]-1] <= myInstance.tabD[tabElements[pointer +1]-1][tabElements[pointer]-1] ){
			if(myInstance.tabTriplets[i1-1][i2-1][i3-1]){
				pointer++;
				if (pointer == n-2){ //last element
					ok = true;
				}
			}else{
				
				if (myInstance.tabTriplets[i1-1][i3-1][i2-1]){
					tabElements[pointer] = i1;
					tabElements[pointer+1] = i3;
					tabElements[pointer+2] = i2;
				}else if (myInstance.tabTriplets[i2-1][i1-1][i3-1]){
					tabElements[pointer] = i2;
					tabElements[pointer+1] = i1;
					tabElements[pointer+2] = i3;
				}else if (myInstance.tabTriplets[i2-1][i3-1][i1-1]){
					tabElements[pointer] = i2;
					tabElements[pointer+1] = i3;
					tabElements[pointer+2] = i1;
				}else if (myInstance.tabTriplets[i3-1][i1-1][i2-1]){
					tabElements[pointer] = i3;
					tabElements[pointer+1] = i1;
					tabElements[pointer+2] = i2;
				}else if (myInstance.tabTriplets[i3-1][i2-1][i1-1]){
					tabElements[pointer] = i3;
					tabElements[pointer+1] = i2;
					tabElements[pointer+2] = i1;
				}else{
					System.out.println("error 3864263269654");
				}
				pointer--;
				if (pointer == -1){ //first triplet is set
					pointer = 1;
				}
			}
		}
		

		Permutation result = new Permutation(tabElements);
		bestScore = result.distanceToSetMatrix(myInstance.tabD);
		myInstance.Borda_opt3_upper_bound = bestScore;
		myInstance.addSolverPermutation(result);
		myInstance.setUpperBound(bestScore);
		if (verbose) System.out.println("BordaCount-3-Opt ("+bestScore+") "+result);
	}

	private static void heuristicCopeland(Instance myInstance, boolean verbose) {
		int bestScore = 99999999;
		int n= myInstance.n;
		int[] tabValues = new int[n];
		int[] tabElements = new int[n];
		for (int i=1; i<=n; i++){
			tabElements[i-1]=i;
			tabValues[i-1]=0;
			for (int j=1; j<=n; j++){
				if (myInstance.tabD[i-1][j-1] > myInstance.tabD[j-1][i-1]){//j<i
					tabValues[i-1]++;//i loss against j -> +1 defeat
				}else{
					//nothing
				}	
			}
		}
		
		//sort the elements
		MergeSort(tabElements,tabValues,0,n-1);
		
		
		Permutation result = new Permutation(tabElements);
		bestScore = result.distanceToSetMatrix(myInstance.tabD);
		myInstance.Copeland_upper_bound = bestScore;
		myInstance.addSolverPermutation(result);
		myInstance.setUpperBound(bestScore);
		if (verbose) System.out.println("Copeland ("+bestScore+") "+result);
	}
	

	private static void heuristicBordaCount(Instance myInstance, boolean verbose) {
		int bestScore = 99999999;
		int n= myInstance.n;
		int[] tabValues = new int[n];
		int[] tabElements = new int[n];
		for (int i=1; i<=n; i++){
			tabElements[i-1]=i;
			tabValues[i-1]=0;
			for (int j=1; j<=n; j++){
				tabValues[i-1]+=myInstance.tabD[i-1][j-1];
			}
		}
		
		//sort the elements
		MergeSort(tabElements,tabValues,0,n-1);
		
		
		Permutation result = new Permutation(tabElements);
		bestScore = result.distanceToSetMatrix(myInstance.tabD);
		myInstance.Borda_upper_bound = bestScore;
		myInstance.addSolverPermutation(result);
		myInstance.setUpperBound(bestScore);
		if (verbose) System.out.println("BordaCount ("+bestScore+") "+result);
	}
	
	private static void heuristicBestOfA(Instance myInstance, boolean verbose) {
		int bestScore = 99999999;
		int distToA = 0;
		Permutation result = null;
		for (Permutation pi : myInstance.A){
			distToA = pi.distanceToSetMatrix(myInstance.tabD);
			if (distToA < bestScore){
				bestScore = distToA;
				result = pi;
			}
		}
		myInstance.BestOfA_upper_bound = bestScore;
		myInstance.addSolverPermutation(result);
		myInstance.setUpperBound(bestScore);
		if (verbose) System.out.println("BestOfA ("+bestScore+") "+result);
			
	}

	//i<j
	private static void MergeSort(int[] tabElements, int[] tabValues, int i, int j) {
		int temp = 0;
		if (j-i == 0){
			//nothing
		}else if (j-i == 1){
			if (tabValues[tabElements[i]-1] > tabValues[tabElements[j]-1]){
				temp = tabElements[i];
				tabElements[i] = tabElements[j];
				tabElements[j] = temp;
			}else{
				//nothing
			}
		}else{
			//sorting in place (not good)
			//13572468
			//*   *
			// *  *
			//12573468
			// *  *
			//  * *
			//12375468
			//  * *
			//   **   
			//12357468
			//   **
			//   * *
			//12347568
			//   * *
			//...
			int middle = (int)Math.ceil((i+j)/2.0);
			MergeSort(tabElements,tabValues,i,middle);
			MergeSort(tabElements,tabValues,middle+1,j);
			int p1 =i;
			int p2 = middle+1;
			int p3=0;
			int[] newTabElements = new int[j-i+1];
			for (int k=0; k<=j-i;k++){
				newTabElements[k]=0;
			}
			while(p3 <= j-i){
				if (p1 > middle){
					newTabElements[p3] = tabElements[p2];
					p2++;
				}else if (p2 > j){
					newTabElements[p3] = tabElements[p1];
					p1++;
				}else if (tabValues[tabElements[p1]-1] > tabValues[tabElements[p2]-1]){
					newTabElements[p3] = tabElements[p2];
					p2++;
				}else{
					newTabElements[p3] = tabElements[p1];
					p1++;
				}
				p3++;
			}
			for (int k=0; k<=j-i;k++){
				tabElements[k+i]=newTabElements[k];
			}
		}
		
	}
	
	private static void heuristicMedianGame(Instance myInstance, boolean verbose){
		List<Permutation> Ulist = new ArrayList<Permutation>();
		Permutation bestPermutation = null;
		int min = 0;
		int min2 = 0;
		for (Permutation pi : myInstance.A){
			Ulist.add(pi);
		}
		
		//creerTabD(U);
		

		
		Permutation medianHeuristic= createARandom(myInstance.n);
		min = medianHeuristic.distanceToSetMatrix(myInstance.tabD);
		min2 = min;
		Permutation rand= null;
		
		/*for (int i = 1; i <= 1000; i++){
			rand = numbersSorted.get((int)(Math.floor(Math.random()*numbersSorted.size())));
			if (rand > medianHeuristic){
				medianHeuristic += 1;
			}else if (rand < medianHeuristic){
				medianHeuristic -= 1;
			}else {
				//nothing
			}
			System.out.println(medianHeuristic);
			
		}*/
		
		//Permutation medianHeuristic2=medianHeuristic;

		int score=0;
		
		for (int i = 1; i <= 1000; i++){
			Permutation orderOfPermutations = createARandom(myInstance.m);
			Permutation orderOfElements = null;
			//Permutation orderOfElements = createARandom(pickARandom(U).getSize());
			for (int j = 0; j < myInstance.m; j++){
				//rand = numbersSorted.get(j);
				rand = Ulist.get(orderOfPermutations.getTab()[j]-1);
				
				if (!medianHeuristic.equals(rand)){
					medianHeuristic = randomMvtTowards(medianHeuristic,rand,orderOfElements);
				}
				score = medianHeuristic.distanceToSetMatrix(myInstance.tabD);
				if (min2 > score){
					min2 = score;
					bestPermutation = new Permutation(medianHeuristic.getTab());
				}

			}	
			score = medianHeuristic.distanceToSetMatrix(myInstance.tabD);
			//System.out.println(score + "  " + shortestDist(medianHeuristic,myInstance.Medians));
			//System.out.println(score);
			if (min > score){
				min = score;
			}
			
		}
		
		/*System.out.println();
		System.out.println("min = " + min);
		System.out.println("min2 = " + min2);*/
		
		score = min2;
		
		myInstance.MedianGame_upper_bound = score;
		myInstance.addSolverPermutation(bestPermutation);
		myInstance.setUpperBound(score);
		if (verbose) System.out.println("MedianGame ("+score+") "+bestPermutation);
		
	}



	private static int[] reverseTable(int[] tab){
		/*  len=6
		 *  0 1 2 3 4 5
		 *  6 2 4 3 5 1 tab
		 *  1 5 3 4 2 6 tabReversed
		 */ 
		int tableSize = tab.length;
		int tabReversed[] = new int[tableSize];
		for(int i=0;i<tableSize;i++){
			tabReversed[i] = tab[tableSize-i-1];
		}
		return tabReversed;
		
	}
	
	private static int[] apply_t_random_adjacent_transpositions(int[] tab,int t){
		/*  len=6
		 *  0 1 2 3 4 5
		 *  6 2 4 3 5 1 tab
		 *  1 5 3 4 2 6 tabReversed
		 */ 
		int tableSize = tab.length;
		int tab2[] = new int[tableSize];
		int r =0;
		int temp =0;
		for(int i=0;i<tableSize;i++){
			tab2[i] = tab[i];
		}
		for(int i=0;i<t;i++){
			r = (int)(Math.random()*(tableSize-1));
			temp = tab2[r];
			tab2[r] = tab2[r+1];
			tab2[r+1]=temp;
		}
		return tab2;
		
	}
	
	
	

	
	
	

	//test if 's' is a web site name with known domain
	public static boolean isWebSite (String s){
		int point = s.lastIndexOf('.');
		if (point == -1)
			return false;
		if (!isCharOk(s, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.-"))
			return false;
		//String name = s.substring(0, point);
		String domain = s.substring(point + 1, s.length());
		if (domain.equalsIgnoreCase("com")){
			//nothing
		}else if (domain.equalsIgnoreCase("org")){
			//nothing
		}else if (domain.equalsIgnoreCase("net")){
			//nothing
		}else if (domain.equalsIgnoreCase("int")){
			//nothing
		}else if (domain.equalsIgnoreCase("edu")){
			//nothing
		}else if (domain.equalsIgnoreCase("gov")){
			//nothing
		}else if (domain.equalsIgnoreCase("mil")){
			//nothing
		}else if (domain.equalsIgnoreCase("arpa")){
			//nothing
		}else if (domain.equalsIgnoreCase("ca")){
			//nothing
		}else if (domain.equalsIgnoreCase("au")){
			//nothing
		}else if (domain.equalsIgnoreCase("ch")){
			//nothing
		}else if (domain.equalsIgnoreCase("cz")){
			//nothing
		}else if (domain.equalsIgnoreCase("de")){
			//nothing
		}else if (domain.equalsIgnoreCase("eu")){
			//nothing
		}else if (domain.equalsIgnoreCase("fr")){
			//nothing
		}else if (domain.equalsIgnoreCase("ca")){
			//nothing
		}else if (domain.equalsIgnoreCase("in")){
			//nothing
		}else if (domain.equalsIgnoreCase("it")){
			//nothing
		}else if (domain.equalsIgnoreCase("jp")){
			//nothing
		}else if (domain.equalsIgnoreCase("pl")){
			//nothing
		}else if (domain.equalsIgnoreCase("ro")){
			//nothing
		}else if (domain.equalsIgnoreCase("ru")){
			//nothing
		}else if (domain.equalsIgnoreCase("se")){
			//nothing
		}else if (domain.equalsIgnoreCase("uk")){
			//nothing
		}else if (domain.equalsIgnoreCase("us")){
			//nothing
		}else if (domain.equalsIgnoreCase("android")){
			//nothing
		}else if (domain.equalsIgnoreCase("gmail")){
			//nothing
		}else if (domain.equalsIgnoreCase("youtube")){
			//nothing
		}else{
			return false;
		}
		
		return true;
		
		
	}
	
	//test if all charcaters of 's' are in 't'
	public static boolean isCharOk (String s, String t){
		boolean match = false;
		for (int i = 0; i < s.length(); i++){
			match = false;
			for (int j = 0; j < t.length(); j++){
				if (s.charAt(i) == t.charAt(j)){
					match = true;
				}
			}
			if (!match){
				return false;
			}
		}
		return true;
	}
	
	public static String uniformizeWebSite(String s){
		String uniformizedWebSite =  s.toLowerCase();
		if (uniformizedWebSite.substring(0, 4).equalsIgnoreCase("www.")){
			uniformizedWebSite = uniformizedWebSite.substring(4);
		}
		if (uniformizedWebSite.substring(0, 5).equalsIgnoreCase("www3.")){
			uniformizedWebSite = uniformizedWebSite.substring(5);
		}
		return uniformizedWebSite;
	}
	

	
	public static void convertSearchEngineResultsToElection(String fichier, int nbRankings){
		List<String> candidats = new ArrayList<String>();
		List<String> rankings = new ArrayList<String>();
		List<String> titles = new ArrayList<String>();
		int n =0;
		int m =0;
		char u;
		String candidat ="";
		String ranking ="";
		boolean ok = false;
		//lecture
        FileReader reader ;
        for (int fileNumber = 1; fileNumber <= nbRankings; fileNumber ++){
	        candidats = new ArrayList<String>();
	        try{
	        	reader = new FileReader(fichier + "_" + fileNumber + ".preelection");
	        	Scanner in = new Scanner(reader);
	        	//System.out.println("****************************");
	        	while (in.hasNext()){
	        		String line = in.nextLine();
	        		if (line.length()!=0 ){
	        			//new ordering
	        			m++;
	        			for (int i =0; i< line.length(); i++){
	        				u=line.charAt(i);
	        				if (u == '/' || u == ' ' || i == line.length()-1){
	        					if (i == line.length()-1)
	        						candidat =candidat + u;
	        					//System.out.println("is web site?: " + candidat);
	        					
	        					//small reajustements
	        					//01234567890 length 11
	        					//a.comCached (yahoo)
	        					if (candidat.length() > 6){
	        						if (candidat.substring(candidat.length() - 6).equalsIgnoreCase("Cached")){
	        							candidat = candidat.substring(0, candidat.length() - 6);
	        						}
	        					}
	        					//adwww.a.com (google)
	        					if (candidat.length() > 2){
	        						if (candidat.substring(0,2).equalsIgnoreCase("ad")){
	        							candidat = candidat.substring(2);
	        						}
	        					}
	        					
	        					
	        					if (isWebSite(candidat)){
	        						//System.out.println("yes" );
	        						candidat = uniformizeWebSite(candidat);
		        					if (candidats.contains(candidat)){
		        						//nothing
		        					}else{
		        						candidats.add(candidat);
		        					}
		        					
	        					}else{
	        						//System.out.println("no" );
	        					}
	        					candidat ="";
	        				}else{
	        					candidat =candidat + u;
	        				}
	        			}
	        			
	        		}
	        	}			
	        	ok = true;
		        //reader.close();
	            in.close();
	        
	        }catch (FileNotFoundException e){
	    	    System.out.println("Fichier "+ fichier + "_" + fileNumber + ".preelection" +" n'a pas Ã©tÃ© trouvÃ©");
	    	}
	        
	        if (candidats.size() > 0){
		        ranking = candidats.get(0);
		        for (int candidateNumber = 1; candidateNumber < candidats.size(); candidateNumber++){
		        	ranking += ">" + candidats.get(candidateNumber);
		        }
		        rankings.add(ranking);
	        }
	        
        }
        
        if (rankings.size() >0){
        	//traitement du fichier
    		try{
    			// Create file 
    			FileWriter fstream = new FileWriter(fichier + ".election",false);
    			BufferedWriter out = new BufferedWriter(fstream);
    			for (int rankingNumber = 0; rankingNumber < rankings.size(); rankingNumber++){
    				out.write(rankings.get(rankingNumber));
    				out.write("\n");
    			}
    			//Close the output stream
    			out.close();
    		}catch (Exception e){//Catch exception if any
    			System.err.println("Error: " + e.getMessage());
    		}
        }else{
        	System.out.println("No rankings found");
        }

	        		
	}
	
	
	private static void integerMedianGame(){
		SortedSet<Integer> numbers = new TreeSet<Integer>();
		List<Integer> numbersSorted = new ArrayList<Integer>();
		int number=0;
		double median = 0;
		for (int i = 1; i <= 6; i++){
			number = (int)(Math.random()*100+1);
			numbers.add(number);
		}
		System.out.println(numbers);
		for (Integer i : numbers){
			numbersSorted.add(i);
		}
		
		int counter = 0;
		if (numbers.size() % 2 == 1){
			/*for (Integer i : numbers){
				counter++;
				if (counter == (Math.floor(numbers.size()/2.0)+1)){
					median = i;
				}
			}*/
			median = numbersSorted.get((int)(Math.floor(numbers.size()/2.0)+1)-1);
		}else{
			/*for (Integer i : numbers){
				counter++;
				if (counter == ((numbers.size()/2.0))){
					median += i;
				}else if (counter == ((numbers.size()/2.0)+1)){
					median += i;
					median /= 2.0;
				}
			}*/
			median = (numbersSorted.get((int)((numbers.size()/2.0))-1) + numbersSorted.get((int)((numbers.size()/2.0)+1)-1))/2.0;
		}
		System.out.println("numbers.size() " +numbers.size());
		System.out.println("median " + median);
		System.out.println("");
		
		
		int medianHeuritic=(int)(Math.random()*100+1);
		
		int rand=0;
		/*for (int i = 1; i <= 1000; i++){
			rand = numbersSorted.get((int)(Math.floor(Math.random()*numbersSorted.size())));
			if (rand > medianHeuritic){
				medianHeuritic += 1;
			}else if (rand < medianHeuritic){
				medianHeuritic -= 1;
			}else {
				//nothing
			}
			System.out.println(medianHeuritic);
			
		}*/
		
		int medianHeuritic2=medianHeuritic;
		
		/*for (int i = 1; i <= 100; i++){
			for (int j = 0; j < numbersSorted.size(); j++){
				rand = numbersSorted.get(j);
				if (rand > medianHeuritic){
					medianHeuritic2 += 1;
				}else if (rand < medianHeuritic){
					medianHeuritic2 -= 1;
				}else {
					//nothing
				}
			}
			medianHeuritic = medianHeuritic2;
			
			System.out.println(medianHeuritic);
			
		}*/
		
		for (int i = 1; i <= 100; i++){
			Permutation pi = createARandom(numbersSorted.size());
			for (int j = 0; j < numbersSorted.size(); j++){
				//rand = numbersSorted.get(j);
				rand = numbersSorted.get(pi.getTab()[j]-1);
				if (rand > medianHeuritic){
					medianHeuritic += 1;
				}else if (rand < medianHeuritic){
					medianHeuritic -= 1;
				}else {
					//nothing
				}
			}	
			System.out.println(medianHeuritic);
			
		}
		
		System.out.println("real median: " + median);
		
	}
	

	
	private static int shortestDist(Permutation medianHeuristic,
			Set<Permutation> m2) {
		// TODO Auto-generated method stub
		int min = 999999;
		int dist = 0;
		for (Permutation pi : m2){
			dist = pi.distanceTo(medianHeuristic);
			if (dist < min){
				min = dist;
			}
		}
		return min;
	}

	private static void find_maxDeltaEnergy(int arg) {
		// TODO Auto-generated method stub
		
		int nbCas = 100;
		int m = 80;
		int n = arg;
		int nbElectrons =10;
		int nbMvts = 10000;
		double temperature = 500;
		double refroidissement = 0.99;
		

		SortedMap<Integer,Integer> dernieresIterations;
		dernieresIterations=new TreeMap<Integer, Integer>();
		

		/*
		 * find_maxDeltaEnergy(3);
	    find_maxDeltaEnergy(4);
	    find_maxDeltaEnergy(5);
	    find_maxDeltaEnergy(6);
	    find_maxDeltaEnergy(7);
	    find_maxDeltaEnergy(8);
	    find_maxDeltaEnergy(9);
	    find_maxDeltaEnergy(10);
	    find_maxDeltaEnergy(11);
	    find_maxDeltaEnergy(12);
	    find_maxDeltaEnergy(13);
	    find_maxDeltaEnergy(14);
	    find_maxDeltaEnergy(15);
	    find_maxDeltaEnergy(20);
	    find_maxDeltaEnergy(25);
	    find_maxDeltaEnergy(30);
	    find_maxDeltaEnergy(35);
	    find_maxDeltaEnergy(40);
	    find_maxDeltaEnergy(45);
	    find_maxDeltaEnergy(50);
	    
	    find_maxDeltaEnergy(10);
	    find_maxDeltaEnergy(15);
	    find_maxDeltaEnergy(20);
	    find_maxDeltaEnergy(25);
	    find_maxDeltaEnergy(30);
	    find_maxDeltaEnergy(35);
	    find_maxDeltaEnergy(40);
	    find_maxDeltaEnergy(45);
	    find_maxDeltaEnergy(50);
		 */
		
		
		
		maxDeltaEnergy = 0;
		
		for (int cas=0; cas< nbCas; cas++){
			//creerA(false,m,n);
			Instance myInstance = new Instance(m,n);

			//SA
			heuristiqueCreerSA_forParameters(myInstance,false, false,nbElectrons, nbMvts,temperature,refroidissement, dernieresIterations);

		}
		
		//System.out.println("maxDeltaEnergy ");
		System.out.println(maxDeltaEnergy);
		
		
	}
	private static void SA_average_solution_stat(int arg1) {
			DecimalFormat df = new DecimalFormat("#.##");
			
			int nbCas = 1000;//default
			nbCas = arg1;
			int m = 3;//default
			int n = 10;//default
			int nbElectrons =1;
			int nbMvts = 700;
			double temperature = 500;
			temperature = (m*0.25+4.0)*n;
			double refroidissement = 0.99;
			


			
			double[] tabAverage = new double[nbMvts];
			
			for (int i=0; i< 700; i++){
				tabAverage[i]=0.0;
			}
			
			
			System.out.println("n="+n+", m="+m+", cas="+nbCas+", ele="+nbElectrons+", mvt="+nbMvts+", tmp="+temperature+", lam="+refroidissement+" ");
			
			for (int cas=0; cas< nbCas; cas++){
				//creerA(false,m,n);
				Instance myInstance = new Instance(m,n);
				heuristiqueCreerSA_forParameters2(myInstance,false, false,nbElectrons, nbMvts,temperature,refroidissement, tabAverage);

			}
			
			for (int i=0; i< 700; i++){
				tabAverage[i]/=nbCas;
			}
			
			for (int i=0; i< 700; i++){
				System.out.println(i + "\t " + df.format(tabAverage[i]) );
			}
	}
	
	private void find_SA_parameters(int arg1, int arg2, double refroidissement) {
		// TODO Auto-generated method stub
		DecimalFormat df = new DecimalFormat("#");
		
		int nbCas = 1000/2;
		int m = 3;//default
		m=arg1;
		int n = 20;//default
		n=arg2;
		int nbElectrons =1000;
		int nbMvts = 100000;
		double temperature = 500;
		temperature = (m*0.25+4.0)*n;
		//double refroidissement = 0.95;
		

		int rendu=0;
		SortedMap<Integer,Integer> dernieresIterations;
		int[] tabDernieresIterations;
		dernieresIterations=new TreeMap<Integer, Integer>();
		
		int cumulative = 0;
		
		double nbFound = 0.0;
		double nbFoundAffichage = 0.0;
		double nbNotFound = 0.0;
		double total =0.0;
		
		double firstFound = 0.0;
		double lastFound = 0.0;
		double scalePlus = 0.0;
		double scaleMult = 0.0;
		double nbOfDivisions = 500.0;
		tabDernieresIterations = new int[(int) nbOfDivisions];
		
		int mvt90accumule = -1;
		int mvt95accumule = -1;
		
		
		
		
		System.out.println("start");
		System.out.println("n="+n+", m="+m+", cas="+nbCas+", ele="+nbElectrons+", mvt="+nbMvts+", tmp="+temperature+", lam="+refroidissement+" ");
		
		for (int cas=0; cas< nbCas; cas++){
			//creerA(false,m,n);
			Instance myInstance = new Instance(m,n);
			//printA();
			//System.out.println("cas "  +cas + "/"+ nbCas);
			//System.out.println("solving...");
			//solve
		  	//algorithme de recherche
			minSubScoresSize = 4;
			maxSubScoresSize = 6;
			heuristicsPack(myInstance, false);
			constraintsPack(myInstance,false,true);
			BranchAndBound(myInstance, false);//modified inst
			//creerM(myInstance,SMn);
			//Permutation pi = null;
			//pi = pickARandom(M);
			//anwser = pi.distanceToSetMatrix(tabD);
			
			//System.out.println("SA test");
			//SA
			heuristiqueCreerSA_forParameters(myInstance,false, false,nbElectrons, nbMvts,temperature,refroidissement, dernieresIterations);

		}
		
		//System.out.println("map size " + dernieresIterations.size());
		rendu=0;
		while (dernieresIterations.firstKey() == -1){
			nbNotFound += dernieresIterations.get(dernieresIterations.firstKey());
			dernieresIterations.remove(dernieresIterations.firstKey());
		}
		
		writeAllFinalGoodIterationsInFile(dernieresIterations, "SA_param_data/finalGoodIterations_m_"+m+"_n_"+n+"_ref_"+refroidissement+".txt");
		
		firstFound = dernieresIterations.firstKey();
		lastFound = dernieresIterations.lastKey();
		scalePlus = firstFound *0.95;
		scaleMult = (lastFound - firstFound)/(nbOfDivisions*0.95) +1.0;
		
		while (!(dernieresIterations.isEmpty())){
			//System.out.println(dernieresIterations.firstKey() + " .. " + dernieresIterations.get(dernieresIterations.firstKey()));
			if (dernieresIterations.firstKey()>= ((rendu+1)*scaleMult+scalePlus))
				rendu++;
			else{
				nbFound += dernieresIterations.get(dernieresIterations.firstKey());
				tabDernieresIterations[rendu]+=dernieresIterations.get(dernieresIterations.firstKey());
				dernieresIterations.remove(dernieresIterations.firstKey());
			}
		}
		
		
		
		//affichage
		String resultat_affiche = "";
		
		total = nbFound + nbNotFound;
		nbFound = nbFound*100.0 / total;
		nbNotFound=nbNotFound*100.0  / total;
		//System.out.println("n="+n+", m="+m+", cas="+nbCas+", ele="+nbElectrons+", mvt="+nbMvts+", tmp="+temperature+", lam="+refroidissement+" ");
		resultat_affiche += "n="+n+", m="+m+", cas="+nbCas+", ele="+nbElectrons+", mvt="+nbMvts+", tmp="+temperature+", lam="+refroidissement+" " + "\n";
		System.out.println("nbFound: " +nbFound + "%, " + "nbNotFound: "  + nbNotFound + "% ");
		resultat_affiche += "nbFound: " +nbFound + "%, " + "nbNotFound: "  + nbNotFound + "% " + "\n";
		nbFoundAffichage = nbFound;
		nbFound = nbFound/100.0 * total;
		
		
		//for (int i=0;i<100;i++){
		//	System.out.println((i*scaleMult+scalePlus)+"-"+((i+1)*scaleMult+scalePlus-1)+": "+tabDernieresIterations[i]);
		//}
		cumulative = 0;
		for (int i=0;i<nbOfDivisions;i++){
			cumulative += tabDernieresIterations[i];
			//System.out.println(df.format((i+1)*scaleMult+scalePlus-1)+" \t "+cumulative);
			
			if(mvt90accumule == -1 && cumulative >= 0.90*nbFound){
				mvt90accumule = (int)((i+1)*scaleMult+scalePlus-1);
			}
			if(mvt95accumule == -1 && cumulative >= 0.95*nbFound){
				mvt95accumule = (int)((i+1)*scaleMult+scalePlus-1);
			}
		}
		
		System.out.println("-");
		System.out.println("mvt90accumule: " +mvt90accumule);
		System.out.println("mvt95accumule: " +mvt95accumule);
		System.out.println("succes: " + +nbFoundAffichage+ "%");
		resultat_affiche += "-" + "\n";
		resultat_affiche += "mvt90accumule: " +mvt90accumule + "\n";
		resultat_affiche += "mvt95accumule: " +mvt95accumule + "\n";
		resultat_affiche += "succes: " + +nbFoundAffichage+ "% \n";
		//nbNotFound=nbNotFound*100.0  / total;
		//System.out.println("n="+n+", m="+m+", cas="+nbCas+", ele="+nbElectrons+", mvt="+nbMvts+", tmp="+temperature+", lam="+refroidissement+" ");
		//System.out.println("nbFound: " +nbFound + "%, " + "nbNotFound: "  + nbNotFound + "% ");
		System.out.println("stop");
		
		writeSAparams_InFile(resultat_affiche, "SA_param_data/SA_params_m_"+m+"_n_"+n+"_ref_"+refroidissement+".txt");
		writeFinalGoodIterationsCDFInFile(tabDernieresIterations, nbOfDivisions, "SA_param_data/CDF_m_"+m+"_n_"+n+"_ref_"+refroidissement+".txt");
		writeFinalGoodIterationsPDFInFile(tabDernieresIterations, nbOfDivisions, "SA_param_data/PDF_m_"+m+"_n_"+n+"_ref_"+refroidissement+".txt");
		writeFinalGoodIterationsScaleInFile(tabDernieresIterations, nbOfDivisions, scaleMult, scalePlus, "SA_param_data/Scale_m_"+m+"_n_"+n+"_ref_"+refroidissement+".txt");
		
	}


	private static void writeSAparams_InFile(String resultat_affiche, String fichier) {
		// TODO Auto-generated method stub
		try{
			// Create file 
			FileWriter fstream = new FileWriter(fichier,false);
			BufferedWriter out = new BufferedWriter(fstream);

			out.write(resultat_affiche);

			//Close the output stream
			out.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	private static void writeFinalGoodIterationsScaleInFile(int[] tabDernieresIterations, double nbOfDivisions,
			double scaleMult, double scalePlus, String fichier) {
		// TODO Auto-generated method stub
		try{
			// Create file 
			FileWriter fstream = new FileWriter(fichier,false);
			BufferedWriter out = new BufferedWriter(fstream);

			for (int i=0;i<nbOfDivisions;i++){
				out.write(df.format((i+1)*scaleMult+scalePlus-1));
				out.write("\n");
			}

			//Close the output stream
			out.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	private static void writeFinalGoodIterationsCDFInFile(int[] tabDernieresIterations, double nbOfDivisions,
			String fichier) {


				try{
					// Create file 
					FileWriter fstream = new FileWriter(fichier,false);
					BufferedWriter out = new BufferedWriter(fstream);

					int cumulative = 0;
					for (int i=0;i<nbOfDivisions;i++){
						cumulative += tabDernieresIterations[i];
						//System.out.println(df.format((i+1)*scaleMult+scalePlus-1)+" \t "+cumulative);
						out.write(df.format(cumulative));
						out.write("\n");

					}

					//Close the output stream
					out.close();
				}catch (Exception e){//Catch exception if any
					System.err.println("Error: " + e.getMessage());
				}
		
	}
	
	private static void writeFinalGoodIterationsPDFInFile(int[] tabDernieresIterations, double nbOfDivisions,
			String fichier) {


				try{
					// Create file 
					FileWriter fstream = new FileWriter(fichier,false);
					BufferedWriter out = new BufferedWriter(fstream);

					int cumulative = 0;
					for (int i=0;i<nbOfDivisions;i++){
						out.write(df.format(tabDernieresIterations[i]));
						out.write("\n");

					}

					//Close the output stream
					out.close();
				}catch (Exception e){//Catch exception if any
					System.err.println("Error: " + e.getMessage());
				}
		
	}

	private static void writeAllFinalGoodIterationsInFile(SortedMap<Integer, Integer> dernieresIterations, String fichier) {


		try{
			// Create file 
			FileWriter fstream = new FileWriter(fichier,false);
			BufferedWriter out = new BufferedWriter(fstream);
			

			for (int i : dernieresIterations.keySet()){
				for(int j=0; j < dernieresIterations.get(i); j++){
					out.write(df.format(i));
					out.write("\n");
				}
			}
			//Close the output stream
			out.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	private static void geographySpaceSn() {
		int n=9;
		Instance myInstance = new Instance(3,n);
		//tabD = creationTabD(A);
		myInstance.print();
		//SortedSet<Permutation> toTake = new TreeSet<Permutation>();
		List<Permutation> toTake = new ArrayList<Permutation>();
		Set<Permutation> Neighborhood = new HashSet<Permutation>();
		List<Permutation> minimumsLocaux = new ArrayList<Permutation>();
		
		myInstance.tabC = new boolean [n][n];
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				myInstance.tabC[i-1][j-1]=false;
			}
		}
		
		
		
		//MOT3(null,A,false, false, true, false,999999);//*modified inst
		MOT3_LUBC(myInstance,false, false, true, false);//*modified inst
		
		creerSnUnderConstraints(myInstance);
		//creerSn(n);
		//printSn();
		
		for (Permutation pi : SnUnderConstraints){
			int dist = pi.distanceToSetMatrix(myInstance.tabD);
			pi.setDist(dist);
			toTake.add(pi);
		}
		
		
		//toTake.sort(Permutation.PermutationComparator);
		toTake.sort(null);
		//Collections.sort(toTake);
		
		printList(toTake);
		//printSet(toTake);
		
		//int a[] = {1,2,3,4,5};
		//printSet(generateNeighborhood(new Permutation(a)));
		
		for (Permutation p: toTake){
			//Neighborhood = generateNeighborhood(p);
			Neighborhood = generateNeighborhoodCirc(p);
			for (Permutation neighbor : Neighborhood){
				if (toTake.contains(neighbor)){
					Permutation neighbor1 = toTake.get(toTake.indexOf(neighbor));
					//if (neighbor1.getDist() >= p.getDist()){
					if (neighbor1.getDist() > p.getDist()){
						neighbor1.setDescendents(p);
						//if (!neighbor1.hasDescendent){
						//	neighbor1.setDescendent(p);
						//}
					}
				}
			}

		}
		
		//System.out.println("***");
		//System.out.println(generatePermuUnderConstraints(n));
		
		System.out.println("***");
		minimumsLocaux = new ArrayList<Permutation>();
		for (Permutation p: toTake){
			if (!p.hasDescendent()){
				minimumsLocaux.add(p);
			}	
		}
		System.out.println("minimumsLocaux.size() "+minimumsLocaux.size());
		for (Permutation p: minimumsLocaux){
			System.out.println("*"+ minimumsLocaux.indexOf(p)+ " "+ p + " " + p.getDist() + "  ("+p.getSumAscendence()+" ascendents)");
			
			/*Neighborhood = generateNeighborhoodCirc(p);
			//System.out.println("Neighborhood.size() "+Neighborhood.size());
			for (Permutation neighbor : Neighborhood){
				Permutation neighbor1 = toTake.get(toTake.indexOf(neighbor));
				System.out.println("   "+ neighbor1 + " " + neighbor1.getDist());
			}*/
		}
		
		int[][] tabSaddleHeight = new int [minimumsLocaux.size()][minimumsLocaux.size()];
		for(int i1 = 0; i1< minimumsLocaux.size(); i1++){
			for(int i2 = 0; i2< minimumsLocaux.size(); i2++){
				tabSaddleHeight[i1][i2] = 999999;
			}
		}
		
		System.out.println("saddle height");
		for(int i1 = 0; i1< minimumsLocaux.size(); i1++){
			Permutation m1 = minimumsLocaux.get(i1);
			for(int i2 = i1 +1; i2< minimumsLocaux.size(); i2++){
				Permutation m2 = minimumsLocaux.get(i2);
				//System.out.println(" "+i1+ " " + m1 + " ("+ m1.getDist() + ") -> "+i2 + " " + m2 + " (" + m2.getDist() + ") = ???"  );
				if (generateNeighborhoodCirc(m1).contains(m2)){
					System.out.println(" "+i1+ " " + m1 + " ("+ m1.getDist() + ") -> "+i2 + " " + m2 + " (" + m2.getDist() + ") = direct");
					tabSaddleHeight[i1][i2] = Math.min(m1.getDist(),m2.getDist());
					tabSaddleHeight[i2][i1] = tabSaddleHeight[i1][i2];
				}else{
					//System.out.println("not in neighborhood");
					for(int i = 0; i< toTake.size(); i++){
						Permutation p1 = toTake.get(i);
						if (p1.hasDescendent()){
							if (p1.getFinalDescendents().contains(m1) && p1.getFinalDescendents().contains(m2)){
								System.out.println(" "+i1+ " " + m1 + " ("+ m1.getDist() + ") -> "+i2 + " " + m2 + " (" + m2.getDist() + ") = " + p1.getDist());
								tabSaddleHeight[i1][i2] = p1.getDist();
								tabSaddleHeight[i2][i1] = tabSaddleHeight[i1][i2];
								i = toTake.size() + 5;
							}
						}
					}
				}
			}
		}
		
		for(int i1 = 0; i1< minimumsLocaux.size(); i1++){
			for(int i2 = i1 + 1; i2< minimumsLocaux.size(); i2++){
				System.out.println(" " + i1 + " ("+ minimumsLocaux.get(i1).getDist() + ") -> " + i2 + " (" + minimumsLocaux.get(i2).getDist() + ") = " + tabSaddleHeight[i1][i2]);
			}
		}
		
		for (int k =0; k < minimumsLocaux.size(); k++){
			for(int i1 = 0; i1< minimumsLocaux.size(); i1++){
				for(int i2 = 0; i2< minimumsLocaux.size(); i2++){
					if (i1 != i2){
						if (tabSaddleHeight[i1][i2] > Math.max(tabSaddleHeight[i1][k],tabSaddleHeight[k][i2])){
							tabSaddleHeight[i1][i2] = Math.max(tabSaddleHeight[i1][k],tabSaddleHeight[k][i2]);
							tabSaddleHeight[i2][i1] = tabSaddleHeight[i1][i2];
							System.out.println("update");
						}
					}
				}
			}
		}
		
		for(int i1 = 0; i1< minimumsLocaux.size(); i1++){
			for(int i2 = i1 + 1; i2< minimumsLocaux.size(); i2++){
				System.out.println(" " + i1 + " ("+ minimumsLocaux.get(i1).getDist() + ") -> " + i2 + " (" + minimumsLocaux.get(i2).getDist() + ") = " + tabSaddleHeight[i1][i2]);
			}
		}
		
		
	}
	
	//creates a new permutation from permutation "from" that is one adjacent swap closer to permutation "to"
	private static Permutation randomMvtTowards(Permutation from,Permutation to, Permutation orderOfElements){
		Permutation neighbor = null;
		int tab[] = null;
		int n = from.getSize();
		int i  = 0;
		
		if (orderOfElements == null){
			orderOfElements = createARandom(n);
		}
		//System.out.println(orderOfElements);
		for (int j = 0; j < n; j++){
			i = orderOfElements.getTab()[j]-1;
			//System.out.println(i);
			if (i != (n -1)){
				if(to.getPosition(from.getTab()[i]) > to.getPosition(from.getTab()[i+1])){
					tab = from.getTab();
					int temp = tab[i];
					tab[i] = tab[i+1];
					tab[i+1] = temp;
					neighbor = new Permutation(tab);
					temp = tab[i+1];
					tab[i+1] = tab[i];
					tab[i] = temp;
					return neighbor;
				}
			}
		}
		
		
		return from;
	}
	
	private static Set<Permutation> generateNeighborhood(Permutation p) {
		Set<Permutation> Neighborhood = new HashSet<Permutation>();
		Permutation neighbor = null;
		int tab[] = null;
		int n = p.getSize();
		//System.out.println(p);
		//System.out.println(n);
		for (int i = 0; i < n-1; i++){
			tab = p.getTab();
			int temp = tab[i];
			tab[i] = tab[i+1];
			tab[i+1] = temp;
			neighbor = new Permutation(tab);
			temp = tab[i+1];
			tab[i+1] = tab[i];
			tab[i] = temp;
			Neighborhood.add(neighbor);
		}
		
		return Neighborhood;
	}
	
	private static Set<Permutation> generateNeighborhoodCirc(Permutation p) {
		Set<Permutation> Neighborhood = new HashSet<Permutation>();
		Permutation neighbor = null;
		int tab[] = null;
		int n = p.getSize();
		//System.out.println(p);
		//System.out.println(n);
		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++){
				if (i <j){
					tab = p.getTab();
					int temp = tab[i];
					for (int k = i; k < j; k++)
						tab[k] = tab[k+1];
					tab[j] = temp;
					neighbor = new Permutation(tab);
					temp = tab[j];
					for (int k = j; k > i; k--)
						tab[k] = tab[k-1];
					tab[i] = temp;
					Neighborhood.add(neighbor);
				}else if (i > j){
					tab = p.getTab();
					int temp = tab[i];
					for (int k = i; k > j; k--)
						tab[k] = tab[k-1];
					tab[j] = temp;
					neighbor = new Permutation(tab);
					temp = tab[j];
					for (int k = j; k < i; k++)
						tab[k] = tab[k+1];
					tab[i] = temp;
					Neighborhood.add(neighbor);
				}
			}
		}
		
		return Neighborhood;
	}
	
	private static void showBrokenMajorOrders(Instance myInstance) {
		int n=myInstance.n;
		int coord1 = -1;
		int coord2 = -1;
		int coord3 = -1;
		int broken = 0;
		String coordinates = "";
		boolean alwaysBrokenMajorOrder = false;
		boolean brokenMajorOrder = false;
		int alwaysBrokenMajorOrderCount = 0;
		
		try{
			// Create file 
			FileWriter fstream = new FileWriter("/u/miloszro/recherche/14-aut2016/brokenMajorOrder/data4.txt",false);//on apprend mode?
			BufferedWriter out = new BufferedWriter(fstream);
			 



			
			
		
		System.out.println("Broken major orders:");
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				if (myInstance.tabD[j-1][i-1]-myInstance.tabD[i-1][j-1] == 1){ //si >0 alors ij major order
				//if (tabD[j-1][i-1]-tabD[i-1][j-1] > 0){ //si >0 alors ij major order
					alwaysBrokenMajorOrder = true;
					brokenMajorOrder = false;
					for (Permutation p: myInstance.Medians){
						//System.out.println(p);
						if (p.getPosition(i) > p.getPosition(j)){
							//broken major order
							brokenMajorOrder = true;
						}else{
							//respected major order
							alwaysBrokenMajorOrder = false;
						}
					}
					coord1 = -1;
					coord2 = -1;
					coord3 = -1;
					if (alwaysBrokenMajorOrder){
						
						if (myInstance.tabC[j-1][i-1]){
							broken = 4;
						}else{
							System.out.println(" "+i+"-"+j);
							alwaysBrokenMajorOrderCount++;
							broken = 0;
							for (Permutation p: myInstance.A){
								//System.out.println("  "+p.showPair(i,j));
							}
						}
					}else if (brokenMajorOrder){
						broken = 1;
					}else if (myInstance.tabC[i-1][j-1]){
						broken = 3;
					}else{
						broken = 2;
					}
					
					Set<Integer> set1 = new HashSet<Integer>();
					Set<Integer> set2 = new HashSet<Integer>();
					Set<Integer> set3 = new HashSet<Integer>();
					
					for (Permutation p: myInstance.A){		
						if (p.getPosition(i) > p.getPosition(j)){
							//minor order
							coord3 = p.getPosition(i) - p.getPosition(j);
							for (int k = p.getPosition(j) +1; k < p.getPosition(i); k++){
								set3.add(p.getTab()[k]);
							}
						}else{
							//major order
							if (coord1 == -1){
								coord1 = p.getPosition(j) - p.getPosition(i);
								for (int k = p.getPosition(i) +1; k < p.getPosition(j); k++){
									set1.add(p.getTab()[k]);
								}
							}else{
								coord2 = p.getPosition(j) - p.getPosition(i);
								for (int k = p.getPosition(i) +1; k < p.getPosition(j); k++){
									set2.add(p.getTab()[k]);
								}
							}
						}
					}
					
					for (int k = 1; k < n; k++){
						if (set3.contains(k)){
							if (set1.contains(k)){
								set1.remove(k);
								set3.remove(k);
							}else if(set2.contains(k)){
								set2.remove(k);
								set3.remove(k);
							}else{
								//nothing
							}
						}
					}
					coord1 = set1.size();
					coord2 = set2.size();
					coord3 = set3.size();
					
					coordinates = ""+coord1+" \t"+coord2+" \t"+coord3+" \t" + broken;
					out.write(coordinates);
					out.write("\n");
					//System.out.println(coordinates);
					
				}
			}
		}
		System.out.println("alwaysBrokenMajorOrderCount: "+alwaysBrokenMajorOrderCount);
		
		
		
			//Close the output stream
			out.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		
	}


	public static void contraintes_spatiales(Instance myInstance,int n, Permutation pi, boolean verbose){
		//besoin des contraintes
		//besoin de tabD
		//besoin d'une approximation
		myInstance.permissionSpatiale = new boolean[n][n];
		boolean[][] permissionSpatialeNouv = new boolean[n][n];
		int[] gauche = new int[n];
		int[] droite = new int[n];
		String space = "";
		
		if (verbose) System.out.println();
		if (verbose) System.out.println("contraintes spatiales");
		
		
		
		for (int element1 = 1; element1<=n; element1++){
			droite[element1-1]=0;
			gauche[element1-1]=0;
			for (int element2 = 1; element2<=n; element2++){
				myInstance.permissionSpatiale[element1-1][element2-1] = true;
				permissionSpatialeNouv[element1-1][element2-1] = false;
				
				if (element1 != element2){
					if (myInstance.tabC[element1-1][element2-1]){
						droite[element1-1]++;
					}else if(myInstance.tabC[element2-1][element1-1]){
						gauche[element1-1]++;
					}else{
						
					}
				}
			}
		}
		
		
		for (int element1 = 1; element1<=n; element1++){
			for (int element2 = 1; element2<=gauche[element1-1]; element2++){
				myInstance.permissionSpatiale[element1-1][element2-1] = false;
			}
			for (int element2 = n-droite[element1-1]+1; element2<=n; element2++){
				myInstance.permissionSpatiale[element1-1][element2-1] = false;
			}
		}
		
		/*for (int element1 = 1; element1<=n; element1++){
			if (element1 <10){
				space = "0"+element1+": ";
			}else{
				space = ""+element1+": ";
			}
			for (int element2 = 1; element2<=n; element2++){
				if (permissionSpatiale[element1-1][element2-1]){
					space += "   ";
				}else{
					space += " xx";
				}
			}
			System.out.println(space);
		}
		System.out.println();*/
		
		
		for (int element1 = 1; element1<=n; element1++){
			myInstance.permissionSpatiale[element1-1][gauche[element1-1]+1-1] = contrainteCase(myInstance,element1,0,n);
			if (!myInstance.permissionSpatiale[element1-1][gauche[element1-1]+1-1]) permissionSpatialeNouv[element1-1][gauche[element1-1]+1-1] = true;
			
			myInstance.permissionSpatiale[element1-1][n-droite[element1-1]-1] = contrainteCase(myInstance,element1,-1,n);
			if (!myInstance.permissionSpatiale[element1-1][n-droite[element1-1]-1]) permissionSpatialeNouv[element1-1][n-droite[element1-1]-1] = true;
		}
		
		//show spatial constraints
		if (verbose){
			for (int i = 1; i<=n; i++){
				int element1 = pi.getTab()[i-1];
				if (element1 <10){
					space = "0"+element1+": ";
				}else{
					space = ""+element1+": ";
				}
				for (int element2 = 1; element2<=n; element2++){
					if (myInstance.permissionSpatiale[element1-1][element2-1]){
						space += "   ";
					}else{
						if (permissionSpatialeNouv[element1-1][element2-1]){
							space += " ##";
						}else{
							space += " xx";
						}
					}
				}
				System.out.println(space);
			}
		}
		
	}
	//methode qui test si un element peut etre place a une certainte position
	private static boolean contrainteCase (Instance myInstance,int element, int position, int n){
		//besoin de tabD
		//besoin des contraintes aussi
		boolean reponse = true;
		int borneInf=0;
		int borneInfA=0;
		int borneInfB=0;
		int borneInfAB=0;
		Set<Integer> setA = new HashSet<Integer>();
		Set<Integer> setB = new HashSet<Integer>();
		Set<Integer> setC = new HashSet<Integer>();
		
		for (int i = 1; i <= n; i ++){
			if (i != element){
				if (myInstance.tabC[i-1][element-1]){
					setA.add(i);
					//System.out.println("A "+i);
				}else if (myInstance.tabC[element-1][i-1]){
					setB.add(i);
					//System.out.println("B "+i);
				}else{
					setC.add(i);
					//System.out.println("C "+i);
				}
			}
		}
		
		//dependamment
		if (position ==0){//avant
			setB.addAll(setC);
		}else if (position ==-1){//arriere
			setA.addAll(setC);
		}
		
		
		for (int i :  setA){
			for (int ii :  setB){
				borneInfAB += myInstance.tabD[i-1][ii-1];
			}
		}
		
		borneInfA = borneInfSet(myInstance, setA, 3);
		for (int i :  setA){
			/*for (int ii :  setA){
				if (i < ii){
					borneInfA += Math.min(tabD[i-1][ii-1],tabD[ii-1][i-1]);
				}
			}*/
			borneInfA += myInstance.tabD[i-1][element-1];
		}
		
		borneInfB = borneInfSet(myInstance, setB, 3);
		for (int i :  setB){
			/*for (int ii :  setB){
				if (i < ii){
					borneInfB += Math.min(tabD[i-1][ii-1],tabD[ii-1][i-1]);
				}
			}*/
			borneInfB += myInstance.tabD[element-1][i-1];
		}
		
		borneInf =  borneInfA + borneInfB + borneInfAB;
		
		//System.out.println("borneInf "+borneInf);
		if (borneInf > myInstance.best_upper_bound)
			reponse = false;
		
		return reponse;
	}
	

	
	
	//approximation avec une contrainte de paire element1 < element2
	//copie les contraintes, rajoute la contrainte de la nouvelle paire puis trouve une borneInf sur tout les elements
	//comme borneInfSet_wConstraints donc avec methode de Conitzer simplifiee
	//methode interessante
	//private static boolean contrainteApproxPaire (Instance myInstance,int element1, int element2, int n, boolean verbose){
	private static int contrainteApproxPaire (Instance myInstance,int element1, int element2, int n, boolean verbose){
		//besoin de tabD
		//besoin des contraintes aussi
		
		// contrainte investiguee: e1 < e2
		// A < e2 < B
		// A' < e1 < B'
		// A' < e1 < e2 < B
		// C: other elements
		boolean methodSwitch = false;
		//boolean reponse = true;
		int borneInf=0;
		
		//copy the constraints
		//System.out.println("copy the constraints");
		myInstance.tabCprime = new boolean[n][n];
		for (int i = 1; i <= n; i ++){
			for (int j = 1; j <= n; j ++){
				myInstance.tabCprime[i-1][j-1]=myInstance.tabC[i-1][j-1];
			}
		}
		
		//add the new constraints
		myInstance.tabCprime[element1-1][element2-1] = true;
		
		//transitive closure of constraints
		//System.out.println("transitive closure");
		/*boolean toContinue = true;
		while (toContinue){
			//System.out.println("+");
			toContinue = false;
			for (int i = 1; i <= n; i ++){
				for (int j = 1; j <= n; j ++){
					for (int k = 1; k <= n; k ++){
						if (tabCprime[i-1][j-1] && tabCprime[j-1][k-1] && !tabCprime[i-1][k-1]){
							tabCprime[i-1][k-1] = true;
							toContinue = true;
						}
					}
				}
			}
		}*/
		transitive_closure_tabCprime_newPair(myInstance,element1, element2);
		
		//min costs
		//System.out.println("min costs");
		int[][] poids = new int [n][n];
		for (int i = 1; i <= n; i ++){
			for (int j = 1; j <= n; j ++){
				if (i < j){
					borneInf += Math.min(myInstance.tabD[i-1][j-1],myInstance.tabD[j-1][i-1]);
				}
			}
		}

		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				if (myInstance.tabD[i-1][j-1] < myInstance.tabD[j-1][i-1]){// i < j
					poids[i-1][j-1] = myInstance.tabD[j-1][i-1] - myInstance.tabD[i-1][j-1] ;//residu majoritaire (cout pour peter ij)
				}else{
					poids[i-1][j-1] = 0;
				}
			}
		}
		
		//known costs
		//System.out.println("known costs");
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				if (myInstance.tabCprime[i-1][j-1]){
					borneInf += poids[j-1][i-1];
					poids[j-1][i-1] = 0;
				}
			}
		}
		
		
		
		
		if (!methodSwitch){
			//first method
			
			//unknown costs with modified Conitzer & Davenport lower bound
			//System.out.println("unknown costs");
			int min = 0;
			for (int i = 1; i <= n; i ++){
				for (int j = 1; j <= n; j ++){
					if ((i != j) && (poids[i-1][j-1] > 0)){
						for (int k = 1; k <= n; k ++){
							if (j != k && k != i){
								if ((poids[i-1][j-1] > 0) && (poids[j-1][k-1] > 0) && (poids[k-1][i-1] > 0)){
									min = Math.min(poids[i-1][j-1],Math.min(poids[j-1][k-1],poids[k-1][i-1]));
									poids[i-1][j-1] -= min;
									poids[j-1][k-1] -= min;
									poids[k-1][i-1] -= min;
									borneInf += min;
								}
							}
						}
					}
				}
			}
			
			//System.out.println("="+borneInf);
			
			
			
			//check for cycles and add to lowerbound if found
			int addCycles = 0;
			int numberOfCycles = 0;
			boolean checkedVertex[] = new boolean [n];
			List<Integer> queue = new ArrayList<Integer>();
			List<Integer> branch = new ArrayList<Integer>();
			int currentVertex = -1;
			for (int i =0; i<n;i++){
				checkedVertex[i] = false; 
			}
			boolean cycleFound = false;
			
			for (int e1 = 1; e1 <= n; e1 ++){
				
				cycleFound = true;//just for the start
				
				while(cycleFound){
				
					queue = new ArrayList<Integer>();
					branch = new ArrayList<Integer>();
					currentVertex = -1;
					for (int i =0; i<n;i++){
						checkedVertex[i] = false; 
					}
					cycleFound = false;//no cycles at the beginning, if one found, continue searching
					
					if (!checkedVertex[e1-1]){
						queue.add(e1);
						//System.out.println(queue.size());
						while (!queue.isEmpty()){
							currentVertex = queue.get(queue.size() -1);
							queue.remove(queue.size() -1);
							
							if (currentVertex== -1){
								branch.remove(branch.size()-1);
							}else{
								if (checkedVertex[currentVertex-1]){
									//nothing
								}else{
									branch.add(currentVertex);
									checkedVertex[currentVertex-1] = true;
									queue.add(-1);
									for (int e2 = 1; e2 <= n; e2 ++){
										if (poids[currentVertex-1][e2-1] > 0 && !cycleFound){
											if (checkedVertex[e2-1]){
												//no add
												if (branch.contains(e2)){
													int minArc = 9999999;
													for(int i = branch.indexOf(e2); i < branch.size()-1; i++){
														if (poids[branch.get(i)-1][branch.get(i+1)-1] < minArc){
															minArc = poids[branch.get(i)-1][branch.get(i+1)-1];
														}
													}
													if (poids[branch.get(branch.size()-1)-1][branch.get(branch.indexOf(e2))-1] < minArc){
														minArc = poids[branch.get(branch.size()-1)-1][branch.get(branch.indexOf(e2))-1];
													}
													
													//System.out.println("cycle! : " + branch + " "+ e2 + " ("+minArc+")");
													
													if (minArc > 0) numberOfCycles++;
													addCycles += minArc;
													//System.out.println("borneInf augmentee de " + minArc);
													
													for(int i = branch.indexOf(e2); i < branch.size()-1; i++){
														poids[branch.get(i)-1][branch.get(i+1)-1] -= minArc;
													}
													poids[branch.get(branch.size()-1)-1][branch.get(branch.indexOf(e2))-1] -= minArc;
													
													queue.clear();
													branch.clear();
													cycleFound = true;
												}
											}else{
												queue.add(e2);
											}
										}
										
										
									}
								}
							}
							//System.out.println(queue.size());
						}
					}
				}
			}
			borneInf += addCycles;
			
		
			
		
		}else{
			// second method (randomized method)
			int borneInfFinal=0;
			int numberOfIterations=5;
			
			for (int counter =1; counter <= numberOfIterations; counter++){
				Permutation rand = null;
				if (counter ==1){
					rand = createIndentite(n);
				}else{
					rand = myInstance.createARandom(n);
				}
				
				//System.out.print(rand + "  ");
			
				borneInf =0;
				int min = 0;
				
				//min costs
				//System.out.println("min costs");
				poids = new int [n][n];
				for (int i = 1; i <= n; i ++){
					for (int j = 1; j <= n; j ++){
						if (i < j){
							borneInf += Math.min(myInstance.tabD[i-1][j-1],myInstance.tabD[j-1][i-1]);
						}
					}
				}

				for (int i = 1; i<= n; i++){
					for (int j = 1; j<= n; j++){
						if (myInstance.tabD[i-1][j-1] < myInstance.tabD[j-1][i-1]){// i < j
							poids[i-1][j-1] = myInstance.tabD[j-1][i-1] - myInstance.tabD[i-1][j-1] ;//residu majoritaire (cout pour peter ij)
						}else{
							poids[i-1][j-1] = 0;
						}
					}
				}
				
				//known costs
				//System.out.println("known costs");
				for (int i = 1; i<= n; i++){
					for (int j = 1; j<= n; j++){
						if (myInstance.tabCprime[i-1][j-1]){
							borneInf += poids[j-1][i-1];
							poids[j-1][i-1] = 0;
						}
					}
				}
				
				
				for (int i = 1; i <= n; i ++){
					for (int j = 1; j <= n; j ++){
						if ((i != j) && (poids[rand.getTab()[i-1]-1][rand.getTab()[j-1]-1] > 0)){
							for (int k = 1; k <= n; k ++){
								if (j != k && k != i){
									if ((poids[rand.getTab()[j-1]-1][rand.getTab()[k-1]-1] > 0) && (poids[rand.getTab()[k-1]-1][rand.getTab()[i-1]-1] > 0)){
										min = Math.min(poids[rand.getTab()[i-1]-1][rand.getTab()[j-1]-1],Math.min(poids[rand.getTab()[j-1]-1][rand.getTab()[k-1]-1],poids[rand.getTab()[k-1]-1][rand.getTab()[i-1]-1]));
										poids[rand.getTab()[i-1]-1][rand.getTab()[j-1]-1] -= min;
										poids[rand.getTab()[j-1]-1][rand.getTab()[k-1]-1] -= min;
										poids[rand.getTab()[k-1]-1][rand.getTab()[i-1]-1] -= min;
										borneInf += min;
									}
								}
							}
						}
					}
				}
			
				
				//System.out.print(borneInf + " \n");
				
				if (borneInf > borneInfFinal){
					borneInfFinal = borneInf;
				}
			
			}
			
			borneInf=borneInfFinal;
			
			
		}
		
		
	
	
		
		if (verbose) System.out.println("borneInf' ("+element1+","+element2+") : "+borneInf+" vs " + myInstance.best_upper_bound);
		//System.out.println("borneInf' ("+element1+","+element2+") : "+borneInf+" vs " + approximation);
		/*if (borneInf > myInstance.best_upper_bound){
			reponse = false;
			if (verbose) System.out.println("    *******************************");
		}else{
			//System.out.println("diff "+(myInstance.best_upper_bound - borneInf));
		}
		return reponse;**/
		return borneInf - myInstance.best_upper_bound;
	}
	

	
	//approximation avec une contrainte de paire element1 < element2
	//copie les contraintes, rajoute la contrainte de la nouvelle paire puis trouve une borneInf sur tout les elements
	//comme borneInfSet_wConstraints donc avec methode de Conitzer simplifiee
	//methode interessante
	/*private static boolean contrainteApproxPaireCplexLP (Instance myInstance,int element1, int element2, boolean verbose){
		//besoin de tabD
		//besoin des contraintes aussi
		
		// contrainte investiguee: e1 < e2
		// A < e2 < B
		// A' < e1 < B'
		// A' < e1 < e2 < B
		// C: other elements
		boolean reponse = true;
		int borneInf=0;
		int n = myInstance.n;
		double objval = -1.0;
		
		//copy the constraints
		//System.out.println("copy the constraints");
		myInstance.tabCprime = new boolean[n][n];
		for (int i = 1; i <= n; i ++){
			for (int j = 1; j <= n; j ++){
				myInstance.tabCprime[i-1][j-1]=myInstance.tabC[i-1][j-1];
			}
		}
		
		//add the new constraints
		myInstance.tabCprime[element1-1][element2-1] = true;
		
		//transitive closure of constraints
		//System.out.println("transitive closure");
		/*boolean toContinue = true;
		while (toContinue){
			//System.out.println("+");
			toContinue = false;
			for (int i = 1; i <= n; i ++){
				for (int j = 1; j <= n; j ++){
					for (int k = 1; k <= n; k ++){
						if (tabCprime[i-1][j-1] && tabCprime[j-1][k-1] && !tabCprime[i-1][k-1]){
							tabCprime[i-1][k-1] = true;
							toContinue = true;
						}
					}
				}
			}
		}*/
		/*transitive_closure_tabCprime_newPair(myInstance,element1, element2);
		
		
		
		
			
		
		
		
		int compteur = 0;
		int coord[][]=new int[n][n];
		//int coord1[]=new int[(n*(n-1))];
		//int coord2[]=new int[(n*(n-1))];
		boolean success= false;
		
		try {
			IloCplex cplex = new IloCplex();
	         // create model and solve it
			
			cplex.setOut(null);//quiet mode
			
			IloNumVar[] x = cplex.numVarArray((n*(n-1)), 0.0, 1.0);//probleme relaxe
			//IloNumVar[] x = cplex.intVarArray((n*(n-1)), 0, 1);//probleme original
			IloLinearNumExpr expr = cplex.linearNumExpr();
			
			compteur = 0;
			for(int i=1;i<=n;i++){
				for(int j=1;j<=n;j++){
					if (i != j){
						coord[i-1][j-1] = compteur;
						expr.addTerm(myInstance.tabD[i-1][j-1], x[compteur]);
						compteur++;
					}
				}
			}
			cplex.addMinimize(expr);
			
			//contraintes de variables
			for(int i=1;i<=n;i++){
				for(int j=i+1;j<=n;j++){
					cplex.addEq(cplex.sum(x[coord[i-1][j-1]], x[coord[j-1][i-1]]), 1);
					if (myInstance.tabCprime[i-1][j-1]){
						cplex.addEq(x[coord[i-1][j-1]], 1);
					}
					if (myInstance.tabCprime[j-1][i-1]){
						cplex.addEq(x[coord[j-1][i-1]], 1);
					}
				}
			}
			
			//contraintes de transitivite
			for(int i=1;i<=n;i++){
				for(int j=1;j<=n;j++){
					if (i !=j){
						for(int k=1;k<=n;k++){
							if (k != i && k != j){
								cplex.addGe(cplex.sum(x[coord[i-1][j-1]], x[coord[j-1][k-1]], x[coord[k-1][i-1]]), 1);
								//out.write(" c"+compteur+": x_"+i+"_"+j+" + x_"+j+"_"+k+" + x_"+k+"_"+i+" >= 1 \n");
							}
						}
					}
					
				}
			}
			
			cplex.setParam(IloCplex.Param.Threads, 1);
			//cplex.setParam(IloCplex.Param.TimeLimit, 2.0);
			cplex.setParam(IloCplex.Param.DetTimeLimit, 100000.0);
			
			success=cplex.solve();
			//System.out.println("cplex success: "+success);
			
			objval = cplex.getObjValue();
			objval -= 0.001;//to cope with numerical residus
			//System.out.println("borneInf' ("+element1+","+element2+") : "+"cplex objval: "+objval + "  ("+Math.ceil(objval)+")");
			
			cplex.clearModel();
			cplex.end();
			
	    } catch (IloException e) {
	    	System.err.println("Concert exception caught: " + e);
	    }
		
		if (objval != -1.0){
			borneInf = (int)(Math.ceil(objval));
		}
	
	
		
		if (verbose) System.out.println("borneInf' ("+element1+","+element2+") : "+borneInf+" vs " + myInstance.best_upper_bound);
		//System.out.println("borneInf' ("+element1+","+element2+") : "+borneInf+" vs " + approximation);
		if (borneInf > myInstance.best_upper_bound){
			reponse = false;
			if (verbose) System.out.println("    *******************************");
		}else{
			//System.out.println("diff "+(myInstance.best_upper_bound - borneInf));
		}
		return reponse;
	}*/
	
	
	
	//approximation avec une contrainte de paire element1 < element2
		//copie les contraintes, rajoute la contrainte de la nouvelle paire puis trouve une borneInf sur tout les elements
		//comme borneInfSet_wConstraints donc avec methode de Conitzer simplifiee
		//methode interessante
		private static boolean contrainteApproxPaireMaxFlow (Instance myInstance,int element1, int element2, int n, boolean verbose){
			//besoin de tabD
			//besoin des contraintes aussi
			
			// contrainte investiguee: e1 < e2
			// A < e2 < B
			// A' < e1 < B'
			// A' < e1 < e2 < B
			// C: other elements
			boolean methodSwitch = false;
			boolean reponse = true;
			int borneInf=0;
			int min = 0;
			
			Set<Integer> set1 = new HashSet<Integer>();
			for (int i =1; i <= n; i++)
				set1.add(i);
			
			
			//copy the constraints
			//System.out.println("copy the constraints");
			myInstance.tabCprime = new boolean[n][n];
			for (int i = 1; i <= n; i ++){
				for (int j = 1; j <= n; j ++){
					myInstance.tabCprime[i-1][j-1]=myInstance.tabC[i-1][j-1];
				}
			}
			
			//add the new constraints
			myInstance.tabCprime[element1-1][element2-1] = true;
			
			//transitive closure of constraints
			//System.out.println("transitive closure");
			/*boolean toContinue = true;
			while (toContinue){
				//System.out.println("+");
				toContinue = false;
				for (int i = 1; i <= n; i ++){
					for (int j = 1; j <= n; j ++){
						for (int k = 1; k <= n; k ++){
							if (tabCprime[i-1][j-1] && tabCprime[j-1][k-1] && !tabCprime[i-1][k-1]){
								tabCprime[i-1][k-1] = true;
								toContinue = true;
							}
						}
					}
				}
			}*/
			transitive_closure_tabCprime_newPair(myInstance,element1, element2);
			
			
			
			
			//borneInf2 de Conitzer
			int[][] poids = new int [n][n];
			int[][] flow = new int [n][n];
			
			
			//min costs
			for (int i = 1; i <= n; i ++){
				for (int j = 1; j <= n; j ++){
					if (i < j){
						borneInf += Math.min(myInstance.tabD[i-1][j-1],myInstance.tabD[j-1][i-1]);
					}
				}
			}

			for (int i = 1; i<= n; i++){
				for (int j = 1; j<= n; j++){
					if (myInstance.tabD[i-1][j-1] < myInstance.tabD[j-1][i-1]){// i < j
						poids[i-1][j-1] = myInstance.tabD[j-1][i-1] - myInstance.tabD[i-1][j-1] ;//residu majoritaire (cout pour peter ij)
					}else{
						poids[i-1][j-1] = 0;
					}
					flow[i-1][j-1] = 0;
				}
			}
			
			//known costs
			//System.out.println("known costs");
			for (int i = 1; i<= n; i++){
				for (int j = 1; j<= n; j++){
					if (myInstance.tabCprime[i-1][j-1]){
						borneInf += poids[j-1][i-1];
						poids[j-1][i-1] = 0;
					}
				}
			}
			
			
			
			
			
			
			
			

			
			List<Integer> queue = new ArrayList<Integer>();
			List<Integer> path = new ArrayList<Integer>();
			int currentVertex = -1;
			int e1 = 0;
			boolean toContinue = true;
			int flowTotal = 0;
			int allowedSize = 4;

			for (int i : set1){
				flowTotal = 0;
				
				for (int j : set1){
					for (int k : set1){
						if (i != j && j != k && k != i){
							if ((poids[i-1][j-1]-flow[i-1][j-1] > 0) && (poids[j-1][k-1]-flow[j-1][k-1] > 0) && (poids[k-1][i-1]-flow[k-1][i-1] > 0)){
								min = Math.min(poids[i-1][j-1]-flow[i-1][j-1],Math.min(poids[j-1][k-1]-flow[j-1][k-1],poids[k-1][i-1]-flow[k-1][i-1]));
								//poids[i-1][j-1] -= min;
								//poids[j-1][k-1] -= min;
								//poids[k-1][i-1] -= min;
								//borneInf += min;
								
								//the direct flow
								flow[i-1][j-1] += min;
								flow[j-1][k-1] += min;
								flow[k-1][i-1] += min;
								
								//the reverse flow
								//flow[j-1][i-1] -= min;
								//flow[k-1][j-1] -= min;
								//flow[i-1][k-1] -= min;
								
								//flowTotal += min;
							}
						}
					}
				}
				
				/*for (int j : set1){
					for (int k : set1){
						if (j != k){
							if(flow[j-1][k-1] > 0){
								flow[k-1][j-1] -= flow[j-1][k-1];
							}
						}
					}
				}*/
				
				
				
				
				
				
				//ford-fulkerson max flow algorithm (mini version)
				toContinue = true;
				allowedSize = 4;

				while (toContinue){
					toContinue = false;
					
					e1 =i;
					currentVertex = -1;
					queue = new ArrayList<Integer>();
					path = new ArrayList<Integer>();
		
				
					queue.add(e1);
					//System.out.println(queue.size());
					while (!queue.isEmpty()){
						currentVertex = queue.get(queue.size() -1);
						queue.remove(queue.size() -1);
						
						if (currentVertex== -1){
							path.remove(path.size()-1);
						}else{
							path.add(currentVertex);
							queue.add(-1);
							for (int e2 : set1){
								if (!toContinue){
									if (currentVertex == e1){
										if ((poids[currentVertex-1][e2-1] - flow[currentVertex-1][e2-1]) > 0){
											queue.add(e2);
										}
									}else if (e2 == e1){
										if((poids[currentVertex-1][e2-1] - flow[currentVertex-1][e2-1]) > 0){
											//new path: better flow!
											path.add(e1);
											
											//calcultate minimum edge on that path
											min = 9999999;
											for(int ii = 0; ii < path.size()-1; ii++){
												if ((poids[path.get(ii)-1][path.get(ii+1)-1] - flow[path.get(ii)-1][path.get(ii+1)-1]+flow[path.get(ii+1)-1][path.get(ii)-1]) < min){
													min = (poids[path.get(ii)-1][path.get(ii+1)-1] - flow[path.get(ii)-1][path.get(ii+1)-1]+flow[path.get(ii+1)-1][path.get(ii)-1]);
												}
											}
											//if ((poids[path.get(path.size()-1)-1][e1-1] - flow[path.get(path.size()-1)-1][e1-1]+flow[e1-1][path.get(path.size()-1)-1]) < min){
											//	min = (poids[path.get(path.size()-1)-1][e1-1] - flow[path.get(path.size()-1)-1][e1-1]+flow[e1-1][path.get(path.size()-1)-1]);
											//}
											
											//trow minimal flow on the path
											for(int ii = 0; ii < path.size()-1; ii++){
												flow[path.get(ii)-1][path.get(ii+1)-1] += min;
												//flow[path.get(ii+1)-1][path.get(ii)-1] -= min;
												if (flow[path.get(ii)-1][path.get(ii+1)-1] > 0 && flow[path.get(ii)-1][path.get(ii+1)-1] > 0){
													int minBothEdges = Math.min(flow[path.get(ii)-1][path.get(ii+1)-1], flow[path.get(ii+1)-1][path.get(ii)-1]);
													flow[path.get(ii)-1][path.get(ii+1)-1] -= minBothEdges; 
													flow[path.get(ii+1)-1][path.get(ii)-1] -= minBothEdges; 
												}
											}
											//flow[path.get(path.size()-1)-1][e1-1] += min;
											//flow[e1-1][path.get(path.size()-1)-1] -= min;
											

											//better flow!
											if (min > 0){
												//path.add(e1);//pour affichage
												//System.out.println("better flow! " + min + " " + path);
												toContinue = true;
												queue.clear();
												path.clear();
												//flowTotal += min;
											}
										}
										
									}else if ((poids[currentVertex-1][e2-1] - flow[currentVertex-1][e2-1] + flow[e2-1][currentVertex-1]) > 0){
										if (path.size() < allowedSize){
											if (!path.contains(e2)){
													queue.add(e2);
											}
										}

									}
								}
								
							}
							
						}
					}
					
					//if ((!toContinue) && (allowedSize<=5)){
					//	toContinue = true;
					//	allowedSize++;
					//}
						
					
				}	
				
				
				
				
				
				
				
				for (int j : set1){
					if (j != i){
						if(flow[i-1][j-1] > 0){
							flowTotal += flow[i-1][j-1];
						}
					}
				}
				
				
				for (int j : set1){
					for (int k : set1){
						if (j != k){
							if(flow[j-1][k-1] > 0){
								poids[j-1][k-1] -= flow[j-1][k-1];
							}
							flow[j-1][k-1] = 0;
						}
					}
				}
				
				
				
				
				
				borneInf += flowTotal;
			}
			
			
			
		
		
			
			if (verbose) System.out.println("borneInf' ("+element1+","+element2+") : "+borneInf+" vs " + myInstance.best_upper_bound);
			//System.out.println("borneInf' ("+element1+","+element2+") : "+borneInf+" vs " + approximation);
			if (borneInf > myInstance.best_upper_bound){
				reponse = false;
				if (verbose) System.out.println("    *******************************");
			}else{
				//System.out.println("diff "+(myInstance.best_upper_bound - borneInf));
			}
			return reponse;
		}
		
		
	
	//transitive closure for 'contrainteApproxPaire2'
	private static void transitive_closure_tabCprime_newPair(Instance myInstance,int i, int j){
		int n = myInstance.n;
		//if we have the constraint i<j
		for (int k = 1; k <= n; k++){
			//jk
			if (myInstance.tabCprime[j-1][k-1] && !myInstance.tabCprime[i-1][k-1]){
				myInstance.tabCprime[i-1][k-1] = true;
				transitive_closure_tabCprime_newPair(myInstance, i, k);
			}
			
			//ki
			if (myInstance.tabCprime[k-1][i-1] && !myInstance.tabCprime[k-1][j-1]){
				myInstance.tabCprime[k-1][j-1] = true;
				transitive_closure_tabCprime_newPair(myInstance, k, j);
			}
		}
	}
	
	//approximation avec une contrainte de triplet element1 < element2 < element3
	private static boolean contrainteApproxTriplet2 (Instance myInstance,int element1, int element2,int element3, int n, boolean verbose){
		//besoin de tabD
		//besoin des contraintes aussi
		
		// contrainte investiguee: e1 < e2
		// A < e2 < B
		// A' < e1 < B'
		// A' < e1 < e2 < B
		// C: other elements
		boolean reponse = true;
		int borneInf=0;
		
		//copy the constraints
		//System.out.println("copy the constraints");
		myInstance.tabCprime = new boolean[n][n];
		for (int i = 1; i <= n; i ++){
			for (int j = 1; j <= n; j ++){
				myInstance.tabCprime[i-1][j-1]=myInstance.tabC[i-1][j-1];
			}
		}
		
		//add the new constraints
		myInstance.tabCprime[element1-1][element2-1] = true;
		myInstance.tabCprime[element2-1][element3-1] = true;
		myInstance.tabCprime[element1-1][element3-1] = true;
		
		//transitive closure of constraints
		//System.out.println("transitive closure");
		/*boolean toContinue = true;
		while (toContinue){
			//System.out.println("+");
			toContinue = false;
			for (int i = 1; i <= n; i ++){
				for (int j = 1; j <= n; j ++){
					for (int k = 1; k <= n; k ++){
						if (tabCprime[i-1][j-1] && tabCprime[j-1][k-1] && !tabCprime[i-1][k-1]){
							tabCprime[i-1][k-1] = true;
							toContinue = true;
						}
					}
				}
			}
		}*/
		transitive_closure_tabCprime_newPair(myInstance,element1, element2);
		transitive_closure_tabCprime_newPair(myInstance,element1, element3);
		transitive_closure_tabCprime_newPair(myInstance,element2, element3);
		
		//min costs
		//System.out.println("min costs");
		int[][] poids = new int [n][n];
		for (int i = 1; i <= n; i ++){
			for (int j = 1; j <= n; j ++){
				if (i < j){
					borneInf += Math.min(myInstance.tabD[i-1][j-1],myInstance.tabD[j-1][i-1]);
				}
			}
		}

		//System.out.println("="+borneInf);
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				if (myInstance.tabD[i-1][j-1] < myInstance.tabD[j-1][i-1]){// i < j
					poids[i-1][j-1] = myInstance.tabD[j-1][i-1] - myInstance.tabD[i-1][j-1] ;//residu majoritaire (cout pour peter ij)
				}else{
					poids[i-1][j-1] = 0;
				}
			}
		}
		
		//known costs
		//System.out.println("known costs");
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				if (myInstance.tabCprime[i-1][j-1]){
					borneInf += poids[j-1][i-1];
					poids[j-1][i-1] = 0;
				}
			}
		}
		//System.out.println("="+borneInf);
		
		
		//unknown costs with modified Conitzer & Davenport lower bound
		//System.out.println("unknown costs");
		int min = 0;
		int max = 3;
		if (max >= 3){
			for (int i = 1; i <= n; i ++){
				for (int j = 1; j <= n; j ++){
					for (int k = 1; k <= n; k ++){
						if (i != j && j != k && k != i){
							if ((poids[i-1][j-1] > 0) && (poids[j-1][k-1] > 0) && (poids[k-1][i-1] > 0)){
								min = Math.min(poids[i-1][j-1],Math.min(poids[j-1][k-1],poids[k-1][i-1]));
								poids[i-1][j-1] -= min;
								poids[j-1][k-1] -= min;
								poids[k-1][i-1] -= min;
								borneInf += min;
							}
						}
					}
				}
			}
		}
		//System.out.println("="+borneInf);
	
		
		if (verbose) System.out.println("borneInf' ("+element1+","+element2+") : "+borneInf+" vs " + myInstance.best_upper_bound);
		//System.out.println("borneInf' ("+element1+","+element2+") : "+borneInf+" vs " + approximation);
		if (borneInf > myInstance.best_upper_bound){
			reponse = false;
			if (verbose) System.out.println("    *******************************");
		}
		return reponse;
	}
	
	
	
	
	//approximation avec une contrainte de paire element1 < element2
	//methode pas interessante
	private static boolean contrainteApproxTriplet(Instance myInstance,int element1, int element2, int element3, int n, int approximation, boolean verbose){
		//besoin de tabD
		//besoin des contraintes aussi
		
		// contrainte investiguee: e1 < e2 < e3
		boolean reponse = true;
		int borneInf=0;
		
		int borneInf1=0;
		int borneInf2=0;
		int borneInf3=0;
		
		int choix1=0;
		int choix2=0;
		int choix3=0;
		int choix4=0;
		
		Set<Integer> setA = new HashSet<Integer>();
		Set<Integer> setB = new HashSet<Integer>();
		Set<Integer> setC = new HashSet<Integer>();
		Set<Integer> setD = new HashSet<Integer>();
		Set<Integer> setAB = new HashSet<Integer>();
		Set<Integer> setCD = new HashSet<Integer>();
		Set<Integer> setABC = new HashSet<Integer>();
		Set<Integer> setBCD = new HashSet<Integer>();
		Set<Integer> setE = new HashSet<Integer>();
		
		
		
		
		//1ere borneInf sur le cas
		for (int i = 1; i <= n; i ++){
			if (i != element1 && i != element2 && i != element3){
				if (myInstance.tabC[i-1][element1-1]){
					setA.add(i);
				}else if (myInstance.tabC[element3-1][i-1]){
					setB.add(i);
				}else{
					setE.add(i);
				}
			}
		}
		borneInf1=0;
		borneInf1 += myInstance.tabD[element1-1][element2-1] + myInstance.tabD[element1-1][element3-1] + myInstance.tabD[element1-2][element3-1];
		for (int i :  setA){
			for (int ii :  setB){
				borneInf1 += myInstance.tabD[i-1][ii-1];
			}
		}
		borneInf1 += borneInfSet(myInstance, setA, 3);
		for (int i :  setA){
			borneInf1 += myInstance.tabD[i-1][element1-1];
			borneInf1 += myInstance.tabD[i-1][element2-1];
			borneInf1 += myInstance.tabD[i-1][element3-1];
		}
		borneInf1 += borneInfSet(myInstance, setB, 3);
		for (int i :  setB){
			borneInf1 += myInstance.tabD[element1-1][i-1];
			borneInf1 += myInstance.tabD[element2-1][i-1];
			borneInf1 += myInstance.tabD[element3-1][i-1];
		}
		
		borneInf1 += borneInfSet(myInstance,setE, 3);
		for (int i :  setE){
			
			choix1=0; //i e1 e2 e3
			for (int ii :  setA){
				choix1 += Math.min(myInstance.tabD[i-1][ii-1],myInstance.tabD[ii-1][i-1]);
			}
			choix1 += myInstance.tabD[i-1][element1-1];
			choix1 += myInstance.tabD[i-1][element2-1];
			choix1 += myInstance.tabD[i-1][element3-1];
			for (int ii :  setB){
				choix1 += myInstance.tabD[i-1][ii-1];
			}
			
			choix2=0; //e1 i e2 e3
			for (int ii :  setA){
				choix2 += myInstance.tabD[ii-1][i-1];
			}
			choix2 += myInstance.tabD[element1-1][i-1];
			choix2 += myInstance.tabD[i-1][element2-1];
			choix2 += myInstance.tabD[i-1][element3-1];
			for (int ii :  setB){
				choix2 += myInstance.tabD[i-1][ii-1];
			}
			
			choix3=0; //e1 e2 i e3
			for (int ii :  setA){
				choix3 += myInstance.tabD[ii-1][i-1];
			}
			choix3 += myInstance.tabD[element1-1][i-1];
			choix3 += myInstance.tabD[element2-1][i-1];
			choix3 += myInstance.tabD[i-1][element3-1];
			for (int ii :  setB){
				choix3 += myInstance.tabD[i-1][ii-1];
			}
			
			
			choix4=0; //e1 e2 e3 i
			for (int ii :  setA){
				choix4 += myInstance.tabD[ii-1][i-1];
			}
			choix4 += myInstance.tabD[element1-1][i-1];
			choix4 += myInstance.tabD[element2-1][i-1];
			choix4 += myInstance.tabD[element3-1][i-1];
			for (int ii :  setB){
				choix4 += Math.min(myInstance.tabD[i-1][ii-1],myInstance.tabD[ii-1][i-1]);
			}
			//System.out.println(choix1+" "+choix2+" "+choix3+" "+choix4+" ");
			borneInf1 += Math.min(Math.min(choix1,choix2),Math.min(choix3,choix4));
		}
		
		
		
		//2ere borneInf sur le cas
		borneInf2 = 0;
		
		
		//3e borneInf sur le cas
		setE = new HashSet<Integer>();
		for (int i = 1; i <= n; i ++){
			if (i != element1 && i != element2 && i != element3){
				setE.add(i);
			}
		}
		borneInf3 = 0;
		borneInf3 = borneInfSet(myInstance, setE, 3); 
		borneInf3 += myInstance.tabD[element1-1][element2-1] + myInstance.tabD[element1-1][element3-1] + myInstance.tabD[element1-2][element3-1];
		for (int i :  setE){
			choix1 = myInstance.tabD[i-1][element1-1]+myInstance.tabD[i-1][element2-1]+myInstance.tabD[i-1][element3-1]; //i e1 e2 e3
			choix2 = myInstance.tabD[element1-1][i-1]+myInstance.tabD[i-1][element2-1]+myInstance.tabD[i-1][element3-1]; //e1 i e2 e3
			choix3 = myInstance.tabD[element1-1][i-1]+myInstance.tabD[element2-1][i-1]+myInstance.tabD[i-1][element3-1]; //e1 e2 i e3
			choix4 = myInstance.tabD[element1-1][i-1]+myInstance.tabD[element2-1][i-1]+myInstance.tabD[element3-1][i-1]; //e1 e2 e3 i
			borneInf3 += Math.min(Math.min(choix1,choix2),Math.min(choix3,choix4));
		}
		
		
		
		
		if (verbose) System.out.println("borneInf ("+element1+","+element2+","+element3+") : "+borneInf1+ ", " +borneInf2 +", " +borneInf3 + " vs " + approximation);
		borneInf = Math.max(Math.max(borneInf1,borneInf2),borneInf3);
		if (borneInf > approximation){
			reponse = false;
			if (verbose) System.out.println("    *******************************");
		}
		return reponse;
	}
	
	
	//methode qui retourne une borneInf sur un set d'elements muni de contraintes utilisant la methode de Conitzer simplifiee
	private static int borneInfSet_wConstraints (Instance myInstance,Collection<Integer> set1, int max, Permutation approx){
		//besoin de tabD
		//besoin des contraintes aussi tabC
		int n = myInstance.n;

		int borneInf=0;
		boolean useApproxPermutation = false;
		
		if (set1 == null){
			set1 = new HashSet<Integer>();
			for (int i =1; i <= n; i++)
				set1.add(i);
		}
		
		
		//copy the constraints
		//System.out.println("copy the constraints");
		myInstance.tabCprime = new boolean[n][n];
		for (int i = 1; i <= n; i ++){
			for (int j = 1; j <= n; j ++){
				myInstance.tabCprime[i-1][j-1]=myInstance.tabC[i-1][j-1];
			}
		}
		
		
		//min costs
		//System.out.println("min costs");
		int[][] poids = new int [n][n];
		
		for (int i : set1){
			for (int j : set1){
				if (i < j){
					borneInf += Math.min(myInstance.tabD[i-1][j-1],myInstance.tabD[j-1][i-1]);
				}
			}
		}
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				if (myInstance.tabD[i-1][j-1] < myInstance.tabD[j-1][i-1]){// i < j
					poids[i-1][j-1] = myInstance.tabD[j-1][i-1] - myInstance.tabD[i-1][j-1] ;
				}else{
					poids[i-1][j-1] = 0;
				}
			}
		}
		
		//System.out.println(borneInf);
		//known costs
		//System.out.println("known costs");
		for (int i : set1){
			for (int j : set1){
				if (myInstance.tabCprime[i-1][j-1]){
					borneInf += poids[j-1][i-1];
					poids[j-1][i-1] = 0;
				}
			}
		}
		//System.out.println("="+borneInf);
		
		//System.out.println(borneInf);
		//using the ApproxPermutation to guide the choice of 3-cycles to investigate 
		//
		int min = 0;
		if (useApproxPermutation){
			if (approx != null){
				for (int i : set1){
					for (int j : set1){
						if ((i != j) && (myInstance.tabD[i-1][j-1] < myInstance.tabD[j-1][i-1]) && (poids[i-1][j-1] > 0)){// i < j maj et arc avec poids
							if (approx.getPosition(i) > approx.getPosition(j)){//si solution approx contient j<i , i.e. un ordre majoritaire brise
								for (int k : set1){//verifier s'il y a un 3-cycles qui passe par la
									if (j != k && k != i){
										if ((poids[j-1][k-1] > 0) && (poids[k-1][i-1] > 0)){
											min = Math.min(poids[i-1][j-1],Math.min(poids[j-1][k-1],poids[k-1][i-1]));
											poids[i-1][j-1] -= min;
											poids[j-1][k-1] -= min;
											poids[k-1][i-1] -= min;
											borneInf += min;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		//System.out.println(borneInf);
		
		
		//unknown costs with modified(simplified) Conitzer & Davenport lower bound
		//int min = 0;
		min = 0;
		
		if (max >= 3){
			for (int i : set1){
				for (int j : set1){
					if((poids[i-1][j-1] > 0) && (i != j)){
						for (int k : set1){
							if ( j != k && k != i){
								if ((poids[i-1][j-1] > 0) && (poids[j-1][k-1] > 0) && (poids[k-1][i-1] > 0)){
									min = Math.min(poids[i-1][j-1],Math.min(poids[j-1][k-1],poids[k-1][i-1]));
									poids[i-1][j-1] -= min;
									poids[j-1][k-1] -= min;
									poids[k-1][i-1] -= min;
									borneInf += min;
								}
							}
						}
					}
				}
			}
		}
		if (max >= 4){
			for (int e1 : set1){
				for (int e2 : set1){
					for (int e3 : set1){
						for (int e4 : set1){
							if ((poids[e1-1][e2-1] > 0) && (poids[e2-1][e3-1] > 0) && (poids[e3-1][e4-1] > 0)&& (poids[e4-1][e1-1] > 0)){
								min = Math.min(Math.min(poids[e1-1][e2-1],poids[e2-1][e3-1]),Math.min(poids[e3-1][e4-1],poids[e4-1][e1-1]));
								poids[e1-1][e2-1] -= min;
								poids[e2-1][e3-1] -= min;
								poids[e3-1][e4-1] -= min;
								poids[e4-1][e1-1] -= min;
								borneInf += min;
							}
						}
					}
				}
			}
		}
		
		
		//affichage .dot
		/*
		System.out.println();
		for (int i : set1){
			for (int j : set1){
				if (poids[i-1][j-1] > 0){
					boolean ok = true;
					for (int k : set1){
						if ((poids[i-1][k-1] > 0)&&(poids[k-1][j-1] > 0)){
							ok =false;
						}
					}
					if (ok) {System.out.println(i+ " -> " + j + "[label="+poids[i-1][j-1]+"];");}
				}
			}
		}
		System.out.println();
		*/
		
		
		
		//check for cycles and add to lowerbound if found
		int addCycles = 0;
		int numberOfCycles = 0;
		boolean checkedVertex[] = new boolean [n];
		List<Integer> queue = new ArrayList<Integer>();
		List<Integer> branch = new ArrayList<Integer>();
		int currentVertex = -1;
		for (int i =0; i<n;i++){
			checkedVertex[i] = false; 
		}
		boolean cycleFound = false;
		
		for (int e1 : set1){
			cycleFound = true;//just for the start
			
			while(cycleFound){
				queue = new ArrayList<Integer>();
				branch = new ArrayList<Integer>();
				currentVertex = -1;
				for (int i =0; i<n;i++){
					checkedVertex[i] = false; 
				}
				cycleFound = false;//no cycles at the beginning, if one found, continue searching
				
				if (!checkedVertex[e1-1]){
					queue.add(e1);
					//System.out.println(queue.size());
					while (!queue.isEmpty()){
						currentVertex = queue.get(queue.size() -1);
						queue.remove(queue.size() -1);
						
						if (currentVertex== -1){
							branch.remove(branch.size()-1);
						}else{
							if (checkedVertex[currentVertex-1]){
								//nothing
							}else{
								branch.add(currentVertex);
								checkedVertex[currentVertex-1] = true;
								queue.add(-1);
								for (int e2 : set1){
									if (poids[currentVertex-1][e2-1] > 0 && !cycleFound){
										if (checkedVertex[e2-1]){
											//no add
											if (branch.contains(e2)){
												int minArc = 9999999;
												for(int i = branch.indexOf(e2); i < branch.size()-1; i++){
													if (poids[branch.get(i)-1][branch.get(i+1)-1] < minArc){
														minArc = poids[branch.get(i)-1][branch.get(i+1)-1];
													}
												}
												if (poids[branch.get(branch.size()-1)-1][branch.get(branch.indexOf(e2))-1] < minArc){
													minArc = poids[branch.get(branch.size()-1)-1][branch.get(branch.indexOf(e2))-1];
												}
												
												System.out.println("cycle! : " + branch + " "+ e2 + " ("+minArc+")");
												
												if (minArc > 0){
													numberOfCycles++;
													//System.out.println("cycle! : " + branch + " "+ e2 + " ("+minArc+")");
													addCycles += minArc;
												}
												
												
												for(int i = branch.indexOf(e2); i < branch.size()-1; i++){
													poids[branch.get(i)-1][branch.get(i+1)-1] -= minArc;
												}
												poids[branch.get(branch.size()-1)-1][branch.get(branch.indexOf(e2))-1] -= minArc;
												
												
												queue.clear();
												branch.clear();
												cycleFound = true;
											}
										}else{
											queue.add(e2);
										}
									}
									
									
								}
							}
						}
						//System.out.println(queue.size());
					}
				}
			}
		
		}
		borneInf += addCycles;
		System.out.println(" " +numberOfCycles+ " cycles,  borneInf augmentee de " + addCycles);
	
		myInstance.setLowerBound(borneInf);
		
		return borneInf; 
	}
	
	
	//methode qui retourne une borneInf sur un set d'elements muni de contraintes utilisant la methode de Conitzer simplifiee
		private static int borneInfSet_wConstraints_2 (Instance myInstance,Collection<Integer> set1){
			//besoin de tabD
			//besoin des contraintes aussi tabC
			int n = myInstance.n;

			double borneInf=0.0;
			boolean useApproxPermutation = false;
			
			if (set1 == null){
				set1 = new HashSet<Integer>();
				for (int i =1; i <= n; i++)
					set1.add(i);
			}
			
			
			//copy the constraints
			//System.out.println("copy the constraints");
			myInstance.tabCprime = new boolean[n][n];
			for (int i = 1; i <= n; i ++){
				for (int j = 1; j <= n; j ++){
					myInstance.tabCprime[i-1][j-1]=myInstance.tabC[i-1][j-1];
				}
			}
			
			
			//min costs
			//System.out.println("min costs");
			double[][] poids = new double [n][n];
			
			for (int i : set1){
				for (int j : set1){
					if (i < j){
						borneInf += (double)Math.min(myInstance.tabD[i-1][j-1],myInstance.tabD[j-1][i-1]);
					}
				}
			}
			for (int i = 1; i<= n; i++){
				for (int j = 1; j<= n; j++){
					if (myInstance.tabD[i-1][j-1] < myInstance.tabD[j-1][i-1]){// i < j
						poids[i-1][j-1] = (double)(myInstance.tabD[j-1][i-1] - myInstance.tabD[i-1][j-1]) ;
					}else{
						poids[i-1][j-1] = 0.0;
					}
				}
			}
			
			//System.out.println(borneInf);
			//known costs
			//System.out.println("known costs");
			for (int i : set1){
				for (int j : set1){
					if (myInstance.tabCprime[i-1][j-1]){
						borneInf += poids[j-1][i-1];
						poids[j-1][i-1] = 0.0;
					}
				}
			}
			//System.out.println("="+borneInf);
			

			
			
			//unknown costs with modified(simplified) Conitzer & Davenport lower bound
			//int min = 0;
			double min = 0;
			boolean found3cycle = true;
			double threshold=0.7;
			
			while(found3cycle){
				found3cycle = false;
				for (int i : set1){
					for (int j : set1){
						if((poids[i-1][j-1] >= threshold) && (i != j)){
							for (int k : set1){
								if ( j != k && k != i){
									if ((poids[i-1][j-1] >= threshold) && (poids[j-1][k-1] >= threshold) && (poids[k-1][i-1] >= threshold)){
										min = threshold;
										//min = Math.min(poids[i-1][j-1],Math.min(poids[j-1][k-1],poids[k-1][i-1]));
										//min = Math.random()*min;
										poids[i-1][j-1] -= min;
										poids[j-1][k-1] -= min;
										poids[k-1][i-1] -= min;
										borneInf += min;
										found3cycle = true;
									}
								}
							}
						}
					}
				}
			}
			
			for (int i : set1){
				for (int j : set1){
					if((poids[i-1][j-1] > 0) && (i != j)){
						for (int k : set1){
							if ( j != k && k != i){
								if ((poids[i-1][j-1] > 0) && (poids[j-1][k-1] > 0) && (poids[k-1][i-1] > 0)){
									min = Math.min(poids[i-1][j-1],Math.min(poids[j-1][k-1],poids[k-1][i-1]));
									poids[i-1][j-1] -= min;
									poids[j-1][k-1] -= min;
									poids[k-1][i-1] -= min;
									borneInf += min;
								}
							}
						}
					}
				}
			}
			


			
			//check for cycles and add to lowerbound if found
			double addCycles = 0.0;
			int numberOfCycles = 0;
			boolean checkedVertex[] = new boolean [n];
			List<Integer> queue = new ArrayList<Integer>();
			List<Integer> branch = new ArrayList<Integer>();
			int currentVertex = -1;
			for (int i =0; i<n;i++){
				checkedVertex[i] = false; 
			}
			boolean cycleFound = false;
			
			for (int e1 : set1){
				cycleFound = true;//just for the start
				
				while(cycleFound){
					queue = new ArrayList<Integer>();
					branch = new ArrayList<Integer>();
					currentVertex = -1;
					for (int i =0; i<n;i++){
						checkedVertex[i] = false; 
					}
					cycleFound = false;//no cycles at the beginning, if one found, continue searching
					
					if (!checkedVertex[e1-1]){
						queue.add(e1);
						//System.out.println(queue.size());
						while (!queue.isEmpty()){
							currentVertex = queue.get(queue.size() -1);
							queue.remove(queue.size() -1);
							
							if (currentVertex== -1){
								branch.remove(branch.size()-1);
							}else{
								if (checkedVertex[currentVertex-1]){
									//nothing
								}else{
									branch.add(currentVertex);
									checkedVertex[currentVertex-1] = true;
									queue.add(-1);
									for (int e2 : set1){
										if (poids[currentVertex-1][e2-1] > 0 && !cycleFound){
											if (checkedVertex[e2-1]){
												//no add
												if (branch.contains(e2)){
													double minArc = 9999999.0;
													for(int i = branch.indexOf(e2); i < branch.size()-1; i++){
														if (poids[branch.get(i)-1][branch.get(i+1)-1] < minArc){
															minArc = poids[branch.get(i)-1][branch.get(i+1)-1];
														}
													}
													if (poids[branch.get(branch.size()-1)-1][branch.get(branch.indexOf(e2))-1] < minArc){
														minArc = poids[branch.get(branch.size()-1)-1][branch.get(branch.indexOf(e2))-1];
													}
													
													System.out.println("cycle! : " + branch + " "+ e2 + " ("+minArc+")");
													
													if (minArc > 0){
														numberOfCycles++;
														//System.out.println("cycle! : " + branch + " "+ e2 + " ("+minArc+")");
														addCycles += minArc;
													}
													
													
													for(int i = branch.indexOf(e2); i < branch.size()-1; i++){
														poids[branch.get(i)-1][branch.get(i+1)-1] -= minArc;
													}
													poids[branch.get(branch.size()-1)-1][branch.get(branch.indexOf(e2))-1] -= minArc;
													
													
													queue.clear();
													branch.clear();
													cycleFound = true;
												}
											}else{
												queue.add(e2);
											}
										}
										
										
									}
								}
							}
							//System.out.println(queue.size());
						}
					}
				}
			
			}
			borneInf += addCycles;
			System.out.println(" " +numberOfCycles+ " cycles,  borneInf augmentee de " + addCycles);
		
			System.out.println("borne reelle: " +borneInf);
			
			int result = (int)Math.ceil(borneInf);
			
			myInstance.setLowerBound(result);
			
			return result; 
		}
	

	//methode qui retourne une borneInf sur un set d'elements utilisant la methode de Conitzer simplifie
	private static int borneInfSet(Instance myInstance, Set<Integer> set1,  int max) {
		int borneInf =0;
		int min = 0;
		int n = myInstance.n;
		
		if (set1 == null){
			set1 = new HashSet<Integer>();
			for (int i =1; i <= n; i++)
				set1.add(i);
		}
		
		
		
		//borneInf2 simplifiee de Conitzer
		int[][] poids = new int [n][n];
		for (int i : set1){//la 1ere borneInf
			for (int j : set1){
				if (i < j){
					borneInf += Math.min(myInstance.tabD[i-1][j-1],myInstance.tabD[j-1][i-1]);
				}
			}
		}
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				if (myInstance.tabD[i-1][j-1] < myInstance.tabD[j-1][i-1]){// i < j
					poids[i-1][j-1] = myInstance.tabD[j-1][i-1] - myInstance.tabD[i-1][j-1] ;
				}else{
					poids[i-1][j-1] = 0;
				}
			}
		}
		if (max >= 3){
			for (int i : set1){
				for (int j : set1){
					for (int k : set1){
						if (i != j && j != k && k != i){
							if ((poids[i-1][j-1] > 0) && (poids[j-1][k-1] > 0) && (poids[k-1][i-1] > 0)){
								min = Math.min(poids[i-1][j-1],Math.min(poids[j-1][k-1],poids[k-1][i-1]));
								poids[i-1][j-1] -= min;
								poids[j-1][k-1] -= min;
								poids[k-1][i-1] -= min;
								borneInf += min;
							}
						}
					}
				}
			}
		}
		if (max >= 4){
			for (int e1 : set1){
				for (int e2 : set1){
					for (int e3 : set1){
						for (int e4 : set1){
							if ((poids[e1-1][e2-1] > 0) && (poids[e2-1][e3-1] > 0) && (poids[e3-1][e4-1] > 0)&& (poids[e4-1][e1-1] > 0)){
								min = Math.min(Math.min(poids[e1-1][e2-1],poids[e2-1][e3-1]),Math.min(poids[e3-1][e4-1],poids[e4-1][e1-1]));
								poids[e1-1][e2-1] -= min;
								poids[e2-1][e3-1] -= min;
								poids[e3-1][e4-1] -= min;
								poids[e4-1][e1-1] -= min;
								borneInf += min;
							}
						}
					}
				}
			}
		}
		
		myInstance.setLowerBound(borneInf);
		return borneInf;
		
	}
	
	//methode qui retourne une borneInf sur un set d'elements utilisant la methode de Conitzer simplifie
	private static int borneInfSet_maxFlow(Instance myInstance, Set<Integer> set1) {
		int borneInf =0;
		int min = 0;
		int n = myInstance.n;
		boolean useConstraints = true;
		
		if (set1 == null){
			set1 = new HashSet<Integer>();
			for (int i =1; i <= n; i++)
				set1.add(i);
		}
		
		
		//copy the constraints
		if (useConstraints){
			//System.out.println("copy the constraints");
			myInstance.tabCprime = new boolean[n][n];
			for (int i = 1; i <= n; i ++){
				for (int j = 1; j <= n; j ++){
					myInstance.tabCprime[i-1][j-1]=myInstance.tabC[i-1][j-1];
				}
			}
		}
		
		
		//borneInf2 de Conitzer
		int[][] poids = new int [n][n];
		int[][] flow = new int [n][n];
		
		for (int i : set1){//la 1ere borneInf
			for (int j : set1){
				if (i < j){
					borneInf += Math.min(myInstance.tabD[i-1][j-1],myInstance.tabD[j-1][i-1]);
				}
			}
		}
		
		
		
		
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				if (myInstance.tabD[i-1][j-1] < myInstance.tabD[j-1][i-1]){// i < j
					poids[i-1][j-1] = myInstance.tabD[j-1][i-1] - myInstance.tabD[i-1][j-1] ;
				}else{
					poids[i-1][j-1] = 0;
				}
				flow[i-1][j-1] = 0;
			}
		}
		
		if (useConstraints){
			for (int i : set1){
				for (int j : set1){
					if (myInstance.tabCprime[i-1][j-1]){
						borneInf += poids[j-1][i-1];
						poids[j-1][i-1] = 0;
					}
				}
			}
		}
		
		
		
		List<Integer> queue = new ArrayList<Integer>();
		List<Integer> path = new ArrayList<Integer>();
		int currentVertex = -1;
		int e1 = 0;
		boolean toContinue = true;
		int flowTotal = 0;
		int allowedSize = 4;

		for (int i : set1){
			flowTotal = 0;
			
			for (int j : set1){
				for (int k : set1){
					if (i != j && j != k && k != i){
						if ((poids[i-1][j-1]-flow[i-1][j-1] > 0) && (poids[j-1][k-1]-flow[j-1][k-1] > 0) && (poids[k-1][i-1]-flow[k-1][i-1] > 0)){
							min = Math.min(poids[i-1][j-1]-flow[i-1][j-1],Math.min(poids[j-1][k-1]-flow[j-1][k-1],poids[k-1][i-1]-flow[k-1][i-1]));
							//poids[i-1][j-1] -= min;
							//poids[j-1][k-1] -= min;
							//poids[k-1][i-1] -= min;
							//borneInf += min;
							
							//the direct flow
							flow[i-1][j-1] += min;
							flow[j-1][k-1] += min;
							flow[k-1][i-1] += min;
							
							//the reverse flow
							//flow[j-1][i-1] -= min;
							//flow[k-1][j-1] -= min;
							//flow[i-1][k-1] -= min;
							
							//flowTotal += min;
						}
					}
				}
			}
			
			/*for (int j : set1){
				for (int k : set1){
					if (j != k){
						if(flow[j-1][k-1] > 0){
							flow[k-1][j-1] -= flow[j-1][k-1];
						}
					}
				}
			}*/
			
			
			
			
			
			
			//ford-fulkerson max flow algorithm (mini version)
			toContinue = true;
			allowedSize = 4;

			while (toContinue){
				toContinue = false;
				
				e1 =i;
				currentVertex = -1;
				queue = new ArrayList<Integer>();
				path = new ArrayList<Integer>();
	
			
				queue.add(e1);
				//System.out.println(queue.size());
				while (!queue.isEmpty()){
					currentVertex = queue.get(queue.size() -1);
					queue.remove(queue.size() -1);
					
					if (currentVertex== -1){
						path.remove(path.size()-1);
					}else{
						path.add(currentVertex);
						queue.add(-1);
						for (int e2 : set1){
							if (!toContinue){
								if (currentVertex == e1){
									if ((poids[currentVertex-1][e2-1] - flow[currentVertex-1][e2-1]) > 0){
										queue.add(e2);
									}
								}else if (e2 == e1){
									if((poids[currentVertex-1][e2-1] - flow[currentVertex-1][e2-1]) > 0){
										//new path: better flow!
										path.add(e1);
										
										//calcultate minimum edge on that path
										min = 9999999;
										for(int ii = 0; ii < path.size()-1; ii++){
											if ((poids[path.get(ii)-1][path.get(ii+1)-1] - flow[path.get(ii)-1][path.get(ii+1)-1]+flow[path.get(ii+1)-1][path.get(ii)-1]) < min){
												min = (poids[path.get(ii)-1][path.get(ii+1)-1] - flow[path.get(ii)-1][path.get(ii+1)-1]+flow[path.get(ii+1)-1][path.get(ii)-1]);
											}
										}
										//if ((poids[path.get(path.size()-1)-1][e1-1] - flow[path.get(path.size()-1)-1][e1-1]+flow[e1-1][path.get(path.size()-1)-1]) < min){
										//	min = (poids[path.get(path.size()-1)-1][e1-1] - flow[path.get(path.size()-1)-1][e1-1]+flow[e1-1][path.get(path.size()-1)-1]);
										//}
										
										//trow minimal flow on the path
										for(int ii = 0; ii < path.size()-1; ii++){
											flow[path.get(ii)-1][path.get(ii+1)-1] += min;
											//flow[path.get(ii+1)-1][path.get(ii)-1] -= min;
											if (flow[path.get(ii)-1][path.get(ii+1)-1] > 0 && flow[path.get(ii)-1][path.get(ii+1)-1] > 0){
												int minBothEdges = Math.min(flow[path.get(ii)-1][path.get(ii+1)-1], flow[path.get(ii+1)-1][path.get(ii)-1]);
												flow[path.get(ii)-1][path.get(ii+1)-1] -= minBothEdges; 
												flow[path.get(ii+1)-1][path.get(ii)-1] -= minBothEdges; 
											}
										}
										//flow[path.get(path.size()-1)-1][e1-1] += min;
										//flow[e1-1][path.get(path.size()-1)-1] -= min;
										

										//better flow!
										if (min > 0){
											//path.add(e1);//pour affichage
											//System.out.println("better flow! " + min + " " + path);
											toContinue = true;
											queue.clear();
											path.clear();
											//flowTotal += min;
										}
									}
									
								}else if ((poids[currentVertex-1][e2-1] - flow[currentVertex-1][e2-1] + flow[e2-1][currentVertex-1]) > 0){
									if (path.size() < allowedSize){
										if (!path.contains(e2)){
												queue.add(e2);
										}
									}

								}
							}
							
						}
						
					}
				}
				
				//if ((!toContinue) && (allowedSize<=5)){
				//	toContinue = true;
				//	allowedSize++;
				//}
					
				
			}	
			
			
			
			
			
			
			
			for (int j : set1){
				if (j != i){
					if(flow[i-1][j-1] > 0){
						flowTotal += flow[i-1][j-1];
					}
				}
			}
			
			
			for (int j : set1){
				for (int k : set1){
					if (j != k){
						if(flow[j-1][k-1] > 0){
							poids[j-1][k-1] -= flow[j-1][k-1];
						}
						flow[j-1][k-1] = 0;
					}
				}
			}
			
			
			
			
			
			borneInf += flowTotal;
		}
		
		
		myInstance.setLowerBound(borneInf);
		return borneInf;
		
	}
	
	//methode qui retourne une borneInf sur un set d'elements utilisant la methode de Conitzer simplifie
	private static int borneInfSetRand(Instance myInstance, Set<Integer> set1,  int numberOfIterations) {
		int borneInfFinal =0;
		int n = myInstance.n;
		
		for (int counter =1; counter <= numberOfIterations; counter++){
			Permutation rand = null;
			if (counter ==1){
				rand = createIndentite(n);
			}else{
				rand = myInstance.createARandom(n);
			}
			
			System.out.print(rand + "  ");
		
			int borneInf =0;
			int min = 0;
			
			
			if (set1 == null){
				set1 = new HashSet<Integer>();
				for (int i =1; i <= n; i++)
					set1.add(i);
			}
			
			//borneInf simplifiee de Conitzer
			int[][] poids = new int [n][n];
			for (int i : set1){//la 1ere borneInf
				for (int j : set1){
					if (i < j){
						borneInf += Math.min(myInstance.tabD[i-1][j-1],myInstance.tabD[j-1][i-1]);
					}
				}
			}
			for (int i = 1; i<= n; i++){
				for (int j = 1; j<= n; j++){
					if (myInstance.tabD[i-1][j-1] < myInstance.tabD[j-1][i-1]){// i < j
						poids[i-1][j-1] = myInstance.tabD[j-1][i-1] - myInstance.tabD[i-1][j-1] ;
					}else{
						poids[i-1][j-1] = 0;
					}
				}
			}
			
		
			for (int i : set1){
				for (int j : set1){
					for (int k : set1){
						if (i != j && j != k && k != i){
							if ((poids[rand.getTab()[i-1]-1][rand.getTab()[j-1]-1] > 0) && (poids[rand.getTab()[j-1]-1][rand.getTab()[k-1]-1] > 0) && (poids[rand.getTab()[k-1]-1][rand.getTab()[i-1]-1] > 0)){
								min = Math.min(poids[rand.getTab()[i-1]-1][rand.getTab()[j-1]-1],Math.min(poids[rand.getTab()[j-1]-1][rand.getTab()[k-1]-1],poids[rand.getTab()[k-1]-1][rand.getTab()[i-1]-1]));
								poids[rand.getTab()[i-1]-1][rand.getTab()[j-1]-1] -= min;
								poids[rand.getTab()[j-1]-1][rand.getTab()[k-1]-1] -= min;
								poids[rand.getTab()[k-1]-1][rand.getTab()[i-1]-1] -= min;
								borneInf += min;
							}
						}
					}
				}
			}
			
			
			
			
			
			
			
			
			/*
			
			//check for cycles and add to lowerbound if found
			int addCycles = 0;
			int numberOfCycles = 0;
			boolean checkedVertex[] = new boolean [n];
			List<Integer> queue = new ArrayList<Integer>();
			List<Integer> branch = new ArrayList<Integer>();
			int currentVertex = -1;
			for (int i =0; i<n;i++){
				checkedVertex[i] = false; 
			}
			
			for (int e1 : set1){
				
				queue = new ArrayList<Integer>();
				branch = new ArrayList<Integer>();
				currentVertex = -1;
				for (int i =0; i<n;i++){
					checkedVertex[i] = false; 
				}
				
				
				if (!checkedVertex[e1-1]){
					queue.add(e1);
					//System.out.println(queue.size());
					while (!queue.isEmpty()){
						currentVertex = queue.get(queue.size() -1);
						queue.remove(queue.size() -1);
						
						if (currentVertex== -1){
							branch.remove(branch.size()-1);
						}else{
							if (checkedVertex[currentVertex-1]){
								//nothing
							}else{
								branch.add(currentVertex);
								checkedVertex[currentVertex-1] = true;
								queue.add(-1);
								for (int e2 : set1){
									if (poids[currentVertex-1][e2-1] > 0){
										if (checkedVertex[e2-1]){
											//no add
											if (branch.contains(e2)){
												int minArc = 9999999;
												for(int i = branch.indexOf(e2); i < branch.size()-1; i++){
													if (poids[branch.get(i)-1][branch.get(i+1)-1] < minArc){
														minArc = poids[branch.get(i)-1][branch.get(i+1)-1];
													}
												}
												if (poids[branch.get(branch.size()-1)-1][branch.get(branch.indexOf(e2))-1] < minArc){
													minArc = poids[branch.get(branch.size()-1)-1][branch.get(branch.indexOf(e2))-1];
												}
												
												//System.out.println("cycle! : " + branch + " "+ e2 + " ("+minArc+")");
												
												if (minArc > 0) numberOfCycles++;
												addCycles += minArc;
												//System.out.println("borneInf augmentee de " + minArc);
												
												for(int i = branch.indexOf(e2); i < branch.size()-1; i++){
													poids[branch.get(i)-1][branch.get(i+1)-1] -= minArc;
												}
												poids[branch.get(branch.size()-1)-1][branch.get(branch.indexOf(e2))-1] -= minArc;
											}
										}else{
											queue.add(e2);
										}
									}
									
									
								}
							}
						}
						//System.out.println(queue.size());
					}
				}
			
			}
			borneInf += addCycles;
			//System.out.println(" " +numberOfCycles+ " cycles,  borneInf augmentee de " + addCycles);
			
			*/
			
			
			
			
			
			
			
			
			System.out.print(borneInf + " \n");
			
			if (borneInf > borneInfFinal){
				borneInfFinal = borneInf;
				myInstance.lowerBoundBestOrderOfExploration = new Permutation(rand.getTab());
			}
		
		}
		
		myInstance.setLowerBound(borneInfFinal);
		return borneInfFinal;
		
	}
	
	
	
	
	
	
	//new method to generate triangles for better lower bound on elements to place using Conitzer simplified method
	//joint cycles
	private static int add3CyclesToLowerBound(Instance myInstance, boolean verbose) {
		int n=myInstance.n;//pickARandom(set1).getSize();
		//tabD = creationTabD(set1);
		
		int[][] segmentWeight = new int [n][n];
		myInstance.tabTriangleAdd = new int [n][n][n];
		//tabTriangleAssocie = new int [n][n];
		myInstance.tabNbTriangleAssocie = new int [n][n];
		myInstance.tabTriangleAssocie = new ArrayList[n][n];
		myInstance.apport = new int[n];
		
		int borneInfAdd =0;
		
		
		int score = 0;
		int addScore = 0;
		int min = 0;
		
		
		
		//using guidance
		boolean useGuidance = true;
		Permutation rand = null;
		if (useGuidance){
			if (myInstance.lowerBoundBestOrderOfExploration != null){
				rand = new Permutation(myInstance.lowerBoundBestOrderOfExploration.getTab());
			}else{
				rand = createIndentite(n);
			}
			
		}
		
		
		
		
		//initialization
		for (int i = 1; i<= n; i++){
			myInstance.apport[i-1] = 0;
		}
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				segmentWeight[i-1][j-1] = 0;
				//tabTriangleAssocie[i-1][j-1] = 0;
				myInstance.tabNbTriangleAssocie[i-1][j-1] = 0;
				myInstance.tabTriangleAssocie[i-1][j-1] = new ArrayList<Integer>();
			}
		}
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				for (int k = 1; k<= n; k++){
					myInstance.tabTriangleAdd[i-1][j-1][k-1] = 0;
				}
			}
		}
		//creating graph : segments of weight = differece of orders
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				if (myInstance.tabD[i-1][j-1] < myInstance.tabD[j-1][i-1]){// i < j
					segmentWeight[i-1][j-1] = myInstance.tabD[j-1][i-1] - myInstance.tabD[i-1][j-1] ;
				}else{
					segmentWeight[i-1][j-1] = 0;
				}
			}
		}
		
		int i = 0;
		int j = 0;
		int k = 0;
		
		for (int i2 = 1; i2<= n; i2++){
			for (int j2 = 1; j2<= n; j2++){
				for (int k2 = 1; k2<= n; k2++){
					
					if (useGuidance){
						i = rand.getTab()[i2-1];
						j = rand.getTab()[j2-1];
						k = rand.getTab()[k2-1];
					}else{
						i = i2;
						j = j2;
						k = k2;
					}
					
					
					if ((segmentWeight[i-1][j-1] > 0) && (segmentWeight[j-1][k-1] > 0) && (segmentWeight[k-1][i-1] > 0)){
						min = Math.min(segmentWeight[i-1][j-1],Math.min(segmentWeight[j-1][k-1],segmentWeight[k-1][i-1]));
						segmentWeight[i-1][j-1] -= min;
						segmentWeight[j-1][k-1] -= min;
						segmentWeight[k-1][i-1] -= min;
						if (min > 0){
							
							borneInfAdd += min;
							
							myInstance.tabNbTriangleAssocie[i-1][j-1] += 1;
							myInstance.tabNbTriangleAssocie[j-1][k-1] += 1;
							myInstance.tabNbTriangleAssocie[k-1][i-1] += 1;
							myInstance.tabNbTriangleAssocie[j-1][i-1] += 1;
							myInstance.tabNbTriangleAssocie[k-1][j-1] += 1;
							myInstance.tabNbTriangleAssocie[i-1][k-1] += 1;
							
							myInstance.tabTriangleAssocie[i-1][j-1].add(k);
							myInstance.tabTriangleAssocie[j-1][k-1].add(i);
							myInstance.tabTriangleAssocie[k-1][i-1].add(j);
							myInstance.tabTriangleAssocie[j-1][i-1].add(k);
							myInstance.tabTriangleAssocie[k-1][j-1].add(i);
							myInstance.tabTriangleAssocie[i-1][k-1].add(j);
							
							/*tabTriangleAssocie[i-1][j-1] = k;
							tabTriangleAssocie[j-1][k-1] = i;
							tabTriangleAssocie[k-1][i-1] = j;
							tabTriangleAssocie[j-1][i-1] = k;
							tabTriangleAssocie[k-1][j-1] = i;
							tabTriangleAssocie[i-1][k-1] = j;*/
							
							myInstance.tabTriangleAdd[i-1][j-1][k-1] = min;
							myInstance.tabTriangleAdd[i-1][k-1][j-1] = min;
							myInstance.tabTriangleAdd[j-1][i-1][k-1] = min;
							myInstance.tabTriangleAdd[j-1][k-1][i-1] = min;
							myInstance.tabTriangleAdd[k-1][i-1][j-1] = min;
							myInstance.tabTriangleAdd[k-1][j-1][i-1] = min;
							
							myInstance.apport[i-1] += min;
							myInstance.apport[j-1] += min;
							myInstance.apport[k-1] += min;
							
							if (verbose) System.out.println(i+ " " + j + " " + k + " : " + min);
						}
					}

				}
			}
		}

		if (verbose) System.out.println("Apports");
		for (int i2 = 1; i2<= n; i2++){
			if (verbose) System.out.println(i2+" : "+myInstance.apport[i2-1]);
		}
		
		//affichage .dot
		/*System.out.println();
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				if (segmentWeight[i-1][j-1] > 0){
					System.out.println(i+ " -> " + j + "[label="+segmentWeight[i-1][j-1]+"];");
				}
			}
		}
		System.out.println();*/
		
		
		/*
		int borneInfAdd2 =0;
		int[][] poids = new int [n][n];//borneInfAdd2
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				if (tabD[i-1][j-1] < tabD[j-1][i-1]){// i < j
					poids[i-1][j-1] = tabD[j-1][i-1] - tabD[i-1][j-1] ;
				}else{
					poids[i-1][j-1] = 0;
				}
			}
		}
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				for (int k = 1; k<= n; k++){
					if ((poids[i-1][j-1] > 0) && (poids[j-1][k-1] > 0) && (poids[k-1][i-1] > 0)){
						min = Math.min(poids[i-1][j-1],Math.min(poids[j-1][k-1],poids[k-1][i-1]));
						poids[i-1][j-1] -= min;
						poids[j-1][k-1] -= min;
						poids[k-1][i-1] -= min;
						borneInfAdd2 += min;
					}
				}
			}
		}
		System.out.println("borneInfAdd " + borneInfAdd + ", borneInfAdd2 " + borneInfAdd2);
		for (int e1 = 1; e1<= n; e1++){
			for (int e2 = 1; e2<= n; e2++){
				for (int e3 = 1; e3<= n; e3++){
					for (int e4 = 1; e4<= n; e4++){
						if ((poids[e1-1][e2-1] > 0) && (poids[e2-1][e3-1] > 0) && (poids[e3-1][e4-1] > 0)&& (poids[e4-1][e1-1] > 0)){
							min = Math.min(Math.min(poids[e1-1][e2-1],poids[e2-1][e3-1]),Math.min(poids[e3-1][e4-1],poids[e4-1][e1-1]));
							poids[e1-1][e2-1] -= min;
							poids[e2-1][e3-1] -= min;
							poids[e3-1][e4-1] -= min;
							poids[e4-1][e1-1] -= min;
							borneInfAdd2 += min;
						}
					}
				}
			}
		}
		System.out.println("borneInfAdd " + borneInfAdd + ", borneInfAdd2 " + borneInfAdd2);
		*/
		return borneInfAdd;
		
		
	}
	
	public static void testElectionsBetzler(){
		String s = "";
		Instance myInstance;
		//int n =0;
		for (int i = 1970; i <= 2008; i++){
			s = "elections/sport_F1_"+i+".election";
			//A=convertElectionsToPermutations(s,0);
			//myInstance = new Instance(A);
			myInstance = new Instance();
			myInstance.convertElectionsToPermutations(s);
			System.out.println(s);
			//System.out.println(avgDist(A));
			//thm_major_3_0_revised_count = 0.0;
			//MOT3(null,A,false, false, true, false,-1);//*modified inst
			MOT3_LUBC(myInstance,false, false, true, false);//*modified inst
			//thm_major_3_0_revised_count *= 100;
			//thm_major_3_0_revised_count /= 100;
			//System.out.println(thm_major_3_0_revised_count);
		}
		
		
		
	    //mesure du temp
	  		lEndTime = new Date().getTime(); //end time
	  	    difference = (lEndTime - lStartTime); //check different
	  	    System.out.println("Temps pour calculer: " +(difference/60000) + " minutes " + ((difference % 60000)/1000) + " secondes " + (difference % 1000) + " millisecondes");
	  	    lStartTime = new Date().getTime();
	}
	
	public static double avgDist (Set <Permutation> U){
		double reponse = 0.0;
		double compteur =0.0;
		double n = pickARandom(U).getSize();
		for (Permutation pi : U){
			for (Permutation pi2 : U){
				if (pi != pi2){
					reponse += pi.distanceTo(pi2);
					compteur += 1.0;
				}
			}
		}
		reponse /= compteur;
		reponse /= ((n*n-(1.0))/2.0);
		
		return reponse;
		
	}
	
	
	
	
	//methode pour tester le nombre de sous-arrangements qui ne rentrent pas en contradiction avec un optimal
	public static void testTestOptimal(){
		//Set<Permutation> restrict = new HashSet<Permutation>();
		Set<Permutation> trash = new HashSet<Permutation>();
		creerSn(7);
		printSn();
		for (Permutation pi : Sn){
			if (contientAntiOptPattern(pi)){
				trash.add(pi);
			}
				
		}
		Sn.removeAll(trash);
		printSn();
		
	}
	public static boolean contientAntiOptPattern(Permutation pi){
		SortedSet<Integer> onTest = new TreeSet<Integer>();
		int n = pi.getSize();
		for (int i = 2; i<= n-1; i++){//size of frame
			for (int j = 1; j<= n-i+1; j++){//start position
				onTest.clear();
				for (int k = j; k<= j+i-1; k++){//checking all the element of the frame
					onTest.add(pi.getTab()[k-1]);
				}
				if ((onTest.last()-onTest.first()) == onTest.size() -1 ){
					//on cheque ici
					for (int k = j; k<= j+i-1-1; k++){//checking all the element of the frame
						if (pi.getTab()[k-1]+1!=pi.getTab()[k+1-1]){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	

	
	public static void duos(Instance myInstance, boolean verbose){
		int n;//ordre des permutations
		int score=0;
		int nbDuos=0;
		//n=pickARandom(a).getSize();
		//creerTabD(a);
				
		n=myInstance.n;

		myInstance.tabDuos = new boolean [n][n];
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
					if (i!=j){
						myInstance.tabDuos[i-1][j-1]=true;
						score=myInstance.tabD[i-1][j-1];// i j
						if (myInstance.tabD[j-1][i-1]< score) myInstance.tabDuos[i-1][j-1]=false;// j i
	
						if (myInstance.tabDuos[i-1][j-1]) nbDuos++;
						//tabTriplets[i-1][j-1][k-1]=true;
					}else{
						myInstance.tabDuos[i-1][j-1]=false;
					}
			}
		}
		if (verbose) System.out.println("nb of duos: "+nbDuos+"/"+(n*(n-1)) + "  ("+df.format(((double)nbDuos*100.0/(double)(n*(n-1))))+"%)");
	}
	
	
	//if ijk is an allowed triplet
	public static void triplets(Instance myInstance, boolean verbose){
		int n;//ordre des permutations
		int score=0;
		int nbTriplets=0;
		//n=pickARandom(a).getSize();
		//creerTabD(a);
		
		n=myInstance.n;
		
		myInstance.tabTriplets = new boolean [n][n][n];
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				for (int k = 1; k<= n; k++){
					if (i!=j && i!=k && j!=k){
						myInstance.tabTriplets[i-1][j-1][k-1]=true;
						score=myInstance.tabD[i-1][j-1]+myInstance.tabD[i-1][k-1]+myInstance.tabD[j-1][k-1];// i j k
						
						if (myInstance.tabD[i-1][k-1]+myInstance.tabD[i-1][j-1]+myInstance.tabD[k-1][j-1] < score) myInstance.tabTriplets[i-1][j-1][k-1]=false;// i k j
						if (myInstance.tabD[j-1][i-1]+myInstance.tabD[j-1][k-1]+myInstance.tabD[i-1][k-1] < score) myInstance.tabTriplets[i-1][j-1][k-1]=false;// j i k
						if (myInstance.tabD[j-1][k-1]+myInstance.tabD[j-1][i-1]+myInstance.tabD[k-1][i-1] < score) myInstance.tabTriplets[i-1][j-1][k-1]=false;// j k i
						if (myInstance.tabD[k-1][i-1]+myInstance.tabD[k-1][j-1]+myInstance.tabD[i-1][j-1] < score) myInstance.tabTriplets[i-1][j-1][k-1]=false;// k i j
						if (myInstance.tabD[k-1][j-1]+myInstance.tabD[k-1][i-1]+myInstance.tabD[j-1][i-1] < score) myInstance.tabTriplets[i-1][j-1][k-1]=false;// k j i
						
						if (myInstance.tabTriplets[i-1][j-1][k-1]) nbTriplets++;
					}else{
						myInstance.tabTriplets[i-1][j-1][k-1]=false;
					}
				}
			}
		}
		
		/*for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				for (int k = 1; k<= n; k++){
					if (i!=j && i!=k && j!=k){
						if (myInstance.tabTriplets[i-1][j-1][k-1]){
							System.out.println(i+" "+j+" "+k+"  o");
						}else{
							System.out.println(i+" "+j+" "+k+"  x");
						}
						
					}
				}
			}
		}*/
		
		if (verbose) System.out.println("nb of triplets: "+nbTriplets+"/"+(n*(n-1)*(n-2)) + "  ("+df.format(((double)nbTriplets*100.0/(double)(n*(n-1)*(n-2))))+"%)");
	}
	

	//public static void MOT3 (Instance myInstance, Set<Permutation> a, boolean verboseDetails, boolean verbosePairs, boolean verboseStats, boolean egalite, int approximation){
	public static void MOT3_LUBC (Instance myInstance, boolean verboseDetails, boolean verbosePairs, boolean verboseStats, boolean egalite){
		int n;//ordre des permutations
		int iteration =0;
		double resolution_exacte = 0.0;
		boolean verboseSymbols = false;
		
		n=myInstance.n;
		//n=pickARandom(a).getSize();
		//creerTabD(a);
		
		myInstance.tabC = new boolean [n][n];
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				myInstance.tabC[i-1][j-1]=false;
			}
		}
		
		
		if (verboseDetails) System.out.println("");
		if (verboseDetails) System.out.println(" *** Evaluation du Major Order Theorem 3.0 mat ***");
		if (verboseDetails) System.out.println("");
		if (verbosePairs) System.out.println("");
		if (verbosePairs) System.out.println(" **MOT3.0 mat");
		
		
		
		//stats
		int add_fermeture_contraintes = 0;//fermeture
		int thm_always_count = 0;
		int constraints_count = 0;
		
		int thm_major_3_0e_count = 0;
		int LUBC_count = 0;
		int LUBC_LP_count = 0;
		int thm_major_3_0pp_count = 0;

		
		
		//thm tjrs
		for (int element1 = 1; element1<=n; element1++){
			for (int element2 = 1; element2<=n; element2++){
				if (myInstance.tabD[element1-1][element2-1] < myInstance.tabD[element2-1][element1-1]){//si element1 est maj avant element2
					if (myInstance.tabD[element1-1][element2-1] == 0){ 
						myInstance.tabC[element1-1][element2-1]=true;
						constraints_count++;
						thm_always_count++;
						if (verboseDetails) System.out.println(element1 + ":" + element2);// element1:element2
						if (verboseDetails) System.out.println("\t Thm major 3.0("+iteration+")  :  ordre majoritaire respecte " + element1 + " " + element2);
						if (verbosePairs) System.out.println("thm major 3.0("+iteration+") " + element1 + ":" + element2);
					}
				}//fin if maj
			}//fin boucle 2
		}//fin boucle 1
		resolution_exacte = 100.0*(constraints_count)/(n*(n-1)/2);
		if (verboseStats) System.out.println("---");
		if (verboseStats) System.out.println("always thm: " + (constraints_count)+ "/" + (n*(n-1)/2)
				+ " ("+df.format(resolution_exacte)+"%)");
		//fin thm tjrs
		
		
		//*****************************************MOT3.0
		myInstance.toCheck = new boolean [n][n];
		myInstance.tabDelta = new int[n][n];
		myInstance.tabDELTA = new int[n][n][n];
		myInstance.tabMaxDELTA = new int[n][n];
		
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				myInstance.toCheck[i-1][j-1]=false;
			}
		}
		
		//creation de la table de difference d'ordre
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				myInstance.tabDelta[i-1][j-1]=myInstance.tabD[j-1][i-1]-myInstance.tabD[i-1][j-1];//si >0 alors ij major order
			}
		}
		
		//creation de la table difference des vecteurs Ri et Rj
		int tmp =0;
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				for (int k = 1; k<= n; k++){
					tmp = myInstance.tabD[i-1][k-1]-myInstance.tabD[j-1][k-1];
					//if (tmp < 0) tmp =0;
					if (k==i || k==j) tmp =0;
					myInstance.tabDELTA[i-1][j-1][k-1] = tmp;
				}
			}
		}
		
		//creation de la table des pire max des sous-vecteurs de Delta
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				myInstance.tabMaxDELTA[i-1][j-1]=0;
				for (int k = 1; k<= n; k++){
					if (myInstance.tabDELTA[i-1][j-1][k-1] >=0)
						myInstance.tabMaxDELTA[i-1][j-1] += myInstance.tabDELTA[i-1][j-1][k-1];
				}
				
			}
		}
		
		//tabC[5-1][1-1] = true; thm_major_4_0_count++;
		
		//patch: update des vecteurs delta du aux contraintes always
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				if (myInstance.tabC[i-1][j-1])
					MOT3_LUBC_update_vecteurs_DELTA(myInstance,i, j);
			}
		}
		
		
		int valuesOfApproxPair[][] = new int[n][n];
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				valuesOfApproxPair[i-1][j-1]=0;	
			}
		}
		
		
		//for the stats
		boolean check1 = false;
		boolean check2 = false;
		boolean check3 = false;

		boolean amelioration = true;
		while (amelioration){//boucle tant qu'il y a des nouvelles contraintes qui apparaissent
			iteration++;
			//System.out.println(" "+tabDELTA[1-1][4-1][1-1]+" "+tabDELTA[1-1][4-1][2-1]+" "+tabDELTA[1-1][4-1][3-1]+" "+tabDELTA[1-1][4-1][4-1]+" "+tabDELTA[1-1][4-1][5-1]+" "+tabDELTA[1-1][4-1][6-1]);
			//System.out.println(" "+tabMaxDELTA[1-1][4-1]);
			//System.out.println(" "+tabC[1-1][2-1]);
			/*for (int i = 1; i<= n; i++){
				System.out.print(" "+tabD[2-1][i-1]);
			}
			System.out.println(" ");
			for (int i = 1; i<= n; i++){
				System.out.print(" "+tabD[5-1][i-1]);
			}*/
			/*int x = 14;
			int y = 12;
			System.out.println(" ");
			for (int i = 1; i<= n; i++){
				System.out.print(" "+tabDELTA[x-1][y-1][i-1]);
			}
			System.out.println(" ");
			System.out.println(" "+tabMaxDELTA[x-1][y-1]);*/
			
			
			
			amelioration = false;
			
			//1 (MOT3)
			for (int i = 1; i<= n; i++){
				for (int j = 1; j<= n; j++){
					
					if (myInstance.tabDelta[i-1][j-1] >= 0 && !myInstance.tabC[i-1][j-1] && !myInstance.tabC[j-1][i-1] && i!=j){//si ij est l'ordre majeur et qu'il n'y pas de contrainte pour cette paire
						if (verboseDetails) System.out.println(i + ":" + j);// element1:element2
						if (myInstance.tabMaxDELTA[i-1][j-1] < myInstance.tabDelta[i-1][j-1]){
							myInstance.tabC[i-1][j-1]=true;
							constraints_count++;
							amelioration = true;
							if (verboseDetails) System.out.println("\t Thm major 3.0("+iteration+")  :  ordre majoritaire respecte " + i + " " + j);
							if (verbosePairs) System.out.println("thm major 3.0("+iteration+") " + i + ":" + j);
							//update des vecteurs DELTA
							MOT3_LUBC_update_vecteurs_DELTA(myInstance,i, j);
							if (verboseSymbols) System.out.print(".");
							
							//debut bloc fermeture
							add_fermeture_contraintes=0;
							add_fermeture_contraintes = MOT3_LUBC_fermeture_contraintes_matriciel_newPair(myInstance,i,j,verboseDetails,verbosePairs);
							constraints_count+=add_fermeture_contraintes;
							//fin bloc fermeture
							
						}else if ((myInstance.tabMaxDELTA[i-1][j-1] == myInstance.tabDelta[i-1][j-1]) && (egalite)){
							myInstance.tabC[i-1][j-1]=true;
							constraints_count++;
							amelioration = true;
							if (myInstance.tabDelta[i-1][j-1]==0){
								if (verboseDetails) System.out.println("\t Thm major 3.0("+iteration+")  :  ordre majoritaire respecte " + i + " " + j + " [=]  d=0");
								if (verbosePairs) System.out.println("thm major 3.0("+iteration+") " + i + ":" + j + " [=]  d=0");
							}else{
								if (verboseDetails) System.out.println("\t Thm major 3.0("+iteration+")  :  ordre majoritaire respecte " + i + " " + j + " [=]");
								if (verbosePairs) System.out.println("thm major 3.0("+iteration+") " + i + ":" + j + " [=]");
							}
							//update des vecteurs DELTA
							MOT3_LUBC_update_vecteurs_DELTA(myInstance,i, j);
							if (verboseSymbols) System.out.print(".");
							
							//fermeture contraintes pour ne pas avoir de contradictions
							
							//debut bloc fermeture
							add_fermeture_contraintes=0;
							add_fermeture_contraintes = MOT3_LUBC_fermeture_contraintes_matriciel_newPair(myInstance,i,j,verboseDetails,verbosePairs);
							constraints_count+=add_fermeture_contraintes;
							//fin bloc fermeture
							
						}
						else{
							if (verboseDetails) System.out.println("\t ordre relatif inconnu");
						}
						
						
						
					}
					
					
					
					
				}
			}
			
			if (!amelioration && !check1){
				check1 = true;
				thm_major_3_0e_count = constraints_count;
				thm_major_3_0_stats += constraints_count;
				thm_MOT3_iterationAvg_stats += iteration;
			}
			
			
			for (int i = 1; i<= n; i++){
				for (int j = 1; j<= n; j++){
					valuesOfApproxPair[i-1][j-1]=-999999;	
				}
			}
			//2 (LUBC)
			if (!amelioration && true){//if there's no new constraints from above, try this now
				if (verbosePairs || verboseDetails) System.out.println("ok: " + constraints_count);
				for (int i = 1; i<= n; i++){
					for (int j = 1; j<= n; j++){
						if (!myInstance.tabC[i-1][j-1] && !myInstance.tabC[j-1][i-1] && i!=j &&(myInstance.tabPairStatus[j-1][i-1]==4 || true)){//s'il n'y a toujours pas de contraintes  //testing the pair status mode
							if (myInstance.best_upper_bound != -1){//on essaie avec les contraintes par approximation
								//contrainteApproxPaire (j, i, n, distMedianApprox,false);
								int value = contrainteApproxPaire (myInstance,j, i, n, false);
								if (value > 0){//si la borneInf de j < i est plus grande que l'approximation...
									myInstance.tabC[i-1][j-1]=true;
									constraints_count++;
									amelioration = true;
									//update des vecteurs DELTA
									MOT3_LUBC_update_vecteurs_DELTA(myInstance,i, j);
									if (verboseSymbols) System.out.print("+");
									
									if (verboseDetails) System.out.println("\t contraintes par approximation  :  ordre majoritaire respecte " + i + " " + j);
									if (verbosePairs) System.out.println("contraintes par approximation " + i + ":" + j);
									
									//debut bloc fermeture
									add_fermeture_contraintes=0;
									add_fermeture_contraintes = MOT3_LUBC_fermeture_contraintes_matriciel_newPair(myInstance,i,j,verboseDetails,verbosePairs);
									constraints_count+=add_fermeture_contraintes;
									//fin bloc fermeture
								}else{
									valuesOfApproxPair[i-1][j-1]=value;	
								}
			
							}
						}
					}
				}
			}
			
			if (!amelioration && !check2){
				check2 = true;
				LUBC_count = constraints_count - thm_major_3_0e_count;
				thm_major_3_0p_stats += constraints_count;
				thm_MOT3p_iterationAvg_stats += iteration;
			}
			
			
			//3 (LUBC-LP)
			/*if (false && !amelioration){//if there's no new constraints from above, try this now
				
				int counter = 1;
				int counterMax = 5;
				
				if (verbosePairs || verboseDetails) System.out.println("ok: " + constraints_count);
				
				
				//1
				while (counter <= counterMax){
					int iMax = 0;
					int jMax = 0;
					int maximum=-999999;
					int minimum=1;
					
					for (int i = 1; i<= n; i++){
						for (int j = 1; j<= n; j++){
							if (!myInstance.tabC[i-1][j-1] && !myInstance.tabC[j-1][i-1] && i!=j){
								//if(valuesOfApproxPair[i-1][j-1] > maximum){
								//	maximum=valuesOfApproxPair[i-1][j-1];
								//	iMax = i;
								//	jMax = j;
								//	//System.out.println(i+" " + j);
								//}
								if((valuesOfApproxPair[i-1][j-1] < minimum) && (valuesOfApproxPair[i-1][j-1] !=-999999) && (myInstance.tabPairStatus[i-1][j-1]==4)){
									minimum=valuesOfApproxPair[i-1][j-1];
									iMax = i;
									jMax = j;
									//System.out.println(i+" " + j);
								}
							}
							
						}
					}
					if (minimum == 1){
					//if (maximum == -999999){
						counter = counterMax+1;
					}else{
						//System.out.println(iMax+" " + jMax);
						valuesOfApproxPair[iMax-1][jMax-1]=-999999;
						
						if (!myInstance.tabC[iMax-1][jMax-1] && !myInstance.tabC[jMax-1][iMax-1] && iMax!=jMax &&(myInstance.tabPairStatus[jMax-1][iMax-1]==4|| true) && (counter <= counterMax)){//s'il n'y a toujours pas de contraintes  //testing the pair status mode
							if (myInstance.best_upper_bound != -1){//on essaie avec les contraintes par approximation
								//contrainteApproxPaire (j, i, n, distMedianApprox,false);
								if (!contrainteApproxPaireCplexLP (myInstance,jMax, iMax, false)){//si la borneInf de j < i est plus grande que l'approximation...
									if (myInstance.tabPairStatus[iMax-1][jMax-1]==2) System.out.println("not good pair");
		
									counter++;
									
									myInstance.tabC[iMax-1][jMax-1]=true;
									constraints_count++;
									amelioration = true;
									//update des vecteurs DELTA
									MOT3_LUBC_update_vecteurs_DELTA(myInstance,iMax, jMax);
									if (verboseSymbols) System.out.print("*");
									//System.out.println("+1");
									
									if (verboseDetails) System.out.println("\t contraintes par approximation LP  :  ordre majoritaire respecte " + iMax + " " + jMax);
									if (verbosePairs) System.out.println("contraintes par approximation LP " + iMax + ":" + jMax);
									
									//debut bloc fermeture
									add_fermeture_contraintes=0;
									add_fermeture_contraintes = MOT3_LUBC_fermeture_contraintes_matriciel_newPair(myInstance,iMax,jMax,verboseDetails,verbosePairs);
									constraints_count+=add_fermeture_contraintes;
									//fin bloc fermeture
								}
								
			
							}
						}
					}
				}
				
				/*
				//2
				if (verbosePairs || verboseDetails) System.out.println("ok: " + constraints_count);
				for (int i = 1; i<= n; i++){
					for (int j = 1; j<= n; j++){
						if (!myInstance.tabC[i-1][j-1] && !myInstance.tabC[j-1][i-1] && i!=j &&(myInstance.tabPairStatus[j-1][i-1]==4|| true) && (counter <= counterMax)){//s'il n'y a toujours pas de contraintes  //testing the pair status mode
							if (myInstance.best_upper_bound != -1){//on essaie avec les contraintes par approximation
								//contrainteApproxPaire (j, i, n, distMedianApprox,false);
								if (!contrainteApproxPaireCplexLP (myInstance,j, i, false)){//si la borneInf de j < i est plus grande que l'approximation...
									if (myInstance.tabPairStatus[i-1][j-1]==2) System.out.println("not good pair");

									counter++;
									
									myInstance.tabC[i-1][j-1]=true;
									constraints_count++;
									amelioration = true;
									//update des vecteurs DELTA
									MOT3_LUBC_update_vecteurs_DELTA(myInstance,i, j);
									if (verboseSymbols) System.out.print("*");
									//System.out.println("+1");
									
									if (verboseDetails) System.out.println("\t contraintes par approximation LP  :  ordre majoritaire respecte " + i + " " + j);
									if (verbosePairs) System.out.println("contraintes par approximation LP " + i + ":" + j);
									
									//debut bloc fermeture
									add_fermeture_contraintes=0;
									add_fermeture_contraintes = MOT3_LUBC_fermeture_contraintes_matriciel_newPair(myInstance,i,j,verboseDetails,verbosePairs);
									constraints_count+=add_fermeture_contraintes;
									//fin bloc fermeture
								}
								
			
							}
						}
					}
				}
				
				*/
				
				
				
			//}
			
			if (!amelioration && !check3){
				check3 = true;
				LUBC_LP_count = constraints_count - LUBC_count- thm_major_3_0e_count;
				//thm_major_3_0p_stats += constraints_count;
				//thm_MOT3p_iterationAvg_stats += iteration;
			}
			
			
			//3 (MOT3++)
			/*if (!amelioration && false){//if there's no new constraints from above, try this now
				if (verbosePairs || verboseDetails) System.out.println("**********************ok ok: " + constraints_count);
				boolean toTry = false;
				//like the 1
				for (int i = 1; i<= n; i++){
					for (int j = 1; j<= n; j++){					
						if (myInstance.tabDelta[i-1][j-1] >= 0 && !myInstance.tabC[i-1][j-1] && !myInstance.tabC[j-1][i-1] && i!=j){//si ij est l'ordre majeur et qu'il n'y pas de contrainte pour cette paire
							//taking out more elements from ij
							toTry = false;
							for (int k = 1; k<= n; k++){
								if (myInstance.tabDELTA[i-1][j-1][k-1] >0){
									if(!contrainteApproxTriplet2 (myInstance,j,k, i, n,false)){
										//System.out.println("out! " + k + " in ("+i+","+j+")");
										myInstance.tabMaxDELTA[i-1][j-1] -= myInstance.tabDELTA[i-1][j-1][k-1];
										myInstance.tabDELTA[i-1][j-1][k-1] = 0;
										toTry = true;
									}else{
										//System.out.println("not out");
									}
								}
							}
							
							//same rest
							if (toTry){
								if (verboseDetails) System.out.println(i + ":" + j);// element1:element2
								if (myInstance.tabMaxDELTA[i-1][j-1] < myInstance.tabDelta[i-1][j-1]){
									myInstance.tabC[i-1][j-1]=true;
									constraints_count++;
									amelioration = true;
									if (verboseDetails) System.out.println("\t Thm major 3.0("+iteration+")  :  ordre majoritaire respecte " + i + " " + j);
									if (verbosePairs) System.out.println("thm major 3.0("+iteration+") " + i + ":" + j);
									//update des vecteurs DELTA
									MOT3_LUBC_update_vecteurs_DELTA(myInstance,i, j);
									
									//debut bloc fermeture
									add_fermeture_contraintes=0;
									add_fermeture_contraintes = MOT3_LUBC_fermeture_contraintes_matriciel_newPair(myInstance,i,j,verboseDetails,verbosePairs);
									constraints_count+=add_fermeture_contraintes;
									//fin bloc fermeture
									
								}else if ((myInstance.tabMaxDELTA[i-1][j-1] == myInstance.tabDelta[i-1][j-1]) && (egalite)){
									myInstance.tabC[i-1][j-1]=true;
									constraints_count++;
									amelioration = true;
									if (myInstance.tabDelta[i-1][j-1]==0){
										if (verboseDetails) System.out.println("\t Thm major 3.0("+iteration+")  :  ordre majoritaire respecte " + i + " " + j + " [=]  d=0");
										if (verbosePairs) System.out.println("thm major 3.0("+iteration+") " + i + ":" + j + " [=]  d=0");
									}else{
										if (verboseDetails) System.out.println("\t Thm major 3.0("+iteration+")  :  ordre majoritaire respecte " + i + " " + j + " [=]");
										if (verbosePairs) System.out.println("thm major 3.0("+iteration+") " + i + ":" + j + " [=]");
									}
									//update des vecteurs DELTA
									MOT3_LUBC_update_vecteurs_DELTA(myInstance,i, j);
									
									//fermeture contraintes pour ne pas avoir de contradictions
									
									//debut bloc fermeture
									add_fermeture_contraintes=0;
									add_fermeture_contraintes = MOT3_LUBC_fermeture_contraintes_matriciel_newPair(myInstance,i,j,verboseDetails,verbosePairs);
									constraints_count+=add_fermeture_contraintes;
									//fin bloc fermeture
									
								}
								else{
									if (verboseDetails) System.out.println("\t ordre relatif inconnu");
								}
							}
							
							
						}
						
						
						
						
					}
				}
			}*/
			
			if (verboseSymbols) System.out.println("ite");
			
			/*if (!amelioration && !check3){
				check3 = true;
				thm_major_3_0pp_count = constraints_count - LUBC_count;
				thm_major_3_0pp_stats += constraints_count;
				thm_MOT3pp_iterationAvg_stats += iteration;
			}*/
			
			
			/*
			//debut bloc fermeture
			add_fermeture_contraintes=0;
			add_fermeture_contraintes = MOT3_fermeture_contraintes_matriciel(1,n,verboseDetails,verbosePairs);
			thm_major_4_0_count+=add_fermeture_contraintes;
			//fin bloc fermeture
			*/
		}//fin while amelioration
		
		//************ ^ fin MOT3
		
		
		
		
		
		//affichage des paires non resolues
		if (verbosePairs){
			for (int element1 = 1; element1<=n; element1++){
				for (int element2 = element1 +1; element2<=n; element2++){
					if (!myInstance.tabC[element1-1][element2-1] && !myInstance.tabC[element2-1][element1-1]){
						System.out.println("paire non resolue " + element1 + ":" + element2);
					}
				}
			}
		}
		
		//test consistency
		if (true){
			for (int element1 = 1; element1<=n; element1++){
				for (int element2 = element1 +1; element2<=n; element2++){
					if (myInstance.tabC[element1-1][element2-1] && myInstance.tabC[element2-1][element1-1]){
						System.out.println("error: not consistent!");
					}
				}
			}
		}
		
		//test other
		if (true){
			int nb=0;
			for (int element1 = 1; element1<=n; element1++){
				for (int element2 = 1; element2<=n; element2++){
					if (myInstance.tabC[element1-1][element2-1]){
						nb++;
					}
				}
			}
			if (nb != constraints_count ){
				System.out.println("error: values not matching! " + nb + " vs " + constraints_count);
			}
		}
		
		//test ordre mineur
		if (false){
			int ordre_mineur_count =0;
			for (int element1 = 1; element1<=n; element1++){
				for (int element2 = 1; element2<=n; element2++){
					if (element1 != element2){
						if (myInstance.tabC[element1-1][element2-1]){
							if (myInstance.tabD[element1-1][element2-1] > myInstance.tabD[element2-1][element1-1]){
								//System.out.println("ordre mineur!");
								ordre_mineur_count++;
							}
						}
					}
				}
			}
			System.out.println("                                   ordre mineur: " + ordre_mineur_count + "/" + (n*(n-1)/2)) ;
		}
		
		
		
		
		//structure arborescente
		/*arbreMOT3 = new TreeSet<Node>();
		rootArbreMOT3 = new Node(0);
		boolean ok=true;
		for (int i = 1; i <=n;i++){
			Node z = new Node(i);
			arbreMOT3.add(z);
		}
		for (Node i : arbreMOT3){
			for (Node j : arbreMOT3){
				if (myInstance.tabC[i.data-1][j.data-1]){
					ok=true;
					for (Node k : arbreMOT3){
						if(myInstance.tabC[i.data-1][k.data-1] && myInstance.tabC[k.data-1][j.data-1]){
							ok=false;
						}
					}
					if (ok){
						i.addChild(j);
						j.addParent(i);
					}
				}else if(!myInstance.tabC[i.data-1][j.data-1] && !myInstance.tabC[j.data-1][i.data-1]){
					/*if (i.data < j.data){
						int e1 = i.data;int e2 = j.data;
						if (tabDelta[e1-1][e2-1]>= 0){
							System.out.println(e1 + " -> " +e2 + "  [label="+tabDelta[e1-1][e2-1]+",color=red];");
						}else{
							System.out.println(e2 + " -> " +e1 + "  [label="+(-1*tabDelta[e1-1][e2-1])+",color=red];");
						}
					}*/
				/*}
			}
		}*/
		/*for (Node i : arbreMOT3){
			System.out.println(i);//print
			if (i.parents.size()==0){
				rootArbreMOT3.addChild(i);
			}
		}
		System.out.println(rootArbreMOT3);*/
		
		
	
		
		
		
		//myInstance.giveConstraints(tabC);
		
		
		
		
		//statistiques
		//thm_major_4_0_stats += thm_major_4_0_count;
		//thm_major_3_0_revised_count = thm_always_count + thm_major_1_0_count + thm_major_2_0_count + constraints_count;//on l'utilise ensuite conjointement avec l'autre seulement si Btztler est applicable
		
		thm_always_stats += thm_always_count;
		//thm_major_3_0_stats += constraints_count;
		thm_major_3_0_revised_count = constraints_count;//on l'utilise ensuite conjointement avec l'autre seulement si Betztler est applicable
		//thm_MOT3_iterationAvg_stats += iteration;
		
		if (thm_MOT3_iterationMax_stats < iteration) thm_MOT3_iterationMax_stats = iteration;
		
		//affichage des stats
		//if (verboseStats) System.out.println("thm_major_4_0_count: " + thm_major_4_0_count);
		//if (verboseStats) System.out.println("nombre de paires: " + (n*(n-1)/2));
		//if (verboseStats) System.out.println("");
		resolution_exacte = 100.0*(constraints_count)/(n*(n-1)/2);
		resolution_stats +=resolution_exacte;
		if (verboseStats){
			if (egalite){
				System.out.println("resolution exacte MOT3e+LUBC: " + (constraints_count)+ "/" + (n*(n-1)/2)
				+ " ("+df.format(resolution_exacte)+"%)");
			}else{
				System.out.println("resolution exacte MOT3+LUBC: " + (constraints_count)+ "/" + (n*(n-1)/2)
						+ " ("+df.format(resolution_exacte)+"%)");
			}
			
			System.out.println("resolution exacte MOT & LUBC & LUBC-LP: " + (thm_major_3_0e_count)+ "/" + (n*(n-1)/2)
					+ ", " + (LUBC_count)+ "/" + (n*(n-1)/2)+ ", " + (LUBC_LP_count)+ "/" + (n*(n-1)/2));
			/*System.out.println("resolution exacte MOT - LUBC - pp: " + (thm_major_3_0e_count)+ "/" + (n*(n-1)/2)
					+ ", " + (LUBC_count)+ "/" + (n*(n-1)/2)
					+ ", " + (thm_major_3_0pp_count)+ "/" + (n*(n-1)/2));*/
			//System.out.println("");
			
		}
		//if (verboseStats) System.out.println(df.format(resolution_exacte));
		
		//contraintesCool = new ArrayList<IntPair>();//pour test d'inclusion thms betzler ds cool
		//contraintesCool.addAll(contraintes);
		
		
		
		
		
		
		//*****************************************MOT4.0
		//verboseDetails = true;
		/*
		tabMOT4 = new boolean[n][n];
		tabMOT4dist = new int[n][n];
		int thm_major_4_0_count = 0;
		int negative_count = 0;
		int value=0;
		int key=0;
		int nbElementsK=0;
		int nbElements=0;
		int counter=0;
		boolean egaliteMOT4 = false;
		SortedMap<Integer, Integer> U = new TreeMap<Integer,Integer>();
		String Ustring = "";
		
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				tabMOT4[i-1][j-1]=false;
				tabMOT4dist[i-1][j-1]=0;
			}
		}*/
		
		
		/*
		// 14-12
		System.out.println("paire non resolue " + 12 + ":" + 14);
		System.out.println("           d_ij=" + tabDelta[12-1][14-1] + "  Delta_ij="+ tabMaxDELTA[12-1][14-1]);
		System.out.print("           ");
		for (int i = 1; i<= n; i++){
			if(i == 12 || i == 14){
				if (verboseDetails)  System.out.print(" "+"!");
			}else if (tabC[i-1][14-1] || tabC[12-1][i-1]){
				if (verboseDetails)  System.out.print(" "+"x");
			}else{
				nbElements++;
				if (verboseDetails)  System.out.print(" "+tabDELTA[12-1][14-1][i-1]);
				if (tabDELTA[12-1][14-1][i-1] < 0) negative_count += tabDELTA[12-1][14-1][i-1];
			}
		}
		System.out.println(" ");
		*/
		
		/*
		for (int element1 = 1; element1<=n; element1++){
			for (int element2 = 1; element2<=n; element2++){
				if ((element1!=element2) && !tabC[element1-1][element2-1] && !tabC[element2-1][element1-1]){
				//if ((element1!=element2) && !tabC[element1-1][element2-1] && !tabC[element2-1][element1-1] && (tabDelta[element1-1][element2-1] >= 0)){
					if (verboseDetails)  System.out.println("paire non resolue " + element1 + ":" + element2);
					if (verboseDetails)  System.out.println("           d_ij=" + tabDelta[element1-1][element2-1] + "  Delta_ij="+ tabMaxDELTA[element1-1][element2-1]);
					if (verboseDetails)  System.out.print("           ");
					negative_count = 0;
					nbElements=0;
					U = new TreeMap<Integer,Integer>();
					for (int i = 1; i<= n; i++){
						if(i == element1 || i == element2){
							if (verboseDetails)  System.out.print(" "+"!");
						}else if (tabC[i-1][element2-1] || tabC[element1-1][i-1]){
							if (verboseDetails)  System.out.print(" "+"x");
						}else{
							//contrainteApproxPaire(element2, element1, n, distMedianApprox, true);
							if (false && !contrainteApproxTriplet2(element2, i,element1, n, distMedianApprox, false)){
								//System.out.println("out! " + i + " in ("+element2+","+element1+")");
								if (verboseDetails)  System.out.print(" "+"/");
								if (tabDELTA[element1-1][element2-1][i-1] > 0) tabMaxDELTA[element1-1][element2-1] -= tabDELTA[element1-1][element2-1][i-1];
							}else{
								nbElements++;
								if (verboseDetails)  System.out.print(" "+tabDELTA[element1-1][element2-1][i-1]);
								if (tabDELTA[element1-1][element2-1][i-1] < 0) negative_count += tabDELTA[element1-1][element2-1][i-1];
								key = tabDELTA[element1-1][element2-1][i-1];
								if (U.get(key) != null){
									value = U.get(tabDELTA[element1-1][element2-1][i-1]);
									U.put(key,value+1);		
								}else{
									U.put(key,1);	
								}
							}
						}	
					}
					
					
					
					
					negative_count += tabMaxDELTA[element1-1][element2-1];
					negative_count -= tabDelta[element1-1][element2-1];
					if (verboseDetails) System.out.println(" ");
					//System.out.println(" "+ "       diff: " + negative_count);
					if (negative_count < 0) {
						//System.out.println("**************");
						//thm_major_4_0_count++;
						
						
						if (verboseDetails) System.out.println(U);
						Ustring = U.toString();
	
						nbElementsK=0;
						counter=-1*tabDelta[element1-1][element2-1];
						//System.out.println("nbElementsK " + nbElementsK);
						int key2 = 0;
						if (U.size() == 0){
							if(tabDelta[element1-1][element2-1] > 0){
								thm_major_4_0_count++;
								tabMOT4[element1-1][element2-1]=true;
								tabMOT4dist[element1-1][element2-1]=0;
								
								
								if (verbosePairs || verboseDetails && true){
									if (tabDelta[element1-1][element2-1] >=0){
										System.out.println("MOT4.0 (" + element1 +","+ element2 +") " +"0/0");
									}else{//shouldn't happen
										System.out.println("MOT4.0 -(" + element1 +","+ element2 +") " +"0/0");
									}
								}
							}
						}
						while(U.size() > 0){
							key2 = U.lastKey();
							//System.out.println(key2);
							if (key2 >= 0){
								counter +=U.get(key2)*key2;
								nbElementsK +=U.get(key2);
								//System.out.println("nbElementsK " + nbElementsK);
								U.remove(key2);
							}else{
								if (counter + U.get(key2)*key2 >= 0){
									counter +=U.get(key2)*key2;
									nbElementsK +=U.get(key2);
									//System.out.println("nbElementsK " + nbElementsK);
									U.remove(key2);
								}else{
									double d1 = (double)counter;
									double d2 = (double)key2;
									double r1 = Math.floor((-1*d1/d2)+1.0);
									counter += (int)r1*key2;
									nbElementsK += (int)r1;
									//System.out.println("r1 " + r1);
									//System.out.println("nbElementsK " + nbElementsK);
									if ((int)r1 == U.get(key2)){
										U.remove(key2);
									}else{
										value = U.get(key2) - (int)r1;
										U.put(key,value);	
									}
									if (U.size() != 0){
										thm_major_4_0_count++;
										tabMOT4[element1-1][element2-1]=true;
										tabMOT4dist[element1-1][element2-1]=nbElementsK;
										
										boolean test111=false;
										if (test111){
											if (true)  System.out.println("paire non resolue " + element1 + ":" + element2);
											if (true)  System.out.println("           d_ij=" + tabDelta[element1-1][element2-1] + "  Delta_ij="+ tabMaxDELTA[element1-1][element2-1]);
											if (true)  System.out.print("           ");
											for (int i = 1; i<= n; i++){
												if(i == element1 || i == element2){
													if (true)  System.out.print(" "+"!");
												}else if (tabC[i-1][element2-1] || tabC[element1-1][i-1]){
													if (true)  System.out.print(" "+"x");
												}else{
													if (true)  System.out.print(" "+tabDELTA[element1-1][element2-1][i-1]);
													
												}	
											}
											if (true) System.out.println(" ");
											if (true) System.out.println(Ustring);
										}
										
										if (verbosePairs || verboseDetails && true){
											if (tabDelta[element1-1][element2-1] >=0){
												System.out.println("MOT4.0 (" + element1 +","+ element2 +") " + nbElementsK+"/"+nbElements);
											}else{
												System.out.println("MOT4.0 -(" + element1 +","+ element2 +") " + nbElementsK+"/"+nbElements);
											}
										}
										//System.out.println("MOT4.0 (" + element1 +","+ element2 +") " + nbElementsK+"/"+nbElements);
										U.clear();
										
									}
								}
							}
						}
						
					}
					
				}
			}
		}*/
		
		/*for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				if (tabMOT4[i-1][j-1]) System.out.println("MOT4.0 (" + i +","+ j + ")  " + tabMOT4dist[i-1][j-1]);
			}
		}*/
		
		/*
		//imprimer le graph des forces...
		System.out.println("");
		Set<Integer> bob = new HashSet<Integer>();
		int x = 13;
		int y = 4;
		bob.add(x); bob.add(y);
		//bob.add(4); bob.add(6); bob.add(3); bob.add(7); bob.add(12); bob.add(11); 
		bob.add(2);bob.add(5);bob.add(6);bob.add(15);bob.add(16);bob.add(17);
		for (int e1 : bob){
			for (int e2 : bob){
				if (e1 < e2 && (e1 == x || e2 ==x || e1 == y || e2 == y )){
					if (tabDelta[e1-1][e2-1]>= 0){
						System.out.println(e1 + " -> " +e2 + "  [label="+tabDelta[e1-1][e2-1]+"];");
					}else{
						System.out.println(e2 + " -> " +e1 + "  [label="+(-1*tabDelta[e1-1][e2-1])+"];");
					}
				}
			}
		}
		System.out.println("");
		*/
		
		
		//System.out.println("MOT4.0 \"\" : " + thm_major_4_0_count);
		/*resolution_exacte = 100.0*(thm_major_4_0_count)/(n*(n-1)/2);
		if (verboseStats){

			System.out.println("resolution exacte MOT4: " + (thm_major_4_0_count)+ "/" + (n*(n-1)/2)
					+ " ("+df.format(resolution_exacte)+"%)");
			System.out.println("");
		}*/
		
		
	}
	
	public static int MOT3_LUBC_update_vecteurs_DELTA(Instance myInstance, int i, int j){
		int n = myInstance.n;
		if (myInstance.tabC[i-1][j-1]){//si la contrainte a ete trouvee
			//if (i ==5 && j ==2) System.out.println("52!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			for (int element = 1; element<= n; element++){
				
				if (myInstance.tabDelta[element-1][j-1] >= 0 &&  !myInstance.tabC[element-1][j-1]){ //si kj ordre majeur, contrainte non trouvee
					if (myInstance.tabDELTA[element-1][j-1][i-1] > 0){
						myInstance.tabMaxDELTA[element-1][j-1] -= myInstance.tabDELTA[element-1][j-1][i-1];
						myInstance.tabDELTA[element-1][j-1][i-1] = 0;
						myInstance.toCheck[element-1][j-1]=true;
					}else{
						//tabDELTA[element-1][j-1][i-1] = 0;
					}
				}else if(!myInstance.tabC[element-1][j-1] && true){//si c'est un ordre mineur... test pour autre truc
					if (myInstance.tabDELTA[element-1][j-1][i-1] > 0){
						myInstance.tabMaxDELTA[element-1][j-1] -= myInstance.tabDELTA[element-1][j-1][i-1];
						myInstance.tabDELTA[element-1][j-1][i-1] = 0;
						myInstance.toCheck[element-1][j-1]=true;
					}else{
						//tabDELTA[element-1][j-1][i-1] = 0;
					}
				}
				
				if (myInstance.tabDelta[i-1][element-1] >= 0 &&  !myInstance.tabC[i-1][element-1]){ //si ik ordre majeur, contrainte non trouvee
					if (myInstance.tabDELTA[i-1][element-1][j-1] > 0){
						myInstance.tabMaxDELTA[i-1][element-1] -= myInstance.tabDELTA[i-1][element-1][j-1];
						myInstance.tabDELTA[i-1][element-1][j-1] = 0;
						myInstance.toCheck[i-1][element-1]=true;
					}else{
						//tabDELTA[i-1][element-1][j-1] = 0;
					}
				}else if(!myInstance.tabC[i-1][element-1] && true){//si c'est un ordre mineur... test pour autre truc
					if (myInstance.tabDELTA[i-1][element-1][j-1] > 0){
						myInstance.tabMaxDELTA[i-1][element-1] -= myInstance.tabDELTA[i-1][element-1][j-1];
						myInstance.tabDELTA[i-1][element-1][j-1] = 0;
						myInstance.toCheck[i-1][element-1]=true;
					}else{
						//tabDELTA[i-1][element-1][j-1] = 0;
					}
				}
				                      
			}
		}
		return 0;
	}
	
	//fermeture d'un ensemble de contraintes par transitivite
	//methode naive implemente rapidement
	public static int MOT3_LUBC_fermeture_contraintes_matriciel_newPair(Instance myInstance,int i, int j, boolean verboseDetails, boolean verbosePairs){
		int n = myInstance.n;
		int nouv = 0;
		for (int element3 = 1; element3<=n; element3++){
			if (myInstance.tabC[element3-1][i-1] && !myInstance.tabC[element3-1][j-1]){
				myInstance.tabC[element3-1][j-1]=true;
				nouv ++;
				if (verboseDetails) System.out.println(element3 + ":" + j);
				if (verboseDetails) System.out.println("\t avec les contraintes " + element3 + ":" + i + " et " + i + ":" + j);
				if (verboseDetails) System.out.println("\t Add fermeture : " + element3 + ":" + j);
				if (verbosePairs) System.out.println("fermeture " + element3 + ":" + j);
				MOT3_LUBC_update_vecteurs_DELTA(myInstance,element3, j);
				nouv += MOT3_LUBC_fermeture_contraintes_matriciel_newPair(myInstance, element3, j, verboseDetails, verbosePairs);
			}
			if (myInstance.tabC[j-1][element3-1] && !myInstance.tabC[i-1][element3-1]){
				myInstance.tabC[i-1][element3-1]=true;
				nouv ++;
				if (verboseDetails) System.out.println(i + ":" + element3);
				if (verboseDetails) System.out.println("\t avec les contraintes " + i + ":" + j + " et " + j + ":" + element3);
				if (verboseDetails) System.out.println("\t Add fermeture : " + i + ":" + element3);
				if (verbosePairs) System.out.println("fermeture " + i + ":" + element3);
				MOT3_LUBC_update_vecteurs_DELTA(myInstance,i, element3);
				nouv += MOT3_LUBC_fermeture_contraintes_matriciel_newPair(myInstance, i,element3, verboseDetails, verbosePairs);
			}
		}
		return nouv;
	}
	
	
	//fermeture d'un ensemble de contraintes par transitivite
	//methode naive implemente rapidement
	public static int MOT3_LUBC_fermeture_contraintes_matriciel(Instance myInstance, int min, boolean verboseDetails, boolean verbosePairs){
		int n = myInstance.n;
		boolean amelioration = true;
		int nouv = 0;
		if (verboseDetails) System.out.println("");
		if (verboseDetails) System.out.println("Fermeture des contraintes trouvees (add):");
		while (amelioration){
			amelioration = false;
			for (int i = min; i <= n; i++){
				for (int j = min; j <= n; j++){
					for (int k = min; k <= n; k++){
						if (!myInstance.tabC[i-1][j-1]){
							if (myInstance.tabC[i-1][k-1] && myInstance.tabC[k-1][j-1]){
								myInstance.tabC[i-1][j-1]=true;
								amelioration = true;
								nouv++;
								if (verboseDetails) System.out.println("  "+i+":"+j);
								if (verbosePairs) System.out.println("add fermeture "+i+":"+j);
								if (myInstance.tabC[j-1][i-1]){
									System.out.println("attention! contrainte contradictoire");
									if (verboseDetails) System.out.println("attention! contrainte contradictoire");
									if (verbosePairs) System.out.println("attention! contrainte contradictoire");
								}
								
								//update des vecteurs DELTA
								MOT3_LUBC_update_vecteurs_DELTA(myInstance,i, j);
								
							}
						}
					}
				}
			}
		}
		if (verboseDetails) System.out.println("");
		return nouv;
	}
	
	public static void affiche_contraintes_spatiales(Instance myInstance){
		System.out.println();
		System.out.println("contraintes spatiales");
		String middleSpace = "";
		String leftSpace = "";
		String rightSpace = "";
		int n = myInstance.n;
		
		for (int element1 = 1; element1<=n; element1++){
			middleSpace = "**";
			
			for (int element2 = 1; element2<=n; element2++){
				if (element1 != element2){
					if (myInstance.tabC[element1-1][element2-1]){
						if (element2 < 10)
							rightSpace = rightSpace+ " 0"+element2+"";
						else
							rightSpace = rightSpace+" "+ element2+"";
					}else if(myInstance.tabC[element2-1][element1-1]){
						if (element2 < 10)
							leftSpace = "0"+element2+" "+leftSpace;
						else
							leftSpace = ""+ element2+" "+leftSpace;
					}else{
						if (element2 % 2 == 0)
							middleSpace = " " + middleSpace + "  ";
						else
							middleSpace = "  " + middleSpace + " ";
					}
				}
			}
			
			if (element1 < 10)
				leftSpace = "0"+element1+":  " + leftSpace;
			else
				leftSpace = ""+ element1+":  " + leftSpace;
			
			System.out.println(leftSpace + middleSpace + rightSpace);
			
			middleSpace = "";
			leftSpace = "";
			rightSpace = "";
		}
	}




	private static void etudeBorneInf(Instance myInstance,Set<Permutation> set1) {
		//etude borneInf valide seulement pour m=3
		int borneInf =0;
		int borneInfAme =0;
		int n=myInstance.n;//pickARandom(set1).getSize();
		//tabD = creationTabD(set1);
		
		//ancienne methode
		for (int i =1;i<=n;i++){
			for (int j =i+1;j<=n;j++){
					borneInf += Math.min(myInstance.tabD[i-1][j-1], myInstance.tabD[j-1][i-1]);
			}
		}
		System.out.println("borneInf: " + borneInf);
		
		//nouvelle methode
		Map<Point2D,Integer> segments = new HashMap<Point2D,Integer>();
		Point x1 = new Point(0,0);
		Point x2 = new Point(0,0);
		Point x3 = new Point(0,0);
		int value = 0;
		for (int i =1;i<=n;i++){
			for (int j =i+1;j<=n;j++){
				for (int k =j+1;k<=n;k++){
					// i -> j -> k -> i
					if (((myInstance.tabD[j-1][i-1]-myInstance.tabD[i-1][j-1]) == 1) && ((myInstance.tabD[k-1][j-1]-myInstance.tabD[j-1][k-1]) == 1) && ((myInstance.tabD[i-1][k-1]-myInstance.tabD[k-1][i-1]) == 1)){
						x1 = new Point(i,j);
						x2 = new Point(j,k);
						x3 = new Point(k,i);
						if (segments.containsKey(x1)){
							value = segments.get(x1);
							segments.put(x1, value + 1);
						}else{
							segments.put(x1,1);
						}
						if (segments.containsKey(x2)){
							value = segments.get(x2);
							segments.put(x2, value + 1);
						}else{
							segments.put(x2,1);
						}
						if (segments.containsKey(x3)){
							value = segments.get(x3);
							segments.put(x3, value + 1);
						}else{
							segments.put(x3,1);
						}
					}
					
					if (((myInstance.tabD[j-1][i-1]-myInstance.tabD[i-1][j-1]) == -1) && ((myInstance.tabD[k-1][j-1]-myInstance.tabD[j-1][k-1]) == -1) && ((myInstance.tabD[i-1][k-1]-myInstance.tabD[k-1][i-1]) == -1)){
						x1 = new Point(j,i);
						x2 = new Point(k,j);
						x3 = new Point(i,k);
						if (segments.containsKey(x1)){
							value = segments.get(x1);
							segments.put(x1, value + 1);
						}else{
							segments.put(x1,1);
						}
						if (segments.containsKey(x2)){
							value = segments.get(x2);
							segments.put(x2, value + 1);
						}else{
							segments.put(x2,1);
						}
						if (segments.containsKey(x3)){
							value = segments.get(x3);
							segments.put(x3, value + 1);
						}else{
							segments.put(x3,1);
						}
					}
						
					
				}
			}
		}
		//SortedSet<Integer> nbSegments = new TreeSet<Integer>();
		//nbSegments.
		List<Integer> nbSegments = new ArrayList<Integer>();
		for (int k : segments.values()){
			nbSegments.add(k);
		}
		Collections.sort(nbSegments);
		Collections.reverse(nbSegments);
		//System.out.println(nbSegments);
		int addBorneInf=0;
		while (!nbSegments.isEmpty()){
			int tmp1 = nbSegments.get(0)*2;
			nbSegments.remove(0);
			if (nbSegments.size() > 0){
				while (tmp1 >0){
					if (nbSegments.size() > 0){
						int tmp2 = nbSegments.get(nbSegments.size()-1);
						nbSegments.set(nbSegments.size()-1, tmp2-1);
						tmp1--;
						if (nbSegments.get(nbSegments.size()-1)==0) nbSegments.remove(nbSegments.size()-1);
					}else{
						tmp1=0;
					}
					
				}
			}
			addBorneInf++;
			//System.out.println(nbSegments);
		}
		borneInfAme = borneInf + addBorneInf;
		System.out.println("borneInfAme: "+ borneInfAme);
		
	}

	//2e etude borne inf
	private static void etudeBorneInf2(Instance myInstance,Set<Permutation> set1) {
		//etude borneInf valide seulement pour m=3
		int borneInf =0;
		int borneInfAme =0;
		int n=myInstance.n;//pickARandom(set1).getSize();
		//tabD = creationTabD(set1);
		
		//ancienne methode
		for (int i =1;i<=n;i++){
			for (int j =i+1;j<=n;j++){
					borneInf += Math.min(myInstance.tabD[i-1][j-1], myInstance.tabD[j-1][i-1]);
			}
		}
		System.out.println("borneInf: " + borneInf);
		
		
		//nouvelle methode
		int nbTriangles = 0;
		boolean[][] segmentFree = new boolean [n][n];
		int[][][] tabTriangleAdd = new int [n][n][n];
		int[][] tabTriangleAssocie = new int [n][n];
		int[] apport = new int[n];
		int borneInfAdd =0;
		int score = 0;
		int addScore = 0;
		int min = 0;
		for (int i = 1; i<= n; i++){
			apport[i-1] = 0;
		}
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				segmentFree[i-1][j-1] = true;
				tabTriangleAssocie[i-1][j-1] = 0;
			}
		}
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				for (int k = 1; k<= n; k++){
					tabTriangleAdd[i-1][j-1][k-1] = 0;
				}
			}
		}
		for (int i = 1; i<= n; i++){
			for (int j = i + 1; j<= n; j++){
				for (int k = j +1; k<= n; k++){
					
					if (segmentFree[i-1][j-1] && segmentFree[j-1][k-1] && segmentFree[k-1][i-1]){
						
						
						score=myInstance.tabD[i-1][j-1]+myInstance.tabD[i-1][k-1]+myInstance.tabD[j-1][k-1];// i j k
						if (myInstance.tabD[i-1][k-1]+myInstance.tabD[i-1][j-1]+myInstance.tabD[k-1][j-1] < score) score = myInstance.tabD[i-1][k-1]+myInstance.tabD[i-1][j-1]+myInstance.tabD[k-1][j-1];// i k j
						if (myInstance.tabD[j-1][i-1]+myInstance.tabD[j-1][k-1]+myInstance.tabD[i-1][k-1] < score) score = myInstance.tabD[j-1][i-1]+myInstance.tabD[j-1][k-1]+myInstance.tabD[i-1][k-1];// j i k
						if (myInstance.tabD[j-1][k-1]+myInstance.tabD[j-1][i-1]+myInstance.tabD[k-1][i-1] < score) score = myInstance.tabD[j-1][k-1]+myInstance.tabD[j-1][i-1]+myInstance.tabD[k-1][i-1];// j k i
						if (myInstance.tabD[k-1][i-1]+myInstance.tabD[k-1][j-1]+myInstance.tabD[i-1][j-1] < score) score = myInstance.tabD[k-1][i-1]+myInstance.tabD[k-1][j-1]+myInstance.tabD[i-1][j-1];// k i j
						if (myInstance.tabD[k-1][j-1]+myInstance.tabD[k-1][i-1]+myInstance.tabD[j-1][i-1] < score) score = myInstance.tabD[k-1][j-1]+myInstance.tabD[k-1][i-1]+myInstance.tabD[j-1][i-1];// k j i
						
						min =0;
						min += Math.min(myInstance.tabD[i-1][j-1],myInstance.tabD[j-1][i-1]);
						min += Math.min(myInstance.tabD[i-1][k-1],myInstance.tabD[k-1][i-1]);
						min += Math.min(myInstance.tabD[j-1][k-1],myInstance.tabD[k-1][j-1]);
						
						addScore = score - min; 
						
						if (addScore>0){
							borneInfAdd += addScore;
							
							segmentFree[i-1][j-1] = false;
							segmentFree[j-1][k-1] = false;
							segmentFree[k-1][i-1] = false;
							segmentFree[j-1][i-1] = false;
							segmentFree[k-1][j-1] = false;
							segmentFree[i-1][k-1] = false;
							
							apport[i-1] += addScore;
							apport[j-1] += addScore;
							apport[k-1] += addScore;
							
							nbTriangles++;
							
							System.out.println(i+ " " + j + " " + k + " : " + addScore);
						}
					}

				}
			}
		}
		System.out.println("Apports");
		for (int i = 1; i<= n; i++){
			System.out.println(i+" : "+apport[i-1]);
		}
		for (int i = 1; i<= n; i++){
			for (int j = i + 1; j<= n; j++){
				if (segmentFree[i-1][j-1]){
					//borneInf
				}
			}
		}
		borneInfAme = borneInf + borneInfAdd;
		System.out.println("borneInfAme: "+borneInf+"+"+borneInfAdd+"="+ borneInfAme);
		System.out.println("nbTriangles: "+ (nbTriangles*3)+ "/" + (n*(n-1)/2));
		
	}





	//etude complementaire sur convergence de Simulated Annealing
	private void loiDeDistributionAtomeStable() {
		// TODO Auto-generated method stub
		int rendu=0;
		//statistiques sur simulated annealing
		SortedSet<Integer> dernieresIterations;
		int[] tabDernieresIterations;
		
		dernieresIterations=new TreeSet<Integer>();
		tabDernieresIterations = new int[100];
		
		//un pett code doit etre implemente dans SA
		//test
		test200("Results3_50.txt","speaking mode",50,30000);
		
		rendu=0;
		while (!(dernieresIterations.isEmpty())){
			if (dernieresIterations.first()>= ((rendu+1)*1000))
				rendu++;
			else{
				dernieresIterations.remove(dernieresIterations.first());
				tabDernieresIterations[rendu]++;
			}
		}
		
		//affichage
		for (int i=0;i<90;i++){
			System.out.println((i*1000)+"-"+((i+1)*1000-1)+": "+tabDernieresIterations[i]);
		}
	}


	
	//methode qui fera des batteries de test pour determiner le % d'erreur de 
	// l'heuristique ME pour trouver la vraie distMediane pour des |A| et n differents
	//(test pris du fichiers 'fichier')
	//format de fichier requis
	public void test200(String fichier, String mode, int arg0, int arg1){//arg0=nb electrons, arg1=nb mvts permis
		int objectiveValue=0;
		int reussites=0;
		int nbTests=0;
		char u;
		int profondeur=0;
		String nombreString = "";
		int[] a = null;
		List<Integer> permuEnCours = null;
		double moyEcart=0;
		double moyCardinaliteM=0;
		int n=0;
		HashSet<Permutation> A = new HashSet<Permutation>();
		
		Permutation pi;
		
		
		//mesure du temp
	    lStartTime = new Date().getTime();
	    
	    //caption
	    if (!(mode.equals("condense mode")))System.out.println(" ***Debut des tests sur l'heuristique (sur fichier: " + fichier + ") ***");
	    
	    //lecture
        FileReader reader ;
        try{
        	reader = new FileReader(fichier);
        	Scanner in = new Scanner(reader);
        	
        	while (in.hasNext()){
        		String line = in.nextLine();
        		//Integer.parseInt(line);
        		//line.charAt(i);
        		if (line.length()!=0 ){
	        		if (line.startsWith("Objective")){
	        			objectiveValue=Integer.parseInt(line.substring(line.indexOf("=")+3, line.length()-2));
	        		}else{
	        			for (int i=0;i< line.length();i++){
	        				u=line.charAt(i);
	        				//System.out.println(u);
	        				if (u == '{' || u == '['){
	        					profondeur++;
	        					if (profondeur ==1){
	        						A = new HashSet<Permutation>();
	        					}else if (profondeur ==2){
	        						pi=null;
	        						permuEnCours= new ArrayList<Integer>();
	        					}else if (profondeur ==3){
	        						//capture de chiffres/nombre
	        						nombreString="";
	        					}else
	        						System.out.println("erreur de lecture");
	        						
	        				}
	        				else if (u == '}' || u == ']'){
	        					profondeur--;
	        					if (profondeur ==0){
	        						Instance myInstance = new Instance(A);
	        						//heuristique ME
	        					   // heuristiqueCreerME(A,"silent mode",arg0);
	        						//creerM(A,ME);
	        						
	        						//heuristique SA
	        						//heuristiqueCreerSA(A,false, false,arg0,arg1,5000,0.99);
	        						//heuristiqueCreerSA(null,A,false, false,3);//*modified inst
	        						heuristiqueCreerSA(myInstance,false, false,3);
	        						
	        						n = myInstance.n;//pour affichage
	        						
	        						nbTests++;
									//if (nbTests==149) printA();
	        						if (objectiveValue == myInstance.best_upper_bound) reussites++;
	        						//if (!(mode.equals("silent mode")))System.out.println("Cas #" + nbTests+"   Ecart: " + (distMediane - objectiveValue) + "  (distMediane trouvee: " + distMediane + ", vraie distMediane: "  + objectiveValue +")  |M|= " + M.size() + "  distMin(A,M)=" + dist2Ens(A,M,0)+ "  distMax(M,M)=" + dist2Ens(M,M,1))
	        						//if (!(mode.equals("silent mode")))System.out.println("Cas #" + nbTests+"   Ecart: " + (distMediane - objectiveValue) + "  (distMediane trouvee: " + distMediane + ", vraie distMediane: "  + objectiveValue +")  |M|= " + M.size());
	        						if ((mode.equals("speaking mode")))System.out.println("Cas #" + nbTests+"   Ecart: " + (myInstance.best_upper_bound - objectiveValue) + "  (distMediane trouvee: " + myInstance.best_upper_bound + ", vraie distMediane: "  + objectiveValue +")");// distMin(A,M)=" + dist2Ens(A,M,0));
	        						moyEcart +=(myInstance.best_upper_bound - objectiveValue);
	        						//moyCardinaliteM +=M.size();
	        					}else if (profondeur ==1){
	        						a = new int[permuEnCours.size()];
	        						for (int j=0; j<permuEnCours.size();j++ )
	        							a[j]= permuEnCours.get(j);
	        						
	        						pi = new Permutation(a);
	        						A.add(pi);
	        					}else if (profondeur ==2){
	        						permuEnCours.add(Integer.parseInt(nombreString));
	        					}else
	        						System.out.println("erreur de lecture");
	        				}
	        				else{
	        					if (profondeur== 3){
	        						//System.out.println("u= "+u);
	        						nombreString += u;
	        						//System.out.println("nombreString= "+nombreString);
	        					}
	        				}
	        				

	        				
	        			}
	        		}
        		}
        		
        	}
        	
        	
	    } catch (FileNotFoundException e){
	        System.out.println("Fichier "+ fichier +" n'a pas été trouvé");
	    }


	    
		if (mode.equals("condense mode")){
			//mesure du temp
			lEndTime = new Date().getTime(); //end time
		    difference = (lEndTime - lStartTime); //check different
		    difference = (int)(difference/nbTests); //check different
		    lStartTime = new Date().getTime();
		    
		    //affichage
			System.out.println("n: "+ n +", nbElectrons: " + arg0  +", nbMvts: "+ arg1 +", score: " + reussites + " / " + nbTests + ", temps: "+(difference/60000) + "min" + ((difference % 60000)/1000) + "sec" + (difference % 1000) + "ms" );
		}else{
			//fin - compilation des resultats
		    
		    System.out.println("resultats: " + reussites + " reussites / " + nbTests + " cas testes." );
			//System.out.println("moyenne des ecarts: " + moyEcart );
			//moyCardinaliteM /= nbTests;
			//System.out.println("moyenne de la cardinalite de M: " + moyCardinaliteM );
			moyEcart /= nbTests;
			//mesure du temp
			lEndTime = new Date().getTime(); //end time
		    difference = (lEndTime - lStartTime); //check different
		    System.out.println("Temps pour calculer test: " +(difference/60000) + " minutes " + ((difference % 60000)/1000) + " secondes " + (difference % 1000) + " millisecondes");
		    difference = (int)(difference/nbTests); //check different
		    System.out.println("Temps moyen par cas: " +(difference/60000) + " minutes " + ((difference % 60000)/1000) + " secondes " + (difference % 1000) + " millisecondes");
		    lStartTime = new Date().getTime();
		    
		    System.out.println("");
		}
		
	}

	//methode qui calcul la distance entre 2 ensembles de permutation
	//si les 2 ensembles sont differents:
	//mode 0 = la plus petite distance entre un elem de set1 et un element de set2
	//mode 1 = la plus grande distance entre un elem de set1 et un element de set2
	//si les 2 ensembles sont le meme:
	//mode 0 = la plus petite distance entre 2 elements distincts de l'ensemble
	//mode 1 = la plus grandedistance entre 2 elements distincts de l'ensemble
	//autres modes a rajouter ulterieurement
	private static int dist2Ens(Set<Permutation> set1, Set<Permutation> set2, int mode) {
		int reponse =0;
		if (!set1.equals(set2)){
			if (mode ==0){
				reponse = 99999999;
				for (Permutation p1: set1)
					for(Permutation p2 : set2)
						if (p1.distanceTo(p2) < reponse)
							reponse = p1.distanceTo(p2);		
			}else if (mode ==1){
				reponse = 0;
				for (Permutation p1: set1)
					for(Permutation p2 : set2)
						if (p1.distanceTo(p2) > reponse)
							reponse = p1.distanceTo(p2);		
			}
		}else{
			if (mode ==0){
				reponse = 99999999;
				for (Permutation p1: set1)
					for(Permutation p2 : set1)
						if (!p1.equals(p2))
							if (p1.distanceTo(p2) < reponse)
								reponse = p1.distanceTo(p2);		
			}else if (mode ==1){
				reponse = 0;
				for (Permutation p1: set1)
					for(Permutation p2 : set1)
						if (!p1.equals(p2))
							if (p1.distanceTo(p2) > reponse)
								reponse = p1.distanceTo(p2);		
			}
		}
		return reponse;
	}



	
	
	//heuritique simulated annealing
	//excellente heuristique probabiliste - la plus efficace pour le moment
	//private static void heuristiqueCreerSA(Instance myInstance, Set<Permutation> set1, boolean verboseDetail, boolean verboseResult, int SAmode) {
	private static void heuristiqueCreerSA(Instance myInstance, boolean verboseDetail, boolean verboseResult, int SAmode) {
		Set<Permutation> SA = new HashSet<Permutation>();
		Permutation pi = null;
		Permutation nextPi=null;
		int energie =0;
		int deltaEnergie=0;
		int nbRuns=0;//nb d'electrons
		int nbMvts=0;//nb de mouvements permis
		double iniTemperature=0;
		double temperature=0;
		double rand=0;
		double alpha=0;
		DecimalFormat df = new DecimalFormat("#.##");
		
		Permutation pMin = null;
		int eMin=999999999;
		int globalEMin=999999999;
		
		//String text="";
		//int r1=0,r2=0,n=pickARandom(set1).getSize(),temp=0,setSize=set1.size();
		int r1=0,r2=0,n=myInstance.n,temp=0,setSize=myInstance.m;
		int[] a = new int[n];
		
		int m = setSize;
		nbRuns=0;
		nbMvts=0;
		iniTemperature=0;
		alpha=0;
		
		
		//pamaterization of SA from m and n
		iniTemperature=(0.25*m + 4.0)*n;
		
		if ((m == 3) || (m == 4)){
			alpha = 0.99;
		}else if (n <= 10){
			alpha = 0.95;
		}else if ((n >= 11) && (n <= 16)){
			alpha = 0.99;
		}else if ((n >= 17) && (n <= 20)){
			alpha = 0.999;
		}else if ((n >= 21) && (n <= 24)){
			alpha = 0.9995;
		}else{
			alpha = 0.9998;
		}
		
		if (m == 3){
			nbMvts=(int) (0.6*Math.pow(n, 3.0) - 11.0*Math.pow(n, 2.0) + 127.0*n);
		}else if (m == 4){
			nbMvts=(int) (0.9*Math.pow(n, 3.0) - 29.0*Math.pow(n, 2.0) + 435.0*n - 1623);
		}else if  (n <= 7){
			nbMvts=250;
		}else if ((n >= 8) && (n <= 24)){
			nbMvts=(int) (90.0*Math.pow(n, 2.0) - 1540.0*n + 7000);
		}else if ((n >= 25) && (n <= 38)){
			nbMvts=(int) (35.0*Math.pow(n, 2.0) - 660.0*n + 31000);
		}else{
			nbMvts=(int) (80.0*Math.pow(n, 2.0) - 2300.0*n + 27000);
		}
		
		if ((m == 3) || (m == 4)){
			nbRuns=(int) Math.ceil(0.05*n+2.0);
		}else if ((n % 2) == 0){
			nbRuns=(int) Math.ceil(0.007*n*m+3.0);
		}else{
			nbRuns=(int) Math.ceil(0.002*n*m+3.0);
		}
		
		if (SAmode == 0){//ultra fast and only going to a local minima from a random solution, not SA
			iniTemperature=0.0;
			nbMvts*=0.33;
			nbRuns=1;
		}else if (SAmode == 1){//very fast and unprecise
			iniTemperature*=0.5;
			alpha *=0.995;
			nbMvts*=0.666;
			nbRuns=1;
		}else if (SAmode == 2){//fast and weakly precise
			iniTemperature*=0.9;
			nbMvts*=0.866;
			nbRuns=(int) (Math.round(0.5*nbRuns) + 1.0);
		}else if (SAmode == 3){//normal time with good precise
			//same parameters
		}else if (SAmode == 4){//slow and very precise
			nbMvts*=1.1;
			nbRuns*=2;
		}else if (SAmode == 5){//very slow and brutally precise
			iniTemperature*=1.2;
			nbMvts*=2;
			nbRuns*=10;
		}else{
			//same parameters
			System.out.println("SA error: SAmode not specified");
		}
		//end paramaterization
		
		
		//int derniereIte=0;
		
		//tabD = creationTabD(set1);
		
		
		pi = createARandom(n);
		if (verboseDetail) System.out.println("SA Details:");
		for (int j=0; j< nbRuns;j++){
			if (verboseDetail) System.out.println("Electron " + j);
			if (verboseDetail) System.out.println("i \ttemp \tenergie");
			temperature=iniTemperature;
			
			energie  = pi.distanceToSetMatrix(myInstance.tabD);
			eMin=999999999;
			//derniereIte=0;
			
			for (int i =0; i < nbMvts;i++){
				
				//faire le randomize ici
				//nextPi  = randomCMoves(pi,1);
				r1= (int)(Math.random()*(n)); //0 <-> (n-1)
				r2= (int)(Math.random()*(n)); //0 <-> (n-1)
				
				
				//calculer deltaEnergie ici
				//deltaEnergie = nextPi.distanceToSetMatrix(tabD) - energie;
				if (r1 != r2){
					if (r2 > r1){
						//mvt-circulaire droite
						temp=0;
						for (int u=r1;u<r2;u++)
							temp += myInstance.tabD[pi.getTab()[r2]-1][pi.getTab()[u]-1];
						deltaEnergie= (- setSize*(r2-r1) + 2*temp);
					}else{
						//mvt-circulaire gauche
						temp=0;
						for (int u=r2+1;u<=r1;u++)
							temp += myInstance.tabD[pi.getTab()[r2]-1][pi.getTab()[u]-1];
						deltaEnergie= (setSize*(r1-r2) - 2*temp);
						//deltaEnergie=99999999;
					}
				}else{
					deltaEnergie=0;
				}
				


				//desicion ici
				if (deltaEnergie <= 0){
					
					//creation de la nouvelle permutation
					//copie du tab
					for (int u=0;u<n;u++){
						a[u]=pi.getTab()[u];
					}
					//execution du mouvement
					if (r1 != r2){
						if (r2 > r1){
							//mvt-circulaire droite
							temp = a[r2];
							for (int u =r2; u> r1; u--){
								a[u]=a[u-1];
							}
							a[r1] = temp;
						}else{
							//mvt-circulaire gauche
							temp = a[r2];
							for (int u =r2; u<r1; u++){
								a[u]=a[u+1]; 
							}
							a[r1] = temp;
						}
					}
					nextPi = new Permutation(a);
					
					
					
					
					pi = nextPi;
					energie=energie+ deltaEnergie;
					if (verboseDetail) System.out.println(i + "\t" + df.format(temperature) + "\t" + energie);
					
					//affichage
					/*text="";
					for (int k =0; k < (energie-2035);k++){
						text+="|";
					}
					System.out.println(i +": "+text);*/
					//System.out.println(i +": "+(energie-2035) + "  t=" + temperature);
					
					if (energie < eMin){
						eMin = energie;
						pMin =pi;
						//derniereIte=i;
						if (eMin < globalEMin) {
							globalEMin=eMin;
							SA.clear();
						}
					}
				}else{
					rand = Math.random();
					if (rand < Math.exp(-deltaEnergie/temperature)){
						
						//creation de la nouvelle permutation
						//copie du tab
						for (int u=0;u<n;u++){
							a[u]=pi.getTab()[u];
						}
						//execution du mouvement
						if (r1 != r2){
							if (r2 > r1){
								//mvt-circulaire droite
								temp = a[r2];
								for (int u =r2; u> r1; u--){
									a[u]=a[u-1];
								}
								a[r1] = temp;
							}else{
								//mvt-circulaire gauche
								temp = a[r2];
								for (int u =r2; u<r1; u++){
									a[u]=a[u+1]; 
								}
								a[r1] = temp;
							}
						}
						nextPi = new Permutation(a);
						
						
						
						
						pi = nextPi;
						energie=energie+ deltaEnergie;
						if (verboseDetail) System.out.println(i + "\t" + df.format(temperature) + "\t" + energie);
						
						//affichage
						/*text="";
						for (int k =0; k < (energie-2035);k++){
							text+="|";
						}
						System.out.println(i +": "+text);*/
						//System.out.println(i +": n "+(energie-2035) + "  t=" + temperature);
					}else{
						//rien
					}
				}
				temperature *= alpha;
				
				//System.out.println(i + "\t " + df.format(temperature) + "\t " + energie + "\t " );
			}
			
			if (energie > eMin){
				pi = pMin;
				energie=eMin;
				//System.out.println("boom!");
			}
			//System.out.println(energie +" "+derniereIte);
			//dernieresIterations.add(derniereIte);
			
			if (energie == globalEMin) SA.add(pi);
			//SA.add(pi);
		}
		
		//pi = pickARandom(SA);
		myInstance.addSolverPermutations(SA);
		myInstance.setUpperBound(globalEMin);
		myInstance.SA_upper_bound = globalEMin;
		
		//if (verboseResult) System.out.println("Simulated Annealing: eMin= " + globalEMin);
		//if (verboseResult) System.out.println("Approximation by SA("+iniTemperature+","+alpha+","+nbMvts+","+nbRuns+"): "+globalEMin+"  "+pi);
		if (verboseResult) System.out.println("Simulated Annealing: ("+globalEMin+") "+pi);
		if (verboseResult) System.out.println("SA heuristic parameters: "+iniTemperature+" initial temp, "+alpha+" cooling, "+nbMvts+" mvts, "+nbRuns+" runs");
		
	}
	
	//heuritique simulated annealing
	//excellente heuristique probabiliste - la plus efficace pour le moment
	private static void heuristiqueCreerSA_forParameters(Instance myInstance, boolean verboseDetail, boolean verboseResult, int arg0, int arg1, double arg2, double arg3, SortedMap<Integer,Integer> dernieresIterations) {
		HashSet<Permutation> SA = new HashSet<Permutation>();
		Permutation pi = null;
		Permutation nextPi=null;
		int energie =0;
		int deltaEnergie=0;
		int nbRuns=arg0;//nb d'electrons
		int nbMvts=arg1;//nb de mouvements permis
		double temperature=arg2;
		double rand=0;
		double lambda=arg3;
		DecimalFormat df = new DecimalFormat("#.##");
		
		Permutation pMin = null;
		int eMin=999999999;
		int globalEMin=999999999;
		
		//String text="";
		int r1=0,r2=0,n=myInstance.n,temp=0,setSize=myInstance.m;
		int[] a = new int[n];
		
		int derniereIte=0;
		
		//tabD = creationTabD(set1);
		
		if (verboseDetail) System.out.println("SA Details:");
		for (int j=0; j< nbRuns;j++){
			pi = createARandom(n);
			if (verboseDetail) System.out.println("Electron " + j);
			if (verboseDetail) System.out.println("i \ttemp \tenergie");
			temperature=arg2;
			
			energie  = pi.distanceToSetMatrix(myInstance.tabD);
			eMin=999999999;
			derniereIte=-1;
			
			for (int i =0; i < nbMvts;i++){
				
				//faire le randomize ici
				//nextPi  = randomCMoves(pi,1);
				r1= (int)(Math.random()*(n)); //0 <-> (n-1)
				r2= (int)(Math.random()*(n)); //0 <-> (n-1)
				
				
				//calculer deltaEnergie ici
				//deltaEnergie = nextPi.distanceToSetMatrix(tabD) - energie;
				if (r1 != r2){
					if (r2 > r1){
						//mvt-circulaire droite
						temp=0;
						for (int u=r1;u<r2;u++)
							temp += myInstance.tabD[pi.getTab()[r2]-1][pi.getTab()[u]-1];
						deltaEnergie= (- setSize*(r2-r1) + 2*temp);
					}else{
						//mvt-circulaire gauche
						temp=0;
						for (int u=r2+1;u<=r1;u++)
							temp += myInstance.tabD[pi.getTab()[r2]-1][pi.getTab()[u]-1];
						deltaEnergie= (setSize*(r1-r2) - 2*temp);
						//deltaEnergie=99999999;
					}
				}else{
					deltaEnergie=0;
				}
				
				//for the initial temperature study
				if (maxDeltaEnergy < deltaEnergie){
					maxDeltaEnergy = deltaEnergie;
				}


				//desicion ici
				if (deltaEnergie <= 0){
					
					//creation de la nouvelle permutation
					//copie du tab
					for (int u=0;u<n;u++){
						a[u]=pi.getTab()[u];
					}
					//execution du mouvement
					if (r1 != r2){
						if (r2 > r1){
							//mvt-circulaire droite
							temp = a[r2];
							for (int u =r2; u> r1; u--){
								a[u]=a[u-1];
							}
							a[r1] = temp;
						}else{
							//mvt-circulaire gauche
							temp = a[r2];
							for (int u =r2; u<r1; u++){
								a[u]=a[u+1]; 
							}
							a[r1] = temp;
						}
					}
					nextPi = new Permutation(a);
					
					
					
					
					pi = nextPi;
					energie=energie+ deltaEnergie;
					if (verboseDetail) System.out.println(i + "\t" + df.format(temperature) + "\t" + energie);
					
					
					if (energie < eMin){
						eMin = energie;
						pMin =pi;
						if(energie == myInstance.best_upper_bound) {
							derniereIte=i;
							i=nbMvts;//***to finish quickly, it's unecessary to continue calcultations
						}
						if (eMin < globalEMin) {
							globalEMin=eMin;
							SA.clear();
						}
					}
				}else{
					rand = Math.random();
					if (rand < Math.exp(-deltaEnergie/temperature)){
						
						//creation de la nouvelle permutation
						//copie du tab
						for (int u=0;u<n;u++){
							a[u]=pi.getTab()[u];
						}
						//execution du mouvement
						if (r1 != r2){
							if (r2 > r1){
								//mvt-circulaire droite
								temp = a[r2];
								for (int u =r2; u> r1; u--){
									a[u]=a[u-1];
								}
								a[r1] = temp;
							}else{
								//mvt-circulaire gauche
								temp = a[r2];
								for (int u =r2; u<r1; u++){
									a[u]=a[u+1]; 
								}
								a[r1] = temp;
							}
						}
						nextPi = new Permutation(a);
						
						
						
						
						pi = nextPi;
						energie=energie+ deltaEnergie;
						if (verboseDetail) System.out.println(i + "\t" + df.format(temperature) + "\t" + energie);

					}else{
						//rien
					}
				}
				temperature *= lambda;
			}
			
			if (energie > eMin){
				pi = pMin;
				energie=eMin;
				//System.out.println("boom!");
			}
			//System.out.println(energie +" "+derniereIte);
			if (dernieresIterations.containsKey(derniereIte)){
				int value = dernieresIterations.get(derniereIte);
				dernieresIterations.put(derniereIte,value+1);
			}else{
				dernieresIterations.put(derniereIte,1);
			}
			//dernieresIterations.add(derniereIte);
			
			if (energie == globalEMin) SA.add(pi);
			//SA.add(pi);
		}
		
		if (verboseResult) System.out.println("Simulated Annealing: eMin= " + globalEMin);
		
	}
	
	//heuritique simulated annealing
		//excellente heuristique probabiliste - la plus efficace pour le moment
		private static void heuristiqueCreerSA_forParameters2(Instance myInstance, boolean verboseDetail, boolean verboseResult, int arg0, int arg1, double arg2, double arg3, double[] tabAverage) {
			HashSet<Permutation> SA = new HashSet<Permutation>();
			Permutation pi = null;
			Permutation nextPi=null;
			int energie =0;
			int deltaEnergie=0;
			int nbRuns=arg0;//nb d'electrons
			int nbMvts=arg1;//nb de mouvements permis
			double temperature=arg2;
			double rand=0;
			double lambda=arg3;
			DecimalFormat df = new DecimalFormat("#.##");
			
			Permutation pMin = null;
			int eMin=999999999;
			int globalEMin=999999999;
			
			//String text="";
			int r1=0,r2=0,n=myInstance.n,temp=0,setSize=myInstance.m;
			int[] a = new int[n];
			
			int derniereIte=0;
			
			//tabD = creationTabD(set1);
			
			if (verboseDetail) System.out.println("SA Details:");
			for (int j=0; j< nbRuns;j++){
				pi = createARandom(n);
				if (verboseDetail) System.out.println("Electron " + j);
				if (verboseDetail) System.out.println("i \ttemp \tenergie");
				temperature=arg2;
				
				energie  = pi.distanceToSetMatrix(myInstance.tabD);
				eMin=999999999;
				derniereIte=-1;
				
				for (int i =0; i < nbMvts;i++){
					
					//faire le randomize ici
					//nextPi  = randomCMoves(pi,1);
					r1= (int)(Math.random()*(n)); //0 <-> (n-1)
					r2= (int)(Math.random()*(n)); //0 <-> (n-1)
					
					
					//calculer deltaEnergie ici
					//deltaEnergie = nextPi.distanceToSetMatrix(tabD) - energie;
					if (r1 != r2){
						if (r2 > r1){
							//mvt-circulaire droite
							temp=0;
							for (int u=r1;u<r2;u++)
								temp += myInstance.tabD[pi.getTab()[r2]-1][pi.getTab()[u]-1];
							deltaEnergie= (- setSize*(r2-r1) + 2*temp);
						}else{
							//mvt-circulaire gauche
							temp=0;
							for (int u=r2+1;u<=r1;u++)
								temp += myInstance.tabD[pi.getTab()[r2]-1][pi.getTab()[u]-1];
							deltaEnergie= (setSize*(r1-r2) - 2*temp);
							//deltaEnergie=99999999;
						}
					}else{
						deltaEnergie=0;
					}
					
					//for the initial temperature study
					if (maxDeltaEnergy < deltaEnergie){
						maxDeltaEnergy = deltaEnergie;
					}


					//desicion ici
					if (deltaEnergie <= 0){
						
						//creation de la nouvelle permutation
						//copie du tab
						for (int u=0;u<n;u++){
							a[u]=pi.getTab()[u];
						}
						//execution du mouvement
						if (r1 != r2){
							if (r2 > r1){
								//mvt-circulaire droite
								temp = a[r2];
								for (int u =r2; u> r1; u--){
									a[u]=a[u-1];
								}
								a[r1] = temp;
							}else{
								//mvt-circulaire gauche
								temp = a[r2];
								for (int u =r2; u<r1; u++){
									a[u]=a[u+1]; 
								}
								a[r1] = temp;
							}
						}
						nextPi = new Permutation(a);
						
						
						
						
						pi = nextPi;
						energie=energie+ deltaEnergie;
						if (verboseDetail) System.out.println(i + "\t" + df.format(temperature) + "\t" + energie);
						
						
						if (energie < eMin){
							eMin = energie;
							pMin =pi;
							if(energie == myInstance.best_upper_bound) {
								derniereIte=i;
								i=nbMvts;//***to finish quickly, it's unecessary to continue calcultations
							}
							if (eMin < globalEMin) {
								globalEMin=eMin;
								SA.clear();
							}
						}
					}else{
						rand = Math.random();
						if (rand < Math.exp(-deltaEnergie/temperature)){
							
							//creation de la nouvelle permutation
							//copie du tab
							for (int u=0;u<n;u++){
								a[u]=pi.getTab()[u];
							}
							//execution du mouvement
							if (r1 != r2){
								if (r2 > r1){
									//mvt-circulaire droite
									temp = a[r2];
									for (int u =r2; u> r1; u--){
										a[u]=a[u-1];
									}
									a[r1] = temp;
								}else{
									//mvt-circulaire gauche
									temp = a[r2];
									for (int u =r2; u<r1; u++){
										a[u]=a[u+1]; 
									}
									a[r1] = temp;
								}
							}
							nextPi = new Permutation(a);
							
							
							
							
							pi = nextPi;
							energie=energie+ deltaEnergie;
							if (verboseDetail) System.out.println(i + "\t" + df.format(temperature) + "\t" + energie);

						}else{
							//rien
						}
					}
					temperature *= lambda;
					
					tabAverage[i] = tabAverage[i]+ energie;
				}
				
				if (energie > eMin){
					pi = pMin;
					energie=eMin;
					//System.out.println("boom!");
				}
				
				
				if (energie == globalEMin) SA.add(pi);
				//SA.add(pi);
			}
			
			if (verboseResult) System.out.println("Simulated Annealing: eMin= " + globalEMin);
			
		}



	//heuritique simulated annealing
	//to complete
	//excellente heuristique probabiliste - la plus efficace pour le moment
	private static void heuristicSA_wConstraints(Instance myInstance, boolean verboseDetail, boolean verboseResult, int arg0, int arg1, double arg2, double arg3) {
		HashSet<Permutation> SA = new HashSet<Permutation>();
		Permutation pi = null;
		Permutation nextPi=null;
		int energie =0;
		int deltaEnergie=0;
		int nbRuns=arg0;//nb d'electrons
		int nbMvts=arg1;//nb de mouvements permis
		double temperature=arg2;
		double rand=0;
		double lambda=arg3;
		DecimalFormat df = new DecimalFormat("#.##");
		
		Permutation pMin = null;
		int eMin=999999999;
		int globalEMin=999999999;
		
		//String text="";
		int r1=0,r2=0,n=myInstance.n,temp=0,setSize=myInstance.m;
		int[] a = new int[n];
		
		//int derniereIte=0;
		
		/*
		tabC = new boolean [n][n];
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				tabC[i-1][j-1]=false;
			}
		}
		
		MOT3(A,false, false, true, false,999999);
		 */
		
		//tabD = creationTabD(set1);
		
		//pi = createARandom(n);
		pi = generatePermuUnderConstraints(myInstance);
		
		//add here module to write positions limits of elements
		//ude tabC[][]
		
		if (verboseDetail) System.out.println("SA Details:");
		for (int j=0; j< nbRuns;j++){
			if (verboseDetail) System.out.println("Electron " + j);
			if (verboseDetail) System.out.println("i \ttemp \tenergie");
			temperature=arg2;
			
			energie  = pi.distanceToSetMatrix(myInstance.tabD);
			eMin=999999999;
			//derniereIte=0;
			
			for (int i =0; i < nbMvts;i++){
				
				//faire le randomize ici
				//nextPi  = randomCMoves(pi,1);
				r1= (int)(Math.random()*(n)); //0 <-> (n-1)
				r2= (int)(Math.random()*(n)); //0 <-> (n-1)
				
				
				//calculer deltaEnergie ici
				//deltaEnergie = nextPi.distanceToSetMatrix(tabD) - energie;
				if (r1 != r2){
					if (r2 > r1){
						//mvt-circulaire droite
						temp=0;
						for (int u=r1;u<r2;u++)
							temp += myInstance.tabD[pi.getTab()[r2]-1][pi.getTab()[u]-1];
						deltaEnergie= (- setSize*(r2-r1) + 2*temp);
					}else{
						//mvt-circulaire gauche
						temp=0;
						for (int u=r2+1;u<=r1;u++)
							temp += myInstance.tabD[pi.getTab()[r2]-1][pi.getTab()[u]-1];
						deltaEnergie= (setSize*(r1-r2) - 2*temp);
						//deltaEnergie=99999999;
					}
				}else{
					deltaEnergie=0;
				}
				


				//desicion ici
				if (deltaEnergie <= 0){
					
					//creation de la nouvelle permutation
					//copie du tab
					for (int u=0;u<n;u++){
						a[u]=pi.getTab()[u];
					}
					//execution du mouvement
					if (r1 != r2){
						if (r2 > r1){
							//mvt-circulaire droite
							temp = a[r2];
							for (int u =r2; u> r1; u--){
								a[u]=a[u-1];
							}
							a[r1] = temp;
						}else{
							//mvt-circulaire gauche
							temp = a[r2];
							for (int u =r2; u<r1; u++){
								a[u]=a[u+1]; 
							}
							a[r1] = temp;
						}
					}
					nextPi = new Permutation(a);
					
					
					
					
					pi = nextPi;
					energie=energie+ deltaEnergie;
					if (verboseDetail) System.out.println(i + "\t" + df.format(temperature) + "\t" + energie);
					
					//affichage
					/*text="";
					for (int k =0; k < (energie-2035);k++){
						text+="|";
					}
					System.out.println(i +": "+text);*/
					//System.out.println(i +": "+(energie-2035) + "  t=" + temperature);
					
					if (energie < eMin){
						eMin = energie;
						pMin =pi;
						//derniereIte=i;
						if (eMin < globalEMin) {
							globalEMin=eMin;
							SA.clear();
						}
					}
				}else{
					rand = Math.random();
					if (rand < Math.exp(-deltaEnergie/temperature)){
						
						//creation de la nouvelle permutation
						//copie du tab
						for (int u=0;u<n;u++){
							a[u]=pi.getTab()[u];
						}
						//execution du mouvement
						if (r1 != r2){
							if (r2 > r1){
								//mvt-circulaire droite
								temp = a[r2];
								for (int u =r2; u> r1; u--){
									a[u]=a[u-1];
								}
								a[r1] = temp;
							}else{
								//mvt-circulaire gauche
								temp = a[r2];
								for (int u =r2; u<r1; u++){
									a[u]=a[u+1]; 
								}
								a[r1] = temp;
							}
						}
						nextPi = new Permutation(a);
						
						
						
						
						pi = nextPi;
						energie=energie+ deltaEnergie;
						if (verboseDetail) System.out.println(i + "\t" + df.format(temperature) + "\t" + energie);
						
						//affichage
						/*text="";
						for (int k =0; k < (energie-2035);k++){
							text+="|";
						}
						System.out.println(i +": "+text);*/
						//System.out.println(i +": n "+(energie-2035) + "  t=" + temperature);
					}else{
						//rien
					}
				}
				temperature *= lambda;
			}
			
			if (energie > eMin){
				pi = pMin;
				energie=eMin;
				//System.out.println("boom!");
			}
			//System.out.println(energie +" "+derniereIte);
			//dernieresIterations.add(derniereIte);
			
			if (energie == globalEMin) SA.add(pi);
			//SA.add(pi);
		}
		
		if (verboseResult) System.out.println("Simulated Annealing: eMin= " + globalEMin);
		
	}
	
	
	//creation de la matrice de distance pour l'ensemble passe en parametre
	private static int[][] creationTabD(Set<Permutation> a) {
		// TODO Auto-generated method stub
		int n,aDroite,nombre;
		Set<Integer> Reste = new TreeSet<Integer>();
		n = pickARandom(a).getSize();
		int[][] tableauD = new int[n][n];
		//nouvelle facon ~|A|n2
		for (Permutation p: a){
			Reste.clear();
			for (int i=1;i<=n;i++){
				Reste.add(i);
			}
			for (int i=0;i<n;i++){
				nombre = p.getTab()[i];
				Reste.remove(nombre);
				for (Integer ii : Reste){
					tableauD[ii-1][nombre-1]++;
				}
			}
		}
		
		/*
		//ancienne facon ~|A|n3
		for (int i=1;i<=n;i++){
			for (int j=1; j<=n;j++){
				if (i != j){
					//aGauche=0;
					aDroite=0;
					for (Permutation p: a){
						if (p.getPosition(j) < p.getPosition(i)){
							//aGauche++;
						}
						else{
							aDroite++;
						}
					}
					
					//optimisation elagage arbre
					tableauD[j-1][i-1]=aDroite;
					//fin optim
				}
				
			}
			
			
		}*/
		
		return tableauD;
	}



	



	//cree l'ensemble Sn: ensemble de toutes les permutations possibles de n
	public static void creerSn (int n){
		Sn = new HashSet<Permutation>();
		
		Set<Integer> nombres = new HashSet<Integer>();
		for (int i=1; i<=n;i++)
			nombres.add(i);
		
		List<Integer> permuEnCours = new ArrayList<Integer>();

		recuSn(permuEnCours, nombres, n);
		
	}
	
	//fonction recurssive au coeur de la methode creerSn, elle aide a batir Sn
	public static void recuSn(List<Integer> permuEnCours, Set<Integer> nombres, int n){
		int[] a;
		Permutation pi;
		Set<Integer> nombres2;
		List<Integer> permuEnCours2;
		if (n == 0){
			a = new int[permuEnCours.size()];
			for (int j=0; j<permuEnCours.size();j++ )
				a[j]= permuEnCours.get(j);
			
			pi = new Permutation(a);
			Sn.add(pi);
		}
		else{
			for(int i: nombres){
				nombres2 = new HashSet<Integer>();
				permuEnCours2 = new ArrayList<Integer>();
				
				copyIntSet(nombres,nombres2);
				copyIntList(permuEnCours,permuEnCours2);
				
				permuEnCours2.add(i);
				nombres2.remove(i);
				recuSn(permuEnCours2, nombres2, n-1);
			}
		}
	}
	
	//cree l'ensemble Sn: ensemble de toutes les permutations possibles de n
		public static void creerSnUnderConstraints (Instance myInstance){
			SnUnderConstraints = new HashSet<Permutation>();
			
			Set<Integer> nombres = new HashSet<Integer>();
			for (int i=1; i<=myInstance.n;i++)
				nombres.add(i);
			
			List<Integer> permuEnCours = new ArrayList<Integer>();

			recuSnUnderConstraints(myInstance, permuEnCours, nombres, myInstance.n);
			
		}
		
		//fonction recurssive au coeur de la methode creerSn, elle aide a batir Sn
		public static void recuSnUnderConstraints(Instance myInstance, List<Integer> permuEnCours, Set<Integer> nombres, int n){
			int[] a;
			Permutation pi;
			Set<Integer> nombres2;
			List<Integer> permuEnCours2;
			boolean permissionContraintes = true;
			if (n == 0){
				a = new int[permuEnCours.size()];
				for (int j=0; j<permuEnCours.size();j++ )
					a[j]= permuEnCours.get(j);
				
				pi = new Permutation(a);
				SnUnderConstraints.add(pi);
			}
			else{
				for(int i: nombres){
					permissionContraintes = true;
					
					for(int j: nombres){
						if (i != j){
							if (myInstance.tabC[j-1][i-1]){
								permissionContraintes = false;
							}
						}
					}

					
					if (permissionContraintes){
						nombres2 = new HashSet<Integer>();
						permuEnCours2 = new ArrayList<Integer>();
						
						copyIntSet(nombres,nombres2);
						copyIntList(permuEnCours,permuEnCours2);
						
						permuEnCours2.add(i);
						nombres2.remove(i);
						recuSnUnderConstraints(myInstance, permuEnCours2, nombres2, n-1);
					}
				}
			}
		}
		
		
		//cree l'ensemble Sn: ensemble de toutes les permutations possibles de n
	public static Permutation generatePermuUnderConstraints (Instance myInstance){
		SnUnderConstraints = new HashSet<Permutation>();
		
		Set<Integer> nombres = new HashSet<Integer>();
		for (int i=1; i<=myInstance.n;i++)
			nombres.add(i);
		
		List<Integer> permuEnCours = new ArrayList<Integer>();

		return recuPermuUnderConstraints(myInstance,permuEnCours, nombres, myInstance.n);
		
	}
	
	//fonction recurssive au coeur de la methode creerSn, elle aide a batir Sn
	public static Permutation recuPermuUnderConstraints(Instance myInstance,List<Integer> permuEnCours, Set<Integer> nombres, int n){
		int[] a;
		Permutation pi;
		Set<Integer> nombres2;
		List<Integer> permuEnCours2;
		boolean permissionContraintes = true;
		if (n == 0){
			a = new int[permuEnCours.size()];
			for (int j=0; j<permuEnCours.size();j++ )
				a[j]= permuEnCours.get(j);
			
			pi = new Permutation(a);
			return pi;
		}
		else{
			permissionContraintes = false;
			int i = 0;
			while (!permissionContraintes){	
				//for(int i: nombres)
				permissionContraintes = true;
				i = pickARandomInt(nombres);
				for(int j: nombres){
					if (i != j){
						if (myInstance.tabC[j-1][i-1]){
							permissionContraintes = false;
						}
					}
				}
				
			}
				
			nombres2 = new HashSet<Integer>();
			permuEnCours2 = new ArrayList<Integer>();
			
			copyIntSet(nombres,nombres2);
			copyIntList(permuEnCours,permuEnCours2);
			
			permuEnCours2.add(i);
			nombres2.remove(i);
			return  recuPermuUnderConstraints(myInstance, permuEnCours2, nombres2, n-1);
			
		}
	}

	/*public static void creerTabD ( Set<Permutation> a){
		int n;//ordre des permutations
		n=pickARandom(a).getSize();
		tabD = new int [n][n];
		int aGauche=0,aDroite=0;
		for (int i=1;i<=n;i++){
			for (int j=1; j<=n;j++){
				if (i != j){
					aGauche=0;
					aDroite=0;
					for (Permutation p: a){
						if (p.getPosition(j) < p.getPosition(i)){
							aGauche++;
						}
						else{
							aDroite++;
						}
					}
					tabD[j-1][i-1]=aDroite;
				}
			}	
		}
		
	}*/
	
	
	//stats pour article
	public static void lanceurStatsArticle (int taille, int repet, int maxM){
		
	    System.out.println("Statistiques thm always, major 1.0, 2.0, 3.0 vs Betzler pour m variable, n="+taille+" et iterations="+repet);
		System.out.println("nombre de paires: " + (taille*(taille-1)/2));
		System.out.println("");
		
		System.out.println("m" + "\t\t" + "always" + "\t\t" + "major"+ "\t\t" + "major"+ "\t\t" + "major" + "\t\t" + "betzler"
				+ "\t\t" + "betzler"+ "\t\t" + "major" + "\t\t" + "betzler" + "\t\t" + "inclusion rate");
		System.out.println(" " + "\t\t" + " " + "\t\t" + "1.0"+ "\t\t" + "2.0"+ "\t\t" + "3.0" + "\t\t" + " "
				+ "\t\t" + "applic."+ "\t\t" + "3.0 rev" + "\t\t" + "revised" + "\t\t" + "revised");
		
		
	    for (int i =3; i<= 15; i++){
			//statistiquesThmCool
		    statistiquesArticle(i,taille,repet, maxM);
		    //mesure du temp
	    }
	    statistiquesArticle(20,taille,repet, maxM);
	    statistiquesArticle(25,taille,repet, maxM);
	    statistiquesArticle(30,taille,repet, maxM);
	    statistiquesArticle(35,taille,repet, maxM);
	    statistiquesArticle(40,taille,repet, maxM);
	    statistiquesArticle(45,taille,repet, maxM);
	    statistiquesArticle(50,taille,repet, maxM);
	    
		//statistiquesArticle(20,taille,repet, maxM);
	    
	    
	  //mesure du temp
  		lEndTime = new Date().getTime(); //end time
  	    difference = (lEndTime - lStartTime); //check different
  	    System.out.println("Temps pour execution: " +(difference/60000) + " minutes " + ((difference % 60000)/1000) + " secondes " + (difference % 1000) + " millisecondes");
  	    lStartTime = new Date().getTime();
	    
	}
	//stats pour article
	
	
	//stats pour article
	public static void statistiquesArticle (int m, int n, int iterations, int maxM){
		//NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
		//DecimalFormat df = (DecimalFormat)nf;
		DecimalFormat df = new DecimalFormat("#.####");
		
		double nbPaires = (double)(n*(n-1)/2);
		
		if (m <= maxM){
		
		thm_always_stats = 0.0;
		thm_major_1_0_stats = 0.0;
		thm_major_2_0_stats = 0.0;
		thm_major_3_0_stats = 0.0;
		thm_betzler_stats = 0.0;
		
		thm_betzler_application = false;
		thm_betzler_application_stats = 0.0;//combien de fois Betzler est applicable
		thm_major_3_0_revised_count = 0.0;//stats de Major 3.0 sur les donnees Betzler-applicable
		thm_betzler_revised_count = 0.0;//stats de Betzler sur les donnees Betzler-applicable
		thm_major_3_0_revised_stats = 0.0;//stats de Major 3.0 sur les donnees Betzler-applicable
		thm_betzler_revised_stats = 0.0;//stats de Betzler sur les donnees Betzler-applicable
		thms_inclusion_revised_stats = 0.0;//stats sur le nombre de fois ou major est inclu dans betzler
		
		

		for (int repetition=0; repetition< iterations; repetition++){
			thm_betzler_application = false;
			//creerA(false,m,n);
			Instance myInstance = new Instance(m,n);
			//thmBetzler(A);
			thmBetzler_matriciel(myInstance, false, false, false);
			//thmMajor(A, false, false, false);
			//thmMajorEgalite_matriciel(A, false, false, false,true);
			MOT3_LUBC(myInstance,false,false,false,true);//attention change
			if (thm_betzler_application){
				thm_major_3_0_revised_stats += thm_major_3_0_revised_count;
				thm_betzler_revised_stats += thm_betzler_revised_count ;
				if (testInclusionThmBetzlerDansThmCool(myInstance)) thms_inclusion_revised_stats++;
			}
		}
		
		thm_major_1_0_stats += thm_always_stats;
		thm_major_2_0_stats += thm_major_1_0_stats;
		thm_major_3_0_stats += thm_major_2_0_stats;
		
		thm_always_stats /= iterations;
		thm_major_1_0_stats /= iterations;
		thm_major_2_0_stats /= iterations;
		thm_major_3_0_stats /= iterations;
		thm_betzler_stats /= iterations;
		
		
		thm_major_3_0_revised_stats /= thm_betzler_application_stats;//stats de Major 3.0 sur les donnees Betzler-applicable
		thm_betzler_revised_stats /= thm_betzler_application_stats;//stats de Betzler sur les donnees Betzler-applicable
		thms_inclusion_revised_stats /= thm_betzler_application_stats;//stats sur le nombre de fois ou major est inclu dans betzler
		
		thm_betzler_application_stats /= iterations;//combien de fois Betzler est applicable en %


		//pour avoir les statistiques de resolution en poucentage des paires
		thm_always_stats /= nbPaires;
		thm_major_1_0_stats /= nbPaires;
		thm_major_2_0_stats /= nbPaires;
		thm_major_3_0_stats /= nbPaires;
		thm_betzler_stats /= nbPaires;
		thm_major_3_0_revised_stats /= nbPaires;
		thm_betzler_revised_stats /= nbPaires;
		
		
		
		/*System.out.println("Statistiques thm cool pour m="+m+", n="+n+" et interations="+iterations);
		//System.out.println("thm_tjrs_count: " + thm_tjrs_stats);
		//System.out.println("thm_cool_count: " + thm_cool_stats);
		System.out.println("nombre de paires: " + (n*(n-1)/2));
		System.out.println("");*/
		//System.out.println(m + "\t" + thm_tjrs_stats + "\t" + thm_cool_stats);
		//System.out.println(m + "\t" + df.format(thm_tjrs_stats) + "\t" + df.format(thm_cool_stats));
		//System.out.println(m + "\t" + df.format(thm_tjrs_stats) + "\t" + df.format(thm_cool_stats)+ "\t" + df.format(fermeture_stats));
		//System.out.println(m + "\t" + df.format(thm_tjrs_stats) + "\t" + df.format(thm_cool_stats)+ "\t" + df.format(thm_cool_extend_stats)+ "\t" + df.format(fermeture_stats) + "\t" + df.format(resolution_stats));
		System.out.println(m + "\t\t" + df.format(thm_always_stats) + "\t\t" + df.format(thm_major_1_0_stats)+ "\t\t" +
				df.format(thm_major_2_0_stats)+ "\t\t" + df.format(thm_major_3_0_stats) + "\t\t" + df.format(thm_betzler_stats)
				+ "\t\t" + df.format(thm_betzler_application_stats)+ "\t\t" + df.format(thm_major_3_0_revised_stats) 
				+ "\t\t" + df.format(thm_betzler_revised_stats) + "\t\t" + df.format(thms_inclusion_revised_stats));

		}else{
			System.out.println(m + "\t\t" + "n/a" + "\t\t" + "n/a"+ "\t\t" +
					"n/a" + "\t\t" + "n/a" + "\t\t" + "n/a"
					+ "\t\t" + "n/a" + "\t\t" + "n/a" 
					+ "\t\t" + "n/a" + "\t\t" + "n/a");
		}
		
	}
	//fin stats pour article
	
	
	
	
	//stats pour article
	public static void lanceurStatsArticle2 (int taille, int repet, int maxM){
		
	    System.out.println("Statistiques thm always, Major 1.0, 2.0 et 3.0 pour m variable, n="+taille+" et iterations="+repet);
		System.out.println("nombre de paires: " + (taille*(taille-1)/2));
		System.out.println("");
		
		System.out.println("m" + "\t\t" + "always" + "\t\t" + "major"+ "\t\t" + "major"+ "\t\t" + "major");
		System.out.println(" " + "\t\t" + " " + "\t\t" + "1.0"+ "\t\t" + "2.0"+ "\t\t" + "3.0");
		
		
	    for (int i =3; i<= 15; i++){
			//statistiquesThmCool
		    statistiquesArticle2(i,taille,repet, maxM);
		    //mesure du temp
	    }
	    statistiquesArticle2(20,taille,repet, maxM);
	    statistiquesArticle2(25,taille,repet, maxM);
	    statistiquesArticle2(30,taille,repet, maxM);
	    statistiquesArticle2(35,taille,repet, maxM);
	    statistiquesArticle2(40,taille,repet, maxM);
	    statistiquesArticle2(45,taille,repet, maxM);
	    statistiquesArticle2(50,taille,repet, maxM);
	    
	  //mesure du temp
  		lEndTime = new Date().getTime(); //end time
  	    difference = (lEndTime - lStartTime); //check different
  	    System.out.println("Temps pour execution: " +(difference/60000) + " minutes " + ((difference % 60000)/1000) + " secondes " + (difference % 1000) + " millisecondes");
  	    lStartTime = new Date().getTime();
	    
	}
	//stats pour article
	
	
	//stats pour article2
	//pas de Betzler
	public static void statistiquesArticle2 (int m, int n, int iterations, int maxM){
		//NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
		//DecimalFormat df = (DecimalFormat)nf;
		DecimalFormat df = new DecimalFormat("#.####");
		
		double nbPaires = (double)(n*(n-1)/2);
		
		if (m <= maxM){
		
		thm_always_stats = 0.0;
		thm_major_1_0_stats = 0.0;
		thm_major_2_0_stats = 0.0;
		thm_major_3_0_stats = 0.0;
		

		for (int repetition=0; repetition< iterations; repetition++){
			//creerA(false,m,n);
			Instance myInstance = new Instance(m,n);
			//thmMajor(A, false, false, false);
			//thmMajorEgalite_matriciel(A, false, false, false,false);
			MOT3_LUBC(myInstance,false,false,false,false);//attention change
			
		}
		
		thm_major_1_0_stats += thm_always_stats;
		thm_major_2_0_stats += thm_major_1_0_stats;
		thm_major_3_0_stats += thm_major_2_0_stats;
		
		thm_always_stats /= iterations;
		thm_major_1_0_stats /= iterations;
		thm_major_2_0_stats /= iterations;
		thm_major_3_0_stats /= iterations;



		//pour avoir les statistiques de resolution en poucentage des paires
		thm_always_stats /= nbPaires;
		thm_major_1_0_stats /= nbPaires;
		thm_major_2_0_stats /= nbPaires;
		thm_major_3_0_stats /= nbPaires;
		

		System.out.println(m + "\t\t" + df.format(thm_always_stats) + "\t\t" + df.format(thm_major_1_0_stats)+ "\t\t" +
				df.format(thm_major_2_0_stats)+ "\t\t" + df.format(thm_major_3_0_stats));

		}else{
			System.out.println(m + "\t\t" + "n/a" + "\t\t" + "n/a"+ "\t\t" +
					"n/a" + "\t\t" + "n/a");
		}
		
	}
	//fin stats pour article2
	
	
	
	
	//stats pour article journal
		public static void lanceurStatsArticleJournal (int n, int repet, int maxM, boolean egalite){
			
		    System.out.println("Statistiques thm always, Major 3.0, 3/4-MR pour m variable, n="+n+" et iterations="+repet);
		    if (egalite) {
		    	System.out.println("  avec egalite");
		    }else{
		    	System.out.println("  sans egalite");
		    }
		    	
			System.out.println("nombre de paires: " + (n*(n-1)/2));
			System.out.println("");
			
			
			System.out.println("m" + "\t\t" + "always" + "\t\t" + "major" + "\t\t" + "3/4-MR"
					+ "\t\t" + "3/4-MR"+ "\t\t" + "major" + "\t\t" + "3/4-MR" + "\t\t" + "incl.rate"
					+ "\t" + "iter" + "\t\t" + "iter");
			System.out.println(" " + "\t\t" + " " + "\t\t" + "3.0" + "\t\t" + " "
					+ "\t\t" + "applic."+ "\t\t" + "3.0 rev" + "\t\t" + "revised" + "\t\t" + "revised"
					+ "\t\t" + "Avg" + "\t\t" + "Max");
			
			
		    /*for (int i =3; i<= 15; i++){
				//statistiquesThmCool
			    statistiquesArticleJournal(i,n,repet, maxM, egalite);
			    //mesure du temp
		    }*/
			statistiquesArticleJournal(3,n,repet, maxM, egalite);
			statistiquesArticleJournal(4,n,repet, maxM, egalite);
			statistiquesArticleJournal(5,n,repet, maxM, egalite);
			statistiquesArticleJournal(6,n,repet, maxM, egalite);
			statistiquesArticleJournal(10,n,repet, maxM, egalite);
			statistiquesArticleJournal(15,n,repet, maxM, egalite);
			
		    statistiquesArticleJournal(20,n,repet, maxM, egalite);
		    statistiquesArticleJournal(25,n,repet, maxM, egalite);
		    statistiquesArticleJournal(30,n,repet, maxM, egalite);
		    statistiquesArticleJournal(35,n,repet, maxM, egalite);
		    statistiquesArticleJournal(40,n,repet, maxM, egalite);
		    statistiquesArticleJournal(45,n,repet, maxM, egalite);
		    statistiquesArticleJournal(50,n,repet, maxM, egalite);
		    statistiquesArticleJournal(100,n,repet, maxM, egalite);
		    statistiquesArticleJournal(101,n,repet, maxM, egalite);
		    
		  //mesure du temp
	  		lEndTime = new Date().getTime(); //end time
	  	    difference = (lEndTime - lStartTime); //check different
	  	    System.out.println("Temps pour execution: " +(difference/60000) + " minutes " + ((difference % 60000)/1000) + " secondes " + (difference % 1000) + " millisecondes");
	  	    lStartTime = new Date().getTime();
		    
		}
		//stats pour article
		
		
		//stats pour article journal
		public static void statistiquesArticleJournal (int m, int n, int iterations, int maxM, boolean egalite){
			//NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
			//DecimalFormat df = (DecimalFormat)nf;
			DecimalFormat df = new DecimalFormat("#.####");
			
			double nbPaires = (double)(n*(n-1)/2);
			
			if (m <= maxM){
			
			thm_always_stats = 0.0;
			thm_major_3_0_stats = 0.0;
			thm_betzler_stats = 0.0;
			thm_MOT3_iterationAvg_stats = 0.0;
			thm_MOT3_iterationMax_stats = 0.0;
			
			thm_betzler_application = false;
			thm_betzler_application_stats = 0.0;//combien de fois Betzler est applicable
			thm_major_3_0_revised_count = 0.0;//stats de Major 3.0 sur les donnees Betzler-applicable
			thm_betzler_revised_count = 0.0;//stats de Betzler sur les donnees Betzler-applicable
			thm_major_3_0_revised_stats = 0.0;//stats de Major 3.0 sur les donnees Betzler-applicable
			thm_betzler_revised_stats = 0.0;//stats de Betzler sur les donnees Betzler-applicable
			thms_inclusion_revised_stats = 0.0;//stats sur le nombre de fois ou major est inclu dans betzler
			

			for (int repetition=0; repetition< iterations; repetition++){
				Instance myInstance = new Instance(m,n);
				//MOT3(null,A, false, false, false, egalite,-1);//*modified inst
				MOT3_LUBC(myInstance, false, false, false, egalite);//*modified inst
				
				thm_betzler_application = false;
				thmBetzler_matriciel(myInstance, false, false, false);
				if (thm_betzler_application){
					thm_major_3_0_revised_stats += thm_major_3_0_revised_count;
					if (testInclusionThmBetzlerDansThmCool(myInstance)) thms_inclusion_revised_stats++;
				}
			}
			
			thm_always_stats /= iterations;//stats par cas
			thm_major_3_0_stats /= iterations;
			thm_betzler_stats /= iterations;
			thm_MOT3_iterationAvg_stats  /= iterations;
			
			thm_major_3_0_revised_stats /= thm_betzler_application_stats;//stats de Major 3.0 sur les donnees Betzler-applicable
			thm_betzler_revised_stats /= thm_betzler_application_stats;//stats de Betzler sur les donnees Betzler-applicable
			thms_inclusion_revised_stats /= thm_betzler_application_stats;//stats sur le nombre de fois ou major est inclu dans betzler
			thm_betzler_application_stats /= iterations;//combien de fois Betzler est applicable en %
			
			//pour avoir les statistiques de resolution en poucentage des paires
			thm_always_stats /= nbPaires;
			thm_major_3_0_stats /= nbPaires;
			thm_betzler_stats /= nbPaires;
			thm_major_3_0_revised_stats /= nbPaires;
			thm_betzler_revised_stats /= nbPaires;
			
				if (thm_betzler_application_stats >= 0.0001){
					System.out.println(m + "\t\t" + df.format(thm_always_stats) + "\t\t" + df.format(thm_major_3_0_stats) + "\t\t" + df.format(thm_betzler_stats)
						+ "\t\t" + df.format(thm_betzler_application_stats)+ "\t\t" + df.format(thm_major_3_0_revised_stats) 
						+ "\t\t" + df.format(thm_betzler_revised_stats) + "\t\t" + df.format(thms_inclusion_revised_stats)
						+ "\t\t" + df.format(thm_MOT3_iterationAvg_stats) + "\t\t" + df.format(thm_MOT3_iterationMax_stats));
				}else{
					System.out.println(m + "\t\t" + df.format(thm_always_stats) + "\t\t" + df.format(thm_major_3_0_stats) + "\t\t" + df.format(thm_betzler_stats)
							+ "\t\t" + df.format(thm_betzler_application_stats)+ "\t\t" + "n/a" 
							+ "\t\t" + "n/a" + "\t\t" + "n/a"
							+ "\t\t" + df.format(thm_MOT3_iterationAvg_stats) + "\t\t" + df.format(thm_MOT3_iterationMax_stats));
				}

			}else{
				System.out.println(m + "\t\t" + "n/a" + "\t\t" + "n/a" + "\t\t" + "n/a"
						+ "\t\t" + "n/a" + "\t\t" + "n/a" 
						+ "\t\t" + "n/a" + "\t\t" + "n/a" 
						+ "\t\t" + "n/a" + "\t\t" + "n/a");
			}

		}
		//fin stats pour article journal
	
	//stats pour article journal sans Betzler
			public static void lanceurStatsArticleJournal2 (int n, int repet, int maxM, boolean egalite){
				
			    System.out.println("Statistiques thm always, Major 3.0 pour m variable, n="+n+" et iterations="+repet);
				System.out.println("nombre de paires: " + (n*(n-1)/2));
				System.out.println("");
				
				
				System.out.println("m" + "\t\t" + "always" + "\t\t" + "major");
				System.out.println(" " + "\t\t" + " " + "\t\t" + "3.0");
				
				
			    for (int i =3; i<= 15; i++){
					//statistiquesThmCool
				    statistiquesArticleJournal2(i,n,repet, maxM, egalite);
				    //mesure du temp
			    }
			    statistiquesArticleJournal2(20,n,repet, maxM, egalite);
			    statistiquesArticleJournal2(25,n,repet, maxM, egalite);
			    statistiquesArticleJournal2(30,n,repet, maxM, egalite);
			    statistiquesArticleJournal2(35,n,repet, maxM, egalite);
			    statistiquesArticleJournal2(40,n,repet, maxM, egalite);
			    statistiquesArticleJournal2(45,n,repet, maxM, egalite);
			    statistiquesArticleJournal2(50,n,repet, maxM, egalite);
			    
			  //mesure du temp
		  		lEndTime = new Date().getTime(); //end time
		  	    difference = (lEndTime - lStartTime); //check different
		  	    System.out.println("Temps pour execution: " +(difference/60000) + " minutes " + ((difference % 60000)/1000) + " secondes " + (difference % 1000) + " millisecondes");
		  	    lStartTime = new Date().getTime();
			    
			}
			//stats pour article
			
			
			//stats pour article journal
			public static void statistiquesArticleJournal2 (int m, int n, int iterations, int maxM, boolean egalite){
				//NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
				//DecimalFormat df = (DecimalFormat)nf;
				DecimalFormat df = new DecimalFormat("#.####");
				
				double nbPaires = (double)(n*(n-1)/2);
				
				if (m <= maxM){
				
				thm_always_stats = 0.0;
				thm_major_3_0_stats = 0.0;

				

				for (int repetition=0; repetition< iterations; repetition++){
					Instance myInstance = new Instance(m,n); 
					//MOT3(null,A, false, false, false, egalite,-1);//*modified inst
					MOT3_LUBC(myInstance, false, false, false, egalite);//*modified inst
				}
				
				thm_always_stats /= iterations;
				thm_major_3_0_stats /= iterations;

				
				//pour avoir les statistiques de resolution en poucentage des paires
				thm_always_stats /= nbPaires;
				thm_major_3_0_stats /= nbPaires;


				System.out.println(m + "\t\t" + df.format(thm_always_stats) + "\t\t" + df.format(thm_major_3_0_stats));

				}else{
					System.out.println(m + "\t\t" + "n/a" + "\t\t" + "n/a");
				}

			}
			//fin stats pour article journal
			
			
			
			//stats pour article en devenir (iwoca 2017)
			public static void lanceurStatsArticle3 (int n, int nbInstances, int maxM, boolean egalite){
				String bidon = ""; 
			    System.out.println("Statistics for Always Thm and Major Order Thm 3.0(_,e)(_,+,++) for variable m, n="+n+" on "+nbInstances + " instances");
				System.out.println("nombre de paires: " + (n*(n-1)/2));
				System.out.println("");
				
				bidon += "Statistics for Always Thm and Major Order Thm 3.0(_,e)(_,+,++) for variable m, n="+n+" on "+nbInstances + " instances"+ "\n"; 
				bidon += "nombre de paires: " + (n*(n-1)/2)+ "\n"; 
				bidon += ""+ "\n"; 
				
				System.out.println("m" + "\t\t" + "always" + "\t\t" + "major"+ "\t\t" + "iter" + "\t\t" + "major"+ "\t\t" + "iter" + "\t\t" +"major"+ "\t\t" + "iter");
				bidon += "m" + "\t\t" + "always" + "\t\t" + "major"+ "\t\t" + "iter" + "\t\t" + "major"+ "\t\t" + "iter" + "\t\t" +"major"+ "\t\t" + "iter"+ "\n"; 
				if (egalite){
					System.out.println(" " + "\t\t" + "thm" + "\t\t" + "3.0e"+ "\t\t" + "Avg" + "\t\t" +"3.0e+"+ "\t\t" + "Avg" + "\t\t" +"3.0e++"+ "\t\t" + "Avg");
					bidon += " " + "\t\t" + "thm" + "\t\t" + "3.0e"+ "\t\t" + "Avg" + "\t\t" +"3.0e+"+ "\t\t" + "Avg" + "\t\t" +"3.0e++"+ "\t\t" + "Avg"+ "\n"; 
				}else{
					System.out.println(" " + "\t\t" + "thm" + "\t\t" + "3.0"+ "\t\t" + "Avg" + "\t\t" +"3.0+"+ "\t\t" + "Avg" + "\t\t" +"3.0++"+ "\t\t" + "Avg");
					bidon += " " + "\t\t" + "thm" + "\t\t" + "3.0"+ "\t\t" + "Avg" + "\t\t" +"3.0+"+ "\t\t" + "Avg" + "\t\t" +"3.0++"+ "\t\t" + "Avg"+ "\n"; 
				}
				
				
				
			    for (int i =3; i<= 15; i++){
					//statistiquesThmCool
			    	bidon += statistiquesArticle3(i,n,nbInstances, maxM, egalite);
				    //mesure du temp
			    }
			    bidon += statistiquesArticle3(20,n,nbInstances, maxM, egalite);
			    bidon += statistiquesArticle3(25,n,nbInstances, maxM, egalite);
			    bidon += statistiquesArticle3(30,n,nbInstances, maxM, egalite);
			    bidon += statistiquesArticle3(35,n,nbInstances, maxM, egalite);
			    bidon += statistiquesArticle3(40,n,nbInstances, maxM, egalite);
			    bidon += statistiquesArticle3(45,n,nbInstances, maxM, egalite);
			    bidon += statistiquesArticle3(50,n,nbInstances, maxM, egalite);
			    
			    //mesure du temp
		  		lEndTime = new Date().getTime(); //end time
		  	    difference = (lEndTime - lStartTime); //check different
		  	    System.out.println("Temps pour execution: " +(difference/60000) + " minutes " + ((difference % 60000)/1000) + " secondes " + (difference % 1000) + " millisecondes");
		  	    bidon +="Temps pour execution: " +(difference/60000) + " minutes " + ((difference % 60000)/1000) + " secondes " + (difference % 1000) + " millisecondes"+ "\n";
		  	    lStartTime = new Date().getTime();
		  	    
		  	    writeStatsArticle3InFile(bidon, "StatsContraintesApproxPaire/statsContraintesApproxPaire_n_"+n+"_equality_"+egalite+"_nbInstances_"+nbInstances+".txt");
			    
			}
			//stats pour article
			
			private static void writeStatsArticle3InFile(String texte, String fichier) {
				// TODO Auto-generated method stub
				try{
					// Create file 
					FileWriter fstream = new FileWriter(fichier,false);
					BufferedWriter out = new BufferedWriter(fstream);

					out.write(texte);
					out.write("\n");
					

					//Close the output stream
					out.close();
				}catch (Exception e){//Catch exception if any
					System.err.println("Error: " + e.getMessage());
				}
			}
			
			
			//stats pour article en devenir iwoca2017
			public static String statistiquesArticle3 (int m, int n, int iterations, int maxM, boolean egalite){
				//NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
				//DecimalFormat df = (DecimalFormat)nf;
				DecimalFormat df = new DecimalFormat("#.####");
				
				String answer = "";
				
				double nbPaires = (double)(n*(n-1)/2);
				
				if (m <= maxM){
				
				thm_always_stats = 0.0;
				thm_major_3_0_stats = 0.0;
				thm_MOT3_iterationAvg_stats = 0.0;
				
				thm_major_3_0p_stats = 0.0;
				thm_MOT3p_iterationAvg_stats = 0.0;
				thm_major_3_0pp_stats = 0.0;
				thm_MOT3pp_iterationAvg_stats = 0.0;
				

				for (int repetition=0; repetition< iterations; repetition++){
					//creerA(false,m,n);
					Instance myInstance = new Instance(m,n);
					
					//SA
					//int nbElectrons =8;
					//int nbMvts = 15000;
					//double temperature = (m*0.25+4.0)*n;
					//double refroidissement = 0.995;
					Permutation pi = null;
					//heuristiqueCreerSA(A,false, false,nbElectrons, nbMvts,temperature,refroidissement);
					//heuristiqueCreerSA(null,A,false, false,3);//*modified inst
					heuristiqueCreerSA(myInstance,false, false,3);
					//pi = pickARandom(SA);
					//distMedianApprox = pi.distanceToSetMatrix(myInstance.tabD);

					
					//MOT3(null,A, false, false, false, egalite,distMedianApprox);//*modified inst
					MOT3_LUBC(myInstance, false, false, false, egalite);//*modified inst
					//MOT3(A, false, false, false, egalite,-1);
				}
				
				thm_always_stats /= iterations;
				
				thm_major_3_0_stats /= iterations;
				thm_MOT3_iterationAvg_stats  /= iterations;
				thm_major_3_0p_stats /= iterations;
				thm_MOT3p_iterationAvg_stats  /= iterations;
				thm_major_3_0pp_stats /= iterations;
				thm_MOT3pp_iterationAvg_stats  /= iterations;
				
				//pour avoir les statistiques de resolution en poucentage des paires
				thm_always_stats /= nbPaires;
				thm_major_3_0_stats /= nbPaires;
				thm_major_3_0p_stats /= nbPaires;
				thm_major_3_0pp_stats /= nbPaires;


				System.out.println(m + "\t\t" + df.format(thm_always_stats) + "\t\t" + df.format(thm_major_3_0_stats)+ "\t\t" + df.format(thm_MOT3_iterationAvg_stats)
						+ "\t\t" + df.format(thm_major_3_0p_stats)+ "\t\t" + df.format(thm_MOT3p_iterationAvg_stats)
						+ "\t\t" + df.format(thm_major_3_0pp_stats)+ "\t\t" + df.format(thm_MOT3pp_iterationAvg_stats));
				answer = m + "\t\t" + df.format(thm_always_stats) + "\t\t" + df.format(thm_major_3_0_stats)+ "\t\t" + df.format(thm_MOT3_iterationAvg_stats)
				+ "\t\t" + df.format(thm_major_3_0p_stats)+ "\t\t" + df.format(thm_MOT3p_iterationAvg_stats)
				+ "\t\t" + df.format(thm_major_3_0pp_stats)+ "\t\t" + df.format(thm_MOT3pp_iterationAvg_stats)+ "\n";

				}else{
					System.out.println(m + "\t\t" + "n/a" + "\t\t" + "n/a"+ "\t\t" + "n/a"+ "\t\t" + "n/a"+ "\t\t" + "n/a");
					answer = m + "\t\t" + "n/a" + "\t\t" + "n/a"+ "\t\t" + "n/a"+ "\t\t" + "n/a"+ "\t\t" + "n/a"+ "\n";
				}

				return answer;
			}
			//fin stats pour article en devenir
			
	
	
	/*
	//etude pour trouver un contre-exemple
	public static void etudeInclusionThmBetzlerDansThmCool (){
		boolean inclusion = false;
		System.out.println("Etude inclusion Betzler ds Cool");
		for (int i=1; i<10000; i++){
			//creerA(false,4,8);
			Instance myInstance = new Instance(4,8);
			thmBetzler(myInstance);	
			thmCool_avecFermeture(A);
			inclusion = testInclusionThmBetzlerDansThmCool();
			System.out.print(inclusion);
			if (!inclusion){
				System.out.println("***********Contre-exemple************");
				printA();
			}
		}
	}*/
	
	public void initialisateurVsAntaki(int ite, int m, int n){
		
		System.out.println("InitialisateurVsAntaki");
		//fichier
		String fichier = "VsAntaki/randomSets_ite"+ite+"_m"+ m +"_n"+n+".txt";

		for (int i=0; i<ite; i++){
			Instance myInstance = new Instance(m,n);
			myInstance.writeIntoFile(fichier, true);
			System.out.print(".");
		}
		
		System.out.println("Done");
		
		
	}
	
	//fonction qui prend en entree un fichier avec des ensembles de departs et qui trouve les contraintes pour chaque cas
	//output en format Antaki
	public static void solverVsAntaki (String fichier){

		System.out.println("SolverVsAntaki");
		
		
		//fichier prealable
		//traitement du dossier
		File f = new File("VsAntaki/solved_"+fichier);
		if (!f.exists()) {
			//System.out.println(f.getPath());
			//System.out.println(f.getParent());
			if (!f.getParentFile().exists())
				f.getParentFile().mkdirs();
			
		} else{
			//rien
		}
		try{
			// Create file 
			FileWriter fstream = new FileWriter("VsAntaki/solved_"+fichier,false);//file access on append mode
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("");
			out.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		
		
		
		//lecture
        FileReader reader ;
        try{
        	reader = new FileReader("VsAntaki/"+fichier);
        	Scanner in = new Scanner(reader);
        	while (in.hasNext()){
        		String line = in.nextLine();
        		Instance myInstance =new Instance(line);
				MOT3_LUBC(myInstance,false,false, false, false);//attention change
        		
				//concatenation des contraintes en un string
				String ligne="";
				for (IntPair p : contraintesCool){
					ligne+=p.toString2()+",";
				}
				if (ligne.length() > 0)
					ligne=ligne.substring(0, ligne.length()-1);
				
				//dans un fichier
				//System.out.println(ligne);
				System.out.print(".");
				
				
				
				
				//traitement du fichier
				try{
					// Create file 
					FileWriter fstream = new FileWriter("VsAntaki/solved_"+fichier,true);//file access on append mode
					BufferedWriter out = new BufferedWriter(fstream);
					out.write(ligne);
					out.write("\n");
					out.close();
				}catch (Exception e){//Catch exception if any
					System.err.println("Error: " + e.getMessage());
				}
        		
        	}
        	in.close();
        	
	    } catch (FileNotFoundException e){
	        System.out.println("Fichier "+ fichier +" n'a pas été trouvé");
	    }
	    
		System.out.println("Done");
	}
	
	//methode qui teste si les contraintes trouvees par le thm de betzler se retrouvent dans les contraintes trouvees par le thm cool
	public static boolean testInclusionThmBetzlerDansThmCool (Instance myInstance){
		boolean silencieux = true;
		boolean inclusion = true;
		
		if (!silencieux) System.out.println();
		if (!silencieux) System.out.println(" **Betzler vs Thm Cool**");
		
		int n=myInstance.n;
		
		//matriciel
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				if (tabContraintesBetzler[i-1][j-1] && (!myInstance.tabC[i-1][j-1])){
					inclusion = false;
					if (!silencieux) System.out.println("contrainte non-trouvee: (" + i + "," + j + ")");
				}
			}
		}
		
		/*
		for (IntPair p : contraintesBetzler){
			//rapide - matriciel
			if (!tabC[p.x-1][p.y-1]){
				if (!silencieux) System.out.println("contrainte non-trouvee:" + p);
				inclusion = false;
			}
			//normal - ensembles
			//if (!contraintesCool.contains(p)){
			//	if (!silencieux) System.out.println("contrainte non-trouvee:" + p);
			//	inclusion = false;
			//}
		}*/
		
		
		if (!silencieux) System.out.println("Inclusion contraintes Betzler ds contraintes Cool : " + inclusion);
		if (!silencieux) System.out.println();
		return inclusion;
	}
	
	//thm Betzler avec non-dirty candidate
	public static void thmBetzler (Instance myInstance){
		
		//declarations et initialisations des variables
		int n;//ordre des permutations
		//List<IntPair> contraintes = new ArrayList<IntPair>(); //liste des contraintes
		List<Integer> NonDirtyCandidates= new ArrayList<Integer>(); //liste des contraintes
		
		n=myInstance.n;//taille des permutations
		double m = (double)myInstance.m; //nombre de permutations
		double nbPermu1avant2 = 0.0;
		//creerTabD(a);//creation de la table tabD qui indique avec 'tabD[i][j]' combien de fois 'i' est a droite de 'j'
		
		boolean silencieux = false;//si on veux avoir les calculs affiches
		boolean affichage_paires = false;//si on veux avoir les paires des thms affichees pour le probleme courant
		boolean affichage_stats = true;//si on veux avoir les stats des thms affichees pour le probleme courant
		
		boolean nonDirtyCandidate = false;
		boolean proceed = true;
		int thm_betzler_iteration=0;
		int thm_betzler_count = 0;
		int nonDirtyPairs =0;
		tabContraintesBetzler = new boolean[n][n];
		//fin des declarations et initialisations des variables
		

		
		//affichage debut
		if (!silencieux) System.out.println("");
		if (!silencieux) System.out.println(" *** Evaluation du thm de Betzler ***");
		if (!silencieux) System.out.println("");
		
		if (affichage_paires) System.out.println("");
		if (affichage_paires) System.out.println(" **Betzler**");
		//fin affichage debut
		
		
		
		//test vendredi
		for (int element1 = 1; element1<=n; element1++){//on met toutes les contraintes a false, car aucune contrainte n'est connue au depart
			for (int element2 = element1 + 1; element2<=n; element2++){
				if (element2 != element1){
					nbPermu1avant2 = (double)myInstance.tabD[element2-1][element1-1];
					if ((nbPermu1avant2/m >= 0.75) || (nbPermu1avant2/m <= 0.25)){
						nonDirtyPairs++;
						//System.out.println("nonDirtyPair: "+ element1 + " " + element2);
					}
				}
			}
		}
		System.out.println("#nonDirtyPair: " + nonDirtyPairs);
		//fin test ven.
		
		
		
		//calcul
		for (int element1 = 1; element1<=n; element1++){//on met toutes les contraintes a false, car aucune contrainte n'est connue au depart
			for (int element2 = 1; element2<=n; element2++){
				tabContraintesBetzler[element1-1][element2-1]=false;
			}
		}
		while (proceed){
			proceed = false;
			thm_betzler_iteration++;
			
			for (int element1 = 1; element1<=n; element1++){//pour chaque element, on verifie s'il est un non-dirty candidate
				nonDirtyCandidate = true;//on suppose que oui
				for (int element2 = 1; element2<=n; element2++){//on teste avec chaque autre element
					if (element2 != element1){
						nbPermu1avant2 = (double)myInstance.tabD[element2-1][element1-1];
						if ((nbPermu1avant2/m >= 0.75) || (nbPermu1avant2/m <= 0.25)){
							//reste un nonDirtyCandidate
						}else if ((tabContraintesBetzler[element1-1][element2-1]) || (tabContraintesBetzler[element2-1][element1-1])){
							//reste un nonDirtyCandidate
						}else{
							nonDirtyCandidate = false;
						}		
					}
				}//fin boucle 2
				
				if (nonDirtyCandidate && (!NonDirtyCandidates.contains(element1))){//it's a new non-dirty candidate
					NonDirtyCandidates.add(element1);
					if (!silencieux)  System.out.println("\t new non-dirty candidate: " + element1);
					proceed = true; //because we found a non-dirty candidate, so we'll have to reapply the theorem on each side
					for (int element2 = 1; element2<=n; element2++){//boucle 2
						if ((element2 != element1) && (!tabContraintesBetzler[element1-1][element2-1])&& (!tabContraintesBetzler[element2-1][element1-1])){
							if (myInstance.tabD[element1-1][element2-1] < myInstance.tabD[element2-1][element1-1]){//si element1 est maj avant element2
								//contraintes.add(new IntPair(element1,element2));
								tabContraintesBetzler[element1-1][element2-1]=true;
								if (!silencieux)  System.out.println("\t Thm betzler("+thm_betzler_iteration+") :  ordre majoritaire respecte " + element1 + " " + element2);
								if (affichage_paires) System.out.println("thm betzler("+thm_betzler_iteration+") " + element1 + ":" + element2);
							}else{
								//contraintes.add(new IntPair(element2,element1));
								tabContraintesBetzler[element2-1][element1-1]=true;
								if (!silencieux)  System.out.println("\t Thm betzler("+thm_betzler_iteration+") :  ordre majoritaire respecte " + element2 + " " + element1);
								if (affichage_paires) System.out.println("thm betzler("+thm_betzler_iteration+") " + element2 + ":" + element1);
							}	
						}
					}//fin boucle 2
					//contraintes = fermeture_contraintes(contraintes,1,n,!silencieux,affichage_paires);

				}
			}//fin boucle 1
			
			//bloc fermeture transitive
			for (int i = 1; i<=n; i++){//on met toutes les contraintes a false, car aucune contrainte n'est connue au depart
				for (int j = 1; j<=n; j++){
					for (int k = 1; k<=n; k++){
						if ((i != j)&&(j != k)&&(i != k)){
							if (tabContraintesBetzler[i-1][k-1] && tabContraintesBetzler[k-1][j-1] && !tabContraintesBetzler[i-1][j-1]){
								tabContraintesBetzler[i-1][j-1] = true;
								if (!silencieux)  System.out.println("\t fermeture :  ordre majoritaire respecte " + i + " " + j);
								if (affichage_paires) System.out.println("fermeture" + i + ":" + j);
							}
						}
					}
				}
			}
			//fin bloc fermeture transitive
		}
		
		
		//fin calcul
		
		
		
		//nombre de contraintes trouvees
		thm_betzler_count=0;
		for (int element1 = 1; element1<=n; element1++){//on met toutes les contraintes a false, car aucune contrainte n'est connue au depart
			for (int element2 = 1; element2<=n; element2++){
				if (element2 != element1){
					if (tabContraintesBetzler[element1-1][element2-1]){
						thm_betzler_count++ ;
						
					}
				}
			}
		}
		//fin nombre de countraites trouvees
		
		
		//coin pour les stats
		thm_betzler_stats += thm_betzler_count;
		if (thm_betzler_count!=0) thm_betzler_application_stats++;
		if (thm_betzler_count!=0) thm_betzler_application = true;
		thm_betzler_revised_count = thm_betzler_count;//on l'utilise ensuite conjointement avec l'autre seulement si Betzler est applicable
		contraintesBetzler = new ArrayList<IntPair>();//pour test d'inclusion thms betzler ds Major Order Theorem
		for (int element1 = 1; element1<=n; element1++){//on met toutes les contraintes a false, car aucune contrainte n'est connue au depart
			for (int element2 = 1; element2<=n; element2++){
				if (element2 != element1){
					if (tabContraintesBetzler[element1-1][element2-1]){
						contraintesBetzler.add(new IntPair(element1,element2));
					}
				}
			}
		}
		//fin stats
		
		
		//affichage du nombre de contraintes trouvees
		if (affichage_stats) System.out.println("---");
		if (affichage_stats) System.out.println("thm_betzler_count: " + thm_betzler_count);
		if (affichage_stats) System.out.println("nombre de paires: " + (n*(n-1)/2));
		if (affichage_stats) System.out.println("");
		double resolution_exacte = 100.0*(thm_betzler_count)/(n*(n-1)/2);
		resolution_stats +=resolution_exacte;
		if (affichage_stats) System.out.println("resolution exacte: " + (thm_betzler_count)+ "/" + (n*(n-1)/2)
				+ " ("+df.format(resolution_exacte)+"%)");
		if (affichage_stats) System.out.println("");
		//fin affichage
		
		
	}
	
	
	
	
	
	
	//thm Betzler avec non-dirty candidate
	public static void thmBetzler_matriciel (Instance myInstance, boolean verboseDetails, boolean verbosePairs, boolean verboseStats){
		int n;//ordre des permutations
		
		n=myInstance.n;
		boolean listNonDirtyCandidates[] = new boolean[n];
		
		tabContraintesBetzler = new boolean [n][n];
		for (int i = 1; i<= n; i++){
			listNonDirtyCandidates[i-1]=false;
			for (int j = 1; j<= n; j++){
				tabContraintesBetzler[i-1][j-1]=false;
			}
		}
		
		double m = (double)myInstance.m;
		double nbPermu1avant2 = 0.0;
		//creerTabD(a);
		
		boolean silencieux = !verboseDetails;//si on veux avoir les calculs affiches
		boolean affichage_paires = verbosePairs;//si on veux avoir les paires des thms affichees pour le probleme courant
		boolean affichage_stats = verboseStats;//si on veux avoir les stats des thms affichees pour le probleme courant
		
		boolean nonDirtyCandidate = false;
		boolean proceed = true;
		int thm_betzler_iteration=0;

		if (!silencieux) System.out.println("");
		if (!silencieux) System.out.println(" *** Evaluation du thm de Betzler ***");
		if (!silencieux) System.out.println("");
		
		if (affichage_paires) System.out.println("");
		if (affichage_paires) System.out.println(" **Betzler**");
		
		int thm_betzler_count = 0;
		
		while (proceed){
			proceed = false;
			thm_betzler_iteration++;
			
			for (int element1 = 1; element1<=n; element1++){
				nonDirtyCandidate = true;
				for (int element2 = 1; element2<=n; element2++){//boucle 2
					if (element2 != element1){
						nbPermu1avant2 = (double)myInstance.tabD[element2-1][element1-1];
						if ((nbPermu1avant2/m >= 0.75) || (nbPermu1avant2/m <= 0.25)){
							//reste un nonDirtyCandidate
						//}else if ((contraintes.contains(new IntPair(element1,element2))) || (contraintes.contains(new IntPair(element2,element1)))){
						}else if (tabContraintesBetzler[element1-1][element2-1] || tabContraintesBetzler[element2-1][element1-1]){
							//reste un nonDirtyCandidate
						}else{
							nonDirtyCandidate = false;
						}		
					}
				}//fin boucle 2
				
				if (nonDirtyCandidate && (!listNonDirtyCandidates[element1-1])){//it's a new non-dirty candidate
					listNonDirtyCandidates[element1-1]=true;
					if (!silencieux)  System.out.println("\t new non-dirty candidate: " + element1);
					proceed = true; //because we found a non-dirty candidate, so we'll have to reapply the theorem on each side
					for (int element2 = 1; element2<=n; element2++){//boucle 2
						if ((element2 != element1) && (!tabContraintesBetzler[element1-1][element2-1])&& (!tabContraintesBetzler[element2-1][element1-1])){
							if (myInstance.tabD[element1-1][element2-1] < myInstance.tabD[element2-1][element1-1]){//si element1 est maj avant element2
								tabContraintesBetzler[element1-1][element2-1] = true;
								if (!silencieux)  System.out.println("\t Thm betzler("+thm_betzler_iteration+") :  ordre majoritaire respecte " + element1 + " " + element2);
								if (affichage_paires) System.out.println("thm betzler("+thm_betzler_iteration+") " + element1 + ":" + element2);
							}else{
								tabContraintesBetzler[element2-1][element1-1] = true;
								if (!silencieux)  System.out.println("\t Thm betzler("+thm_betzler_iteration+") :  ordre majoritaire respecte " + element2 + " " + element1);
								if (affichage_paires) System.out.println("thm betzler("+thm_betzler_iteration+") " + element2 + ":" + element1);
							}	
						}
					}//fin boucle 2
					fermeture_contraintesBetzler_matriciel(1,n,!silencieux,affichage_paires);
				}
			}//fin boucle 1
		}
		
		fermeture_contraintesBetzler_matriciel(1,n,!silencieux,affichage_paires);
		thm_betzler_count=0;
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				if (tabContraintesBetzler[i-1][j-1]){
					thm_betzler_count++;
				}
			}
		}
		
		
		thm_betzler_stats += thm_betzler_count;
		if (thm_betzler_count!=0) thm_betzler_application_stats++;
		if (thm_betzler_count!=0) thm_betzler_application = true;
		if (thm_betzler_count!=0) thm_betzler_revised_stats += thm_betzler_count;//on l'utilise ensuite conjointement avec l'autre seulement si Betzler est applicable
		
		if (affichage_stats) System.out.println("---");
		if (affichage_stats) System.out.println("thm_betzler_count: " + thm_betzler_count);
		if (affichage_stats) System.out.println("nombre de paires: " + (n*(n-1)/2));
		if (affichage_stats) System.out.println("");
		double resolution_exacte = 100.0*(thm_betzler_count)/(n*(n-1)/2);
		resolution_stats +=resolution_exacte;
		if (affichage_stats) System.out.println("resolution exacte: " + (thm_betzler_count)+ "/" + (n*(n-1)/2)
				+ " ("+df.format(resolution_exacte)+"%)");
		if (affichage_stats) System.out.println("");
		
	}
	
	
	//thm Betzler matriciel avec non-dirty candidate
	//c'est un module pour rajoute des conrtaintes Betzler sur un probleme qui a deja des contraintes trouvees par d'autres methodes
	public static int apply_thmBetzler_matriciel ( Instance myInstance, boolean verboseDetails, boolean verbosePairs, boolean verboseStats){
		int n;//ordre des permutations
		
		n=myInstance.n;
		boolean listNonDirtyCandidates[] = new boolean[n];
		
		for (int i = 1; i<= n; i++){
			listNonDirtyCandidates[i-1]=false;
		}
		
		double m = (double)myInstance.m;
		double nbPermu1avant2 = 0.0;
		//creerTabD(a);
		
		boolean silencieux = !verboseDetails;//si on veux avoir les calculs affiches
		boolean affichage_paires = verbosePairs;//si on veux avoir les paires des thms affichees pour le probleme courant
		boolean affichage_stats = verboseStats;//si on veux avoir les stats des thms affichees pour le probleme courant
		
		boolean nonDirtyCandidate = false;
		boolean proceed = true;
		int thm_betzler_iteration=0;

		if (!silencieux) System.out.println("");
		if (!silencieux) System.out.println(" *** Evaluation du thm de Betzler ***");
		if (!silencieux) System.out.println("");
		
		if (affichage_paires) System.out.println("");
		if (affichage_paires) System.out.println(" **Betzler**");
		
		int thm_betzler_count = 0;
		
		while (proceed){
			proceed = false;
			thm_betzler_iteration++;
			
			for (int element1 = 1; element1<=n; element1++){
				nonDirtyCandidate = true;
				for (int element2 = 1; element2<=n; element2++){//boucle 2
					if (element2 != element1){
						nbPermu1avant2 = (double)myInstance.tabD[element2-1][element1-1];
						if ((nbPermu1avant2/m >= 0.75) || (nbPermu1avant2/m <= 0.25)){
							//reste un nonDirtyCandidate
						//}else if ((contraintes.contains(new IntPair(element1,element2))) || (contraintes.contains(new IntPair(element2,element1)))){
						}else if (myInstance.tabC[element1-1][element2-1] || myInstance.tabC[element2-1][element1-1]){
							//reste un nonDirtyCandidate
						}else{
							nonDirtyCandidate = false;
						}		
					}
				}//fin boucle 2
				
				if (nonDirtyCandidate && (!listNonDirtyCandidates[element1-1])){//it's a new non-dirty candidate
					listNonDirtyCandidates[element1-1]=true;
					if (!silencieux)  System.out.println("\t new non-dirty candidate: " + element1);
					proceed = true; //because we found a non-dirty candidate, so we'll have to reapply the theorem on each side
					for (int element2 = 1; element2<=n; element2++){//boucle 2
						if ((element2 != element1) && (!myInstance.tabC[element1-1][element2-1])&& (!myInstance.tabC[element2-1][element1-1])){
							if (myInstance.tabD[element1-1][element2-1] < myInstance.tabD[element2-1][element1-1]){//si element1 est maj avant element2
								myInstance.tabC[element1-1][element2-1] = true;
								thm_betzler_count++;
								if (!silencieux)  System.out.println("\t Thm betzler("+thm_betzler_iteration+") :  ordre majoritaire respecte " + element1 + " " + element2);
								if (affichage_paires) System.out.println("thm betzler("+thm_betzler_iteration+") " + element1 + ":" + element2);
							}else{
								myInstance.tabC[element2-1][element1-1] = true;
								thm_betzler_count++;
								if (!silencieux)  System.out.println("\t Thm betzler("+thm_betzler_iteration+") :  ordre majoritaire respecte " + element2 + " " + element1);
								if (affichage_paires) System.out.println("thm betzler("+thm_betzler_iteration+") " + element2 + ":" + element1);
							}	
						}
					}//fin boucle 2
					thm_betzler_count+=fermeture_contraintes_matriciel(myInstance, 1,n,!silencieux,affichage_paires);
				}
			}//fin boucle 1
		}
		
		thm_betzler_count+=fermeture_contraintes_matriciel(myInstance, 1,n,!silencieux,affichage_paires);
		
		
		if (affichage_stats) System.out.println("add thm_betzler_count: " + thm_betzler_count);
		double resolution_exacte = 100.0*(thm_betzler_count)/(n*(n-1)/2);
		if (affichage_stats) System.out.println("add resolution exacte: " + (thm_betzler_count)+ "/" + (n*(n-1)/2)
				+ " ("+df.format(resolution_exacte)+"%)");
		
		return thm_betzler_count;
		
	}
	
	
	
	
	
	
	
	
	//fermeture d'un ensemble de contraintes par transitivite
	//methode naive implemente rapidement
	public static List<IntPair> fermeture_contraintes(List<IntPair> contraintes_intiales, int min, int max, boolean speak, boolean speak_pair){
		List<IntPair> contraintes_finales = new ArrayList<IntPair>();
		contraintes_finales.addAll(contraintes_intiales);
		boolean amelioration = true;
		if (speak) System.out.println("");
		if (speak) System.out.println("Fermeture des contraintes trouvees (add):");
		while (amelioration){
			amelioration = false;
			for (int i = min; i <= max; i++){
				for (int j = min; j <= max; j++){
					for (int k = min; k <= max; k++){
						if (!contraintes_finales.contains(new IntPair(i,j))){
							if (contraintes_finales.contains(new IntPair(i,k)) && contraintes_finales.contains(new IntPair(k,j))){
								contraintes_finales.add(new IntPair(i,j));
								amelioration = true;
								if (speak) System.out.println("  "+i+":"+j);
								if (speak_pair) System.out.println("add fermeture "+i+":"+j);
								if (contraintes_finales.contains(new IntPair(j,i))){
									if (speak) System.out.println("attention! contrainte contradictoire");
									if (speak_pair) System.out.println("attention! contrainte contradictoire");
								}
							}
						}
					}
				}
			}
		}
		
		/*for (int i =0; i<contraintes_finales.size();i++){
			System.out.println(contraintes_finales.get(i));
		}*/
		if (speak) System.out.println("");
		return contraintes_finales;
	}
	
	
	
	//fermeture d'un ensemble de contraintes par transitivite
	//methode naive implemente rapidement
	public static int fermeture_contraintes_matriciel(Instance myInstance, int min, int max, boolean verboseDetails, boolean verbosePairs){
		boolean amelioration = true;
		int nouv = 0;
		if (verboseDetails) System.out.println("");
		if (verboseDetails) System.out.println("Fermeture des contraintes trouvees (add):");
		while (amelioration){
			amelioration = false;
			for (int i = min; i <= max; i++){
				for (int j = min; j <= max; j++){
					for (int k = min; k <= max; k++){
						if (!myInstance.tabC[i-1][j-1]){
							if (myInstance.tabC[i-1][k-1] && myInstance.tabC[k-1][j-1]){
								myInstance.tabC[i-1][j-1]=true;
								amelioration = true;
								nouv++;
								if (verboseDetails) System.out.println("  "+i+":"+j);
								if (verbosePairs) System.out.println("add fermeture "+i+":"+j);
								if (myInstance.tabC[j-1][i-1]){
									if (verboseDetails) System.out.println("attention! contrainte contradictoire");
									if (verbosePairs) System.out.println("attention! contrainte contradictoire");
								}
							}
						}
					}
				}
			}
		}
		if (verboseDetails) System.out.println("");
		return nouv;
	}
	
	
	//fermeture d'un ensemble de contraintes par transitivite
	//methode naive implemente rapidement
	public static int fermeture_contraintesBetzler_matriciel(int min, int max, boolean verboseDetails, boolean verbosePairs){
		boolean amelioration = true;
		int nouv = 0;
		if (verboseDetails) System.out.println("");
		if (verboseDetails) System.out.println("Fermeture des contraintes trouvees (add):");
		while (amelioration){
			amelioration = false;
			for (int i = min; i <= max; i++){
				for (int j = min; j <= max; j++){
					for (int k = min; k <= max; k++){
						if (!tabContraintesBetzler[i-1][j-1]){
							if (tabContraintesBetzler[i-1][k-1] && tabContraintesBetzler[k-1][j-1]){
								tabContraintesBetzler[i-1][j-1]=true;
								amelioration = true;
								nouv++;
								if (verboseDetails) System.out.println("  "+i+":"+j);
								if (verbosePairs) System.out.println("add fermeture "+i+":"+j);
								if (tabContraintesBetzler[j-1][i-1]){
									if (verboseDetails) System.out.println("attention! contrainte contradictoire");
									if (verbosePairs) System.out.println("attention! contrainte contradictoire");
								}
							}
						}
					}
				}
			}
		}
		if (verboseDetails) System.out.println("");
		return nouv;
	}
	

	
	
	
	//cree l'ensemble SMn: ensemble de toutes les permutations succeptibles d'etre dans M
	//a besoin de l'ensemble cible A (passe en parametre)
	
	//update: utilise une permutation approxime pour trier les nombres a placer puis pour avoir une premiere approximation de distance mediane
	//utilise les contraintes MOT
	public static void BranchAndBound (Instance myInstance, boolean verbose){
		int n;//nombre d'elements
		int reponse = 0;
		int resolutionMOT=0;
		//int resolutionMOT4=0;
		n=myInstance.n;
		myInstance.nbExploredNodesBnB=0;
		myInstance.nbMaxNodesBnB=500000000;
		//nbMaxNodesBnB=500;
		memoryLimit = 100000;
		
		myInstance.nbRejectGD=0.0;
		myInstance.nbRejectTriplets=0.0;
		myInstance.nbRejectMOT=0.0;
		myInstance.nbRejectMOT4=0.0;
		myInstance.nbRejectSemiDistBI=0.0;
		myInstance.nbRejectTopScores=0.0;
		myInstance.nbRejectSubScores=0.0;
		myInstance.nbRejectSpatial=0.0;
		myInstance.nbReject=0.0;
		
		minSubScoresSize = 4;
		maxSubScoresSize = 6;
		//System.out.println("SubScoresSize: " + ii + "-" + (ii +2));
		
		//topScores = new TreeMap<BitVector,Integer>();//liste des topScores avec des sous ensemble d'elements
		//topScores2 = new TreeMap<BitVector,Integer>();//liste des topScores avec des sous ensemble d'elements
		myInstance.topScores = new HashMap<BitVector,Integer>();//liste des topScores avec des sous ensemble d'elements
		myInstance.subScores = new HashMap<BitVector,Integer>();//liste des topScores avec des sous ensemble d'elements
		
		
		if (verbose) System.out.println("");
		if (verbose) System.out.println(" ***EXACT B&B SOLVER***");
		if (verbose) System.out.println("");
		
		//myInstance.M = new HashSet<Permutation>();
		
		//smallestLowerBoundBnB=99999999;
		//lowerBoundsBnB = new HashMap<Integer,Integer>();
		
		
		
		
		
		//tabMOT = new boolean [n][n];
		for (int i = 1; i<= n; i++){
			for (int j = 1; j<= n; j++){
				if (myInstance.tabC[i-1][j-1]){
					//tabMOT[i-1][j-1]=true;
					resolutionMOT++;
				}else{
					//tabMOT[i-1][j-1]=false;
				}
				//if (tabMOT4[i-1][j-1]) resolutionMOT4++;
			}
		}
		double resolution_exacte = 100.0*(resolutionMOT)/(n*(n-1)/2);
		if (verbose) System.out.println("Resolution contraintes par contraintes: " + (resolutionMOT)+ "/" + (n*(n-1)/2)
				+ " ("+df.format(resolution_exacte)+"%)");
		resoluMOT = df.format(resolution_exacte);
		
		/*resolution_exacte = 100.0*(resolutionMOT4)/(n*(n-1)/2);
		if (verbose) System.out.println("Resolution contraintes par MOT4: " + (resolutionMOT4)+ "/" + (n*(n-1)/2)
				+ " ("+df.format(resolution_exacte)+"%)");
		resoluMOT = df.format(resolution_exacte);*/
		
		
		
		
		
		//statistiques
		//terminaisonsInfertilesContraintes=0;
		//terminaisonsInfertilesSemiDist=0;
		
		
		//********************ORDERING NUMBERS TO PLACE
		//le pool de nombre qu'aura le droit d'utiliser la fct recursive recuSMn, et son ordre
		List<Integer> nombres = new ArrayList<Integer>();
		Permutation pi = null;
		if (myInstance.Medians.size()==0){//if we don't use SA heuristic to order our choice
		//if (SA.size()==0){//if we don't use SA heuristic to order our choice
			for (int i=1; i<=n;i++)
				nombres.add(i);
		}else{//if we use SA heuristic to order our choice
			//if (verbose) System.out.println(pi);
			pi = myInstance.pickARandomMedian();
			for (int i=0; i<pi.getSize(); i++){
				nombres.add(pi.getTab()[i]);
				/*for (Node nn : arbreMOT3){
					if (nn.data == pi.getTab()[i]){
						nn.setPosition(i);
						//System.out.println(nn.data + " [label=\""+nn.data+"("+(i+1)+")\"]");
					}
				}*/
			}
		}
		
		/*for (Node nn : arbreMOT3){
			System.out.print(nn.data + " ");
		}
		System.out.println();*/
		
		
		//********************CONSTRAINTS : SPATIAL CONSTRAINTS
		contraintes_spatiales(myInstance,n,pi, verbose);
		
		


		
		//********************GAP BETWEEN LOWER BOUND (WITH 3/4-CYCLES) AND UPPER BOUND (SA)
		int borneInf = myInstance.simple_lower_bound;
		int borneInfAdd = myInstance.add3cyles_lower_bound;
		if (verbose) System.out.println("Gap = "+ myInstance.getGap() + " ("+df.format(myInstance.getRelativeGap())+"%)");
		
		
		//********************WE START HERE THE BNB
		// on commence ici!
		BitVector vecteurBit = new BitVector(n);
		vecteurBit.setValue(0);
		List<Integer> permuEnCours;
		permuEnCours= new ArrayList<Integer>();
		/*List<Integer> interScores;
		interScores= new ArrayList<Integer>();
		for (int i=0; i< n; i++)
			interScores.add(0);*/
		/*Set<Node> nodes = new TreeSet<Node>();
		for (Node nn : rootArbreMOT3.children){
			nodes.add(nn);
		}*/
		/*for (Node nn : nodes){
			System.out.print(nn.data + " ");
		}
		System.out.println();*/
		//reponse = recuSMn_wMOT(permuEnCours, nombres, nodes, n,0,borneInf, vecteurBit, false);
		
		if (verbose) System.out.println("Starting the BnB...");
		
		//reponse = recuSMn_wConstraints(permuEnCours, interScores, apport, borneInfAdd, nombres, n,0,borneInf, vecteurBit, false, 0);
		reponse = BranchAndBound_recursive(myInstance, permuEnCours, myInstance.apport, borneInfAdd, nombres, n,0,borneInf, vecteurBit, false, 0);
		
		if (myInstance.nbExploredNodesBnB >= myInstance.nbMaxNodesBnB){
			if (verbose) System.out.println("node limit exceeded : BnB didn't finish optimizing");
			if (verbose) System.out.println("Gap = "+ myInstance.getGap() + " ("+df.format(myInstance.getRelativeGap())+"%)");
		}else{
			if (verbose) System.out.println("BnB terminated correctly");
			myInstance.decalreIsOptimal();
		}
		
		
		
		
		//********************COMPILING RESULTS OF THE BnB
		myInstance.nbReject=myInstance.nbRejectGD+myInstance.nbRejectTriplets+myInstance.nbRejectMOT+myInstance.nbRejectMOT4+myInstance.nbRejectSemiDistBI+myInstance.nbRejectTopScores+myInstance.nbRejectSubScores+myInstance.nbRejectSpatial;
		if (verbose && true) System.out.println("profil des rejects - "+ "GD : " + myInstance.nbRejectGD + ", triplets : " + myInstance.nbRejectTriplets + ", MOT3e+LUBC : " + myInstance.nbRejectMOT + ", SemiDistBI : " + myInstance.nbRejectSemiDistBI +", SemiDistBIadd : " + myInstance.nbRejectSemiDistBIadd +  ", TopScs : " + myInstance.nbRejectTopScores +  ", SubScs : " + myInstance.nbRejectSubScores +  ", Spatial : " + myInstance.nbRejectSpatial + ",  total : " + myInstance.nbReject);
		myInstance.nbRejectGD=100.0*myInstance.nbRejectGD/myInstance.nbReject;
		myInstance.nbRejectTriplets=100.0*myInstance.nbRejectTriplets/myInstance.nbReject;
		myInstance.nbRejectMOT=100.0*myInstance.nbRejectMOT/myInstance.nbReject;
		myInstance.nbRejectMOT4=100.0*myInstance.nbRejectMOT4/myInstance.nbReject;
		myInstance.nbRejectSemiDistBI=100.0*myInstance.nbRejectSemiDistBI/myInstance.nbReject;
		myInstance.nbRejectSemiDistBIadd=100.0*myInstance.nbRejectSemiDistBIadd/myInstance.nbReject;
		myInstance.nbRejectTopScores=100.0*myInstance.nbRejectTopScores/myInstance.nbReject;
		myInstance.nbRejectSubScores=100.0*myInstance.nbRejectSubScores/myInstance.nbReject;
		myInstance.nbRejectSpatial=100.0*myInstance.nbRejectSpatial/myInstance.nbReject;
		myInstance.nbReject=100.0*myInstance.nbReject/myInstance.nbReject;
		if (verbose && true) System.out.println("profil des rejects - "+ "GD : " + df.format(myInstance.nbRejectGD) + "%, triplets : " + df.format(myInstance.nbRejectTriplets) + "%, MOT3e+LUBC : " + df.format(myInstance.nbRejectMOT) + "%, SemiDistBI : " + df.format(myInstance.nbRejectSemiDistBI) + "%, SemiDistBIadd : " + df.format(myInstance.nbRejectSemiDistBIadd) + "%, TopScs : " + df.format(myInstance.nbRejectTopScores) +"%, SubScs : " + df.format(myInstance.nbRejectSubScores) +"%, Spatial : " + df.format(myInstance.nbRejectSpatial) + "%,  total : " + df.format(myInstance.nbReject)+"%");
		if (verbose && true) System.out.println("size of topScores = " + myInstance.topScores.size() + ",  size of subScores("+minSubScoresSize+"-"+maxSubScoresSize+") = " + myInstance.subScores.size());
		if (verbose && true) System.out.println("nb de noeuds explore: "+ myInstance.nbExploredNodesBnB);
		if (verbose && true) System.out.println();
	}
	
	//fonction recurssive au coeur de la methode creerSMn, elle aide a batir SMn
	//public static int recuSMn_wMOT(List<Integer> permuEnCours, List<Integer> nombres, Set<Node> nodes, int n, int semiDist, int borneInf, BitVector vecteurBit, boolean verboseDetails){
	//public static int recuSMn_wConstraints(List<Integer> permuEnCours,List<Integer> interScores, int[] apports, int borneInfAdd, List<Integer> numbers, int leftToSet, int semiDist, int borneInf, BitVector vecteurBit, boolean verboseDetails, int thatBnBnumber){
	public static int BranchAndBound_recursive(Instance myInstance, List<Integer> permuEnCours, int[] apports, int borneInfAdd, List<Integer> numbers, int leftToSet, int semiDist, int borneInf, BitVector vecteurBit, boolean verboseDetails, int thatBnBnumber){
		int reponse = 99999999;
		int reponseCandidat = 99999999;
		int nouvSemiDist =0;
		int nouvBorneInf=0;
		int nouvBorneInfAdd=0;
		int[] nouvApports = null;
		int lastElement=0;
		int beforeLastElement=0;
		int elementToPut=0;

		
		boolean permissionToContinue= true;
		
		//usage des contraintes
		boolean useGD = true;
		boolean useTriplets = true;
		boolean useConstraints = true;
		boolean useSemiDistBIadd = true;
		boolean useTopScores = true;
		

		List<Integer> numbersAfterFirstCuts;
		List<Integer> associatedLowerBounds;
		int associatedLowerBound = 0;
		//int numberOfLowerBounds=0;
		

		List<Integer> nombres2;
		List<Integer> permuEnCours2;

		
		boolean isDot = false;//for dot graph generation
		int thisBnBnumber =0;
		
		
		//limit on the number of nodes of the BnB
		if (myInstance.nbExploredNodesBnB >= myInstance.nbMaxNodesBnB){
			return -1;
		}
		
		myInstance.nbExploredNodesBnB++;
		
		//******************** for .DOT graphics ********************
		//System.out.println("nBnB:" +nbExploredNodesBnB+ " topScs:" + topScores.size());
		if (isDot) thisBnBnumber = myInstance.nbExploredNodesBnB;
		if (isDot) {
			if (myInstance.nbExploredNodesBnB != 1){
				System.out.println(thatBnBnumber + " -> " + thisBnBnumber+";");
				//System.out.println(thatBnBnumber + " -> " + thisBnBnumber + "  [label="+permuEnCours.get(permuEnCours.size()-1)+"]");
				System.out.println(thisBnBnumber + "  [label="+permuEnCours.get(permuEnCours.size()-1)+"]" +";");
			}
			else{
				//System.out.println(thatBnBnumber + " -> " + thisBnBnumber+ "  [label=start]" +";");
				//System.out.println(thatBnBnumber + " -> " + thisBnBnumber+ "  [label=start]");
				//System.out.println(0 + "  [label= ]" +";");
				System.out.println(thisBnBnumber + "  [label=x]" +";");
			}
		}
		
		//******************** for calculations ********************
		BitVector invVecteurBit = new BitVector(vecteurBit.size());//creation du vecteur de bits inverse
		for (int bit =0; bit < vecteurBit.size(); bit++){
			invVecteurBit.set(bit,!vecteurBit.get(bit));
		}
		
		//********************for display********************
		if (verboseDetails) System.out.println(permuEnCours + "  sd:" + semiDist + "   bi:" + borneInf+ "   add:" + borneInfAdd);
		if (verboseDetails){
			//System.out.println("interScores: "+interScores);
			/*for (Node nn : nodes){
				System.out.print(nn.data + " ");
			}
			System.out.println();*/
		}
		
		
		
		
		//******************** LEAF ********************
		if (leftToSet == 0){
			
			int[] a;
			Permutation pi;
			a = new int[permuEnCours.size()];
			for (int j=0; j<permuEnCours.size();j++ )
				a[j]= permuEnCours.get(j);
			pi = new Permutation(a);
			pi.setDist(semiDist);
			
			if (semiDist < myInstance.best_upper_bound) {
				myInstance.setUpperBound(semiDist);
				//System.out.println("nouvelle permutation avec meilleur score "+semiDist+ " ("+nbExploredNodesBnB+")");
				//SMn.clear();
				//SMn.add(pi);
				myInstance.Medians.clear();
				myInstance.addSolverPermutation(pi);
			}else if (semiDist == myInstance.best_upper_bound) {
				myInstance.setUpperBound(semiDist);
				//System.out.println("nouvelle permutation avec score "+semiDist + " ("+nbExploredNodesBnB+")");
				//System.out.println("BAAM! BAAM!");
				//System.out.println(pi + " ("+nbExploredNodesBnB+")");
				//SMn.add(pi);
				myInstance.addSolverPermutation(pi);
			}

			reponse = semiDist;

		//******************** NODE ********************
		}else{
			//on prend en consideration les derniers et avant-derniers elements
			if (permuEnCours.size() >= 1)
				lastElement=permuEnCours.get(permuEnCours.size()-1);
			if (permuEnCours.size() >= 2)
				beforeLastElement=permuEnCours.get(permuEnCours.size()-2);
			

		
			numbersAfterFirstCuts = new ArrayList<Integer>();
			associatedLowerBounds = new ArrayList<Integer>();
			
			
			//for(int i: nombres){
			for (int i=0; i< numbers.size(); i++){//on parcours les nombres a placer
				elementToPut=numbers.get(i);
			//for(Node n1 : nodes){
			//	elementToPut=n1.data;
				
				
				
				permissionToContinue= true;
				
				if (useGD){
					//we check if the elementToPut can be placed right after the last element
					if (permuEnCours.size() == 0){
						permissionToContinue = true;
					}else{
						if (permuEnCours.size() >= 1){//contraintes G/D juste pour 1 element
							permissionToContinue = false;
							//if(contraintesD.get(permuEnCours.get(permuEnCours.size()-1)-1).contains(nombres.get(i))){
							if(myInstance.tabContraintesD[lastElement-1][elementToPut-1]){
								permissionToContinue = true;
							}else{
								if (verboseDetails) System.out.println(permuEnCours + "(" + elementToPut +")? nope: D/G");
								myInstance.nbRejectGD++;
							}
						}
					}
				}
				
				if(permissionToContinue){
					permissionToContinue = true;
					if (useTriplets){
						//we check if the elementToPut can be placed right after the last two element
						if (permuEnCours.size() >= 2){
							//System.out.println(tabTriplets[beforeLastElement-1][lastElement-1][elementToPut-1]);
							if (myInstance.tabTriplets[beforeLastElement-1][lastElement-1][elementToPut-1] ==false){
								permissionToContinue = false;
								if (verboseDetails) System.out.println(permuEnCours + "(" + elementToPut +")? nope: triplets");
								myInstance.nbRejectTriplets++;
							}
						}
					}
				}
				
				
				if(permissionToContinue){
					permissionToContinue = true;
					if (useConstraints){//contraintes
						//MOT3e and LUBC
						//we check if the elementToPut can be placed before all the other elements to be placed
						for (int j=0; j< numbers.size(); j++){
							if (elementToPut != numbers.get(j))
								if (myInstance.tabC[numbers.get(j)-1][elementToPut-1]){
									permissionToContinue = false;
									j= numbers.size();
								}
						}
						if (!permissionToContinue){
							if (verboseDetails) System.out.println(permuEnCours + "(" + elementToPut +")? nope: MOT3");
							myInstance.nbRejectMOT++;
						}
						
					}
					
				}
					
				
				if(permissionToContinue){
					permissionToContinue = true;
					
					
					//if the elementToPut has passed all the previous tests and can be placed
					// we simulate a new permutation in construction with that element and check for further obstacles
					
					nombres2 = new ArrayList<Integer>();
					permuEnCours2 = new ArrayList<Integer>();
												
					copyIntList(numbers,nombres2);//sinon ca passe des pointeurs, modifier=faire tout crasher
					copyIntList(permuEnCours,permuEnCours2);
					
					permuEnCours2.add(elementToPut);//la permutation sous forme d'array encore, avant d'etre finalisee
					nombres2.remove(nombres2.lastIndexOf(elementToPut));//le pool de nombres disponible restant
					
					nouvBorneInf=borneInf;
					for (int k=0; k< nombres2.size(); k++)
						nouvBorneInf -= Math.min(myInstance.tabD[elementToPut-1][nombres2.get(k)-1], myInstance.tabD[nombres2.get(k)-1][elementToPut-1]);
					
					nouvSemiDist = semiDist;
					for (int j=0; j< nombres2.size(); j++)
						nouvSemiDist+=myInstance.tabD[elementToPut-1][nombres2.get(j)-1];
					

					
						
					if (useSemiDistBIadd){//borneInf amelioree (borneInfAdd)
						//we check if the new permutation in construction is exceeding a known cost/score with a stronger bound (using the 3-cycles of Conitzer simplified method)
						nouvApports = new int[apports.length];
						for (int j = 0; j < apports.length; j++){
							nouvApports[j]=apports[j];
						}
						nouvBorneInfAdd = borneInfAdd - apports[elementToPut-1];
						
						//>=
						if (nouvSemiDist + nouvBorneInf + nouvBorneInfAdd >= myInstance.best_upper_bound){
						//if (nouvSemiDist + nouvBorneInf + nouvBorneInfAdd > distMedianeElagage){
							if (verboseDetails) System.out.println(permuEnCours + "(" + elementToPut +")? nope: semiDist+borneInf+add ("+nouvSemiDist+"+"+ nouvBorneInf+"+"+ nouvBorneInfAdd+"="+(nouvSemiDist + nouvBorneInf+ nouvBorneInfAdd)+" > "+myInstance.best_upper_bound+")");
							permissionToContinue= false;
							myInstance.nbRejectSemiDistBIadd++;
						}else{
							/*nouvApports[elementToPut-1]=-1;
							for (int j=0; j< nombres2.size(); j++){
								for (int k=0; k< tabNbTriangleAssocie[elementToPut-1][nombres2.get(j)-1]; k++){
									if (tabTriangleAssocie[elementToPut-1][nombres2.get(j)-1].get(k) != 0){//si on viens de peter un triangle
										if (nouvApports[tabTriangleAssocie[elementToPut-1][nombres2.get(j)-1].get(k)-1] != -1){//...un triangle qui n'a pas deja ete pete ;)
											nouvApports[nombres2.get(j)-1] -= tabTriangleAdd[elementToPut-1][nombres2.get(j)-1][tabTriangleAssocie[elementToPut-1][nombres2.get(j)-1].get(k)-1];
										}
									}else{//sinon 
										//reste pareil
									}
								}

							}*/

								
						}
					}

					
					//the numbers that passed the first screening
					if(permissionToContinue){
						associatedLowerBound = nouvSemiDist + nouvBorneInf + nouvBorneInfAdd;
						numbersAfterFirstCuts.add(elementToPut);
						associatedLowerBounds.add(associatedLowerBound);
						
						
						//lowerbounds
						/*if (associatedLowerBound < smallestLowerBoundBnB){ 
							smallestLowerBoundBnB = associatedLowerBound;
							System.out.println("new smaller lower bound " + associatedLowerBound);
						}
						if(!lowerBoundsBnB.containsKey(associatedLowerBound)){
							lowerBoundsBnB.put(associatedLowerBound,1);
						}else{
							numberOfLowerBounds=lowerBoundsBnB.get(associatedLowerBound);
							lowerBoundsBnB.put(associatedLowerBound,numberOfLowerBounds+1);
						}*/
						
					}

				
				}else{
					//nothing
				}
			}
			
			
			
			
			
			//now going through the numbers that passed the first screening and have an associatyed lower bound
			for (int i=0; i< numbersAfterFirstCuts.size(); i++){//on parcours les nombres a placer
				elementToPut=numbersAfterFirstCuts.get(i);
				
				
				
				
				
				
				
				nombres2 = new ArrayList<Integer>();
				permuEnCours2 = new ArrayList<Integer>();
											
				copyIntList(numbers,nombres2);//sinon ca passe des pointeurs, modifier=faire tout crasher
				copyIntList(permuEnCours,permuEnCours2);
				
				permuEnCours2.add(elementToPut);//la permutation sous forme d'array encore, avant d'etre finalisee
				nombres2.remove(nombres2.lastIndexOf(elementToPut));//le pool de nombres disponible restant
				
				nouvBorneInf=borneInf;
				for (int k=0; k< nombres2.size(); k++)
					nouvBorneInf -= Math.min(myInstance.tabD[elementToPut-1][nombres2.get(k)-1], myInstance.tabD[nombres2.get(k)-1][elementToPut-1]);
				
				nouvSemiDist = semiDist;
				for (int j=0; j< nombres2.size(); j++)
					nouvSemiDist+=myInstance.tabD[elementToPut-1][nombres2.get(j)-1];
				
				
				
				
				
				
				
				
				
				nouvApports = new int[apports.length];
				for (int j = 0; j < apports.length; j++){
					nouvApports[j]=apports[j];
				}
				nouvBorneInfAdd = borneInfAdd - apports[elementToPut-1];
				
				nouvApports[elementToPut-1]=-1;
				for (int j=0; j< nombres2.size(); j++){
					for (int k=0; k< myInstance.tabNbTriangleAssocie[elementToPut-1][nombres2.get(j)-1]; k++){
						if (myInstance.tabTriangleAssocie[elementToPut-1][nombres2.get(j)-1].get(k) != 0){//si on viens de peter un triangle
							if (nouvApports[myInstance.tabTriangleAssocie[elementToPut-1][nombres2.get(j)-1].get(k)-1] != -1){//...un triangle qui n'a pas deja ete pete ;)
								nouvApports[nombres2.get(j)-1] -= myInstance.tabTriangleAdd[elementToPut-1][nombres2.get(j)-1][myInstance.tabTriangleAssocie[elementToPut-1][nombres2.get(j)-1].get(k)-1];
							}
						}else{//sinon 
							//reste pareil
						}
					}

				}

							
					
				
				
				
				
				
				
				
				
				
				/////topScores prunning
				//we check if the new permutation in construction has been already investigated 
				// (with the same elements but different order) but with a lesser cost/score
				BitVector nouvVecteurBit = new BitVector(vecteurBit.size());
				for (int bit =0; bit < vecteurBit.size(); bit++)
					nouvVecteurBit.set(bit,vecteurBit.get(bit));
				nouvVecteurBit.set(elementToPut-1,true);
				nouvVecteurBit.setValue(nouvSemiDist);

				if (useTopScores){	
					if (myInstance.topScores.get(nouvVecteurBit) != null){//voie deja exploree
						if (myInstance.topScores.get(nouvVecteurBit) < nouvSemiDist){//mauvaise voie
							if (verboseDetails) System.out.println(permuEnCours + "(" + elementToPut +")? nope: topScores " +"avant= " + myInstance.topScores.get(nouvVecteurBit) + "  now= " + nouvSemiDist );
							permissionToContinue = false;
							myInstance.nbRejectTopScores++;
						}else{
							if (myInstance.topScores.get(nouvVecteurBit) > nouvSemiDist){
								myInstance.topScores.put(nouvVecteurBit, nouvSemiDist);
							}
							permissionToContinue = true;
							
						}
					}else{ //voie non exploree encore
						if (myInstance.topScores.size()<memoryLimit){
							myInstance.topScores.put(nouvVecteurBit, nouvSemiDist);
						}else{
							myInstance.topScores.put(nouvVecteurBit, nouvSemiDist);
						}
						permissionToContinue = true;
					}
				}
				//////fin topScores prunning
				
				
				
				
				
				// GO GO GO!!!!
				//we can continue building the permutation with that elementToPut :)
				if(permissionToContinue){
					if (verboseDetails) System.out.println(permuEnCours + "(" + elementToPut +")? ok");
					reponseCandidat = BranchAndBound_recursive(myInstance, permuEnCours2, nouvApports, nouvBorneInfAdd, nombres2, leftToSet-1, nouvSemiDist, nouvBorneInf, nouvVecteurBit,verboseDetails,thisBnBnumber);
				}
				
				//update de la reponse qu'on retourne
				if ((reponseCandidat != -1)&&(reponseCandidat < reponse)) reponse = reponseCandidat;
				
				
				
				//lowerbounds
				/*associatedLowerBound = associatedLowerBounds.get(i);
				if(lowerBoundsBnB.containsKey(associatedLowerBound)){
					numberOfLowerBounds=lowerBoundsBnB.get(associatedLowerBound);
					if (numberOfLowerBounds > 1){
						lowerBoundsBnB.put(associatedLowerBound,numberOfLowerBounds-1);
					}else{
						lowerBoundsBnB.remove(associatedLowerBound);
						if (associatedLowerBound == smallestLowerBoundBnB){
							smallestLowerBoundBnB = 99999999;
							for (int lowerBound : lowerBoundsBnB.keySet()){
								if (lowerBound < smallestLowerBoundBnB){
									smallestLowerBoundBnB = lowerBound;
								}
							}
							System.out.println("better lower bound  " + smallestLowerBoundBnB);
						}
					}
				}else{
					System.out.println("error :(  ");
				}*/
				
			}

		}
		
		return reponse;
		
		
	}
	
	
	
	
	//copie le 1er ensemble d'entiers dans le 2e
	public static void copyIntSet(Set<Integer> set1, Set<Integer> set2){
		set2.clear();
		for (int k: set1)
			set2.add(k);
	}

	
	//copie le 1er ensemble de permutations dans le 2e
	public static void copyPermuSet(Set<Permutation> set1, Set<Permutation> set2){
		set2.clear();
		for (Permutation k: set1)
			set2.add(new Permutation(k.getTab()));
	}
	
	//copie la 1ere liste d'entiers dans la 2e
	public static void copyIntList(List<Integer> list1, List<Integer> list2){
		list2.clear();
		for (int k: list1)
			list2.add(k);
	}
	

	
	//creer l'enseble de permutation duquel on va chercher l'ensemble mediane
	public static Set<Permutation> creerA(){
		HashSet<Permutation> A = new HashSet<Permutation>();
			
		
		//test pour reparer MOT5
		/*int a[] ={1,5,6,7,8,9,10,2,3,4};//not ij
		int b[] ={5,6,7,8,9,10,3,4,1,2};
		int c[] ={5,6,7,8,9,10,2,3,4,1};*/
		/*int a[] ={1,5,6,7,8,9,10,2,3,4}; //ij
		int b[] ={3,4,1,5,6,7,8,9,10,2};
		int c[] ={5,6,7,8,9,10,2,3,4,1};*/
		
		
		//n=8, test pour amelioration borneInf m=3
		//int a[] ={1,5,2,6,3,7,4,8};
		//int b[] ={6,8,2,1,4,7,3,5};
		//int c[] ={3,8,4,7,1,2,5,6};

		//n=200, distMed'=17861
		//int a[] ={8,122,80,189,50,130,167,13,88,161,101,74,164,46,142,92,200,184,148,43,35,76,84,173,87,85,127,1,165,110,115,192,120,90,3,62,31,66,145,160,131,51,89,105,193,83,41,33,123,178,102,54,103,186,107,199,40,75,136,182,16,93,188,118,23,185,91,78,100,27,125,71,17,168,183,134,22,30,60,37,171,163,57,24,135,156,197,97,15,166,67,119,176,36,177,149,14,25,175,56,48,191,194,133,55,59,12,19,63,65,195,99,138,121,2,49,150,143,29,151,140,109,72,81,190,7,106,198,154,95,18,144,34,86,196,113,179,6,139,187,116,112,162,39,5,52,174,82,153,157,152,94,70,21,42,137,9,132,124,58,159,155,47,158,114,98,10,141,32,77,28,146,45,147,96,104,20,181,126,53,73,61,11,172,64,44,26,108,111,38,169,129,4,79,68,69,170,117,180,128};
		//int b[] ={165,94,27,38,17,115,119,75,171,151,64,180,131,3,177,4,97,184,175,61,60,69,113,6,172,58,39,109,107,117,148,24,1,167,160,110,71,90,169,11,195,104,178,26,63,23,124,199,173,196,181,25,29,49,54,46,82,144,145,20,188,7,158,133,74,121,68,37,189,22,106,35,45,147,126,179,99,8,41,51,108,150,140,91,34,16,76,111,32,174,85,33,12,191,194,137,134,10,197,142,152,98,2,159,193,186,164,138,89,105,162,9,93,67,101,128,84,57,62,146,176,114,59,80,96,118,170,185,102,190,55,139,13,103,83,130,21,166,78,56,122,125,136,70,14,132,81,19,187,168,192,30,141,31,149,120,15,47,129,156,28,153,183,87,157,66,155,44,198,77,88,5,40,73,48,42,112,123,36,92,79,95,116,65,53,86,161,50,127,163,182,52,143,43,72,154,100,200,135,18};
		//int c[] ={36,106,104,40,70,132,176,105,128,48,20,153,164,187,98,53,168,181,189,66,1,38,170,186,138,78,162,90,103,174,50,185,112,63,14,125,171,175,180,30,172,37,60,28,89,41,56,140,102,67,80,10,108,72,123,12,134,11,97,15,136,46,110,71,16,59,199,160,158,137,5,188,193,19,119,76,155,183,109,99,152,113,4,194,139,190,130,107,79,87,58,9,126,149,51,54,29,85,100,143,154,169,157,159,192,173,129,43,122,42,64,69,74,182,88,86,83,179,24,52,44,2,94,117,25,166,65,55,178,35,144,39,124,81,82,33,32,92,131,133,151,27,142,8,3,167,165,17,146,68,121,118,23,26,47,147,161,177,135,18,21,195,148,61,6,150,13,111,96,198,73,115,200,57,184,163,62,95,93,141,7,145,45,191,116,75,114,156,84,31,22,91,197,77,120,101,127,49,196,34};

		
		//n=100, distMed'=4102
		int a[] ={86,61,10,59,60,28,43,23,39,90,100,1,98,37,2,47,76,36,13,7,14,22,44,55,3,84,73,49,66,15,85,33,88,54,35,89,11,87,45,12,24,40,20,92,75,82,30,52,94,81,63,74,67,79,32,17,58,26,91,31,70,72,42,46,21,27,80,77,4,34,96,29,53,51,68,99,62,5,9,41,19,64,25,38,78,97,48,56,93,50,65,57,18,8,6,69,16,71,83,95};
		int b[] ={70,67,83,51,44,99,42,85,75,33,59,27,69,72,54,98,9,90,39,36,18,45,52,8,96,76,34,100,3,57,81,84,4,40,92,6,15,16,61,62,31,88,25,17,13,2,26,89,64,29,74,19,56,71,53,73,55,50,23,14,77,58,78,24,41,21,10,60,97,12,28,30,91,43,93,95,86,5,80,79,11,47,82,68,87,63,37,49,94,22,48,20,35,66,38,1,46,7,32,65};
		int c[] ={13,36,52,76,54,90,34,62,46,99,26,11,72,78,51,89,10,58,42,100,50,24,98,69,80,65,77,75,19,18,9,97,66,85,20,38,45,92,39,95,64,23,87,71,37,84,4,67,88,47,28,57,1,12,70,53,73,29,41,2,60,17,25,3,63,30,33,32,86,48,94,56,6,21,44,15,91,96,79,16,8,14,31,27,40,43,55,82,68,5,83,49,22,35,61,7,59,81,93,74};
		
		//n=50, distMed=4439 '?, cas tres difficile
		/*int a[] ={49,35,4,33,6,34,27,14,31,50,22,18,2,8,39,41,28,30,24,43,45,16,10,15,48,17,46,42,36,5,3,13,25,19,40,9,7,23,44,32,21,11,1,20,29,47,26,12,38,37};
		int b[] ={48,16,25,36,7,11,32,17,3,45,30,2,26,27,43,5,13,4,23,20,41,47,19,12,9,29,46,22,44,49,33,31,24,34,39,35,50,14,8,38,6,18,10,42,21,40,1,15,28,37};
		int c[] ={44,12,1,6,2,39,24,15,33,40,17,4,18,9,26,36,21,45,35,30,38,25,46,29,16,8,42,19,49,3,22,34,13,10,23,41,43,50,14,20,27,31,32,7,47,37,48,11,5,28};
		int d[] ={32,41,9,48,26,8,18,14,15,44,37,25,19,50,16,12,38,47,49,39,45,33,27,29,24,42,11,21,43,5,40,10,17,35,34,46,13,36,28,30,4,31,7,22,3,2,23,6,20,1};
		int e[] ={28,43,26,31,3,33,9,6,17,13,48,32,1,44,40,5,21,36,22,47,12,46,2,35,29,27,14,34,10,18,50,19,30,41,38,45,37,49,20,23,25,4,16,8,39,11,7,15,24,42};
		int f[] ={24,29,5,13,7,32,33,18,42,46,8,11,47,35,9,48,15,38,40,21,26,17,49,50,37,27,3,34,19,31,43,25,16,41,45,20,36,39,28,22,10,14,4,12,23,2,44,30,6,1};
		int g[] ={12,37,38,28,36,9,13,23,41,42,14,17,44,26,49,18,5,19,6,32,3,20,16,27,29,8,30,7,34,35,39,45,10,33,40,21,22,2,24,47,31,4,48,1,50,11,43,25,15,46};
		int h[] ={6,50,28,21,14,9,23,20,18,7,46,47,15,31,40,44,42,2,25,38,24,17,8,37,45,29,4,22,1,27,30,48,12,33,43,16,49,13,39,11,5,35,36,10,34,26,32,41,19,3};
		int i[] ={3,23,27,22,35,14,37,33,48,4,2,30,17,15,29,34,28,41,40,43,1,36,16,26,18,19,12,49,21,9,46,47,50,13,42,31,25,8,38,24,11,44,10,20,7,6,32,39,5,45};
		*/
		/*
		//n=50, distMed=2035
		int a[] = {2,45,16,1,49,19,37,6,29,22,24,20,26,41,13,11,12,14,23,31,3,9,44,35,28,42,47,33,4,43,39,8,10,5,25,30,32,18,21,46,48,27,50,38,7,36,40,34,17,15};
		int b[] = {3,20,35,4,26,32,13,5,43,11,27,22,15,36,7,45,41,31,46,48,25,29,34,16,49,40,21,37,23,10,44,14,19,42,1,17,33,28,2,6,8,47,24,38,18,12,30,9,39,50};
		int c[] = {5,4,11,22,34,9,23,43,14,40,26,47,6,12,29,36,48,20,16,15,37,25,41,8,35,31,32,2,28,18,39,30,1,44,50,7,33,38,49,13,21,3,27,10,17,19,45,42,46,24};
		int d[] = {27,26,24,16,2,39,36,12,11,48,40,10,14,30,3,21,25,29,9,37,7,6,47,50,13,20,33,19,1,42,15,4,5,49,38,32,18,43,44,46,35,17,22,23,34,8,45,28,41,31};
		int e[] = {28,40,10,23,37,15,5,32,50,11,38,21,13,34,45,14,2,30,49,42,20,22,24,44,47,31,41,9,3,35,43,26,25,39,4,48,8,12,17,16,33,27,7,36,6,1,18,29,19,46};
		*/
		
		//n=50, distMed=2151
		/*int a[] ={9,20,23,6,24,21,7,40,28,43,3,14,26,4,50,29,30,45,34,25,39,41,27,48,8,44,19,11,1,32,15,42,31,13,49,36,10,16,46,37,18,22,5,17,47,38,12,35,2,33};
		int b[] ={12,42,9,29,21,11,15,34,10,17,18,26,49,5,35,30,7,22,28,36,24,1,45,48,27,3,31,6,23,47,16,33,4,43,20,14,13,41,19,39,8,25,50,44,2,37,32,46,40,38};
		int c[] ={12,47,33,22,5,25,30,38,8,49,2,35,15,36,24,19,4,11,42,45,18,6,40,14,7,46,50,39,27,34,31,32,23,9,10,29,26,16,48,44,41,20,37,21,3,43,1,13,17,28};
		int d[] ={16,23,39,28,15,6,45,17,9,35,5,42,18,4,34,20,37,43,1,40,25,36,46,22,38,11,26,14,24,50,19,27,8,33,48,29,31,30,7,44,41,32,2,21,10,3,12,47,13,49};
		int e[] ={44,11,2,8,43,3,19,26,45,30,42,37,20,28,9,47,18,39,1,33,32,48,41,34,31,7,13,35,14,49,12,10,5,16,40,36,23,24,46,17,22,6,50,21,27,38,4,29,25,15};*/

		//n=50, distMed=2066
		/*int a[] ={6,4,44,14,13,17,7,20,33,26,28,29,34,22,12,48,25,10,11,3,2,21,18,23,32,49,8,9,24,40,37,16,46,27,1,39,43,31,30,47,42,19,36,38,5,50,15,41,35,45};
		int b[] ={9,29,4,21,50,19,47,2,11,25,38,14,49,10,1,36,6,17,42,3,46,48,16,27,40,13,8,22,5,39,7,18,26,45,44,20,43,32,24,30,41,12,33,28,15,23,31,37,35,34};
		int c[] ={19,30,35,49,4,12,9,18,10,1,37,20,7,31,39,34,41,3,47,8,15,17,13,38,33,44,25,50,42,27,22,32,11,29,21,16,43,14,23,40,45,36,46,5,2,26,24,28,48,6};
		int d[] ={37,10,4,28,39,21,18,26,46,30,25,3,47,33,34,32,42,41,36,19,13,16,40,5,12,27,7,9,38,44,11,15,6,17,49,45,22,14,2,24,31,43,50,20,23,29,48,35,8,1};
		int e[] ={38,30,37,25,19,46,2,27,24,45,36,22,5,49,39,40,18,50,1,14,15,23,42,17,41,28,9,34,7,32,29,35,4,11,21,20,16,12,10,13,33,43,26,8,3,47,48,6,44,31};
		*/
		//n=16, distMed=201, cas probleme HEURvsALGO
		/*int a[] ={3,2,7,13,16,8,4,6,10,14,9,1,15,12,5,11};
		int b[] ={2,12,4,14,8,1,11,15,3,5,16,9,7,6,10,13};
		int c[] ={6,7,16,14,13,8,2,5,1,11,9,10,4,15,3,12};
		int d[] ={13,15,12,2,9,3,5,14,8,4,10,11,16,7,6,1};
		int e[] ={7,4,16,5,15,3,6,8,13,14,1,2,11,9,12,10};*/
	
		
		//n=20, distMed=306
		/*int a[] = {2,7,9,1,15,13,20,8,12,18,5,4,6,17,3,16,14,10,19,11};
		int b[] = {4,3,9,20,6,2,18,15,13,19,10,8,11,16,17,12,1,7,14,5};
		int c[] = {4,17,7,6,11,9,2,14,1,20,8,18,13,10,15,3,16,5,19,12};
		int d[] = {8,6,19,14,20,5,7,12,11,3,16,17,10,15,18,2,4,13,1,9};
		int e[] = {8,7,5,4,13,17,15,9,14,16,2,20,3,18,1,19,6,10,11,12};*/
		
		
		//n=20, distMed=326, 6 medianes
		//temps= 30sec original
		//temps= 5sec900millisec nombres:Set->List
		//temps= 6sec200millisec pour commencer permu: debutPermu->nombres
		//temps= 3sec900millisec elagage stricte
		//temps= 0sec450millisec avec approximation (exacte...) SA(5,10000)
		//temps= 0sec200millisec avec elagege contraintes MOT
		/*int a[] = {2,7,8,9,10,18,1,12,11,5,6,14,17,19,15,16,13,20,3,4};
		int b[] = {3,6,13,10,14,18,7,8,19,9,12,5,17,15,16,11,20,4,1,2};
		int c[] = {15,6,1,18,16,3,19,17,12,14,13,8,5,11,2,10,9,7,4,20};
		int d[] = {17,7,4,9,14,2,16,13,11,8,20,12,3,6,1,15,18,19,5,10};
		int e[] = {19,3,7,1,10,15,5,12,13,14,8,18,6,20,2,9,11,16,17,4};*/

		//n=25, distMed=534, 5 medianes
		/*int a[] = {10,16,8,12,4,15,21,18,20,25,9,11,24,2,5,23,1,6,22,3,13,17,19,14,7};
		int b[] = {11,6,10,21,20,8,16,23,3,19,14,2,15,13,5,4,12,24,22,9,25,17,1,7,18};
		int c[] = {17,12,24,20,25,10,8,23,15,4,18,5,9,1,19,14,3,21,16,7,11,2,22,6,13};
		int d[] = {21,13,22,20,1,5,6,10,17,3,2,24,12,23,7,11,18,25,9,8,16,14,15,19,4};
		int e[] = {22,1,10,14,17,7,19,12,9,11,2,15,3,5,18,23,24,6,21,16,25,4,13,8,20};*/

		//n=35, distMed=1028, 1 mediane
		//duos 50%, trio 19.09%, MOT 32.27%, 
		//2sec, size of topScores = 37139, nb de noeuds explore: 226098,  size of subScores = 0
		//6sec, size of topScores = 35380, nb de noeuds explore: 210540,  size of subScores = 2986620
		/*int a[] = {21,24,4,3,34,33,27,19,28,29,26,13,2,18,10,32,16,6,5,22,7,8,14,1,20,23,17,25,15,30,9,35,31,12,11};
		int b[] = {25,8,26,17,31,2,1,13,34,30,28,21,14,23,5,35,18,16,9,12,32,20,19,10,7,27,33,22,11,15,29,6,3,4,24};
		int c[] = {11,28,7,26,34,32,1,29,18,33,2,6,20,9,4,16,12,30,25,35,24,31,21,8,17,13,5,22,19,15,14,3,23,10,27};
		int d[] = {19,9,2,25,32,27,1,14,24,15,29,34,30,8,17,22,11,33,5,23,31,13,16,3,7,35,10,6,12,4,20,28,26,18,21};
		int e[] = {4,15,7,2,1,34,5,20,8,10,11,35,3,32,17,16,13,25,28,9,26,27,29,22,33,23,30,14,24,31,6,12,18,21,19};*/
		
		
		//n=40, distMed=1385, 76 medianes
		//duos 50%, trios 19%, MOT 24% (MOT= 33%)
		//52.733sec, sizes of topScores = 4174904, nb de noeuds explore: 4175626
		/*int a[] = {16,36,37,34,38,14,30,12,7,11,32,19,17,21,35,8,27,26,29,6,4,39,23,28,3,25,40,22,20,1,2,24,33,10,18,9,31,5,15,13};
		int b[] = {26,14,8,3,24,31,7,40,17,22,28,39,15,29,5,13,18,4,32,20,21,2,36,35,38,12,27,37,33,34,11,30,9,10,25,19,23,6,1,16};
		int c[] = {38,1,25,31,13,18,14,36,33,5,23,21,24,10,15,20,22,30,26,3,8,7,2,32,6,40,35,29,39,37,34,4,12,27,28,9,17,16,11,19};
		int d[] = {27,14,33,34,10,8,31,37,6,19,15,28,30,22,23,39,40,3,35,24,9,1,13,32,36,4,20,5,16,12,29,25,18,21,17,2,11,7,26,38};
		int e[] = {3,18,29,16,23,40,14,8,17,28,33,30,4,13,9,39,32,21,38,26,2,7,11,10,1,5,36,25,24,31,22,20,35,27,12,34,15,37,6,19};*/
		
		//n=15, distMed=151, 9 medianes
		/*int a[] = {6,4,5,8,14,7,9,2,13,12,10,3,11,1,15};
		int b[] = {11,8,10,13,2,4,9,5,7,6,1,3,12,14,15};
		int c[] = {3,13,11,1,7,9,10,6,15,8,12,2,14,5,4};
		int d[] = {1,15,13,8,4,2,7,14,3,9,12,6,11,10,5};
		int e[] = {1,10,8,15,9,5,6,14,7,11,4,13,3,12,2};*/
		
		//n=40
		//MOT 47.7% -> 48.6
		/*int a[] = {1,36,7,27,3,31,40,15,16,11,33,13,2,8,26,25,29,10,4,37,35,20,18,21,24,30,19,32,39,22,23,14,12,5,38,28,6,17,34,9};
		int b[] = {19,10,2,6,38,12,31,28,16,3,34,30,22,24,8,23,29,4,13,9,5,14,40,37,32,17,25,26,27,33,11,18,20,21,35,39,15,36,7,1};
		int c[] = {39,18,12,31,19,13,11,36,22,26,9,2,30,20,14,15,40,6,32,35,10,23,28,21,1,17,37,38,16,34,5,24,33,27,7,25,3,4,8,29};*/
		
		//n=20, distMed=287, exemple pour le BnB - 5
		/*int a[] = {1,8,20,17,9,13,12,6,5,16,10,15,3,11,7,4,19,14,2,18};
		int b[] = {19,4,14,13,12,20,18,9,7,3,2,1,5,6,17,16,15,8,10,11};
		int c[] = {1,2,9,15,6,13,19,5,4,8,17,3,11,20,18,10,14,7,16,12};
		int d[] = {19,3,9,16,20,17,4,8,1,5,7,13,2,6,11,10,12,18,15,14};
		int e[] = {8,5,19,20,18,2,1,17,15,16,11,4,13,7,10,14,9,12,6,3};*/
		
		//n=15, suite MOT3 - sudoku
		/*int a[] = {12,5,3,7,6,8,13,4,1,2,9,11,15,14,10};
		int b[] = {14,7,2,13,1,15,10,4,9,6,8,3,12,11,5};
		int c[] = {1,5,4,15,13,10,6,8,3,9,7,14,2,11,12};*/
		
		//n=40, m=3 - broken major order (MOT4?), aug 2016
		//MOT 67% -> 79%
		/*int a[] = {32,27,19,35,39,2,21,16,40,6,26,18,14,25,36,15,29,11,22,20,1,34,5,10,24,37,4,3,13,12,33,9,17,30,38,7,8,28,23,31};
		int b[] = {11,1,27,6,37,8,38,22,15,2,33,26,23,39,28,10,34,25,17,30,4,7,3,5,13,24,21,29,36,20,12,32,19,14,18,31,35,16,40,9};
		int c[] = {1,26,39,36,4,25,20,34,17,33,35,18,14,29,30,8,27,7,22,16,21,23,3,24,37,11,12,40,15,38,28,32,31,5,2,6,9,19,13,10};*/
		
		//n=30, m=3 - broken major order (MOT4?), aug 2016
		//MOT 82% -> 96%
		/*int a[] = {7,29,1,25,2,13,14,19,30,23,9,6,17,20,24,15,28,21,22,10,27,3,11,8,4,18,16,12,26,5};
		int b[] = {8,22,2,13,3,18,9,25,19,30,24,5,26,11,10,12,23,29,15,28,1,14,16,20,6,4,17,7,21,27};
		int c[] = {22,3,23,6,9,10,4,7,21,30,5,26,12,8,18,11,27,15,19,28,2,14,20,25,17,16,1,13,24,29};*/
		
		//n=20,  exemple pour le BnB - 3
		/*int a[] = {6,19,7,9,8,1,12,14,16,5,11,13,15,4,10,20,17,2,3,18};
		int b[] = {8,20,14,18,4,17,19,16,5,10,7,13,15,2,9,3,11,1,6,12};
		int c[] = {5,16,2,10,11,7,18,9,1,15,3,17,19,6,4,14,8,20,13,12};
		int d[] = {3,8,5,1,13,17,19,16,4,9,11,7,15,2,10,6,12,14,18,20};
		int e[] = {18,2,1,12,13,19,16,11,15,6,7,20,4,3,14,8,5,17,10,9};*/
		
		//n=50, distMed=1030, 270+? medianes
		//MOT 58.5% -> 60%
		/*int a[] = {41,48,9,8,16,24,14,25,10,18,2,3,12,39,35,30,31,23,19,5,4,43,44,32,27,22,49,34,26,21,42,13,47,11,15,33,29,37,46,17,6,45,20,50,36,40,1,28,7,38};
		int b[] = {33,4,21,11,25,23,22,37,16,9,41,6,44,13,2,50,46,38,45,5,3,12,1,47,30,27,39,15,18,19,35,14,7,28,29,24,10,32,40,8,36,26,20,43,17,34,49,42,48,31};
		int c[] = {49,47,4,38,23,44,43,12,5,33,19,7,8,9,1,39,40,14,28,48,26,30,16,6,46,50,22,15,11,34,41,42,29,20,10,27,24,45,2,37,31,35,36,3,25,17,21,18,32,13};
		*/
		
		//n=50, distMed=1051, 4+? medianes, 51sec-1min10sec, sizes of topScores = 537 063, nb de noeuds explore: 8 525 797
		//                    5+? medianes, 10sec, sizes of topScores = 49467, nb de noeuds explore: 362 000, borneInf=1032
		//                                  10sec, sizes of topScores = 16450, nb de noeuds explore: 93363, borneInf=1039 (meilleure borne rand)
		//MOT 50.5% -> 50.9%
		/*int a[] = {11,9,13,42,49,25,27,28,44,45,46,34,39,32,48,20,1,24,16,7,12,37,3,8,5,31,6,40,22,29,47,43,2,14,36,19,50,15,21,23,33,26,17,4,41,10,38,18,30,35};
		int b[] = {3,47,22,16,17,36,25,9,30,13,28,23,14,40,35,38,21,15,39,2,50,20,24,29,5,42,10,1,32,45,34,12,33,27,31,11,44,26,8,4,46,49,18,7,43,41,37,19,6,48};
		int c[] = {28,13,3,29,41,1,50,24,45,15,18,31,4,35,42,17,38,14,8,21,48,22,12,44,26,40,47,2,5,7,46,10,49,20,16,30,6,11,25,9,23,39,32,27,19,37,34,36,33,43};
		*/
		
		//n=40, erreur borneInfMaxFlow
		/*int a[] = {3,40,17,7,9,22,16,35,15,8,19,34,37,2,12,38,25,13,18,36,26,20,24,23,39,10,1,32,5,4,14,33,11,30,28,21,31,6,27,29};
		int b[] = {22,7,39,23,5,40,37,6,36,14,1,33,28,27,18,35,13,24,31,25,9,30,21,10,11,32,4,12,34,3,19,20,29,16,17,15,2,26,8,38};
		int c[] = {1,3,38,39,11,23,13,5,24,29,12,10,14,21,15,34,9,30,35,8,4,36,25,31,6,27,20,28,26,18,16,7,33,19,40,37,22,17,32,2};	
		*/
				
		//n=60, distMediane= 1526, sizes of topScores = 413 169, nb de noeuds explore: 33 631 321, 4 minutes 12 secondes 460 millisecondes
		//MOT 60.2% -> 62.15%
		/*int a[] = {49,5,32,42,14,11,58,45,50,15,52,43,1,25,27,38,16,17,54,37,24,41,56,39,7,29,19,47,48,33,6,51,59,13,2,3,28,34,35,26,18,8,46,12,30,4,57,36,9,20,10,22,21,55,60,23,44,40,53,31};
		int b[] = {50,53,1,7,49,25,58,27,51,60,46,2,10,56,44,11,59,9,41,6,3,20,35,8,38,40,29,22,32,42,36,19,21,55,16,52,15,28,14,37,12,45,48,39,34,17,18,4,57,13,24,5,54,30,43,33,26,47,23,31};
		int c[] = {40,17,5,28,32,46,57,9,39,11,16,34,19,4,23,47,41,55,31,51,8,59,49,30,25,14,56,22,27,35,29,20,54,36,58,21,2,43,26,48,52,7,10,37,53,18,45,13,3,12,1,15,33,24,50,42,44,38,6,60};
		*/
		
		//25 juillet 2014
		//test cas cyclique espace
		/*int a[] = {1,2,3,4,5,6};
		int b[] = {5,6,1,2,3,4};
		int c[] = {3,4,5,6,1,2};
		int d[] = {1,3,4,5,6,2};
		int e[] = {5,1,2,3,4,6};
		int f[] = {3,5,6,1,2,4};*/
		
		
		//27mars2015, distMed=140
		//test amelioration thm_cool
		//0 minutes 1 secondes 700 millisecondes
		/*int a[] = {10,14,13,9,7,3,6,15,1,16,12,20,18,11,17,8,5,4,19,2};
		int b[] = {14,15,16,5,19,7,13,20,11,17,4,6,9,12,1,3,8,18,2,10};
		int c[] = {5,16,13,7,18,12,11,17,14,9,10,2,1,6,20,8,19,4,15,3};*/
		
		
		//exemple memoire, distMed=22
		/*int a[] ={7,8,2,3,6,1,5,4};
		int b[] ={3,5,1,7,8,6,2,4};
		int c[] ={5,8,3,4,1,2,7,6};*/
		
		/*int a[] ={23,15,9,29,30,12,3,25,27,21,5,19,13,8,18,4,14,24,2,26,11,7,6,16,22,17,28,20,10,1};
		int b[] ={28,7,1,2,23,12,8,19,29,18,4,13,30,10,5,6,22,15,9,26,21,25,3,14,17,11,20,16,27,24};
		int c[] ={22,10,13,17,27,14,4,5,12,9,1,18,25,26,11,7,30,19,6,3,23,8,24,21,16,2,28,15,29,20};
		int d[] ={12,22,20,19,3,16,21,11,30,8,17,4,25,27,5,18,28,7,6,2,14,1,24,23,9,29,13,10,26,15};*/
		
		//exemple 1-2, similar structure of A, 19 decembre 2016 
		/*int a[] ={1,2,3,4,5,6,7,8};
		int b[] ={3,5,1,7,8,6,2,4};
		int c[] ={5,8,3,4,1,2,7,6};*/
		/*int a[] ={1,2,3,4,5,7,8,6};
		int b[] ={3,5,1,6,7,8,2,4};
		int c[] ={5,8,3,4,1,2,7,6};*/
		
		//exemple des minimums locaux du memoire, m=5, n=4, distMediane= 12
		/*int a[] = {2,1,3,4};
		int b[] = {3,1,4,2};
		int c[] = {1,4,3,2};
		int d[] = {4,2,1,3};
		int e[] = {3,4,2,1};*/
		
		//exemple des minimums locaux du memoire, m=5, n=4, distMediane= 12
		/*int a[] = {22,17,21,13,25,23,1,16,8,18,15,7,9,12,4,3,6,24,14,2,5,10,20,11,19};
		int b[] = {16,7,17,23,20,24,19,1,21,25,11,8,14,10,3,4,5,12,6,13,15,9,18,2,22};
		int c[] = {21,9,3,24,4,23,12,14,25,1,17,5,10,16,8,13,19,7,15,18,20,2,11,6,22};*/
		

		Permutation pi1 =  new Permutation(a);
		Permutation pi2 =  new Permutation(b);
		Permutation pi3 =  new Permutation(c);
		/*Permutation pi4 =  new Permutation(d);
		Permutation pi5 =  new Permutation(e);
		
		/*Permutation pi6 =  new Permutation(f);
		Permutation pi7 =  new Permutation(g);
		Permutation pi8 =  new Permutation(h);
		Permutation pi9 =  new Permutation(i);
		/*Permutation pi10 =  new Permutation(j);
		Permutation pi11 =  new Permutation(k);
		Permutation pi12 =  new Permutation(l);
		Permutation pi13 =  new Permutation(m);*/
		
		//afficahe de certaines distances
		/*System.out.println("dKT pi1  pi2  pi3  pi4  pi5");
		System.out.println("pi1  X  " + pi1.distanceTo(pi2) + "  "+ pi1.distanceTo(pi3) + "  "+ pi1.distanceTo(pi4) + "  "+ pi1.distanceTo(pi5) );
		System.out.println("pi2 " + pi2.distanceTo(pi1)+ "   X" + "  "+ pi2.distanceTo(pi3) + "  "+ pi2.distanceTo(pi4) + "  "+ pi2.distanceTo(pi5) );
		System.out.println("pi3 " + pi3.distanceTo(pi1) + "  "+ pi3.distanceTo(pi2)+ "   X" + "  "+ pi3.distanceTo(pi4) + "  "+ pi3.distanceTo(pi5) );
		System.out.println("pi4 " + pi4.distanceTo(pi1) + "  "+ pi4.distanceTo(pi2) + "  "+ pi4.distanceTo(pi3)+ "   X" + "  "+ pi4.distanceTo(pi5) );
		System.out.println("pi5 " + pi5.distanceTo(pi1) + "  "+ pi5.distanceTo(pi2) + "  "+ pi5.distanceTo(pi3) + "  "+ pi5.distanceTo(pi4) + "   X");*/
		
		
		A.add(pi1);
		A.add(pi2);
		A.add(pi3);
		/*A.add(pi4);
		A.add(pi5);
		/*A.add(pi6);
		A.add(pi7);
		A.add(pi8);
		A.add(pi9);
		/*A.add(pi10);
		A.add(pi11);
		A.add(pi12);
		A.add(pi13);*/
		

		
		/*B.add(pi3);
		B.add(pi4);
		B.add(pi5);*/
		
		return A;
		
	}
	
	//creation d'un ensemble de permutations a partir d'un sous-ensemble d'elements
	/*public static Set<Permutation> creerSubA (Set<Permutation> set1, int elements[]){
		int size = elements.length;
		int size2 = pickARandom(set1).getSize();
		Set<Permutation> SubA = new HashSet<Permutation>();
		int[] tableInvertSubPermu = new int[size2];
		tableSubPermu = new int[size];
		//int compteur = 1;
		for (int i = 0; i < size; i++){
			tableSubPermu[i] = elements[i];
			tableInvertSubPermu[elements[i]-1] = i+1;
		}
		
		
		for (Permutation pi : set1){
			List<Integer> permuEnCours  = new ArrayList<Integer>();
					
			for (int i = 0; i < size2; i++){
				if (tableInvertSubPermu[pi.getTab()[i]-1] != 0){
					permuEnCours.add(tableInvertSubPermu[pi.getTab()[i]-1]);
				}
			}
					
			
			Permutation piSub = null;
			int[] b = new int[permuEnCours.size()];
			for (int j=0; j<permuEnCours.size();j++ )
				b[j]= permuEnCours.get(j);
			piSub = new Permutation(b);
			
			piSub.setSubPermu(tableSubPermu);
			
			
			SubA.add(piSub);
		}
		
		return SubA;
	}*/
	

	

	//copie le 1er ensemble de permutation dans le 2e
	public static void copyPermutationSet(Set<Permutation> set1, Set<Permutation> set2){
		Permutation k2;
		for (Permutation k: set1){
			k2 =  new Permutation(k.getTab());
			set2.add(k2);
		}
	}
	
	//choisi une permuttation au hasard
	public static Permutation pickARandom(Set<Permutation> set1){
		Permutation k2 = null;
		int num=0,compteur=0;
		num = (int)(Math.random()*set1.size()+1);
		for (Permutation k: set1){
			compteur++;
			if (compteur == num){
				k2 =  new Permutation(k.getTab());
				return k2;
			}
		}
		System.out.println("problem #201");
		return k2;
	}
	
	//choisi une permuttation au hasard
	public static Permutation pickARandom2(SortedSet<Permutation> set1){
		Permutation k2 = null;
		int num=0,compteur=0;
		num = (int)(Math.random()*set1.size()+1);
		for (Permutation k: set1){
			compteur++;
			if (compteur == num){
				k2 =  new Permutation(k.getTab());
				return k2;
			}
		}
		System.out.println("problem #201");
		return k2;
	}
	
	//choisi un entier au hasard
	public static int pickARandomInt(Set<Integer> set1){
		int k2 = 0;
		int num=0,compteur=0;
		num = (int)(Math.random()*set1.size()+1);
		for (int k: set1){
			compteur++;
			if (compteur == num){
				k2 =  k;
				return k2;
			}
		}
		System.out.println("problem #203");
		return k2;
	}
	
	
	
	//creer une permuttation au hasard
	public static Permutation createARandom(int n){
		Permutation k2 = null;
		int num=0,temp=0;
		int a[] = new int[n];
		
		//enumerer
		for (int i=1;i<=n;i++){
			a[i-1]=i;
		}
		
		//a verifier...
		//brasser (Fisher-Yates shuffle)
		for (int i=1;i<=n;i++){
			num = (int)(Math.random()*i+1);
			temp=a[num-1];
			a[num-1]=a[i-1];
			a[i-1]=temp;
		}
		
		//finaliser
		k2 =  new Permutation(a);
		
		return k2;
	}
	
	//Permutation identite d'ordre n
	public static Permutation createIndentite(int n){
		Permutation k2 = null;
		int a[] = new int[n];
		
		//enumerer
		for (int i=1;i<=n;i++){
			a[i-1]=i;
		}
		
		//finaliser
		k2 =  new Permutation(a);
		
		return k2;
	}
	

	
	
	
	//printing a set of permutations
	public static void printSet (Set <Permutation> ensemble){

		
		System.out.println("set ??? (" + ensemble.size() + ") :" );
		if (ensemble.size() < 10001){
			for (Permutation p: ensemble){
				//System.out.println(p);
				System.out.println(p + " " + p.getDist());
			}
		}else{
			System.out.println("Plus de 200 permutations...(pas la peine d'imprimer)");
		}

	}
	
	//printing a list of permutations
	public static void printList (List <Permutation> list1){

		
		System.out.println("list ??? (" + list1.size() + ") :" );
		if (list1.size() < 10001){
			for (Permutation p: list1){
				//System.out.println(p);
				System.out.println(p + " " + p.getDist());
			}
		}else{
			System.out.println("Plus de 200 permutations...(pas la peine d'imprimer)");
		}

	}
	
	


	

	
	//imprime l'ensemble de permutations Sn
	public static void printSn (){
		System.out.println("Set Sn (" + Sn.size() + ") :" );
		for (Permutation p: Sn){
			System.out.println(p);
			//System.out.println(p+ "   " + p.distanceToSet(A));
		}
	}
	


}
