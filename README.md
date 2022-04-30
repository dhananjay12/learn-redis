# learn-redis

## Start Redis

Run docker image

```
docker-compose up -d redis
```

To go inside the container
```
docker exec -it redis bash
```

### Basic commands

https://redis.io/commands/

Start cli - `redis-cli`

See all keys - `keys *`

Check - `ping`

Get - `get <key>`

Check type - `type <key>`

Hash product key - `hget <hash-key> <key>`

Flush All - `flushdb`


