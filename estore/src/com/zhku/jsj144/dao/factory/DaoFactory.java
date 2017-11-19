package com.zhku.jsj144.dao.factory;

import java.util.ResourceBundle;

/*
 * dao工厂
 */
public class DaoFactory {

	private static DaoFactory factory=new DaoFactory();
	private DaoFactory(){}
	//返回工厂
	public static DaoFactory getFactory(){
		return factory;//确保工厂只要一个
	} 
	//返回Dao接口对应的实现类的实例对象
	public static <T> T newInstance(Class<T> t){
		//举例：UserDao=com.zhku.jsj144.dao.impl.UserDaoImpl
		
		String simpleName = t.getSimpleName();
		String className = ResourceBundle.getBundle("dao").getString(simpleName);
		try {
			Class<?> clazz = Class.forName(className);
			
			return (T)clazz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
