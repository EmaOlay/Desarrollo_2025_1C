# .devcontainer/Dockerfile
FROM mcr.microsoft.com/devcontainers/universal:2

# Install MSSQL tools (sqlcmd and bcp)
RUN sudo apt-get update && \
    sudo apt-get install -y curl apt-transport-https && \
    curl https://packages.microsoft.com/keys/microsoft.asc | sudo apt-key add - && \
    curl https://packages.microsoft.com/config/ubuntu/22.04/prod.list | sudo tee /etc/apt/sources.list.d/mssql-release.list && \
    sudo apt-get update && \
    sudo ACCEPT_EULA=Y apt-get install -y mssql-tools unixodbc-dev

# [Optional] Uncomment to install a specific version of Node.js if you need it
# RUN sudo apt-get update && sudo apt-get install -y nodejs npm

# [Optional] Install any other tools or dependencies needed for your app