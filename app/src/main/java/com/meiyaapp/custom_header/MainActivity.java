package com.meiyaapp.custom_header;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.meiyaapp.custom_header.base.SimpleObservableScrollViewCallbacks;
import com.meiyaapp.custom_header.base.SimplePtrUIHandler;
import com.meiyaapp.custom_header.base.ViewSize;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

public class MainActivity extends Activity {

    @InjectView(R.id.lvList)
    ObservableListView lvList;
    @InjectView(R.id.ptrFrame)
    PtrFrameLayout ptrFrame;
    @InjectView(R.id.ivImage)
    ImageView ivImage;

    private int mImageInitHeight;//默认图片高度
    private Handler mHandler = new Handler();
    private int mScreenWidth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ch_main);
        ButterKnife.inject(this);
        ViewSize.init(getApplicationContext());

        mImageInitHeight = ViewSize.getSizeByDp(200).get();
        mScreenWidth = ViewSize.getScreenWidth().get();

        setupListView();
        setupPtrFrame();
        setupImage();
        setupScrollListener();
    }

    private void setupListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, getData());
        lvList.setAdapter(adapter);

        View header = LayoutInflater.from(this).inflate(R.layout.ch_main_header, null, false);
        lvList.addHeaderView(header);

        View stub_view = header.findViewById(R.id.vStubView);
        ViewSize.setHeight(stub_view, mImageInitHeight);
    }

    private void setupImage() {
        ViewSize.setHeight(ivImage, mImageInitHeight);

        Glide.with(this)
                .load("http://img4.duitang.com/uploads/item/201312/05/20131205172252_xAMJU.jpeg")
                .override(mScreenWidth, mScreenWidth)
                .into(ivImage);
    }


    private void setupPtrFrame() {
        // header
        final MaterialHeader header = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, 30, 0, 20);
        header.setPtrFrameLayout(ptrFrame);

        ptrFrame.setLoadingMinTime(1000);
        ptrFrame.setDurationToCloseHeader(1500);
        ptrFrame.setHeaderView(header);
        ptrFrame.addPtrUIHandler(header);

        ptrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrame.refreshComplete();
                    }
                }, 3000);
            }
        });

    }

    private void setupScrollListener() {
        ptrFrame.addPtrUIHandler(new SimplePtrUIHandler() {
            @Override
            public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
                super.onUIPositionChange(frame, isUnderTouch, status, ptrIndicator);
                mPrePtrScrollPosition = ptrIndicator.getCurrentPosY();
                updateWhenScroll();
            }
        });

        lvList.setScrollViewCallbacks(new SimpleObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
                mPreListViewScrollY = scrollY;
                updateWhenScroll();
            }
        });
    }

    private void updateWhenScroll() {
        int scrollY = mPreListViewScrollY - mPrePtrScrollPosition;
        ViewSize.setHeight(ivImage, mImageInitHeight - scrollY);
        ivImage.invalidate();
        ivImage.requestLayout();
    }

    int mPreListViewScrollY = 0; //listview的滑动
    int mPrePtrScrollPosition = 0; //下拉刷新的头部滑动

    private ArrayList<String> getData() {
        ArrayList<String> list = new ArrayList<>();
        list.add("180平米的房子");
        list.add("一个勤劳漂亮的老婆");
        list.add("一辆宝马");
        list.add("一个强壮且永不生病的身体");
        list.add("一个喜欢的事业");
        list.add("180平米的房子");
        list.add("一个勤劳漂亮的老婆");
        list.add("一辆宝马");
        list.add("一个强壮且永不生病的身体");
        list.add("一个喜欢的事业");
        list.add("180平米的房子");
        list.add("一个勤劳漂亮的老婆");
        list.add("一辆宝马");
        list.add("一个强壮且永不生病的身体");
        list.add("一个喜欢的事业");
        list.add("180平米的房子");
        list.add("一个勤劳漂亮的老婆");
        list.add("一辆宝马");
        list.add("一个强壮且永不生病的身体");
        list.add("一个喜欢的事业");
        list.add("180平米的房子");
        list.add("一个勤劳漂亮的老婆");
        list.add("一辆宝马");
        list.add("一个强壮且永不生病的身体");
        list.add("一个喜欢的事业");
        list.add("180平米的房子");
        list.add("一个勤劳漂亮的老婆");
        list.add("一辆宝马");
        list.add("一个强壮且永不生病的身体");
        list.add("一个喜欢的事业");
        list.add("180平米的房子");
        list.add("一个勤劳漂亮的老婆");
        list.add("一辆宝马");
        list.add("一个强壮且永不生病的身体");
        list.add("一个喜欢的事业");
        return list;
    }
}
