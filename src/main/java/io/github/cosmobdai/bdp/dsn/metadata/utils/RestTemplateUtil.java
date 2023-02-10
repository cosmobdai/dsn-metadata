package io.github.cosmobdai.bdp.dsn.metadata.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.ExtractingResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * @author mahe
 * @date 2021/8/20 18:15
 */
@Slf4j
public class RestTemplateUtil {

    private static final RestTemplate REST_TEMPLATE = createRestTemplate();

    private RestTemplateUtil() {
    }

    private static RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return initRestTemplate(restTemplate);
    }

    /**
     * 初始化RestTemplate对象
     *
     * @return RestTemplate
     */
    private static RestTemplate initRestTemplate(RestTemplate restTemplate) {
        try {
            restTemplate.setRequestFactory(clientHttpRequestFactory());
        } catch (Exception e) {
            log.error("设置Request工厂异常：", e);
        }
        restTemplate.setErrorHandler(new ExtractingResponseErrorHandler());
        List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> httpMessageConverter : list) {
            if (httpMessageConverter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) httpMessageConverter).setDefaultCharset(StandardCharsets.UTF_8);
                break;
            }
        }
        return restTemplate;
    }

    private static HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new org.apache.http.ssl.TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                return true;
            }
        }).build();
        httpClientBuilder.setSSLContext(sslContext);
        HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                hostnameVerifier);
        // 注册http和https请求
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslConnectionSocketFactory).build();
        // 开始设置连接池
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(
                socketFactoryRegistry);
        // 最大连接数3000
        poolingHttpClientConnectionManager.setMaxTotal(2000);
        // 同路由并发数100
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(100);
        httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
        // 重试次数
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(1, true));
        HttpClient httpClient = httpClientBuilder.build();
        // httpClient连接配置
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                httpClient);
        // 连接超时
        clientHttpRequestFactory.setConnectTimeout(10 * 1000);
        // 数据读取超时时间
        clientHttpRequestFactory.setReadTimeout(30 * 1000);
        // 连接不够用的等待时间
        clientHttpRequestFactory.setConnectionRequestTimeout(5 * 1000);
        return clientHttpRequestFactory;
    }

    /**
     * Http请求构建器
     *
     * @param url        URL
     * @param httpMethod METHOD
     * @param t          RequestBody
     * @param <T>        RequestBody 类型
     * @return JSONObject
     */
    public static <T> JSONObject exchange(String url, HttpMethod httpMethod, T t) {
        return exchange(url, httpMethod, t, null);
    }

    /**
     * Http请求构建器
     *
     * @param url        URL
     * @param httpMethod METHOD
     * @param t          RequestBody
     * @param <T>        RequestBody 类型
     * @param mediaType  类型
     * @return JSONObject
     */
    public static <T> JSONObject exchange(String url, HttpMethod httpMethod, T t, MediaType mediaType) {
        HttpEntity<T> requestEntity;
        if (null == mediaType) {
            requestEntity = new HttpEntity<>(t);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(mediaType);
            requestEntity = new HttpEntity<>(t, headers);
        }
        ResponseEntity<String> exchange = REST_TEMPLATE.exchange(url, httpMethod, requestEntity, String.class);
        String body = exchange.getBody();
        if (StringUtils.isBlank(body)) {
            return null;
        }
        try {
            return JSONObject.parseObject(body);
        } catch (Exception ex) {
            return JSONObject.parseObject("{\"data\":" + "\"" + body + "\"" +"}");
        }
    }

    /**
     * Http请求构建器
     *
     * @param url        URL
     * @param httpMethod METHOD
     * @param <T>        RequestBody 类型
     * @return JSONObject
     */
    public static <T> JSONObject exchange(String url, HttpMethod httpMethod) {
        return exchange(url, httpMethod, null);
    }
}
