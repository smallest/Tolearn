package com.smallest.tolearn.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.smallest.tolearn.dao.BaseTask;

public class TaskDBHelper extends BaseDBHelper {
	public static final String DB_NAME = "tolearn.db";
	private static Logger log;

	public TaskDBHelper(Context context) {
		super(context, DB_NAME, null, 1);
		log = LoggerFactory.getLogger(TaskDBHelper.class);
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
			public static final String UPDATE_TIME = "update_time";
			public static final String START_TIME = "start_time";
			public static final String END_TIME = "end_time";
			public static final String DESC = "desc";
			public static final String TID = "tid";
			// public static final String TAG = "tag";
			public static final String SRC = "src";
			public static final String STATE = "state";
			public static final String COMMENT = "commment";
			public static String[] DEFAULT_QUERY = { TITLE, DESC, TID,
					UPDATE_TIME, START_TIME, END_TIME, SRC, STATE, COMMENT };
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
		if (log == null) {
			log = LoggerFactory.getLogger(TaskDBHelper.class);
		}
		try {
			String createTaskTable = "CREATE TABLE " + TaskTb.TB + " ("
					+ TaskTb.Colums.TITLE + "  TEXT,"
					+ TaskTb.Colums.UPDATE_TIME + "  TEXT,"
					+ TaskTb.Colums.START_TIME + "  TEXT,"
					+ TaskTb.Colums.END_TIME + "  TEXT," + TaskTb.Colums.DESC
					+ "  TEXT," + TaskTb.Colums.SRC + "  TEXT,"
					+ TaskTb.Colums.TID + "  TEXT," + TaskTb.Colums.STATE
					+ " INT," + TaskTb.Colums.COMMENT + ")";
			db.execSQL(createTaskTable);
			String createTagTable = "CREATE TABLE " + TagTb.TB + " ("
					+ TagTb.Colums.TID + " TEXT," + TagTb.Colums.TAG + " TEXT)";
			db.execSQL(createTagTable);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public static ArrayList<BaseTask> getCurrentBaseTask(Context context) {
		TaskDBHelper helper = getDBHelper(context);
		try {
			SQLiteDatabase database = helper.getWDatabase();
			Cursor cursor = database.query(TaskTb.TB,
					TaskTb.Colums.DEFAULT_QUERY, null, null, null, null, null);
			BaseTask info = null;
			ArrayList<BaseTask> taskList = new ArrayList<BaseTask>();
			if (cursor.moveToFirst()) {
				do {
					info = new BaseTask();
					info.setTitle(cursor.getString(cursor
							.getColumnIndexOrThrow(TaskTb.Colums.TITLE)));
					info.setUpdateTime(cursor.getString(cursor
							.getColumnIndexOrThrow(TaskTb.Colums.UPDATE_TIME)));
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
					info.setComment(cursor.getString(cursor
							.getColumnIndexOrThrow(TaskTb.Colums.COMMENT)));
					Cursor tagCursor = database.query(TagTb.TB,
							TagTb.Colums.DEFAULT_QUERY, TaskTb.Colums.TID
									+ "=?", new String[] { info.getTid() },
							null, null, null);
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
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	public static ArrayList<BaseTask> getTodoTasks(Context context) {
		TaskDBHelper helper = getDBHelper(context);
		try {
			SQLiteDatabase database = helper.getWDatabase();
			Cursor cursor = database.query(TaskTb.TB,
					TaskTb.Colums.DEFAULT_QUERY, TaskTb.Colums.STATE + "=?",
					new String[] { "1" }, null, null, null);
			BaseTask info = null;
			ArrayList<BaseTask> taskList = new ArrayList<BaseTask>();
			if (cursor.moveToFirst()) {
				do {
					info = new BaseTask();
					info.setTitle(cursor.getString(cursor
							.getColumnIndexOrThrow(TaskTb.Colums.TITLE)));
					info.setUpdateTime(cursor.getString(cursor
							.getColumnIndexOrThrow(TaskTb.Colums.UPDATE_TIME)));
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
					info.setComment(cursor.getString(cursor
							.getColumnIndexOrThrow(TaskTb.Colums.COMMENT)));
					Cursor tagCursor = database.query(TagTb.TB,
							TagTb.Colums.DEFAULT_QUERY, TaskTb.Colums.TID
									+ "=?", new String[] { info.getTid() },
							null, null, null);
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
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	public static ArrayList<BaseTask> getRepoTasks(Context context) {
		TaskDBHelper helper = getDBHelper(context);
		try {
			SQLiteDatabase database = helper.getWDatabase();
			Cursor cursor = database.query(TaskTb.TB,
					TaskTb.Colums.DEFAULT_QUERY, TaskTb.Colums.STATE + "=?",
					new String[] { "0" }, null, null, TaskTb.Colums.TID
							+ " desc");
			BaseTask info = null;
			ArrayList<BaseTask> taskList = new ArrayList<BaseTask>();
			if (cursor.moveToFirst()) {
				do {
					info = new BaseTask();
					info.setTitle(cursor.getString(cursor
							.getColumnIndexOrThrow(TaskTb.Colums.TITLE)));
					info.setUpdateTime(cursor.getString(cursor
							.getColumnIndexOrThrow(TaskTb.Colums.UPDATE_TIME)));
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
					info.setComment(cursor.getString(cursor
							.getColumnIndexOrThrow(TaskTb.Colums.COMMENT)));
					Cursor tagCursor = database.query(TagTb.TB,
							TagTb.Colums.DEFAULT_QUERY, TaskTb.Colums.TID
									+ "=?", new String[] { info.getTid() },
							null, null, null);
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
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	public static List<String> getTagList(Context context) {
		TaskDBHelper helper = getDBHelper(context);
		try {
			SQLiteDatabase database = helper.getWDatabase();
			Cursor cursor = database.query(true, TagTb.TB,
					TagTb.Colums.TAG_QUERY, null, null, null, null, null, null);
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
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	public static Map<String, String> getArchiveTagsMap(Context context) {
		TaskDBHelper helper = getDBHelper(context);
		try {
			SQLiteDatabase database = helper.getWDatabase();
			Cursor cursor = database
					.rawQuery(
							"SELECT tag,count(tag) from tb_tag JOIN tb_task on tb_tag.tid=tb_task.tid where tb_task.state=3 group by tag",
							new String[] {});
			Map<String, String> result = new TreeMap<String, String>();
			if (cursor.moveToFirst()) {
				do {
					String tagName = cursor.getString(cursor
							.getColumnIndexOrThrow(TagTb.Colums.TAG));
					String tagSize = cursor.getString(cursor
							.getColumnIndexOrThrow("count(tag)"));
					result.put(tagName, tagSize);
				} while (cursor.moveToNext());
			}
			cursor.close();
			database.close();
			return result;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * */
	public static List<BaseTask> getArchiveTasksByTag(Context context,
			String tagName) {
		TaskDBHelper helper = getDBHelper(context);
		try {
			SQLiteDatabase database = helper.getWDatabase();
			Cursor cursor = database
					.rawQuery(
							"SELECT * from tb_task join tb_tag on tb_task.tid=tb_tag.tid where tb_tag.tag=?",
							new String[] { tagName });
			BaseTask info = null;
			ArrayList<BaseTask> taskList = new ArrayList<BaseTask>();
			if (cursor.moveToFirst()) {
				do {
					info = new BaseTask();
					info.setTitle(cursor.getString(cursor
							.getColumnIndexOrThrow(TaskTb.Colums.TITLE)));
					info.setUpdateTime(cursor.getString(cursor
							.getColumnIndexOrThrow(TaskTb.Colums.UPDATE_TIME)));
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
					info.setComment(cursor.getString(cursor
							.getColumnIndexOrThrow(TaskTb.Colums.COMMENT)));
					Cursor tagCursor = database.query(TagTb.TB,
							TagTb.Colums.DEFAULT_QUERY, TaskTb.Colums.TID
									+ "=?", new String[] { info.getTid() },
							null, null, null);
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
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	public static BaseTask getTaskFromId(Context context, String id) {
		try {
			TaskDBHelper helper = getDBHelper(context);
			SQLiteDatabase database = helper.getWDatabase();
			Cursor cursor = database.query(TaskTb.TB,
					TaskTb.Colums.DEFAULT_QUERY, TaskTb.Colums.TID + "=?",
					new String[] { id }, null, null, null);
			BaseTask info = null;
			if (cursor.moveToFirst()) {
				do {
					info = new BaseTask();
					info.setTitle(cursor.getString(cursor
							.getColumnIndexOrThrow(TaskTb.Colums.TITLE)));
					info.setUpdateTime(cursor.getString(cursor
							.getColumnIndexOrThrow(TaskTb.Colums.UPDATE_TIME)));
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
					info.setComment(cursor.getString(cursor
							.getColumnIndexOrThrow(TaskTb.Colums.COMMENT)));
					Cursor tagCursor = database.query(TagTb.TB,
							TagTb.Colums.DEFAULT_QUERY, TaskTb.Colums.TID
									+ "=?", new String[] { id }, null, null,
							null);
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
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	public static void insertInfo(Context context, BaseTask task) {
		if (task == null) {
			return;
		}
		TaskDBHelper helper = getDBHelper(context);
		try {
			SQLiteDatabase database = helper.getWDatabase();
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
			database.close();
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	public static boolean updateInfo(Context context, BaseTask task) {
		boolean flag = false;
		if (task == null) {
			return false;
		}
		try {
			TaskDBHelper helper = getDBHelper(context);
			SQLiteDatabase database = helper.getWDatabase();
			Cursor cursor = database.query(TaskTb.TB,
					TaskTb.Colums.DEFAULT_QUERY, TaskTb.Colums.TID + "=?",
					new String[] { task.getTid() }, null, null, null);
			if (!cursor.moveToFirst()) {
				insertInfo(context, task);
				return true;
			}
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
			database.close();
			flag = true;
		} catch (Exception e) {
			flag = false;
			log.error(e.getMessage());
		}
		return flag;
	}

	public static ContentValues createTaskContentValue(BaseTask task)
			throws Exception {
		if (task != null) {
			ContentValues cv = new ContentValues();
			cv.put(TaskTb.Colums.TITLE, task.getTitle());
			cv.put(TaskTb.Colums.UPDATE_TIME, task.getUpdateTime());
			cv.put(TaskTb.Colums.START_TIME, task.getStartTime());
			cv.put(TaskTb.Colums.END_TIME, task.getEndTime());
			cv.put(TaskTb.Colums.DESC, task.getDesc());
			cv.put(TaskTb.Colums.TID, task.getTid());
			cv.put(TaskTb.Colums.STATE, task.getState());
			cv.put(TaskTb.Colums.COMMENT, task.getComment());
			return cv;
		}
		return null;
	}

	public static List<ContentValues> createTagContentValue(BaseTask task)
			throws Exception {
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
			log.error(e.getMessage());
			return null;
		}
	}

}
