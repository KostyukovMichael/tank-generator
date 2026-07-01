package ru.kostyukov.tankgenerator.services.ammo;

import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.http.HttpHeaders;
import ru.kostyukov.tankgenerator.models.HttpRequest;

class HttpFormatter {
  private static final List<String> METHODS_WITH_BODY = List.of("POST", "PUT", "PATCH");

  public static String format(HttpRequest httpRequest) {
    StringBuilder httpBuilder = new StringBuilder();

    httpBuilder
        .append(httpRequest.method())
        .append(" ")
        .append(httpRequest.path())
        .append(" HTTP/1.1\r\n");

    httpRequest
        .headers()
        .forEach(
            (header, value) ->
                httpBuilder.append(header).append(": ").append(value).append("\r\n"));

    String body = httpRequest.body();

    if (body != null && !body.isBlank() && METHODS_WITH_BODY.contains(httpRequest.method())) {
      byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
      httpBuilder.append(HttpHeaders.CONTENT_TYPE).append(": application/json\r\n");
      httpBuilder.append(HttpHeaders.CONTENT_LENGTH).append(": ").append(bodyBytes.length).append("\r\n");
      httpBuilder.append("\r\n").append(body);
    } else {
      httpBuilder.append("\r\n");
    }

    return httpBuilder.toString();
  }
}
