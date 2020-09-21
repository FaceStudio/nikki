package com.inspur.nikki;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

public class HenCoderActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager pager;
    List<HenCoderActivity.PageModel> pageModels = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hencoder);

        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        Log.i("Nikki", "type:" + type);
        switch (type) {
            case 1: {
                pageModels.clear();
                pageModels.add(new PageModel(R.layout.sample_color, R.string.title_draw_color));
                pageModels.add(new PageModel(R.layout.sample_circle, R.string.title_draw_circle));
                pageModels.add(new PageModel(R.layout.sample_rect, R.string.title_draw_rect));
                pageModels.add(new PageModel(R.layout.sample_point, R.string.title_draw_point));
                pageModels.add(new PageModel(R.layout.sample_oval, R.string.title_draw_oval));
                pageModels.add(new PageModel(R.layout.sample_line, R.string.title_draw_line));
                pageModels.add(new PageModel(R.layout.sample_round_rect, R.string.title_draw_round_rect));
                pageModels.add(new PageModel(R.layout.sample_arc, R.string.title_draw_arc));
                pageModels.add(new PageModel(R.layout.sample_path, R.string.title_draw_path));
                pageModels.add(new PageModel(R.layout.sample_histogram, R.string.title_draw_histogram));
                pageModels.add(new PageModel(R.layout.sample_pie_chart, R.string.title_draw_pie_chart));
            }
            break;
            case 2: {
                pageModels.clear();
                pageModels.add(new PageModel(R.layout.sample_linear_gradient, R.string.title_linear_gradient));
                pageModels.add(new PageModel(R.layout.sample_radial_gradient, R.string.title_radial_gradient));
                pageModels.add(new PageModel(R.layout.sample_sweep_gradient, R.string.title_sweep_gradient));
                pageModels.add(new PageModel(R.layout.sample_bitmap_shader, R.string.title_bitmap_shader));
                pageModels.add(new PageModel(R.layout.sample_compose_shader, R.string.title_compose_shader));
                pageModels.add(new PageModel(R.layout.sample_lighting_color_filter, R.string.title_lighting_color_filter));
                pageModels.add(new PageModel(R.layout.sample_color_mask_color_filter, R.string.title_color_matrix_color_filter));
                pageModels.add(new PageModel(R.layout.sample_xfermode, R.string.title_xfermode));
                pageModels.add(new PageModel(R.layout.sample_stroke_cap, R.string.title_stroke_cap));
                pageModels.add(new PageModel(R.layout.sample_stroke_join, R.string.title_stroke_join));
                pageModels.add(new PageModel(R.layout.sample_stroke_miter, R.string.title_stroke_miter));
                pageModels.add(new PageModel(R.layout.sample_path_effect, R.string.title_path_effect));
                pageModels.add(new PageModel(R.layout.sample_shadow_layer, R.string.title_shader_layer));
                pageModels.add(new PageModel(R.layout.sample_mask_filter, R.string.title_mask_filter));
                pageModels.add(new PageModel(R.layout.sample_fill_path, R.string.title_fill_path));
                pageModels.add(new PageModel(R.layout.sample_text_path, R.string.title_text_path));
            }
            break;
            case 3: {
                pageModels.clear();
                pageModels.add(new PageModel(R.layout.sample_draw_text, R.string.title_draw_text));
                pageModels.add(new PageModel(R.layout.sample_static_layout, R.string.title_static_layout));
                pageModels.add(new PageModel(R.layout.sample_set_text_size, R.string.title_set_text_size));
                pageModels.add(new PageModel(R.layout.sample_set_typeface, R.string.title_set_typeface));
                pageModels.add(new PageModel(R.layout.sample_set_fake_bold_text, R.string.title_set_fake_bold_text));
                pageModels.add(new PageModel(R.layout.sample_set_strike_thru_text, R.string.title_set_strike_thru_text));
                pageModels.add(new PageModel(R.layout.sample_set_underline_text, R.string.title_set_underline_text));
                pageModels.add(new PageModel(R.layout.sample_set_text_skew_x, R.string.title_set_text_skew_x));
                pageModels.add(new PageModel(R.layout.sample_set_text_scale_x, R.string.title_set_text_scale_x));
                pageModels.add(new PageModel(R.layout.sample_set_text_align, R.string.title_set_text_align));
                pageModels.add(new PageModel(R.layout.sample_get_font_spacing, R.string.title_get_font_spacing));
                pageModels.add(new PageModel(R.layout.sample_measure_text, R.string.title_measure_text));
                pageModels.add(new PageModel(R.layout.sample_get_text_bounds, R.string.title_get_text_bounds));
                pageModels.add(new PageModel(R.layout.sample_get_font_metrics, R.string.title_get_font_metrics));
            }
            break;
            case 4: {
                pageModels.clear();
                pageModels.add(new PageModel(R.layout.sample_clip_rect, R.string.title_clip_rect));
                pageModels.add(new PageModel(R.layout.sample_clip_path, R.string.title_clip_path));
                pageModels.add(new PageModel(R.layout.sample_translate, R.string.title_translate));
                pageModels.add(new PageModel(R.layout.sample_scale, R.string.title_scale));
                pageModels.add(new PageModel(R.layout.sample_rotate, R.string.title_rotate));
                pageModels.add(new PageModel(R.layout.sample_skew, R.string.title_skew));
                pageModels.add(new PageModel(R.layout.sample_matrix_translate, R.string.title_matrix_translate));
                pageModels.add(new PageModel(R.layout.sample_matrix_scale, R.string.title_matrix_scale));
                pageModels.add(new PageModel(R.layout.sample_matrix_rotate, R.string.title_matrix_rotate));
                pageModels.add(new PageModel(R.layout.sample_matrix_skew, R.string.title_matrix_skew));
                pageModels.add(new PageModel(R.layout.sample_camera_rotate, R.string.title_camera_rotate));
                pageModels.add(new PageModel(R.layout.sample_camera_rotate_fixed, R.string.title_camera_rotate_fixed));
                pageModels.add(new PageModel(R.layout.sample_camera_rotate_hitting_face, R.string.title_camera_rotate_hitting_face));
                pageModels.add(new PageModel(R.layout.sample_flipboard, R.string.title_flipboard));
            }
            break;
            case 5: {
                pageModels.clear();
                pageModels.add(new PageModel(R.layout.sample_after_on_draw, R.string.title_after_on_draw));
                pageModels.add(new PageModel(R.layout.sample_before_on_draw, R.string.title_before_on_draw));
                pageModels.add(new PageModel(R.layout.sample_on_draw_layout, R.string.title_on_draw_layout));
                pageModels.add(new PageModel(R.layout.sample_dispatch_draw, R.string.title_dispatch_draw));
                pageModels.add(new PageModel(R.layout.sample_after_on_draw_foreground, R.string.title_after_on_draw_foreground));
                pageModels.add(new PageModel(R.layout.sample_before_on_draw_foreground, R.string.title_before_on_draw_foreground));
                pageModels.add(new PageModel(R.layout.sample_after_draw, R.string.title_after_draw));
                pageModels.add(new PageModel(R.layout.sample_before_draw, R.string.title_before_draw));
            }
            break;
            case 6: {
                pageModels.clear();
                pageModels.add(new PageModel(R.layout.sample_translation, R.string.title_translation));
                pageModels.add(new PageModel(R.layout.sample_rotation, R.string.title_rotation));
                pageModels.add(new PageModel(R.layout.sample_scale06, R.string.title_scale));
                pageModels.add(new PageModel(R.layout.sample_alpha, R.string.title_alpha));
                pageModels.add(new PageModel(R.layout.sample_multi_properties, R.string.title_multi_properties));
                pageModels.add(new PageModel(R.layout.sample_duration, R.string.title_duration));
                pageModels.add(new PageModel(R.layout.sample_interpolator, R.string.title_interpolator));
                pageModels.add(new PageModel(R.layout.sample_object_anomator, R.string.title_object_animator));
            }
            break;
            case 7: {
                pageModels.clear();
                pageModels.add(new PageModel(R.layout.sample_argb_evaluator, R.string.title_argb_evaluator));
                pageModels.add(new PageModel(R.layout.sample_hsv_evaluator, R.string.title_hsv_evaluator));
                pageModels.add(new PageModel(R.layout.sample_of_object, R.string.title_of_object));
                pageModels.add(new PageModel(R.layout.sample_property_values_holder, R.string.title_property_values_holder));
                pageModels.add(new PageModel(R.layout.sample_animator_set, R.string.title_animator_set));
                pageModels.add(new PageModel(R.layout.sample_keyframe, R.string.title_keyframe));
                pageModels.add(new PageModel(R.layout.sample_square_image_view, R.string.title_08));
            }
            break;

        }

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                PageModel pageModel = pageModels.get(position);
                return PageFragment.newInstance(pageModel.sampleLayoutRes);
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
        @LayoutRes
        int sampleLayoutRes;
        @StringRes
        int titleRes;

        PageModel(@LayoutRes int sampleLayoutRes, @StringRes int titleRes) {
            this.sampleLayoutRes = sampleLayoutRes;
            this.titleRes = titleRes;
        }
    }
}
