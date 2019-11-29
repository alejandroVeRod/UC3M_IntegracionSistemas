package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import weka.classifiers.*;
import weka.core.Instances;

public class StartWeka {
	
	public static void main(String[] args) throws Exception {
		BufferedReader buffreader = new BufferedReader(new FileReader("gasolina.arff"));
		
		
		buffreader.close();
	}
}
