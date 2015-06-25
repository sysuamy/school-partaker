package com.zx.pinke.activity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zx.pinke.R;
import com.zx.pinke.activity.base.BaseActivity;
import com.zx.pinke.bean.AccountInfo;
import com.zx.pinke.bean.PublicService;
import com.zx.pinke.util.AppConfig;
import com.zx.pinke.util.AsyncHttpQueryHandle;
import com.zx.pinke.util.HttpFileResult.ResultCode;
import com.zx.pinke.util.HttpJsonResult;

public class ActivityInfoDetailActivity extends BaseActivity {

	private PublicService mPublicService;

	private static final int TOKEN_JOIN = 1;
	private static final int TOKEN_QUIT = 2;
	private static final int TOKEN_UNPUBLISH = 3;
	private BackgroundQueryHandler mBackgroundQueryHandler;
	private ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_pulibc_service_detail);
		mPublicService = (PublicService) getIntent().getSerializableExtra("ps");
		if (mPublicService.getLaunch().equals(
				AccountInfo.getAccountInfo().getAccount())) {
			setContentView(R.layout.activity_activity_detail_my_launch);
		} else {
			setContentView(R.layout.activity_activity_detail_my_join);
		}
		mBackgroundQueryHandler = new BackgroundQueryHandler(this);
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMessage(getString(R.string.processing));
		mProgressDialog.setCancelable(true);
		mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_BACK:
					mBackgroundQueryHandler.cancelOperation(TOKEN_JOIN);
				}
				return false;
			}
		});

		initViews();
	}

	private TextView mTitleTv;
	private TextView mLaunch;
	private TextView mBeginDateTv;
	private TextView mEndDateTv;
	private TextView mCategoryTv;
	private TextView mPlaceTv;
	private TextView mContactsTv;
	private TextView mNeed;
	private TextView mJoinTv;
	private TextView mDetailTv;
	private TextView mSeeJoin;

	private Button mJoinBtn;

	private void initViews() {
		mTitleTv = (TextView) findViewById(R.id.title);
		mLaunch = (TextView) findViewById(R.id.launch);
		mSeeJoin = (TextView) findViewById(R.id.see_join);
		mBeginDateTv = (TextView) findViewById(R.id.begin_date);
		mEndDateTv = (TextView) findViewById(R.id.end_date);
		mCategoryTv = (TextView) findViewById(R.id.category);
		mPlaceTv = (TextView) findViewById(R.id.place);
		mJoinTv = (TextView) findViewById(R.id.join_num);
		mDetailTv = (TextView) findViewById(R.id.detail);
		mDetailTv.setMovementMethod(ScrollingMovementMethod.getInstance());

		mContactsTv = (TextView) findViewById(R.id.contacts);
		mNeed = (TextView) findViewById(R.id.need_num);
		mJoinBtn = (Button) findViewById(R.id.join);

		mTitleTv.setText(mPublicService.getTitle());
		mLaunch.setText(getString(R.string.launch, mPublicService.getLauncherInfo().getUserName()));
		mBeginDateTv.setText(getString(R.string.begin_time_at, mPublicService
				.getStartDate() == null ? "无" : mPublicService.getStartDate()));
		mEndDateTv.setText(getString(R.string.end_time_at, mPublicService
				.getEndDate() == null ? "无" : mPublicService.getEndDate()));
		mCategoryTv.setText(getString(R.string.category_at,
				mPublicService.getCategory()));
		mPlaceTv.setText(getString(R.string.place_at, mPublicService.getPlace()));
		if (mPublicService.getNeedNum() == 0) {
			mJoinTv.setText(getString(R.string.join_number,
					mPublicService.getJoinNum()));
		} else {
			mJoinTv.setText(getString(R.string.join_and_last_number,
					mPublicService.getJoinNum(), mPublicService.getNeedNum()
							- mPublicService.getJoinNum()));
		}
		mDetailTv
				.setText(Html.fromHtml(mPublicService.getContent() == null ? "暂无"
						: mPublicService.getContent()));

		mContactsTv.setText(getString(R.string.contacts_of,
				mPublicService.getContacts()));
		mNeed.setText(getString(R.string.need_number, mPublicService
				.getNeedNum() == 0 ? "不限" : mPublicService.getNeedNum()));

		if (mPublicService.getLaunch().equals(
				AccountInfo.getAccountInfo().getAccount())) {
			mJoinBtn.setOnClickListener(new UnPublishListener());
			mJoinBtn.setText(R.string.i_want_unpublish);
			mLaunch.setVisibility(View.GONE);
			mSeeJoin.setVisibility(View.VISIBLE);
		} else {
			boolean isJoin = false;
			for (AccountInfo user : mPublicService.getJoinUser()) {
				if (user.getAccount().equals(
						AccountInfo.getAccountInfo().getAccount())) {
					isJoin = true;
				}
			}
			if (isJoin) {
				mJoinBtn.setText(R.string.i_want_quit);
				mJoinBtn.setOnClickListener(new QuitListener());
			} else {
				mJoinBtn.setText(R.string.i_want_to_join);
				mJoinBtn.setOnClickListener(new JoinListener());
			}
			mLaunch.setVisibility(View.VISIBLE);
			mSeeJoin.setVisibility(View.GONE);
		}

		Date startTime=new Date(mPublicService.getStartDate());
		Date now=new Date(System.currentTimeMillis());
		if (startTime.before(now)) {
			mJoinBtn.setText(R.string.finish);
			mJoinBtn.setEnabled(false);
		}
		
		mSeeJoin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ActivityInfoDetailActivity.this,
						JoinUserListActivity.class);
				intent.putExtra("mid", mPublicService.getKey());
				startActivity(intent);
			}
		});
	}

	private class JoinListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			String key = mPublicService.getKey();

			try {
				mProgressDialog.show();
				URI uri = new URI(AppConfig.NetWork.IV.URL_SERVICE_JOIN);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("activeid", key);
				AccountInfo info = AccountInfo.getAccountInfo();
				params.put("suser", info.getAccount());
				String sessionId = info.getSessionId();
				System.out.println(sessionId + "," + key);
				mBackgroundQueryHandler.startPost(TOKEN_JOIN, params, uri,
						params, sessionId);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}

	}

	private class QuitListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			quitDlg();
		}

	}

	private class UnPublishListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			unPublishDlg();
		}

	}

	private static final int REQUEST_CODE_AUTO_LOGIN = 1;
	private boolean requestAutoLogined = false;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == REQUEST_CODE_AUTO_LOGIN) {
			requestAutoLogined = true;
			if (resultCode == RESULT_OK) {
				mJoinBtn.performClick();
			} else if (resultCode == RESULT_CANCELED) {
				if (mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
				}
			} else {
				if (mProgressDialog.isShowing()) {
					mProgressDialog.dismiss();
					Toast.makeText(this, R.string.fail_alter_account_info,
							Toast.LENGTH_LONG).show();
				}
			}
		}

	}

	private class BackgroundQueryHandler extends AsyncHttpQueryHandle {
		private Context mContext;

		public BackgroundQueryHandler(Context context) {
			super(context);
			mContext = context;
		}

		private boolean showProgress = true;

		@Override
		protected void onPostComplete(int token, Object cookie,
				HttpJsonResult result) {

			logger.info("ShareInfoDetailActivity:" + result.success());
			if (!result.success()) {
				if (result.getResultCode() == ResultCode.CODE_FAIL_NO_NETWORK) {
					Toast.makeText(mContext, R.string.alert_no_network,
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(mContext, R.string.alert_fail_network,
							Toast.LENGTH_LONG).show();
				}
			} else {

				if (token == TOKEN_JOIN) {
					JSONObject jsonObject = result.getJsonResult();
					try {
						String msg = (String) jsonObject.get("returnMsg");
						logger.info("ShareInfoDetailActivity:" + msg);
						Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
						int returnCode = jsonObject.getInt("returnCode");
						if (returnCode == 0) {
							setResult(RESULT_OK);
							mJoinBtn.setText(R.string.i_want_quit);
							mJoinBtn.setOnClickListener(new QuitListener());
							mPublicService.setJoinNum(mPublicService.getJoinNum()+1);
							if (mPublicService.getNeedNum() == 0) {
								
								mJoinTv.setText(getString(R.string.join_number,
										mPublicService.getJoinNum()));
							} else {
								mJoinTv.setText(getString(R.string.join_and_last_number,
										mPublicService.getJoinNum(), mPublicService.getNeedNum()
												- mPublicService.getJoinNum()));
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
						Toast.makeText(mContext, R.string.fail_login,
								Toast.LENGTH_LONG).show();
					}
				}
				if (token == TOKEN_QUIT) {
					JSONObject jsonObject = result.getJsonResult();
					try {
						String msg = (String) jsonObject.get("returnMsg");
						logger.info("ShareInfoDetailActivity:" + msg);
						Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
						int returnCode = jsonObject.getInt("returnCode");
						if (returnCode == 0) {
							setResult(RESULT_OK);
							mJoinBtn.setText(R.string.i_want_to_join);
							mJoinBtn.setOnClickListener(new JoinListener());
							mPublicService.setJoinNum(mPublicService.getJoinNum()-1);
							if (mPublicService.getNeedNum() == 0) {
								mJoinTv.setText(getString(R.string.join_number,
										mPublicService.getJoinNum()));
							} else {
								mJoinTv.setText(getString(R.string.join_and_last_number,
										mPublicService.getJoinNum(), mPublicService.getNeedNum()
												- mPublicService.getJoinNum()));
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
						Toast.makeText(mContext, R.string.fail_login,
								Toast.LENGTH_LONG).show();
					}
				}
				if (token == TOKEN_UNPUBLISH) {
					JSONObject jsonObject = result.getJsonResult();
					try {
						String msg = (String) jsonObject.get("returnMsg");
						logger.info("ShareInfoDetailActivity:" + msg);
						Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
						int returnCode = jsonObject.getInt("returnCode");
						if (returnCode == 0) {
							setResult(RESULT_OK);
							finish();
						}
					} catch (JSONException e) {
						e.printStackTrace();
						Toast.makeText(mContext, R.string.fail_login,
								Toast.LENGTH_LONG).show();
					}
				}
			}
			mProgressDialog.dismiss();
		}
	}

	@Override
	protected boolean onBackable() {
		return true;
	}

	@Override
	protected String getHeaderTitle() {
		// TODO Auto-generated method stub
		return getString(R.string.title_detail);
	}

	private void quitDlg() {
		new AlertDialog.Builder(this)
				.setTitle("温馨提示")
				.setMessage("确认退出活动吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String key = mPublicService.getKey();

						try {
							mProgressDialog.show();
							URI uri = new URI(
									AppConfig.NetWork.IV.URL_SERVICE_QUIT);
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("activeid", key);
							AccountInfo info = AccountInfo.getAccountInfo();
							params.put("suser", info.getAccount());
							String sessionId = info.getSessionId();
							System.out.println(sessionId + "," + key);
							mBackgroundQueryHandler.startPost(TOKEN_QUIT,
									params, uri, params, sessionId);
						} catch (URISyntaxException e) {
							e.printStackTrace();
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

	private void unPublishDlg() {
		new AlertDialog.Builder(this)
				.setTitle("温馨提示")
				.setMessage("确认取消活动吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String key = mPublicService.getKey();

						try {
							mProgressDialog.show();
							URI uri = new URI(
									AppConfig.NetWork.IV.URL_SERVICE_UNPUBLISH);
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("activeid", key);
							AccountInfo info = AccountInfo.getAccountInfo();
							params.put("suser", info.getAccount());
							String sessionId = info.getSessionId();
							System.out.println(sessionId + "," + key);
							mBackgroundQueryHandler.startPost(TOKEN_UNPUBLISH,
									params, uri, params, sessionId);
						} catch (URISyntaxException e) {
							e.printStackTrace();
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).show();
	}

}
