package br.edu.ifrs.canoas.richardburton.books;

import java.io.IOException;
import java.time.Year;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class YearSerializer extends JsonSerializer<Year> {

    @Override
    public void serialize(Year year, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

        jsonGenerator.writeObject(year.getValue());
    }
}
