package com.inspur.nikki;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager pager;
    List<PageModel> pageModels = new ArrayList<>();

    {
        pageModels.add(new PageModel(new Fragment_QR(), R.string.qr_code));
        pageModels.add(new PageModel(new Fragment_AIDL(), R.string.aidl_function));
        pageModels.add(new PageModel(new Fragment_Contacts(), R.string.list_contact));
        pageModels.add(new PageModel(new Fragment_Copy(), R.string.asset_copy));
        pageModels.add(new PageModel(new Fragment_File(), R.string.file));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("Nikki",MD5Utils.md5("besto1560492284666"));

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                PageModel pageModel = pageModels.get(position);
                return pageModel.fragment;
            }

            @Override
            public int getCount() {
                return pageModels.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return getString(pageModels.get(position).titleRes);
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private class PageModel {
        int titleRes;
        Fragment fragment;

        PageModel(Fragment fragment, int titleRes) {
            this.fragment = fragment;
            this.titleRes = titleRes;
        }
    }
}
