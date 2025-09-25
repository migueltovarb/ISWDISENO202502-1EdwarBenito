package employ;

public class Employee {
	private int id;
	private String firstName;
	private String lastName;
	private int salary;
	
	public Employee(int id,String fname,String lname,int salary){
		this.id= id ;
		this.firstName=fname;
		this.lastName=lname;
		this.salary=salary;		
	}
	
	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getName() {
		return this.firstName + this.lastName;
	}

	public int getSalary() {
		return salary;
	}

	public int getAnualsalary() {
		return salary*12;
	}
	
	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	public int raiseSalary(int percent) {
		
		return salary += salary*percent;
	}
	public String toString() {
		
		return "ID: "+id+",first name: "+firstName+",last name: "+lastName+",salary: "+salary;
	}
	public static void main(String[] args) {
		

	}

}
