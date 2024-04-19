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

	//stats about the graph
	int max_in_distrib, max_out_distrib, max_all_distrib, max_multi, min_in_distrib, min_out_distrib, min_all_distrib, min_multi, nb_labels;
	float mean_in_distrib, mean_out_distrib, mean_all_distrib, mean_multi_distrib, mean_label_distrib, std_in_distrib, std_out_distrib, std_all_distrib, std_label_distrib, std_multi_distrib;

	int* in_distrib; //[MAX_DEGREE];
	int* out_distrib;//[MAX_DEGREE];
	int* all_distrib;//[MAX_DEGREE];
	int* label_distrib;//[MAX_LABELS];
	int loops;
	int multi_arcs;
	int* multi_distrib;//[MAX_DEGREE];
	int* level_count;
	int* level;
	int nbNodeLabels;

	float max_u,mean_u,std_u,min_u,maxi_u;
	float max_norm,mean_norm,std_norm,min_norm,maxi_norm;
	

	map<string, int> nodeNumbers;

	Edge* ** G;

	Graph(void);
	Graph(int, string);
	Graph(int, int, int); 

	void initMode0(ifstream*);
	void initMode4(ifstream*);
	void initMode5(ifstream*);
	void initMode1(ifstream*);
	void initMode2(ifstream*, int);

	void initMode3(ifstream*);

	void initMode6(ifstream*);

	void nodeRank(float, float);
	void sortNodes();

	vector<Vertex*> getV();
	vector<Edge*> getE();

	int getOrder();
	int getSize();

	Edge* getEdge(int,int);

	//Google style
	float c;
	float* u;
	
	int* rank;

	float* norm;
	int* _rank;

	float* T(float *, int);
	float* computeU(float, int);

	float specialNorm(float*, float* , int);
	void printU();

	//unsigned int read_word(ifstream);

	void insertEdge(Edge*);
	void deleteEdge(Edge*);
	void collectStats();
	void graphDetails();
	void mozillaDetails();

	void print();
	void write(int, string);

public:
	virtual ~Graph(void);
};