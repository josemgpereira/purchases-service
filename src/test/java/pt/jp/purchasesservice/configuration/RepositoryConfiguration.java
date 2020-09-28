package pt.jp.purchasesservice.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"pt.jp.purchasesservice.models"})
@EnableJpaRepositories(basePackages = {"pt.jp.purchasesservice.repositories"})
@EnableTransactionManagement
public class RepositoryConfiguration {
}