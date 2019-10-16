package cochesNet;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import EjercicioMedicina.Medicamento;

public class ScrapperCochesNet {


	
	public static final String URL="https://www.coches.net/segunda-mano/";
	
	public static void main(String[] args) {
		
		ArrayList<String> enlacesCoches= new ArrayList<String>();
		Elements enlaces;
		//This is a test

		try {
			URL enlace=new URL(URL);
			System.out.println(RobotRule.robotSafe(enlace));
			//Recuperamos los enlaces por orden alfabetico
			Document doc = Jsoup.connect(URL).get();
			Elements lst = doc.select("div.mt-SerpList-item");
			for (Element ele : lst) {
				System.out.println(ele.text());
				//enlacesCoches.add((ele.select("a[href]").toString()); //Recoge los enlaces a los coches.
			}
		}catch(IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	

}
