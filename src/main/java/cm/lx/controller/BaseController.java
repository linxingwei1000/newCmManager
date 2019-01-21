package cm.lx.controller;

import java.net.URLEncoder;

public class BaseController {

	protected static final String TIP = "tip";
	protected static final String DATA = "dataList";

	protected static final Integer CREATE_ACTION = 1;
	protected static final Integer MOD_ACTION = 2;
	protected static final Integer DELETE_ACTION = 3;

	public String urlEncode(String str) {
		try {
			return URLEncoder.encode(URLEncoder.encode(str, "utf-8"), "utf-8");
		} catch (Exception e) {
			return "";
		}
	}

	public String toUtf8(String source) {
		try {
			return new String(source.getBytes("iso-8859-1"), "utf-8");
		} catch (Exception e) {
			return "";
		}
	}
}
