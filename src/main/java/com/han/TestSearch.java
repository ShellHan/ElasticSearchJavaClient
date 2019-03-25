package com.han;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;

public class TestSearch {

	private static String host="192.168.232.16"; // 服务器地址
    private static int port=9300; // 端口号
    
    //public static final String CLUSTER_NAME="my-application"; // 集群名称

    //private static Settings.Builder settings=Settings.builder().put("cluster.name",CLUSTER_NAME);
    
    private TransportClient client=null;
    
    @SuppressWarnings({ "resource", "unchecked" })
	@Before
    public void getCient()throws Exception{
    	client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(host),port));
    }
    
    @After
    public void close(){
    	if(client!=null){
    		client.close();
    	}
    }
    
    /**
     * 查询所有文档
     * @throws Exception
     */
    @Test
    public void searchAll()throws Exception{
    	SearchRequestBuilder srb = client.prepareSearch("film").setTypes("dongzuo");
    	SearchResponse sr = srb.setQuery(QueryBuilders.matchAllQuery())
    		.execute()
    		.actionGet(); //查詢所有
    	SearchHits hits = sr.getHits();
    	for(SearchHit hit:hits){
    		System.out.println(hit.getSourceAsString());
    	}
    }
    
    /**
     * 分页
     * @throws Exception
     */
    @Test
    public void searchaging()throws Exception{
    	SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
    	SearchResponse sr=srb.setQuery(QueryBuilders.matchAllQuery())
    		.setFrom(1)
    		.setSize(3)
    		.execute()
    		.actionGet(); 
    	SearchHits hits=sr.getHits();
    	for(SearchHit hit:hits){
    		System.out.println(hit.getSourceAsString());
    	}
    }
    
    /**
     * 排序
     * @throws Exception
     */
    @Test
    public void searchSort()throws Exception{
    	SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
    	SearchResponse sr=srb.setQuery(QueryBuilders.matchAllQuery())
    		.addSort("publishDate", SortOrder.ASC)
    		.execute()
    		.actionGet(); 
    	SearchHits hits=sr.getHits();
    	for(SearchHit hit:hits){
    		System.out.println(hit.getSourceAsString());
    	}
    }
    
    /**
     * 数据列过滤
     * @throws Exception
     */
    @Test
    public void searchInclude()throws Exception{
    	SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
    	SearchResponse sr=srb.setQuery(QueryBuilders.matchAllQuery())
    		.setFetchSource(new String[]{"title","price"}, null)
    		.execute()
    		.actionGet(); 
    	SearchHits hits=sr.getHits();
    	for(SearchHit hit:hits){
    		System.out.println(hit.getSourceAsString());
    	}
    }
    
    /**
     * 条件查询
     * @throws Exception
     */
    @Test
    public void searchByCondition()throws Exception{
    	SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
    	SearchResponse sr=srb.setQuery(QueryBuilders.matchQuery("title", "战"))
    		.setFetchSource(new String[]{"title","price"}, null)
    		.execute()
    		.actionGet(); 
    	SearchHits hits=sr.getHits();
    	for(SearchHit hit:hits){
    		System.out.println(hit.getSourceAsString());
    	}
    }
    
    /**
     * 高亮查询
     * @throws Exception
     */
    @Test
    public void searchHighlight()throws Exception{
    	SearchRequestBuilder srb=client.prepareSearch("film").setTypes("dongzuo");
    	HighlightBuilder highlightBuilder=new HighlightBuilder();
    	highlightBuilder.preTags("<h2><font>");
    	highlightBuilder.postTags("</font></h2>");
    	highlightBuilder.field("title");
		SearchResponse sr=srb.setQuery(QueryBuilders.matchQuery("title", "战"))
    		.highlighter(highlightBuilder)
    		.setFetchSource(new String[]{"title","price"}, null)
    		.execute()
    		.actionGet(); 
    	SearchHits hits=sr.getHits();
    	for(SearchHit hit:hits){
    		System.out.println(hit.getSourceAsString());
    		System.out.println(hit.getHighlightFields());
    	}
    }
    
}
