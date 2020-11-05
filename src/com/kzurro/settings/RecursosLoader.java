package com.kzurro.settings;
import java.net.URL;

final public class RecursosLoader {
	
	public static URL load(String path) {
		URL input = RecursosLoader.class.getResource(path);
		if (input == null) {
			input = RecursosLoader.class.getResource("/"+path);
		}
		return input;
	}

}
