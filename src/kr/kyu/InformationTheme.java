/*
 * @(#)InformationTheme.java	2015. 12. 23
 * 
 * Copyright(c) 2009 namkyu.
 * 
 * NOTICE:
 * This source code that is confidential and proprietary to namkyu.
 * No part of this source code may be reproduced in any form 
 * whatsoever without prior approval by namkyu.
 */
package kr.kyu;

import android.app.Activity;
import android.os.Bundle;

/**
 * The Class InformationTheme.
 */
public class InformationTheme extends Activity {

	/**<pre>
	 * onCreate
	 *
	 * </pre>
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.information);
	}
}