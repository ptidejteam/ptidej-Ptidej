package fr.emn.oadymppac.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.text.ChangedCharSetException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.2 $
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class WebStatMaker {

	/**
	 * Convenience method.
	 */
	public static void main(final String[] args) {
		final WebStatMaker wsm = new WebStatMaker(args);
		wsm.export("website.txt", false);
	}
	Set visitedPages = Collections.synchronizedSet(new HashSet());
	Set pendingPages = Collections.synchronizedSet(new HashSet());

	HashMap linkmap = new HashMap();
	boolean spip = false;
	String root;

	String currentPage;

	HTMLEditorKit.ParserCallback callback = new HTMLEditorKit.ParserCallback() {
		public void handleStartTag(
			final HTML.Tag t,
			final MutableAttributeSet a,
			final int pos) {
			String address = "";
			if (t == HTML.Tag.A) {
				address = (String) a.getAttribute(HTML.Attribute.HREF);
				// output or store the structure here
				if (WebStatMaker.this.testOK(address)) {
					WebStatMaker.this.processPage(address);
				}
			}
		}
	};

	/**
	 * Constructor for WebStatMaker.
	 */
	public WebStatMaker(final String[] param) {
		if (param.length > 1) {
			this.spip = param[1].equals("-spip");
		}
		this.root = this.getRoot(param[0]);
		this.currentPage = this.root;
		this.processPage(this.root);
		this.readURL(this.root);
		this.visitNext();
		System.out.println("This site contains " + this.visitedPages.size()
				+ " pages");
	}

	/**
	 * This method adds the link given by its ends to the site map.
	 */
	private void addLink(final String source, final String target) {
		if (!this.linkmap.containsKey(source)) {
			this.linkmap.put(source, new HashSet());
		}
		((HashSet) this.linkmap.get(source)).add(target);
		//System.out.println("added link : "+source+ " -> "+target);
	}

	/**
	 * This method adds the current URL to the set 
	 * of pages to be visited.
	 */
	private synchronized void addPage(final String address) {
		//System.out.println("Adding page "+address);
		this.pendingPages.add(address);
	}

	/**
	 * This method outputs the site map into a text file.
	 * It should be adapted to export data in dot format.
	 */
	public void export(final String filename, final boolean dotformat) {
		try {
			final FileWriter fw = new FileWriter(filename);
			HashSet linkset = new HashSet();
			for (final Iterator i = this.linkmap.entrySet().iterator(); i
				.hasNext();) {
				final Map.Entry current = (Map.Entry) i.next();
				linkset = (HashSet) current.getValue();
				for (final Iterator j = linkset.iterator(); j.hasNext();) {
					fw.write(this.format((String) current.getKey()) + " "
							+ this.format((String) j.next()) + " 1\n");
				}
			}
			fw.flush();
			fw.close();
		}
		catch (final IOException e) {
		}

	}

	/**
	 * This method formats the address in the way 
	 * it should appear in the data file, and incidentally in the graph.
	 * It is used to clip the URLs.
	 */
	private String format(final String address) {
		if (address.indexOf('?') > 0) {
			return address.substring(address.indexOf('?') + 1);
		}
		return address;
	}
	/**
	 * This method extracts the root of a site from a given
	 * URL. By default, we suppose that the root ends at the last
	 * slash. More sophisticated options could be added.
	 */
	private String getRoot(final String address) {
		return address.substring(0, address.lastIndexOf("/"));
	}

	/**
	 * This method marks the given page as visited : 
	 * it removes the page from the pending set 
	 * and puts it in the visited set of pages.
	 */
	private synchronized void markAsVisited(final String address) {
		//System.out.println(address + " has been visited");
		this.visitedPages.add(address);
		this.pendingPages.remove(address);
	}

	/**
	 * This method checks whether more pages are pending.
	 */
	private boolean moreToVisit() {
		System.out.println(this.pendingPages.size() + " pages pending...");
		return !this.pendingPages.isEmpty();
	}

	/**
	 * This method checks whether the given page has 
	 * previously been visited.
	 */
	private boolean processed(final String address) {
		return this.visitedPages.contains(address)
				|| this.pendingPages.contains(address);
	}

	/**
	 * This method calls the appropriate operations to be 
	 * performed on a URL once it has been tested.
	 */
	private void processPage(final String address) {
		if (!this.processed(address)) {
			this.addPage(address);
		}
		//System.out.println("processing page "+address);
		this.addLink(this.currentPage, address);
	}

	/**
	 * This method does the actual reading of a web page.
	 * It handles some extra errors that are not detected 
	 * in the testOK() method.
	 */
	private void readURL(final String address) {
		//System.out.println("");
		//System.out.println("SPIDERING URL : "+address);
		this.currentPage = address;
		try {
			final URL url = new URL(address);
			final InputStream is = url.openStream();
			final BufferedReader bf =
				new BufferedReader(new InputStreamReader(is));
			new ParserDelegator().parse(bf, this.callback, true);
		}
		catch (final ChangedCharSetException cce) {
			System.out.println("->" + cce.getCharSetSpec());
		}
		catch (final FileNotFoundException fne) {
			System.out.println("Broken link : " + address);
		}
		catch (final IOException e) {
			// Authorization failed
			if (e.getMessage().startsWith(
				"Server returned HTTP response code: 401")) {
				System.out.println("PRIVATE AREA : " + address);
			}
			else {
				e.printStackTrace();
			}
		}
		finally {
			this.markAsVisited(address);
		}
	}

	/**
	 * This method performs a few basic tests on a URL :
	 * <UL>
	 * <LI>it rejects email addresses</LI>
	 * <LI>it rejects javascript URLs</LI>
	 * <LI>it transforms relative paths to global ones</LI>
	 * <LI>it rejects URLs on other sites</LI>
	 * </UL>
	 * More sophisticated criteria could be handled via user flags.
	 */
	private boolean testOK(String address) {
		if (address == null) {
			return false;
		}
		if (address.startsWith("mailto")) {
			return false;
		}
		if (address.startsWith("javascript")) {
			return false;
		}

		// if the URL is malformed
		try {
			new URL(address);
		}
		catch (final MalformedURLException me) {

			// Pour traiter les chemins relatifs
			if (me.getMessage().startsWith("no protocol")) {
				if (!address.startsWith(this.root)) {
					if (!this.root.endsWith("/")) {
						this.root += "/";
					}
					// if the website is managed by spip
					if (this.spip) {
						if (address.startsWith("article")
								|| address.startsWith("rubrique")
								|| address.startsWith("breve")) {
							address = this.root + address;
						}
						this.processPage(this.root + address);
						return false;
					}
					else {
						// if the website is not managed by the CMS "spip"
						if (address.startsWith("/")) {
							this.processPage(this.root + address.substring(1));
						}
						return true;
					}
				}
			}
		}

		if (address.startsWith(this.root)) {
			return true; // may be replaced with a domain restriction option
		}
		return false;
	}

	/**
	 * This method picks one URL at random among the pending addresses.
	 */
	private void visitNext() {
		while (this.moreToVisit()) {
			this.readURL((String) this.pendingPages.iterator().next());
		}
	}
}
