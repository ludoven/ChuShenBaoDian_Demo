package com.jay.fragmentdemo.activty;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jay.fragmentdemo.R;
import com.jay.fragmentdemo.sqlite.MyDatabaseHelper;
import com.jay.fragmentdemo.sqlite.MyloginCursor;
import com.jay.fragmentdemo.sqlite.MytabOperate;

public class ChangePasswordActivity extends Activity implements
		OnClickListener, OnFocusChangeListener {
	private Button changePwBack;
	private Button changePwBtn;
	private TextView changePwBackText;
	private TextView changePwIdText;
	private EditText changePwId;
	private TextView changePwText;
	private EditText changePw;
	private TextView changePwNewText;
	private EditText changePwNew;
	private String myPhone, myPassword, myPwNew;
	private int myflagPhone, myflagPassword, myflagPwNew;
	private SQLiteOpenHelper helper;
	private MytabOperate mylogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_changepassword);

		helper = new MyDatabaseHelper(this);
		initView();
	}

	private void initView() {
		changePwBack = (Button) findViewById(R.id.changePwBack);

		changePwBack.setOnClickListener(this);
		changePwBtn = (Button) findViewById(R.id.changePwBtn);
		changePwBtn.setOnClickListener(this);
		changePwBackText = (TextView) findViewById(R.id.changePwBackText);
		changePwBackText.setOnClickListener(this);

		changePwId = (EditText) findViewById(R.id.changePwId);

		changePwId.setOnFocusChangeListener(this);
		changePw = (EditText) findViewById(R.id.changePw);
		changePw.setOnFocusChangeListener(this);
		changePwNew = (EditText) findViewById(R.id.changePwNew);
		changePwNew.setOnFocusChangeListener(this);
		changePwNew.setOnClickListener(this);

		changePwIdText = (TextView) findViewById(R.id.changePwIdText);
		changePwText = (TextView) findViewById(R.id.changePwText);
		changePwNewText = (TextView) findViewById(R.id.changePwNewText);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.changePwBack:

			ChangePasswordActivity.this.finish();
			break;
		case R.id.changePwBackText:
			ChangePasswordActivity.this.finish();
			break;
		case R.id.changePwNew:

			changePwNew.setFocusable(true);
			changePwNew.setFocusableInTouchMode(true);
			changePwNew.requestFocus();
			changePwNew.findFocus();
			break;
		case R.id.changePwBtn:

			changePwNew.setFocusable(false);

			myPhone = changePwId.getText().toString();
			myPwNew = changePwNew.getText().toString();
			if (myflagPhone == 1 && myflagPassword == 1 && myflagPwNew == 1) {

				mylogin = new MytabOperate(helper.getWritableDatabase());

				mylogin.updata(myPwNew, myPhone);
				// 提示框
				new AlertDialog.Builder(ChangePasswordActivity.this)
						.setTitle("标题").setMessage("该功能暂未开放")
						.setPositiveButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										Intent i = new Intent(
												ChangePasswordActivity.this,
												LoginActivity.class);
										startActivity(i);
									}
								}).show();
			} else {
				Toast.makeText(ChangePasswordActivity.this, "该功能暂未开放",
						Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		myPhone = changePwId.getText().toString();
		myPassword = changePw.getText().toString();
		myPwNew = changePwNew.getText().toString();
		switch (v.getId()) {
		case R.id.changePwId:
			if (hasFocus == false) {
				if (new MyloginCursor(
						ChangePasswordActivity.this.helper
								.getReadableDatabase()).find(myPhone).size() == 0
						&& changePwId.length() != 0) {
					changePwIdText.setVisibility(View.VISIBLE);
				} else {
					changePwIdText.setVisibility(View.INVISIBLE);
					myflagPhone = 1;
				}
			}
			break;
		case R.id.changePw:
			if (hasFocus == false) {
				if (myflagPhone == 1) {
					String result[] = new MyloginCursor(
							ChangePasswordActivity.this.helper
									.getReadableDatabase()).find(myPhone)
							.toString().split(",");
					if (myPassword.equals(result[2]) && changePw.length() != 0) {
						changePwText.setVisibility(View.INVISIBLE);
						myflagPassword = 1;
					} else {
						changePwText.setVisibility(View.VISIBLE);
					}
				} else {
					Toast.makeText(ChangePasswordActivity.this, "����������ȷ���˺�",
							Toast.LENGTH_SHORT).show();
				}
			}

			break;

		case R.id.changePwNew:
			if (hasFocus == false) {
				if ((myPwNew.length() < 6 || myPwNew.length() > 20)
						&& myPwNew.length() != 0) {
					changePwNewText.setVisibility(View.VISIBLE);
				} else {
					changePwNewText.setVisibility(View.INVISIBLE);
					myflagPwNew = 1;
				}
			}
			break;
		default:
			break;
		}
	}
}
