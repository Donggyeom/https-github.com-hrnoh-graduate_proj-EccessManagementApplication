package ng.grad_proj.eccessmanagementapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by KimDonggyeom on 2017-04-12.
 */

public class ADListAdapter extends BaseAdapter {

    ArrayList<AccessData> items;
    Context mContext;

    public ADListAdapter(Context context){
        super();
        mContext = context;
        items = new ArrayList<AccessData>();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v==null) {
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.access_data_item, null);
        }
        AccessData item = items.get(position);

        TextView nameView = (TextView)v.findViewById(R.id.name);
        TextView dataView = (TextView)v.findViewById(R.id.accessdata);
        nameView.setText(item.getName());
        dataView.setText(item.getAccessData());

        return v;
    }

    public void addItem(ArrayList<AccessData> additems) {
        for (int i=0; i < additems.size(); i++) {
            items.add(additems.get(i));
        }
    }
}
