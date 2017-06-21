package ng.grad_proj.eccessmanagementapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ng.grad_proj.eccessmanagementapplication.Network.PullEmpList;
import ng.grad_proj.eccessmanagementapplication.VO.AccessData;
import ng.grad_proj.eccessmanagementapplication.R;
import ng.grad_proj.eccessmanagementapplication.VO.EmployeeVO;

/**
 * Created by KimDonggyeom on 2017-04-04.
 */

public class TabFragment2 extends Fragment {

    public ArrayList<AccessData> accessDatas;
    private List<EmployeeVO> empList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.tab2, container, false);

        PullEmpList pullEmpList = new PullEmpList();
        try {
            // 결과를 받아올 때 까지 블럭
            pullEmpList.execute("").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        empList = pullEmpList.getEmpList();
        String[] list = new String[empList.size()];

        // 공유 객체 저장
        SharedPreferences eList = getActivity().getSharedPreferences("eList", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = eList.edit();
        Gson gson = new Gson();

        for (int i = 0; i < empList.size(); i++) {
            list[i] = empList.get(i).toListData();
            // 공유데이터 설정
            editor.putString("empItem" + i, gson.toJson(empList.get(i)));
        }

        ListView listView = (ListView)view.findViewById(R.id.EmpListView);
        ArrayAdapter adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, list) ;

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("empClicked", position);
                editor.commit();
                startActivity(new Intent(view.getContext(), EmployeeDetailActivity.class));
            }
        });

        // 추가 버튼 리스너
        Button AddBtn = (Button)view.findViewById(R.id.empAddBtn);
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), AddEmployeeActivity.class));
            }
        });

        return view;
    }
}
