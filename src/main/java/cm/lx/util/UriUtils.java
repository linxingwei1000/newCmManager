package cm.lx.util;

/**
 * @author lbb@jtang.cn
 */
public class UriUtils {
    /**
     * @param uri
     * @return
     */
	public static boolean ignoreStaticResource(String uri) {
		String[] ignoreStaticResources = { "/css/", "/img/", "/js/", "/jqplayer", "/file/", "/lib/" };
		return containsIn(uri, ignoreStaticResources);
	}

	/**
	 * 向外提供的URI
	 * 
	 * @param uri
	 * @return
	 */
	public static boolean ignore(String uri) {
		//2014-9-19新加一个http接口，向外提供短信查询服务
		String[] ignoreUrls = { "/login", "/logout", "/index", "/nopermission",
				"/code"};
		return containsIn(uri, ignoreUrls);
	}

	static boolean containsIn(String str, String[] array ){
		for (String i : array) {
			if (str.contains(i))
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @param uri
	 * @return
	 */
	public static boolean defaultPermission(String uri) {
		String[] defaultPermissionUrls = { "/main", "/fontAwesome", "/fonts" };
        return containsIn(uri, defaultPermissionUrls);
	}
	
	public static boolean judgeUpdatePermission(String uri) {
		String[] defaultPermissionUrls = { "/viewUpdateUser","/viewUpdateMyselfPsw" };
        return containsIn(uri, defaultPermissionUrls);
	}
}
