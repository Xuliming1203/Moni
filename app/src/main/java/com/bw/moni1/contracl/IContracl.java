package com.bw.moni1.contracl;

import com.bw.moni1.base.IBaseModel;
import com.bw.moni1.base.IBaseView;
import com.bw.moni1.model.entity.ShopEntity;

/**
 * 时间：2019/12/30
 * 作者：徐黎明
 * 类的作用：
 */
public interface IContracl {
    interface IModel extends IBaseModel{
        void getshop(String url,ModelCallBack modelCallBack);
        interface ModelCallBack{
            void success(ShopEntity shopEntity);
            void fshibai(Throwable throwable);
        }
    }
    interface IView extends IBaseView{
        void success(ShopEntity shopEntity);
        void fshibai(Throwable throwable);
    }
    interface IPresenter{
        void getshop(String url);
    }
}
