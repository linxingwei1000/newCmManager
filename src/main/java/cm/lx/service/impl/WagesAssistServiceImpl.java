package cm.lx.service.impl;

import cm.lx.dao.WagesAssistMapper;
import cm.lx.bean.entity.WagesAssist;
import cm.lx.service.WagesAssistService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
@Service
public class WagesAssistServiceImpl implements WagesAssistService {

    @Resource
    WagesAssistMapper wagesAssistMapper;


    @Override
    public Integer createWagesAssist(WagesAssist wagesAssist) {
        wagesAssist.setUtime(System.currentTimeMillis());
        wagesAssist.setCtime(System.currentTimeMillis());
        return wagesAssistMapper.insert(wagesAssist);
    }

    @Override
    public Integer updateWagesAssist(WagesAssist wagesAssist) {
        wagesAssist.setUtime(System.currentTimeMillis());
        return wagesAssistMapper.updateById(wagesAssist);
    }

    @Override
    public Integer deleteWagesAssistByCid(Integer cid) {
        QueryWrapper<WagesAssist> query = new QueryWrapper<>();
        query.eq("car_record_id", cid);
        return wagesAssistMapper.delete(query);
    }

    @Override
    public WagesAssist getWagesAssistById(Integer id) {
        return wagesAssistMapper.selectById(id);
    }

    @Override
    public WagesAssist getWagesAssistByCid(Integer cid) {
        QueryWrapper<WagesAssist> query = new QueryWrapper<>();
        query.eq("car_record_id", cid);
        return wagesAssistMapper.selectOne(query);
    }

    @Override
    public List<WagesAssist> getWagesAssistByTime(Long st, Long et, Long soldt) {
        QueryWrapper<WagesAssist> query = new QueryWrapper<>();
        query.between("sale_date", st, et).ge("sold_date", soldt);
        return wagesAssistMapper.selectList(query);
    }
}
