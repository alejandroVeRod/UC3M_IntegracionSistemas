package vista;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import java.util.Map;

import modelo.DAOCoches;
import vista.FormCommand;


@Controller
@SessionAttributes("coches")
public class IndexController {

	@GetMapping("/")
	public String index(HttpSession session,Model model,ModelAndView modelView ) {
		((Model) model).addAttribute("titulo", "TuMejorCoche");
		
		//obteniendo coches
		
		model.addAttribute("coches", DAOCoches.listaCoches);	
		model.addAttribute("marcas", DAOCoches.obtenerMarcasCoches());
		model.addAttribute("modelos", DAOCoches.obtenerModelosCoches());
		model.addAttribute("kilometros", generarKilometros());
		model.addAttribute("command", new FormCommand());
		return "index";
	}
	
	@PostMapping ("/") 
	public String foobarPost(@ModelAttribute("command")FormCommand form, Model model) {
		List<Document> coches= null;
		
		float precioMin= Float.parseFloat(form.getPrecioMinimo());
		float precioMax= Float.parseFloat(form.getPrecioMaximo());
						
		String marca= (form.getMarca().equals("0"))?"": form.getMarca();
		String modelo= (form.getModelo().equals("0"))?"": form.getModelo();
		
		coches=DAOCoches.advance(precioMin, precioMax, marca, modelo);		
		
		model.addAttribute("coches", coches);
		model.addAttribute("command", new FormCommand());
		model.addAttribute("precios", generarPrecios());
		model.addAttribute("marcas", DAOCoches.obtenerMarcasCoches());
		model.addAttribute("modelos", DAOCoches.obtenerModelosCoches());
		model.addAttribute("kilometros", generarKilometros());
		return "index"; 
	}	
	

	public ArrayList<String> generarKilometros(){
		ArrayList<String> result = new ArrayList<String>();
		
		ArrayList<Float> kms= DAOCoches.obtenerKilometrajes();
		Float maximo= Collections.max(kms);
		Float minimo= Collections.min(kms);

	    for(Float i=minimo; i< maximo; i+= 100) {
	    	String value= Float.toString(i);
	    	result.add(value);
	    }
	    return result;
	}
	
	public ArrayList<String> generarPrecios() {
		ArrayList<String> precios = new ArrayList<String>();

	    for(int i=200; i< 2000; i+= 100) {
	    	String value= Integer.toString(i);
	    	precios.add(value);
	    }
	    return precios;
	}
	
	
	
	public String estadisticas(Model model, List<org.bson.Document> coches) {
		Map<String, Integer> map= new LinkedHashMap<String, Integer>();
		for (Document document : coches) {
			map.put(document.getString("combustible"), document.getInteger("precio"));
		}
		model.addAttribute("grafico", map);
		return "index";
	}
	
//	@GetMapping("/mix-params")
//	public String getCochesPorPrecio(HttpServletRequest request, Model model) {
//		float precioMin= Float.parseFloat(request.getParameter("precioMin"));
//		float precioMax= Float.parseFloat(request.getParameter("precioMax"));
//		
//		List<Document> coches= DAOCoches.selectRange(precioMin, precioMax);
//		model.addAttribute("coches", coches);
//		return "index";
//	}
	
}
