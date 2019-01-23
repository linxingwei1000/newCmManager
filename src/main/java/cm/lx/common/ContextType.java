package cm.lx.common;

public class ContextType {

    public final static Integer PURCHASE_CG = 1;                        //采购
    public final static Integer PURCHASE_JS = 2;                        //寄售
    public final static Integer PURCHASE_BM = 3;                        //帮买
    public final static Integer PURCHASE_MB = 4;                        //帮买

    public final static String CAR_LINE = "CAR_LINE";                                   //车系
    public final static String CAR_LEVEL = "CAR_LEVEL";                                 //车级
    public final static String CAR_CHANNEL = "CAR_CHANNEL";                             //来源渠道
    public final static String CAR_TAKE_TYPE = "CAR_TAKE_TYPE";                         //提车方式
    public final static String CAR_STATUS = "CAR_STATUS";                               //车况
    public final static String CAR_PURCHASE_TYPE = "CAR_PURCHASE_TYPE";                 //车辆买入方式
    public final static String CAR_CONSUMER_PROPERTY = "CAR_CONSUMER_PROPERTY";         //客户属性
    public final static String CAR_CONSUMER_RESOURCE = "CAR_CONSUMER_RESOURCE";         //获客渠道
    public final static String CAR_PURCHASE_USE = "CAR_PURCHASE_USE";                   //购车用途
    public final static String CONSUMER_GENERATION = "CONSUMER_GENERATION";             //客户年龄段
    public final static String BUSINESS_TYPE = "BUSINESS_TYPE";             //保险业务类型

    public final static String CAR_PROPERTY_DEFAULT = "未填选";                         //车辆属性缺省值

    public final static String CAR_BATH_IDS = "CAR_BATH_IDS";               //批量采购ids


    public final static Integer RECORD_STATUS_PURCHASE = 1;             //车辆买入
    public final static Integer RECORD_STATUS_STOCK = 2;                //车辆库存
    public final static Integer RECORD_STATUS_SALE = 3;                //车辆销售中
    public final static Integer RECORD_STATUS_SOLD = 4;                //车辆已售

    public final static Integer REMARK_TYPE_PURCHASE = 1;             //备注车辆买入
    public final static Integer REMARK_TYPE_STOCK = 2;                //备注车辆库存
    public final static Integer REMARK_TYPE_SALE = 3;                //备注车辆销售中
    public final static Integer REMARK_TYPE_DOSSIER = 4;             //档案管理备注

    public final static Integer PAY_RECORD_PURCHASE = 1;                //采购付款记录
    public final static Integer PAY_RECORD_SALE = 2;                    //销售付款记录
    public final static Integer PAY_RECORD_MORTGAGE = 3;                //按揭放款记录
    public final static Integer PAY_RECORD_BACK = 4;                    //退订记录
    public final static Integer PAY_RECORD_DEPOSIT = 5;                    //订金记录
    public final static Integer PAY_RECORD_MORTGAGE_LOG = 6;         //代办按揭放款记录
    public final static Integer PAY_RECORD_BUDGET = 7;                //档案预算记录
    public final static Integer PAY_RECORD_NEW_CAR_MORTGAGE = 8;                //新车按揭放款记录

    public final static Integer PRE_SETUP_TYPE = 1;                             //车辆售前整备
    public final static Integer AFTER_SETUP_TYPE = 2;                           //车辆售后整备
    public final static Integer OTHER_INCOME = 3;                               //车辆其他收入
    public final static Integer SALE_TYPE = 4;                                  //销售整备
    public final static Integer NEW_CAR_COST_TYPE = 5;                          //新车成本整备
    public final static Integer NEW_CAR_INCOME_TYPE = 6;                        //新车收入整备
    public final static Integer NEW_CAR_FIANCE_COST_TYPE = 7;                   //新车金融方案成本整备
    public final static Integer MORTGAGE_AGENT_PAY = 8;                         //按揭代付项目

    public final static String SETUP_SKIP_PARAM = "action;id;over;carCostId;setupType;recordStatus"; //整备跳过的参数处理

    public final static Integer CAR_STATUS_FROM_STOCK_TO_SALE = 1;             //车辆由库存转到销售
    public final static Integer CAR_STATUS_FROM_SALE_TO_STOCK = 2;             //车辆由销售转到库存
    public final static Integer CAR_STATUS_FROM_SALE_TO_SOLD = 3;             //车辆由销售转到已售
    public final static Integer CAR_STATUS_FROM_SOLD_TO_SALE = 4;             //车辆由已售转到销售

    public final static Integer SALE_TYPE_QK = 1;                     //全款
    public final static Integer SALE_TYPE_AJ = 2;                     //按揭

    public final static Double SERVICE_MONEY = 1000.0;                  //每辆车售后服务金

    public final static Integer NEW_CAR_SALE = 1;                       //新车销售中
    public final static Integer NEW_CAR_SOLD = 2;                       //新车售后

    public final static Integer DISPLAY_STATUS_SHOW = 1;                //数据显示
    public final static Integer DISPLAY_STATUS_HIDE = 2;                //数据隐藏
    public final static Integer DISPLAY_STATUS_DONE = 3;                //数据已处理

    public final static Integer MONEY_TYPE_CASH = 1;                    //现金流水
    public final static Integer MONEY_TYPE_BANK = 2;                    //银行流水
    public final static Integer MONEY_TYPE_POSS = 3;                    //poss机流水
    public final static Integer MONEY_TYPE_LOAN = 4;                    //借款
    public final static Integer MONEY_TYPE_COOPERATE = 5;               //合作款
    public final static Integer MONEY_TYPE_RECEIVABLE = 6;              //应收款
    public final static Integer MONEY_TYPE_BASIC = 7;                   //基本户
    public final static Integer MONEY_TYPE_HOUSE = 8;                   //房租平台广告

    public final static Integer MONEY_MANAGER_OUT = 1;                  //资金管理出
    public final static Integer MONEY_MANAGER_IN = 2;                   //资金管理入

    public final static Integer MORTGAGE_TYPE_AGENCY = 1;               //代办按揭
    public final static Integer MORTGAGE_TYPE_OUT = 2;                  //外扩按揭
}
