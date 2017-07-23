package test.aplock;

import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.ToggleButton;
import ccc.ioslockscreen.R;

public class MyAppAdapter extends ArrayAdapter<Pinfo> {

	private final ArrayList<Pinfo> list;
	private final Activity context;
	boolean checkAll_flag = false;
	boolean checkItem_flag = false;
	int h, w;
	AppDBHelper db;
	SharedPreferences prefs;
	Editor edit;
	public ImageLoader imageLoader;

	public MyAppAdapter(Activity context, ArrayList<Pinfo> main_list,
			ArrayList<String> data2) {
		super(context, R.layout.raw_item, main_list);
		this.context = context;
		this.data = data2;

		this.list = main_list;
		imageLoader = new ImageLoader(context.getApplicationContext());

		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		edit = prefs.edit();
		DisplayMetrics metrics = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		h = metrics.heightPixels;
		db = new AppDBHelper(context);
		ArrayList<String> state1List = db.getApsHasStateTrue();
		MyAppLockService.locked_list = new ArrayList<String>();
		for (String st : state1List) {
			for (int i = 0; i < main_list.size(); i++) {
				if (main_list.get(i).packName.contains(st)) {

					MyAppLockService.locked_list.add(st);
					main_list.get(i).isSelected = true;
				}
			}
		}
	}

	static class ViewHolder {
		protected TextView text;
		protected ToggleButton checkbox;
		protected ImageView iv;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder = null;
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			convertView = inflator.inflate(R.layout.raw_item_lock, null);
			viewHolder = new ViewHolder();
			viewHolder.text = (TextView) convertView
					.findViewById(R.id.textView_raw);
			viewHolder.iv = (ImageView) convertView
					.findViewById(R.id.imageView_raw);
			int size = h * 60 / 800;
			LayoutParams params = new RelativeLayout.LayoutParams(size, size);
			params.addRule(RelativeLayout.CENTER_IN_PARENT);
			viewHolder.iv.setLayoutParams(params);
			viewHolder.checkbox = (ToggleButton) convertView
					.findViewById(R.id.toggleButton_raw);
			viewHolder.checkbox
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							int getPosition = (Integer) buttonView.getTag();
							boolean ischecked = buttonView.isChecked();
							list.get(getPosition).setSelected(ischecked);
							if (ischecked) {
								db.updateApp(list.get(getPosition).packName, 1);
								MyAppLockService.locked_list.add(list
										.get(getPosition).packName);
								if (db.getApsHasStateTrue().size() == list
										.size()) {
									ApplistActivity.tb_all.setChecked(true);
								}
							} else {
								db.updateApp(list.get(getPosition).packName, 0);
								MyAppLockService.locked_list.remove(list
										.get(getPosition).packName);
								edit.putBoolean("isAllChecked", false);
								edit.commit();
								ApplistActivity.removeCheck = true;
								ApplistActivity.tb_all.setChecked(false);
							}
						}

					});
			convertView.setTag(viewHolder);
			convertView.setTag(R.id.textView_raw, viewHolder.text);
			convertView.setTag(R.id.toggleButton_raw, viewHolder.checkbox);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.checkbox.setTag(position);
		viewHolder.text.setText(list.get(position).getLabel());
		imageLoader.DisplayImage(data.get(position), viewHolder.iv);

		viewHolder.checkbox.setChecked(list.get(position).getSelected());

		return convertView;
	}

	private ArrayList<String> data;
}
