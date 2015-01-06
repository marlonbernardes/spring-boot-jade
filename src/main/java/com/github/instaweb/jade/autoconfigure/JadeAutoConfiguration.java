/*
 * Copyright 2013-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.instaweb.jade.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;

import com.github.instaweb.jade.JadeResourceTemplateLoader;
import com.github.instaweb.jade.web.JadeViewResolver;

import de.neuland.jade4j.Jade4J;
import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.TemplateLoader;

/**
 * @author Marlon Bernardes
 *
 */
@Configuration
@ConditionalOnClass(Jade4J.class)
@EnableConfigurationProperties(JadeProperties.class)
public class JadeAutoConfiguration {
	
	@Autowired
	private JadeProperties properties;
	
	@Autowired
	private Environment environment;
	
	@Bean
	@ConditionalOnMissingBean(JadeConfiguration.class)
	public JadeConfiguration jadeConfiguration(TemplateLoader jadeTemplateLoader) {
		JadeConfiguration configuration = new JadeConfiguration();
		configuration.setCaching(false); //Cache is handled by spring
		configuration.setPrettyPrint(properties.isPrettyPrint());
		configuration.setTemplateLoader(jadeTemplateLoader);
		//configuration.setSharedVariables(sharedVariables);
		//configuration.setFilter(name, filter);
		//configuration.setMode(mode);
		return configuration;
	}

	@Bean
	@ConditionalOnMissingBean(TemplateLoader.class)
	public TemplateLoader jadeTemplateLoader() {
		JadeResourceTemplateLoader loader = new JadeResourceTemplateLoader(properties.getPrefix(), properties.getSuffix());
		loader.setCharSet(properties.getCharSet());
		return loader;
	}
	
	@Bean
	@ConditionalOnMissingBean(JadeViewResolver.class)
	public JadeViewResolver jadeViewResolver(JadeConfiguration jadeConfiguration) {
		JadeViewResolver resolver = new JadeViewResolver();
		resolver.setPrefix(properties.getPrefix());
		resolver.setSuffix(properties.getSuffix());
		resolver.setCache(properties.isCache());
		resolver.setViewNames(properties.getViewNames());
		resolver.setContentType(properties.getContentType());
		resolver.setJadeConfiguration(jadeConfiguration);
		resolver.setOrder(Ordered.LOWEST_PRECEDENCE-10);
		return resolver;
	}

}
