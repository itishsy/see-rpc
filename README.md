# see-rpc
一个轻量级RPC底层框架

### 功能简介

- 基于Netty底层tcp通信，使用Kyro序列化与反序列化操作
- Zookeeper注册中心
- 负载均衡（轮循、加权轮循、最小压力）
- 熔断、降级
- Spring SDK bean配置 与 Spring Boot自动注入
