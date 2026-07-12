//package com.ust.shopkart.tests;
//
//import com.ust.shopkart.config.DatabaseConfig;
//import com.ust.shopkart.factory.OrderFactory;
//import com.ust.shopkart.report.ExtentTestListener;
//import com.ust.shopkart.repository.OrderRepository;
//import com.ust.shopkart.support.DbSupport;
//import com.ust.shopkart.support.TestEnvironment;
//import org.flywaydb.core.Flyway;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.testcontainers.containers.MySQLContainer;
//
//@ExtendWith(ExtentTestListener.class)
//public abstract class AbstractOrderTest {
//    static MySQLContainer<?> mysql;
//    static DatabaseConfig config;
//    static OrderRepository repository;
//    static OrderFactory factory;
//    static DbSupport dbSupport;
//
//    @BeforeAll
//    static void setupDatabase() {
//        boolean useContainer = Boolean.parseBoolean(TestEnvironment.optional("USE_TESTCONTAINERS", "false"));
//
//        if (useContainer) {
//            mysql = new MySQLContainer<>("mysql:8.0")
//                    .withDatabaseName("orders_db")
//                    .withUsername("test")
//                    .withPassword("test");
//            mysql.start();
//
//            config = new DatabaseConfig(
//                    mysql.getJdbcUrl() + "?allowPublicKeyRetrieval=true&useSSL=false",
//                    mysql.getUsername(),
//                    mysql.getPassword()
//            );
//        } else {
//            config = DatabaseConfig.fromEnvironmentCredential();
//        }
//
//        Flyway.configure()
//                .dataSource(config.jdbcUrl(), config.username(), config.password())
//                .locations("classpath:db/migration")
//                .load()
//                .migrate();
//
//        repository = new OrderRepository(config);
//        factory = new OrderFactory(repository);
//        dbSupport = new DbSupport(config);
//    }
//
//    @AfterAll
//    static void tearDownDatabase() {
//        if (mysql != null) {
//            mysql.stop();
//        }
//    }
//
//    @BeforeEach
//    void resetDatabase() {
//        repository.reset();
//    }
//}