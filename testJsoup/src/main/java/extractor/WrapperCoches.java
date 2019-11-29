package extractor;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import extractor.Coches.ScrapperCoches;
import extractor.DistintivoMedioambiental.ScrapperDistintivos;
import modelo.DAOCoches;
import valoraciones.Utils;

public class WrapperCoches {

	private static List<org.bson.Document> listaDistintivos;
	public static void main(String[]args) {
		ScrapperDistintivos sDistintivos=new ScrapperDistintivos();
		listaDistintivos=sDistintivos.getInfoDistintivos();
		System.out.println(listaDistintivos);
		ScrapperCoches sCoches=new ScrapperCoches();
		
		sCoches.guardarCoches();
	}
	public static void asignarDistintivo(org.bson.Document coche) {
		for(org.bson.Document docDistintivo: listaDistintivos) {
			if((coche.containsValue("Gasolina")||coche.containsValue("Super 95") ) && docDistintivo.containsKey("inicioPeriodoGasolina")){
				if(coche.getInteger("ano") >= Integer.parseInt((String) docDistintivo.get("inicioPeriodoGasolina"))) {
					
					coche.append("distintivo",docDistintivo.get("distintivo"));
					//System.out.println(coche);
				}
			}else if(coche.containsValue("Diésel") && docDistintivo.containsKey("inicioPeriodoDiesel")) {
				if(coche.getInteger("ano")>=Integer.parseInt((String)docDistintivo.get("inicioPeriodoDiesel"))) {
					
					coche.append("distintivo", docDistintivo.get("distintivo"));
					//System.out.println(coche);
				}
			
			}else if(!(coche.containsValue("Diésel") || coche.containsValue("Gasolina")) && docDistintivo.containsKey("tiposCombustible")) {
				switch(coche.get("tipoCombustible").toString()) {
				case "Electro/Gasolina":
					coche.remove("tipoCombustible");
					coche.append("tipoCombustible", "híbrido");
					break;
				case "Electro/Diesel":
					coche.remove("tipoCombustible");
					coche.append("tipoCombustible", "híbrido");
					break;
				case "Gas licuado del petróleo":
					coche.remove("tipoCombustible");
					coche.append("tipoCombustible", "GLP");
					break;
				case "Gas natural":
					coche.remove("tipoCombustible");
					coche.append("tipoCombustible", "GNC");
					break;
				}

				if(docDistintivo.get("tiposCombustible").toString().contains((CharSequence) coche.get("tipoCombustible"))) {
					coche.append("distintivo", docDistintivo.get("distintivo"));
				}
					
	
			}

			}
	}
	public static void evaluarCoche(org.bson.Document coche) {
		
		float evaluacionCoche=Utils.setValorCoche(coche.getString("distintivo"), coche.getInteger("ano"), coche.getString("kilometraje"));
		System.out.println("Nota de coche"+evaluacionCoche);
		coche.append("nota", evaluacionCoche);
	}
}
