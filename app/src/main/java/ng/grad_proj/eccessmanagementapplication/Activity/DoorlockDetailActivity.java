package ng.grad_proj.eccessmanagementapplication.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

import com.google.gson.Gson;

import ng.grad_proj.eccessmanagementapplication.R;
import ng.grad_proj.eccessmanagementapplication.VO.DoorlockVO;

/**
 * Created by KimDonggyeom on 2017-06-15.
 */
public class DoorlockDetailActivity extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.doorlock_detail_view);

        // 도어락 아이템 가져오기
        SharedPreferences dList = getSharedPreferences("dList", MODE_PRIVATE);
        Integer clicked = dList.getInt("doorlockClicked", -1);
        String item = dList.getString("doorlockItem" + clicked.toString(), "");
        DoorlockVO doorlockItem = new Gson().fromJson(item, DoorlockVO.class);

        TextView tbl_no = (TextView)findViewById(R.id.doorlock_tbl_no);
        TextView tbl_lev = (TextView)findViewById(R.id.doorlock_tbl_level);
        TextView tbl_loca = (TextView)findViewById(R.id.doorlock_tbl_location);
        TextView tbl_mac = (TextView)findViewById(R.id.doorlock_tbl_mac);

        tbl_no.setText(Integer.toString(doorlockItem.getDno()));
        tbl_lev.setText(Integer.toString(doorlockItem.getLevel()));
        tbl_loca.setText(doorlockItem.getLocation());
        tbl_mac.setText(doorlockItem.getMac());
    }
}
