package com.ford.shanghai.finder.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class IOUtil {
	
	public static String readFromFile(String filepath) {

		File file = new File(filepath);

		InputStream in = null;
		String readFromIO = null;
		try {
			in = new FileInputStream(file);
			readFromIO = readFromIO(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return readFromIO;
	}

	public static String readFromIO(InputStream in) {
		StringBuilder sb = new StringBuilder();

		try {
			byte[] bytes = new byte[2048];
			int n = -1;

			while ((n = in.read(bytes, 0, bytes.length)) != -1) {
				String str = new String(bytes, 0, n, "utf-8");
				sb.append(str);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
}
