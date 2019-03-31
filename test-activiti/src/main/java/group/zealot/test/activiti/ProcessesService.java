package group.zealot.test.activiti;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class ProcessesService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RuntimeService runtimeService;

    public ProcessInstance startProcess(String processDefinitionKey, String businessKey) {
        logger.info("start process processDefinitionKey:" + processDefinitionKey + " businessKey:" + businessKey);
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey);
        logger.info("process id:" + pi.getProcessDefinitionId());
        return pi;
    }

    public InputStream generateImage(String processInstanceId) {
        //1.创建核心引擎流程对象processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        //流程定义
        BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel(task.getProcessDefinitionId());

        //正在活动节点
        List<String> activeActivityIds = processEngine.getRuntimeService().getActiveActivityIds(task.getExecutionId());

        ProcessDiagramGenerator pdg = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();
        //生成流图片
        InputStream inputStream = pdg.generateDiagram(bpmnModel, "PNG", activeActivityIds, activeActivityIds,
                processEngine.getProcessEngineConfiguration().getActivityFontName(),
                processEngine.getProcessEngineConfiguration().getLabelFontName(),
                processEngine.getProcessEngineConfiguration().getActivityFontName(),
                processEngine.getProcessEngineConfiguration().getProcessEngineConfiguration().getClassLoader(), 1.0);

        return inputStream;
    }
}
