package com.smallest.tolearn.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
			// public static final String TAG = "tag";
			public static final String SRC = "src";
			public static final String STATE = "state";
			public static String[] DEFAULT_QUERY = { TITLE, DESC, TID,
					START_TIME, END_TIME, SRC, STATE };
			// public static String[] TAG_SEARCH = { TAG };
		}
	}

	public static class TagTb {
		public static final String TB = "tb_tag";

		public static interface Colums {
			public static final String TID = "tid";
			public static final String TAG = "tag";
			public static String[] DEFAULT_QUERY = { TID, TAG };
			public static String[] TAG_QUERY = { TAG };
		}
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTaskTable = "CREATE TABLE " + TaskTb.TB + " ("
				+ TaskTb.Colums.TITLE + "  TEXT," + TaskTb.Colums.START_TIME
				+ "  TEXT," + TaskTb.Colums.END_TIME + "  TEXT,"
				+ TaskTb.Colums.DESC + "  TEXT," + "  TEXT,"
				+ TaskTb.Colums.SRC + "  TEXT," + TaskTb.Colums.TID + "  TEXT,"
				+ TaskTb.Colums.STATE + " INT )";
		db.execSQL(createTaskTable);
		String createTagTable = "CREATE TABLE " + TagTb.TB + " ("
				+ TagTb.Colums.TID + " TEXT," + TagTb.Colums.TAG + " TEXT)";
		db.execSQL(createTagTable);
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
				info.setSrc(cursor.getString(cursor
						.getColumnIndexOrThrow(TaskTb.Colums.SRC)));
				info.setState(cursor.getInt(cursor
						.getColumnIndexOrThrow(TaskTb.Colums.STATE)));
				Cursor tagCursor = database.query(TagTb.TB,
						TagTb.Colums.DEFAULT_QUERY, TaskTb.Colums.TID + "=?",
						new String[] { info.getTid() }, null, null, null);
				List<String> tagList = new ArrayList<String>();
				if (tagCursor.moveToFirst()) {
					do {
						String tag = tagCursor.getString(tagCursor
								.getColumnIndexOrThrow(TagTb.Colums.TAG));
						tagList.add(tag);
					} while (tagCursor.moveToNext());
				}
				info.setTag(tagList.toArray(new String[] {}));
				tagCursor.close();
				taskList.add(info);
			} while (cursor.moveToNext());
		}
		cursor.close();
		database.close();
		return taskList;
	}

	public static ArrayList<BaseTask> getTodoTasks(Context context) {
		TaskDBHelper helper = getDBHelper(context);
		SQLiteDatabase database = helper.getWDatabase();
		Cursor cursor = database.query(TaskTb.TB, TaskTb.Colums.DEFAULT_QUERY,
				TaskTb.Colums.STATE + "=?", new String[] { "1" }, null, null,
				null);
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
				info.setSrc(cursor.getString(cursor
						.getColumnIndexOrThrow(TaskTb.Colums.SRC)));
				info.setState(cursor.getInt(cursor
						.getColumnIndexOrThrow(TaskTb.Colums.STATE)));
				Cursor tagCursor = database.query(TagTb.TB,
						TagTb.Colums.DEFAULT_QUERY, TaskTb.Colums.TID + "=?",
						new String[] { info.getTid() }, null, null, null);
				List<String> tagList = new ArrayList<String>();
				if (tagCursor.moveToFirst()) {
					do {
						String tag = tagCursor.getString(tagCursor
								.getColumnIndexOrThrow(TagTb.Colums.TAG));
						tagList.add(tag);
					} while (tagCursor.moveToNext());
				}
				info.setTag(tagList.toArray(new String[] {}));
				tagCursor.close();
				taskList.add(info);
			} while (cursor.moveToNext());
		}
		cursor.close();
		database.close();
		return taskList;
	}

	public static ArrayList<BaseTask> getRepoTasks(Context context) {
		TaskDBHelper helper = getDBHelper(context);
		SQLiteDatabase database = helper.getWDatabase();
		Cursor cursor = database.query(TaskTb.TB, TaskTb.Colums.DEFAULT_QUERY,
				TaskTb.Colums.STATE + "=?", new String[] { "0" }, null, null,
				null);
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
				info.setSrc(cursor.getString(cursor
						.getColumnIndexOrThrow(TaskTb.Colums.SRC)));
				info.setState(cursor.getInt(cursor
						.getColumnIndexOrThrow(TaskTb.Colums.STATE)));
				Cursor tagCursor = database.query(TagTb.TB,
						TagTb.Colums.DEFAULT_QUERY, TaskTb.Colums.TID + "=?",
						new String[] { info.getTid() }, null, null, null);
				List<String> tagList = new ArrayList<String>();
				if (tagCursor.moveToFirst()) {
					do {
						String tag = tagCursor.getString(tagCursor
								.getColumnIndexOrThrow(TagTb.Colums.TAG));
						tagList.add(tag);
					} while (tagCursor.moveToNext());
				}
				info.setTag(tagList.toArray(new String[] {}));
				tagCursor.close();
				taskList.add(info);
			} while (cursor.moveToNext());
		}
		cursor.close();
		database.close();
		Log.d("mydb", "repo task size:" + taskList.size());
		return taskList;
	}

	public static List<String> getTagList(Context context) {
		TaskDBHelper helper = getDBHelper(context);
		SQLiteDatabase database = helper.getWDatabase();
		Cursor cursor = database.query(true, TagTb.TB, TagTb.Colums.TAG_QUERY,
				null, null, null, null, null, null);
		List<String> tagList = new ArrayList<String>();
		if (cursor.moveToFirst()) {
			do {
				String tagName = cursor.getString(cursor
						.getColumnIndexOrThrow(TagTb.Colums.TAG));
				tagList.add(tagName);
			} while (cursor.moveToNext());
		}
		cursor.close();
		database.close();
		return tagList;
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
				info.setSrc(cursor.getString(cursor
						.getColumnIndexOrThrow(TaskTb.Colums.SRC)));
				info.setState(cursor.getInt(cursor
						.getColumnIndexOrThrow(TaskTb.Colums.STATE)));
				Cursor tagCursor = database.query(TagTb.TB,
						TagTb.Colums.DEFAULT_QUERY, TaskTb.Colums.TID + "=?",
						new String[] { id }, null, null, null);
				List<String> tagList = new ArrayList<String>();
				if (tagCursor.moveToFirst()) {
					do {
						String tag = tagCursor.getString(tagCursor
								.getColumnIndexOrThrow(TagTb.Colums.TAG));
						tagList.add(tag);
					} while (tagCursor.moveToNext());
				}
				info.setTag(tagList.toArray(new String[] {}));
				tagCursor.close();
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
			ContentValues values = createTaskContentValue(task);
			if (values != null) {
				database.insert(TaskTb.TB, null, values);
			}
			List<ContentValues> valueList = createTagContentValue(task);
			if (valueList != null && valueList.size() > 0) {
				for (ContentValues cv : valueList) {
					database.insert(TagTb.TB, null, cv);
				}
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
			ContentValues values = createTaskContentValue(task);
			if (values != null) {
				database.update(TaskTb.TB, values, TaskTb.Colums.TID + "=?",
						new String[] { task.getTid() });
			}
			database.delete(TagTb.TB, TaskTb.Colums.TID + "=?",
					new String[] { task.getTid() });
			List<ContentValues> valueList = createTagContentValue(task);
			if (valueList != null && valueList.size() > 0) {
				for (ContentValues cv : valueList) {
					database.insert(TagTb.TB, null, cv);
				}
			}
		} catch (Exception e) {
		}
		database.close();
	}

	public static ContentValues createTaskContentValue(BaseTask task) {
		if (task != null) {
			ContentValues cv = new ContentValues();
			cv.put(TaskTb.Colums.TITLE, task.getTitle());
			cv.put(TaskTb.Colums.START_TIME, task.getStartTime());
			cv.put(TaskTb.Colums.END_TIME, task.getEndTime());
			cv.put(TaskTb.Colums.DESC, task.getDesc());
			cv.put(TaskTb.Colums.TID, task.getTid());
			cv.put(TaskTb.Colums.STATE, task.getState());
			return cv;
		}
		return null;
	}

	public static List<ContentValues> createTagContentValue(BaseTask task) {
		if (task != null && task.getTags().length > 0) {
			List<ContentValues> cvList = new ArrayList<ContentValues>();
			for (String tag : task.getTags()) {
				ContentValues cv = new ContentValues();
				cv.put(TagTb.Colums.TID, task.getTid());
				cv.put(TagTb.Colums.TAG, tag);
				cvList.add(cv);
			}
			return cvList;
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
