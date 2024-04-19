#include "Edge.h"

//constructors
Edge::Edge(void)
{
}

Edge::Edge(int fr, int t)
{
	setFrom(fr);
	setTo(t);
}

Edge::Edge(int fr, int t, string lb)
{
	setFrom(fr);
	setTo(t);
	setLabel(lb);
}

Edge::Edge(int fr, int t, string lb, float* wt)
{
	setFrom(fr);
	setTo(t);
	setLabel(lb);
	setWeight(wt);
}

//getters
int Edge::getNumber()
{
	return number;
}

int Edge::getFrom()
{
	return from;
}

int Edge::getTo()
{
	return to;
}

string Edge::getLabel()
{
	return label;
}

float* Edge::getWeight()
{
	return weight;
}

//setters
int Edge::setNumber(int nb)
{
	number = nb;
	return nb;
}

int Edge::setFrom(int fr)
{
	from = fr;
	return fr;
}

int Edge::setTo(int t)
{
	to = t;
	return t;
}

string Edge::setLabel(string lb)
{
	label = lb;
	return lb;
}

float* Edge::setWeight(float* wt)
{
	for (int i=0; i<MAX_W; i++){
		if(wt[i]==NULL) break;
		weight[i] = wt[i];
	}	
	return wt;
}

float Edge::addWeight(float f, int i)
{
	weight[i] = f;
	return f;
}

//utilities
void Edge::print()
{
	cout << "\n "  << from << ">" << to << "( " << label << " ); \n";
}

Edge::~Edge(void)
{
}