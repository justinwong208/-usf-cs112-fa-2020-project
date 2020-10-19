
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Main {
	
		//variables used to display acc and precision
	    static double printAcc;
	    static double printPre;
	
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
		contentPane.add(new JLabel("Accuracy = " + AccString));
		String PreString = String.valueOf(printPre);
		contentPane.add(new JLabel("Precision = " + PreString));
		
		// pack() and setVisible() need to be called to realize and display 
		// the window.  If you're using an older JDK, you may need to use
		// show() instead of setVisible(true).
		myFrame.pack();
		myFrame.setVisible(true);
		
	   }  
	   
	public static void main(String[] args) {
		
		//Generate two sets of random DataPoints, one being the training data set, one being the test data set.
		ArrayList<DataPoint> trainData = new ArrayList<DataPoint>();
		trainData.add(new DataPoint(0, 0, "red", "yes"));
		trainData.add(new DataPoint(1, 0, "red", "yes"));
		trainData.add(new DataPoint(1, 1, "red", "no"));
		trainData.add(new DataPoint(2, 2, "blue", "no"));
		
		ArrayList<DataPoint> testData = new ArrayList<DataPoint>();
		testData.add(new DataPoint(1, 2, "blue", ""));
		
		//Instantiate a DummyModel class, train and test with your random data and compute the precision and accuracy.
		DummyModel firstTry = new DummyModel();
		firstTry.train(trainData);
		firstTry.test(trainData);
		
		printAcc  = firstTry.getAccuracy(trainData);
		printPre = firstTry.getPrecision(trainData);
		
		
		//Something to do with java swing that was used in lab3
		SwingUtilities.invokeLater(
		          new Runnable() { public void run() { initAndShowGUI(); } }
		        );
		    }
	}




