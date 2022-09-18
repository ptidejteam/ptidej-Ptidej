/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.zip.GZIPInputStream;

/**
 * Simple parser for Apache web log format.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class WebLogParser {
    SimpleDateFormat dateFormat;
	
    public WebLogParser() {
	this.dateFormat  = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", new Locale("en","US"));
    }
	
    public static class Entry {
	public String from;
	public Date date;
	public String httpCommand;
	public String httpArg;
	public String httpVersion;
	public String httpAnswer;
	public int httpSize;
		
	public String toString() {
	    return this.from+" "
		+this.date+" "
		+this.httpCommand+" "
		+this.httpArg+" "
		+this.httpVersion+" "
		+this.httpAnswer+" "
		+this.httpSize;
	}
    }
	
    public Entry readEntry(BufferedReader in, Entry entry)
	throws IOException {
	if (entry == null) {
	    entry = new Entry();
	}
	String line = in.readLine();

	if (line == null)
	    return null;
				
	int pos = line.indexOf(' ');
	if (pos < 0)
	    throw new IOException("invalid source URL syntax for Web Log entry");
	entry.from = line.substring(0, pos);
	pos = line.indexOf('[', pos+1);
	if (pos < 0)
	    throw new IOException("invalid date syntax for Web Log entry");
	int nextPos = line.indexOf(']', pos+1);
	if (nextPos < 0)
	    throw new IOException("invalid date syntax for Web Log entry");
	String date = line.substring(pos+1, nextPos);
		
	try {
	    entry.date = this.dateFormat.parse(date);
	}
	catch(ParseException e) {
	    throw new IOException("invalid date syntax for Web Log entry");
	}
		
	pos = line.indexOf('"', nextPos+1);
	if (pos < 0)
	    throw new IOException("invalid HTTP command syntax for Web Log entry");
	nextPos = line.indexOf(' ', pos+1);
	if (nextPos < 0)
	    throw new IOException("invalid HTTP command syntax for Web Log entry");
	entry.httpCommand = line.substring(pos+1, nextPos);
		
	pos = nextPos+1;
	nextPos = line.indexOf(' ', pos+1);
	if (nextPos < 0)
	    throw new IOException("invalid HTTP arg syntax for Web Log entry");
	entry.httpArg = line.substring(pos, nextPos);
		
	pos = nextPos+1;
	nextPos = line.indexOf(' ', pos+1);
	if (nextPos < 0)
	    throw new IOException("invalid HTTP version syntax for Web Log entry");
	entry.httpVersion = line.substring(pos, nextPos);
		
	pos = nextPos+1;
	nextPos = line.indexOf(' ', pos+1);
	if (nextPos < 0)
	    throw new IOException("invalid HTTP answer syntax for Web Log entry");
	entry.httpAnswer = line.substring(pos, nextPos);
		
	pos = nextPos+1;
	String size = line.substring(pos);
	if (size.equals("-")) {
	    entry.httpSize = -1;
	}
	else {
	    entry.httpSize = Integer.parseInt(size);
	}
	return entry;
    }
	
    public static void main(String args[]) {
	WebLogParser parser = new WebLogParser();
	BufferedReader rin;
	Entry entry = null;
		
	for (int i = 0; i < args.length; i++) {
	    try {
		FileInputStream fin = new FileInputStream(args[i]);
		if (args[i].endsWith(".Z") ||
		    args[i].endsWith(".gz")) {
		    GZIPInputStream in = new GZIPInputStream(fin);
		    rin = new BufferedReader(new InputStreamReader(in));
		}
		else {
		    rin = new BufferedReader(new InputStreamReader(fin));
		}
			
		while ((entry = parser.readEntry(rin, entry)) != null) {
		    System.out.println(entry);
		}
		rin.close();
		fin.close();
	    }
	    catch(FileNotFoundException e) {
	    }
	    catch(IOException e) {
	    }
	}		
    }
}
