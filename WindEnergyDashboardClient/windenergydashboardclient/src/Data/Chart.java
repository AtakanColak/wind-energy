package Data;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.block.BlockContainer;
import org.jfree.chart.block.BorderArrangement;
import org.jfree.chart.block.LabelBlock;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CenterTextMode;
import org.jfree.chart.plot.RingPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.Value;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.UnitType;

import java.awt.*;
import java.text.NumberFormat;
import java.util.*;

//import sun.plugin2.message.SetJVMIDMessage;

/**
 * Created by atakan on 03/02/18.
 */
public class Chart {

	public static JFreeChart create_chart(CategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createBarChart(
				"Wind Energy Index",
				"Year" /* x-axis label*/,
				"WindSpeed" /* y-axis label */, dataset);
		//chart.addSubtitle(new TextTitle("Time to generate 1000 charts in SVG "
		//+ "format (lower bars = better performance)"));
		chart.setBackgroundPaint(null);
		chart.setTitle(new TextTitle("Wind Energy Index", new Font("NotosansCoptic", Font.PLAIN, 25)));
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setBackgroundPaint(null);
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setAutoTickUnitSelection(false);
		rangeAxis.setTickUnit(new NumberTickUnit(100000));
		rangeAxis.setTickMarkPaint(new Color(238,118,36));
		rangeAxis.setTickMarkStroke(new BasicStroke(2f));
		rangeAxis.setTickMarkOutsideLength(0f);
		rangeAxis.setTickMarkInsideLength(100000000f);
		rangeAxis.setTickMarksVisible(true);
		rangeAxis.setTickLabelsVisible(false);


//        plot.getDomainAxis().setMaximumCategoryLabelWidthRatio(0.6f)
		plot.getDomainAxis().setTickLabelFont(new Font("NotoSansCoptic", Font.PLAIN,10));
		plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
		plot.getDomainAxis().setTickMarksVisible(true);
		plot.setOutlinePaint(Color.white);
		plot.setBackgroundPaint(Color.white);
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		BarRenderer renderer = (BarRenderer) plot.getRenderer();

//        renderer.setDrawBarOutline(false);
		StackedBarRenderer barRenderer = new StackedBarRenderer(true);
		barRenderer.setBarPainter(new StandardBarPainter());
		barRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		barRenderer.setBaseItemLabelsVisible(true);
		barRenderer.setRenderAsPercentages(true);
//		plot.setRenderer(barRenderer);
		renderer.setDrawBarOutline(true);
		renderer.setShadowVisible(false);
		renderer.setBarPainter(new StandardBarPainter());
		renderer.setMaximumBarWidth(0.08);
		NumberFormat numberFormat= NumberFormat.getPercentInstance();
		numberFormat.setMaximumFractionDigits(3);
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}",numberFormat));
		renderer.setBaseItemLabelsVisible(true);
		renderer.setBaseItemLabelPaint(new Color(65,64,66));
		renderer.setBaseItemLabelFont(new Font("NotoSansCoptic", Font.PLAIN, 10));
		renderer.setItemMargin(0.2D);

		for(int i=0; i<5; i++){
			renderer.setSeriesPaint(i, new Color(0,60,113));
			renderer.setSeriesOutlinePaint(i, new Color(59,142,222));
		}
		chart.getLegend().setFrame(BlockBorder.NONE);
		return chart;
	}

	public static JFreeChart createChart(PieDataset dataset) {

		JFreeChart chart = ChartFactory.createRingChart(
				"Wind Index Percentage",  // chart title
				dataset,             // data
				true,               // include legend
				true,
				false
		);
		chart.setTitle(new TextTitle("Wind Index Percentage", new Font("NotoSansCoptic", Font.PLAIN, 25)));
		chart.setPadding(new RectangleInsets(4,8,2,2));
		RingPlot plot = (RingPlot) chart.getPlot();
		plot.setLabelFont(new Font("NotoSansCoptic", Font.PLAIN, 12));
		plot.setNoDataMessage("No data available");
		plot.setSectionDepth(0.45);
		plot.setCircular(true);
		plot.setCenterTextMode(CenterTextMode.FIXED);
		double value = plot.getDataset().getValue("a").doubleValue();
		double percentage = (value-25);
		System.out.println(percentage);
		String p = String.format("%.1f",percentage);
		String r = "%";
		String c = p+r;
		plot.setCenterText(c);
		plot.setCenterTextColor(Color.black);
		plot.setCenterTextFont(new Font("微软雅黑",Font.PLAIN,20));

//        plot.setLabelGap(0.1);
		plot.setOutlinePaint(Color.white);
		plot.setSectionPaint("b" ,new Color(255,255,255));
		plot.setSectionPaint("a", new Color(59,144,222));
		plot.setSectionPaint("c", new Color(255,255,255));
		plot.setShadowPaint(null);
		plot.setInnerSeparatorExtension(0);
		plot.setOuterSeparatorExtension(0);
		plot.setSectionOutlinePaint("c",new Color(255,255,255));
		plot.setSectionOutlinePaint("b",new Color(0,60,113));
		plot.setSectionOutlinePaint("a",new Color(0,60,113));
		plot.setSeparatorPaint(new Color(0,60,113));
		plot.setBackgroundPaint(Color.white);
		plot.setSimpleLabels(true);
		plot.setLabelGenerator(null);

		plot.setSimpleLabelOffset(new RectangleInsets (UnitType.RELATIVE, 1D, 1D, 1D, 1D));
		plot.setLabelBackgroundPaint(null);
		plot.setLabelGap(5.0);
		plot.setLabelShadowPaint(null);
		plot.setLabelOutlinePaint(null);;
		plot.setStartAngle(0.0);
		plot.setSeparatorsVisible(true);
//
		LegendTitle legendTitle = new LegendTitle(chart.getPlot());
		BlockContainer blockcontainer = new BlockContainer(new BorderArrangement());
		blockcontainer.setBorder(1.0D, 1.0D, 1.0D, 1.0D);
		LabelBlock labelBlock = new LabelBlock("Wing", new Font("NotoSansCoptic", Font.PLAIN,12));
		labelBlock.setPadding(5D, 5D, 5D, 5D);
		blockcontainer.add(labelBlock, RectangleEdge.TOP);
		legendTitle.setWrapper(blockcontainer);
		legendTitle.setPosition(RectangleEdge.BOTTOM);
		chart.addSubtitle(legendTitle);

		plot.setSeparatorPaint(Color.WHITE);
		plot.setSectionOutlineStroke(new BasicStroke(1.0f));



		plot.setSimpleLabelOffset(new RectangleInsets (UnitType.RELATIVE, 0.09, 0.09, 0.09, 0.09));
		plot.setInteriorGap(0.1D);
		return chart;

	}

	public static ChartPanel create_chart_panel(JFreeChart chart, Dimension preferred_size) {
		ChartPanel chartPanel = new ChartPanel(chart, false);
		chartPanel.setBackground(null);
		chartPanel.setMouseWheelEnabled(false);
		chartPanel.setMouseZoomable(true);
		chartPanel.setDismissDelay(Integer.MAX_VALUE);
		return chartPanel;
	}

	public static CategoryDataset bar_chart_dataset(java.util.List<WindData> datas, int start_year, int end_year){
		int year_account = end_year - start_year + 1;
		Double sum = 0.0;
		Double sum1 = 0.0;
		Double mean = 0.0;
		Double value;
		Double speed;
		int monthaccount = 12;
		java.util.List<Double> year_average_speed = new ArrayList<>();
		int total_year_account = 5;
		for (int i=0; i<datas.size(); i++){
			System.out.println(datas.get(i).year);
		}
//		int year_account = Datasize/monthaccount;
		DefaultCategoryDataset windData = new DefaultCategoryDataset();
		for(int i=0; i < 8; i++){
			double value1 = 0.0;
			value1 = datas.get(i).windspeed;
			sum1 += value1;
		}
		year_average_speed.add(sum1/8);
		for(int i=0; i <total_year_account; i++){
			value = 0.0;
			for(int j=1; j<monthaccount+1; j++){
				Double windspeed = datas.get(7+i*12+j).windspeed;
				value += windspeed;
			}
			value /= monthaccount;
			System.out.println(value);
			System.out.println("123");
			year_average_speed.add(value);
		}
		double averge_wind_speed_2018 = (datas.get(datas.size()-1).windspeed+datas.get(datas.size()-2).windspeed)/2;
		year_average_speed.add(averge_wind_speed_2018);

		for(int k=0; k<datas.size(); k++){
			sum += datas.get(k).windspeed;
		}
		System.out.println("sum" + sum);
		mean = sum/datas.size();
		System.out.println("mean" + mean);
//        System.out.println(start_year);
//        System.out.println(year_average_speed);
		for(int l=0
		    ; l<year_account; l++){
			speed = 0.0;
			speed = (year_average_speed.get(start_year-2012+l)-mean)/100;
			System.out.println(speed);
			System.out.println(year_average_speed.get(l));
			windData.addValue(speed,"",String.valueOf(start_year+l));
		}
		return windData;
	}

	public static PieDataset pie_chart_dataset(java.util.List<WindData> datas, int select_year){
		Double value;
		Double value1;
		Double sum = 0.0;
		DefaultPieDataset pieData = new DefaultPieDataset();
		java.util.List<Double> year_average_speed = new ArrayList<>();
		Integer Datasize = 5;

		for(int i=0; i<8; i++){
			value1=0.0;
			value1=datas.get(i).windspeed;
			sum += value1;
		}
		year_average_speed.add(sum/8);

		for(int i=0; i <Datasize; i++){
			value = 0.0;
			for(int j=0; j<12; j++){
				Double windspeed = datas.get(7+i*12+j).windspeed;
				value += windspeed;
			}
			value /= 12;
			year_average_speed.add(value);

		}

		year_average_speed.add((datas.get(datas.size()-1).windspeed+datas.get(datas.size()-2).windspeed)/2);
		Double speed = year_average_speed.get(select_year-2012);

		System.out.println(speed);
		pieData.setValue("c",50.0);
		pieData.setValue("a",25.0+speed);
		pieData.setValue("b",25.0-speed);
		return pieData;
	}

	public static PieDataset pieDataset(){
		DefaultPieDataset pieDataset = new DefaultPieDataset();
		pieDataset.setValue("c",50.0);
		pieDataset.setValue("a",25.0);
		pieDataset.setValue("b",25.0);
		return pieDataset;
	}
}
