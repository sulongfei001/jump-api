package com.sulongfei.jump.mapper;

import com.sulongfei.jump.model.RoomSimple;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RoomSimpleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RoomSimple record);

    int insertSelective(RoomSimple record);

    RoomSimple selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RoomSimple record);

    int updateByPrimaryKey(RoomSimple record);

    List<RoomSimple> selectRoomSimple(@Param("remoteClubId") Long remoteClubId);
}