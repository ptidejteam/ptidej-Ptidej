/**
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France
 * -------------------------------------------------------------------------
 * This software is published under the terms of the QPL Software License
 * a copy of which has been included with this distribution in the
 * license-infovis.txt file.
 */
import infovis.Tree;
import infovis.column.IntColumn;
import infovis.column.LongColumn;
import infovis.column.StringColumn;
import infovis.tree.DefaultTree;
import infovis.tree.io.AbstractTreeReader;
import infovis.tree.io.XMLTreeWriter;
import infovis.utils.RowIterator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.ParseException;
import java.util.zip.GZIPInputStream;

/**
 * Reader for List of directory and files using the Unix syntax. Specialized
 * for the InfoVis contest but could be generalized.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.2 $
 */
public class FileListTreeReader extends AbstractTreeReader {
	StringColumn fileName;
	IntColumn hitCount;
	IntColumn size;
	IntColumn uid;
	IntColumn gid;
	LongColumn mtime;
	LongColumn ctime;
	StringColumn title;

	/**
	 * Constructor for FileListTreeReader.
	 *
	 * @param in
	 * @param name
	 * @param tree
	 */
	public FileListTreeReader(BufferedReader in, String name, Tree tree) {
		super(in, name, tree);
	}

	/**
	 * @see infovis.io.AbstractTableReader#load()
	 */
	public boolean load() {
		String line;

		this.fileName = StringColumn.findColumn(this.tree, "name");
		this.fileName.setExtend(0, "/");
		this.hitCount = IntColumn.findColumn(this.tree, "hitCount");
		this.size = IntColumn.findColumn(this.tree, "size");
		this.uid = IntColumn.findColumn(this.tree, "uid");
		this.gid = IntColumn.findColumn(this.tree, "gid");
		this.mtime = LongColumn.findColumn(this.tree, "mtime");
		this.ctime = LongColumn.findColumn(this.tree, "ctime");
		this.title = StringColumn.findColumn(this.tree, "title");

		try {
			while ((line = this.in.readLine()) != null) {
				if (line.charAt(0) != '#')
					readFile(line);
			}
		}
		catch (IOException e) {
			return false;
		}
		finally {
			try {
				this.in.close();
			}
			catch (IOException e) {
			}
		}
		return true;
	}

	int findNode(String name, int parent) {
		for (RowIterator iter = this.tree.childrenIterator(parent); iter
			.hasNext();) {
			int child = iter.nextRow();
			if (!this.fileName.isValueUndefined(child)
					&& this.fileName.get(child).equals(name)) {
				return child;
			}
		}
		int node = this.tree.addNode(parent);
		this.fileName.setExtend(node, name);

		return node;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param line DOCUMENT ME!
	 */
	public void readFile(String line) {
		int end = line.indexOf(':');
		int i = 0;
		int node = Tree.ROOT;
		String pathPart = line.substring(0, end);
		int next = pathPart.indexOf('/', 1);

		do {
			if (next == -1) {
				next = end;
			}
			else {
				String path = pathPart.substring(i + 1, next);
				node = findNode(path, node);

				i = next;
				next = pathPart.indexOf('/', next + 1);
			}
		}
		while (i != -1 && i < end);

		try {
			i = end;
			end = line.indexOf(':', end + 1);
			this.hitCount.setValueAt(node, line.substring(i + 1, end));

			i = end;
			end = line.indexOf(':', end + 1);
			this.size.setValueAt(node, line.substring(i + 1, end));

			i = end;
			end = line.indexOf(':', end + 1);
			this.uid.setValueAt(node, line.substring(i + 1, end));

			i = end;
			end = line.indexOf(':', end + 1);
			this.gid.setValueAt(node, line.substring(i + 1, end));

			i = end;
			end = line.indexOf(':', end + 1);
			this.mtime.setValueAt(node, line.substring(i + 1, end));

			i = end;
			end = line.indexOf(':', end + 1);
			if (end == -1) {
				this.ctime.setValueAt(node, line.substring(i + 1));
				return;
			}
			else
				this.ctime.setValueAt(node, line.substring(i + 1, end));

			i = end;
			if (i == -1)
				return;
			String t = line.substring(i + 1);
			if (t != null && t.length() != 0) {
				i = t.indexOf('"');
				end = t.lastIndexOf('"');
				if (i == end)
					end = t.length();
				if (i != -1 && end != -1) {
					this.title.setValueAt(node, t.substring(i + 1, end));
				}
			}
		}
		catch (ParseException e) {
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param args DOCUMENT ME!
	 */
	public static void main(String[] args) {
		try {
			Tree tree = new DefaultTree();
			BufferedReader in;
			GZIPInputStream is;

			if (args[0].endsWith(".gz") || args[0].endsWith(".z")
					|| args[0].endsWith(".Z")) {
				is = new GZIPInputStream(new FileInputStream(args[0]));
				in = new BufferedReader(new InputStreamReader(is));
			}
			else {
				FileReader fin = new FileReader(args[0]);
				in = new BufferedReader(fin);
			}

			FileListTreeReader reader =
				new FileListTreeReader(in, args[0], tree);
			reader.load();
			in.close();

			Writer out;
			if (args.length > 1) {
				out = new BufferedWriter(new FileWriter(args[1]));
			}
			else {
				out = new OutputStreamWriter(System.out);
			}
			XMLTreeWriter writer = new XMLTreeWriter(out, tree);
			writer.write();
			out.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
