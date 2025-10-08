package sysVet;
import java.util.*;
public class Pet {
	private String name;
	private String specie;
	private int age;
	private Owner owner;
	private List<Control> controlSummary=new ArrayList();
	
	public Pet(String name, String specie, int age, Owner owner, List<Control> controlSummary) {
		super();
		this.name = name;
		this.specie = specie;
		this.age = age;
		this.owner = owner;
		this.controlSummary = controlSummary;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecie() {
		return specie;
	}

	public void setSpecie(String specie) {
		this.specie = specie;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public List<Control> getControlSummary() {
		return controlSummary;
	}

	public void setControlSummary(List<Control> controlSummary) {
		this.controlSummary = controlSummary;
	}

	@Override
	public String toString() {
		return "Pet [name=" + name + ", specie=" + specie + ", age=" + age + "]";
	}
	
	

}
