package com.han;

import com.google.gson.JsonObject;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;

public class TestIndex {

	private static String host="192.168.232.16"; // 服务器地址
    private static int port=9300; // 端口号
    
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
     * 添加索引
     * @throws Exception
     */
    @Test
    public void testIndex()throws Exception{
    	JsonObject jsonObject=new JsonObject();
    	jsonObject.addProperty("name", "java编程思想");
    	jsonObject.addProperty("publishDate", "2012-11-11");
    	jsonObject.addProperty("price", 100);
    	
    	IndexResponse response=client.prepareIndex("book", "java", "1")
    		.setSource(jsonObject.toString(), XContentType.JSON).get();
    	System.out.println("索引名称："+response.getIndex());
    	System.out.println("类型："+response.getType());
    	System.out.println("文档id："+response.getId());
    	System.out.println("返回值状态："+response.status());
    }
    
    /**
     * 根据Id获取文档
     * @throws Exception
     */
    @Test
    public void testGet() throws Exception{
    	GetResponse response=client.prepareGet("book", "java", "1").get();
    	System.out.println(response.getSourceAsString());
    }
    
    /**
     * 根据ID 修改文档
     * @throws Exception
     */
    @Test
    public void testUpdate()throws Exception{
    	JsonObject jsonObject=new JsonObject();
    	jsonObject.addProperty("name", "java编程思想2");
    	jsonObject.addProperty("publishDate", "2012-11-12");
    	jsonObject.addProperty("price", 102);
    	
    	UpdateResponse response=client.prepareUpdate("book", "java", "1")
				.setDoc(jsonObject.toString(), XContentType.JSON).get();
		System.out.println("索引名称："+response.getIndex());
		System.out.println("类型："+response.getType());
		System.out.println("文档id："+response.getId());
		System.out.println("返回值状态："+response.status());
    }
    
    /**
     * 根据Id 删除文档
     * @throws Exception
     */
    @Test
    public void testDelete() throws Exception{
    	DeleteResponse response=client.prepareDelete("book", "java", "1").get();
		System.out.println("索引名称："+response.getIndex());
		System.out.println("类型："+response.getType());
		System.out.println("文档id："+response.getId());
		System.out.println("返回值状态："+response.status());
    }
}
