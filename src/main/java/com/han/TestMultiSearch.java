package com.han;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;

public class TestMultiSearch {

	private static String host="192.168.232.16"; // ��������ַ
    private static int port=9300; // �˿�
    
    public static final String CLUSTER_NAME="my-application"; // ��Ⱥ����
    
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
     * ��������ѯ
     * @throws Exception
     */
    @Test
    public void searchMulti()throws Exception{
    	SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
		QueryBuilder queryBuilder=QueryBuilders.matchPhraseQuery("title", "ս");
		QueryBuilder queryBuilder2=QueryBuilders.matchPhraseQuery("content", "����");
		SearchResponse sr=srb.setQuery(QueryBuilders.boolQuery()
				.must(queryBuilder)
				.must(queryBuilder2))
    		.execute()
    		.actionGet(); 
    	SearchHits hits=sr.getHits();
    	for(SearchHit hit:hits){
    		System.out.println(hit.getSourceAsString());
    	}
    }
    
    /**
     * ��������ѯ
     * @throws Exception
     */
    @Test
    public void searchMulti2()throws Exception{
    	SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
		QueryBuilder queryBuilder=QueryBuilders.matchPhraseQuery("title", "ս");
		QueryBuilder queryBuilder2=QueryBuilders.matchPhraseQuery("content", "��ʿ");
		SearchResponse sr=srb.setQuery(QueryBuilders.boolQuery()
				.must(queryBuilder)
				.mustNot(queryBuilder2))
    		.execute()
    		.actionGet(); 
    	SearchHits hits=sr.getHits();
    	for(SearchHit hit:hits){
    		System.out.println(hit.getSourceAsString());
    	}
    }
    
    /**
     * ��������ѯ
     * @throws Exception
     */
    @Test
    public void searchMulti3()throws Exception{
    	SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
		QueryBuilder queryBuilder=QueryBuilders.matchPhraseQuery("title", "ս");
		QueryBuilder queryBuilder2=QueryBuilders.matchPhraseQuery("content", "����");
		QueryBuilder queryBuilder3=QueryBuilders.rangeQuery("publishDate").gte("2018-01-01");
		SearchResponse sr=srb.setQuery(QueryBuilders.boolQuery()
				.must(queryBuilder)
				.should(queryBuilder2)
				.should(queryBuilder3))
    		.execute()
    		.actionGet(); 
    	SearchHits hits=sr.getHits();
    	for(SearchHit hit:hits){
    		System.out.println(hit.getScore()+":"+hit.getSourceAsString());
    	}
    }
    
    /**
     * ��������ѯ
     * @throws Exception
     */
    @Test
    public void searchMulti4()throws Exception{
    	SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
		QueryBuilder queryBuilder=QueryBuilders.matchPhraseQuery("title", "ս");
		QueryBuilder queryBuilder2=QueryBuilders.rangeQuery("price").lte(40);
		SearchResponse sr=srb.setQuery(QueryBuilders.boolQuery()
				.must(queryBuilder)
				.filter(queryBuilder2))
    		.execute()
    		.actionGet(); 
    	SearchHits hits=sr.getHits();
    	for(SearchHit hit:hits){
    		System.out.println(hit.getSourceAsString());
    	}
    }
}
