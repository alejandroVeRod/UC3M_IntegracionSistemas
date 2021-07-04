package vista;

public class FormCommand {

	private String precioMinimo;
	private String precioMaximo;
	private String marca;
	private String modelo;
	private String kilometraje;	

	public FormCommand() {
		
	}

	public String getPrecioMinimo() {
		return precioMinimo;
	}

	public void setPrecioMinimo(String precioMinimo) {
		this.precioMinimo = precioMinimo;
	}

	public String getPrecioMaximo() {
		return precioMaximo;
	}

	public void setPrecioMaximo(String precioMaximo) {
		this.precioMaximo = precioMaximo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getKilometraje() {
		return kilometraje;
	}

	public void setKilometraje(String kilometraje) {
		this.kilometraje = kilometraje;
	}
	
}
