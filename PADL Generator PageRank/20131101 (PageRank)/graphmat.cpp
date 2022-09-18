// graphmat.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "Graph.h"
#include <iostream>

using namespace std;

//void generate

void usage ()
{
  cerr << endl;
  cerr << "Legal switches:" << endl;
  cerr << "-g path of graph 1 " << endl;
}

int main(int argc, char* argv[])
{
	cout << "START \n";  
	
	Graph* G1;
	string graph1; 

	argc--;
	argv++;

	bool use_path_list = false;
	if (argc < 2)
	{
	  cerr << "Parameters e expected" << endl;
	  exit (1);
	}

	while (argc > 0 && argv[0][0] == '-')
	{
		switch (argv[0][1])
		{
			case 'g':
			  argv++;
			  argc--;
			  graph1 = argv[0];
			  break;

			default:
			  usage ();
			  break;
		}
		argv++;
		argc--;
	}

	Graph* g1 = new Graph(6,graph1);
	g1->printU();

}

