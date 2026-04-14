package com.company.demo;

import com.company.demo.entity.GeoPointEntity;
import com.company.demo.entity.Metric;
import com.company.demo.entity.OperationResult;
import com.company.demo.entity.Order;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.Metadata;
import io.jmix.core.MetadataTools;
import io.jmix.core.entity.EntityValues;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EntitiesTest {

    @Autowired
    DataManager dataManager;
    @Autowired
    Metadata metadata;
    @Autowired
    MetadataTools metadataTools;
    @Autowired
    SystemAuthenticator authenticator;

    @BeforeEach
    void setUp() {
        authenticator.begin();
    }

    @AfterEach
    void tearDown() {
        authenticator.end();
    }

    @Test
    void testCustomStore() {
        // tag::custom-store[]
        Metric metric = dataManager.create(Metric.class);
        metric.setName("test");
        metric.setValue(10.0);
        dataManager.save(metric);

        Metric metric1 = dataManager.load(Id.of(metric)).one();
        // end::custom-store[]
        assertEquals(metric, metric1);
        assertNotSame(metric, metric1);
    }

    @Test
    void testSimpleDto() {
        OperationResult operationResult = metadata.create(OperationResult.class);
        assertNotNull(EntityValues.getId(operationResult));
    }

    @Test
    void testInstanceName() {
        GeoPointEntity geoPointEntity = metadata.create(GeoPointEntity.class);
        geoPointEntity.setLatitude(53.20076);
        geoPointEntity.setLongitude(50.098603);

        String instanceName = metadataTools.getInstanceName(geoPointEntity);
        assertEquals("Latitude: 53.200760, Longitude: 50.098603", instanceName);
    }

    @Test
    void testPostConstruct() {
        Order order = metadata.create(Order.class);
        assertEquals(LocalDate.now(), order.getDate());
    }
}
