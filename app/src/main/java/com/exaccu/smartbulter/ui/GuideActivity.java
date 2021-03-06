package com.exaccu.smartbulter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.exaccu.smartbulter.MainActivity;
import com.exaccu.smartbulter.R;
import com.exaccu.smartbulter.utils.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.mViewPager)
    ViewPager mViewPager;
    @BindView(R.id.point1)
    ImageView point1;
    @BindView(R.id.point2)
    ImageView point2;
    @BindView(R.id.point3)
    ImageView point3;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    private List<View> mList = new ArrayList<>();

    private View view1, view2, view3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);

        view1 = View.inflate(this, R.layout.pager_item_one, null);
        view2 = View.inflate(this, R.layout.pager_item_two, null);
        view3 = View.inflate(this, R.layout.pager_item_three, null);
        mList.add(view1);
        mList.add(view2);
        mList.add(view3);
        //"进入主页"按钮的点击
        view3.findViewById(R.id.btn_start).setOnClickListener(this);
        //默认三个小圆点的选中状态
        setPointImage(true, false, false);

        mViewPager.setAdapter(new GuideAdapter());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                L.i("position:" + position);
                switch (position) {
                    case 0:
                        setPointImage(true, false, false);
                        ivBack.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setPointImage(false, true, false);
                        ivBack.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setPointImage(false, false, true);
                        ivBack.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ivBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.iv_back:
                startActivity(new Intent(this,LoginActivity.class));
        }
    }

    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ((ViewPager) container).addView(mList.get(position));
            return mList.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            ((ViewPager) container).removeView(mList.get(position));

        }
    }

    /**
     * 设置三个小圆点的选中效果
     *
     * @param isCheck1
     * @param isCheck2
     * @param isCheck3
     */
    private void setPointImage(boolean isCheck1, boolean isCheck2, boolean isCheck3) {
        if (isCheck1) {
            point1.setBackgroundResource(R.drawable.point_on);
        } else {
            point1.setBackgroundResource(R.drawable.point_off);
        }
        if (isCheck2) {
            point2.setBackgroundResource(R.drawable.point_on);
        } else {
            point2.setBackgroundResource(R.drawable.point_off);
        }
        if (isCheck3) {
            point3.setBackgroundResource(R.drawable.point_on);
        } else {
            point3.setBackgroundResource(R.drawable.point_off);
        }
    }
}
