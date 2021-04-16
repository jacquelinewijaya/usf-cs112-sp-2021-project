package datanalytics;

import java.util.ArrayList;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Random;
import java.lang.Math;

public class KNNPredictor extends Predictor {
	
	private int K;
	private int survivedPassengers = 0;
	private int diedPassengers = 0;
	static ArrayList<DataPoint> trainLabels = new ArrayList<DataPoint>();
	
	public KNNPredictor(int val) {
		this.K = val;
	}
	
	public KNNPredictor() {
	}
	
	public ArrayList readData(String filename) {
		int testentry = 0;
		int trainentry = 0;
		ArrayList<DataPoint> dataPoint = new ArrayList<DataPoint>();
		DataPoint d = new DataPoint();
		
		Scanner data = new Scanner(filename);
		
		data.useDelimiter(",");
		while (data.hasNext()) {
			double pclass = data.nextDouble();
			double survival = data.nextDouble();
			String survivalStr = String.valueOf(survival);
			String fullName = d.getLabel();
			String lastName = data.next();
			String firstName = data.next();
			fullName = firstName.substring(1, firstName.length()-1) + " " + lastName.substring(1);
			
			
			String gender = data.next();
			String stringAge = data.next();
			String stringFare = data.next();
			
			
			if (!stringFare.equals("") && !stringAge.equals("")) { //if age and fare are valid numbers
				double age = d.getF1();
				age = Double.parseDouble(stringAge);
				double fare = d.getF2();
				fare = Double.parseDouble(stringFare);
				if (age>=1) {
					boolean type = d.isTest();
					Random rand = new Random();
					double randNum = rand.nextDouble();
					
					if(randNum < 0.9) {
						type = false; //false is train
						if (survival == 1.0) {
							survivedPassengers++;
						}
						else {
							diedPassengers++;
						}
						trainentry++;
					}
					else {
						type = true; //true is test
						testentry++;
					}
					d = new DataPoint(age, fare, survivalStr, type);
					dataPoint.add(d);
				}
			}
		}
		System.out.println("training set survived passengers: " + survivedPassengers);
		System.out.println("training set passengers who died: " + diedPassengers);
		System.out.println("people assigned to test: " + testentry);
		System.out.println("people assigned to train: " + trainentry);
		
		return dataPoint; //return arraylist
	}
	
	private double getDistance(DataPoint p1, DataPoint p2) {
		double x1 = p1.f1;
		double y1 = p1.f2;
		
		double x2 = p2.f1;
		double y2 = p2.f2;
		
		return Math.sqrt(((x2-x1) * (x2-x1)) + ((y2-y1) * (y2-y1)));
		
	}
	public String test(DataPoint data) {
		double distance;
		int k = 3;
		int survived = 0;
		int died = 0;
		int count = 0;
		Double[][] arr = new Double[trainLabels.size()][2];
		
		for (DataPoint trainDataPoint : trainLabels) {
			distance = getDistance(data, trainDataPoint);
			arr[count][0] = distance;
			arr[count][1] = Double.parseDouble(trainDataPoint.label);
			count++;
		}
		
		java.util.Arrays.sort(arr,new java.util.Comparator<Double[]>() {
			public int compare(Double[]a, Double[]b){
				return a[0].compareTo(b[0]);
			}
		});
		
		
		for (int i = 0; i<k; i++) {
			if (arr[i][1] == 1) {
				survived++;
			}
			else {
				died ++;
			}
		}
		
		if (survived > died) {
			return "1";
		}
		else {
			return "0";
		}
	}
	
	public Double getAccuracy(ArrayList<DataPoint> data) {
		double truePositive = 0.0;
		double falsePositive = 0.0;
		double trueNegative = 0.0;
		double falseNegative = 0.0;
		
		for (DataPoint d : data) {
			if (d.isTest = true) {
				String label = test(d);
				if (label.equals("1") && d.label.equals("1.0")) {
					truePositive++;
				}
				if (label.equals("1") && d.label.equals("0.0")) {
					falsePositive++;
				}
				if (label.equals("0") && d.label.equals("0.0")) {
					trueNegative++;
				}
				if (label.equals("0") && d.label.equals("1.0")) {
					falseNegative++;
				}
			}
		}
		double result = (truePositive + trueNegative) / (truePositive + trueNegative + falsePositive + falseNegative);
		return result*100;
	}
	
	public Double getPrecision(ArrayList<DataPoint> data) {
		double truePositive = 0.0;
		double falsePositive = 0.0;
		double trueNegative = 0.0;
		double falseNegative = 0.0;
		
		for (DataPoint d : data) {
			if (d.isTest == true) {
				String label = test(d);

				if (label.equals("1") && d.label.equals("1.0")) {
					truePositive++;
				}
				if (label.equals("1") && d.label.equals("0.0")) {
					falsePositive++;
				}
				if (label.equals("0") && d.label.equals("0.0")) {
					trueNegative++;
				}
				if (label.equals("0") && d.label.equals("1.0")) {
					falseNegative++;
				}
			}
		}
		
		double precisionResult = (truePositive) / (truePositive + falseNegative);
		return precisionResult*100;
	}
	static ArrayList<DataPoint> finalArray = new ArrayList<DataPoint>();
	public static void main(String[] args) throws FileNotFoundException {
		KNNPredictor predictor = new KNNPredictor();
		
		try (Scanner scanner = new Scanner(new File("titanic.csv"))) {
			scanner.nextLine();
			String records = "" ;
			
			//reading from file
			while(scanner.hasNextLine()) {
				String record = scanner.nextLine() + ",";
				records += record;
			}
			finalArray = predictor.readData(records);
			
			//adding to array of train labels
			for (DataPoint f : finalArray) { 
				if (f.isTest == false) {
					trainLabels.add(f);
				}
			}
			//getting accuracy
			double accuracy = predictor.getAccuracy(finalArray);
			
			//getting precision
			double precision = predictor.getPrecision(finalArray);
			
			JFrame myFrame = new JFrame();
			Container contentPane = myFrame.getContentPane();
			contentPane.setLayout(new FlowLayout());
			
			contentPane.add(new JButton(String.format("%.2f", accuracy)));
			contentPane.add(new JButton(String.format("%.2f", precision)));
			
			myFrame.pack();
			myFrame.setVisible(true);
	    		myFrame.setTitle("Accuracy and Precision");
			
		}
	}
	
}
