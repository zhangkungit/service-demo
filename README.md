# service-demo
技术语言工程
## demo-user
包含
    1 dbsync服务，基于canal实现数据（支持分库分表）进行同步，其HA机制基于canal的HA机制
    2 id服务，发号器，生成分布式唯一id，目前提供3中实现
        2.1 Twitter的snakeflow算法生成唯一id，生成18唯一id
        2.2 基于缓冲池技术实现，双缓冲池 主缓冲池mainBuf与预缓冲池preBuf，
        初始时mainBuf与preBuf都从db中取一段id，以后发号直接从mainBuf，
        mainBuf取完先用preBuf的，preBuf异步从db从db中load新的id发号区间
        2.3 基于znode实现，zookpeer会为每一个服务发唯一的id，各服务以此id为前缀唯一发号
    3     

