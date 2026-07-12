//package com.ust.shopkart.tests;
//
//import com.ust.shopkart.config.DatabaseConfig;
//import com.ust.shopkart.factory.OrderFactory;
//import com.ust.shopkart.model.Order;
//import com.ust.shopkart.repository.OrderRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//
//import static com.ust.shopkart.builder.OrderBuilder.anOrder;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//public class OrderTest {
//
//    private OrderRepository repo;
//    private OrderFactory factory;
//    private DatabaseConfig config;
//
//    @BeforeEach
//    void setup() {
//        config=DatabaseConfig.fromEnvironmentCredential();
//        repo = new OrderRepository(config);
//        factory = new OrderFactory(repo);
//        repo.reset();
//    }
//
//    @Test
//    void createsOrder() {
//        factory.persisted(anOrder().build());
//
//        assertEquals(1, repo.count());
//    }
//
//    @Test
//    void countsOrders() {
//        factory.persisted(anOrder().build());
//
//        assertEquals(1, repo.count());
//    }
//
//    @Nested
//    class Exercise1 {
//
//        @Test
//        void createsOrderWithDefaultBuilder() {
//            Order order = factory.persisted(anOrder().build());
//
//            assertEquals("SKU-1", order.sku());
//            assertEquals(1, order.qty());
//            assertEquals(1299.00, order.price());
//            assertEquals(1, repo.count());
//        }
//
//        @Test
//        void createsOrderWithOverriddenQty() {
//            Order order = factory.persisted(anOrder().withQty(3).build());
//
//            assertEquals(3, order.qty());
//            assertEquals(1, repo.count());
//        }
//
//        @Test
//        void createsOrderWithOverriddenSkuAndPrice() {
//            Order order = factory.persisted(
//                    anOrder().withSku("SKU-99").withPrice(499.00).build()
//            );
//
//            assertEquals("SKU-99", order.sku());
//            assertEquals(499.00, order.price());
//            assertEquals(1, repo.count());
//        }
//    }
//
//    @Nested
//    class Exercise2 {
//
//        @Test
//        void countOwnDataFirst() {
//            factory.persisted(anOrder().build());
//
//            assertEquals(1, repo.count());
//        }
//
//        @Test
//        void countOwnDataSecond() {
//            factory.persisted(anOrder().build());
//            factory.persisted(anOrder().withQty(2).build());
//
//            assertEquals(2, repo.count());
//        }
//
//        @Test
//        void countOwnDataIndependentOfExecutionOrder() {
//            factory.persisted(anOrder().withSku("SKU-2").build());
//
//            assertEquals(1, repo.count());
//        }
//    }
//
//    @Nested
//    class Exercise3 {
//
//        @Test
//        void firstTestCreatesItsOwnRow() {
//            factory.persisted(anOrder().build());
//
//            assertEquals(1, repo.count());
//        }
//
//        @Test
//        void secondTestCreatesItsOwnRow() {
//            factory.persisted(anOrder().withQty(5).build());
//
//            assertEquals(1, repo.count());
//        }
//
//        @Test
//        void thirdTestCreatesItsOwnRow() {
//            factory.persisted(anOrder().shipped().build());
//
//            assertEquals(1, repo.count());
//        }
//
//        @Test
//        void fourthTestCreatesMultipleRowsItOwns() {
//            factory.persisted(anOrder().withQty(1).build());
//            factory.persisted(anOrder().withQty(2).build());
//            factory.persisted(anOrder().withQty(3).build());
//
//            assertEquals(3, repo.count());
//        }
//    }
//
//    @Nested
//    class Milestone {
//
//        @Test
//        void repositorySavesAndCounts() {
//            repo.save(anOrder().build());
//
//            assertEquals(1, repo.count());
//        }
//
//        @Test
//        void repositoryResetClearsData() {
//            repo.save(anOrder().build());
//            repo.reset();
//
//            assertEquals(0, repo.count());
//        }
//
//        @Test
//        void builderProducesSensibleDefaults() {
//            Order order = anOrder().build();
//
//            assertNotNull(order.sku());
//            assertEquals(1, order.qty());
//            assertFalse(order.shipped());
//        }
//
//        @Test
//        void factoryPersistsInOneLine() {
//            Order order = factory.persisted(anOrder().withQty(3).build());
//
//            assertEquals(3, order.qty());
//            assertEquals(1, repo.count());
//        }
//
//        @Test
//        void isolationHoldsWhenRunAlone() {
//            factory.persisted(anOrder().build());
//
//            assertEquals(1, repo.count());
//        }
//
//        @Test
//        void isolationHoldsWhenRunTogether() {
//            factory.persisted(anOrder().withQty(7).build());
//
//            assertEquals(1, repo.count());
//        }
//
//        @Test
//        void isolationHoldsInReverseOrder() {
//            factory.persisted(anOrder().withSku("SKU-Z").build());
//
//            assertEquals(1, repo.count());
//        }
//    }
//}