package extractor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import modelo.DAOCoches;


//import logic.Coche;

public class ScrapperCoches {

	private static final int MAX_PAGES=30;
	
	private static final String URL_COCHES= "https://www.autoscout24.es";
	private static final String URL="https://www.autoscout24.es/lst?sort=standard&desc=0&ustate=N%2CU&size=20&lon=-3.700345&lat=40.416691&zip=Madrid&zipr=1000&cy=E&atype=C&ac=0";
	
	private static final String filtroDetalles= "div[data-item-name= car-details]";
	private static final String filtroPrecio="div.cldt-price";
	
	
	private List<String> enlaces;
	private List<org.bson.Document> coches;
	
	public static void main(String[] args) {
//		ScrapperCoches sc=new ScrapperCoches();
//		sc.guardarCoches();
		List<org.bson.Document> coches=DAOCoches.selectRange(2000, 5000);
		
		for(org.bson.Document coche : coches)
		System.out.println(coche.get("precio"));

	}
	
	
	public ScrapperCoches() {
		this.enlaces=getUrls();
		this.coches=getCoches();
	}
	
	public List<org.bson.Document> getCoches(){
		List<org.bson.Document> listaCoches=new ArrayList<org.bson.Document>();
		
		String tipo ="";
		String marca ="";
		String modelo ="";
		int anno =0;
		String tipoCombustible ="";
		float consumo =0;
		float precio=0;
		String imagen="";
		String localizacion="";
		String categoria="";

		for (String enlace : enlaces) {
			Document doc = getHtmlDocument(URL_COCHES+enlace);
			org.bson.Document coche=new org.bson.Document();
			Element elem = doc.select(filtroDetalles).first();
			
			tipo =elem.getElementsContainingOwnText("Tipo de vehículo").next().text();
			precio =Float.valueOf(doc.select(filtroPrecio).first().text().replaceAll("[^\\dA-Za-z]", ""));
			imagen= doc.select("div.gallery-picture img").attr("src");
			localizacion=doc.select("div.cldt-stage-vendor-text.sc-font-s").first().text().split(",")[0];
			marca =elem.getElementsContainingOwnText("Marca").next().text();
			modelo =elem.getElementsContainingOwnText("Modelo").next().text();
			anno =Integer.parseInt(elem.getElementsContainingOwnText("Año").next().text());
			categoria=elem.getElementsContainingOwnText("Categoría").next().text();

			tipoCombustible=elem.getElementsContainingOwnText("Combustible").next().first().text();
			String c=elem.getElementsContainingOwnText("Consumo de combustible:").next().text().split(" ")[0];
			if(c.length()>1) {
				consumo=Float.valueOf(c.replace(",", "."));
			}

			coche.append("enlace", enlace);
			coche.append("tipo", tipo);
			coche.append("marca", marca);
			coche.append("modelo", modelo);
			coche.append("ano", anno);
			coche.append("categoria", categoria);
			coche.append("tipoCombustible", tipoCombustible);
			coche.append("consumo", consumo);
			coche.append("precio", precio);
			coche.append("imagen", imagen);
			coche.append("localizacion", localizacion);

			//System.out.println(coche);
			listaCoches.add(coche);
			System.out.println(coche);
			listaCoches.add(coche);
		}
		
		return listaCoches;
	}
	
	public void guardarCoches() {
		
		for(org.bson.Document coche : coches)
			DAOCoches.insert(coche);
	}
	
	private List<String> getUrls(){
		List<String> urls=new ArrayList<String>();
		String filtro="a[href*=/anuncios/]";
		
		for(int i=1;i<=MAX_PAGES;i++) {
			String enlace=URL+"&page="+i;
			List<String> enlaces= getHref(enlace,filtro);
			urls.addAll(enlaces);			
		}
		return urls;		
	}
	
	private static List<String> getHref(String enlace,String filtro) {
		List<String> enlaces=new ArrayList<String>();
		if(getStatusConnectionCode(URL)==200){
			Document doc = getHtmlDocument(enlace);
			Elements lst = doc.select(filtro);
			for (Element ele : lst) {
				String url= ele.attr("href");
				enlaces.add(url);			
			}
		}else {
			System.out.println("Error, de conexión");
		}
		return enlaces;
		
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
	 * Con esta mï¿½todo compruebo el Status code de la respuesta que recibo al hacer la peticiï¿½n
	 * EJM:
	 * 		200 OK			300 Multiple Choices
	 * 		301 Moved Permanently	305 Use Proxy
	 * 		400 Bad Request		403 Forbidden
	 * 		404 Not Found		500 Internal Server Error
	 * 		502 Bad Gateway		503 Service Unavailable
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

	public void setCoches(List<org.bson.Document> coches) {
		this.coches = coches;
	}
	

}

