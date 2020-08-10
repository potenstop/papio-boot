package top.potens.core.service.impl;

import top.potens.core.enums.CommonExceptionCodeEnums;
import top.potens.core.exception.ApiException;
import top.potens.core.model.TokenUser;
import top.potens.core.serialization.JSON;
import top.potens.core.service.TableCommonService;
import top.potens.core.util.ReflectionsUtil;
import top.potens.log.AppLogger;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className AbstractSimpleTableCommonServiceImpl
 * @projectName web-api
 * @date 2019/12/10 19:55
 */
public abstract class AbstractSimpleTableCommonServiceImpl<Model, PrimaryKey> implements TableCommonService<Model, PrimaryKey> {
    /**
    *
    * 方法功能描述: 根据id查询
    *
    * @author yanshaowen
    * @date 2019/12/10 20:09
    * @param id
    * @return
    * @throws
    */
    protected abstract Model mapperByPrimaryKey(PrimaryKey id);
    /**
    *
    * 方法功能描述: 根据id查询
    *
    * @author yanshaowen
    * @date 2019/12/10 20:09
    * @param id
    * @return
    * @throws
    */
    protected abstract Model mapperBySecondPrimaryKey(PrimaryKey id);

    /**
    *
    * 方法功能描述: 判断记录是否为删除  true: 删除 false: 未删除
    *
    * @author yanshaowen
    * @date 2019/12/10 20:13
    * @param model
    * @return
    * @throws
    */
    protected abstract Boolean isDelete(Model model);

    @Override
    public Model byPrimaryKey(PrimaryKey id) {
        if (id == null) {
            return null;
        }
        Model model = mapperByPrimaryKey(id);
        Boolean delete = isDelete(model);
        if (delete) {
            return null;
        }
        return model;
    }

    @Override
    public Model byPrimaryKeyException(PrimaryKey id) {
        if (id == null) {
            return null;
        }
        Model model = mapperByPrimaryKey(id);
        if (model == null) {
            throw new ApiException(CommonExceptionCodeEnums.RECODE_NOT_FOUND);
        }
        Boolean delete = isDelete(model);
        if (delete) {
            throw new ApiException(CommonExceptionCodeEnums.RECODE_IS_DELETE);
        }
        return model;
    }

    @Override
    public Model bySecondPrimaryKey(PrimaryKey id) {
        if (id == null) {
            return null;
        }
        Model model = mapperBySecondPrimaryKey(id);
        Boolean delete = isDelete(model);
        if (delete) {
            return null;
        }
        return model;
    }

    @Override
    public Model bySecondPrimaryKeyException(PrimaryKey id){
        if (id == null) {
            return null;
        }
        Model model = mapperBySecondPrimaryKey(id);
        if (model == null) {
            throw new ApiException(CommonExceptionCodeEnums.RECODE_NOT_FOUND);
        }
        Boolean delete = isDelete(model);
        if (delete) {
            throw new ApiException(CommonExceptionCodeEnums.RECODE_IS_DELETE);
        }
        return model;
    }

    @Override
    public void setDefaultModelValue(Object obj) {
        try {
            Date now = new Date();
            ReflectionsUtil.setFieldValue(obj, "createTime", now);
            ReflectionsUtil.setFieldValue(obj, "updateTime", now);
            ReflectionsUtil.setFieldValue(obj, "isDelete", 1);
        } catch (Exception e) {
            AppLogger.warn("设置默认属性失败 message:[{}]", e, e.getMessage());
            throw new ApiException("500", "设置默认属性失败" + e.getMessage());
        }
    }
}
