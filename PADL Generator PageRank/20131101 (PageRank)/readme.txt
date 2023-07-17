INPUT OF THE ALGORITHM

Since we don't take into account inter-procedural calls,
we should have every function in a file named after the function.
 
As an example, we have tcase_add_fixture.
The wanted format is in tcase_add_fixture_new.txt

The first line must have
#statements,#relations
Note that #statements is very important and should be accurate
while #relations is not "really" used in the current code.

There should be lines about the statements like provided in 
tcase_add_fixture_new.txt. We can do it the older way, 
having only the number of statements, but it may be good
to keep information about those statements in the graph.

eg, s,1,IF

Note that statements begin at number 0 and 0 is a dummy node
just before the first condition.


Lines about the relations can remain the same. The only difference is
that starting statements at #0 shifts the numbers of the statements.
Check the exemple for more information.

If the input in the good format, the program attached can be used as is.


OUTPUT

There's an exe file working that way
pagerank -i file_name -o output_file
to generate output_file (which contains the results).




segla.kpodjedo@polymtl.ca if it is not clear enough.
