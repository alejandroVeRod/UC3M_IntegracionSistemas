package valoraciones;

import java.io.IOException;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Utils {
	
	/**
	 * M�todo que da un valor al coche en el momento de la inserci�n sobre un m�ximo de 5
	 **/
	public static float setValorCoche (String distintivo, int ano, float kilometraje) {
		
		float valoracion = 0;
		
		int annoMatriculacion =(Integer) ano;	
		float numeroKm = kilometraje;
		
		if (annoMatriculacion <= 1990) {
			valoracion += 1;
		} else if (annoMatriculacion > 1990 && annoMatriculacion < 2002) {
			valoracion += 2;
		} else if (annoMatriculacion >= 2002 && annoMatriculacion < 2012) {
			valoracion += 3;
		} else if (annoMatriculacion >= 2012 && annoMatriculacion < 2017) {
			valoracion += 4;
		} else if (annoMatriculacion >= 2017) {
			valoracion += 5;
		} 

		if (numeroKm <= 60) {
			valoracion += 5;
		} else if (numeroKm > 60 && numeroKm < 80){
			valoracion += 4;
		} else if (numeroKm >= 80 && numeroKm < 100) {
			valoracion += 3;
		} else if (numeroKm >= 100 && numeroKm < 200) {
			valoracion += 2;
		} else if (numeroKm >= 200) {
			valoracion += 1;
		}
		
		if(distintivo!=null) {
			switch (distintivo) {
			case "B":
				valoracion += 2;
				break;
			case "C":
				valoracion += 3;
				break;
			case "ECO":
				valoracion += 4;
				break;
			case "0":
				valoracion += 5;
				break;
			default:
				valoracion += 1;
				break;
			}
			System.out.println("valoracion distintivo"+distintivo+" "+valoracion);
		}
		return valoracion/3;
	}
	
	/**
	 * M�todo que suma al valor del coche otro valor basado en la adecuaci�n del coche devuelto a los par�metros de b�squeda 
	 **/
	public static float setAgregarValorPorBusqueda (String precioMin, String precioMax, String precio, String kilometrajeMin, String kilometrajeMax, String kilometraje, 
			String marcaBusqueda, String marcaEncontrada, String modeloBusqueda, String modeloEncontrado, String annoBusqueda, String annoMatricula) {
		
		float valoracion = 0;
		
		int precioCocheMin = precioMin != null ? Integer.parseInt(precioMin) : 0;
		int precioCocheMax = precioMax != null ? Integer.parseInt(precioMax) : 0;
		int precioCoche = precio != null ? Integer.parseInt(precio) : 0;
		int numeroKmMin = kilometrajeMin != null ? Integer.parseInt(kilometrajeMin) : 0;
		int numeroKmMax = kilometrajeMax != null ? Integer.parseInt(kilometrajeMax) : 0;
		int numeroKm = kilometraje != null ? Integer.parseInt(kilometraje) : 0;
		
		if (precioCoche < precioCocheMin) {
			valoracion += 2;
		} else if (precioCoche == precioCocheMax) {
			valoracion += 1;
		} else {
			valoracion += 0.5;
		} 

		if (numeroKm < numeroKmMin) {
			valoracion += 2;
		} else if (numeroKm == numeroKmMax) {
			valoracion += 1;
		} else {
			valoracion += 0.5;
		}
		
		valoracion += marcaBusqueda.equals(marcaEncontrada) && marcaBusqueda != null && marcaEncontrada != null ? 2 : 1;
		valoracion += modeloBusqueda.equals(modeloEncontrado) && modeloBusqueda != null && modeloEncontrado != null ? 2 : 1;
		valoracion += annoBusqueda.equals(annoMatricula) && annoBusqueda != null && annoMatricula != null ? 2 : 1;
		
		return valoracion/5;
		
	}
	


}
