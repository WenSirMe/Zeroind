package org.sssta.zeroind.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.sssta.zeroind.NContent;
import org.sssta.zeroind.R;
import org.sssta.zeroind.service.BaseService;
import org.sssta.zeroind.service.response.ResponseStatus;
import org.sssta.zeroind.util.SharedPreferenceUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String FROM_INDEX = "FromIndex";
    private static final String WIND_DIRECTION = "WindDirection";

    @Bind(R.id.wind_edit_down)
    Button windEditDown;
    @Bind(R.id.wind_edit_text)
    EditText windEditText;


    // TODO: Rename and change types of parameters
    private int fromIndex;
    private int direction;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment EditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditFragment newInstance(int fromIndex,int direction) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putInt(FROM_INDEX,fromIndex);
        args.putInt(WIND_DIRECTION, direction);
        fragment.setArguments(args);
        return fragment;
    }

    public EditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        windEditText.setText(
                SharedPreferenceUtil
                        .getInstance()
                        .getPostFragmentTempEditText(fromIndex));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fromIndex = getArguments().getInt(FROM_INDEX);
            direction = getArguments().getInt(WIND_DIRECTION);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        ButterKnife.bind(this, view);
        windEditDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (fromIndex){
                    case NContent.POST_MESSAGE:
                        mListener.editActivityDown();
                        break;
                    case NContent.REQUEST_MESSAGE:
                        submitTextToServer(direction);
                        break;
                }
            }
        });
        windEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferenceUtil
                        .getInstance()
                        .setPostFragmentTempEditText(fromIndex,windEditText.getText().toString());
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
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
        ButterKnife.unbind(this);
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);

        void editActivityDown();
    }
    public void submitTextToServer(int wind_direction){
        Call<ResponseStatus> call = BaseService
                .getMessageService()
                .postMessage(SharedPreferenceUtil.getInstance().getToken()
                        , wind_direction
                        , SharedPreferenceUtil.getInstance().getPostFragmentTempEditText(fromIndex)
                        , "");

        call.enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Response<ResponseStatus> response, Retrofit retrofit) {
                ResponseStatus responseStatus = response.body();
                if (responseStatus !=null && responseStatus.getStatus()==0){
                    Toast.makeText(getActivity(), "发送成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "发送失败", Toast.LENGTH_SHORT).show();
                }
                //This will stop Refresh Animation
            }

            @Override
            public void onFailure(Throwable t) {

            }

        });
    }

}
