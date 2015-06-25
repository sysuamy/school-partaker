package com.zx.pinke.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.zx.pinke.R;
import com.zx.pinke.util.Log;
import com.zx.pinke.util.LogFactory;

public class MainActivity extends TabActivity {

	public Log logger = LogFactory.getLog(this.getClass());
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		buildTabSpec();
	}
	
	private void buildTabSpec() {

		List<TabItem> tabItems = getTabItems();
		for(TabItem tabItem : tabItems){

			View tabItemView = getLayoutInflater().inflate(R.layout.item_tab_nav_main, null);
			TextView tvTabItem = (TextView) tabItemView.findViewById(R.id.tab_item_tv);
			LinearLayout tab_layout=(LinearLayout)tabItemView.findViewById(R.id.tab_layout);
			tvTabItem.setText(tabItem.getTitle());
			tvTabItem.setCompoundDrawablesWithIntrinsicBounds(0, tabItem.getIcon(), 0, 0);
			tab_layout.setBackgroundResource(R.drawable.bg_tab_footer);
			TabSpec tabSpec = getTabHost().newTabSpec(tabItem.getTitle());
			tabSpec.setIndicator(tabItemView);
			tabSpec.setContent(tabItem.getIntent());
			getTabHost().addTab(tabSpec);
		}
	}
	
	//导航Tab数据
	private List<TabItem> getTabItems(){

		List<TabItem> tabs = new ArrayList<TabItem>();
		TabItem publicServiceItem = new TabItem(
				this.getString(R.string.nav_public_service_activity),R.drawable.ic_public_service,new Intent(this,ActivityFragmentActivity.class));

		TabItem volunteerItem = new TabItem(
				this.getString(R.string.nav_volunteer_activity),R.drawable.ic_donation,new Intent(this,LaunchActivity.class));

		TabItem selfItem = new TabItem(
				this.getString(R.string.nav_myself_activity),R.drawable.ic_my_volunteer,new Intent(this,AccountActivity.class));

		tabs.add(publicServiceItem);
		tabs.add(volunteerItem);
		tabs.add(selfItem);
		
		return tabs;
	}
		
	class TabItem {
		private String title;// tab item title
		private int icon;
		private Intent intent;
		public TabItem(String title,int icon,Intent intent) {
			this.title = title;
			this.icon = icon;
			this.intent = intent;
		}
		public Intent getIntent() {
			return intent;
		}
		public String getTitle() {
			return title;
		}

		public int getIcon(){
			return icon;
		}
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getRepeatCount() == 0) {
			// 具体的操作代码
			quitDlg();
		}
		return super.dispatchKeyEvent(event);
	}

	private void quitDlg() {
		new AlertDialog.Builder(this)
				.setTitle("温馨提示")
				.setMessage("确认退出吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
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
