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

import logic.Coche;

public class ScrapperCoches {

	
	public static final int MAX_PAGES=10;
	private static final String URL_COCHES= "https://www.autoscout24.es";
	public static final String URL="https://www.autoscout24.es/lst?sort=standard&desc=0&ustate=N%2CU&size=20&lon=-3.700345&lat=40.416691&zip=Madrid&zipr=1000&cy=E&atype=C&ac=0";
	
	public static void main(String[] args) {
		//getUrls();	//recoge todos los enlaces de los coches existentes en la pagina
		getCoches();
	}	
	
	private static List<Coche> getCoches(){
		List<Coche> coches= new ArrayList<Coche>();
		
		List<String> urls= getUrls();
		
		String tipo ="";
		String marca ="";
		String modelo ="";
		String anno ="";
		String combustible ="";
		
		for (String enlace : urls) {
			Document doc = getHtmlDocument(URL_COCHES+enlace);
			String filtro= "div[data-item-name= car-details]";
			Elements lst = doc.select(filtro);
			
			for (Element elem : lst) {
				tipo =elem.getElementsContainingOwnText("Tipo de vehículo").next().text();
				marca =elem.getElementsContainingOwnText("Marca").next().text();
				modelo =elem.getElementsContainingOwnText("Modelo").next().text();
				anno =elem.getElementsContainingOwnText("Año").next().text();
				combustible =elem.getElementsContainingOwnText("Combustible").next().text();
				
//				Coche coche= new Coche(urlImagen, marca, modelo, precio, km, anno, ubicacion, caballos, combustible, consumoCombustible, emisiones, tipoCoche);
			}
			System.out.println(marca + " , " + modelo+" , " + anno + " , " + tipo+ " , " + combustible);
		}
		
		
		
		return coches;
	}
	
	private static List<String> getUrls(){
		List<String> urls=new ArrayList<String>();
		String filtro="a[href*=/anuncios/]";
		
		for(int i=1;i<MAX_PAGES;i++) {
			String enlace=URL+"&page="+i;
			List<String> enlaces= getHref(enlace,filtro);
			urls.addAll(enlaces);			
		}
		System.out.println(urls);
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
			System.out.println("Error, de conexiÃ³n");
		}
		return enlaces;
		
	}
	

	private static void getEnlacesCoches(List<String> enlacesCoches) {
		if(getStatusConnectionCode(URL)==200){
			Document doc = getHtmlDocument(URL);
			Elements lst = doc.select("a[href*=/coches-segunda-mano/]");
			for (Element ele : lst) {
				enlacesCoches.add(ele.absUrl("href"));
				//enlacesCoches.add((ele.select("a[href]").toString()); //Recoge los enlaces a los coches.
			}
		}
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
	

}

