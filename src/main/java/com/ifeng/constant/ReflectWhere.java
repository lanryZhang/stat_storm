package com.ifeng.constant;

import com.ifeng.entities.*;
import com.ifeng.core.query.Where;
import com.ifeng.entities.vdn.VdnEntity;

/**
 * Created by zhanglr on 2016/4/6.
 */
public class  ReflectWhere {
    public static Where toWhere(IpsEntity en){
        Where where = new Where();
        where.and("hostIp", en.getHostIp());
        where.and("nodeIp",en.getNodeIp());
        where.and("clientType",en.getClientType());
        where.and("createDate",en.getCreateDate());
        where.and("hm",en.getHm());
        where.and("requestType",en.getRequestType());
        return where;
    }

    public static Where toWhere(VdnEntity en){
        Where where = new Where();
        if(en.getTm()!="")where.and("tm", en.getTm());
        if(en.getFrm()!="")where.and("frm",en.getFrm());
        if(en.getVideotype()!="")where.and("videotype",en.getVideotype());
        if(en.getStatistype()!="")where.and("statistype",en.getStatistype());
        if(en.getCity()!="")where.and("city",en.getCity());
        if(en.getNetName()!="")where.and("netName",en.getNetName());
        if(en.getChentype()!="")where.and("chentype",en.getChentype());
        if(en.getDateTime()!="")where.and("dateTime",en.getDateTime());
        if(en.getTr()!="")where.and("tr",en.getTr());
        return where;
    }

    public static Where toWhere(BandWidthEntity en){
        Where where = new Where();
        where.and("hostIp", en.getHostIp());
        where.and("localTime",en.getLocalTime());
        return where;
    }

    public static Where toWhereNetName(BandWidthEntity en){
        Where where = new Where();
        where.and("localTime",en.getLocalTime());
        where.and("netName",en.getNetName());
        return where;
    }

    public static Where toWhereNetNameAndType(BandWidthEntity en){
        Where where = new Where();
        where.and("netName", en.getNetName());
        where.and("type",en.getType());
        where.and("localTime",en.getLocalTime());
        return where;
    }

    public static Where toWhere(PeerLogEntity en){
        Where where = new Where();
        where.and("netName",en.getNetName());
        where.and("groups",en.getGroups());
        where.and("requestType",en.getRequestType());
        where.and("date",en.getDate());
        where.and("time",en.getTime());
        return where;
    }
    public static Where toWhere(P2PSingleEntity en){
        Where where = new Where();
        where.and("dateTime",en.getDateTime());
        where.and("time",en.getTime());
        return where;
    }

    public static Where toWhereSingle(P2PSingleEntity en){
        Where where = new Where();
        where.and("time",en.getTime());
        where.and("dateTime",en.getDateTime());
        where.and("guid",en.getGuid());
        return where;
    }

    public static Where toWhere(P2PServerDurationEntity en){
        Where where = new Where();
        where.and("date",en.getDate());
        where.and("duration",en.getDuration());
        return where;
    }

    public static Where toWhere(P2PServerProgramTopEntity en){
        Where where = new Where();
        where.and("url",en.getUrl());
        where.and("netName",en.getNetName());
        return where;
    }
    public static Where toWhere(P2PNatEntity en){
        Where where = new Where();
        where.and("time",en.getTime());
        where.and("dateTime",en.getDateTime());
        return where;
    }
    public static Where toWhereNat(P2PNatEntity en){
        Where where = new Where();
        where.and("time",en.getTime());
        where.and("dateTime",en.getDateTime());
        where.and("netName",en.getNetName());
        return where;
    }
}
