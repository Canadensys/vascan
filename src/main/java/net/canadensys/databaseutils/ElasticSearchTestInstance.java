package net.canadensys.databaseutils;

import static org.elasticsearch.client.Requests.refreshRequest;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.indices.IndexMissingException;
import org.elasticsearch.node.Node;
import org.springframework.core.io.Resource;

/**
 * Helper class to manage local(embedded) instance of ElasticSearch for testing purpose.
 * @author canadensys
 *
 */
public class ElasticSearchTestInstance {

	//the actual node
    private Node node;
    
    private Map<String,Resource> indices = new HashMap<String,Resource>();
    private List<Resource> documents = new ArrayList<Resource>();
    
	public void startElasticSearch(){
        node = nodeBuilder().clusterName("es-test-vascan").local(true).node();
        node.start();
        node.client().admin().cluster().prepareHealth().setWaitForGreenStatus().execute().actionGet();
        
        setupIndices();
    }

    public void stopElasticSearch(){
    	if(node !=null){
    		node.close();
    	}
    }
    
    /**
     * Build a client to connect to a running local(embedded) ElacticSearch node.
     * @return
     */
	public Client getLocalClient(){
		return node.client();
	}
	
	/**
	 * Setup ElasticSearch indices from defined indices and documents.
	 * Warning: if an index already exists, it will be deleted and recreated.
	 * 
	 */
	private void setupIndices(){
		Client client = node.client();
		
		// (re)create all indices
		for(String indexName : indices.keySet()){
			// ES will keep the previous version of the cluster on disk so, we need to delete the index
			try{
				client.admin().indices().prepareDelete(indexName).execute().actionGet();
			}
			catch(IndexMissingException imEx){}//ignore
			
			try {
				String json = IOUtils.toString(indices.get(indexName).getInputStream());
				createIndex(client, indexName, json);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		//refresh the index
		client.admin().indices().refresh(refreshRequest()).actionGet();
		
		// Add data, the supported format is 'index/type/id:{json}'
		for(Resource resource : documents){
			try {
				List<String> docs = IOUtils.readLines(resource.getInputStream());
				String[] id;
				String json;
				for(String doc : docs){
					id = StringUtils.substringBefore(doc, ":").split("/");
					json = StringUtils.substringAfter(doc, ":");
					addDocument(client, id[0], id[1], id[2], json);
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//refresh the index
		client.admin().indices().refresh(refreshRequest()).actionGet();
	}
	
	/**
	 * Create an ESindex.
	 * 
	 * @param client
	 * @param indexName
	 * @param indexCreationStream
	 */
	private void createIndex(Client client, String indexName, String json){
		client.admin().indices().prepareCreate(indexName)
			.setSource(json)
			.execute()
			.actionGet();
	}
	
	/**
	 * Add a document to an ES index.
	 * 
	 * @param client
	 * @param indexName
	 * @param type
	 * @param id
	 * @param json
	 */
	private void addDocument(Client client, String indexName, String type, String id, String json){
		client.prepareIndex(indexName, type, id)
	        .setSource(json)
	        .execute()
	        .actionGet();
	}

	public Map<String, Resource> getIndices() {
		return indices;
	}

	public void setIndices(Map<String, Resource> indices) {
		this.indices = indices;
	}

	public List<Resource> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Resource> documents) {
		this.documents = documents;
	}

}
