package com.zhku.jsj144.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.zhku.jsj144.domain.Product;
import com.zhku.jsj144.service.ProductService;
import com.zhku.jsj144.utils.PicUtils;
/*
 * 添加商品
 * 1、上传图片
 * 2、封装数据
 */
public class AddProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DiskFileItemFactory factory=new DiskFileItemFactory();//工厂
		//此处错误
//		factory.setRepository(new File(request.getSession().getServletContext()+"/temp"));//临时缓冲区
		factory.setRepository(new File(request.getSession().getServletContext().getRealPath("/temp")));//临时缓冲区
		factory.setSizeThreshold(1024*1024);//1m的临时缓冲区大小
		
		ServletFileUpload parse=new ServletFileUpload(factory);//解析器
		parse.setSizeMax(1024*1024*5);//上传文件大小限制在5m以内
		parse.setHeaderEncoding("utf-8");//防止中文文件名乱码
		
		Product product=new Product();//商品
		try {
			List<FileItem> list = parse.parseRequest(request);
			for (FileItem fileItem : list) {
				if(fileItem.isFormField()){//是表单
					String fieldName = fileItem.getFieldName();//名称
					String value = fileItem.getString("utf-8");//值
					//防止乱码发生
					BeanUtils.setProperty(product, fieldName, value);//封装值到product对象
				}
				else{//是文件
					String name = fileItem.getName();//文件名
					//C:\Users\Administrator\Pictures\1.jpg
					//统一处理为：1.jpg
					int lastIndexOf = name.lastIndexOf("\\");
					if(lastIndexOf!=-1){
//						name=name.substring(lastIndexOf);//处理文件名  错误
						name=name.substring(lastIndexOf+1);//处理文件名
					}
					
					/*
					 * 1.对文件名进行处理
					 *   生成唯一文件名
					 * 2.生成随机存放文件夹，避免读取速度过慢
					 * 3.不能将文件放在WEB_INF目录下，这样会导致商城使用时，无法访问图片
					 */
					// 路径： D:/myeclipse_workspace/estore/images
					String realPath=request.getSession().getServletContext().getRealPath("/images");
					
					// 路径： /1/2 
					String randomDir=generateRandomDir(name);//生成随机文件夹
					
					// 路径：/images/1/2
					String imageurl="/images"+randomDir;
					
					// 路径： D:/myeclipse_workspace/estore/images/1/2
					String newPath=realPath+randomDir;
					File f=new File(newPath);
					if(!f.exists()){
						f.mkdirs();//创建文件夹
					}
					
					String uuidName=generateName(name);//生成唯一文件名    xxx.jpg
					
					InputStream in = fileItem.getInputStream();//获得文件读取流
					//：D:/myeclipse_workspace/estore/images/1/2  /  xxx.jpg
					File file=new File(newPath,uuidName);//文件
					OutputStream out=new FileOutputStream(file);//文件输出流
					byte[] buf=new byte[1024];
					int len=0;
					while((len=in.read(buf))!=-1){
						out.write(buf, 0, len);//写文件
					}
					in.close();
					out.close();//关闭流
					fileItem.delete();//删除临时文件
					//：D:/myeclipse_workspace/estore /images/1/2+  "/"  +xxx.jpg  失败
					//应该是   /images/1/2 +  "/"  +   xxx.jpg  成功
					product.setImageurl(imageurl+"/"+uuidName);//存放图片位置    
					
					//文件实际存放路径
					PicUtils util=new PicUtils(file.getCanonicalPath());
					util.resize(200, 200);//设置高度和宽度
					//在同一目录下生成一个小图
				}
			}
			ProductService service=new ProductService();
			service.insertProduct(product);//添加商品
			request.setAttribute("msg", "添加商品成功");
			request.getRequestDispatcher("/message2.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//生成唯一文件名
	private String generateName(String name) {
		String uuid = UUID.randomUUID().toString();
		int abs = Math.abs(uuid.hashCode());
		name="product_"+abs+name;
		return name;
	}

	
	
	//生成随机文件夹
	private String generateRandomDir(String name) {
		int hashcode=name.hashCode();
		int first=hashcode&(0xf);//一级目录
		int second=(hashcode>>4)&(0xf);//二级目录
		String randomDir="/"+first+"/"+second;
		return randomDir;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);

	}

}
