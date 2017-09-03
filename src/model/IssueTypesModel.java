package model;

import java.net.MalformedURLException;

import com.nulabinc.backlog4j.IssueType;
import com.nulabinc.backlog4j.ResponseList;

import api.Backlog;

/**
 * Table class IssueTypesTable
 */
public class IssueTypesModel extends AppModel {
       
    /**
     * @see IssueTypesTable()
     */
    public IssueTypesModel() {
        super();
    }
    
    /**
     * @throws MalformedURLException 
     * @see getIssueType()
     */
    public ResponseList<IssueType> getIssueType() throws MalformedURLException
    {
        String nameSpace	= "https://test.backlogtool.com"; // backlog url
		String projKey	= "test"; // project key
		String apiKey	= "sUTAcbxFSshL4z7ze99BFQqULsUURsnxDwu6itXe50Bk1jnp6kXgbDPTOc2EOq2c"; // API Key
		
		Backlog backlog = new Backlog( nameSpace, apiKey );
		
		return backlog.getIssueTypes(projKey);
    }
}
