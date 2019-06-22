package likeit.com.jingdong.network.model;

import java.io.Serializable;
import java.util.List;

public class ClassifyList01Model implements Serializable {

    /**
     * catlevel : 3
     * filtrate : [{"id":"1245","titel":"厨房卫浴","thumb":"","rank":"1","type":"6","twotier":[{"id":"1246","name":"淋浴花洒","thumb":"","rank":"2","threetier":[{"id":"1247","name":"升降花洒","thumb":"","rank":"3"},{"id":"1248","name":"手持花洒","thumb":"","rank":"3"}]},{"id":"1249","name":"马桶","thumb":"","rank":"2","threetier":[{"id":"1250","name":"普通马桶","thumb":"","rank":"3"},{"id":"1251","name":"智能马桶","thumb":"","rank":"3"},{"id":"1271","name":"智能马桶盖","thumb":"","rank":"3"}]},{"id":"1252","name":"水槽","thumb":"","rank":"2","threetier":[{"id":"1253","name":"单槽","thumb":"","rank":"3"},{"id":"1254","name":"双槽","thumb":"","rank":"3"},{"id":"1255","name":"手工槽","thumb":"","rank":"3"},{"id":"1256","name":"石英石水槽","thumb":"","rank":"3"}]},{"id":"1257","name":"浴室柜","thumb":"","rank":"2","threetier":[{"id":"1258","name":"洗衣柜","thumb":"","rank":"3"},{"id":"1259","name":"浴室柜","thumb":"","rank":"3"}]},{"id":"1260","name":"五金挂件","thumb":"","rank":"2","threetier":[{"id":"1261","name":"卫浴五金挂件","thumb":"","rank":"3"},{"id":"1262","name":"厨房五金挂件","thumb":"","rank":"3"}]},{"id":"1263","name":"龙头","thumb":"","rank":"2","threetier":[{"id":"1264","name":"厨房龙头","thumb":"","rank":"3"},{"id":"1265","name":"面盆龙头","thumb":"","rank":"3"}]},{"id":"1266","name":"其它","thumb":"","rank":"2","threetier":[{"id":"1267","name":"豪华套装","thumb":"","rank":"3"},{"id":"1268","name":"配件","thumb":"","rank":"3"},{"id":"1269","name":"晾衣架","thumb":"","rank":"3"},{"id":"1270","name":"陶瓷面盆","thumb":"","rank":"3"}]}]},{"id":"1272","titel":"门锁","thumb":"","rank":"1","type":"7","twotier":[{"id":"1273","name":"锁","thumb":"","rank":"2","threetier":[{"id":"1274","name":"直板锁","thumb":"","rank":"3"},{"id":"1275","name":"推拉锁","thumb":"","rank":"3"},{"id":"1276","name":"配件专区","thumb":"","rank":"3"}]}]}]
     */

    private String catlevel;
    private List<FiltrateBean> filtrate;

    public String getCatlevel() {
        return catlevel;
    }

    public void setCatlevel(String catlevel) {
        this.catlevel = catlevel;
    }

    public List<FiltrateBean> getFiltrate() {
        return filtrate;
    }

    public void setFiltrate(List<FiltrateBean> filtrate) {
        this.filtrate = filtrate;
    }

    public static class FiltrateBean implements Serializable {
        /**
         * id : 1245
         * titel : 厨房卫浴
         * thumb :
         * rank : 1
         * type : 6
         * twotier : [{"id":"1246","name":"淋浴花洒","thumb":"","rank":"2","threetier":[{"id":"1247","name":"升降花洒","thumb":"","rank":"3"},{"id":"1248","name":"手持花洒","thumb":"","rank":"3"}]},{"id":"1249","name":"马桶","thumb":"","rank":"2","threetier":[{"id":"1250","name":"普通马桶","thumb":"","rank":"3"},{"id":"1251","name":"智能马桶","thumb":"","rank":"3"},{"id":"1271","name":"智能马桶盖","thumb":"","rank":"3"}]},{"id":"1252","name":"水槽","thumb":"","rank":"2","threetier":[{"id":"1253","name":"单槽","thumb":"","rank":"3"},{"id":"1254","name":"双槽","thumb":"","rank":"3"},{"id":"1255","name":"手工槽","thumb":"","rank":"3"},{"id":"1256","name":"石英石水槽","thumb":"","rank":"3"}]},{"id":"1257","name":"浴室柜","thumb":"","rank":"2","threetier":[{"id":"1258","name":"洗衣柜","thumb":"","rank":"3"},{"id":"1259","name":"浴室柜","thumb":"","rank":"3"}]},{"id":"1260","name":"五金挂件","thumb":"","rank":"2","threetier":[{"id":"1261","name":"卫浴五金挂件","thumb":"","rank":"3"},{"id":"1262","name":"厨房五金挂件","thumb":"","rank":"3"}]},{"id":"1263","name":"龙头","thumb":"","rank":"2","threetier":[{"id":"1264","name":"厨房龙头","thumb":"","rank":"3"},{"id":"1265","name":"面盆龙头","thumb":"","rank":"3"}]},{"id":"1266","name":"其它","thumb":"","rank":"2","threetier":[{"id":"1267","name":"豪华套装","thumb":"","rank":"3"},{"id":"1268","name":"配件","thumb":"","rank":"3"},{"id":"1269","name":"晾衣架","thumb":"","rank":"3"},{"id":"1270","name":"陶瓷面盆","thumb":"","rank":"3"}]}]
         */

        private String id;
        private String titel;
        private String thumb;
        private String rank;
        private String type;
        private List<TwotierBean> twotier;




        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitel() {
            return titel;
        }

        public void setTitel(String titel) {
            this.titel = titel;
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
             * id : 1246
             * name : 淋浴花洒
             * thumb :
             * rank : 2
             * threetier : [{"id":"1247","name":"升降花洒","thumb":"","rank":"3"},{"id":"1248","name":"手持花洒","thumb":"","rank":"3"}]
             */

            private String id;
            private String name;
            private String thumb;
            private String rank;
            private List<ThreetierBean> threetier;
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

            public List<ThreetierBean> getThreetier() {
                return threetier;
            }

            public void setThreetier(List<ThreetierBean> threetier) {
                this.threetier = threetier;
            }

            public static class ThreetierBean implements Serializable {
                /**
                 * id : 1247
                 * name : 升降花洒
                 * thumb :
                 * rank : 3
                 */

                private String id;
                private String name;
                private String thumb;
                private String rank;
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

                public String getRank() {
                    return rank;
                }

                public void setRank(String rank) {
                    this.rank = rank;
                }
            }
        }
    }
}
