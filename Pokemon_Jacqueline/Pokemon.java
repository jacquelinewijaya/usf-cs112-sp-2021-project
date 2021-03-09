package inheritanceclass;

//Maleke H. Cloned Repository


public class Pokemon implements Comparable<Pokemon> {
	private int age;
	private String name;
	private String color;
	private boolean hasTrainer;
	private String moveType;
	private int value;
	
	public Pokemon(String nParam, int aParam) {
		this.name = nParam;
		this.age = aParam;
	}
	public String getName() {
		return this.name;
	}
	public int getAge() {
		return this.age;
	}
	
	public int compareTo (Pokemon otherP) {
		return this.age - otherP.getAge();
	}
	
	/*
	public Pokemon(String nParam, String cParam) {
		this.name = nParam;
		this.color = cParam;
		this.hasTrainer = false;
	}
	
	public Pokemon(String nParam, String cParam, boolean hParam) {
		this.name = "";
		this.color = "";
		this.hasTrainer = hParam;
	}
	//default constructor
	public Pokemon() {
		this.name = "";
		this.color = "";
		this.hasTrainer = false;
		this.moveType = "";
	}
	//accessor
	public String getName() {
		return this.name;
	}
	public String getColor() {
		return this.color;
	}
	public boolean hasTrainer() {
		return this.hasTrainer;
	}
	//mutator
	public void setName(String nParam) {
		this.name = nParam;
	}
	public void setColor(String cParam) {
		this.color = cParam;
	}
	public void setHasTrainer(boolean hParam) {
		this.hasTrainer = hParam;
	}
	//method
	public String toString() {
		return "I am a Pokemon: \" + this.name + \" : \" + this.color + \" : \" + this.hasTrainer()";
	}
	//virtual method
	public void move(String moveParam) {
		this.moveType = moveParam;
	}
	//equals method
	public boolean equals(Object obj) {
		Pokemon other = (Pokemon) obj;
		return color.equals(other.color) && value == other.value;
	}
	//abstract methods
	//public abstract void attack();
	//public abstract void speak();
	 
	 */
}
