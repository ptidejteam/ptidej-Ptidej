/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.io;

import infovis.Table;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;


/**
 * Abstract factory of table readers.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class AbstractReaderFactory {
    ArrayList creators = new ArrayList();

    /**
     * Constructor for TableReaderFactory.
     */
    public AbstractReaderFactory() {
	addDefaultCreators();
    }

    /**
     * Adds the default creators.
     */
    protected void addDefaultCreators() {
    }

    /**
     * Adds a creator of table reader.
     *
     * @param c the Creator.
     */
    public void add(Creator c) {
	this.creators.add(c);
    }

    /**
     * Removes a creator of table reader.
     *
     * @param c the Creator.
     *
     * @return <code>true</code> if the Creator was removed.
     */
    public boolean remove(Creator c) {
	return this.creators.remove(c);
    }

    /**
     * Returns an iterator over the added creators.
     *
     * @return an iterator over the added creators.
     */
    public Iterator iterator() {
	return this.creators.iterator();
    }

    /**
     * Returns the Creator at a specified index.
     *
     * @param index the index.
     *
     * @return the Creator at a specified index or null.
     */
    public Creator getCreatorAt(int index) {
	return (Creator)this.creators.get(index);
    }

    /**
     * Returns an <code>AbstractReader</code> able to read the specified resource name
     *  or <code>null</code>.
     *
     * @param name the resource name.
     * @param table the table.
     *
     * @return an <code>AbstractReader</code> able to read the specified resource name
     *  or <code>null</code>.
     */
    public AbstractReader create(String name, Table table) {
	AbstractReader ret = null;
	for (int i = 0; i < this.creators.size(); i++) {
	    Creator c = getCreatorAt(i);
	    ret = c.create(name, table);
	    if (ret != null)
		break;
	}
	return ret;
    }
    
    public boolean tryRead(String name, Table table) {
        AbstractReader ret = null;
        for (int i = 0; i < this.creators.size(); i++) {
            Creator c = getCreatorAt(i);
            ret = c.create(name, table);
            if (ret != null) {
                try {
                    boolean ok = ret.load();
                    if (ok)
                        return ok;
                }
                catch(Exception e) {
                }
            }
            
        }
        return false;
    }

    /**
     * Interface for Table Reader creators.
     */
    public interface Creator {
	public AbstractReader create(String name, Table table);
    }

    public abstract static class AbstractCreator implements Creator {
	String suffix;

	public AbstractCreator(String suffix) {
	    this.suffix = suffix;
	}

	public BufferedReader open(String name, boolean decompress)
	    throws IOException, FileNotFoundException {
	    BufferedReader in = null;
	    InputStream is = null;
	    if (name.indexOf(':') != -1) {
		try {
		    URL url = new URL(name);
		    is = url.openStream();	
		}
		catch(MalformedURLException e) {
		}
		catch(IOException e) {
		}
	    }
	    if (is == null) {
		is = new FileInputStream(name);
	    }
	    if (decompress) {
		is = new GZIPInputStream(is);
	    }
	    in = new BufferedReader(new InputStreamReader(is));
			
	    return in;
	}

	public AbstractReader create(String name, Table table) {
	    boolean decompress = false;
	    String  realName = name;
	    if (name.endsWith(".gz") || name.endsWith(".Z")) {
		decompress = true;
		realName = name.substring(0, name.lastIndexOf('.'));
	    }
	    if (!realName.endsWith(this.suffix))
		return null;
	    try {
		return doCreate(name, table, decompress);
	    } catch (FileNotFoundException e) {
		return null;
	    } catch (IOException e) {
		return null;
	    }
	}

	public abstract AbstractReader doCreate(String name, Table table,
						boolean decompress)
	    throws IOException, 
		   FileNotFoundException;
    }
}
