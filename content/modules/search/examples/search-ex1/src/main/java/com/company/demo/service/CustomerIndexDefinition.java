package com.company.demo.service;

import com.company.demo.entity.Customer;
import com.company.demo.entity.Status;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.search.index.annotation.AutoMappedField;
import io.jmix.search.index.annotation.IndexablePredicate;
import io.jmix.search.index.annotation.JmixEntitySearchIndex;
import io.jmix.search.index.annotation.ManualMappingDefinition;
import io.jmix.search.index.mapping.MappingDefinition;
import io.jmix.search.index.mapping.MappingDefinitionElement;
import io.jmix.search.index.mapping.propertyvalue.impl.FilePropertyValueExtractor;
import io.jmix.search.index.mapping.propertyvalue.impl.SimplePropertyValueExtractor;
import io.jmix.search.index.mapping.strategy.impl.AutoMappingStrategy;

import java.util.function.Predicate;

// tag::interface[]
@JmixEntitySearchIndex(entity = Customer.class)
public interface CustomerIndexDefinition {
    // end::interface[]
    // tag::method[]
    @AutoMappedField(
            includeProperties = {"firstName", "lastName"},
            analyzer = "german")
    void customerMapping();
    // end::method[]

    // tag::manual-mapping[]
    @ManualMappingDefinition // <1>
    default MappingDefinition mapping(FilePropertyValueExtractor filePropertyValueExtractor) { // <2>
        return MappingDefinition.builder()
                .addElement(
                        MappingDefinitionElement.builder()
                                .includeProperties("*") // <3>
                                .excludeProperties("hobby", "maritalStatus") // <4>
                                .withFieldMappingStrategyClass(AutoMappingStrategy.class) // <5>
                                .withPropertyValueExtractor(filePropertyValueExtractor) // <6>
                                .build()
                )
                .build();
    }
    // end::manual-mapping[]
    // tag::predicate[]

    @IndexablePredicate
    default Predicate<Customer> indexCustomerWithGoldStatusOnlyPredicate(DataManager dataManager) {
        return (instance) -> {
            Id<Customer> id = Id.of(instance);
            Customer reloadedInstance = dataManager.load(id)
                    .fetchPlanProperties("status")
                    .one();
            Status status = reloadedInstance.getStatus();
            return Status.GOLD.equals(status);
        };
    }
    // end::predicate[]

    // tag::text-config[]
    @ManualMappingDefinition
    default MappingDefinition mappingMethod(AutoMappingStrategy autoMappingStrategy,
                                      SimplePropertyValueExtractor simplePropertyValueExtractor) {
        return MappingDefinition.builder()
                .addElement(
                        MappingDefinitionElement.builder()
                                .includeProperties("*")
                                .excludeProperties("firstName", "lastName") // <1>
                                .withFieldMappingStrategyClass(AutoMappingStrategy.class)
                                .build()
                )
                .addElement(
                        MappingDefinitionElement.builder()
                                .includeProperties("firstName") // <2>
                                .withFieldMappingStrategy(autoMappingStrategy) // <3>
                                .withFieldConfiguration( // <4>
                                        "{\n" +
                                                "    \"type\": \"text\",\n" +
                                                "    \"analyzer\": \"standard\",\n" +
                                                "    \"boost\": 2\n" +
                                                "}"
                                )
                                .build()
                )
                .addElement(
                        MappingDefinitionElement.builder()
                                .includeProperties("lastName")
                                .withFieldConfiguration( // <5>
                                        "{\n" +
                                                "    \"type\": \"text\",\n" +
                                                "    \"analyzer\": \"english\"\n" +
                                                "}"
                                )
                                .withPropertyValueExtractor(simplePropertyValueExtractor) // <6>
                                .withOrder(1) // <7>
                                .build()
                )
                .build();
    }
// end::text-config[]
    // tag::interface[]
}
// end::interface[]