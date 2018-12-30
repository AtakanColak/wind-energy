import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.openstreetmap.gui.jmapviewer.JMapViewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import Data.Chart;
import Data.PanelData;
	import Data.WindData;

public class Try_window {
	public static DashboardClientWindow dialog = new DashboardClientWindow();
	public static PanelData panel = new PanelData(dialog.panel, dialog.panel_map, dialog.panel_graph1,
			                               dialog.panel_graph2, dialog.textField1,
			                               dialog.textField2, dialog.comboBox1, dialog.comboBox2, dialog.comboBox3, dialog.button1);
	public static int end = Integer.parseInt(panel.year_select.getSelectedItem().toString());
//	public static int end1 = end-1996;
	public static void try_window() {

		dialog.panelData = panel;
		dialog.setTitle("WindEnergy DashBoard Client Interface");
		JFreeChart chart1 = Chart.create_chart(new DefaultCategoryDataset());
		JFreeChart chart2 = Chart.createChart(Chart.pieDataset());
		Dimension dimension1 = new Dimension(
				                                    dialog.panel_graph1.getWidth(),
				                                    dialog.panel_graph1.getHeight());
		Dimension dimension2 = new Dimension(
				                                    dialog.panel_graph2.getWidth(),
				                                    dialog.panel_graph2.getHeight());
		ChartPanel chartPanel1 = Chart.create_chart_panel(chart1, dimension1);
		ChartPanel chartPanel2 = Chart.create_chart_panel(chart2, dimension2);
		dialog.panel_graph1.setLayout(new BorderLayout());
		dialog.panel_graph2.setLayout(new GridLayout());

		dialog.panel_graph1.add(chartPanel1);

		JPanel layeredPane = new JPanel(true){
			@Override
			public boolean isOptimizedDrawingEnabled() {
				return true;
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
		dialog.panel_graph2.add(layeredPane);

//		JButton CloseButton = new JButton();
//		CloseButton.setIcon(new ImageIcon("files/icons8_Close_Window_32.png"));
//		CloseButton.setBounds(1000-32, 0, 32,32);
//		dialog.getContentPane().setLayout(null);
//		dialog.getContentPane().add(CloseButton);

		JMapViewer map = new WindEnergyJMapViewer(panel, dimension1);
		JPanel map_viewer = (JPanel) map;
		dialog.panel_map.setLayout(new BorderLayout());
		dialog.panel_map.add(map_viewer);
		dialog.pack();
		dialog.setSize(1000, 1000);
		dialog.setLocationRelativeTo(null);
//		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2;
//		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2;
//		dialog.setLocation();
		dialog.setVisible(true);
//		System.exit(0);
	}

}
