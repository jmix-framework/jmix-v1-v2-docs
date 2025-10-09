package com.company.orders;

import com.company.shared.service.InventoryService;
import io.jmix.core.JmixOrder;
import io.jmix.restds.util.RemoteServiceConfigurationCustomizer;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Component;

@Component
@Order(JmixOrder.HIGHEST_PRECEDENCE - 10)
public class SharedServicesConfigurer implements RemoteServiceConfigurationCustomizer {

    @Override
    public TypeFilter getScannerIncludeFilter() {
        return (metadataReader, metadataReaderFactory) ->
                metadataReader.getClassMetadata().getClassName().equals(InventoryService.class.getName());
    }

    @Override
    public ServiceParameters getServiceParameters(Class<?> serviceInterface) {
        return serviceInterface.equals(InventoryService.class) ?
                new ServiceParameters().withStoreName("products") : null;
    }
}
