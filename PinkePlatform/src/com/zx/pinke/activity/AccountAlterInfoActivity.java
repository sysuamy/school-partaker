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
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.zx.pinke.R;
import com.zx.pinke.activity.base.BaseActivity;
import com.zx.pinke.bean.AccountInfo;
import com.zx.pinke.util.AppConfig;
import com.zx.pinke.util.AsyncHttpQueryHandle;
import com.zx.pinke.util.HttpFileResult.ResultCode;
import com.zx.pinke.util.HttpJsonResult;

public class AccountAlterInfoActivity extends BaseActivity {

	private static final int TOKEN_ACCOUNT_ALTER_INFO = 1;//修改
	
	private BackgroundQueryHandler mBackgroundQueryHandler;
	private ProgressDialog mProgressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_i_account_alter_info);
		mBackgroundQueryHandler = new BackgroundQueryHandler(this);
		mProgressDialog = new ProgressDialog(AccountAlterInfoActivity.this);
		mProgressDialog.setMessage(getString(R.string.processing));
		mProgressDialog.setCancelable(true);
		mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				switch (keyCode) {
					case KeyEvent.KEYCODE_BACK:
						mBackgroundQueryHandler.cancelOperation(TOKEN_ACCOUNT_ALTER_INFO);
				}
				return false;
			}
		});
		initViews();
	}
	
	private EditText mPhoneEt;
	private EditText mNameEt;
	private EditText mEmailEt;
	private RadioGroup mSexRg;
	private EditText mSchoolEt;
	
	private Button mAlterBtn;
	
	private int sex=0;
	
	private void initViews(){
		mPhoneEt = (EditText)findViewById(R.id.phone);
		mNameEt = (EditText)findViewById(R.id.name);
		mEmailEt = (EditText)findViewById(R.id.email);
		mSexRg = (RadioGroup)findViewById(R.id.sexEnum);
		mSchoolEt = (EditText)findViewById(R.id.school);
		
		mAlterBtn = (Button)findViewById(R.id.btn_alter);
		mAlterBtn.setOnClickListener(new AlterListener());
		
		AccountInfo info = AccountInfo.getAccountInfo();
		if(info.getAccount() != null){
			mPhoneEt.setText(info.getPhone() == null? "" : info.getPhone());
			mNameEt.setText(info.getUserName() == null ?  "" : info.getUserName());
			mSchoolEt.setText(info.getSchool() == null ?  "" : info.getSchool());
			mEmailEt.setText(info.getEmail() == null ?  "" : info.getEmail());
			if (info.getSex()==0) {
				mSexRg.check(R.id.male);
			}else {
				mSexRg.check(R.id.female);
			}

		}
		
		mSexRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.male:
					sex=0;
					break;
				case R.id.female:
					sex=1;
					break;
				default:
					sex=0;
					break;
				}
			}
		});
		
	}
	
	private class AlterListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			String phone = mPhoneEt.getText().toString().trim();
			String name = mNameEt.getText().toString().trim();
			String email = mEmailEt.getText().toString().trim();
			String school = mSchoolEt.getText().toString().trim();
			
			if(TextUtils.isEmpty(name)){
				shakeAndToast(mNameEt,getString(R.string.warn_name_not_empty));
				return;
			}
			
			if(TextUtils.isEmpty(phone)){
				shakeAndToast(mPhoneEt,getString(R.string.warn_phone_number_not_empty));
				return;
			}
			
			if(TextUtils.isEmpty(school)){
				shakeAndToast(mSchoolEt,getString(R.string.warn_school_not_empty));
				return;
			}
			
			if(TextUtils.isEmpty(email)){
				shakeAndToast(mEmailEt,getString(R.string.warn_email_not_empty));
				return;
			}
			
			try {
				mProgressDialog.show();
				URI uri = new URI(AppConfig.NetWork.IV.URL_ACCOUNT_ALTER_INFO);
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("phone", phone);
				params.put("pname", name);
				params.put("email", email);
				params.put("school", school);
				params.put("sex", sex);
				
				AccountInfo info = AccountInfo.getAccountInfo();
				String sessionId = info.getSessionId();
				params.put("suser", info.getAccount());
				mBackgroundQueryHandler.startPost(TOKEN_ACCOUNT_ALTER_INFO, null, uri, params,sessionId);
				
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			
		}
		
		private void shakeAndToast(View view,String error){
			 Animation shake = AnimationUtils.loadAnimation(AccountAlterInfoActivity.this, R.anim.shake);
			 view.requestFocus();
			 view.startAnimation(shake);
			 Toast.makeText(AccountAlterInfoActivity.this, error,Toast.LENGTH_LONG).show();
		}
	}
	
//	private class ChooseServiceAreaListener implements View.OnClickListener{
//
//		private final String[] AREA_CODES = {
//			"0591","0592","0593","0594","0595","0596","0597","0598","0599"
//		};
//		private final String[] AREA_NAMES = {"福州","厦门","宁德","莆田","泉州","漳州","龙岩","三明","南平"};
//		
//		@Override
//		public void onClick(final View v) {
//			new AlertDialog.Builder(AccountAlterInfoActivity.this).setTitle("选择服务地区") 
//			.setSingleChoiceItems(AREA_NAMES, 1, new DialogInterface.OnClickListener() { 
//			     public void onClick(DialogInterface dialog, int item) { 
//			            Toast.makeText(getApplicationContext(), AREA_NAMES[item],  
//			        Toast.LENGTH_SHORT).show();
//			            EditText serviceAreaEt = (EditText)v;
//			            serviceAreaEt.setText(AREA_NAMES[item]);
//			            serviceAreaEt.setTag(AREA_CODES[item]);
//			            dialog.cancel(); 
//			      } 
//			}).show();
//		}
//	}
	
	private static final int REQUEST_CODE_AUTO_LOGIN = 1;
	
	private boolean requestAutoLogined = false;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode == REQUEST_CODE_AUTO_LOGIN){
			requestAutoLogined = true;
			if(resultCode == RESULT_OK){
				mAlterBtn.performClick();
			}else if(resultCode == RESULT_CANCELED){
				if(mProgressDialog.isShowing()){
					mProgressDialog.dismiss();
				}
			}else{
				if(mProgressDialog.isShowing()){
					mProgressDialog.dismiss();
					Toast.makeText(this, R.string.fail_alter_account_info,Toast.LENGTH_LONG).show();
				}
			}
		}
		
	}
	
	private class BackgroundQueryHandler extends AsyncHttpQueryHandle{

		private static final int RESULT_CODE_OK = 0;
		private static final int RESULT_CODE_NO_AUTH = 1;
		private static final int RESULT_CODE_ERROR = 2;
		
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
				
				if(token == TOKEN_ACCOUNT_ALTER_INFO){
					JSONObject jsonObject = result.getJsonResult();
					try {
						int resultCode = jsonObject.getInt("returnCode");
						String msg = (String)jsonObject.get("returnMsg");
						if (resultCode==0) {
							JSONObject info = jsonObject.getJSONObject("userInfo");
							try {
								AccountInfo.saveAccountInfo(AccountInfo.getAccountInfo().getSessionId(),AccountInfo.getAccountInfo().getAccount(), AccountInfo.getAccountInfo().getPassword(), info);
								setResult(RESULT_OK);
								finish();
							} catch (Exception e) {
								e.printStackTrace();
								logger.error("用户信息解析出错！");
							}
						}
						Toast.makeText(mContext, msg,Toast.LENGTH_LONG).show();
						
					} catch (JSONException e) {
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
		return getString(R.string.title_alter_info);
	}
}
