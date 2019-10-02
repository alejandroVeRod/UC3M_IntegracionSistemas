package testJsoup;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {
	public static final String URL_WIKIE = "https://es.wikipedia.com/";

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		getTitle(URL_WIKIE);
		getHRef(URL_WIKIE);
		getImgs(URL_WIKIE);
	}
	
	public static void getTitle(String url) throws IOException{
		Document doc = Jsoup.connect(url).get();
		System.out.println(url + ":\t" + doc.title());
		System.out.println();
	}
	
	public static void getHRef(String url) throws IOException{
		Document doc = Jsoup.connect(url).get();
		System.out.println("a href from:\t" + doc.title());
		Elements lst = doc.select("a[href]");
		for (Element elem:lst) {
			System.out.println("\t:" + elem.text());
		}
		System.out.println();
	}
	public static void getImgs(String url) throws IOException{
		Document doc = Jsoup.connect(url).get();
		System.out.println("Imgs from:\t" + doc.title());
		Elements lst = doc.select("img[src~=[\\w\\d\\W]logo[\\w\\d\\W]]");
		for (Element elem:lst) {
			System.out.println("\t:" + elem.attr("src"));
		}
		System.out.println();
	}
}
