/*------------------------------------------------    
   Transi IN JAVA VER 2.50 
 
   PROGRAMMED BY  SUNG, Ki-seok 
                  SO, young seob 
                  CHOI, jin min 
                  JU, hientaek 
                  EOM, soongeun 
                  LEE, kyuseung  
   DIRECTED BY  PROF. PARK, soondal 
   SEOUL NATIONAL UNIVERSITY 
 
   PROGRAMMED ON OCT 29, 1984 
   LAST REVISED ON JAN 13, 2000 
   MODIFIED by Yann-Gaël Guéhéneuc 
   TO BE CALLED FROM THE EPI FRAMEWORK
 -----------------------------------------------*/

package epi.algorithm;

import java.util.StringTokenizer;

public class TransportationSimplex {
	//TODO: new value?
	private final int MAXC = 800; //old: 10
	private final int MAXR = 1000; //old: 60
	private final int MT = 600;

	private final double[][] C = new double[this.MAXR][this.MAXC];
	private final double[] D1 = new double[this.MAXC];
	private final double[] DEMAND = new double[this.MAXC];
	private final double[][] EC = new double[this.MAXR + 1][this.MAXC + 1];
	//	private String ERR_MSG;
	private final double[][] FLOW = new double[this.MAXR][this.MAXC];
	private int IERROR;
	private final int[] MOVEX = new int[this.MT];
	private final int[] MOVEY = new int[this.MT];
	private int NOSUP, NODEM, CP, CT, INIT, IPR, IT; // N9
	private int OPT, MINI, MINJ, NOWI, NOWJ, INDIC;
	private final double[] S1 = new double[this.MAXR];
	private final double[] SUPPLY = new double[this.MAXR];
	private double TCOST, CMIN, XMIN;
	private final double[] U = new double[this.MAXR];
	private final double[] V = new double[this.MAXC];

	private final String input;
	private final ITransportationSimplexListener listener;

	public TransportationSimplex(
		final String input,
		final ITransportationSimplexListener listener) {

		this.input = input;
		this.listener = listener;
	}

	/************* 
	    BALANCE 
	*************/
	private void BALANCE() {
		double ss, ds, diff;
		int i, j;

		ss = 0.0;
		ds = 0.0;
		for (i = 1; i <= this.NOSUP; i++) {
			ss += this.SUPPLY[i];
		}
		for (j = 1; j <= this.NODEM; j++) {
			ds += this.DEMAND[j];
		}
		//	this.N9 = this.NODEM;
		diff = ss - ds;
		if (diff == 0.0) {
			return;
		}
		if (diff < 0.0) {
			this.listener
				.addToOutput(" The total SUPPLY must be greater than the total DEMAND.\n");
			this.listener.addToOutput(" So this problem is infeasible.\n");
			this.listener.addToOutput(" Check the DATA.\n");
			//	this.ERR_MSG =
			//		" The total SUPPLY must be greater than the total DEMAND.";
			this.IERROR = 3;
			this.error_handle();
		}
		this.NODEM += 1;
		for (i = 1; i <= this.NOSUP; i++) {
			this.C[i][this.NODEM] = 1E+20;
		}
		this.DEMAND[this.NODEM] = diff;
		if (this.IPR > 1) {
			if (diff == 0) {
				this.listener
					.addToOutput("\n The original problem is balanced.");
			}
			if (diff > 0) {
				this.listener
					.addToOutput("\n The unbalnced problem is now balanced.");
			}
		}
	}

	/************ 
	    CCBAR 
	************/
	private void CCBAR() {

		int last, i1, j1, k;
		double CBAR;

		if (this.IPR > 1) {
			this.listener.addToOutput("\n\n ** START OF CCBAR **");
		}
		last = 0;
		this.OPT = 0;
		this.CMIN = 1E+20;
		this.MINI = 0;
		this.MINJ = 0;
		for (i1 = 1; i1 <= this.NOSUP; i1++) {
			for (j1 = 1; j1 <= this.NODEM; j1++) {
				if (this.EC[i1][j1] == 0) {
					last += 1;
					this.NOWI = i1;
					this.NOWJ = j1;
					this.LOOP();
					CBAR = 0;
					for (k = 1; k <= this.INDIC; k += 2) {
						CBAR += this.C[this.MOVEX[k]][this.MOVEY[k]];
						CBAR -= this.C[this.MOVEX[k + 1]][this.MOVEY[k + 1]];
					}
					if (this.CMIN > CBAR) {
						this.CMIN = CBAR;
						this.MINI = i1;
						this.MINJ = j1;
					}
				}
			}
		}
		if (this.CMIN >= 0) {
			this.OPT = 1;
		}
		if (this.IPR > 1) {
			this.listener.addToOutput("\n\n CMIN, MINI, MINJ = " + this.CMIN
					+ " " + this.MINI + " " + this.MINJ);
			this.listener.addToOutput(" No of nonbasic cells = " + last);
			this.listener.addToOutput("\n\n ** END OF CCBAR **");
		}
	}

	/************ 
	    CHEAP 
	************/
	private void CHEAP() /* function CHEAP */{

		int i, j, icom, MINI, MINJ;
		double CMIN;

		for (i = 1; i <= this.NOSUP; i++) {
			for (j = 1; j <= this.NODEM; j++) {
				this.FLOW[i][j] = 0;
			}
		}
		for (i = 1; i <= this.NOSUP; i++) {
			this.S1[i] = this.SUPPLY[i];
		}
		for (j = 1; j <= this.NODEM; j++) {
			this.D1[j] = this.DEMAND[j];
		}
		do {
			/* find a cell of the lowest cost and assign */
			icom = 0;
			CMIN = 1E+20;
			MINI = 0;
			MINJ = 0;
			for (i = 1; i <= this.NOSUP; i++) {
				if (this.S1[i] > 0.00001) {
					icom = 1;
					for (j = 1; j <= this.NODEM; j++) {
						if (this.D1[j] > 0.00001) {
							icom = 1;
							if (CMIN > this.C[i][j]) {
								CMIN = this.C[i][j];
								MINI = i;
								MINJ = j;
							}
						}
					}
				}
			}
			if (icom == 1) {
				if (this.fabs(this.S1[MINI] - this.D1[MINJ]) < 0.0001) {
					this.FLOW[MINI][MINJ] = this.S1[MINI];
					this.S1[MINI] = 0;
					this.D1[MINJ] = 0;
				}
				if (this.fabs(this.S1[MINI] - this.D1[MINJ]) >= 0.0001
						&& this.S1[MINI] < this.D1[MINJ]) {
					this.FLOW[MINI][MINJ] = this.S1[MINI];
					this.D1[MINJ] -= this.S1[MINI];
					this.S1[MINI] = 0;
				}
				if (this.fabs(this.S1[MINI] - this.D1[MINJ]) >= 0.0001
						&& this.S1[MINI] > this.D1[MINJ]) {
					this.FLOW[MINI][MINJ] = this.D1[MINJ];
					this.S1[MINI] -= this.D1[MINJ];
					this.D1[MINJ] = 0;
				}
			}
		}
		while (icom == 1);

		if (this.IPR > 1) {
			this.listener
				.addToOutput("** \nINITIAL BASIC SOLUTION BY CHEAP METHOD **");
		}
		if (icom == 0) {
			return;
		}
	}

	/************ 
	    DEGEN 
	************/
	private void DEGEN() {

		int i, j, k;
		double diff1, diff2;

		for (i = 1; i <= this.NOSUP; i++) {
			this.U[i] = this.NODEM;
			this.S1[i] = this.SUPPLY[i];
		}
		for (j = 1; j <= this.NODEM; j++) {
			this.V[j] = this.NOSUP;
			this.D1[j] = this.DEMAND[j];
		}
		for (i = 1; i <= this.NOSUP; i++) {
			for (j = 1; j <= this.NODEM; j++) {
				this.EC[i][j] = 0;
			}
		}
		for (i = 1; i <= this.NOSUP; i++) {
			for (j = 1; j <= this.NODEM; j++) {
				if (this.FLOW[i][j] == 0.0) {
					continue;
				}
				this.EC[i][j] = 1;
				diff1 = this.S1[i] - this.FLOW[i][j];
				diff2 = this.D1[j] - this.FLOW[i][j];
				if (diff1 > 0 && diff2 > 0) {
					this.S1[i] = diff1;
					this.D1[j] = diff2;
				}
				else if (diff1 > diff2) {
					this.S1[i] = diff1;
					this.D1[j] = 0;
					this.V[j] = 0;
					for (k = 1; k <= this.NOSUP; k++) {
						if (this.U[k] != 0) {
							this.U[k]--;
						}
					}
				}
				else if (diff1 < diff2) {
					this.S1[i] = 0;
					this.D1[j] = diff2;
					this.U[i] = 0;
					for (k = 1; k <= this.NODEM; k++) {
						if (this.V[k] != 0) {
							this.V[k]--;
						}
					}
				}
				else if (this.V[j] > 1) {
					this.S1[i] = 0;
					this.D1[j] = 0;
					this.U[i] = 0;
					for (k = 1; k <= this.NODEM; k++) {
						if (this.V[k] != 0) {
							this.V[k]--;
						}
					}
				}
				else {
					this.S1[i] = 0;
					this.D1[j] = 0;
					this.V[j] = 0;
					for (k = 1; k <= this.NOSUP; k++) {
						if (this.U[k] != 0) {
							this.U[k]--;
						}
					}
				}
			}
		}
		for (i = 1; i <= this.NOSUP; i++) {
			if (this.U[i] == 0) {
				continue;
			}
			for (j = 1; j <= this.NODEM; j++) {
				if (this.U[i] != 0 && this.V[j] != 0) {
					this.EC[i][j] = 1;
					this.U[i] = 0;
					this.V[j] = 0;
					this.EC[i][j] = 1;
					this.CT++;
				}
			}
		}
		if (this.IPR > 1) {
			this.listener.addToOutput("\n** DEGEN **\n");
			this.listener.addToOutput("\n SUPPLY ----  DEMAND        FLOW\n");
			for (i = 1; i <= this.NOSUP; i++) {
				for (j = 1; j <= this.NODEM; j++) {
					if (this.EC[i][j] == 1) {
						this.listener.addToOutput("\n  SUPPLY(" + i
								+ ")  ----   DEMAND(" + j + ")         "
								+ this.FLOW[i][j]);
						this.listener.newFlow(i - 1, j - 1, this.FLOW[i][j]);
					}
				}
			}
			this.listener.addToOutput("\n\n     TOTAL COST = " + this.TCOST
					+ "\n");
		}
	}

	/************ 
	 ERROR_HANDLE 
	*************/
	private void error_handle() {

		/************************************/
		/* Error Code                       */
		/************************************/
		/* IERROR=0  No Error               */
		/* IERROR=1  Normal Exit            */
		/* IERROR=2  File Open Error        */
		/* IERROR=3  problem is infeasible  */
		/************************************/
		if (this.IERROR == 0 || this.IERROR == 1) {
			return;
		}
		else {
			this.listener.addToOutput("\nExit code: " + this.IERROR);
		}
	}
	private double fabs(final double num) {
		if (num < 0.0) {
			return -num;
		}
		else {
			return num;
		}
	}

	/************** 
	   FILEMODE 
	**************/
	private void FILEMODE() /* function FILE INPUT */{

		/*            NOSUP=4;NODEM=5;INIT=1;IPR=1;
		            C[1][1]=16;C[1][2]=16;C[1][3]=13;C[1][4]=22;C[1][5]=17;
		            C[2][1]=14;C[2][2]=14;C[2][3]=13;C[2][4]=19;C[2][5]=15;
		            C[3][1]=19;C[3][2]=19;C[3][3]=20;C[3][4]=23;C[3][5]=40;
		            C[4][1]=40;C[4][2]=10;C[4][3]=40;C[4][4]=11;C[4][5]=12;
		            SUPPLY[1]=50;SUPPLY[2]=60;SUPPLY[3]=50;SUPPLY[4]=50;
		            DEMAND[1]=30;DEMAND[2]=20;DEMAND[3]=70;DEMAND[4]=30;DEMAND[5]=60;*/
		int i, j;
		final StringTokenizer tokens = new StringTokenizer(this.input);
		this.NOSUP = Integer.parseInt(tokens.nextToken());
		this.NODEM = Integer.parseInt(tokens.nextToken());
		this.INIT = Integer.parseInt(tokens.nextToken());
		this.IPR = Integer.parseInt(tokens.nextToken());

		for (i = 1; i <= this.NOSUP; i++) {
			for (j = 1; j <= this.NODEM; j++) {
				this.C[i][j] = Double.valueOf(tokens.nextToken()).doubleValue();
			}
		}
		for (i = 1; i <= this.NOSUP; i++) {
			this.SUPPLY[i] = Double.valueOf(tokens.nextToken()).doubleValue();
		}
		for (j = 1; j <= this.NODEM; j++) {
			this.DEMAND[j] = Double.valueOf(tokens.nextToken()).doubleValue();
		}

		if (this.IPR > 1) {
			this.listener.addToOutput("NOSUP = " + this.NOSUP + "\n");
			this.listener.addToOutput("NODEM = " + this.NODEM + "\n");
			this.listener.addToOutput("INIT = " + this.INIT + "\n");
			this.listener.addToOutput("IPR = " + this.IPR + "\n");
			for (i = 1; i <= this.NOSUP; i++) {
				for (j = 1; j <= this.NODEM; j++) {
					this.listener.addToOutput("C[" + i + "][" + j + "] = "
							+ this.C[i][j] + "\n");
				}
			}
			for (i = 1; i <= this.NOSUP; i++) {
				this.listener.addToOutput("SUPPLY[" + i + "] = "
						+ this.SUPPLY[i] + "\n");
			}
			for (j = 1; j <= this.NODEM; j++) {
				this.listener.addToOutput("DEMAND[" + j + "] = "
						+ this.DEMAND[j] + "\n");
			}
		}
	}

	/************ 
	   INISOL 
	************/
	private void INISOL() /* function INISOL */{

		int i, j;

		for (i = 1; i <= this.NOSUP; i++) {
			for (j = 1; j <= this.NODEM; j++) {
				this.EC[i][j] = 0;
			}
		}
		switch (this.INIT) {
			case 1 :
				this.NORTH();
				break;
			case 2 :
				this.CHEAP();
				break;
			case 3 :
				this.VOGEL();
				break;
			case 4 :
				this.RUSSEL();
				break;
		}
		this.IT = 0; /* Initialize Number of Iteration */
		this.TCOST = 0.0;
		for (i = 1; i <= this.NOSUP; i++) {
			for (j = 1; j <= this.NODEM; j++) {
				if (this.FLOW[i][j] == 0) {
					continue;
				}
				this.EC[i][j] = 1;
				this.CT++;
				this.TCOST = this.TCOST + this.C[i][j] * this.FLOW[i][j];
			}
		}

		if (this.IPR > 1) {
			if (this.CT < this.CP) {
				this.listener
					.addToOutput(" \nThe initial solution is degenerate.");
			}
			else {
				this.listener
					.addToOutput(" \nThe initial solution is non-degenerate.");
			}
			this.listener.addToOutput("\n\n SUPPLY ----  DEMAND        FLOW\n");
			for (i = 1; i <= this.NOSUP; i++) {
				for (j = 1; j <= this.NODEM; j++) {
					if (this.EC[i][j] == 0) {
						continue;
					}
					this.listener.addToOutput("\n  SUPPLY(" + i
							+ ")  ----   DEMAND(" + j + ")         "
							+ this.FLOW[i][j]);
					this.listener.newFlow(i - 1, j - 1, this.FLOW[i][j]);
				}
			}
			this.listener.addToOutput("\n\n     TOTAL COST = " + this.TCOST
					+ "\n");
		}
	}

	/********** 
	   LOOP 
	**********/
	private void LOOP() {

		int i, j, NEWI, NEWJ;
		final int[][] BC = new int[this.MAXR][this.MAXC];
		final int[] RC = new int[this.MAXR]; /*  Row basic cell Counter             */
		final int[] CC = new int[this.MAXC]; /*  Colomn basic cell Counter          */
		int FOUND; /*  1 if found a basic cell            */
		int SUCCESS; /*  1 if cycle constructed             */

		for (i = 1; i <= this.NOSUP; i++) {
			RC[i] = 0;
		}
		for (j = 1; j <= this.NODEM; j++) {
			CC[j] = 0;
		}
		for (i = 1; i <= this.NOSUP; i++) {
			for (j = 1; j <= this.NODEM; j++) {
				if (this.EC[i][j] == 1) {
					BC[i][j] = 1;
					RC[i]++;
					CC[j]++;
				}
				else {
					BC[i][j] = 0;
				}
			}
		}
		this.MOVEX[1] = this.NOWI;
		this.MOVEY[1] = this.NOWJ;
		this.INDIC = 1;
		do {
			//TODO: temp
			if (this.INDIC < 0) {
				break;
			}

			FOUND = 0;
			SUCCESS = 0;
			NEWI = this.MOVEX[this.INDIC];
			NEWJ = this.MOVEY[this.INDIC];
			if (this.INDIC % 2 == 1) {
				if (RC[NEWI] > 0) {
					for (j = 1; j <= this.NODEM; j++) {
						if (BC[NEWI][j] != 0 && CC[j] > 0) {
							FOUND = 1;
							if (this.NOWJ == j) {
								SUCCESS = 1;
							}
							NEWJ = j;
							break;
						}
					}
				}
			}
			else {
				if (CC[NEWJ] > 0) {
					for (i = 1; i <= this.NOSUP; i++) {
						if (BC[i][NEWJ] != 0 && RC[i] > 0) {
							FOUND = 1;
							if (this.NOWI == i) {
								SUCCESS = 1;
							}
							NEWI = i;
							break;
						}
					}
				}
			}
			if (FOUND != 0) {
				this.INDIC++;
				this.MOVEX[this.INDIC] = NEWI;
				this.MOVEY[this.INDIC] = NEWJ;
				BC[NEWI][NEWJ] = 0;
				RC[NEWI]--;
				CC[NEWJ]--;
			}
			else {
				this.INDIC--;
			}
		}
		while (SUCCESS != 1);
		if (this.IPR > 1) {
			this.listener.addToOutput("\n\n** LOOP **\n");
			this.listener.addToOutput("\nNOWI,NOWJ =  " + this.NOWI + ""
					+ this.NOWJ);
			if (this.INDIC == 1) {
				this.listener.addToOutput("\n\n New loop can not be found.");
			}
			if (this.INDIC > 1) {
				this.listener.addToOutput("\n\n New loop is ");
				for (i = 1; i <= this.INDIC; i++) {
					this.listener.addToOutput("\n " + this.MOVEX[i] + ""
							+ this.MOVEY[i]);
				}
				this.listener.addToOutput("\n INDIC = " + this.INDIC);
			}
		}
	}

	/************ 
	   NEWSOL 
	************/
	private void NEWSOL() {

		int i, j;
		int dropi = 0;
		int dropj = 0;

		if (this.IPR > 1) {
			this.listener.addToOutput("\n\n ** START OF NEWSOL **");
		}
		this.NOWI = this.MINI;
		this.NOWJ = this.MINJ;
		this.XMIN = 1E+20;
		this.LOOP();
		for (i = 2; i <= this.INDIC; i += 2) {
			if (this.FLOW[this.MOVEX[i]][this.MOVEY[i]] < this.XMIN) {
				this.XMIN = this.FLOW[this.MOVEX[i]][this.MOVEY[i]];
				dropi = this.MOVEX[i];
				dropj = this.MOVEY[i];
			}
		}
		for (i = 1; i <= this.INDIC; i += 2) {
			this.FLOW[this.MOVEX[i]][this.MOVEY[i]] += this.XMIN;
			this.FLOW[this.MOVEX[i + 1]][this.MOVEY[i + 1]] -= this.XMIN;
		}
		this.EC[this.MINI][this.MINJ] = 1;
		this.EC[dropi][dropj] = 0;
		this.TCOST = this.TCOST + this.CMIN * this.XMIN;
		if (this.IPR > 1) {
			this.listener
				.addToOutput("\n     SUPPLY   ----  DEMAND       FLOW\n");
			for (i = 1; i <= this.NOSUP; i++) {
				for (j = 1; j <= this.NODEM; j++) {
					if (this.FLOW[i][j] == 0.0) {
						continue;
					}
					this.listener.addToOutput("\n      SUPPLY(" + i
							+ ")    ----   DEMAND(" + j + ")        "
							+ this.FLOW[i][j]);
					this.listener.newFlow(i - 1, j - 1, this.FLOW[i][j]);
				}
			}
			this.listener.addToOutput("\n\n     TOTAL COST = " + this.TCOST
					+ "\n");
			this.listener.addToOutput("\n\n     NUMBER OF ITERATION = "
					+ this.IT + "\n");
			this.listener.addToOutput("\n\n ** END OF NEWSOL **");
		}
	}

	/************ 
	    NORTH 
	************/
	private void NORTH() /* function NORTH */{

		int i, j;

		for (i = 1; i <= this.NOSUP; i++) {
			for (j = 1; j <= this.NODEM; j++) {
				this.FLOW[i][j] = 0;
			}
		}
		for (i = 1; i <= this.NOSUP; i++) {
			this.S1[i] = this.SUPPLY[i];
		}
		for (j = 1; j <= this.NODEM; j++) {
			this.D1[j] = this.DEMAND[j];
		}
		for (i = 1; i <= this.NOSUP; i++) {
			if (this.fabs(this.S1[i]) < 0.00001) {
				continue;
			}
			for (j = 1; j <= this.NODEM; j++) {
				if (this.fabs(this.D1[j]) < 0.00001) {
					continue;
				}
				if (this.fabs(this.S1[i] - this.D1[j]) < 0.0001) {
					this.FLOW[i][j] = this.S1[i];
					this.S1[i] = 0;
					this.D1[j] = 0;
				}
				else if (this.S1[i] < this.D1[j]) {
					this.FLOW[i][j] = this.S1[i];
					this.D1[j] -= this.S1[i];
					this.S1[i] = 0;
				}
				else {
					this.FLOW[i][j] = this.D1[j];
					this.S1[i] -= this.D1[j];
					this.D1[j] = 0;
				}
			}
		}
		if (this.IPR > 1) {
			this.listener
				.addToOutput("\n** INITIAL BASIC SOLUTION BY NORTH-WEST METHOD\n");
		}
	}

	/************ 
	    OPTSOL 
	************/
	private void OPTSOL() {

		int i, j;

		this.listener.addToOutput("\n\n *** OPTIMAL SOLUTION *** \n");
		this.listener
			.addToOutput("\n       SUPPLY      ----    DEMAND            FLOW\n");
		this.listener.addToOutput("\n\n *** OPTIMAL SOLUTION *** \n");
		this.listener
			.addToOutput("\n       SUPPLY      ----    DEMAND            FLOW\n");
		for (i = 1; i <= this.NOSUP; i++) {
			for (j = 1; j <= this.NODEM; j++) {
				if (this.FLOW[i][j] == 0.0) {
					continue;
				}
				this.listener.addToOutput("\n      SUPPLY(" + i
						+ ")    ----   DEMAND(" + j + ")        "
						+ this.FLOW[i][j]);
				this.listener.newFlow(i - 1, j - 1, this.FLOW[i][j]);
			}
		}
		this.listener.addToOutput("\n\n     TOTAL COST = " + this.TCOST + "\n");
		this.listener.addToOutput("\n     NUMBER OF ITERATION = " + this.IT
				+ "\n");
	}

	/************ 
	    RUSSEL 
	************/
	private void RUSSEL() /* function RUSSEL */{
		int i, j, k, icom, MINI, MINJ;
		double CMIN, CBAR;

		for (i = 1; i <= this.NOSUP; i++) {
			for (j = 1; j <= this.NODEM; j++) {
				this.FLOW[i][j] = 0;
			}
		}
		for (i = 1; i <= this.NOSUP; i++) {
			this.S1[i] = this.SUPPLY[i];
		}
		for (j = 1; j <= this.NODEM; j++) {
			this.D1[j] = this.DEMAND[j];
		}
		/* find a cell of the lowest cbar(i,j) and assign */
		do {
			icom = 0;
			CMIN = 1E+20;
			MINI = 0;
			MINJ = 0;
			//L44:    icom=0; CMIN=1E+20; MINI=0; MINJ=0; 
			for (i = 1; i <= this.NOSUP; i++) {
				if (this.S1[i] > 0.00001) {
					icom = 1;
					for (j = 1; j <= this.NODEM; j++) {
						if (this.D1[j] > 0.00001) {
							icom = 1;
							for (k = 1; k <= this.NOSUP; k++) {
								this.U[k] = -1E+20;
							}
							for (k = 1; k <= this.NODEM; k++) {
								if (this.U[i] <= this.C[i][k]) {
									this.U[i] = this.C[i][k];
								}
							}
							for (k = 1; k <= this.NODEM; k++) {
								this.V[k] = -1E+20;
							}
							for (k = 1; k <= this.NOSUP; k++) {
								if (this.V[j] <= this.C[k][j]) {
									this.V[j] = this.C[k][j];
								}
							}
							CBAR = this.C[i][j] - this.U[i] - this.V[j];
							if (CMIN > CBAR) {
								CMIN = CBAR;
								MINI = i;
								MINJ = j;
							}
						}
					}
				}
			}
			if (icom == 1) {
				if (this.fabs(this.S1[MINI] - this.D1[MINJ]) < 0.0001) {
					this.FLOW[MINI][MINJ] = this.S1[MINI];
					this.S1[MINI] = 0;
					this.D1[MINJ] = 0;
				}
				if (this.fabs(this.S1[MINI] - this.D1[MINJ]) >= 0.0001
						&& this.S1[MINI] < this.D1[MINJ]) {
					this.FLOW[MINI][MINJ] = this.S1[MINI];
					this.D1[MINJ] -= this.S1[MINI];
					this.S1[MINI] = 0;
				}
				if (this.fabs(this.S1[MINI] - this.D1[MINJ]) >= 0.0001
						&& this.S1[MINI] > this.D1[MINJ]) {
					this.FLOW[MINI][MINJ] = this.D1[MINJ];
					this.S1[MINI] -= this.D1[MINJ];
					this.D1[MINJ] = 0;
				}
			}
		}
		while (icom == 1);
		// if (icom == 1) goto L44; 
		if (this.IPR > 1) {
			this.listener
				.addToOutput("** \nINITIAL BASIC SOLUTION BY RUSSEL METHOD **");
		}
		if (icom == 0) {
			return;
		}
	}

	/********** 
	   MAIN 
	**********/
	public int solve() {

		this.FILEMODE();
		this.error_handle();
		this.CP = this.NOSUP + this.NODEM - 1;
		this.CT = 0;
		this.BALANCE();
		this.error_handle();
		this.INISOL();
		if (this.CT < this.CP) {
			this.DEGEN();
		}
		this.CCBAR();
		while (this.OPT == 0) {
			this.NEWSOL();
			this.IT++;
			this.CCBAR();
		}
		this.OPTSOL();
		this.IERROR = 1;
		this.error_handle();
		return 0;
	}

	/************ 
	   VOGEL 
	************/
	private void VOGEL() /* function VOGEL */{

		int i, j, icom, MINI, imin1, MINJ, jmin1, itt;
		double CMIN1, CMIN2, DIFF, DIFFT;

		for (i = 1; i <= this.NOSUP; i++) {
			for (j = 1; j <= this.NODEM; j++) {
				this.FLOW[i][j] = 0;
			}
		}
		for (i = 1; i <= this.NOSUP; i++) {
			this.S1[i] = this.SUPPLY[i];
		}
		for (j = 1; j <= this.NODEM; j++) {
			this.D1[j] = this.DEMAND[j];
		}
		do {
			//L44 : icom=0; DIFF=-1E+20; MINI=0; MINJ=0; 
			icom = 0;
			DIFF = -1E+20;
			MINI = 0;
			MINJ = 0;
			/* find a cell of the maximal DIFF in each row */
			for (i = 1; i <= this.NOSUP; i++) {
				if (this.S1[i] > 0.00001) {
					icom = 1;
					CMIN1 = 1E+20;
					imin1 = 0;
					jmin1 = 0;
					CMIN2 = 1E+20;
					itt = 0;
					for (j = 1; j <= this.NODEM; j++) {
						if (this.D1[j] > 0.00001) {
							itt++;
							if (CMIN1 > this.C[i][j]) {
								CMIN1 = this.C[i][j];
								imin1 = i;
								jmin1 = j;
							}
						}
					}
					if (itt > 1) {
						for (j = 1; j <= this.NODEM; j++) {
							if (this.D1[j] > 0.00001) {
								if (j != jmin1 && CMIN2 > this.C[i][j]) {
									CMIN2 = this.C[i][j];
								}
							}
						}
					}
					DIFFT = CMIN2 - CMIN1;
					if (itt == 1) {
						DIFFT = 0;
					}
					if (DIFF < DIFFT) {
						DIFF = DIFFT;
						MINI = imin1;
						MINJ = jmin1;
					}
				}
			}
			/* find a cell of the maximal DIFF in each column*/
			for (j = 1; j <= this.NODEM; j++) {
				if (this.D1[j] > 0.00001) {
					icom = 1;
					CMIN1 = 1E+20;
					imin1 = 0;
					jmin1 = 0;
					CMIN2 = 1E+20;
					itt = 0;
					for (i = 1; i <= this.NOSUP; i++) {
						if (this.S1[i] > 0.00001) {
							itt++;
							if (CMIN1 > this.C[i][j]) {
								CMIN1 = this.C[i][j];
								imin1 = i;
								jmin1 = j;
							}
						}
					}
					if (itt > 1) {
						for (i = 1; i <= this.NOSUP; i++) {
							if (this.S1[i] > 0.00001) {
								if (i != imin1 && CMIN2 > this.C[i][j]) {
									CMIN2 = this.C[i][j];
								}
							}
						}
					}
					DIFFT = CMIN2 - CMIN1;
					if (itt == 1) {
						DIFFT = 0;
					}
					if (DIFF < DIFFT) {
						DIFF = DIFFT;
						MINI = imin1;
						MINJ = jmin1;
					}
				}
			}
			if (icom == 1) {
				if (this.fabs(this.S1[MINI] - this.D1[MINJ]) < 0.0001) {
					this.FLOW[MINI][MINJ] = this.S1[MINI];
					this.S1[MINI] = 0;
					this.D1[MINJ] = 0;
				}
				if (this.fabs(this.S1[MINI] - this.D1[MINJ]) >= 0.0001
						&& this.S1[MINI] < this.D1[MINJ]) {
					this.FLOW[MINI][MINJ] = this.S1[MINI];
					this.D1[MINJ] -= this.S1[MINI];
					this.S1[MINI] = 0;
				}
				if (this.fabs(this.S1[MINI] - this.D1[MINJ]) >= 0.0001
						&& this.S1[MINI] > this.D1[MINJ]) {
					this.FLOW[MINI][MINJ] = this.D1[MINJ];
					this.S1[MINI] -= this.D1[MINJ];
					this.D1[MINJ] = 0;
				}
			}
		}
		while (icom == 1);

		if (this.IPR > 1) {
			if (icom == 0) {
				return;
			}
		}
	}
}
