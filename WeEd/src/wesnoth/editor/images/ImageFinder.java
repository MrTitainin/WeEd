package wesnoth.editor.images;

import java.io.File;

public class ImageFinder {

	private static final String DEFAULT = "misc/blank-hex.png";

	private String corePath;
	private String addonPath;

	public ImageFinder(String corePath, String addonPath) {
		this.corePath = corePath;
		this.addonPath = addonPath;
	}

	public File get(String img) {
		String[] paths = { this.addonPath + img, this.corePath + img, this.corePath + DEFAULT };

		for (String path : paths) {
			File file = new File(path);
			if (file.exists())
				return file;
		}

		throw new IllegalStateException("Unable to find a suitable file for " + img);
	}


}
