package com.sulongfei.jump.mapper;

import com.sulongfei.jump.model.GlobalDictionary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface GlobalDictionaryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GlobalDictionary record);

    int insertSelective(GlobalDictionary record);

    GlobalDictionary selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GlobalDictionary record);

    int updateByPrimaryKey(GlobalDictionary record);

    GlobalDictionary selectByKey(@Param("key") String sms_code_expire);
}