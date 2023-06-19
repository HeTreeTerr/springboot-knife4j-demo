# springboot-knife4j-demo

## springboot 2.1.0版本适配
1、pom.xml
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.1.0.RELEASE</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
    
<!-- 省略 -->

<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-spring-boot-starter</artifactId>
    <version>2.0.4</version>
</dependency>
```
2、com.geekplus.rms.analysis.config.Knife4jConfiguration
```java
@Configuration
//@EnableSwagger2WebMvc //去掉
@EnableSwagger2 //补充
@EnableKnife4j //补充
@ConditionalOnProperty(value = "knife4j.enable",havingValue = "true")
public class Knife4jConfiguration {
    //省略
}
```