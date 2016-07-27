package dym.unique.funnyball.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import dym.unique.funnyball.R;
import dym.unique.funnyball.view.DotsTabView;

public class IntroduceActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager mVpPagers = null;
    private DotsTabView mDtvTabs = null;
    private ImageView mImgClose = null;
    private List<View> mListViews = new ArrayList<>();

    private PagerAdapter mPagerAdapter = new PagerAdapter() {

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)   {
            container.removeView(mListViews.get(position));
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mListViews.get(position), 0);//添加页卡
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return  mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0==arg1;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduce);

        mDtvTabs = (DotsTabView) findViewById(R.id.dtv_tabView);
        mVpPagers = (ViewPager) findViewById(R.id.vp_pages);
        mImgClose = (ImageView) findViewById(R.id.img_close);

        int[] images = new int[] {
                R.drawable.ic_introduce_page_1,
                R.drawable.ic_introduce_page_2,
                R.drawable.ic_introduce_page_3
        };

        ViewGroup.LayoutParams imgParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < images.length; i ++) {
            ImageView img = new ImageView(this);
            img.setLayoutParams(imgParams);
            img.setImageResource(images[i]);
            mListViews.add(img);
        }
        mVpPagers.setAdapter(mPagerAdapter);

        mVpPagers.addOnPageChangeListener(this);
        mImgClose.setOnClickListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 2) {
            mDtvTabs.setIndex(mVpPagers.getCurrentItem());
        }
    }

    @Override
    public void onClick(View v) {
        this.finish();
    }
}
