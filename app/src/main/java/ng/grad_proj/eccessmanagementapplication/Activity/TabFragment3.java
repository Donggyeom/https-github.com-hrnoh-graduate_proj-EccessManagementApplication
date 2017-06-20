package ng.grad_proj.eccessmanagementapplication.Activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.SharedPreferencesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ng.grad_proj.eccessmanagementapplication.Network.PullDoorlockList;
import ng.grad_proj.eccessmanagementapplication.R;
import ng.grad_proj.eccessmanagementapplication.VO.DoorlockVO;

/**
 * Created by KimDonggyeom on 2017-06-12.
 */
public class TabFragment3 extends Fragment {

    private List<DoorlockVO> doorlockList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab3, container, false);

        // 도어락 리스트 가져오기
        PullDoorlockList pullDoorlockList = new PullDoorlockList();

        try {
            pullDoorlockList.execute("").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        doorlockList = pullDoorlockList.getDoorlockList();

        String[] doorlockListData = new String[doorlockList.size()];

        // 공유 객체 저장
        SharedPreferences dList = getActivity().getSharedPreferences("dList", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = dList.edit();
        Gson gson = new Gson();

        for (int i=0; i<doorlockList.size(); i++) {
            // 리스트 아이템 설정
            doorlockListData[i] = doorlockList.get(i).toListData();
            // 공유데이터 설정
            editor.putString("doorlockItem" + i, gson.toJson(doorlockList.get(i)));
        }
        editor.commit();

        // 리스트뷰 세팅
        ListView doorlockListView = (ListView)view.findViewById(R.id.doorlockListView);
        ArrayAdapter<String> dAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, doorlockListData);
        doorlockListView.setAdapter(dAdapter);

        // 아이템 클릭 리스너
        doorlockListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("doorlockClicked", position);
                editor.commit();
                startActivity(new Intent(view.getContext(), DoorlockDetailActivity.class));
            }
        });

        return view;
    }
}
