package org.sssta.zeroind.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.sssta.zeroind.R;

import org.sssta.zeroind.ui.AnimationRecycler;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnUserReplyListener} interface
 * to handle interaction events.
 * Use the {@link PlaneReceive#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaneReceive extends BaseFragment  {

    private final String TAG = "receiveTag";
    private Context mContext;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters

    private OnUserReplyListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PlaneReceive.
     */
    // TODO: Rename and change types and number of parameters
    public static PlaneReceive newInstance(Context context) {
        PlaneReceive fragment = new PlaneReceive();
        Bundle args = new Bundle();
        fragment.mContext = context;
        return fragment;
    }

    public PlaneReceive() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    private void initView(View view) {


//        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
//        recyclerViewReceive = (RecyclerView) view.findViewById(R.id.recyclerView_receive);
//        recyclerViewReceive.setLayoutManager(mLayoutManager);
//        recyclerViewReceive.setAdapter(adapter);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                handler.sendEmptyMessageDelayed(0, 3000);
//                Log.d(TAG, "ignore manully update!");
//            }
//        });
//
//        recyclerViewReceive.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView,
//                                             int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
//                    handler.sendEmptyMessageDelayed(1, 3000);
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
//            }
//        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plane_receive, container, false);
        initView(view);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onUserReplay(uri);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("state", "on activity created");
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnUserReplyListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnUserReplyListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnUserReplyListener {
        // TODO: Update argument type and name
        public void onUserReplay(Uri uri);
    }

//    private Handler handler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 0:
//                    Toast.makeText(mContext, "DOWN", Toast.LENGTH_SHORT).show();
//                    swipeRefreshLayout.setRefreshing(false);
//
//                    adapter.getLists().clear();
//                    addList();
//                    break;
//                case 1:
//                    Toast.makeText(mContext, "UP", Toast.LENGTH_SHORT).show();
//                    addList();
//                    break;
//                default:
//                    break;
//            }
//
//        }
//
//    };



}
