package net.sf.jasperreports.charts.design;

import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.charts.JRGanttDataset;
import net.sf.jasperreports.charts.JRGanttSeries;
import net.sf.jasperreports.engine.JRChartDataset;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.design.JRDesignChartDataset;
import net.sf.jasperreports.engine.design.JRVerifier;

/**
 * @author Peter Risko (peter@risko.hu)
 * @version $Id: JRDesignGanttDataset.java,v 1.1 2008/09/29 16:21:32 guehene Exp $
 */
public class JRDesignGanttDataset  extends JRDesignChartDataset implements JRGanttDataset {

    /**
     *
     */
    private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

    private List ganttSeriesList = new ArrayList();


    /**
     *
     */
    public JRDesignGanttDataset(JRChartDataset dataset)
    {
        super(dataset);
    }


    /**
     *
     */
    public JRGanttSeries[] getSeries()
    {
        JRGanttSeries[] ganttSeriesArray = new JRGanttSeries[this.ganttSeriesList.size()];
        
        this.ganttSeriesList.toArray(ganttSeriesArray);

        return ganttSeriesArray;
    }
    

    /**
     * 
     */
    public List getSeriesList()
    {
        return this.ganttSeriesList;
    }

    
    /**
     *
     */
    public void addGanttSeries(JRGanttSeries ganttSeries)
    {
        this.ganttSeriesList.add(ganttSeries);
    }
    

    /**
     *
     */
    public JRGanttSeries removeGanttSeries(JRGanttSeries ganttSeries)
    {
        if (ganttSeries != null)
        {
            this.ganttSeriesList.remove(ganttSeries);
        }
        
        return ganttSeries;
    }


    /** 
     * 
     */
    public byte getDatasetType() {
        return JRChartDataset.GANTT_DATASET;
    }
    
    
    /**
     *
     */
    public void collectExpressions(JRExpressionCollector collector)
    {
        collector.collect(this);
    }


    public void validate(JRVerifier verifier)
    {
        verifier.verify(this);
    }

    
}
