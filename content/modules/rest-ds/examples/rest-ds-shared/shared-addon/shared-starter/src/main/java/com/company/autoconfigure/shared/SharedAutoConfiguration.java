package com.company.autoconfigure.shared;

import com.company.shared.SharedConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({SharedConfiguration.class})
public class SharedAutoConfiguration {
}

