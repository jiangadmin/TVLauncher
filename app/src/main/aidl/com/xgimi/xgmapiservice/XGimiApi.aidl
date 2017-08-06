// XGimiApi.aidl
package com.xgimi.xgmapiservice;

// Declare any non-default types here with import statements

interface XGimiApi {
    void setLedMode(String ledMode);
    String getLedMode();
    String getWindSpeed();
    void setWindSpeed(String windSpeedValue);
    void updateWindSpeedValue(int windSpeedValue);
    String getMachineId();
    String getTemp();
    void setProjectionMode(String projectMode);
    String getProjectionMode();
    int getZoomValue();
    int getHorizentalValue();
    int getVerticalValue();
    void setZoomValue(int value);
    void setHorizentalValue(int value);
    void setVerticalValue(int value);
    String getMachineSignal();
    String getBootSource();
    void setBootSource(int bootSource);
    int getBootSourceIndex();
    void setDeviceName(String deviceName);
    String getDeviceName();
    void setAutoUpdateTime(int autoTime);
    void setPowerOnStart(boolean ifstart);
    boolean getPowerOnStartValue();
}
