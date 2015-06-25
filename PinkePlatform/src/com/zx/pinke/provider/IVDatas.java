package com.zx.pinke.provider;

import android.net.Uri;

public class IVDatas {
	public static final String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/mms";
	public static final String CONTENT_TYPE_ITEM = "vnd.anroid.cursor.item/mms";
	
	public static final String AUTHORITY = "com.xiaobai.provider";
	public static final Uri CONTENT_PUBLIC_SERVICE_URI = Uri.parse("content://" + AUTHORITY + "/public_service");
	public static final String DEFAULT_SORT_ORDER = "_id asc";
	public static final String PUBLIC_SERVICE = "public_service";//����
	public interface PublicServiceColumn{
		/**
		 * ��������
		 * <P> Type: INTEGER (long) </P>
		 */
		public static final String ID = "_id";
		
		/**
		 * ҵ������
		 */
		public static final String KEY = "key";
		/**
		 * ����
		 * <P> Type: Text </P>
		 */
		public static final String TITLE = "title";
		
		/**
		 * �����
		 * <P> Type: Text </P>
		 */
		public static final String CONTENT = "content";
		
		/**
		 * �����
		 *  <P> Type: TEXT </P>
		 */
		public static final String CATEGORY = "category";
		
		/**
		 * ״̬
		 * <P> Type: TEXT </P>
		 */
		public static final String STATE = "state";
		
		/**
		 * ��ص�
		 *  <P> Type: TEXT </P>
		 */
		public static final String PLACE = "place";
		
		/**
		 * ���ʼʱ��
		 * <P> Type: TEXT </P>
		 */
		public static final String START_DATE = "start_date";
		
		/**
		 * �����ʱ��
		 * <P> Type: TEXT </P>
		 */
		public static final String END_DATE = "end_date";
		/**
		 * ��������
		 * <P> Type: INTEGER (long) </P>
		 */
		public static final String JOIN_NUM = "join_num";
		
		/**
		 * ��������
		 * <P> Type: INTEGER (long) </P>
		 */
		public static final String NEED_NUM = "need_num";
		
		/**
		 * �ͼ��URL��ַ
		 * <P> Type: TEXT </P>
		 */
		public static final String ICON_URL = "icon_url";
		
		/**
		 * �ͼ��
		 *  <P> Type: BLOB </P>
		 */
		public static final String ICON = "icon";
		
		/**
		 * ��ϵ��
		 * <P> Type: TEXT </P>
		 */
		public static final String CONTACTS = "contacts";
		
		/**
		 * ������
		 * <P> Type: TEXT </P>
		 */
		public static final String LAUNCH = "launch";
		
		
		/**
		 * �����û�
		 * <P> Type: TEXT </P>
		 */
		public static final String USERLIST = "userlist";
		
		/**
		 * ��������Ϣ
		 * <P> Type: TEXT </P>
		 */
		public static final String LAUNCHERINFO = "launcherinfo";
		
	}
	
}
