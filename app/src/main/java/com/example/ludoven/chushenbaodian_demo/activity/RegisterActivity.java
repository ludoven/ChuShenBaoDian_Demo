package com.jay.fragmentdemo.activty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jay.fragmentdemo.R;
import com.jay.fragmentdemo.sqlite.MyDatabaseHelper;
import com.jay.fragmentdemo.sqlite.MyloginCursor;
import com.jay.fragmentdemo.sqlite.MytabOperate;
import com.jay.fragmentdemo.utils.Autjcode;


public class RegisterActivity extends Activity implements OnClickListener,
		OnFocusChangeListener {
	private Button registerBack;
	private Button registerCheck;
	private Button registerBtn;
	private EditText registerId;
	private EditText registerPassword;
	private EditText registerAuth;
	private EditText turePassword;
	private TextView registerBackText;
	private TextView registerIdText;
	private TextView registerPwText;
	private TextView turePwText;
	private TextView registerAuthText;
	private ImageView registerAuthimg;
	private String isPhone, isPassword, isTruePassword, Autecode, Autecodeimg;
	private int flagPhone, flagPassword, flagTruePassword, flagAutecode;
	private SQLiteOpenHelper helper;
	private MytabOperate mylogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		helper = new MyDatabaseHelper(this);
		initView();
	}

	private void initView() {
		registerBack = (Button) findViewById(R.id.registerBack);
		registerBack.setOnClickListener(this);
		registerCheck = (Button) findViewById(R.id.registerCheck);
		registerCheck.setOnClickListener(this);
		registerBtn = (Button) findViewById(R.id.registerBtn);
		registerBtn.setOnClickListener(this);
		registerBackText = (TextView) findViewById(R.id.registerBackText);
		registerBackText.setOnClickListener(this);

		registerId = (EditText) findViewById(R.id.registerId);
		registerId.setOnFocusChangeListener(this);
		registerPassword = (EditText) findViewById(R.id.registerPassword);
		registerPassword.setOnFocusChangeListener(this);
		registerAuth = (EditText) findViewById(R.id.registerAuth);
		registerAuth.setOnFocusChangeListener(this);
		registerAuth.setOnClickListener(this);
		turePassword = (EditText) findViewById(R.id.turePassword);
		turePassword.setOnFocusChangeListener(this);

		registerAuthimg = (ImageView) findViewById(R.id.registerAuthimg);
		registerAuthimg.setImageBitmap(Autjcode.getInstance().createBitmap());

		registerIdText = (TextView) findViewById(R.id.registerIdText);
		registerPwText = (TextView) findViewById(R.id.registerPwText);
		turePwText = (TextView) findViewById(R.id.turePwText);
		registerAuthText = (TextView) findViewById(R.id.registerAuthText);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.registerBack:
			RegisterActivity.this.finish();
			break;
		case R.id.registerBackText:
			RegisterActivity.this.finish();
			break;
		case R.id.registerAuth:
			registerAuth.setFocusable(true);
			registerAuth.setFocusableInTouchMode(true);
			registerAuth.requestFocus();
			registerAuth.findFocus();
			break;
		case R.id.registerCheck:
			registerAuthimg.setImageBitmap(Autjcode.getInstance()
					.createBitmap());
			break;
		case R.id.registerBtn:
			registerAuth.setFocusable(false);
			isPhone = registerId.getText().toString();
			if (flagAutecode == 1 && flagPassword == 1 && flagPhone == 1
					&& flagTruePassword == 1) {
				if (new MyloginCursor(
						RegisterActivity.this.helper.getReadableDatabase())
						.find(isPhone).size() == 0) {
					mylogin = new MytabOperate(helper.getWritableDatabase());
					mylogin.insert(isPhone, isPassword);
					new AlertDialog.Builder(RegisterActivity.this)
							.setTitle("提示")
							.setMessage("注册成功")
							.setPositiveButton("返回登录",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											Intent i = new Intent(
													RegisterActivity.this,
													LoginActivity.class);
											i.putExtra("myId", isPhone);
											RegisterActivity.this.finish();
											startActivity(i);
										}
									}).show();

				} else {
					new AlertDialog.Builder(RegisterActivity.this)
							.setTitle("标题").setMessage("该手机号已注册")
							.setPositiveButton("知道了", null).show();
				}
			} else {
				Toast.makeText(RegisterActivity.this, "别急别急，还有呢。",
						Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}

	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		isPhone = registerId.getText().toString();
		isPassword = registerPassword.getText().toString();
		isTruePassword = turePassword.getText().toString();
		Autecode = registerAuth.getText().toString();
		Autecodeimg = Autjcode.getInstance().getCode().toUpperCase();
		switch (v.getId()) {
		case R.id.registerId:
			if (hasFocus == false) {
				// �ֻ�����������ж�
				Pattern pattern = Pattern.compile("^1[3,5,8]\\d{9}$");
				Matcher matcher = pattern.matcher(isPhone);
				if (matcher.find()) {
					registerIdText.setVisibility(View.INVISIBLE);
					flagPhone = 1;
				} else {
					if (registerId.length() != 0) {
						registerIdText.setVisibility(View.VISIBLE);
					}
				}
			}
			break;
		case R.id.registerPassword:
			if (hasFocus == false) {
				if ((isPassword.length() < 6 || isPassword.length() > 20)
						&& isPassword.length() != 0) {
					registerPwText.setVisibility(View.VISIBLE);
				} else {
					registerPwText.setVisibility(View.INVISIBLE);
					flagPassword = 1;
				}
			}
			break;
		case R.id.turePassword:
			if (hasFocus == false) {
				if (isTruePassword.equals(isPassword)) {
					turePwText.setVisibility(View.INVISIBLE);
					flagTruePassword = 1;
				} else {
					if (turePassword.length() != 0) {
						turePwText.setVisibility(View.VISIBLE);
					}
				}
			}
			break;
		case R.id.registerAuth:
			if (hasFocus == false) {
				// �ж���֤���Ƿ���ȷ��toUpperCase()�ǲ����ִ�Сд
				if (Autecode.toUpperCase().equals(Autecodeimg)) {
					registerAuthText.setVisibility(View.INVISIBLE);
					flagAutecode = 1;
				} else {
					if (registerAuth.length() != 0) {
						registerAuthText.setVisibility(View.VISIBLE);
					}
				}
			}
			break;
		default:
			break;
		}
	}
}
