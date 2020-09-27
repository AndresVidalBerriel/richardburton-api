package br.edu.ifrs.canoas.richardburton.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Year;

public class YearSerializer extends JsonSerializer<Year> {

    @Override
    public void serialize(Year year, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {

        jsonGenerator.writeObject(year.getValue());
    }
}