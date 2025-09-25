package Circle;

public class Program {

	public static void main(String[] args) {
		circle miCirculo = new circle();
		double area=miCirculo.getArea();
		System.out.println("area:"+area);
		miCirculo.setRadio(300);
		area = miCirculo.getArea();
		
		System.out.println("area:"+area);
		
		circle miSegCirculo = new circle();
		area = miSegCirculo.getArea();
		System.out.println("area:"+area);
		
		double perimetro = miSegCirculo.getPerimetro();
		System.out.println("perimetro:"+perimetro);
		System.out.println(miSegCirculo);
		
	}

}
