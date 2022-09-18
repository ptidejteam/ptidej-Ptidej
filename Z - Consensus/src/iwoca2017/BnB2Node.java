package iwoca2017;

import java.util.ArrayList;
import java.util.List;

public class BnB2Node implements Comparable<BnB2Node> {

	public boolean[][] tabC;//matrice de contraintes: tabC[i][j]=true == (i<j) ds contraintes
	public int n;
	public int best_lower_bound;

	public BnB2Node(int N) {
		n = N;
		beginningThinks();
	}

	public String toString() {
		String s = "n";
		s += best_lower_bound;
		return s;
	}

	private void beginningThinks() {
		tabC = new boolean[n][n];
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				tabC[i - 1][j - 1] = false;
			}
		}
		best_lower_bound = -1;
	}

	public void feedLowerBound(int lb) {
		if (best_lower_bound == -1) {
			best_lower_bound = lb;
		}
		else if (best_lower_bound < lb) {
			best_lower_bound = lb;
		}
		else {
			//nothing
		}
	}

	public void calculateLowerBound(Instance myInstance) {
		best_lower_bound = borneInfSet_wConstraints(myInstance);
	}

	//methode qui retourne une borneInf sur un set d'elements muni de contraintes utilisant la methode de Conitzer simplifiee
	private int borneInfSet_wConstraints(Instance myInstance) {
		//besoin de tabD

		int borneInf = 0;

		//System.out.println("min costs");
		int[][] poids = new int[n][n];
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (i < j) {
					borneInf += Math.min(
						myInstance.tabD[i - 1][j - 1],
						myInstance.tabD[j - 1][i - 1]);
				}
			}
		}

		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (myInstance.tabD[i - 1][j - 1] < myInstance.tabD[j - 1][i
						- 1]) {// i < j
					poids[i - 1][j - 1] = myInstance.tabD[j - 1][i - 1]
							- myInstance.tabD[i - 1][j - 1];//residu majoritaire (cout pour peter ij)
				}
				else {
					poids[i - 1][j - 1] = 0;
				}
			}
		}

		//known costs
		//System.out.println("known costs");
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (tabC[i - 1][j - 1]) {
					borneInf += poids[j - 1][i - 1];
					poids[j - 1][i - 1] = 0;
				}
			}
		}

		//unknown costs with modified Conitzer & Davenport lower bound
		//System.out.println("unknown costs");
		int min = 0;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if ((i != j) && (poids[i - 1][j - 1] > 0)) {
					for (int k = 1; k <= n; k++) {
						if (j != k && k != i) {
							if ((poids[i - 1][j - 1] > 0)
									&& (poids[j - 1][k - 1] > 0)
									&& (poids[k - 1][i - 1] > 0)) {
								min = Math.min(
									poids[i - 1][j - 1],
									Math.min(
										poids[j - 1][k - 1],
										poids[k - 1][i - 1]));
								poids[i - 1][j - 1] -= min;
								poids[j - 1][k - 1] -= min;
								poids[k - 1][i - 1] -= min;
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
		boolean checkedVertex[] = new boolean[n];
		List<Integer> queue = new ArrayList<Integer>();
		List<Integer> branch = new ArrayList<Integer>();
		int currentVertex = -1;
		for (int i = 0; i < n; i++) {
			checkedVertex[i] = false;
		}
		boolean cycleFound = false;

		for (int e1 = 1; e1 <= n; e1++) {

			cycleFound = true;//just for the start

			while (cycleFound) {

				queue = new ArrayList<Integer>();
				branch = new ArrayList<Integer>();
				currentVertex = -1;
				for (int i = 0; i < n; i++) {
					checkedVertex[i] = false;
				}
				cycleFound = false;//no cycles at the beginning, if one found, continue searching

				if (!checkedVertex[e1 - 1]) {
					queue.add(e1);
					//System.out.println(queue.size());
					while (!queue.isEmpty()) {
						currentVertex = queue.get(queue.size() - 1);
						queue.remove(queue.size() - 1);

						if (currentVertex == -1) {
							branch.remove(branch.size() - 1);
						}
						else {
							if (checkedVertex[currentVertex - 1]) {
								//nothing
							}
							else {
								branch.add(currentVertex);
								checkedVertex[currentVertex - 1] = true;
								queue.add(-1);
								for (int e2 = 1; e2 <= n; e2++) {
									if (poids[currentVertex - 1][e2 - 1] > 0
											&& !cycleFound) {
										if (checkedVertex[e2 - 1]) {
											//no add
											if (branch.contains(e2)) {
												int minArc = 9999999;
												for (int i = branch.indexOf(
													e2); i < branch.size()
															- 1; i++) {
													if (poids[branch
														.get(i) - 1][branch
															.get(i + 1)
																- 1] < minArc) {
														minArc = poids[branch
															.get(i) - 1][branch
																.get(i + 1)
																	- 1];
													}
												}
												if (poids[branch
													.get(branch.size() - 1)
														- 1][branch.get(
															branch.indexOf(e2))
																- 1] < minArc) {
													minArc = poids[branch
														.get(branch.size() - 1)
															- 1][branch.get(
																branch.indexOf(
																	e2)) - 1];
												}

												if (minArc > 0) {
													addCycles += minArc;
													//System.out.println("cycle! : " + branch + " "+ e2 + " ("+minArc+")");
												}

												for (int i = branch.indexOf(
													e2); i < branch.size()
															- 1; i++) {
													poids[branch.get(i)
															- 1][branch.get(
																i + 1) - 1] -=
																	minArc;
												}
												poids[branch
													.get(branch.size() - 1)
														- 1][branch.get(
															branch.indexOf(e2))
																- 1] -= minArc;

												queue.clear();
												branch.clear();
												cycleFound = true;
											}
										}
										else {
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

		return borneInf;
	}

	public void giveConstraints(boolean[][] tabC2) {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				tabC[i - 1][j - 1] = tabC2[i - 1][j - 1];
			}
		}

	}

	public void addConstraint(int e1, int e2) {
		tabC[e1 - 1][e2 - 1] = true;
		transitiveClosing(e1, e2);
	}

	private void transitiveClosing(int i, int j) {
		//if we have the constraint i<j
		for (int k = 1; k <= n; k++) {
			//jk
			if (tabC[j - 1][k - 1] && !tabC[i - 1][k - 1]) {
				tabC[i - 1][k - 1] = true;
				transitiveClosing(i, k);
			}

			//ki
			if (tabC[k - 1][i - 1] && !tabC[k - 1][j - 1]) {
				tabC[k - 1][j - 1] = true;
				transitiveClosing(k, j);
			}
		}
	}

	public boolean[][] getTab() {
		return tabC;
	}

	@Override
	public int compareTo(BnB2Node otherNode) {
		if (this.equals(otherNode)) {
			return 0;
		}
		else if (best_lower_bound == otherNode.best_lower_bound) {
			boolean[][] tabC2 = null;
			tabC2 = otherNode.getTab();
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n; j++) {
					if (tabC[i - 1][j - 1] != tabC2[i - 1][j - 1]) {
						if (tabC[i - 1][j - 1]) {
							return 1;
						}
						else {
							return -1;
						}
					}
				}
			}
		}
		else {
			return (best_lower_bound - otherNode.best_lower_bound);
		}

		return (best_lower_bound - otherNode.best_lower_bound);

		//return (best_lower_bound - otherNode.best_lower_bound);
	}

	@Override
	public boolean equals(Object that) {
		boolean[][] tabC2 = null;
		if (this == that)
			return true;
		if (!(that instanceof BnB2Node))
			return false;

		BnB2Node that2 = (BnB2Node) that;
		tabC2 = that2.getTab();
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (tabC[i - 1][j - 1] != tabC2[i - 1][j - 1])
					return false;
			}
		}
		return true;

	}

}