//import Data.Chart;
import Data.PanelData;
import Data.WindData;
import Network.NetState;
import Network.OperateUser;
//import WindEnergyJMapViewer;
//
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.JFreeChart;
//import org.jfree.data.category.CategoryDataset;
//
//import org.jfree.data.category.DefaultCategoryDataset;
//import org.openstreetmap.gui.jmapviewer.JMapViewer;
//
//import javax.swing.*;
//
//import java.awt.*;
//import java.awt.event.ComponentAdapter;
//import java.awt.event.ComponentEvent;
//import java.awt.image.BufferedImage;
//import java.net.InetAddress;
import java.awt.Color;
import java.net.URL;
import java.io.IOException;
import javax.swing.UIManager;
import java.awt.event.WindowEvent;
import java.util.List;
import java.io.InputStream;


//import static org.jfree.experimental.chart.demo.CombinedCategoryPlotDemo1.createDataset1;

public class Main {
	private static PanelData panel;

	public static void main(String[] args) {
		URL url = null;
		Boolean bon = false;
		UIManager.put("OptionPane.background", new Color(59,142,222));
		JFrameNoBorder j = new JFrameNoBorder();
		j.setVisible(true);
		OperateUser user = new OperateUser();
		List<WindData> datas = user.SelectData(1.0, 1.0);
		System.out.println(datas);
		try {
			url = new URL("http://www.google.com/");
			InputStream in = url.openStream();//
			System.out.println("Connection is Good");
			in.close();//
			try { Thread.sleep(2000); }
			catch (InterruptedException c) {
				c.printStackTrace();
			}
			j.welcomeText.setText("Connection is Made...");
			j.dispatchEvent(new WindowEvent(j, WindowEvent.WINDOW_CLOSING));
			Try_window.try_window();
			System.out.println("Execution successfull.");

		} catch (IOException e) {
//			JOptionPane.showMessageDialog(null, "No Network Connection", "Lloyd's Register", JOptionPane.ERROR_MESSAGE);
			j.welcomeText.setText("No network connection, please try again...");
			System.out.println("No Connection :" + url.toString());
			try {
				Thread.sleep(5000);
			} catch (InterruptedException f) {
				f.printStackTrace();
			}
			System.exit(0);


		}
		bon = NetState.isReachable(NetState.remoteInetAddr);
		System.out.println("pingIP：" + bon);
	}
}
