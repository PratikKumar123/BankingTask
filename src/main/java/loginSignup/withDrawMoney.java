package loginSignup;

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
 * Servlet implementation class withDrawMoney
 */
@WebServlet("/withDrawMoney")
public class withDrawMoney extends HttpServlet {

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
			HttpSession session=request.getSession(false);
			String id1 = request.getParameter("uid");
			String accNo1 = request.getParameter("uacc");
			String withdraw = request.getParameter("withd");
			response.setContentType("text/html");

			PreparedStatement p = con.prepareStatement("select accountNo from bankdetails where Id = ?");
			p.setString(1, id1);
			response.setContentType("text/html");
			ResultSet r1 = p.executeQuery();
			String name = (String) session.getAttribute("name");
			while (r1.next()) {
				if (accNo1.equals(r1.getString("accountNo"))) {
					PreparedStatement check = con.prepareStatement("select balance from bankdetails where id= ?");
					check.setString(1, id1);
					ResultSet r = check.executeQuery();
					while (r.next()) {
						if (r.getInt("balance") < Integer.parseInt(withdraw)) {
							out.println("<h1>\n Insufficient balance <h1>");
						} else {
							PreparedStatement psd = con
									.prepareStatement("update bankdetails set balance = balance-? where id = ?");
							psd.setString(1, withdraw);
							psd.setString(2, id1);
							int i = psd.executeUpdate();

							if (i == 1) {
								File file = new File("C:/new_file/withdraw.txt");
								FileWriter fw = new FileWriter(file, true);

								out.println("<h1>\n Balance Debited succesfully <h1>");
								out.println("<h2>" + name + " you have Debited money = " + withdraw + "</h2>");
								out.println("<h1>\n Account no is " + accNo1 + " <h1>");

								fw.write("\n Balance Debited succesfully ");
								fw.write("\n" + name + " you have Debited money = " + withdraw);
								fw.write("\n Account no is " + accNo1);
								fw.write("\n");
								fw.write("----------------------------------------------------");
								fw.write("\n");
								fw.flush();
								fw.close();
							}

						}
					}
				} else {
					out.println("Invalid Credentials");
					out.println("<a class='btn' href='withdrawal.html'>Go back</a>");
				}
			}
		} catch (Exception e) {
			out.print(e);
		}

	}

}
