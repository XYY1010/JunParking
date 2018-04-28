package com.example.xyy.dbhelper;

/**
 * Created by XYY on 2018/4/28.
 */

public class DBHelper {

    public DBHelper(){}

    public void createDB(){
        int[] airportId = {1001, 1002, 1003, 1014, 1015, 1017, 1016, 1017,1025, 1035, 1044, 1045,
                1046, 1054, 1055, 1056, 1057, 1058, 1065, 1066, 1077, 1084, 1085, 1086, 1087, 1094,
                1095, 1096, 1097, 2001, 2011, 2012, 2013, 2014, 2015, 2016, 2021, 2022, 2023, 2033,
                2034, 2035, 2036, 2041, 2042, 2043, 2051, 2052, 2053, 2054, 2055, 2056, 2061, 2062, 2063,
                2064};
        String[] airportName = {"包头二里半", "北京南苑", "北京首都", "常州奔牛", "长春龙嘉", "重庆江北",
                "长沙黄花","成都双流", "大连周水子", "福州长乐", "桂林两江", "贵阳龙洞堡", "广州白云",
                "呼和浩特白塔", "海口美兰", "合肥新桥", "哈尔滨太平", "杭州萧山", "揭阳潮汕", "济南遥墙",
                "昆明长水", "兰州中川", "拉萨贡嘎", "丽江三义", "临沂沭埠岭", "宁波栎社", "南宁吴圩", "南昌昌北",
                "南京禄口", "青岛流亭", "石家庄正定", "上海迪士尼", "沈阳仙桃", "深圳宝安", "上海虹桥", "上海浦东",
                "台州路桥", "天津滨海", "太原武宿", "温州龙湾", "乌鲁木齐地窝堡", "无锡硕放", "武汉天河", "西宁曹家堡",
                "厦门高崎", "西安咸阳", "银川河东", "义乌机场", "烟台蓬莱", "宜昌三峡", "延吉机场","扬州泰州机场", "珠海金湾",
                "遵义新舟", "张家界荷花", "郑州新郑"};
        for (int i = 0; i<airportId.length; i++) {
            Airport airports = new Airport();
            airports.setAirportId(airportId[i]);
            airports.setAirportName(airportName[i]);
            airports.save();
        }

        int[] stationId = {1001, 1011, 1021, 1031, 1032, 1033, 1034, 1035, 1036, 1037, 1043, 1051, 1061,
                1071, 1072, 1073, 1081, 1082, 1091, 1092, 1093, 2001, 2002, 2003, 2004, 2011, 2012, 2013,
                2014, 2015, 2016, 2025, 2026};
        String[] stationName = {"北京南站", "长春西站", "广州南站", "海口站", "汉口站", "合肥南站", "哈尔滨西站",
                "衡阳东站", "杭州东站", "杭州城站", "济南西站", "昆明站", "娄底南站", "宁波站", "南昌西站",
                "南京南站", "深圳北站", "上海虹桥站", "太原南站", "天津南站", "天津站", "无锡新区站", "武汉站",
                "厦门北站", "西宁站", "烟台站", "义乌站", "永康南站", "营口东站", "宜兴站", "宜昌东站", "珠海高铁站",
                "珠海拱北口岸"
        };
        for (int i = 0; i<stationId.length; i++) {
            HighSpeedRialStation station = new HighSpeedRialStation();
            station.setStationId(stationId[i]);
            station.setStationName(stationName[i]);
            station.save();
        }

        ParkingLot parkingLot1 = new ParkingLot();
        parkingLot1.setParkingLotId(10001);
        parkingLot1.setParkingLotName("北京机场新星亮点停车场");
        parkingLot1.setImageSrc(1);
        parkingLot1.setRatingScore(5);
        parkingLot1.setSales(119);
        parkingLot1.setIsInRoom("室外");
        parkingLot1.setDistance("距离机场直线距离4.2公里");
        parkingLot1.setRemarks("温馨提醒：六环以外外地牌照车辆过来停车无需办理进京证。");
        parkingLot1.setAvgPrice(20);
        parkingLot1.setCityName("北京首都");
        parkingLot1.setService1(true);
        parkingLot1.setService2(true);
        parkingLot1.setService3(true);
        parkingLot1.setService4(true);
        parkingLot1.setDiscount("预付20元");
        parkingLot1.save();

        ParkingLot parkingLot2 = new ParkingLot();
        parkingLot2.setParkingLotId(10002);
        parkingLot2.setParkingLotName("首都泊安飞正元停车场");
        parkingLot2.setImageSrc(2);
        parkingLot2.setRatingScore(5);
        parkingLot2.setSales(221);
        parkingLot2.setIsInRoom("室外");
        parkingLot2.setDistance("距离机场T3直线距离2.5公里");
        parkingLot2.setRemarks("新场开业，室内车位，特价促销，距航站楼最近的大型地下停车场。");
        parkingLot2.setAvgPrice(20);
        parkingLot2.setCityName("北京首都");
        parkingLot2.setService1(true);
        parkingLot2.setService2(true);
        parkingLot2.setService3(false);
        parkingLot2.setService4(true);
        parkingLot2.setDiscount("预付20元");
        parkingLot2.save();

        ParkingLot parkingLot3 = new ParkingLot();
        parkingLot3.setParkingLotId(10003);
        parkingLot3.setParkingLotName("萧山航杰停车场");
        parkingLot3.setImageSrc(3);
        parkingLot3.setRatingScore(4.8);
        parkingLot3.setSales(1890);
        parkingLot3.setIsInRoom("室内/室外");
        parkingLot3.setDistance("距机场直线4公里");
        parkingLot3.setRemarks("不含政府收取的过路费(国家法定节假日免收)；室外车位带遮阳棚。");
        parkingLot3.setAvgPrice(15);
        parkingLot3.setCityName("杭州萧山");
        parkingLot3.setService1(false);
        parkingLot3.setService2(true);
        parkingLot3.setService3(false);
        parkingLot3.setService4(true);
        parkingLot3.setDiscount("预付20元抵30元");
        parkingLot3.save();

        ParkingLot parkingLot4 = new ParkingLot();
        parkingLot4.setParkingLotId(10004);
        parkingLot4.setParkingLotName("首都庞大叮叮泊车");
        parkingLot4.setImageSrc(4);
        parkingLot4.setRatingScore(4.8);
        parkingLot4.setSales(346);
        parkingLot4.setIsInRoom("室内");
        parkingLot4.setDistance("距离机场直线5公里");
        parkingLot4.setRemarks("只有5座车接送（多辆），大型室内停车场，防雨，防晒，防剐蹭，去机场我最近。");
        parkingLot4.setAvgPrice(35);
        parkingLot4.setCityName("北京首都");
        parkingLot4.setService1(true);
        parkingLot4.setService2(false);
        parkingLot4.setService3(true);
        parkingLot4.setService4(true);
        parkingLot4.setDiscount("预付20元");
        parkingLot4.save();

    }
}
