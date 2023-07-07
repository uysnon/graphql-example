Что работает?
 - unit tests (h2)
 - graphiql (h2), url = http://localhost:8080/graphiql

Полезные ссылки:
1) documentation for \<groupId>org.springframework.graphql\</groupId> \<artifactId>spring-graphql\</artifactId>: https://docs.spring.io/spring-graphql/docs/current/reference/html/
2) спецификация для graphql над http: https://github.com/graphql/graphql-over-http/blob/main/spec/GraphQLOverHTTP.md

Инфо:
    либа поддерживает обработку запросов по HTTP, WebSocket, RSocket

За обработку HTTP requests отвечает класс GraphQlHttpHandler:
1) передает управление Inreception chain, для того чтобы быть включенным 
в эту цепочку обработчиков следует создать бин следующего вида


    class RequestHeaderInterceptor implements WebGraphQlInterceptor {
    
        @Override
        public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
            String value = request.getHeaders().getFirst("myHeader");
            request.configureExecutionInput((executionInput, builder) ->
                    builder.graphQLContext(Collections.singletonMap("myHeader", value)).build());
            return chain.next(request);
        }
    }

    @Controller
    class MyContextValueController {
    
        @QueryMapping
        Person person(@ContextValue String myHeader) {
            ...
        }
    }

* можно добавлять также кастомную логику в ответах


    class ResponseHeaderInterceptor implements WebGraphQlInterceptor {
    
        @Override
        public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) { 
            return chain.next(request).doOnNext(response -> {
                String value = response.getExecutionInput().getGraphQLContext().get("cookieName");
                ResponseCookie cookie = ResponseCookie.from("cookieName", value).build();
                response.getResponseHeaders().add(HttpHeaders.SET_COOKIE, cookie.toString());
            });
        }
    }
    
    @Controller
    class MyCookieController {
    
        @QueryMapping
        Person person(GraphQLContext context) { 
            context.put("cookieName", "123");
            ...
        }
    }

2) ExecutionGraphQlService отвечает за выполнение graphql запросов. Его конфигурировать проще всего через Bean GraphQlSource,
его Boot Starter предоставляет и загружает схему из директории. Для кастомизации
можно написать свой GraphQLSourceBuilderCustomizer. 

По дефолту GraphLQSource.Builder смотрит на classpath:graphql/** -> ".graphqls" | ".gqls". Т.е.
проще всего запихнуть в src/main/resources/graphql





    @Configuration(proxyBeanMethods = false)
    class GraphQlConfig {
    
        @Bean
        public GraphQlSourceBuilderCustomizer sourceBuilderCustomizer() {
            return (builder) ->
                    builder.configureGraphQl(graphQlBuilder ->
                            graphQlBuilder.executionIdProvider(new CustomExecutionIdProvider()));
        }
    }





