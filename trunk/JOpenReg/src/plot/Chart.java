package plot;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Chart {

	private JFreeChart chart;
	private XYSeriesCollection data;
	private XYSeries series;
	
	
    public Chart(double[][] tableValues) {

    	
    	series = new XYSeries("Werte");
        for (int i = 0; i < tableValues[0].length; i++) {
			series.add(tableValues[0][i], tableValues[1][i]);
		}
        data = new XYSeriesCollection(series);
        chart = ChartFactory.createScatterPlot(
        	"Schaubild",
            "X", 
            "Y", 
            data,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );       
    }
	
    public JFreeChart getChart() {
    	return chart;
    }
    
    public void removeData() {
    	data.removeAllSeries();
    }
    
    public void addSeries(double[][] tableValues) {
    	series = new XYSeries("Werte");
        for (int i = 0; i < tableValues[0].length; i++) {
			series.add(tableValues[0][i], tableValues[1][i]);
		}
    	data.addSeries(series);
    }
}
