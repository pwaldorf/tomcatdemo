package com.pw.config;


import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import javax.jms.ConnectionFactory;



@Configuration
public class JmsConfigs {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsConfigs.class);
    
    @Bean("jms2")
    public JmsComponent jmsComponent2() throws Exception {
              
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(dispatchConnectionFactory());        
        jmsComponent.setTransacted(true);        
        jmsComponent.setCacheLevelName("CACHE_CONSUMER");
        jmsComponent.setTransactionManager(dispatchTransactionManager());        
        return jmsComponent;
    }

    @Bean("jms")
    public JmsComponent jmsComponent() throws Exception {
              
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(dispatchConnectionFactory());        
        jmsComponent.setTransacted(true);
        jmsComponent.setCacheLevelName("CACHE_CONSUMER");
        jmsComponent.setTransactionManager(dispatchTransactionManager());        
        return jmsComponent;
    }

    @Bean("dispatchConnectionFactory")
	public ConnectionFactory dispatchConnectionFactory() {		
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		
		try {
			LOGGER.info("Set Connection Parms");
			connectionFactory.setBrokerURL("tcp://192.168.6.1:61616");
			connectionFactory.setUser("artemis");
			connectionFactory.setPassword("artemis");			
			LOGGER.info("Connection Parms Set");			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		WrapCachingConnectionFactory wrapConnectionFactory = new WrapCachingConnectionFactory(connectionFactory);
		wrapConnectionFactory.setSessionCacheSize(10);
		
		return wrapConnectionFactory;
	}    

    @Bean("dispatchTransactionManager")
    public PlatformTransactionManager dispatchTransactionManager() {
        JmsTransactionManager transactionManager = new JmsTransactionManager(dispatchConnectionFactory());
        return transactionManager;
    }

    @Bean("txRequired")
    public SpringTransactionPolicy txRequired() {
        SpringTransactionPolicy required = new SpringTransactionPolicy();
        required.setTransactionManager(dispatchTransactionManager());
        required.setPropagationBehaviorName("PROPAGATION_REQUIRED");
        return required;    
    }

    @Bean("txRequiredNew")
    public SpringTransactionPolicy txRequiredNew() {
        SpringTransactionPolicy required = new SpringTransactionPolicy();
        required.setTransactionManager(dispatchTransactionManager());
        required.setPropagationBehaviorName("PROPAGATION_REQUIRES_NEW");
        return required;    
    }

    @Bean("txRequiredMandatory")
    public SpringTransactionPolicy txRequiredMandatory() {
        SpringTransactionPolicy required = new SpringTransactionPolicy();
        required.setTransactionManager(dispatchTransactionManager());
        required.setPropagationBehaviorName("PROPAGATION_REQUIRES_Mandatory");
        return required;    
    }
}
