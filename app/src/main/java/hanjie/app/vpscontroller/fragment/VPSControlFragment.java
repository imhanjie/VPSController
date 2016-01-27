package hanjie.app.vpscontroller.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONObject;

import hanjie.app.vpscontroller.R;
import hanjie.app.vpscontroller.privacy.API;
import hanjie.app.vpscontroller.utils.DataParse;
import hanjie.app.vpscontroller.utils.HttpUtils;
import hanjie.app.vpscontroller.utils.SnackBarUtils;

/**
 * Created by hanjie on 2016/1/26.
 */
public class VPSControlFragment extends BaseFragment implements View.OnClickListener {

    private TextView mStartVPS;
    private TextView mRestartVPS;
    private TextView mStopVPS;
    private TextView mKillVPS;
    private AVLoadingIndicatorView mLoading;
    private LinearLayout mButtonArea;

    public VPSControlFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View controlView = inflater.inflate(R.layout.fragment_vps_control, container, false);
        mLoading = (AVLoadingIndicatorView) controlView.findViewById(R.id.loading);
        mButtonArea = (LinearLayout) controlView.findViewById(R.id.ll_buttonArea);
        mStartVPS = (TextView) controlView.findViewById(R.id.tv_startVPS);
        mStartVPS.setOnClickListener(this);
        mRestartVPS = (TextView) controlView.findViewById(R.id.tv_restartVPS);
        mRestartVPS.setOnClickListener(this);
        mStopVPS = (TextView) controlView.findViewById(R.id.tv_stopVPS);
        mStopVPS.setOnClickListener(this);
        mKillVPS = (TextView) controlView.findViewById(R.id.tv_killVPS);
        mKillVPS.setOnClickListener(this);
        return controlView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_startVPS: {
                new CommandAsyncTask().execute(API.getStartURL(getActivity()));
                break;
            }
            case R.id.tv_restartVPS: {
                new CommandAsyncTask().execute(API.getRestartURL(getActivity()));
                break;
            }
            case R.id.tv_stopVPS: {
                new CommandAsyncTask().execute(API.getStopURL(getActivity()));
                break;
            }
            case R.id.tv_killVPS: {
                new CommandAsyncTask().execute(API.getKillURL(getActivity()));
                break;
            }
        }
    }

    class CommandAsyncTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            mLoading.setVisibility(View.VISIBLE);
            mButtonArea.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Integer doInBackground(String... params) {
            int resultCode = 1;
            try {
                String data = HttpUtils.requestData(params[0]);
                JSONObject jObj = new JSONObject(data);
                resultCode = jObj.getInt(DataParse.ERROR);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultCode;
        }

        @Override
        protected void onPostExecute(Integer resultCode) {
            if (resultCode == 0) {
                // 命令执行成功
                showSimpleSnackBar("执行成功");
            } else {
                // 命令执行失败
                showSimpleSnackBar("执行失败");
            }
            mLoading.setVisibility(View.INVISIBLE);
            mButtonArea.setVisibility(View.VISIBLE);
        }
    }

    public void showSimpleSnackBar(String text) {
        Snackbar snackbar = Snackbar.make(mStartVPS, text, Snackbar.LENGTH_LONG);
        SnackBarUtils.customSnackBar(snackbar, getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.white), 0);
        snackbar.show();
    }


}
