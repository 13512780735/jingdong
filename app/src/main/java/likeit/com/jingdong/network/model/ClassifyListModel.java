package likeit.com.jingdong.network.model;

import java.io.Serializable;
import java.util.List;

public class ClassifyListModel implements Serializable{

    /**
     * catlevel : 2
     * list : [{"id":"1228","name":"线下加盟（套餐）","thumb":"","rank":"1","twotier":[{"id":"1230","name":"门店套餐","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/qMSCn5xksM5NXTxrYDAgzSXKx9Xggk.jpg"}]},{"id":"1192","name":"吸顶灯","thumb":"","rank":"1","twotier":[{"id":"1199","name":"现代简约","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/RI54U5r1f43OHhXhgHO8aaom5P11HJ.jpg"},{"id":"1200","name":"新中式","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/cSv5s5OW55vZ8Bb5cvWsg5vVejVEpo.jpg"},{"id":"1201","name":"中式实木","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/vk1j3IGDkYkUK0jTGYY00b6T23gUZU.jpg"},{"id":"1202","name":"欧美系列","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/ZU194YsSzupWIwwH77y9P6bb8I1aZx.jpg"},{"id":"1203","name":"北欧系列","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/qc42C4858spvbP48bcP5ILv5Ps5v79.jpg"},{"id":"1204","name":"轻奢时尚","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/kghSgWGHPsaWg3MsmGPaJs79fwZSGs.jpg"},{"id":"1205","name":"儿童系列","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/J8dqL1MSpyMgE1PrZZiWpsG0W4ZWmg.jpg"}]},{"id":"1191","name":"吊灯","thumb":"","rank":"1","twotier":[{"id":"1206","name":"现代简约","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/QtHTZujcfuDyR9PD12uM2hdDTYtjTm.jpg"},{"id":"1207","name":"新中式","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/WyGkO8oYOZz5p45G6w48rx4pyoSpGx.jpg"},{"id":"1208","name":"欧美吊灯","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/jFgG2fthz6O0oF4oslLPFGf5X56l22.jpg"},{"id":"1209","name":"轻奢时尚","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/NG49A6YhZVk11aNknA0xvSvdfHv3nn.jpg"},{"id":"1210","name":"北欧吊灯","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/A9rwtz6yGyR7q866Y8m6YWF5Dze77D.jpg"},{"id":"1211","name":"豪装臻品","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/twybLQu20J1bpm5mbyLhowlOofzZ4M.jpg"},{"id":"1212","name":"儿童吊灯","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/krgcMmGeRig2mgG4Mg4uR3gidtOZIR.jpg"}]},{"id":"1213","name":"灯具类型","thumb":"","rank":"1","twotier":[{"id":"1214","name":"风扇灯","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/r3LZ7Sb4NkJR3rtknTLL4w5kTJ3825.jpg"},{"id":"1215","name":"餐吊灯","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/KHZCYrvprvhyyJrVdvdfnRYWn3Vb6Y.jpg"},{"id":"1216","name":"台灯/落地灯","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/A8Hte9KKfRkk6CRz1t08l8rKk0jJhF.jpg"},{"id":"1217","name":"壁灯","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/etUl3rm6UMuQzuCR6zNNczqML15Q1L.jpg"},{"id":"1218","name":"过道灯","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/YqRaNADAS3kR4Lw4SXLqpSxdB3B4Ka.jpg"},{"id":"1219","name":"镜前灯","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/JW7iB9CMmVwk9CH7h6IpXqhnk3Nn6B.jpg"},{"id":"1220","name":"辅助光源","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/dRG0dD087gllNQd0rq1lQFL8z7l7fB.jpg"},{"id":"1221","name":"厨卫灯","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/xtTTSj9dbl4Lt3lBtJ8bW8N9SJ3J4Y.jpg"},{"id":"1222","name":"庭院灯","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/Pv3gyRh51aC3c3vv133v3S433szwZ6.jpg"}]},{"id":"1223","name":"日常家居","thumb":"","rank":"1","twotier":[{"id":"1224","name":"浴霸","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/SMiQHHQiQhRwWISkSmWz9fwmQD0KA8.jpg"},{"id":"1226","name":"开关插座","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/HQE0Z0QJB8SQt85SSqB05aqn5NHeQ0.jpg"}]}]
     */

    private String catlevel;
    private List<ListBean> list;

    public String getCatlevel() {
        return catlevel;
    }

    public void setCatlevel(String catlevel) {
        this.catlevel = catlevel;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * id : 1228
         * name : 线下加盟（套餐）
         * thumb :
         * rank : 1
         * twotier : [{"id":"1230","name":"门店套餐","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/qMSCn5xksM5NXTxrYDAgzSXKx9Xggk.jpg"}]
         */

        private String id;
        private String name;
        private String thumb;
        private String rank;
        private List<TwotierBean> twotier;
        private boolean nameIsChecked;
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public List<TwotierBean> getTwotier() {
            return twotier;
        }

        public void setTwotier(List<TwotierBean> twotier) {
            this.twotier = twotier;
        }

        public static class TwotierBean implements Serializable{
            /**
             * id : 1230
             * name : 门店套餐
             * thumb : https://wx.jddengju.com/attachment/images/1/2019/05/qMSCn5xksM5NXTxrYDAgzSXKx9Xggk.jpg
             */

            private String id;
            private String name;
            private String thumb;
            private boolean isChecked;
            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }
        }
    }
}
