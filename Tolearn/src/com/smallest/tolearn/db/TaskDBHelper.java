package com.smallest.tolearn.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.smallest.tolearn.dao.BaseTask;

public class TaskDBHelper extends BaseDBHelper {
	public static final String DB_NAME = "tolearn.db";

	public TaskDBHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	public TaskDBHelper(Context context, int version) {
		super(context, DB_NAME, null, version);
	}

	public TaskDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DB_NAME, null, version);
	}

	public static synchronized TaskDBHelper getDBHelper(Context context) {
		return new TaskDBHelper(context, 1);
	}

	public static class TaskTb {
		public static final String TB = "tb_task";

		public static interface Colums {
			public static final String TITLE = "title";
			public static final String START_TIME = "start_time";
			public static final String END_TIME = "end_time";
			public static final String DESC = "desc";
			public static final String TID = "tid";
			public static final String TAG = "tag";
			public static final String SRC = "src";
			public static String[] DEFAULT_QUERY = { TITLE, DESC, TID,
					START_TIME, END_TIME, TAG, SRC };
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTaskTable = "CREATE TABLE " + TaskTb.TB + " ("
				+ TaskTb.Colums.TITLE + "  TEXT," + TaskTb.Colums.START_TIME
				+ "  TEXT," + TaskTb.Colums.END_TIME + "  TEXT,"
				+ TaskTb.Colums.DESC + "  TEXT," + "  TEXT,"
				+ TaskTb.Colums.TAG + "  TEXT," + TaskTb.Colums.SRC + "  TEXT,"
				+ TaskTb.Colums.TID + "  TEXT )";
		db.execSQL(createTaskTable);
	}

	public static ArrayList<BaseTask> getCurrentBaseTask(Context context) {
		TaskDBHelper helper = getDBHelper(context);
		SQLiteDatabase database = helper.getWDatabase();
		Cursor cursor = database.query(TaskTb.TB, TaskTb.Colums.DEFAULT_QUERY,
				null, null, null, null, null);
		BaseTask info = null;
		ArrayList<BaseTask> taskList = new ArrayList<BaseTask>();
		if (cursor.moveToFirst()) {
			do {
				info = new BaseTask();
				info.setTitle(cursor.getString(cursor
						.getColumnIndexOrThrow(TaskTb.Colums.TITLE)));
				info.setStartTime(cursor.getString(cursor
						.getColumnIndexOrThrow(TaskTb.Colums.START_TIME)));
				info.setEndTime(cursor.getString(cursor
						.getColumnIndexOrThrow(TaskTb.Colums.END_TIME)));
				info.setDesc(cursor.getString(cursor
						.getColumnIndexOrThrow(TaskTb.Colums.DESC)));
				info.setTid(cursor.getString(cursor
						.getColumnIndexOrThrow(TaskTb.Colums.TID)));
				info.setTag(cursor.getString(cursor
						.getColumnIndexOrThrow(TaskTb.Colums.TAG)));
				info.setSrc(cursor.getString(cursor
						.getColumnIndexOrThrow(TaskTb.Colums.SRC)));
				taskList.add(info);
			} while (cursor.moveToNext());
		}
		cursor.close();
		database.close();
		return taskList;
	}

	public static BaseTask getTaskFromId(Context context, String id) {
		TaskDBHelper helper = getDBHelper(context);
		SQLiteDatabase database = helper.getWDatabase();
		Cursor cursor = database
				.query(TaskTb.TB, TaskTb.Colums.DEFAULT_QUERY,
						TaskTb.Colums.TID + "=?", new String[] { id }, null,
						null, null);
		BaseTask info = null;
		if (cursor.moveToFirst()) {
			do {
				info = new BaseTask();
				info.setTitle(cursor.getString(cursor
						.getColumnIndexOrThrow(TaskTb.Colums.TITLE)));
				info.setStartTime(cursor.getString(cursor
						.getColumnIndexOrThrow(TaskTb.Colums.START_TIME)));
				info.setEndTime(cursor.getString(cursor
						.getColumnIndexOrThrow(TaskTb.Colums.END_TIME)));
				info.setDesc(cursor.getString(cursor
						.getColumnIndexOrThrow(TaskTb.Colums.DESC)));
				info.setTid(cursor.getString(cursor
						.getColumnIndexOrThrow(TaskTb.Colums.TID)));
				info.setTag(cursor.getString(cursor
						.getColumnIndexOrThrow(TaskTb.Colums.TAG)));
				info.setSrc(cursor.getString(cursor
						.getColumnIndexOrThrow(TaskTb.Colums.SRC)));
				break;
			} while (cursor.moveToNext());
		}
		cursor.close();
		database.close();
		return info;
	}

	public static void insertInfo(Context context, BaseTask task) {
		if (task == null) {
			return;
		}
		TaskDBHelper helper = getDBHelper(context);
		SQLiteDatabase database = helper.getWDatabase();
		try {
			ContentValues values = createContentValue(task);
			if (values != null) {
				database.insert(TaskTb.TB, null, values);
			}
		} catch (Exception e) {
		}
		database.close();
	}

	public static void updateInfo(Context context, BaseTask task) {
		if (task == null) {
			return;
		}

		TaskDBHelper helper = getDBHelper(context);
		SQLiteDatabase database = helper.getWDatabase();
		Cursor cursor = database.query(TaskTb.TB, TaskTb.Colums.DEFAULT_QUERY,
				TaskTb.Colums.TID + "=?", new String[] { task.getTid() }, null,
				null, null);
		if (!cursor.moveToFirst()) {
			insertInfo(context, task);
			return;
		}
		try {
			ContentValues values = createContentValue(task);
			if (values != null) {
				database.update(TaskTb.TB, values, TaskTb.Colums.TID + "=?",
						new String[] { task.getTid() });
			}
		} catch (Exception e) {
		}
		database.close();
	}

	public static ContentValues createContentValue(BaseTask task) {
		if (task != null) {
			ContentValues cv = new ContentValues();
			cv.put(TaskTb.Colums.TITLE, task.getTitle());
			cv.put(TaskTb.Colums.START_TIME, task.getStartTime());
			cv.put(TaskTb.Colums.END_TIME, task.getEndTime());
			cv.put(TaskTb.Colums.DESC, task.getDesc());
			cv.put(TaskTb.Colums.TAG, task.getTag());
			cv.put(TaskTb.Colums.TID, task.getTid());
			return cv;
		}
		return null;
	}

	public synchronized SQLiteDatabase getWDatabase() {
		try {
			return getWritableDatabase();
		} catch (Exception e) {
			Log.e("MWP", e.getMessage());
			return null;
		}
	}

}
