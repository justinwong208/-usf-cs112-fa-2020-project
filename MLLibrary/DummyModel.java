public class DummyModel extends Model{
	abstract void train(ArrayList<DataPoint> data) {
		
		return "Finished!!";
	}
	
	abstract String test(ArrayList<DataPoint> data) {
		
		return "Test Great!!";
	}
	
	abstract Double getAccuracy(ArrayList<DataPoint> data) {
		return 5.00;
		
	}
		
	abstract Double getPrecision(ArrayList<DataPoint> data) {
		return 1.50;
		
	}

}
