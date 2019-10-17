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

public class ScrapperCoches {

	
	public static final int MAX_PAGES=10;
	public static final String URL="https://www.ocasionplus.com/coches-ocasion";
	
	public static void main(String[] args) {
		
			
			String filtro="a[href*=/coches-segunda-mano/]";
			
			for(int i=1;i<MAX_PAGES;i++) {
				String enlace=URL+"?pagina="+i;
				System.out.println(getHref(enlace,filtro));
			}
			
	}


	
	private static List<String> getHref(String enlace,String filtro) {
		List<String> enlaces=new ArrayList<String>();
		if(getStatusConnectionCode(URL)==200){
			Document doc = getHtmlDocument(enlace);
			Elements lst = doc.select("filtro");
			for (Element ele : lst) {
				enlaces.add(ele.absUrl("href"));			}
		}else {
			System.out.println("Error, de conexión");
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
			System.out.println("Excepci�n al obtener el HTML de la p�gina" + ex.getMessage());
		    }
	    return doc;
	}
	
	/**
	 * Con esta m�todo compruebo el Status code de la respuesta que recibo al hacer la petici�n
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
		System.out.println("Excepci�n al obtener el Status Code: " + ex.getMessage());
	    }
	    return response.statusCode();
	}
	

}

