package padl.example.fieldsAndReturnTypes;

import java.util.List;
import java.util.Map;

public abstract class FieldsAndReturnTypes {
	// refaire mettre les même choses en return type, en type field et en param
	// à faire demain matin inchaAllah et voir ce que donne le .class avec les
	// mêmes données, les passer au comparateur...

	// Fields
	int q;

	String compilerCompliance = "1.6";

	Integer i;

	NamedReader[] classpathEntries;

	int[] tabI;

	Object[] tabObjects;

	Object[][][] multiTabObjects;

	List l;

	List<Object>[] tabListOjects;

	List<NamedReader> listNamedReader;

	List<NamedReader[]> listTabsNamedReader;

	List<NamedReader[][]> listMultiTabsNamedReader;

	List<Integer> listIntegers;

	List<List<Integer>> listListsIntegers;

	List[] listTabsObjects;// array

	Map<String, String> map;

	// voir le cas d'un member class comme type et comparé avec le resultat en
	// .class

	// Return Types
	abstract protected int m1();

	abstract protected String m2();

	abstract protected Integer m3();

	abstract protected int[] m4();

	abstract protected NamedReader[] m5();

	abstract protected NamedReader[][] m6();

	abstract protected Object[][] m7();

	abstract protected List<Object>[] m8();

	abstract protected List<NamedReader> m9();

	abstract protected List<NamedReader[]> m10();

	abstract protected List<NamedReader[][]> m11();

	abstract protected List m12();

	abstract protected List<Integer> m13();

	abstract protected List<List<Integer>> m14();

	abstract protected List[] m15();

	abstract protected Map<String, String> m16();

	protected List<NamedReader[]> methodWithParams(int q,
			String compilerCompliance, Integer i,
			NamedReader[] classpathEntries, int[] tabI, Object[] tabObjects,
			Object[][][] multiTabObjects, List l, List<Object>[] tabListOjects,
			List<NamedReader> listNamedReader,
			List<NamedReader[]> listTabsNamedReader,
			List<NamedReader[][]> listMultiTabsNamedReader,
			List<Integer> listIntegers, List<List<Integer>> listListsIntegers,
			List[] listTabsObjects, Map<String, String> map) {

		return null;
	}

}
