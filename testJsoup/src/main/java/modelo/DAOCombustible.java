package modelo;

import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import static com.mongodb.client.model.Filters.and;

import java.util.ArrayList;

public class DAOCombustible {

	private static MongoCollection<Document> dbCombustible = MongoBroker.get().getCollection("combustible");
	private static MongoCollection<Document> dbPrediccion = MongoBroker.get().getCollection("prediccion");


	public static void insert(Document precioCombustible) {
		dbCombustible = MongoBroker.get().getCollection("combustible");
		dbCombustible.insertOne(precioCombustible);
	}
	
	public static void insertPrediccion(Document precioPrediccion) {
		dbPrediccion = MongoBroker.get().getCollection("prediccion");
		dbPrediccion.insertOne(precioPrediccion);
	}
	
	// Al llamar a este método tenemos que pasarle como atributo el combustible que queremos extraer
	// Puede ser Gasolina, o bien, Diesel
	public static ArrayList<Float> extract(String etiqueta) {
		ArrayList<Float> lista = new ArrayList<Float>();
		MongoCursor<Document> iter = dbCombustible.find().iterator();
		while(iter.hasNext()) {
			Document d = iter.next();
			lista.add(Float.valueOf(d.getString("Precio"+etiqueta)));
		}
		return lista;
	}
	
	// Al llamar a este método tenemos que pasarle como atributo el combustible que queremos extraer
	// Puede ser Gasolina, o bien, Diesel
	public static ArrayList<Double> extractPrediccion(String etiqueta) {
		ArrayList<Double> lista = new ArrayList<Double>();
		MongoCursor<Document> iter = dbPrediccion.find().iterator();
		while(iter.hasNext()) {
			Document d = iter.next();
			lista.add(Double.valueOf(d.getString("Prediccion"+etiqueta)));
		}
		return lista;
	}
}
