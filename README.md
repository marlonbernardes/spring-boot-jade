## Spring Boot autoconfig support for Jade4j

### Usage via Maven 
#### Just add the following dependency to your `pom.xml`
```xml
<dependency>
	<!-- Its not yet published to maven's central repository!
	 This comment will be removed once it's there. -->
  <groupId>com.github.instaweb</groupId>
  <artifactId>spring-boot-jade</artifactId>
  <version>0.9.0</version>
</dependency>
```


### Example webapp, with template (in `classpath:/templates/home.jade`):

```jade
doctype html
html
  head
    title #{title}
  body
    p #{message}
    span #{time}
```

application code:

```java
@SpringBootApplication
@Controller
public static class Application {

	@RequestMapping("/")
	public String home(Map<String, Object> model) {
		model.put("time", new Date());
		model.put("message", "Hello World");
		model.put("title", "Hello App");
		return "home";
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
```

Run the app and then load the HTML page at http://localhost:8080.


