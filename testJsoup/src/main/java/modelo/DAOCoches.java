package modelo;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

public class DAOCoches {

	private static MongoCollection<Document> dbCoches = MongoBroker.get().getCollection("coches");

	public static void insert(Document coche) {
		dbCoches = MongoBroker.get().getCollection("coches");
		dbCoches.insertOne(coche);

	}
}
