package com.code.codefram.model;

public class WeatherJson {


    //weatherinfo需要对应json数据的名称，我之前随便写了个，被坑很久
    private Weatherinfo weatherinfo;

    public Weatherinfo getWeatherinfo() {
        return weatherinfo;
    }

    public void setWeatherinfo(Weatherinfo weatherinfo) {
        this.weatherinfo = weatherinfo;
    }

    //city、cityid必须对应json数据的名称，不然解析不了
    public class Weatherinfo {

        private String city;

        private String cityid;

        private String temp;

        private String WD;

        private String WS;

        private String SD;

        private String WSE;

        private String time;

        private String isRadar;

        private String Radar;

        private String njd;

        private String qy;
        //这里省略get和set方法

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCityid() {
            return cityid;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getWD() {
            return WD;
        }

        public void setWD(String WD) {
            this.WD = WD;
        }

        public String getWS() {
            return WS;
        }

        public void setWS(String WS) {
            this.WS = WS;
        }

        public String getSD() {
            return SD;
        }

        public void setSD(String SD) {
            this.SD = SD;
        }

        public String getWSE() {
            return WSE;
        }

        public void setWSE(String WSE) {
            this.WSE = WSE;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getIsRadar() {
            return isRadar;
        }

        public void setIsRadar(String isRadar) {
            this.isRadar = isRadar;
        }

        public String getRadar() {
            return Radar;
        }

        public void setRadar(String radar) {
            Radar = radar;
        }

        public String getNjd() {
            return njd;
        }

        public void setNjd(String njd) {
            this.njd = njd;
        }

        public String getQy() {
            return qy;
        }

        public void setQy(String qy) {
            this.qy = qy;
        }
    }
}
