package com.boot.api.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.api.dao.entity.OrmUser;

/**
 * <p>
 * �û�ע����ҵʱ�����˻��������˺ŵ��û���һ������ҵƴ����
������˺��ǣ���ʽ�� ���˺�:���˺����¼�Ǳ� Mapper 接口
 * </p>
 *
 * @author 王俊辉
 * @since 2019-05-29
 */
public interface OrmUserMapper extends BaseMapper<OrmUser> {

	@Select("show tables")
	List<String> getTables(String str);

	List<OrmUser> getUser(String id);
}
