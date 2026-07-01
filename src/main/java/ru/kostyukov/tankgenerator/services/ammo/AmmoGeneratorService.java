package ru.kostyukov.tankgenerator.services.ammo;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import ru.kostyukov.tankgenerator.dto.GenerationRequest;
import ru.kostyukov.tankgenerator.models.Endpoint;
import ru.kostyukov.tankgenerator.models.HttpRequest;

@Service
public class AmmoGeneratorService implements AmmoGenerator {
  private static final String USER_AGENT = "Tank";

  @Override
  public String generateAmmo(List<Endpoint> endpoints, GenerationRequest generationRequest) {
    StringBuilder ammoBuilder = new StringBuilder();

    endpoints.forEach(
        endpoint -> {
          String path = PathResolver.resolvePath(endpoint.getPath());

          Map<String, String> headers = new LinkedHashMap<>();
          headers.put(HttpHeaders.HOST, generationRequest.getTargetHost());
          headers.put(HttpHeaders.USER_AGENT, USER_AGENT);
          headers.put(HttpHeaders.CONNECTION, "Keep-Alive");

          String authToken = generationRequest.getAuthToken();
          if (authToken != null && !authToken.isEmpty()) {
            headers.put(HttpHeaders.AUTHORIZATION, "Bearer" + authToken.strip());
          }

          HttpRequest httpRequest =
              new HttpRequest(
                  endpoint.getMethod(), endpoint.getPath(), headers, endpoint.getBody());

          String httpRequestString = HttpFormatter.format(httpRequest);

          byte[] httpRequestBytes = httpRequestString.getBytes(StandardCharsets.UTF_8);

          String tag =
              endpoint.getMethod()
                  + "_"
                  + path.replace("/", "_").toLowerCase().replaceAll("[^a-z0-9_]+", "");

          ammoBuilder
              .append(httpRequestBytes.length)
              .append(" ")
              .append(tag)
              .append("\n")
              .append(httpRequestString);
        });

    return ammoBuilder.toString();
  }
}
