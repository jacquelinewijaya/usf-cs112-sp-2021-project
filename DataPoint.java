package datanalytics;

public class DataPoint {
	public Double f1;
	public Double f2;
	public String label;
	public Boolean isTest;
	
	public DataPoint () {
		this.f1 = 0.0;
		this.f2 = 0.0;
		this.label = "";
		this.isTest = false;
	}
	
	public DataPoint (Double f1, Double f2, String label, Boolean isTest) {
		this.f1 = f1;
		this.f2 = f2;
		this.label = label;
		this.isTest = isTest;
	}
	
	public Double getF1() {
		return this.f1;
	}
	public Double getF2() {
		return this.f2;
	}
	public String getLabel() {
		return this.label;
	}
	public boolean isTest() {
		return this.isTest;
	}
	
	public void setF1(Double f1Num) {
		this.f1 = f1Num;
	}
	public void setF2(Double f2Num) {
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
	
}
