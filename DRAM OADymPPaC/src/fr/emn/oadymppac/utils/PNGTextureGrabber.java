/*
 * Created on 24 juil. 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package fr.emn.oadymppac.utils;

import gl4java.GLFunc;
import gl4java.utils.textures.TextureGrabber;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 * A convenience class for saving a texture in PNG 
 * format. PNG format specifications are available
 * at the following url : 
 * http://www.libpng.org/pub/png/spec/1.2/PNG-Contents.html 
 */
public class PNGTextureGrabber extends TextureGrabber {

	/* Table of CRCs of all 8-bit messages. */
	long[] crc_table = new long[256];

	/* Flag: has the table been computed? Initially false. */
	boolean crc_table_computed = false;

	/**
	 * @param arg0
	 */
	public PNGTextureGrabber(final GLFunc gl) {
		super(gl);
	}

	/* Return the CRC of the bytes buf[0..len-1]. */
	long crc(final char[] buf, final int len) {
		return this.update_crc(0xffffffffL, buf, len) ^ 0xffffffffL;
	}

	byte[] intToByteArray(final int val, final int len) {
		final byte[] res = new byte[len];
		for (int i = 0; i < len; i++) {
			res[len - i - 1] = (byte) (val >> 8 * i);
		}
		return res;
	}

	/* Make the table for a fast CRC. */
	void make_crc_table() {
		long c;
		int n, k;

		for (n = 0; n < 256; n++) {
			c = n;
			for (k = 0; k < 8; k++) {
				if ((c & 1) > 0) {
					c = 0xedb88320L ^ c >> 1;
				}
				else {
					c = c >> 1;
				}
			}
			this.crc_table[n] = c;
		}
		this.crc_table_computed = true;
	}

	/* Update a running CRC with the bytes buf[0..len-1]--the CRC
	   should be initialized to all 1's, and the transmitted value
	   is the 1's complement of the final running CRC (see the
	   crc() routine below)). */

	long update_crc(final long crc, final char[] buf, final int len) {
		long c = crc;
		int n;

		if (!this.crc_table_computed) {
			this.make_crc_table();
		}
		for (n = 0; n < len; n++) {
			c = this.crc_table[(int) ((c ^ buf[n]) & 0xff)] ^ c >> 8;
		}
		return c;
	}

	/* (non-Javadoc)
	 * @see gl4java.utils.textures.TextureGrabber#write2File(java.io.OutputStream)
	 */
	public boolean write2File(final OutputStream os) {

		try {
			final DataOutputStream fout = new DataOutputStream(os);

			final byte[] signature =
				{ (byte) 137, (byte) 80, (byte) 78, (byte) 71, (byte) 13,
						(byte) 10, (byte) 26, (byte) 10 };

			final ByteArrayOutputStream bos = new ByteArrayOutputStream();

			// create header chunk
			bos.write("IHDR".getBytes()); // chunk type
			bos.write(this.intToByteArray(this.width, 4));
			bos.write(this.intToByteArray(this.height, 4));
			bos.write(8); // bit depth on 1 byte
			bos.write(2); // color type on 1 byte
			bos.write(0); // compression method
			bos.write(0); // filter method
			bos.write(0); // interlace method
			bos.flush();

			//write PNG signature
			fout.write(signature); // 8 bytes

			//write header chunk
			fout.writeInt(bos.size()); // IHDR chunk length
			fout.write(bos.toByteArray());
			fout.writeInt((int) this.crc(
				bos.toString().toCharArray(),
				bos.size()));
			fout.flush();
			System.out.println("bytes written: " + bos.size());
			bos.reset();

			// create data chunk
			bos.write("IDAT".getBytes()); // chunk type

			//write pixel data scanline by scanline
			//width * color components + 1 byte for filter type
			int line = 0;
			byte[] data = null;
			while (line < this.height - 1) {
				data = new byte[3 * this.width + 1];
				data[0] = (byte) 0; // filter type
				for (int i = 0; i < this.width * 3; i++) {
					data[i + 1] = this.pixels[i + line * this.width * 3];
				}
				line++;
				bos.write(data);
				bos.flush();
			}

			// write data chunk
			fout.writeInt(bos.size());
			fout.write(bos.toByteArray());
			fout.writeInt((int) this.crc(
				bos.toString().toCharArray(),
				bos.size()));
			fout.flush();
			System.out.println("bytes written: " + bos.size());
			bos.reset();

			// create end chunk
			bos.write("IEND".getBytes()); // chunk type

			bos.flush();

			// write end chunk
			fout.writeInt(bos.size());
			fout.write(bos.toByteArray());
			fout.writeInt((int) this.crc(
				bos.toString().toCharArray(),
				bos.size()));
			fout.flush();
			System.out.println("bytes written: " + bos.size());
			bos.reset();
			fout.close();

		}
		catch (final Exception e) {
			System.err.println("something went wrong");
			e.printStackTrace();
			return false;
		}

		return true;
	}

}
