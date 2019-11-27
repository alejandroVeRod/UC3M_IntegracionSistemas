package modelo;

import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import static com.mongodb.client.model.Filters.and;

import java.util.ArrayList;

public class DAOCombustible {

	private static MongoCollection<Document> dbCombustible = MongoBroker.get().getCollection("combustible");

	public static void insert(Document precioCombustible) {
		dbCombustible = MongoBroker.get().getCollection("combustible");
		dbCombustible.insertOne(precioCombustible);
	}
	
	public static ArrayList<Float> extract(String etiqueta) {
		ArrayList<Float> lista = new ArrayList<Float>();
		MongoCursor<Document> iter = dbCombustible.find().iterator();
		while(iter.hasNext()) {
			Document d = iter.next();
			lista.add(Float.valueOf(d.getString("Precio"+etiqueta)));
		}
		return lista;
	}
}
