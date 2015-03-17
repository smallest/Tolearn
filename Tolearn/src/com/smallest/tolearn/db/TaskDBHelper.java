
package com.smallest.tolearn.db;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class TaskDBHelper extends BaseDBHelper {
    public static final String DB_NAME = "tolearn.db";

    public TaskDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public TaskDBHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    public TaskDBHelper(Context context, String name, CursorFactory factory, int version) {
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
            public static String[] DEFAULT_QUERY = {
                    TITLE, DESC, TID, START_TIME, END_TIME, TAG, SRC
            };
        }
    }

    public static class TaskInfo {

        public String title = "";
        public String start_time = "";
        public String end_time = "";
        public String desc = "";
        public String tid = "";
        public String selected = "";
        public String tag = "";
        public String src = "";

        public TaskInfo() {
            start_time = Calendar.getInstance().toString();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTaskTable = "CREATE TABLE " + TaskTb.TB + " (" + TaskTb.Colums.TITLE
                + "  TEXT,"
                + TaskTb.Colums.START_TIME + "  TEXT," + TaskTb.Colums.END_TIME + "  TEXT,"
                + TaskTb.Colums.DESC
                + "  TEXT," + "  TEXT," + TaskTb.Colums.TAG + "  TEXT,"
                + TaskTb.Colums.SRC + "  TEXT," + TaskTb.Colums.TID
                + "  TEXT )";
        db.execSQL(createTaskTable);
    }

    public static ArrayList<TaskInfo> getCurrentTaskInfo(Context context) {
        TaskDBHelper helper = getDBHelper(context);
        SQLiteDatabase database = helper.getWDatabase();
        Cursor cursor = database.query(TaskTb.TB, TaskTb.Colums.DEFAULT_QUERY,
                null, null,
                null, null, null);
        TaskInfo info = null;
        ArrayList<TaskInfo> taskInfoList = new ArrayList<TaskInfo>();
        if (cursor.moveToFirst()) {
            do {
                info = new TaskInfo();
                info.title = cursor.getString(cursor.getColumnIndexOrThrow(TaskTb.Colums.TITLE));
                info.start_time = cursor.getString(cursor
                        .getColumnIndexOrThrow(TaskTb.Colums.START_TIME));
                info.end_time = cursor.getString(cursor
                        .getColumnIndexOrThrow(TaskTb.Colums.END_TIME));
                info.desc = cursor.getString(cursor
                        .getColumnIndexOrThrow(TaskTb.Colums.DESC));
                info.tid = cursor.getString(cursor.getColumnIndexOrThrow(TaskTb.Colums.TID));
                info.tag = cursor.getString(cursor
                        .getColumnIndexOrThrow(TaskTb.Colums.TAG));
                info.src = cursor.getString(cursor
                        .getColumnIndexOrThrow(TaskTb.Colums.SRC));
                taskInfoList.add(info);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return taskInfoList;
    }

    public static void insertInfo(Context context, TaskInfo taskInfo) {
        if (taskInfo == null) {
            return;
        }
        TaskDBHelper helper = getDBHelper(context);
        SQLiteDatabase database = helper.getWDatabase();
        try {
            ContentValues values = createContentValue(taskInfo);
            if (values != null) {
                database.insert(TaskTb.TB, null, values);
            }
        } catch (Exception e) {
        }
        database.close();
    }

    public static void updateInfo(Context context, TaskInfo taskInfo) {
        if (taskInfo == null) {
            return;
        }

        TaskDBHelper helper = getDBHelper(context);
        SQLiteDatabase database = helper.getWDatabase();
        Cursor cursor = database.query(TaskTb.TB, TaskTb.Colums.DEFAULT_QUERY,
                TaskTb.Colums.TID + "=?",
                new String[] {
                    taskInfo.tid
                }, null, null, null);
        if (!cursor.moveToFirst()) {
            insertInfo(context, taskInfo);
            return;
        }
        try {
            ContentValues values = createContentValue(taskInfo);
            if (values != null) {
                database.update(TaskTb.TB, values, TaskTb.Colums.TID + "=?", new String[] {
                        taskInfo.tid
                });
            }
        } catch (Exception e) {
        }
        database.close();
    }

    public static ContentValues createContentValue(TaskInfo taskInfo) {
        if (taskInfo != null) {
            ContentValues cv = new ContentValues();
            cv.put(TaskTb.Colums.TITLE, taskInfo.title);
            cv.put(TaskTb.Colums.START_TIME, taskInfo.start_time);
            cv.put(TaskTb.Colums.END_TIME, taskInfo.end_time);
            cv.put(TaskTb.Colums.DESC, taskInfo.desc);
            cv.put(TaskTb.Colums.TAG, taskInfo.tag);
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
