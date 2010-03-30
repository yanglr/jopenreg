package maple;

import table.CustomTable;

import com.maplesoft.externalcall.MapleException;
import com.maplesoft.openmaple.Engine;
import com.maplesoft.openmaple.EngineCallBacksDefault;

public class MapleEngine {


	private Engine engine;
	private boolean isCalculated;
	private String lastMethod;

	/**
	 * opens a connection to Maple
	 */
	public MapleEngine() {
		isCalculated = false;
		String a[];
		a = new String[1];
		a[0] = "java";
		try {
			engine = new Engine( a, new EngineCallBacksDefault(), null, null );
		} catch (MapleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void executeExp(String exp) {

		try {
			engine.evaluate(exp);
		} catch (MapleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String doReggression(CustomTable table, Methods methods) {

		lastMethod = methods.toString();

		String x = table.getVectorStrings()[0];
		String y = table.getVectorStrings()[1];

		String regressionCurve = "<html>\n<body>\n";	
		try {
			engine.evaluate("with(CurveFitting):");
			engine.evaluate("xVector:=Vector(["+ x + "]):");
			engine.evaluate("yVector:=Vector(["+ y + "]):");

			switch(methods) {
			case LEASTSQUARES:
				engine.evaluate("R:=LeastSquares(xVector,yVector,x):");
				regressionCurve += engine.evaluate("eval(R):").toString();
				break;
			case SPLINE:
				engine.evaluate("R:=Spline(xVector,yVector,x,degree=1):");
				String[] parts;
				parts = engine.evaluate("eval(R):").toString().split("\\(");

				parts = parts[1].split(",");
				for (int i = 0; i < parts.length; i++) {
					regressionCurve += parts[i];
					if(i%2 != 0) {
						regressionCurve += "<br>\n";
					} else {
						regressionCurve += " : ";
					}
				}
				regressionCurve = regressionCurve.replace(" < ", "&lt;");
				regressionCurve = regressionCurve.replace(") :", " sonst");

			}

		} catch (MapleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		isCalculated = true;
		regressionCurve += "</body></html>";
		return regressionCurve;		
	}

	public String doEval(CustomTable table, Methods methods, double value) {
		String calculatedValue = null;
		if(!isCalculated || (lastMethod != methods.toString())) {
			doReggression(table, methods);
		}
		try {
			calculatedValue = engine.evaluate("eval(R,x=" + value + "):").toString();
		} catch (MapleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calculatedValue;
	}

	public CustomTable getCalculatedTable(CustomTable table, Methods methods) {
		Double[][] rowData;
		Object[] columnNames = {"X", "Y"};
		for (int i = 0; i < table.getTable().getTableValues()[0].length; i++) {
			rowData[0][i] = table.getTable().getTableValues()[0][i];
			rowData[1][i] = Double.parseDouble(doEval(table, methods, table.getTable().getTableValues()[0][i]));
		}				
		return new CustomTable(rowData, columnNames);
	}
}