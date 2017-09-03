package controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nulabinc.backlog4j.IssueType;
import com.nulabinc.backlog4j.ResponseList;

import action.Action;
import model.ImportExcelModel;
import model.IssueTypesModel;
import action.ActionFactory;
import api.Backlog;

import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.json.*;

/**
 * Servlet implementation class MinosServlet
 */
@WebServlet("/main")
public class MinosController extends AppController {
	private static final long serialVersionUID = 1L;
	private static String  nameSpace	= null;
	private static String  projKey	= null;
	private static String  apiKey	= null;
	private static Backlog backlog	= null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MinosController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IssueTypesModel issueTypes = null;
		
		try {
			issueTypes = (IssueTypesModel) this.loadModel("IssueTypes");
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		ResponseList<IssueType> issueTypeList = issueTypes.getIssueType();
		HashMap<String, ResponseList<IssueType>> map = new HashMap<String, ResponseList<IssueType>>();
		map.put("data", issueTypeList);
		
		JSONObject jsonIssueTypeList = new JSONObject(map);
		
		request.setAttribute("issueTypeList", jsonIssueTypeList);
		
		ActionFactory af = ActionFactory.getInstance();
		Action action = af.getAction("main");
		if(action != null){
			action.execute(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		IssueTypesModel	 issueTypes			= null;
		ImportExcelModel	 importExcelModel 	= null;
		try {
			issueTypes = (IssueTypesModel) this.loadModel("IssueTypes");
			ResponseList<IssueType> issueTypeList = issueTypes.getIssueType();
			importExcelModel = (ImportExcelModel) this.loadModel("ImportExcel");
			JSONObject jsonIssueTypeList = importExcelModel.getJsonIssueTypeList(issueTypeList);
			request.setAttribute("issueTypeList", jsonIssueTypeList);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Boolean isExcel     = Boolean.valueOf(request.getParameter("isExcel"));
		Boolean isConnected = Boolean.valueOf(request.getParameter("isConnected"));
		
		if (isExcel) {
			DiskFileItemFactory	factory	= new DiskFileItemFactory();
			ServletFileUpload 	sfu 	 	= new ServletFileUpload(factory);
			FileItemIterator 	iterator	= null;

			try {
				iterator = sfu.getItemIterator(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			JSONObject jsonTask = importExcelModel.importExcel(iterator);
			request.setAttribute("task", jsonTask);
		} else if(isConnected){
			MinosController.nameSpace = request.getParameter("nameSpace");
			MinosController.projKey   = request.getParameter("projectKey");
			MinosController.apiKey    = request.getParameter("apiKey");
			MinosController.backlog   = new Backlog( MinosController.nameSpace, MinosController.apiKey );
		} else {			
			String 	strTaskCnt	= request.getParameter("taskCnt");
			int		taskCnt 	= Integer.parseInt(strTaskCnt);
			
			for(int i=0; i < taskCnt; i++ ) {
				String summary		= request.getParameterValues("summary")[i];
				String description	= request.getParameterValues("description")[i];
				String issueTypeName	= request.getParameterValues("issueTypeName")[i];
				String priorityName	= request.getParameterValues("priorityName")[i];
				
				long issueId = backlog.regist(MinosController.projKey, issueTypeName, "", summary, description, priorityName );
				System.out.println("IssueID: " + issueId);
			}
		}
		
		doGet(request, response);
	}

}
