package com.bw.moni1.presenter;

import com.bw.moni1.base.BasePresenter;
import com.bw.moni1.contracl.IContracl;
import com.bw.moni1.model.Model;
import com.bw.moni1.model.entity.ShopEntity;

/**
 * 时间：2019/12/30
 * 作者：徐黎明
 * 类的作用：
 */
public class Presenter extends BasePresenter<Model, IContracl.IView> implements IContracl.IPresenter {
    @Override
    protected Model initModel() {
        return new Model();
    }

    @Override
    public void getshop(String url) {
            model.getshop(url, new IContracl.IModel.ModelCallBack() {
                @Override
                public void success(ShopEntity shopEntity) {

                        getView().success(shopEntity);

                }

                @Override
                public void fshibai(Throwable throwable) {

                        getView().fshibai(throwable);

                }
            });
    }
}
