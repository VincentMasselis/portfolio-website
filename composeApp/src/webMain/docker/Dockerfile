FROM nginx:alpine
COPY composeApp/src/webMain/docker/nginx.conf /etc/nginx/conf.d/default.conf
COPY composeApp/build/dist/composeWebCompatibility/productionExecutable/ /usr/share/nginx/html/
EXPOSE 80

# docker build -f composeApp/src/webMain/docker/Dockerfile -t portfolio .