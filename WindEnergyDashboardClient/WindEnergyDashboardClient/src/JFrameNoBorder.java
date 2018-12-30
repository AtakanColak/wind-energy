import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class JFrameNoBorder extends JFrame {

//	public static void main(String[] args) {
//		JFrameNoBorder j = new JFrameNoBorder();
//		j.setVisible(true);
//	}

	JLabel welcomeText = new JLabel();

	private static final long serialVersionUID = 1L;
	//coordinates to move the JFrame
	int xOld = 0;
	int yOld = 0;

	public JFrameNoBorder() {
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
				JFrameNoBorder.this.setLocation(xx, yy);
			}
		});

		//JLayeredPane to get two added panel
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 1000, 1000);
		this.add(layeredPane);

		//create backgroundPanel
		JPanel bgPanel = new JPanel();
		bgPanel.setBounds(0, 0, 741, 591);


		//add image to backgroundPanel
		JLabel bgLabel = new JLabel(new ImageIcon("files/LR-logo.jpg"));
		bgPanel.add(bgLabel);

		//mainPanel
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 741, 591);
		mainPanel.setLayout(null);


        //text

		welcomeText.setText("Checking for internet connection...");
		welcomeText.setBounds(250, 571, 350, 20);
		welcomeText.setForeground(Color.BLACK);


		//add everything to layeredPane
		//the earlier the topper
		layeredPane.add(welcomeText);
		layeredPane.add(bgPanel);
		layeredPane.add(mainPanel);

		this.setBounds(50,50,741,591);
		this.setUndecorated(true);
		this.setLocationRelativeTo(null);
	}
}
