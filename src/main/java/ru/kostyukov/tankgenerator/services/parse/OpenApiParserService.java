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
import org.springframework.stereotype.Service;
import ru.kostyukov.tankgenerator.exceptions.OpenApiParsingException;
import ru.kostyukov.tankgenerator.models.Endpoint;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Service
public class OpenApiParserService {

  private final ObjectMapper objectMapper;

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

    Object dummy = schemaHelper(schema);

    try {
      return objectMapper.writeValueAsString(dummy);
    } catch (JacksonException e) {
      throw new OpenApiParsingException("error while parsing", e);
    }
  }

  private Object schemaHelper(Schema<?> schema) {
    if (schema == null) {
      return null;
    }

    String type = schema.getType() == null ? "string" : schema.getType();

    return switch (type) {
      case "integer", "number" -> 1;

      case "boolean" -> true;

      case "string" -> "test";

      case "array" -> {
        Schema<?> itemsSchema = schema.getItems();

        if (itemsSchema != null) {
          yield List.of(schemaHelper(itemsSchema));
        }
        yield List.of();
      }

      case "object" -> {
        Map<String, Schema> properties = schema.getProperties();

        if (properties == null || properties.isEmpty()) {
          yield Map.of();
        }

        Map<String, Object> nestedObjects = new LinkedHashMap<>();
        properties.forEach((key, valueSchema) -> nestedObjects.put(key, schemaHelper(valueSchema)));

        yield nestedObjects;
      }

      default ->
          throw new OpenApiParsingException(
              "unexpected type: " + type + " in OpenApi specification");
    };
  }
}
