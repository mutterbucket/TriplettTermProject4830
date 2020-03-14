

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
 * Servlet implementation class Leaderboard
 */
@WebServlet("/Leaderboard")
public class Leaderboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Leaderboard() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		search(response);
	}
	
	void search(HttpServletResponse response) throws IOException {

		String catTable = "SELECT * FROM catNames";
		String dogTable = "SELECT * FROM dogNames";
		String catColumn = "CAT_NAME";
		String dogColumn = "DOG_NAME";
		
        String[] catName = new String[10];
        String[] catVotes = new String[10];
        String[] dogName = new String[10];
        String[] dogVotes = new String[10];
        String[] rows = new String[10];
        int i = 0;  

		response.setContentType("text/html");
		PrintWriter output = response.getWriter();
		String title = "Names Leaderboard";
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
				"transistional//en\">\n";
		output.println(docType + //
			    "<html>\n" + //
			    "<head><title>" + title + "</title></head>\n" + //
			    "<body bgcolor=\"#f0f0f0\">\n" + //
			    "<h1 align=\"center\">" + title + "</h1>\n" +
			    "<style type=\"text/css\">" +
			    "th {" +
			    	"border: 1px solid black;" +
				"}" +
				"caption {" +
				  "padding: 5px;" +
				  "font-size: 24px;" +
				"}" +
				"table#t01 tr:nth-child(even) {" +
				  "background-color: #grey;" +
				"}" +
				"table#t01 tr:nth-child(odd) {" +
				  "background-color: #silver;" +
				"}" +
				"table#t01 th {" +
				  "color: white;" +
				  "background-color: black;" +
				"}</style>");
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
	    try {
	    	DBConnect.getDBConnection();
			connection = DBConnect.connection;

            preparedStatement = connection.prepareStatement(catTable);
	        ResultSet rs = preparedStatement.executeQuery();
	        
	        while (rs.next() && i < 10) {
	        	i++;
	            catName[i] = rs.getString(catColumn).trim();
	            catVotes[i] = rs.getString("votes").trim();
	         }
	        
            preparedStatement = connection.prepareStatement(dogTable);
	        rs = preparedStatement.executeQuery();
	        i = 0;
	        while (rs.next() && i < 10) {
	        	i++;
	            dogName[i] = rs.getString(dogColumn).trim();
	            dogVotes[i] = rs.getString("votes").trim();
	         }
	        
	        for (i = 0; i < 10; i++) {
	        	rows[i] = "<tr>";
	        	if (catName[i] != null) {
		        	catName[i] = "<td>" + catName[i] + ": " + catVotes[i] + " Votes" + "</td> ";
		        	rows[i] = rows[i] + catName[i];
	        	}
	        	if (dogName[i] != null) {
		        	dogName[i] = "<td>" + dogName[i] + ": " + dogVotes[i] + " Votes" + "</td> ";
		        	rows[i] = rows[i] + dogName[i];
	        	}
	        	rows[i] = rows[i] + "</tr> <br>";
	        }
	        
	        output.println("<table style=margin-top:10px; align=\"left\">" + 
	        "<tr>" +
	          "<th align=\"left\">Cats</th>" +
	          "<th align=\"left\">Dogs</th>" +
 	        "</tr>" +
	       rows[0] +
	       rows[1] +
	       rows[2] +
	       rows[3] +
	       rows[4] +
	       rows[5] +
	       rows[6] +
	       rows[7] +
	       rows[8] +
	       rows[8] +
	      "</table>");
	        
	         rs.close();
	         preparedStatement.close();
	         connection.close();
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
	         if (connection != null)
	            connection.close();
	      } catch (SQLException se) {
	         se.printStackTrace();
	      }
	   }
       output.println("<br><br> <a href=/individualExcerciseTriplett/home_search.html>Search Data</a> <br>");
       output.println("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
