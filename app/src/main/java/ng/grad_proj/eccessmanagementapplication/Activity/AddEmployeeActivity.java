package ng.grad_proj.eccessmanagementapplication.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import ng.grad_proj.eccessmanagementapplication.Network.AddDoorlock;
import ng.grad_proj.eccessmanagementapplication.Network.AddEmployee;
import ng.grad_proj.eccessmanagementapplication.R;

public class AddEmployeeActivity extends AppCompatActivity{

    private String[] deptName = {
            "총무팀", "경리부", "경영지원팀", "경영개선팀", "인사과", "인사팀", "재경부", "고객만족팀",
            "구매부서", "관리부서", "기술지원팀", "기획팀", "전략기획팀", "연구기획팀", "연구개발팀",
            "비서실", "생산관리팀", "시설관리팀", "연구실"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_employee_view);

        final EditText nameText = (EditText)findViewById(R.id.eAdd_name);
        final EditText ageText = (EditText)findViewById(R.id.eAdd_age);
        final EditText deptText = (EditText)findViewById(R.id.eAdd_department);
        final EditText posText = (EditText)findViewById(R.id.eAdd_position);
        final EditText phoneText = (EditText)findViewById(R.id.eAdd_phone);
        final EditText levText = (EditText)findViewById(R.id.eAdd_level);

        Button closeBtn = (Button) findViewById(R.id.eAddCloseBtn);
        Button registBtn = (Button) findViewById(R.id.eAddRegistBtn);


        registBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameText.getText().toString().isEmpty() ||
                        deptText.getText().toString().isEmpty() ||
                        posText.getText().toString().isEmpty() ||
                        phoneText.getText().toString().isEmpty() ||
                        levText.getText().toString().isEmpty()) {
                    Toast.makeText(v.getContext(), "정보를 모두 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    AddEmployee addEmployee = new AddEmployee();
                    String deptname = deptText.getText().toString();
                    Integer dno = 0;
                    for(int i=0;i<deptName.length; i++) {
                        if(deptName[i].equals(deptname)) {
                            dno = i + 1;
                            break;
                        }
                    }
                    String param = "{ \"name\": \"" + nameText.getText().toString() + "\"" +
                            ", \"age\":" + ageText.getText().toString() +
                            ", \"phoneNum\":\"" + phoneText.getText().toString() + "\"" +
                            ", \"position\":\"" + posText.getText().toString() + "\"" +
                            ", \"level\":" + levText.getText().toString() +
                            ", \"dno\":" + dno + "}";
                    addEmployee.execute(param);

                    try {
                        String res = addEmployee.get();

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
