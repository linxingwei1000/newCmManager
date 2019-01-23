package cm.lx.dao;

import cm.lx.bean.entity.CarSf;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
@Repository
public interface CarSfMapper extends BaseMapper<CarSf> {

    /**
     * 获取总服务基金
     * @return
     */
    Double getServiceMoney();
}
