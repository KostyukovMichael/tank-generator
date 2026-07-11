package ru.kostyukov.tankgenerator.services.parse;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;
import ru.kostyukov.tankgenerator.exceptions.OpenApiParsingException;
import ru.kostyukov.tankgenerator.models.Endpoint;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Service
public class OpenApiParserService {

  private final ObjectMapper objectMapper;
  private final Faker faker = new Faker();

  public OpenApiParserService(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public List<Endpoint> parseOpenApi(String content) {
    List<Endpoint> endpoints = new ArrayList<>();

    SwaggerParseResult parseResult = new OpenAPIV3Parser().readContents(content, null, null);
    OpenAPI openAPI = parseResult.getOpenAPI();

    if (openAPI == null || openAPI.getPaths() == null) {
      throw new OpenApiParsingException("OpenApi doesn't recognised");
    }

    openAPI
        .getPaths()
        .forEach(
            (path, pathItem) ->
                pathItem
                    .readOperationsMap()
                    .forEach(
                        (httpMethod, operation) -> {
                          String dummyBody = generateDummyBody(operation);
                          endpoints.add(
                              Endpoint.builder()
                                  .path(path)
                                  .method(httpMethod.name())
                                  .body(dummyBody)
                                  .build());
                        }));

    return endpoints;
  }

  private String generateDummyBody(Operation operation) {
    var requestBody = operation.getRequestBody();
    if (requestBody == null) {
      return null;
    }

    var content = requestBody.getContent();
    if (content == null || !content.containsKey("application/json")) {
      return null;
    }

    MediaType mediaType = content.get("application/json");
    Schema<?> schema = mediaType.getSchema();
    if (schema == null) {
      return "{}";
    }

    Object dummy = schemaHelper(schema, null);

    try {
      return objectMapper.writeValueAsString(dummy);
    } catch (JacksonException e) {
      throw new OpenApiParsingException("error while parsing", e);
    }
  }

  private Object schemaHelper(Schema<?> schema, String fieldName) {
    if (schema == null) {
      return null;
    }

    String type = schema.getType();
    if (type == null) {
      type =
          schema.getProperties() != null && !schema.getProperties().isEmpty() ? "object" : "string";
    }

    return switch (type) {
      case "integer", "number" -> generateNumber(fieldName);

      case "boolean" -> faker.bool().bool();

      case "string" -> generateString(fieldName);

      case "array" -> {
        Schema<?> itemsSchema = schema.getItems();

        if (itemsSchema != null) {
          yield List.of(schemaHelper(itemsSchema, fieldName));
        }
        yield List.of();
      }

      case "object" -> {
        Map<String, Schema> properties = schema.getProperties();

        if (properties == null || properties.isEmpty()) {
          yield Map.of();
        }

        Map<String, Object> nestedObjects = new LinkedHashMap<>();
        properties.forEach(
            (key, valueSchema) -> nestedObjects.put(key, schemaHelper(valueSchema, key)));

        yield nestedObjects;
      }

      default ->
          throw new OpenApiParsingException(
              "unexpected type: " + type + " in OpenApi specification");
    };
  }

  private Object generateNumber(String fieldName) {
    String name = fieldName != null ? fieldName.toLowerCase() : "";

    if (name.contains("id")) {
      return faker.number().numberBetween(1, 99999);
    }
    if (name.contains("age")) {
      return faker.number().numberBetween(18, 90);
    }
    if (name.contains("price") || name.contains("cost")) {
      return faker.number().randomDouble(2, 1, 10000);
    }

    return faker.number().numberBetween(1, 1000);
  }

  private String generateString(String fieldName) {
    String name = fieldName != null ? fieldName.toLowerCase() : "";

    if (name.contains("name")) {

      if (name.contains("first")) {
        return faker.name().firstName();
      }

      if (name.contains("last")) {
        return faker.name().lastName();
      }

      return faker.name().name();
    }

    if (name.contains("phone")) {
      return faker.phoneNumber().phoneNumber();
    }

    if (name.contains("email")) {
      return faker.internet().emailAddress();
    }

    if (name.contains("country")) {
      return faker.country().name();
    }

    if (name.contains("city")) {
      return faker.address().city();
    }

    if (name.contains("address")) {
      return faker.address().fullAddress();
    }

    return faker.lorem().word();
  }
}
