package datanalytics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;



public class Graph extends JPanel {

    private static final long serialVersionUID = 1L;
    private int labelPadding = 40;
    private Color lineColor = new Color(255, 255, 254);

    // TODO: Add point colors for each type of data point
    private Color pointColor = new Color(255, 0, 255);
    private Color red = new Color(255, 0, 0);
    private Color blue = new Color(0, 0, 255);
    private Color cyan = new Color(0, 255, 255);
    private Color yellow = new Color(255, 255, 0);

    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);

    // TODO: Change point width as needed
    private static int pointWidth = 10;

    // Number of grids and the padding width
    private int numXGridLines = 6;
    private int numYGridLines = 6;
    private int padding = 40;

    private ArrayList<DataPoint> data;

    // TODO: Add a private KNNPredictor variable
    private KNNPredictor predictor;
    static ArrayList<DataPoint> trainLabels = new ArrayList<DataPoint>();
    private double accuracy;
    private double precision;
	/**
	 * Constructor method
	 */
    public Graph(int K, String fileName) {
    	predictor = new KNNPredictor(K);
        this.data = (predictor.readData(fileName));
        for (DataPoint f : data) { 
			if (f.isTest == false) {
				trainLabels.add(f);
			}
		}
        
        int maxDataPoints = 20;
        // Max value of f1 and f2 that is arbitrarily set
        int maxF1 = 8;
        int maxF2 = 300;
        
        this.accuracy = predictor.getAccuracy(data, trainLabels);
        this.precision = predictor.getPrecision(data, trainLabels);
        // TODO: Remove the above logic where random data is generated
        // TODO: instantiate the KNNPredictor variable
        // TODO: Run readData using input filename to split the data to test and training
        // TODO: Set this.data as the output of readData
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
            Random rand = new Random();
    		double randNum = rand.nextDouble();
    		
            if(randNum < 0.9) {
    			data.get(i).isTest = false;
    		}
    		else {
    			data.get(i).isTest = true;
    		}
            if (data.get(i).isTest == true) { //if data is test
            	String label = predictor.test(data.get(i), trainLabels);
				if (label.equals("1") && data.get(i).label.equals("1.0")) {
					//truePositive++;
					g2.setColor(blue);
				}
				if (label.equals("1") && data.get(i).label.equals("0.0")) {
					//falsePositive++;
					g2.setColor(cyan);
				}
				if (label.equals("0") && data.get(i).label.equals("0.0")) {
					//trueNegative++;
					g2.setColor(yellow);
				}
				if (label.equals("0") && data.get(i).label.equals("1.0")) {
					//falseNegative++;
					g2.setColor(red);
				}
				g2.fillOval(x, y, ovalW, ovalH);
				
            }
            
        }
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
    public void setData(ArrayList<DataPoint> data) {
        this.data = data;
        invalidate();
        this.repaint();
    }

    /* Accessor */
    public List<DataPoint> getData() {
        return data;
    }
    
    /*  Run createAndShowGui in the main method, where we create the frame too and pack it in the panel*/
    private static void createAndShowGui(int K, String fileName) {
    	
        Graph mainPanel = new Graph(K, fileName);
        mainPanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println("Mouse was clicked at (" + e.getX() + "," + e.getY() + ")");
			}
		});
        mainPanel.setPreferredSize(new Dimension(700, 800));
        
        JPanel panel1 = new JPanel();
        panel1.add(new JLabel("Accuracy & Precision"));
        JButton b1 = new JButton(String.format("%.2f", mainPanel.accuracy));
        JButton b2 = new JButton(String.format("%.2f", mainPanel.precision));
        
        panel1.add(b1);
        panel1.add(b2);
        
        
        //creating the frame 
        JFrame frame = new JFrame("CS 112 Project Part 3");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
       
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.getContentPane().add(BorderLayout.NORTH, panel1);

		
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        
        System.out.println(mainPanel.accuracy);
        System.out.println(mainPanel.precision);
    
    }
    
    /* The main method runs createAndShowGui*/
    public static void main(String[] args) {
        int K = 5; // A value of K selected    
        String fileName = "titanic.csv"; // TODO: Change this to titanic.csv
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui(K, fileName);
                
            }
        });
    }
}
