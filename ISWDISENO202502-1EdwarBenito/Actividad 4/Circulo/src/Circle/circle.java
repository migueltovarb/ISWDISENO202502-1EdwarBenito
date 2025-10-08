package Circle;

public class circle {
	
	private double radio;
	
	public circle() {
		this.radio=1.0;
	}
	
	public circle(double radio) {
		this.radio=radio;
	}
	
	public double getRadio() {
		return this.radio ;
	}
	
	public void setRadio (double radio) {
		this.radio = radio;
	}
	
	public double getArea() {
		double area = Math.PI*Math.pow(radio, 2);	
		return area;
		}
	
	public double getPerimetro() {
		double perimetro = 2*Math.PI*radio;	
		return perimetro;
		}
	public String toString() {
		return "radio ="+ radio +", area = "+ getArea() +", perimetro= "+ getPerimetro();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
