package com.sar.fsdemo.business.config;

/**
 * @author Mr.Wang
 * @version 1.0
 * @time 2016/8/22-11:02
 * @describe
 */
public class AppConstant {

    //----------------Share SDK--------------------------
    public final static String ShareTitle = "MDMApp";

    public final static String ShareText = "测试分享";

    public static String ShareUrl = "https://www.dj10000.com/#/index";

    public static String ShareLogo = "";

    //------------------基本配置--------------------------
    public static final String WELOCOME_APP = "isOnce";     //是否显示欢迎页

    public static final int REQUEST_SUCCESS_CODE = 0;

    public static final int REQUEST_TOKEN_ERROR = 10401;    //TOKEN失效错误码

    public static final int REQUEST_SERVICE_ERROR = 10000;    //服务器停用状态

    // APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String WX_APP_ID = "wx5efc45cc6c017880";

    public static String FE_CACHE_CONFIG = "FE_cache_config";

    public static String URL_HOST = "https://www.dj10000.com/#/index";
    public static String URL_HOST_V2 = "";

    public static int DEFAULT_TIMEOUT = 20;      //超时时间 单位S秒

    public static int PAGE_SIZE = 10;      //分页大小

    public static int ON_PAGE = -1;      //分页关闭

    public static String WIDTHPIXELS = "widthPixels";      //屏幕宽度

    public static String HEIGHTPIXELS = "heightPixels";      //屏幕宽度

    public static String CHAT_SERVER_URL = "";

    //缓存contests数据
    public static String CACHE_CONTESTS = "chche_contests";

    //缓存contests数据
    public static String GAME_AREA = "game_area";

    //缓存问答题目数据
    public static String QUESTION = "question";

    //验证码倒计时
    public static final String YZM_TIME = "yzmTime";

    // 是否开启push声音
    public final static String IS_PUSH_SOUND = "is_push_sound";

    // 是否开启push震动
    public final static String IS_PUSH_SHOCK = "is_push_shock";

    // （极光）开启竞猜列表的我的竞猜的PAGE
    public final static String KEY_SHOWPAGE = "TAG_SHOW";
    public final static int VALUE_SHOWPAGE = 1;


    //（极光）区分传入站内信详情界面的id
    public final static String Miail_ID = "id";

    //username
    public final static String USER_NAME = "user_name";

    //赛事联赛
    public final static String HERALDS_LEAGUE = "heralds_league";
    public final static String HAS_MATCH_DAYS = "has_match_days";

    //服务器时间差
    public final static String SERVICE_TIME_DT = "service_time_dt";

}
