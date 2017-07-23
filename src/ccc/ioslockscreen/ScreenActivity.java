package ccc.ioslockscreen;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import b.b.LazyAdapter;

public class ScreenActivity extends Activity implements OnItemClickListener,
		OnClickListener {
	GridView gridView;
	ArrayList<String> gridArray = new ArrayList<String>();
	LazyAdapter customGridAdapter;
	SharedPreferences prefs;
	Editor edit;
	String[] names;
	int w, h;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_screen);
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		w = outMetrics.widthPixels;
		h = outMetrics.heightPixels;
		Button more, rate;
		rate = (Button) findViewById(R.id.rate);
		more = (Button) findViewById(R.id.more);
		rate.setOnClickListener(this);
		more.setOnClickListener(this);

		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		edit = prefs.edit();
		AssetManager asset = getAssets();
		try {
			names = getImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// set grid view item
		for (int i = 0; i < names.length; i++) {
			// Bitmap icon = null;
			// try {
			// icon = BitmapFactory
			// .decodeStream(asset.open("set/" + names[i]));
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			gridArray.add("set/" + names[i]);
		}

		gridView = (GridView) findViewById(R.id.gridView1);
		customGridAdapter = new LazyAdapter(ScreenActivity.this, gridArray);
		gridView.setAdapter(customGridAdapter);
		gridView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		edit.putString("name", names[arg2]);
		edit.putBoolean("wallpaper_from", false);
		edit.putBoolean("wallpaper_asset", false);
		edit.commit();
		finish();
	}

	private String[] getImage() throws IOException {

		AssetManager assetManager = getAssets();
		String[] files = assetManager.list("set");
		return files;

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {

		case R.id.rate:

			String pckgname = getPackageName();
			Intent it_rate = new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id="
							+ pckgname));
			startActivity(it_rate);

			break;
		case R.id.more:
			startActivity(new Intent(getApplicationContext(),
					ExtraActivity.class));
			break;
		}
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	@Override
	protected void onDestroy() {
		System.gc();
		super.onDestroy();
	}
}
