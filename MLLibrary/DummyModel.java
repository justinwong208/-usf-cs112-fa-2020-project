
import java.util.ArrayList;

public class DummyModel extends Model{
	public void train(ArrayList<DataPoint> data) {
		
		System.out.println( "Finished!!");
	}
	
	 public String test(ArrayList<DataPoint> data) {
		
		return "Test Great!!";
		
	}
	
	 public Double getAccuracy(ArrayList<DataPoint> data) {
		return 5.00;
		
	}
		
	 public Double getPrecision(ArrayList<DataPoint> data) {
		return 1.50;
		
	}

}
