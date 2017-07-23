package test.aplock;

import java.io.IOException;
import java.util.ArrayList;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import ccc.ioslockscreen.ByQuestionAnswerActivity;
import ccc.ioslockscreen.MainActivity;
import ccc.ioslockscreen.R;
import ccc.ioslockscreen.Test_theme;


public class AppLockActivity extends Activity implements OnClickListener {

	private EditText mEdtxtPassword;
	String password = "";
	SharedPreferences mSharedPreference;
	Editor mEditor;
	String pass;
	ArrayList<String> tmp = new ArrayList<String>();
	ImageButton delete, cancel;
	ToggleButton tb1, tb2, tb3, tb4;
	LinearLayout ll;
	SoundPool sp;
	boolean loaded;
	int soundID,id;
	float volume;
	boolean sound_flag, vib_flag;
	Vibrator vb;
	ArrayList<ImageButton> list = new ArrayList<ImageButton>();
	ArrayList<Bitmap> blist = new ArrayList<Bitmap>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(0, 0);
		//setContentView(R.layout.activity_lock_screen);
		
		
		SharedPreferences myPrefs = this.getSharedPreferences("theme",
				MODE_WORLD_READABLE);

		SharedPreferences myPrefs2 = this.getSharedPreferences("tstyle",
				MODE_WORLD_READABLE);
		
		
		id = myPrefs.getInt("position", 0);
		
		String style = myPrefs2.getString("style","keypade");
			
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

		
		ll = (LinearLayout) findViewById(R.id.ll_dots);
		face = Typeface.createFromAsset(getAssets(), "fonts/h.ttf");
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
		mSharedPreference = PreferenceManager
				.getDefaultSharedPreferences(AppLockActivity.this);
		pass = mSharedPreference.getString("password", "" + 176);
		sound_flag = mSharedPreference.getBoolean("sound_chap", false);
		vib_flag = mSharedPreference.getBoolean("vib_chap", false);
		pass = mSharedPreference.getString("password", "" + 123);
		mEditor = mSharedPreference.edit();
		//((Button) findViewById(R.id.emergency)).setOnClickListener(this);
		
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				if (Looper.myLooper() == null)
					Looper.prepare();
				InitViewResources();
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
//				int i = 0;
//				for (ImageButton ib : list) {
//
//					ib.setImageBitmap(blist.get(i));
//					i++;
//				}
				super.onPostExecute(result);
			}
		}.execute();
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

	Animation shake;

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
			//cancel.setVisibility(View.VISIBLE);
			//delete.setVisibility(View.GONE);
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

			if (mEdtxtPassword.getText().toString().equals(pass)) {
				if (MyAppLockService.class != null
						&& MyAppLockService.pack != null
						&& MyAppLockService.locked_list != null) {
					MyAppLockService.locked_list.remove(MyAppLockService.pack);
					finish();
				}
			}

			else {

				mEdtxtPassword.setText("");
				password = "";
				tmp.clear();
				ll.startAnimation(shake);
				vb.vibrate(300);

			}
		} else {
			//cancel.setVisibility(View.GONE);
			delete.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//		case R.id.cancel:
//			onBackPressed();
//			break;
		case R.id.emergency:
			Intent it = new Intent(getApplicationContext(),
					ByQuestionAnswerActivity.class);
			it.putExtra("isFromReset", true);
			startActivity(it);
			break;
		case R.id.delete:
			if (!tmp.isEmpty()) {
				password = password.replaceFirst(".$", "");
				tmp.remove(tmp.size() - 1);
				InsertDot();
				break;
			}
		}
	}

	@Override
	public void onBackPressed() {
		Intent it_Home = new Intent(Intent.ACTION_MAIN);
		it_Home.addCategory(Intent.CATEGORY_HOME);
		it_Home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(it_Home);
		super.onBackPressed();
	}

	void InitViewResources() {
		mSharedPreference = PreferenceManager
				.getDefaultSharedPreferences(AppLockActivity.this);
		pass = mSharedPreference.getString("password", "" + 123);
		final TextView txtAppName = (TextView) findViewById(R.id.lock_textView1);
		final RelativeLayout rl_all = (RelativeLayout) findViewById(R.id.rll_main);

		
		mEditor = mSharedPreference.edit();

		mEdtxtPassword = (EditText) findViewById(R.id.lock_editText1);
		
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

		list.add(imgBtnZero);
		list.add(imgBtnOne);
		list.add(imgBtnTwo);
		list.add(imgBtnThree);
		list.add(imgBtnFour);
		list.add(imgBtnFive);
		list.add(imgBtnSix);
		list.add(imgBtnSeven);
		list.add(imgBtnEight);
		list.add(imgBtnNine);
//		for (ImageButton ib : list) {
//			Bitmap bmp = BitmapFactory.decodeFile(getFilesDir()
//					.getAbsolutePath() + "/dp" + ib.getTag() + ".png");
//			if (bmp != null)
//				blist.add(bmp);
//			else {
//				blist.add(BitmapFactory.decodeResource(getResources(),
//						R.drawable.pin_number_btn_normal));
//			}
//		}
		final TextView lockText = (TextView) findViewById(R.id.lock_textView1);
		final TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t0;
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
		
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				mEdtxtPassword.setEnabled(false);

				if (pass.equalsIgnoreCase("null") || pass.length() < 4) {
					txtAppName.setText("Choose your Passcode");
				} else {
					txtAppName.setText("Enter Passcode");
				}
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
				lockText.setTypeface(face);
//				rl_all.setBackgroundDrawable(new BitmapDrawable(
//						initBackground()));
			}
		});

		//cancel = (Button) findViewById(R.id.cancel);
		delete = (ImageButton) findViewById(R.id.delete);
		//cancel.setOnClickListener(AppLockActivity.this);
		delete.setOnClickListener(AppLockActivity.this);
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
		ll = (LinearLayout) findViewById(R.id.ll_dots);
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

	Typeface face;

	Bitmap initBackground() {
		mSharedPreference = PreferenceManager
				.getDefaultSharedPreferences(AppLockActivity.this);
		sound_flag = mSharedPreference.getBoolean("sound_chap", true);
		vib_flag = mSharedPreference.getBoolean("vib_chap", false);
		AssetManager asset = getAssets();

		Bitmap icon = null;
		if (mSharedPreference.getBoolean("wallpaper_from", false)) {

			icon = BitmapFactory.decodeFile(mSharedPreference.getString(
					"wallpaper_uri", ""));

		} else {
			if (!mSharedPreference.getBoolean("wallpaper_asset", false)) {
				String name1 = "";
				try {
					name1 = mSharedPreference.getString("name",
							asset.list("set")[0]);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					icon = BitmapFactory.decodeStream(asset
							.open("set/" + name1));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {

			}
		}
		return icon;
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		finish();
	}
	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if (resultCode == RESULT_OK && requestCode == 007) {
//			startActivity(new Intent(getApplicationContext(),
//					MainActivity.class));
//			finish();
//		} else {
//			finish();
//		}
//
//	}

}