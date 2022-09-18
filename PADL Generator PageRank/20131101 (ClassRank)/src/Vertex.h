#pragma once
#include "stdafx.h"
#include "Edge.h"

class Vertex
{
public:
	//attributes
	int number; //# of the vertex in the vector V of a Graph
	string label;
	float weight[MAX_W]; //table of associated weights
	vector<Edge*> in; //incoming edges
    vector<Edge*> out; //outgoing edges
	vector<Edge*> edges; //all edges

	map<string, string> attributes;
	map<string, string> methods;

	float sum_out; //for PageRank algo, number or weighted sum of outgoing edges

	//constructors
	Vertex(void);
	Vertex(int); // number
	Vertex(int, string); // number, label
	Vertex(int, string, float*); // number, label, table of weights

	//getters
	int getNumber();
	string getLabel();
	float* getWeight();
	vector<Edge*> getIn();
	vector<Edge*> getOut();
	vector<Edge*> getEdges();

	//setters
	int setNumber(int);
	string setLabel(string);
	float* setWeight(float* );

	float addWeight(float,int);//w,i (add w as a ith weight)

	//utilities
	void print();

public:
	virtual ~Vertex(void);
};
