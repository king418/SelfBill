package com.king.selfbill;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * User: king
 * Date: 2015/4/10
 */
public class ListViewAdapter extends BaseAdapter{
    private Context context;
    private List<Map<String,Object>> list;

    public ListViewAdapter(Context context,List<Map<String,Object>> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_main,parent,false);
            holder = new ViewHolder();
            holder.tv_listview_cost = (TextView) convertView.findViewById(R.id.tv_listview_cost);
            holder.tv_listview_date = (TextView) convertView.findViewById(R.id.tv_listview_date);
            holder.tv_listview_subtype = (TextView) convertView.findViewById(R.id.tv_listview_subtype);
            holder.tv_listview_type = (TextView) convertView.findViewById(R.id.tv_listview_type);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        float cost = Float.parseFloat(list.get(position).get("cost_in").toString());

        if (cost>=0){
            holder.tv_listview_cost.setTextColor(Color.RED);
            holder.tv_listview_subtype.setVisibility(View.GONE);
        }else if (cost<0){
            holder.tv_listview_cost.setTextColor(Color.GREEN);
            holder.tv_listview_subtype.setVisibility(View.VISIBLE);
            holder.tv_listview_subtype.setText(list.get(position).get("subtype").toString());
        }
        holder.tv_listview_cost.setText(""+cost);
        holder.tv_listview_type.setText(list.get(position).get("typename").toString());
        holder.tv_listview_date.setText(list.get(position).get("date").toString());

        return convertView;
    }

    class ViewHolder{
        private TextView tv_listview_cost;
        private TextView tv_listview_type;
        private TextView tv_listview_subtype;
        private TextView tv_listview_date;
    }
}
