## Spring Boot autoconfig support for Jade4j

### Usage via Maven 
##### 1. Add the following snapshot dependency to your `pom.xml`
```xml
<dependency>
	<groupId>com.github.instaweb</groupId>
	<artifactId>spring-boot-jade</artifactId>
	<version>0.9.0-SNAPSHOT</version>
</dependency>
```
##### 2. Be sure to add sonatype snapshots to your repository list (`pom.xml` or `~.m2/settings.xml`)
```xml
<repositories>
	<repository>
		<id>sonatype-snapshots</id>
		<url>https://oss.sonatype.org/content/repositories/snapshots</url>
		<releases>
			<enabled>false</enabled>
		</releases>
		<snapshots>
			<enabled>true</enabled>
		</snapshots>
	</repository>
</repositories>
```



### Example webapp
#### Template in `classpath:/templates/home.jade`

```jade
doctype html
html
  head
    title #{title}
  body
    p #{message}
    span #{time}
```

#### Application.java

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

**Thats it!**. Just run the app and then load the HTML page at http://localhost:8080.


