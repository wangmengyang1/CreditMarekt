package com.cjqb.caijiqianbao.http;

public class HttpUrl {

    public static final String URL = "http://www.ylwnc.cn";
        public static final String API = URL + "/api";
    public static final String INDX = API + "/index";
    public static final String QUOTA = INDX + "/quota";
    public static final String AUTH_INFO = INDX + "/auth_info";
    public static final String BASIC_INFO = INDX + "/basic_info";
    public static final String UPLOAD = API + "/common/upload";
    public static final String UPLOAD_IMAGE = API + "/index/setBaseinfo";
    public static final String BANK_CARD = INDX + "/bank_card";
    public static final String CONFIRM_LOAN = INDX + "/confirm_loan";
    public static final String ARTICLE = INDX + "/article_detail?article_id=";
    public static final String PRODUCT = INDX + "/goods_detail?product_id=";
    public static final String PRODUCT_LIST = INDX + "/goods_list";
    public static final String ARTICLE_LIST = INDX + "/article_list";
    public static final String LOGIN = API + "/login/index";
    public static final String LOGOUT = API + "/login/logout";
    public static final String SEND = API + "/login/send_short_message";

    public static final String GETORDERBANKINFO = API + "/CaijiPay/getOrderBankInfo";
    public static final String SENDBINDCARDVERIFYCODE = API + "/CaijiPay/sendBindCardVerifyCode";
    public static final String CREATEORDER = API + "/CaijiPay/createOrder";
    public static final String BINDCARD = API + "/CaijiPay/bindCard";
    public static final String PAYMENT_CONFIRMATION = API + "/Caijipay/payment_confirmation";
//    public static final String SEND = API + "/send/sendVerifyCode";
    public static final String apple = INDX + "/users_visit";
    public static final String user = API + "/user/index";
    public static final String vip_agreement = URL + "/portal/index/vip_agreement";
    public static final String PRODUCT_ID = INDX + "/product_list?product_class_id=";
    public static final String USER_TIP = URL + "/portal/index/user_agreement";
    public static final String MAY_QUOTA = INDX + "/may_quota";
    public static final String VEST = INDX + "/vest";
    public static final String BINDCODE = API + "/sendBindCardVerifyCode";
    public static final String GETVERIFYSTATUS = INDX + "/getVerifyStatus";
    public static final String CONTACTCENTER = API + "/user/contactCenter";
    public static final String PAY_CALL = API + "/CaijiPay/payResultCallBack";

//    http://192.168.8.121:8888/api/index/vest
//    http://jqb.jingchunagky.com/portal/index/user_agreement
}

