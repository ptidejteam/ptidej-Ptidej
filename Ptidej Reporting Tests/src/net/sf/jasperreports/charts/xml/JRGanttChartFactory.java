package net.sf.jasperreports.charts.xml;

import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRBaseFactory;

import org.xml.sax.Attributes;

/**
 * @author Peter Risko (peter@risko.hu)
 * @version $Id: JRGanttChartFactory.java,v 1.1 2008/09/29 16:20:20 guehene Exp $
 */
public class JRGanttChartFactory extends JRBaseFactory {

    public Object createObject( Attributes attrs ){
        JasperDesign jasperDesign = (JasperDesign)this.digester.peek(this.digester.getCount() - 2);

        JRDesignChart chart = new JRDesignChart(jasperDesign, JRChart.CHART_TYPE_GANTT);

        return chart;
    }

    
}
