package net.sf.jasperreports.charts.base;

import java.io.Serializable;

import net.sf.jasperreports.charts.JRGanttSeries;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.base.JRBaseObjectFactory;

/**
 * @author Peter Risko (peter@risko.hu)
 * @version $Id: JRBaseGanttSeries.java,v 1.1 2008/09/29 16:20:32 guehene Exp $
 */
public class JRBaseGanttSeries implements JRGanttSeries, Serializable 
{
    
    /**
     *
     */
    private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

    protected JRExpression seriesExpression = null;
    protected JRExpression taskExpression = null;
    protected JRExpression subtaskExpression = null;
    protected JRExpression startDateExpression = null;
    protected JRExpression endDateExpression = null;
    protected JRExpression percentExpression = null;
    protected JRExpression labelExpression = null;
    protected JRHyperlink itemHyperlink;

    /**
     *
     */
    protected JRBaseGanttSeries()
    {
    }
    
    
    /**
     *
     */
    public JRBaseGanttSeries(JRGanttSeries ganttSeries, JRBaseObjectFactory factory)
    {
        factory.put(ganttSeries, this);

        this.seriesExpression = factory.getExpression(ganttSeries.getSeriesExpression());
        this.taskExpression = factory.getExpression(ganttSeries.getTaskExpression());
        this.subtaskExpression = factory.getExpression(ganttSeries.getSubtaskExpression());
        this.startDateExpression = factory.getExpression(ganttSeries.getStartDateExpression());
        this.endDateExpression = factory.getExpression(ganttSeries.getEndDateExpression());
        this.percentExpression = factory.getExpression(ganttSeries.getPercentExpression());
        this.labelExpression = factory.getExpression(ganttSeries.getLabelExpression());
        this.itemHyperlink = factory.getHyperlink(ganttSeries.getItemHyperlink());
    }

    
    /**
     *
     */
    public JRExpression getSeriesExpression()
    {
        return this.seriesExpression;
    }
        
    /**
     *
     */
    public JRExpression getTaskExpression()
    {
        return this.taskExpression;
    }
        
    /**
     *
     */
    public JRExpression getSubtaskExpression()
    {
        return this.subtaskExpression;
    }
       
    /**
     *
     */
    public JRExpression getStartDateExpression()
    {
        return this.startDateExpression;
    }
      
    /**
     *
     */
    public JRExpression getEndDateExpression()
    {
        return this.endDateExpression;
    }
        
    /**
     *
     */
    public JRExpression getPercentExpression()
    {
        return this.percentExpression;
    }
       
    /**
     *
     */
    public JRExpression getLabelExpression()
    {
        return this.labelExpression;
    }

    
    public JRHyperlink getItemHyperlink()
    {
        return this.itemHyperlink;
    }
        

	/**
	 * 
	 */
	public Object clone() 
	{
		JRBaseGanttSeries clone = null;
		
		try
		{
			clone = (JRBaseGanttSeries)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new JRRuntimeException(e);
		}
		
		if (this.seriesExpression != null)
		{
			clone.seriesExpression = (JRExpression)this.seriesExpression.clone();
		}
		if (this.taskExpression != null)
		{
			clone.taskExpression = (JRExpression)this.taskExpression.clone();
		}
		if (this.subtaskExpression != null)
		{
			clone.subtaskExpression = (JRExpression)this.subtaskExpression.clone();
		}
		if (this.startDateExpression != null)
		{
			clone.startDateExpression = (JRExpression)this.startDateExpression.clone();
		}
		if (this.endDateExpression != null)
		{
			clone.endDateExpression = (JRExpression)this.endDateExpression.clone();
		}
		if (this.percentExpression != null)
		{
			clone.percentExpression = (JRExpression)this.percentExpression.clone();
		}
		if (this.labelExpression != null)
		{
			clone.labelExpression = (JRExpression)this.labelExpression.clone();
		}
		if (this.itemHyperlink != null)
		{
			clone.itemHyperlink = (JRHyperlink)this.itemHyperlink.clone();
		}
		
		return clone;
	}

}
