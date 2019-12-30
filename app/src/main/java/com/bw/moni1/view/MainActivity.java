package com.bw.moni1.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bw.moni1.R;
import com.bw.moni1.adapter.ShopAdapter;
import com.bw.moni1.base.BaseActivity;
import com.bw.moni1.contracl.IContracl;
import com.bw.moni1.model.entity.ShopEntity;
import com.bw.moni1.presenter.Presenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity<Presenter> implements IContracl.IView {
    @BindView(R.id.rv)
    RecyclerView rv;


    @Override
    protected void initData() {
        //http://172.17.8.100/small/commodity/v1/findCommodityByKeyword?keyword=%E6%89%8B%E6%9C%BA&page=1&count=5
        presenter.getshop("http://172.17.8.100/small/commodity/v1/findCommodityByKeyword?keyword=手机&page=1&count=5");

    }

    @Override
    protected void initView() {

        rv.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected Presenter initPresenter() {
        return new Presenter();
    }

    @Override
    public void success(ShopEntity shopEntity) {
        if (shopEntity != null) {

            List<ShopEntity.ResultBean> result = shopEntity.getResult();
            ShopAdapter shopAdapter = new ShopAdapter(MainActivity.this, result);
            rv.setAdapter(shopAdapter);

            shopAdapter.setAdapterClick(new ShopAdapter.AdapterClick() {
                @Override
                public void click(String s) {
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(intent);
                }
            });
            Toast.makeText(this, "" + shopEntity.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void fshibai(Throwable throwable) {

    }


}
