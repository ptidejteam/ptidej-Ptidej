#pragma once
#include "stdafx.h"

class Edge
{
public:
	
	//attributes
	int number; //# in the vector E of a Graph
	int from;
	int to;
	string label;
	float weight[MAX_W]; // table of associated weights

	//constructors
	Edge(void);
	Edge(int, int); //from, to
	Edge(int, int, string); //from, to, label
	Edge(int, int, string, float*); //from, to, label, weights	

	//getters
	int getNumber();
	int getFrom();
	int getTo();
	string getLabel();
	float* getWeight();

	//setters
	int setNumber(int);
	int setFrom(int);
	int setTo(int);
	string setLabel(string);
	float* setWeight(float* );

	float addWeight(float,int);//w,i (add w as a ith weight)

	//utilities
	void print();

public:
	~Edge(void);
};