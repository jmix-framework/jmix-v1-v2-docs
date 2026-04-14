package com.company.orders;

import com.company.shared.entity.Product;
import io.jmix.core.JmixOrder;
import io.jmix.core.MetadataMutationTools;
import io.jmix.core.MetadataPostProcessor;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.core.metamodel.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(JmixOrder.HIGHEST_PRECEDENCE - 10)
public class SharedEntitiesConfigurer implements MetadataPostProcessor {

    @Autowired
    private MetadataMutationTools metadataMutationTools;

    @Override
    public void process(Session session) {
        for (MetaClass metaClass : session.getClasses()) {
            if (metaClass.getJavaClass().getPackage().equals(Product.class.getPackage())) {
                metadataMutationTools.setStore(metaClass, "products");
            }
        }
    }
}
