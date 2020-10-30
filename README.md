## Quarkus-Securewith-Keycloak
Using Keycloak Authorization Services and Policy Enforcer to Protect JAX-RS Endpoints.

This demo uses Keycloak, which is Open Source Identity and Access Management Server, which is a OAuth2 and OpenID Connect(OIDC) protocol complaint

## POC:

```
1) Deploy Keycloak on Kubernetes Cluster and configure Realm
2) Build, Deploy and product JAX-RS Endpoint using Keycloak
3) Test JAX-RS Endpoints
4) Uninstall the setup
```

## Keycloak Configuration

```
step 1: Create keycloak in kubernetes cluster
  
  kubectl create -f https://raw.githubusercontent.com/keycloak/keycloak-quickstarts/latest/kubernetes-examples/keycloak.yaml

Step 2: create realm using the 'quarkus-realm.json' which is available under 'keycloak-config' folder
```

## Build and Deploy

```
step 1: git clone https://github.com/mosesalphonse/Quarkus-Securewith-Keycloak.git

cd Quarkus-Securewith-Keycloak

Step 2: Configure application.properties

quarkus.oidc.auth-server-url=http://{keycloakip}:{port}/auth/realms/quarkus
quarkus.container-image.group={moses-286120}

Step 3: Build and deploy

mvn clean compile package -Dquarkus.container-image.push=true  // Build and push the image into GCR

kubectl create -f target/kubernetes/kubernetes.yml // Deploy into kubernetes cluster

```

## Test

```
Step 1: Test Unprotected Endpoint

curl http://34.70.21.16:8080/api/public
curl http://34.70.21.16:8080/api/test

Step 2: Test protected Endpoint with User Role

export access_token=$(\
    curl -X POST http://{keycloak:port}/auth/realms/quarkus/protocol/openid-connect/token \
    --user backend-service:secret \
    -H 'content-type: application/x-www-form-urlencoded' \
    -d 'username=alice&password=alice&grant_type=password' | jq --raw-output '.access_token' \
 )
curl -v -X GET \
  http://{quarkusinstance:port}/api/users/me \
  -H "Authorization: Bearer "$access_token

curl -v -X GET \
  http://{quarkusinstance:port}/api/test \
  -H "Authorization: Bearer "$access_token

Step 3: Test protected Endpoint with Admin Role

export access_token=$(\
    curl -X POST http://{keycloak:port}/auth/realms/quarkus/protocol/openid-connect/token \
    --user backend-service:secret \
    -H 'content-type: application/x-www-form-urlencoded' \
    -d 'username=admin&password=admin&grant_type=password' | jq --raw-output '.access_token' \
 )
 
 curl -v -X GET \
   http://{quarkusinstance:port}/api/admin \
   -H "Authorization: Bearer "$access_token

curl -v -X GET \
  http://{quarkusinstance:port}/api/users/me \
  -H "Authorization: Bearer "$access_token

curl -v -X GET \
  http://{quarkusinstance:port}/api/test \
  -H "Authorization: Bearer "$access_token
  
```
## Uninstall the above setup

```
kubectl delete -f target/kubernetes/kubernetes.yml // undeploy this quarkus application

kubectl delete -f https://raw.githubusercontent.com/keycloak/keycloak-quickstarts/latest/kubernetes-examples/keycloak.yaml // undeploy this Keycloak
```

Note: This code was tested in Google Kubernetes Platform
