package top.potens.core.service;

/**
 * 功能描述:
 *
 * @author yanshaowen
 * @className TableCommonService
 * @projectName web-api
 * @date 2019/12/10 17:47
 */
public interface TableCommonService<Model> {
    /**
    *
    * 方法功能描述: 按表的主键查询一条记录 不返回异常 不存在返回null
    *
    * @author yanshaowen
    * @date 2019/12/10 17:49
    * @param id 主键id
    * @return
    * @throws
    */
    Model byPrimaryKey(Integer id);

    /**
     *
     * 方法功能描述: 按表的主键查询一条记录 不存在返回异常
     *
     * @author yanshaowen
     * @date 2019/12/10 17:49
     * @param id 主键id
     * @return
     * @throws
     */
    Model byPrimaryKeyException(Integer id);

    /**
     *
     * 方法功能描述: 按第二业务主键查询
     *
     * @author yanshaowen
     * @date 2019/12/10 17:49
     * @param id 主键id
     * @return
     * @throws
     */
    Model bySecondPrimaryKey(Integer id);

    /**
     *
     * 方法功能描述: 按第二业务主键查询
     *
     * @author yanshaowen
     * @date 2019/12/10 17:49
     * @param id 主键id
     * @return
     * @throws
     */
    Model bySecondPrimaryKeyException(Integer id);
}
