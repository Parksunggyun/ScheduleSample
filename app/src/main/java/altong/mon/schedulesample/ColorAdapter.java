package altong.mon.schedulesample;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Vector;

public class ColorAdapter extends BaseAdapter {

    Vector<TypeColor> colors;
    LayoutInflater inflater;

    public ColorAdapter(Vector<TypeColor> colors, LayoutInflater inflater) {
        this.colors = colors;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return colors.size();
    }

    @Override
    public Object getItem(int position) {
        return colors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_color, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.colorTv = (TextView) convertView.findViewById(R.id.typeColorTv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.colorTv.setTextColor(Color.rgb(
                Integer.parseInt(colors.get(position).getRed()),
                Integer.parseInt(colors.get(position).getGreen()),
                Integer.parseInt(colors.get(position).getBlue()))
        );

        return convertView;
    }


    class ViewHolder {
        TextView colorTv;
    }
}
