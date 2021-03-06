package org.sssta.zeroind.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.sssta.zeroind.R;
import org.sssta.zeroind.adapter.RecyclerWrapAdapter;
import org.sssta.zeroind.ui.AnimationRecycler;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link PlaneSend#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaneSend extends BaseFragment implements AnimationRecycler.OnRefreshListener {

    AnimationRecycler recycler;
    private Context mContext;
    private RecyclerWrapAdapter recyclerWrapAdapter;
    private int lastVisibleItem;
    private LinearLayoutManager mLayoutManager;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PlaneSend.
     */
    // TODO: Rename and change types and number of parameters
    public static PlaneSend newInstance(Context context) {
        PlaneSend fragment = new PlaneSend();
        fragment.mContext = context;
        return fragment;
    }

    public PlaneSend() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_plane_send, container, false);
        initView(view);
        return view;
    }

    void initView(View view) {
        recycler = (AnimationRecycler) view.findViewById(R.id.test_animationRecycler);
        recyclerWrapAdapter = new RecyclerWrapAdapter(null);
        mLayoutManager = new LinearLayoutManager(mContext);
        recycler.setmLinearLayoutManager(mLayoutManager);
        recycler.setOnRefreshListener(this);
        recycler.setAdapter(recyclerWrapAdapter);
        addList();
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    @Override
    public void onRefresh() {
        Toast.makeText(mContext, "OK", Toast.LENGTH_SHORT).show();
        addList();
    }

    private void addList() {
        List<Integer> list = getList();
        recyclerWrapAdapter.getLists().addAll(list);
        recyclerWrapAdapter.notifyDataSetChanged();
    }
    private List<Integer> getList() {
        List<Integer> list = new ArrayList<Integer>();
        int size = recyclerWrapAdapter.getLists().size();
        int lastPosition = size > 0 ? recyclerWrapAdapter.getLists().get(size - 1) : 0;
        for (int i = 1; i < 20; i++) {
            list.add(lastPosition + i);
        }
        return list;
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
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }

}
