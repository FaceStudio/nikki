package com.inspur.nikki;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.inspur.nikki.utils.CommandExecution;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager pager;
    List<PageModel> pageModels = new ArrayList<>();

    {
        pageModels.add(new PageModel(new Fragment_HenCoder(), R.string.hencoder));
        pageModels.add(new PageModel(new Fragment_CirclePhoto(), R.string.circle_photo));
        pageModels.add(new PageModel(new Fragment_ToastTracker(), R.string.toast_tracker));
        pageModels.add(new PageModel(new Fragment_Contacts(), R.string.list_contact));
        pageModels.add(new PageModel(new Fragment_Copy(), R.string.asset_copy));
        pageModels.add(new PageModel(new Fragment_File(), R.string.file));
        pageModels.add(new PageModel(new Fragment_DashBoard(), R.string.dashboard));
        pageModels.add(new PageModel(new Fragment_Diagnose(), R.string.diagnose));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
