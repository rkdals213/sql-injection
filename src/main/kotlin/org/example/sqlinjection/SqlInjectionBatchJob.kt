package org.example.sqlinjection

import jakarta.persistence.EntityManager
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.transaction.PlatformTransactionManager
import java.nio.file.Files
import java.nio.file.Paths

@Configuration
class SqlInjectionBatchJob(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val entityManager: EntityManager,
) {

    @Bean
    fun sqlInjectionBatchJob01(): Job {
        return JobBuilder("sqlInjectionBatchJob01", jobRepository)
            .start(sqlInjectionStep01())
            .build()
    }

    @Bean
    fun sqlInjectionStep01(): Step {
        return StepBuilder("sqlInjectionStep01", jobRepository)
            .tasklet({ _, _ ->
                val fileLine = StringBuilder()
                val resource = ClassPathResource("test2.sql")

                val path = Paths.get(resource.uri)
                Files.lines(path, Charsets.UTF_8).forEach { fileLine.append(it).append(" ") }

                val sqls = fileLine.split(";")

                for (sql in sqls) {
                    if (sql.trim().isEmpty()) {
                        continue
                    }

                    entityManager.createNativeQuery(sql).executeUpdate()
                }

                RepeatStatus.FINISHED
            }, transactionManager)
            .build()
    }
}
