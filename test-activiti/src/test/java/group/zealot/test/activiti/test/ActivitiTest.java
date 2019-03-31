package group.zealot.test.activiti.test;

import group.zealot.test.activiti.ProcessesService;
import group.zealot.test.activiti.Run;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Run.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActivitiTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ProcessesService processesService;
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private HistoryService historyService;


    @Test
    public void TestStartProcess() {
        ProcessInstance pi = processesService.startProcess("testProcess", "1");
        logger.info("启动流程，process id：" + pi.getProcessDefinitionId());
    }

    @Test
    public void claimTasks() {
        String processId = "25005";
        Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        String userId = "zealot";
        String taskId = task.getId();
        taskService.claim(taskId, userId);
        logger.info("认领");
    }

    @Test
    public void unclaimTasks() {
        String processId = "25005";
        Task task = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        String taskId = task.getId();
        taskService.unclaim(taskId);
        logger.info("取消认领");
    }

    @Test
    public void findTasks() {
        List<Task> list = taskService.createTaskQuery().processDefinitionKey("testProcess").list();
        for (Task task : list) {
            logger.info("task id " + task.getId() + ",process id " + task.getProcessInstanceId());
        }
    }

    @Test
    public void writeImage() {
        String processId = "25005";
        InputStream inputStream = processesService.generateImage(processId);
        //生成本地图片
        File file = new File("D:/test.png");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            byte[] b = new byte[1024];
            int length;
            while ((length = inputStream.read(b)) != -1) {
                fos.write(b, 0, length);
            }
        } catch (IOException e) {
            logger.error("IO流写入文件异常", e);
        }
        logger.info("写入图片");
    }
}
