package cochesNet;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class ScrapperCochesNet {


	
	public static final String URL="https://www.ocasionplus.com/coches-ocasion";
	
	public static void main(String[] args) {
		
		ArrayList<String> enlacesCoches= new ArrayList<String>();
		Elements enlaces;
		//This is a test

		try {
			URL enlace=new URL(URL);
			//System.out.println("is it safe? "+RobotRule.robotSafe(enlace));
			Document doc = Jsoup.connect(URL).get();
			System.out.println("Scrapping..."+doc.title());
			Elements lst = doc.select("div.jsx-2435177345");
			//System.out.println(doc.select("div.jsx-1873604330car-card").first().text());
			for (Element ele : lst) {
				System.out.println(ele.text());
				//enlacesCoches.add((ele.select("a[href]").toString()); //Recoge los enlaces a los coches.
			}
		}catch(IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	

}
