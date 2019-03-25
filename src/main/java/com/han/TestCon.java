package com.han;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

public class TestCon {

	private static String host="192.168.232.16"; // ַ服务器地址
    private static int port=9300; // 端口号

	public static void main(String[] args) throws Exception{
		@SuppressWarnings({ "resource", "unchecked" })
		TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(host),port));
		System.out.println(client);
		client.close();
	}
}
