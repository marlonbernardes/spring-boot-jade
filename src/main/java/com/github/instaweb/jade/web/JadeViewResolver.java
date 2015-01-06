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

package com.github.instaweb.jade.web;

import java.io.IOException;
import java.util.Locale;

import org.springframework.beans.propertyeditors.LocaleEditor;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.JadeTemplate;

/**
 * @author Marlon Bernardes
 *
 */
public class JadeViewResolver extends UrlBasedViewResolver {

	private JadeConfiguration jadeConfiguration;

	public JadeViewResolver() {
		setViewClass(JadeView.class);
	}

	public void setJadeConfiguration(JadeConfiguration jadeConfiguration) {
		this.jadeConfiguration = jadeConfiguration;
	}

	@Override
	protected View loadView(String viewName, Locale locale) throws Exception {
		String resource = resolveResource(viewName, locale);
		if (resource == null) {
			return null;
		}
		JadeView view = new JadeView(jadeConfiguration, createTemplate(resource));
		view.setApplicationContext(getApplicationContext());
		view.setServletContext(getServletContext());
		return view;
	}

	private JadeTemplate createTemplate(String resource) throws IOException {
		return jadeConfiguration.getTemplate(resource);
	}

	private String resolveResource(String viewName, Locale locale) {
		String l10n = "";
		if (locale != null) {
			LocaleEditor localeEditor = new LocaleEditor();
			localeEditor.setValue(locale);
			l10n = "_" + localeEditor.getAsText();
		}
		return resolveFromLocale(viewName, l10n);
	}

	private String resolveFromLocale(String viewName, String locale) {
		String name = getPrefix() + viewName + locale + getSuffix();
		Resource resource = getApplicationContext().getResource(name);
		if (resource == null || !resource.exists()) {
			if (locale.isEmpty()) {
				return null;
			}
			int index = locale.lastIndexOf("_");
			return resolveFromLocale(viewName, locale.substring(0, index));
		}
		return name;
	}

}
