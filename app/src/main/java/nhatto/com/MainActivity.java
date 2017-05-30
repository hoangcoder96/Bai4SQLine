package nhatto.com;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import nhatto.com.adapter.CustomAdapter;
import nhatto.com.data.DBManager;
import nhatto.com.model.Student;

public class MainActivity extends AppCompatActivity {

    private EditText edtId;
    private EditText edtName;
    private EditText edtNumber;
    private EditText edtAddress;
    private EditText edtEmail;
    private Button btnSave;
    private Button btnUpdate;
    private ListView lvStudent;
    private CustomAdapter customAdapter;
    private List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DBManager dbManager = new DBManager(this);
        initWidget();
        studentList = dbManager.getAllStudent();
        setAdapter();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = createStudent();
                if (student != null){
                    dbManager.addStudent(student);
                }
                studentList.clear();
                studentList.addAll(dbManager.getAllStudent());

                setAdapter();

            }
        });
        // ngay 2:
        lvStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student student = studentList.get(position);
                edtId.setText(String.valueOf(student.getmID()));
                edtName.setText(student.getmName());
                edtNumber.setText(student.getmPhoneNumber());
                edtAddress.setText(student.getmAddress());
                edtEmail.setText(student.getmEmail());
                btnSave.setEnabled(false);
                btnUpdate.setEnabled(true);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student();
                student.setmID(Integer.parseInt(String.valueOf(edtId.getText())));
                student.setmName(edtName.getText().toString());
                student.setmPhoneNumber(edtNumber.getText().toString());
                student.setmAddress(edtAddress.getText().toString());
                student.setmEmail(edtEmail.getText().toString());
                int result = dbManager.updateStudent(student);
                if (result > 0){
                    btnSave.setEnabled(true);
                    studentList.clear();
                    studentList.addAll(dbManager.getAllStudent());
                    customAdapter.notifyDataSetChanged();
                }else {
                    btnSave.setEnabled(true);
                    btnUpdate.setEnabled(false);
                }
            }
        });

        lvStudent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Student student = studentList.get(position);
                int result = dbManager.deleteStudent(student.getmID());
                if (result > 0){
                    Toast.makeText(MainActivity.this, "Delete Successfully", Toast.LENGTH_SHORT).show();
                    studentList.clear();
                    studentList.addAll(dbManager.getAllStudent());
                    customAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(MainActivity.this, "Delete fail", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    private void initWidget(){
        edtId = (EditText) findViewById(R.id.edt_id);
        edtName = (EditText) findViewById(R.id.edt_name);
        edtNumber = (EditText) findViewById(R.id.edt_number);
        edtAddress = (EditText) findViewById(R.id.edt_address);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        lvStudent = (ListView) findViewById(R.id.lv_student);
    }

    private Student createStudent(){
        String name = edtName.getText().toString();
        String address = edtAddress.getText().toString();
        String phoneNumber = edtNumber.getText().toString();
        String email = edtEmail.getText().toString();

        Student student = new Student(name,address,phoneNumber,email);
        return student;
    }

    private void setAdapter(){
        if (customAdapter == null){
            customAdapter = new CustomAdapter(this,R.layout.item_list_student,studentList);
            lvStudent.setAdapter(customAdapter);

        }else {
            customAdapter.notifyDataSetChanged();
            lvStudent.setSelection(customAdapter.getCount()-1);
        }

    }

}
