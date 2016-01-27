package hanjie.app.vpscontroller.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hanjie on 2016/1/26.
 */
public class DataParse {

    // ------------------SHAREDPREFERENCES FILE NAME---------------------
    public static final String SERVICE_INFO = "service_info";
    public static final String CONFIG = "config";

    // ------------------SERVICE INFO---------------------
    public static final String STATUS = "status";
    public static final String SSH_PORT = "ssh_port";
    public static final String HOST_NAME = "hostname";
    public static final String NODE_LOCATION = "node_location";
    public static final String PLAN = "plan";
    public static final String PLAN_MONTHLY_DATA = "plan_monthly_data";
    public static final String OS = "os";
    public static final String EMAIL = "email";
    public static final String DATA_COUNTER = "data_counter";
    public static final String DATA_NEXT_RESET = "data_next_reset";
    public static final String IP_ADDRESSES = "ip_addresses";

    public static final String ERROR = "error";
    public static final String SUCCESS_CODE = "0";

    public static final String CACHED = "cached";
    public static final String ACCOUNT = "account";
    public static final String VEID = "veid";
    public static final String API_KEY = "api_key";
    public static final String SERVICE_INFO_SIZE = "service_info_size";
    public static final int SERVICE_INFO_SIZE_VALUE = 11;

    public static int parseServiceInfoData(Context context, String data) throws JSONException {
        SharedPreferences sp = context.getSharedPreferences(SERVICE_INFO, Context.MODE_PRIVATE);
        JSONObject jObj = new JSONObject(data);
        String code = jObj.getString(ERROR);
        if (!code.equals(SUCCESS_CODE)) {
            // 错误
            return 0;
        }
        SharedPreferences.Editor editor = sp.edit();
        String status = jObj.getJSONObject("vz_status").getString(STATUS);
        if (status.equals("running")) {
            status = "运行中";
        } else if (status.equals("mounted") || status.equals("stopped")) {
            status = "停止";
        }
        editor.putString(STATUS, status);
        editor.putString(SSH_PORT, jObj.getString(SSH_PORT));
        editor.putString(HOST_NAME, jObj.getString(HOST_NAME));
        editor.putString(NODE_LOCATION, jObj.getString(NODE_LOCATION));
        editor.putString(PLAN, jObj.getString(PLAN));
        editor.putString(PLAN_MONTHLY_DATA, FileSizeUtils.FormetFileSize(Long.parseLong(jObj.getString(PLAN_MONTHLY_DATA))));
        editor.putString(OS, jObj.getString(OS));
        editor.putString(EMAIL, jObj.getString(EMAIL));
        editor.putString(DATA_COUNTER, FileSizeUtils.FormetFileSize(Long.parseLong(jObj.getString(DATA_COUNTER))));
        editor.putString(DATA_NEXT_RESET, DateUtils.getCustomDate(Long.parseLong(jObj.getString(DATA_NEXT_RESET)) * 1000, "yyyy-MM-dd"));
        JSONArray ipArray = jObj.getJSONArray(IP_ADDRESSES);
        String firstIP = ipArray.getString(0);
        if (!TextUtils.isEmpty(firstIP)) {
            editor.putString(IP_ADDRESSES, firstIP);
        } else {
            editor.putString(IP_ADDRESSES, "");
        }
        editor.commit();
        // 写入相关配置
        SharedPreferences configSP = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor configEditor = configSP.edit();
        configEditor.putBoolean(CACHED, true);
        configEditor.putInt(SERVICE_INFO_SIZE, SERVICE_INFO_SIZE_VALUE);
        configEditor.commit();
        return SERVICE_INFO_SIZE_VALUE;
    }

    public static ArrayList<String> getServiceInfoNameList() {
        ArrayList<String> serviceInfoNameList = new ArrayList<String>();
        serviceInfoNameList.add("主机名称");
        serviceInfoNameList.add("主机状态");
        serviceInfoNameList.add("位置");
        serviceInfoNameList.add("IP地址");
        serviceInfoNameList.add("SSH端口");
        serviceInfoNameList.add("当前套餐");
        serviceInfoNameList.add("月流量");
        serviceInfoNameList.add("已用流量");
        serviceInfoNameList.add("流量清零");
        serviceInfoNameList.add("操作系统");
        serviceInfoNameList.add("邮箱");
        return serviceInfoNameList;
    }

    public static ArrayList<String> getServiceInfoKeyList() {
        ArrayList<String> serviceInfoKeyList = new ArrayList<String>();
        serviceInfoKeyList.add(HOST_NAME);
        serviceInfoKeyList.add(STATUS);
        serviceInfoKeyList.add(NODE_LOCATION);
        serviceInfoKeyList.add(IP_ADDRESSES);
        serviceInfoKeyList.add(SSH_PORT);
        serviceInfoKeyList.add(PLAN);
        serviceInfoKeyList.add(PLAN_MONTHLY_DATA);
        serviceInfoKeyList.add(DATA_COUNTER);
        serviceInfoKeyList.add(DATA_NEXT_RESET);
        serviceInfoKeyList.add(OS);
        serviceInfoKeyList.add(EMAIL);
        return serviceInfoKeyList;
    }

}
