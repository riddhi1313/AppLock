package test.aplock;

import android.graphics.drawable.Drawable;

public class Pinfo {
	String label;
	String packName;
	Drawable icon;
	boolean isSelected;

	public Pinfo(String label, String packName) {
		this.label = label;
		this.packName = packName;
	}

	public Pinfo(boolean isChecked) {
		this.isSelected = isChecked;
	}

	public String getLabel() {
		return label;
	}

	public String getPackName() {
		return packName;
	}

	public boolean getSelected() {
		return isSelected;
	}

	void setLabel(String Label) {
		this.label = Label;
	}

	void setPAckName(String PackName) {
		this.packName = PackName;
	}

	void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
