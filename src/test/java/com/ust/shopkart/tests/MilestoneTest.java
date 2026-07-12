//package com.ust.shopkart.tests;
//
//import com.ust.shopkart.model.Order;
//import org.junit.jupiter.api.Test;
//
//import static com.ust.shopkart.builder.OrderBuilder.anOrder;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//class MilestoneTest extends AbstractOrderTest {
//
//    @Test
//    void m1_databaseIsIsolatedAndReachable() throws Exception {
////        assertTrue(MYSQL.isRunning());
//        assertTrue(dbSupport.isReachable());
//    }
//
//    @Test
//    void m2_repositorySavesAndCounts() {
//        factory.persisted(anOrder().build());
//
//        assertEquals(1, repository.count());
//    }
//
//    @Test
//    void m3_builderAndFactoryProduceOrder() {
//        Order order = factory.persisted(anOrder().withSku("SKU-M3").withQty(5).build());
//
//        assertNotNull(order.id());
//        assertEquals("SKU-M3", order.sku());
//        assertEquals(5, order.qty());
//    }
//
//    @Test
//    void m4_testsAreIsolatedByReset() {
//        assertEquals(0, repository.count());
//
//        factory.persisted(anOrder().build());
//
//        assertEquals(1, repository.count());
//    }
//
//    @Test
//    void m5_dataIsVerifiedIndependentlyInDatabase() throws Exception {
//        factory.persisted(anOrder().withSku("SKU-M5").build());
//
//        assertTrue(dbSupport.orderExists("SKU-M5"));
//        assertEquals(1, dbSupport.countOrders());
//    }
//}