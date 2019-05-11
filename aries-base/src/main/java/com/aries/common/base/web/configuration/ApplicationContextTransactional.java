package com.aries.common.base.web.configuration;

import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Collections;

/**
 * SpringBoot配置全局事务    ---> 之注解声明式事务, 使用声明式事务配置, 可以有效规范代码
 *
 * @author
 */
@Aspect
@Configuration
@MapperScan(value="com.aries.common.base.web.mapper") // 扫描操作表方法对应的接口文件路径
public class ApplicationContextTransactional {

    // 事务方法超时时间设置
    private static final int TX_METHOD_TIMEOUT = 5;
    // AOP切面的切点表达式, 在controller层提交事务
    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.application.common.yuan.web.controller..*.*(..))";
    // AOP切面的切点表达式, 在service层提交事务
    //private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.application.common.yuan.web.service*..*.*(..))";
    // 注入事务管理器
    @Autowired
    private PlatformTransactionManager transactionManager;

    /**
     ** 增强(事务)的属性的配置
     **   isolation:DEFAULT  :事务的隔离级别.
     **   propagation        :事务的传播行为.
     **   read-only          :false.不是只读
     **   timeout            :-1
     **   no-rollback-for    :发生哪些异常不回滚
     **   rollback-for       :发生哪些异常回滚事务
     ** @return
     */
    @Bean
    public TransactionInterceptor txAdvice() {

        System.out.println("SpringBoot配置全局事务---2--->");
        /**
         *	 	propagation="REQUIRED" ,
         *		isolation=READ_COMMITTED
         * 		timeout=5 ;
         * 		rollback-for="Exception" 配置
         */
        RuleBasedTransactionAttribute txAttr_REQUIRED = new RuleBasedTransactionAttribute();
        txAttr_REQUIRED.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        txAttr_REQUIRED.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        txAttr_REQUIRED.setTimeout(TX_METHOD_TIMEOUT);
        txAttr_REQUIRED.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        System.out.println("SpringBoot配置全局事务---3--->" + txAttr_REQUIRED);
        /**
         *	 	propagation="SUPPORTS" ,
         *		readOnly="true"配置
         */
        RuleBasedTransactionAttribute txAttr_REQUIRED_READONLY = new RuleBasedTransactionAttribute();
        txAttr_REQUIRED_READONLY.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
        txAttr_REQUIRED_READONLY.setReadOnly(true);
        System.out.println("SpringBoot配置全局事务---4--->" + txAttr_REQUIRED_READONLY);
        /*
         * 增强(事务)的属性的配置             * <tx:attributes>             *
         */
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        /*
         *      注意：方法名称来自类匹配的到方法
         *		【save*, “*”表示匹配任意個字符】
         *      <tx:method .../>
         */
        // 增、删、改相关方法
        source.addTransactionalMethod("save*", txAttr_REQUIRED);
        source.addTransactionalMethod("delete*", txAttr_REQUIRED);
        source.addTransactionalMethod("update*", txAttr_REQUIRED);
        source.addTransactionalMethod("add*", txAttr_REQUIRED);
        source.addTransactionalMethod("edit*", txAttr_REQUIRED);
        source.addTransactionalMethod("modify*", txAttr_REQUIRED);
        source.addTransactionalMethod("sort*", txAttr_REQUIRED);
        source.addTransactionalMethod("exec*", txAttr_REQUIRED);
        source.addTransactionalMethod("set*", txAttr_REQUIRED);
        // 查询相关方法
        source.addTransactionalMethod("get*", txAttr_REQUIRED_READONLY);
        source.addTransactionalMethod("query*", txAttr_REQUIRED_READONLY);
        source.addTransactionalMethod("find*", txAttr_REQUIRED_READONLY);
        source.addTransactionalMethod("select*", txAttr_REQUIRED_READONLY);
        source.addTransactionalMethod("list*", txAttr_REQUIRED_READONLY);
        source.addTransactionalMethod("count*", txAttr_REQUIRED_READONLY);
        source.addTransactionalMethod("is*", txAttr_REQUIRED_READONLY);

        System.out.println("SpringBoot配置全局事务---5--->" + source);
        return new TransactionInterceptor(transactionManager, source);
    }

    /**
     * AOP配置定义切面和切点的信息
     *
     * @return
     */
    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        System.out.println("SpringBoot配置全局事务---1--->" + pointcut);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }

}
