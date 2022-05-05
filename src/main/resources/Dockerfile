FROM nginx
COPY index.html /usr/share/nginx/html/read.html
RUN sed -i '16 a text/markdown   md;' /etc/nginx/mime.types \
    && sed -i '4 a charset utf-8;' /etc/nginx/conf.d/default.conf