package likeit.com.jingdong.network.model;

import java.io.Serializable;
import java.util.List;

public class ClassifyListModel implements Serializable {


    /**
     * catlevel : 2
     * list : [{"id":"1191","name":"吊灯","thumb":"","rank":"1","type":"0","twotier":[{"id":"1206","name":"现代简约","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/QtHTZujcfuDyR9PD12uM2hdDTYtjTm.jpg","type":false},{"id":"1207","name":"新中式","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/WyGkO8oYOZz5p45G6w48rx4pyoSpGx.jpg","type":false},{"id":"1208","name":"欧美吊灯","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/jFgG2fthz6O0oF4oslLPFGf5X56l22.jpg","type":false}]},{"id":"1192","name":"吸顶灯","thumb":"","rank":"1","type":"1","twotier":[{"id":"1199","name":"现代简约","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/RI54U5r1f43OHhXhgHO8aaom5P11HJ.jpg","type":false},{"id":"1200","name":"新中式","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/cSv5s5OW55vZ8Bb5cvWsg5vVejVEpo.jpg","type":false},{"id":"1201","name":"中式实木","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/vk1j3IGDkYkUK0jTGYY00b6T23gUZU.jpg","type":false}]},{"id":"1228","name":"上样套餐","thumb":"","rank":"1","type":"2","twotier":[{"id":"1230","name":"活动套餐","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/qMSCn5xksM5NXTxrYDAgzSXKx9Xggk.jpg","type":false}]},{"id":"1213","name":"灯具类型","thumb":"","rank":"1","type":"3","twotier":[{"id":"1214","name":"风扇灯","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/r3LZ7Sb4NkJR3rtknTLL4w5kTJ3825.jpg","type":false},{"id":"1215","name":"餐吊灯","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/KHZCYrvprvhyyJrVdvdfnRYWn3Vb6Y.jpg","type":false},{"id":"1216","name":"台灯/落地灯","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/A8Hte9KKfRkk6CRz1t08l8rKk0jJhF.jpg","type":false}]},{"id":"1223","name":"日常家居","thumb":"","rank":"1","type":"4","twotier":[{"id":"1224","name":"浴霸","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/SMiQHHQiQhRwWISkSmWz9fwmQD0KA8.jpg","type":false},{"id":"1226","name":"开关插座","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/HQE0Z0QJB8SQt85SSqB05aqn5NHeQ0.jpg","type":false}]}]
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

    public static class ListBean implements Serializable {
        /**
         * id : 1191
         * name : 吊灯
         * thumb :
         * rank : 1
         * type : 0
         * twotier : [{"id":"1206","name":"现代简约","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/QtHTZujcfuDyR9PD12uM2hdDTYtjTm.jpg","type":false},{"id":"1207","name":"新中式","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/WyGkO8oYOZz5p45G6w48rx4pyoSpGx.jpg","type":false},{"id":"1208","name":"欧美吊灯","thumb":"https://wx.jddengju.com/attachment/images/1/2019/05/jFgG2fthz6O0oF4oslLPFGf5X56l22.jpg","type":false}]
         */

        private String id;
        private String name;
        private String thumb;
        private String rank;
        private String type;
        private List<TwotierBean> twotier;
        private boolean nameIsChecked;

        public boolean isNameIsChecked() {
            return nameIsChecked;
        }

        public void setNameIsChecked(boolean nameIsChecked) {
            this.nameIsChecked = nameIsChecked;
        }

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<TwotierBean> getTwotier() {
            return twotier;
        }

        public void setTwotier(List<TwotierBean> twotier) {
            this.twotier = twotier;
        }

        public static class TwotierBean implements Serializable {
            /**
             * id : 1206
             * name : 现代简约
             * thumb : https://wx.jddengju.com/attachment/images/1/2019/05/QtHTZujcfuDyR9PD12uM2hdDTYtjTm.jpg
             * type : false
             */

            private String id;
            private String name;
            private String thumb;
            private boolean type;
            private boolean isChecked;

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }

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

            public boolean isType() {
                return type;
            }

            public void setType(boolean type) {
                this.type = type;
            }
        }
    }
}
