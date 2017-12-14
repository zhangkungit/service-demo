package com.yryz.common.brave;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.EmptySpanCollectorMetricsHandler;
import com.github.kristofa.brave.http.HttpSpanCollector;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2017/12/11
 * @description
 */
public class ZipkinBraveFactoryBean implements FactoryBean<Brave> {

    private String serviceName;
    private String zipkinHost;

    private Brave instance;

    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    public void setZipkinHost(final String zipkinHost) {
        this.zipkinHost = zipkinHost;
    }

    private void createInstance() {
        if (this.serviceName == null) {
            throw new BeanInitializationException("Property serviceName must be set.");
        }

        Brave.Builder builder = new Brave.Builder(this.serviceName);
        if (this.zipkinHost != null && !"".equals(this.zipkinHost)) {
            builder.spanCollector(HttpSpanCollector.create(
                    this.zipkinHost, new EmptySpanCollectorMetricsHandler()));
        }
        this.instance = builder.build();
    }

    @Override
    public Brave getObject() throws Exception {
        if (this.instance == null) {
            this.createInstance();
        }
        return this.instance;
    }

    @Override
    public Class<?> getObjectType() {
        return Brave.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
