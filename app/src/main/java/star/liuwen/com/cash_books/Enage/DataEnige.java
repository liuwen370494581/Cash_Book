package star.liuwen.com.cash_books.Enage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoChoiceAccount;
import star.liuwen.com.cash_books.Dao.DaoShouRuModel;
import star.liuwen.com.cash_books.Dao.DaoZhiChuModel;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.Utils.DateTimeUtil;
import star.liuwen.com.cash_books.bean.AccountWealthModel;
import star.liuwen.com.cash_books.bean.ChoiceAccount;
import star.liuwen.com.cash_books.bean.ColorModel;
import star.liuwen.com.cash_books.bean.IndexModel;
import star.liuwen.com.cash_books.bean.PlanSaveMoneyModel;
import star.liuwen.com.cash_books.bean.ShouRuModel;
import star.liuwen.com.cash_books.bean.ZhiChuModel;
import star.liuwen.com.cash_books.widget.CharacterParser;
import star.liuwen.com.cash_books.widget.PinyinComparator;

/**
 * Created by liuwen on 2017/1/5.
 */
public class DataEnige {


    public static List<ZhiChuModel> getZhiChuData() {
        List<ZhiChuModel> list = new ArrayList<>();
        list.add(new ZhiChuModel(R.mipmap.icon_shouru_type_qita, "一般"));
        list.add(new ZhiChuModel(R.mipmap.dapai, "打牌"));
        list.add(new ZhiChuModel(R.mipmap.dianfei, "电费"));
        list.add(new ZhiChuModel(R.mipmap.dianying, "电影"));
        list.add(new ZhiChuModel(R.mipmap.fangdai, "房贷"));
        list.add(new ZhiChuModel(R.mipmap.fangzu, "房租"));
        list.add(new ZhiChuModel(R.mipmap.fanka, "饭卡"));
        list.add(new ZhiChuModel(R.mipmap.feijipiao, "飞机票"));
        list.add(new ZhiChuModel(R.mipmap.icon_gouwu, "购物"));
        list.add(new ZhiChuModel(R.mipmap.icon_jaotongyunshu, "交通出行"));
        list.add(new ZhiChuModel(R.mipmap.icon_jinianpin, "礼物"));
        list.add(new ZhiChuModel(R.mipmap.icon_lingshixiaochi, "零食小吃"));
        list.add(new ZhiChuModel(R.mipmap.icon_jiudianzhusu, "好玩"));
        list.add(new ZhiChuModel(R.mipmap.youfei, "邮费"));
        list.add(new ZhiChuModel(R.mipmap.maicai, "买菜"));
        list.add(new ZhiChuModel(R.mipmap.zaocan, "早餐"));
        list.add(new ZhiChuModel(R.mipmap.zhongfan, "中饭"));
        list.add(new ZhiChuModel(R.mipmap.wanfan, "晚饭"));
        list.add(new ZhiChuModel(R.mipmap.xiaochi, "小吃"));
        list.add(new ZhiChuModel(R.mipmap.wanggou, "网购"));
        list.add(new ZhiChuModel(R.mipmap.naifen, "奶粉"));
        list.add(new ZhiChuModel(R.mipmap.jiushui, "酒水"));
        list.add(new ZhiChuModel(R.mipmap.lingshi, "零食"));
        list.add(new ZhiChuModel(R.mipmap.richangyongpin, "生活品"));
        list.add(new ZhiChuModel(R.mipmap.xiezi, "鞋子"));
        list.add(new ZhiChuModel(R.mipmap.yaopinfei, "医药费"));
        list.add(new ZhiChuModel(R.mipmap.yifu, "衣服"));
        list.add(new ZhiChuModel(R.mipmap.icon_zhichu_type_taobao, "淘宝"));
        list.add(new ZhiChuModel(R.mipmap.tingchefei, "停车"));
        list.add(new ZhiChuModel(R.mipmap.majiang, "麻将"));
        list.add(new ZhiChuModel(R.mipmap.icon_add_12, "结婚礼金"));
        list.add(new ZhiChuModel(R.mipmap.icon_shouru_type_gongzi, "工资"));
        list.add(new ZhiChuModel(R.mipmap.icon_shouru_type_shenghuofei, "生活费"));
        list.add(new ZhiChuModel(R.mipmap.icon_shouru_type_hongbao, "红包"));
        list.add(new ZhiChuModel(R.mipmap.icon_shouru_type_linghuaqian, "零花钱"));
        list.add(new ZhiChuModel(R.mipmap.icon_shouru_type_jianzhiwaikuai, "外快兼职"));
        list.add(new ZhiChuModel(R.mipmap.icon_shouru_type_touzishouru, "投资收入"));
        list.add(new ZhiChuModel(R.mipmap.icon_shouru_type_jiangjin, "奖金"));
        list.add(new ZhiChuModel(R.mipmap.icon_zhichu_type_baoxiaozhang, "报销"));
        list.add(new ZhiChuModel(R.mipmap.xianjin, "现金"));
        list.add(new ZhiChuModel(R.mipmap.tuikuan, "退款"));
        list.add(new ZhiChuModel(R.mipmap.zhifubao, "支付宝"));
        return list;
    }


    public static List<PlanSaveMoneyModel> getPlanSaveMoneyData() {
        List<PlanSaveMoneyModel> list = new ArrayList<>();
        list.add(new PlanSaveMoneyModel("旅行", "48234人已经加入", R.mipmap.lvyou, "加入 +", "生活不止眼前的苟且，还有远方的诗和田野"));
        list.add(new PlanSaveMoneyModel("演唱会", "4987人已经加入", R.mipmap.yanchanghui, "加入 +", "跨越万水千山去看你，只为静静听你唱，那首贯穿我整个青春的歌"));
        list.add(new PlanSaveMoneyModel("比赛", "429人已经加入", R.mipmap.bisai, "加入 +", "输赢都是精彩，在我心中你永远是英雄"));
        list.add(new PlanSaveMoneyModel("买房装修", "20312人已经加入", R.mipmap.fang, "加入 +", "生活不止眼前的苟且，还有远方的诗和田野"));
        list.add(new PlanSaveMoneyModel("买车", "18929人已经加入", R.mipmap.che, "加入 +", "家,是你在这个城市扎下的根,是下班回家后橘黄色灯光的暖"));
        list.add(new PlanSaveMoneyModel("存下第一笔十万", "78551人已经加入", R.mipmap.shiwan, "加入 +", "不再做月光，是时候为自己的未来打算了"));
        return list;
    }


    public static List<IndexModel> getXykData() {
        List<IndexModel> data = new ArrayList<>();
        data.add(new IndexModel("北京农商银行"));
        data.add(new IndexModel("北京银行"));
        data.add(new IndexModel("成都工商银行"));
        data.add(new IndexModel("成都银行"));
        data.add(new IndexModel("长沙银行"));
        data.add(new IndexModel("重庆银行"));
        data.add(new IndexModel("大连银行"));
        data.add(new IndexModel("东莞银行"));
        data.add(new IndexModel("甘肃银行"));
        data.add(new IndexModel("广州银行"));
        data.add(new IndexModel("工商银行"));
        data.add(new IndexModel("广发银行"));
        data.add(new IndexModel("光大银行"));
        data.add(new IndexModel("杭州银行"));
        data.add(new IndexModel("河北银行"));
        data.add(new IndexModel("恒丰银行"));
        data.add(new IndexModel("恒生银行"));
        data.add(new IndexModel("华夏银行"));
        data.add(new IndexModel("吉林银行"));
        data.add(new IndexModel("江苏银行"));
        data.add(new IndexModel("建设银行"));
        data.add(new IndexModel("交通银行"));
        data.add(new IndexModel("兰州银行"));
        data.add(new IndexModel("民泰银行"));
        data.add(new IndexModel("民生银行"));
        data.add(new IndexModel("南京银行"));
        data.add(new IndexModel("内蒙古银行"));
        data.add(new IndexModel("宁波银行"));
        data.add(new IndexModel("宁夏银行"));
        data.add(new IndexModel("农商银行"));
        data.add(new IndexModel("农业银行"));
        data.add(new IndexModel("平安银行"));
        data.add(new IndexModel("浦东发展银行"));
        data.add(new IndexModel("齐鲁银行"));
        data.add(new IndexModel("青岛银行"));
        data.add(new IndexModel("青海银行"));
        data.add(new IndexModel("其他"));
        data.add(new IndexModel("上海农商银行"));
        data.add(new IndexModel("上海银行"));
        data.add(new IndexModel("天津银行"));
        data.add(new IndexModel("温州银行"));
        data.add(new IndexModel("兴业银行"));
        data.add(new IndexModel("邮政银行"));
        data.add(new IndexModel("浙商银行"));
        data.add(new IndexModel("中国银行"));
        data.add(new IndexModel("招商银行"));
        data.add(new IndexModel("中信银行"));
        PinyinComparator pinyinComparator = new PinyinComparator();
        CharacterParser characterParser = CharacterParser.getInstance();
        for (IndexModel indexModel : data) {
            indexModel.topc = characterParser.getSelling(indexModel.name).substring(0, 1).toUpperCase();
            if (indexModel.name.equals("重庆银行")) {
                indexModel.topc = "C";
            }
        }
        Collections.sort(data, pinyinComparator);
        return data;

    }


    public static List<IndexModel> getHeadBankData() {
        List<IndexModel> list = new ArrayList<>();
        list.add(new IndexModel("工商银行"));
        list.add(new IndexModel("建设银行"));
        list.add(new IndexModel("交通银行"));
        list.add(new IndexModel("农业银行"));
        list.add(new IndexModel("中国银行"));
        list.add(new IndexModel("招商银行"));
        list.add(new IndexModel("邮政储蓄"));
        list.add(new IndexModel("民生银行"));
        list.add(new IndexModel("兴业银行"));
        list.add(new IndexModel("中信银行"));
        list.add(new IndexModel("浦发银行"));
        list.add(new IndexModel("平安银行"));
        list.add(new IndexModel("广发银行"));
        list.add(new IndexModel("光大银行"));
        list.add(new IndexModel("其他"));
        return list;
    }


    public static void InsertAccountData() {
        DaoChoiceAccount.insertChoiceAccount(new ChoiceAccount(DaoChoiceAccount.getCount(), R.mipmap.icon_account_xianjin, "现金", 0.00, 0.00, "", "", R.color.xianjian, Config.CASH, 0.00, 0.00, DateTimeUtil.getCurrentTime_Today()));
        DaoChoiceAccount.insertChoiceAccount(new ChoiceAccount(DaoChoiceAccount.getCount(), R.mipmap.icon_account_yinhangka, "储蓄卡", 0.00, 0.00, "", "", R.color.chuxuka, Config.CXK, 0.00, 0.00, DateTimeUtil.getCurrentTime_Today()));
        DaoChoiceAccount.insertChoiceAccount(new ChoiceAccount(DaoChoiceAccount.getCount(), R.mipmap.icon_account_xinyongka, "信用卡", 0.00, 0.00, "", "", R.color.xinyongka, Config.XYK, 0.00, 0.00, DateTimeUtil.getCurrentTime_Today()));
        DaoChoiceAccount.insertChoiceAccount(new ChoiceAccount(DaoChoiceAccount.getCount(), R.mipmap.icon_account_zhifubao, "支付宝", 0.00, 0.00, "", "", R.color.zhifubao, Config.ZFB, 0.00, 0.00, DateTimeUtil.getCurrentTime_Today()));
        DaoChoiceAccount.insertChoiceAccount(new ChoiceAccount(DaoChoiceAccount.getCount(), R.mipmap.jiedai, "借出", 0.00, 0.00, "", "", R.color.jiechu, Config.JC, 0.00, 0.00, DateTimeUtil.getCurrentTime_Today()));
        DaoChoiceAccount.insertChoiceAccount(new ChoiceAccount(DaoChoiceAccount.getCount(), R.mipmap.jiedai, "借入", 0.00, 0.00, "", "", R.color.jieru, Config.JR, 0.00, 0.00, DateTimeUtil.getCurrentTime_Today()));
    }

    public static List<PlanSaveMoneyModel> getAddChoiceAccount() {
        List<PlanSaveMoneyModel> list = new ArrayList<>();
        list.add(new PlanSaveMoneyModel(R.mipmap.zongxiaofei, "现金", "", Config.CASH, R.color.xianjian, R.mipmap.icon_account_xianjin));
        list.add(new PlanSaveMoneyModel(R.mipmap.icon_add_1, "储蓄卡", "", Config.CXK, R.color.chuxuka, R.mipmap.icon_account_yinhangka));
        list.add(new PlanSaveMoneyModel(R.mipmap.huankuan, "信用卡", "(蚂蚁花呗,京东白条)", Config.XYK, R.color.xinyongka, R.mipmap.icon_account_xinyongka));
        list.add(new PlanSaveMoneyModel(R.mipmap.zhifubao, "支付宝", "", Config.ZFB, R.color.zhifubao, R.mipmap.icon_account_zhifubao));
        list.add(new PlanSaveMoneyModel(R.mipmap.config_share_weixin, "微信钱包", "", Config.WEIXIN, R.color.weixin, R.mipmap.icon_login_wechat));
        list.add(new PlanSaveMoneyModel(R.mipmap.fanka, "储值卡", "(饭卡,公交卡)", Config.CZK, R.color.chuzhiKa, R.mipmap.icon_account_chuzhika));
        list.add(new PlanSaveMoneyModel(R.mipmap.touzilicai, "投资理财", "", Config.TOUZI, R.color.touziLicai, R.mipmap.icon_account_gupiao));
        list.add(new PlanSaveMoneyModel(R.mipmap.lixishouru, "网络账户", "", Config.INTENTACCOUNT, R.color.intentAccount, R.mipmap.wangluozhanghu));
        list.add(new PlanSaveMoneyModel(R.mipmap.zhuanzhang, "借出", "(别人欠我的钱)", Config.JC, R.color.jiechu, R.mipmap.jiedai));
        list.add(new PlanSaveMoneyModel(R.mipmap.tuikuan, "借入", "(我欠别人钱)", Config.JR, R.color.jieru, R.mipmap.jiedai));
        return list;
    }


    public static void InsertShouRuData() {
        DaoShouRuModel.insertShouRu(new ShouRuModel(DaoShouRuModel.getCount(), R.mipmap.icon_shouru_type_gongzi, "工资"));
        DaoShouRuModel.insertShouRu(new ShouRuModel(DaoShouRuModel.getCount(), R.mipmap.icon_shouru_type_shenghuofei, "生活费"));
        DaoShouRuModel.insertShouRu(new ShouRuModel(DaoShouRuModel.getCount(), R.mipmap.icon_shouru_type_hongbao, "红包"));
        DaoShouRuModel.insertShouRu(new ShouRuModel(DaoShouRuModel.getCount(), R.mipmap.icon_shouru_type_linghuaqian, "零花钱"));
        DaoShouRuModel.insertShouRu(new ShouRuModel(DaoShouRuModel.getCount(), R.mipmap.icon_shouru_type_jianzhiwaikuai, "外快兼职"));
        DaoShouRuModel.insertShouRu(new ShouRuModel(DaoShouRuModel.getCount(), R.mipmap.icon_shouru_type_touzishouru, "投资收入"));
        DaoShouRuModel.insertShouRu(new ShouRuModel(DaoShouRuModel.getCount(), R.mipmap.icon_shouru_type_jiangjin, "奖金"));
        DaoShouRuModel.insertShouRu(new ShouRuModel(DaoShouRuModel.getCount(), R.mipmap.icon_zhichu_type_baoxiaozhang, "报销"));
        DaoShouRuModel.insertShouRu(new ShouRuModel(DaoShouRuModel.getCount(), R.mipmap.xianjin, "现金"));
        DaoShouRuModel.insertShouRu(new ShouRuModel(DaoShouRuModel.getCount(), R.mipmap.tuikuan, "退款"));
        DaoShouRuModel.insertShouRu(new ShouRuModel(DaoShouRuModel.getCount(), R.mipmap.zhifubao, "支付宝"));
        DaoShouRuModel.insertShouRu(new ShouRuModel(DaoShouRuModel.getCount(), R.mipmap.icon_shouru_type_qita, "其他"));
    }


    public static void InsertZHiCHuData() {
        DaoZhiChuModel.insertZhiChu(new ZhiChuModel(DaoZhiChuModel.getCount(), R.mipmap.icon_shouru_type_qita, "一般"));
        DaoZhiChuModel.insertZhiChu(new ZhiChuModel(DaoZhiChuModel.getCount(), R.mipmap.maicai, "买菜"));
        DaoZhiChuModel.insertZhiChu(new ZhiChuModel(DaoZhiChuModel.getCount(), R.mipmap.zaocan, "早餐"));
        DaoZhiChuModel.insertZhiChu(new ZhiChuModel(DaoZhiChuModel.getCount(), R.mipmap.zhongfan, "中饭"));
        DaoZhiChuModel.insertZhiChu(new ZhiChuModel(DaoZhiChuModel.getCount(), R.mipmap.wanfan, "晚饭"));
        DaoZhiChuModel.insertZhiChu(new ZhiChuModel(DaoZhiChuModel.getCount(), R.mipmap.xiaochi, "小吃"));
        DaoZhiChuModel.insertZhiChu(new ZhiChuModel(DaoZhiChuModel.getCount(), R.mipmap.wanggou, "网购"));
        DaoZhiChuModel.insertZhiChu(new ZhiChuModel(DaoZhiChuModel.getCount(), R.mipmap.naifen, "奶粉"));
        DaoZhiChuModel.insertZhiChu(new ZhiChuModel(DaoZhiChuModel.getCount(), R.mipmap.jiushui, "酒水"));
        DaoZhiChuModel.insertZhiChu(new ZhiChuModel(DaoZhiChuModel.getCount(), R.mipmap.lingshi, "零食"));
        DaoZhiChuModel.insertZhiChu(new ZhiChuModel(DaoZhiChuModel.getCount(), R.mipmap.richangyongpin, "生活品"));
        DaoZhiChuModel.insertZhiChu(new ZhiChuModel(DaoZhiChuModel.getCount(), R.mipmap.xiezi, "鞋子"));
        DaoZhiChuModel.insertZhiChu(new ZhiChuModel(DaoZhiChuModel.getCount(), R.mipmap.yaopinfei, "医药费"));
        DaoZhiChuModel.insertZhiChu(new ZhiChuModel(DaoZhiChuModel.getCount(), R.mipmap.yifu, "衣服"));
        DaoZhiChuModel.insertZhiChu(new ZhiChuModel(DaoZhiChuModel.getCount(), R.mipmap.icon_zhichu_type_taobao, "淘宝"));
        DaoZhiChuModel.insertZhiChu(new ZhiChuModel(DaoZhiChuModel.getCount(), R.mipmap.tingchefei, "停车"));
        DaoZhiChuModel.insertZhiChu(new ZhiChuModel(DaoZhiChuModel.getCount(), R.mipmap.majiang, "麻将"));
        DaoZhiChuModel.insertZhiChu(new ZhiChuModel(DaoZhiChuModel.getCount(), R.mipmap.dianying, "电影"));
    }


    public static List<IndexModel> getHuoBiData() {
        List<IndexModel> list = new ArrayList<>();
        list.add(new IndexModel("人民币", "CNY"));
        list.add(new IndexModel("美元", "USD"));
        list.add(new IndexModel("欧元", "EUR"));
        list.add(new IndexModel("日元", "JPY"));
        list.add(new IndexModel("英镑", "GBP"));
        list.add(new IndexModel("澳大利亚元", "AUD"));
        list.add(new IndexModel("加拿大元", "CAD"));
        list.add(new IndexModel("澳门元", "MOP"));
        list.add(new IndexModel("新西兰元", "NZD"));
        list.add(new IndexModel("泰铢", "THB"));
        list.add(new IndexModel("印度卢比", "INR"));
        list.add(new IndexModel("新加坡元", "SGD"));
        list.add(new IndexModel("韩元", "KRW"));
        list.add(new IndexModel("阿联酋迪拉姆", "AED"));
        list.add(new IndexModel("巴西雷亚尔", "BRL"));
        list.add(new IndexModel("瑞士法郎", "CHF"));
        list.add(new IndexModel("丹麦克朗", "DKK"));
        list.add(new IndexModel("埃及镑", "EGP"));
        list.add(new IndexModel("印度尼西亚盾", "IDR"));
        list.add(new IndexModel("柬埔寨瑞尔", "KHR"));
        list.add(new IndexModel("老挝基普", "LAk"));
        list.add(new IndexModel("斯里兰卡卢比", "LKR"));
        list.add(new IndexModel("马来西亚林吉特", "MYR"));
        list.add(new IndexModel("缅甸元", "MMK"));
        list.add(new IndexModel("马尔代夫卢非亚", "MVR"));
        list.add(new IndexModel("菲律宾比索", "PHP"));
        list.add(new IndexModel("卢布", "RUB"));
        list.add(new IndexModel("瑞典克朗", "SEK"));
        list.add(new IndexModel("越南盾", "YND"));
        list.add(new IndexModel("南非兰特", "ZAR"));
        list.add(new IndexModel("文莱元", "BND"));
        list.add(new IndexModel("巴基斯坦卢比", "PKR"));
        list.add(new IndexModel("乌克兰格里夫钠", "UAH"));
        list.add(new IndexModel("哥斯达黎家克朗", "CRC"));
        list.add(new IndexModel("保加利亚新列佛", "BGN"));
        list.add(new IndexModel("孟加拉国塔卡", "BDT"));
        list.add(new IndexModel("塔桑尼亚先令", "TZS"));
        list.add(new IndexModel("以色列谢克尔", "ILS"));
        return list;
    }


    public static List<IndexModel> getP2PData() {
        List<IndexModel> data = new ArrayList<>();
        data.add(new IndexModel("余额宝"));
        data.add(new IndexModel("娱乐宝"));
        data.add(new IndexModel("爱钱进"));
        data.add(new IndexModel("百度财富"));
        data.add(new IndexModel("草根投资"));
        data.add(new IndexModel("点融网"));
        data.add(new IndexModel("钱宝网"));
        data.add(new IndexModel("红岭创投"));
        data.add(new IndexModel("桔子理财"));
        data.add(new IndexModel("积木盒子"));
        data.add(new IndexModel("口袋财富"));
        data.add(new IndexModel("懒投资"));
        data.add(new IndexModel("陆金所"));
        data.add(new IndexModel("你我贷"));
        data.add(new IndexModel("PPMoney"));
        data.add(new IndexModel("钱爸爸"));
        data.add(new IndexModel("人人贷"));
        data.add(new IndexModel("铜板街"));
        data.add(new IndexModel("投米网"));
        data.add(new IndexModel("投那网"));
        data.add(new IndexModel("温州贷"));
        data.add(new IndexModel("小金理财"));
        data.add(new IndexModel("鑫合汇"));
        data.add(new IndexModel("翼龙贷"));
        data.add(new IndexModel("拍拍贷"));
        data.add(new IndexModel("盈盈理财"));
        data.add(new IndexModel("宜人贷"));
        data.add(new IndexModel("有利网"));
        data.add(new IndexModel("其他"));
        PinyinComparator pinyinComparator = new PinyinComparator();
        CharacterParser characterParser = CharacterParser.getInstance();
        for (IndexModel indexModel : data) {
            indexModel.topc = characterParser.getSelling(indexModel.name).substring(0, 1).toUpperCase();
            if (indexModel.name.equals("草根投资")) {
                indexModel.topc = "C";
            }
        }
        Collections.sort(data, pinyinComparator);
        return data;

    }

    public static List<IndexModel> getBankData() {
        List<IndexModel> data = new ArrayList<>();
        data.add(new IndexModel("北京银行"));
        data.add(new IndexModel("工商银行"));
        data.add(new IndexModel("光大银行"));
        data.add(new IndexModel("广发银行"));
        data.add(new IndexModel("杭州银行"));
        data.add(new IndexModel("华夏银行"));
        data.add(new IndexModel("建设银行"));
        data.add(new IndexModel("交通银行"));
        data.add(new IndexModel("民生银行"));
        data.add(new IndexModel("南京银行"));
        data.add(new IndexModel("农商银行"));
        data.add(new IndexModel("农业银行"));
        data.add(new IndexModel("平安银行"));
        data.add(new IndexModel("浦发银行"));
        data.add(new IndexModel("上海银行"));
        data.add(new IndexModel("兴业银行"));
        data.add(new IndexModel("招商银行"));
        data.add(new IndexModel("中国人民银行"));
        data.add(new IndexModel("中国银行"));
        data.add(new IndexModel("中国邮政储蓄银行"));
        data.add(new IndexModel("中信银行"));
        data.add(new IndexModel("央行国债"));
        data.add(new IndexModel("其他"));
        PinyinComparator pinyinComparator = new PinyinComparator();
        CharacterParser characterParser = CharacterParser.getInstance();
        for (IndexModel indexModel : data) {
            indexModel.topc = characterParser.getSelling(indexModel.name).substring(0, 1).toUpperCase();
        }
        Collections.sort(data, pinyinComparator);
        return data;
    }


    public static List<String> getDateDate() {
        List<String> list = new ArrayList<>();
        list.add("1日");
        list.add("2日");
        list.add("3日");
        list.add("4日");
        list.add("5日");
        list.add("6日");
        list.add("7日");
        list.add("8日");
        list.add("9日");
        list.add("10日");
        list.add("11日");
        list.add("12日");
        list.add("13日");
        list.add("14日");
        list.add("15日");
        list.add("16日");
        list.add("17日");
        list.add("18日");
        list.add("19日");
        list.add("20日");
        list.add("21日");
        list.add("22日");
        list.add("23日");
        list.add("24日");
        list.add("25日");
        list.add("26日");
        list.add("27日");
        list.add("28日");
        list.add("月末");
        return list;
    }

    public static List<AccountWealthModel> getWealthData() {
        List<AccountWealthModel> list = new ArrayList<>();
        list.add(new AccountWealthModel("新手专享", "口袋富宝新手专享(银行定期收益6.7倍)", 10.00, "30天", "爆", "年化收益率", Config.DINGQI, 57));
        list.add(new AccountWealthModel("优选活期", "口袋活期宝(随存随取,按日计息)", 4.00, "活期", "新", "7日年化收益率", Config.HUOQI, 57));
        list.add(new AccountWealthModel("精选定期", "口袋富宝1号(银行定期收益4.1倍)", 6.20, "45天", "新", "年化收益率", Config.DINGQI, 52));
        list.add(new AccountWealthModel("精选定期", "口袋富宝2号(银行定期收益4.3倍)", 6.40, "90天", "热", "年化收益率", Config.DINGQI, 76));
        list.add(new AccountWealthModel("精选定期", "口袋富宝3号(银行定期收益4.7倍)", 7.00, "180天", "热", "年化收益率", Config.DINGQI, 53));
        list.add(new AccountWealthModel("精选定期", "口袋富宝女性专享(银行定期收益5.3倍)", 7.00, "180天", "爆", "年化收益率", Config.DINGQI, 59));
        list.add(new AccountWealthModel("精选定期", "口袋富宝5号(银行定期收益6.3倍)", 9.50, "365天", "热", "年化收益率", Config.DINGQI, 41));
        return list;

    }


    public static List<String> getBannerDataUrl() {
        List<String> list = new ArrayList<>();
        list.add("http://img2.3lian.com/2014/c7/25/d/40.jpg");
        list.add("http://img2.3lian.com/2014/c7/25/d/41.jpg");
        list.add("http://imgsrc.baidu.com/forum/pic/item/b64543a98226cffc8872e00cb9014a90f603ea30.jpg");
        list.add("http://imgsrc.baidu.com/forum/pic/item/261bee0a19d8bc3e6db92913828ba61eaad345d4.jpg");
        return list;
    }

    public static List<String> getBannerDataTips() {
        List<String> list = new ArrayList<>();
        list.add("标题1");
        list.add("标题2");
        list.add("标题3");
        list.add("标题4");
        return list;
    }

    public static List<ColorModel> getColorData() {
        List<ColorModel> list = new ArrayList<>();
        list.add(new ColorModel(R.color.xianjian));
        list.add(new ColorModel(R.color.wealth_color));
        list.add(new ColorModel(R.color.zaocan));
        list.add(new ColorModel(R.color.weixin));
        list.add(new ColorModel(R.color.zhongfan));
        list.add(new ColorModel(R.color.zhifubao));
        list.add(new ColorModel(R.color.chuzhiKa));
        list.add(new ColorModel(R.color.chuxuka));
        list.add(new ColorModel(R.color.weixin));
        list.add(new ColorModel(R.color.xinyongka));
        list.add(new ColorModel(R.color.maicai));
        list.add(new ColorModel(R.color.majiang));
        list.add(new ColorModel(R.color.baoxiao));
        list.add(new ColorModel(R.color.tuikuan));
        list.add(new ColorModel(R.color.jiechu));
        list.add(new ColorModel(R.color.jieru));
        list.add(new ColorModel(R.color.feijipiao));
        list.add(new ColorModel(R.color.jiushui));
        list.add(new ColorModel(R.color.tingche));
        list.add(new ColorModel(R.color.gongzi));
        list.add(new ColorModel(R.color.touzi));
        return list;


    }

}
