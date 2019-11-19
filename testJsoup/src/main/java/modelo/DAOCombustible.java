package modelo;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

public class DAOCombustible {

	//private static MongoCollection<Document> dbCoches = MongoBroker.get().getCollection("coches");
	private static MongoCollection<Document> dbCombustible = MongoBroker.get().getCollection("combustible");

	public static void insert(Document precioCombustible) {
		/*dbCoches = MongoBroker.get().getCollection("coches");
		dbCoches.insertOne(coche);*/
		
		dbCombustible = MongoBroker.get().getCollection("combustible");
		dbCombustible.insertOne(precioCombustible);
	}
}
