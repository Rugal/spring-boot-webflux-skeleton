# HOW TO CONTRIBUTE

### spring security

 * [security](https://www.marcobehler.com/guides/spring-security)
 * [architecture](https://spring.io/guides/topicals/spring-security-architecture)

### Naming Convention

1. WeChat
2. wechat

## Create OpenSSL key pair

### create RSA key pair

```shell
openssl genrsa -out keypair.pem 1024
```

### extract public key in x.509

```shell
openssl rsa -in keypair.pem -pubout -out publickey.crt
```

### extract private key in pkcs8

```shell
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out pkcs8.key
```
