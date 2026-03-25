FROM nginx:alpine
COPY composeApp/src/wasmJsMain/docker/nginx.conf /etc/nginx/conf.d/default.conf
COPY composeApp/build/dist/wasmJs/productionExecutable/ /usr/share/nginx/html/
COPY composeApp/src/commonMain/composeResources/files/resume.pdf /usr/share/nginx/html/resume.pdf
EXPOSE 80

# docker build -f composeApp/src/wasmJsMain/docker/Dockerfile -t portfolio .