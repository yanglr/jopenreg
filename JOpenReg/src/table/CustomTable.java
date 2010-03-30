package table;

import javax.swing.JOptionPane;
import javax.swing.JTable;

public class CustomTable extends JTable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CustomTable table;

	public CustomTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
	}

	public CustomTable() {

		Object[] options = {
				"Testtabelle",
				"Neue Tabelle",
				"Tabelle laden"
		};
		int response = JOptionPane.showOptionDialog(
				null, 
				"Neue Tabelle erstelen", 
				"Tabelle", 
				0, 
				JOptionPane.INFORMATION_MESSAGE, 
				null, 
				options, 
				options[0]);

		switch(response) {
		case 0:
			// testtable
			Object[][] rowData1 = {{1.0,45.0},{2.0,48.0},{3.0,53.0},{4.0,46.0},{5.0,41.0},{6.0,38.0},{7.0,35.0},{8.0,44.0},{9.0,50.0},{10.0,53.0},{11.0,55.0},{12.0,59.0}};
			Object[] columnNames1 = {"X", "Y"};
			table = new CustomTable( rowData1, columnNames1 );
			break;
		case 1:
			// empty table
			//TODO: check if input is an Integer
			int numRows = Integer.parseInt(JOptionPane.showInputDialog("Wie viele Spalten?"));
			Object[][] rowData2 = new Object[numRows][2];
			Object[] columnNames2 = {"X", "Y"};
			table = new CustomTable( rowData2, columnNames2 );
			break;
		case 2:
			//TODO: load table
		} 

	}

	public CustomTable getTable() {
		return this.table;
	}

	public String[] getVectorStrings() {
		// create Vectorstrings from the table
		String[] xVector = new String[this.getRowCount()];
		String[] yVector = new String[this.getRowCount()];
		for (int i = 0; i < table.getRowCount(); i++) {
			xVector[i]=table.getValueAt(i, 0).toString();
			yVector[i]=table.getValueAt(i, 1).toString();
		}

		String[] xy = new String[2];
		for (int i = 0; i < xVector.length; i++) {
			if(i<(xVector.length-1)) {
				xy[0] += xVector[i] + ",";
			} else {
				xy[0] += xVector[i];
			}

		}

		for (int i = 0; i < yVector.length; i++) {
			if(i<(yVector.length-1)) {
				xy[1] += yVector[i] + ",";
			} else {
				xy[1] += yVector[i];
			}

		}
		return xy;

	}

	public double[][] getTableValues() {

		double[][] tableValues = new double[2][this.getRowCount()];

		for (int i = 0; i < this.getRowCount(); i++) {
			if(this.getValueAt(i, 0) == null) {
				tableValues[0][i] = 0;
				tableValues[1][i] = 0;
			} else {
				tableValues[0][i]=Double.parseDouble(this.getValueAt(i, 0).toString());
				tableValues[1][i]=Double.parseDouble(this.getValueAt(i, 1).toString());
			}
		}
		return tableValues;

	}

	
}
