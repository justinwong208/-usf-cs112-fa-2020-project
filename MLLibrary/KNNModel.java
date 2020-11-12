import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

public class KNNModel extends Model{
	public ArrayList<DataPoint> trainSet;
	public ArrayList<DataPoint> testSet;
	public ArrayList<String> category;
	private int K;
	private int numPassengersA = 0;
	private int numPassengersD = 0;
	ArrayList<DataPoint> dpList = new ArrayList<DataPoint>();
	
	
	public KNNModel(int k) {
		K = k;
		
		this.trainSet = new ArrayList<DataPoint>();
		this.testSet = new ArrayList<DataPoint>();
		this.category = new ArrayList<String>();
		
	}
	
	private double getDistance(DataPoint p1, DataPoint p2) {
		double distance = 0;
		double first = Math.pow(p2.getF1() - p1.getF1(), 2);
		double second = Math.pow(p2.getF2() - p1.getF2(), 2);
		distance = first + second;
		distance = Math.sqrt(distance);
		return distance;
	}
	
	

	// Helper function to split the line by commas and
	// return the values as a List of String
	private static List<String> getRecordFromLine(String line) {
		
	 List<String> values = new ArrayList<String>();
	 
	 try (Scanner rowScanner = new Scanner(line)) {
		 rowScanner.useDelimiter(",");
		 while (rowScanner.hasNext()) {
			 values.add(rowScanner.next());
		 }
	 }
	 	return values;
	}

	
	public ArrayList<DataPoint> csvIntoDp(){
		
		try (Scanner scanner = new Scanner(new File("titanic.csv"));) {
		
			while (scanner.hasNextLine()) {
				List<String> records = getRecordFromLine(scanner.nextLine());
			// TODO: Select the columns from the records and create a DataPoint object
				DataPoint dp = new DataPoint();
			
				dp.setF1(Double.parseDouble(records.get(4)));
			
				dp.setF2(Double.parseDouble(records.get(5)));
				
				dp.setLabel(records.get(1));
				
				
			
				dpList.add(dp);
			 }
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("File not found");
			}  
		return dpList;
	}
	//Sort dpList by train or test 
	public void trainOrTest() {
		// Put the below code in a method
		for(DataPoint sorted: dpList) {
			Random rand = new Random();
			double randNum = rand.nextDouble();
			// 90% of the data is reserved for training
			if (randNum < 0.9) {
				// Set the type of DataPoint as “train” and put into the Collection
				sorted.setType("train");
				
			} else {
				// Set the type of DataPoint as “test” and put into the Collection
				sorted.setType("test");
				
				}
			}
	
	}
	
	public void train(ArrayList<DataPoint> data) {

		int countA = 0;
		int countD = 0;
		for(DataPoint findingTrain: data) {
			
			if(findingTrain.getType().equals("train")){
				trainSet.add(findingTrain);
				
				if(findingTrain.getLabel().equals("0")){
						countD += 1;
				} else if(findingTrain.getLabel().equals("1")) {
					countA += 1;
				}
				
			}
			
		}
		numPassengersA = countA;
		numPassengersD = countD;
	}

	public String test(ArrayList<DataPoint> data) {
		//2D Array to hold distance and label
		testSet = new ArrayList<DataPoint>();
		category = new ArrayList<String>();
		double[][] array = new double[trainSet.size()][2];
		int counter = 0;
		for(DataPoint findingTest: data) {
			
			if(findingTest.getType().equals("test")){
				testSet.add(findingTest);
				//break;
			}
		}
		//Checks if the assumed "1" data is a test datapoint
		for(DataPoint test: testSet) {
		//if(testSet.get(0).getLabel().equals("1")) {
			//sort the distance of each trainset distance from test data and the train label into 2d array
			counter = 0;
			for(DataPoint compDist: trainSet) {
				
				array[counter][0] = getDistance(test, compDist);
				array[counter][1] = Double.parseDouble(compDist.getLabel());
				counter++;
			}
			
			//Sorts the 2d array by distance
			Arrays.sort(array, new java.util.Comparator<double[]>() {
			    public int compare(double[] a, double[] b) {
			        return Double.compare(a[0], b[0]);
			    }
			});
		//I think this is what your looking for in Step F5
			//Count the numbers of labels
			int countA = 0;
			int countD = 0;
			for(int i = 0; i < K; i++){
				if(array[i][1] == 0.0) {
					countD++;
				} else {
					countA++;
				}
			}		
			//Return the label that occurs the most
			if(countA > countD){
				category.add("1");
			} else{
				
				category.add("0");
			}
			
		
			
			
			
			
			
			
		}
		//}
		//Sorts the 2d array by distance
		Arrays.sort(array, new java.util.Comparator<double[]>() {
		    public int compare(double[] a, double[] b) {
		        return Double.compare(a[0], b[0]);
		    }
		});
	//I think this is what your looking for in Step F5
		//Count the numbers of labels
		return "";
		
	}
	
	 public Double getAccuracy(ArrayList<DataPoint> data) {
		double truePositive = 0; 
		double falsePositive = 0;
		double trueNegative = 0; 
		double falseNegative = 0;
		
		test(data);
		System.out.println(category.size() + " " + testSet.size());
		for(int i =0; i < category.size(); i++) {
			if(testSet.get(i).getLabel().equals("1")) {	
				if(testSet.get(i).getLabel().equals(category.get(i))) {
					truePositive ++;	
				}else {
					falsePositive++;
				}
			
			}
			if(testSet.get(i).getLabel().equals("0")) {	
				if(testSet.get(i).getLabel().equals(category.get(i))) {
					trueNegative ++;	
				}else {
					falseNegative++;
				}
			
			}

		}
		
		
		 return (truePositive + trueNegative) / (truePositive + trueNegative + falsePositive + falseNegative);
	 }
	 
	 public Double getPrecision(ArrayList<DataPoint> data) {
		 	double truePositive = 0; 
			double falsePositive = 0;
			double trueNegative = 0; 
			double falseNegative = 0;
			test(data);
			System.out.println(category.size() + " " + testSet.size());
			for(int i =0; i < category.size(); i++) {
				if(testSet.get(i).getLabel().equals("1")) {	
					if(testSet.get(i).getLabel().equals(category.get(i))) {
						truePositive ++;	
					}else {
						falsePositive++;
					}
				
				}
				if(testSet.get(i).getLabel().equals("0")) {	
					if(testSet.get(i).getLabel().equals(category.get(i))) {
						trueNegative ++;	
					}else {
						falseNegative++;
					}
				
				}

			}
		return truePositive / (truePositive + falseNegative);
	 }
	 
	 

	
	


}
