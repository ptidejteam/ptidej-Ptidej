#pragma once
#include "stdafx.h"
#include <fstream>
#include <sstream>
#include "Vertex.h"
#include "Edge.h"

class Graph
{
public:
	int vertices;
	int edges;
	string name;
	vector<Vertex*> V;
	vector<Edge*> E;
	bool notAGraph;

	//stats about the graph
	int nb_labels, loops, multi_arcs, nbNodeLabels;
	int max_in_distrib, max_out_distrib, max_all_distrib, max_multi;
	int min_in_distrib, min_out_distrib, min_all_distrib, min_multi;
	double mean_in_distrib, mean_out_distrib, mean_all_distrib, mean_multi_distrib, mean_label_distrib;
	double std_in_distrib, std_out_distrib, std_all_distrib, std_label_distrib, std_multi_distrib;

	int* in_distrib; //[MAX_DEGREE];
	int* out_distrib;//[MAX_DEGREE];
	int* all_distrib;//[MAX_DEGREE];
	int* label_distrib;//[MAX_LABELS];
	int* multi_distrib;//[MAX_DEGREE];

	//Layers of importance, abandoned idea
	int* level_count;
	int* level;

	//u: PageRank, norm: (in,out) degrees
	double max_u,mean_u,std_u,min_u,maxi_u;
	double max_norm,mean_norm,std_norm,min_norm,maxi_norm;

	//used for requirements
	map<string, int> nodeNumbers;

	//2D matrix with edges and null edges
	Edge* ** G;

	//Google style
	double c;
	double* u;

	int* rank; // rank of nodes (PageRank)
	int* _rank; // rank of nodes (local norm)

	double* norm;


	double jump_proba;

	double* edge_weight;


	Graph(void);

	//build a graph given a mode(int) [initModei] and a path (string)
	Graph(int, double, string, string, double*);

	Graph(int, int, int);

	/*
	 * Generic graph format
	 * n,m
	 * #node,label_node,weight0,weight1, ..
	 * ...
	 * #node_src,#node_dest,label_edge
	 * ...
	 */
	void initMode0(ifstream*);

	/*
	 * Unina Database - Unlabeled Graphs
	 */
	void initMode1(ifstream*);

	/*
	 * Unina Database - Labeled Graphs
	 */
	void initMode2(ifstream*, int);

	/*
	 * Requirements files
	 */
	void initMode3(ifstream*);

	/*
	 * Class Diagrams with attributes/methods information
	 * n,m
	 * c,#class,class_name,#attributes,#methods
	 * a,#class,visibility@type@name
	 * m,#class,visibility@output_type@name@[input1_type,input2_type,...]
	 */
	void initMode4(ifstream*);

	/*
	 * Class Diagrams without attributes/methods information
	 * n,m
	 * c,#class,class_name,#attributes,#methods
	 */
	void initMode5(ifstream*);



	void nodeRank(double, double);

	//sort Nodes given Page Rank
	void sortNodes();

	vector<Vertex*> getV();
	vector<Edge*> getE();

	int getOrder();
	int getSize();

	Edge* getEdge(int,int);



	double* T(double *, int);
	double* computeU(double, int);
	double specialNorm(double*, double* , int);
	void printU(string);

	//unsigned int read_word(ifstream);

	void insertEdge(Edge*);
	void deleteEdge(Edge*);

	void collectStats();
	void graphDetails();
	void mozillaDetails();

	void print();

	//write a graph in a file with mode 0 format
	void write(int, string);

public:
	virtual ~Graph(void);
};
