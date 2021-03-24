package datanalytics;

import java.util.ArrayList;

public abstract class Predictor {
	
	public abstract ArrayList readData(String filename);
	
	public abstract String test(DataPoint data);
	
	public abstract Double getAccuracy(ArrayList<DataPoint> data);
	
	public abstract Double getPrecision(ArrayList<DataPoint> data);

}