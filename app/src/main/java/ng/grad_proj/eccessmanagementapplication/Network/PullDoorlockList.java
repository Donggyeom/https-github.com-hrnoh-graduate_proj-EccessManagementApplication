package ng.grad_proj.eccessmanagementapplication.Network;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ng.grad_proj.eccessmanagementapplication.VO.DoorlockVO;
import ng.grad_proj.eccessmanagementapplication.VO.EmployeeVO;

/**
 * Created by KimDonggyeom on 2017-06-02.
 */
public class PullDoorlockList extends AsyncTask<String, String, String> {

    private List<DoorlockVO> doorlockList;

    public PullDoorlockList() {
        doorlockList = new ArrayList<>();
    }

    public List<DoorlockVO> getDoorlockList() {
        return doorlockList;
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
            HttpConnect httpConnect = new HttpConnect(new URL("http://192.168.0.39:8080/app/dList"), "GET");

            if (httpConnect.open())
                res = httpConnect.connect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            if (res == null) return "";

            JSONArray list = new JSONArray(res);
            Gson gson = new GsonBuilder().create();
            for (int i = 0; i < list.length(); i++) {
                doorlockList.add(gson.fromJson(list.get(i).toString(), DoorlockVO.class));
                Log.d("emp", doorlockList.get(i).toString());
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