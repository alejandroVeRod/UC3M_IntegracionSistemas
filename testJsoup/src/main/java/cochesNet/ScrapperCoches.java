package cochesNet;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
		
		List<String> enlacesCoches=new ArrayList<String>();
			
			getEnlacesCoches(enlacesCoches);

			System.out.println(enlacesCoches);
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
			System.out.println("Excepción al obtener el HTML de la página" + ex.getMessage());
		    }
	    return doc;
	}
	
	/**
	 * Con esta método compruebo el Status code de la respuesta que recibo al hacer la petición
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
		System.out.println("Excepción al obtener el Status Code: " + ex.getMessage());
	    }
	    return response.statusCode();
	}
	

}

