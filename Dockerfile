# For Java 11, try this

FROM tomcat:10.1.30
EXPOSE 8080

# Gzip compression on the HTTP connector (port 8080) in the image's own conf/server.xml.
#   compression="on"         Tomcat gzips text responses (HTML, CSS, JS, XML, JSON)
#                            when the browser sends "Accept-Encoding: gzip"; every browser does.
#   compressionMinSize=2048  responses under 2 KB are sent as they are.
#   useSendfile="false"      otherwise static files over 48 KB (jquery.js, big CSS)
#                            are sent uncompressed, because sendfile takes precedence.
# The grep stops the build if the connector line was not found, so the image can
# never ship silently without compression.
# This step sits before the WAR copy, so Docker reuses it on every rebuild.
RUN sed -i 's|<Connector port="8080" protocol="HTTP/1.1"|<Connector port="8080" protocol="HTTP/1.1" compression="on" compressionMinSize="2048" useSendfile="false"|' /usr/local/tomcat/conf/server.xml \
 && grep -q 'compression="on"' /usr/local/tomcat/conf/server.xml

#COPY target/cms-manhattan-one-0.0.5.war /usr/local/tomcat/webapps/ROOT.war

#COPY target/cms-manhattan-one-0.0.12.war /usr/local/tomcat/webapps/cms.war
COPY target/cms-manhattan-one-0.0.55.war /usr/local/tomcat/webapps/ROOT.war

##COPY target/cms-manhattan-one-0.0.8.war /opt/apache-tomcat-7.0.90/webapps/ROOT.war

# FIX: the build failed here with "COPY failed: no source files were specified".
# There is no tomcat/ directory in the project, so the wildcard matched nothing
# and Docker aborted the image build after the WAR had already been packaged.
# Re-enable this line only once a tomcat/conf tree (server.xml, ...) is actually
# committed next to the Dockerfile.
#COPY tomcat/* /usr/local/tomcat/conf/