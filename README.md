# Rock Paper Scissor

This is a Web Application simulating the popular game.

## Build

To create a docker image of the web application use the following command:

```gradle bootBuildImage```

## Frontend

The [frontend sources](
https://github.com/SvenKuhlmann/rps_frontend
) are stored in a different git.

Build the frontend via script:

```./build_frontend```

## Hosting

### Docker

Run the application using docker:

```docker run -p 8080:8080 docker.dataenv.de/game:0.0.1-SNAPSHOT```

The application will be reachable at http://localhost:8080

### Kubernetes

To deploy the game into your kubernetes cluster use the helm chart.
In the helm directory use the command:

```helm install rock-paper-scissor .```

The application will be reachable at http://rock-paper-scissor .



