package ng.grad_proj.eccessmanagementapplication.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import ng.grad_proj.eccessmanagementapplication.VO.ADListAdapter;
import ng.grad_proj.eccessmanagementapplication.VO.AccessData;
import ng.grad_proj.eccessmanagementapplication.R;

/**
 * Created by KimDonggyeom on 2017-04-04.
 */

public class TabFragment2 extends Fragment {

    public ArrayList<AccessData> accessDatas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.tab2, container, false);
        ListView acDataListView = (ListView)view.findViewById(R.id.personalADList);
        ADListAdapter adapter = new ADListAdapter(container.getContext());
        accessDatas = new ArrayList<AccessData>();

        // 디비에서 데이타 가져오기
        AccessData dummy00 = new AccessData();
        dummy00.setName("dummy00");
        dummy00.setAccessData("2017/04/12 19:03 회의실");
        accessDatas.add(dummy00);
        adapter.addItem(accessDatas);
        acDataListView.setAdapter(adapter);

        // 사원 리스트 버튼 클릭 이벤트
        Button empListBtn = (Button)view.findViewById(R.id.EmpListBtn);
        empListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), EmpListActivity.class));
            }
        });


        return view;
    }
}
