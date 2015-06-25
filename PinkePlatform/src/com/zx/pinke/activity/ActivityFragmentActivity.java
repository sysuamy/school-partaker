package com.zx.pinke.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.zx.pinke.R;
import com.zx.pinke.activity.base.BaseFragmentActivity;
import com.zx.pinke.fragment.ActivityInfoFragment;

public class ActivityFragmentActivity extends BaseFragmentActivity {
	/**
	 * Tab标题
	 */

	private View mSearchPanel;
	private EditText mSearchEt;
	private Button mSearchDeleteBtn;
	private Button mSearchBtn;
	
	List<Fragment> fragments=new ArrayList<Fragment>();
	List<String> titles=new ArrayList<String>();
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment_activity);
		
		initHeader();
		initData();
		initView();
		
	}

	private void initData(){
		titles.add("吃饭");
		titles.add("运动");
		titles.add("出游");

		Bundle bundle1=new Bundle();
		bundle1.putInt("category", 0);
		Fragment fragment1=new ActivityInfoFragment();
		fragment1.setArguments(bundle1);
		
		Bundle bundle2=new Bundle();
		bundle2.putInt("category", 1);
		Fragment fragment2=new ActivityInfoFragment();
		fragment2.setArguments(bundle2);
		
		Bundle bundle3=new Bundle();
		bundle3.putInt("category", 2);
		Fragment fragment3=new ActivityInfoFragment();
		fragment3.setArguments(bundle3);
		
		fragments.add(fragment1);
		fragments.add(fragment2);
		fragments.add(fragment3);

	}
	
	private void initView() {
		// TODO Auto-generated method stub

		//ViewPager的adapter
				FragmentStatePagerAdapter adapter = new TabPageIndicatorAdapter(getSupportFragmentManager(),fragments,titles);
		        ViewPager pager = (ViewPager)findViewById(R.id.mViewPager);
		        pager.setAdapter(adapter);

				PagerTabStrip mPagerTabStrip = (PagerTabStrip) findViewById(R.id.mPagerTabStrip);
				mPagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.light_blue)); 
				mPagerTabStrip.setTextColor(getResources().getColor(R.color.light_blue));

	}
	
	protected void initHeader() {
		mSearchPanel = findViewById(R.id.search_panel);
		mSearchEt = (EditText) findViewById(R.id.search_et);
		mSearchDeleteBtn = (Button) findViewById(R.id.delete);
		mSearchDeleteBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mSearchEt.setText("");
				mSearchPanel.setVisibility(View.GONE);
				mSearchBtn.setBackgroundResource(R.drawable.ic_search_big);
				mSearchBtn.setTag(false);

			}
		});
		mSearchBtn = (Button) findViewById(R.id.btn_right_header);
		mSearchBtn.setVisibility(View.GONE);
		mSearchBtn.setBackgroundResource(R.drawable.ic_search_big);
		mSearchBtn.setTag(false);
		mSearchBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean searchable = (Boolean) v.getTag();
				if (!searchable) {
					mSearchPanel.setVisibility(View.VISIBLE);
					mSearchBtn.setBackgroundResource(R.drawable.ic_search_btn);
					v.setTag(true);
				} else {
					String content = mSearchEt.getText().toString().trim();
					if (!TextUtils.isEmpty(content)) {
						
					}
				}
			}
		});
	}

	/**
	 * ViewPager适配�?
	 * @author len
	 *
	 */
    class TabPageIndicatorAdapter extends FragmentStatePagerAdapter {
    	
    	List<Fragment> mFragments=new ArrayList<Fragment>();
    	List<String> mTitles=new ArrayList<String>();
    	
        public TabPageIndicatorAdapter(FragmentManager fm,List<Fragment> fragments,List<String> titles) {
            super(fm);
            mFragments=fragments;
            mTitles=titles;
        }

        @Override
        public Fragment getItem(int position) {
        	//新建多个Fragment来展示ViewPager item的内容，并传递参�?
        	
            return mFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        @Override
        public int getCount() {
            return mTitles.size();
        }
    }

}
