package extractor;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import modelo.DAOCoches;

public class WrapperCoches {

	private static List<org.bson.Document> listaDistintivos;
	public static void main(String[]args) {
		ScrapperDistintivos sDistintivos=new ScrapperDistintivos();
		listaDistintivos=sDistintivos.getInfoDistintivos();
		ScrapperCoches sCoches=new ScrapperCoches();
		sCoches.guardarCoches();
	}
	public static void asignarDistintivo(org.bson.Document coche) {
		for(org.bson.Document docDistintivo: listaDistintivos) {
			if((coche.containsValue("Gasolina")||coche.containsValue("Super 95") ) && docDistintivo.containsKey("inicioPeriodoGasolina")){
				if(coche.getInteger("ano") >= Integer.parseInt((String) docDistintivo.get("inicioPeriodoGasolina"))) {
					
					coche.append("Distintivo",docDistintivo.get("distintivo"));
					//System.out.println(coche);
				}
			}else if(coche.containsValue("Di�sel") && docDistintivo.containsKey("inicioPeriodoDiesel")) {
				if(coche.getInteger("ano")>=Integer.parseInt((String)docDistintivo.get("inicioPeriodoDiesel"))) {
					
					coche.append("Distintivo", docDistintivo.get("distintivo"));
					//System.out.println(coche);
				}
			
			}else if(!(coche.containsValue("Di�sel") || coche.containsValue("Gasolina")) && docDistintivo.containsKey("tiposCombustible")) {
				switch(coche.get("tipoCombustible").toString()) {
				case "Electro/Gasolina":
					coche.remove("tipoCombustible");
					coche.append("tipoCombustible", "h�brido");
					break;
				case "Electro/Diesel":
					coche.remove("tipoCombustible");
					coche.append("tipoCombustible", "h�brido");
					break;
				case "Gas licuado del petr�leo":
					coche.remove("tipoCombustible");
					coche.append("tipoCombustible", "GLP");
					break;
				case "Gas natural":
					coche.remove("tipoCombustible");
					coche.append("tipoCombustible", "GNC");
					break;
				}

				if(docDistintivo.get("tiposCombustible").toString().contains((CharSequence) coche.get("tipoCombustible"))) {
					coche.append("Distintivo", docDistintivo.get("distintivo"));
				}
					
	
			}
			}
			
	
	}
}
