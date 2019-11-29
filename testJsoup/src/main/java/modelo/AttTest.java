package modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import modelo.DAOCombustible;

public class AttTest {
	
	public static void main(String[] args) throws Exception {
		//Crear gasolina.arff para el precio de la Gasolina
		ArrayList<Float> datosGasolina = new ArrayList<Float>();
		datosGasolina = DAOCombustible.extract("Gasolina");
		CreateArff("gasolina.arff", datosGasolina);
		
		//Crear diesel.arff para el precio del Diesel
		ArrayList<Float> datosDiesel = new ArrayList<Float>();
		datosDiesel = DAOCombustible.extract("Diesel");
		CreateArff("diesel.arff", datosDiesel);
	}

	private static File CreateArff(String fileName, ArrayList<Float> datos) throws IOException {
		File outputFile = new File(fileName);
		FileWriter fileWriter = new FileWriter(outputFile);
		BufferedWriter buffWriter = new BufferedWriter(fileWriter);
		
		buffWriter.write("@relation C__Users_oscar_git_UC3M_IntegracionSistemas_testJsoup\n");
		for (int i = 0; i < 2; i++) {
			buffWriter.write("@attribute x_"+i+" numeric\n");
		}
		//buffWriter.write("@attribute class numeric\n");
		buffWriter.write("@data\n");
		
		int cont = 1;
		for (Float float1 : datos) {
			buffWriter.write(cont+","+float1.toString()+"\n");
			cont++;
		}
		
		buffWriter.close();
		fileWriter.close();
		
		return outputFile;
	}	
	
}
