package preferee.data.access.persistent_xml_processing;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * Hulpklasse bij POST_requesten
 * Created by domien on 28/03/2015.
 */
public class POST_requester {
        /**
         * Verzend post-request en geeft de gecreërdde connectie terug.
         * @param url
         * @param query
         * @return connection van post request
         * @throws java.io.IOException
         */
        public static URLConnection executePOSTRequest(String url, String query) throws IOException {
            URLConnection connection = new URL(url).openConnection();
            connection.setDoOutput(true); // Triggers POST.
            connection.setRequestProperty("Accept-Charset", StandardCharsets.UTF_8.name());
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + StandardCharsets.UTF_8.name());

            // connection openen
            try (OutputStream output = connection.getOutputStream()) {
                output.write(query.getBytes(StandardCharsets.UTF_8.name()));
            }

            return connection;
        }
}
