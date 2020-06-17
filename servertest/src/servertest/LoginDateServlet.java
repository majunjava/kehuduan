package servertest;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;
import db.DBUtils;
import model.BaseBean;
import model.UserBean;
import net.sf.json.JSONObject;


public class LoginDateServlet extends HttpServlet {


	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		System.out.println("request--->"+request.getRequestURL()+"===="+request.getParameterMap().toString());
		String username = request.getParameter("username"); // ��ȡ�ͻ��˴������Ĳ���
		String password = request.getParameter("password");//http://localhost:8080/servertest/LoginDateServlet?username=aa&password=123
		String condition = request.getParameter("condition");
		response.setContentType("text/html;charset=utf-8");
		if (username == null || username.equals("") || password == null || password.equals("")) {
			System.out.println("�û���������Ϊ��");
			return;
		} // �������ݿ�
		if (condition.equals("login")) {
			DBUtils dbUtils = new DBUtils();
			dbUtils.openConnect();
			JSONObject jsonObject=new JSONObject();
			if (dbUtils.isExistInDB(username, password)) {
				jsonObject.put("con","ok");
				jsonObject.put("id","1");
				jsonObject.put("name",username);
				
				
			}
			else {
				jsonObject.put("con","no");
			}
			
			
			String jsonString=jsonObject.toString();
			response.getWriter().print(jsonString);
			
			response.getWriter().close();
			dbUtils.closeConnect();
			return;
		}
		else {
	
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect();
		// �����ݿ�����
		BaseBean data = new BaseBean(); // ������󣬻ش����ͻ��˵�json����
		UserBean userBean = new UserBean(); // user�Ķ���
		
		if (dbUtils.isExistInDB(username, password)) {
			// �ж��˺��Ƿ����
			data.setCode(-1);
			data.setData(userBean);
			data.setMsg("���˺��Ѵ���");
		} else if (!dbUtils.insertDataToDB(username, password)) {
			// ע��ɹ�
			data.setCode(0);
			data.setMsg("ע��ɹ�!!");
			ResultSet rs = dbUtils.getUser(1);
			int id = -1;
			if (rs != null) {
				try {
					while (rs.next()) {
						if (rs.getString("user_name").equals(username) 
								&& rs.getString("user_pwd").equals(password)) {
							id = rs.getInt("user_id");
						}
					}
					userBean.setId(id);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			userBean.setUsername(username);
			userBean.setPassword(password);
			data.setData(userBean);
		} else {
			// ע�᲻�ɹ����������û��ϸ�֣�����Ϊ���ݿ����
			data.setCode(500);
			data.setData(userBean);
			data.setMsg("���ݿ����");
		}
		Gson gson = new Gson();
		String json = gson.toJson(data);
		// ������ת����json�ַ���
		try {
			response.getWriter().println(json);
			// ��json���ݴ����ͻ���
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close(); // �ر����������Ȼ�ᷢ�������
		}
		dbUtils.closeConnect(); // �ر����ݿ�����}
	}

	}
	
}
