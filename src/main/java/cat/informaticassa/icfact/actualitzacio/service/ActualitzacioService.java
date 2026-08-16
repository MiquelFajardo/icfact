package cat.informaticassa.icfact.actualitzacio.service;

import cat.informaticassa.icfact.BuildInfo;
import cat.informaticassa.icfact.actualitzacio.model.InformacioActualitzacio;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ActualitzacioService {
    private static final Logger logger = LoggerFactory.getLogger(ActualitzacioService.class);
    private static final String URL_JSON = "https://informaticassa.tailc888e4.ts.net/arxius/icfact/updates.json";
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    public ActualitzacioService() {
        this.objectMapper = new ObjectMapper();
        this.httpClient = HttpClient.newHttpClient();
    }

    public InformacioActualitzacio comprovar() {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL_JSON)).GET().build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                logger.error("Error en consultar les actualitzacions. HTTP {}", response.statusCode());
                return null;
            }
            InformacioActualitzacio informacio = objectMapper.readValue(response.body(), InformacioActualitzacio.class);
            String versioActual = BuildInfo.getVersio();
            String ultimaVersio = informacio.getUltimaVersio();
            if (ultimaVersio == null || ultimaVersio.isBlank()) {
                logger.error("El fitxer d'actualitzacions no conté una versió vàlida.");
                return null;
            }
            if (!hiHaActualitzacio(versioActual, ultimaVersio)) {
                return null;
            }
            return informacio;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Comprovació d'actualitzacions interrompuda.", e);
            return null;
        } catch (IOException | RuntimeException e) {
            logger.error("Error en comprovar les actualitzacions.", e);
            return null;
        }
    }

    private boolean hiHaActualitzacio(String versioActual, String ultimaVersio) {
        try {
            String[] actual = versioActual.split("\\.");
            String[] ultima = ultimaVersio.split("\\.");
            int longitud = Math.max(actual.length, ultima.length);
            for (int i = 0; i < longitud; i++) {
                int numeroActual = i < actual.length ? Integer.parseInt(actual[i]) : 0;
                int numeroUltima = i < ultima.length ? Integer.parseInt(ultima[i]) : 0;
                if (numeroUltima > numeroActual) {
                    return true;
                }
                if (numeroUltima < numeroActual) {
                    return false;
                }
            }
            return false;
        } catch (NumberFormatException e) {
            logger.error( "Format de versió incorrecte. Actual='{}', última='{}'.", versioActual, ultimaVersio, e);
            return false;
        }
    }
}