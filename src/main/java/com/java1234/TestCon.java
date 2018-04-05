package com.java1234;

import java.net.InetAddress;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class TestCon {

	private static String host="192.168.1.110"; // 服务器地址
    private static int port=9300; // 端口
	
	public static void main(String[] args) throws Exception{
		@SuppressWarnings({ "resource", "unchecked" })
		TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host),port));
		System.out.println(client);
		client.close();
	}
}
