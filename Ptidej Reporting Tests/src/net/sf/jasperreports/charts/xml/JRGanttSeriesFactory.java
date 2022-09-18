package net.sf.jasperreports.charts.xml;

import net.sf.jasperreports.charts.design.JRDesignGanttSeries;
import net.sf.jasperreports.engine.xml.JRBaseFactory;

import org.xml.sax.Attributes;

/**
 * @author Peter Risko (peter@risko.hu)
 * @version $Id: JRGanttSeriesFactory.java,v 1.1 2008/09/29 16:20:20 guehene Exp $
 */
public class JRGanttSeriesFactory extends JRBaseFactory {

    /**
     *
     */
    public Object createObject(Attributes atts)
    {
        return new JRDesignGanttSeries();
    }

    
}
