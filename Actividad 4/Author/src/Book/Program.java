package Book;

public class Program {
    public static void main(String[] args) {
        Author a1 = new Author("Gabriel García Márquez", "gabo@gmail.com", 'm');
        System.out.println(a1);

        a1.setEmail("ggmarquez@yahoo.com");
        System.out.println("Nuevo correo: " + a1.getEmail());
        
    }
}
