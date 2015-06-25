package com.zx.pinke.fragment;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zx.pinke.R;
import com.zx.pinke.activity.ActivityInfoDetailActivity;
import com.zx.pinke.activity.UserInfoActivity;
import com.zx.pinke.bean.AccountInfo;
import com.zx.pinke.bean.PublicService;
import com.zx.pinke.provider.IVDatabaseHelper;
import com.zx.pinke.provider.IVDatas;
import com.zx.pinke.provider.IVDatas.PublicServiceColumn;
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

public class ActivityInfoFragment extends ListFragment implements
		IXListViewListener {
	private static Log logger = LogFactory.getLog(ActivityInfoFragment.class);
	private XListView xListView;
	private TextView mTipTv;

	private QueryCondition mQueryCondition = new QueryCondition();
	private PublicServicePaginationAdapter mAdapter;
	private BackgroundQueryHandler mBackgroundQueryHandler;
	
	private static final int REQUEST_CODE_ACTIVITYINFO = 5;

	private static final int TOKEN_QUERY_CONTENT = 1;
	private static final String PARAM_START = "currentPage";
	private static final String PARAM_LIMIT = "pageSize";
	private int start = 0;
	private int limit = 10;
	
	private String category = "吃饭";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mBackgroundQueryHandler = new BackgroundQueryHandler(getActivity());
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		if (getArguments().getInt("category")==0) {
			category = "吃饭";
		}else if(getArguments().getInt("category")==1) {
			category = "运动";
		}else {
			category = "出游";
		}
		
		initViews();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_activity, container, false);
		
		return rootView;
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
		mAdapter = new PublicServicePaginationAdapter(getActivity(),
				getServiceFromLocal());
		xListView.setAdapter(mAdapter);
		xListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				logger.info("当前位置：ShareInfoActivity.itemClickListener"+position);
				PublicService publicService = (PublicService)mAdapter.getItem(position-1);
				Intent intent = new Intent(getActivity(),ActivityInfoDetailActivity.class);
				intent.putExtra("ps",publicService);
				startActivityForResult(intent, REQUEST_CODE_ACTIVITYINFO);
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

	private class PublicServicePaginationAdapter extends BaseAdapter {

		private Context mContext;
		private List<PublicService> mItems;
		private ImageDownloader imageDownloader;

		public PublicServicePaginationAdapter(Context context,
				List<PublicService> items) {
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
								ContentValues values = new ContentValues();
								values.put(PublicServiceColumn.ICON,
										baos.toByteArray());
								// logger.debug("url="+url+",写入数据库");
								getActivity().getContentResolver().update(
										IVDatas.CONTENT_PUBLIC_SERVICE_URI,
										values,
										PublicServiceColumn.ICON_URL + "='"
												+ url + "'", null);
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
			return mItems.get(position).getId();
		}

		public void addItem(PublicService c) {
			mItems.add(c);
		}

		@Override
		public View getView(final int position, View view, ViewGroup parent) {	
			
			if (view == null) {
				if (mItems.get(position).getLaunch().equals(AccountInfo.getAccountInfo().getAccount())) {
					view = getActivity().getLayoutInflater().inflate(
							R.layout.item_activity_my_launch, parent, false);
				}else {
					view = getActivity().getLayoutInflater().inflate(
							R.layout.item_activity_my_join, parent, false);
				}
				
			}
			PublicService publicService = mItems.get(position);
			byte[] icon = publicService.getIcon();
			ImageView imageView;
			if (icon == null) {
				// imageDownloader.download(publicService.getIconUrl(),(ImageView)view.findViewById(R.id.icon));
				imageView = (ImageView) view.findViewById(R.id.icon);

			} else {
				imageView = (ImageView) view.findViewById(R.id.icon);
				Bitmap iconBitmap = BitmapFactory.decodeByteArray(icon, 0,
						icon.length);
				imageView.setImageBitmap(iconBitmap);
			}
			
			imageView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					PublicService publicService = mItems.get(position);
					Intent intent = new Intent(getActivity(),UserInfoActivity.class);
					intent.putExtra("ai", publicService.getLauncherInfo());
					startActivity(intent);
				}
			});

			((TextView) view.findViewById(R.id.title)).setText(publicService
					.getTitle());

			logger.info(publicService.getStartDate());
			//SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
	
			((TextView) view.findViewById(R.id.begin_date)).setText(getString(
					R.string.begin_time_at,
					publicService.getStartDate() == null ? "无" : publicService
							.getStartDate()));
			((TextView) view.findViewById(R.id.end_date)).setText(getString(
					R.string.end_time_at,
					publicService.getEndDate() == null ? "无" : publicService
							.getEndDate()));

			((TextView) view.findViewById(R.id.category)).setText(getString(
					R.string.category_at, publicService.getCategory()));
			((TextView) view.findViewById(R.id.place)).setText(getString(
					R.string.place_at, publicService.getPlace()));
			if (publicService.getNeedNum()==0) {
				((TextView) view.findViewById(R.id.join_num)).setText(getString(R.string.join_number,publicService.getJoinNum()));
			}else {
				((TextView) view.findViewById(R.id.join_num)).setText(getString(R.string.join_and_last_number,publicService.getJoinNum(),publicService.getNeedNum()-publicService.getJoinNum()));
			}
			((TextView) view.findViewById(R.id.need_num)).setText(getString(R.string.need_number, publicService.getNeedNum()==0?"不限":publicService.getNeedNum()));
			((TextView) view.findViewById(R.id.contacts)).setText(getString(R.string.contacts_of,publicService.getContacts()));
			
			((TextView)view.findViewById(R.id.launch)).setText( publicService.getLauncherInfo().getUserName());
	
			return view;
		}

	}
	
	private static String[] PROJECTION = { PublicServiceColumn.ID,
			PublicServiceColumn.KEY, PublicServiceColumn.TITLE,
			PublicServiceColumn.PLACE, PublicServiceColumn.CATEGORY,
			PublicServiceColumn.START_DATE, PublicServiceColumn.END_DATE,
			PublicServiceColumn.JOIN_NUM, PublicServiceColumn.ICON_URL,
			PublicServiceColumn.ICON, PublicServiceColumn.CONTENT,
			PublicServiceColumn.CONTACTS, PublicServiceColumn.STATE,
			PublicServiceColumn.NEED_NUM,PublicServiceColumn.LAUNCH  ,
			PublicServiceColumn.USERLIST ,PublicServiceColumn.LAUNCHERINFO};
	private static final int ID_COLUMN = 0;
	private static final int KEY_COLUMN = 1;
	private static final int TITLE_COLUMN = 2;
	private static final int PLACE_COLUMN = 3;
	private static final int CATEGORY_COLUMN = 4;
	private static final int START_DATE_COLUMN = 5;
	private static final int END_DATE_COLUMN = 6;
	private static final int JOIN_NUM_COLUMN = 7;
	private static final int ICON_URL_COLUMN = 8;
	private static final int ICON_COLUMN = 9;
	private static final int CONTENT_COLUMN = 10;
	private static final int CONTACTS_COLUMN = 11;
	private static final int STATE_COLUMN = 12;
	private static final int NEED_NUM_COLUMN = 13;
	private static final int LAUNCH_COLUMN = 14;
	private static final int USERLIST_COLUMN = 15;
	private static final int LAUNCHER_INFO_COLUMN = 16;

	private List<PublicService> getServiceFromLocal() {

		List<PublicService> datas = new ArrayList<PublicService>();

		Uri uri = IVDatas.CONTENT_PUBLIC_SERVICE_URI;

		String orderBy = PublicServiceColumn.ID + " asc limit " + limit
				+ " offset " + 0;
		CursorLoader loader = new CursorLoader(getActivity(), uri, PROJECTION, IVDatas.PublicServiceColumn.CATEGORY+ "=?", new String[]{category}, orderBy);
		Cursor cursor = loader.loadInBackground();
		logger.debug(cursor);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				PublicService c = new PublicService();
				c.setId(cursor.getLong(ID_COLUMN));
				c.setKey(cursor.getString(KEY_COLUMN));
				c.setTitle(cursor.getString(TITLE_COLUMN));
				c.setPlace(cursor.getString(PLACE_COLUMN));
				c.setCategory(cursor.getString(CATEGORY_COLUMN));
				c.setStartDate(cursor.getString(START_DATE_COLUMN));
				c.setEndDate(cursor.getString(END_DATE_COLUMN));
				c.setJoinNum(cursor.getLong(JOIN_NUM_COLUMN));
				c.setIconUrl(cursor.getString(ICON_URL_COLUMN));
				c.setIcon(cursor.getBlob(ICON_COLUMN));
				c.setContent(cursor.getString(CONTENT_COLUMN));
				c.setContacts(cursor.getString(CONTACTS_COLUMN));
				c.setState(cursor.getString(STATE_COLUMN));
				c.setNeedNum(cursor.getLong(NEED_NUM_COLUMN));
				c.setLaunch(cursor.getString(LAUNCH_COLUMN));

				try {
					JSONArray userSet = new JSONArray(cursor.getString(USERLIST_COLUMN));
					List<AccountInfo> accountInfos=new ArrayList<AccountInfo>();
					
					for (int j = 0; j < userSet.length(); j++) {
						AccountInfo accountInfo=new AccountInfo();
						
						JSONObject userInfo = (JSONObject) userSet.get(j);
						
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
					c.setJoinUser(accountInfos);
					
					AccountInfo user=new AccountInfo();
					JSONObject userInfo = new JSONObject(cursor.getString(LAUNCHER_INFO_COLUMN));
					
					user.setAccount(userInfo.getString("suser"));
					user.setPassword(userInfo.getString("spwd"));
					user.setThumbUrl(userInfo.getString("simgPath")); ;//头像http地址
					user.setStaffTime(userInfo.getInt("ivTitle")); ;//等级
					user.setUserName(userInfo.getString("sname")); ;//用户名
					user.setLevel(userInfo.getInt("ivTimes")); ;//服务时长
					user.setStaffScore(userInfo.getInt("ivScore")); ;//服务质量
					user.setPhone(userInfo.getString("sphone"));  ;//手机号
					
					user.setSex(userInfo.getInt("ssex")); ;//服务时长
					user.setSchool( userInfo.getString("sschool")); ;//手机号
					user.setEmail(userInfo.getString("semail")); ;//手机号
					user.setArea(userInfo.getString("sarea")); ;//手机号
					user.setAreaStr( userInfo.getString("saddress")); //手机号
					c.setLauncherInfo(user);
				
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				datas.add(c);
			}
		}
		return datas;
	}

	@Override
	public void onRefresh() {
		logger.info("onRefresh开始刷新....");
		try {
			URI uri = new URI(AppConfig.NetWork.IV.URL_PUBLIC_SERVICE);
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

		logger.info("onLoadMore开始更多....");
		try {
			URI uri = new URI(AppConfig.NetWork.IV.URL_PUBLIC_SERVICE);
			mQueryCondition.updateCondition(PARAM_START, start);
			mTipTv.setText(R.string.loading_and_waiting);
			mBackgroundQueryHandler.startPost(TOKEN_QUERY_CONTENT, false, uri,
					mQueryCondition.getQueryParams());
		} catch (Exception e) {
			xListView.stopLoadMore();
		}

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
			//myactive 的值有"myjion","mylaunch","","null"
			params.put("myActive", "");
			//activeweek的值有"1"，"2"，"3"，"null"，""
			params.put("active_week", "4");//不显示当前时间以前的活动
			//regionid的值有 "","null",place
			params.put("regionId", "");
			params.put("category", category);
			
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
				if (((PublicServicePaginationAdapter) ((HeaderViewListAdapter) xListView
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
						JSONArray jsonArray = jsonObj.getJSONArray(PARAM_ROWS);

						IVDatabaseHelper helper = IVDatabaseHelper
								.getInstance(mContext);
						SQLiteDatabase db = helper.getWritableDatabase();
						db.beginTransaction();
						try {
							db.delete(IVDatas.PUBLIC_SERVICE,IVDatas.PublicServiceColumn.CATEGORY+ "=?", new String[]{category});

							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject activity = (JSONObject) jsonArray
										.get(i);
								String key = activity.getString("mid");
								String title = activity.getString("mtitle");
								String category = activity
										.getString("mcategory");
								String startDate = activity
										.getString("mstartDateStr");
								String endDate = activity.getString("mendDateStr");
								String contacts = activity
										.getString("mcontacts");
								String content = activity
										.getString("mcontent");
								String place = activity
										.getString("mplaceStr");
								String iconUrl = activity.getString("miconUrl");
								int joinNum = activity.getInt("mjoinNum");
								int needNum = activity.getInt("mneedNum");
								String state = activity.getString("mstate");
								String launch = activity.getString("mlaunch");
								String userList  = activity.getJSONArray("userSet").toString();
								
								String userInfo  = activity.getJSONObject("launcherInfo").toString();
								
								ContentValues values = new ContentValues();
								values.put(IVDatas.PublicServiceColumn.KEY, key);
								values.put(IVDatas.PublicServiceColumn.TITLE,
										title);
								values.put(IVDatas.PublicServiceColumn.PLACE,
										place);
								values.put(
										IVDatas.PublicServiceColumn.JOIN_NUM,
										joinNum);
								values.put(
										IVDatas.PublicServiceColumn.CATEGORY,
										category);
								values.put(IVDatas.PublicServiceColumn.CONTENT,
										content);
								values.put(
										IVDatas.PublicServiceColumn.START_DATE,
										startDate);
								values.put(
										IVDatas.PublicServiceColumn.END_DATE,
										endDate);
								values.put(
										IVDatas.PublicServiceColumn.ICON_URL,
										iconUrl);
								values.put(
										IVDatas.PublicServiceColumn.CONTACTS,
										contacts);
								values.put(IVDatas.PublicServiceColumn.STATE,
										state);
								values.put(
										IVDatas.PublicServiceColumn.NEED_NUM,
										needNum);
								values.put(
										IVDatas.PublicServiceColumn.LAUNCH,
										launch);
								values.put(
										IVDatas.PublicServiceColumn.USERLIST,
										userList);
								values.put(
										IVDatas.PublicServiceColumn.LAUNCHERINFO,
										userInfo);
								
								long count = db.insert(IVDatas.PUBLIC_SERVICE,
										null, values);
								logger.info("插入条数：" + count);
							}
							start = start + jsonArray.length();
							db.setTransactionSuccessful();
						} catch (Exception e) {
							logger.error("解析操作数据失败" + e.getMessage());
						} finally {
							db.endTransaction();
							mAdapter = new PublicServicePaginationAdapter(
									mContext, getServiceFromLocal());
							xListView.setAdapter(mAdapter);
							if (mAdapter.getCount() == 0) {
								mTipTv.setText(R.string.no_search_records);
							}
						}
					} else {// 更多操作

						JSONArray jsonArray = jsonObj.getJSONArray(PARAM_ROWS);
						logger.info(jsonArray.length()+"");
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject activity = (JSONObject) jsonArray.get(i);

							PublicService c = new PublicService();
							c.setKey(activity.getString("mid"));
							c.setTitle(activity.getString("mtitle"));
							c.setCategory(activity.getString("mcategory"));
							c.setStartDate(activity.getString("mstartDateStr"));
							c.setEndDate(activity.getString("mendDateStr"));
							c.setContacts(activity.getString("mcontacts"));
							c.setContent(activity.getString("mcontent"));
							c.setPlace(activity.getString("mplaceStr"));
							c.setIconUrl(activity.getString("miconUrl"));
							c.setJoinNum(activity.getInt("mjoinNum"));
							c.setState(activity.getString("mstate"));
							c.setNeedNum(activity.getInt("mneedNum"));
							c.setLaunch(activity.getString("mlaunch"));
							
							JSONArray userSet = activity.getJSONArray("userSet");
							
							List<AccountInfo> accountInfos=new ArrayList<AccountInfo>();
							
							for (int j = 0; j < userSet.length(); j++) {
								AccountInfo accountInfo=new AccountInfo();
								
								JSONObject userInfo = (JSONObject) userSet.get(i);
								
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
							c.setJoinUser(accountInfos);
							
							AccountInfo user=new AccountInfo();
							JSONObject userInfo =activity.getJSONObject("launcherInfo");
							
							user.setAccount(userInfo.getString("suser"));
							user.setPassword(userInfo.getString("spwd"));
							user.setThumbUrl(userInfo.getString("simgPath")); ;//头像http地址
							user.setStaffTime(userInfo.getInt("ivTitle")); ;//等级
							user.setUserName(userInfo.getString("sname")); ;//用户名
							user.setLevel(userInfo.getInt("ivTimes")); ;//服务时长
							user.setStaffScore(userInfo.getInt("ivScore")); ;//服务质量
							user.setPhone(userInfo.getString("sphone"));  ;//手机号
							
							user.setSex(userInfo.getInt("ssex")); ;//服务时长
							user.setSchool( userInfo.getString("sschool")); ;//手机号
							user.setEmail(userInfo.getString("semail")); ;//手机号
							user.setArea(userInfo.getString("sarea")); ;//手机号
							user.setAreaStr( userInfo.getString("saddress")); //手机号
							c.setLauncherInfo(user);
							
							mAdapter.addItem(c);
						}
						mAdapter.notifyDataSetChanged();
						start = start + jsonArray.length();
						if (jsonArray.length() == 0) {
							Toast.makeText(getActivity(), "没有更多内容了！",
									Toast.LENGTH_SHORT);
						}
					}
				} else if (CODE_FAIL == resultCode) {
					Toast.makeText(getActivity(), "数据加载失败！",
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_CODE_ACTIVITYINFO){
			if(resultCode == FragmentActivity.RESULT_OK){
				onRefresh();
			}
		}
	}
}
