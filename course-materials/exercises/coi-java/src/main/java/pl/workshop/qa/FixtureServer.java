package pl.workshop.qa;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

/** Loopback-only synthetic fixture. Not a production service. */
public final class FixtureServer implements AutoCloseable {
    private final HttpServer server;
    private final byte[] slots;
    private final byte[] page;

    private FixtureServer(int port) throws IOException {
        slots = resource("/slots.json");
        page = resource("/index.html");
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", port), 0);
        server.createContext("/", this::handle);
        server.start();
    }

    public static FixtureServer start(int port) throws IOException { return new FixtureServer(port); }

    public URI baseUri() { return URI.create("http://127.0.0.1:" + server.getAddress().getPort() + "/"); }

    private static byte[] resource(String name) throws IOException {
        try (InputStream stream = FixtureServer.class.getResourceAsStream(name)) {
            if (stream == null) throw new IOException("Missing fixture resource: " + name);
            return stream.readAllBytes();
        }
    }

    private void handle(HttpExchange exchange) throws IOException {
        try (exchange) {
            String path = exchange.getRequestURI().getPath();
            String method = exchange.getRequestMethod();
            switch (path) {
                case "/" -> {
                    if (!method.equals("GET")) { methodNotAllowed(exchange, "GET"); return; }
                    send(exchange, 200, "text/html; charset=utf-8", page);
                }
                case "/api/slots" -> {
                    if (!method.equals("GET")) { methodNotAllowed(exchange, "GET"); return; }
                    send(exchange, 200, "application/json; charset=utf-8", slots);
                }
                case "/api/reservations" -> {
                    if (method.equals("GET")) { json(exchange, 200, "[]"); }
                    else if (method.equals("POST")) {
                        // Deliberately unfinished: implement only after selecting AC-05/AC-06 and writing a failing test.
                        json(exchange, 501, "{\"error\":\"NOT_IMPLEMENTED\"}");
                    } else { methodNotAllowed(exchange, "GET, POST"); }
                }
                case "/favicon.ico" -> { exchange.sendResponseHeaders(204, -1); }
                default -> json(exchange, 404, "{\"error\":\"NOT_FOUND\"}");
            }
        }
    }

    private static void methodNotAllowed(HttpExchange exchange, String allow) throws IOException {
        exchange.getResponseHeaders().set("Allow", allow);
        json(exchange, 405, "{\"error\":\"METHOD_NOT_ALLOWED\"}");
    }

    private static void json(HttpExchange exchange, int status, String body) throws IOException {
        send(exchange, status, "application/json; charset=utf-8", body.getBytes(StandardCharsets.UTF_8));
    }

    private static void send(HttpExchange exchange, int status, String type, byte[] body) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", type);
        exchange.getResponseHeaders().set("Cache-Control", "no-store");
        exchange.sendResponseHeaders(status, body.length);
        exchange.getResponseBody().write(body);
    }

    @Override public void close() { server.stop(0); }

    public static void main(String[] args) throws Exception {
        int port = args.length == 0 ? 8087 : Integer.parseInt(args[0]);
        FixtureServer fixture = start(port);
        Runtime.getRuntime().addShutdownHook(new Thread(fixture::close));
        System.out.println("SYNTHETIC FIXTURE " + fixture.baseUri() + " - Ctrl+C kończy proces.");
        new CountDownLatch(1).await();
    }
}
