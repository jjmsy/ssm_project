package cn.indi.controller;

import cn.indi.domain.SysLog;
import cn.indi.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {

    //注入 request对象
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ISysLogService sysLogService;


    private Date visitTime;//开始时间
    private Class clazz;//访问的类
    private Method method;//访问的方法
    /**
     * 前置通知，主要是获取开始时间，执行的类是哪一个，执行的是哪一个方法
     * @param jp
     */
    @Before("execution(* cn.indi.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {
        visitTime = new Date();//当前的时间就是开始访问的时间
        clazz = jp.getTarget().getClass();//获取具体要访问的类
        String methodName = jp.getSignature().getName();//获取访问的方法名称
        Object[] args = jp.getArgs();//获取当问的方法中的参数

        //获取具体执行方法的Method对象
        if (args == null || args.length == 0){
            method = clazz.getMethod(methodName);//获取无参的方法
        }else {
            Class[] classArgs = new Class[args.length];
            for (int i = 0; i < args.length ; i++){
                classArgs[i] = args[i].getClass();//将有参的方法中的参数类型给classArgs
            }
            method = clazz.getMethod(methodName,classArgs);//获取到有参的方法
        }
    }

    /**
     * 后置通知，在后置通知中获取一个当前时间减去前置通知中的开始时间，及获取到了访问时长
     * @param jp
     */
    @After("execution(* cn.indi.controller.*.*(..))")
    public void doAfter(JoinPoint jp) throws Exception {
        long time = new Date().getTime() - visitTime.getTime();//获取访问时长
        //通过反射获取url
        String url = "";
        if (clazz!= null && method != null && clazz != LogAop.class){
            //获取类上的RequestMapping
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if(classAnnotation != null){
                String[] classValue = classAnnotation.value();//获取类上的value值，/orders
                //获取方法上的RequestMapping
                RequestMapping methodAnnotation = this.method.getAnnotation(RequestMapping.class);
                if (methodAnnotation != null){
                    String[] methodValue = methodAnnotation.value();
                    url = classValue[0] + methodValue[0];
                }
            }
        }
        //通过request获取访问的ip，在web.xml配置RequestContextListener
        String ip = request.getRemoteAddr();

        //获取当前操作的用户,可以通过securityContext获取，也可以从request.getSession中获取
        SecurityContext context = SecurityContextHolder.getContext();//从上下文中获取当前的 用户
        //request.getSession().getAttribute("SPRING_SECURITY_CONTEXT")
        User user = (User) context.getAuthentication().getPrincipal();
        String username = user.getUsername();

        //将日志相关信息封装到SysLog对象
        SysLog sysLog = new SysLog();
        sysLog.setExecutionTime(time);
        sysLog.setIp(ip);
        sysLog.setMethod("[类名] "+clazz.getName()+"[方法名] "+method.getName());
        sysLog.setUrl(url);
        sysLog.setUsername(username);
        sysLog.setVisitTime(visitTime);

        //调用service完成操作
        sysLogService.save(sysLog);

    }
}
