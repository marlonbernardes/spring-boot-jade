package com.github.instaweb.jade;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.instaweb.jade.AutoApplicationTests.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest("server.port:0")
@WebAppConfiguration
public class AutoApplicationTests {

	@Autowired
	private EmbeddedWebApplicationContext context;
	private int port;

	@Before
	public void init() {
		port = context.getEmbeddedServletContainer().getPort();
	}

	@Test
	public void testHomePage() throws Exception {
		String body = new TestRestTemplate().getForObject("http://localhost:" + port,
				String.class);
		assertTrue(body.contains("<title>Hello App</title>"));
		assertTrue(body.contains("<p>Hello World</p>"));
	}

	@Test
	public void testExtendsPage() throws Exception {
		String body = new TestRestTemplate().getForObject("http://localhost:" + port
				+ "/extends", String.class);
		assertTrue(body.contains("<title>Article Title</title>"));
		assertTrue(body.contains("<h1>My Article</h1>"));
		assertTrue(body.contains("<h1>welcome</h1>"));
	}

	@Test
	public void testIncludePage() throws Exception {
		String body = new TestRestTemplate().getForObject("http://localhost:" + port
				+ "/include", String.class);
		assertTrue(body.contains("<title>Testing Include</title>"));
		assertTrue(body.contains("<h1>Include worked</h1>"));
		assertTrue(body.contains("<h2>include-content</h2>"));
	}
	@Configuration
	@EnableAutoConfiguration
	@Controller
	public static class Application {

		@RequestMapping("/")
		public String home(Map<String, Object> model) {
			model.put("time", new Date());
			model.put("message", "Hello World");
			model.put("title", "Hello App");
			return "home";
		}

		@RequestMapping("/extends")
		public String extendsPage(Map<String, Object> model) {
			model.put("time", new Date());
			model.put("message", "Hello World");
			model.put("title", "Hello App");
			return "extends-content";
		}

		@RequestMapping("/include")
		public String includePage(Map<String, Object> model) {
			model.put("time", new Date());
			model.put("message", "Include worked");
			model.put("title", "Testing Include");
			return "include";
		}


		public static void main(String[] args) {
			SpringApplication.run(Application.class, args);
		}

	}

}
