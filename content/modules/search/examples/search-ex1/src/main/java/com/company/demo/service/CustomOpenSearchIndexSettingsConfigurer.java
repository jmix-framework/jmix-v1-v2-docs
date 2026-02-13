package com.company.demo.service;

// tag::import[]
import com.company.demo.entity.Order;
import io.jmix.searchopensearch.index.OpenSearchIndexSettingsConfigurationContext;
import io.jmix.searchopensearch.index.OpenSearchIndexSettingsConfigurer;
import org.opensearch.client.opensearch.indices.IndexSettings;
import org.springframework.stereotype.Component;

// end::import[]

// tag::index-settings-configurer[]
@Component
public class CustomOpenSearchIndexSettingsConfigurer implements OpenSearchIndexSettingsConfigurer {

    // end::index-settings-configurer[]
    // tag::configure[]
    @Override
    public void configure(OpenSearchIndexSettingsConfigurationContext context) {
        // end::configure[]
        // tag::configure-body[]
        IndexSettings.Builder commonSettingsBuilder = context.getCommonIndexSettingsBuilder();
        commonSettingsBuilder
                .maxResultWindow(15000)
                .analysis(analysisBuilder ->
                        analysisBuilder.analyzer("customized_standard", analyzerBuilder ->
                                analyzerBuilder.standard(stdAnalyzerBuilder ->
                                        stdAnalyzerBuilder.maxTokenLength(100)
                                )
                        )
                );

        IndexSettings.Builder orderSettingsBuilder = context.getEntityIndexSettingsBuilder(Order.class);
        orderSettingsBuilder
                .maxResultWindow(15000)
                .maxRegexLength(2000)
                .analysis(analysisBuilder ->
                        analysisBuilder.analyzer("customized_standard", analyzerBuilder ->
                                analyzerBuilder.standard(stdAnalyzerBuilder ->
                                        stdAnalyzerBuilder.maxTokenLength(150)
                                )
                        )
                );
        // end::configure-body[]
        // tag::configure[]
    }
    // end::configure[]
    // tag::index-settings-configurer[]
}
// end::index-settings-configurer[]
