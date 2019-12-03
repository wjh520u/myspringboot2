package com.boot.api.service.dao.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.api.dao.entity.OrmUser;
import com.boot.api.dao.mapper.OrmUserMapper;
import com.boot.api.service.dao.IEntUserService;

/**
 * <p>
 * �û�ע����ҵʱ�����˻��������˺ŵ��û���һ������ҵƴ����
������˺��ǣ���ʽ�� ���˺�:���˺����¼�Ǳ� 服务实现类
 * </p>
 *
 * @author 王俊辉
 * @since 2019-05-29
 */
@Service
public class EntUserServiceImpl extends ServiceImpl<OrmUserMapper, OrmUser> implements IEntUserService {

}
