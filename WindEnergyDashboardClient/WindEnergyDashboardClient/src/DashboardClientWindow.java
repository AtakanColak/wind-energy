import Data.PanelData;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Data.Chart;
//import Data.Chart;
//import Network.OperateUser;


public class DashboardClientWindow extends JFrame {

	private JPanel contentPane;
	public JPanel panel_map;
	public JPanel panel_graph1;
	public JPanel panel_graph2;
	public JPanel panel;
	public JTextField textField1;
	public JTextField textField2;
	public JButton button1;
	private JLabel Longitude;
	private JLabel Latitude;
	private JLabel Startyear;
	private JLabel Endyear;
	public JComboBox comboBox1;
	public JComboBox comboBox2;
	public JComboBox comboBox3;
	private JLabel Selectedyear;

	public PanelData panelData;
//	private JButton button2;

	public DashboardClientWindow() {
		button1.setText("ExcelExport");
		textField1.setEditable(false);
		textField2.setEditable(false);
		Longitude.setLayout(new BorderLayout());
		Longitude.setFont(new Font("NotoSansCoptic", Font.PLAIN, 15));
		Longitude.setForeground(Color.WHITE);
		Latitude.setLayout(new BorderLayout());
		Latitude.setFont(new Font("NotoSansCoptic", Font.PLAIN, 15));
		Latitude.setForeground(Color.WHITE);
		Startyear.setLayout(new BorderLayout());
		Startyear.setFont(new Font("NotoSansCoptic", Font.PLAIN, 15));
		Startyear.setForeground(Color.WHITE);
		Endyear.setLayout(new BorderLayout());
		Endyear.setFont(new Font("NotoSansCoptic", Font.PLAIN, 15));
		Endyear.setForeground(Color.WHITE);
		Selectedyear.setLayout(new BorderLayout());
		Selectedyear.setFont(new Font("NotoSansCoptic", Font.PLAIN, 15));
		Selectedyear.setForeground(Color.WHITE);
		textField1.setLayout(new BorderLayout());
		textField2.setLayout(new BorderLayout());
		button1.setLayout(new BorderLayout());
		button1.setIcon(new ImageIcon("files/icons8_Export_32.png"));




		for (int i = 0; i < 6; i++) {
			comboBox1.addItem( (2012+i)+"" );
			comboBox2.addItem( (2013+i)+"" );
			comboBox3.addItem( (2012+i)+"" );
		}


		ItemListener itemListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(ItemEvent.SELECTED == e.getStateChange()){
					String Lat = textField1.getText();
					Double LatV = Double.parseDouble(Lat);
					String Long = textField2.getText();
					Double LongV = Double.parseDouble(Long);
					Integer startyear = Integer.parseInt(comboBox1.getSelectedItem().toString());
					System.out.println(startyear);
					Integer endyear = Integer.parseInt(comboBox2.getSelectedItem().toString());
					panel_graph1.remove(0);
					CategoryDataset dataset = Chart.bar_chart_dataset(panelData.current_wind_dataset,startyear,endyear);
					JFreeChart chart = Chart.create_chart(dataset);
					panel_graph1.add(Chart.create_chart_panel(chart,new Dimension(panel_graph1.getWidth(), panel_graph1.getHeight())));
					panel_graph1.getComponent(0).setSize(panel_graph1.getSize());
					panel_graph1.repaint();

				}

			}
		};


		ItemListener itemListener1 = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(ItemEvent.SELECTED == e.getStateChange()){

					String Lat = textField1.getText();
					Double LatV = Double.parseDouble(Lat);
					String Long = textField2.getText();
					Double LongV = Double.parseDouble(Long);
					String year1 = comboBox3.getSelectedItem().toString();
					Integer year2 = Integer.parseInt(year1);;
					ChartPanel chartPanel2 = Chart.create_chart_panel(
							Chart.createChart(Chart.pie_chart_dataset(panelData.current_wind_dataset,year2)),
							new Dimension(panel_graph2.getWidth(), panel_graph2.getHeight()));


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
					panel_graph2.removeAll();
					panel_graph2.add(layeredPane);
					layeredPane.revalidate();
				}
			}
		};


		comboBox1.addItemListener(itemListener);
		comboBox3.addItemListener(itemListener1);
		comboBox2.addItemListener(itemListener);


		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				ExcelExporter excel = new ExcelExporter();
//				String path = excel.chooseLocation();
//				excel.export(panelData.current_wind_dataset,path);
				ExporterWindow window = new ExporterWindow();
				window.setVisible(true);
			}
		});

		setContentPane(contentPane);
		setMinimumSize(new Dimension(1000,750));


//        setModal(true);

		// call onCancel() when cross is clicked
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				onCancel();
			}
		});

		// call onCancel() on ESCAPE
		contentPane.registerKeyboardAction(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCancel();
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	private void onOK() {
		// add your code here
		dispose();
	}

	private void onCancel() {
		// add your code here if necessary
		dispose();
	}


}
