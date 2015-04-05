package com.smallest.tolearn.dao;

public class BaseTask {
	private String title = "";
	private String updateTime = "";
	private String startTime = "";
	private String endTime = "";
	private String desc = "";
	private String tid = "";
	private String[] tags = { "" };
	private String src = "";
	private String comment = "";
	public static int STATE_CREATING = -1;
	public static int STATE_REPO = 0;
	public static int STATE_TODO = 1;
	public static int STATE_TRASH = 2;
	public static int STATE_ARCHIVE = 3;
	public static int STATE_REMOVED = 4;
	/**
	 * state,-1:处于创建状态,0:处于repo中,1:处于todo中,2:处于trash中,3:处于完成的状态,4:处于已删除的状态
	 * */
	private int state = 0;
	private Records records;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getTitle() {
		return title;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public String[] getTags() {
		return tags;
	}

	public String getComment() {
		return comment;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setTag(String[] tags) {
		this.tags = tags;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
