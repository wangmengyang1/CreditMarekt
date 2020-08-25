package com.cjqb.caijiqianbao.bean.cityBean;

import java.util.List;

public  class Shi {
        private String name;
        private List<String> area;
        public void setName(String name) {
                this.name = name;
        }
        public String getName() {
                return name;
        }

        public void setArea(List<String> area) {
                this.area = area;
        }
        public List<String> getArea() {
                return area;
        }
}
