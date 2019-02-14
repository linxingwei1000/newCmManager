package cm.lx.business;

import cm.lx.bean.entity.CarDossier;
import cm.lx.bean.entity.CarRecord;
import cm.lx.common.ContextType;
import cm.lx.service.CarDossierService;
import cm.lx.service.CarRecordService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linxingwei
 * @date 2019/2/14
 */
@Service
public class TaskCenter {
    @Resource
    CarRecordService carRecordService;

    @Resource
    CarDossierService carDossierService;

    /**
     * 定时刷新档案数据，每5分钟刷新一次
     */
    @Scheduled(cron = "0 */5 * * * ? ")
    public void taskCycle() {
        List<CarDossier> list = carDossierService.getCarDossierListByDisplayStatus(null);
        for (CarDossier carDossier : list) {
            CarRecord carRecord = carRecordService.getCarRecordById(carDossier.getCarRecordId());
            //判断车辆销售状态
            if (carRecord.getRecordStatus().equals(ContextType.RECORD_STATUS_SOLD)) {
                if (!carDossier.getDisplayStatus().equals(ContextType.DISPLAY_STATUS_HIDE)) {
                    CarDossier update = new CarDossier();
                    update.setId(carDossier.getId());
                    update.setDisplayStatus(ContextType.DISPLAY_STATUS_HIDE);
                    carDossierService.updateCarDossierById(update);
                }
            } else {
                if (carDossier.getDisplayStatus().equals(ContextType.DISPLAY_STATUS_HIDE)) {
                    CarDossier update = new CarDossier();
                    update.setId(carDossier.getId());
                    update.setDisplayStatus(ContextType.DISPLAY_STATUS_SHOW);
                    carDossierService.updateCarDossierById(update);
                }
            }
        }
    }
}
