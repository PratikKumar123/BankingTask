package loginSignup;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


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
			Statement s = con.createStatement();
			ResultSet r = s.executeQuery("select userMail,pass from register");
			while (r.next()) {
				
				if (email.equalsIgnoreCase(r.getString("userMail")) && pass.equals(r.getString("pass")))
				{
					HttpSession session=request.getSession();  
			        session.setAttribute("name",lname);  
					RequestDispatcher r1 = request.getRequestDispatcher("Welcome");
					r1.forward(request, response);
					break;
				}
			
			}
			out.println("<a class='btn' href='login.html'>Error while login in < please Go back</a>");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}