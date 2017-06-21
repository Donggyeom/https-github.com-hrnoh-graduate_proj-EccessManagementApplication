package ng.grad_proj.eccessmanagementapplication.Network;

import android.os.AsyncTask;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by KimDonggyeom on 2017-06-02.
 */
public class DeleteEmployee extends AsyncTask<String, String, String> {

    /**
     * doInBackground 실행되기 이전에 동작한다.
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * 본 작업을 쓰레드로 처리해준다.
     * @param params
     * @return
     */
    @Override
    protected String doInBackground(String... params) {
        String res = null;

        try {
            HttpConnect httpConnect = new HttpConnect(new URL("http://192.168.0.39:8080/app/eDel/" + params[0]), "DELETE");

            if (httpConnect.open())
                res = httpConnect.connect(null);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return res;
    }

    /**
     * doInBackground 종료되면 동작한다.
     * @param s : doInBackground가 리턴한 값이 들어온다.
     */
    @Override
    protected void onPostExecute(String s) {
    }
}