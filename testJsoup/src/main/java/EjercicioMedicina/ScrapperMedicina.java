package EjercicioMedicina;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ScrapperMedicina {

	public static final String URL="https://medlineplus.gov/spanish/druginformation.html";
	
	public static void main(String[] args) {
		ArrayList<String> enlacesAlfabeticos= new ArrayList<String>();
		ArrayList<String> enlacesMedicamentos= new ArrayList<String>();
		ArrayList<Medicamento> medicamentos=new ArrayList<Medicamento>();
		//This is a test
		try {
			//Recuperamos los enlaces por orden alfabetico
			enlacesAlfabeticos=getHref(URL,"a[href*=druginfo/drug_]");

			for(String enlace: enlacesAlfabeticos) {
				enlacesMedicamentos=getHref(enlace,"a[href*=./meds]");
				
				for(String enlaceMedicamento: enlacesMedicamentos) {
					
					Medicamento m=new Medicamento(enlaceMedicamento);
					medicamentos.add(m);
				}
			}

			for(Medicamento med: medicamentos) {
				//getTitle(med.getUrl());
				getComoUsar(med.getUrl());
			}
			

			
			
		}catch(IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
	public static String getTitle(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements lst=doc.select("h1.with-also");
		return lst.text();
	}
	public static String getComoUsar(String url) throws IOException{
		Document doc = Jsoup.connect(url).get();
		Elements lst=doc.select("div.section-body");
		System.out.println(lst.text());
		return null;
	}
	
	public static ArrayList<String> getHref(String url,String filter) throws IOException{
		Document doc = Jsoup.connect(url).get();
		Elements lst = doc.select(filter);
		ArrayList<String> enlaces=new ArrayList<String>();
		for(Element elem:lst) {
			enlaces.add(elem.absUrl("href"));
		}
		return enlaces;
	}
	

	
}
