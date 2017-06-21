package ng.grad_proj.eccessmanagementapplication.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

import ng.grad_proj.eccessmanagementapplication.Network.DeleteDoorlock;
import ng.grad_proj.eccessmanagementapplication.Network.DeleteEmployee;
import ng.grad_proj.eccessmanagementapplication.Network.PullDoorlockLog;
import ng.grad_proj.eccessmanagementapplication.Network.PullEmployeeLog;
import ng.grad_proj.eccessmanagementapplication.R;
import ng.grad_proj.eccessmanagementapplication.VO.DoorlockVO;
import ng.grad_proj.eccessmanagementapplication.VO.EmployeeVO;
import ng.grad_proj.eccessmanagementapplication.VO.LogVO;

/**
 * Created by KimDonggyeom on 2017-06-15.
 */
public class EmployeeDetailActivity extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.employee_detail_view);

        // 도어락 아이템 가져오기
        SharedPreferences eList = getSharedPreferences("eList", MODE_PRIVATE);
        Integer clicked = eList.getInt("empClicked", -1);
        String item = eList.getString("empItem" + clicked.toString(), "");
        final EmployeeVO empItem = new Gson().fromJson(item, EmployeeVO.class);

        TextView tbl_name = (TextView)findViewById(R.id.emp_tbl_name);
        TextView tbl_deptname = (TextView)findViewById(R.id.emp_tbl_deptname);
        TextView tbl_position = (TextView)findViewById(R.id.emp_tbl_position);
        TextView tbl_phoneNum = (TextView)findViewById(R.id.emp_tbl_phoneNum);
        TextView tbl_level = (TextView)findViewById(R.id.emp_tbl_level);

        tbl_name.setText(empItem.getName());
        tbl_deptname.setText(empItem.getDeptName());
        tbl_position.setText(empItem.getPosition());
        tbl_phoneNum.setText(empItem.getPhoneNum());
        tbl_level.setText(Integer.toString(empItem.getLevel()));

        // 로그 테이블
        TableLayout eLogTbl = (TableLayout)findViewById(R.id.eLogTbl);
        // 로그 가져오기
        PullEmployeeLog log = new PullEmployeeLog();
        log.execute(Integer.toString(empItem.getEno()));
        try {
            String logJson = log.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // 테이블 데이터 추가
        for (LogVO vo : log.getEmpLog()) {
            TableRow row = new TableRow(this);
            row.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView location = new TextView(this);
            TextView date = new TextView(this);
            TextView res = new TextView(this);

            location.setText(vo.getLocation());
            date.setText(vo.getTime().toString());
            res.setText(vo.getResult());

            row.addView(location);
            row.addView(date);
            row.addView(res);

            eLogTbl.addView(row);
        }

        // 버튼 클릭 리스너
        Button remove = (Button) findViewById(R.id.doorlock_detail_remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeleteEmployee eDel = new DeleteEmployee();
                eDel.execute(Integer.toString(empItem.getEno()));

                try {
                    String res = eDel.get();

                    if (!res.contains("success")) {
                        Toast.makeText(v.getContext(), "DeleteFailed", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(v.getContext(), "Successed", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        Button close = (Button) findViewById(R.id.doorlock_detail_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
