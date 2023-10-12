# raizekusu: Mealary authentication Service

## build

### spring boot plugin

spring boot maven plugin is a recommended approach.  

```shell
mvn spring-boot:build-image
```

### docker

Alternatively can use docker directly.  

```shell
docker build . -t ${IMAGE}:${TAG}
```

## run

### kubernetes

```shell
# create k8s resource
kubectl apply -f configuration/container/deployment.yml
```

### docker

```shell
# use docker directly
docker run --rm -p 8080:8080 ${IMAGE}:${TAG}
# or use docker-compose
docker-compose up -d
```

## test

### create token

```shell
# request and parse token
TOKEN=$(echo '{"operationName":null,"variables":{},"query":"mutation { createToken(id: 1, password:\"1\")   }"}' | http POST $INGRESS_HOST/graphql Host:raizekusu.api | jq -r '.data.createToken')
# decode JWS
echo $TOKEN | jwt decode -
```

### vaidate token

```shell
echo '{"operationName":null,"variables":{},"query":"{  validateToken   }"}' | http POST $INGRESS_HOST/graphql Host:raizekusu.api Authorization:"Bearer $TOKEN"
```

### get user

```shell
echo '{"operationName":null,"variables":{"id":6 },"query":"query getUser($id: Int!) {getUser(id: $id) { id username secret email status} }  "}' | http POST $INGRESS_HOST/graphql Host:raizekusu.api Authorization:"Bearer $TOKEN
```
