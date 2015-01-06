package com.github.instaweb.jade.web;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractTemplateView;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.JadeTemplate;

public class JadeView extends AbstractTemplateView {

	private final JadeTemplate template;
	private final JadeConfiguration config;
	
	public JadeView(JadeConfiguration config, JadeTemplate template) {
		this.config = config;
		this.template = template;
	}

	@Override
	protected void renderMergedTemplateModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		config.renderTemplate(template, model, response.getWriter());
	}

}

