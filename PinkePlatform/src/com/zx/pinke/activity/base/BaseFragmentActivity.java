package com.zx.pinke.activity.base;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zx.pinke.R;
import com.zx.pinke.util.Log;
import com.zx.pinke.util.LogFactory;

public class BaseFragmentActivity extends FragmentActivity {

	public Log logger = LogFactory.getLog(this.getClass());
	
	@Override
	public Resources getResources() {
		return super.getResources();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private boolean mInitHeader = false;
	@Override
	protected void onStart() {
		if(!mInitHeader){
			View headerView = findViewById(R.id.header);
			if(headerView != null){
				((TextView)headerView.findViewById(R.id.tv_head_title)).setText(getHeaderTitle());
				if(onBackable()){
					Button leftBtn = (Button)headerView.findViewById(R.id.btn_left_header);
					leftBtn.setVisibility(View.VISIBLE);
					leftBtn.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							finish();
						}
					});
				}
				initHeader(headerView);
			}
			mInitHeader = true;
		}
		
		super.onStart();
	}
	
	/**
	 * 重新初始化头部
	 */
	protected void reInitHeader(){
		View headerView = findViewById(R.id.header);
		if(headerView != null){
			((TextView)headerView.findViewById(R.id.tv_head_title)).setText(getHeaderTitle());
			if(onBackable()){
				Button leftBtn = (Button)headerView.findViewById(R.id.btn_left_header);
				leftBtn.setVisibility(View.VISIBLE);
				leftBtn.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						finish();
					}
				});
			}
			initHeader(headerView);
		}
		mInitHeader = true;
	}
	
	//头部标题处理
	/**
	 * 左边按钮Button:@+id/btn_left_header
	 * 右边按钮Button:@+id/btn_right_header
	 */
	protected void initHeader(View view){}
	
	protected boolean onBackable(){
		return false;
	}
	
	protected String getHeaderTitle(){
		return getString(R.string.app_label);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
}
