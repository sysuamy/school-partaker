package com.zx.pinke.activity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zx.pinke.R;
import com.zx.pinke.activity.base.BaseActivity;
import com.zx.pinke.bean.AccountInfo;
import com.zx.pinke.util.AppConfig;
import com.zx.pinke.util.AsyncHttpQueryHandle;
import com.zx.pinke.util.HttpFileResult.ResultCode;
import com.zx.pinke.util.HttpJsonResult;

public class LoginActivity extends BaseActivity {

	private ProgressDialog mProgressDialog;
	private BackgroundQueryHandler mBackgroundQueryHandler;
	
	private static final int TOKEN_ACCOUNT_LOGIN = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_login);
		mBackgroundQueryHandler = new BackgroundQueryHandler(this);
		mProgressDialog = new ProgressDialog(LoginActivity.this);
		mProgressDialog.setMessage(getString(R.string.logining));
		mProgressDialog.setCancelable(true);
		mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				switch (keyCode) {
					case KeyEvent.KEYCODE_BACK:
						mBackgroundQueryHandler.cancelOperation(TOKEN_ACCOUNT_LOGIN);
				}
				return false;
			}
		});
		
		initViews();
	}
	
	private EditText mAccountEt;
	private EditText mPasswordEt;
	private Button mRegisterBtn;
	private Button mLoginBtn;
	
	private void initViews(){
		findViewById(R.id.btn_left_header).setVisibility(View.GONE);
		mAccountEt = (EditText)findViewById(R.id.account);
		mPasswordEt = (EditText)findViewById(R.id.password);
		mRegisterBtn = (Button)findViewById(R.id.btn_register);
		mLoginBtn = (Button)findViewById(R.id.btn_login);
		
		AccountInfo accountInfo = AccountInfo.getAccountInfo();
		String account = accountInfo.getAccount();
		String password = accountInfo.getPassword();
		if(account != null && !"".equals(account)){
			mAccountEt.setText(account);		
		}
		if(password != null && !"".equals(password)){
			mPasswordEt.setText(password);	
		}
		mRegisterBtn.setOnClickListener(new RegisterListener());
		mLoginBtn.setOnClickListener(new LoginListener());
		if (account != null && !"".equals(account)&&password != null && !"".equals(password)) {
			mLoginBtn.performClick();
		}
		
	}
	
	private static final int REQUEST_CODE_REGISTER = 1;
	private class RegisterListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
			startActivityForResult(intent, REQUEST_CODE_REGISTER);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(resultCode == RESULT_OK){
			Intent intent = new Intent();
			intent.setClass(LoginActivity.this,MainActivity.class);
			startActivity(intent);
		}
		
	}
	
	private class LoginListener implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			String account = mAccountEt.getText().toString().trim();
			if(TextUtils.isEmpty(account)){
				shakeAndToast(mAccountEt, getString(R.string.warn_account_not_empty));
				return;
			}
			String password = mPasswordEt.getText().toString().trim();
			if(TextUtils.isEmpty(password)){
				shakeAndToast(mPasswordEt, getString(R.string.warn_password_not_empty));
				return;
			}
			try {
				mProgressDialog.show();
				URI uri = new URI(AppConfig.NetWork.IV.URL_ACCOUNT_LOGIN);
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("username", account);
				params.put("password", password);
				mBackgroundQueryHandler.startPost(TOKEN_ACCOUNT_LOGIN, params, uri, params);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		private void shakeAndToast(View view,String error){
			 Animation shake = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shake);
			 view.requestFocus();
			 view.startAnimation(shake);
			 Toast.makeText(LoginActivity.this, error,Toast.LENGTH_LONG).show();
		}
	}
	
	private class BackgroundQueryHandler extends AsyncHttpQueryHandle{

		private Context mContext;
		public BackgroundQueryHandler(Context context) {
			super(context);
			mContext = context;
		}
		
		@Override
		protected void onPostComplete(int token, Object cookie,
				HttpJsonResult result) {
			
			logger.debug(result);
			if(!result.success()){
				if(result.getResultCode() == ResultCode.CODE_FAIL_NO_NETWORK){
					Toast.makeText(mContext, R.string.alert_no_network,Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(mContext, R.string.alert_fail_network,Toast.LENGTH_LONG).show();
				}
			}else{
				
				if(token == TOKEN_ACCOUNT_LOGIN){
					JSONObject jsonObject = result.getJsonResult();
					try {
						int success = jsonObject.getInt("returnCode");
						String msg = (String)jsonObject.get("returnMsg");
				
						if(success==0){
							JSONObject info = jsonObject.getJSONObject("userInfo");
							String sessionId = jsonObject.getString("sessionId");
							Map<String,Object> param = (Map<String,Object>)cookie;
							String account = param.get("username").toString();
							String password = param.get("password").toString();
							
							try {
								AccountInfo.saveAccountInfo(sessionId,account, password, info);
								Intent intent = new Intent();
								intent.setClass(LoginActivity.this,MainActivity.class);
								startActivity(intent);
								finish();
							} catch (Exception e) {
								e.printStackTrace();
								logger.error("用户信息解析出错！");
							}
						}
						Toast.makeText(mContext, msg,Toast.LENGTH_LONG).show();
					} catch (JSONException e) {
						e.printStackTrace();
						Toast.makeText(mContext, R.string.fail_login,Toast.LENGTH_LONG).show();
					}
				}
			}
			mProgressDialog.dismiss();
		}
	}
}
