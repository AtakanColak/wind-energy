//import com.sun.java.util.jar.pack.Attribute.Layout;

import java.awt.Color;
import java.awt.Component;
import Data.PanelData;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ExporterWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private  ExporterWindow exporterWindow = this;
	//coordinates to move the JFrame
	int xOld = 0;
	int yOld = 0;

	public ExporterWindow() {
		this.setLayout(null);

		//mouse click the coordinate to start move event
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xOld = e.getX();
				yOld = e.getY();
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int xOnScreen = e.getXOnScreen();
				int yOnScreen = e.getYOnScreen();
				int xx = xOnScreen - xOld;
				int yy = yOnScreen - yOld;
				ExporterWindow.this.setLocation(xx, yy);
			}
		});

		//JLayeredPane to get two added panel
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 500, 450);
		this.add(layeredPane);

		//create backgroundPanel
		JPanel bgPanel = new JPanel();
		bgPanel.setBounds(0, 0, 741, 591);
		bgPanel.setBackground(Color.WHITE);


		//add image to backgroundPanel
//		JLabel bgLabel = new JLabel(new ImageIcon("files/LR-logo.jpg"));
//		bgPanel.add(bgLabel);

		//mainPanel
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 500, 400);
		mainPanel.setLayout(null);


		//JRadiobutton
		JRadioButton email = new JRadioButton("Email");
		JRadioButton save_file = new JRadioButton("Save File");
		email.setFont(new Font("Arial",Font.PLAIN,16));
		save_file.setFont(new Font("Arial",Font.PLAIN,16));
		email.setBounds(100,75,100,50);
		save_file.setBounds(300,75,100,50);
		email.setBackground(Color.WHITE);
		save_file.setBackground(Color.WHITE);



		//text
		JLabel title = new JLabel();
		title.setText("Export");
		title.setFont(new Font("Arial",Font.PLAIN,25));
		title.setForeground(Color.BLACK);
		title.setBounds(200,40,500,20);

		//Select one button
		ButtonGroup group = new ButtonGroup();
		group.add(email);
		group.add(save_file);

		//close button
		JButton closeButton = new JButton();
		closeButton.setIcon(new ImageIcon("files/icons8_Close_Window_32.png"));
		closeButton.setBounds(468,0,32,32);
		closeButton.setBackground(Color.WHITE);
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exporterWindow.dispatchEvent(new WindowEvent(exporterWindow, WindowEvent.WINDOW_CLOSING));
			}
		});

		//Itemlistener
		ItemListener itemListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getSource() == email){
					System.out.println("eamil selected");
					layeredPane.removeAll();
					exporterWindow.setBounds(exporterWindow.getX(),exporterWindow.getY(),500,450);
					for(int i=1;i<6;i++){
						JLabel label = new JLabel();
						JTextField textField = new JTextField();
//						label.setName("label"+ i);
						label.setBackground(Color.WHITE);
						label.setFont(new Font("Arial",Font.PLAIN,15));
						label.setBounds(90,130+(i-1)*40,110,30);
						textField.setBounds(240,130+(i-1)*40,200,30);
						if(i==1){
							label.setText("Email Address");
						}
						else if(i==2){
							label.setText("Email Password");
						}
						else if(i==3){
							label.setText("Send to");
						}
						else if(i==4){
							label.setText("Subject");
						}
						else {
							label.setText("Message");
							textField.setSize(200,100);
						}
						layeredPane.add(label);
						layeredPane.add(textField);
					}

					JButton button = new JButton();
					button.setBackground(Color.WHITE);
					button.setText("Email");
					button.setFont(new Font("Arial", Font.PLAIN,15));
					button.setBounds(200,410,100,30);


					layeredPane.setBackground(Color.WHITE);
					layeredPane.add(title);
					layeredPane.add(email);
					layeredPane.add(save_file);
					layeredPane.add(closeButton);
					layeredPane.add(button);
					layeredPane.add(bgPanel);
					layeredPane.add(mainPanel);
					layeredPane.revalidate();
				}

				else {
					System.out.println("Save file selected");
					layeredPane.removeAll();
					exporterWindow.setBounds(exporterWindow.getX(),exporterWindow.getY(),500,300);
//					JLabel label1 =new JLabel("<html><center>The File will be downloaded in the files location in the project folder </html>");
//					label1.setFont(new Font("Arial", Font.PLAIN,20));
//					label1.setBounds(60,250,400,100);
					JLabel label = new JLabel("File saved location");
					label.setBounds(60,140,150,40);
					label.setFont(new Font("Arial", Font.PLAIN,17));
					label.setForeground(Color.BLUE);
					label.setBackground(Color.WHITE);

					JTextField textField = new JTextField();
					textField.setBounds(230,140,240,40);
					textField.setFont(new Font("Arial",Font.PLAIN,10));

					JButton button1 = new JButton();
					button1.setBackground(Color.WHITE);
					button1.setText("Choose file location");
					button1.setFont(new Font("Arial",Font.PLAIN,15));
					button1.setBounds(60,210,165,50);
					button1.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							ExcelExporter excel = new ExcelExporter();
							String files = excel.returnLocation();
							textField.setText(files);
						}
					});

					JButton button = new JButton();
					button.setBackground(Color.WHITE);
					button.setText("Save File");
					button.setFont(new Font("Arial", Font.PLAIN,15));
					button.setBounds(300,210,100,50);
					button.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							Integer start = Integer.parseInt(PanelData.year_start.getSelectedItem().toString());
							Integer end = Integer.parseInt(PanelData.year_end.getSelectedItem().toString());
							System.out.println(start + "," + end);
							ExcelExporter excel = new ExcelExporter();

							excel.chooseLocation(textField.getText());
							excel.export(WindEnergyJMapViewer.panel.current_wind_dataset,textField.getText(),start,end);
							textField.setText("File Saved");

						}
					});

					layeredPane.add(label);
					layeredPane.add(textField);
					layeredPane.add(button1);
					layeredPane.add(button);
					layeredPane.setBackground(Color.WHITE);
					layeredPane.add(title);
					layeredPane.add(email);
					layeredPane.add(save_file);
					layeredPane.add(closeButton);
					layeredPane.add(bgPanel);
					layeredPane.add(mainPanel);
					layeredPane.revalidate();
				}
			}
		};

		email.addItemListener(itemListener);
		save_file.addItemListener(itemListener);




		//add everything to layeredPane
		//the earlier the topper
		layeredPane.add(title);
		layeredPane.add(email);
		layeredPane.add(save_file);
		layeredPane.add(closeButton);
		layeredPane.add(bgPanel);
		layeredPane.add(mainPanel);

		this.setBounds(50,50,500,200);
		this.setUndecorated(true);
		this.setLocationRelativeTo(null);
	}

//	public static void main(String[] args){
//		ExporterWindow exporterWindow= new ExporterWindow();
//		exporterWindow.setVisible(true);
//
//	}
}
