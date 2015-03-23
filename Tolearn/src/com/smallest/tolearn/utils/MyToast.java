package com.smallest.tolearn.utils;

import javax.xml.datatype.Duration;

import android.content.Context;
import android.widget.Toast;

public class MyToast {
	private MyToast() {
	}

	private static MyToast myToast;
	private static int duration = Toast.LENGTH_SHORT;

	public static void makeText(Context context, CharSequence text, int duration) {
		if (myToast == null) {
			myToast = new MyToast();
		}
		Toast.makeText(context, text, duration).show();
	}

	public static void makeText(Context context, CharSequence text) {
		if (myToast == null) {
			myToast = new MyToast();
		}
		Toast.makeText(context, text, duration).show();
	}
}
