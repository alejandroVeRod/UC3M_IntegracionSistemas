package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import weka.core.Instances;
import weka.classifiers.functions.GaussianProcesses;
import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.timeseries.WekaForecaster;

public class StartWeka {

	public static void main(String[] args) {

		try {

			// load the instance of Gasolina data
			System.out.println("--- Prediccion Gasolina ---");
			ArrayList<Double> prediccionGasolina = new ArrayList<Double>();
			Instances datasetGasolina = new Instances(new BufferedReader(new FileReader("gasolina.arff")));
			prediccionGasolina = prediccionWeka(datasetGasolina);

			// load the instance of Diesel data
			System.out.println("--- Prediccion Diesel ---");
			ArrayList<Double> prediccionDiesel = new ArrayList<Double>();
			Instances datasetDiesel = new Instances(new BufferedReader(new FileReader("diesel.arff")));
			prediccionDiesel = prediccionWeka(datasetDiesel);
			
			// INSERTAR EN LA BASE DE DATOS
			insertarDocumentos(prediccionGasolina, prediccionDiesel);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static ArrayList<Double> prediccionWeka(Instances dataset) {

		ArrayList<Double> prediccion = new ArrayList<Double>();
		try {
			// new forecaster
			WekaForecaster forecaster = new WekaForecaster();
			// set the targets we want to forecast.
			forecaster.setFieldsToForecast("x_0");

			// default underlying classifier is SMOreg (SVM) - we'll use
			// gaussian processes for regression instead
			forecaster.setBaseForecaster(new GaussianProcesses());

			forecaster.getTSLagMaker().setTimeStampField("None"); // time stamp
			// forecaster.getTSLagMaker().addCustomPeriodic("Weekly");
			forecaster.getTSLagMaker().setMinLag(1);
			forecaster.getTSLagMaker().setMaxLag(159);

			forecaster.getTSLagMaker().setAddDayOfWeek(true);

			// build the model
			forecaster.buildForecaster(dataset, System.out);
			// prime the forecaster with enough recent historical data
			// to cover up to the maximum lag.
			forecaster.primeForecaster(dataset);

			// forecast for 12 units (weeks) beyond the end of the training data
			List<List<NumericPrediction>> forecast = forecaster.forecast(12, System.out);

			// output the predictions.
			for (int i = 0; i < forecast.size(); i++) {
				List<NumericPrediction> predsAtStep = forecast.get(i);
				NumericPrediction predForTarget = predsAtStep.get(0);
				System.out.println("" + predForTarget.predicted() + " ");
				prediccion.add(predForTarget.predicted());
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return prediccion;
	}
	
	public static void insertarDocumentos(ArrayList<Double> prediccionGasolina, ArrayList<Double> prediccionDiesel) {
		for (int i = 0; i < prediccionGasolina.size(); i++) {
			org.bson.Document documento = new org.bson.Document();
			documento.append("Semana ", i + 1);
			documento.append("PrediccionGasolina", prediccionGasolina.get(i));
			documento.append("PrediccionDiesel", prediccionDiesel.get(i));
			DAOCombustible.insertPrediccion(documento);
			System.out.println("PrediccionGasolina - Semana "+(i+1)+": " + prediccionGasolina.get(i));
			System.out.println("PrediccionDiesel - Semana "+(i+1)+": " + prediccionDiesel.get(i));
		}
	}
}
