
/** 
 * @file AddName.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		
		System.out.print(animal + "\n");
		System.out.print(newName + "\n");
		Connection connection = null;
		String insertSql = null;
		String petColumn = null;
		switch (animal) {
		case "cat": insertSql = catTable;
			petColumn = "CAT_NAME";
			break;
		case "dog": insertSql = dogTable;
			petColumn = "DOG_NAME";
			break;
		default: insertSql = catTable;
			break;
		}
		
		int votes = checkVotes(newName, animal, response);
		try { 
			DBConnect.getDBConnection();
			connection = DBConnect.connection;
			PreparedStatement preparedstmt = connection.prepareStatement(insertSql);
			preparedstmt.setString(1,  newName);
			preparedstmt.setString(2, Integer.toString(votes));
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
		 out.println("<a href=/individualExcerciseTriplett/home_search.html>Search Data</a> <br>");
		 out.println("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	int checkVotes(String keyword, String animal, HttpServletResponse response) throws IOException {
		
		System.out.println("\n" + keyword + "\n");
		System.out.println(animal + "\n");
		String catTable = "SELECT * FROM catNames";
		String dogTable = "SELECT * FROM dogNames";
		String searchSql = null;
		String petColumn = null;
		String petTable = null;
		switch (animal) {
			case "cat": searchSql = catTable;
				petColumn = "CAT_NAME";
				petTable = "catNames";
				break;
			case "dog": searchSql = dogTable;
				petColumn = "DOG_NAME";
				petTable = "dogNames";
				break;
			default: 
				System.out.print("Defaulting to cat");
				searchSql = catTable;
				petColumn = "CAT_NAME";
				petTable = "catNames";
				break;
		}
		
		response.setContentType("text/html");
		PrintWriter output = response.getWriter();
		String title = "Existing Names";
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
				"transistional//en\">\n";
		output.println(docType + //
			    "<html>\n" + //
			    "<head><title>" + title + "</title></head>\n" + //
			    "<body bgcolor=\"#f0f0f0\">\n" + //
			    "<h1 align=\"center\">" + title + "</h1>\n");
		
		Connection connectionInner = null;
		PreparedStatement preparedStatement = null;
	    try {
	    	DBConnect.getDBConnection();
			connectionInner = DBConnect.connection;

	        if (keyword.isEmpty()) {
	        	String selectSQL = searchSql;
	            preparedStatement = connectionInner.prepareStatement(selectSQL);
	        } else {
	            String selectSQL = searchSql;
	            String keyExists = " WHERE " + petColumn + " LIKE ?";
	            String thePetName = keyword;
	            preparedStatement = connectionInner.prepareStatement(selectSQL + keyExists);
	            preparedStatement.setString(1, thePetName);
	        }
	        ResultSet rs = preparedStatement.executeQuery();
	        
	        if (rs.next()) {
	        	String votes = rs.getString("votes").trim();
	        	String removeOldItem = "DELETE FROM " + petTable;
	        	preparedStatement = connectionInner.prepareStatement(removeOldItem);
	        	//preparedStatement.setString(1, keyword);
	        	preparedStatement.executeUpdate();
	        	connectionInner.close();
	        	int v = Integer.parseInt(votes) + 1;
	        	return v;

	        }
	        else {
	        	System.out.println("Not In List\n");
	        	connectionInner.close();
	        	return 1;
	        }
	        
	     } catch (SQLException se) {
	         se.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	         if (preparedStatement != null)
	            preparedStatement.close();
	      } catch (SQLException se2) {
	      }
	      try {
	         if (connectionInner != null)
	            connectionInner.close();
	      } catch (SQLException se) {
	         se.printStackTrace();
	      }
	   }
	   return 1;
	}

}
