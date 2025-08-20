package com.carlosnazario.hackathon.converters;

import com.carlosnazario.hackathon.models.Parcela;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Collections;
import java.util.List;

@Converter
public class ListParcelaConverter implements AttributeConverter<List<Parcela>, String> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Parcela> attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Erro ao converter lista de parcelas para JSON", e);
        }
    }

    @Override
    public List<Parcela> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return Collections.emptyList();
        }
        try {
            return OBJECT_MAPPER.readValue(dbData, OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, Parcela.class));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Erro ao converter JSON para lista de parcelas", e);
        }
    }
}