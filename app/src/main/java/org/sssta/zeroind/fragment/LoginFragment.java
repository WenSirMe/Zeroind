package org.sssta.zeroind.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import org.sssta.zeroind.R;
import org.sssta.zeroind.util.TextUtil;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends android.support.v4.app.Fragment {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";
        @Bind(R.id.login_edit_userName)
        EditText editUserName;
        @Bind(R.id.login_userName_image)
        ImageView userNameImage;
        @Bind(R.id.login_edit_password)
        EditText editPassword;
        @Bind(R.id.login_password_image)
        ImageView passwordImage;

        private Context mContext;
        private boolean beenChecked = false;

        private boolean isRequesting = false;

        // TODO: Rename and change types of parameters


        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        public static LoginFragment newInstance(Context context) {
            LoginFragment fragment = new LoginFragment();
            fragment.mContext = context;
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        public LoginFragment() {
            // Required empty public constructor
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
            }
        }

        public String getUserName() {
//            editUserName.setError("用户名不能为空");
            if (editUserName==null || TextUtil.isEmpty(editUserName) ) {
                editUserName.setError("用户名不能为空");
                return null;
            }
            return editUserName.getText().toString();
        }

        public String getPassword() {
            if (editPassword.length() < 6 || editPassword.length() >20) {
                editPassword.setError("密码须为6至20位");
                return null;
            }
            return editPassword.getText().toString();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_login, container, false);
            ButterKnife.bind(this, view);
            return view;
        }
        // TODO: Rename method, update argument and hook method into UI event

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }

        @Override
        public void onDetach() {
            super.onDetach();
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


}
