package logic;

public class Coche {
	private String urlImagen;
	private String marca;
	private String modelo;
	private float precio;
	private float km;
	private int anno;
	private Object ubicacion;
	private int caballos;
	private String tipoCombustible;
	private float consumoCombustible;
	private float emisiones;
	private TipoCoche tipoCoche;

	public Coche(String urlImagen, String marca, String modelo, float precio, float km, int anno, Object ubicacion,
			int caballos, String tipoCombustible, float consumoCombustible, float emisiones, TipoCoche tipoCoche) {
		super();
		this.urlImagen = urlImagen;
		this.marca = marca;
		this.modelo = modelo;
		this.precio = precio;
		this.km = km;
		this.anno = anno;
		this.ubicacion = ubicacion;
		this.caballos = caballos;
		this.tipoCombustible = tipoCombustible;
		this.consumoCombustible = consumoCombustible;
		this.emisiones = emisiones;
		this.tipoCoche = tipoCoche;
	}

}
