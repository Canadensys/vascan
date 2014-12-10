package net.canadensys.databaseutils;

import static org.elasticsearch.client.Requests.refreshRequest;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.ElasticSearchException;
import org.elasticsearch.action.index.IndexResponse;
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
    private Map<String,Resource> documents = new HashMap<String,Resource>();
    
	public void startElasticSearch(){
        node = nodeBuilder().clusterName("es-test-vascan").local(true).node();
        node.start();
        node.client().admin().cluster().prepareHealth().setWaitForGreenStatus().execute().actionGet();
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
	public Client buildLocalClient(){
		Client client = node.client();
		
		// (re)create all indices
		for(String indexName : indices.keySet()){
			// ES will keep the previous version of the cluster on disk so, we need to delete the index
			try{
				client.admin().indices().prepareDelete(indexName).execute().actionGet();
			}
			catch(IndexMissingException imEx){}//ignore
			
			try {
				createIndex(client, indexName, indices.get(indexName).getInputStream());
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		//refresh the index
		client.admin().indices().refresh(refreshRequest()).actionGet();
		
		for(String typeName : documents.keySet()){
			try {
				List<String> docs = IOUtils.readLines(documents.get(typeName).getInputStream());
				String id, json;
				for(String doc : docs){
					id = StringUtils.substringBefore(doc, ":");
					json = StringUtils.substringAfter(doc, ":");
					addDocument(client, "vascan", typeName, id, json);
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//refresh the index
		client.admin().indices().refresh(refreshRequest()).actionGet();
		
		return client;
	}
	
	/**
	 * Create index
	 * @param client
	 * @param indexName
	 * @param indexCreationStream
	 */
	private void createIndex(Client client, String indexName, InputStream indexCreationStream){
		
		try {
			client.admin().indices().prepareCreate(indexName)
				.setSource(IOUtils.toString(indexCreationStream))
				.execute()
				.actionGet();
		}
		catch (ElasticSearchException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void addDocument(Client client, String indexName, String type, String id, String json){
		IndexResponse rep = client.prepareIndex(indexName, type, id)
	        .setSource(json)
	        .execute()
	        .actionGet();
		
		System.out.println(rep);
	}

	public Map<String, Resource> getIndices() {
		return indices;
	}

	public void setIndices(Map<String, Resource> indices) {
		this.indices = indices;
	}

	public Map<String, Resource> getDocuments() {
		return documents;
	}

	public void setDocuments(Map<String, Resource> documents) {
		this.documents = documents;
	}
}
