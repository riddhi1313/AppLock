package ccc.ioslockscreen;

import java.io.File;

import com.haibison.android.lockpattern.LockPatternActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ByQuestionAnswerActivity extends Activity implements
		OnClickListener {
	SharedPreferences prefs;
	Editor edit;
	boolean isFromReset;
	String style;
	SharedPreferences myPrefs;
	int CREATE_PATTERN = 456789;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_by_question_answer);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		 myPrefs = this.getSharedPreferences("tstyle",
				MODE_WORLD_READABLE);

		style = myPrefs.getString("style","keypade");
		
		if (getIntent().getExtras() != null)
			isFromReset = getIntent().getBooleanExtra("isFromReset", false);
		(findViewById(R.id.reset)).setOnClickListener(this);
		ans = (EditText) findViewById(R.id.answer);
		String t = prefs.getString("answer", "def");
		if (t.equalsIgnoreCase("def")) {
			ans.setText("Que-Ans Has not been set.Try Email");
			ans.setFocusableInTouchMode(false);
			ans.setClickable(false);
			ans.setCursorVisible(false);
			ans.setFocusable(false);
		}
		TextView que = (TextView) findViewById(R.id.question);
		que.setText(prefs.getString("question",
				"what is your best friends name?"));
		Button rate = (Button) findViewById(R.id.rate);
		Button more = (Button) findViewById(R.id.more);

		rate.setOnClickListener(this);
		more.setOnClickListener(this);
	}

	EditText ans;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.reset:
			String a = ans.getText().toString();
			if (!a.equalsIgnoreCase("")) {
				if (a.equalsIgnoreCase(prefs.getString("answer", "default"))) {

					if(style.equals("keypade"))
					{
					Intent it = new Intent(getApplicationContext(),
							Theme_Change_Activity.class);
					it.putExtra("isFromReset", isFromReset);
					it.putExtra("isAnswered", true);
					startActivity(it);
					finish();
					}
					else if(style.equals("pattern"))
					{
						Intent it = new Intent(
								LockPatternActivity.ACTION_CREATE_PATTERN,
								null, ByQuestionAnswerActivity.this,
								LockPatternActivity.class);
						it.putExtra("enableBack", false);
						startActivityForResult(it, CREATE_PATTERN);
					}
				}
				else {
					Toast.makeText(getApplicationContext(), "Wrong Answer!",
							Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(getApplicationContext(),
						"Please Enter Answer First", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.rate:

			Intent it_rate = new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id="
							+ getPackageName()));
			startActivity(it_rate);
			break;
		case R.id.more:

			Intent it_more = new Intent(ByQuestionAnswerActivity.this,
					ExtraActivity.class);
			startActivity(it_more);
			break;

		}

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CREATE_PATTERN && resultCode == RESULT_OK) {
			finish();
		} else {
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
