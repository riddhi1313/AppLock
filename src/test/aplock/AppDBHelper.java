package test.aplock;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDBHelper extends SQLiteOpenHelper {
	private HashMap<String, Integer> map;
	private static String DATABASE_NAME = "myDb";
	private static String TABLE_NAME = "apps";
	private static String NAME = "name";
	private static String STATE = "state";
	Context c;

	public AppDBHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
		c = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table apps "
				+ "(id integer primary key,name text not null unique,state integer)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS apps");
		onCreate(db);

	}

	public boolean insertApp(String name, int state) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", name);
		// values.put("icon", icon_array);
		values.put("state", state);
		db.insert(TABLE_NAME, null, values);
		return true;
	}

	public boolean updateApp(String name, int state) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		// values.put(NAME, name);
		values.put(STATE, state);
		db.update(TABLE_NAME, values, "name = ? ", new String[] { name });
		return true;
	}

	public HashMap<String, Integer> getAllApps() {
		map = new HashMap<String, Integer>();
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cr = db.rawQuery("select * from apps", null);
		cr.moveToFirst();
		while (cr.isAfterLast() == false) {
			map.put(cr.getString(cr.getColumnIndex(NAME)),
					cr.getInt(cr.getColumnIndex(STATE)));
			cr.moveToNext();
		}

		return map;
	}

	public ArrayList<String> getApsHasStateTrue() {
		ArrayList<String> list = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cr = db.rawQuery("select * from apps where state='" + 1 + "'",
				null);
		cr.moveToFirst();
		while (cr.isAfterLast() == false) {
			list.add(cr.getString(cr.getColumnIndex(NAME)));
			cr.moveToNext();
		}
		return list;
	}

	public boolean getState(String name) {
		boolean flag = true;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor i = db.rawQuery("select state from apps where name=" + name,
				null);
		i.moveToFirst();
		int state = i.getInt(i.getColumnIndex(STATE));
		if (state > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean removeApp(String packName) {
		SQLiteDatabase db = getWritableDatabase();

		try {
			db.delete(TABLE_NAME, "name=?", new String[] { packName });
		} catch (Exception ex) {
			return false;
		}
		return true;
	}
}
