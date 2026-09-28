# For Java 11, try this

FROM tomcat:10.1.30
EXPOSE 8080
#COPY target/cms-manhattan-one-0.0.5.war /usr/local/tomcat/webapps/ROOT.war

#COPY target/cms-manhattan-one-0.0.12.war /usr/local/tomcat/webapps/cms.war
COPY target/cms-manhattan-one-0.0.54.war /usr/local/tomcat/webapps/ROOT.war

##COPY target/cms-manhattan-one-0.0.8.war /opt/apache-tomcat-7.0.90/webapps/ROOT.war

# FIX: the build failed here with "COPY failed: no source files were specified".
# There is no tomcat/ directory in the project, so the wildcard matched nothing
# and Docker aborted the image build after the WAR had already been packaged.
# Re-enable this line only once a tomcat/conf tree (server.xml, ...) is actually
# committed next to the Dockerfile.
#COPY tomcat/* /usr/local/tomcat/conf/

