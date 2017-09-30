FROM tomee:8-jre-7.0.3-plus

# Build with : docker build -t petitroll/tcs-rest-hotel .
# Publish with: docker push petitroll/tcs-rest-hotel

WORKDIR /usr/local/tomee/

COPY ./target/tcs-service-rest-hotel.war ./webapps/.

HEALTHCHECK --interval=5s CMD curl --fail http://localhost:8080/ || exit 1


EXPOSE 8080
