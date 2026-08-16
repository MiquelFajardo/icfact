package cat.informaticassa.icfact.actualitzacio.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.BiConsumer;

public final class ActualitzacioDownloader {
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build();

    private ActualitzacioDownloader() {
    }

    public static void descarregar(String url, Path desti, BiConsumer<Long, Long> progres) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<InputStream> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofInputStream());
        if (response.statusCode() != 200) {
            response.body().close();
            throw new IOException("Error HTTP " + response.statusCode());
        }
        long totalBytes = response.headers().firstValueAsLong("Content-Length").orElse(-1);
        try (InputStream input = response.body(); OutputStream output = Files.newOutputStream(desti, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            byte[] buffer = new byte[8192];
            long bytesDescarregats = 0;
            int llegits;
            while ((llegits = input.read(buffer)) != -1) {
                output.write(buffer, 0, llegits);
                bytesDescarregats += llegits;
                progres.accept(bytesDescarregats, totalBytes );
            }
        }
    }
}