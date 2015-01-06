/*
 * Copyright 2012-2013 the original author or authors.
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

package com.github.instaweb.jade;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import de.neuland.jade4j.template.TemplateLoader;


public class JadeResourceTemplateLoader implements TemplateLoader, ResourceLoaderAware {

	private String prefix = "";

	private String suffix = "";
	
	private String charSet = "UTF-8";

	private ResourceLoader resourceLoader = new DefaultResourceLoader();
	
	public JadeResourceTemplateLoader() {
	}

	public JadeResourceTemplateLoader(String prefix, String suffix) {
		super();
		this.prefix = prefix;
		this.suffix = suffix;
	}
	
	/**
	 * @param charSet the charSet to set
	 */
	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	/**
	 * @param resourceLoader the resourceLoader to set
	 */
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
	
	@Override
	public long getLastModified(String name) throws IOException {
		// FIXME
		return 0;
	}

	@Override
	public Reader getReader(String name) throws IOException {
		return new InputStreamReader(resourceLoader.getResource(name).getInputStream(), charSet);
	}

}
