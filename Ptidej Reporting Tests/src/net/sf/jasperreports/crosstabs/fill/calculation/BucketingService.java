/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 *
 * JasperReports - Free Java report-generating library.
 * Copyright (C) 2001-2006 JasperSoft Corporation http://www.jaspersoft.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307, USA.
 * 
 * JasperSoft Corporation
 * 303 Second Street, Suite 450 North
 * San Francisco, CA 94107
 * http://www.jaspersoft.com
 */
package net.sf.jasperreports.crosstabs.fill.calculation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import net.sf.jasperreports.crosstabs.fill.calculation.BucketDefinition.Bucket;
import net.sf.jasperreports.crosstabs.fill.calculation.MeasureDefinition.MeasureValue;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRVariable;
import net.sf.jasperreports.engine.fill.JRCalculable;
import net.sf.jasperreports.engine.util.JRProperties;

/**
 * Crosstab bucketing engine.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: BucketingService.java,v 1.1 2008/09/29 16:21:53 guehene Exp $
 */
public class BucketingService
{
	
	public static final String PROPERTY_BUCKET_MEASURE_LIMIT = JRProperties.PROPERTY_PREFIX + "crosstab.bucket.measure.limit";
	
	protected static final byte DIMENSION_ROW = 0;

	protected static final byte DIMENSION_COLUMN = 1;

	protected static final int DIMENSIONS = 2;

	protected final BucketDefinition[] allBuckets;
	protected final BucketDefinition[][] buckets;

	protected final int rowBucketCount;
	protected final int colBucketCount;

	protected final boolean[][] retrieveTotal;
	private boolean[] rowRetrTotals;
	private int rowRetrTotalMin;
	private int rowRetrTotalMax;
	private int[] rowRetrColMax;

	protected final MeasureDefinition[] measures;
	protected final int origMeasureCount;
	protected final int[] measureIndexes;

	protected final boolean sorted;

	protected final BucketMap bucketValueMap;
	protected long dataCount;
	protected boolean processed;
	
	protected HeaderCell[][] colHeaders;
	protected HeaderCell[][] rowHeaders;
	protected CrosstabCell[][] cells;

	private final MeasureValue[] zeroUserMeasureValues;

	private final int bucketMeasureLimit;
	private int runningBucketMeasureCount = 0;
	
	/**
	 * Creates a crosstab bucketing engine.
	 * 
	 * @param rowBuckets the row bucket definitions
	 * @param columnBuckets the column bucket definitions
	 * @param measures the measure definitions
	 * @param sorted whether the data is presorted
	 * @param retrieveTotal totals to retrieve along with the cell values
	 */
	public BucketingService(List rowBuckets, List columnBuckets, List measures, boolean sorted, boolean[][] retrieveTotal)
	{
		this.sorted = sorted;

		this.buckets = new BucketDefinition[DIMENSIONS][];
		
		this.rowBucketCount = rowBuckets.size();
		this.buckets[DIMENSION_ROW] = new BucketDefinition[this.rowBucketCount];
		rowBuckets.toArray(this.buckets[DIMENSION_ROW]);
		
		this.colBucketCount = columnBuckets.size();
		this.buckets[DIMENSION_COLUMN] = new BucketDefinition[this.colBucketCount];
		columnBuckets.toArray(this.buckets[DIMENSION_COLUMN]);

		this.allBuckets = new BucketDefinition[this.rowBucketCount + this.colBucketCount];
		System.arraycopy(this.buckets[DIMENSION_ROW], 0, this.allBuckets, 0, this.rowBucketCount);
		System.arraycopy(this.buckets[DIMENSION_COLUMN], 0, this.allBuckets, this.rowBucketCount, this.colBucketCount);

		this.origMeasureCount = measures.size();
		List measuresList = new ArrayList(measures.size() * 2);
		List measureIndexList = new ArrayList(measures.size() * 2);
		for (int i = 0; i < measures.size(); ++i)
		{
			MeasureDefinition measure = (MeasureDefinition) measures.get(i);
			addMeasure(measure, i, measuresList, measureIndexList);
		}
		this.measures = new MeasureDefinition[measuresList.size()];
		measuresList.toArray(this.measures);
		this.measureIndexes = new int[measureIndexList.size()];
		for (int i = 0; i < this.measureIndexes.length; ++i)
		{
			this.measureIndexes[i] = ((Integer) measureIndexList.get(i)).intValue();
		}

		this.retrieveTotal = retrieveTotal;
		checkTotals();
		
		this.bucketValueMap = createBucketMap(0);
		
		this.zeroUserMeasureValues = initUserMeasureValues();
		
		this.bucketMeasureLimit = JRProperties.getIntegerProperty(PROPERTY_BUCKET_MEASURE_LIMIT, 0);
	}


	protected void checkTotals()
	{
		this.rowRetrTotalMin = this.rowBucketCount + 1;
		this.rowRetrTotalMax = -1;
		this.rowRetrTotals = new boolean[this.rowBucketCount + 1];
		this.rowRetrColMax = new int[this.rowBucketCount + 1];
		for (int row = 0; row <= this.rowBucketCount; ++row)
		{
			this.rowRetrColMax[row] = -1;
			boolean total = false;
			for (int col = 0; col <= this.colBucketCount; ++col)
			{
				if (this.retrieveTotal[row][col])
				{
					total = true;
					this.rowRetrColMax[row] = col;
				}
			}
			
			this.rowRetrTotals[row] = total;
			if (total)
			{
				if (row < this.rowRetrTotalMin)
				{
					this.rowRetrTotalMin = row;
				}
				this.rowRetrTotalMax = row;
				
				if (row < this.rowBucketCount)
				{
					this.allBuckets[row].setComputeTotal();
				}
			}
		}
		
		for (int col = 0; col < this.colBucketCount; ++col)
		{
			BucketDefinition colBucket = this.allBuckets[this.rowBucketCount + col];
			if (!colBucket.computeTotal())
			{
				boolean total = false;
				for (int row = 0; !total && row <= this.rowBucketCount; ++row)
				{
					total = this.retrieveTotal[row][col];
				}
				
				if (total)
				{
					colBucket.setComputeTotal();
				}
			}
		}
		
		for (int d = 0; d < DIMENSIONS; ++d)
		{
			boolean dTotal = false;
			
			for (int i = 0; i < this.buckets[d].length; ++i)
			{
				if (dTotal)
				{
					this.buckets[d][i].setComputeTotal();
				}
				else
				{
					dTotal = this.buckets[d][i].computeTotal();
				}
			}
		}
	}


	/**
	 * Clears all the accumulated and computed data.
	 */
	public void clear()
	{
		this.bucketValueMap.clear();
		this.processed = false;
		this.dataCount = 0;
		this.runningBucketMeasureCount = 0;
	}
	
	protected BucketMap createBucketMap(int level)
	{
		BucketMap map;
		if (this.sorted)
		{
			map = new BucketListMap(level, false);
		}
		else
		{
			map = new BucketTreeMap(level);
		}
		return map;
	}
	
	protected BucketListMap createCollectBucketMap(int level)
	{
		return new BucketListMap(level, true);
	}

	protected void addMeasure(MeasureDefinition measure, int index, List measuresList, List measureIndexList)
	{
		switch (measure.getCalculation())
		{
			case JRVariable.CALCULATION_AVERAGE:
			case JRVariable.CALCULATION_VARIANCE:
			{
				MeasureDefinition sumMeasure = MeasureDefinition.createHelperMeasure(measure, JRVariable.CALCULATION_SUM);
				addMeasure(sumMeasure, index, measuresList, measureIndexList);
				MeasureDefinition countMeasure = MeasureDefinition.createHelperMeasure(measure, JRVariable.CALCULATION_COUNT);
				addMeasure(countMeasure, index, measuresList, measureIndexList);
				break;
			}
			case JRVariable.CALCULATION_STANDARD_DEVIATION:
			{
				MeasureDefinition varianceMeasure = MeasureDefinition.createHelperMeasure(measure, JRVariable.CALCULATION_VARIANCE);
				addMeasure(varianceMeasure, index, measuresList, measureIndexList);
				break;
			}
			case JRVariable.CALCULATION_DISTINCT_COUNT:
			{
				MeasureDefinition countMeasure = MeasureDefinition.createDistinctCountHelperMeasure(measure);
				addMeasure(countMeasure, index, measuresList, measureIndexList);
				break;
			}
		}

		measuresList.add(measure);
		measureIndexList.add(Integer.valueOf(index));
	}

	
	/**
	 * Feeds data to the engine.
	 *  
	 * @param bucketValues the bucket values
	 * @param measureValues the measure values
	 * @throws JRException
	 */
	public void addData(Object[] bucketValues, Object[] measureValues) throws JRException
	{
		if (this.processed)
		{
			throw new JRException("Crosstab data has already been processed.");
		}
		
		++this.dataCount;
		
		Bucket[] bucketVals = getBucketValues(bucketValues);

		MeasureValue[] values = this.bucketValueMap.insertMeasureValues(bucketVals);

		for (int i = 0; i < this.measures.length; ++i)
		{
			Object measureValue = measureValues[this.measureIndexes[i]];
			values[i].addValue(measureValue);
		}
	}
	
	protected void bucketMeasuresCreated()
	{
		this.runningBucketMeasureCount += this.origMeasureCount;
		
		checkBucketMeasureCount(this.runningBucketMeasureCount);
	}

	protected Bucket[] getBucketValues(Object[] bucketValues)
	{
		Bucket[] bucketVals = new Bucket[this.allBuckets.length];

		for (int i = 0; i < this.allBuckets.length; ++i)
		{
			BucketDefinition bucket = this.allBuckets[i];
			Object value = bucketValues[i];
			bucketVals[i] = bucket.create(value);
		}
		
		return bucketVals;
	}

	protected MeasureValue[] initMeasureValues()
	{
		MeasureValue[] values;
		values = new MeasureValue[this.measures.length];

		for (int i = 0; i < this.measures.length; ++i)
		{
			MeasureDefinition measure = this.measures[i];
			values[i] = measure.new MeasureValue();

			switch (measure.getCalculation())
			{
				case JRVariable.CALCULATION_AVERAGE:
				case JRVariable.CALCULATION_VARIANCE:
				{
					values[i].setHelper(values[i - 2], JRCalculable.HELPER_SUM);
					values[i].setHelper(values[i - 1], JRCalculable.HELPER_COUNT);
					break;
				}
				case JRVariable.CALCULATION_STANDARD_DEVIATION:
				{
					values[i].setHelper(values[i - 1], JRCalculable.HELPER_VARIANCE);
				}
				case JRVariable.CALCULATION_DISTINCT_COUNT:
				{
					values[i].setHelper(values[i - 1], JRCalculable.HELPER_COUNT);
				}
			}
		}
		return values;
	}

	protected MeasureValue[] initUserMeasureValues()
	{
		MeasureValue[] vals = new MeasureValue[this.origMeasureCount];
		
		for (int c = 0, i = 0; i < this.measures.length; ++i)
		{
			if (!this.measures[i].isSystemDefined())
			{
				vals[c] = this.measures[i].new MeasureValue();
				++c;
			}
		}
		
		return vals;
	}

	
	/**
	 * Processes the data which was fed to the engine.
	 * <p>
	 * This method should be called after the data has been exhausted.
	 * The processing consists of total calculations and crosstab table creation.
	 * 
	 * @throws JRException
	 */
	public void processData() throws JRException
	{
		if (!this.processed)
		{
			if (this.dataCount > 0)
			{
				if (this.allBuckets[this.rowBucketCount - 1].computeTotal() || this.allBuckets[this.allBuckets.length - 1].computeTotal())
				{
					computeTotals(this.bucketValueMap);
				}

				createCrosstab();
			}
			
			this.processed = true;
		}
	}

	
	/**
	 * Checks whether there is any data accumulated by the engine.
	 * 
	 * @return <code>true</code> iff the engine has any accumulated data
	 */
	public boolean hasData()
	{
		return this.dataCount > 0;
	}
	
	
	/**
	 * Returns the crosstab column headers.
	 * <p>
	 * {@link #processData() processData()} has to be called before this.
	 * 
	 * @return the crosstab column headers
	 */
	public HeaderCell[][] getColumnHeaders()
	{
		return this.colHeaders;
	}
	
	
	/**
	 * Returns the crosstab row headers.
	 * <p>
	 * {@link #processData() processData()} has to be called before this.
	 * 
	 * @return the crosstab row headers
	 */
	public HeaderCell[][] getRowHeaders()
	{
		return this.rowHeaders;
	}
	
	
	/**
	 * Returns the crosstab data cells.
	 * <p>
	 * {@link #processData() processData()} has to be called before this.
	 * 
	 * @return the crosstab data cells
	 */
	public CrosstabCell[][] getCrosstabCells()
	{
		return this.cells;
	}
	
	
	/**
	 * Returns the measure values for a set of bucket values.
	 * 
	 * @param bucketValues the bucket values
	 * @return the measure values corresponding to the bucket values
	 */
	public MeasureValue[] getMeasureValues(Bucket[] bucketValues)
	{
		BucketMap map = this.bucketValueMap;
		
		for (int i = 0; map != null && i < this.allBuckets.length - 1; ++i)
		{
			map = (BucketMap) map.get(bucketValues[i]);
		}
		
		return map == null ? null : (MeasureValue[]) map.get(bucketValues[this.allBuckets.length - 1]);
	}

	protected MeasureValue[] getUserMeasureValues(MeasureValue[] values)
	{
		MeasureValue[] vals = new MeasureValue[this.origMeasureCount];
		
		for (int c = 0, i = 0; i < this.measures.length; ++i)
		{
			if (!this.measures[i].isSystemDefined())
			{
				vals[c] = values[i];
				++c;
			}
		}
		
		return vals;
	}

	
	/**
	 * Returns the grand total measure values.
	 * 
	 * @return the grand total measure values
	 */
	public MeasureValue[] getGrandTotals()
	{
		BucketMap map = this.bucketValueMap;
		
		for (int i = 0; map != null && i < this.allBuckets.length - 1; ++i)
		{
			map = (BucketMap) map.getTotalEntry().getValue();
		}
		
		return map == null ? null : (MeasureValue[]) map.getTotalEntry().getValue();
	}

	
	protected void computeTotals(BucketMap bucketMap) throws JRException
	{
		byte dimension = bucketMap.level < this.rowBucketCount ? DIMENSION_ROW : DIMENSION_COLUMN;
		
		if (dimension == DIMENSION_COLUMN && !this.allBuckets[this.allBuckets.length - 1].computeTotal())
		{
			return;
		}
		
		if (!bucketMap.last)
		{
			for (Iterator it = bucketMap.entryIterator(); it.hasNext();)
			{
				Map.Entry entry = (Map.Entry) it.next();

				computeTotals((BucketMap) entry.getValue());
			}
		}
		
		if (this.allBuckets[bucketMap.level].computeTotal())
		{
			if (dimension == DIMENSION_COLUMN)
			{
				computeColumnTotal(bucketMap);
			}
			else
			{
				computeRowTotals(bucketMap);
			}
		}
	}


	protected void sumVals(MeasureValue[] totals, MeasureValue[] vals) throws JRException
	{
		for (int i = 0; i < this.measures.length; i++)
		{
			totals[i].addValue(vals[i]);
		}
	}
	
	protected void computeColumnTotal(BucketMap bucketMap) throws JRException
	{
		MeasureValue[] totals = initMeasureValues();
		
		for (Iterator it = bucketMap.entryIterator(); it.hasNext();)
		{
			Map.Entry entry = (Map.Entry) it.next();
			
			for (int i = bucketMap.level + 1; i < this.allBuckets.length; ++i)
			{
				entry = ((BucketMap) entry.getValue()).getTotalEntry();
			}
			
			sumVals(totals, (MeasureValue[]) entry.getValue());
		}
				
		for (int i = bucketMap.level + 1; i < this.allBuckets.length; ++i)
		{
			bucketMap = bucketMap.addTotalNextMap();
		}
		
		bucketMap.addTotalEntry(totals);
	}


	protected void computeRowTotals(BucketMap bucketMap) throws JRException
	{
		BucketListMap totals = createCollectBucketMap(this.rowBucketCount);
		
		for (Iterator it = bucketMap.entryIterator(); it.hasNext();)
		{
			Map.Entry entry = (Map.Entry) it.next();
			
			for (int i = bucketMap.level + 1; i < this.rowBucketCount; ++i)
			{
				entry = ((BucketMap) entry.getValue()).getTotalEntry();
			}
			
			totals.collectVals((BucketMap) entry.getValue(), true);			
		}
		
		BucketMap totalBucketMap = bucketMap;
		for (int i = bucketMap.level + 1; i < this.rowBucketCount; ++i)
		{
			totalBucketMap = totalBucketMap.addTotalNextMap();
		}
		
		totalBucketMap.addTotalEntry(totals);
	}

	
	static protected class MapEntry implements Map.Entry, Comparable
	{
		final Bucket key;

		final Object value;
		
		MapEntry(Bucket key, Object value)
		{
			this.key = key;
			this.value = value;
		}

		public Object getKey()
		{
			return this.key;
		}

		public Object getValue()
		{
			return this.value;
		}

		public Object setValue(Object value)
		{
			throw new UnsupportedOperationException();
		}

		public int compareTo(Object o)
		{
			return this.key.compareTo(((MapEntry) o).key);
		}
		
		public String toString()
		{
			return this.key + "=" + this.value;
		}
	}
	
	protected abstract class BucketMap
	{
		final int level;
		final boolean last;
		final Bucket totalKey;

		BucketMap(int level)
		{
			this.level = level;
			this.last = level == BucketingService.this.allBuckets.length - 1;
			this.totalKey = BucketingService.this.allBuckets[level].VALUE_TOTAL;
		}

		BucketMap addTotalNextMap()
		{
			BucketMap nextMap = createBucketMap(this.level + 1);
			addTotalEntry(nextMap);
			return nextMap;
		}
		
		abstract void set(Bucket key, Object value);

		abstract void clear();

		abstract Iterator entryIterator();

		abstract Object get(Bucket key);

		abstract MeasureValue[] insertMeasureValues(Bucket[] bucketValues);

/*		abstract void fillKeys(Collection collectedKeys);*/

		abstract void addTotalEntry(Object val);

		abstract int size();
		
		abstract Map.Entry getTotalEntry();
	}

	protected class BucketTreeMap extends BucketMap
	{
		TreeMap map;

		BucketTreeMap(int level)
		{
			super(level);

			this.map = new TreeMap();
		}
		
		void clear()
		{
			this.map.clear();
		}

		Iterator entryIterator()
		{
			return this.map.entrySet().iterator();
		}

		Object get(Bucket key)
		{
			return this.map.get(key);
		}

		MeasureValue[] insertMeasureValues(Bucket[] bucketValues)
		{
			BucketTreeMap levelMap = (BucketTreeMap) BucketingService.this.bucketValueMap;
			for (int i = 0; i < bucketValues.length - 1; i++)
			{
				BucketTreeMap nextMap = (BucketTreeMap) levelMap.get(bucketValues[i]);
				if (nextMap == null)
				{
					nextMap = new BucketTreeMap(i + 1);
					levelMap.map.put(bucketValues[i], nextMap);
				}

				levelMap = nextMap;
			}

			MeasureValue[] values = (MeasureValue[]) levelMap.get(bucketValues[bucketValues.length - 1]);
			if (values == null)
			{
				values = initMeasureValues();
				levelMap.map.put(bucketValues[bucketValues.length - 1], values);
				
				bucketMeasuresCreated();
			}

			return values;
		}

		int size()
		{
			return this.map.size();
		}

		void addTotalEntry(Object value)
		{
			this.map.put(this.totalKey, value);
		}
		
		Map.Entry getTotalEntry()
		{
			Object value = get(this.totalKey);
			return value == null ? null : new MapEntry(this.totalKey, value);
		}
		
		
		public String toString()
		{
			return this.map.toString();
		}

		void set(Bucket key, Object value)
		{
			this.map.put(key, value);
		}
	}

	protected class BucketListMap extends BucketMap
	{
		List entries;

		BucketListMap(int level, boolean linked)
		{
			super(level);

			if (linked)
			{
				this.entries = new LinkedList();
			}
			else
			{
				this.entries = new ArrayList();
			}
		}

		void clear()
		{
			this.entries.clear();
		}
		
		Iterator entryIterator()
		{
			return this.entries.iterator();
		}

		private void add(Bucket key, Object value)
		{
			this.entries.add(new MapEntry(key, value));
		}

		Object get(Bucket key)
		{
			int idx = Collections.binarySearch(this.entries, new MapEntry(key, null));
			return idx >= 0 ? ((MapEntry) this.entries.get(idx)).value : null;
		}

		MeasureValue[] insertMeasureValues(Bucket[] bucketValues)
		{
			int i = 0;
			Object levelObj = this;
			BucketListMap map = null;
			while (i < BucketingService.this.allBuckets.length)
			{
				map = (BucketListMap) levelObj;
				int size = map.entries.size();
				if (size == 0)
				{
					break;
				}

				MapEntry lastEntry = (MapEntry) map.entries.get(size - 1);
				if (!lastEntry.key.equals(bucketValues[i]))
				{
					break;
				}
				
				++i;
				levelObj = lastEntry.value;
			}

			if (i == BucketingService.this.allBuckets.length)
			{
				return (MeasureValue[]) levelObj;
			}

			while (i < BucketingService.this.allBuckets.length - 1)
			{
				BucketListMap nextMap = new BucketListMap(i + 1, false);
				map.add(bucketValues[i], nextMap);
				map = nextMap;
				++i;
			}

			MeasureValue[] values = initMeasureValues();
			map.add(bucketValues[i], values);
			
			bucketMeasuresCreated();

			return values;
		}

		int size()
		{
			return this.entries.size();
		}

		void addTotalEntry(Object value)
		{
			add(this.totalKey, value);
		}

		Map.Entry getTotalEntry()
		{
			MapEntry lastEntry = (MapEntry) this.entries.get(this.entries.size() - 1);
			if (lastEntry.key.isTotal())
			{
				return lastEntry;
			}
			
			return null;
		}

		void set(Bucket key, Object value)
		{
			MapEntry mapEntry = new MapEntry(key, value);
			int idx = Collections.binarySearch(this.entries, mapEntry);
			int ins = -idx - 1;
			this.entries.add(ins, mapEntry);
		}

		
		void collectVals(BucketMap map, boolean sum) throws JRException
		{
			ListIterator totalIt = this.entries.listIterator();
			MapEntry totalItEntry = totalIt.hasNext() ? (MapEntry) totalIt.next() : null;
			
			Iterator it = map.entryIterator();
			Map.Entry entry = it.hasNext() ? (Map.Entry) it.next() : null;
			while(entry != null)
			{
				Bucket key = (Bucket) entry.getKey();
				
				int compare = totalItEntry == null ? -1 : key.compareTo(totalItEntry.key);
				if (compare <= 0)
				{
					Object addVal = null;
					
					if (this.last)
					{
						if (sum)
						{
							MeasureValue[] totalVals = compare == 0 ? (MeasureValue[]) totalItEntry.value : null;

							if (totalVals == null)
							{
								totalVals = initMeasureValues();
								addVal = totalVals;
							}

							sumVals(totalVals, (MeasureValue[]) entry.getValue());
						}
					}
					else
					{
						BucketListMap nextTotals = compare == 0 ? (BucketListMap) totalItEntry.value : null;
						
						if (nextTotals == null)
						{
							nextTotals = createCollectBucketMap(this.level + 1);
							addVal = nextTotals;
						}
						
						nextTotals.collectVals((BucketMap) entry.getValue(), sum);
					}
					
					if (compare < 0)
					{
						if (totalItEntry != null)
						{
							totalIt.previous();
						}
						totalIt.add(new MapEntry(key, addVal));
						if (totalItEntry != null)
						{
							totalIt.next();
						}
					}
					
					entry = it.hasNext() ? (Map.Entry) it.next() : null;
				}
				
				if (compare >= 0)
				{
					totalItEntry = totalIt.hasNext() ? (MapEntry) totalIt.next() : null;
				}
			}
		}
		
		public String toString()
		{
			StringBuffer sb = new StringBuffer();
			sb.append('{');
			for (Iterator it = this.entries.iterator(); it.hasNext();)
			{
				MapEntry entry = (MapEntry) it.next();
				sb.append(entry);
				if (it.hasNext())
				{
					sb.append(", ");
				}
			}
			sb.append('}');
			return sb.toString();
		}
	}

	
	
	protected void createCrosstab() throws JRException
	{
		CollectedList[] collectedHeaders = new CollectedList[BucketingService.DIMENSIONS];
		collectedHeaders[DIMENSION_ROW] = createHeadersList(DIMENSION_ROW, this.bucketValueMap, 0, false);
		
		BucketListMap collectedCols;
		if (this.allBuckets[0].computeTotal())
		{
			BucketMap map = this.bucketValueMap;
			for (int i = 0; i < this.rowBucketCount; ++i)
			{
				map = (BucketMap) map.getTotalEntry().getValue();
			}
			collectedCols = (BucketListMap) map;
		}
		else
		{
			collectedCols = createCollectBucketMap(this.rowBucketCount);
			collectCols(collectedCols, this.bucketValueMap);
		}
		collectedHeaders[DIMENSION_COLUMN] = createHeadersList(DIMENSION_COLUMN, collectedCols, 0, false);
		
		int rowBuckets = collectedHeaders[BucketingService.DIMENSION_ROW].span;
		int colBuckets = collectedHeaders[BucketingService.DIMENSION_COLUMN].span;

		int bucketMeasureCount = rowBuckets * colBuckets * this.origMeasureCount;
		checkBucketMeasureCount(bucketMeasureCount);
		
		this.colHeaders = createHeaders(BucketingService.DIMENSION_COLUMN, collectedHeaders);
		this.rowHeaders = createHeaders(BucketingService.DIMENSION_ROW, collectedHeaders);
		
		this.cells = new CrosstabCell[rowBuckets][colBuckets];
		fillCells(collectedHeaders, this.bucketValueMap, 0, new int[]{0, 0}, new ArrayList(), new ArrayList());
	}


	protected void checkBucketMeasureCount(int bucketMeasureCount)
	{
		if (this.bucketMeasureLimit > 0 && bucketMeasureCount > this.bucketMeasureLimit)
		{
			throw new JRRuntimeException("Crosstab bucket/measure limit (" + this.bucketMeasureLimit + ") exceeded.");
		}
	}


	protected void collectCols(BucketListMap collectedCols, BucketMap bucketMap) throws JRException
	{
		if (this.allBuckets[bucketMap.level].computeTotal())
		{
			BucketMap map = bucketMap;
			for (int i = bucketMap.level; i < this.rowBucketCount; ++i)
			{
				map = (BucketMap) map.getTotalEntry().getValue();
			}
			collectedCols.collectVals(map, false);
			
			return;
		}
		
		for (Iterator it = bucketMap.entryIterator(); it.hasNext();)
		{
			Map.Entry entry = (Map.Entry) it.next();
			BucketMap nextMap = (BucketMap) entry.getValue();
			if (bucketMap.level == this.rowBucketCount - 1)
			{
				collectedCols.collectVals(nextMap, false);
			}
			else
			{
				collectCols(collectedCols, nextMap);
			}
		}
	}
	
	
	protected CollectedList createHeadersList(byte dimension, BucketMap bucketMap, int level, boolean total)
	{
		CollectedList headers = new CollectedList();

		for (Iterator it = bucketMap.entryIterator(); it.hasNext();)
		{
			Map.Entry entry = (Map.Entry) it.next();
			Bucket bucketValue = (Bucket) entry.getKey();

			boolean totalBucket = bucketValue.isTotal();
			byte totalPosition = this.allBuckets[bucketMap.level].getTotalPosition();
			boolean createHeader = !totalBucket || total || totalPosition != BucketDefinition.TOTAL_POSITION_NONE;

			if (createHeader)
			{
				CollectedList nextHeaders;
				if (level + 1 < this.buckets[dimension].length)
				{
					BucketMap nextMap = (BucketMap) entry.getValue();
					nextHeaders = createHeadersList(dimension, nextMap, level + 1, total || totalBucket);
				}
				else
				{
					nextHeaders = new CollectedList();
					nextHeaders.span = 1;
				}
				nextHeaders.key = bucketValue;

				if (totalBucket)
				{
					if (totalPosition == BucketDefinition.TOTAL_POSITION_START)
					{
						headers.addFirst(nextHeaders);
					}
					else
					{
						headers.add(nextHeaders);
					}
				}
				else
				{
					headers.add(nextHeaders);
				}
			}
		}

		if (headers.span == 0)
		{
			headers.span = 1;
		}

		return headers;
	}
	
	protected HeaderCell[][] createHeaders(byte dimension, CollectedList[] headersLists)
	{
		HeaderCell[][] headers = new HeaderCell[this.buckets[dimension].length][headersLists[dimension].span];
		
		List vals = new ArrayList();
		fillHeaders(dimension, headers, 0, 0, headersLists[dimension], vals);
		
		return headers;
	}

	
	protected void fillHeaders(byte dimension, HeaderCell[][] headers, int level, int col, CollectedList list, List vals)
	{
		if (level == this.buckets[dimension].length)
		{
			return;
		}
		
		for (Iterator it = list.iterator(); it.hasNext();)
		{
			CollectedList subList = (CollectedList) it.next();
			
			vals.add(subList.key);
			
			int depthSpan = subList.key.isTotal() ? this.buckets[dimension].length - level : 1;
			Bucket[] values = new Bucket[this.buckets[dimension].length];
			vals.toArray(values);
			
			headers[level][col] = new HeaderCell(values, subList.span, depthSpan);
			
			if (!subList.key.isTotal())
			{
				fillHeaders(dimension, headers, level + 1, col, subList, vals);
			}
			
			col += subList.span;
			vals.remove(vals.size() - 1);
		}
	}


	protected void fillCells(CollectedList[] collectedHeaders, BucketMap bucketMap, int level, int[] pos, List vals, List bucketMaps)
	{
		bucketMaps.add(bucketMap);
		
		byte dimension = level < this.rowBucketCount ? DIMENSION_ROW : DIMENSION_COLUMN;
		boolean last = level == this.allBuckets.length - 1;

		CollectedList[] nextCollected = null;
		if (!last)
		{
			nextCollected = new CollectedList[DIMENSIONS];
			for (int d = 0; d < DIMENSIONS; ++d)
			{
				if (d != dimension)
				{
					nextCollected[d] = collectedHeaders[d];
				}
			}
		}
		
		boolean incrementRow = level == this.buckets[BucketingService.DIMENSION_ROW].length - 1;
				
		CollectedList collectedList = collectedHeaders[dimension];
		
		Iterator bucketIt = bucketMap == null ? null : bucketMap.entryIterator();
		Map.Entry bucketItEntry = bucketIt != null && bucketIt.hasNext() ? (Map.Entry) bucketIt.next() : null;
		for (Iterator it = collectedList.iterator(); it.hasNext();)
		{
			CollectedList list = (CollectedList) it.next();
			
			Map.Entry bucketEntry = null;
			if (list.key.isTotal())
			{
				if (bucketMap != null)
				{
					bucketEntry = bucketMap.getTotalEntry();
				}
			}
			else
			{
				if (bucketItEntry != null && bucketItEntry.getKey().equals(list.key))
				{
					bucketEntry = bucketItEntry;
					bucketItEntry = bucketIt.hasNext() ? (Map.Entry) bucketIt.next() : null;
				}
			}
			
			vals.add(list.key);
			if (last)
			{
				fillCell(pos, vals, bucketMaps, bucketEntry);
			}
			else
			{				
				nextCollected[dimension] = list;
				BucketMap nextMap = bucketEntry == null ? null : (BucketMap) bucketEntry.getValue();
				
				fillCells(nextCollected, nextMap, level + 1, pos, vals, bucketMaps);
			}
			vals.remove(vals.size() - 1);
				
			if (incrementRow)
			{
				++pos[0];
				pos[1] = 0;
			}
		}
		
		bucketMaps.remove(bucketMaps.size() - 1);
	}


	protected void fillCell(int[] pos, List vals, List bucketMaps, Map.Entry bucketEntry)
	{
		Iterator valsIt = vals.iterator();
		Bucket[] rowValues = new Bucket[this.buckets[BucketingService.DIMENSION_ROW].length];
		for (int i = 0; i < rowValues.length; i++)
		{
			rowValues[i] = (Bucket) valsIt.next();
		}
		
		Bucket[] columnValues = new Bucket[this.buckets[BucketingService.DIMENSION_COLUMN].length];
		for (int i = 0; i < columnValues.length; i++)
		{
			columnValues[i] = (Bucket) valsIt.next();
		}
		
		MeasureValue[] measureVals = bucketEntry == null ? this.zeroUserMeasureValues : getUserMeasureValues((MeasureValue[]) bucketEntry.getValue());
		MeasureValue[][][] totals = retrieveTotals(vals, bucketMaps);
		this.cells[pos[0]][pos[1]] = new CrosstabCell(rowValues, columnValues, measureVals, totals);
		++pos[1];
	}
	
	
	protected MeasureValue[][][] retrieveTotals(List vals, List bucketMaps)
	{
		MeasureValue[][][] totals = new MeasureValue[this.rowBucketCount + 1][this.colBucketCount + 1][];
		
		for (int row = this.rowRetrTotalMax; row >= this.rowRetrTotalMin; --row)
		{
			if (!this.rowRetrTotals[row])
			{
				continue;
			}
			
			BucketMap rowMap = (BucketMap) bucketMaps.get(row);
			for (int i = row; rowMap != null && i < this.rowBucketCount; ++i)
			{
				Entry totalEntry = rowMap.getTotalEntry();
				rowMap = totalEntry == null ? null : (BucketMap) totalEntry.getValue();
			}

			for (int col = 0; col <= this.rowRetrColMax[row]; ++col)
			{
				BucketMap colMap = rowMap;
				
				if (col < this.colBucketCount - 1)
				{
					if (row == this.rowBucketCount)
					{
						rowMap = (BucketMap) bucketMaps.get(this.rowBucketCount + col + 1);
					}
					else if (rowMap != null)
					{
						rowMap = (BucketMap) rowMap.get((Bucket) vals.get(this.rowBucketCount + col));
					}
				}
				
				if (!this.retrieveTotal[row][col])
				{
					continue;
				}
				
				for (int i = col + 1; colMap != null && i < this.colBucketCount; ++i)
				{
					colMap = (BucketMap) colMap.getTotalEntry().getValue();
				}
				
				if (colMap != null)
				{
					if (col == this.colBucketCount)
					{
						MeasureValue[] measureValues = (MeasureValue[]) colMap.get((Bucket) vals.get(this.rowBucketCount + this.colBucketCount - 1));
						if (measureValues != null)
						{
							totals[row][col] = getUserMeasureValues(measureValues);
						}
					}
					else
					{
						Map.Entry totalEntry = colMap.getTotalEntry();
						if (totalEntry != null)
						{
							MeasureValue[] totalValues = (MeasureValue[]) totalEntry.getValue();
							totals[row][col] = getUserMeasureValues(totalValues);
						}
					}
				}
				
				if (totals[row][col] == null)
				{
					totals[row][col] = this.zeroUserMeasureValues;
				}
			}
		}

		return totals;
	}
	
	protected static class CollectedList extends LinkedList
	{
		private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

		int span;
		Bucket key;
		
		CollectedList()
		{
			super();
			
			this.span = 0;
		}

		public boolean add(Object o)
		{
			boolean added = super.add(o);
			
			incrementSpan(o);
			
			return added;
		}

		public void addFirst(Object o)
		{
			super.addFirst(o);
			
			incrementSpan(o);
		}

		public void addLast(Object o)
		{
			super.add(o);

			incrementSpan(o);
		}

		private void incrementSpan(Object o)
		{
			if (o != null && o instanceof CollectedList)
			{
				this.span += ((CollectedList) o).span;
			}
			else
			{
				this.span += 1;
			}
		}
		
		public String toString()
		{
			return this.key + "/" + this.span + ": " + super.toString();
		}
	}
}
