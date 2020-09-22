package org.hzero.iam.infra.repository.impl;

import io.choerodon.core.domain.Page;
import io.choerodon.mybatis.pagehelper.PageHelper;
import io.choerodon.mybatis.pagehelper.domain.PageRequest;
import org.apache.commons.lang3.StringUtils;
import org.hzero.iam.domain.entity.Label;
import org.hzero.iam.domain.repository.LabelRepository;
import org.hzero.iam.infra.mapper.LabelMapper;
import org.hzero.mybatis.base.impl.BaseRepositoryImpl;
import org.hzero.mybatis.domian.Condition;
import org.hzero.mybatis.util.Sqls;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 资源库实现
 *
 * @author xiaoyu.zhao@hand-china.com 2020-02-25 14:22:16
 */
@Component
public class LabelRepositoryImpl extends BaseRepositoryImpl<Label> implements LabelRepository {

    @Resource
    private LabelMapper labelMapper;

    @Override
    public Page<Label> pageLabelList(PageRequest pageRequest, Label label) {
        return PageHelper.doPageAndSort(pageRequest, () -> labelMapper.pageLabelList(label));
    }

    @Override
    public Boolean validLabelUnique(Label label) {
        // 唯一性约束：name + type + fdLevel
        int count = selectCountByCondition(Condition.builder(Label.class)
                .andWhere(Sqls.custom()
                        .andEqualTo(Label.FIELD_NAME, label.getName())
                        .andEqualTo(Label.FIELD_TYPE, label.getType())
                        .andEqualTo(Label.FIELD_FD_LEVEL, label.getFdLevel())
                )
                .build());
        return count <= 0;
    }

    @Override
    public List<Label> getLabelListByType(String type) {
        if (StringUtils.isBlank(type)) {
            return Collections.emptyList();
        }

        return labelMapper.getLabelListByType(type);
    }

    @Override
    public List<Label> getLabelListByTypeAndNames(String type, String[] labelName) {
        return labelMapper.getLabelListByTypeAndNames(type, labelName);
    }
}
