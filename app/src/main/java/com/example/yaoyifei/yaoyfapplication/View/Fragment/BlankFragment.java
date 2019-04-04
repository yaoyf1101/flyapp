package com.example.yaoyifei.yaoyfapplication.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yaoyifei.yaoyfapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FloatingActionButton mFloatingActionButton ;
    private TextView textView;
    private OnFragmentInteractionListener mListener;

    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFloatingActionButton = view.findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                PackageManager packageManager = getActivity().getPackageManager();
                intent = packageManager.getLaunchIntentForPackage("net.yrom.screenrecorder");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
                getActivity().startActivity(intent);
            }
        });
        textView= view.findViewById(R.id.text);
        String html = "<h3>基本使用操作说明（学生）</h3>\n" +
                "<ul>\n" +
                "<li>学生初次使用需先注册一个<strong>学生身份</strong>的账号。</li>\n" +
                "<li>登录时选择正确的<strong>学生身份</strong>并确保<strong>网络畅通</strong>，否则很可能登录失败。</li>\n" +
                "<li>登录成功会进入主页，在此界面点击右下角的悬浮按钮即可进行<strong>投屏</strong>，在安装了流媒体播放器的任意客户端都可以显示屏幕内容。</li>\n" +
                "<li>主页右滑动进入<strong>考试界面</strong>，首次进入考试界面<strong>下拉刷新</strong>题库即可开始作答。点击屏幕下方的<strong>开始考试</strong>按钮才可以开始考试（此时考试开始计时），否则无法开始考试。点击<strong>下一题</strong>之前请确保当前题目已经作答。完成所有题目后请点击<strong>结束考试</strong>（如果<strong>考试时间结束</strong>也会结束考试），系统即会自动核对你的答案并跳转至<strong>我的成绩界面</strong>，你可以在此界面查看自己的得分情况。</li>\n" +
                "<li>结束考试后你可以再次进入<strong>考试界面</strong>，查看问题的<strong>答案和解析</strong>。</li>\n" +
                "<li>结束考试以后，你也可以进入<strong>考生成绩界面</strong>查看已经提交成绩的学生答题结果。</li>\n" +
                "\n" +
                "</ul>\n";
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
        textView.setText(Html.fromHtml(html));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_blank, container, false);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
