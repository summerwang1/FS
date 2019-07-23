package com.sar.fsdemo.business.mode.http;


import com.sar.fsdemo.app.base.BaseObserver;
import com.sar.fsdemo.business.mode.serviceapi.IServiceAPI;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Mr.Wang
 * @version 1.0
 * @time 2016/8/22-13:12
 * @describe
 */
public class Business implements IBusiness {

    static Business mBusiness;

    public static Business getInstance() {
        if (mBusiness == null) {
            mBusiness = new Business();
        }
        return mBusiness;
    }


    IServiceAPI mIServiceAPI = HttpDao.getInstance().getIServiceAPI();


    @Override
    public void login(int uid, BaseObserver baseObserver) {

        mIServiceAPI.login(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseObserver);
    }

}
