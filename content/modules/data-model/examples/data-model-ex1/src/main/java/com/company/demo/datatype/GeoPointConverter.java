/*
 * Copyright 2020 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.company.demo.datatype;

// tag::datatype[]
import com.company.demo.entity.GeoPoint;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true) // <1>
public class GeoPointConverter implements AttributeConverter<GeoPoint, String> {

    @Override
    public String convertToDatabaseColumn(GeoPoint attribute) {
        if (attribute == null)
            return null;
        return attribute.latitude() + "|" + attribute.longitude();
    }

    @Override
    public GeoPoint convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;
        String[] strings = dbData.split("\\|");
        return new GeoPoint(Double.parseDouble(strings[0]), Double.parseDouble(strings[1]));
    }
}
// end::datatype[]
