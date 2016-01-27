package hanjie.app.vpscontroller.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hanjie.app.vpscontroller.R;
import hanjie.app.vpscontroller.fragment.VPSControlFragment;
import hanjie.app.vpscontroller.fragment.VPSInfoFragment;
import hanjie.app.vpscontroller.utils.DataParse;

public class HomeActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private SharedPreferences mSP;
    private FloatingActionButton mFabRefresh;
    private VPSInfoFragment mVPSInfoFragment;
    private VPSControlFragment mVPSControlFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (!hasAccount()) {
            enterSetup();
        }

        initToolbar();

        initViewPager();

        initTabLayout();

        initFab();

    }

    private void enterSetup() {
        startActivity(new Intent(this, SetupActivity.class));
        finish();
    }

    /**
     * 检查是否已设置账户信息
     */
    private boolean hasAccount() {
        return getSharedPreferences(DataParse.CONFIG, MODE_PRIVATE).getBoolean(DataParse.ACCOUNT, false);
    }

    private void initFab() {
        mFabRefresh = (FloatingActionButton) findViewById(R.id.fab_refresh);
        mFabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVPSInfoFragment.refreshServiceInfo();
            }
        });
    }

    private void initTabLayout() {
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        // 为Toolbar设置菜单点击监听
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_config: {
                        enterSetup();
                        break;
                    }
                }
                return false;
            }
        });
    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mVPSInfoFragment = new VPSInfoFragment();
        mVPSControlFragment = new VPSControlFragment();
        adapter.addFragment(mVPSInfoFragment, "VPS信息");
        adapter.addFragment(mVPSControlFragment, "VPS控制");
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position != 0) {
                    mFabRefresh.hide();
                } else {
                    mFabRefresh.show();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        /**
         * 自定义添加Fragment方法
         *
         * @param fragment
         * @param title
         */
        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }


}
