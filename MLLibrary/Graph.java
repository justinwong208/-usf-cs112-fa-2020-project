
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Graph extends JPanel {
	
	/////
	//Self added stuff
	 static ArrayList<DataPoint> dpList = new ArrayList<DataPoint>();
	 static double printAcc;
	 static double printPre;
	 public static ArrayList<DataPoint> trainSet  = new ArrayList<DataPoint>();
	public static ArrayList<DataPoint> testSet = new ArrayList<DataPoint>();
	public static ArrayList<String> category = new ArrayList<String>();

    private static final long serialVersionUID = 1L;
    private int labelPadding = 40;
    private Color lineColor = new Color(255, 255, 254);

    // TODO: Add point colors for each type of data point
    private Color pointColor = new Color(255, 0, 255);
    private Color blue = new Color(0, 0, 255);
    private Color cyan = new Color(0, 255, 255);
    private Color yellow = new Color(255, 255, 0);
    private Color red = new Color(255, 0, 0);

    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);

    // TODO: Change point width as needed
    private static int pointWidth = 10;

    // Number of grids and the padding width
    private int numXGridLines = 6;
    private int numYGridLines = 6;
    private int padding = 40;

    private List<DataPoint> data;

    // TODO: Add a private KNNModel variable
    private KNNModel kMOD;
    	
	/**
	 * Constructor method
	 */
    public Graph(List<DataPoint> testData, List<DataPoint> trainData) {
        this.data = testData;
        // TODO: instantiate a KNNModel variable
        kMOD = new KNNModel(7);
        kMOD.train((ArrayList<DataPoint>) trainData);
         //TODO: Run train with the trainData
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double minF1 = getMinF1Data();
        double maxF1 = getMaxF1Data();
        double minF2 = getMinF2Data();
        double maxF2 = getMaxF2Data();

        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - 
        		labelPadding, getHeight() - 2 * padding - labelPadding);
        g2.setColor(Color.BLUE);

        double yGridRatio = (maxF2 - minF2) / numYGridLines;
        for (int i = 0; i < numYGridLines + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2 -
            		labelPadding)) / numYGridLines + padding + labelPadding);
            int y1 = y0;
            if (data.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = String.format("%.2f", (minF2 + (i * yGridRatio)));
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 6, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        double xGridRatio = (maxF1 - minF1) / numXGridLines;
        for (int i = 0; i < numXGridLines + 1; i++) {
            int y0 = getHeight() - padding - labelPadding;
            int y1 = y0 - pointWidth;
            int x0 = i * (getWidth() - padding * 2 - labelPadding) / (numXGridLines) + padding + labelPadding;
            int x1 = x0;
            if (data.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                g2.setColor(Color.BLACK);
                String xLabel = String.format("%.2f", (minF1 + (i * xGridRatio)));
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(xLabel);
                g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // Draw the main axis
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() -
        		padding, getHeight() - padding - labelPadding);

        // Draw the points
        paintPoints(g2, minF1, maxF1, minF2, maxF2);
    }

    private void paintPoints(Graphics2D g2, double minF1, double maxF1, double minF2, double maxF2) {
        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        double xScale = ((double) getWidth() - (3 * padding) - labelPadding) /(maxF1 - minF1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (maxF2 - minF2);
        g2.setStroke(oldStroke);
        for (int i = 0; i < data.size(); i++) {
            int x1 = (int) ((data.get(i).getF1() - minF1) * xScale + padding + labelPadding);
            int y1 = (int) ((maxF2 - data.get(i).getF2()) * yScale + padding);
            int x = x1 - pointWidth / 2;
            int y = y1 - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
        
            // TODO: Depending on the type of data and how it is tested, change color here.
            // You need to test your data here using the model to obtain the test value 
            // and compare against the true label.
            
          //Category is the string arraylist that holds the testSet results from the test function
            //Both testSet and Category both have the same amount of elements
            
            ////FIND A WAY TO PICK ALL THE POINTS ONE AT A TIME 
            ///IF NOT, PUSH IT THROGH SOME SORT OF ARRAY
            
           
    			if(testSet.get(i).getLabel().equals("1")) {	
    				if(testSet.get(i).getLabel().equals(category.get(i))) {
    					//truePositive ++;	
    					g2 .setColor(blue);
    					g2.fillOval(x, y, ovalW, ovalH);
    				}else {
    					//falsePositive++;
    					g2.setColor(cyan);
    					g2.fillOval(x, y, ovalW, ovalH);
    				}
    			
    			}
    			else if(testSet.get(i).getLabel().equals("0")) {	
    				if(testSet.get(i).getLabel().equals(category.get(i))) {
    					//trueNegative ++;	
    					
    				
    					g2.setColor(red); 
    					g2.fillOval(x, y, ovalW, ovalH);	
    				}else {
    					//falseNegative++;
    					g2.setColor(yellow);
    					g2.fillOval(x, y, ovalW, ovalH);
    				}
    			
    		

    		}
            }
          /*

            if (i % 2 == 0) {

              g2.setColor(pointColor);
              g2.fillOval(x, y, ovalW, ovalH);

            } else {

              g2.setColor(red);
              g2.fillOval(x, y, ovalW, ovalH);

            }
            */
    		
            //g2.setColor(pointColor);
          //  g2.fillOval(x, y, ovalW, ovalH);
        
            
      }  
    

    /*
     * @Return the min values
     */
    private double getMinF1Data() {
        double minData = Double.MAX_VALUE;
        for (DataPoint pt : this.data) {
            minData = Math.min(minData, pt.getF1());
        }
        return minData;
    }

    private double getMinF2Data() {
        double minData = Double.MAX_VALUE;
        for (DataPoint pt : this.data) {
            minData = Math.min(minData, pt.getF2());
        }
        return minData;
    }


    /*
     * @Return the max values;
     */
    private double getMaxF1Data() {
        double maxData = Double.MIN_VALUE;
        for (DataPoint pt : this.data) {
            maxData = Math.max(maxData, pt.getF1());
        }
        return maxData;
    }

    private double getMaxF2Data() {
        double maxData = Double.MIN_VALUE;
        for (DataPoint pt : this.data) {
            maxData = Math.max(maxData, pt.getF2());
        }
        return maxData;
    }

    /* Mutator */
    public void setData(List<DataPoint> data) {
        this.data = data;
        invalidate();
        this.repaint();
    }

    /* Accessor */
    public List<DataPoint> getData() {
        return data;
    }

    /*  Run createAndShowGui in the main method, where we create the frame too and pack it in the panel*/
    private static void createAndShowGui(List<DataPoint> testData, List<DataPoint> trainData) {


	    /* Main panel */
        Graph mainPanel = new Graph(testData, trainData);

        // Feel free to change the size of the panel
        mainPanel.setPreferredSize(new Dimension(700, 600));

        /* creating the frame */
        JFrame frame = new JFrame("CS 112 Lab Part 3");
        
        JPanel panel = new JPanel();
        JLabel accL = new JLabel("Accuracy =" + String.valueOf(printAcc));
        JLabel preL = new JLabel("Precision =" + String.valueOf(printPre));
       
 		panel.add(accL);
 		panel.add(preL);
 		
 		frame.add(panel, BorderLayout.SOUTH);
 		
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    ///////////
    ///////////
    ///////////
    //Self added from PArt 2
    ///////////
    
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

      
    /* The main method runs createAndShowGui*/
    public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
      
            // TODO: Change the above logic retrieve the data from titanic.csv
            // Split the data to test and training
            
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
    		
    	
    		/////
    		/////
    		/////My Project requires KNNMODEL to be instaniated so it can run test() and fill my test array with elements
    		
    		//Instantiate a knnModel
    		KNNModel firstTestRun = new KNNModel(7);
    		
    		firstTestRun.train(dpList);
    		
    		firstTestRun.test(dpList);
    		trainSet = firstTestRun.trainSet;
    		testSet = firstTestRun.testSet;
    		category = firstTestRun.category;
    	

    		printAcc  = firstTestRun.getAccuracy(dpList);
    		printPre = firstTestRun.getPrecision(dpList);
    		
    		
    		
    		createAndShowGui(testSet, dpList);
    		
    		
    	
    		/*
    		SwingUtilities.invokeLater(
  		          new Runnable() { public void run() { initAndShowGUI(); } }
  		        );
    		*/
            // TODO: Pass in the testData and trainData separately 
            // Be careful with the order of the variables.
           // createAndShowGui(data, null);
    		
         }
      });
    }
}

