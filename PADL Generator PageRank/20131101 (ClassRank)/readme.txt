ClassRank

-i input file 
-o output file (default: 'input_file_' + concatenate(parameters of ClassRank))
-m input file format (default: 4 for diagram classes with nodes preceded by "c" or "s" and edges precededed by "r")
m=0 to read
/*
	 * Generic graph format
	 * n,m
	 * #node,label_node,weight0,weight1, ..
	 * ...
	 * #node_src,#node_dest,label_edge
	 * ...
*/

-j jump probability for PageRank (default: standard 0.15)
-a weight of 1st label type (default: 1)
-b weight of 2nd label type (default: 1)
-c weight of 3rd label type (default: 1)
-d weight of 4th label type (default: 1)
-e weight of 5th label type (default: 1)