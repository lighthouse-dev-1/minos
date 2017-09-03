package api;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.nulabinc.backlog4j.BacklogClient;
import com.nulabinc.backlog4j.BacklogClientFactory;
import com.nulabinc.backlog4j.Category;
import com.nulabinc.backlog4j.Issue;
import com.nulabinc.backlog4j.IssueType;
import com.nulabinc.backlog4j.Project;
import com.nulabinc.backlog4j.ResponseList;
import com.nulabinc.backlog4j.api.option.CreateIssueParams;
import com.nulabinc.backlog4j.conf.BacklogConfigure;
import com.nulabinc.backlog4j.conf.BacklogJpConfigure;
import com.nulabinc.backlog4j.conf.BacklogPackageConfigure;

public class Backlog {
	
	private BacklogClient backlog;

	public Backlog(String url, String apiKey) throws MalformedURLException  {
		BacklogConfigure conf = null;
		
		if(url.startsWith("http")) {
			conf = new BacklogPackageConfigure(url);
		}else{
			conf = new BacklogJpConfigure(url);
		}
		
		conf.apiKey(apiKey);
		backlog = new BacklogClientFactory(conf).newClient();
	}
	
	public long regist(String projKey, String issueTypeName, String categoryName, String summary, String description, String priorityName) {
		
		CreateIssueParams param = new CreateIssueParams(
				getProjectID(projKey), 
				summary, 
				getIssueTypeId(projKey, issueTypeName), 
				Issue.PriorityType.valueOf(priorityName) );
		
		param.description(description);
		
		if( StringUtils.isNotEmpty(categoryName) ) {
			List<String> categoryIds = new ArrayList<String>();
			categoryIds.add( getCategoryId(projKey, categoryName) );
			param.categoryIds(categoryIds);
		}
		
		Issue issue = backlog.createIssue(param);
		if(issue != null){
			return issue.getId();
		}
		return 0;
	}

	private String getCategoryId(String projKey, String categoryName) {
		for( Category type: backlog.getCategories(projKey) ) {
			if( type.getName().equals(categoryName) ) {
				return type.getIdAsString();
			}
		}
		return null;
	}

	private Object getIssueTypeId(String projKey, String issueTypeName) {
		for( IssueType type: backlog.getIssueTypes(projKey) ) {
			if( type.getName().equals(issueTypeName) ) {
				return type.getIdAsString();
			}
		}
		return null;
	}

	private Object getProjectID(String projKey) {
		for( Project type: backlog.getProjects() ) {
			if( type.getProjectKey().equals(projKey) ) {
				return type.getIdAsString();
			}
		}
		return null;
	}

	public ResponseList<IssueType> getIssueTypes(String projKey) {
		return backlog.getIssueTypes(projKey);
	}
}
