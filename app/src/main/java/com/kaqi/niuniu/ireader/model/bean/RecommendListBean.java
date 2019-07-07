package com.kaqi.niuniu.ireader.model.bean;

import java.util.List;

public class RecommendListBean {

    private String status;
    private DataBean data;
    private String error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public static class DataBean {
        private List<ResultBean> result;

        public List<ResultBean> getResult() {
            return result;
        }

        public void setResult(List<ResultBean> result) {
            this.result = result;
        }

        public static class ResultBean {

            private HeadBean head;
            private List<BodyBean> body;

            public HeadBean getHead() {
                return head;
            }

            public void setHead(HeadBean head) {
                this.head = head;
            }

            public List<BodyBean> getBody() {
                return body;
            }

            public void setBody(List<BodyBean> body) {
                this.body = body;
            }

            public static class HeadBean {
                /**
                 * title : 最新热门影片推荐2019大片精选
                 * type : recommend
                 */

                private String title;
                private String type;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }

            public static class BodyBean {
                /**
                 * cover : https://88ys.in/upload/vod/2019-04/15547360111.jpg
                 * year : 201904
                 * id : 58765
                 * tag : 更新至34集
                 * info : ["王丽坤,罗晋,邓伦,张博,胡静,于和伟,何杜娟,邹兆龙,海一天","国产剧","2019/大陆"]
                 * title : 封神演义2019
                 */

                private String cover;
                private String year;
                private String id;
                private String tag;
                private String title;
                private List<String> info;

                public String getCover() {
                    return cover;
                }

                public void setCover(String cover) {
                    this.cover = cover;
                }

                public String getYear() {
                    return year;
                }

                public void setYear(String year) {
                    this.year = year;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getTag() {
                    return tag;
                }

                public void setTag(String tag) {
                    this.tag = tag;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public List<String> getInfo() {
                    return info;
                }

                public void setInfo(List<String> info) {
                    this.info = info;
                }
            }
        }
    }
}
