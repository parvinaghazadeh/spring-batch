package com.parvin.springbatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.core.task.VirtualThreadTaskExecutor;

@Configuration
public class BatchConfiguration {

    @Value("${file.input}")
    private String fileInput;

    private final Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);

//    @Bean
//    public VirtualThreadTaskExecutor taskExecutor() {
//        return new VirtualThreadTaskExecutor("virtual-thread-executor");
//    }

    @Bean
    public FlatFileItemReader<DataModel> reader() {
        return new FlatFileItemReaderBuilder<DataModel>().name("coffeeItemReader")
                .resource(new ClassPathResource(fileInput))
                .delimited()
                .names(new String[] { "brand", "origin", "characteristics" })
                .fieldSetMapper(new BeanWrapperFieldSetMapper<DataModel>() {{
                    setTargetType(DataModel.class);
                }})
                .build();
    }

    @Bean
    public BatchItemProcessor processor() {
        return new BatchItemProcessor();
    }

//    @Bean
//    public JdbcBatchItemWriter<DataModel> writer(/*DataSource dataSource*/) {
//        return new JdbcBatchItemWriterBuilder<DataModel>().itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
//                .sql("INSERT INTO coffee (brand, origin, characteristics) VALUES (:brand, :origin, :characteristics)")
//                .dataSource(dataSource)
//                .build();
//    }
    @Bean
    public ItemWriter<DataModel> writer() {
        return items -> {
            logger.info("writer..." + items.size());
            for (DataModel item : items) {
                logger.info(item.toString());
            }
        };
    }

    @Bean
    public Job importUserJob(JobRepository jobRepository, JobCompletionNotificationListener listener, Step step1) {
        return new JobBuilder("importUserJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager, ItemWriter<DataModel> writer/*, VirtualThreadTaskExecutor taskExecutor*/) {
        return new StepBuilder("step1", jobRepository)
                .<DataModel, DataModel> chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer)
//                .taskExecutor(taskExecutor)
                .build();
    }

}