package ru.kostyukov.tankgenerator.services.ammo;

import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.stereotype.Service;
import ru.kostyukov.tankgenerator.dto.GenerationRequest;
import ru.kostyukov.tankgenerator.models.Endpoint;

@Service
public class AmmoGeneratorService implements AmmoGenerator {

  @Override
  public String generateAmmo(List<Endpoint> endpoints, GenerationRequest generationRequest) {
    StringBuilder ammoBuilder = new StringBuilder();

    endpoints.forEach(
        endpoint -> {
          String path = endpoint.getPath().replaceAll("\\{[^}]+}", "1");
          String method = endpoint.getMethod();
          String body = endpoint.getBody();

          StringBuilder httpBuilder = new StringBuilder();
          httpBuilder.append(method).append(" ").append(path).append(" HTTP/1.1\r\n");

          httpBuilder.append("Host: ").append(generationRequest.getTargetHost()).append("\r\n");
          httpBuilder.append("User-Agent: Tank\r\n");
          httpBuilder.append("Connection: Keep-Alive\r\n");

          String authToken = generationRequest.getAuthToken();
          if (authToken != null && !authToken.isEmpty()) {
            httpBuilder.append("Authorization: Bearer ").append(authToken.strip()).append("\r\n");
          }

          if (body != null && !body.isBlank() && List.of("POST", "PUT", "PATCH").contains(method)) {
            byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
            httpBuilder.append("Content-Type: application/json\r\n");
            httpBuilder.append("Content-Length: ").append(bodyBytes.length).append("\r\n");
            httpBuilder.append("\r\n").append(body);
          } else {
            httpBuilder.append("\r\n");
          }

          String httpRequestString = httpBuilder.toString();
          byte[] httpRequestBytes = httpRequestString.getBytes();
          String tag =
              method + "_" + path.replace("/", "_").toLowerCase().replaceAll("[^a-z0-9_]+", "");
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
