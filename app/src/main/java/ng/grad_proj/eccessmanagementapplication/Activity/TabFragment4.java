package ng.grad_proj.eccessmanagementapplication.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ng.grad_proj.eccessmanagementapplication.R;
import ng.grad_proj.eccessmanagementapplication.VO.AccessData;
import ng.grad_proj.eccessmanagementapplication.VO.MessageDTO;

/**
 * Created by KimDonggyeom on 2017-04-04.
 */

public class TabFragment4 extends Fragment {

    public ArrayList<AccessData> accessDatas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab4, container, false);

        return view;
    }
}
