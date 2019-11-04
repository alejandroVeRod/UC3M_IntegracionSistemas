package modelo;

import org.bson.Document;
import org.bson.conversions.Bson;


import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.or;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.gt;

import static com.mongodb.client.model.Updates.set;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class DAOCoches {

	private static MongoCollection<Document> dbCoches=MongoBroker.get().getCollection("Coches");
	private static String coleccionCoches="Coches";

	public static void insert(Document coche) {
		dbCoches=MongoBroker.getCollection(coleccionCoches);
		dbCoches.insertOne(coche);
	}
	
	public static List<Document> selectRange(float minPrecio,float maxPrecio) {
		//genera un filtro para la busqueda por precio
		Bson filtroRango=and(gt("precio",minPrecio),lt("precio",maxPrecio));
		
		FindIterable<Document> resultados= dbCoches.find(filtroRango);
		List<Document> coches = new ArrayList<Document>();
		for(Document doc: resultados) {
			coches.add(doc);
		}
		return coches;
	}
	
	//metodo para buscar los coches por su marca
	public static List<Document> selectFirm(String name){
		Bson filtroMarca=eq("marca",name);
		FindIterable<Document> resultados=dbCoches.find(filtroMarca);
		List<Document> coches = new ArrayList<Document>();
		for(Document doc: resultados) {
			coches.add(doc);
		}
		return coches;
	}
}
