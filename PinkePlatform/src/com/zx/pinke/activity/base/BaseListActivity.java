package com.zx.pinke.activity.base;

import android.app.ListActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zx.pinke.R;
import com.zx.pinke.util.Log;
import com.zx.pinke.util.LogFactory;

/**
 * 基类-用于扩展统计和主题样式
 * @author lintp
 *
 */
public class BaseListActivity extends ListActivity {

	protected Log logger = LogFactory.getLog(getClass());
	
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
	
	//头部标题处理
	/**
	 * 左边按钮Button:@+id/btn_left_header
	 * 右边按钮Button:@+id/btn_right_header
	 */
	protected void initHeader(View view){}
	protected String getHeaderTitle(){
		return getString(R.string.app_label);
	}
	
	protected boolean onBackable(){
		return false;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		//UsrActionAgent.onResume(this);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		//UsrActionAgent.onResume(this);
	}
}
