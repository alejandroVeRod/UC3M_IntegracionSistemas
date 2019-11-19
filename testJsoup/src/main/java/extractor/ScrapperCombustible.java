package extractor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.FileReader;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import java.util.Map.Entry;

import modelo.DAOCombustible;

public class ScrapperCombustible {
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws FileNotFoundException {
		/*
		 * List<org.bson.Document> listaCoches = getCoches();
		 * 
		 * for (org.bson.Document doc : listaCoches) { DAOCoches.insert(doc); }
		 */
		
		//PRECIO COMBUSTIBLE GASOLINA
		ArrayList<String> preciosGasolina = new ArrayList<String>();
		JsonParser parserGasolina = new JsonParser();
		FileReader frGasolina = new FileReader("evolucion_del_precio_de_l.json");
		JsonElement datosGasolina = parserGasolina.parse(frGasolina);
		dumpJSONElement(datosGasolina, preciosGasolina);
		
		//PRECIO COMBUSTIBLE DIESEL
		ArrayList<String> preciosDiesel = new ArrayList<String>();
		JsonParser parserDiesel = new JsonParser();
		FileReader frDiesel = new FileReader("evolucion_del_precio_de_g.json");
		JsonElement datosDiesel = parserDiesel.parse(frDiesel);
		dumpJSONElement(datosDiesel, preciosDiesel);
		
		//IMPRIMIR ARRAYS DE COMBUSTIBLE Y ALMACENAR DATOS EN MONGODB
		System.out.println("---PRECIOS GASOLINA---");
		for (String gasolina : preciosGasolina) {
			System.out.println(gasolina);
			//DAOCombustible.insert(gasolina);
		}
		
		System.out.println("---PRECIOS DIESEL---");
		for (String diesel : preciosDiesel) {
			System.out.println(diesel);
			//DAOCombustible.insert(diesel);
		}
	}

	public static void dumpJSONElement(JsonElement elemento, ArrayList<String> precios) {
		if (elemento.isJsonObject()) {
			JsonObject obj = elemento.getAsJsonObject();
			java.util.Set<java.util.Map.Entry<String, JsonElement>> entradas = obj.entrySet();
			java.util.Iterator<java.util.Map.Entry<String, JsonElement>> iter = entradas.iterator();
			while (iter.hasNext()) {
				java.util.Map.Entry<String, JsonElement> entrada = iter.next();
				dumpJSONElement(entrada.getValue(), precios);
			}

		} else if (elemento.isJsonArray()) {
			JsonArray array = elemento.getAsJsonArray();
			java.util.Iterator<JsonElement> iter = array.iterator();
			while (iter.hasNext()) {
				JsonElement entrada = iter.next();
				dumpJSONElement(entrada, precios);
			}

		} else if (elemento.isJsonPrimitive()) {
			JsonPrimitive valor = elemento.getAsJsonPrimitive();
			if (valor.isNumber() && (valor.getAsDouble() > 0 && valor.getAsDouble() <= 2)) {
				try {
					precios.add(valor.getAsString());
				}
				catch (Exception e) {
					System.out.println("Error: " + e.toString());
				}
			}
		} else {
			
		}
	}

	/*
	 * private static List<org.bson.Document> getCoches() { List<org.bson.Document>
	 * listaCoches = new ArrayList<org.bson.Document>(); List<String> urls =
	 * getUrls(); String tipo = ""; String marca = ""; String modelo = ""; String
	 * anno = ""; String combustible = "";
	 * 
	 * for (String enlace : urls) { Document doc = getHtmlDocument(URL_COCHES +
	 * enlace); String filtro = "div[data-item-name= car-details]"; Elements lst =
	 * doc.select(filtro); org.bson.Document coche = new org.bson.Document(); for
	 * (Element elem : lst) { tipo =
	 * elem.getElementsContainingOwnText("Tipo de vehículo").next().text(); marca =
	 * elem.getElementsContainingOwnText("Marca").next().text(); modelo =
	 * elem.getElementsContainingOwnText("Modelo").next().text(); anno =
	 * elem.getElementsContainingOwnText("Año").next().text(); combustible =
	 * elem.getElementsContainingOwnText("Combustible").next().text();
	 * 
	 * coche.append("tipo", tipo); coche.append("marca", marca);
	 * coche.append("modelo", modelo); coche.append("ano", anno);
	 * coche.append("combustible", combustible); // Coche coche= new
	 * Coche(urlImagen, marca, modelo, precio, km, anno, ubicacion, // caballos,
	 * combustible, consumoCombustible, emisiones, tipoCoche); }
	 * 
	 * System.out.println(marca + " , " + modelo + " , " + anno + " , " + tipo +
	 * " , " + combustible);
	 * 
	 * // listaCoches.add(coche);
	 * 
	 * }
	 * 
	 * return listaCoches; }
	 * 
	 */

	public static Document getHtmlDocument(String url) {

		Document doc = null;
		try {
			doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get();
		} catch (IOException ex) {
			System.out.println("Excepciï¿½n al obtener el HTML de la pï¿½gina" + ex.getMessage());
		}
		return doc;
	}

	/**
	 * Con esta mï¿½todo compruebo el Status code de la respuesta que recibo al
	 * hacer la peticiï¿½n EJM: 200 OK 300 Multiple Choices 301 Moved Permanently
	 * 305 Use Proxy 400 Bad Request 403 Forbidden 404 Not Found 500 Internal Server
	 * Error 502 Bad Gateway 503 Service Unavailable
	 */
	public static int getStatusConnectionCode(String url) {

		Response response = null;

		try {
			response = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute();
		} catch (IOException ex) {
			System.out.println("Excepciï¿½n al obtener el Status Code: " + ex.getMessage());
		}
		return response.statusCode();
	}

}
