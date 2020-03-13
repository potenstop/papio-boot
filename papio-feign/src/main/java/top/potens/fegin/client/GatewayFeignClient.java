package top.potens.fegin.client;

import feign.Client;
import feign.Request;
import feign.Response;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;
import static feign.Util.*;
import static java.lang.String.format;
/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className GatewayFeignClient
 * @projectName papio-framework
 * @date 2020/3/13 13:50
 */
public class GatewayFeignClient implements Client {
    @Value("${fx.domain.end-api-gateway:http://192.168.1.60:10001/}")
    private String endApiGatewayHost;
    @Override
    public Response execute(Request request, Request.Options options) throws IOException {
        String newUrl = request.url().replaceFirst("http://", endApiGatewayHost);
        Request newRequest = Request.create(request.httpMethod(), newUrl, request.headers(), request.requestBody());
        HttpURLConnection connection = convertAndSend(newRequest, options);
        return convertResponse(connection, request);
    }
    HttpURLConnection convertAndSend(Request request, Request.Options options) throws IOException {
        final HttpURLConnection
                connection =
                (HttpURLConnection) new URL(request.url()).openConnection();
        connection.setConnectTimeout(options.connectTimeoutMillis());
        connection.setReadTimeout(options.readTimeoutMillis());
        connection.setAllowUserInteraction(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestMethod(request.method());

        Collection<String> contentEncodingValues = request.headers().get(CONTENT_ENCODING);
        boolean
                gzipEncodedRequest =
                contentEncodingValues != null && contentEncodingValues.contains(ENCODING_GZIP);
        boolean
                deflateEncodedRequest =
                contentEncodingValues != null && contentEncodingValues.contains(ENCODING_DEFLATE);

        boolean hasAcceptHeader = false;
        Integer contentLength = null;
        for (String field : request.headers().keySet()) {
            if (field.equalsIgnoreCase("Accept")) {
                hasAcceptHeader = true;
            }
            for (String value : request.headers().get(field)) {
                if (field.equals(CONTENT_LENGTH)) {
                    if (!gzipEncodedRequest && !deflateEncodedRequest) {
                        contentLength = Integer.valueOf(value);
                        connection.addRequestProperty(field, value);
                    }
                } else {
                    connection.addRequestProperty(field, value);
                }
            }
        }
        // Some servers choke on the default accept string.
        if (!hasAcceptHeader) {
            connection.addRequestProperty("Accept", "*/*");
        }

        if (request.body() != null) {
            if (contentLength != null) {
                connection.setFixedLengthStreamingMode(contentLength);
            } else {
                connection.setChunkedStreamingMode(8196);
            }
            connection.setDoOutput(true);
            OutputStream out = connection.getOutputStream();
            if (gzipEncodedRequest) {
                out = new GZIPOutputStream(out);
            } else if (deflateEncodedRequest) {
                out = new DeflaterOutputStream(out);
            }
            try {
                out.write(request.body());
            } finally {
                try {
                    out.close();
                } catch (IOException suppressed) { // NOPMD
                }
            }
        }
        return connection;
    }

    Response convertResponse(HttpURLConnection connection, Request request) throws IOException {
        int status = connection.getResponseCode();
        String reason = connection.getResponseMessage();

        if (status < 0) {
            throw new IOException(format("Invalid status(%s) executing %s %s", status,
                    connection.getRequestMethod(), connection.getURL()));
        }

        Map<String, Collection<String>> headers = new LinkedHashMap<String, Collection<String>>();
        for (Map.Entry<String, List<String>> field : connection.getHeaderFields().entrySet()) {
            // response message
            if (field.getKey() != null) {
                headers.put(field.getKey(), field.getValue());
            }
        }

        Integer length = connection.getContentLength();
        if (length == -1) {
            length = null;
        }
        InputStream stream;
        if (status >= 400) {
            stream = connection.getErrorStream();
        } else {
            stream = connection.getInputStream();
        }
        return Response.builder()
                .status(status)
                .reason(reason)
                .headers(headers)
                .body(stream, length)
                .request(request)
                .build();
    }
}
