package ng.grad_proj.eccessmanagementapplication.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

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
        pullEmpList.execute("");

        empList = pullEmpList.getEmpList();
        ArrayList<String> list = new ArrayList<>();

        for (EmployeeVO emp : empList) {
            list.add(emp.toString());
        }

        ListView listView = (ListView)findViewById(R.id.EmpListView);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list) ;

        listView.setAdapter(adapter);

    }
}
