#include <stdio.h>

// this is a reqlly dumb program
main(int argc, char *argv[])
{FILE *p1 = fopen(argv[1],"r");
 FILE *p2 = fopen("tmpDOS","w");
 int ic = 1;
 if (p1 != NULL)
 {char c = getc(p1);
 while (c != EOF)
   {if (((int) c) != 13) putc(c,p2);
    ic++;
    c = getc(p1);}
  fclose(p2);
  FILE *p3 = fopen(argv[1],"w");
  FILE *p4 = fopen("tmpDOS","r");
  while ((c = getc(p4)) != EOF) putc(c,p3);
  fclose(p3);
  printf("[%s] Copied %d chars\n",argv[1],ic);
 }}

