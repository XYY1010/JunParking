package com.example.xyy.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xyy.dbhelper.ParkingLot;
import com.example.xyy.junparking.DetailShowActivity;
import com.example.xyy.junparking.R;

import java.util.List;

/**
 * Created by XYY on 2018/4/28.
 */

public class ParkingLotAdapter extends RecyclerView.Adapter<ParkingLotAdapter.ViewHolder> {
    private List<ParkingLot> mParkingLotList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView parkingLotImg;
        TextView parkingLotName;
        TextView ratingScore;
        TextView monthSales;
        TextView isInRoom;
        TextView Distance;
        TextView Remarks;
        TextView Service1;
        TextView Service2;
        TextView Service3;
        TextView Service4;
        TextView Discount;
        TextView avgCost;
        LinearLayout ll_parkinglot;

        public ViewHolder(View view){
            super(view);
            parkingLotImg = (ImageView) view.findViewById(R.id.img_parking_lot);
            parkingLotName = (TextView) view.findViewById(R.id.tv_parkinglot_name);
            ratingScore = (TextView) view.findViewById(R.id.tv_rating_score);
            monthSales = (TextView) view.findViewById(R.id.tv_sales);
            isInRoom = (TextView) view.findViewById(R.id.tv_isinroom);
            Distance = (TextView) view.findViewById(R.id.tv_distance);
            Remarks = (TextView) view.findViewById(R.id.tv_remarks);
            Service1 = (TextView) view.findViewById(R.id.tv_service1);
            Service2 = (TextView) view.findViewById(R.id.tv_service2);
            Service3 = (TextView) view.findViewById(R.id.tv_service3);
            Service4 = (TextView) view.findViewById(R.id.tv_service4);
            Discount = (TextView) view.findViewById(R.id.tv_discount);
            avgCost = (TextView) view.findViewById(R.id.tv_avg_cost);
            ll_parkinglot = (LinearLayout) view.findViewById(R.id.ll_parking_lot_item);
        }
    }

    public ParkingLotAdapter(List<ParkingLot> parkingLotList){
        mParkingLotList = parkingLotList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parking_lot_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.ll_parkinglot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailShowActivity.class);
                intent.putExtra("ParkingLotName", holder.parkingLotName.getText().toString());
                v.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ParkingLot parkingLotInfo = mParkingLotList.get(position);
        holder.parkingLotName.setText(parkingLotInfo.getParkingLotName());
        holder.ratingScore.setText(String.valueOf(parkingLotInfo.getRatingScore())+"分");
        holder.monthSales.setText("月销"+parkingLotInfo.getSales()+"单");
        holder.isInRoom.setText(parkingLotInfo.getIsInRoom());
        holder.Distance.setText(parkingLotInfo.getDistance());
        holder.Remarks.setText(parkingLotInfo.getRemarks());
        boolean isService1 = parkingLotInfo.isService1();
        boolean isService2 = parkingLotInfo.isService2();
        boolean isService3 = parkingLotInfo.isService3();
        boolean isService4 = parkingLotInfo.isService4();
        if (isService1){
            holder.Service1.setVisibility(View.VISIBLE);
        }
        if (isService2){
            holder.Service2.setVisibility(View.VISIBLE);
        }
        if (isService3){
            holder.Service3.setVisibility(View.VISIBLE);
        }
        if (isService4){
            holder.Service4.setVisibility(View.VISIBLE);
        }
        holder.Discount.setText(parkingLotInfo.getDiscount());
        holder.avgCost.setText(String.valueOf(parkingLotInfo.getAvgPrice()));
        holder.parkingLotImg.setImageResource(parkingLotInfo.getImageSrc());
    }

    @Override
    public int getItemCount() {
        return mParkingLotList.size();
    }
}
