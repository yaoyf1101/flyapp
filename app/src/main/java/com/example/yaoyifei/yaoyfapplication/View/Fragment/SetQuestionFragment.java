package com.example.yaoyifei.yaoyfapplication.View.Fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.yaoyifei.yaoyfapplication.R;

public class SetQuestionFragment extends Fragment implements View.OnClickListener {

    private RadioButton danxuan;
    private RadioButton duoxuan;
    private RadioButton panduan;
    private RadioButton tiankongorjianda;
    private EditText title,a,b,c,d,answer,analysis;
    private WebView webView;
    private ScrollView scrollView;
    private Button btn_back;
    private LinearLayout function_area;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set_question, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        danxuan = (RadioButton) view.findViewById(R.id.danxuan);
        duoxuan = (RadioButton) view.findViewById(R.id.duoxuan);
        panduan = (RadioButton) view.findViewById(R.id.panduan);
        tiankongorjianda = (RadioButton) view.findViewById(R.id.tiankongorjianda);
        view.findViewById(R.id.addQuestion).setOnClickListener(this);
        view.findViewById(R.id.getQuestion).setOnClickListener(this);
        title = (EditText) getView().findViewById(R.id.title);
        a = (EditText) view.findViewById(R.id.A);
        b = (EditText) view.findViewById(R.id.B);
        c = (EditText) view.findViewById(R.id.C);
        d = (EditText) view.findViewById(R.id.D);
        answer = (EditText) view.findViewById(R.id.answer);
        analysis = (EditText) view.findViewById(R.id.analysis);
        webView = view.findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://47.102.199.28/flyapp/listQuestion");
        scrollView = view.findViewById(R.id.scrollView);
        function_area = view.findViewById(R.id.function_area);
        btn_back = view.findViewById(R.id.back);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addQuestion:
                Toast.makeText(getActivity(),"添加题目成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.getQuestion:
                scrollView.setVisibility(View.GONE);
                function_area.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                btn_back.setVisibility(View.VISIBLE);
                break;
            case R.id.back:
                scrollView.setVisibility(View.VISIBLE);
                function_area.setVisibility(View.VISIBLE);
                webView.setVisibility(View.GONE);
                btn_back.setVisibility(View.GONE);
                break;
        }
    }
}
