package cat.informaticassa.icfact.infraestructura.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Converter
public class SetStringConverter implements AttributeConverter<Set<String>, String> {

    @Override
    public String convertToDatabaseColumn(Set<String> atribut) {

        if (atribut == null || atribut.isEmpty()) {
            return "";
        }

        return String.join(";", atribut);
    }

    @Override
    public Set<String> convertToEntityAttribute(String dades) {

        if (dades == null || dades.isBlank()) {
            return new TreeSet<>();
        }

        return Arrays.stream(dades.split(";"))
                .collect(Collectors.toCollection(TreeSet::new));
    }
}