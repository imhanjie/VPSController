package hanjie.app.vpscontroller.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import hanjie.app.vpscontroller.R;
import hanjie.app.vpscontroller.utils.DataParse;
import hanjie.app.vpscontroller.utils.DateUtils;
import hanjie.app.vpscontroller.utils.FileSizeUtils;

/**
 * Created by hanjie on 2016/1/26.
 */
public class VPSInfoAdapter extends RecyclerView.Adapter<VPSInfoAdapter.ViewHolder> {

    private int mSize;
    private ArrayList<String> mServiceInfoNameList;
    private ArrayList<String> mServiceInfoKeyList;
    private SharedPreferences mSP;

    /**
     * 构造Adapter的同时需要一个数组
     *
     * @param size
     */
    public VPSInfoAdapter(Context context, int size) {
        mSize = size;
        mSP = context.getSharedPreferences(DataParse.SERVICE_INFO, Context.MODE_PRIVATE);
        mServiceInfoNameList = DataParse.getServiceInfoNameList();
        mServiceInfoKeyList = DataParse.getServiceInfoKeyList();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public VPSInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vps_info, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mKey.setText(mServiceInfoNameList.get(position));
        holder.mValue.setText(mSP.getString(mServiceInfoKeyList.get(position), null));
    }

    @Override
    public int getItemCount() {
        return mSize;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mKey;
        public TextView mValue;

        public ViewHolder(View view) {
            super(view);
            mKey = (TextView) view.findViewById(R.id.tv_key);
            mValue = (TextView) view.findViewById(R.id.tv_value);
        }
    }

}