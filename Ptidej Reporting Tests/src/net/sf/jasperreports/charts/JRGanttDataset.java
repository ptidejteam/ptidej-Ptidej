package net.sf.jasperreports.charts;

import net.sf.jasperreports.engine.JRChartDataset;

/**
 * @author Peter Risko (peter@risko.hu)
 * @version $Id: JRGanttDataset.java,v 1.1 2008/09/29 16:21:37 guehene Exp $
 */
public interface JRGanttDataset extends JRChartDataset {
 
    /**
     * 
     */
    public JRGanttSeries[] getSeries();


}
