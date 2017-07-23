package ccc.ioslockscreen;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class Theme_Grid_Display extends Activity implements OnItemClickListener {

	GridView gridView;
	ArrayList<Item> gridArray = new ArrayList<Item>();
	Theme_Grid_Adpter customGridAdapter;
	SharedPreferences prefs;
	Editor edit;
	int w, h;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.activity_theme_display);

		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		w = outMetrics.widthPixels;
		h = outMetrics.heightPixels;

		prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		edit = prefs.edit();

		// Bitmap screen1 = BitmapFactory.decodeResource(this.getResources(),
		// R.drawable.ch1_screenshot);
		// Bitmap screen2 = BitmapFactory.decodeResource(this.getResources(),
		// R.drawable.ch2_screenshot);
		// Bitmap screen3 = BitmapFactory.decodeResource(this.getResources(),
		// R.drawable.lok1_screenshot);
		// Bitmap screen4 = BitmapFactory.decodeResource(this.getResources(),
		// R.drawable.lok2_screenshot);
		// Bitmap screen5 = BitmapFactory.decodeResource(this.getResources(),
		// R.drawable.ch7_screenshot);
		// Bitmap screen6 = BitmapFactory.decodeResource(this.getResources(),
		// R.drawable.ch8_screenshot);
		// Bitmap screen7 = BitmapFactory.decodeResource(this.getResources(),
		// R.drawable.red_screenshot);

		Bitmap screen1 = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.coffe_screenshot);
		Bitmap screen2 = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.con2_screenshot);
		Bitmap screen3 = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.con3_screenshot);
		Bitmap screen4 = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.con4_screenshot);
		Bitmap screen5 = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.con8_screenshot);
		Bitmap screen6 = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.con5_screenshot);
		Bitmap screen7 = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.con6_screenshot);
		Bitmap screen8 = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.con7_screenshot);
//		Bitmap screen9 = BitmapFactory.decodeResource(this.getResources(),
//				R.drawable.con9_screenshot);
//		Bitmap screen10 = BitmapFactory.decodeResource(this.getResources(),
//				R.drawable.con10_screenshot);
		
		
		gridArray.add(new Item(screen1, "sc1"));
		gridArray.add(new Item(screen2, "sc2"));
		gridArray.add(new Item(screen3, "sc3"));
		gridArray.add(new Item(screen4, "sc4"));
		gridArray.add(new Item(screen5, "sc5"));
		gridArray.add(new Item(screen6, "sc6"));
		gridArray.add(new Item(screen7, "sc7"));
		gridArray.add(new Item(screen8, "sc8"));
//		gridArray.add(new Item(screen9, "sc9"));
//		gridArray.add(new Item(screen10, "sc10"));

		gridView = (GridView) findViewById(R.id.gridView1);
		customGridAdapter = new Theme_Grid_Adpter(this,
				R.layout.raw_theme_display, gridArray);
		gridView.setAdapter(customGridAdapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				if (position == 0) {

					// Intent i=new
					// Intent(Theme_Grid_Display.this,Test_theme.class);
					// i.putExtra("pos",""+position);
					// startActivity(i);

					SharedPreferences myPrefs = getSharedPreferences("theme",
							MODE_WORLD_READABLE);
					SharedPreferences.Editor prefsEditor = myPrefs.edit();
					prefsEditor.putInt("position", position);
					prefsEditor.commit();
					Intent it = new Intent(Theme_Grid_Display.this,
							MainActivity.class);
					it.putExtra("pos", position);
					startActivity(it);
					finish();

				} else if (position == 1) {
					SharedPreferences myPrefs = getSharedPreferences("theme",
							MODE_WORLD_READABLE);
					SharedPreferences.Editor prefsEditor = myPrefs.edit();
					prefsEditor.putInt("position", position);
					prefsEditor.commit();
					Intent it = new Intent(Theme_Grid_Display.this,
							MainActivity.class);
					it.putExtra("pos", position);
					startActivity(it);
					finish();
				} else if (position == 2) {
					SharedPreferences myPrefs = getSharedPreferences("theme",
							MODE_WORLD_READABLE);
					SharedPreferences.Editor prefsEditor = myPrefs.edit();
					prefsEditor.putInt("position", position);
					prefsEditor.commit();
					Intent it = new Intent(Theme_Grid_Display.this,
							MainActivity.class);
					it.putExtra("pos", position);
					startActivity(it);
					finish();
				} else if (position == 3) {
					SharedPreferences myPrefs = getSharedPreferences("theme",
							MODE_WORLD_READABLE);
					SharedPreferences.Editor prefsEditor = myPrefs.edit();
					prefsEditor.putInt("position", position);
					prefsEditor.commit();
					Intent it = new Intent(Theme_Grid_Display.this,
							MainActivity.class);
					it.putExtra("pos", position);
					startActivity(it);
					finish();
				} else if (position == 4) {
					SharedPreferences myPrefs = getSharedPreferences("theme",
							MODE_WORLD_READABLE);
					SharedPreferences.Editor prefsEditor = myPrefs.edit();
					prefsEditor.putInt("position", position);
					prefsEditor.commit();
					Intent it = new Intent(Theme_Grid_Display.this,
							MainActivity.class);
					it.putExtra("pos", position);
					startActivity(it);
					finish();
				} else if (position == 5) {
					SharedPreferences myPrefs = getSharedPreferences("theme",
							MODE_WORLD_READABLE);
					SharedPreferences.Editor prefsEditor = myPrefs.edit();
					prefsEditor.putInt("position", position);
					prefsEditor.commit();
					Intent it = new Intent(Theme_Grid_Display.this,
							MainActivity.class);
					it.putExtra("pos", position);
					startActivity(it);
					finish();
				} else if (position == 6) {
					SharedPreferences myPrefs = getSharedPreferences("theme",
							MODE_WORLD_READABLE);
					SharedPreferences.Editor prefsEditor = myPrefs.edit();
					prefsEditor.putInt("position", position);
					prefsEditor.commit();
					Intent it = new Intent(Theme_Grid_Display.this,
							MainActivity.class);
					it.putExtra("pos", position);
					startActivity(it);
					finish();
				} else if (position == 7) {
					SharedPreferences myPrefs = getSharedPreferences("theme",
							MODE_WORLD_READABLE);
					SharedPreferences.Editor prefsEditor = myPrefs.edit();
					prefsEditor.putInt("position", position);
					prefsEditor.commit();
					Intent it = new Intent(Theme_Grid_Display.this,
							MainActivity.class);
					it.putExtra("pos", position);
					startActivity(it);
					finish();
				}
//				else if (position == 8) {
//					SharedPreferences myPrefs = getSharedPreferences("theme",
//							MODE_WORLD_READABLE);
//					SharedPreferences.Editor prefsEditor = myPrefs.edit();
//					prefsEditor.putInt("position", position);
//					prefsEditor.commit();
//					Intent it = new Intent(Theme_Grid_Display.this,
//							MainActivity.class);
//					it.putExtra("pos", position);
//					startActivity(it);
//					finish();
//				}
//				else if (position == 9) {
//					SharedPreferences myPrefs = getSharedPreferences("theme",
//							MODE_WORLD_READABLE);
//					SharedPreferences.Editor prefsEditor = myPrefs.edit();
//					prefsEditor.putInt("position", position);
//					prefsEditor.commit();
//					Intent it = new Intent(Theme_Grid_Display.this,
//							MainActivity.class);
//					it.putExtra("pos", position);
//					startActivity(it);
//					finish();
//				}

			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	//
	// @Override
	// public void onBackPressed() {
	// Intent backtoHome = new Intent(this, MainActivity.class);
	// finish();
	// startActivity(backtoHome);
	// }
	//
	// public void onClick(View arg0) {
	// onBackPressed();
	// }

	@Override
	public void onUserLeaveHint() { // this only executes when Home is selected.
		// do stuff
		super.onUserLeaveHint();
	}

}
