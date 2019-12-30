package com.bw.moni1.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.moni1.R;
import com.bw.moni1.base.BaseActivity;
import com.bw.moni1.base.BasePresenter;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.Unbinder;

public class SecondActivity extends BaseActivity {


    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.bt)
    Button bt;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.bt1)
    Button bt1;
    @BindView(R.id.bt2)
    Button bt2;
    private Unbinder bind;

    @Override
    protected void initData() {
        CodeUtils.init(this);
        //注册eventsbut
       EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_second;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        bind = ButterKnife.bind(this);

    }

    @OnClick(R.id.bt)
    public void onViewClicked() {
        if (TextUtils.isEmpty(et.getText().toString())) {
            Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
        }
        Bitmap bitmap = CodeUtils.createImage(et.getText().toString(), 150, 150, null);
        iv.setImageBitmap(bitmap);

    }


    @OnClick({R.id.bt1, R.id.bt2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                EventBus.getDefault().post("kson");
                break;
            case R.id.bt2:
                EventBus.getDefault().post(et);
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getString(String s){
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getet(EditText editText){
        Toast.makeText(this, ""+editText.getText().toString(), Toast.LENGTH_SHORT).show();
    }
    @OnLongClick(R.id.iv)
    public void dd(){
        if (iv!=null){
            CodeUtils.analyzeByImageView(iv, new CodeUtils.AnalyzeCallback() {
                @Override
                public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                    Toast.makeText(SecondActivity.this, result, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAnalyzeFailed() {
                    Toast.makeText(SecondActivity.this, "解析失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
