package com.sample;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


import org.jbpm.process.workitem.rest.RESTWorkItemHandler;
//import org.jbpm.test.JBPMHelper;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.manager.RuntimeManagerFactory;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;

public class InitialClassKeySession {

	private static InitialClassKeySession variableKeySession = null;
	private static RESTWorkItemHandler restHandler = null;
	private static KieSession ksession = null;

	public static InitialClassKeySession startProcessInit() {
		if (variableKeySession == null)
			variableKeySession = new InitialClassKeySession();

		return variableKeySession;
	}
	


	private InitialClassKeySession() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieBase kbase = kContainer.getKieBase("kbase");

		RuntimeManager manager = createRuntimeManager(kbase);
		RuntimeEngine engine = manager.getRuntimeEngine(null);
		ksession = engine.getKieSession();
		
		TaskService taskService = engine.getTaskService();
		ksession.getWorkItemManager().registerWorkItemHandler("Rest",

			    new RESTWorkItemHandler());
		
	}

	private static RuntimeManager createRuntimeManager(KieBase kbase) {
		//JBPMHelper.startH2Server();
		//JBPMHelper.setupDataSource();
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("org.jbpm.persistence.jpa");
		RuntimeEnvironmentBuilder builder = RuntimeEnvironmentBuilder.Factory
				.get().newDefaultBuilder().entityManagerFactory(emf)
				.knowledgeBase(kbase);
		return RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(
				builder.get(), "com.sample:example:1.0");
	}

	public void startProcess(Integer temp) {
		System.out.println("inside start process");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Temperature", temp);
		System.out.println("inside start process temperature =" + temp);
		ProcessInstance pi = ksession.startProcess("com.sample.bpmn.hello",
				params);
	}
}
