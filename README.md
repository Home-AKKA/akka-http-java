
akka-http-java
---

* https://github.com/Ordina-JTech/akka-http-java
* https://doc.akka.io/docs/akka-http/current/routing-dsl/directives/file-upload-directives/fileUpload.html

? https://doc.akka.io/docs/akka/2.5/stream/stream-io.html


1. `http://localhost:8080`  GET
    ```text
    HALLO
    ```

2. `http://localhost:8080/groups`  GET
    ```json
    [
        {
            "name": "BLa bla",
            "uuid": "d0926864-e5e7-4bca-8067-d05eb7c725e9"
        },
        {
            "name": "Two two",
            "uuid": "ec467c91-c2d7-4240-8bca-46ad9302ad2a"
        }
    ]
    ```

3. `http://localhost:8080/groups/d0926864-e5e7-4bca-8067-d05eb7c725e9`  GET
    ```json
    {
      "name": "BLa bla",
      "uuid": "d0926864-e5e7-4bca-8067-d05eb7c725e9"
    }
    ```

4. `http://localhost:8080/groups`  POST  [Content-Type : application/json]
    ```json
    {
        "name": "One one"
    }
    ```
    201

5. `http://localhost:8080/groups/ee0f7ca1-b7c8-422c-bb4d-9b0b0f491a7a`  PUT  [Content-Type : application/json]
    ```json
    {
        "name": "One-1 one-1",
        "uuid": "ee0f7ca1-b7c8-422c-bb4d-9b0b0f491a7a"
    }
    ```
    200

6. `http://localhost:8080/groups/ee0f7ca1-b7c8-422c-bb4d-9b0b0f491a7a`  PUT  [Content-Type : application/json]
    ```json
    {
    }
    ```
    400

7. `http://localhost:8080/groups/ee0f7ca1-b7c8-422c-bb4d-9b0b0f491a7a`  DELETE
    200

8. `http://localhost:8080/groups/csv-upload`  POST
    from-data: `File = sample-10-rows.csv`
    201

* `www.sample-videos.com` [https://www.sample-videos.com/csv](https://www.sample-videos.com/csv)

![akka-http-csv-upload](kFxyI.jpg)

