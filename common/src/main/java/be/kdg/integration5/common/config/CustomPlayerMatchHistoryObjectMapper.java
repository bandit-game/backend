package be.kdg.integration5.common.config;

import be.kdg.integration5.common.domain.HistorySubType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import org.reflections.Reflections;

import java.util.Set;

public  class CustomPlayerMatchHistoryObjectMapper {
    public static ObjectMapper get() {

        ObjectMapper objectMapper = new ObjectMapper();

        Reflections reflections = new Reflections("be.kdg.integration5");
        Set<Class<?>> subtypes = reflections.getTypesAnnotatedWith(HistorySubType.class);

        for (Class<?> subType : subtypes) {
            HistorySubType annotation = subType.getAnnotation(HistorySubType.class);
            if (annotation != null) {
                String typeName = annotation.value();
                objectMapper.registerSubtypes(new NamedType(subType, typeName));
            }
        }

        return objectMapper;
    }
}
