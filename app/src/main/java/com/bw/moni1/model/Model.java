package com.bw.moni1.model;

import com.bw.moni1.contracl.IContracl;
import com.bw.moni1.model.entity.ShopEntity;
import com.bw.moni1.utils.OkHttputil;
import com.google.gson.Gson;

/**
 * 时间：2019/12/30
 * 作者：徐黎明
 * 类的作用：
 */
public class Model implements IContracl.IModel {
    @Override
    public void getshop(String url, final ModelCallBack modelCallBack) {
        OkHttputil.getInstance().doget(url, new OkHttputil.OkHttpCallBack() {
            @Override
            public void success(String repose) {
                ShopEntity shopEntity = new Gson().fromJson(repose, ShopEntity.class);
                if (modelCallBack != null) {
                   modelCallBack.success(shopEntity);
                }
            }

            @Override
            public void fshibai(Throwable error) {
                if (modelCallBack != null) {
                    modelCallBack.fshibai(error);
                }
            }
        });
    }
}
