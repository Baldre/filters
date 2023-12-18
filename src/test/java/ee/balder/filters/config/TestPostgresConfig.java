package ee.balder.filters.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@Testcontainers
@TestConfiguration
public class TestPostgresConfig {

    @Container
    public static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("filters")
            .withUsername("USER_DEMO")
            .withPassword("PASSWORD_DEMO")
            .withExposedPorts(5432);


    @Bean
    public DataSource dataSource() {
        postgres.start();

        String url = String.format("jdbc:postgresql://%s:%d/%s",
                postgres.getHost(),
                postgres.getMappedPort(5432),
                postgres.getDatabaseName()
        );

        return DataSourceBuilder.create()
                .username(postgres.getUsername())
                .password(postgres.getPassword())
                .url(url)
                .driverClassName(org.postgresql.Driver.class.getName())
                .build();
    }

}
