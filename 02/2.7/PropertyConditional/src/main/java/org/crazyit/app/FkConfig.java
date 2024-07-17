package org.crazyit.app;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;

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
public class FkConfig
{
	@Bean
	// 只有当org.fkjava.test属性具有foo属性值时，下面配置方法才会生效
	@ConditionalOnProperty(name = "test", havingValue = "foo",
			prefix = "org.fkjava")
	public DateFormat dateFormat()
	{
		return DateFormat.getDateInstance();
	}
}
