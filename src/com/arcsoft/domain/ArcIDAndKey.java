package com.arcsoft.domain;

import com.sun.jna.Platform;

public class ArcIDAndKey {
	private static String APPID;
	private static String FD_SDKKEY;
	private static String FR_SDKKEY;
	private static String FAE_SDKKEY;
	private static String FGE_SDKKEY;
	private static String FT_SDKKEY;

	static {
		if (Platform.is64Bit() && Platform.isLinux()) {
			APPID = "CxwTcQXo1d3Cx1WMhTPirmKRCkiMmH5sk8ztoL3GodvS";
			FD_SDKKEY = "AFXj6hiEPBKFBhX4rqc5JsfyMqgrNYUYt9s9Kd2H5T2U";
			FR_SDKKEY = "AFXj6hiEPBKFBhX4rqc5JsgU1SjXEKwJ9UB1iV6529uQ";
			FAE_SDKKEY = "AFXj6hiEPBKFBhX4rqc5JsgiLFFtHEuGEtf4oD1FG1PN";
			FGE_SDKKEY = "AFXj6hiEPBKFBhX4rqc5JsgqVeX4xWfUF1iRcE8HhprM";
			FT_SDKKEY = "AFXj6hiEPBKFBhX4rqc5JsfrCSRfuQ4xjD9Lc3A5QPjo";
		} else if (Platform.isWindows()) {
			if (Platform.is64Bit()) {
				APPID = "CxwTcQXo1d3Cx1WMhTPirmKJ3MTAjdThVn3f4gSFNeur";
				FD_SDKKEY = "Hk7hSLaeYikhmqwDvn8nsH5ZZbt7EtbatV1JMsGdoKrS";
				FR_SDKKEY = "Hk7hSLaeYikhmqwDvn8nsH64DCvnuX8c9xNFQuaoLiHE";
				FAE_SDKKEY = "Hk7hSLaeYikhmqwDvn8nsH6JY1T8xppex8ZnSwYLZRF2";
				FGE_SDKKEY = "Hk7hSLaeYikhmqwDvn8nsH6RhQiJrXWM1gyGWUrgGhqT";
				FT_SDKKEY = "Hk7hSLaeYikhmqwDvn8nsH5SQCcvG77C1wXFwH9Wp388";
			} else {
				APPID = "CxwTcQXo1d3Cx1WMhTPirmKYN9yW8xhigTUP6GQpBStW";
				FD_SDKKEY = "HEi9WUJgSwKbxB858hsQHK9o3Ws5VRZ6BDKLuV4Thv1A";
				FR_SDKKEY = "HEi9WUJgSwKbxB858hsQHK9vCv8Faxu4F1B4Z9kDEndA";
				FAE_SDKKEY = "HEi9WUJgSwKbxB858hsQHKAQrXAuctCrABvSjNvi68Zv";
				FGE_SDKKEY = "HEi9WUJgSwKbxB858hsQHKAfBKhDRMS15ZVBJ4mhPk2i";
				FT_SDKKEY = "HEi9WUJgSwKbxB858hsQHKAnLixRX1sE2j9HXMrqTYun";
			}
		} else {
			System.err.println("unsupported platform,init arcsoft appid and appkey failed.");
		}
	}

	public static String getAPPID() {
		return APPID;
	}

	public static void setAPPID(String APPID) {
		ArcIDAndKey.APPID = APPID;
	}

	public static String getFtSdkkey() {
		return FT_SDKKEY;
	}

	public static void setFtSdkkey(String ftSdkkey) {
		FT_SDKKEY = ftSdkkey;
	}

	public static String getFgeSdkkey() {

		return FGE_SDKKEY;
	}

	public static void setFgeSdkkey(String fgeSdkkey) {
		FGE_SDKKEY = fgeSdkkey;
	}

	public static String getFaeSdkkey() {

		return FAE_SDKKEY;
	}

	public static void setFaeSdkkey(String faeSdkkey) {
		FAE_SDKKEY = faeSdkkey;
	}

	public static String getFrSdkkey() {

		return FR_SDKKEY;
	}

	public static void setFrSdkkey(String frSdkkey) {
		FR_SDKKEY = frSdkkey;
	}

	public static String getFdSdkkey() {

		return FD_SDKKEY;
	}

	public static void setFdSdkkey(String fdSdkkey) {
		FD_SDKKEY = fdSdkkey;
	}
}
