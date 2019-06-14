package com.sulongfei.jump.mapper;

import com.sulongfei.jump.model.RoomSpread;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RoomSpreadMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RoomSpread record);

    int insertSelective(RoomSpread record);

    RoomSpread selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoomSpread record);

    int updateByPrimaryKey(RoomSpread record);

    List<RoomSpread> selectAll(@Param("remoteClubId") Long remoteClubId, @Param("userId") Long userId);

    Long checkPassword(@Param("password") String password);

    RoomSpread selectByPassword(@Param("password") String password);
}