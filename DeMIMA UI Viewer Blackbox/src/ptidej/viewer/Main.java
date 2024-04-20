/* (c) Copyright 2001 and following years, Yann-Gaël Guéhéneuc ,
 * University of Montreal.
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * the author, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN,
 * ANY LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF THE AUTHOR IS ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package ptidej.viewer;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import util.io.ProxyConsole;
import util.io.ProxyDisk;

public class Main {
	public static void main(final String[] args) {
		final Calendar cal = Calendar.getInstance();
		final SimpleDateFormat sdf =
			new SimpleDateFormat(Constants.DATE_FORMAT);
		final String now = sdf.format(cal.getTime());

		final PrintWriter writer =
			new PrintWriter(ProxyDisk.getInstance().fileAbsoluteOutput(
				Constants.PROGRAM_FILE_NAME + ".log",
				true));
		writer
			.println("-------------------------------------------------------------------------------");
		writer.print("Ptidej run on ");
		writer.println(now);
		writer
			.println("-------------------------------------------------------------------------------");
		ProxyConsole.getInstance().setDebugOutput(writer);
		ProxyConsole.getInstance().setErrorOutput(writer);
		ProxyConsole.getInstance().setNormalOutput(writer);

		final WindowMain ui = new WindowMain();
		ui.setVisible(true);
	}
}
