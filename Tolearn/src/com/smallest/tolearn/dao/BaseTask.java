package com.smallest.tolearn.dao;


public class BaseTask {
	private String title = "";
	private String startTime = "";
	private String endTime = "";
	private String desc = "";
	private String tid = "";
	private String tag = "";
	private String src = "";
	private String comment = "";

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

	public String getTag() {
		return tag;
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

	public void setTag(String tag) {
		this.tag = tag;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


}
