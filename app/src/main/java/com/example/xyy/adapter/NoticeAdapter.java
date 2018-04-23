package com.example.xyy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xyy.bean.Notice;
import com.example.xyy.junparking.R;

import java.util.List;


/**
 * Created by XYY on 2018/4/23.
 */

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    private List<Notice> mNoticeList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View noticeView;
        TextView content;
        TextView time;
        ImageView noticeImage;
        public ViewHolder (View view){
            super(view);
            noticeView = view;
            noticeImage = (ImageView) view.findViewById(R.id.notice_img);
            content = (TextView) view.findViewById(R.id.tv_noticecontent);
            time = (TextView) view.findViewById(R.id.tv_noticetime);
        }
    }

    public  NoticeAdapter(List<Notice> noticeList){
        mNoticeList = noticeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.noticeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Notice notice = mNoticeList.get(position);
                Toast.makeText(v.getContext(), "您点击了"+notice.getContent()+"公告,发布于:"+notice.getTime(), Toast.LENGTH_SHORT).show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Notice notice = mNoticeList.get(position);
        holder.noticeImage.setImageResource(notice.getImgId());
        holder.content.setText(notice.getContent());
        holder.time.setText(notice.getTime());
    }

    @Override
    public int getItemCount() {
        return mNoticeList.size();
    }
}
