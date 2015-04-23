package preferee.data.access.server.http_post_request;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * Voert POST_requesten uit.
 * Created by Domien Van Steendam on 28/03/2015.
 */
public class POST_requester {
        /**
         * Verzend post-request en geeft de gecreërdde connectie terug.
         * @param url : protocol http
         * @param query : parameters
         * @return response-connectie van post request
         * @throws java.io.IOException
         */
        public static URLConnection executePOSTRequest(String url, String query) throws IOException {
            URLConnection connection = new URL(url).openConnection();
            connection.setDoOutput(true); // Triggers POST.
            connection.setRequestProperty("Accept-Charset", StandardCharsets.UTF_8.name());
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + StandardCharsets.UTF_8.name());

            // post_request openen
            try (OutputStream output = connection.getOutputStream()) {
                output.write(query.getBytes(StandardCharsets.UTF_8.name()));
            }

            return connection;
        }
}
