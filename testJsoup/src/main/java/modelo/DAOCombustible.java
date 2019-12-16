package modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.Math;
import java.text.DecimalFormat;

import org.bson.Document;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;

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
	
	// Al llamar a este m�todo tenemos que pasarle como atributo el combustible que queremos extraer
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
	
	// Al llamar a este m�todo tenemos que pasarle como atributo el combustible que queremos extraer
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
	
	public static float promedioPrecioEstimado(String combustible) {
		float avg=0;
		double sumaPrecio=0;
		MongoCursor<Document> iter = dbPrediccion.find().iterator();
		while(iter.hasNext()) {
			Document d = iter.next();
			sumaPrecio += d.getDouble("Prediccion"+combustible);			
		}
		
		if(sumaPrecio>0) {
			avg= (float) (sumaPrecio/12);
		}	
//		DecimalFormat df = new DecimalFormat("#.00");
//		return Float.valueOf(df.format(avg));
		
		return avg;
	}
}
