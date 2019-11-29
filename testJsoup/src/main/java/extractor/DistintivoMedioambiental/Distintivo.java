package extractor.DistintivoMedioambiental;

import java.util.ArrayList;

public class Distintivo {

	private String distintivo = "";
	private ArrayList<String> combustibles = new ArrayList<String>();
	
	
	
	public String getDistintivo() {
		return distintivo;
	}
	public void setDistintivo(String distintivo) {
		this.distintivo = distintivo;
	}
	public ArrayList<String> getTiposVehiculos() {
		return combustibles;
	}
	public void setTiposVehiculos(ArrayList<String> tiposVehiculos) {
		this.combustibles = tiposVehiculos;
	}

}
