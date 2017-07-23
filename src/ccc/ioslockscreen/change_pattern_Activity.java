package ccc.ioslockscreen;

import java.io.File;

import com.haibison.android.lockpattern.LockPatternActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.widget.Toast;


public class change_pattern_Activity extends Activity {
	
	int CREATE_PATTERN = 456789;
	SharedPreferences mSharedPreference, prefs;;
	Editor mEditor, edit;;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unlock);
		

		prefs = PreferenceManager.getDefaultSharedPreferences(change_pattern_Activity.this);
		edit = prefs.edit();
		
		Intent it = new Intent(LockPatternActivity.ACTION_CREATE_PATTERN,
				null, change_pattern_Activity.this, LockPatternActivity.class);
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

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
//		Intent i=new Intent(Theme_style_option.this,MainActivity.class);
//		startActivity(i);
//		finish();
		
		if (requestCode == CREATE_PATTERN && resultCode == RESULT_OK) {
			
			Intent it=new Intent(change_pattern_Activity.this,QuestionActivity.class);
			it.putExtra("isAnswered", false);
			it.putExtra("doOpenAct", false);
			startActivity(it);
			finish();

		} else if (requestCode == CREATE_PATTERN
				&& resultCode == RESULT_CANCELED) {
			Toast.makeText(change_pattern_Activity.this,
					"Canceled!! pattern is not enabled", Toast.LENGTH_LONG)
					.show();
			Intent it=new Intent(change_pattern_Activity.this,MainActivity.class);
			startActivity(it);
		} else if ((requestCode == 0 || requestCode == 1 || requestCode == 2)
				&& resultCode == RESULT_OK) {
			
			Toast.makeText(change_pattern_Activity.this, "Theme Applied",
					Toast.LENGTH_LONG).show();
			finish();
		} else {
			Toast.makeText(change_pattern_Activity.this,
					"Canceled!! new Lock Not applied.", Toast.LENGTH_LONG)
					.show();
		}
	}
	
	
	
}
