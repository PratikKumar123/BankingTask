package loginSignup;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Register extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context=getServletContext();  
		String url = context.getInitParameter("url");
		String dname = context.getInitParameter("dname");
		String dpass = context.getInitParameter("dpass");
		
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String pass = request.getParameter("pwd");
		String cpass = request.getParameter("cpwd");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url,dname, dpass);

			PreparedStatement p = con.prepareStatement("insert into register(userName,userMail,phoneNo,pass,cpass) values(?,?,?,?,?)");

			p.setString(1, name);
			p.setString(2, email);
			p.setString(3, phone);
			p.setString(4, pass);
			p.setString(5, cpass);
			

			int i = p.executeUpdate();

			if (i == 1) {
				out.println("Registration  successfully!!");
				request.getRequestDispatcher("Login.java");
			}else {
				out.println("Registration  error!!");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}