package Data;

import javax.swing.*;
import java.util.List;

/**
 * Created by atakan on 04/02/18.
 */
public class PanelData {
    public PanelData(JPanel p, JPanel p_map, JPanel p_g1, JPanel p_g2, JTextField t1, JTextField t2, JComboBox t3, JComboBox t4, JComboBox t5, JButton button) {
        panel = p;
        panel_map = p_map;
        panel_graph1 = p_g1;
        panel_graph2 = p_g2;
        coordinate1 = t1;
        coordinate2 = t2;
        year_start = t3;
        year_end = t4;
        year_select = t5;
        export = button;
    }
    public JPanel panel;
    public JPanel panel_map;
    public JPanel panel_graph1;
    public JPanel panel_graph2;
    public JTextField coordinate1;
    public JTextField coordinate2;
	public JComboBox year_select;
    public static JComboBox year_start;
    public static JComboBox year_end;
    public JButton export;
    public List<WindData> current_wind_dataset;
}
