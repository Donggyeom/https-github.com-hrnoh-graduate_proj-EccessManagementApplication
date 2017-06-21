package ng.grad_proj.eccessmanagementapplication.Activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ng.grad_proj.eccessmanagementapplication.VO.ADListAdapter;
import ng.grad_proj.eccessmanagementapplication.VO.AccessData;
import ng.grad_proj.eccessmanagementapplication.R;
import ng.grad_proj.eccessmanagementapplication.VO.MessageDTO;

/**
 * Created by KimDonggyeom on 2017-04-04.
 */

public class TabFragment1 extends Fragment {

    public ArrayList<AccessData> accessDatas;
    public static TextView logView;
    public static ListView linkedDoorlock;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab1, container, false);

        logView = (TextView)view.findViewById(R.id.LogView);
        linkedDoorlock = (ListView)view.findViewById(R.id.LinkedDoorlockList);

        return view;
    }
}
