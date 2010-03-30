package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import org.jfree.chart.ChartPanel;

import plot.Chart;

import maple.MapleEngine;
import maple.Methods;
import table.CustomTable;


/**
 * 
 * @author Thomas Pfister
 *
 * 28.01.2010
 * 
 * The main Window of the application
 *
 */
public class MainWindow extends JFrame implements ActionListener {

	/**
	 * default serialVersionUID
	 * TODO generate serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private MapleEngine engine;

	private CustomTable table;

	private Chart chart;

	private JButton btRegression;
	private JButton btEval;
	private JButton refresh;
	private JButton btTest;

	private JLabel lbFunction;
	private JLabel lbEval;

	private JScrollPane scrollPane;

	private JTextField txtValue;

	private JRadioButton rdLeastSquares;
	private JRadioButton rdSpline;
	/**
	 * the constructor initializes the main window
	 * 
	 * @param title The title of the window
	 */
	public MainWindow(String title) {
		super(title);
		this.initWindow();		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * initialize the main window with its components
	 */
	protected void initWindow() {

		// Connect to Maple
		//		engine = new MapleEngine();

		createToolBar();
		
		// creates the Menu
		new Menu(this);


		
		// create table
		table = new CustomTable();

		JPanel pnLeft = new JPanel(new GridLayout());
		scrollPane = new JScrollPane(table.getTable());
		table.getTable().setFillsViewportHeight(true);
		pnLeft.add(scrollPane);

		chart = new Chart(table.getTable().getTableValues());
		ChartPanel chartPanel = new ChartPanel(chart.getChart());

		JPanel pnRightLower = new JPanel(new GridBagLayout());		
		GridBagConstraints c = new GridBagConstraints();

		rdLeastSquares = new JRadioButton("LeastSquares");
		rdLeastSquares.setActionCommand("LEASTSQUARES");
		rdLeastSquares.addActionListener(this);
		rdLeastSquares.setSelected(true);	
		rdSpline = new JRadioButton("Spline");
		rdLeastSquares.setActionCommand("SPLINE");
		rdSpline.addActionListener(this);
		pnRightLower.add(rdLeastSquares,c);
		pnRightLower.add(rdSpline,c);

		ButtonGroup group = new ButtonGroup();
		group.add(rdLeastSquares);
		group.add(rdSpline);

		btRegression = new JButton("Regressionsgerade berechnen");
		btRegression.addActionListener(this);
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 0;
		c.gridy = 1;
		pnRightLower.add(btRegression,c);

		txtValue = new JTextField(5);
		txtValue.setText("5");
		c.gridx = 1;
		c.gridy = 2;
		pnRightLower.add(txtValue,c);

		btEval = new JButton("Wert berechnen");
		btEval.addActionListener(this);
		c.gridx = 0;
		c.gridy = 2;
		pnRightLower.add(btEval,c);


		lbFunction = new JLabel("Regressionsgerade: ");
		c.gridx = 2;
		c.gridy = 1;
		pnRightLower.add(lbFunction,c);

		lbEval = new JLabel("Wert: ");
		c.gridx = 2;
		c.gridy = 2;
		pnRightLower.add(lbEval,c);

		btTest = new JButton("Test");
		btTest.addActionListener(this);
		pnRightLower.add(btTest);

		JSplitPane rightVerticalSplit  = new JSplitPane(JSplitPane.VERTICAL_SPLIT,chartPanel,pnRightLower);
		rightVerticalSplit.setDividerLocation(400);

		JSplitPane horizontalSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,pnLeft,rightVerticalSplit);

		horizontalSplit.setOneTouchExpandable(true);
		horizontalSplit.setDividerLocation(200);



		this.add(horizontalSplit);



		// size
		// fullscreen
		//		this.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));

		// Window mode
		this.setSize(1024, 640);


		//pack();
		setVisible(true);


	}

	/**
	 * starts the program
	 * @param args
	 */
	public static void main(String[] args) {
		new MainWindow("Titel");
	}

	public void createToolBar() {
		refresh = new JButton("Aktualisieren");
		refresh.addActionListener(this);
		JToolBar toolbar = new JToolBar();
		toolbar.add(refresh);
		add(toolbar, BorderLayout.BEFORE_FIRST_LINE);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		Object button = e.getSource();


		Methods method = null;
		if(rdLeastSquares.isSelected()) {
			method = Methods.LEASTSQUARES;
		}
		if(rdSpline.isSelected()) {
			method = Methods.SPLINE;
		}

		if(button == btRegression) {
			String regressionCurve = engine.doReggression(table.getTable(), method);	
			lbFunction.setText(regressionCurve);	
		}

		if(button == btEval) {
			String value = engine.doEval(table.getTable(), method, Double.parseDouble(txtValue.getText()));
			lbEval.setText(value);
		}

		if(button == btTest) {
			// get TableValues
			//			double [][] tableValues = table.getTable().getTableValues();
			//			for (int i = 0; i < tableValues[0].length; i++) {
			//				System.out.println(tableValues[0][i] + " | " + tableValues[1][i]);
			//			}



		}

		if(button == refresh) {
			// repaint chart with new data
			chart.removeData();
			chart.addSeries(table.getTable().getTableValues());
		}

	}
}
