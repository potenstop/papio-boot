package top.potens.core.service.impl;

import top.potens.core.enums.CommonExceptionCodeEnums;
import top.potens.core.exception.ApiException;
import top.potens.core.service.TableCommonService;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className AbstractSimpleTableCommonServiceImpl
 * @projectName web-api
 * @date 2019/12/10 19:55
 */
public abstract class AbstractSimpleTableCommonServiceImpl<Model> implements TableCommonService<Model> {
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
    protected abstract Model mapperByPrimaryKey(Integer id);
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
    protected abstract Model mapperBySecondPrimaryKey(Integer id);

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
    public Model byPrimaryKey(Integer id) {
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
    public Model byPrimaryKeyException(Integer id) {
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
    public Model bySecondPrimaryKey(Integer id) {
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
    public Model bySecondPrimaryKeyException(Integer id){
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
}
