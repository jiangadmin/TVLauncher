package com.jiang.tvlauncher.entity;

import java.util.List;

/**
 * Created by wwwfa on 2017/8/9.
 */

public class FindLanunch extends BaseEntity {


    /**
     * errorcode : 1000
     * result : [{"createAuthor":"","createTime":"2017-07-06 13:08:30","id":3,"isDefault":1,"isDelete":0,"launchName":"开机方案2","launchType":1,"mediaType":1,"mediaUrl":"http://7xkcno.com2.z0.glb.qiniucdn.com/F4594CF749FE8A4B6091B17E66F6591A.png","updateAuthor":"","updateTime":"2017-07-06 13:08:30"}]
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
         * createAuthor :
         * createTime : 2017-07-06 13:08:30
         * id : 3
         * isDefault : 1
         * isDelete : 0
         * launchName : 开机方案2
         * launchType : 1
         * mediaType : 1
         * mediaUrl : http://7xkcno.com2.z0.glb.qiniucdn.com/F4594CF749FE8A4B6091B17E66F6591A.png
         * updateAuthor :
         * updateTime : 2017-07-06 13:08:30
         */

        private String createAuthor;
        private String createTime;
        private int id;
        private int isDefault;
        private int isDelete;
        private String launchName;
        private int launchType;
        private int mediaType;
        private String mediaUrl;
        private String updateAuthor;
        private String updateTime;

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

        public int getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public String getLaunchName() {
            return launchName;
        }

        public void setLaunchName(String launchName) {
            this.launchName = launchName;
        }

        public int getLaunchType() {
            return launchType;
        }

        public void setLaunchType(int launchType) {
            this.launchType = launchType;
        }

        public int getMediaType() {
            return mediaType;
        }

        public void setMediaType(int mediaType) {
            this.mediaType = mediaType;
        }

        public String getMediaUrl() {
            return mediaUrl;
        }

        public void setMediaUrl(String mediaUrl) {
            this.mediaUrl = mediaUrl;
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
    }
}
