package cm.lx.business;

import cm.lx.bean.*;
import cm.lx.dao.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DaoCenter {

    @Resource
    AccountDao accountDao;

    @Resource
    CarPropertyDao carPropertyDao;

    @Resource
    DepartmentAuthorityDao departmentAuthorityDao;

    @Resource
    CarDepositDao carDepositDao;

    @Resource
    CarRecordDao carRecordDao;

    @Resource
    CarRemarkDao carRemarkDao;

    @Resource
    CarBathDao carBathDao;

    @Resource
    CarCostDao carCostDao;

    @Resource
    CarSaleSetupDao carSaleSetupDao;

    @Resource
    CarPaidRecordDao carPaidRecordDao;

    @Resource
    CarPayMoneyAssistDao carPayMoneyAssistDao;

    @Resource
    CarSaleInfoDao carSaleInfoDao;

    @Resource
    CarSfDao carSfDao;

    @Resource
    MortgageRecordDao mortgageRecordDao;

    @Resource
    MortgageLogDao mortgageLogDao;

    @Resource
    InsuranceDao insuranceDao;

    @Resource
    InsuranceBusinessDao insuranceBusinessDao;

    @Resource
    ServiceFundDao serviceFundDao;

    @Resource
    NewCarDao newCarDao;

    @Resource
    NewCarFinanceDao newCarFinanceDao;

    @Resource
    MoneyManagerDao moneyManagerDao;

    @Resource
    WagesAssistDao wagesAssistDao;

    @Resource
    WagesDao wagesDao;

    @Resource
    CarDossierDao carDossierDao;

    @Resource
    MortgageRebateDao mortgageRebateDao;

    @Resource
    CarRecordMapper carRecordMapper;
    //用户数据相关
    //----------------------------------------------------------------------------
    //新建用户
    public void createAccount(Account account) {
        account.setCtime(System.currentTimeMillis());
        account.setUtime(System.currentTimeMillis());
        accountDao.insert(account);
    }

    //根据部门删除成员
    public void deleteAccountByDepartmentId(Integer departmentId) {
        accountDao.deleteByDepartmentId(departmentId);
    }

    //根据id删除成员
    public void deleteAccountById(Integer id) {
        Account account = new Account();
        account.setId(id);
        account.setActive(0);
        account.setUtime(System.currentTimeMillis());
        accountDao.updateById(account);
    }

    //获取用户
    public Account getAccountByName(String accountNum) {
        return accountDao.getByName(accountNum);
    }

    //获取用户
    public Account getAccountByRealName(String name) {
        return accountDao.getByRealName(name);
    }

    //获取用户
    public Account getAccountById(Integer id) {
        return accountDao.getById(id);
    }


    //获取用户列表
    public List<Account> getAccountList(String name) {
        return accountDao.getAccountList(name);
    }

    //更新用户数据
    public void updateAccountById(Account account) {
        accountDao.updateById(account);
    }


    //部门操作相关
    //----------------------------------------------------------------------------
    //创建部门
    public void createDepartment(DepartmentAuthority departmentAuthority) {
        departmentAuthorityDao.insert(departmentAuthority);
    }

    //更新部门信息
    public void updateDepartment(DepartmentAuthority departmentAuthority) {
        departmentAuthorityDao.updateById(departmentAuthority);
    }

    //删除部门
    public void deleteDepartment(Integer id) {
        departmentAuthorityDao.deleteById(id);
    }

    //获取部门信息
    public DepartmentAuthority getDepartmentAuthorityById(Integer id) {
        return departmentAuthorityDao.getById(id);
    }

    //获取部门列表
    public List<DepartmentAuthority> getDepartmentAuthorityList() {
        return departmentAuthorityDao.getDepartmentAuthorityList();
    }

    //获取部门信息
    public DepartmentAuthority getDepartmentAuthorityByName(String name) {
        return departmentAuthorityDao.getByName(name);
    }

    //备注相关
    //----------------------------------------------------------------------------
    public Integer  createCarRemark(CarRemark carRemark){
        carRemark.setUtime(System.currentTimeMillis());
        carRemark.setCtime(System.currentTimeMillis());
        return carRemarkDao.insert(carRemark);
    }

    public Integer updateCarRemark(CarRemark carRemark){
        carRemark.setUtime(System.currentTimeMillis());
        return carRemarkDao.updateById(carRemark);
    }

    public CarRemark getCarRemark(Integer id){
        return carRemarkDao.getById(id);
    }

    public Integer deleteCarRemark(Integer id){
        return carRemarkDao.deleteById(id);
    }

    public List<CarRemark> getCarRemarkList(Map map){
        return carRemarkDao.getCarRemarkList(map);
    }

    //订金寻车相关
    //----------------------------------------------------------------------------
    public Integer  createCarDeposit(CarDeposit carDeposit){
        carDeposit.setUtime(System.currentTimeMillis());
        carDeposit.setCtime(System.currentTimeMillis());
        return carDepositDao.insert(carDeposit);
    }

    public Integer updateCarDeposit(CarDeposit carDeposit){
        carDeposit.setUtime(System.currentTimeMillis());
        return carDepositDao.updateById(carDeposit);
    }

    public CarDeposit getCarDeposit(Integer id){
        return carDepositDao.getById(id);
    }

    public Integer deleteCarDeposit(Integer id){
        return carDepositDao.deleteById(id);
    }

    public List<CarDeposit> getCarDepositList(Map map){
        return carDepositDao.getCarDepositListByKey(map);
    }


    //车辆买入相关操作
    //----------------------------------------------------------------------------
    public Integer getCarRecordAutoId() {
        return carRecordDao.getCarBathAutoId();
    }

    //创建买入记录
    public void createCarRecord(CarRecord carRecord) {
        carRecord.setUtime(System.currentTimeMillis());
        carRecord.setCtime(System.currentTimeMillis());
        carRecordDao.insert(carRecord);
    }

    //更新采购信息
    public void updateCarRecord(CarRecord carRecord) {
        carRecord.setUtime(System.currentTimeMillis());
        carRecordDao.updateById(carRecord);
    }

    //删除采购记录
    public void deleteCarRecord(Integer id) {
        carRecordDao.deleteById(id);
    }

    //获取采购信息
    public CarRecord getCarRecordById(Integer id) {
        return carRecordDao.getById(id);
    }

    //获取采购信息
    public List<CarRecord> getCarRecordListByRecordStatus(Integer recordStatus) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("recordStatus", recordStatus);
        return carRecordDao.getCarRecordListByRecordStatus(map);
    }

    //获取采购信息list
    public List<CarRecord> getCarRecordByQuery(QueryWrapper<CarRecord> query) {
        return carRecordMapper.selectList(query);
    }

    //车辆属性相关
    //----------------------------------------------------------------------------
    //创建车辆新属性
    public void createCarProperty(CarProperty carProperty) {
        carPropertyDao.insert(carProperty);
    }

    //删除车辆属性
    public void deleteCarPropertyById(Integer id) {
        carPropertyDao.deleteById(id);
    }

    //更新车辆属性
    public void updateCarPropertyById(CarProperty carProperty) {
        carPropertyDao.updateById(carProperty);
    }


    //获取车辆属性列表
    public List<CarProperty> getCarPropertyListByKey(String key) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", key);
        return carPropertyDao.getCarPropertyListByKey(map);
    }


    //批量采购记录
    //----------------------------------------------------------------------------
    public Integer getCarBathAutoId() {
        return carBathDao.getCarBathAutoId();
    }

    public List<CarBath> getCarBathListByKey(String accountNum) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("accountNum", accountNum);
        return carBathDao.getCarBathListByKey(map);
    }

    //创建批量采购记录
    public Integer createCarBath(CarBath carBath) {
        carBath.setUtime(System.currentTimeMillis());
        carBath.setCtime(System.currentTimeMillis());
        return carBathDao.insert(carBath);
    }

    public Integer updateCarBath(CarBath carBath) {
        carBath.setUtime(System.currentTimeMillis());
        return carBathDao.updateById(carBath);
    }

    public CarBath getCarBathById(Integer id) {
        return carBathDao.getById(id);
    }

    public Integer delCarBathById(Integer id) {
        return carBathDao.deleteById(id);
    }

    public CarBath getCarBathByName(String name) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("bathName", name);
        return carBathDao.getByName(map);
    }


    //成本录入相关
    //----------------------------------------------------------------------------
    public Integer getCarCostAutoId() {
        return carCostDao.getCarCostAutoId();
    }

    public CarCost getCarCostById(Integer id) {
        return carCostDao.getById(id);
    }

    public Integer createCarCost(CarCost carCost) {
        carCost.setUtime(System.currentTimeMillis());
        carCost.setCtime(System.currentTimeMillis());
        return carCostDao.insert(carCost);
    }

    public Integer updateCarCost(CarCost carCost) {
        carCost.setUtime(System.currentTimeMillis());
        return carCostDao.updateById(carCost);
    }

    public Integer deleteCarCost(Integer id) {
        return carCostDao.deleteById(id);
    }


    //整备相关
    //----------------------------------------------------------------------------
    public Integer getCarSaleSetupAutoId() {
        return carSaleSetupDao.getCarSaleSetupAutoId();
    }

    public List<CarSaleSetup> getCarSaleSetupByCarCostId(Integer carCostId, Integer setupType) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("carCostId", carCostId);
        map.put("setupType", setupType);
        return carSaleSetupDao.getByCarCostId(map);
    }

    public CarSaleSetup getCarSaleSetupById(Integer id) {
        return carSaleSetupDao.getById(id);
    }

    public Integer createCarSaleSetup(CarSaleSetup carSaleSetup) {
        carSaleSetup.setUtime(System.currentTimeMillis());
        carSaleSetup.setCtime(System.currentTimeMillis());
        return carSaleSetupDao.insert(carSaleSetup);
    }

    public Integer updateCarSaleSetup(CarSaleSetup carSaleSetup) {
        carSaleSetup.setUtime(System.currentTimeMillis());
        return carSaleSetupDao.updateById(carSaleSetup);
    }

    public Integer deleteCarSaleSetup(Integer id) {
        return carSaleSetupDao.deleteById(id);
    }

    public Integer deleteCarSaleSetupByMap(Map map) {
        return carSaleSetupDao.deleteByMap(map);
    }

    //销售信息相关
    //----------------------------------------------------------------------------
    public Integer getCarSaleInfoAutoId() {
        return carSaleInfoDao.getCarSaleInfoAutoId();
    }

    public CarSaleInfo getCarSaleInfoById(Integer id) {
        return carSaleInfoDao.getById(id);
    }

    public Integer createCarSaleInfo(CarSaleInfo carSaleInfo) {
        carSaleInfo.setUtime(System.currentTimeMillis());
        carSaleInfo.setCtime(System.currentTimeMillis());
        return carSaleInfoDao.insert(carSaleInfo);
    }

    public Integer updateCarSaleInfo(CarSaleInfo carSaleInfo) {
        carSaleInfo.setUtime(System.currentTimeMillis());
        return carSaleInfoDao.updateById(carSaleInfo);
    }

    public Integer deleteCarSaleInfo(Integer id) {
        return carSaleInfoDao.deleteById(id);
    }

    public Integer getCarSfAutoId() {
        return carSfDao.getCarSfAutoId();
    }

    public CarSf getCarSfById(Integer id) {
        return carSfDao.getById(id);
    }

    public Integer createCarSf(CarSf carSf) {
        carSf.setUtime(System.currentTimeMillis());
        carSf.setCtime(System.currentTimeMillis());
        return carSfDao.insert(carSf);
    }

    public Integer updateCarSf(CarSf carSf) {
        carSf.setUtime(System.currentTimeMillis());
        return carSfDao.updateById(carSf);
    }

    public Integer deleteCarSf(Integer id) {
        return carSfDao.deleteById(id);
    }

    public Integer getServiceMoney() {
        return carSfDao.getServiceMoney();
    }

    public Integer createCarPayMoneyAssist(CarPayMoneyAssist carPayMoneyAssist) {
        carPayMoneyAssist.setUtime(System.currentTimeMillis());
        carPayMoneyAssist.setCtime(System.currentTimeMillis());
        return carPayMoneyAssistDao.insert(carPayMoneyAssist);
    }

    public CarPayMoneyAssist getCarPayMoneyAssist(Integer id) {
        return carPayMoneyAssistDao.getById(id);
    }

    public Integer deleteCarPayMoneyAssist(Integer id) {
        return carPayMoneyAssistDao.deleteById(id);
    }

    public List<CarPayMoneyAssist> getCarPayMoneyAssistList(Map map) {
        return carPayMoneyAssistDao.getByCarSaleInfoId(map);
    }

    public Integer deleteCarPayMoneyAssistList(Map map) {
        return carPayMoneyAssistDao.deleteByMap(map);
    }

    public Integer updateCarPayMoneyAssist(CarPayMoneyAssist carPayMoneyAssist) {
        carPayMoneyAssist.setUtime(System.currentTimeMillis());
        return carPayMoneyAssistDao.updateById(carPayMoneyAssist);
    }

    //付款记录
    //----------------------------------------------------------------------------
    public Integer createCarPaidRecord(CarPaidRecord carPaidRecord) {
        carPaidRecord.setUtime(System.currentTimeMillis());
        carPaidRecord.setCtime(System.currentTimeMillis());
        return carPaidRecordDao.insert(carPaidRecord);
    }

    public CarPaidRecord getCarPaidRecord(Integer id) {
        return carPaidRecordDao.getById(id);
    }

    public Integer deleteCarPaidRecord(Integer id) {
        return carPaidRecordDao.deleteById(id);
    }

    public List<CarPaidRecord> getCarPaidRecordList(Map map) {
        return carPaidRecordDao.getByMap(map);
    }

    public Integer deleteCarPaidRecordList(Map map) {
        return carPaidRecordDao.deleteByMap(map);
    }

    public Integer updateCarPaidRecord(CarPaidRecord carPaidRecord) {
        carPaidRecord.setUtime(System.currentTimeMillis());
        return carPaidRecordDao.updateById(carPaidRecord);
    }


    //售后服务基金
    //----------------------------------------------------------------------------
    public Integer getServiceFundAutoId() {
        return serviceFundDao.getServiceFundAutoId();
    }

    public ServiceFund getServiceFundById(Integer id) {
        return serviceFundDao.getById(id);
    }

    public Integer createServiceFund(ServiceFund serviceFund) {
        serviceFund.setUtime(System.currentTimeMillis());
        serviceFund.setCtime(System.currentTimeMillis());
        return serviceFundDao.insert(serviceFund);
    }

    public Integer updateServiceFund(ServiceFund serviceFund) {
        serviceFund.setUtime(System.currentTimeMillis());
        return serviceFundDao.updateById(serviceFund);
    }

    public List<ServiceFund> getServiceFundByMap(Map map) {
        return serviceFundDao.getByMap(map);
    }

    public Integer getUseServiceMoney() {
        return serviceFundDao.getUseServiceMoney();
    }

    public Integer deleteServiceFund(Integer id) {
        return serviceFundDao.deleteById(id);
    }


    //车辆按揭相关
    //----------------------------------------------------------------------------
    public Integer getMortgageRecordAutoId() {
        return mortgageRecordDao.getMortgageRecordAutoId();
    }

    public MortgageRecord getMortgageRecordById(Integer id) {
        return mortgageRecordDao.getById(id);
    }

    public Integer createMortgageRecord(MortgageRecord mortgageRecord) {
        mortgageRecord.setUtime(System.currentTimeMillis());
        mortgageRecord.setCtime(System.currentTimeMillis());
        return mortgageRecordDao.insert(mortgageRecord);
    }

    public Integer updateMortgageRecord(MortgageRecord mortgageRecord) {
        mortgageRecord.setUtime(System.currentTimeMillis());
        return mortgageRecordDao.updateById(mortgageRecord);
    }

    public Integer deleteMortgageRecord(Integer id) {
        return mortgageRecordDao.deleteById(id);
    }

    public List<MortgageRecord> getMortgageRecordList(Map map) {
        return mortgageRecordDao.getMortgageRecordList(map);
    }

    //按揭相关
    //----------------------------------------------------------------------------
    public Integer getMortgageLogAutoId() {
        return mortgageLogDao.getAutoId();
    }

    public MortgageLog getMortgageLogById(Integer id) {
        return mortgageLogDao.getById(id);
    }

    public Integer createMortgageLog(MortgageLog mortgageRecord) {
        mortgageRecord.setUtime(System.currentTimeMillis());
        mortgageRecord.setCtime(System.currentTimeMillis());
        return mortgageLogDao.insert(mortgageRecord);
    }

    public Integer updateMortgageLog(MortgageLog mortgageRecord) {
        mortgageRecord.setUtime(System.currentTimeMillis());
        return mortgageLogDao.updateById(mortgageRecord);
    }

    public Integer deleteMortgageLog(Integer id) {
        return mortgageLogDao.deleteById(id);
    }

    public List<MortgageLog> getMortgageLogList(Map map) {
        return mortgageLogDao.getMortgageLogList(map);
    }

    //保险相关
    //----------------------------------------------------------------------------
    public List<Insurance> getInsuranceList(Map map) {
        return insuranceDao.getInsuranceByMap(map);
    }

    public Insurance getInsurance(Integer id) {
        return insuranceDao.getById(id);
    }

    public Integer createInsurance(Insurance insurance) {
        insurance.setUtime(System.currentTimeMillis());
        insurance.setCtime(System.currentTimeMillis());
        return insuranceDao.insert(insurance);
    }

    public Integer updateInsurance(Insurance insurance) {
        insurance.setUtime(System.currentTimeMillis());
        return insuranceDao.updateById(insurance);
    }

    public Integer deleteInsurance(Integer id) {
        return insuranceDao.deleteById(id);
    }

    public List<InsuranceBusiness> getInsuranceBusinessList(Map map) {
        return insuranceBusinessDao.getInsuranceBusinessByMap(map);
    }

    public InsuranceBusiness getInsuranceBusiness(Integer id) {
        return insuranceBusinessDao.getById(id);
    }

    public Integer createInsuranceBusiness(InsuranceBusiness insuranceBusiness) {
        insuranceBusiness.setCtime(System.currentTimeMillis());
        insuranceBusiness.setUtime(System.currentTimeMillis());
        return insuranceBusinessDao.insert(insuranceBusiness);
    }

    public Integer updateInsuranceBusiness(InsuranceBusiness insuranceBusiness) {
        insuranceBusiness.setUtime(System.currentTimeMillis());
        return insuranceBusinessDao.updateById(insuranceBusiness);
    }

    public Integer deleteInsuranceBusiness(Integer id) {
        return insuranceBusinessDao.deleteById(id);
    }

    public Integer deleteInsuranceBusinessByInsuranceId(Integer InsuranceId) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("insuranceId", InsuranceId);
        return insuranceBusinessDao.deleteByInsuranceId(map);
    }

    //新车业务相关
    //----------------------------------------------------------------------------
    public Integer getNewCarAutoId() {
        return newCarDao.getNewCarAutoId();
    }

    public Integer createNewCar(NewCar newCar) {
        newCar.setCtime(System.currentTimeMillis());
        newCar.setUtime(System.currentTimeMillis());
        return newCarDao.insert(newCar);
    }

    public Integer updateNewCar(NewCar newCar) {
        newCar.setUtime(System.currentTimeMillis());
        return newCarDao.updateById(newCar);
    }

    public Integer deleteNewCar(Integer id) {
        return newCarDao.deleteById(id);
    }

    public NewCar getNewCar(Integer id) {
        return newCarDao.getById(id);
    }

    public List<NewCar> getNewCarListByMap(Map map) {
        return newCarDao.getNewCarList(map);
    }

    //新车金融方案相关
    //----------------------------------------------------------------------------
    public Integer getNewCarFinanceAutoId() {
        return newCarFinanceDao.getNewCarFinanceAutoId();
    }

    public Integer createNewCarFinance(NewCarFinance newCarFinance) {
        newCarFinance.setCtime(System.currentTimeMillis());
        newCarFinance.setUtime(System.currentTimeMillis());
        return newCarFinanceDao.insert(newCarFinance);
    }

    public Integer updateNewCarFinance(NewCarFinance newCarFinance) {
        newCarFinance.setUtime(System.currentTimeMillis());
        return newCarFinanceDao.updateById(newCarFinance);
    }

    public Integer deleteNewCarFinance(Integer id) {
        return newCarFinanceDao.deleteById(id);
    }

    public NewCarFinance getNewCarFinance(Integer id) {
        return newCarFinanceDao.getById(id);
    }

    public List<NewCarFinance> getNewCarFinanceList(Map map) {
        return newCarFinanceDao.getNewCarFinanceList(map);
    }

    //资金管理
    //----------------------------------------------------------------------------
    public Integer getmoneyManagerAutoId() {
        return moneyManagerDao.getAutoId();
    }

    public Integer createmoneyManager(MoneyManager moneyManager) {
        moneyManager.setCtime(System.currentTimeMillis());
        moneyManager.setUtime(System.currentTimeMillis());
        return moneyManagerDao.insert(moneyManager);
    }

    public Integer updatemoneyManager(MoneyManager moneyManager) {
        moneyManager.setUtime(System.currentTimeMillis());
        return moneyManagerDao.updateById(moneyManager);
    }

    public Integer deletemoneyManager(Integer id) {
        return moneyManagerDao.deleteById(id);
    }

    public MoneyManager getmoneyManager(Integer id) {
        return moneyManagerDao.getById(id);
    }

    public List<MoneyManager> getMoneyManagerList(Map map) {
        return moneyManagerDao.getMoneyManagerList(map);
    }

    //工资辅助操作
    //----------------------------------------------------------------------------
    public Integer getWagesAssistAutoId() {
        return wagesAssistDao.getWagesAssistAutoId();
    }

    public Integer createWagesAssist(WagesAssist wagesAssist) {
        wagesAssist.setCtime(System.currentTimeMillis());
        wagesAssist.setUtime(System.currentTimeMillis());
        return wagesAssistDao.insert(wagesAssist);
    }

    public Integer updateWagesAssist(WagesAssist wagesAssist) {
        wagesAssist.setUtime(System.currentTimeMillis());
        return wagesAssistDao.updateById(wagesAssist);
    }

    public WagesAssist getWagesAssist(Integer id) {
        return wagesAssistDao.getById(id);
    }

    public WagesAssist getWagesAssistByCarRecordId(Integer id) {
        return wagesAssistDao.getByCarRecordId(id);
    }

    public Integer deleteWagesAssist(Integer id) {
        return wagesAssistDao.deleteById(id);
    }

    public Integer deleteWagesAssistByMap(Map map){
        return wagesAssistDao.deleteByMap(map);
    }

    public List<WagesAssist> getWagesAssistByMap(Map map) {
        return wagesAssistDao.getListByMap(map);
    }

    //工资操作
    //----------------------------------------------------------------------------
    public Integer getWagesAutoId() {
        return wagesDao.getWagesAutoId();
    }

    public Integer createWages(Wages wages) {
        wages.setUtime(System.currentTimeMillis());
        wages.setCtime(System.currentTimeMillis());
        return wagesDao.insert(wages);
    }

    public Integer updateWages(Wages wages) {
        wages.setUtime(System.currentTimeMillis());
        return wagesDao.updateById(wages);
    }

    public Wages getWages(Integer id) {
        return wagesDao.getById(id);
    }

    public Integer deleteWages(Integer id) {
        return wagesDao.deleteById(id);
    }

    public List<Wages> getWagesByMap(Map map) {
        return wagesDao.getListByMap(map);
    }

    //车辆档案相关操作
    //----------------------------------------------------------------------------
    public Integer getCarDossierAutoId() {
        return carDossierDao.getCarDossierAutoId();
    }

    public Integer createCarDossier(CarDossier carDossier) {
        carDossier.setUtime(System.currentTimeMillis());
        carDossier.setCtime(System.currentTimeMillis());
        return carDossierDao.insert(carDossier);
    }

    public Integer updateCarDossier(CarDossier carDossier) {
        carDossier.setUtime(System.currentTimeMillis());
        return carDossierDao.updateById(carDossier);
    }

    public CarDossier getCarDossier(Integer id) {
        return carDossierDao.getById(id);
    }

    public Integer deleteCarDossier(Integer id) {
        return carDossierDao.deleteById(id);
    }

    public Integer deleteCarDossierByMap(Map map) {
        return carDossierDao.deleteByMap(map);
    }

    public List<CarDossier> getCarDossierByMap(Map map) {
        return carDossierDao.getListByMap(map);
    }

    //按揭返点相关
    //----------------------------------------------------------------------------
    public Integer  createMortgageRebate(MortgageRebate mortgageRebate){
        mortgageRebate.setUtime(System.currentTimeMillis());
        mortgageRebate.setCtime(System.currentTimeMillis());
        return mortgageRebateDao.insert(mortgageRebate);
    }

    public Integer updateMortgageRebate(MortgageRebate mortgageRebate){
        mortgageRebate.setUtime(System.currentTimeMillis());
        return mortgageRebateDao.updateById(mortgageRebate);
    }

    public MortgageRebate getMortgageRebate(Integer id){
        return mortgageRebateDao.getById(id);
    }

    public Integer deleteMortgageRebate(Integer id){
        return mortgageRebateDao.deleteById(id);
    }

    public List<MortgageRebate> getMortgageRebateList(Map map){
        return mortgageRebateDao.getMortgageRebateList(map);
    }
}
