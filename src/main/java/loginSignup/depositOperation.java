package loginSignup;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class depositOperation
 */
@WebServlet("/depositOperation")
public class depositOperation extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();
		PrintWriter out = response.getWriter();
		String url = context.getInitParameter("url");
		String dname = context.getInitParameter("dname");
		String dpass = context.getInitParameter("dpass");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, dname, dpass);

			String id1 = request.getParameter("uid");
			String accNo1 = request.getParameter("uacc");
			String depo1 = request.getParameter("udeposit");
			
			PreparedStatement p = con.prepareStatement("select accountNo from bankdetails where Id = ?");
			p.setString(1, id1);
			
			ResultSet r1 = p.executeQuery();
			Cookie ck[]=request.getCookies();  
			while (r1.next()) {
				if (accNo1.equals(r1.getString("accountNo"))) {
					
					PreparedStatement psd = con.prepareStatement("update bankdetails set balance = balance+? where id = ?");
					psd.setString(1, depo1);
					psd.setString(2, id1);
					int i = psd.executeUpdate();
					
					if(i==1){
						
						out.println("<h1>\n Balance deposited succesfully <h1>");
						out.println("Deposited balance of  "+ck[0].getValue()+" is > "+ depo1);
					}

				} else {
					out.println("Invalid Credentials");
				}
			}
		} catch (Exception e) {
			out.print(e);
		}
	}

}
