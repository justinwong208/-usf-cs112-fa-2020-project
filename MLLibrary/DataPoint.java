
public class DataPoint{
	
	//instance variables
	private Double f1;
	
	private Double f2;
	
	private String label;
	
	//Assumed that "type" was just another "feature"
	private String type;
	
	//Constructor
	 public DataPoint(double f1, double f2, String type, String label) {   
		 this.f1 = f1;     
		 this.f2 = f2;   
		 this.label = label;  
		 this.type = type;
	 }
	 
	 //No-arguement Constructor
	 public DataPoint() {
		 this(0, 0, "", "");
		 
	 }
	 
	 //Getters and Setters
	 public double getF1() {
		 return this.f1;
	 }
	 
	 public void setF1(double f1param) {
		 f1 = f1param;
	 }
	 
	 public double getF2() {
		 return this.f2;
	 }
	 
	 public void setF2(double f2param) {
		 f2 = f2param;
	 }
	 
	 public String getLabel() {
		 return this.label;
	 }
	 
	 public void setLabel(String labelparam) {
		 this.label = labelparam;
	 }
	 
	 public String getType() {
		 return this.type;
	 }
	 
	 public void setType(String typeparam) {
		 this.type = typeparam;
	 }
}
