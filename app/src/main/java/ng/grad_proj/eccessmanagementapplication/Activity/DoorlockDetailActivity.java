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
import ng.grad_proj.eccessmanagementapplication.Network.PullDoorlockLog;
import ng.grad_proj.eccessmanagementapplication.R;
import ng.grad_proj.eccessmanagementapplication.VO.DoorlockVO;
import ng.grad_proj.eccessmanagementapplication.VO.LogVO;

public class DoorlockDetailActivity extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.doorlock_detail_view);

        // 도어락 아이템 가져오기
        SharedPreferences dList = getSharedPreferences("dList", MODE_PRIVATE);
        Integer clicked = dList.getInt("doorlockClicked", -1);
        String item = dList.getString("doorlockItem" + clicked.toString(), "");
        final DoorlockVO doorlockItem = new Gson().fromJson(item, DoorlockVO.class);

        TextView tbl_lev = (TextView)findViewById(R.id.doorlock_tbl_level);
        TextView tbl_loca = (TextView)findViewById(R.id.doorlock_tbl_location);
        TextView tbl_mac = (TextView)findViewById(R.id.doorlock_tbl_mac);

        tbl_lev.setText(Integer.toString(doorlockItem.getLevel()));
        tbl_loca.setText(doorlockItem.getLocation());
        tbl_mac.setText(doorlockItem.getMac());

        // 로그 테이블
        TableLayout dLogTbl = (TableLayout)findViewById(R.id.dLogTbl);
        // 로그 가져오기
        PullDoorlockLog log = new PullDoorlockLog();
        log.execute(doorlockItem.getMac());
        try {
            String logJson = log.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // 테이블 데이터 추가
        for (LogVO vo : log.getDoorlockLog()) {
            TableRow row = new TableRow(this);
            row.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView name = new TextView(this);
            TextView dept = new TextView(this);
            TextView position = new TextView(this);
            TextView date = new TextView(this);
            TextView res = new TextView(this);

            name.setText(vo.getName());
            dept.setText(vo.getDeptName());
            position.setText(vo.getPosition());
            date.setText(vo.getTime().toString());
            res.setText(vo.getResult());

            row.addView(name);
            row.addView(dept);
            row.addView(position);
            row.addView(date);
            row.addView(res);

            dLogTbl.addView(row);
        }

        // 버튼 클릭 리스너
        Button remove = (Button) findViewById(R.id.doorlock_detail_remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeleteDoorlock dDel = new DeleteDoorlock();
                dDel.execute(doorlockItem.getMac());

                try {
                    String res = dDel.get();

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
