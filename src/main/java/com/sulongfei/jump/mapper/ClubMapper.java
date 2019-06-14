package com.sulongfei.jump.mapper;

import com.sulongfei.jump.model.Club;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ClubMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Club record);

    int insertSelective(Club record);

    Club selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Club record);

    int updateByPrimaryKey(Club record);

    Club selectByOrgId(@Param("orgId") Long orgId);
}