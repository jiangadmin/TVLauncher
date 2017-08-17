package com.jiang.tvlauncher.entity;

import java.util.List;

/**
 * Created by wwwfa on 2017/8/17.
 */

public class FindChannelList extends BaseEntity {


    /**
     * errorcode : 1000
     * result : [{"appList":[{"appName":"优酷","createAuthor":"","createTime":"2017-07-18 16:10:59","iconUrl":"","id":1,"isDelete":0,"packageName":"com.youku.la","remark":"","updateAuthor":"","updateTime":"2017-07-18 16:10:59","useState":1},{"appName":"爱奇艺","createAuthor":"","createTime":"2017-07-18 16:12:01","iconUrl":"","id":2,"isDelete":0,"packageName":"com.aiqy","remark":"我就想测试一下","updateAuthor":"系统管理员","updateTime":"2017-08-07 21:58:25","useState":1}],"bgUrl":"http://7xkcno.com2.z0.glb.qiniucdn.com/3D338FEA957441E6303808B33D2628B2.jpg","channelCode":"","channelName":"影视剧场","contentType":2,"contentUrl":"","createAuthor":"系统管理员","createTime":"2017-08-11 17:49:03","id":2,"isDelete":0,"parentId":0,"remark":"123","updateAuthor":"系统管理员","updateTime":"2017-08-15 17:18:03","useState":0},{"appList":[],"bgUrl":"http://7xkcno.com2.z0.glb.qiniucdn.com/167587314ADA5E1166FA3E83EF9EED83.jpg","channelCode":"","channelName":"123","contentType":0,"contentUrl":"","createAuthor":"系统管理员","createTime":"2017-08-11 17:40:50","id":1,"isDelete":0,"parentId":0,"remark":"123","updateAuthor":"系统管理员","updateTime":"2017-08-11 17:40:50","useState":0}]
     */

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * appList : [{"appName":"优酷","createAuthor":"","createTime":"2017-07-18 16:10:59","iconUrl":"","id":1,"isDelete":0,"packageName":"com.youku.la","remark":"","updateAuthor":"","updateTime":"2017-07-18 16:10:59","useState":1},{"appName":"爱奇艺","createAuthor":"","createTime":"2017-07-18 16:12:01","iconUrl":"","id":2,"isDelete":0,"packageName":"com.aiqy","remark":"我就想测试一下","updateAuthor":"系统管理员","updateTime":"2017-08-07 21:58:25","useState":1}]
         * bgUrl : http://7xkcno.com2.z0.glb.qiniucdn.com/3D338FEA957441E6303808B33D2628B2.jpg
         * channelCode :
         * channelName : 影视剧场
         * contentType : 2
         * contentUrl :
         * createAuthor : 系统管理员
         * createTime : 2017-08-11 17:49:03
         * id : 2
         * isDelete : 0
         * parentId : 0
         * remark : 123
         * updateAuthor : 系统管理员
         * updateTime : 2017-08-15 17:18:03
         * useState : 0
         */

        private String bgUrl;
        private String channelCode;
        private String channelName;
        private int contentType;
        private String contentUrl;
        private String createAuthor;
        private String createTime;
        private int id;
        private int isDelete;
        private int parentId;
        private String remark;
        private String updateAuthor;
        private String updateTime;
        private int useState;
        private List<AppListBean> appList;

        public String getBgUrl() {
            return bgUrl;
        }

        public void setBgUrl(String bgUrl) {
            this.bgUrl = bgUrl;
        }

        public String getChannelCode() {
            return channelCode;
        }

        public void setChannelCode(String channelCode) {
            this.channelCode = channelCode;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public int getContentType() {
            return contentType;
        }

        public void setContentType(int contentType) {
            this.contentType = contentType;
        }

        public String getContentUrl() {
            return contentUrl;
        }

        public void setContentUrl(String contentUrl) {
            this.contentUrl = contentUrl;
        }

        public String getCreateAuthor() {
            return createAuthor;
        }

        public void setCreateAuthor(String createAuthor) {
            this.createAuthor = createAuthor;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getUpdateAuthor() {
            return updateAuthor;
        }

        public void setUpdateAuthor(String updateAuthor) {
            this.updateAuthor = updateAuthor;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getUseState() {
            return useState;
        }

        public void setUseState(int useState) {
            this.useState = useState;
        }

        public List<AppListBean> getAppList() {
            return appList;
        }

        public void setAppList(List<AppListBean> appList) {
            this.appList = appList;
        }

        public static class AppListBean {
            /**
             * appName : 优酷
             * createAuthor :
             * createTime : 2017-07-18 16:10:59
             * iconUrl :
             * id : 1
             * isDelete : 0
             * packageName : com.youku.la
             * remark :
             * updateAuthor :
             * updateTime : 2017-07-18 16:10:59
             * useState : 1
             */

            private String appName;
            private String createAuthor;
            private String createTime;
            private String iconUrl;
            private int id;
            private int isDelete;
            private String packageName;
            private String remark;
            private String updateAuthor;
            private String updateTime;
            private int useState;

            public String getAppName() {
                return appName;
            }

            public void setAppName(String appName) {
                this.appName = appName;
            }

            public String getCreateAuthor() {
                return createAuthor;
            }

            public void setCreateAuthor(String createAuthor) {
                this.createAuthor = createAuthor;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsDelete() {
                return isDelete;
            }

            public void setIsDelete(int isDelete) {
                this.isDelete = isDelete;
            }

            public String getPackageName() {
                return packageName;
            }

            public void setPackageName(String packageName) {
                this.packageName = packageName;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getUpdateAuthor() {
                return updateAuthor;
            }

            public void setUpdateAuthor(String updateAuthor) {
                this.updateAuthor = updateAuthor;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public int getUseState() {
                return useState;
            }

            public void setUseState(int useState) {
                this.useState = useState;
            }
        }
    }
}
