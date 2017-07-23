package ccc.ioslockscreen;

import java.io.IOException;
import java.util.ArrayList;

import test.aplock.AppLockActivity;

import com.haibison.android.lockpattern.LockPatternActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Test_theme extends Activity implements OnClickListener {

	private EditText mEdtxtPassword;
	String password = "";
	TextView txtAppName;
	SharedPreferences mSharedPreference, prefs;;
	Editor mEditor, edit;;
	String pass;
	ArrayList<String> tmp = new ArrayList<String>();
	ImageButton delete;
	ToggleButton tb1, tb2, tb3, tb4;
	View ll;
	SoundPool sp;
	boolean loaded, isAnswered;
	int soundID;
	float volume;
	boolean sound_flag, vib_flag, wasNotSet;
	Vibrator vb;
	Typeface face;
	Animation shake;
	String val;
	int id;
	AssetManager asset;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.activity_screen_lok1);

		prefs = PreferenceManager.getDefaultSharedPreferences(Test_theme.this);
		edit = prefs.edit();

		SharedPreferences myPrefs = this.getSharedPreferences("theme",
				MODE_WORLD_READABLE);

		SharedPreferences myPrefs2 = this.getSharedPreferences("tstyle",
				MODE_WORLD_READABLE);

		String style = myPrefs2.getString("style", "keypade");
		// Toast.makeText(getApplicationContext(), style, 2000).show();

		if (getIntent().getExtras() != null) {
			id = getIntent().getIntExtra("pos", 0);
		} else {
			id = myPrefs.getInt("position", 0);
		}

		if (style.equals("keypade")) {
			
			 //Toast.makeText(getApplicationContext(),""+id, 2000).show();

			switch (id) {
			case 0:
				setContentView(R.layout.activity_screen_coffe);
				break;
			case 1:
				setContentView(R.layout.activity_screen_con2);
				break;
			case 2:
				setContentView(R.layout.activity_screen_con3);
				break;
			case 3:
				setContentView(R.layout.activity_screen_con4);
				break;
			case 4:
				setContentView(R.layout.activity_screen_con5);
				break;
			case 5:
				setContentView(R.layout.activity_screen_con6);
				break;
			case 6:
				setContentView(R.layout.activity_screen_con7);
				break;
			case 7:
				setContentView(R.layout.activity_screen_con8);
				break;

			}
		} else if (style.equals("pattern")) {
			Intent it = new Intent(LockPatternActivity.ACTION_COMPARE_PATTERN,
					null, Test_theme.this, LockPatternActivity.class);
			// if (f.exists()) {
			// it.putExtra("path", f.getAbsolutePath());
			// }
			it.putExtra(
					LockPatternActivity.EXTRA_INTENT_ACTIVITY_FORGOT_PATTERN,
					new Intent(this, ByQuestionAnswerActivity.class));
			it.putExtra("enableBack", true);
			it.putExtra("topBg", false);
			it.putExtra("s_flag", prefs.getBoolean("sound_chap", true));
			it.putExtra("vib_flag", prefs.getBoolean("vib_chap", false));
			startActivityForResult(it, 007);

		}

		Intent it = getIntent();
		if (it.getExtras() != null) {
			isAnswered = it.getBooleanExtra("isAnswered", false);
		}
		ll = findViewById(R.id.ll_dots);
		vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		sp.setOnLoadCompleteListener(new OnLoadCompleteListener() {

			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId,
					int status) {
				loaded = true;
			}
		});
		soundID = sp.load(this, R.raw.click, 1);

		AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		float actualVolume = (float) audioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float maxVolume = (float) audioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		volume = actualVolume / maxVolume;
		AssetManager asset = getAssets();
		mSharedPreference = PreferenceManager
				.getDefaultSharedPreferences(Test_theme.this);
		String name = "";
		try {
			name = mSharedPreference.getString("name", asset.list("set")[0]);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Bitmap icon = null;
		try {
			icon = BitmapFactory.decodeStream(asset.open("set/" + name));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		face = Typeface.createFromAsset(asset, "fonts/h.ttf");
		// findViewById(R.id.rll_main).setBackgroundDrawable(
		// new BitmapDrawable(icon));

		sound_flag = mSharedPreference.getBoolean("sound_chap", true);
		vib_flag = mSharedPreference.getBoolean("vib_chap", false);
		pass = mSharedPreference.getString("password", "" + 123);

		// Toast.makeText(getApplicationContext(),pass,10000);

		txtAppName = (TextView) findViewById(R.id.lock_textView1);
		if (pass.length() > 3) {
			txtAppName.setText("Enter your Passcode");
		} else {
			wasNotSet = true;
			txtAppName.setText("Choose your Passcode");
		}
		mEditor = mSharedPreference.edit();

		mEdtxtPassword = (EditText) findViewById(R.id.lock_editText1);
		mEdtxtPassword.setEnabled(false);

		ImageButton imgBtnOne = (ImageButton) findViewById(R.id.imageButton1);
		ImageButton imgBtnTwo = (ImageButton) findViewById(R.id.imageButton2);
		ImageButton imgBtnThree = (ImageButton) findViewById(R.id.imageButton3);
		ImageButton imgBtnFour = (ImageButton) findViewById(R.id.imageButton4);
		ImageButton imgBtnFive = (ImageButton) findViewById(R.id.imageButton5);
		ImageButton imgBtnSix = (ImageButton) findViewById(R.id.imageButton6);
		ImageButton imgBtnSeven = (ImageButton) findViewById(R.id.imageButton7);
		ImageButton imgBtnEight = (ImageButton) findViewById(R.id.imageButton8);
		ImageButton imgBtnNine = (ImageButton) findViewById(R.id.imageButton9);
		ImageButton imgBtnZero = (ImageButton) findViewById(R.id.imageButton0);
		TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t0;
		t1 = (TextView) findViewById(R.id.textView1);
		t2 = (TextView) findViewById(R.id.textView2);
		t3 = (TextView) findViewById(R.id.textView3);
		t4 = (TextView) findViewById(R.id.textView4);
		t5 = (TextView) findViewById(R.id.textView5);
		t6 = (TextView) findViewById(R.id.textView6);
		t7 = (TextView) findViewById(R.id.textView7);
		t8 = (TextView) findViewById(R.id.textView8);
		t9 = (TextView) findViewById(R.id.textView9);
		t0 = (TextView) findViewById(R.id.textView0);
		t1.setTypeface(face);
		t2.setTypeface(face);
		t3.setTypeface(face);
		t4.setTypeface(face);
		t5.setTypeface(face);
		t6.setTypeface(face);
		t7.setTypeface(face);
		t8.setTypeface(face);
		t9.setTypeface(face);
		t0.setTypeface(face);
		// TextView lockText = (TextView) findViewById(R.id.lock_textView1);
		// lockText.setTypeface(face);

		findViewById(R.id.emergency).setOnClickListener(this);
		// cancel = (Button) findViewById(R.id.cancel);
		delete = (ImageButton) findViewById(R.id.delete);
		// cancel.setOnClickListener(this);
		delete.setOnClickListener(this);
		imgBtnZero.setOnClickListener(btnClickListener);
		imgBtnOne.setOnClickListener(btnClickListener);
		imgBtnTwo.setOnClickListener(btnClickListener);
		imgBtnThree.setOnClickListener(btnClickListener);
		imgBtnFour.setOnClickListener(btnClickListener);
		imgBtnFive.setOnClickListener(btnClickListener);
		imgBtnSix.setOnClickListener(btnClickListener);
		imgBtnSeven.setOnClickListener(btnClickListener);
		imgBtnEight.setOnClickListener(btnClickListener);
		imgBtnNine.setOnClickListener(btnClickListener);
		tb1 = (ToggleButton) findViewById(R.id.imageView1);
		tb2 = (ToggleButton) findViewById(R.id.imageView2);
		tb3 = (ToggleButton) findViewById(R.id.imageView3);
		tb4 = (ToggleButton) findViewById(R.id.imageView4);
		shake = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.shake);
		shake.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				InsertDot();
			}
		});
	}

	OnClickListener btnClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {

			switch (view.getId()) {
			case R.id.imageButton0:
				password += "0";
				tmp.add("0");
				mEdtxtPassword.setText(password);

				break;
			case R.id.imageButton1:
				tmp.add("1");
				password += "1";
				mEdtxtPassword.setText(password);

				break;
			case R.id.imageButton2:
				tmp.add("2");
				password += "2";
				mEdtxtPassword.setText(password);

				break;
			case R.id.imageButton3:
				tmp.add("3");
				password += "3";
				mEdtxtPassword.setText(password);

				break;
			case R.id.imageButton4:
				tmp.add("4");
				password += "4";
				mEdtxtPassword.setText(password);

				break;
			case R.id.imageButton5:
				tmp.add("5");
				password += "5";
				mEdtxtPassword.setText(password);

				break;
			case R.id.imageButton6:
				tmp.add("6");
				password += "6";
				mEdtxtPassword.setText(password);

				break;
			case R.id.imageButton7:
				tmp.add("7");
				password += "7";
				mEdtxtPassword.setText(password);

				break;
			case R.id.imageButton8:
				tmp.add("8");
				password += "8";
				mEdtxtPassword.setText(password);

				break;
			case R.id.imageButton9:
				tmp.add("9");
				password += "9";
				mEdtxtPassword.setText(password);

				break;
			}
			if (loaded && sound_flag) {
				sp.play(soundID, volume, volume, 1, 0, 1f);
			}
			InsertDot();
			exitActivity();

		}
	};

	void InsertDot() {

		switch (password.length()) {
		case 0:
			// cancel.setVisibility(View.VISIBLE);
			// delete.setVisibility(View.GONE);
			tb1.setChecked(false);
			tb2.setChecked(false);
			tb3.setChecked(false);
			tb4.setChecked(false);
			break;
		case 1:

			tb1.setChecked(true);
			tb2.setChecked(false);
			tb3.setChecked(false);
			tb4.setChecked(false);
			break;
		case 2:

			tb1.setChecked(true);
			tb2.setChecked(true);
			tb3.setChecked(false);
			tb4.setChecked(false);
			break;
		case 3:

			tb1.setChecked(true);
			tb2.setChecked(true);
			tb3.setChecked(true);
			tb4.setChecked(false);
			break;
		case 4:

			tb1.setChecked(true);
			tb2.setChecked(true);
			tb3.setChecked(true);
			tb4.setChecked(true);
			break;
		}
	}

	private void exitActivity() {
		if (vib_flag) {
			vb.vibrate(20);
		}
		if (mEdtxtPassword.getText().length() == 4) {

			if (wasNotSet) {
				Intent itQue = new Intent(Test_theme.this,
						QuestionActivity.class);
				itQue.putExtra("pwd", mEdtxtPassword.getText().toString()
						.trim());
				itQue.putExtra("doOpenAct", false);
				startActivity(itQue);
				finish();
			} else {
				if (mEdtxtPassword.getText().toString().equals(pass)) {
					startActivity(new Intent(getApplicationContext(),
							MainActivity.class));
					finish();
				} else {
					ll.startAnimation(shake);
					mEdtxtPassword.setText("");
					password = "";
					tmp.clear();
				}
			}

		} else {
			// cancel.setVisibility(View.GONE);
			delete.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// case R.id.cancel:
		// finish();
		// break;
		case R.id.delete:
			password = password.replaceFirst(".$", "");
			try {
				tmp.remove(tmp.size() - 1);
			} catch (Exception e) {

			}
			InsertDot();
			break;
		case R.id.emergency:
			startActivity(new Intent(getApplicationContext(),
					ByQuestionAnswerActivity.class));
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

		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK && requestCode == 007) {
			startActivity(new Intent(getApplicationContext(),
					MainActivity.class));
			finish();
		} else {
			finish();
		}

	}
}
