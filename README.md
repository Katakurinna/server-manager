# Server Manager
## First steps
#### 1. Install mysql server
#### 2. Create user for servermanager
Example:

username: root

password: [Random password generator](https://passwordsgenerator.net/). 

#### 3. Configure service
Configure [example-application.properties](src/main/resources/example-application.properties) 
Rename `example-application.properties` to `application.properties`
#### 4. Add a new server to database
```http request
POST http://localhost:8080/api/v1/servers/server/new
{
    "address": "server ip",
    "port": 28016,
    "password": "password",
    "name": "server name"
}
```