package com.zx.pinke.activity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class AccountAlterPwdActivity extends BaseActivity {

	private static final int TOKEN_ACCOUNT_ALTER_PWD = 1;
	
	private BackgroundQueryHandler mBackgroundQueryHandler;
	private ProgressDialog mProgressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_i_account_alter_pwd);
		mBackgroundQueryHandler = new BackgroundQueryHandler(this);
		mProgressDialog = new ProgressDialog(AccountAlterPwdActivity.this);
		mProgressDialog.setMessage(getString(R.string.processing));
		mProgressDialog.setCancelable(true);
		mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				switch (keyCode) {
					case KeyEvent.KEYCODE_BACK:
						mBackgroundQueryHandler.cancelOperation(TOKEN_ACCOUNT_ALTER_PWD);
				}
				return false;
			}
		});
		initViews();
	}
	
	private EditText mPasswordEt;
	private EditText mConfirmPwdEt;
	private EditText mNewPwdEt;
	private Button mAlterBtn;
	private void initViews(){
		mPasswordEt = (EditText)findViewById(R.id.password);
		mConfirmPwdEt = (EditText)findViewById(R.id.confirm_password);
		mNewPwdEt = (EditText)findViewById(R.id.new_password);
		mAlterBtn = (Button)findViewById(R.id.btn_alter);
		mAlterBtn.setOnClickListener(new AlterListener());
	}
	
	private class AlterListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			
			String password = mPasswordEt.getText().toString().trim();
			String confirmPwd = mConfirmPwdEt.getText().toString().trim();
			String newPwd = mNewPwdEt.getText().toString().trim();
			
			if(TextUtils.isEmpty(password)){
				shakeAndToast(mPasswordEt,getString(R.string.warn_password_not_empty));
				return;
			}
			
			if(TextUtils.isEmpty(newPwd)){
				shakeAndToast(mNewPwdEt,getString(R.string.warn_password_not_empty));
				return;
			}
			
			if(TextUtils.isEmpty(confirmPwd)){
				shakeAndToast(mConfirmPwdEt,getString(R.string.warn_password_not_empty));
				return;
			}
			
			if(!confirmPwd.equals(newPwd)){
				shakeAndToast(mConfirmPwdEt,getString(R.string.warn_different_two_password));
				return;
			}
			

			
			try {
				mProgressDialog.show();
				URI uri = new URI(AppConfig.NetWork.IV.URL_ACCOUNT_ALTER_PWD);
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("oldPwd", password);
				params.put("newPwd", newPwd);
				
				AccountInfo info = AccountInfo.getAccountInfo();
				String sessionId = info.getSessionId();
				params.put("suser", info.getAccount());
				mBackgroundQueryHandler.startPost(TOKEN_ACCOUNT_ALTER_PWD, null, uri, params,sessionId);
				
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			
		}
		
		private void shakeAndToast(View view,String error){
			 Animation shake = AnimationUtils.loadAnimation(AccountAlterPwdActivity.this, R.anim.shake);
			 view.requestFocus();
			 view.startAnimation(shake);
			 Toast.makeText(AccountAlterPwdActivity.this, error,Toast.LENGTH_LONG).show();
		}
	}
	
	private class BackgroundQueryHandler extends AsyncHttpQueryHandle{	
		private Context mContext;
		public BackgroundQueryHandler(Context context) {
			super(context);
			mContext = context;
		}
		
		private boolean showProgress = true;
		
		@Override
		protected void onPostComplete(int token, Object cookie,
				HttpJsonResult result) {
			showProgress = false;
			logger.debug(result);
			if(!result.success()){
				if(result.getResultCode() == ResultCode.CODE_FAIL_NO_NETWORK){
					Toast.makeText(mContext, R.string.alert_no_network,Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(mContext, R.string.alert_fail_network,Toast.LENGTH_LONG).show();
				}
			}else{
				
				if(token == TOKEN_ACCOUNT_ALTER_PWD){
					JSONObject jsonObject = result.getJsonResult();
					try {
						int resultCode = jsonObject.getInt("returnCode");
						String msg = (String)jsonObject.get("returnMsg");
						if (resultCode==0) {
							finish();
						}
						Toast.makeText(mContext, msg,Toast.LENGTH_LONG).show();
						
					} catch (Exception e) {
						e.printStackTrace();
						Toast.makeText(mContext, R.string.fail_alter_account_info,Toast.LENGTH_LONG).show();
					}
				}
			}
			if(!showProgress)
				mProgressDialog.dismiss();
		}
	}
	
	@Override
	protected boolean onBackable() {
		return true;
	}
	
	@Override
	protected String getHeaderTitle() {
		return getString(R.string.title_alter_pwd);
	}
}
