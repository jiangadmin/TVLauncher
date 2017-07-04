package com.jiang.tvlauncher.entity;

/**
 * Created by  jiang
 * on 2017/7/4.
 * Email: www.fangmu@qq.com
 * Phone：186 6120 1018
 * Purpose:TODO 注册实体类
 * update：
 */
public class Register extends BaseEntity {

    /**
     * errorcode : 1000
     * result : {"createAuthor":"system","createTime":1499138307797,"id":6,"isDelete":0,"modelNum":"智米投影","serialNum":"111111","updateAuthor":"system","updateTime":1499138307797}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * createAuthor : system
         * createTime : 1499138307797
         * id : 6
         * isDelete : 0
         * modelNum : 智米投影
         * serialNum : 111111
         * updateAuthor : system
         * updateTime : 1499138307797
         */

        private String createAuthor;
        private long createTime;
        private int id;
        private int isDelete;
        private String modelNum;
        private String serialNum;
        private String updateAuthor;
        private long updateTime;

        public String getCreateAuthor() {
            return createAuthor;
        }

        public void setCreateAuthor(String createAuthor) {
            this.createAuthor = createAuthor;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
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

        public String getModelNum() {
            return modelNum;
        }

        public void setModelNum(String modelNum) {
            this.modelNum = modelNum;
        }

        public String getSerialNum() {
            return serialNum;
        }

        public void setSerialNum(String serialNum) {
            this.serialNum = serialNum;
        }

        public String getUpdateAuthor() {
            return updateAuthor;
        }

        public void setUpdateAuthor(String updateAuthor) {
            this.updateAuthor = updateAuthor;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }
    }
}
