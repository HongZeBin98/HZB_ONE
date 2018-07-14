package com.hongzebin.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.hongzebin.R;
import com.hongzebin.util.GlobalTools;

import java.util.Calendar;

/**
 * one页面（本来以为要向one的app一样有每天的推送，后来发现不要求做而被抛弃。。。）能进行日期的选择和空白listview
 * Created by 洪泽彬
 */
public class OneFragment extends Fragment {
    private DatePickerDialog mDateDialog;
    private Button mBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.one, container, false);
        final GlobalTools tool = new GlobalTools();

        //获取当前日期，并传入按键
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        mBtn = (Button) view.findViewById(R.id.datebutton);
        mBtn.setText(tool.turnDateStyle(day + " " + GlobalTools.getEnglishMonth(month + 1) + "." + year, 85, 0, 2));

        //日期选择的对话框
        mDateDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int year, int month,
                                  int day) {
                mBtn.setText(tool.turnDateStyle(day + " " + GlobalTools.getEnglishMonth(month + 1) + "." + year, 85, 0, 2));
            }
        }, year, month, day);
        DatePicker datePicker = mDateDialog.getDatePicker();

        //设置能选择的最早日期
        cal.clear();
        cal.set(2012, 10, 07);
        long millis = (long) (cal.getTimeInMillis() - 2.6226E9);
        datePicker.setMinDate(millis);

        //设置能选择的最晚日期为当前
        datePicker.setMaxDate(System.currentTimeMillis());
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击日期选择器按钮时显示出日期对话框
                mDateDialog.show();
            }
        });
        return view;
    }
}
