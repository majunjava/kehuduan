package servertest;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DBUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class goodsinf  extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String name = request.getParameter("name");
		 response.setContentType("text/html;charset=utf-8");

		//http://localhost:8080/servertest/Dongtai?name=name
			    
			DBUtils dbUtils = new DBUtils();
			dbUtils.openConnect();
			JSONArray array=new JSONArray();
			JSONObject jsonObject=new JSONObject();
				
			ResultSet rs = dbUtils.getUser(3);
				//rs.getArray("id");
				
				if (rs != null){
					try {
						while (rs.next()) { // 遍历结果集
							
							if(rs.getString("id").equals(name)){
								String dinf=rs.getString("information");
								jsonObject.put("inf", dinf);
								String dprice=rs.getString("price");
								jsonObject.put("price", dprice);
								break;
								
							}
							else {
								jsonObject.put("inf", "没有信息。");
								jsonObject.put("price", "null");
							}
					
								}
							} catch (SQLException e) {
							e.printStackTrace();
							}
							try {
								response.getWriter().print(jsonObject);
								// 将json数据传给客户端
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								response.getWriter().close(); // 关闭这个流，不然会发生错误的
							}
							}
						dbUtils.closeConnect();	
					
				
				}
	
}