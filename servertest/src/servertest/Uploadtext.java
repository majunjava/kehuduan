package servertest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DBUtils;
import net.sf.json.JSONObject;

public class Uploadtext extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name"); 
		String url = request.getParameter("url"); 
		
		String []namestr;
		namestr=url.split("/");
		int i=namestr.length;
		System.out.println(namestr[i-1]);
		
		String inf = request.getParameter("inf");
		 String addString=request.getParameter("add");
		 String pString=request.getParameter("price");
		 String cString=request.getParameter("con");
		 response.setContentType("text/html;charset=utf-8");
		 DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect();
		if(!dbUtils.insertDatainf(name, namestr[i-1],Integer.parseInt(cString),inf,addString,Float.parseFloat(pString))){
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("result","ok");
				
				response.getWriter().print(jsonObject.toString());
		
				response.getWriter().close();
				
			}
			else {
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("result","no");
				
				response.getWriter().print(jsonObject.toString());
		
				response.getWriter().close();
			}
		 
		 
		 
			dbUtils.closeConnect();
	    }
	   
}
