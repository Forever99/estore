package com.zhku.jsj144.utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

//把得到连接及事务有关的方法写到此类中
/*
 *  ThreadLocal类【线程本地类】的使用：确保同一个线程使用的始终是同一个Connection对象
 *  
 *  ThreadLocal类:  内部是维护了 一个 map , 这个map 的key 始终 都是 当前 的线程 
 *  	key:当前线程    
 * 	    value:connection对象
 * 为什么使用这个类：
 * 因为进行事务处理时，要开启事务，提交事务，回滚事务
 *       而因为操作数据库时，是从连接池中获取到的连接：connection，
 *       一般的事务处理，我们无法确定我们拿到的是不是同一个connection，如果是不同的，我们的事务管理就会失败；
 *       所以为了确保拿到的是同一个connection，我们需要处理connection的唯一性问题？
 *       而ThreadLocal类就解决了这个问题
 *       
 *--ThreadLocal则从另一个角度来解决多线程的并发访问。
 *--ThreadLocal会为每一个线程提供一个独立的变量副本，从而隔离了多个线程对数据的访问冲突。
 *--因为每一个线程都拥有自己的变量副本，从而也就没有必要对该变量进行同步了。
 *--ThreadLocal提供了线程安全的共享对象，在编写多线程代码时，可以把不安全的变量封装进ThreadLocal。
 */
public class TransactionUtil {
	
	// 内部是维护了 一个 map , 这个map 的key 始终 都是 当前 的线程 
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	
	private static DataSource ds = JDBCUtils.getDataSource();
	
	public static DataSource getDataSource(){
		return ds;
	}
	
	//  这里, 获得一个 connection  对象 
	public static Connection getConnection(){
		try {
			Connection conn = tl.get();
			if(conn==null){
				//从数据连接池 中 取 一个连接 出来 
				conn = ds.getConnection();
				
				//将 取出来  connection 放到 tl中去
				tl.set(conn);
			}
			return conn;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	// 开启 事务  
	// 结果 就是 返回了 一个 connection对象, 并且 将 返回的 connection放到了 threadlocal中 , 
	public static void startTransaction(){
		try {
			Connection conn = tl.get();
			if(conn==null){
				conn = getConnection();
//				tl.set(conn);
			}
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public static void rollback(){
		try {
			Connection conn = tl.get();
			if(conn==null){
				conn = getConnection();
//				tl.set(conn);
			}
			conn.rollback();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public static void commit(){
		try {
			Connection conn = tl.get();
			if(conn==null){
				conn = getConnection();
//				tl.set(conn);
			}
			conn.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public static void relase(){
		try {
			Connection conn = tl.get();
			if(conn!=null){
				conn.close();
				tl.remove();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
