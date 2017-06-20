package ng.grad_proj.eccessmanagementapplication.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ng.grad_proj.eccessmanagementapplication.Network.PullEmpList;
import ng.grad_proj.eccessmanagementapplication.R;
import ng.grad_proj.eccessmanagementapplication.VO.EmployeeVO;

public class EmpListActivity extends AppCompatActivity {

    private List<EmployeeVO> empList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_emp_list);

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

        for (int i = 0; i < empList.size(); i++) {
            list[i] = empList.get(i).toListData();
        }

        ListView listView = (ListView)findViewById(R.id.EmpListView);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list) ;

        listView.setAdapter(adapter);

        // close 버튼 리스너
        Button closeBtn = (Button)findViewById(R.id.EmpCloseBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
