package hanjie.app.vpscontroller.privacy;

import android.content.Context;

import hanjie.app.vpscontroller.utils.DataParse;

/**
 * Created by hanjie on 2016/1/26.
 */
public class API {

    public static final String COMMAND_GET_LIVE_SERVICE_INFO = "getLiveServiceInfo";
    public static final String COMMAND_START_VPS = "start";
    public static final String COMMAND_RESTART_VPS = "restart";
    public static final String COMMAND_STOP_VPS = "stop";
    public static final String COMMAND_KILL_VPS = "kill";

    public static String getVEID(Context context) {
        return context.getSharedPreferences(DataParse.CONFIG, Context.MODE_PRIVATE).getString(DataParse.VEID, "");
    }

    public static String getAPIKey(Context context) {
        return context.getSharedPreferences(DataParse.CONFIG, Context.MODE_PRIVATE).getString(DataParse.API_KEY, "");
    }

    public static String getInfoURL(Context context) {
        return "https://api.kiwivm.it7.net/v1/" + COMMAND_GET_LIVE_SERVICE_INFO + "?veid=" + getVEID(context) + "&api_key=" + getAPIKey(context);
    }

    public static String getStartURL(Context context) {
        return "https://api.kiwivm.it7.net/v1/" + COMMAND_START_VPS + "?veid=" + getVEID(context) + "&api_key=" + getAPIKey(context);
    }

    public static String getRestartURL(Context context) {
        return "https://api.kiwivm.it7.net/v1/" + COMMAND_RESTART_VPS + "?veid=" + getVEID(context) + "&api_key=" + getAPIKey(context);
    }

    public static String getStopURL(Context context) {
        return "https://api.kiwivm.it7.net/v1/" + COMMAND_STOP_VPS + "?veid=" + getVEID(context) + "&api_key=" + getAPIKey(context);
    }

    public static String getKillURL(Context context) {
        return "https://api.kiwivm.it7.net/v1/" + COMMAND_KILL_VPS + "?veid=" + getVEID(context) + "&api_key=" + getAPIKey(context);
    }

}
