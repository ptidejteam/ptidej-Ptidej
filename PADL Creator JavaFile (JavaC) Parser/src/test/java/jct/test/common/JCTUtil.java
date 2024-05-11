package jct.test.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Assert;

public abstract class JCTUtil {
	public static String getFileContent(final File file) {
		try {
			final char characters[] = new char[(int) file.length()];
			final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			final int length = bufferedReader.read(characters, 0, characters.length);
			bufferedReader.close();
			String str = new String(characters, 0, length);
			return (str.replaceAll("\\r", ""));
		} catch (final IOException e) {
			Assert.fail("Cannot read file " + file.getAbsolutePath());
		}
		throw new RuntimeException(
				"Assert.fail() failed to fail... (Cannot read file + " + file.getAbsolutePath() + ")");
	}

}
