/**
 * Class for finding and printing the optimal Chinese Postman tour of multidigraphs.
 * For more details, read <a href="http://www.uclic.ucl.ac.uk/harold/cpp">http://www.uclic.ucl.ac.uk/harold/cpp</a>.
 *
 * @author Harold Thimbleby, 2001, 2, 3
 */

// Chinese Postman Code
// Harold Thimbleby, 2001-3 

// <tex file="class.tex">
package epi.algorithm;

import java.util.Vector;

public class ChinesePostman {
	// </tex><tex file="printCPT.tex">
	private static final int NONE = -1; // anything < 0
	// </tex>

	private final int arcs[][]; // adjacency matrix, counts arcs between vertices
	private float basicCost; // total cost of traversing each arc once
	private final float c[][]; // costs of cheapest arcs or paths
	private final String cheapestLabel[][]; // labels of cheapest arcs
	private final boolean defined[][];
	// whether path cost is defined between vertices
	private final int delta[]; // deltas of vertices
	private final int f[][]; // repeated arcs in CPT
	private final Vector label[][];
	// vectors of labels of arcs (for each vertex pair)
	private int N; // number of vertices
	private int neg[], pos[]; // unbalanced vertices
	private final int path[][]; // spanning tree of the graph
	private final IChinesePostmanListener listener;

	// <literal>$\vdots$\\\vbox{}</literal><null>
	// Other declarations are described below
	// <literal>$\vdots$\\</literal>
	// </tex><tex file="constructor.tex">
	// allocate array memory, and instantiate graph object
	public ChinesePostman(
		final int vertices,
		final IChinesePostmanListener listener) {
		this.listener = listener;
		if ((this.N = vertices) <= 0) {
			throw new Error("Graph is empty");
		}
		this.delta = new int[this.N];
		this.defined = new boolean[this.N][this.N];
		this.label = new Vector[this.N][this.N];
		this.c = new float[this.N][this.N];
		this.f = new int[this.N][this.N];
		this.arcs = new int[this.N][this.N];
		this.cheapestLabel = new String[this.N][this.N];
		this.path = new int[this.N][this.N];
		this.basicCost = 0;
	}

	// </tex><tex file="addedge.tex">
	public ChinesePostman addArc(
		final String lab,
		final int u,
		final int v,
		final float cost) {
		if (!this.defined[u][v]) {
			this.label[u][v] = new Vector();
		}
		this.label[u][v].addElement(lab);
		this.basicCost += cost;
		if (!this.defined[u][v] || this.c[u][v] > cost) {
			this.c[u][v] = cost;
			this.cheapestLabel[u][v] = lab;
			this.defined[u][v] = true;
			this.path[u][v] = v;
		}
		this.arcs[u][v]++;
		this.delta[u]++;
		this.delta[v]--;
		return this;
	}

	// </tex><tex file="check.tex">
	private void checkValid(final boolean validate) {
		for (int i = 0; i < this.N; i++) {
			for (int j = 0; j < this.N; j++) {
				if (!this.defined[i][j]) {
					if (validate) {
						this.addArc("dm", i, j, 100);
					}
					else {
						throw new Error("Graph is not strongly connected " + i
								+ " " + j);
					}
				}
			}
			if (this.c[i][i] < 0) {
				throw new Error("Graph has a negative cycle");
			}
		}
	}

	// </tex><tex file="cost.tex">
	public float cost() {
		return this.basicCost + this.phi();
	}

	// Print out most of the matrices: defined, path and f
	//	private void debug() {
	//		for (int i = 0; i < N; i++) {
	//			System.out.print(i + " ");
	//			for (int j = 0; j < N; j++)
	//				System.out.print(
	//					j
	//						+ ":"
	//						+ (defined[i][j] ? "T" : "F")
	//						+ " "
	//						+ c[i][j]
	//						+ " p="
	//						+ path[i][j]
	//						+ " f="
	//						+ f[i][j]
	//						+ "; ");
	//			System.out.println();
	//		}
	//	}

	// Print arcs and f
	//	private void debugarcf() {
	//		for (int i = 0; i < N; i++) {
	//			System.out.print("f[" + i + "]= ");
	//			for (int j = 0; j < N; j++)
	//				System.out.print(f[i][j] + " ");
	//			System.out.print("  arcs[" + i + "]= ");
	//			for (int j = 0; j < N; j++)
	//				System.out.print(arcs[i][j] + " ");
	//			System.out.println();
	//		}
	//	}

	// Print out cost matrix.
	//	private void debugc() {
	//		for (int i = 0; i < N; i++) {
	//			boolean any = false;
	//			for (int j = 0; j < N; j++)
	//				if (c[i][j] != 0) {
	//					any = true;
	//					System.out.print(
	//						"c("
	//							+ i
	//							+ ","
	//							+ j
	//							+ ":"
	//							+ label[i][j]
	//							+ ")="
	//							+ c[i][j]
	//							+ "  ");
	//				}
	//			if (any)
	//				System.out.println();
	//		}
	//	}

	// Print out non zero f elements, and phi
	//	private void debugf() {
	//		float sum = 0;
	//		for (int i = 0; i < N; i++) {
	//			boolean any = false;
	//			for (int j = 0; j < N; j++)
	//				if (f[i][j] != 0) {
	//					any = true;
	//					System.out.print(
	//						"f("
	//							+ i
	//							+ ","
	//							+ j
	//							+ ":"
	//							+ label[i][j]
	//							+ ")="
	//							+ f[i][j]
	//							+ "@"
	//							+ c[i][j]
	//							+ "  ");
	//					sum += f[i][j] * c[i][j];
	//				}
	//			if (any)
	//				System.out.println();
	//		}
	//		System.out.println("-->phi=" + sum);
	//	}
	//</tex><tex file="greedy.tex">

	private void findFeasible() { // delete next 3 lines to be faster, but non-reentrant
		final int delta[] = new int[this.N];
		for (int i = 0; i < this.N; i++) {
			delta[i] = this.delta[i];
		}

		for (int u = 0; u < this.neg.length; u++) {
			final int i = this.neg[u];
			for (int v = 0; v < this.pos.length; v++) {
				final int j = this.pos[v];
				this.f[i][j] = -delta[i] < delta[j] ? -delta[i] : delta[j];
				delta[i] += this.f[i][j];
				delta[j] -= this.f[i][j];
			}
		}
	}

	private int findPath(final int from, final int f[][]) // find a path between unbalanced vertices
	{
		for (int i = 0; i < this.N; i++) {
			if (f[from][i] > 0) {
				return i;
			}
		}
		return ChinesePostman.NONE;
	}
	//</tex><tex file="degrees.tex">

	private void findUnbalanced() {
		int nn = 0, np = 0; // number of vertices of negative/positive delta

		for (int i = 0; i < this.N; i++) {
			if (this.delta[i] < 0) {
				nn++;
			}
			else if (this.delta[i] > 0) {
				np++;
			}
		}

		this.neg = new int[nn];
		this.pos = new int[np];
		nn = np = 0;
		for (int i = 0; i < this.N; i++) {
			if (this.delta[i] < 0) {
				this.neg[nn++] = i;
			}
			else if (this.delta[i] > 0) {
				this.pos[np++] = i;
			}
		}
	}

	// </tex><tex file="iterate.tex">
	private boolean improvements() {
		//System.out.println("improvements");
		final ChinesePostman residual =
			new ChinesePostman(this.N, this.listener);
		for (int u = 0; u < this.neg.length; u++) {
			final int i = this.neg[u];
			for (int v = 0; v < this.pos.length; v++) {
				final int j = this.pos[v];
				residual.addArc(null, i, j, this.c[i][j]);
				if (this.f[i][j] != 0) {
					residual.addArc(null, j, i, -this.c[i][j]);
				}
			}
		}
		residual.leastCostPaths(); // find a negative cycle
		for (int i = 0; i < this.N; i++) {
			if (residual.c[i][i] < 0) // cancel the cycle (if any)
			{
				int k = 0, u, v;
				boolean kunset = true;
				u = i;
				do // find k to cancel
				{
					v = residual.path[u][i];
					if (residual.c[u][v] < 0 && (kunset || k > this.f[v][u])) {
						k = this.f[v][u];
						kunset = false;
					}
				}
				while ((u = v) != i);
				u = i;
				do // cancel k along the cycle
				{
					v = residual.path[u][i];
					if (residual.c[u][v] < 0) {
						this.f[v][u] -= k;
					}
					else {
						this.f[u][v] += k;
					}
				}
				while ((u = v) != i);
				return true; // have another go
			}
		}
		return false; // no improvements found
	}
	//</tex>

	/** Floyd-Warshall algorithm
	 *  Assumes no negative self-cycles.
	 *  Finds least cost paths or terminates on finding any non-trivial negative cycle.
	 */
	// <tex file="floyd.tex">
	private void leastCostPaths() {
		for (int k = 0; k < this.N; k++) {
			for (int i = 0; i < this.N; i++) {
				if (this.defined[i][k]) {
					for (int j = 0; j < this.N; j++) {
						if (this.defined[k][j]
								&& (!this.defined[i][j] || this.c[i][j] > this.c[i][k]
										+ this.c[k][j])) {
							this.path[i][j] = this.path[i][k];
							this.c[i][j] = this.c[i][k] + this.c[k][j];
							this.defined[i][j] = true;
							if (i == j && this.c[i][j] < 0) {
								return; // stop on negative cycle
							}
						}
					}
				}
			}
		}
	}

	private float phi() {
		float phi = 0;
		for (int i = 0; i < this.N; i++) {
			for (int j = 0; j < this.N; j++) {
				phi += this.c[i][j] * this.f[i][j];
			}
		}
		return phi;
	}

	public void printCPT(final int startVertex) {
		int v = startVertex;

		// delete next 7 lines to be faster, but non-reentrant
		//	int arcs[][] = new int[N][N];
		//	int f[][] = new int[N][N];
		//	for (int i = 0; i < N; i++)
		//		for (int j = 0; j < N; j++) {
		//			arcs[i][j] = this.arcs[i][j];
		//			f[i][j] = this.f[i][j];
		//		}

		while (true) {
			int u = v;
			if ((v = this.findPath(u, this.f)) != ChinesePostman.NONE) {
				this.f[u][v]--; // remove path
				for (int p; u != v; u = p) // break down path into its arcs
				{
					p = this.path[u][v];
					this.listener.addToOutput("Take arc "
							+ this.cheapestLabel[u][p] + " from " + u + " to "
							+ p);
					this.listener.newLeg(u, p, this.cheapestLabel[u][p]);
				}
			}
			else {
				final int bridgeVertex = this.path[u][startVertex];
				if (this.arcs[u][bridgeVertex] == 0) {
					break; // finished if bridge already used
				}
				v = bridgeVertex;
				for (int i = 0; i < this.N; i++) {
					if (i != bridgeVertex && this.arcs[u][i] > 0) {
						v = i;
						break;
					}
				}
				this.arcs[u][v]--; // decrement count of parallel arcs
				this.listener.addToOutput("Take arc "
						+ this.label[u][v].elementAt(this.arcs[u][v])
						+ " from " + u + " to " + v);
				this.listener.newLeg(u, v, (String) this.label[u][v]
					.elementAt(this.arcs[u][v]));
				// use each arc label in turn
			}
		}
	}

	public void solve(final boolean validate) {
		this.leastCostPaths();
		this.checkValid(validate);
		this.findUnbalanced();
		this.findFeasible();
		while (this.improvements()) {
			;
		}
	}

	// <tex file="class.tex">
}
// </tex>

// <tex file="open.tex">
//	class OpenCPP {
//		class Arc {
//			float cost;
//			String lab;
//			int u, v;
//			Arc(String lab, int u, int v, float cost) {
//				this.lab = lab;
//				this.u = u;
//				this.v = v;
//				this.cost = cost;
//			}
//		}
//		//</tex>
//	
//		static void test() {
//			OpenCPP G = new OpenCPP(4); // create a graph of four vertices
//			// add the arcs for the example graph
//			G
//				.addArc("a", 0, 1, 1)
//				.addArc("b", 0, 2, 1)
//				.addArc("c", 1, 2, 1)
//				.addArc("d", 1, 3, 1)
//				.addArc("e", 2, 3, 1)
//				.addArc("f", 3, 0, 1);
//			int besti = 0;
//			float bestCost = 0;
//			for (int i = 0; i < 4; i++) {
//				System.out.println("Solve from " + i);
//				float c = G.printCPT(i);
//				System.out.println("Cost = " + c);
//				if (i == 0 || c < bestCost) {
//					bestCost = c;
//					besti = i;
//				}
//			}
//			System.out.println("// <tex file=\"open.tex\">");
//			G.printCPT(besti);
//			System.out.println("Cost = " + bestCost);
//			System.out.println("// </tex>");
//		}
//		Vector arcs = new Vector();
//		int N;
//	
//		OpenCPP(int vertices) {
//			N = vertices;
//		}
//	
//		OpenCPP addArc(String lab, int u, int v, float cost) {
//			if (cost < 0)
//				throw new Error("Graph has negative costs");
//			arcs.addElement(new Arc(lab, u, v, cost));
//			return this;
//		}
//	
//		float printCPT(int startVertex) {
//			ChinesePostman bestGraph = null, g;
//			float bestCost = 0, cost;
//			int i = 0;
//			do {
//				g = new ChinesePostman(N + 1);
//				for (int j = 0; j < arcs.size(); j++) {
//					Arc it = (Arc) arcs.elementAt(j);
//					g.addArc(it.lab, it.u, it.v, it.cost);
//				}
//				cost = g.basicCost;
//				g.findUnbalanced(); // initialise g.neg on original graph
//				g.addArc("'virtual start'", N, startVertex, cost);
//				g.addArc("'virtual end'",
//				// graph is Eulerian if neg.length=0
//				g.neg.length == 0 ? startVertex : g.neg[i], N, cost);
//				g.solve();
//				if (bestGraph == null || bestCost > g.cost()) {
//					bestCost = g.cost();
//					bestGraph = g;
//				}
//			}
//			while (++i < g.neg.length);
//			System.out.println(
//				"Open CPT from " + startVertex + " (ignore virtual arcs)");
//			bestGraph.printCPT(N);
//			return cost + bestGraph.phi();
//		}
//		// <tex file="open.tex">	
//	
//	}
// </tex>
