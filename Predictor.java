package datanalytics;

import java.util.ArrayList;

public abstract class Predictor {
	
	public abstract ArrayList readData(String filename);
	
	public abstract String test(DataPoint data, ArrayList<DataPoint> trainLabels);
	
	public abstract Double getAccuracy(ArrayList<DataPoint> data, ArrayList<DataPoint> trainLabels);
	
	public abstract Double getPrecision(ArrayList<DataPoint> data, ArrayList<DataPoint> trainLabels);

}
