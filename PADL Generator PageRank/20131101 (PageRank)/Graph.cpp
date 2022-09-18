#include "Graph.h"

Graph::Graph(void)
{
}

/* WARNING: for simplicity this code does not check for End-Of-File! */
unsigned int read_word(ifstream* in)
{
	unsigned char b1, b2;
    
    (*in).read(reinterpret_cast<char*>(&b1),sizeof(char)); /* Least-significant Byte */
    (*in).read(reinterpret_cast<char*>(&b2),sizeof(char)); /* Most-significant Byte */

	//return 20;
     return b1 | (b2 << 8);
}

void Graph::initMode0(ifstream* file)
{
	string line;
	int num_line = 0, num_edge = 0;
	
	while ( getline(*file, line) )
	{            
		istringstream issline( line );
		string word;
		int num_word;
		//first line : number of vertices, number of edges
		if (num_line==0)
 		{
			num_word = 0;
			while ( std::getline(issline, word, ',') )
			{
				num_word++;
				if (num_word==1) from_string(word, vertices);
				else if (num_word==2) from_string(word, edges);
			}
			G = new Edge** [vertices];
			for(int i=0; i<vertices; i++)
			{
				*(G+i) = new Edge* [vertices];
				for(int j=0; j<vertices; j++)
				{
					G[i][j] = NULL;
				}
			}
		}
		//lines 1-vertices : node number, node label, node weight 1, node weight 2 ...
		else if (num_line<=vertices)
		{
			Vertex* v = new Vertex();
			num_word = 0;
			while ( std::getline(issline, word, ',') )
			{
				num_word++;					
				if (num_word==1) { int in; from_string(word, in); (*v).setNumber(in); }
				else if (num_word==2) (*v).setLabel(word);
				else {float fl; from_string(word, fl); (*v).addWeight( fl, num_word-3 ); }
			}
			V.push_back(v);					
		}
		//lines vertices+1,vertices+edges : edge from, edge to, edge label, edge weight 1, edge weight 2 ...
		else if (num_line>vertices)
		{
			Edge* e = new Edge();
			(*e).setNumber(num_edge++);
			num_word = 0;
			while ( std::getline(issline, word, ',') )
			{
				num_word++;
				if (num_word==1) { int in; from_string(word, in); (*e).setFrom(in); }
				else if (num_word==2) { int in; from_string(word, in); (*e).setTo(in); }
				else if (num_word==3) 
				{
					(*e).setLabel(word);
				}
				else {float fl; from_string(word, fl); (*e).addWeight( fl, num_word-4 ); }
			}
			/*
			if (e->label=="3")
			{
				int permut = e->from;
				e->from = e->to;
				e->to = permut;
			}
*/
			
			insertEdge(e);		
								
		}			
		num_line++;
	}
}

void Graph::initMode4(ifstream* file)
{
	string line;
	int num_line = 0, num_edge = 0;
	
	while ( getline(*file, line) )
	{            
		istringstream issline( line );
		string word;
		int num_word;
		//first line : number of vertices, number of edges
		if (num_line==0)
 		{
			num_word = 0;
			while ( std::getline(issline, word, ',') )
			{
				num_word++;
				if (num_word==1) from_string(word, vertices);
				else if (num_word==2) from_string(word, edges);
			}
			G = new Edge** [vertices];
			for(int i=0; i<vertices; i++)
			{
				*(G+i) = new Edge* [vertices];
				for(int j=0; j<vertices; j++)
				{
					G[i][j] = NULL;
				}
			}
		}
		else if (line.substr(0,2)=="c,")
		{
			Vertex* v = new Vertex();
			num_word = 0;
			while ( std::getline(issline, word, ',') )
			{
				num_word++;					
				if (num_word==2) { int in; from_string(word, in); (*v).setNumber(in); }
				else if (num_word==3) (*v).setLabel(word);
				else if (num_word>3) {float fl; from_string(word, fl); (*v).addWeight( fl, num_word-3 ); }
			}
			V.push_back(v);					
		}
		else if (line.substr(0,2)=="a,")
		{
			Vertex* v;
			string key, val;
			num_word = 0;
			while ( std::getline(issline, word, ',') )
			{
				num_word++;					
				if (num_word==2) { int in; from_string(word, in); v=V[in]; }
				else if (num_word==3) 
				{
					key = word;
					int pos = word.rfind("@");
					val = word.substr(pos+1);
					v->attributes[key] = val;				
				}
			}
			//cout << "\n*" <<line << "*\t*" << key << "*\t*" << val << "*" ;
			//system("pause");				
		}
		else if (line.substr(0,2)=="m,")
		{
			Vertex* v;
			string key, val;
			num_word = 0;
			while ( std::getline(issline, word, ',') )
			{
				num_word++;					
				if (num_word==2) { int in; from_string(word, in); v=V[in]; }
				else if (num_word==3) 
				{
					int pos0 = line.find(",",3);
					key = line.substr(pos0+1);
					int pos1 = key.rfind("@");
					int pos2 = key.rfind("@",pos1-1);
					val = key.substr(pos2+1,pos1-pos2-1);
					v->methods[key] = val;				
				}
			}
			//cout << "\n*" <<line << "*\t*" << key << "*\t*" << val << "*" ;
			//system("pause");			
		}
		else if (line.substr(0,2)=="r,")
		{
			Edge* e = new Edge();
			(*e).setNumber(num_edge++);
			num_word = 0;
			while ( std::getline(issline, word, ',') )
			{
				num_word++;
				if (num_word==2) { int in; from_string(word, in); (*e).setFrom(in); }
				else if (num_word==3) { int in; from_string(word, in); (*e).setTo(in); }
				else if (num_word==4) 
				{
					(*e).setLabel(word);
				}
				else if (num_word>4) {float fl; from_string(word, fl); (*e).addWeight( fl, num_word-4 ); }
			}
			/*
			if (e->label=="3")
			{
				int permut = e->from;
				e->from = e->to;
				e->to = permut;
			}
			*/
			insertEdge(e);									
		}			
		num_line++;
	}
}

void Graph::initMode5(ifstream* file)
{
	string line;
	int num_line = 0, num_edge = 0;
	
	while ( getline(*file, line) )
	{            
		istringstream issline( line );
		string word;
		int num_word;
		//first line : number of vertices, number of edges
		if (num_line==0)
 		{
			num_word = 0;
			while ( std::getline(issline, word, ',') )
			{
				num_word++;
				if (num_word==1) from_string(word, vertices);
				else if (num_word==2) from_string(word, edges);
			}
			G = new Edge** [vertices];
			for(int i=0; i<vertices; i++)
			{
				*(G+i) = new Edge* [vertices];
				for(int j=0; j<vertices; j++)
				{
					G[i][j] = NULL;
				}
			}
		}
		else if (line.substr(0,2)=="c,")
		{
			Vertex* v = new Vertex();
			num_word = 0;
			while ( std::getline(issline, word, ',') )
			{
				num_word++;					
				if (num_word==2) { int in; from_string(word, in); (*v).setNumber(in); }
				else if (num_word==3) (*v).setLabel(word);
				else if (num_word>3) {float fl; from_string(word, fl); (*v).addWeight( fl, num_word-3 ); }
			}
			V.push_back(v);					
		}
		else if (line.substr(0,2)=="r,")
		{
			Edge* e = new Edge();
			(*e).setNumber(num_edge++);
			num_word = 0;
			while ( std::getline(issline, word, ',') )
			{
				num_word++;
				if (num_word==2) { int in; from_string(word, in); (*e).setFrom(in); }
				else if (num_word==3) { int in; from_string(word, in); (*e).setTo(in); }
				else if (num_word==4) 
				{
					(*e).setLabel(word);
				}
				else if (num_word>4) {float fl; from_string(word, fl); (*e).addWeight( fl, num_word-4 ); }
			}
			insertEdge(e);									
		}			
		num_line++;
	}
}



void Graph::initMode1(ifstream* file)
{
	edges=0;
	int deg, target, num_edge = 0;

	// Read the number of nodes
	vertices = read_word(file);

	// For each node i ... 
	for(int i=0; i<vertices; i++)
	{ 
		for(int j=0; j<vertices; j++)
		{
			G[i][j] = NULL;
		}
		Vertex* v = new Vertex();
		(*v).setNumber(i);
		(*v).setLabel(to_string(i));
		(*v).addWeight(0, 0);
		(*v).addWeight(0, 1);
		V.push_back(v);
	}

	// For each node i ... 
	for(int i=0; i<vertices; i++)
	{ 
		// Read the number of edges coming out of node i 
		deg=read_word(file);
		edges +=deg;

		// For each edge out of node i... 
		for(int j=0; j<deg; j++)
		{ 
			Edge* e = new Edge();
			(*e).setNumber(num_edge++);
			// Read the destination node of the edge 
			target = read_word(file);
			(*e).setFrom(i);
			(*e).setTo(target);
			(*e).setLabel("1");		
			insertEdge(e);					
		}
	}
}

void Graph::initMode2(ifstream* file, int labels)
{
	edges=0;
	int deg, target, num_edge = 0;

	// Read the number of nodes
	vertices = read_word(file);			

	// For each node i ... 
	for(int i=0; i<vertices; i++)
	{ 
		for(int j=0; j<vertices; j++)
		{
			G[i][j] = NULL;
		}
		Vertex* v = new Vertex();
		(*v).setNumber(i);
		(*v).setLabel(to_string(i)); read_word(file);
		(*v).addWeight(0, 0);
		(*v).addWeight(0, 1);
		V.push_back(v);
	}
	// For each node i ... 
	for(int i=0; i<vertices; i++)
	{ 
		// Read the number of edges coming out of node i 
		deg=read_word(file);
		edges +=deg;

		// For each edge out of node i... 
		for(int j=0; j<deg; j++)
		{ 
			Edge* e = new Edge();
			(*e).setNumber(num_edge++);
			// Read the destination node of the edge 
			target = read_word(file);

			(*e).setFrom(i);
			(*e).setTo(target);
			int in = 1 + read_word(file)%labels;
			(*e).setLabel(to_string(in));					
			
			insertEdge(e);										
		}
	}
}


void Graph::initMode3(ifstream* file)
{
	string line;
	int num_line = 0, num_edge = 0;
/*	
	string person, person1, person2;
	num_person = 0;
	istringstream issperson( substr(name,0,name.size()-4) );

	//get Persons
	while ( std::getline(issperson, person, ',') )
	{
		num_person++;
		int i = 0;
		while (i<MAX_PERSONS)
		{
			if (persons[i]==NULL)
			{
				persons[i] = person;
				break;
			}
		}
		if (num_person==1) 
		{ 
			person1 = person;
		}
		else if (num_person==2) 
		{ 
			person2 = person;
		}
	}
*/	

	for(int i=0; i<500; i++)
	{ 
		for(int j=0; j<500; j++)
		{
			G[i][j] = NULL;
		}
	}

	//treatLines
	vertices = 0;
	while ( getline(*file, line) )
	{            
		//cout << line << "\n";
		if (line.find(",,",0)!=string::npos || line.find(", ,",0)!=string::npos) 
		{
			//cout << line << "\n";
			//num_line++;
			//cout << ",,\n";
			continue;
		}
		
		istringstream issline( line );
		string word;
		int num_word;
		
		
		
		Edge* e = new Edge();
		(*e).setNumber(num_edge++);
		num_word = 0;
		while ( std::getline(issline, word, ',') )
		{
			num_word++;	
			if (num_word<=2) 
			{
				if (nodeNumbers.find(word)==nodeNumbers.end()) //nodeNumbers[word]<0 || nodeNumbers[word]>100000)
				{
					nodeNumbers[word] = vertices;
					Vertex* v = new Vertex();
					(*v).setNumber(vertices);
					(*v).setLabel(word);
					(*v).addWeight(0, 0);
					(*v).addWeight(0, 1);
					V.push_back(v);
					//v->print();
					vertices++;
				}
			}
			if (num_word==1) 
			{ 				
				int in = nodeNumbers[word];		
				(*e).setFrom(in); 
			}
			else if (num_word==2) 
			{ 				
				int in = nodeNumbers[word]; 
				(*e).setTo(in); 
			}
			else if (num_word==3) 
			{
				//float fl; from_string(word, fl);
				(*e).setLabel(word);
			}
			//else {float fl; from_string(word, fl); (*e).addWeight( fl, num_word-4 ); }
		}

		
		insertEdge(e);
		Edge* e2 = new Edge();
		(*e2).setNumber(num_edge++);
		(*e2).setFrom(e->to); 
		(*e2).setTo(e->from); 
		(*e2).setLabel(e->label); 
		insertEdge(e2);

		//e->print();
		//e2->print();

		num_line++;
	}
}


void Graph::insertEdge(Edge* e)
{
	float in;
	from_string(e->label, in);
	//cout << e->from << ">" << e->to << "\t";
 	V[e->from]->sum_out += 1; //in;

	if (in > nb_labels) nb_labels = (int)in ;	
	
	Edge* old_e = G[(*e).getFrom()][(*e).getTo()];
					
	if (old_e!=NULL)
	{
		(*old_e).label.append( (*e).getLabel() );
		//cout << "\n" << (*e).getLabel() << "\t" << (*old_e).getLabel() << "\n";
		e = old_e;
	}				
	else
	{
		G[(*e).getFrom()][(*e).getTo()] = e;
		E.push_back(e);
		V[(*e).getFrom()]->out.push_back(e);
		V[(*e).getTo()]->in.push_back(e);

	
		V[(*e).getTo()]->edges.push_back(e);
		if ( (*e).getTo() != (*e).getFrom() )
			V[(*e).getFrom()]->edges.push_back(e);
	}

	//stats
	int _in1, _in2, _out1, _out2, _all1, _all2, _multi;
	_in1 = V[e->from]->in.size(); _in2 = V[e->to]->in.size();
	_out1 = V[e->from]->out.size(); _out2 = V[e->to]->out.size();
	_all1 = V[e->from]->edges.size(); _all2 = V[e->to]->edges.size();
	_multi = e->label.size();	//May change if labels are no longer integers

	if (max_in_distrib < _in1) max_in_distrib = _in1;
	if (max_in_distrib < _in2) max_in_distrib = _in2;
	if (min_in_distrib > _in1) min_in_distrib = _in1;
	if (min_in_distrib > _in2) min_in_distrib = _in2;

	if (max_out_distrib < _out1) max_out_distrib = _out1;
	if (max_out_distrib < _out2) max_out_distrib = _out2;
	if (min_out_distrib > _out1) min_out_distrib = _out1;
	if (min_out_distrib > _out2) min_out_distrib = _out2;

	if (max_all_distrib < _all1) max_all_distrib = _all1;
	if (max_all_distrib < _all2) max_all_distrib = _all2;
	if (min_all_distrib > _all1) min_all_distrib = _all1;
	if (min_all_distrib > _all2) min_all_distrib = _all2;

	if (max_multi < _multi) max_multi = _multi;
	if (min_multi > _multi) min_multi = _multi;

	//cout << "(" << _in1 << "," << _in2 << ") (" << _out1 << "," << _out2 << ") (" << _all1 << "," << _all2 << ") (" <<  _multi <<  ")\n"; 
	//cout << "\t[" << max_in_distrib << "/" << max_out_distrib << "/" << max_all_distrib << "/" << max_multi << "]\n"; 
}
/*
void Graph::deleteEdge(Edge* e)
{
	Edge* old_e = G[(*e).getFrom()][(*e).getTo()];
	G[(*e).getFrom()][(*e).getTo()] = NULL;
	E.e


		G[(*e).getFrom()][(*e).getTo()] = e;
		E.push_back(e);
		V[(*e).getFrom()]->out.push_back(e);
		V[(*e).getTo()]->in.push_back(e);
	
		V[(*e).getTo()]->edges.push_back(e);
		if ( (*e).getTo() != (*e).getFrom() )
			V[(*e).getFrom()]->edges.push_back(e);
	}

}
*/

void Graph::initMode6(ifstream* file)
{
	string line;
	int num_line = 0, num_edge = 0;
	
	while ( getline(*file, line) )
	{            
		istringstream issline( line );
		string word;
		int num_word;
		//first line : number of vertices, number of edges
		if (num_line==0)
 		{
			num_word = 0;
			while ( std::getline(issline, word, ',') )
			{
				num_word++;
				if (num_word==1) from_string(word, vertices);
				else if (num_word==2) from_string(word, edges);
			}
			G = new Edge** [vertices];
			for(int i=0; i<vertices; i++)
			{
				*(G+i) = new Edge* [vertices];
				for(int j=0; j<vertices; j++)
				{
					G[i][j] = NULL;
				}
			}
		}
		else if (line.substr(0,2)=="s,")
		{
			Vertex* v = new Vertex();
			num_word = 0;
			while ( std::getline(issline, word, ',') )
			{
				num_word++;					
				if (num_word==2) { int in; from_string(word, in); (*v).setNumber(in); }
				else if (num_word==3) 
				{
					int lsize = word.size();
					
					//cout << "*" << word.at(

					if (word.substr(lsize-1)=="\r")
						word = word.substr(0,lsize-1);
					(*v).setLabel(word);
				}
				else if (num_word>3) {float fl; from_string(word, fl); (*v).addWeight( fl, num_word-3 ); }
			}
			V.push_back(v);					
		}
		else if (line.substr(0,2)=="r,")
		{
			Edge* e = new Edge();
			(*e).setNumber(num_edge++);
			num_word = 0;
			while ( std::getline(issline, word, ',') )
			{
				num_word++;
				if (num_word==2) { int in; from_string(word, in); (*e).setFrom(in); }
				else if (num_word==3) { int in; from_string(word, in); (*e).setTo(in); }
			}
			e->label = "1";
			insertEdge(e);									
		}			
		num_line++;
	}
}



void Graph::nodeRank(float _c, float epsilon)
{
	c = _c;		
	u = computeU(epsilon,vertices);
	min_u = 0; min_norm = 0;
	max_u = 0; max_norm = 0;
	mean_u = 0; mean_norm = 0;
	std_u = 0; std_norm = 0;

	for (int i=0; i<vertices; i++)
	{
		//u[i] = vertices*vertices*u[i];
		norm[i] = sqrt( pow((double)V[i]->in.size(),2) + pow((double)V[i]->out.size(),2) );

		if (i==0 || u[i]<min_u)
			min_u = u[i];
		if (i==0 || u[i]>max_u)
			max_u = u[i];
		mean_u += u[i];
		std_u += (u[i]*u[i]);

		if (i==0 || norm[i]<min_norm)
			min_norm = norm[i];
		if (i==0 || norm[i]>max_norm)
			max_norm = norm[i];
		mean_norm += norm[i];
		std_norm += (norm[i]*norm[i]);
	}

	mean_u /= vertices;
	std_u /= vertices;
	std_u -= (mean_u*mean_u);
	std_u = sqrt(std_u);

	mean_norm /= vertices;
	std_norm /= vertices;
	std_norm -= (mean_norm*mean_norm);
	std_norm = sqrt(std_norm);

	maxi_u = mean_u + 2*std_u;
	if (maxi_u>max_u) maxi_u = max_u;

	maxi_norm = mean_norm + 2*std_norm;
	if (maxi_norm>max_norm) maxi_norm = max_norm;

	sortNodes();
	printU();
}

void Graph::sortNodes()
{
	rank = new int[vertices];
	_rank = new int[vertices];

	level = new int[vertices];
	level_count = new int[100];
	for (int i=0; i<vertices; i++)
	{
		rank[i] = 0;
		_rank[i] = 0;
		for (int j=0; j<vertices; j++)
		{
			if (i!=j && u[i]<u[j])
				rank[i]++;
			if (i!=j && norm[i]<norm[j])
				_rank[i]++;
		}
		if (rank[i] == 0)
			max_u = u[i];
		if (_rank[i] == 0)
			max_norm = norm[i];

		int perc = 100*rank[i]/vertices;
		level[i] = perc;

		if (level_count[perc]<0) level_count[perc] = 1;
		else level_count[perc] += 1;
	}

	for (int i=0; i<100; i++)
	{
		if (level_count[i]<0) level_count[i] = 0;
		//cout << i << "\t" << level_count[i] << "\n";
	}
}

Graph::Graph(int mode, string path)
{
	ifstream file;
	
	max_in_distrib = 0; max_out_distrib = 0; max_all_distrib =0; max_multi = 0;
	min_in_distrib = MAX_DEGREE; min_out_distrib = MAX_DEGREE; min_all_distrib = MAX_DEGREE; min_multi = MAX_MULTI;

	mean_in_distrib = 0; mean_out_distrib = 0; mean_all_distrib =0; mean_multi_distrib = 0; mean_label_distrib = 0;
	std_in_distrib = 0; std_out_distrib = 0; std_all_distrib = 0; std_multi_distrib = 0; std_label_distrib = 0;

	nb_labels = 1; loops = 0; multi_arcs = 0;

	if (mode==0) file.open( path.c_str() );
	else file.open(path.c_str(),ios::binary);

	cout << "File " << path << " ...";
    if (file) 
    {
        cout << "Found \n";		
		
		int path_length = path.length();
		int pos = path.rfind("/");
		if (pos<0) pos=-1;

		name = path.substr(++pos);

		//MOZILLA FILES
		if (mode==0)
		{
			initMode0(&file);			
		}
		else if (mode==6)
		{
			initMode6(&file);
		}
		else if (mode==4)
		{
			initMode4(&file);
		}
		else if (mode==5)
		{
			initMode5(&file);
		}
		//GRAPH DATABASE : UNLABELED
		else if (mode==1)
		{
			initMode1(&file);
		}
		
		//GRAPH DATABASE : LABELED
		else if (mode==2)
		{
			initMode2(&file,4);
		}

		else if (mode==3)
		{
			initMode3(&file);
		}
		
		
		edges = E.size();
		norm = new float[vertices];
		nodeRank(0.1, 0.00000001);	


		file.close();	
    }	
	else cout << "Not Found \n";

}

Edge* Graph::getEdge(int from, int to)
{
	vector<Edge*> v = V[from]->out;
	Edge* e = NULL;
	for (int i=0; i<v.size(); i++)
	{
		if (v[i]->to==to)
			return v[i];		
	}

	return e;

}

void Graph::printU()
{
	ofstream file;
	string metra = "./"+name+"_metrics.csv";
	file.open( metra.c_str());
	file << "#statement" << ";" << "statement" << ";" << "PageRank value" << ";" << "Rank"  << ";" << "All Edges"  << ";" << "Incoming Edges"  << ";" << "Outgoing Edges"  << "\n";

	for (int i=0; i<vertices; i++)
	{

		file << V[i]->getNumber() << ";" << V[i]->getLabel() << ";" << u[i] << ";" << rank[i]  << ";" << V[i]->edges.size()  << ";" << V[i]->in.size()  << ";" << V[i]->out.size()  << "\n";
		/*
		for (int j=0; j<V[i]->in.size(); j++)
		{
			Edge* e = V[i]->in[j];

			double dbl;
			from_string(e->label,dbl);
			if (dbl>0.1)
				file << V[e->from]->label << " : " << e->label << ";";
		}
		*/
		//file << "\n";
	}
	file.close();
	//system("pause");
}

float* Graph::T(float* x, int n)
{
	float* y = new float[n];
	int lj;
	for (int i=0; i<n; i++)
	{
		y[i] = c/n;
	}

	for (int j=0; j<n; j++)
	{
		lj = V[j]->getOut().size();
		//if (lj<1) lj = 1;
		for (int k=0; k<lj; k++)
		{	
			int i = V[j]->getOut()[k]->getTo();
			float weight = V[j]->out[k]->label.size();
			//from_string(V[j]->out[k]->label, weight);
			//y[i] += ((1-c)/lj)*x[j];
			y[i] += ((1-c)*weight/V[j]->sum_out)*x[j];
		}
	}
	return y;
}

float* Graph::computeU(float delta, int n)
{
	float* x = new float[n];
	float* y = new float[n];

	//cout << "\nb loop \n";
	for (int i=0; i<n; i++)
	{
		//cout << "\nok\n";
		x[i] = 1/(float)n;
	}

	do
	{
		for (int i=0; i<n; i++)
		{
			y[i] = x[i];
		}
		
		x = T(x,n);
	}
	while(specialNorm(x,y,n)<c*delta/(1-c));

	double snorm = 0;
	for (int i=0; i<n; i++)
	{
		snorm += x[i];
	}
	for (int i=0; i<n; i++)
	{
		x[i] /= snorm;
	}

	return x;
}

void Graph::collectStats()
{
	
	//Initialization
	in_distrib = new int[max_in_distrib+1];
	out_distrib = new int[max_out_distrib+1];
	all_distrib = new int[max_all_distrib+1];
	label_distrib = new int[nb_labels+1];
	multi_distrib = new int[max_multi+1];

	cout << "\n" << max_in_distrib << "/" << max_out_distrib << "/" << max_all_distrib << "/" << nb_labels << "/" << max_multi << "\n"; 
	
	for (int i=0; i<=max_in_distrib; i++)
	{
		in_distrib[i] = 0;
	}
	for (int i=0; i<=max_out_distrib; i++)
	{
		out_distrib[i] = 0;
	}
	for (int i=0; i<=max_all_distrib; i++)
	{
		all_distrib[i] = 0;
	}
	for (int i=0; i<=nb_labels; i++)
	{
		label_distrib[i] = 0;
	}
	for (int i=0; i<=max_multi; i++)
	{
		multi_distrib[i] = 0;
	}
	
	//Fill
	nbNodeLabels = 0;
	ofstream file_isol;
	string file_isol_name = "isolated_"  + name + ".csv";
	file_isol.open (file_isol_name.c_str());
	for (int i=0; i<V.size(); i++)
	{
		Vertex* v = V[i];

		//cout << v->number << ":" << v->label << "\n";
		
		int _in = (int)(*v).in.size();
		int _out = (int)(*v).out.size();
		int _all = (int)(*v).edges.size();
		
		in_distrib[_in] = in_distrib[_in] + 1;
		out_distrib[_out] = out_distrib[_out] + 1;


		//if (_all==0)
		//	file_isol << v->number << "," << v->label << "\n";
		all_distrib[_all] = all_distrib[_all] + 1 ;	

		nbNodeLabels++;
		for (int j=i-1; j>=0; j--)
		{
			Vertex* vv = V[j];
			if (vv->label==v->label)
			{
				nbNodeLabels--;			
				break;			
			}
		}


	}

	file_isol.close();
	
	string all_labels = "";
	for (int i= 0; i<E.size(); i++)
	{
		Edge* e = E.at(i);

		if (e->from == e->to)
			loops++;
		
		//if (e->label == "") cout << e->from << "->" << e->to  << "\n"; //;<< "[" << e->label ;
		
		int _multi = e->label.size();
		if (_multi>1) multi_arcs++;

		string test_lbl = ";" + e->label + ";";

		if (all_labels.find(test_lbl)==string::npos)
			all_labels += test_lbl;
		
		for (int j=0; j< _multi; j++)
		{
			int lb;
			string lbl = e->label.substr(j,1);
			
			
			//cout << "/" << lbl;
			from_string(lbl,lb);
			label_distrib[lb]++;
		}
		multi_distrib[_multi]++;	
		//cout << "]\n";		
	}
	
	cout << "\n [" << all_labels<< "]\n";

	int num_in=0, num_out=0, num_all=0, num_lab=0, num_multi=0;
	for (int i=0; i<=max_in_distrib; i++)
	{
		num_in += in_distrib[i];
		mean_in_distrib += i*in_distrib[i];
		std_in_distrib += i*i*in_distrib[i];
	}
	for (int i=0; i<=max_out_distrib; i++)
	{
		num_out += out_distrib[i];
		mean_out_distrib += i*out_distrib[i];
		std_out_distrib += i*i*out_distrib[i];
	}
	for (int i=0; i<=max_all_distrib; i++)
	{
		num_all += all_distrib[i];
		mean_all_distrib += i*all_distrib[i];
		std_all_distrib += i*i*all_distrib[i];
	}
	for (int i=0; i<=nb_labels; i++)
	{
		num_lab += label_distrib[i];
		mean_label_distrib += i*label_distrib[i];
		std_label_distrib += i*i*label_distrib[i];
	}
	for (int i=0; i<=max_multi; i++)
	{
		num_multi += multi_distrib[i];
		mean_multi_distrib += i*multi_distrib[i];
		std_multi_distrib += i*i*multi_distrib[i];
	}
	if (in_distrib[0]>0) min_in_distrib = 0;
	if (out_distrib[0]>0) min_out_distrib = 0;
	if (all_distrib[0]>0) min_all_distrib = 0;

	
	mean_in_distrib = mean_in_distrib/num_in;
	mean_out_distrib = mean_out_distrib/num_out;
	mean_all_distrib = mean_all_distrib/num_all;
	mean_label_distrib = mean_label_distrib/num_lab;
	mean_multi_distrib = mean_multi_distrib/num_multi;

	std_in_distrib = sqrt(std_in_distrib/num_in - mean_in_distrib*mean_in_distrib);
	std_out_distrib = sqrt(std_out_distrib/num_out - mean_out_distrib*mean_out_distrib);
	std_all_distrib = sqrt(std_all_distrib/num_all - mean_all_distrib*mean_all_distrib);
	std_label_distrib = sqrt(std_label_distrib/num_lab - mean_label_distrib*mean_label_distrib);
	std_multi_distrib = sqrt(std_multi_distrib/num_multi - mean_multi_distrib*mean_multi_distrib);
	
	label_distrib[0] = vertices*vertices - E.size();
}

void Graph::mozillaDetails()
{
	string all_graphs = "./graphs/all_graphs.csv";
	string graph_detail = "./graphs/" + name + ".csv";

	collectStats();

	ofstream file1;
	file1.open(all_graphs.c_str(), ios::app);
	ofstream file2;
	file2.open(graph_detail.c_str());

	string details;

//  #;Name;|V|;|E|;|LV|;|LE|;Loops;Multi-arcs;Isolated Nodes;Min_in_degree;Mean_in_degree;Std_in_degree;Max_in_degree;Min_out_degree;Mean_out_degree;Std_out_degree;Max_out_degree;Min_degree;Mean_degree;Std_degree;Max_degree;Mean_label;Std_label;Mean_multi;Std_multi;Max_multi;#labels1;#labels2;#labels3

	details = ";" + name + ";" + to_string(vertices) + ";" + to_string(edges) + ";" + to_string(nbNodeLabels) + ";" + to_string(nb_labels) + ";" + to_string(loops) + ";" + to_string(multi_arcs) + ";";
	details = details + to_string(all_distrib[0]) + ";" + to_string(min_in_distrib) + ";" + to_string(mean_in_distrib) + ";" + to_string(std_in_distrib) + ";" + to_string(max_in_distrib) + ";" + to_string(min_out_distrib) + ";" + to_string(mean_out_distrib) + ";" + to_string(std_out_distrib) + ";" + to_string(max_out_distrib) + ";";
	details = details + to_string(min_all_distrib) + ";" + to_string(mean_all_distrib) + ";" + to_string(std_all_distrib) + ";" + to_string(max_all_distrib) + ";" + to_string(mean_label_distrib) + ";" + to_string(std_label_distrib) + ";" + to_string(mean_multi_distrib) + ";" + to_string(std_multi_distrib) + ";" + to_string(max_multi) +";";
	details = details + to_string(label_distrib[1]) + ";" + to_string(label_distrib[2]) + ";" + to_string(label_distrib[3]) + ";\n";

	file1 << details;
	file1.close();
	
	details = "Name;|V|;|E|;|LV|;|LE|;Loops;Multi-arcs;Isolated Nodes;Min_in_degree;Mean_in_degree;Std_in_degree;Max_in_degree;Min_out_degree;Mean_out_degree;Std_out_degree;Max_out_degree;Min_degree;Mean_degree;Std_degree;Max_degree;Mean_label;Std_label;Mean_multi;Std_multi;Max_multi;\n"+details;
	
	details = details + "\nIn Distribution\n";
	for (int i=0; i<=max_in_distrib; i++)
	{
		//if (in_distrib[i]>0)
			details  = details + to_string(i) + " <> " + to_string(in_distrib[i]) +";";
	}
	
	details = details + "\nOut Distribution\n";	
	for (int i=0; i<=max_out_distrib; i++)
	{
		if (out_distrib[i]>0)
			details  = details + to_string(i) + " <> " + to_string(out_distrib[i]) +";";
	}
	details = details + "\nEdge Distribution\n";	
	for (int i=0; i<=max_all_distrib; i++)
	{
		if (all_distrib[i]>0)
			details  = details + to_string(i) + " <> " + to_string(all_distrib[i]) +";";
	}
	if (nb_labels>1)
	{
		details = details + "\nLabel Distribution\n";
		for (int i=0; i<=nb_labels; i++)
		{
			if (label_distrib[i]>0)
				details  = details + to_string(i) + " <> " + to_string(label_distrib[i]) +";";
		}
	}
	if (multi_arcs>0)
	{
		details = details + "\nMulti-Arc Distribution\n";
		for (int i=0; i<=max_multi; i++)
		{
			if (multi_distrib[i]>0)	
				details  = details + to_string(i) + " <> " + to_string(multi_distrib[i]) +";";
		}
	}

	file2 << details;

	file2.close();

	//cout << "\n" << filename ;

}

void Graph::graphDetails()
{
	string all_graphs = "./graphs/all_graphs.csv";
	string graph_detail = "./graphs/" + name + ".csv";

	collectStats();

	ofstream file1;
	file1.open(all_graphs.c_str(), ios::app);
	ofstream file2;
	file2.open(graph_detail.c_str());

	string details;

	details = name + ";" + to_string(vertices) + ";" + to_string(edges) + ";" + to_string(vertices) + ";" + to_string(nb_labels) + ";" + to_string(loops) + ";" + to_string(multi_arcs) + ";";
	details = details + to_string(all_distrib[0]) + ";" + to_string(min_in_distrib) + ";" + to_string(mean_in_distrib) + ";" + to_string(std_in_distrib) + ";" + to_string(max_in_distrib) + ";" + to_string(min_out_distrib) + ";" + to_string(mean_out_distrib) + ";" + to_string(std_out_distrib) + ";" + to_string(max_out_distrib) + ";";
	details = details + to_string(min_all_distrib) + ";" + to_string(mean_all_distrib) + ";" + to_string(std_all_distrib) + ";" + to_string(max_all_distrib) + ";" + to_string(mean_label_distrib) + ";" + to_string(std_label_distrib) + ";" + to_string(mean_multi_distrib) + ";" + to_string(std_multi_distrib) + ";" + to_string(max_multi) + ";\n";

	file1 << details;
	file1.close();
	
	details = "Name;|V|;|E|;|LV|;|LE|;Loops;Multi-arcs;Isolated Nodes;Min_in_degree;Mean_in_degree;Std_in_degree;Max_in_degree;Min_out_degree;Mean_out_degree;Std_out_degree;Max_out_degree;Min_degree;Mean_degree;Std_degree;Max_degree;Mean_label;Std_label;Mean_multi;Std_multi;Max_multi;\n"+details;
	
	details = details + "\nIn Distribution\n";
	for (int i=0; i<=max_in_distrib; i++)
	{
		//if (in_distrib[i]>0)
			details  = details + to_string(i) + " <> " + to_string(in_distrib[i]) +";";
	}
	
	details = details + "\nOut Distribution\n";	
	for (int i=0; i<=max_out_distrib; i++)
	{
		if (out_distrib[i]>0)
			details  = details + to_string(i) + " <> " + to_string(out_distrib[i]) +";";
	}
	details = details + "\nEdge Distribution\n";	
	for (int i=0; i<=max_all_distrib; i++)
	{
		if (all_distrib[i]>0)
			details  = details + to_string(i) + " <> " + to_string(all_distrib[i]) +";";
	}
	if (nb_labels>1)
	{
		details = details + "\nLabel Distribution\n";
		for (int i=0; i<=nb_labels; i++)
		{
			if (label_distrib[i]>0)
				details  = details + to_string(i) + " <> " + to_string(label_distrib[i]) +";";
		}
	}
	if (multi_arcs>0)
	{
		details = details + "\nMulti-Arc Distribution\n";
		for (int i=0; i<=max_multi; i++)
		{
			if (multi_distrib[i]>0)	
				details  = details + to_string(i) + " <> " + to_string(multi_distrib[i]) +";";
		}
	}

	file2 << details;

	file2.close();

	//cout << "\n" << filename ;

}


float Graph::specialNorm(float* x, float* y, int n)
{
	float norm = 0;

	for (int i=0; i<n; i++)
	{
		norm += fabs(x[i]-y[i]);
	}
	return norm;
}

void Graph::write(int mode, string name)
{
	if (mode==0)
	{
		ofstream file;
		file.open(name.c_str());

		file << V.size() << "," << E.size() << "\n";
		
		for (int i= 0; i<V.size(); i++)
		{
			Vertex* v = V[i];
			file << v->number << "," << v->label;

			int wgt = 0;
			while (v->weight[wgt]>=0)
			{
				file << "," << v->weight[wgt];
				wgt++;
			}
			file << "\n";
		}

		for (int i= 0; i<E.size(); i++)
		{
			Edge* e = E[i];
			if (e->label == "")
				continue;
			//for (int j=0; j<e->label.size(); j++)
			//{
				file << e->from << "," << e->to << "," << e->label;//.at(j);
				int wgt = 0;
				while (e->weight[wgt]>=0)
				{
					file << "," << e->weight[wgt];
					wgt++;
				}
				file << "\n";
			//}
		}
		file.close();
	}

}

void Graph::print()
{
	cout << "|V|=" << V.size() << ", |E|=" << E.size() << "\n";
	//DEBUG_GRAPH
	
	//system( "pause" );
	
	cout << "*************************************** VERTICES ************************************** \n"; 
	for (int i=0; i<vertices; i++)
	{		 
		cout << V[i]->getNumber() << ", " << V[i]->getLabel() << ", " << V[i]->getWeight()[0] << ", " << V[i]->getWeight()[1] << "\n";
	}
/*
	system( "pause" );
	cout << "***************************************** EDGES ************************************** \n"; 
	for (int  i=0; i<edges; i++)
	{		
		cout << E[i].getFrom() << ", " << E[i].getTo() << ", " << E[i].getLabel() << "\n";
	}
	*/
	
//	system( "pause" );	
/*	
	cout << "******************************** VERTICES & EDGES ******************************* \n"; 
	for (int  i=0; i<vertices; i++)
	{		 
		cout << "\n" << V[i]->getNumber() << ", " << V[i]->getLabel() << ", " << V[i]->getWeight()[0] << ", " << V[i]->getWeight()[1] << "\n";
		int deg_in = V[i]->getIn().size();
		cout << "\tIN : " << deg_in << "\n\t\t";
		for (int j=0; j<deg_in; j++)
		{
			Edge* e = V[i]->getIn()[j];
			cout << j << "/ " << e->getFrom() << "( " << e->getLabel() << " ); ";
		}
		int deg_out = V[i]->getOut().size();
		cout << "\n\tOUT : " << deg_out << "\n\t\t";
		for (int j=0; j<deg_out; j++)
		{
			Edge* e = V[i]->getOut()[j];
			cout << j << "/ " << e->getTo() << "( " << e->getLabel() << " ); ";
		}
		int deg_all = V[i]->getEdges().size();
		cout << "\n\tALL : " << deg_all << "\n\t\t";
		for (int j=0; j<deg_all; j++)
		{
			Edge* e = V[i]->getEdges()[j];
			cout << j << "/" << e->getFrom() << ">" << e->getTo() << "( " << e->getLabel() << " ); ";
		}
	}
*/	

	cout << "\n\n EDGES : " << "\n\n";
	for (int  i=0; i<E.size(); i++)
	{		 
		Edge* _e = E[i];
		int _i = _e->getFrom();
		int _j = _e->getTo();

		cout << "\n " << i << " : " << _i << ">" << _j << "( " << _e->getLabel() << " ); ";
	}
	system("pause");
}



vector<Vertex*> Graph::getV()
{
	return V;
}

vector<Edge*> Graph::getE()
{
	return E;
}

int Graph::getOrder()
{
	return vertices;
}
int Graph::getSize()
{
	return edges;
}

Graph::~Graph(void)
{
}
