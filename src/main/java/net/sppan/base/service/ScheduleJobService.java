package net.sppan.base.service;

import net.sppan.base.entity.ScheduleJob;
import net.sppan.base.service.support.IBaseService;
import net.sppan.base.vo.ScheduleJobVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface ScheduleJobService extends IBaseService<ScheduleJob,Integer> {

    /**
     * 初始化定时任务
     */
    public void initScheduleJob() throws Exception;

    /**
     * 新增
     * 
     * @param scheduleJobVo
     * @return
     */
    public void insert(ScheduleJobVo scheduleJobVo)throws Exception;

    /**
     * 直接修改 只能修改运行的时间，参数、同异步等无法修改
     * 
     * @param scheduleJobVo
     */
    public void update(ScheduleJobVo scheduleJobVo)throws Exception;

    /**
     * 删除重新创建方式
     * 
     * @param scheduleJobVo
     */
    public void delUpdate(ScheduleJobVo scheduleJobVo)throws Exception;

    /**
     * 删除
     * 
     * @param scheduleJobId
     */
    public void delete1(Integer scheduleJobId)throws Exception;

    /**
     * 运行一次任务
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
    public void runOnce(Integer scheduleJobId)throws Exception;

    /**
     * 暂停任务
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
    public void pauseJob(Integer scheduleJobId)throws Exception;

    /**
     * 恢复任务
     *
     * @param scheduleJobId the schedule job id
     * @return
     */
    public void resumeJob(Integer scheduleJobId)throws Exception;

    /**
     * 获取任务对象
     * 
     * @param scheduleJobId
     * @return
     */
    public ScheduleJobVo get(Integer scheduleJobId);

    /**
     * 查询任务列表
     * 
     * @param scheduleJobVo
     * @return
     */
    public List<ScheduleJobVo> queryList(ScheduleJobVo scheduleJobVo);

    /**
     * 获取运行中的任务列表
     *
     * @return
     */
    public List<ScheduleJobVo> queryExecutingJobList();

    /**
     * 根据关键字获取分页
     * @param searchText
     * @param pageRequest
     * @return
     */
    Page<ScheduleJob> findAllByLike(String searchText, PageRequest pageRequest);

    ScheduleJob findByLongId(Integer id);


}
