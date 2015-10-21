/*
 * @(#)AddActivity.java	2015. 10. 16
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
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import kr.kyu.common.Constants;
import kr.kyu.db.DBAdapter;
import kr.kyu.db.WakeOnLanVO;
import kr.kyu.util.CommonUtil;


/**
 * The Class AddActivity.
 */
public class AddActivity extends Activity {

	/** The name edit. */
	private EditText nameEdit;

	/** The mac edit. */
	private EditText macEdit;

	/** The ip edit. */
	private EditText ipEdit;

	/** The port edit. */
	private EditText portEdit;

	/** The db. */
	DBAdapter db;

	/**
	 * <pre>
	 * onCreate
	 *
	 * </pre>.
	 *
	 * @param savedInstanceState the saved instance state
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_layout);

		db = new DBAdapter(this);
		db.open();

		nameEdit = (EditText) findViewById(R.id.nameTextBox);
		macEdit = (EditText) findViewById(R.id.macTextBox);
		ipEdit = (EditText) findViewById(R.id.ipTextBox);
		portEdit = (EditText) findViewById(R.id.portTextBox);

		// Edit 박스 길이 제한
		nameEdit.setFilters(new InputFilter[] { new InputFilter.LengthFilter(Constants.NAME_TEXT_BOX_LIMIT_SIZE) });
		macEdit.setFilters(new InputFilter[] { new InputFilter.LengthFilter(Constants.MAC_TEXT_BOX_LIMIT_SIZE) });
		ipEdit.setFilters(new InputFilter[] { new InputFilter.LengthFilter(Constants.IP_TEXT_BOX_LIMIT_SIZE) });
		portEdit.setFilters(new InputFilter[] { new InputFilter.LengthFilter(Constants.PORT_TEXT_BOX_LIMIT_SIZE) });

		// MAC Edit 박스 채우기
		macEdit.addTextChangedListener(mWatcher);

		// Add 버튼 클릭 이벤트
		ImageButton addButton = (ImageButton) findViewById(R.id.addButton);
		addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = nameEdit.getText().toString();
				String macAddress = macEdit.getText().toString();
				String ipAddress = ipEdit.getText().toString();
				String port = portEdit.getText().toString();

				if (TextUtils.isEmpty(name)) {
					CommonUtil.showShortToast(AddActivity.this, getString(R.string.nameIsEmpty));
					return;
				} else if (TextUtils.isEmpty(macAddress)) {
					CommonUtil.showShortToast(AddActivity.this, getString(R.string.macAddressIsEmpty));
					return;
				} else if (TextUtils.isEmpty(ipAddress)) {
					CommonUtil.showShortToast(AddActivity.this, getString(R.string.ipAddressIsEmpty));
					return;
				} else if (TextUtils.isEmpty(port)) {
					CommonUtil.showShortToast(AddActivity.this, getString(R.string.portIsEmpty));
					return;
				}

				boolean isSuccess = insertProcess(name, macAddress, ipAddress, port);
				if (isSuccess) { // 성공
					String msg = name + " " + getString(R.string.insertSuccess);
					CommonUtil.showShortToast(AddActivity.this, msg);
				} else { // 실패
					CommonUtil.showShortToast(AddActivity.this, getString(R.string.insertFail));
					return;
				}

				// 첫 번째 인자는 현재 컨텍스트이고, 두 번째 인자는 호출 할 Activity의 class 이다.
				Intent intent = new Intent(AddActivity.this, ImUtils.class);
				intent.putExtra("currentIdx", Constants.CURRENT_TAB_LIST_INDEX); // intent 에 parameter key로 설정하여 저장
				startActivity(intent);
			}

			/**
			 * <pre>
			 * insertProcess
			 *
			 * </pre>
			 * @param name
			 * @param macAddress
			 * @param ipAddress
			 * @param port
			 * @return
			 */
			private boolean insertProcess(String name, String macAddress, String ipAddress, String port) {
				WakeOnLanVO vo = new WakeOnLanVO();
				vo.setName(name);
				vo.setMacAddress(macAddress);
				vo.setIpAddress(ipAddress);
				vo.setPort(port);

				return db.insertWakeOnLan(vo) > 0;
			}
		});

		// Cancel 버튼 클릭 이벤트
		ImageButton cancelButton = (ImageButton) findViewById(R.id.cancelButton);
		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Editable nameValue = nameEdit.getText();
				Editable macValue = macEdit.getText();
				Editable ipValue = ipEdit.getText();
				Editable portValue = portEdit.getText();

				nameValue.clear();
				macValue.clear();
				ipValue.clear();
				portValue.clear();
			}
		});
	}

	/** The m watcher. */
	TextWatcher mWatcher = new TextWatcher() {

		public void afterTextChanged(Editable s) {
		}

		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}
	};
}
