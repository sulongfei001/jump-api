package com.sulongfei.jump.mapper;

import com.sulongfei.jump.model.Ticket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TicketMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Ticket record);

    int insertSelective(Ticket record);

    Ticket selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Ticket record);

    int updateByPrimaryKey(Ticket record);

    Ticket selectByClubId(@Param("userId") Long userId,@Param("orgId") Long orgId);
}