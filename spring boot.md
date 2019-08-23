# spring boot

## SpringBootApplication

@SpringBootApplication = (默认属性)@SpringBootConfiguration + @EnableAutoConfiguration + @ComponentScan。

```java
@Target(ElementType.TYPE)  // 见JAVA元注解
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
		@Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM,
				classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {...}
```

```java
public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
	    // 构造时传入的资源loader
		this.resourceLoader = resourceLoader;
		Assert.notNull(primarySources, "PrimarySources must not be null");
		// 主要源，springboot启动指定的入口 包含bean扫描路径等
		this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));
		// 是否是web应用
		this.webApplicationType = WebApplicationType.deduceFromClasspath();
		// 通过springfactories自动设置进来的上下文
		/** 默认值
		0 = "org.springframework.boot.context.ConfigurationWarningsApplicationContextInitializer"
		1 = "org.springframework.boot.context.ContextIdApplicationContextInitializer"
		2 = "org.springframework.boot.context.config.DelegatingApplicationContextInitializer"
		3 = "org.springframework.boot.web.context.ServerPortInfoApplicationContextInitializer"
		4 = "org.springframework.boot.autoconfigure.SharedMetadataReaderFactoryContextInitializer"
		5 = "org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener"
		*/
		setInitializers((Collection) getSpringFactoriesInstances(
				ApplicationContextInitializer.class));
		// 通过springfactories自动设置进来的监听器
		/**
		0 = "org.springframework.boot.ClearCachesApplicationListener"
		1 = "org.springframework.boot.builder.ParentContextCloserApplicationListener"
		2 = "org.springframework.boot.context.FileEncodingApplicationListener"
		3 = "org.springframework.boot.context.config.AnsiOutputApplicationListener"
		4 = "org.springframework.boot.context.config.ConfigFileApplicationListener"
		5 = "org.springframework.boot.context.config.DelegatingApplicationListener"
		6 = "org.springframework.boot.context.logging.ClasspathLoggingApplicationListener"
		7 = "org.springframework.boot.context.logging.LoggingApplicationListener"
		8 = "org.springframework.boot.liquibase.LiquibaseServiceLocatorApplicationListener"
		9 = "org.springframework.boot.autoconfigure.BackgroundPreinitializer"
		*/
		setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));

		this.mainApplicationClass = deduceMainApplicationClass();
	}
```

```java
public ConfigurableApplicationContext run(String... args) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		ConfigurableApplicationContext context = null;
		Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
		configureHeadlessProperty();
		/**
		EventPublishingRunListener
		**/
		SpringApplicationRunListeners listeners = getRunListeners(args);
		listeners.starting();
		try {
			ApplicationArguments applicationArguments = new DefaultApplicationArguments(
					args);
			ConfigurableEnvironment environment = prepareEnvironment(listeners,
					applicationArguments);
			configureIgnoreBeanInfo(environment);
			Banner printedBanner = printBanner(environment);
			context = createApplicationContext();
			exceptionReporters = getSpringFactoriesInstances(
					SpringBootExceptionReporter.class,
					new Class[] { ConfigurableApplicationContext.class }, context);
			prepareContext(context, environment, listeners, applicationArguments,
					printedBanner);
			// 刷新context，重要
			refreshContext(context);
			afterRefresh(context, applicationArguments);
			stopWatch.stop();
			if (this.logStartupInfo) {
				new StartupInfoLogger(this.mainApplicationClass)
						.logStarted(getApplicationLog(), stopWatch);
			}
			// EventPublishingRunListener 发布ApplicationStartedEvent
			listeners.started(context);
			// 调用自定义runner,实现CommandLineRunner和ApplicationRunner的类
			callRunners(context, applicationArguments);
		}
		catch (Throwable ex) {
			handleRunFailure(context, ex, exceptionReporters, listeners);
			throw new IllegalStateException(ex);
		}

		try {
			// EventPublishingRunListener 发布ApplicationReadyEvent
			listeners.running(context);
		}
		catch (Throwable ex) {
			handleRunFailure(context, ex, exceptionReporters, null);
			throw new IllegalStateException(ex);
		}
		return context;
	}
```

## SpringBootConfiguration

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
public @interface SpringBootConfiguration {...}
```

## Configuration
从Spring3.0，@Configuration用于定义配置类，可替换xml配置文件，被注解的类内部包含有一个或多个被@Bean注解的方法，这些方法将会被AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类进行扫描，并用于构建bean定义，初始化Spring容器。

@Configuration注解的配置类有如下要求：
1. @Configuration不可以是final类型；
2. @Configuration不可以是匿名类；
3. 嵌套的configuration必须是静态类。