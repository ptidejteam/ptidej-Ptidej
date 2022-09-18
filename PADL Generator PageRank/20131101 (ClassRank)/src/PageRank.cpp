// graphmat.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "Graph.h"
#include <iostream>

using namespace std;

//void generate

void help ()
{

  cerr << endl;
  cerr << "Legal switches:" << endl;
  cerr << "-i input file " << endl;
  cerr << "-o output file (default: 'input_file_parameters')" << endl;
  cerr << "-m input file format (default: 4 for diagram classes)" << endl;
  cerr << "-j jump probability for PageRank (default: standard 0.15)" << endl;
  cerr << "-a weight of 1st label type (default: 1)" << endl;
  cerr << "-b weight of 2nd label type (default: 1)" << endl;
  cerr << "-c weight of 3rd label type (default: 1)" << endl;
  cerr << "-d weight of 4th label type (default: 1)" << endl;
  cerr << "-e weight of 5th label type (default: 1)" << endl;

}

int main(int argc, char* argv[])
{
	argc--;
	argv++;

	bool use_path_list = false;
	if (argc < 2)
	{
		cerr << "\n Input file expected" << endl;
		help();
		exit (1);
	}

	string input_file, output_file;
	int mode = 4;
	double w1 = 1, w2 = 1, w3 = 1, w4 = 1, w5 = 1, jump = 0.15;

	while (argc > 0 && argv[0][0] == '-')
	{
		switch (argv[0][1])
		{
			case 'i':
			  argv++;
			  argc--;
			  input_file = argv[0];
			  break;

			case 'o':
			  argv++;
			  argc--;
			  output_file = argv[0];
			  break;

			case 'm':
			  argv++;
			  argc--;
			  mode = atoi(argv[0]);
			  break;

			case 'j':
			  argv++;
			  argc--;
			  jump = atof(argv[0]);
			  break;

			case 'a':
			  argv++;
			  argc--;
			  w1 = atof(argv[0]);
			  break;

			case 'b':
			  argv++;
			  argc--;
			  w2 = atof(argv[0]);
			  break;

			case 'c':
			  argv++;
			  argc--;
			  w3 = atof(argv[0]);
			  break;

			case 'd':
			  argv++;
			  argc--;
			  w4 = atof(argv[0]);
			  break;

			case 'e':
			  argv++;
			  argc--;
			  w5 = atof(argv[0]);
			  break;

			default:
			  help();
			  exit(1);
		}

		argv++;
		argc--;

	}

	cout << "i:'" << input_file << "' o:'" << output_file <<"' m:'" << mode <<"' j:'" << jump <<"' a:'" <<w1  <<"' b:'"<<w2  <<"' c:'"<<w3 <<"' d:'"<<w4 <<"' e:'"<<w5<<"'\n";

	double* ww = new double[10];
	ww[0] = 0;
	ww[1] = w1;
	ww[2] = w2;
	ww[3] = w3;
	ww[4] = w4;
	ww[5] = w5;

	Graph* graph =  new Graph(mode,jump,input_file,output_file,ww);

	return 0;

}




