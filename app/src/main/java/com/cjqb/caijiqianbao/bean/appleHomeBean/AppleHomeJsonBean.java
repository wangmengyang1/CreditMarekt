package com.cjqb.caijiqianbao.bean.appleHomeBean;

import com.cjqb.caijiqianbao.http.JsonBean;

public class AppleHomeJsonBean extends JsonBean {


        private String code;
        private String msg;
        private Data data;
        public void setCode(String code) {
            this.code = code;
        }
        public String getCode() {
            return code;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
        public String getMsg() {
            return msg;
        }

        public void setData(Data data) {
            this.data = data;
        }
        public Data getData() {
            return data;
        }
}
