package altong.mon.schedulesample;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.philliphsu.bottomsheetpickers.time.BottomSheetTimePickerDialog;
import com.philliphsu.bottomsheetpickers.time.grid.GridTimePickerDialog;
import com.ribell.colorpickerview.ColorPickerView;
import com.ribell.colorpickerview.interfaces.ColorPickerViewListener;

import java.util.Calendar;
import java.util.Vector;


public class AddScheduleDialog extends DialogFragment implements View.OnClickListener,
        BottomSheetTimePickerDialog.OnTimeSetListener, ColorPickerViewListener {
    private Vector<TypeColor> colors;
    private TextView startTv, endTv;
    OnMyDialogResult mDialogResult;
    EditText shiftNameEt;
    GridTimePickerDialog grid;
    Spinner spinner;
    public AddScheduleDialog() {
        super();
    }
    Calendar now;
    String red,green,blue;
    Fragment fragment;
    TextView week, weekend;
    String type;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_schedule, container, false);
        spinner = (Spinner) v.findViewById(R.id.workTypeS);
        fragment = getActivity().getSupportFragmentManager().findFragmentByTag("addShift");
        endTv = (TextView) v.findViewById(R.id.endTv);
        startTv = (TextView) v.findViewById(R.id.startTv);
        ImageView closeIv = (ImageView) v.findViewById(R.id.closeIv);
        TextView addShiftTv = (TextView) v.findViewById(R.id.addShiftTv);
        shiftNameEt = (EditText) v.findViewById(R.id.shiftNameEt);
        Bundle args = getArguments();
        week = (TextView) v.findViewById(R.id.weekTv);
        weekend = (TextView) v.findViewById(R.id.weekendTv);
        endTv.setOnClickListener(this);
        startTv.setOnClickListener(this);
        closeIv.setOnClickListener(this);
        addShiftTv.setOnClickListener(this);
        weekend.setOnClickListener(this);
        week.setOnClickListener(this);
        String[] wageType = new String[]{
                "Open",
                "Middle",
                "Close",
                "Allday",
                "Night",
                "OverNight"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, wageType);
        spinner.setAdapter(adapter);
        if (args != null) {
            String name = args.getString("name");
            String type = args.getString("type");
            String week = args.getString("week");
            String time = args.getString("time");
            Log.d(type, week + "1" + time);
            String[] times = time.split("~");
            startTv.setText(times[0].trim());
            endTv.setText(times[1].trim());
            spinner.setPrompt(type);
            shiftNameEt.setText(name);

        }

        try {
            ColorPickerView colorGv = (ColorPickerView) v.findViewById(R.id.colorGv);
            colorGv.setListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*colors = new Vector<>();
        for (int i = 0; i < 21; i++) {
            String r = String.valueOf((int) ((Math.random() * 255) + 1));
            String g = String.valueOf((int) ((Math.random() * 255) + 1));
            String b = String.valueOf((int) ((Math.random() * 255) + 1));
            Log.d("color", r + "/" + g + "/" + b);
            colors.add(new TypeColor(r,g,b));
        }
        colorGv.setAdapter(new ColorAdapter(colors, getActivity().getLayoutInflater()));
        colorGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < 21; i++) {
                    if (i == position) {
                        colorGv.getChildAt(i).setBackgroundResource(R.drawable.color_border);
                        red = colors.get(position).getRed();
                        green = colors.get(position).getGreen();
                        blue = colors.get(position).getBlue();
                    } else {
                        colorGv.getChildAt(i).setBackgroundResource(0);
                    }

                }
            }
        });*/

        return v;
    }

    @Override
    public void onColorPickerClick(int colorPosition) {
        Log.d("position", colorPosition + "");
        switch (colorPosition){
            case 0:
                red = "76";
                green ="178";
                blue = "212";
                break;
            case 1:
                red = "132";
                green ="77";
                blue = "158";
                break;
            case 2:
                red = "235";
                green ="123";
                blue = "45";
                break;
            case 3:
                red = "249";
                green ="237";
                blue = "58";
                break;
            case 4:
                red = "48";
                green ="73";
                blue = "155";
                break;
            case 5:
                red = "238";
                green ="64";
                blue = "53";
                break;
            case 6:
                red = "240";
                green ="163";
                blue = "47";
                break;
            case 7:
                red = "86";
                green ="185";
                blue = "73";
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addShiftTv:
                String shiftName = shiftNameEt.getText().toString();
                String shiftType = spinner.getSelectedItem().toString();
                String shiftTime = startTv.getText().toString() + "~" + endTv.getText().toString();
                if (TextUtils.isEmpty(shiftName)) {
                    Toast.makeText(getContext(),"이름을 입력해주세요.",Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(shiftType)) {
                    Toast.makeText(getContext(),"타입을 입력해주세요.",Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(startTv.getText().toString()) ||TextUtils.isEmpty(endTv.getText().toString())) {
                    Toast.makeText(getContext(),"시간 대를 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(red) ||TextUtils.isEmpty(green) ||TextUtils.isEmpty(blue) ) {
                    Toast.makeText(getContext(),"색상을 입력해주세요.",Toast.LENGTH_SHORT).show();
                } else {
                    if (fragment != null) {
                        if( mDialogResult != null ){
                            mDialogResult.finish(shiftName+"/"+shiftType+"/"+shiftTime+"/"+red+"/"+green+"/"+blue);
                        }
                        DialogFragment dialogFragment = (DialogFragment) fragment;
                        dialogFragment.dismiss();
                    }
                }

                break;
            case R.id.closeIv:
                if (fragment != null) {
                    DialogFragment dialogFragment = (DialogFragment) fragment;
                    dialogFragment.dismiss();
                }
                break;
            case R.id.startTv:
                now = Calendar.getInstance();
                openTimePicker("startTime", now);
                break;
            case R.id.endTv:
                now = Calendar.getInstance();
                openTimePicker("endTime", now);
                break;
            case R.id.weekTv:
                week.setTextColor(Color.WHITE);
                week.setBackgroundResource(R.drawable.color_border);
                weekend.setTextColor(Color.BLACK);
                weekend.setBackgroundResource(R.drawable.border);
                type = "week";
                break;
            case R.id.weekendTv:
                weekend.setTextColor(Color.WHITE);
                weekend.setBackgroundResource(R.drawable.color_border);
                week.setTextColor(Color.BLACK);
                week.setBackgroundResource(R.drawable.border);
                type = "weekend";
                break;
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (fragment != null) {
            DialogFragment dialogFragment = (DialogFragment) fragment;
            dialogFragment.dismiss();
        }
    }

    public void setDialogResult(OnMyDialogResult dialogResult){
        mDialogResult = dialogResult;
    }

    public interface OnMyDialogResult{
        void finish(String result);
    }

    @Override
    public void onTimeSet(ViewGroup viewGroup, int hourOfDay, int minute) {
        String hour = "", min = "";

        if (hourOfDay<10) hour += "0" + hourOfDay;
        else hour = "" + hourOfDay;
        if(minute == 0) min += "0"+minute;
        else min = "" + minute;
        String time = hour + ":" + min;
        switch (grid.getTag()) {
            case "startTime" :
                startTv.setText(time);
                break;
            case "endTime" :
                endTv.setText(time);
                break;
        }
    }

    private void openTimePicker (String tag, Calendar calendar) {
        grid = GridTimePickerDialog.newInstance(
                this,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(getContext()));
        grid.show(getActivity().getSupportFragmentManager(),tag);
    }
}
