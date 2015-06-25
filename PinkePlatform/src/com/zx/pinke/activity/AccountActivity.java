package com.zx.pinke.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.pinke.R;
import com.zx.pinke.activity.base.BaseActivity;
import com.zx.pinke.bean.AccountInfo;
import com.zx.pinke.widget.xscrollview.ElasticScrollView;


public class AccountActivity extends BaseActivity {

	private ElasticScrollView mElasticScrollView;
	private ImageView mIconIv;
	private TextView mNameTv;
	private TextView mSexTv;
	private TextView mSchoolTv;
	private TextView mServiceTime;
	private TextView mServiceQuality;
	private TextView mLevel;
	
	private View mMyLaunch;
	private View mMyJoin;
	
	private View mAlterInfo;
	private View mAlterPwd;
	private static final int REQUEST_CODE_ACCOUNT_ALTER = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_i_account);
		mElasticScrollView = (ElasticScrollView)findViewById(R.id.scroll_view);
		
		View view = getLayoutInflater().inflate(R.layout.item_account,null, false);
		mElasticScrollView.addChild(view);
        
        mIconIv = (ImageView)findViewById(R.id.icon);
        mNameTv = (TextView)findViewById(R.id.account);
        mSexTv = (TextView)findViewById(R.id.sex);
        mSchoolTv = (TextView)findViewById(R.id.school);
        mIconIv.setImageResource(R.drawable.ic_header);

        mServiceTime = (TextView)findViewById(R.id.service_time);
        mServiceQuality = (TextView)findViewById(R.id.service_quality);
        mLevel = (TextView)findViewById(R.id.level);
        
        mAlterInfo = findViewById(R.id.alter_info);
        mAlterInfo.setOnClickListener(new AlterInfoListener());
        mAlterPwd = findViewById(R.id.alter_pwd);
        mAlterPwd.setOnClickListener(new AlterPwdListener());
        mMyLaunch = findViewById(R.id.my_launch);
        mMyLaunch.setOnClickListener(new MyLaunchListener());
        mMyJoin = findViewById(R.id.my_join);
        mMyJoin.setOnClickListener(new MyJoinListener());
//        mIconIv.setImageBitmap(toOvalBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher)));
//        resetAccountInfo();
        
        mIconIv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AccountActivity.this,UserInfoActivity.class);
				intent.putExtra("ai", AccountInfo.getAccountInfo());
				startActivity(intent);
			}
		});
        
        loadAccountInfo();
	}

	private class AlterInfoListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(AccountActivity.this,AccountAlterInfoActivity.class);
			startActivityForResult(intent, REQUEST_CODE_ACCOUNT_ALTER);
		}
	}
	
	private class AlterPwdListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
				
			Intent intent = new Intent(AccountActivity.this,AccountAlterPwdActivity.class);
//				Intent intent = new Intent(AccountActivity.this,AutoLoginBackgroundActivity.class);
			startActivity(intent);
		}
	}
	
	private class MyLaunchListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(AccountActivity.this,MyLaunchActivityInfoActivity.class);
			startActivity(intent);
		}
	}
	
	private class MyJoinListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
				
			Intent intent = new Intent(AccountActivity.this,MyJoinActivityInfoActivity.class);
			startActivity(intent);
		}
	}
	
	private void resetAccountInfo(){
        mServiceTime.setText(getString(R.string.ic_service_time, "--"));
        mNameTv.setText("我的昵称");
        mServiceQuality.setText("--");
        mLevel.setText("--");
        mSexTv.setText("--");
        mSchoolTv.setText("--");
	}
	
	private void loadAccountInfo(){
		
		AccountInfo info = AccountInfo.getAccountInfo();
		if(info.getAccount() != null){
			mNameTv.setText(info.getUserName());
			mServiceTime.setText(getString(R.string.ic_service_time, info.getStaffTime()+""));
			mServiceQuality.setText(info.getStaffScore()+"");
			mLevel.setText(info.getLevel()+"");
			if (info.getSex()==0) {
				mSexTv.setText(R.string.sex_male);
			}else {
				mSexTv.setText(R.string.sex_female);
			}
		    mSchoolTv.setText(info.getSchool());
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_CODE_ACCOUNT_ALTER){
			if(resultCode == RESULT_OK){
				loadAccountInfo();
			}
		}
	}
	@Override
	protected boolean onBackable() {
		return true;
	}
	
	@Override
	protected String getHeaderTitle() {
		return getString(R.string.ic_tittle);
	}
	
	@Override
	protected void initHeader(View view) {
		view.findViewById(R.id.btn_left_header).setVisibility(View.GONE);
		Button rightBtn = (Button)view.findViewById(R.id.btn_right_header);
		rightBtn.setBackgroundResource(R.drawable.btn_exit);
		rightBtn.setVisibility(View.VISIBLE);
		rightBtn.setOnClickListener(new LogoutListener());
	}
	
	private class LogoutListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			logoutDlg();
		}
	}

	protected void OnReceiveData(String str) {
		TextView textView =  new TextView(this);
		textView.setText(str);
		mElasticScrollView.addChild(textView, 1);
		mElasticScrollView.onRefreshComplete();
	}
	
	private void logoutDlg() {
		new AlertDialog.Builder(this)
				.setTitle("注销登录")
				.setMessage("确认注销此账号登录吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						resetAccountInfo();
						AccountInfo.clearAccontInfo();
						Intent intent=new Intent(AccountActivity.this, LoginActivity.class);
						startActivity(intent);
						
						finish();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}
}
