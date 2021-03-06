package ccc.ioslockscreen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class QuestionActivity extends Activity implements OnClickListener {
	EditText question, answer;
	Button next;
	SharedPreferences prefs;
	Editor edit;
	String pass = null;
	boolean doOpenAct;
	String style;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_question);
		prefs = PreferenceManager
				.getDefaultSharedPreferences(QuestionActivity.this);
		edit = prefs.edit();
		
		SharedPreferences myPrefs2 = this.getSharedPreferences("tstyle",
				MODE_WORLD_READABLE);
		style = myPrefs2.getString("style", "keypade");
		
		question = (EditText) findViewById(R.id.question);
		answer = (EditText) findViewById(R.id.answer);
		next = (Button) findViewById(R.id.next);
		next.setOnClickListener(this);
		if (getIntent().getExtras() != null) {
			pass = getIntent().getStringExtra("pwd");
			doOpenAct = getIntent().getBooleanExtra("doOpenAct", true);
		} else {
			Toast.makeText(getApplicationContext(), "Error please try again",
					Toast.LENGTH_LONG).show();
			finish();

		}
		Button rate = (Button) findViewById(R.id.rate);
		Button more = (Button) findViewById(R.id.more);

		rate.setOnClickListener(this);
		more.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.next:
			String q,
			ans;
			q = question.getText().toString();
			ans = answer.getText().toString();
			if (!q.equalsIgnoreCase("") && !ans.equalsIgnoreCase("")) {
				if (!q.equalsIgnoreCase("")) {
					edit.putString("question", q);
					if (!ans.equalsIgnoreCase("")) {
						edit.putString("answer", ans);
					}

				}
				edit.putString("password", pass);
				edit.putBoolean("default", true);
				edit.putBoolean("pin", true);
				edit.commit();
				if(style.equals("keypade"))
				{
				Toast.makeText(getApplicationContext(),
						"Your Password Has Been Set" + pass, Toast.LENGTH_LONG)
						.show();
				}
				else if(style.equals("pattern"))
				{
					Toast.makeText(getApplicationContext(),
							"Your New Pattern Has Been Set", Toast.LENGTH_LONG)
							.show();
				}
				
				if (!doOpenAct)
					startActivity(new Intent(getApplicationContext(),
							MainActivity.class));
				finish();
			} else {
				Toast.makeText(QuestionActivity.this,
						"Please Enter One of Value", Toast.LENGTH_LONG).show();
			}
			edit.commit();
			break;
		case R.id.rate:

			Intent it_rate = new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id="
							+ getPackageName()));
			startActivity(it_rate);
			break;
		case R.id.more:

			Intent it_more = new Intent(QuestionActivity.this,
					ExtraActivity.class);
			startActivity(it_more);
			break;
		}
	}

}
