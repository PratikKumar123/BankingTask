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
			response.setContentType("text/html"); 
			PreparedStatement p = con.prepareStatement("select accountNo from bankdetails where Id = ?");
			p.setString(1, id1);
			
			ResultSet r1 = p.executeQuery();
			HttpSession session=request.getSession(false);
			String name=(String)session.getAttribute("name");
			
			   
			while (r1.next()) {
				if (accNo1.equals(r1.getString("accountNo"))) {
					
					PreparedStatement psd = con.prepareStatement("update bankdetails set balance = balance+? where id = ?");
					psd.setString(1, depo1);
					psd.setString(2, id1);
					int i = psd.executeUpdate();
					
					if(i==1){
						File file = new File("C:/new_file/deposit.txt");
						FileWriter fw = new FileWriter(file,true);
						
						out.println("<h1>\n Balance deposited succesfully <h1>");
						out.println("<h2>\n"+name+"</h2>");
						out.println("<h1>\n you have deposited money = "+ depo1 +"<h1>");
						out.println("<h1>acount id is "+id1+"<h1>");
						out.println("<h1>acount No  is "+accNo1+"<h1>");
						
						fw.write("\n Balance deposited succesfully");
						fw.write("\n"+name);
						fw.write("\n you have deposited money = "+ depo1 );
						fw.write("\nacount id is "+id1);
						fw.write("\nacount No  is "+accNo1);
						fw.write("\n");
						fw.write("----------------------------------------------------");
						fw.write("\n");
						fw.flush();
						fw.close();

					}

				} else {
					out.println("Invalid Credentials");
					out.println("<a class='btn' href='Deposit.html'>Go back</a>");
					
				}
			}
		} catch (Exception e) {
			out.print(e);
		}
	}

}
