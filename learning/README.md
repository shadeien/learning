# introduction

本工程包含两个模块，一个是基于redis的动态订阅/发布实现，另一个是基于zookeeper的动态选举。

## redis-publish-subscribe

## zk-leader-selector

zk选举需要启两个服务（两个端口），然后在selectorController中有个mapping可以把本机下掉，然后另一个服务可以抢到leader。
