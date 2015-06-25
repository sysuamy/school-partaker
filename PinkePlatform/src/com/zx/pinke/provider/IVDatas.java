package com.zx.pinke.provider;

import android.net.Uri;

public class IVDatas {
	public static final String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/mms";
	public static final String CONTENT_TYPE_ITEM = "vnd.anroid.cursor.item/mms";
	
	public static final String AUTHORITY = "com.xiaobai.provider";
	public static final Uri CONTENT_PUBLIC_SERVICE_URI = Uri.parse("content://" + AUTHORITY + "/public_service");
	public static final String DEFAULT_SORT_ORDER = "_id asc";
	public static final String PUBLIC_SERVICE = "public_service";//公益活动
	public interface PublicServiceColumn{
		/**
		 * 本地主键
		 * <P> Type: INTEGER (long) </P>
		 */
		public static final String ID = "_id";
		
		/**
		 * 业务主键
		 */
		public static final String KEY = "key";
		/**
		 * 标题
		 * <P> Type: Text </P>
		 */
		public static final String TITLE = "title";
		
		/**
		 * 活动详情
		 * <P> Type: Text </P>
		 */
		public static final String CONTENT = "content";
		
		/**
		 * 活动分类
		 *  <P> Type: TEXT </P>
		 */
		public static final String CATEGORY = "category";
		
		/**
		 * 状态
		 * <P> Type: TEXT </P>
		 */
		public static final String STATE = "state";
		
		/**
		 * 活动地点
		 *  <P> Type: TEXT </P>
		 */
		public static final String PLACE = "place";
		
		/**
		 * 活动开始时间
		 * <P> Type: TEXT </P>
		 */
		public static final String START_DATE = "start_date";
		
		/**
		 * 活动结束时间
		 * <P> Type: TEXT </P>
		 */
		public static final String END_DATE = "end_date";
		/**
		 * 报名人数
		 * <P> Type: INTEGER (long) </P>
		 */
		public static final String JOIN_NUM = "join_num";
		
		/**
		 * 需求人数
		 * <P> Type: INTEGER (long) </P>
		 */
		public static final String NEED_NUM = "need_num";
		
		/**
		 * 活动图标URL地址
		 * <P> Type: TEXT </P>
		 */
		public static final String ICON_URL = "icon_url";
		
		/**
		 * 活动图标
		 *  <P> Type: BLOB </P>
		 */
		public static final String ICON = "icon";
		
		/**
		 * 联系人
		 * <P> Type: TEXT </P>
		 */
		public static final String CONTACTS = "contacts";
		
		/**
		 * 发起人
		 * <P> Type: TEXT </P>
		 */
		public static final String LAUNCH = "launch";
		
		
		/**
		 * 参与用户
		 * <P> Type: TEXT </P>
		 */
		public static final String USERLIST = "userlist";
		
		/**
		 * 发起者信息
		 * <P> Type: TEXT </P>
		 */
		public static final String LAUNCHERINFO = "launcherinfo";
		
	}
	
}
