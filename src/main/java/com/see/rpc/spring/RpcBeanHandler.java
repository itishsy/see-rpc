package com.see.rpc.spring;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by Administrator on 2018/12/7.
 */
public class RpcBeanHandler extends NamespaceHandlerSupport implements BeanDefinitionParser {

    static {
        System.out.println("  	 ___  ___  ___       _ __ _ __   ___ 	  ");
        System.out.println("  	/ __|/ _ \\/ _ \\ ___ | `__| `_ \\ / __|	  ");
        System.out.println("  	\\__ \\  __/  __/ ___ | |  | |_) | (__ 	  ");
        System.out.println("  	|___/\\___|\\___|     |_|  | .__/ \\___|	  ");
        System.out.println("  	ArchTeam · 2018.10      |_|         	  ");
    }

    @Override
    public void init() {
        registerBeanDefinitionParser("rpc", this::parse);
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String hosts = element.getAttribute("hosts");
        String scope = element.getAttribute("scope");
        if (StringUtils.isBlank(hosts)) {
            throw new IllegalArgumentException("hosts is not null");
        }

        GenericBeanDefinition definition = new GenericBeanDefinition();
        definition.setBeanClass(RpcBootstrap.class);
        definition.getPropertyValues().add("hosts", hosts);
        definition.getPropertyValues().add("scope", scope);
        parserContext.getRegistry().registerBeanDefinition("rpcBootstrap", definition);

        GenericBeanDefinition registryBeanDefinition = new GenericBeanDefinition();
        registryBeanDefinition.setBeanClass(AbstractBootstrap.class);

        return definition;
    }
}
