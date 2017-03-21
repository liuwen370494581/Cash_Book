package star.liuwen.com.cash_books.Base;

import android.os.Environment;

/**
 * Created by liuwen on 2017/1/10.
 */
public class Config {

    public static final String RootPath = getRootPath() + "/Cash_Books/"; //上传经过压缩好的相册
    public static final String SHARED_PREFERENCES_FILE_NAME = "Jzb";

    public static final String PhotoPath = getRootPath() + "/cashPhoto"; //存放拍好的相册

    private static String getRootPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }


    public final static String isBgCash = "isBgCash";
    public final static String isBgCXK = "isBgCXK";
    public final static String isBgXYK = "isBgXYK";
    public final static String isBgZFB = "isBgZFB";
    public final static String isBgJC = "isBgJC";
    public final static String isBgJR = "isBgJR";

    public final static String isRemindPush = "isRemindPush";
    public final static String isOpenCodedLock = "isOpenCodedLock";
    public final static String ChangeBg = "ChangeBg";


    public enum AccountType {
        zhiChu, shouRu;
    }

    public enum Account {
        Cash, Cxk, Xyk, Zfb, Jc, Jr, other
    }


    public final static String ZHI_CHU = "ZHICHU";
    public final static String SHOU_RU = "SHOURU";
    public final static String CASH = "现金";
    public final static String CXK = "储蓄卡";
    public final static String XYK = "信用卡";
    public final static String ZFB = "支付宝";
    public final static String JC = "借出";
    public final static String JR = "借入";
    public final static String OTHER = "OTHER";


    public final static String TarGetUrl = "TarGetUrl";
    public final static String userUrl = "userUrl";
    public final static String userNickName = "userNickName";
    public final static String userSignature = "userSignature";
    public final static String userSex = "userSix";


    public final static String HuoBICh = "HuoBICh";
    public final static String HuoBIEn = "HuoBIEn";

    public final static String AccountSetting = "AccountSetting";

    public static final String SHARE_LOGO = RootPath + "/share/logo/";


    public static final String LockPassword = "LockPassword";
    public static final String newSavePosition = "newSavePosition";
    public static final String PlanSaveMoneyModel = "PlanSaveMoneyModel";
    public static final String Travel = "Travel";
    public static final String Concert = "Concert";
    public static final String Game = "Game";

    public static final String FistStar = "FirstStar";
    public static final String UserName = "UserName";
    public static final String UserPassWord = "UserPassWord";

    public static final String PlanMoney = "PlanMoney";
    public static final String PlanFinishTime = "PlanFinishTime";
    public static final String PlanRemark = "PlanRemark";
    public static final String PlanIsPut = "PlanIsPut";

    public static final String SaveAPenPlatform = "SaveAPenPlatform";
    public static final String SaveAccount = "SaveAccount";
    public static final String TextInPut = "TextInPut";


    public static final String TxtMoney = "TxtMoney";
    public static final String TxtPercent = "TxtPercent";
    public static final String TxtRemark = "TxtRemark";
    public static final String TxtAccountName = "TxtAccountName";
    public static final String TxtAccountMoney = "TxtAccountMoney";
    public static final String TxtCreditLimit = "TxtCreditLimit";
    public static final String TxtDebt = "TxtDebt";
    public static final String TxtYuEr = "TxtYuer";
    public static final String TxtAccount = "TxtAccount";
    public static final String TxtStartTime = "TxtStartTime";
    public static final String TxtEndTime = "TxtEndTime";
    public static final String TxtPlanMoney = "TxtPlanMoney";
    public static final String TxtPlanRemark = "TxtPlanRemark";
    public static final String TxtNickName = "TxtNickName";
    public static final String TxtSigNature = "TxtSigNature";
    public static final String TxtChoiceAccount = "TxtChoiceAccount";
    public static final String TxtChoiceAccountDate = "TxtChoiceAccountDate";

    public static final String ModelWallet = "ModelWallet";
    public static final String ModelSaveAPen = "ModeSaveAPen";

    public static final String RxToReports = "RxToReports";
    public static final String RxToZhiChu = "RxToZhiChu";
    public static final String RxToSHouRu = "RxToSHouRu";

}
