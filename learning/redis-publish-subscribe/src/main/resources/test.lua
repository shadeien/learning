local size = redis.call('pubsub channels');
return size;