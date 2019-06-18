package com.sulongfei.jump.context;

import com.sulongfei.jump.mapper.GlobalDictionaryMapper;
import com.sulongfei.jump.model.GlobalDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class GlobalContext {

    @Autowired
    private GlobalDictionaryMapper dictionaryMapper;

    public Integer getSmsCodeExpire() {
        GlobalDictionary model = dictionaryMapper.selectByKey("sms_code_expire");
        return (model != null && model.getValue() != null) ? Integer.valueOf(model.getValue()) : 0;
    }

    public String getResetRankCron() {
        GlobalDictionary model = dictionaryMapper.selectByKey("reset_rank_cron");
        return (model != null && model.getValue() != null) ? model.getValue() : "";
    }

    public Integer getEntryIntegral() {
        GlobalDictionary model = dictionaryMapper.selectByKey("game_entry_integral");
        return (model != null && model.getValue() != null) ? Integer.valueOf(model.getValue()) : 0;
    }

    public Integer getEntryNum() {
        GlobalDictionary model = dictionaryMapper.selectByKey("game_entry_num");
        return (model != null && model.getValue() != null) ? Integer.valueOf(model.getValue()) : 0;
    }

    public BigDecimal getTicketSinglePrice() {
        GlobalDictionary model = dictionaryMapper.selectByKey("ticket_single_price");
        return (model != null && model.getValue() != null) ? new BigDecimal(model.getValue()) : new BigDecimal(0);
    }

    public Integer getEverydayTicketNum() {
        GlobalDictionary model = dictionaryMapper.selectByKey("everyday_ticket_num");
        return (model != null && model.getValue() != null) ? Integer.valueOf(model.getValue()) : 0;
    }

    private String getResetDayTicketCron() {
        GlobalDictionary model = dictionaryMapper.selectByKey("reset_day_ticket_cron");
        return (model != null && model.getValue() != null) ? model.getValue() : "";
    }

    public String getDefaultAvatar() {
        GlobalDictionary model = dictionaryMapper.selectByKey("default_avatar");
        return (model != null && model.getValue() != null) ? model.getValue() : "";
    }

}
