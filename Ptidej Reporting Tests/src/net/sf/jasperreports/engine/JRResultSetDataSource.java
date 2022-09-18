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

/*
 * Contributors:
 * S. Brett Sutton - bsutton@idatam.com.au
 */
package net.sf.jasperreports.engine;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.util.JRImageLoader;


/**
 * An implementation of a data source that uses a supplied <tt>ResultSet</tt>.
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRResultSetDataSource.java,v 1.1 2008/09/29 16:20:42 guehene Exp $
 */
public class JRResultSetDataSource implements JRDataSource
{


	private static final String INDEXED_COLUMN_PREFIX = "COLUMN_";
	private static final int INDEXED_COLUMN_PREFIX_LENGTH = INDEXED_COLUMN_PREFIX.length();
	
	/**
	 *
	 */
	private ResultSet resultSet = null;
	private Map columnIndexMap = new HashMap();
	

	/**
	 *
	 */
	public JRResultSetDataSource(ResultSet rs)
	{
		this.resultSet = rs;
	}


	/**
	 *
	 */
	public boolean next() throws JRException
	{
		boolean hasNext = false;
		
		if (this.resultSet != null)
		{
			try
			{
				hasNext = this.resultSet.next();
			}
			catch (SQLException e)
			{
				throw new JRException("Unable to get next record.", e);
			}
		}
		
		return hasNext;
	}


	/**
	 *
	 */
	public Object getFieldValue(JRField field) throws JRException
	{
		Object objValue = null;

		if (field != null && this.resultSet != null)
		{
			Integer columnIndex = getColumnIndex(field.getName());
			Class clazz = field.getValueClass();

			try
			{
				if (clazz.equals(java.lang.Boolean.class))
				{
					objValue = this.resultSet.getBoolean(columnIndex.intValue()) ? Boolean.TRUE : Boolean.FALSE;
				}
				else if (clazz.equals(java.lang.Byte.class))
				{
					objValue = new Byte(this.resultSet.getByte(columnIndex.intValue()));
					if(this.resultSet.wasNull())
					{
						objValue = null;
					}
				}
				else if (
					clazz.equals(java.util.Date.class)
					|| clazz.equals(java.sql.Date.class)
					)
				{
					objValue = this.resultSet.getDate(columnIndex.intValue());
					if(this.resultSet.wasNull())
					{
						objValue = null;
					}
				}
				else if (clazz.equals(java.sql.Timestamp.class))
				{
					objValue = this.resultSet.getTimestamp(columnIndex.intValue());
					if(this.resultSet.wasNull())
					{
						objValue = null;
					}
				}
				else if (clazz.equals(java.sql.Time.class))
				{
					objValue = this.resultSet.getTime(columnIndex.intValue());
					if(this.resultSet.wasNull())
					{
						objValue = null;
					}
				}
				else if (clazz.equals(java.lang.Double.class))
				{
					objValue = Double.valueOf(this.resultSet.getDouble(columnIndex.intValue()));
					if(this.resultSet.wasNull())
					{
						objValue = null;
					}
				}
				else if (clazz.equals(java.lang.Float.class))
				{
					objValue = Float.valueOf(this.resultSet.getFloat(columnIndex.intValue()));
					if(this.resultSet.wasNull())
					{
						objValue = null;
					}
				}
				else if (clazz.equals(java.lang.Integer.class))
				{
					objValue = Integer.valueOf(this.resultSet.getInt(columnIndex.intValue()));
					if(this.resultSet.wasNull())
					{
						objValue = null;
					}
				}
				else if (clazz.equals(java.io.InputStream.class))
				{
					byte[] bytes = readBytes(columnIndex);
					
					if(bytes == null)
					{
						objValue = null;
					}
					else
					{
						objValue = new ByteArrayInputStream(bytes);
					}					
				}
				else if (clazz.equals(java.lang.Long.class))
				{
					objValue = Long.valueOf(this.resultSet.getLong(columnIndex.intValue()));
					if(this.resultSet.wasNull())
					{
						objValue = null;
					}
				}
				else if (clazz.equals(java.lang.Short.class))
				{
					objValue = new Short(this.resultSet.getShort(columnIndex.intValue()));
					if(this.resultSet.wasNull())
					{
						objValue = null;
					}
				}
				else if (clazz.equals(java.math.BigDecimal.class))
				{
					objValue = this.resultSet.getBigDecimal(columnIndex.intValue());
					if(this.resultSet.wasNull())
					{
						objValue = null;
					}
				}
				else if (clazz.equals(java.lang.String.class))
				{
					int columnType = this.resultSet.getMetaData().getColumnType(columnIndex.intValue());
					switch (columnType)
					{
						case Types.CLOB:
							Clob clob = this.resultSet.getClob(columnIndex.intValue());
							if (this.resultSet.wasNull())
							{
								objValue = null;
							}
							else
							{
								objValue = clobToString(clob);
							}
							break;
							
						default:
							objValue = this.resultSet.getString(columnIndex.intValue());
							if(this.resultSet.wasNull())
							{
								objValue = null;
							}
							break;
					}
				}
				else if (clazz.equals(Clob.class))
				{
					objValue = this.resultSet.getClob(columnIndex.intValue());
					if(this.resultSet.wasNull())
					{
						objValue = null;
					}
				}
				else if (clazz.equals(Reader.class))
				{
					Reader reader = null;
					long size = -1;
					
					int columnType = this.resultSet.getMetaData().getColumnType(columnIndex.intValue());
					switch (columnType)
					{
						case Types.CLOB:
							Clob clob = this.resultSet.getClob(columnIndex.intValue());
							if (!this.resultSet.wasNull())
							{
								reader = clob.getCharacterStream();
								size = clob.length();
							}
							break;
							
						default:
							reader = this.resultSet.getCharacterStream(columnIndex.intValue());
							if (this.resultSet.wasNull())
							{
								reader = null; 
							}
					}
					
					if (reader == null)
					{
						objValue = null;
					}
					else
					{
						objValue = getArrayReader(reader, size);
					}
				}
				else if (clazz.equals(Blob.class))
				{
					objValue = this.resultSet.getBlob(columnIndex.intValue());
					if(this.resultSet.wasNull())
					{
						objValue = null;
					}
				}
				else if (clazz.equals(Image.class))
				{
					byte[] bytes = readBytes(columnIndex);
					
					if(bytes == null)
					{
						objValue = null;
					}
					else
					{
						objValue = JRImageLoader.loadImage(bytes);
					}					
				}
				else
				{
					objValue = this.resultSet.getObject(columnIndex.intValue());
				}
			}
			catch (Exception e)
			{
				throw new JRException("Unable to get value for field '" + field.getName() + "' of class '" + clazz.getName() + "'", e);
			}
		}
		
		return objValue;
	}





	/**
	 *
	 */
	private Integer getColumnIndex(String fieldName) throws JRException
	{
		Integer columnIndex = (Integer)this.columnIndexMap.get(fieldName);
		if (columnIndex == null)
		{
			try
			{
				columnIndex = searchColumnByName(fieldName);
				
				if (columnIndex == null)
				{
					columnIndex = searchColumnByLabel(fieldName);
				}
				
				if (columnIndex == null && fieldName.startsWith(INDEXED_COLUMN_PREFIX))
				{
					columnIndex = Integer.valueOf(fieldName.substring(INDEXED_COLUMN_PREFIX_LENGTH));
					if (
						columnIndex.intValue() <= 0
						|| columnIndex.intValue() > this.resultSet.getMetaData().getColumnCount()
						)
					{
						throw new JRException("Column index out of range : " + columnIndex);
					}
				}
				
				if (columnIndex == null)
				{
					throw new JRException("Unknown column name : " + fieldName);
				}
			}
			catch (SQLException e)
			{
				throw new JRException("Unable to retrieve result set metadata.", e);
			}

			this.columnIndexMap.put(fieldName, columnIndex);
		}
		
		return columnIndex;
	}


	protected Integer searchColumnByName(String fieldName) throws SQLException
	{
		Integer columnIndex = null;
		ResultSetMetaData metadata = this.resultSet.getMetaData();
		for(int i = 1; i <= metadata.getColumnCount(); i++)
		{
			String columnName = metadata.getColumnName(i);
			if (fieldName.equalsIgnoreCase(columnName))
			{
				columnIndex = Integer.valueOf(i);
				break;
			}
		}
		return columnIndex;
	}


	protected Integer searchColumnByLabel(String fieldName) throws SQLException
	{
		Integer columnIndex = null;
		ResultSetMetaData metadata = this.resultSet.getMetaData();
		for(int i = 1; i <= metadata.getColumnCount(); i++)
		{
			String columnLabel = metadata.getColumnLabel(i);
			if (columnLabel != null && fieldName.equalsIgnoreCase(columnLabel))
			{
				columnIndex = Integer.valueOf(i);
				break;
			}
		}
		return columnIndex;
	}


	protected String clobToString(Clob clob) throws JRException
	{
		try
		{
			int bufSize = 8192;
			char[] buf = new char[bufSize];
			
			Reader reader = new BufferedReader(clob.getCharacterStream(), bufSize);
			StringBuffer str = new StringBuffer((int) clob.length());
			
			for (int read = reader.read(buf); read > 0; read = reader.read(buf))
			{
				str.append(buf, 0, read);
			}

			return str.toString();
		}
		catch (SQLException e)
		{
			throw new JRException("Unable to read clob value", e);
		}
		catch (IOException e)
		{
			throw new JRException("Unable to read clob value", e);
		}
	}

	protected CharArrayReader getArrayReader(Reader reader, long size) throws IOException
	{
		char[] buf = new char[8192];
		CharArrayWriter bufWriter = new CharArrayWriter((size > 0) ? (int) size : 8192);
		
		BufferedReader bufReader = new BufferedReader(reader, 8192);
		for (int read = bufReader.read(buf); read > 0; read = bufReader.read(buf))
		{
			bufWriter.write(buf, 0, read);
		}
		bufWriter.flush();
		
		return new CharArrayReader(bufWriter.toCharArray());
	}

	protected byte[] readBytes(Integer columnIndex) throws SQLException, IOException
	{
		InputStream is = null;
		long size = -1;
		
		int columnType = this.resultSet.getMetaData().getColumnType(columnIndex.intValue());
		switch (columnType)
		{
			case Types.BLOB:
				Blob blob = this.resultSet.getBlob(columnIndex.intValue());
				if (!this.resultSet.wasNull())
				{
					is = blob.getBinaryStream();
					size = blob.length();
				}
				break;
				
			default:
				is = this.resultSet.getBinaryStream(columnIndex.intValue());
				if (this.resultSet.wasNull())
				{
					is = null; 
				}
		}
		
		byte[] bytes = null;
		if (is != null)
		{
			bytes = readBytes(is, size);
		}
		
		return bytes;
	}

	protected byte[] readBytes(InputStream is, long size) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream(size > 0 ? (int) size : 1000);
		byte[] bytes = new byte[1000];
		int ln = 0;
		try
		{
			while ((ln = is.read(bytes)) > 0)
			{
				baos.write(bytes, 0, ln);
			}
			baos.flush();
		}
		finally
		{
			try
			{
				baos.close();
			}
			catch(IOException e)
			{
			}
		}
		return baos.toByteArray();
	}
}
