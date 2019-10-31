package modelo;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

public class DAOCoches {

	private static MongoCollection<Document> dbCoches=MongoBroker.get().getCollection("coches");
	private static String coleccionCoches="Coches";

	public static void insert(Document coche) {
		dbCoches=MongoBroker.getCollection(coleccionCoches);
		dbCoches.insertOne(coche);
	}
}
