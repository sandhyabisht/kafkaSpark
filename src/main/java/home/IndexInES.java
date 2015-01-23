package home;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexResponse;
import static org.elasticsearch.node.NodeBuilder.*;

import org.elasticsearch.action.percolate.PercolateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.node.Node;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class IndexInES {

	public IndexInES(String ES_server,String ES_port,String ES_index,String json,Model model ,String Alert_Field_value,String Alert_index) throws IOException
	{
		System.out.println(json);
		System.out.println("ES "+ES_server+ES_port+ES_index);
		
		//*****user this if there is cluster with "cybox"*/
		
		/*Settings settings = ImmutableSettings.settingsBuilder()
        .put("cluster.name", "cybox").build();
		Client client = new TransportClient(settings)*/
		
		//****Else(for default)*/
		Client client = new TransportClient()
        .addTransportAddress(new InetSocketTransportAddress(ES_server, Integer.valueOf(ES_port)));
		

	    String[] alertsArray=Alert_Field_value.split(",");
		Set<String> alertSet = new HashSet<String>(Arrays.asList(alertsArray));
		System.out.print("Alerts Number "+alertSet.size());
		if(alertSet.contains(model.getS_action()))
		{
			//Create index in alerts
			try {
				CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(Alert_index.toLowerCase());
				CreateIndexResponse response = createIndexRequestBuilder.execute().actionGet();
				System.out.println(response.isAcknowledged());
			}catch(Exception e)
			{
				//e.printStackTrace();
				System.out.println("Index " + Alert_index + " already there!!!!");
			}

			//Insert Doc in Alert
			IndexResponse response1 = client.prepareIndex(Alert_index.toLowerCase(), "alert")
					.setSource(json)
					.execute()
					.actionGet();

		}else {
			try {
				CreateIndexRequestBuilder createIndexRequestBuilder = client.admin().indices().prepareCreate(ES_index.toLowerCase());
				CreateIndexResponse response = createIndexRequestBuilder.execute().actionGet();
				System.out.println(response.isAcknowledged());
			}catch(Exception e)
			{
				//e.printStackTrace();
				System.out.println("Index "+ES_index+" already there!!!!");
			}
			IndexResponse response1 = client.prepareIndex(ES_index.toLowerCase(), "log")
					.setSource(json)
					.execute()
					.actionGet();
		}
		client.close();
	}
}
