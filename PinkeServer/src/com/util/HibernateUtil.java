package com.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;

import com.bean.MessageBean;

public class HibernateUtil {

	private static SessionFactory sessionFactory;
	private Session session;
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void setSessionFactory(SessionFactory sessionFactory) {
		HibernateUtil.sessionFactory = sessionFactory;
	}

	/**
	 * 保存方法的封装
	 * @param <T>
	 * @param obj
	 *            要保存的对象
	 * @return 返回操作结果的MessageBean
	 */
	public <T> MessageBean saveObj(T obj) {
		MessageBean messageBean = new MessageBean();
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.save(obj);
			session.getTransaction().commit();
			messageBean.setMessageFlag(true);
			session.close();
		} catch (Exception e) {
			messageBean.setMessageFlag(false);
			messageBean.setReturnMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return messageBean;
	}

	/**
	 * 通过ID删除对象
	 *MessageBean
	 * @param <T>
	 * @param t_class 要删除对象的Class
	 * @param id 对象的ID
	 * @return 返回操作结果的MessageBean
	 */
	public static <T> MessageBean deleteObjById(Class<T> t_class, Serializable id) {
		MessageBean messageBean = new MessageBean();
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			T t = (T) session.get(t_class, id);
			session.delete(t);
			messageBean.setMessageFlag(true);
			session.close();
		} catch (Exception e) {
			messageBean.setMessageFlag(false);
			messageBean.setReturnMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return messageBean;
	}
	
	/**
	 * 通过执行hql语句删除对象
	 *MessageBean
	 * @param <T>
	 * @param hql 执行删除语句的hql语句
	 * @return 返回操作结果的MessageBean
	 */
	public static <T> MessageBean deleteObjByHql(String hql) {
		MessageBean messageBean = new MessageBean();
		Query query = null;
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			query =  session.createQuery(hql);
			int count = query.executeUpdate();
			session.getTransaction().commit();
			if(count>0)
				messageBean.setMessageFlag(true);
			session.close();
		} catch (Exception e) {
			messageBean.setMessageFlag(false);
			messageBean.setReturnMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return messageBean;
	}

	/**
	 * 通过ID得到要查找的对象
	 *T
	 * @param <T>
	 * @param t_class 要查找对象的Class
	 * @param id 对象的ID
	 * @return 返回得到的对象
	 */
	public  <T>T getObjById(Class<T> t_class,Serializable id){
		Session session = sessionFactory.openSession();
		T t = null;
		try {
			t = (T) session.get(t_class, id);
			this.session = session;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return t;
	}
	
	public void closeSession(){
		try {
			if(this.session!=null){
				this.session.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 通过hql来实现分页的查找
	 *List<T> 
	 * @param <T>
	 * @param hql 查找的hql语句
	 * @param start 分页的开始
	 * @param size 分页的大小
	 * @return 返回得到的集合
	 */
	public <T> List<T> getList(String hql,int start,int size) {
		Session session = sessionFactory.openSession();
		Query query = null;
		List<T> objList = null;
		try {
			query = session.createQuery(hql);
			query.setFirstResult(start);
			query.setMaxResults(size);
			objList = query.list();
			this.session = session;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return objList;
	}

	/**
	 * 通过hql语句得到要查找的集合
	 *List<T>
	 * @param <T>
	 * @param hql 查找的hql语句
	 * @return 返回得到的集合
	 */
	public static <T> List<T> getList(String hql) {
		//sessionFactory = (SessionFactory)SpringUtil.getBean("sessionFactory", LocalSessionFactoryBean.class);
		Session session = sessionFactory.openSession();
		Query query = null;
		List<T> objList = null;
		try {
			query = session.createQuery(hql);
			objList = query.list();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return objList;
	}
	
	/**
	 * 将查找得到的结果映射成Map<String,Object> 的形式返回
	 *List<Map<String,Object>> 
	 * @param hql 执行查询语句的hql
	 * @return 返回得到的集合
	 */
	public static List<Map<String,Object>> getListForMap(String hql) {
		Session session = sessionFactory.openSession();
		Query query = null;
		List<Map<String,Object>> objList = null;
		try {
			query = session.createQuery(hql);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			objList = query.list();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return objList;
	}
	
	/**
	 * 在Hibernate中执行sql语句得到分页查询的结果
	 *List<Map<String,Object>>
	 * @param sql 要查询的sql
	 * @param start sql分页查询的开始
	 * @param count sql分页查询的大小
	 * @return 返回得到的集合
	 */
	public static List<Map<String,Object>> sqlQuery(String sql, int start, int count) {
		Session session = sessionFactory.openSession();
		SQLQuery query = null;
		List<Map<String,Object>> objList = null;
		try {
			query = session.createSQLQuery(sql);
			query.setFirstResult(start);
			query.setMaxResults(count);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			objList = query.list();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return objList;
	}
	
	/**
	 * 通过sql语句查询
	 *List<Map<String,Object>>
	 * @param sql 执行查询的sql语句
	 * @return 返回得到的集合
	 */
	public static List<Map<String,Object>> sqlQuery(String sql) {
		Session session = sessionFactory.openSession();
		SQLQuery query = null;
		List<Map<String,Object>> objList = null;
		try {
			query = session.createSQLQuery(sql);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			objList = query.list();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return objList;
	}

	/**
	 * 执行Hibernate的更新操作
	 *MessageBean
	 * @param <T>
	 * @param hql 执行更新操作的HQL语句
	 * @param t 要更新数据封装的对象
	 * @return 返回操作结果的MessageBean
	 */
	public static <T> MessageBean updateObj(String hql,T t){
		MessageBean messageBean = new MessageBean();
		Session session = sessionFactory.openSession();
		Query query = null;
		try {
			session.beginTransaction();
			query = session.createQuery(hql);
			query.setProperties(t);
			query.executeUpdate();
			session.getTransaction().commit();
			messageBean.setMessageFlag(true);
			session.close();
		} catch (Exception e) {
			messageBean.setMessageFlag(false);
			messageBean.setReturnMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return messageBean;
	}
	
	/**
	 * 更新某个对象
	 *boolean
	 * @param <T>
	 * @param t 要更新的对象
	 * @return 返回操作结果的MessageBean
	 */
	public static <T> MessageBean updateObj(T t){
		MessageBean messageBean = new MessageBean();
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.update(t);
			session.getTransaction().commit();
			messageBean.setMessageFlag(true);
			session.close();
		} catch (Exception e) {
			messageBean.setMessageFlag(false);
			messageBean.setReturnMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return messageBean;
	}
	
	/**
	 * 通过SQL语句执行更新操作
	 *boolean
	 * @param <T>
	 * @param sql 执行更新操作的SQL语句
	 * @return 返回操作结果的MessageBean
	 */
	public static <T> MessageBean updateObj(String sql){
		MessageBean messageBean = new MessageBean();
		Session session = sessionFactory.openSession();
		SQLQuery query = null;
		try {
			session.beginTransaction();
			query = session.createSQLQuery(sql);
			int rows = query.executeUpdate();
			session.getTransaction().commit();
			if(rows>0){
				messageBean.setMessageFlag(true);
			}else{
				messageBean.setMessageFlag(false);
			}
			session.close();
		} catch (Exception e) {
			messageBean.setMessageFlag(false);
			messageBean.setReturnMessage(e.getMessage());
			throw new RuntimeException(e);
		}
		return messageBean;
	}
	
	/**
	 * 通过执行HQL语句得到数据的总条数
	 *Long
	 * @param <T>
	 * @param hql 要执行的HQL语句
	 * @return 返回得到的总条数
	 */
	public static <T> Long getCount(String hql){
		Session session = sessionFactory.openSession();
		Query query = null;
		Long count;
		try {
			query = session.createQuery(hql);
			count = (Long)query.uniqueResult();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return count;
	}
	
	/**
	 * 通过执行SQL语句得到数据的总条数
	 *Long
	 * @param <T>
	 * @param sql 要执行的SQL语句
	 * @return 返回得到的总条数
	 */
	public static <T> Long getCountBySql(String sql){
		Session session = sessionFactory.openSession();
		SQLQuery query = null;
		Long count;
		try {
			query = session.createSQLQuery(sql);
			count = new BigDecimal(query.uniqueResult().toString()).longValue();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return count;
	}
	
	/**
	 * 通过执行hql语句删除对象
	 *UserShare
	 * @param <T>
	 * @param hql 执行删除语句的hql语句
	 * @return 返回操作结果的MessageBean
	 */
	public static int deleteUserShareBySql(String sql) {
	
		Query query = null;
		Session session = sessionFactory.openSession();
		int count;
		try {
			session.beginTransaction();
			query =  session.createSQLQuery(sql);
			count = query.executeUpdate();
			session.getTransaction().commit();
				
			session.close();
			
		} catch (Exception e) {
			
			throw new RuntimeException(e);
		
		}
		return count;
	}
}
