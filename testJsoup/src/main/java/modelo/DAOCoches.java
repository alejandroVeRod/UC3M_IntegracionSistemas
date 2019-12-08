package modelo;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.lte;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class DAOCoches {

	private static MongoCollection<Document> dbCoches=MongoBroker.get().getCollection("Coches");
	private static String coleccionCoches="Coches";
	public static List<Document> listaCoches;	
	
	public static void insert(Document coche) {
		//dbCoches=MongoBroker.getCollection(coleccionCoches);
		dbCoches.insertOne(coche);
	}
	
	public static void deleteAll() {
		dbCoches.drop();
	}
	
	public static List<Document> getAllCars() {		
		FindIterable<Document> resultados = dbCoches.find().sort(new BasicDBObject("nota",-1));
		listaCoches = new ArrayList<Document>();
		for(Document doc: resultados) {
			listaCoches.add(doc);
		}
		
		return listaCoches;
	}	
	
	public static List<Double> getKilometers() {		
		List<Double> kms= new ArrayList<Double>(); 
		for(Document doc: listaCoches) {
			kms.add(doc.getDouble("kilometraje"));
		}
		return kms;
	}	
	
	public static List<Document> advance(float minPrecio,float maxPrecio,String marca, String modelo, float kilometraje){
		//b�squeda base (todos los coches con un precio mayor a cero u otro valor)
		Bson km=and(gt("kilometraje", kilometraje));
		if(kilometraje > 0) {
			km= and(lt("kilometraje", kilometraje));
		}
		
		Bson filtro= and(gt("precio",minPrecio), km);		
		
		if(maxPrecio > 0) {
			//busqueda en un rango de precios
			filtro= and(gt("precio",minPrecio),lt("precio",maxPrecio));
			if(marca != "" ) {
				//busqueda en un rango de precio y marca definida
				filtro= and(gt("precio",minPrecio),lt("precio",maxPrecio),
						  and(eq("marca",marca), km));
				if(modelo!= "") {
					//busqueda en un rango de precio, con marca y modelo definidos
					filtro= and(gt("precio",minPrecio),lt("precio",maxPrecio),
							  and(eq("marca",marca), and(eq("modelo",modelo), km)));
				}
			}
		}else {
			if(marca != "" ) {
				filtro= and(gte("precio",minPrecio),and(eq("marca",marca)));
				if(modelo!= "") {
					filtro= and(gte("precio",minPrecio),and(eq("marca",marca), and(eq("modelo",modelo))));
				}
			}else {
				if(modelo!= "") {
					filtro= and(gte("precio",minPrecio), and(eq("modelo",modelo)));
				}
			}
		}		
		
		FindIterable<Document> resultados=dbCoches.find(filtro).sort(new BasicDBObject("nota",-1));
		List<Document> coches = new ArrayList<Document>();
		for(Document doc: resultados) {
			coches.add(doc);
		}
		return coches;
	}
	
	public static ArrayList<String> obtenerMarcasCoches() {
		ArrayList<String> marcas = new ArrayList<String>();
		for(Document doc: listaCoches) {
			marcas.add(doc.getString("marca"));
		}
		return marcas;
	}
	
	public static ArrayList<String> obtenerModelosCoches() {
		ArrayList<String> modelos = new ArrayList<String>();
		for(Document doc: listaCoches) {
			modelos.add(doc.getString("modelo"));
		}	    
	    return modelos;
	}
	
	public static ArrayList<Integer> obtenerKilometrajes() {
		ArrayList<Integer> kms = new ArrayList<Integer>();
		for(Document doc: listaCoches) {
			double d= doc.getDouble("kilometraje");
			kms.add((int)d);
		}	    
	    return kms;
	}
}
