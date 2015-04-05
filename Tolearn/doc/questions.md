1. SQLiteDatabase.query实现如下的sql命令的功能？ “SELECT tag,count(tag) FROM tb_tag group by tag”,现在使用rawQuery()实现的
2. database.query(TaskTb.TB, TaskTb.Colums.DEFAULT_QUERY,
				TaskTb.Colums.STATE + "=?", new String[] { "0" }, null, null,
				TaskTb.Colums.TID+" desc");//desc好像没起作用
- 需要修改表的时候 看看SQLiteOpenHelper.onUpgrade