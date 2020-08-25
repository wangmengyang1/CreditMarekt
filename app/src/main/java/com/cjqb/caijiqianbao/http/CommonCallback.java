package com.cjqb.caijiqianbao.http;

import com.cjqb.caijiqianbao.utils.SpUtil;
import com.cjqb.caijiqianbao.utils.ToastUtil;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;

/**
 * Created by cxf on 2017/8/11.
 */

public abstract class CommonCallback<T> extends AbsCallback<T> {


    @Override
    public void onSuccess(Response<T> response) {
        String s = response.body().toString();
        JsonBean body = (JsonBean) response.body();
        if (body != null) {
            if (!("200").equals(body.getCode())) {
                if (("403").equals(body.getCode())) {
                    ToastUtil.show("登录失效，请重新登录");
                    SpUtil.getInstance().setBooleanValue(SpUtil.IS_LOGIN, false);
                    SpUtil.getInstance().setIntValue(SpUtil.COUNT_DIALOG, 0);
                    SpUtil.getInstance().setBooleanValue(SpUtil.SHOW_DIALOG, false);
                    SpUtil.getInstance().setStringValue(SpUtil.IS_VIP, "0");
                } else {
                    ToastUtil.show(body.getMsg());
                }
            }
        }
    }

    @Override
    public void onError(Response<T> response) {
        super.onError(response);
        ToastUtil.show("服务器返回值异常" + response.message());
    }
}
