// XGimiApi.aidl
package com.xgimi.xgmapiservice;

// Declare any non-default types here with import statements

interface XGimiApi {
    //设置LED亮度模式 MOVIE_MODE 观影模式 OFFICE_MODE 办公模式
    void setLedMode(String ledMode);
    //获取LED亮度模式
    String getLedMode();
    //设置风速 0为自动 10 为自动
    void setWindSpeed(String windSpeedValue);
    //设置风速挡位
    void updateWindSpeedValue(int windSpeedValue);
    //获取风速
    String getWindSpeed();
    //获取设备编号
    String getMachineId();
    //获取温度
    String getTemp();
    //设置投影方式 “0”:正装正投“1”:吊装正投 “2”:正装背投“3”:吊装背投
    void setProjectionMode(String projectMode);
    //获取投影方式
    String getProjectionMode();
    //设置全局缩放
    void setZoomValue(int value);
    //获取全局缩放
    int getZoomValue();
    //设置横向缩放
    void setHorizentalValue(int value);
    //获取横向缩放
    int getHorizentalValue();
    //设置纵向缩放
    void setVerticalValue(int value);
    //获取纵向缩放
    int getVerticalValue();
    //获取该机型所有的Signal
    String getMachineSignal();
    //设置开机源 0 关闭开机源 数字对应Signal数据
    void setBootSource(int bootSource);
    //获取开机源
    String getBootSource();
    //？？？？获取开机源
    int getBootSourceIndex();
    //设置设备名称
    void setDeviceName(String deviceName);
    //获取设备名称
    String getDeviceName();
    //自动获取时间设置
    void setAutoUpdateTime(int autoTime);
    //设置上电开机
    void setPowerOnStart(boolean ifstart);
    //获取上电开机
    boolean getPowerOnStartValue();
}
