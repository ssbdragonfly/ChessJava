import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;

public class GetRequestExample {

    public chessMove AI(String[] args) {
        try {
            String urlStr = "https://www.chessdb.cn/cdb.php?action=querybest&board=r1bqkb1r/ppp2ppp/5n2/n2Pp1N1/2B5/8/PPPP1PPP/RNBQK2R%20w%20KQkq%20-%201%206";
            URI uri = new URI(urlStr);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                String re = (response.toString()).substring(5);

                int startX = -1;
                char startChar = re.charAt(0);
                if (startChar == 'h') {
                    startX = 7;
                } else if (startChar == 'g') {
                    startX = 6;
                } else if (startChar == 'f') {
                    startX = 5;
                } else if (startChar == 'e') {
                    startX = 4;
                } else if (startChar == 'd') {
                    startX = 3;
                } else if (startChar == 'c') {
                    startX = 2;
                } else if (startChar == 'b') {
                    startX = 1;
                } else if (startChar == 'a') {
                    startX = 0;
                }

                int startY = Character.getNumericValue(re.charAt(1));

                int endX = -1;
                char endChar = re.charAt(2);
                if (endChar == 'h') {
                    endX = 7;
                } else if (endChar == 'g') {
                    endX = 6;
                } else if (endChar == 'f') {
                    endX = 5;
                } else if (endChar == 'e') {
                    endX = 4;
                } else if (endChar == 'd') {
                    endX = 3;
                } else if (endChar == 'c') {
                    endX = 2;
                } else if (endChar == 'b') {
                    endX = 1;
                } else if (endChar == 'a') {
                    endX = 0;
                }
                int endY = Character.getNumericValue(re.charAt(3));
                chessMove thing = new chessMove(startX, startY, endX, endY) ;
                return thing;
            } else {
                System.out.println("GET request not worked");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
