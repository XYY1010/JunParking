package com.example.xyy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.xyy.bean.Flight;
import com.example.xyy.junparking.R;

import java.util.List;

import static com.example.xyy.junparking.R.mipmap.flight;


/**
 * Created by XYY on 2018/5/12.
 */

public class FlightAdapter extends ArrayAdapter<Flight>{
    private List<Flight> flightList;
    private Context mContext;
    private int mLayoutId;

    public FlightAdapter(Context context, int layoutId, List<Flight> flights){
        super(context, layoutId, flights);
        mContext = context;
        flightList = flights;
        mLayoutId = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Flight flight = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(mLayoutId, null);
            viewHolder = new ViewHolder();
            viewHolder.tvDepCity = (TextView) view.findViewById(R.id.tvDepCity);
            viewHolder.tvArrCity = (TextView) view.findViewById(R.id.tvArrCity);
            viewHolder.tvDepScheduled = (TextView) view.findViewById(R.id.tvDepScheduled);
            viewHolder.tvArrScheduled = (TextView) view.findViewById(R.id.tvArrScheduled);
            viewHolder.tvDepTime = (TextView) view.findViewById(R.id.tvDepTime);
            viewHolder.tvArrTime = (TextView) view.findViewById(R.id.tvArrTime);
            viewHolder.tvDepTerminal = (TextView) view.findViewById(R.id.tvDepTerminal);
            viewHolder.tvArrTerminal = (TextView) view.findViewById(R.id.tvArrTerminal);
            viewHolder.tvRate = (TextView) view.findViewById(R.id.tvRate);
            viewHolder.tvArrRate = (TextView) view.findViewById(R.id.tvArrRate);
            viewHolder.tvFlightNo = (TextView) view.findViewById(R.id.tvFlightNo);
            viewHolder.tvFlightState = (TextView) view.findViewById(R.id.tvFlightState);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvDepCity.setText(flight.getDepCity());
        viewHolder.tvArrCity.setText(flight.getArrCity());
        String[] strArr1 = flight.getDepScheduled().split("T");
        viewHolder.tvDepScheduled.setText(strArr1[0]);
        viewHolder.tvDepTime.setText(strArr1[1]);
        String[] strArr2 = flight.getArrScheduled().split("T");
        viewHolder.tvArrScheduled.setText(strArr2[0]);
        viewHolder.tvArrTime.setText(strArr2[1]);
        viewHolder.tvDepTerminal.setText(flight.getDepTerminal()+"航站楼");
        viewHolder.tvArrTerminal.setText(flight.getArrTerminal()+"航站楼");
        viewHolder.tvFlightNo.setText(flight.getFlightNo());
        viewHolder.tvFlightState.setText("航班状态："+flight.getFlightState());
        if (flight.getArrRate() == null) {
            viewHolder.tvArrRate.setText("准时抵达概率：无预测");
        }else {
            viewHolder.tvArrRate.setText("准时抵达概率：" + flight.getArrRate());
        }
        viewHolder.tvRate.setText("航班评价："+flight.getRate());
        return view;
    }

    class ViewHolder{
        TextView tvDepCity;
        TextView tvArrCity;
        TextView tvDepTerminal;
        TextView tvArrTerminal;
        TextView tvDepScheduled;
        TextView tvArrScheduled;
        TextView tvDepTime;
        TextView tvArrTime;
        TextView tvRate;
        TextView tvArrRate;
        TextView tvFlightState;
        TextView tvFlightNo;
    }
}
