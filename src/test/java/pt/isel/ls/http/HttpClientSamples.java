package pt.isel.ls.http;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class HttpClientSamples {

    @Test
    public void can_do_a_get() throws IOException {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()){
            HttpGet get = new HttpGet("http://httpbin.org/get");
            get.addHeader("test-header","test-value");
            try (CloseableHttpResponse resp = client.execute(get)){
                assertEquals(200, resp.getStatusLine().getStatusCode());
                assertEquals("application/json", resp.getEntity().getContentType().getValue());
                HttpEntity body = resp.getEntity();
                Scanner scanner = new Scanner(body.getContent()).useDelimiter("\\A");
                String bodyString = scanner.hasNext() ? scanner.next() : "";
                JSONObject json = new JSONObject(bodyString);
                assertEquals("http://httpbin.org/get", json.getString("url"));
                assertEquals("test-value", json.getJSONObject("headers").getString("Test-Header"));
            }
        }
    }
}
