package ccc.ioslockscreen;

import java.io.File;

import com.haibison.android.lockpattern.LockPatternActivity;
import com.haibison.android.lockpattern.util.Settings;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class Theme_style_option extends Activity implements
		OnCheckedChangeListener {

	ToggleButton pattern, keypade, passcode;
	int CREATE_PATTERN = 456789;
	SharedPreferences prefs;
	Editor edit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_style_option);

		prefs = PreferenceManager
				.getDefaultSharedPreferences(Theme_style_option.this);
		edit = prefs.edit();

		Settings.Security.setAutoSavePattern(getApplicationContext(), true);
		pattern = (ToggleButton) findViewById(R.id.tb_pattern);
		pattern.setOnCheckedChangeListener(this);
		keypade = (ToggleButton) findViewById(R.id.tb_keypade);
		keypade.setOnCheckedChangeListener(this);
//		passcode = (ToggleButton) findViewById(R.id.tb_passcode);
//		passcode.setOnCheckedChangeListener(this);

		SharedPreferences myPrefs2 = this.getSharedPreferences("tstyle",
				MODE_WORLD_READABLE);

		String style = myPrefs2.getString("style", "keypade");

		if (style.equals("keypade")) {
			keypade.setBackgroundResource(R.drawable.new_on);
			pattern.setBackgroundResource(R.drawable.new_off);
		} else if (style.equals("pattern")) {
			pattern.setBackgroundResource(R.drawable.new_on);
			keypade.setBackgroundResource(R.drawable.new_off);
		}

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub

		if (pattern.isChecked()) {

			SharedPreferences myPrefs = getSharedPreferences("tstyle",
					MODE_WORLD_READABLE);
			SharedPreferences.Editor prefsEditor = myPrefs.edit();
			prefsEditor.putString("style", "pattern");
			prefsEditor.commit();
			edit.putString("password", "" + 1);
			edit.commit();
			Intent it = new Intent(LockPatternActivity.ACTION_CREATE_PATTERN,
					null, Theme_style_option.this, LockPatternActivity.class);
			it.putExtra("enableBack", false);
			it.putExtra("s_flag", prefs.getBoolean("sound_chap", true));
			it.putExtra("vib_flag", prefs.getBoolean("vib_chap", false));
			File f = new File(Environment.getExternalStorageDirectory()
					+ "/Android/data/" + getPackageName()
					+ "/Background/lock_bg.jpg");
			if (f.exists()) {
				it.putExtra("path", f.getAbsolutePath());
			}
			startActivityForResult(it, CREATE_PATTERN);
		}
		if (keypade.isChecked()) {

			SharedPreferences myPrefs = getSharedPreferences("tstyle",
					MODE_WORLD_READABLE);
			SharedPreferences.Editor prefsEditor = myPrefs.edit();
			prefsEditor.putString("style", "keypade");
			prefsEditor.commit();
			edit.putString("password", "" + 1);
			edit.commit();
			Intent i = new Intent(Theme_style_option.this, Test_theme.class);
			i.putExtra("s_flag", prefs.getBoolean("sound_chap", true));
			i.putExtra("vib_flag", prefs.getBoolean("vib_chap", false));
			startActivity(i);
			finish();
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		// Intent i=new Intent(Theme_style_option.this,MainActivity.class);
		// startActivity(i);
		// finish();

		if (requestCode == CREATE_PATTERN && resultCode == RESULT_OK) {

			Intent it = new Intent(Theme_style_option.this,
					QuestionActivity.class);
			it.putExtra("isAnswered", false);
			it.putExtra("doOpenAct", false);
			startActivity(it);
			// Toast.makeText(Theme_style_option.this, "Theme Applied",
			// Toast.LENGTH_LONG).show();
			finish();

		} else if (requestCode == CREATE_PATTERN
				&& resultCode == RESULT_CANCELED) {
			Toast.makeText(Theme_style_option.this,
					"Canceled!! pattern is not enabled", Toast.LENGTH_LONG)
					.show();
		} else if ((requestCode == 0 || requestCode == 1 || requestCode == 2)
				&& resultCode == RESULT_OK) {

			Toast.makeText(Theme_style_option.this, "Theme Applied",
					Toast.LENGTH_LONG).show();
			finish();
		} else {
			Toast.makeText(Theme_style_option.this,
					"Canceled!! new Lock Not applied.", Toast.LENGTH_LONG)
					.show();
		}
	}

}
