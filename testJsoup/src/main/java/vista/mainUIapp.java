package vista;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import modelo.DAOCoches;


@SpringBootApplication
public class mainUIapp {

	public static void main(String[] args) {
		
		
		//eliminarRegistros();
		//ScrapperCoches.getInstance().guardarCoches();
		//inicializa la conexion para almacenar los coches de la bd en una variable
		DAOCoches.getAllCars(); 
		SpringApplication.run(mainUIapp.class, args);
	}
	
//	private static void eliminarRegistros() {
//		DAOCoches.deleteAll();
//	}

}
