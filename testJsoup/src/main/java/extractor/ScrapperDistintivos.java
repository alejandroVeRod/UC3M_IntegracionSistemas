package extractor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class ScrapperDistintivos {

	/**
	 * URL DIANA
	 * */
	
	private List<org.bson.Document> infoDistintivos;
	public static final String URL_ITV = "https://itv.com.es/clasificacion-dgt-emisiones-co2-coches";

	public ScrapperDistintivos() {
		try {
			infoDistintivos=getDistintivos();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<org.bson.Document> getInfoDistintivos(){
		return this.infoDistintivos;
	}

	/**
	 * Método que devolverá los distintivos ambientales y su información asociada
	 * */
	private static List<org.bson.Document> getDistintivos() throws IOException {
		List<org.bson.Document> infoDistintivos = new ArrayList<org.bson.Document>();
		Document doc;
		//doc = Jsoup.connect(URL_ITV).get();
		doc = getHtmlDocument(URL_ITV);
		Elements etiquetas = doc.select("h3 > span");
		Elements descDistintivos = doc.select("h3 + p");

		for (int i=0; i<etiquetas.size(); i++) {
			String distintivo = etiquetas.get(i).text().replace("Vehículos ","").trim().toUpperCase();
			if (distintivo.contentEquals("0")) {
				org.bson.Document docDistintivoZero = new org.bson.Document();
				
				docDistintivoZero.append("distintivo", crearDistintivo0(distintivo, descDistintivos.get(i).text()).getDistintivo());
				docDistintivoZero.append("tiposCombustible", crearDistintivo0(distintivo, descDistintivos.get(i).text()).getTiposVehiculos());
				infoDistintivos.add(docDistintivoZero);
			} else if (distintivo.contentEquals("ECO")) {
				org.bson.Document docDistintivoEco = new org.bson.Document();
				docDistintivoEco.append("distintivo", crearDistintivoECO(distintivo, descDistintivos.get(i).text()).getDistintivo());
				docDistintivoEco.append("tiposCombustible", crearDistintivoECO(distintivo, descDistintivos.get(i).text()).getTiposVehiculos());
				infoDistintivos.add(docDistintivoEco);
			} else if (distintivo.contentEquals("C")) {
				org.bson.Document docDistintivoC = new org.bson.Document();
				docDistintivoC.append("distintivo", crearDistintivoC(distintivo, descDistintivos.get(i).text()).getDistintivo());
				docDistintivoC.append("inicioPeriodoGasolina", crearDistintivoC(distintivo, descDistintivos.get(i).text()).getInicioPeriodoDistintivoGasolina());
				docDistintivoC.append("inicioPeriodoDiesel", crearDistintivoC(distintivo, descDistintivos.get(i).text()).getInicioPeriodoDistintivoDiesel());
				infoDistintivos.add(docDistintivoC);
			} else if (distintivo.contentEquals("B")) {
				org.bson.Document docDistintivoB = new org.bson.Document();
				docDistintivoB.append("distintivo", crearDistintivoB(distintivo, descDistintivos.get(i).text()).getDistintivo());
				docDistintivoB.append("inicioPeriodoGasolina", crearDistintivoB(distintivo, descDistintivos.get(i).text()).getInicioPeriodoDistintivoGasolina());
				docDistintivoB.append("inicioPeriodoDiesel", crearDistintivoB(distintivo, descDistintivos.get(i).text()).getInicioPeriodoDistintivoDiesel());
				infoDistintivos.add(docDistintivoB);
			}
		}
		return infoDistintivos;
	}


	public static DistintivoZero crearDistintivo0(String distintivo, String descripcionDistintivo) {

		ArrayList<String> combustiblesZero = new ArrayList<String>();

		Pattern p1 = Pattern.compile("eléctricos de batería");
		Pattern p2 = Pattern.compile("BEV");
		Pattern p3 = Pattern.compile("autonomía extendida");
		Pattern p4 = Pattern.compile("REEV");
		Pattern p5 = Pattern.compile("eléctricos híbridos enchufables");
		Pattern p6 = Pattern.compile("PHEV");
		Pattern p7 = Pattern.compile("vehículos de pila de combustible");

		Matcher zero1 = p1.matcher(descripcionDistintivo);
		if (zero1.find()) {
			combustiblesZero.add(zero1.group());
		}
		Matcher zero2 = p2.matcher(descripcionDistintivo);
		if (zero2.find()) {
			combustiblesZero.add(zero2.group());
		}
		Matcher zero3 = p3.matcher(descripcionDistintivo);
		if (zero3.find()) {
			combustiblesZero.add(zero3.group());
		}
		Matcher zero4 = p4.matcher(descripcionDistintivo);
		if (zero4.find()) {
			combustiblesZero.add(zero4.group());
		}
		Matcher zero5 = p5.matcher(descripcionDistintivo);
		if (zero5.find()) {
			combustiblesZero.add(zero5.group());
		}
		Matcher zero6 = p6.matcher(descripcionDistintivo);
		if (zero6.find()) {
			combustiblesZero.add(zero6.group());
		}
		Matcher zero7 = p7.matcher(descripcionDistintivo);
		if (zero7.find()) {
			combustiblesZero.add(zero7.group());
		}

		DistintivoZero distintivoZero = new DistintivoZero();
		distintivoZero.setDistintivo(distintivo);
		distintivoZero.setTiposVehiculos(combustiblesZero);

		return distintivoZero;
	}

	public static DistintivoEco crearDistintivoECO(String distintivo, String descripcionDistintivo) {

		ArrayList<String> combustiblesEco = new ArrayList<String>();

		Pattern p8 = Pattern.compile("híbridos enchufables con una autonomía inferior a 40 km");
		Pattern p9 = Pattern.compile("híbridos no enchufables");
		Pattern p10 = Pattern.compile("HEV");
		Pattern p11 = Pattern.compile("GNC");
		Pattern p12 = Pattern.compile("GNL");
		Pattern p13 = Pattern.compile("GLP");

		Matcher eco8 = p8.matcher(descripcionDistintivo);
		if (eco8.find()) {
			combustiblesEco.add(eco8.group());
		}
		Matcher eco9 = p9.matcher(descripcionDistintivo);
		if (eco9.find()) {
			combustiblesEco.add(eco9.group());
		}
		Matcher eco10 = p10.matcher(descripcionDistintivo);
		if (eco10.find()) {
			combustiblesEco.add(eco10.group());
		}
		Matcher eco11 = p11.matcher(descripcionDistintivo);
		if (eco11.find()) {
			combustiblesEco.add(eco11.group());
		}
		Matcher eco12 = p12.matcher(descripcionDistintivo);
		if (eco12.find()) {
			combustiblesEco.add(eco12.group());
		}
		Matcher eco13 = p13.matcher(descripcionDistintivo);
		if (eco13.find()) {
			combustiblesEco.add(eco13.group());
		}

		DistintivoEco distintivoEco = new DistintivoEco();
		distintivoEco.setDistintivo(distintivo);
		distintivoEco.setTiposVehiculos(combustiblesEco);
		
		return distintivoEco;
	}

	public static DistintivoC crearDistintivoC(String distintivo, String descripcionDistintivo) {

		Pattern p14 = Pattern.compile("gasolina matriculados a partir de enero de 2006");
		Pattern p15 = Pattern.compile("diésel matriculados después de enero de 2014");

		String inicioPeriodoDistintivoGasolina = "";
		String inicioPeriodoDistintivoDiesel = "";

		Matcher eco14 = p14.matcher(descripcionDistintivo);
		if (eco14.find()) {
			inicioPeriodoDistintivoGasolina = eco14.group().substring(eco14.group().length()-4, eco14.group().length()).trim();
		}
		Matcher eco15 = p15.matcher(descripcionDistintivo);
		if (eco15.find()) {
			inicioPeriodoDistintivoDiesel = eco15.group().substring(eco15.group().length()-4, eco15.group().length()).trim();
		}

		DistintivoC distintivoC = new DistintivoC();
		distintivoC.setDistintivo(distintivo);
		distintivoC.setInicioPeriodoDistintivoGasolina(inicioPeriodoDistintivoGasolina);
		distintivoC.setInicioPeriodoDistintivoDiesel(inicioPeriodoDistintivoDiesel);
		
		return distintivoC;
	}

	public static DistintivoB crearDistintivoB(String distintivo, String descripcionDistintivo) {

		Pattern p16 = Pattern.compile("gasolina posteriores al 2000");
		Pattern p17 = Pattern.compile("diésel matriculadas desde enero de 2006");

		String inicioPeriodoDistintivoGasolina = "";
		String inicioPeriodoDistintivoDiesel = "";

		Matcher eco16 = p16.matcher(descripcionDistintivo);
		if (eco16.find()) {
			inicioPeriodoDistintivoGasolina = eco16.group().substring(eco16.group().length()-4, eco16.group().length()).trim();
		}
		Matcher eco17 = p17.matcher(descripcionDistintivo);
		if (eco17.find()) {
			inicioPeriodoDistintivoDiesel = eco17.group().substring(eco17.group().length()-4, eco17.group().length()).trim();
		}
		DistintivoB distintivoB = new DistintivoB();
		distintivoB.setDistintivo("B");
		distintivoB.setInicioPeriodoDistintivoGasolina(inicioPeriodoDistintivoGasolina);
		distintivoB.setInicioPeriodoDistintivoDiesel(inicioPeriodoDistintivoDiesel);
		
		return distintivoB;
	}

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
