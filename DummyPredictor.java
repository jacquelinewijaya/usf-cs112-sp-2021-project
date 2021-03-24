package datanalytics;

import java.util.ArrayList;
import java.awt.Container;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.*;  

public class DummyPredictor extends Predictor{
	
	private Double goodAvg;
	private Double badAvg;
	
	public ArrayList readData(String filename) {
		Double goodAvg = 0.0;
		Double badAvg = 0.0;
		ArrayList<Double>good = new ArrayList<Double>();
		ArrayList<Double>bad = new ArrayList<Double>();
		DataPoint d = new DataPoint();
		Double f1 = d.getF1();
		Double f2 = d.getF2();
		
		Scanner data = new Scanner(filename);
		String num1 = data.next();
		f1 = Double.parseDouble(num1);
		
		String num2 = data.next();
		f2 = Double.parseDouble(num2);
		
		Double avg = (f1+f2)/2;
		String label = data.next();
		
		if (label.equals("Good")) {
			this.goodAvg = avg;
			good.add(avg);
			System.out.printf("Good Avg: ");
			return good;
		}
		else {
			this.badAvg = avg;
			bad.add(avg);
			System.out.printf("Bad Avg: ");
			return bad;
		}
		
		
	}
	public String test(DataPoint data) {
		double testAvg = (data.getF1() + data.getF2())/2;
		Double good = Math.abs(testAvg - this.goodAvg);
		Double bad = Math.abs(testAvg - this.badAvg);
		
		if (good < bad) {
			return "Good";
		}
		else {
			return "Bad";
		}
		
	}
	public Double getAccuracy(ArrayList data) {
		return 0.0;
		
	}
	public Double getPrecision(ArrayList data) {
		return 0.0;
		
	}
	
	public static void main(String[] args)throws FileNotFoundException {
		DummyPredictor predictor = new DummyPredictor();
		DataPoint d = new DataPoint();
		File file = new File("Values.txt");
		
		//training
		Scanner scanner = new Scanner(file);
		String trainingSet1 = scanner.nextLine();
		System.out.println(predictor.readData(trainingSet1));
		
		
		String trainingSet2 = scanner.nextLine();
		System.out.println(predictor.readData(trainingSet2));
		
		
		//testing
		Double testF1 = d.getF1();
		Double testF2 = d.getF2();
		String testingSet = scanner.nextLine();
		Scanner testSetScanner = new Scanner(testingSet);
		String stringF1 = testSetScanner.next();
		testF1 = Double.parseDouble(stringF1);
		
		String stringF2 = testSetScanner.next();
		testF2 = Double.parseDouble(stringF2);
		
		DataPoint testData = new DataPoint(testF1, testF2, "", false);
		System.out.println("Test Data Label: " + predictor.test(testData));
		
		//precision and accuracy display in a JFrame
		JFrame myFrame = new JFrame();
		Container contentPane = myFrame.getContentPane();
		contentPane.setLayout(new FlowLayout());
		
		ArrayList<Double> avgArray = new ArrayList<> ();
		Double randomAccuracy = predictor.getAccuracy(avgArray);
		Double randomPrecision = predictor.getPrecision(avgArray);
		
		contentPane.add(new JButton(randomAccuracy.toString()));
    	contentPane.add(new JButton(randomPrecision.toString()));
    	myFrame.pack();
    	myFrame.setVisible(true);
    	myFrame.setTitle("Accuracy and Precision");
		
	}
}
