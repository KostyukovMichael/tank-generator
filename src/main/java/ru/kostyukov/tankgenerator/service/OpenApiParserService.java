package ru.kostyukov.tankgenerator.service;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import ru.kostyukov.tankgenerator.model.Endpoint;

@Service
public class OpenApiParserService {

  public List<Endpoint> parseOpenApi(String content) {
    List<Endpoint> endpoints = new ArrayList<>();

    SwaggerParseResult parseResult = new OpenAPIV3Parser().readContents(content, null, null);
    OpenAPI openAPI = parseResult.getOpenAPI();

    if (openAPI == null || openAPI.getPaths() == null) {
      throw new IllegalArgumentException("не удалось распознать OpenAPI");
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

    MediaType mediaType = content.get("applicaton/json");
    Schema<?> schema = mediaType.getSchema();
    if (schema == null) {
      return "{}";
    }

    Map<String, Schema> properties = schema.getProperties();
    if (properties == null || properties.isEmpty()) {
      return "{}";
    }

    return properties.entrySet().stream()
        .map(
            entry -> {
              String fieldName = entry.getKey();
              String rawType = entry.getValue().getType();
              String type = rawType == null ? "string" : rawType;

              String value =
                  switch (type) {
                    case "integer", "numeric" -> "1";
                    case "boolean" -> "true";
                    default -> "\"test\"";
                  };

              return "\"" + fieldName + "\": " + value;
            })
        .collect(Collectors.joining(", "));
  }
}
