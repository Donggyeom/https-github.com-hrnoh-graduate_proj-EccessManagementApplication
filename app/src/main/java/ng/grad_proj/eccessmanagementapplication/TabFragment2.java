package ng.grad_proj.eccessmanagementapplication;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by KimDonggyeom on 2017-04-04.
 */

public class TabFragment2 extends Fragment {

    public ArrayList<AccessData> accessDatas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab2, container, false);
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

        return view;
    }
}
