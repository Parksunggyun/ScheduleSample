package altong.mon.schedulesample;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Vector;


class ScheduleAdapter extends BaseAdapter {

    private Vector<Schedule> schedules;
    private LayoutInflater inflater;

    ScheduleAdapter(Vector<Schedule> schedules, LayoutInflater inflater) {
        this.schedules = schedules;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return schedules.size();
    }

    @Override
    public Object getItem(int position) {
        return schedules.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        System.out.println("getview:"+position+" "+convertView);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_schedule, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.workNameTv = (TextView) convertView.findViewById(R.id.workNameTv);
            viewHolder.typeTv = (TextView) convertView.findViewById(R.id.workTypeTv);
            viewHolder.weekOrWeekendTv = (TextView) convertView.findViewById(R.id.workWeekOrWeekendTv);
            viewHolder.startEndTv = (TextView) convertView.findViewById(R.id.workStartEndTv);
            viewHolder.typeColorV = convertView.findViewById(R.id.typeColorV);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        int typeColor;
        switch (schedules.get(position).getType()) {
            case "Open" :
                typeColor = Color.RED;
                break;
            case "Close" :
                typeColor = Color.BLUE;
                break;
            case "Middle" :
                typeColor = Color.LTGRAY;
                break;
            case "AllDay" :
                typeColor = Color.MAGENTA;
                break;
            case "Night" :
                typeColor = Color.GREEN;
                break;
            case "OverNight" :
                typeColor = Color.CYAN;
                break;
            default:
                typeColor = schedules.get(position).getColor();
                break;

        }
        viewHolder.workNameTv.setText(schedules.get(position).getName());
        viewHolder.typeColorV.setBackgroundColor(typeColor);
        viewHolder.typeTv.setText(schedules.get(position).getType());
        viewHolder.weekOrWeekendTv.setText(schedules.get(position).getWeekOrWeekend());
        viewHolder.startEndTv.setText(schedules.get(position).getStartEndTime());

        return convertView;
    }

    private class ViewHolder {
        TextView typeTv;
        TextView weekOrWeekendTv;
        TextView startEndTv;
        TextView workNameTv;
        View typeColorV;

    }

}
