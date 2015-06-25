package com.zx.pinke.activity;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zx.pinke.R;
import com.zx.pinke.activity.base.BaseListActivity;
import com.zx.pinke.bean.AccountInfo;
import com.zx.pinke.util.AppConfig;
import com.zx.pinke.util.AsyncHttpQueryHandle;
import com.zx.pinke.util.DateUtil;
import com.zx.pinke.util.HttpFileResult.ResultCode;
import com.zx.pinke.util.HttpJsonResult;
import com.zx.pinke.util.ImageDownloader;
import com.zx.pinke.util.Log;
import com.zx.pinke.util.LogFactory;
import com.zx.pinke.widget.xlistview.XListView;
import com.zx.pinke.widget.xlistview.XListView.IXListViewListener;

public class JoinUserListActivity extends BaseListActivity implements
		IXListViewListener {
	private static Log logger = LogFactory.getLog(JoinUserListActivity.class);
	private XListView xListView;
	private TextView mTipTv;

	private QueryCondition mQueryCondition = new QueryCondition();
	private UserInfoPaginationAdapter mAdapter;
	private BackgroundQueryHandler mBackgroundQueryHandler;
	
	private static final int REQUEST_CODE_USERLIST = 5;

	private static final int TOKEN_QUERY_CONTENT = 1;
	private static final String PARAM_START = "currentPage";
	private static final String PARAM_LIMIT = "pageSize";
	private int start = 0;
	private int limit = 10;

	private static final String SERVICES_URL = AppConfig.NetWork.IV.URL_SERVICE_USERLIST;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_join_userlist);
		mBackgroundQueryHandler = new BackgroundQueryHandler(this);
		initViews();
	}

	private void initViews() {

		xListView = (XListView) getListView();
		xListView.setXListViewListener(this);
		xListView.setPullLoadEnable(true);
		// List<PublicService> list = new ArrayList<PublicService>();
		// PublicService publicService = new PublicService();
		// publicService.setContent("不是我不明白");
		// publicService.setId(1);
		// publicService.setEndDate("2013-8-7");
		// publicService.setStartDate("2012-8-7");
		// list.add(publicService);
	
		xListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				logger.info("当前位置：ShareInfoActivity.itemClickListener"+position);
				AccountInfo accountInfo = (AccountInfo)mAdapter.getItem(position-1);
				Intent intent = new Intent(JoinUserListActivity.this,UserInfoActivity.class);
				intent.putExtra("ai",accountInfo);
				startActivityForResult(intent, REQUEST_CODE_USERLIST);
			}

		});
		xListView.getEmptyView().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!mBackgroundQueryHandler.hasMessages(TOKEN_QUERY_CONTENT)) {
					onRefresh();
				}
			}
		});
		mTipTv = (TextView) xListView.getEmptyView()
				.findViewById(R.id.empty_tv);
		onRefresh();
	}

	private class UserInfoPaginationAdapter extends BaseAdapter {

		private Context mContext;
		private List<AccountInfo> mItems;
		private ImageDownloader imageDownloader;

		public UserInfoPaginationAdapter(Context context,
				List<AccountInfo> items) {
			mContext = context;
			mItems = items;
			imageDownloader = new ImageDownloader(context, context
					.getResources().getDrawable(R.drawable.ic_launcher));
			imageDownloader
					.setOnDownloadComplete(new ImageDownloader.OnDownloadComplete() {
						@Override
						public void execute(String url, ImageView imageView,
								Bitmap image) {
							if (image != null) {
								ByteArrayOutputStream baos = new ByteArrayOutputStream();
								image.compress(Bitmap.CompressFormat.PNG, 100,
										baos);
							}
						}
					});
		}

		@Override
		public int getCount() {
			return mItems.size();
		}

		@Override
		public Object getItem(int position) {
			return mItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public void addItem(AccountInfo c) {
			mItems.add(c);
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {

			if (view == null) {
				view = getLayoutInflater().inflate(
						R.layout.item_activity_join_userlist, parent, false);
			}
			AccountInfo accountInfo = mItems.get(position);
			String icon = accountInfo.getThumbUrl();
			if (icon == null) {
				// imageDownloader.download(publicService.getIconUrl(),(ImageView)view.findViewById(R.id.icon));
				ImageView imageView = (ImageView) view.findViewById(R.id.icon);

			} else {
			
//				imageDownloader.download(icon,(ImageView)view.findViewById(R.id.icon));
			}

			//SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
	
			((TextView) view.findViewById(R.id.contacts)).setText(getString(R.string.contacts_of,accountInfo.getPhone()));
			((TextView)view.findViewById(R.id.launch)).setText( accountInfo.getUserName());
			return view;
		}

	}

	@Override
	public void onRefresh() {
		logger.info("onRefresh开始刷新....");
		try {
			URI uri = new URI(SERVICES_URL);
			mTipTv.setText(R.string.loading_and_waiting);
			mQueryCondition.reset();
			mBackgroundQueryHandler.startPost(TOKEN_QUERY_CONTENT, true, uri,
					mQueryCondition.getQueryParams());
		} catch (Exception e) {
			logger.debug(e.toString());
			xListView.stopRefresh();
		}
	}

	@Override
	public void onLoadMore() {

//		logger.info("onLoadMore开始更多....");
//		try {
//			URI uri = new URI(SERVICES_URL);
//			mQueryCondition.updateCondition(PARAM_START, start);
//			mTipTv.setText(R.string.loading_and_waiting);
//			mBackgroundQueryHandler.startPost(TOKEN_QUERY_CONTENT, false, uri,
//					mQueryCondition.getQueryParams());
//		} catch (Exception e) {
//			xListView.stopLoadMore();
//		}
		xListView.stopLoadMore();
	}

	class QueryCondition {
		private Map<String, Object> params = new HashMap<String, Object>();

		
		public QueryCondition() {
			reset();
		}

		public void reset() {
			start = 0;
			params.put(PARAM_START, start + "");
			params.put(PARAM_LIMIT, limit + "");
		}

		public Map<String, Object> getQueryParams() {
			params.put("activeid", getIntent().getStringExtra("mid"));
			params.put("suser", AccountInfo.getAccountInfo().getAccount());
			return this.params;
		}

		/**
		 * 重置翻页参数
		 * 
		 * @param key
		 * @param value
		 */
		public void resetCondition(String key, Object value) {
			reset();
			params.put(key, value);
		}

		/**
		 * 更新某个查询参数
		 * 
		 * @param key
		 * @param value
		 */
		public void updateCondition(String key, Object value) {
			params.put(key, value);
		}
	}

	private class BackgroundQueryHandler extends AsyncHttpQueryHandle {

		private Context mContext;

		public BackgroundQueryHandler(Context context) {
			super(context);
			mContext = context;
		}

		@Override
		protected void onPostComplete(int token, Object cookie,
				HttpJsonResult result) {
			boolean isRefresh = cookie == null ? false : (Boolean) cookie;
			logger.debug("jsonresult=" + result.toString());

			if (result.success()) {
				xListView.setRefreshTime(DateUtil.getDateText(System
						.currentTimeMillis()));
				processResult(result, isRefresh);
			} else {
				if (result.getResultCode() == ResultCode.CODE_FAIL_NO_NETWORK) {
					Toast.makeText(mContext, R.string.alert_no_network,
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(mContext, R.string.alert_fail_network,
							Toast.LENGTH_SHORT).show();
				}
				if (((UserInfoPaginationAdapter) ((HeaderViewListAdapter) xListView
						.getAdapter()).getWrappedAdapter()).getCount() == 0) {
					mTipTv.setText(R.string.click_and_reload);
				}
			}
			xListView.stopRefresh();
			xListView.stopLoadMore();
		}

		// 处理JSOn返回结果
		private synchronized void processResult(HttpJsonResult result,
				boolean isRefresh) {

			JSONObject jsonObj = result.getJsonResult();
			try {

				int resultCode = jsonObj.getInt(PARAM_RESULT_CODE);
				System.out.println("resultCode == CODE_SUCCESS" + "---->"
						+ (CODE_SUCCESS == resultCode));
				if (CODE_SUCCESS == resultCode) {
					if (isRefresh) {// 更新操作
						JSONArray jsonArray = jsonObj.getJSONArray("userList");

						List<AccountInfo> accountInfos=new ArrayList<AccountInfo>();
						try {
							logger.info(jsonArray.length()+"");
						
							
							
							for (int j = 0; j < jsonArray.length(); j++) {
								AccountInfo accountInfo=new AccountInfo();
								
								JSONObject userInfo = (JSONObject) jsonArray.get(j);
								
								accountInfo.setAccount(userInfo.getString("suser"));
								accountInfo.setPassword(userInfo.getString("spwd"));
								accountInfo.setThumbUrl(userInfo.getString("simgPath")); ;//头像http地址
								accountInfo.setStaffTime(userInfo.getInt("ivTitle")); ;//等级
								accountInfo.setUserName(userInfo.getString("sname")); ;//用户名
								accountInfo.setLevel(userInfo.getInt("ivTimes")); ;//服务时长
								accountInfo.setStaffScore(userInfo.getInt("ivScore")); ;//服务质量
								accountInfo.setPhone(userInfo.getString("sphone"));  ;//手机号
								
								accountInfo.setSex(userInfo.getInt("ssex")); ;//服务时长
								accountInfo.setSchool( userInfo.getString("sschool")); ;//手机号
								accountInfo.setEmail(userInfo.getString("semail")); ;//手机号
								accountInfo.setArea(userInfo.getString("sarea")); ;//手机号
								accountInfo.setAreaStr( userInfo.getString("saddress")); //手机号
								accountInfos.add(accountInfo);
							}
						
							start = start + jsonArray.length();
							
						} catch (Exception e) {
							logger.error("解析操作数据失败" + e.getMessage());
						} finally {
							mAdapter = new UserInfoPaginationAdapter(
									mContext, accountInfos);
							xListView.setAdapter(mAdapter);
							if (mAdapter.getCount() == 0) {
								mTipTv.setText(R.string.no_search_records);
							}
						}
					} else {// 更多操作

						JSONArray jsonArray = jsonObj.getJSONArray("userList");
						logger.info(jsonArray.length()+"");
			
						for (int j = 0; j < jsonArray.length(); j++) {
							AccountInfo accountInfo=new AccountInfo();
							
							JSONObject userInfo = (JSONObject) jsonArray.get(j);
							
							accountInfo.setAccount(userInfo.getString("suser"));
							accountInfo.setPassword(userInfo.getString("spwd"));
							accountInfo.setThumbUrl(userInfo.getString("simgPath")); ;//头像http地址
							accountInfo.setStaffTime(userInfo.getInt("ivTitle")); ;//等级
							accountInfo.setUserName(userInfo.getString("sname")); ;//用户名
							accountInfo.setLevel(userInfo.getInt("ivTimes")); ;//服务时长
							accountInfo.setStaffScore(userInfo.getInt("ivScore")); ;//服务质量
							accountInfo.setPhone(userInfo.getString("sphone"));  ;//手机号
							
							accountInfo.setSex(userInfo.getInt("ssex")); ;//服务时长
							accountInfo.setSchool( userInfo.getString("sschool")); ;//手机号
							accountInfo.setEmail(userInfo.getString("semail")); ;//手机号
							accountInfo.setArea(userInfo.getString("sarea")); ;//手机号
							accountInfo.setAreaStr( userInfo.getString("saddress")); //手机号
							mAdapter.addItem(accountInfo);
						}
						mAdapter.notifyDataSetChanged();
						start = start + jsonArray.length();
						if (jsonArray.length() == 0) {
							Toast.makeText(JoinUserListActivity.this, "没有更多内容了！",
									Toast.LENGTH_SHORT);
						}
					}
				} else if (CODE_FAIL == resultCode) {
					Toast.makeText(JoinUserListActivity.this, "数据加载失败！",
							Toast.LENGTH_SHORT).show();
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		private String PARAM_RESULT_CODE = "returnCode";
		private static final int CODE_SUCCESS = 0;
		private static final int CODE_NO_AUTH = 1;
		private static final int CODE_FAIL = 2;
	}
	
	@Override
	protected String getHeaderTitle() {
		// TODO Auto-generated method stub
		return getString(R.string.title_join_user_list);
	}
}
