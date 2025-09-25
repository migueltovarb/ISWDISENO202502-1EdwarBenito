package employ;

public class Program{
    public static void main(String[] args) {
        
        Employee emp1 = new Employee(1, "Juan", "Pérez", 2000);
        System.out.println("Empleado 1: " + emp1);
        
        System.out.println("Nombre: " + emp1.getName());
        System.out.println("Salario mensual: " + emp1.getSalary());
        System.out.println("Salario anual: " + emp1.getAnualsalary());
        
        emp1.raiseSalary(10);
        System.out.println("Salario tras aumento del 10%: " + emp1.getSalary());
        
        Employee emp2 = new Employee(2, "Ana", "López", 3500);
        System.out.println("\nEmpleado 2: " + emp2);
        
        emp2.setSalary(4000);
        System.out.println("Nuevo salario de Ana: " + emp2.getSalary());
        
        System.out.println("Salario anual de Ana: " + emp2.getAnualsalary());
        
        System.out.println(emp2);
    }
}

