package servertest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UploadFile extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getHeader("img");
		
		String []namestr;
		namestr=name.split("/");
		int i=namestr.length;
		
		InputStream is = request.getInputStream();
		 byte[] buffer = new byte[1024];  
//		 String file="D://cover.jpg";
		 String file="D://ProgramFiles//java//eclipse//servertest//WebContent//WEB-INF//pic//"+namestr[i-1];
		 FileOutputStream out = new FileOutputStream(file);  
		 int read;
	        while ((read = is.read(buffer)) > 0) {
	            out.write(buffer, 0, read);
	        }
		 is.close();
		 out.close();
		 
		 
	}
	}