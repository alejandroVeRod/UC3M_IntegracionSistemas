package modelo;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoBroker {

	private final String databaseName;
	private MongoClientURI uri;
	private MongoClient client;
	private static MongoDatabase database;

	public MongoBroker() {

		this.databaseName = "mongodb://alex:alex1234@ds016108.mlab.com:16108/uc3m_isi?retryWrites=false";
		this.uri = new MongoClientURI(databaseName);
		this.client = new MongoClient(uri);
		MongoBroker.database = this.client.getDatabase("uc3m_isi");

	}

	private static class MongoBrokerHolder {
		static MongoBroker singleton = new MongoBroker();
	}

	public static MongoBroker get() {
		return MongoBrokerHolder.singleton;
	}

	public static MongoCollection<Document> getCollection(String name) {
		return database.getCollection(name);
	}
}
