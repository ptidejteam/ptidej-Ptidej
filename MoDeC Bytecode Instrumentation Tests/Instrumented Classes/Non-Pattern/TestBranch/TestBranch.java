/**
 * @(#)TestBranch.java
 *
 *
 * @author 
 * @version 1.00 2007/1/31
 */

import java.io.*;

public class TestBranch {

    public static void main(String [] args)
    {	String [] lesActeurs = {"a", "b", "c" };
    	String [] lesFilms = {"1", "2", "3" };
    	
    	
    /*	int test = 0;
    	while (test <5 )
    	{
    		test++ ;
    		if(test == 4)
    			supprimerActeur(lesActeurs, lesFilms, 4, 4, "if");
    		else
    			supprimerActeur(lesActeurs, lesFilms, 4, 4, "else");
    			
    			
    	}
    			supprimerActeur(lesActeurs, lesFilms, 4, 4, "apres la boucle !");
    	
    */
    /*	try {
    		
    		
    	}
    	catch (FileNotFoundException e)
    	{
    		e.printStackTrace();
    	}*/
    	
    /*	int a = 0;
    	if(a == 0)
    	{
    		System.out.println("a");
    		System.out.println("b");
    		System.out.println("c");
    		
    	}*/
    	
    
    	
    	
    	/*for(int i = 0 ; i < 3; i++)
    		System.out.println("for " + i);
    	*/	
    /*	int i=0;
    	boolean entree = false;
    	int toto = 9 ;
    	while(i < 3 &&  !entree || (toto > 8 && toto != 4) )
    	{
    	//	try {
	    	//	FileWriter fstream = new FileWriter("toto", true);
	 	    	//BufferedWriter out = new BufferedWriter(fstream);
	    		entree = true;
	    		
	    	for(int j = 0 ; j < 2 ; j++)
	    	{
	    		int k = 0 ;
	    		while (k < 2)
	    		{
	    			System.out.println("Bonjour a tous !! ");
					k++;
				}
	    		
	    	
	    		if(entree)
		    		supprimerActeur(lesActeurs, lesFilms, 89, 98, "allo");
	    		i++;
	    		if(i == 2)
	    			entree = true;
	    		else
	    			System.out.println("Allo !");
	    	}
	    	toto--;
	    	
	    		//	out.write(i + "\n");    
			//	out.close();
    		//}
    		//catch(Exception e)
    		//{
    		//	System.err.println("Error: " + e.getMessage());
    		//}   
	    		
    	}*/
    	
        /*i=0;
    	do {
    		System.out.println("do .. while " + i);
    		i++;
    	} while (i < 3);*/
    	
    	/* ==============================================================*/
    	int h = 9;
    	switch (h)
    	{
    	
    			default :
    			
    				try
    				{
    					FileWriter fstream = new FileWriter("toototot", true);
 	    				BufferedWriter out = new BufferedWriter(fstream);
    					out.write(h + "\n");    
    					out.close();
    				} catch(Exception e)
    				{
    					System.err.println("Error: " + e.getMessage());
    				}
    			
    			System.out.println("switch default "  + h);
    			//break;
    		case 10 :
    			while (h <= 0)
    				h++;
    		case 4 :    			
    			supprimerActeur(lesActeurs, lesFilms, 4, 4, "allo");
    			//break;
    		case 9 :
    			while (h <= 0)
    				h++;
    			supprimerActeur(lesActeurs, lesFilms, 9, 9, "allo");
    			//System.out.println("switch case 3 " + i);
    			//break;
    		case 7 :    			
    			supprimerActeur(lesActeurs, lesFilms, 7, 7, "allo");
    			//break;

    	
    	}
    //	System.out.println("THE END");
    
    /*	 ==============================================================*/
    	 
    	 
    }
    
    
    static void supprimerActeur(String lesActeurs[], String lesFilms[], int nbActeurs, int nbFilms, String nom)
    {
    	StackTraceElement[] trace = new Throwable().getStackTrace();    		
    //	for(int i = 0 ; i < trace.length ; i++)
    	if(trace.length >= 1)
    		LogToFile.write("toto.txt", trace[1]);
    }
    
    
}