个人练习实现：
**假设我们要实现一个分布式集群，这个集群是由节点 A、B、C 组成，提供只读 KV 存储服务。
你应该知道，创建只读变量的时候，必须要对它进行赋值，而且这个值后续没办法修改。
因此一个节点创建只读变量后就不能再修改它了，所以所有节点必须要先对只读变量的值达成共识，然后所有节点再一起创建这个只读变量。**



基于 **Basic Paxos** 算法，实现多节点之间如何就某个值（提案 Value）达成共识；只是简单的实现多节点之间的共识算法。该实现方法考虑的是
可用性（任何来自客户端的请求，不管访问哪个非故障节点，都能得到响应数据，但不保证是同一份最新数据）以及分区容错性(当节点间出现任意数量的消息丢失或高延迟的时候，系统仍然在继续工作)。