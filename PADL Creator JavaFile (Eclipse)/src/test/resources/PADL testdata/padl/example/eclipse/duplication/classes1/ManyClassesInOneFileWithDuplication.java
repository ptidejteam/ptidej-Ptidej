package padl.example.eclipse.duplication.classes1.manyClassesInOneFile;

public class ManyClassesInOneFileWithDuplication {

	private String name;

	public ManyClassesInOneFileWithDuplication() {

	}

	public ManyClassesInOneFileWithDuplication(String s) {
		this.name = s;

	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

class ManyClassesInOneFileWithDuplication {

	private String name;
	

	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	}

}

class ThirdClass {

	private int id;

	public ThirdClass() {

	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

class ThirdClass {

	

	public ThirdClass() {

	}

	

}
