package actions;

import helper.SearchResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;

/**
 * Servlet implementation class FirstPageAction
 */
@WebServlet("/processaQuery")
public class ActionSearch extends HttpServlet {
	

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ActionSearch() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String query = request.getParameter("query");

		ServletContext application = getServletContext();
		HttpSession session = request.getSession();
		session.setAttribute("query",query);
		
		try {
			SearchResult searchResult = helper.Searcher.mkQuery(query);
			session.setAttribute("hits", searchResult.getTopHits());
			session.setAttribute("originalQuery", searchResult.getOriginalQuery());
			session.setAttribute("suggestedQuery", searchResult.getSuggestedQuery());			
			session.setAttribute("totalHitCount", searchResult.getTotalHitCount());
			session.setAttribute("searchDuration", searchResult.getSearchDuration());



			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*try {
			helper.Searcher.mkQuery(query);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		RequestDispatcher rd = application.getRequestDispatcher("/results.jsp");
		rd.forward(request, response);

	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}


}
