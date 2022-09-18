/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.graph.io;

import infovis.Graph;
import infovis.Table;
import infovis.graph.DefaultGraph;
import infovis.io.AbstractReader;
import infovis.io.AbstractReaderFactory;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Reader Factory for Graphs.
 * Modified by Yann-Gaël Guéhéneuc to add a reader
 * dedicated to graphs for the OADymPPaC project.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class GraphReaderFactory extends AbstractReaderFactory {
	static GraphReaderFactory sharedInstance = new GraphReaderFactory();

	/**
	 * Returns a Graph from a specified table.
	 *
	 * @param table the table.
	 *
	 * @return a Graph from a specified table.
	 */
	public static Graph getGraph(Table table) {
		if (table instanceof Graph) {
			return (Graph) table;
		}
		return new DefaultGraph(table.getTable());
	}

	/**
	 * @see infovis.io.AbstractReaderFactory#addDefaultCreators()
	 */
	protected void addDefaultCreators() {
		add(new AbstractCreator(".xml") {
			public AbstractReader doCreate(
				String name,
				Table table,
				boolean decompress)
				throws IOException, FileNotFoundException {
				return new GraphMLReader(
					open(name, decompress),
					name,
					getGraph(table));
			}
		});
		add(new AbstractCreator(".dot") {
			public AbstractReader doCreate(
				String name,
				Table table,
				boolean decompress)
				throws IOException, FileNotFoundException {
				return new DOTGraphReader(
					open(name, decompress),
					name,
					getGraph(table));
			}
		});
		add(new Creator() {
			public AbstractReader create(String name, Table table) {
				if (name.startsWith("http:")
					|| name.startsWith("ftp:")
					|| name.endsWith(".html")
					|| name.endsWith(".htm"))
					return new HTMLGraphReader(name, getGraph(table));
				return null;
			}
		});
	}

	/**
	 * Returns the shared instance of this <code>GraphReaderFactory</code>
	 *
	 * @return the shared instance of this <code>GraphReaderFactory</code>
	 */
	public static GraphReaderFactory sharedInstance() {
		return sharedInstance;
	}

	/**
	 * Creates a graph reader from a specified resource name and a graph
	 *
	 * @param name the resource name
	 * @param graph the graph
	 *
	 * @return a graph reader or <code>null</code>.
	 */
	public static AbstractReader createReader(String name, Graph graph) {
		return sharedInstance().create(name, graph);
	}

	public static boolean readGraph(String name, Graph graph) {
		return sharedInstance().tryRead(name, graph);
	}
}
