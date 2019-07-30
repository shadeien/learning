# Zuul 架构

在zuul中， 整个请求的过程是这样的，首先将请求给zuulservlet处理，zuulservlet中有一个zuulRunner对象，该对象中初始化了RequestContext：作为存储整个请求的一些数据，并被所有的zuulfilter共享。zuulRunner中还有 FilterProcessor，FilterProcessor作为执行所有的zuulfilter的管理器。FilterProcessor从filterloader 中获取zuulfilter，而zuulfilter是被filterFileManager所加载，并支持groovy热加载，采用了轮询的方式热加载。

有了这些filter之后，zuulservelet首先执行的Pre类型的过滤器，再执行route类型的过滤器，最后执行的是post 类型的过滤器，如果在执行这些过滤器有错误的时候则会执行error类型的过滤器。执行完这些过滤器，最终将请求的结果返回给客户端。

# 源码分析

## EnableZuulProxy

```java
@EnableCircuitBreaker
@EnableDiscoveryClient
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ZuulProxyConfiguration.class)
public @interface EnableZuulProxy {
}
```

其中，引用了ZuulProxyConfiguration，跟踪ZuulProxyConfiguration，该类注入了DiscoveryClient、RibbonCommandFactoryConfiguration用作负载均衡相关的。注入了一些列的filters，比如PreDecorationFilter、RibbonRoutingFilter、SimpleHostRoutingFilter，代码如如下：

```java

	public PreDecorationFilter preDecorationFilter(RouteLocator routeLocator, ProxyRequestHelper proxyRequestHelper) {
		return new PreDecorationFilter(routeLocator, this.server.getServletPrefix(), this.zuulProperties,
				proxyRequestHelper);
	}

	// route filters
	@Bean
	public RibbonRoutingFilter ribbonRoutingFilter(ProxyRequestHelper helper,
			RibbonCommandFactory<?> ribbonCommandFactory) {
		RibbonRoutingFilter filter = new RibbonRoutingFilter(helper, ribbonCommandFactory, this.requestCustomizers);
		return filter;
	}

	@Bean
	public SimpleHostRoutingFilter simpleHostRoutingFilter(ProxyRequestHelper helper, ZuulProperties zuulProperties) {
		return new SimpleHostRoutingFilter(helper, zuulProperties);
	}

```

## Zuul默认过滤器

直接打开 spring-cloud-netflix-core.jar的 zuul.filters包，可以看到一些列的filter

|过滤器	|order	|描述	|类型|
| ------| :----:| :-----:| :--:|
|ServletDetectionFilter	|-3	|检测请求是用 DispatcherServlet还是 ZuulServlet	|pre|
|Servlet30WrapperFilter	|-2	|在Servlet 3.0 下，包装 requests	|pre|
|FormBodyWrapperFilter	|-1	|解析表单数据	|pre|
|SendErrorFilter	|0	|如果中途出现错误	|error|
|DebugFilter	|1	|设置请求过程是否开启debug	|pre|
|PreDecorationFilter	|5	|根据uri决定调用哪一个route过滤器	|pre|
|RibbonRoutingFilter	|10	|如果写配置的时候用ServiceId则用这个route过滤器，该过滤器可以用Ribbon 做负载均衡，用hystrix做熔断	|route|
|SimpleHostRoutingFilter	|100	|如果写配置的时候用url则用这个route过滤	|route|
|SendForwardFilter	|500	|用RequestDispatcher请求转发	|route|
|SendResponseFilter	|1000	|用RequestDispatcher请求转发	|post|