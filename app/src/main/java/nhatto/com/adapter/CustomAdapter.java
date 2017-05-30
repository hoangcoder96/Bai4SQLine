package nhatto.com.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nhatto.com.R;
import nhatto.com.model.Student;

/**
 * Created by User name on 29/05/2017.
 */

public class CustomAdapter extends ArrayAdapter<Student> {

    private Context context;
    private int resource;
    private List<Student> listStudent;

    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Student> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listStudent = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_student,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.tvId = (TextView) convertView.findViewById(R.id.tv_id);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvPhone = (TextView) convertView.findViewById(R.id.tv_number);
            viewHolder.tvAddress = (TextView) convertView.findViewById(R.id.tv_address);
            viewHolder.tvEmail = (TextView) convertView.findViewById(R.id.tv_email);

            convertView.setTag(viewHolder);
        }else {
            viewHolder =(ViewHolder) convertView.getTag();
        }
        Student student = listStudent.get(position);
        viewHolder.tvId.setText(String.valueOf(student.getmID()));
        viewHolder.tvName.setText(student.getmName());
        viewHolder.tvPhone.setText(student.getmPhoneNumber());
        viewHolder.tvAddress.setText(student.getmAddress());
        viewHolder.tvEmail.setText(student.getmEmail());

        return convertView;
    }

    public class ViewHolder{
        private TextView tvId;
        private TextView tvName;
        private TextView tvPhone;
        private TextView tvAddress;
        private TextView tvEmail;
    }
}
