# Roles API

## Summary

This project is a Java SpringBoot microservice that spins up a REST API, called Roles API, to improve upon the
capabilities of the Users API and the Teams API.

# How to run

## Docker

**Requirements**

- docker
- docker-compose

The solution can be executed with Docker and docker-compose. Since the build process needs to download all Maven
dependencies it can take some time to start.

```shell
docker-compose up --build
```

This will execute tests, build the jar, build the docker image and start the container. The first execution will take
longer since all maven dependencies will be downloaded. But next executions will run significantly faster as
dependencies are downloaded in a separate layer, thus cached. The `--build` triggers the Docker image build if there is
any change in the source code.

## Build from source

**Requirements**

- maven
- java 11

To `build`, `test`, `package`, `report` and `run` execute the command:

```shell
mvn clean package verify spring-boot:run
```

To check the coverage open `target/site/jacoco/index.html`


# How to develop

## Spotless

To check for any code style issue

```shell
mvn spotless:check
```

To apply the fixes for code style issues

```shell
mvn spotless:apply
```

# How to make an API call

- Following the steps above you will be able to build and run the API on port 8080 in your local environment, after that you will be able to make API call, which you can do like this:

1 - Import the local Postman Collection which you can find in the source the project

## Headers

In all endpoints you must have to make http requests with the header Accept:application/json. In http requests made with http verbs POST you need to set the header Content-Type:application/json.

### Roles Search - Feature
In order to use this feature, you must first attend to a requirement. First you need to create a Membership between a user and a Team, after that you will be able to search for Roles. In order to do so, you must follow the steps below:

- #1 - get the team Id


To get the list of teams, send a HTTP GET request to `/v1/teams/search` with the teamId and userId as QUERY params.


On success it will return a HTTP CODE 200 with a list of teams:

```json
[
    {
        "id": "7676a4bf-adfe-415c-941b-1739af07039b",
        "name": "Ordinary Coral Lynx"
    }
] 
```
- #2 - get the user Id

After that you can copy a random team id from the list and hit the endpoint `/v1/teams/{teamId}`

On success it will return a HTTP CODE 200 with a the details of the team:

```json
{
    "id": "7676a4bf-adfe-415c-941b-1739af07039b",
    "name": "Ordinary Coral Lynx",
    "teamLeadId": "b12fa35a-9c4c-4bf9-8f32-27cf03a1f190",
    "teamMemberIds": [
        "371d2ee8-cdf4-48cf-9ddb-04798b79ad9e",
        "54383a18-425c-4f50-9424-1c4c27e776dd",
        "e0dba3dc-313d-4648-bd9c-4ddc8b189e84",
        "b047d3f4-3469-47ce-a03f-1637a6de036b",
        "ee91a519-fefa-48a7-bdf7-672bde38aef9",
        "197c2b23-1218-44d0-b6b8-d757ba004515",
        "e947058e-2d5f-47d9-925b-27bcab14c38e"
    ]
}
```

- #3 - get the role Id

To get a role id you must call the endpoint GET `/v1/roles`

On success it will return a HTTP CODE 200 with the list of available roles:

```json
[
    {
        "id": "1b3c333b-36e7-4b64-aa15-c22ed5908ce4",
        "name": "Developer"
    },
]
```
- #4 - Create a membership

Then, with the team members id (userId) and the teamId in your hands you can create a Membership by hitting the HTTP POST endpoint `/v1/roles/memberships` with the body 

```json

{
    "roleId": "1b8c333b-36e7-4b64-aa15-c22ed5908ce4",
    "userId": "371d2ee8-cdf4-48cf-9ddb-04798b79ad9e",
    "teamId": "7676a4bf-adfe-415c-941b-1739af07039b"
}
```

- 
On success it will return a HTTP CODE 200 with the body of membership you just created

```json
{
    "id": "a5f46497-dc07-428c-9ee4-bab09d279777",
    "roleId": "1b3c333b-36e7-4b64-aa15-c22ed5908ce4",
    "userId": "371d2ee8-cdf4-48cf-9ddb-04798b79ad9e",
    "teamId": "7676a4bf-adfe-415c-941b-1739af07039b"
}
```


- #5 - Search Roles

Then, finally you are able to search roles by userId and teamId. To do that send a HTTP GET request to `/v1/roles/search` with the teamId and userId as QUERY params.

- 
On success it will return a HTTP CODE 200 with the body of The Role entity:

```json
{
	"id": "1b8c333b-36e7-4b64-aa15-c22ed5908ce4",
	"name": "Developer",
}
```

PS: If you send a invalid value or invalid request in any of these steps, the API will return a 400/404/500 HTTP CODE and a friendly message for you to fix your request.