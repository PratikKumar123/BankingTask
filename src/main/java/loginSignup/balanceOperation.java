package loginSignup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
import javax.servlet.http.HttpSession;

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
		response.setContentType("text/html"); 
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, dname, dpass);

			String id = request.getParameter("uid");
			String accNo = request.getParameter("uacc");
			HttpSession session=request.getSession(false);
			String name=(String)session.getAttribute("name");
			PreparedStatement p = con.prepareStatement("select accountNo ,id  from bankdetails where Id = ?");
			p.setString(1, id);
			ResultSet r1 = p.executeQuery();
			
			while (r1.next()) {
				if (accNo.equals(r1.getString("accountNo")) && id.equals(r1.getString("id"))) {
					PreparedStatement psd = con.prepareStatement("select balance from bankdetails where id= ?");
					psd.setString(1, id);
					ResultSet rs1 = psd.executeQuery();
						while (rs1.next()) {
							File file = new File("C:/new_file/balance.txt");
							FileWriter fw = new FileWriter(file,true);
							
							out.println("<h2>Name  is ="+name+"</h2>");
							out.println("<h2>\nCURRENT BALANCE IS = " + rs1.getInt(1)+"</h2>");
							out.println("<h2>\nAccount no  IS = " +accNo+"</h2>");
							out.println("<hr>");
							
							fw.write("\nName  is ="+name);
							fw.write("\nCURRENT BALANCE IS = " + rs1.getInt(1));
							fw.write("\nAccount no  IS = " +accNo);
							
							fw.write("\n");
							fw.write("----------------------------------------------------");
							fw.write("\n");
							fw.flush();
							fw.close();
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
