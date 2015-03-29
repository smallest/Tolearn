package com.smallest.tolearn.utils;

import java.util.HashSet;
import java.util.Set;

public class TagManager {
	private Set<String> tagList = new HashSet<String>();

	public boolean hasTag(String tag) {
		return tagList.contains(tag);
	}

	public boolean addTag(String tag) {
		tagList.add(tag);
		return true;
	}
}
