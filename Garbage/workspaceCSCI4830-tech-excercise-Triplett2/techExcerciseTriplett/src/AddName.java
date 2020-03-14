
/** 
 * @file AddName.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddName
 */
@WebServlet("/AddName")
public class AddName extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddName() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String newName = request.getParameter("newName");
		String animal = request.getParameter("pets");
		String catTable = "INSERT INTO catNames (id, CAT_NAME, VOTES) values (default, ?, ?)";
		String dogTable = "INSERT INTO dogNames (id, DOG_NAME, VOTES) values (default, ?, ?)";
		String birdTable = "INSERT INTO birdNames (id, BIRD_NAME, VOTES) values (default, ?, ?)";
		
		System.out.print(animal + "\n");
		System.out.print(newName + "\n");
		Connection connection = null;
		String insertSql = null;
		switch (animal) {
		case "cat": insertSql = catTable;
		break;
		case "dog": insertSql = dogTable;
		break;
		case "bird": insertSql = birdTable;
		default: break;
		}
		
		try { 
			DBConnect.getDBConnection();
			connection = DBConnect.connection;
			PreparedStatement preparedstmt = connection.prepareStatement(insertSql);
			preparedstmt.setString(1,  newName);
			preparedstmt.setString(2, "1");
			preparedstmt.execute();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String title = "Adding Pet Name";
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transistional//en\">\n";
		out.println(docType + //
				"<html>\n" + //
				 "<head><title>" + title + "</title></head>\n" + //
		          "<body bgcolor=\"#f0f0f0\">\n" + //
		          "<h2 align=\"center\">" + title + "</h2>\n" + //
		          "<ul>\n" + //

		          "  <li><b>Pet Type</b>: " + animal + "\n" + //
		          "  <li><b>Pet Name</b>: " + newName + "\n" + //

		          "</ul>\n");
		 out.println("<a href=/techExcerciseTriplett/home_search.html>Search Data</a> <br>");
		 out.println("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
