package com.zx.pinke.activity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
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
import android.view.View.OnClickListener;
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
import com.zx.pinke.lisnter.OnDateTimeChangeLisnter;
import com.zx.pinke.util.AppConfig;
import com.zx.pinke.util.AsyncHttpQueryHandle;
import com.zx.pinke.util.DateUtil;
import com.zx.pinke.util.HttpFileResult.ResultCode;
import com.zx.pinke.util.HttpJsonResult;
import com.zx.pinke.widget.xdialog.DateTimePickerDialog;

public class LaunchActivity extends BaseActivity {

	private static final int TOKEN_ADD_SHARE = 1;//ÐÞ¸Ä
	
	private BackgroundQueryHandler mBackgroundQueryHandler;
	private ProgressDialog mProgressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
		mBackgroundQueryHandler = new BackgroundQueryHandler(this);
		mProgressDialog = new ProgressDialog(LaunchActivity.this);
		mProgressDialog.setMessage(getString(R.string.processing));
		mProgressDialog.setCancelable(true);
		mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				switch (keyCode) {
					case KeyEvent.KEYCODE_BACK:
						mBackgroundQueryHandler.cancelOperation(TOKEN_ADD_SHARE);
				}
				return false;
			}
		});
		initViews();
	}
	
	private EditText mTitleEt;
	private EditText mStartTimeEt;
	private EditText mEndTimeEt;
	private EditText mPlcaeEt;
	private EditText mNumberEt;
	private EditText mPhoneEt;
	private EditText mContentEt;
	private RadioGroup mCateRg;
	
	private Button mAddShareBtn;
	
	private String category="³Ô·¹";
	
	@Override
	protected void initHeader(View view) {
		// TODO Auto-generated method stub
		view.findViewById(R.id.btn_left_header).setVisibility(View.GONE);
	}
	
	private void initViews(){
		mTitleEt = (EditText)findViewById(R.id.title);
		mStartTimeEt = (EditText)findViewById(R.id.startTime);
		mEndTimeEt = (EditText)findViewById(R.id.endTime);
		mPlcaeEt = (EditText)findViewById(R.id.place);
		mNumberEt = (EditText)findViewById(R.id.number);
		mPhoneEt = (EditText)findViewById(R.id.phone);
		mContentEt = (EditText)findViewById(R.id.content);
		mCateRg = (RadioGroup)findViewById(R.id.cateEmun);
		
		mAddShareBtn = (Button)findViewById(R.id.btn_addShare);
		mAddShareBtn.setOnClickListener(new AddShareListener());
		
		mNumberEt.setText("0");
		mPhoneEt.setText(AccountInfo.getAccountInfo().getPhone());
		
		mCateRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.meal:
					category=getString(R.string.cate_meal);
					break;
				case R.id.sport:
					category=getString(R.string.cate_sport);
					break;
				case R.id.tour:
					category=getString(R.string.cate_tour);
					break;
				default:
					category=getString(R.string.cate_meal);
					break;
				}
			}
		});
		
		mStartTimeEt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DateTimePickerDialog dateTimePickerDialog=new DateTimePickerDialog(LaunchActivity.this, new OnDateTimeChangeLisnter() {
					
					@Override
					public void OnDateTimeChange(long time) {
						// TODO Auto-generated method stub
						mStartTimeEt.setText(DateUtil.getDateTimeStr(new Date(time)));
					}
				}, Calendar.getInstance());
				dateTimePickerDialog.show();
			}
		});
	}
	
	private class AddShareListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			String title = mTitleEt.getText().toString().trim();
			String startTime = mStartTimeEt.getText().toString().trim();
			String endTime = mEndTimeEt.getText().toString().trim();
			String place = mPlcaeEt.getText().toString().trim();
			String number = mNumberEt.getText().toString().trim();
			String phone = mPhoneEt.getText().toString().trim();
			String content = mContentEt.getText().toString().trim();
			
			if(TextUtils.isEmpty(title)){
				shakeAndToast(mTitleEt,getString(R.string.warn_title_not_empty));
				return;
			}
			
			if(TextUtils.isEmpty(startTime)){
				shakeAndToast(mStartTimeEt,getString(R.string.warn_start_time_not_empty));
				return;
			}
			
			endTime=startTime;
//			if(TextUtils.isEmpty(endTime)){
//				shakeAndToast(mEndTimeEt,getString(R.string.warn_end_time_not_empty));
//				return;
//			}
			
			if(TextUtils.isEmpty(place)){
				shakeAndToast(mPlcaeEt,getString(R.string.warn_place_not_empty));
				return;
			}
			
			if(TextUtils.isEmpty(number)){
				shakeAndToast(mNumberEt,getString(R.string.warn_join_number_not_empty));
				return;
			}
			
			if(TextUtils.isEmpty(phone)){
				shakeAndToast(mPhoneEt,getString(R.string.warn_phone_number_not_empty));
				return;
			}
			
			if(TextUtils.isEmpty(content)){
				shakeAndToast(mContentEt,getString(R.string.warn_content_not_empty));
				return;
			}
			
			try {
				mProgressDialog.show();
				URI uri = new URI(AppConfig.NetWork.IV.URL_SERVICE_ADD);
				
				AccountInfo info = AccountInfo.getAccountInfo();
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("title", title);
				params.put("category", category);
				params.put("placeStr", place);
				params.put("placeCode",info.getArea() );
				params.put("startDate", startTime);
				params.put("endDate", endTime);
				params.put("needNum", number);
				params.put("contact", phone);
				params.put("content", content);
			
				params.put("suser", info.getAccount());
				String sessionId = info.getSessionId();
				
				mBackgroundQueryHandler.startPost(TOKEN_ADD_SHARE, null, uri, params,sessionId);
				
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			
		}
		
		private void shakeAndToast(View view,String error){
			 Animation shake = AnimationUtils.loadAnimation(LaunchActivity.this, R.anim.shake);
			 view.requestFocus();
			 view.startAnimation(shake);
			 Toast.makeText(LaunchActivity.this, error,Toast.LENGTH_LONG).show();
		}
	}
	
	
	private static final int REQUEST_CODE_AUTO_LOGIN = 1;
	
	private boolean requestAutoLogined = false;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode == REQUEST_CODE_AUTO_LOGIN){
			requestAutoLogined = true;
			if(resultCode == RESULT_OK){
				mAddShareBtn.performClick();
			}else if(resultCode == RESULT_CANCELED){
				if(mProgressDialog.isShowing()){
					mProgressDialog.dismiss();
				}
			}else{
				if(mProgressDialog.isShowing()){
					mProgressDialog.dismiss();
					Toast.makeText(this, R.string.fail_add_share,Toast.LENGTH_LONG).show();
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
				
				if(token == TOKEN_ADD_SHARE){
					JSONObject jsonObject = result.getJsonResult();
					try {
						int resultCode = jsonObject.getInt("returnCode");
						String msg = (String)jsonObject.get("returnMsg");
						Toast.makeText(mContext, msg,Toast.LENGTH_LONG).show();
					} catch (JSONException e) {
						e.printStackTrace();
						Toast.makeText(mContext, R.string.fail_add_share,Toast.LENGTH_LONG).show();
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
		return getString(R.string.title_alter_add_share);
	}
}
