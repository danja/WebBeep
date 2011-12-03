/**
 * Simple line chart plotter
 * 
 * integer (discrete time) steps on X-axis
 * +/- double values on Y-axis
 */
package org.hyperdata.urltone.common;

/**
 * @author danny
 *
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Plotter extends JPanel {

	/**
	 * @param drawLines
	 *            the drawLines to set
	 */
	public void setDrawLines(boolean drawLines) {
		this.drawLines = drawLines;
	}

	/**
	 * @return the data
	 */
	public List<Double> getData() {
		return this.data;
	}

	private int windowWidth = 1000;
	private int windowHeight = 200;

	private int screenX = 200;
	private int screenY = 200;

	private List<Double> data = new ArrayList<Double>();

	private String title = "Plot";
	private String xLabel = "X";
	private String yLabel = "Y";

	final int PAD = 20; // border padding

	// will be calculated in doCalcs
	private double max;
	private double min;
	private double offset = 0;
	private double xStep = 0;
	private double scale = 0;
	private int pointSize = 2;
	private boolean drawLines = false;

	/**
	 * @return the windowWidth
	 */
	public int getWindowWidth() {
		return this.windowWidth;
	}

	/**
	 * @return the windowHeight
	 */
	public int getWindowHeight() {
		return this.windowHeight;
	}

	/**
	 * @return the screenX
	 */
	public int getScreenX() {
		return this.screenX;
	}

	/**
	 * @return the screenY
	 */
	public int getScreenY() {
		return this.screenY;
	}

	/**
	 * @param windowWidth
	 *            the windowWidth to set
	 */
	public void setWindowWidth(int windowWidth) {
		this.windowWidth = windowWidth;
	}

	/**
	 * @param windowHeight
	 *            the windowHeight to set
	 */
	public void setWindowHeight(int windowHeight) {
		this.windowHeight = windowHeight;
	}

	/**
	 * @param screenX
	 *            the screenX to set
	 */
	public void setScreenX(int screenX) {
		this.screenX = screenX;
	}

	/**
	 * @param screenY
	 *            the screenY to set
	 */
	public void setScreenY(int screenY) {
		this.screenY = screenY;
	}

	public void setData(List<Double> data) {
		this.data = data;
	}

	public Plotter() {
		super();
	}

	public Plotter(List<Double> data) {
		this();
		setData(data);
	}

	/**
	 * @param string
	 * @param data2
	 */
	public Plotter(List<Double> data, String title) {
		this(data);
		setTitle(title);
	}

	/**
	 * @param string
	 * @param data2
	 */
	public Plotter(List<Double> data, String title, int pointSize) {
		this(data, title);
		setPointSize(pointSize);
	}

	/**
	 * @param string
	 * @param data2
	 */
	public Plotter(List<Double> data, String title, int pointSize,
			boolean drawLines) {
		this(data, title, pointSize);
		setDrawLines(drawLines);
	}

	/**
	 * @param xLabel
	 *            the xLabel to set
	 */
	public void setXLabel(String xLabel) {
		this.xLabel = xLabel;
	}

	/**
	 * @param yLabel
	 *            the yLabel to set
	 */
	public void setYLabel(String yLabel) {
		this.yLabel = yLabel;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * @param pointSize
	 */
	public void setPointSize(int pointSize) {
		this.pointSize = pointSize;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		doCalcs();

		drawAxes(g2);
		drawLabels(g2);

		// draw points/lines
		double previousX = 0;
		double previousY = 0;

		for (int i = 0; i < data.size(); i++) {
			double x = PAD + i * xStep;
			double y = getHeight()/2 - scale * (data.get(i) - offset); // - offset
			// System.out.println(data.get(i)+"   x="+x+"   y="+y);
			if (drawLines && i > 0) {
				g2.setPaint(Color.blue);
				g2.draw(new Line2D.Double(previousX, previousY, x, y));
			}
			g2.setPaint(Color.green);
			g2.fill(new Ellipse2D.Double(x - pointSize / 2, y - pointSize / 2,
					pointSize, pointSize)); // point?
			previousX = x;
			previousY = y;
		}
	}
	
	private void doCalcs() {
		this.max = data.get(0);
		this.min = data.get(0);
		for (int i = 1; i < data.size(); i++) {
			if (data.get(i) > this.max)
				this.max = data.get(i);
			if (data.get(i) < this.min)
				this.min = data.get(i);
		}
		this.offset = (max+min)/2;
		this.xStep = (double) (getWidth() - 2 * PAD) / (data.size() - 1);
		this.scale = (double) (getHeight())/(max-min);
	}

	/**
	 * @param g2
	 */
	private void drawAxes(Graphics2D g2) {
		// Draw Y-Axis
		g2.draw(new Line2D.Double(PAD, PAD, PAD, getHeight() - PAD));
		// Draw X-Axis
		g2.draw(new Line2D.Double(PAD, getHeight() / 2, getWidth() - PAD,
				getHeight() / 2));
	}

	private void drawLabels(Graphics2D g2) {
		Font font = g2.getFont();
		FontRenderContext frc = g2.getFontRenderContext();
		LineMetrics lm = font.getLineMetrics("0", frc);
		float sh = lm.getAscent() + lm.getDescent();

		// Y-Axis
		float labelY = PAD + ((getHeight() - 2 * PAD) - yLabel.length() * sh)
				/ 2 + lm.getAscent();
		for (int i = 0; i < yLabel.length(); i++) {
			String letter = String.valueOf(yLabel.charAt(i));
			float sw = (float) font.getStringBounds(letter, frc).getWidth();
			float sx = (PAD - sw) / 2;
			g2.drawString(letter, sx, labelY);
			labelY += sh;
		}
		// Y max label
		String maxLabel = String.valueOf(this.max);
		labelY = (float) (PAD - lm.getAscent() / 2);
		g2.drawString(maxLabel, PAD, labelY);

		// Y min label
		String minLabel = String.valueOf(this.min);
		labelY = (float) (PAD - lm.getAscent() / 2);
		g2.drawString(minLabel, PAD, getHeight() - labelY);

		// X-Axis
		labelY = getHeight() - PAD + (PAD - sh) / 2 + lm.getAscent();
		float sw = (float) font.getStringBounds(xLabel, frc).getWidth();
		float sx = (getWidth() - sw) / 2;
		g2.drawString(xLabel, sx, labelY);

		// max X value label
		String xValue = String.valueOf(data.size());
		sw = (float) font.getStringBounds(xValue, frc).getWidth();
		g2.drawString(xValue, getWidth() - sw - PAD, labelY);
	}

	public static void plot(List<Double> data) {
		Plotter plotter = new Plotter(data);
		makeFrame(plotter);
	}

	public static void plot(List<Double> data, String title) {
		Plotter plotter = new Plotter(data, title);
		makeFrame(plotter);
	}

	public static void plot(List<Double> data, String title, int pointSize) {
		Plotter plotter = new Plotter(data, title, pointSize);
		makeFrame(plotter);
	}

	public static void plot(List<Double> data, String title, int pointSize,
			boolean drawLines) {
		Plotter plotter = new Plotter(data, title, pointSize, drawLines);
		makeFrame(plotter);
	}

	private static void makeFrame(Plotter plotter) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(plotter);
		f.setSize(plotter.getWindowWidth(), plotter.getWindowHeight());
		f.setLocation(plotter.getScreenX(), plotter.getScreenY());
		f.setTitle(plotter.getTitle() + "   ("
				+ plotter.getData().size()+" samples)");
		f.setVisible(true);
	}

	public static void main(String[] args) {
		List<Double> data = new ArrayList<Double>();
		int nPoints = 20;
		for (int i = 0; i < nPoints; i++) {
			double x = i * 2 * Math.PI / nPoints;
			double y = Math.sin(x)/3+1;
			// System.out.println(x + "  " + y);
			data.add(y);
		}
		Plotter.plot(data, "Sine Wave", 8, true);
	}
}