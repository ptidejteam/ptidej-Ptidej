package edu.poly.ptidej.sample;

/**
 * 
 */

class AnotherRoot {
	
	class Branch {
		
	}
}

// The Root Class
public class Root {

	/*
	 * An inner class
	 */
	class Son extends Kid{
		
		class GrandDaughter implements GrandKid{
			
		}
	}

	class Daughter extends Kid{
				
		class GrandSon implements GrandKid{
			
		}

	}

}


