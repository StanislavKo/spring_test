package com.indielemon.logsystem.webboot.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;

import com.indielemon.logsystem.webboot.consts.Consts;

public class StringUtils {

	public static StringBuilder getInputStreamContent(InputStream inputStream) {
		try {
			final int bufferSize = 1024;
			final char[] buffer = new char[bufferSize];
			final StringBuilder out = new StringBuilder("");
			Reader in = new InputStreamReader(inputStream, "UTF-8");
			for (;;) {
				int rsz = in.read(buffer, 0, buffer.length);
				if (rsz < 0)
					break;
				out.append(buffer, 0, rsz);
			}
			return out;
		} catch (IOException ex) {
			ex.printStackTrace();
			return new StringBuilder("");
		}
	}

}
