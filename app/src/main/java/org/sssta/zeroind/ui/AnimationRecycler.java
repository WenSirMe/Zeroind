package org.sssta.zeroind.ui;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.sssta.zeroind.R;
import org.sssta.zeroind.adapter.RecyclerWrapAdapter;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 林韬 on 2015/10/6.
 */
public class AnimationRecycler extends RecyclerView {

    //注意adapter

    private Context mContext;

    private RecyclerWrapAdapter mAdapter;

    private final static int RELEASE_To_REFRESH = 0;// 下拉过程的状态值
    private final static int PULL_To_REFRESH = 1; // 从下拉返回到不刷新的状态值
    private final static int REFRESHING = 2;// 正在刷新的状态值
    private final static int DONE = 3;
    private final static int LOADING = 4;

    // 实际的padding的距离与界面上偏移距离的比例
    private float radio = 3.0f;
    private LayoutInflater inflater;

    // ListView头部下拉刷新的布局
    private LinearLayout headerLayout;
    private ImageView headerImage;

    // 定义头部下拉刷新的布局的高度
    private int headerContentHeight;
    private int headerImageHeight;

    private int startY;
    private int state;
    private boolean isBack;

    private boolean isBitmapGetted = false;

    private LinearLayoutManager mLinearLayoutManager;

    private int headerState;

    // 用于保证startY的值在一个完整的touch事件中只被记录一次
    private boolean isRecored;
    private boolean isRefreshable;

    //刷新接口
    OnRefreshListener onRefreshListener;

    Matrix matrix = new Matrix();
    float total_degress = 0;
    float degress = 0;
    float multiple_degress = 1.5f;
    Timer timer;
    TimerTask timerTask;
    int headerPadding = 0;


    public AnimationRecycler(Context context) {
        super(context);
        init(context);
    }

    public AnimationRecycler(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AnimationRecycler(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }



    OnScrollListener onScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (mLinearLayoutManager!=null && headerState <= 1) {
                isRefreshable = true;
            } else {
                isRefreshable = false;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (mLinearLayoutManager!=null) {
                headerState = mLinearLayoutManager.findFirstVisibleItemPosition();

                Log.d("headerState",headerState+"");

            }
        }
    };


    /**
     * 要实现刷新接口，如果下拉刷新
     */
    public interface OnRefreshListener {
        public void onRefresh();
    }


    /**
     * 设置下拉刷新监听器
     * @param onRefreshListener
     */
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }


    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
    }


    private void measureView(View child) {
        ViewGroup.LayoutParams params = child.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0,
                params.width);
        int lpHeight = params.height;
        int childHeightSpec;
        if (lpHeight > 0) {

            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);

        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
//        if (headerLayout == null) {
//            headerLayout = (LinearLayout) inflater
//                    .inflate(R.layout.pull_to_refresh_header, null);
//
//            headerImage = (ImageView) headerLayout
//                    .findViewById(R.id.pull_header_image);
//        }
        mAdapter = (RecyclerWrapAdapter) adapter;
        if (mAdapter.getHeaderLayout() == null) {
            headerLayout = (LinearLayout) inflater
                    .inflate(R.layout.pull_to_refresh_header, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
            headerLayout.setLayoutParams(layoutParams);
            headerImage = (ImageView) headerLayout
                    .findViewById(R.id.pull_header_image);
            mAdapter.setHeaderLayout(headerLayout);
        } else {
            headerLayout = (LinearLayout) mAdapter.getHeaderLayout();
            headerImage = (ImageView) headerLayout.getChildAt(0);
        }
        setOnScrollListener(onScrollListener);
            // 设置下拉刷新图标的最小高度和宽度
        measureView(headerLayout);
        //重新计算view宽高，否者为(0，0);
        measureView(headerImage);
        headerContentHeight = headerLayout.getMeasuredHeight();
        headerImageHeight = headerImage.getMeasuredWidth();

        Log.d("height","headerContent "+headerContentHeight + " headerimage " + headerImageHeight);

        // 设置内边距，正好距离顶部为一个负的整个布局的高度，正好把头部隐藏
        headerLayout.setPadding(0, -1 * headerContentHeight, 0, 0);
        // 重绘一下
        headerLayout.invalidate();
        // 设置滚动监听事件
        // 设置旋转动画事件

        // 一开始的状态就是下拉刷新完的状态，所以为DONE
        state = DONE;
        // 是否正在刷新
        isRefreshable = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (isRefreshable) {
            if (!isBitmapGetted) {
                headerImage.setDrawingCacheEnabled(true);
                headerImage.setDrawingCacheEnabled(false);
            }
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!isRecored) {
                        isRecored = true;

                        /////////////////
                        if (timer!=null)
                        timer.cancel();
                        runTimer();

                        startY = (int) e.getY();   // 手指按下时记录当前位置
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (state != REFRESHING && state != LOADING) {
                        if (state == PULL_To_REFRESH) {
                            state = DONE;
                            changeHeaderViewByState();
                        }
                        if (state == RELEASE_To_REFRESH) {
                            state = REFRESHING;
                            changeHeaderViewByState();
                            onLvRefresh();
                        }
                    }
                    isRecored = false;
                    isBack = false;

                    break;

                case MotionEvent.ACTION_MOVE:
                    int tempY = (int) e.getY();
                    if (!isRecored) {
                        isRecored = true;
                        startY = tempY;
                    }
                    if (state != REFRESHING && isRecored && state != LOADING) {
                        // 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动
                        // 可以松手去刷新了
                        if (state == RELEASE_To_REFRESH) {
                            scrollToPosition(0);
                            // 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
                            if (((tempY - startY) / radio < headerContentHeight)// 由松开刷新状态转变到下拉刷新状态
                                    && (tempY - startY) > 0) {
                                state = PULL_To_REFRESH;
                                changeHeaderViewByState();
                            }
                            // 一下子推到顶了
                            else if (tempY - startY <= 0) {// 由松开刷新状态转变到done状态
                                state = DONE;
                                changeHeaderViewByState();
                            }
                        }
                        // 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
                        if (state == PULL_To_REFRESH) {
                            scrollToPosition(0);
                            // 下拉到可以进入RELEASE_TO_REFRESH的状态
                            if ((tempY - startY) / radio >= headerContentHeight) {// 由done或者下拉刷新状态转变到松开刷新
                                state = RELEASE_To_REFRESH;
                                isBack = true;
                                changeHeaderViewByState();
                            }
                            // 上推到顶了
                            else if (tempY - startY <= 0) {// 由DOne或者下拉刷新状态转变到done状态
                                state = DONE;
                                changeHeaderViewByState();
                            }
                        }
                        // done状态下
                        if (state == DONE) {
                            if (tempY - startY > 0) {
                                state = PULL_To_REFRESH;
                                if (timer!=null)
                                timer.cancel();

                                runTimer();

                                changeHeaderViewByState();
                            }
                        }
                        // 更新headView的size
                        if (state == PULL_To_REFRESH) {
                            setDegress(tempY - startY);
                            headerPadding =tempY-startY;
                            headerLayout.setPadding(0,(int)( -1 * headerContentHeight + headerPadding / radio), 0, 0);


                        }
                        // 更新headView的paddingTop
                        if (state == RELEASE_To_REFRESH) {
                            setDegress(tempY-startY);
                            headerPadding =(tempY-startY);
                            headerLayout.setPadding(0, (int)(headerPadding/radio - headerContentHeight), 0, 0);
                        }

                    }

                    Log.d("refresh state",state+"");

                    break;

                default:
                    break;
            }
        }
        return super.onTouchEvent(e);
    }


    public void setmLinearLayoutManager (LinearLayoutManager linearLayoutManager) {
        mLinearLayoutManager = linearLayoutManager;
        setLayoutManager(linearLayoutManager);
    }

    private void onLvRefresh() {
        if (onRefreshListener != null) {
            onRefreshListener.onRefresh();
            mAdapter.notifyDataSetChanged();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                        state = DONE;
                        changeHeaderViewByState();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    private void changeHeaderViewByState() {


        Log.d("state state",state+"");

        switch (state) {
            case RELEASE_To_REFRESH:
                break;
            case PULL_To_REFRESH:
//                headerImage.clearAnimation();
//                headerImage.setVisibility(View.VISIBLE);
                // 是由RELEASE_To_REFRESH状态转变来的
                if (isBack) {
                    isBack = false;
//                    headerImage.clearAnimation();
                } else {
                }
                break;

            case REFRESHING:
                headerLayout.setPadding(0, 0, 0, 0);
                setDegress(headerContentHeight);
//                headerImage.clearAnimation();
//                headerImage.setVisibility(View.GONE);
                break;
            case DONE:

                myHandler.sendEmptyMessage(0);
//                headerLayout.setPadding(0, -1 * headerContentHeight, 0, 0);

//                headerImage.clearAnimation();
                isRefreshable = false;
                break;
        }
    }

    /**
     * 设置下拉刷新的图片
     * @param headerImage
     */
    public void setHeaderImage(ImageView headerImage) {
        this.headerImage = headerImage;
        headerImageHeight = this.headerImage.getHeight();
        if (headerImageHeight <= 0) {
            headerImageHeight = this.headerImage.getLayoutParams().height;
        } else {
            this.headerImage.getLayoutParams().height = headerImageHeight;
        }
    }

    /**
     * 设置实际下拉距离与偏移比例
     * @param radio
     */
    public void setRadio(int radio) {
        this.radio = radio;
    }

    /**
     * 设置下拉旋转速度的倍数
     * @param multiple
     */
    public void setDegressMultiple(float multiple) {
        this.multiple_degress = multiple;
    }

    private void setDegress(float degress) {

        this.degress = (float)Math.sqrt(degress / headerContentHeight)*multiple_degress;
        Log.d("degress",this.degress+"");
    }

    final Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (headerPadding>0) {
                        headerPadding*=0.9f;
                        headerLayout.setPadding(0, (int)(headerPadding/radio - headerContentHeight), 0, 0);
                        return;
                    }
                    timer.cancel();
                    total_degress = 0;
                    headerImage.setRotation(total_degress);
                    break;
                case 1:
                    total_degress = (total_degress+degress)%360;
                    headerImage.setRotation(total_degress);
                    break;
                case 2:
//                    if (headerPadding/radio > headerContentHeight) {
//                        headerPadding*=0.9f;
//                        headerLayout.setPadding(0, (int)(headerPadding/radio - headerContentHeight), 0, 0);
//                    } else {
//                        headerPadding = headerContentHeight;
//                        headerLayout.setPadding(0,0,0,0);
//                    }
                    total_degress = (total_degress+degress)%360;
                    headerImage.setRotation(total_degress);
                default:
            }
        }
    };

    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
//            Log.d("stateNormal",state+"");
            if (state==DONE) {
                myHandler.sendEmptyMessage(0);
            } else if (state == REFRESHING) {
                myHandler.sendEmptyMessage(2);
            } else {
                myHandler.sendEmptyMessage(1);
            }
        }
    }

    private void runTimer() {
        timer = new Timer();
        timerTask = new MyTimerTask();
        timer.schedule(timerTask,0,10);
    }


}
