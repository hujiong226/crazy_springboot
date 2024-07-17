package org.crazyit.app;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.PreemptiveBasicAuthClientBuilderFactory;
import org.apache.solr.client.solrj.request.GenericSolrRequest;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.boot.autoconfigure.solr.SolrProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * Description:<br>
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a><br>
 * Copyright (C), 2001-2022, Yeeku.H.Lee<br>
 * This program is protected by copyright laws.<br>
 * Program Name:<br>
 * Date:<br>
 *
 * @author Yeeku.H.Lee kongyeeku@163.com 公众号: fkbooks<br>
 * @version 1.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({HttpSolrClient.class, CloudSolrClient.class})
@EnableConfigurationProperties(SolrProperties.class)
public class SolrConfig
{
	@Value("${spring.data.solr.username}")
	private String username;
	@Value("${spring.data.solr.password}")
	private String password;
	@Bean
	public SolrClient solrClient(SolrProperties properties) throws IOException, SolrServerException
	{
		// 设置使用基本认证的客户端
		System.setProperty("solr.httpclient.builder.factory",
				"org.apache.solr.client.solrj.impl.PreemptiveBasicAuthClientBuilderFactory");
		// 设置认证的用户名和密码
		System.setProperty("basicauth", username + ":" + password);
		// 如果zk-host属性存在，使用CloudSolrClient创建SolrClient
		if (StringUtils.hasText(properties.getZkHost()))
		{
			return new CloudSolrClient.Builder(Arrays.asList(
					properties.getZkHost()), Optional.empty()).build();
		}
		// 创建单机模式下的SolrClient
		return new HttpSolrClient.Builder(properties.getHost()).build();
	}
}
