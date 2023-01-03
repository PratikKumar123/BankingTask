package loginSignup;

import java.io.File;
import java.io.FileWriter;
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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class transferop
 */
@WebServlet("/TransferOperation")
public class TransferOperation extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		ServletContext context = getServletContext();
		String url = context.getInitParameter("url");
		String dname = context.getInitParameter("dname");
		String dpass = context.getInitParameter("dpass");

		PrintWriter out = response.getWriter();

		String id = request.getParameter("uid");
		String userAc = request.getParameter("uacc");
		String reciverAc = request.getParameter("racc");
		String udeposit = request.getParameter("udeposit");

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, dname, dpass);
			HttpSession session=request.getSession(false);
			String name=(String)session.getAttribute("name");
			PreparedStatement p2 = con.prepareStatement("select accountNo from bankdetails where id= ?");
			p2.setString(1, id);
			ResultSet r1 = p2.executeQuery();
			while (r1.next()) {
				if (userAc.equals(r1.getString("accountNo"))) {
					PreparedStatement check = con
							.prepareStatement("select balance from bankdetails where accountNo= ?");
					check.setString(1, userAc);
					ResultSet r = check.executeQuery();
					while (r.next()) {
						if (r.getInt("balance") < Integer.parseInt(udeposit)) {
							out.println("<h1>\n Insufficient balance to send<h1>");
						} else {
							PreparedStatement p = con
									.prepareStatement("update bankdetails set balance=balance-? WHERE accountNo=?");
							p.setString(1, udeposit);
							p.setString(2, userAc);
							p.executeUpdate();

							PreparedStatement p1 = con
									.prepareStatement("update bankdetails set balance=balance+? WHERE accountNo=?");
							p1.setString(1, udeposit);
							p1.setString(2, reciverAc);

							int c = p1.executeUpdate();
							if (c == 1) {
								File file = new File("C:/new_file/transfer.txt");
								FileWriter fw = new FileWriter(file, true);

								out.println("<h1>\n Money transfer succesfully <h1>");
								out.println("<h1>\n "+name+ "<h1>");
								out.println("<h1>\n You have deposited money = " + udeposit + "<h1>");
								out.println("<h1>  Your acount Number  is " + userAc + "<h1>");
								out.println("<h1> Reciver account  No  is " + reciverAc + "<h1>");

								
								fw.write("\n Money transfer succesfully");
								fw.write("\n "+name);
								fw.write("\n You have deposited money = " + udeposit);
								fw.write("\n  Your acount Number  is " + userAc);
								fw.write("\n Reciver account  No  is " + reciverAc);
								fw.write("\n");
								fw.write("----------------------------------------------------");
								fw.write("\n");
								fw.flush();
								fw.close();

							} else {
								out.println("Error while sending money please try again");
								out.println("<a class='btn' href='transaction.html'>Go back</a>");
							}
						}

					}
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
