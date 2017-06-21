package ng.grad_proj.eccessmanagementapplication.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by KimDonggyeom on 2017-06-14.
 */
public class HttpConnect {

    private URL url;
    private String method;
    private HttpURLConnection connection;


    public HttpConnect(URL url, String method) {
        this.url = url;
        this.method = method;
    }
    public URL getUrl() {
        return url;
    }
    public String getMethod() {
        return method;
    }
    public void setUrl(URL url) {
        this.url = url;
    }
    public void setMethod(String method) {
        this.method = method;
    }

    public boolean open() {
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod(method);
            connection.setDoInput(true);
            if (getMethod().equals("POST")) {
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type","application/json");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public String connect(String param) {
        if (param != null) {
            byte[] outputInBytes;
            try {
                outputInBytes = param.getBytes("UTF-8");
                OutputStream os = connection.getOutputStream();
                os.write(outputInBytes);
                os.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String res;
        try {
            int retCode = connection.getResponseCode();
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = br.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            br.close();

            res = response.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return res;
    }

}
