package servertest;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DBUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SouYe extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String name = request.getParameter("name");
		 response.setContentType("text/html;charset=utf-8");

		 if(name.equals("name")){//http://localhost:8080/servertest/Dongtai?name=name
			    
				DBUtils dbUtils = new DBUtils();
				dbUtils.openConnect();
				JSONArray array=new JSONArray();
				JSONObject jsonObject=new JSONObject();
				
				ResultSet rs = dbUtils.getUser(3);
				//rs.getArray("id");
				
						if (rs != null){
							try {
								while (rs.next()) { // 遍历结果集
									String duid=rs.getString("id");	
									String dname=rs.getString("name");
									String durl=rs.getString("url");
									String dprice=rs.getString("price");
									jsonObject.put("id",duid);
									jsonObject.put("name",dname);
									jsonObject.put("url", durl);
									jsonObject.put("price", dprice);
									array.add(jsonObject);
								}
							} catch (SQLException e) {
							e.printStackTrace();
							}
							String jsonString=array.toString();
							try {
								response.getWriter().print(jsonString);
								// 将json数据传给客户端
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								response.getWriter().close(); // 关闭这个流，不然会发生错误的
							}
							}
						dbUtils.closeConnect();	
					}	else {
						return;
					}
				
				}
	
}
