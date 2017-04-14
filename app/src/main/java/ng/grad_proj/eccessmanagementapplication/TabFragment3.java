package ng.grad_proj.eccessmanagementapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

/**
 * Created by KimDonggyeom on 2017-04-04.
 */
public class TabFragment3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3, container, false);

        // ImageView 크기 설정
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)container.getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);

        ImageView img = (ImageView) view.findViewById(R.id.personalImage);
        LayoutParams params = (LayoutParams)img.getLayoutParams();
        params.width = metrics.widthPixels / 2;
        params.height = metrics.heightPixels / 3;
        img.setLayoutParams(params);

        return view;
    }
}
