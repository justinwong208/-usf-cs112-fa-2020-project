
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Main {
	
		//variables used to display acc and precision
	    static double printAcc;
	    static double printPre;
	    static ArrayList<DataPoint> dpList = new ArrayList<DataPoint>();
	     public static ArrayList<DataPoint> trainSet; 
		 public static  ArrayList<DataPoint> testSet; 
		
		
	   private static void initAndShowGUI() {
		// A JFrame is a window.
		JFrame myFrame = new JFrame();

		// The content pane is the meat of the window -- the window minus
		// any menubars, titlebar, close/minimize/maximize buttons, etc.
		Container contentPane = myFrame.getContentPane();
		// We need to set how we want our content pane to lay out the 
		// objects we add to it.  For now, we'll use a FlowLayout, which
		// places objects left-to-right in a line.  (See the javadocs.)
		contentPane.setLayout(new FlowLayout());
		// Now we can add any Swing components we like:
		myFrame.setLayout(new GridLayout(4,1));
		myFrame.setTitle("Testing Results");

		
		String AccString = String.valueOf(printAcc);
		contentPane.add(new JLabel("Accuracy =" + AccString));
		String PreString = String.valueOf(printPre);
		contentPane.add(new JLabel("Precision =" + PreString));
		
		// pack() and setVisible() need to be called to realize and display 
		// the window.  If you're using an older JDK, you may need to use
		// show() instead of setVisible(true).
		myFrame.pack();
		myFrame.setVisible(true);
		
	   }  
	   
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
		public static void trainOrTest() {
			// Put the below code in a method
			
			
			for(DataPoint sorted: dpList) {
				Random rand = new Random();
				double randNum = rand.nextDouble();
				// 90% of the data is reserved for training
				if (randNum < 0.9) {
					// Set the type of DataPoint as “train” and put into the Collection
					sorted.setType("train");
					//trainSet.add(sorted);
				} else {
					// Set the type of DataPoint as “test” and put into the Collection
					sorted.setType("test");
					//testSet.add(sorted);
					}
				}
		
		}
	   
	   
	public static void main(String[] args) {
		
		//Generate two sets of random DataPoints, one being the training data set, one being the test data set.
		/*DataPoint[] trainData = new DataPoint[4];
		trainData[0] = new DataPoint(0, 0, "red", "yes");
		trainData[1] = new DataPoint(1, 0, "red", "yes");
		trainData[2] = new DataPoint(1, 1, "red", "no");
		trainData[3] = new DataPoint(2, 2, "blue", "no");
		*/
		
		ArrayList<DataPoint> trainData = new ArrayList<DataPoint>();
		trainData.add(new DataPoint(0, 0, "red", "yes"));
		trainData.add(new DataPoint(1, 0, "red", "yes"));
		trainData.add(new DataPoint(1, 1, "red", "no"));
		trainData.add(new DataPoint(2, 2, "blue", "no"));
		/*
		DataPoint[] testData = new DataPoint[1];
		testData[0] = new DataPoint(1, 2, "blue", "");
		*/
		
		ArrayList<DataPoint> testData = new ArrayList<DataPoint>();
		testData.add(new DataPoint(1, 2, "blue", ""));
		
		//Instantiate a DummyModel class, train and test with your random data and compute the precision and accuracy.
		DummyModel firstTry = new DummyModel();
		firstTry.train(trainData);
		firstTry.test(trainData);
		
		////////////////////
		//this is for part 2 of project
		///////////////////
		
		
		//read titanic into dpList
		try (Scanner scanner = new Scanner(new File("titanic.csv"));) {
			List<String> spacer = getRecordFromLine(scanner.nextLine());
			while (scanner.hasNextLine()) {
				
				List<String> records = getRecordFromLine(scanner.nextLine());
			// TODO: Select the columns from the records and create a DataPoint object
				DataPoint dp = new DataPoint();
				
				
				if(records.get(5).length() > 0) {
					dp.setF1(Double.parseDouble(records.get(5)));
				} else {
					
					dp.setF1(0.0);
				}
				if(records.size() > 6) {
					dp.setF2(Double.parseDouble(records.get(6)));
				} else {
					
					dp.setF2(0.0);
				}
				
				
				dp.setLabel(records.get(1));
				
				
			
				dpList.add(dp);
			 }
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("File not found");
				}  
			
		trainOrTest();
		
		//Instantiate a knnModel
		KNNModel firstTestRun = new KNNModel(7);
		
		firstTestRun.train(dpList);
		
		firstTestRun.test(dpList);
		
		printAcc  = firstTestRun.getAccuracy(dpList);
		printPre = firstTestRun.getPrecision(dpList);
		
		System.out.println(firstTestRun.getAccuracy(dpList));
		//for(DataPoint sorted: dpList) {
		//	System.out.println(sorted.getType());
		//}
		//Something to do with java swing that was used in lab3
		SwingUtilities.invokeLater(
		          new Runnable() { public void run() { initAndShowGUI(); } }
		        );
		    }
	}




