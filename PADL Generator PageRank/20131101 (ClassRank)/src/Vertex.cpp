#include "Vertex.h"

//constructors
Vertex::Vertex(void)
{
	sum_out = 0;
}

Vertex::Vertex(int nb)
{
	setNumber(nb);
	sum_out = 0;
}

Vertex::Vertex(int nb, string lb)
{
	setNumber(nb);
	setLabel(lb);
	sum_out = 0;
}

Vertex::Vertex(int nb, string lb, float* wt)
{
	setNumber(nb);
	setLabel(lb);
	setWeight(wt);
	sum_out = 0;
}

//getters
int Vertex::getNumber()
{
	return number;
}

string Vertex::getLabel()
{
	return label;
}

float* Vertex::getWeight()
{
	return weight;
}

vector<Edge*> Vertex::getIn()
{
	return in;
}

vector<Edge*> Vertex::getOut()
{
	return out;
}

vector<Edge*> Vertex::getEdges()
{
	return edges;
}

//setters
int Vertex::setNumber(int nb)
{
	number = nb;
	return nb;
}

string Vertex::setLabel(string lb)
{
	label = lb;
	return lb;
}

float* Vertex::setWeight(float* wt)
{
	for (int i=0; i<MAX_W; i++){
		if(wt[i]==NULL) break;
		weight[i] = wt[i];
	}
	return wt;
}

float Vertex::addWeight(float f, int i)
{
	weight[i] = f;
	return f;
}

void Vertex::print()
{
	//cout << "\n" << number << ", " << label << ", " << weight[0] << ", " << weight[1] << "\n";
	int deg_in = in.size();
	//cout << "\tIN : " << deg_in << "\n\t\t";
	for (int j=0; j<deg_in; j++)
	{
		Edge* e = in[j];
		//cout << j << "/ " << e->getFrom() << "( " << e->getLabel() << " ); ";
	}
	int deg_out = out.size();
	//cout << "\n\tOUT : " << deg_out << "\n\t\t";
	for (int j=0; j<deg_out; j++)
	{
		Edge* e = out[j];
		//cout << j << "/ " << e->getTo() << "( " << e->getLabel() << " ); ";
	}
	int deg_all = edges.size();
	//cout << "\n\tALL : " << deg_all << "\n\t\t";
	for (int j=0; j<deg_all; j++)
	{
		Edge* e = edges[j];
		//cout << j << "/" << e->getFrom() << ">" << e->getTo() << "( " << e->getLabel() << " ); ";
	}
}

Vertex::~Vertex(void)
{
}
