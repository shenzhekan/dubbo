#### 前言
    本文以zookeeper为注册中心，所以使用前先安装zookeeper，并在本机启动一个伪分布式集群


#### 搭建准备

1. 获得zookeeper包
    > http://mirror.bit.edu.cn/apache/zookeeper/zookeeper-3.4.14/zookeeper-3.4.14.tar.gz

2. 解压
    > tar zxvf zookeeper-3.4.14.tar.gz
    > mv ./zookeeper-3.4.14 /usr/local

3. 下面的操作需要root权限，当遇到访问限制时使用sudo

#### 开始搭建

1. 修改 zoo.cfg  
    > cd /usr/local/zookeeper-3.4.14/conf
    > mv zoo_sample.cfg zoo.cfg
    > vim zoo.cfg
```
# 心跳间隔
tickTime=2000
# 初始容忍的心跳数
initLimit=10
# 等待最大容忍的心跳数
syncLimit=5
# 本地数据保存目录
dataDir=/usr/local/zookeeper-3.4.14/zkData
# 客户端默认端口号
clientPort=2181

server.1=127.0.0.1:2888:3888
server.2=127.0.0.1:2889:3889
server.3=127.0.0.1:2890:3890
```

2. 配置数据目录  
    > 可以看到我们配置了dataDir，所以需要设置目录
    > cd /usr/local/zookeeper-3.4.14
    > mkdir zkData
    > vim myid   // 输入1

3. 启动
    > cd /usr/local/zookeeper-3.4.14/bin
    > ./zkServer.sh start

4. 同理开始搭建第二个服务
    > cd /usr/local
    > cp -rf zookeeper-3.4.14/ zookeeper01
    > cd zookeeper01/conf
    > vim zoo.cfg
```
# 本地数据保存目录
dataDir=/usr/local/zookeeper01/zkData
# 客户端默认端口号
clientPort=2182
```

5. 启动第二个服务
    > cd /usr/local/zookeeper01/zkData
    > vim myid   // 改为2
    > cd ..
    > vim myid   // 改为2
    > cd /bin
    > ./zkServer.sh start

6. 同理开始搭建第三个服务
    > cd /usr/local
    > cp -rf zookeeper-3.4.14/ zookeeper02
    > cd zookeeper02/conf
    > vim zoo.cfg
```
# 本地数据保存目录
dataDir=/usr/local/zookeeper02/zkData
# 客户端默认端口号
clientPort=2183
```

7. 启动第三那个服务
    > cd /usr/local/zookeeper02/zkData
    > vim myid   // 改为3
    > cd ..
    > vim myid   // 改为3
    > cd /bin
    > ./zkServer.sh start

8. 使用bin目录下的 **zkServer.sh** 查看三个服务的状态值
    > ./zkServer.sh staus
    > 当三个服务的状态值都为Mode: follower , Mode: follower, Mode: leader 时则启动完毕，开始测试

#### 服务测试

1. 登录一个客户端，并创建一个节点数据
    > cd /usr/local/zookeeper-3.4.14/bin
    > ./zkCli.sh -server localhost:2181
```
[zk: localhost:2181(CONNECTED) 0] ls /
[zookeeper, testNode]
[zk: localhost:2181(CONNECTED) 1] get /testNode
nodeTxt
cZxid = 0x200000002
ctime = Thu May 23 15:29:51 CST 2019
mZxid = 0x200000002
mtime = Thu May 23 15:29:51 CST 2019
pZxid = 0x200000002
cversion = 0
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x0
dataLength = 7
numChildren = 0
```

2. 登录第二个客户端，查看是否有同步节点
    > cd /usr/local/zookeeper01/bin
    > ./zkCli.sh -server localhost:2182
```
[zk: localhost:2183(CONNECTED) 0] ls /
[zookeeper, testNode]
[zk: localhost:2183(CONNECTED) 1] get /testNode
nodeTxt
cZxid = 0x200000002
ctime = Thu May 23 15:29:51 CST 2019
mZxid = 0x200000002
mtime = Thu May 23 15:29:51 CST 2019
pZxid = 0x200000002
cversion = 0
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x0
dataLength = 7
numChildren = 0
```

3. 结束测试（第三个就不重复了）具体代码在分支( shenzk )


