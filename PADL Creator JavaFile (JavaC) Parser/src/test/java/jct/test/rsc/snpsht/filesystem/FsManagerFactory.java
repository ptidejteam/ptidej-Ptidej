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
package jct.test.rsc.snpsht.filesystem;

import java.io.File;
import java.io.IOException;

public class FsManagerFactory {

	/**
	 * Build the correct manager with the given source file.<br>
	 * For the moment, source must be a zip file or don't exist.<br>
	 * It will be really simple to implement directory management, take a look
	 * to {@linkplain jct.test.rsc.snpsht.filesystem.IFsManager}.<br>
	 * If source don't exist, a zip file will be create with it.<br>
	 * 
	 * @param source Source file
	 * @throws IOException
	 *         If File can't be read, create, or is a directory
	 */
	public static IFsManager getNewManager(File source) throws IOException {
		IFsManager manager;

		if (source.exists()) {
			if (source.isFile()) {
				manager = new FsZipFileManager();
				manager.load(source);
			} else {
				throw new IOException(
					"Directory management feature not yet implemented");
			}
		} else {
			manager = new FsZipFileManager();
			manager.load(source);
		}

		return manager;
	}
}
