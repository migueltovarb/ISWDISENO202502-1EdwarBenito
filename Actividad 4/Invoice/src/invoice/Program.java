package invoice;

public class Program {
    public static void main(String[] args) {
        Invoice item1 = new Invoice("A101", "Laptop", 2, 1500.0);
        System.out.println(item1);
        System.out.println("Total: " + item1.getTotal());

        item1.setQty(3);
        item1.setUnitPrice(1400.0);
        System.out.println("Nuevo total: " + item1.getTotal());
        System.out.println(item1);
    }
}
