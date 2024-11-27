package com.isylph.basis.consts;

public class CommonConsts {
    public static final Long NULL_UID = 0L;
    public static final String NULL_ACCOUNT_ID = "";

    public static final String CHARSET_UTF8 = "UTF-8";

    public static final long ROLE_ID_ROOT=0; /**root角色的记录ID*/
    public static final long ROLE_ID_ADMIN=1; /**admin角色的记录ID*/
    public static final long ROLE_ID_MEMBER=2;/**member角色的记录ID*/

    public static final int RECORD_DELETED = 1;
    public static final int RECORD_AVAILABLE = 0;

    public static final int BLOCKED = 0;
    public static final int DEACTIVE = 100;
    public static final int ACTIVE = 200;
    public static final int OFFLINE = 300;   //下线
    public static final int ONLINE = 400;   //上线

    public static final int NO = 0;
    public static final int YES = 1;

    /**
     * 审核
     */
    public static final int APPROVED = 1;
    public static final int REJECTED = 2;

    public static final long SYSTEM_ERROR_CODE = 500; /**内部服务器错误码*/
    public static final long INCORRECT_REQUEST_ERROR_CODE = 400; /**请求不正确错误码，无法通过验证的请求选项*/





    public static final String COMMON_PATH_SEPARATE_STR = ";";

    /**
     * 系统中树数据结构的根节点Level,0
     */
    public static final byte LB_TREE_ROOT_LEVEL = 0;




    public static final Integer RANDOM_LEN = 6;//随机数的长度

    public static final String CLIENT_PASSWORD_SALT = "Bg#06"; //前台的盐

    public static final String ADMINISTATOR_ACCOUNT = "admin"; //

    /**
     * 终端类型
     */
    public static final Byte CLIENT_TYPE_WEB = 0;
    public static final Byte CLIENT_TYPE_APP = 1;
    public static final Byte CLIENT_TYPE_ALI = 2;
    public static final Byte CLIENT_TYPE_WECHAT = 3;

}
