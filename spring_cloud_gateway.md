

## 配置

DiscoveryLocatorProperties: @ConfigurationProperties("spring.cloud.gateway.discovery.locator")
```
/** Flag that enables DiscoveryClient gateway integration */
	private boolean enabled = false;

	/**
	 * The prefix for the routeId, defaults to discoveryClient.getClass().getSimpleName() + "_".
	 * Service Id will be appended to create the routeId.
	 */
	private String routeIdPrefix;

	/**
	 * SpEL expression that will evaluate whether to include a service in gateway integration or not,
	 * defaults to: true
	 */
	private String includeExpression = "true";

	/** SpEL expression that create the uri for each route, defaults to: 'lb://'+serviceId */
	private String urlExpression = "'lb://'+serviceId";

	/**
	 * Option to lower case serviceId in predicates and filters, defaults to false.
	 * Useful with eureka when it automatically uppercases serviceId.
	 * so MYSERIVCE, would match /myservice/**
	 */
	private boolean lowerCaseServiceId = false;

	private List<PredicateDefinition> predicates = new ArrayList<>();

	private List<FilterDefinition> filters = new ArrayList<>(); // 启用注册发现会默认添加一个filter：RewritePath，手动配置后会覆盖
```

GatewayProperties: @ConfigurationProperties("spring.cloud.gateway")
```
List<RouteDefinition> routes // 路由定义，使用注册发现不在这边配置
List<FilterDefinition> defaultFilters//默认的过滤器，使用注册发现时，不在这边配置
List<MediaType> streamingMediaTypes = Arrays.asList(MediaType.TEXT_EVENT_STREAM,
			MediaType.APPLICATION_STREAM_JSON);

```

HttpClientProperties: @ConfigurationProperties("spring.cloud.gateway.httpclient")
```
/** The connect timeout in millis, the default is 45s. */
	private Integer connectTimeout;

	/** The response timeout. */
	private Duration responseTimeout;

	/** Pool configuration for Netty HttpClient */
	private Pool pool = new Pool();

	/** Proxy configuration for Netty HttpClient */
	private Proxy proxy = new Proxy();

	/** SSL configuration for Netty HttpClient */
	private Ssl ssl = new Ssl();
```

LoadBalancerProperties: @ConfigurationProperties("spring.cloud.gateway.loadbalancer")
```
private boolean use404; // 是否使用404，负载失败时fallback相关, 可以与hystrix相结合
```

GlobalCorsProperties: @ConfigurationProperties("spring.cloud.gateway.globalcors")
```
final Map<String, CorsConfiguration>

CorsConfiguration: {
	@Nullable
    private List<String> allowedOrigins;
    @Nullable
    private List<String> allowedMethods;
    @Nullable
    private List<HttpMethod> resolvedMethods;
    @Nullable
    private List<String> allowedHeaders;
    @Nullable
    private List<String> exposedHeaders;
    @Nullable
    private Boolean allowCredentials;
    @Nullable
    private Long maxAge;
}
```

## GatewayControllerEndpoint
需要启用：actuator
敏感信息记得加security

```
spring:
  security:
    user:
      name: actuator
      password: actuator
management:
  endpoints:
    web:
      exposure:
        include: gateway
```
