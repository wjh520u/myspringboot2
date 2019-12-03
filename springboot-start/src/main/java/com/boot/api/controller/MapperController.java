package com.boot.api.controller;

import com.boot.api.dao.entity.OrmUser;
import com.boot.api.dao.mapper.OrmUserMapper;
import com.boot.config.web.response.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
public class MapperController {

    @Autowired
    OrmUserMapper ormUserMapper;

    @RequestMapping( "testm" )
    public R test(@NotBlank String id ) {
        //List<OrmUser> id1 = ormUserMapper.selectList(Wrappers.<OrmUser>lambdaQuery().eq(OrmUser::getId, id));
        //List<String> tables = ormUserMapper.getTables("444");
        List<OrmUser> id1 = ormUserMapper.getUser(id);
        return R.success(id1);
    }

}
