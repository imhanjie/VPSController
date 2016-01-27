package hanjie.app.vpscontroller.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONObject;

import hanjie.app.vpscontroller.R;
import hanjie.app.vpscontroller.adapter.VPSInfoAdapter;
import hanjie.app.vpscontroller.privacy.API;
import hanjie.app.vpscontroller.utils.DataParse;
import hanjie.app.vpscontroller.utils.DividerItemDecoration;
import hanjie.app.vpscontroller.utils.DividerLine;
import hanjie.app.vpscontroller.utils.HttpUtils;
import hanjie.app.vpscontroller.utils.SnackBarUtils;

/**
 * Created by hanjie on 2016/1/26.
 */
public class VPSInfoFragment extends BaseFragment {


    private RecyclerView mRecyclerView;
    private VPSInfoAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AVLoadingIndicatorView mLoading;

    public VPSInfoFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("bingo", "info onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("bingo", "info onCreateView");
        View vpsInfoView = inflater.inflate(R.layout.fragment_vps_info, container, false);
        mLoading = (AVLoadingIndicatorView) vpsInfoView.findViewById(R.id.loading);
        mRecyclerView = (RecyclerView) vpsInfoView.findViewById(R.id.recyclerView_vpsInfo);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        // 判断是否已经缓存
        SharedPreferences configSP = getActivity().getSharedPreferences(DataParse.CONFIG, Context.MODE_PRIVATE);
        if (configSP.getBoolean(DataParse.CACHED, false)) {
            // 已经缓存
            updateRecyclerView(configSP.getInt(DataParse.SERVICE_INFO_SIZE, 0));
        } else {
            refreshServiceInfo();
        }
        return vpsInfoView;
    }

    /**
     * 刷新serviceInfo信息
     */
    public void refreshServiceInfo() {
        new AccessInfoAsyncTask().execute(API.getInfoURL(getActivity()));
    }

    class AccessInfoAsyncTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            mLoading.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Integer doInBackground(String... params) {
            String data = null;
            int size = 0;
            try {
                data = HttpUtils.requestData(params[0]);
                if (!TextUtils.isEmpty(data)) {
                    JSONObject jObj = new JSONObject(data);
                    int resultCode = jObj.getInt(DataParse.ERROR);
                    if (resultCode == 0) {
                        // 解析json数据到sp
                        size = DataParse.parseServiceInfoData(getActivity(), data);
                    } else {
                        showSimpleSnackBar("请检查参数配置");
                    }
                }
            } catch (Exception e) {
                // 命令执行失败
                showSimpleSnackBar("请检查网络设置");
                e.printStackTrace();
            }
            return size;
        }

        @Override
        protected void onPostExecute(Integer size) {
            mLoading.setVisibility(View.INVISIBLE);
            updateRecyclerView(size);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void updateRecyclerView(Integer size) {
        if (mRecyclerView.getAdapter() == null) {
            mAdapter = new VPSInfoAdapter(getActivity(), size);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setVisibility(View.VISIBLE);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    public void showSimpleSnackBar(String text) {
        Snackbar snackbar = Snackbar.make(mLoading, text, Snackbar.LENGTH_LONG);
        SnackBarUtils.customSnackBar(snackbar, getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.white), 0);
        snackbar.show();
    }

}

