package com.example.yaoyifei.yaoyfapplication.View.Fragment;

import android.content.ComponentName;
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
import android.widget.Toast;

import com.example.yaoyifei.yaoyfapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragmentTeacher.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragmentTeacher#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragmentTeacher extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FloatingActionButton mFloatingActionButton ;
    private OnFragmentInteractionListener mListener;
    private TextView textView;

    public BlankFragmentTeacher() {
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
    public static BlankFragmentTeacher newInstance(String param1, String param2) {
        BlankFragmentTeacher fragment = new BlankFragmentTeacher();
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
        String html = "<h3>基本使用操作说明（教师）</h3>\n" +
                "<ul>\n" +
                "<li>目前暂未开放教师注册功能，请使用账号“<strong>teacher</strong>” 密码“<strong>123456</strong>”进行登录。</li>\n" +
                "<li>登录时选择正确的<strong>教师身份</strong>并确保网络畅通，否则很可能登录失败。</li>\n" +
                "<li>登录成功会进入主页，在此界面点击右下角的悬浮按钮即可进行<strong>投屏</strong>，在安装了流媒体播放器的任意客户端都可以显示屏幕内容。</li>\n" +
                "<li>主页右滑动进入<strong>出题</strong>界面，输入题目信息后点击<strong>添加题目</strong>即可添加题目（题目类型分为客观题和主观题两种，客观题包括单选多选和判断，主观题包括填空和简答），点击操作题库即可对已经存在的题目进行<strong>编辑和删除</strong>操作。最后点击<strong>生成测试</strong>即可显示出的<strong>题目分值和题目用时</strong>（分值和测试时长由每道题的分值和用时累加得出，添加题目时由教师录入）并进入题目<strong>预览</strong>界面。</li>\n" +
                "<li><strong>预览</strong>界面会显示所有题目的<strong>答案和解析</strong>。</li>\n" +
                "<li>进入<strong>考生成绩</strong>界面查看已经提交成绩的学生答题结果。</li>\n" +
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
