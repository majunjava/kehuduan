package servertest;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.Base64Util;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import java.util.Base64;
import java.util.Base64.Decoder;
import com.sun.image.codec.jpeg.*;//sun公司仅提供了jpg图片文件的编码api

import javax.imageio.stream.*;

import java.awt.*;

import java.awt.image.BufferedImage;
public class ContentServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String name = request.getParameter("name");
		 
		 System.out.println(name);
	        if(name!=null){//http://localhost:8080/servertest/ContentServlet?name=name
	        	response.setContentType("image/jpeg"); //设置显示内容类型
	            InputStream in = getServletContext().getResourceAsStream("/WEB-INF/pic/"+name);//获得输入流
               
	            OutputStream out = response.getOutputStream();//取得输出流
	            writeBytes(in,out);//读取文件并显示在浏览器
	        }
	        else {
	        	System.out.println("llll");
	        }
	    }
	    //writeBytes()构造方法
	    private void writeBytes(InputStream in, OutputStream out) throws IOException {
	        byte[] buffer= new byte[1024];
	        int length = -1;
	        while ((length = in.read(buffer))!=-1){
	            out.write(buffer,0,length);

	        }
	        in.close();
	        out.close();
	    }
}
