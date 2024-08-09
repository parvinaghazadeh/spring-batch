
## Spring Batch
Note that with Spring boot > 3.0, the @EnableBatchProcessing is discouraged. Also, JobBuilderFactory and StepBuilderFactory are deprecated

> VirtualThreadTaskExecutor use in java > 21


> reader from DB
>>@Bean
public JdbcCursorItemReader<Person> itemReader() {
String sql = "select * from person";
return new JdbcCursorItemReaderBuilder<Person>()
.name("personItemReader")
.dataSource(dataSource())
.sql(sql)
.beanRowMapper(Person.class)
.build();
}


### resourse
#### samples
- https://github.com/eugenp/tutorials/tree/master/spring-batch-2
- https://spring.io/blog/2021/01/27/spring-batch-on-kubernetes-efficient-batch-processing-at-scale


#### learning sites
- https://www.baeldung.com/spring-boot-spring-batch
- https://medium.com/@kundanscode/batch-processing-in-spring-boot-and-how-to-implement-it-46746195fe77
- https://spring.io/guides/gs/batch-processing
- https://www.baeldung.com/introduction-to-spring-batch

#### cloud-native
- https://github.com/mminella/cloud-native-batch/blob/master/batch-job/src/main/java/io/spring/batch/EnrichmentProcessor.java
- https://github.com/zacscoding/cloud-native-spring-batch/blob/main/batch-service/src/main/java/com/github/zacscoding/batch/batch/BatchController.java
- https://spring.io/blog/2021/01/27/spring-batch-on-kubernetes-efficient-batch-processing-at-scale

#### reader
https://docs.spring.io/spring-batch/reference/readers-and-writers/database.html
https://medium.com/@shashibheemanapally/using-a-jparepository-to-read-and-write-records-in-a-database-using-spring-batch-fb8aeb40ec57