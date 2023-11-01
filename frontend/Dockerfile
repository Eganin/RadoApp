#docker run --name some-nginx -v C:\Users\rafae\docker_compose\web_frontend:/usr/share/nginx/html:ro -d -p 8081:80 nginx
FROM nginx
COPY . /usr/share/nginx/html/
WORKDIR /usr/share/nginx/html/
RUN nginx