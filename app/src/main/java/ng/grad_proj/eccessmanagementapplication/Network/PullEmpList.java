package ng.grad_proj.eccessmanagementapplication.Network;

import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import ng.grad_proj.eccessmanagementapplication.VO.EmployeeVO;

/**
 * Created by KimDonggyeom on 2017-05-22.
 */
public class PullEmpList extends AsyncTask<String, String, String> {

    private List<EmployeeVO> empList;

    public PullEmpList() {
        empList = new ArrayList<>();
    }

    public List<EmployeeVO> getEmpList(){
        return empList;
    }

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
            HttpConnect httpConnect = new HttpConnect(new URL("http://192.168.0.39:8080/app/eList"), "GET");

            if (httpConnect.open())
                res = httpConnect.connect(null);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            if (res == null) return "";

            JSONArray list = new JSONArray(res);
            Gson gson = new GsonBuilder().create();
            for (int i = 0; i < list.length(); i++) {
                empList.add(gson.fromJson(list.get(i).toString(), EmployeeVO.class));
                Log.d("emp", empList.get(i).toString());
            }
        } catch (JSONException e) {
            //e.printStackTrace();
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