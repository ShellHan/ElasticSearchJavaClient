package com.han;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;

public class TestPartSearch {

	private static String host="192.168.232.16"; // ��������ַ
    private static int port=9300; // �˿�
    
    public static final String CLUSTER_NAME="my-application"; // ��Ⱥ����
    
    public static final String ANALYZER="smartcn";
    
    private static Settings.Builder settings=Settings.builder().put("cluster.name",CLUSTER_NAME);
    
    private TransportClient client=null;
    
    @SuppressWarnings({ "resource", "unchecked" })
	@Before
    public void getCient()throws Exception{
    	client = new PreBuiltTransportClient(settings.build())
                .addTransportAddress(new TransportAddress(InetAddress.getByName(host),port));
    }
    
    @After
    public void close(){
    	if(client!=null){
    		client.close();
    	}
    }

    /**
     * �ִʲ�ѯ
     */
    @Test
    public void search()throws Exception{
    	SearchRequestBuilder srb=client.prepareSearch("film2").setTypes("dongzuo");
    	SearchResponse sr=srb.setQuery(QueryBuilders.matchQuery("title", "����").analyzer(ANALYZER))
    		.setFetchSource(new String[]{"title","price"}, null)
    		.execute()
    		.actionGet(); 
    	SearchHits hits=sr.getHits();
    	for(SearchHit hit:hits){
    		System.out.println(hit.getSourceAsString());
    	}
    }
    
    /**
     * ���ֶηִʲ�ѯ
     * @throws Exception
     */
    @Test
    public void search2()throws Exception{
    	SearchRequestBuilder srb=client.prepareSearch("film2").setTypes("dongzuo");
    	SearchResponse sr=srb.setQuery(QueryBuilders.multiMatchQuery("����������ȭ", "title","content").analyzer(ANALYZER))
    		.setFetchSource(new String[]{"title","price"}, null)
    		.execute()
    		.actionGet(); 
    	SearchHits hits=sr.getHits();
    	for(SearchHit hit:hits){
    		System.out.println(hit.getSourceAsString());
    	}
    }
}
