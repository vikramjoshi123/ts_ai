steps:
  - uses: actions/checkout@v3
    name: Check out code

  - uses: mr-smithers-excellent/docker-build-push@v6
    name: Build & push Docker image
    with:
      image: repo/image
      tags: v1, latest
      registry: registry-url.io
      dockerfile: Dockerfile.ci
      username: ${{ secrets.DOCKER_USERNAME }}
      password: ${{ secrets.DOCKER_PASSWORD }}