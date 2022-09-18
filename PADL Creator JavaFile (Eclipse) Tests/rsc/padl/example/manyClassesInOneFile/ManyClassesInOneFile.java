package padl.example.manyClassesInOneFile;

public class ManyClassesInOneFile {

	private String name;

	public ManyClassesInOneFile() {

	}

	public ManyClassesInOneFile(String s) {
		this.name = s;

	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

class SecondClass {

	private String name;
	private int id;

	public SecondClass() {

	}

	public SecondClass(String s, int _id) {
		this.name = s;
		this.id = _id;

	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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
