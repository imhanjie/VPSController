package hanjie.app.vpscontroller.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import hanjie.app.vpscontroller.R;
import hanjie.app.vpscontroller.privacy.API;
import hanjie.app.vpscontroller.utils.DataParse;
import hanjie.app.vpscontroller.utils.DialogUtils;

/**
 * Created by hanjie on 2016/1/27.
 */
public class SetupActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private EditText mVEIDEditTextView;
    private EditText mApiKeyEditTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        initToolbar();
        mVEIDEditTextView = (EditText) findViewById(R.id.et_veid);
        mVEIDEditTextView.setText(API.getVEID(this));
        mApiKeyEditTextView = (EditText) findViewById(R.id.et_apikey);
        mApiKeyEditTextView.setText(API.getAPIKey(this));
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
    }

    public void confirm(View view) {
        String veid = mVEIDEditTextView.getText().toString();
        String apiKey = mApiKeyEditTextView.getText().toString();
        if (!TextUtils.isEmpty(veid) && !TextUtils.isEmpty(apiKey)) {
            SharedPreferences sp = getSharedPreferences(DataParse.CONFIG, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(DataParse.VEID, veid);
            editor.putString(DataParse.API_KEY, apiKey);
            editor.putBoolean(DataParse.ACCOUNT, true);
            editor.commit();
            enterHome();
        }
    }

    private void enterHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    public void help(View view) {
        DialogUtils.showAlertDialog(this, "如何找到这些信息 ?", getString(R.string.help_doc), "确定", null, null, new DialogUtils.DialogCallBack() {
            @Override
            public void onPositiveButton(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

            @Override
            public void onNegativeButton(DialogInterface dialog, int which) {

            }

            @Override
            public void onNeutralButton(DialogInterface dialog, int which) {

            }
        });
    }

}
