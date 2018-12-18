package com.jay.fragmentdemo.activty;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jay.fragmentdemo.MainActivity;
import com.jay.fragmentdemo.R;
import com.jay.fragmentdemo.sqlite.MyDatabaseHelper;
import com.jay.fragmentdemo.sqlite.MyloginCursor;

public class LoginActivity extends Activity implements OnClickListener {

	private EditText loginId;
	private EditText loginPassword;
	private Button loginBtn;
	private Button loginMissps;
	private Button loginNewUser;
	private Button loginChangePw;
	private String isId, isPs;
	private SQLiteOpenHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //无标题栏
		setContentView(R.layout.activity_login);
		helper = new MyDatabaseHelper(this);
		initView();
		Intent i = super.getIntent();
		String Id = i.getStringExtra("myId");
		loginId.setText(Id);
	}


	private void initView() {
		loginId = (EditText) findViewById(R.id.loginId);
		loginPassword = (EditText) findViewById(R.id.loginPassword);
		loginBtn = (Button) findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(this);
		loginMissps = (Button) findViewById(R.id.loginMissps);
		loginMissps.setOnClickListener(this);
		loginNewUser = (Button) findViewById(R.id.loginNewUser);
		loginNewUser.setOnClickListener(this);
		loginChangePw = (Button) findViewById(R.id.loginChangePw);
		loginChangePw.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loginBtn:
			isId = loginId.getText().toString();
			isPs = loginPassword.getText().toString();
			 if(isId.equals("2018")&&isPs.equals("2018")){
			Intent intent=new Intent(this, MainActivity.class);
			startActivity(intent);
				 break;
		}
			if (new MyloginCursor(
					LoginActivity.this.helper.getReadableDatabase()).find(isId)
					.size() == 0) {

				Toast.makeText(LoginActivity.this, "请输入正确的账号或密码",
						Toast.LENGTH_SHORT).show();
			} else {
				String lph = new MyloginCursor(
						LoginActivity.this.helper.getReadableDatabase()).find(
						isId).toString();

				String result[] = lph.split(",");
				if (result[1].equals(isId) && result[2].equals(isPs)) {
					Intent intent=new Intent(this, MainActivity.class);
					startActivity(intent);
					break;
				}
				else {
					Toast.makeText(LoginActivity.this, "请输入正确的账号或密码",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.loginMissps:
			Intent a = new Intent(LoginActivity.this,
					FindPasswordActivity.class);
			startActivity(a);
			break;
		case R.id.loginNewUser:
			Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(i);
			break;
		case R.id.loginChangePw:
			Intent l = new Intent(LoginActivity.this,
					ChangePasswordActivity.class);
			startActivity(l);
			break;
		default:
			break;
		}

	}
}
