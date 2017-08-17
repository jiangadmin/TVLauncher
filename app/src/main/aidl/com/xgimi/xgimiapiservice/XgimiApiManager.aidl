// XgimiApiManager.aidl
package com.xgimi.xgimiapiservice;

interface XgimiApiManager {
  String set(String methodName,String value,String value1, String value2, String value3);
  String get(String methodName,String key, String key1);
}
