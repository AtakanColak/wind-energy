import Data.*;
import Network.OperateUser;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.openstreetmap.gui.jmapviewer.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;


import static java.lang.Math.round;

/**
 * Created by atakan on 02/12/17.
 */
public class WindEnergyJMapViewer extends JMapViewer implements MouseListener {

	private double[][][] wind_data;
	public static PanelData panel;
	private Dimension dimension1;
	public static OperateUser operator;

	public WindEnergyJMapViewer(PanelData p, Dimension d) {
		this.addMouseListener(this);
		panel = p;
		dimension1 = d;
		operator = new OperateUser();
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		Point clicked_point = mouseEvent.getPoint();
		Coordinate coordinate = (Coordinate) this.getPosition(clicked_point);
		double tx = round_lower(coordinate.getLat());
		double ty = round_lower(coordinate.getLon());
		panel.coordinate1.setText(String.valueOf(tx));
		panel.coordinate2.setText(String.valueOf(ty));
		add_tile(Color.WHITE, coordinate, 150);
		if (ty < 0) ty += 360;
		List<WindData> timeline = operator.SelectData(tx,ty);

//        ExcelExporter excel = new ExcelExporter();
//        String path = excel.chooseLocation();
//        excel.export(timeline,path);
		panel.current_wind_dataset = timeline;
		Integer year_start = Integer.parseInt(panel.year_start.getSelectedItem().toString());
		Integer year_end = Integer.parseInt(panel.year_end.getSelectedItem().toString());
		CategoryDataset dataset = Chart.bar_chart_dataset(timeline,year_start,year_end);
		JFreeChart chart = Chart.create_chart(dataset);
		panel.panel_graph1.remove(0);
		panel.panel_graph1.add(Chart.create_chart_panel(chart,dimension1));
		panel.panel_graph1.getComponent(0).setSize(panel.panel_graph1.getSize());
		panel.panel_graph1.repaint();

		Integer select_year = Integer.parseInt(panel.year_select.getSelectedItem().toString());
		ChartPanel chartPanel2 = Chart.create_chart_panel(
				Chart.createChart(Chart.pie_chart_dataset(timeline,select_year)),
				new Dimension(panel.panel_graph2.getWidth(), panel.panel_graph2.getHeight()));


		System.out.println(chartPanel2);
		JPanel layeredPane = new JPanel(true){
			@Override
			public boolean isOptimizedDrawingEnabled() {
				return false;
			}
		};
		layeredPane.setLayout(new OverlayLayout(layeredPane));
		JPanel graphLabelPanel = new JPanel();
		graphLabelPanel.setLayout(new GridLayout(2, 1));
		graphLabelPanel.setOpaque(false);
		JPanel comp = new JPanel();
		comp.setBackground(Color.WHITE);
		JLabel text = new JLabel("text");
		text.setText("<html><center> <br> Up to the end of the selected year <br><center>  the chosen tile experienced <br><center> average wind speeds as shown above </html>");

		//text.setText("<html><center> <br> Up to the end of Q2 <br><center>  Ireland experienced below <br><center>  average wind speeds </html>");
		text.setForeground(new Color(238,118,36));
		text.setFont(new Font("NotoSansCoptic", Font.PLAIN, 20));
		comp.add(text);
		JPanel glass = new JPanel();
		glass.setOpaque(false);//透明的, it will show the covered panel, so named glass
		graphLabelPanel.add(glass);
		graphLabelPanel.add(comp);
		layeredPane.add(graphLabelPanel);
		layeredPane.add(chartPanel2);
		panel.panel_graph2.removeAll();
		panel.panel_graph2.add(layeredPane);
		layeredPane.revalidate();

	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {

	}

	private static Coordinate c(double lat, double lon) {
		return new Coordinate(lat, lon);
	}

	private static double round_lower(double val) {
		double lower =  (double) round(val - 0.25);
		if (lower + 0.25 < val) return lower + 0.5;
		else return lower;
	}
	private static double decide_rounding(double val) {
		double lower = round_lower(val);
		if (val < lower + 0.25) return lower - 0.25;
		else					return lower + 0.25;
	}

	private static Color transparent_color(Color color, int transparency) { return new Color(color.getRed(),color.getGreen(),color.getBlue(),255-transparency); }

	private void add_tile(Color color, Coordinate location, int transparency) {
		double tx = decide_rounding(location.getLat());
		double ty = decide_rounding(location.getLon());
		double size = 0.5;
		MapPolygonImpl tile = new MapPolygonImpl(c(tx,ty),c(tx,ty + size),c(tx + size,ty + size),c(tx + size,ty));
		tile.setName("(" + round_lower(location.getLat()) + "," + round_lower(location.getLon()) + ")");

		tile.setStyle(tile_style(color,transparency));
		this.removeAllMapPolygons();
		this.addMapPolygon(tile);
	}

	private Style tile_style(Color color, int transparency) {
		Style tile_style = new Style();
		tile_style.setBackColor(transparent_color(color,transparency));
		tile_style.setFont(tile_font());
		tile_style.setStroke(new BasicStroke());
		return tile_style;
	}

	private Font tile_font() {
		Font myFont = new Font("serif", Font.ROMAN_BASELINE, 19);
		myFont.deriveFont(Font.LAYOUT_RIGHT_TO_LEFT,12f);
		return myFont;
	}

//    public Font getFont(float size) {
//        String pathString = RandImgnumUtil.class.getClassLoader().getResource("").getFile();
//        try {
//            Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, new File(pathString));
//            dynamicFont = dynamicFont.deriveFont(size);
//            return dynamicFont;
//        } catch (FontFormatException ex) {
//            Logger.getLogger(RandImgnumUtil.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(RandImgnumUtil.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return null;
//    }
}
