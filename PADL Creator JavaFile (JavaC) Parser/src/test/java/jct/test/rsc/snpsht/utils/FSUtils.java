/*
 * (c) Copyright 2008 and following years, Julien Tanteri, University of
 * Montreal.
 * 
 * Use and copying of this software and preparation of derivative works based
 * upon this software are permitted. Any copy of this software or of any
 * derivative work must include the above copyright notice of the author, this
 * paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS ALL
 * WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE, AND NOT
 * WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN, ANY LIABILITY FOR DAMAGES
 * RESULTING FROM THE SOFTWARE OR ITS USE IS EXPRESSLY DISCLAIMED, WHETHER
 * ARISING IN CONTRACT, TORT (INCLUDING NEGLIGENCE) OR STRICT LIABILITY, EVEN IF
 * THE AUTHOR IS ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package jct.test.rsc.snpsht.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FSUtils {

	public static void rmDir(File dir) {
		if (dir.isFile()) {
			dir.delete();
		} else {
			for (File child : dir.listFiles()) {
				rmDir(child);
			}
			dir.delete();
		}
	}

	public static void copyFile(File source, File dest) throws IOException {
		InputStream in;
		OutputStream out;
		int c = 0;

		if (!source.isFile() || !source.exists()) {
			throw new IllegalArgumentException(
				"Source must be a file and exist.");
		}

		if (dest.isDirectory()) {
			throw new IllegalArgumentException("Destination is a directory.");
		}

		in = new FileInputStream(source);

		out = new FileOutputStream(dest);

		while ((c = in.read()) != -1)
			out.write(c);

		in.close();
		out.close();
	}

	/**
	 * Next 3 functions was created by David M. Howard Sources :
	 * http://www.devx.com/tips/Tip/13737
	 */

	/**
	 * break a path down into individual elements and add to a list. example :
	 * if a path is /a/b/c/d.txt, the breakdown will be [d.txt,c,b,a]
	 * 
	 * @param f
	 *        input file
	 * @return a List collection with the individual elements of the path in
	 *         reverse order
	 * @author David M. Howard - http://www.devx.com/tips/Tip/13737
	 */
	private static List<String> getPathList(File f) {
		List<String> l = new ArrayList<String>();
		File r;
		try {
			r = f.getCanonicalFile();
			while (r != null) {
				l.add(r.getName());
				r = r.getParentFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
			l = null;
		}
		return l;
	}

	/**
	 * figure out a string representing the relative path of 'f' with respect to
	 * 'r'
	 * 
	 * @param r
	 *        home path
	 * @param f
	 *        path of file
	 * @author David M. Howard - http://www.devx.com/tips/Tip/13737
	 */
	private static String matchPathLists(List<String> r, List<String> f) {
		int i;
		int j;
		String s;
		// start at the beginning of the lists
		// iterate while both lists are equal
		s = "";
		i = r.size() - 1;
		j = f.size() - 1;

		// first eliminate common root
		while ((i >= 0) && (j >= 0) && (r.get(i).equals(f.get(j)))) {
			i--;
			j--;
		}

		// for each remaining level in the home path, add a ..
		for (; i >= 0; i--) {
			s += ".." + File.separator;
		}

		// for each level in the file path, add the path
		for (; j >= 1; j--) {
			s += f.get(j) + File.separator;
		}

		// file name
		s += f.get(j);
		return s;
	}

	/**
	 * get relative path of File 'f' with respect to 'home' directory example :
	 * home = /a/b/c f = /a/d/e/x.txt s = getRelativePath(home,f) =
	 * ../../d/e/x.txt
	 * 
	 * @param home
	 *        base path, should be a directory, not a file, or it doesn't make
	 *        sense
	 * @param f
	 *        file to generate path for
	 * @return path from home to f as a string
	 * @author David M. Howard - http://www.devx.com/tips/Tip/13737
	 */
	public static String getRelativePath(File home, File f) {
		List<String> homelist;
		List<String> filelist;
		String s;

		if (home.equals(f))
			return ".";

		homelist = getPathList(home);
		filelist = getPathList(f);
		s = matchPathLists(homelist, filelist);

		return s;
	}

	/**
	 * Makes a MD5 checksum on a file and returns it.
	 * 
	 * @param file
	 *        File to checksum
	 * @return MD5 checksum
	 * @author Vlad -
	 *         http://vyshemirsky.blogspot.com/2007/08/computing-md5-digest
	 *         -checksum-in-java.html
	 */
	public static String checksum(File file) {
		try {
			InputStream fin = new FileInputStream(file);
			return checksum(fin);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	public static String checksum(InputStream fin) {
		try {
			java.security.MessageDigest md5er =
				MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[1024];
			int read;
			do {
				read = fin.read(buffer);
				if (read > 0)
					md5er.update(buffer, 0, read);
			} while (read != -1);
			fin.close();
			byte[] digest = md5er.digest();
			if (digest == null)
				return null;
			String strDigest = "0x";
			for (int i = 0; i < digest.length; i++) {
				strDigest +=
					Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(
						1).toUpperCase();
			}
			return strDigest;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Compress an array of files to a zip file.<br>
	 * This method is not recursive, so directory is file array will be ignored.
	 * 
	 * @param zipFile
	 *        Zip file output
	 * @param files2zip
	 *        Files to zip (not recursively)
	 * @throws IOException
	 */
	public static void zip(File zipFile, File[] files2zip) throws IOException {
		zip(zipFile, files2zip, false);
	}

	/**
	 * Zip recursively content list of a directory.
	 * 
	 * @param zipFile
	 *        Zip file output
	 * @param dir2zip
	 *        Directory to zip content
	 * @throws IOException
	 */
	public static void zip(File zipFile, File dir2zip) throws IOException {
		zip(zipFile, dir2zip.listFiles(), true);
	}

	/**
	 * Compress, recursively or not, one or more files to a zip file.<br>
	 * 
	 * @param zipFile
	 *        Zip file output
	 * @param files2zip
	 *        Files to zip
	 * @param rec
	 *        If true, directory content of files2zip array will be zip, else
	 *        will be ignored.
	 * @throws IOException
	 */
	public static void zip(File zipFile, File[] files2zip, boolean rec)
			throws IOException {
		// Create the ZIP file
		ZipOutputStream out =
			new ZipOutputStream(new FileOutputStream(zipFile));

		// Compress the files
		for (int i = 0; i < files2zip.length; i++) {
			add2zip(files2zip[i].getParentFile(), files2zip[i], out, rec);
		}

		out.close();
	}

	/**
	 * Add a file to a zip.
	 * 
	 * @param relativeDir
	 *        Root directory of zip file. Useful to compute relative paths.
	 * @param file2add
	 *        File to add.
	 * @param out
	 *        Zip file output stream
	 * @param rec
	 *        Recursivity
	 * @throws IOException
	 */
	private static void add2zip(
		File relativeDir,
		File file2add,
		ZipOutputStream out,
		boolean rec) throws IOException {

		if (file2add.isDirectory()) {
			if (rec) {
				for (File child : file2add.listFiles()) {
					add2zip(relativeDir, child, out, rec);
				}
			}
		} else {
			byte[] buf = new byte[1024];

			FileInputStream in = new FileInputStream(file2add);
			// Add ZIP entry to output stream.
			out.putNextEntry(new ZipEntry(fsPath2zipPath(getRelativePath(
				relativeDir,
				file2add))));

			// Transfer bytes from the file to the ZIP file
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}

			// Complete the entry
			out.closeEntry();
			in.close();
		}
	}

	/**
	 * Unzip a file from a zip.
	 * 
	 * @param zipFile
	 *        Zip file witch contains file to unzip.
	 * @param outDir
	 *        Output directory
	 * @param fileToUnzip
	 *        File to unzip logical name (file name in zip)
	 * @throws IOException
	 */
	public static void unzip(File zipFile, File outDir, String fileToUnzip)
			throws IOException {
		ZipEntry entry;
		ZipInputStream in;

		// Find file to unzip
		in = new ZipInputStream(new FileInputStream(zipFile));

		do {
			entry = in.getNextEntry();
		} while (entry != null && entry.getName().compareTo(fileToUnzip) != 0);

		if (entry != null) {
			// File found -> unzip
			unzip(in, new File(outDir, zipPath2fsPath(entry.getName())));
		}

		in.close();
	}

	/**
	 * Unzip all files of a zip.
	 * 
	 * @param zipFile
	 *        Zip file to uncompress.
	 * @param outDir
	 *        Output directory
	 * @throws IOException
	 */
	public static void unzip(File zipFile, File outDir) throws IOException {
		ZipEntry entry;
		ZipInputStream in;

		// Find file to unzip
		in = new ZipInputStream(new FileInputStream(zipFile));

		while ((entry = in.getNextEntry()) != null) {
			unzip(in, new File(outDir, zipPath2fsPath(entry.getName())));
		}

		in.close();
	}

	/**
	 * Unzip a file from a zip stream
	 * 
	 * @param in
	 * @param outFile
	 * @throws IOException
	 */
	private static void unzip(ZipInputStream in, File outFile)
			throws IOException {
		outFile.getParentFile().mkdirs();
		OutputStream out = new FileOutputStream(outFile);

		// Transfer bytes from the ZIP file to the output file
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}

		out.close();
		in.close();
	}

	/**
	 * List files in a zip, and returns an array of their logicals names.
	 * 
	 * @param zipFile
	 *        Zip file
	 * @return Array of file's (in zip file) logical names
	 * @throws IOException
	 */
	public static String[] listZipContent(File zipFile) throws IOException {
		ZipFile zf = new ZipFile(zipFile);
		List<String> filesList = new ArrayList<String>();

		// Enumerate each entry
		for (Enumeration<? extends ZipEntry> entries = zf.entries(); entries
			.hasMoreElements();) {
			// Get the entry name
			filesList.add(((ZipEntry) entries.nextElement()).getName());
		}
		zf.close();

		return filesList.toArray(new String[0]);
	}

	/**
	 * Convert a path in zip format to local file system path.
	 * 
	 * @param zipPath
	 * @return
	 */
	private static String zipPath2fsPath(String zipPath) {
		if (File.separator.compareTo("/") == 0) {
			return zipPath;
		} else {
			return zipPath.replace(File.separator, "/");
		}
	}

	/**
	 * Convert a local file system path to path in zip format.
	 * 
	 * @param zipPath
	 * @return
	 */
	private static String fsPath2zipPath(String fsPath) {
		if (File.separator.compareTo("/") == 0) {
			return fsPath;
		} else {
			return fsPath.replace("/", File.separator);
		}
	}
}
