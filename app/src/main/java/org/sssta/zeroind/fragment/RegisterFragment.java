package org.sssta.zeroind.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.sssta.zeroind.R;
import org.sssta.zeroind.adapter.MySpinnerAdaper;
import org.sssta.zeroind.service.BaseService;
import org.sssta.zeroind.service.response.ResponseStatus;
import org.sssta.zeroind.util.TextUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterListener} interface
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.register_edit_username)
    EditText editUsername;
    @Bind(R.id.register_spinner_sex)
    Spinner spinnerSex;
    @Bind(R.id.register_edit_password)
    EditText editPassword;

    private String[] strings = new String[] {
            "男生","女生","新人类"
    };
    private String sex = strings[0];
    private int[] imageIds = new int[] {
            R.drawable.men,R.drawable.women,R.drawable.flip
    };
    // TODO: Rename and change types of parameters

    private RegisterListener mListener;

    private Context mContext;
    private String userName;
    private boolean beenChecked = false;

    private boolean isRequesting = false;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(Context context) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.mContext = context;
        fragment.setArguments(args);
        return fragment;
    }

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        editUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (editUsername == null)
                    return;
                if (!hasFocus && !editUsername.hasFocus() && !TextUtil.isEmpty(editUsername)) {
                    checkUserName();
                }
            }
        });
        MySpinnerAdaper spinnerAdaper = new MySpinnerAdaper(strings,imageIds,mContext);
        spinnerSex.setAdapter(spinnerAdaper);
        spinnerSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sex = strings[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    // TODO: Rename method, update argument and hook method into UI event

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (RegisterListener) activity;
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public void checkUserName() {
        isRequesting = true;
        userName = editUsername.getText().toString();
        Call<ResponseStatus> call = BaseService.getUserService().checkName(userName);
        call.enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Response<ResponseStatus> response, Retrofit retrofit) {
                if (response.body() == null || response.body().getStatus() != 0) {
                    userName = null;
                    editUsername.setError("用户名已存在");
                    isRequesting = false;
                    return;
                }
                isRequesting = false;
                if (beenChecked) {
                    register();
                    beenChecked = false;
                }
            }

            @Override
            public void onFailure(Throwable t) {
                userName = null;
                Toast.makeText(mContext, "未知错误", Toast.LENGTH_SHORT).show();
                isRequesting = false;
            }
        });
    }

//    public String getSex() {
//
//    }

    public String getUserName() {
        if (isRequesting && !beenChecked) {
            beenChecked = true;
        }
        if (TextUtil.isEmpty(editUsername)) {
            editUsername.setError("请输入用户名");
            return null;
        }
        return userName;
    }

    public String getPassword() {
        if (editPassword.length() < 6 || editPassword.length() >20) {
            editPassword.setError("密码须为6至20位");
            return null;
        }
        return editPassword.getText().toString();
    }

    private void register() {
        if (getPassword()!=null) {
            if (mListener != null) {
                mListener.register(userName,getPassword(),sex);
            }
        }
    }

    public interface RegisterListener{
        public void register(String userName, String password, String sex);
    }

    public String getSex() {
        return sex;
    }

}
