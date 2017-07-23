package test.aplock;

import android.graphics.Bitmap;

public class ImageModel {
	String path;
	boolean isFile;
	Bitmap bmp;
	String fileName;
	boolean isChecked;
	boolean NeedBorder;

	public ImageModel(String path, boolean isFile, Bitmap bmp, String fileName,
			boolean NeedBorder) {
		this.path = path;
		this.isFile = isFile;
		this.bmp = bmp;
		this.fileName = fileName;
		this.NeedBorder = NeedBorder;
	}

	void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	boolean getchecked() {
		return this.isChecked;

	}
}
