package likeit.com.jingdong.network.model;

import java.util.List;

public class WeatherModel {

    /**
     * airCondition : 优
     * airQuality : {"aqi":16,"city":"中山","district":"中山","fetureData":[{"aqi":27,"date":"2019-06-07","quality":"优"},{"aqi":31,"date":"2019-06-08","quality":"优"},{"aqi":31,"date":"2019-06-09","quality":"优"},{"aqi":32,"date":"2019-06-10","quality":"优"},{"aqi":35,"date":"2019-06-11","quality":"优"},{"aqi":32,"date":"2019-06-12","quality":"优"}],"hourData":[{"aqi":16,"dateTime":"2019-06-06 08:00:00"},{"aqi":14,"dateTime":"2019-06-06 07:00:00"},{"aqi":13,"dateTime":"2019-06-06 05:00:00"},{"aqi":15,"dateTime":"2019-06-06 03:00:00"},{"aqi":14,"dateTime":"2019-06-06 02:00:00"},{"aqi":14,"dateTime":"2019-06-06 01:00:00"},{"aqi":14,"dateTime":"2019-06-06 00:00:00"},{"aqi":15,"dateTime":"2019-06-05 23:00:00"},{"aqi":16,"dateTime":"2019-06-05 22:00:00"},{"aqi":16,"dateTime":"2019-06-05 21:00:00"},{"aqi":16,"dateTime":"2019-06-05 19:00:00"},{"aqi":16,"dateTime":"2019-06-05 18:00:00"},{"aqi":12,"dateTime":"2019-06-05 17:00:00"},{"aqi":13,"dateTime":"2019-06-05 16:00:00"},{"aqi":17,"dateTime":"2019-06-05 15:00:00"},{"aqi":19,"dateTime":"2019-06-05 14:00:00"},{"aqi":16,"dateTime":"2019-06-05 13:00:00"},{"aqi":21,"dateTime":"2019-06-05 12:00:00"},{"aqi":18,"dateTime":"2019-06-05 11:00:00"},{"aqi":20,"dateTime":"2019-06-05 10:00:00"},{"aqi":22,"dateTime":"2019-06-05 09:00:00"}],"no2":12,"pm10":16,"pm25":8,"province":"广东","quality":"优","so2":2,"updateTime":"2019-06-06 09:00:00"}
     * city : 中山
     * coldIndex : 易发期
     * date : 2019-06-06
     * distrct : 中山
     * dressingIndex : 薄短袖类
     * exerciseIndex : 不适宜
     * future : [{"date":"2019-06-06","dayTime":"雷雨","night":"多云","temperature":"32°C / 27°C","week":"今天","wind":"南风 3级"},{"date":"2019-06-07","dayTime":"零散雷雨","night":"多云","temperature":"33°C / 26°C","week":"星期五","wind":"西南偏南风 3级"},{"date":"2019-06-08","dayTime":"局部雷雨","night":"多云","temperature":"33°C / 27°C","week":"星期六","wind":"西南偏南风 3级"},{"date":"2019-06-09","dayTime":"多云","night":"零散雷雨","temperature":"33°C / 28°C","week":"星期日","wind":"西南偏南风 4级"},{"date":"2019-06-10","dayTime":"零散雷雨","night":"雷雨","temperature":"32°C / 27°C","week":"星期一","wind":"西南偏南风 3级"},{"date":"2019-06-11","dayTime":"雷雨","night":"雷雨","temperature":"31°C / 26°C","week":"星期二","wind":"西南偏南风 3级"},{"date":"2019-06-12","dayTime":"雷雨","night":"雷雨","temperature":"31°C / 26°C","week":"星期三","wind":"南风 2级"},{"date":"2019-06-13","dayTime":"雷雨","night":"雷雨","temperature":"30°C / 26°C","week":"星期四","wind":"东南偏南风 2级"},{"date":"2019-06-14","dayTime":"雷雨","night":"零散雷雨","temperature":"30°C / 26°C","week":"星期五","wind":"东南偏南风 3级"}]
     * humidity : 湿度：79%
     * pollutionIndex : 16
     * province : 广东
     * sunrise : 06:05
     * sunset : 18:47
     * temperature : 29℃
     * time : 09:03
     * updateTime : 20190606091721
     * washIndex :
     * weather : 阴
     * week : 周四
     * wind : 南风2级
     */

    private String airCondition;
    private AirQualityBean airQuality;
    private String city;
    private String coldIndex;
    private String date;
    private String distrct;
    private String dressingIndex;
    private String exerciseIndex;
    private String humidity;
    private String pollutionIndex;
    private String province;
    private String sunrise;
    private String sunset;
    private String temperature;
    private String time;
    private String updateTime;
    private String washIndex;
    private String weather;
    private String week;
    private String wind;
    private List<FutureBean> future;

    public String getAirCondition() {
        return airCondition;
    }

    public void setAirCondition(String airCondition) {
        this.airCondition = airCondition;
    }

    public AirQualityBean getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(AirQualityBean airQuality) {
        this.airQuality = airQuality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getColdIndex() {
        return coldIndex;
    }

    public void setColdIndex(String coldIndex) {
        this.coldIndex = coldIndex;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDistrct() {
        return distrct;
    }

    public void setDistrct(String distrct) {
        this.distrct = distrct;
    }

    public String getDressingIndex() {
        return dressingIndex;
    }

    public void setDressingIndex(String dressingIndex) {
        this.dressingIndex = dressingIndex;
    }

    public String getExerciseIndex() {
        return exerciseIndex;
    }

    public void setExerciseIndex(String exerciseIndex) {
        this.exerciseIndex = exerciseIndex;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPollutionIndex() {
        return pollutionIndex;
    }

    public void setPollutionIndex(String pollutionIndex) {
        this.pollutionIndex = pollutionIndex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getWashIndex() {
        return washIndex;
    }

    public void setWashIndex(String washIndex) {
        this.washIndex = washIndex;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public List<FutureBean> getFuture() {
        return future;
    }

    public void setFuture(List<FutureBean> future) {
        this.future = future;
    }

    public static class AirQualityBean {
        /**
         * aqi : 16
         * city : 中山
         * district : 中山
         * fetureData : [{"aqi":27,"date":"2019-06-07","quality":"优"},{"aqi":31,"date":"2019-06-08","quality":"优"},{"aqi":31,"date":"2019-06-09","quality":"优"},{"aqi":32,"date":"2019-06-10","quality":"优"},{"aqi":35,"date":"2019-06-11","quality":"优"},{"aqi":32,"date":"2019-06-12","quality":"优"}]
         * hourData : [{"aqi":16,"dateTime":"2019-06-06 08:00:00"},{"aqi":14,"dateTime":"2019-06-06 07:00:00"},{"aqi":13,"dateTime":"2019-06-06 05:00:00"},{"aqi":15,"dateTime":"2019-06-06 03:00:00"},{"aqi":14,"dateTime":"2019-06-06 02:00:00"},{"aqi":14,"dateTime":"2019-06-06 01:00:00"},{"aqi":14,"dateTime":"2019-06-06 00:00:00"},{"aqi":15,"dateTime":"2019-06-05 23:00:00"},{"aqi":16,"dateTime":"2019-06-05 22:00:00"},{"aqi":16,"dateTime":"2019-06-05 21:00:00"},{"aqi":16,"dateTime":"2019-06-05 19:00:00"},{"aqi":16,"dateTime":"2019-06-05 18:00:00"},{"aqi":12,"dateTime":"2019-06-05 17:00:00"},{"aqi":13,"dateTime":"2019-06-05 16:00:00"},{"aqi":17,"dateTime":"2019-06-05 15:00:00"},{"aqi":19,"dateTime":"2019-06-05 14:00:00"},{"aqi":16,"dateTime":"2019-06-05 13:00:00"},{"aqi":21,"dateTime":"2019-06-05 12:00:00"},{"aqi":18,"dateTime":"2019-06-05 11:00:00"},{"aqi":20,"dateTime":"2019-06-05 10:00:00"},{"aqi":22,"dateTime":"2019-06-05 09:00:00"}]
         * no2 : 12
         * pm10 : 16
         * pm25 : 8
         * province : 广东
         * quality : 优
         * so2 : 2
         * updateTime : 2019-06-06 09:00:00
         */

        private int aqi;
        private String city;
        private String district;
        private int no2;
        private int pm10;
        private int pm25;
        private String province;
        private String quality;
        private int so2;
        private String updateTime;
        private List<FetureDataBean> fetureData;
        private List<HourDataBean> hourData;

        public int getAqi() {
            return aqi;
        }

        public void setAqi(int aqi) {
            this.aqi = aqi;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public int getNo2() {
            return no2;
        }

        public void setNo2(int no2) {
            this.no2 = no2;
        }

        public int getPm10() {
            return pm10;
        }

        public void setPm10(int pm10) {
            this.pm10 = pm10;
        }

        public int getPm25() {
            return pm25;
        }

        public void setPm25(int pm25) {
            this.pm25 = pm25;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public int getSo2() {
            return so2;
        }

        public void setSo2(int so2) {
            this.so2 = so2;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public List<FetureDataBean> getFetureData() {
            return fetureData;
        }

        public void setFetureData(List<FetureDataBean> fetureData) {
            this.fetureData = fetureData;
        }

        public List<HourDataBean> getHourData() {
            return hourData;
        }

        public void setHourData(List<HourDataBean> hourData) {
            this.hourData = hourData;
        }

        public static class FetureDataBean {
            /**
             * aqi : 27
             * date : 2019-06-07
             * quality : 优
             */

            private int aqi;
            private String date;
            private String quality;

            public int getAqi() {
                return aqi;
            }

            public void setAqi(int aqi) {
                this.aqi = aqi;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getQuality() {
                return quality;
            }

            public void setQuality(String quality) {
                this.quality = quality;
            }
        }

        public static class HourDataBean {
            /**
             * aqi : 16
             * dateTime : 2019-06-06 08:00:00
             */

            private int aqi;
            private String dateTime;

            public int getAqi() {
                return aqi;
            }

            public void setAqi(int aqi) {
                this.aqi = aqi;
            }

            public String getDateTime() {
                return dateTime;
            }

            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
            }
        }
    }

    public static class FutureBean {
        /**
         * date : 2019-06-06
         * dayTime : 雷雨
         * night : 多云
         * temperature : 32°C / 27°C
         * week : 今天
         * wind : 南风 3级
         */

        private String date;
        private String dayTime;
        private String night;
        private String temperature;
        private String week;
        private String wind;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDayTime() {
            return dayTime;
        }

        public void setDayTime(String dayTime) {
            this.dayTime = dayTime;
        }

        public String getNight() {
            return night;
        }

        public void setNight(String night) {
            this.night = night;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getWind() {
            return wind;
        }

        public void setWind(String wind) {
            this.wind = wind;
        }
    }
}
