package EjercicioMedicina;

public class Medicamento {

	private String nombre;
	private String descripcionUso;
	private String url;

	
	public Medicamento(String url) {
		this.url=url;
	}
	
	public Medicamento(String nombre, String descripcionUso) {

		this.nombre = nombre;
		this.descripcionUso = descripcionUso;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcionUso() {
		return descripcionUso;
	}
	public void setDescripcionUso(String descripcionUso) {
		this.descripcionUso = descripcionUso;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
