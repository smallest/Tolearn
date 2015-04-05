package com.smallest.tolearn.utils;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.smallest.tolearn.dao.BaseTask;
import com.smallest.tolearn.db.TaskDBHelper;

/**
 * manager current todo tasks 1. drop a todo task to repo 2. select tasks tofill
 * the todo list 3. add a task to repo 4. move a task to trash 5. modify a task
 * */
public class TaskManager {
	private List<BaseTask> todoTaskList;
	private static TaskManager instance = null;
	private IPickTasksStrategy pickStrategy;
	private Context context;
	private OnTodoSetChangedListener mOnTodoSetChangedListener;
	private OnRepoSetChangedListener mOnRepoSetChangedListener;
	private OnTaskStatusChangedListener mOnTaskStatusChangedListener;
	private List<BaseTask> repoList;

	private TaskManager(Context context) {
		this.context = context;
		pickStrategy = new RandomPickTaskStrategy();
		todoTaskList = TaskDBHelper.getTodoTasks(context);// 从数据库中检索出todo 的task
		int nEmptySeat = MyConstants.NUM_TODO - todoTaskList.size();// 如果todo
																	// 的条目不足则从repo中选取task
		if (nEmptySeat > 0) {
			loadNewTodoTasks(nEmptySeat);
		}
	}

	public static TaskManager getInstance(Context context) {
		if (instance == null) {
			instance = new TaskManager(context);
			Log.d("smallest", "TaskManager instance");
		}
		return instance;
	}

	public BaseTask getTaskById(String tid) {
		return TaskDBHelper.getTaskFromId(context, tid);
	}

	/**
	 * change the state of task in the taskList to todo(1)
	 * */
	private boolean loadTasks(Context context, List<BaseTask> taskList) {
		for (BaseTask task : taskList) {
			task.setState(1);
			TaskDBHelper.updateInfo(context, task);
		}
		return true;
	}

	public List<BaseTask> getTodoList() {
		return todoTaskList;
	}

	public List<BaseTask> getRepoList() {
		repoList = TaskDBHelper.getRepoTasks(context);
		return repoList;
	}

	/**
	 * 获取标签的集合
	 * */
	public List<String> getTagList() {
		return TaskDBHelper.getTagList(context);
	}

	/**
	 * 获取每个标签archive的task的数量
	 * */
	public Map<String, String> getArchiveTagsMap() {
		return TaskDBHelper.getArchiveTagsMap(context);
	}

	public List<BaseTask> getArchiveTasksByTag(String tagName) {
		return TaskDBHelper.getArchiveTasksByTag(context, tagName);
	}

	public boolean unloadTask(BaseTask task) {
		todoTaskList.remove(task);
		task.setState(0);
		TaskDBHelper.updateInfo(context, task);
		int nEmptySeat = MyConstants.NUM_TODO - todoTaskList.size();
		if (nEmptySeat > 0) {
			loadNewTodoTasks(nEmptySeat);
		}
		return true;
	}

	/**
	 * load num tasks from repo to todo
	 * */
	public boolean loadNewTodoTasks(int num) {
		List<BaseTask> newTodoTaskList = pickStrategy.pickTasks(
				TaskDBHelper.getRepoTasks(context), num);
		loadTasks(context, newTodoTaskList);
		for (BaseTask task : newTodoTaskList) {
			todoTaskList.add(task);
		}
		return true;
	}

	/**
	 * add a new task
	 * */
	public boolean addTask(BaseTask task) {
		String time = String.valueOf(System.currentTimeMillis());
		task.setStartTime(time);
		task.setTid(time);
		task.setUpdateTime(time);
		task.setState(BaseTask.STATE_REPO);
		TaskDBHelper.insertInfo(context, task);
		Log.d("smallest", "add a task over");
		if (repoList != null) {
			repoList.add(task);
		}
		int nEmptySeat = MyConstants.NUM_TODO - todoTaskList.size();
		if (nEmptySeat > 0) {
			loadNewTodoTasks(nEmptySeat);
			if (mOnTodoSetChangedListener != null) {
				mOnTodoSetChangedListener.dataSetChanged();
			}
		}
		if (mOnRepoSetChangedListener != null) {
			mOnRepoSetChangedListener.dataSetChanged();
		}
		return true;
	}

	/**
	 * move a task to trash
	 * */
	public boolean trashTask(BaseTask task) {
		task.setState(BaseTask.STATE_TRASH);
		TaskDBHelper.updateInfo(context, task);
		if (mOnRepoSetChangedListener != null) {
			mOnRepoSetChangedListener.dataSetChanged();
		}
		return true;
	}

	/**
	 * move a task from trash to repo
	 * */
	public boolean unTrashTask(BaseTask task) {
		task.setState(BaseTask.STATE_REPO);
		TaskDBHelper.updateInfo(context, task);
		return true;
	}

	/**
	 * remove a task from trash
	 */
	public boolean removeTask(BaseTask task) {
		task.setState(BaseTask.STATE_REMOVED);
		TaskDBHelper.updateInfo(context, task);
		return true;
	}

	/**
	 * 把完成的task归档
	 * */
	public boolean archiveTask(BaseTask task) {
		task.setState(BaseTask.STATE_ARCHIVE);
		task.setUpdateTime(String.valueOf(System.currentTimeMillis()));
		if (TaskDBHelper.updateInfo(context, task)) {
			for (BaseTask taskItem : todoTaskList) {
				if (taskItem.getTid().equals(task.getTid())) {
					todoTaskList.remove(taskItem);
					break;
				}
			}
			loadNewTodoTasks(1);
			if (mOnTodoSetChangedListener != null) {
				mOnTodoSetChangedListener.dataSetChanged();
			}
			return true;
		} else {
			Log.d("tolearn", "archive task false");
			return false;
		}
	}

	public boolean modifyTaskDesc(BaseTask task) {
		String time = String.valueOf(System.currentTimeMillis());
		task.setUpdateTime(time);
		boolean flag = TaskDBHelper.updateInfo(context, task);
		if (flag && mOnTaskStatusChangedListener != null) {
			mOnTaskStatusChangedListener.taskStatusChanged();
		}
		return flag;
	}

	public boolean modifyTitle(BaseTask task, String title) {
		return true;
	}

	public boolean modifyDesc(BaseTask task, String desc) {
		return true;
	}

	public boolean modifyTag(BaseTask task, String tag) {
		return true;
	}

	public boolean modifySrc(BaseTask task, String src) {
		return true;
	}

	public boolean modifyComment(BaseTask task, String comment) {
		return true;
	}

	/**
	 * 修改Id为taskID的task的comment，并写入数据库
	 * */
	public boolean modifyComment(String taskID, String comment) {
		BaseTask task = TaskDBHelper.getTaskFromId(context, taskID);
		task.setComment(comment);
		String time = String.valueOf(System.currentTimeMillis());
		task.setUpdateTime(time);
		TaskDBHelper.updateInfo(context, task);
		if (mOnTaskStatusChangedListener != null) {
			mOnTaskStatusChangedListener.taskStatusChanged();
		}
		return true;
	}

	public void setOnTodoSetChangedListener(
			OnTodoSetChangedListener onTodoSetChangedListener) {
		this.mOnTodoSetChangedListener = onTodoSetChangedListener;
	}

	public interface OnTodoSetChangedListener {
		public void dataSetChanged();
	}

	public void setOnRepoSetChangedListener(
			OnRepoSetChangedListener onRepoSetChangedListener) {
		this.mOnRepoSetChangedListener = onRepoSetChangedListener;
	}

	public void setOnTaskStatusChangedListener(
			OnTaskStatusChangedListener onTaskStatusChangedListener) {
		this.mOnTaskStatusChangedListener = onTaskStatusChangedListener;
	}

	public interface OnRepoSetChangedListener {
		public void dataSetChanged();
	}

	public interface OnTaskStatusChangedListener {
		public void taskStatusChanged();
	}
}
