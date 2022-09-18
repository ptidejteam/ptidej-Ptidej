package fr.emn.oadymppac.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * DOCUMENT ME!
 *
 * @version $Revision: 1.1 $
 * @author $author$
 */
public class TextStatMaker {
	static final String[][] letterVariants = new String[][] {
			{ "E", "ÈÉÊËèéêë" }, { "S", "Šš" }, { "Z", "" }, { "Y", "Ÿİıÿ" },
			{ "OE", "œ" }, { "A", "ÀÁÂÃÄÅàáâãäå" }, { "AE", "Ææ" },
			{ "C", "Çç" }, { "I", "ÌÍÎÏìíîï" }, { "D", "Ğ" }, { "N", "Ññ" },
			{ "O", "ÒÓÔÕÖòóôõö" }, { "U", "ÙÚÛÜùúûü" } };
	static final String spaces = "',:;.?!-\"()*\n\t\r\f";
	/**
	 * DOCUMENT ME!
	 *
	 * @param args DOCUMENT ME!
	 */
	public static void main(final String[] args) {
		System.out.println("Starting program...");

		if (args.length < 2) {
			System.out.println("Arguments: <input file> <output file> n");
			System.exit(0);
		}

		final String filename_in = args[0];

		if (!new File(filename_in).exists()) {
			System.out.println("File not found.");
			System.exit(0);
		}

		String filename_out = null;
		int n = 1;

		if (args.length > 2) {
			filename_out = args[1];

			try {
				n = Integer.parseInt(args[2]);
			}
			catch (final NumberFormatException num) {
				num.printStackTrace();
			}
		}

		final TextStatMaker ts = new TextStatMaker(filename_in, n);

		if (filename_out == null) {
			filename_out = filename_in + ".out";
		}

		ts.writeStats(filename_out, filename_out.endsWith(".dot"));
	}
	Map statTable = new TreeMap();

	int n;

	/**
	 * Creates a new TextStatMaker object.
	 *
	 * @param filename_in DOCUMENT ME!
	 * @param n DOCUMENT ME!
	 */
	public TextStatMaker(final String filename_in, final int n) {
		this.n = n;
		this.makeStats(filename_in);
	}

	private void makeStats(final String inputfilename) {
		System.out.println("Will make statistics...");

		try {
			final File f = new File(inputfilename);

			final Reader fis = new BufferedReader(new FileReader(f));
			final StringBuffer buffer = new StringBuffer();

			System.out.println("Reading through file " + inputfilename);

			// read through the file and memorize the n-grams
			boolean skipSpaces = false;
			final char[] buf = new char[1];

			while (fis.read(buf) == 1) {
				char b = buf[0];

				if (!Character.isLetter(b)) {
					if (skipSpaces) {
						continue;
					}

					b = '_';
					skipSpaces = true;
				}
				else {
					skipSpaces = false;
				}

				b = Character.toUpperCase(b);
				buffer.append(b);

				if (buffer.length() == 2 * this.n) {
					final String entry = buffer.toString();

					//System.out.println("Current entry is : "+entry);
					if (this.statTable.containsKey(entry)) {
						this.statTable.put(
							entry,
							Integer.valueOf(((Integer) this.statTable.get(entry))
								.intValue() + 1));
					}
					else {
						this.statTable.put(entry, Integer.valueOf(1));
					}

					buffer.deleteCharAt(0);
				}
				else {
					System.out.println("buffer length is : " + buffer.length());
				}
			}
			fis.close();
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private void writeStats(final String outputfilename, final boolean dotFormat) {
		System.out.println("Writing results to : " + outputfilename);

		try {
			final PrintWriter write =
				new PrintWriter(new FileWriter(outputfilename));
			final Iterator e = this.statTable.keySet().iterator();
			String sep = " ";
			if (dotFormat) {
				write.println("digraph g {");
				sep = " -> ";
			}

			while (e.hasNext()) {
				final String key = (String) e.next();
				final String output =
					key.substring(0, this.n) + sep
							+ key.substring(this.n, key.length())
							+ (dotFormat ? ";" : " " + this.statTable.get(key));
				write.println(output);
				System.out.println(output);
			}
			if (dotFormat) {
				write.println("}");
			}
			write.close();
		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}
}