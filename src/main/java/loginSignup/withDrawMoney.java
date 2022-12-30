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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class withDrawMoney
 */
@WebServlet("/withDrawMoney")
public class withDrawMoney extends HttpServlet {

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
			String withdraw = request.getParameter("withd");
			
			PreparedStatement p = con.prepareStatement("select id, accountNo from bankdetails where id = ?");
			p.setString(1, id1);
			
			ResultSet r1 = p.executeQuery();
			
			while (r1.next()) {
				if (r1.getString("id").equals(id1)&&r1.getString("accountNo").equals(accNo1)) {
					
					PreparedStatement psd = con.prepareStatement("update bankdetails set balance = (balance - ?) where accountNo=?");
					psd.setString(1, withdraw);
					psd.setString(1, accNo1);
					int i = psd.executeUpdate();
					
					if(i==1){
						
						out.println("<h1>\n Balance debited succesfully <h1>");
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
