package com.sar.fsdemo.business.mode.http;

import android.util.Log;
import android.webkit.WebSettings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sar.fsdemo.app.MDMApp;
import com.sar.fsdemo.app.base.BaseObserver;
import com.sar.fsdemo.business.config.AppConstant;
import com.sar.fsdemo.business.mode.bean.CallBackVo;
import com.sar.fsdemo.business.mode.serviceapi.DeserializerData;
import com.sar.fsdemo.business.mode.serviceapi.IServiceAPI;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Mr.Wang
 * @version 1.0
 * @time 2016/8/22-13:17
 * @describe
 */
public class HttpDao {

    private static final String TAG = HttpDao.class.getSimpleName();
    private static HttpDao mHttpDao = null;

    private HttpDao() {
    };

    public static HttpDao getInstance() {
        if (mHttpDao == null) {
            synchronized (HttpDao.class){
                if (mHttpDao == null) {
                    mHttpDao = new HttpDao();
                }
            }
        }
        return mHttpDao;
    }

    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message ->
            Log.i("RetrofitLog", "retrofitBack = " + message)
       );

    /**
     * 获取 IServiceAPI实列
     *
     * @return IServiceAPI
     */
    public IServiceAPI getIServiceAPI() {

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //retrofit底层用的okHttp,所以设置超时还需要okHttp
        //然后设置5秒超时
        //其中DEFAULT_TIMEOUT是我这边定义的一个常量
        //TimeUnit为java.util.concurrent包下的时间单位
        //TimeUnit.SECONDS这里为秒的单位
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(AppConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(AppConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(AppConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    Request.Builder builder1 = request.newBuilder();
                    Request build = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        build = builder1.addHeader("Accept", "application/json")
                                .addHeader("User-Agent", WebSettings.getDefaultUserAgent(MDMApp.getInstance().getApplicationContext()))
                                .build();
                    }
                    return chain.proceed(build);
                })
                .build();

        // 使用自定义转换器
        Gson mGson = new GsonBuilder()
                .registerTypeAdapter(String.class, new DeserializerData())
                .create();
        Retrofit mRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(AppConstant.URL_HOST).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .build();
        return mRetrofit.create(IServiceAPI.class);
    }

    /**
     * 创建 Subscriber
     * @param mICallBackListener
     * @return Subscriber
     */
    public BaseObserver createOberver(final ICallBackListener mICallBackListener) {
        return new BaseObserver() {
            @Override
            protected void onSuccess(CallBackVo t) throws Exception {
                if (t.getCode() == AppConstant.REQUEST_SUCCESS_CODE) {
                    mICallBackListener.onSuccess(t);
                } else {
                    mICallBackListener.onFaild(t);
                }
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                Log.e(TAG, "[onError]" + e.getMessage());
                CallBackVo mCallBackVo = new CallBackVo();
                mCallBackVo.setCode(1);
                mCallBackVo.setMsg("请求失败");
                mCallBackVo.setData(null);
                mICallBackListener.onFaild(mCallBackVo);
                return;
            }
        };
    }
}
