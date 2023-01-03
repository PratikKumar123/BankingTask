package loginSignup;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Welcome
 */
@WebServlet("/Welcome")
public class Welcome extends HttpServlet {



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		    response.setContentType("text/html");  
		    PrintWriter out = response.getWriter();  
		    HttpSession session=request.getSession(false);
		    
		    if(session!=null){  
		    String name=(String)session.getAttribute("name"); 
		    out.print("<Center><h1>Welcome , "+name+"</h1></Center>");
		    out.println("<hr>");
		    RequestDispatcher r = request.getRequestDispatcher("BankingDetails.html");
		    r.include(request, response);

		    }
		    else {
		    	out.print("Please login first");  
	            request.getRequestDispatcher("login.html").include(request, response);  
			}

	}

}
