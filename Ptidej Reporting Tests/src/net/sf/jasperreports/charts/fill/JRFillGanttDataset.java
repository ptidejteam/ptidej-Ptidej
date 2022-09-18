package net.sf.jasperreports.charts.fill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.charts.JRGanttDataset;
import net.sf.jasperreports.charts.JRGanttSeries;
import net.sf.jasperreports.charts.util.CategoryLabelGenerator;
import net.sf.jasperreports.engine.JRChartDataset;
import net.sf.jasperreports.engine.JRExpressionCollector;
import net.sf.jasperreports.engine.design.JRVerifier;
import net.sf.jasperreports.engine.fill.JRCalculator;
import net.sf.jasperreports.engine.fill.JRExpressionEvalException;
import net.sf.jasperreports.engine.fill.JRFillChartDataset;
import net.sf.jasperreports.engine.fill.JRFillObjectFactory;
import net.sf.jasperreports.engine.util.Pair;

import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.general.Dataset;
import org.jfree.data.time.SimpleTimePeriod;

/**
 * @author Peter Risko (peter@risko.hu)
 * @version $Id: JRFillGanttDataset.java,v 1.1 2008/09/29 16:21:42 guehene Exp $
 */
public class JRFillGanttDataset extends JRFillChartDataset implements JRGanttDataset {

    /**
     *
     */
    protected JRFillGanttSeries[] ganttSeries = null;

    private List seriesNames = null;
    private Map seriesMap = null;
    private Map labelsMap = null;
    
    private Map itemHyperlinks;
    
    
    /**
     *
     */
    public JRFillGanttDataset(
        JRGanttDataset ganttDataset, 
        JRFillObjectFactory factory
        )
    {
        super(ganttDataset, factory);
        
        /*   */
        JRGanttSeries[] srcGanttSeries = ganttDataset.getSeries();
        if (srcGanttSeries != null && srcGanttSeries.length > 0)
        {
            this.ganttSeries = new JRFillGanttSeries[srcGanttSeries.length];
            for(int i = 0; i < this.ganttSeries.length; i++)
            {
                this.ganttSeries[i] = (JRFillGanttSeries)factory.getGanttSeries(srcGanttSeries[i]);
            }
        }
    }
    
    
    /**
     *
     */
    public JRGanttSeries[] getSeries()
    {
        return this.ganttSeries;
    }


    /**
     *
     */
    protected void customInitialize()
    {
        this.seriesNames = null;
        this.seriesMap = null;
        this.labelsMap = null;
        this.itemHyperlinks = null;
    }

    
    /**
     *
     */
    protected void customEvaluate(JRCalculator calculator) throws JRExpressionEvalException
    {
        if (this.ganttSeries != null && this.ganttSeries.length > 0)
        {
            for(int i = 0; i < this.ganttSeries.length; i++)
            {
                this.ganttSeries[i].evaluate(calculator);
            }
        }
    }

    
    /**
     *
     */
    protected void customIncrement()
    {
        if (this.ganttSeries != null && this.ganttSeries.length > 0)
        {
            if (this.seriesNames == null)
            {
                this.seriesNames = new ArrayList();
                this.seriesMap = new HashMap();
                this.labelsMap = new HashMap();
                this.itemHyperlinks = new HashMap();
            }

            for(int i = 0; i < this.ganttSeries.length; i++)
            {
                JRFillGanttSeries crtGanttSeries = this.ganttSeries[i];

                Comparable seriesName = crtGanttSeries.getSeries();
                TaskSeries taskSrs = (TaskSeries)this.seriesMap.get(seriesName);
                if (taskSrs == null)
                {
                    taskSrs =  new TaskSeries((String)seriesName);
                    this.seriesNames.add(seriesName);
                    this.seriesMap.put(seriesName, taskSrs);
                }
                
                // create task
                Task task = taskSrs.get(crtGanttSeries.getTask());
                if(task == null) {
                    task = new Task(crtGanttSeries.getTask(), 
                            crtGanttSeries.getStartDate(),
                            crtGanttSeries.getEndDate());
                    taskSrs.add(task);
                }
                // create subtask
                Task subtask = new Task(crtGanttSeries.getSubtask(),
                        crtGanttSeries.getStartDate(),
                        crtGanttSeries.getEndDate());
                // NOTE: For correct scaling/plotting JFreeChart needs the subtasks
                //       to be 'inside' of the containing task.
                //       Therefore the earliest subtask startvalue
                //       is set as startvalue for the whole task, and the
                //       latest subtask endvalue set as endvalue for the
                //       whole task.
                if(subtask.getDuration().getStart().before(task.getDuration().getStart())) {
                    task.setDuration(new SimpleTimePeriod(subtask.getDuration().getStart(), task.getDuration().getEnd()));
                }
                if(subtask.getDuration().getEnd().after(task.getDuration().getEnd())) {
                    task.setDuration(new SimpleTimePeriod(task.getDuration().getStart(), subtask.getDuration().getEnd()));
                }
                Number percent = crtGanttSeries.getPercent();
                if (percent != null)
                {
                    subtask.setPercentComplete(percent.doubleValue());
                }
                task.addSubtask(subtask);
                
                if (crtGanttSeries.getLabelExpression() != null)
                {
                    Map seriesLabels = (Map)this.labelsMap.get(seriesName);
                    if (seriesLabels == null)
                    {
                        seriesLabels = new HashMap();
                        this.labelsMap.put(seriesName, seriesLabels);
                    }
                    
                    // TODO: is it OK like this?
                    //seriesLabels.put(crtXySeries.getXValue(), crtXySeries.getLabel());
                    seriesLabels.put(crtGanttSeries.getTask(), crtGanttSeries.getLabel());
                }
                
                if (crtGanttSeries.hasItemHyperlinks())
                {
                    Map seriesLinks = (Map) this.itemHyperlinks.get(seriesName);
                    if (seriesLinks == null)
                    {
                        seriesLinks = new HashMap();
                        this.itemHyperlinks.put(seriesName, seriesLinks);
                    }
                    // TODO: ?? not sure how to do
                    //Pair xyKey = new Pair(crtXySeries.getXValue(), crtXySeries.getYValue());
                    //seriesLinks.put(xyKey, crtXySeries.getPrintItemHyperlink());
                    Pair taskSubtaskKey = new Pair(crtGanttSeries.getTask(), crtGanttSeries.getSubtask());
                    seriesLinks.put(taskSubtaskKey, crtGanttSeries.getPrintItemHyperlink());
                }
            }
        }
    }

    
    /**
     *
     */
    public Dataset getCustomDataset()
    {
        TaskSeriesCollection dataset = new TaskSeriesCollection();
        if (this.seriesNames != null)
        {
            for(int i = 0; i < this.seriesNames.size(); i++)
            {
                Comparable seriesName = (Comparable)this.seriesNames.get(i);
                dataset.add((TaskSeries)this.seriesMap.get(seriesName));
            }
        }
        return dataset;
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
    public CategoryItemLabelGenerator getLabelGenerator(){
        return new CategoryLabelGenerator(this.labelsMap);
    }
    
    
    /**
     *
     */
    public void collectExpressions(JRExpressionCollector collector)
    {
        collector.collect(this);
    }

    
    public Map getItemHyperlinks()
    {
        return this.itemHyperlinks;
    }
    
    
    public boolean hasItemHyperlinks()
    {
        boolean foundLinks = false;
        if (this.ganttSeries != null && this.ganttSeries.length > 0)
        {
            for (int i = 0; i < this.ganttSeries.length && !foundLinks; i++)
            {
                JRFillGanttSeries series = this.ganttSeries[i];
                foundLinks = series.hasItemHyperlinks();
            }
        }
        return foundLinks;
    }


    public void validate(JRVerifier verifier)
    {
        verifier.verify(this);
    }


    
}
