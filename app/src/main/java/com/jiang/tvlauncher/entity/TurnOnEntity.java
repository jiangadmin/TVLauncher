package com.jiang.tvlauncher.entity;

import java.util.List;

/**
 * Created by wwwfa on 2017/8/9.
 */

public class TurnOnEntity extends BaseEntity {

    /**
     * errorcode : 1000
     * result : {"shadowcnf":{"createAuthor":"系统管理员","createTime":"2017-08-20 15:27:51","hotPoint":1,"id":1,"isDefault":1,"isDelete":0,"ladderPwd":"123","monitRate":300000,"powerTurn":0,"shadowName":"测试方案","shadowPwd":"8624862","threeDim":1,"turnSource":"123","updateAuthor":"系统管理员","updateTime":"2017-08-20 15:27:51","wifi":"123","wifiPassword":"123"},"devInfo":{"createAuthor":"system","createTime":"2017-08-17 22:43:10","id":14,"isDelete":0,"merchantId":0,"modelNum":"智米投影","roomId":0,"serialNum":"DC26HMF33TSH","updateAuthor":"system","updateTime":"2017-08-17 22:43:10","useState":0,"zoomVal":"{\"current_x\":0,\"current_y\":0,\"idx\":0,\"max_x\":100,\"max_y\":100,\"min_x\":0,\"min_y\":0},{\"current_x\":0,\"current_y\":0,\"idx\":1,\"max_x\":0,\"max_y\":100,\"min_x\":-100,\"min_y\":0},{\"current_x\":0,\"current_y\":0,\"idx\":3,\"max_x\":0,\"max_y\":0,\"min_x\":-100,\"min_y\":-100},{\"current_x\":0,\"current_y\":0,\"idx\":2,\"max_x\":100,\"max_y\":0,\"min_x\":0,\"min_y\":-100}"},"launch":[{"createAuthor":"","createTime":"2017-07-06 13:08:30","id":3,"isDefault":1,"isDelete":0,"launchName":"开机方案2","launchType":1,"mediaType":2,"mediaUrl":"http://7xkcno.com2.z0.glb.qiniucdn.com/9361BE1FB331EA2915B82569758BE30C.mp4","updateAuthor":"","updateTime":"2017-07-06 13:08:30"}]}
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
         * shadowcnf : {"createAuthor":"系统管理员","createTime":"2017-08-20 15:27:51","hotPoint":1,"id":1,"isDefault":1,"isDelete":0,"ladderPwd":"123","monitRate":300000,"powerTurn":0,"shadowName":"测试方案","shadowPwd":"8624862","threeDim":1,"turnSource":"123","updateAuthor":"系统管理员","updateTime":"2017-08-20 15:27:51","wifi":"123","wifiPassword":"123"}
         * devInfo : {"createAuthor":"system","createTime":"2017-08-17 22:43:10","id":14,"isDelete":0,"merchantId":0,"modelNum":"智米投影","roomId":0,"serialNum":"DC26HMF33TSH","updateAuthor":"system","updateTime":"2017-08-17 22:43:10","useState":0,"zoomVal":"{\"current_x\":0,\"current_y\":0,\"idx\":0,\"max_x\":100,\"max_y\":100,\"min_x\":0,\"min_y\":0},{\"current_x\":0,\"current_y\":0,\"idx\":1,\"max_x\":0,\"max_y\":100,\"min_x\":-100,\"min_y\":0},{\"current_x\":0,\"current_y\":0,\"idx\":3,\"max_x\":0,\"max_y\":0,\"min_x\":-100,\"min_y\":-100},{\"current_x\":0,\"current_y\":0,\"idx\":2,\"max_x\":100,\"max_y\":0,\"min_x\":0,\"min_y\":-100}"}
         * launch : [{"createAuthor":"","createTime":"2017-07-06 13:08:30","id":3,"isDefault":1,"isDelete":0,"launchName":"开机方案2","launchType":1,"mediaType":2,"mediaUrl":"http://7xkcno.com2.z0.glb.qiniucdn.com/9361BE1FB331EA2915B82569758BE30C.mp4","updateAuthor":"","updateTime":"2017-07-06 13:08:30"}]
         */

        private ShadowcnfBean shadowcnf;
        private DevInfoBean devInfo;
        private List<LaunchBean> launch;

        public ShadowcnfBean getShadowcnf() {
            return shadowcnf;
        }

        public void setShadowcnf(ShadowcnfBean shadowcnf) {
            this.shadowcnf = shadowcnf;
        }

        public DevInfoBean getDevInfo() {
            return devInfo;
        }

        public void setDevInfo(DevInfoBean devInfo) {
            this.devInfo = devInfo;
        }

        public List<LaunchBean> getLaunch() {
            return launch;
        }

        public void setLaunch(List<LaunchBean> launch) {
            this.launch = launch;
        }

        public static class ShadowcnfBean {
            /**
             * createAuthor : 系统管理员
             * createTime : 2017-08-20 15:27:51
             * hotPoint : 1
             * id : 1
             * isDefault : 1
             * isDelete : 0
             * ladderPwd : 123
             * monitRate : 300000
             * powerTurn : 0
             * shadowName : 测试方案
             * shadowPwd : 8624862
             * threeDim : 1
             * turnSource : 123
             * updateAuthor : 系统管理员
             * updateTime : 2017-08-20 15:27:51
             * wifi : 123
             * wifiPassword : 123
             */

            private String createAuthor;
            private String createTime;
            private int hotPoint;
            private int id;
            private int isDefault;
            private int isDelete;
            private String ladderPwd;
            private int monitRate;
            private int powerTurn;
            private String shadowName;
            private String shadowPwd;
            private int threeDim;
            private String turnSource;
            private String updateAuthor;
            private String updateTime;
            private String wifi;
            private String wifiPassword;

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

            public int getHotPoint() {
                return hotPoint;
            }

            public void setHotPoint(int hotPoint) {
                this.hotPoint = hotPoint;
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

            public String getLadderPwd() {
                return ladderPwd;
            }

            public void setLadderPwd(String ladderPwd) {
                this.ladderPwd = ladderPwd;
            }

            public int getMonitRate() {
                return monitRate;
            }

            public void setMonitRate(int monitRate) {
                this.monitRate = monitRate;
            }

            public int getPowerTurn() {
                return powerTurn;
            }

            public void setPowerTurn(int powerTurn) {
                this.powerTurn = powerTurn;
            }

            public String getShadowName() {
                return shadowName;
            }

            public void setShadowName(String shadowName) {
                this.shadowName = shadowName;
            }

            public String getShadowPwd() {
                return shadowPwd;
            }

            public void setShadowPwd(String shadowPwd) {
                this.shadowPwd = shadowPwd;
            }

            public int getThreeDim() {
                return threeDim;
            }

            public void setThreeDim(int threeDim) {
                this.threeDim = threeDim;
            }

            public String getTurnSource() {
                return turnSource;
            }

            public void setTurnSource(String turnSource) {
                this.turnSource = turnSource;
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

            public String getWifi() {
                return wifi;
            }

            public void setWifi(String wifi) {
                this.wifi = wifi;
            }

            public String getWifiPassword() {
                return wifiPassword;
            }

            public void setWifiPassword(String wifiPassword) {
                this.wifiPassword = wifiPassword;
            }
        }

        public static class DevInfoBean {
            /**
             * createAuthor : system
             * createTime : 2017-08-17 22:43:10
             * id : 14
             * isDelete : 0
             * merchantId : 0
             * modelNum : 智米投影
             * roomId : 0
             * serialNum : DC26HMF33TSH
             * updateAuthor : system
             * updateTime : 2017-08-17 22:43:10
             * useState : 0
             * zoomVal : {"current_x":0,"current_y":0,"idx":0,"max_x":100,"max_y":100,"min_x":0,"min_y":0},{"current_x":0,"current_y":0,"idx":1,"max_x":0,"max_y":100,"min_x":-100,"min_y":0},{"current_x":0,"current_y":0,"idx":3,"max_x":0,"max_y":0,"min_x":-100,"min_y":-100},{"current_x":0,"current_y":0,"idx":2,"max_x":100,"max_y":0,"min_x":0,"min_y":-100}
             */

            private String createAuthor;
            private String createTime;
            private int id;
            private int isDelete;
            private int merchantId;
            private String modelNum;
            private int roomId;
            private String serialNum;
            private String updateAuthor;
            private String updateTime;
            private int useState;
            private String zoomVal;

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

            public int getMerchantId() {
                return merchantId;
            }

            public void setMerchantId(int merchantId) {
                this.merchantId = merchantId;
            }

            public String getModelNum() {
                return modelNum;
            }

            public void setModelNum(String modelNum) {
                this.modelNum = modelNum;
            }

            public int getRoomId() {
                return roomId;
            }

            public void setRoomId(int roomId) {
                this.roomId = roomId;
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

            public String getZoomVal() {
                return zoomVal;
            }

            public void setZoomVal(String zoomVal) {
                this.zoomVal = zoomVal;
            }
        }

        public static class LaunchBean {
            /**
             * createAuthor :
             * createTime : 2017-07-06 13:08:30
             * id : 3
             * isDefault : 1
             * isDelete : 0
             * launchName : 开机方案2
             * launchType : 1
             * mediaType : 2
             * mediaUrl : http://7xkcno.com2.z0.glb.qiniucdn.com/9361BE1FB331EA2915B82569758BE30C.mp4
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
}
