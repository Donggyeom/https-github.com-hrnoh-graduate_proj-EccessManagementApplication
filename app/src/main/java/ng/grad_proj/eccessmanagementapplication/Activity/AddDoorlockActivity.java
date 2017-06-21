package ng.grad_proj.eccessmanagementapplication.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import ng.grad_proj.eccessmanagementapplication.Network.AddDoorlock;
import ng.grad_proj.eccessmanagementapplication.R;

public class AddDoorlockActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_doorlock_view);

        final EditText locText = (EditText)findViewById(R.id.dAddLoc);
        final EditText macText = (EditText)findViewById(R.id.dAddMac);
        final EditText levText = (EditText)findViewById(R.id.dAddLevel);

        Button closeBtn = (Button) findViewById(R.id.dAddCloseBtn);
        Button registBtn = (Button) findViewById(R.id.dAddRegistBtn);

        registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locText.getText().toString().isEmpty() ||
                        macText.getText().toString().isEmpty() ||
                        levText.getText().toString().isEmpty()) {
                    Toast.makeText(v.getContext(), "정보를 모두 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    AddDoorlock addDoorlock = new AddDoorlock();
                    String param = "{ \"mac\": \"" + macText.getText().toString() + "\"" +
                            ", \"location\":\"" + locText.getText().toString() + "\"" +
                            ", \"level\":" + levText.getText().toString() + "}";
                    addDoorlock.execute(param);

                    try {
                        String res = addDoorlock.get();

                        if (!res.contains("success")) {
                            Toast.makeText(v.getContext(), "Add Failed", Toast.LENGTH_SHORT).show();
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
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
