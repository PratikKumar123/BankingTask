package loginSignup;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Login extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext context = getServletContext();
		String url = context.getInitParameter("url");
		String dname = context.getInitParameter("dname");
		String dpass = context.getInitParameter("dpass");

		PrintWriter out = response.getWriter();
		
		String lname =request.getParameter("name");
		String email = request.getParameter("lemail");
		String pass = request.getParameter("lpwd");

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, dname, dpass);
			Cookie ck=new Cookie("uname",lname); 
		    response.addCookie(ck);
			Statement s = con.createStatement();
			ResultSet r = s.executeQuery("select userMail,pass from register");
			while (r.next()) {
				
				if (email.equalsIgnoreCase(r.getString("userMail")) && pass.equals(r.getString("pass")))
				{
					
					RequestDispatcher r1 = request.getRequestDispatcher("Welcome");
					r1.forward(request, response);
					break;
				}
			}
			out.println("Error !!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}