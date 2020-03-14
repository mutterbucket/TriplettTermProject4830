
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
 * Servlet implementation class HomeSearch
 */
@WebServlet("/HomeSearch")
public class HomeSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		String animal = request.getParameter("pets");
		search(keyword, animal, response);
	}
	
	void search(String keyword, String animal, HttpServletResponse response) throws IOException {
		
		String catTable = "SELECT * FROM catNames";
		String dogTable = "SELECT * FROM dogNames";
		String birdTable = "SELECT * FROM birdNames";
		String searchSql = null;
		switch (animal) {
			case "cat": searchSql = catTable;
			break;
			case "dog": searchSql = dogTable;
			break;
			case "bird": searchSql = birdTable;
			default: searchSql = catTable;
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
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
	    try {
	    	DBConnect.getDBConnection();
			connection = DBConnect.connection;

	        if (keyword.isEmpty()) {
	        	String selectSQL = searchSql;
	            preparedStatement = connection.prepareStatement(selectSQL);
	        } else {
	            String selectSQL = searchSql;
	            String thePetName = keyword + "%";
	            preparedStatement = connection.prepareStatement(selectSQL);
	            preparedStatement.setString(1, thePetName);
	        }
	        ResultSet rs = preparedStatement.executeQuery();
	        while (rs.next()) {
	            int id = rs.getInt("id");
	            String petName = rs.getString("name").trim();
	            String votes = rs.getString("votes").trim();

	            if (keyword.isEmpty() || petName.contains(keyword)) {
	               output.println("ID: " + id + ", ");
	               output.println("Name: " + petName + ", ");
	               output.println("Votes: " + votes);
	            }
	         }
	         output.println("<a href=/techExcerciseTriplett/home_search.html>Search Data</a> <br>");
	         output.println("</body></html>");
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
