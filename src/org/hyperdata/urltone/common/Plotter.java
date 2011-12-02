/**
 * 
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

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Plotter extends JPanel {

	private static int WINDOW_WIDTH = 1000;

	private static int WINDOW_HEIGHT = 200;

	private List<Double> data = new ArrayList<Double>();

	private String title = "Plot";
	private String xLabel = "X";
	private String yLabel = "Y";

	final int PAD = 20; // border padding

	// will be calculated in doCalcs
	private double max = 0;
	private double min = 0;
	private double offset = 0;
	private double xStep = 0;
	private double scale = 0;

	private int pointSize = 2;

	/**
	 * @param string
	 * @param data2
	 */
	public Plotter(String title, List<Double> data) {
		this(title);
		setData(data);
	}

	public Plotter(String title) {
		super();
		setTitle(title);
	}

	public void setData(List<Double> data) {
		this.data = data;
	}

	/**
	 * @param xLabel
	 *            the xLabel to set
	 */
	public void setxLabel(String xLabel) {
		this.xLabel = xLabel;
	}

	/**
	 * @param yLabel
	 *            the yLabel to set
	 */
	public void setyLabel(String yLabel) {
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
			double y = getHeight() - PAD - scale * (data.get(i) + offset / 2);
			g2.setPaint(Color.red);
			g2.fill(new Ellipse2D.Double(x -  pointSize/2, y -  pointSize/2,  pointSize,  pointSize)); // point?
			//System.out.println(data.get(i)+"   x="+x+"   y="+y);
			if (i > 0) {
				g2.setPaint(Color.green);
				g2.draw(new Line2D.Double(previousX, previousY, x, y));
			}
			previousX = x;
			previousY = y;
		}
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
		float sy = PAD + ((getHeight() - 2 * PAD) - yLabel.length() * sh) / 2
				+ lm.getAscent();
		for (int i = 0; i < yLabel.length(); i++) {
			String letter = String.valueOf(yLabel.charAt(i));
			float sw = (float) font.getStringBounds(letter, frc).getWidth();
			float sx = (PAD - sw) / 2;
			g2.drawString(letter, sx, sy);
			sy += sh;
		}
		// Y max label
		String s = String.valueOf(this.max);
		float sw = (float) font.getStringBounds(s, frc).getWidth();
		float sx = PAD - sw - 2;

		sy = (float) (PAD - lm.getAscent() / 2);
		g2.drawString(s, sx, sy);

		// X-Axis
		sy = getHeight() - PAD + (PAD - sh) / 2 + lm.getAscent();
		sw = (float) font.getStringBounds(xLabel, frc).getWidth();
		sx = (getWidth() - sw) / 2;
		g2.drawString(xLabel, sx, sy);

		// max X value label
		String xValue = String.valueOf(data.size() - 1);
		sw = (float) font.getStringBounds(xValue, frc).getWidth();
		g2.drawString(xValue, getWidth() - sw, sy);
	}

	private void doCalcs() {
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i) > this.max)
				this.max = data.get(i);
			if (data.get(i) < this.min)
				this.min = data.get(i);
		}
		this.offset = max - min;

		this.xStep = (double) (getWidth() - 2 * PAD) / (data.size() - 1);
		this.scale = (double) (getHeight() - 2 * PAD) / offset;
	}

	public static void main(String[] args) {
		List<Double> data = new ArrayList<Double>();
		int nPoints = 20;
		for (int i = 0; i < nPoints; i++) {
			double x = i * 2 * Math.PI / nPoints;
			double y = Math.sin(x);
			// System.out.println(x + "  " + y);
			data.add(y);
		}
		// List<Double> data = Arrays.asList(-0.9, -0.8, -0.7, -0.6, -0.5,
		// -0.4, -0.3, -0., -0.54, -0.77, 0.61, 0.55, 0.48, 0.60,
		// 0.49, 0.36, 0.38, 0.27, 0.20, 0.18);

		Plotter.plot("This", data);
	}

	public static void plot(String title, List<Double> data) {
		plot(title,data,8);
	}
	
	public static void plot(String title, List<Double> data, int pointSize) {
		Plotter plotter = new Plotter(title, data);
		plotter.setPointSize(pointSize);
		JFrame f = new JFrame(plotter.getTitle());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(plotter);
		f.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		f.setLocation(200, 200);
		f.setVisible(true);
	}

	/**
	 * @param pointSize
	 */
	public void setPointSize(int pointSize) {
		this.pointSize  = pointSize;
	}
}