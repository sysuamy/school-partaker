package com.zx.pinke.activity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
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
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.zx.pinke.R;
import com.zx.pinke.activity.base.BaseActivity;
import com.zx.pinke.util.AppConfig;
import com.zx.pinke.util.AppPreference;
import com.zx.pinke.util.AsyncHttpQueryHandle;
import com.zx.pinke.util.HttpFileResult.ResultCode;
import com.zx.pinke.util.HttpJsonResult;

public class RegisterActivity extends BaseActivity {

	private static final int TOKEN_ACCOUNT_REGISTER = 1;
	
	private BackgroundQueryHandler mBackgroundQueryHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		mBackgroundQueryHandler = new BackgroundQueryHandler(this);
		mProgressDialog = new ProgressDialog(RegisterActivity.this);
		mProgressDialog.setMessage(getString(R.string.logining));
		mProgressDialog.setCancelable(true);
		mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				switch (keyCode) {
					case KeyEvent.KEYCODE_BACK:
						mBackgroundQueryHandler.cancelOperation(TOKEN_ACCOUNT_REGISTER);
				}
				return false;
			}
		});
		
		initViews();
	}
	
	@Override
	protected boolean onBackable() {
		return true;
	}
	
	@Override
	protected String getHeaderTitle() {
		return getString(R.string.title_i_register);
	}
	
	private EditText mUserNameEt;
	private EditText mPasswordEt;
	private EditText mConfirmPwdEt;
	private EditText mNameEt;
	private EditText mIDCardEt;
	private EditText mPhoneEt;
	private EditText mServiceAreaEt;
	private RadioGroup mSexRg;
	private EditText mSchoolEt;
	private EditText mEmailEt;
	
	private Button mRegisterBtn;
	
	private ProgressDialog mProgressDialog;
	
	private int sex=0;
	
	private void initViews(){
		mUserNameEt = (EditText)findViewById(R.id.user_name);
		mPasswordEt = (EditText)findViewById(R.id.password);
		mConfirmPwdEt = (EditText)findViewById(R.id.confirm_password);
		mNameEt = (EditText)findViewById(R.id.name);
		mIDCardEt = (EditText)findViewById(R.id.id_card);
		mPhoneEt = (EditText)findViewById(R.id.phone);
		
		mServiceAreaEt = (EditText)findViewById(R.id.service_area);
		
		mSexRg = (RadioGroup)findViewById(R.id.sexEnum);
		mSchoolEt = (EditText)findViewById(R.id.school);
		mEmailEt = (EditText)findViewById(R.id.email);
		
		mRegisterBtn = (Button)findViewById(R.id.btn_register);
		
		mRegisterBtn.setOnClickListener(new RegisterListener());
		
		mServiceAreaEt.setOnClickListener(new ChooseServiceAreaListener());
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
	
	private class RegisterListener implements View.OnClickListener{

		private String ID_CARD_15_PATTERN = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
		private String ID_CARD_18_PATTERN = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$";
		
		@Override
		public void onClick(View v) {
			
			String userName = mUserNameEt.getText().toString().trim();
			String password = mPasswordEt.getText().toString().trim();
			String confirmPwd = mConfirmPwdEt.getText().toString().trim();
			String name = mNameEt.getText().toString().trim();
			String idCard = mIDCardEt.getText().toString().trim();
			String phone = mPhoneEt.getText().toString().trim();
			
			String school = mSchoolEt.getText().toString().trim();
			String email = mEmailEt.getText().toString().trim();
			
			String serviceCity = mServiceAreaEt.getTag()== null?"":mServiceAreaEt.getTag().toString().trim();
			
			if(TextUtils.isEmpty(userName)){
				shakeAndToast(mUserNameEt,getString(R.string.warn_user_name_not_empty));
				return;
			}
			
			if(TextUtils.isEmpty(password)){
				shakeAndToast(mPasswordEt,getString(R.string.warn_password_not_empty));
				return;
			}
			
			if(TextUtils.isEmpty(confirmPwd)){
				shakeAndToast(mConfirmPwdEt,getString(R.string.warn_password_not_empty));
				return;
			}
			
			if(!confirmPwd.equals(password)){
				shakeAndToast(mConfirmPwdEt,getString(R.string.warn_different_two_password));
				return;
			}
			
			idCard="450907198709211423";
			if(TextUtils.isEmpty(idCard)){
				shakeAndToast(mIDCardEt,getString(R.string.warn_id_card_not_empty));
				return;
			}
			if(!(idCard.length()==15 || idCard.length()==18)){
				shakeAndToast(mIDCardEt,getString(R.string.warn_invalid_id_card));
				return;
			}
			
			if(idCard.length()==15){
				if(!idCard.matches(ID_CARD_15_PATTERN)){
					shakeAndToast(mIDCardEt,getString(R.string.warn_invalid_id_card));
					return;
				}
			}
			
			if(idCard.length()==18){
				if(!idCard.matches(ID_CARD_18_PATTERN)){
					shakeAndToast(mIDCardEt,getString(R.string.warn_invalid_id_card));
					return;
				}
			}
			
			serviceCity="南宁";
			if(TextUtils.isEmpty(serviceCity)){
				shakeAndToast(mServiceAreaEt,getString(R.string.warn_service_area_not_empty));
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
				URI uri = new URI(AppConfig.NetWork.IV.URL_ACCOUNT_REGISTER);
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("username", userName);
				params.put("password", password);
				params.put("pname", name);
				params.put("idCard", idCard);
				params.put("phone", phone);
				params.put("address", serviceCity);
				params.put("area", "0591");
				params.put("sex", sex);
				params.put("school", school);
				params.put("email", email);
				
				mBackgroundQueryHandler.startPost(TOKEN_ACCOUNT_REGISTER, userName, uri, params);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		
		private void shakeAndToast(View view,String error){
			 Animation shake = AnimationUtils.loadAnimation(RegisterActivity.this, R.anim.shake);
			 view.requestFocus();
			 view.startAnimation(shake);
			 Toast.makeText(RegisterActivity.this, error,Toast.LENGTH_LONG).show();
		}
	}
	
	private class ChooseServiceAreaListener implements View.OnClickListener{

		private final String[] AREA_CODES = {
			"0591","0592","0593","0594","0595","0596","0597","0598","0599"
		};
		private final String[] AREA_NAMES = {"南宁","柳州","桂林","北海","河池","玉林","钦州","梧州","百色"};
		
		@Override
		public void onClick(final View v) {
			new AlertDialog.Builder(RegisterActivity.this).setTitle("请选择您的所在城市") 
			.setSingleChoiceItems(AREA_NAMES, 1, new DialogInterface.OnClickListener() { 
			     public void onClick(DialogInterface dialog, int item) { 
			            Toast.makeText(getApplicationContext(), AREA_NAMES[item],  
			        Toast.LENGTH_SHORT).show();
			            EditText serviceAreaEt = (EditText)v;
			            serviceAreaEt.setText(AREA_NAMES[item]);
			            serviceAreaEt.setTag(AREA_CODES[item]);
			            dialog.cancel(); 
			      } 
			}).show();
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
				
				if(token == TOKEN_ACCOUNT_REGISTER){
					JSONObject jsonObject = result.getJsonResult();
					try {
						int success = jsonObject.getInt("returnCode");
						String msg = (String)jsonObject.get("returnMsg");
						if(success==0){
							String account = (String)cookie;
							AppPreference.putString(AppPreference.IV.PREF_KEY_ACCOUNT, account);
							setResult(RESULT_OK);
							finish();
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
