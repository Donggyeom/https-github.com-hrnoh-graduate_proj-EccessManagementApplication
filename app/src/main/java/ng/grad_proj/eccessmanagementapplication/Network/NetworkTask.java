package ng.grad_proj.eccessmanagementapplication.Network;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by KimDonggyeom on 2017-05-15.
 */
public class NetworkTask extends AsyncTask<String, String, String> {

    /**
     * doInBackground 실행되기 이전에 동작한다.
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        // HTTP 요청 준비 작업
        HttpClient.Builder http = new HttpClient.Builder("POST",
                "http://192.168.0.39:8080/doorlock/android/connect");

        // 파라미터를 전송한다.
        http.addOrReplace("test", "한글한글");

        // HTTP 요청 전송
        HttpClient post = http.create();
        post.request();

        // 응답 상태코드 가져오기
        int statusCode = post.getHttpStatusCode();

        // 응답 본문 가져오기
        String body = post.getBody();

        return body;
    }

    /**
     * doInBackground 종료되면 동작한다.
     * @param s : doInBackground가 리턴한 값이 들어온다.
     */
    @Override
    protected void onPostExecute(String s) {
        Log.d("HTTP_RESULT", s);
    }
}
