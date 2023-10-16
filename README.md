# Spring Boot Web Flux Skeleton

1. test
   1. unit
   2. integration
2. endpoint
3. r2dbc


## test


```shell
echo '{"operationName":null,"variables":{ "id": 1   },"query":"query getTag($id: Int!) {  getTag(id: $id) { id name}   }"}' | http POST 127.0.0.1:8080/graphql
```
