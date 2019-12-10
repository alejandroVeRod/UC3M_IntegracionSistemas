package extractor.Combustible;

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

		// PRECIO COMBUSTIBLE GASOLINA
		ArrayList<String> preciosGasolina = new ArrayList<String>();
		JsonParser parserGasolina = new JsonParser();
		FileReader frGasolina = new FileReader("evolucion_del_precio_de_l.json");
		JsonElement datosGasolina = parserGasolina.parse(frGasolina);
		dumpJSONElement(datosGasolina, preciosGasolina);

		// PRECIO COMBUSTIBLE DIESEL
		ArrayList<String> preciosDiesel = new ArrayList<String>();
		JsonParser parserDiesel = new JsonParser();
		FileReader frDiesel = new FileReader("evolucion_del_precio_de_g.json");
		JsonElement datosDiesel = parserDiesel.parse(frDiesel);
		dumpJSONElement(datosDiesel, preciosDiesel);

		// INSERTAR EN LA BASE DE DATOS
		insertarDocumentos(preciosGasolina, preciosDiesel);
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
				} catch (Exception e) {
					System.out.println("Error: " + e.toString());
				}
			}
		} else {

		}
	}

	// INSERTAR DATOS DE COMBUSTIBLE EN MONGODB
	public static void insertarDocumentos(ArrayList<String> preciosGasolina, ArrayList<String> preciosDiesel) {
		for (int i = 0; i < preciosGasolina.size(); i++) {
			org.bson.Document documento = new org.bson.Document();
			documento.append("Semana ", i + 1);
			documento.append("PrecioGasolina", preciosGasolina.get(i));
			documento.append("PrecioDiesel", preciosDiesel.get(i));
			DAOCombustible.insert(documento);
			System.out.println("Gasolina - Semana "+(i+1)+": " + preciosGasolina.get(i));
			System.out.println("Diesel - Semana "+(i+1)+": " + preciosDiesel.get(i));
		}
	}

	public static Document getHtmlDocument(String url) {

		Document doc = null;
		try {
			doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).get();
		} catch (IOException ex) {
			System.out.println("Excepci�n al obtener el HTML de la p�gina" + ex.getMessage());
		}
		return doc;
	}

	/**
	 * Con esta m�todo compruebo el Status code de la respuesta que recibo al
	 * hacer la petici�n EJM: 200 OK 300 Multiple Choices 301 Moved Permanently
	 * 305 Use Proxy 400 Bad Request 403 Forbidden 404 Not Found 500 Internal Server
	 * Error 502 Bad Gateway 503 Service Unavailable
	 */
	public static int getStatusConnectionCode(String url) {

		Response response = null;

		try {
			response = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute();
		} catch (IOException ex) {
			System.out.println("Excepci�n al obtener el Status Code: " + ex.getMessage());
		}
		return response.statusCode();
	}

}
