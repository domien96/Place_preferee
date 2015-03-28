package preferee.tests.RealServerTests;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Created by domien on 24/03/2015.
 */
public class SendingPOSTRequests_TEST {

    public static void main(String[] args) throws Exception {
        new SendingPOSTRequests_TEST().run();
    }

    private void run() throws Exception {
        createNewReservation();
    }

    private void createNewReservation() throws Exception {

            String url = "http://esari.ugent.be/orders.xml";
            String charset = StandardCharsets.UTF_8.name();
            String param1 = "Fifty shades of simon  ( ͡° ͜ʖ ͡°)";
            String param2 = "17";

            String query = String.format("name=%s&showing_id=%s",
                    URLEncoder.encode(param1, charset),
                    URLEncoder.encode(param2, charset));

        //DEEL2

            URLConnection connection = new URL(url).openConnection();
            //System.out.println(((HttpURLConnection)connection).getResponseCode());
            connection.setDoOutput(true); // Triggers POST.
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

            try (OutputStream output = connection.getOutputStream()) {
                output.write(query.getBytes(charset));
            }

            for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
                System.out.println(header.getKey() + "=" + header.getValue());
            }

            System.out.println(((HttpURLConnection)connection).getResponseCode());

            String contentType = connection.getHeaderField("Content-Type");

        try (InputStream response = connection.getInputStream();) {
            for (String param : contentType.replace(" ", "").split(";")) {
                if (param.startsWith("charset=")) {
                    charset = param.split("=", 2)[1];
                    break;
                }
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response, charset))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    System.out.println(line);
                }
            }
        }
                /**try (InputStream response = connection.getInputStream();) {

                }*/
    }
}
