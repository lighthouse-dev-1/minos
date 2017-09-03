package controller;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AppController
 */
public class AppController extends HttpServlet {
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AppController() {
        super();
    }

	/**
	 * @return 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected Object loadModel(String ModelName) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class t = Class.forName("model." + ModelName + "Model");
		return t.newInstance();
	}

}
