package group.zealot.test.activity;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class IndexController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProcessesService processesService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("getImage")
    public void getImage(HttpServletResponse response) {
        Task task;
        String processInstanceId;
        while (true) {
            ProcessInstance processInstance = processesService.getFirstProcessInstance();
            if (processInstance == null) {
                processInstance = processesService.startProcess("TestProcess", "1");
                processInstanceId = processInstance.getId();
                break;
            }
            processInstanceId = processInstance.getId();
            task = processesService.getTask(processInstanceId);

            processesService.claim(task.getId(), "super");
            processesService.complete(task.getId(), null);

            if (processesService.getTask(processInstanceId) != null) {
                break;
            }
        }

        InputStream in = processesService.generateImage(processInstanceId);
        try {
            byte[] b = new byte[1024];
            int length;
            while ((length = in.read(b)) != -1) {
                response.getOutputStream().write(b, 0, length);
            }
            response.getOutputStream().flush();
        } catch (IOException e) {
            logger.error("IO流写入文件异常", e);
        }
    }

}
