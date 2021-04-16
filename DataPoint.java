package datanalytics;

public class DataPoint {
	public double f1;
	public double f2;
	public String label;
	public Boolean isTest;
	
	public DataPoint () {
		this.f1 = 0.0;
		this.f2 = 0.0;
		this.label = "";
		this.isTest = false;
	}
	
	public DataPoint (double f1, double f2, String label, Boolean isTest) {
		this.f1 = f1;
		this.f2 = f2;
		this.label = label;
		this.isTest = isTest;
	}
	
	public double getF1() {
		return this.f1;
	}
	public double getF2() {
		return this.f2;
	}
	public String getLabel() {
		return this.label;
	}
	public boolean isTest() {
		return this.isTest;
	}
	
	public void setF1(double f1Num) {
		this.f1 = f1Num;
	}
	public void setF2(double f2Num) {
		this.f2 = f2Num;
	}
	public void setLabel(String label) {
		if (! (label.equals("Green") || label.equals("Blue"))) {
			return;
		}
		this.label = label;
	}
	public void setIsTest(boolean testParam) {
		this.isTest = testParam;
	}
	public String toString() {
		return "age: " + this.f1 + " fare: " +this.f2 + " survival: " +this.label + " type: " + this.isTest;
	}
	
}
