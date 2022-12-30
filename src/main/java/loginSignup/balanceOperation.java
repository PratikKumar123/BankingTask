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
 * Servlet implementation class balanceOperation
 */
@WebServlet("/balanceOperation")
public class balanceOperation extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext context = getServletContext();
		PrintWriter out = response.getWriter();
		String url = context.getInitParameter("url");
		String dname = context.getInitParameter("dname");
		String dpass = context.getInitParameter("dpass");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, dname, dpass);

			String id = request.getParameter("uid");
			String accNo = request.getParameter("uacc");
			
			PreparedStatement p = con.prepareStatement("select accountNo from bankdetails where Id = ?");
			p.setString(1, id);
			
			ResultSet r1 = p.executeQuery();
			
			while (r1.next()) {
				if (accNo.equals(r1.getString("accountNo"))) {
					PreparedStatement psd = con.prepareStatement("select balance from bankdetails where id= ?");
					psd.setString(1, id);
					
					ResultSet rs1 = psd.executeQuery();
						while (rs1.next()) {
							out.println("\nCURRENT BALANCE IS" + rs1.getInt(1));
						}
				} else {
					out.println("Invalid Credentials !!!! ....");
				}
			}
		} catch (Exception e) {
			out.print(e);
		}
	}

}
