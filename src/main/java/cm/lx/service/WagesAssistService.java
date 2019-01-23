package cm.lx.service;

import cm.lx.bean.entity.WagesAssist;

import java.util.List;

/**
 * @author linxingwei
 * @date 2019/1/21
 */
public interface WagesAssistService {

    Integer createWagesAssist(WagesAssist wagesAssist);

    Integer updateWagesAssist(WagesAssist wagesAssist);

    Integer deleteWagesAssistByCid(Integer cid);

    WagesAssist getWagesAssistById(Integer id);

    WagesAssist getWagesAssistByCid(Integer cid);

    List<WagesAssist> getWagesAssistByTime(Long st, Long et, Long soldt);

}
