package ccc.ioslockscreen;

import java.util.ArrayList;

import test.aplock.ApplistActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnCheckedChangeListener,
		OnClickListener {
	ToggleButton b;

	SharedPreferences prefs;
	Editor edit;
	Button bg, rate, more;
	// LinearLayout myGallery;
	boolean bound;
	String imageNames[];
	AssetManager asset;
	ArrayList<Raw_Item> gridArray = new ArrayList<Raw_Item>();
	int h, w;
	ToggleButton vib, sound;
	String style;
	Button theme ;
	LinearLayout applist,lockstyle,changepasscode,lock_theme;
	LinearLayout btn_change_pattern,change_passcode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_main);

		SharedPreferences myPrefs2 = this.getSharedPreferences("tstyle",
				MODE_WORLD_READABLE);
		style = myPrefs2.getString("style", "keypade");

		lock_theme = (LinearLayout) findViewById(R.id.rlllllllll_lock_themes);
		lock_theme.setOnClickListener(this);
		btn_change_pattern = (LinearLayout) findViewById(R.id.rlllllllllll_pattern);
		btn_change_pattern.setOnClickListener(this);
		change_passcode = (LinearLayout) findViewById(R.id.rlllllllllll);
		change_passcode.setOnClickListener(this);

		if (style.equals("pattern")) {

			lock_theme.setVisibility(View.GONE);

		} else if (style.equals("keypade")) {

			lock_theme.setVisibility(View.VISIBLE);

		}
		
		LinearLayout applock = (LinearLayout) findViewById(R.id.rl_applist);
		applock.setOnClickListener(this);
		LinearLayout lockstyle = (LinearLayout) findViewById(R.id.lockstyle);
		lockstyle.setOnClickListener(this);

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		h = metrics.heightPixels;
		w = metrics.widthPixels;
		prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		edit = prefs.edit();
		asset = getAssets();
		sound = (ToggleButton) findViewById(R.id.btn_sound);
		vib = (ToggleButton) findViewById(R.id.btn_vibrate);
		sound.setChecked(prefs.getBoolean("sound_chap", true));
		vib.setChecked(prefs.getBoolean("vib_chap", false));
		vib.setOnCheckedChangeListener(this);
		sound.setOnCheckedChangeListener(this);

		//bg = (Button) findViewById(R.id.background);
		rate = (Button) findViewById(R.id.rate);
		more = (Button) findViewById(R.id.more);

		//bg.setOnClickListener(this);
		rate.setOnClickListener(this);
		more.setOnClickListener(this);

	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		switch (arg0.getId()) {

		case R.id.btn_sound:
			if (arg1) {

				edit.putBoolean("sound_chap", true);
				edit.commit();
			} else {
				edit.putBoolean("sound_chap", false);
				edit.commit();
			}

			break;
		case R.id.btn_vibrate:
			if (arg1) {

				edit.putBoolean("vib_chap", true);
				edit.commit();

			} else {
				edit.putBoolean("vib_chap", false);
				edit.commit();
			}

			break;
		}

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.rlllllllll_lock_themes:
			Intent i = new Intent(MainActivity.this, Theme_Grid_Display.class);
			startActivity(i);
			break;
//		case R.id.custom_wallpaper_btn:
//			startActivity(new Intent(getApplicationContext(),
//					ImageGridActivity.class));
//			break;
//		case R.id.btn_passcode_themes:
//			startActivity(new Intent(getApplicationContext(),
//					SetImagesActivity.class));
//			break;
//		case R.id.background:
//
//			Intent it_bg = new Intent(MainActivity.this, ScreenActivity.class);
//			startActivity(it_bg);
//			break;
		case R.id.rate:
			String pckgname = getPackageName();
			Intent it_rate = new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id="
							+ pckgname));
			startActivity(it_rate);

			break;
		case R.id.rlllllllllll:
			Intent it = new Intent(getApplicationContext(),
					Theme_Change_Activity.class);
			it.putExtra("isAnswered", false);
			it.putExtra("doOpenAct", false);
			startActivity(it);
			break;
		case R.id.rlllllllllll_pattern:
			Intent it_pattern = new Intent(getApplicationContext(),
					change_pattern_Activity.class);
			it_pattern.putExtra("s_flag", prefs.getBoolean("sound_chap", true));
			it_pattern
					.putExtra("vib_flag", prefs.getBoolean("vib_chap", false));
			startActivity(it_pattern);
			break;
		case R.id.more:
			Intent it_more = new Intent(MainActivity.this, ExtraActivity.class);
			startActivity(it_more);
			break;
		case R.id.rl_applist:
			Intent it_ap = new Intent(MainActivity.this, ApplistActivity.class);
			startActivity(it_ap);
			break;
		case R.id.lockstyle:
			Intent it_style = new Intent(MainActivity.this,
					Theme_style_option.class);
			startActivity(it_style);
			break;

		}
	}

	@Override
	protected void onDestroy() {
		System.gc();
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		// Intent intent = new Intent(NewActivity_Video.this,
		// MainActivity.class);
		//
		// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
		// | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//
		// startActivity(intent);
		finish();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		SharedPreferences myPrefs2 = this.getSharedPreferences("tstyle",
				MODE_WORLD_READABLE);
		style = myPrefs2.getString("style", "keypade");

		if (style.equals("pattern")) {

			lock_theme.setVisibility(View.GONE);
			change_passcode.setVisibility(View.GONE);
			btn_change_pattern.setVisibility(View.VISIBLE);

		} else if (style.equals("keypade")) {

			lock_theme.setVisibility(View.VISIBLE);
			change_passcode.setVisibility(View.VISIBLE);
			btn_change_pattern.setVisibility(View.GONE);
		}
	}

}
