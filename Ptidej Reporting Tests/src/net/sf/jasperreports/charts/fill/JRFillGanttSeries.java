package net.sf.jasperreports.charts.fill;

import java.util.Date;

import net.sf.jasperreports.charts.JRGanttSeries;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRHyperlink;
import net.sf.jasperreports.engine.JRPrintHyperlink;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.fill.JRCalculator;
import net.sf.jasperreports.engine.fill.JRExpressionEvalException;
import net.sf.jasperreports.engine.fill.JRFillHyperlinkHelper;
import net.sf.jasperreports.engine.fill.JRFillObjectFactory;

/**
 * @author Peter Risko (peter@risko.hu)
 * @version $Id: JRFillGanttSeries.java,v 1.1 2008/09/29 16:21:42 guehene Exp $
 */
public class JRFillGanttSeries implements JRGanttSeries {

    /**
     *
     */
    protected JRGanttSeries parent = null;

    private Comparable series = null;
    private String task = null;
    private String subtask = null;
    private Date startDate = null;
    private Date endDate = null;
    private Number percent = null;
    private String label = null;
    private JRPrintHyperlink itemHyperlink;
    
    
    /**
     *
     */
    public JRFillGanttSeries(
        JRGanttSeries ganttSeries, 
        JRFillObjectFactory factory
        )
    {
        factory.put(ganttSeries, this);

        this.parent = ganttSeries;
    }


    /**
     *
     */
    public JRExpression getSeriesExpression()
    {
        return this.parent.getSeriesExpression();
    }
        
    public JRExpression getStartDateExpression(){
        return this.parent.getStartDateExpression();
    }
    
    public JRExpression getEndDateExpression(){
        return this.parent.getEndDateExpression();
    }
    
    /**
     *
     */
    public JRExpression getTaskExpression()
    {
        return this.parent.getTaskExpression();
    }
        
    /**
     *
     */
    public JRExpression getSubtaskExpression()
    {
        return this.parent.getSubtaskExpression();
    }
        
    /**
     *
     */
    public JRExpression getPercentExpression()
    {
        return this.parent.getPercentExpression();
    }
       
    /**
     *
     */
    public JRExpression getLabelExpression()
    {
        return this.parent.getLabelExpression();
    }
    
    
    /**
     *
     */
    protected Comparable getSeries()
    {
        return this.series;
    }
        
    protected Date getStartDate(){
        return this.startDate;
    }
    
    protected Date getEndDate(){
        return this.endDate;
    }
    
    /**
     *
     */
    protected String getTask()
    {
        return this.task;
    }
        
    /**
     *
     */
    protected String getSubtask()
    {
        return this.subtask;
    }
        
    /**
     *
     */
    protected Number getPercent()
    {
        return this.percent;
    }
       
    /**
     *
     */
    protected String getLabel()
    {
        return this.label;
    }
    
    protected JRPrintHyperlink getPrintItemHyperlink()
    {
        return this.itemHyperlink;
    }
    
    
    /**
     *
     */
    protected void evaluate(JRCalculator calculator) throws JRExpressionEvalException
    {
        this.series = (Comparable)calculator.evaluate(getSeriesExpression()); 
        this.startDate = (Date)calculator.evaluate( getStartDateExpression() );
        this.endDate = (Date)calculator.evaluate( getEndDateExpression() );
        this.task = (String)calculator.evaluate(getTaskExpression()); 
        this.subtask = (String)calculator.evaluate(getSubtaskExpression());
        this.percent = (Number)calculator.evaluate(getPercentExpression());
        this.label = (String)calculator.evaluate(getLabelExpression());
        
        if (hasItemHyperlinks())
        {
            evaluateItemHyperlink(calculator);
        }
    }


    protected void evaluateItemHyperlink(JRCalculator calculator) throws JRExpressionEvalException
    {
        try
        {
            this.itemHyperlink = JRFillHyperlinkHelper.evaluateHyperlink(getItemHyperlink(), calculator, JRExpression.EVALUATION_DEFAULT);
        }
        catch (JRExpressionEvalException e)
        {
            throw e;
        }
        catch (JRException e)
        {
            throw new JRRuntimeException(e);
        }
    }


    public JRHyperlink getItemHyperlink()
    {
        return this.parent.getItemHyperlink();
    }

    
    public boolean hasItemHyperlinks()
    {
        return getItemHyperlink() != null;
    }


    
}
