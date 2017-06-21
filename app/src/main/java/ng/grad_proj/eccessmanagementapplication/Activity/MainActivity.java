package ng.grad_proj.eccessmanagementapplication.Activity;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import ng.grad_proj.eccessmanagementapplication.R;
import ng.grad_proj.eccessmanagementapplication.VO.MessageDTO;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    // WebSocket
    public static WebSocketClient mWebSocketClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 웹소켓 연결
        connectWebSocket();
//        mWebSocketClient.onMessage(res);

/*        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/
        // Set up the ViewPager with the sections adapter.
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.container);

        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);


        mTabLayout.getTabAt(0).setText("Home");
        mTabLayout.getTabAt(1).setText("사원 관리");
        mTabLayout.getTabAt(2).setText("도어락 관리");
        mTabLayout.getTabAt(3).setText("+");

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        MessageDTO msg = new MessageDTO(200, "");
        MainActivity.mWebSocketClient.send(msg.toJson());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    private void connectWebSocket() {
        URI uri;
        try {
            uri = new URI("ws://192.168.0.39:8080/doorlock-ws");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri, new Draft_17()) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
                //mWebSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
            }

            @Override
            public void onMessage(final String s) {
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MessageDTO msg = null;
                        msg = MessageDTO.convMessage(s);
                        switch (msg.getType()) {
                            case 500:
                                Gson gson = new Gson();
                                List<DoorlockListItem> doorlockListItems = gson.fromJson(msg.getData(), List.class);
                                String[] items = new String[doorlockListItems.size()];
                                for (int i=0;i<items.length; i++) {
                                    items[i] = doorlockListItems.toString();
                                }
                                TabFragment1.linkedDoorlock.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, items));
                                break;
                            case 600:
                                TabFragment1.logView.setText(TabFragment1.logView.getText() + msg.getData() + "\n");
                                break;
                            case 700:
                                Toast.makeText(MainActivity.this, msg.getData(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }

    // 도어락 리스트 아이템
    private class DoorlockListItem {
        WebSocketClient session;
        @Expose
        private String mac;
        @Expose
        private String location;
        @Expose
        private String ip;

        public DoorlockListItem() {}
        public DoorlockListItem(WebSocketClient session, String mac, String location) {
            this.session = session;
            this.mac = mac;
            this.location = location;

            if(this.session != null) {
                this.ip = session.getURI().getHost();
            }
        }
        public WebSocketClient getSession() {
            return session;
        }
        public void setSession(WebSocketClient session) {
            this.session = session;
        }
        public String getMac() {
            return mac;
        }
        public void setMac(String mac) {
            this.mac = mac;
        }
        public String getLocation() {
            return location;
        }
        public void setLocation(String location) {
            this.location = location;
        }
        public String getIp() {
            return ip;
        }
        public void setIp(String ip) {
            this.ip = ip;
        }
        @Override
        public String toString() {
            return "DoorlockListItem [session=" + session + ", mac=" + mac + ", location=" + location + ", ip=" + ip + "]";
        }
    }

}
