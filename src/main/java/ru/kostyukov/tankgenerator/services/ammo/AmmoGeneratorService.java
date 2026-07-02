package ru.kostyukov.tankgenerator.services.ammo;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import ru.kostyukov.tankgenerator.config.TankGeneratorProperties;
import ru.kostyukov.tankgenerator.dto.GenerationRequest;
import ru.kostyukov.tankgenerator.models.Endpoint;
import ru.kostyukov.tankgenerator.models.HttpRequest;

@Service
public class AmmoGeneratorService implements AmmoGenerator {
  private final TankGeneratorProperties properties;

  public AmmoGeneratorService(TankGeneratorProperties properties) {
    this.properties = properties;
  }

  @Override
  public String generateAmmo(List<Endpoint> endpoints, GenerationRequest generationRequest) {
    StringBuilder ammoBuilder = new StringBuilder();

    endpoints.forEach(
        endpoint -> {
          HttpRequest httpRequest = getHttpRequest(generationRequest, endpoint);

          String httpRequestString = HttpFormatter.format(httpRequest);

          byte[] httpRequestBytes = httpRequestString.getBytes(StandardCharsets.UTF_8);

          String tag = TagGenerator.generateTag(httpRequest.method(), httpRequest.path());

          ammoBuilder
              .append(httpRequestBytes.length)
              .append(" ")
              .append(tag)
              .append("\n")
              .append(httpRequestString);
        });

    return ammoBuilder.toString();
  }

  private HttpRequest getHttpRequest(GenerationRequest generationRequest, Endpoint endpoint) {
    String path =
        PathResolver.resolvePath(endpoint.getPath(), properties.getDefaultReplaceParameter());

    Map<String, String> headers = new LinkedHashMap<>();
    headers.put(HttpHeaders.HOST, generationRequest.getTargetHost());
    headers.put(HttpHeaders.USER_AGENT, properties.getDefaultUserAgent());
    headers.put(HttpHeaders.CONNECTION, "Keep-Alive");

    String authToken = generationRequest.getAuthToken();
    if (authToken != null && !authToken.isEmpty()) {
      headers.put(HttpHeaders.AUTHORIZATION, "Bearer " + authToken.strip());
    }

    return new HttpRequest(endpoint.getMethod(), path, headers, endpoint.getBody());
  }
}
