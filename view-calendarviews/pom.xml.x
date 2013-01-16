<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

  <modelVersion>4.0.0</modelVersion>
  <properties>
    <wicketstuff.version>6.0.0</wicketstuff.version>
    <isis.version>1.0.0</isis.version>
    <isis-objectstore-jdo.version>1.0.0</isis-objectstore-jdo.version>
    <isis-viewer-wicket.version>1.0.0</isis-viewer-wicket.version>
    <isis-viewer-restfulobjects.version>1.0.0</isis-viewer-restfulobjects.version>
  </properties>
  <parent>
    <groupId>org.apache.isis.extensions</groupId>
    <artifactId>wicket-views</artifactId>
    <version>0.1-SNAPSHOT</version>
  </parent>

  <artifactId>wicket-view-googlecharts</artifactId>
  <name>Wicket Viewer: Googlecharts</name>
  
  <packaging>pom</packaging>

  <dependencyManagement>
    <dependencies>
      <dependency>
	<groupId>org.apache.isis.extensions</groupId>
	<artifactId>wicket-view-googlecharts-applib</artifactId>
	<version>0.1-SNAPSHOT</version>
      </dependency>

      <dependency>
	<groupId>org.apache.isis.extensions</groupId>
	<artifactId>wicket-view-googlecharts-view</artifactId>
	<version>0.1-SNAPSHOT</version>
      </dependency>

      <dependency>
	<groupId>org.wicketstuff</groupId>
	<artifactId>googlecharts</artifactId>
	<version>${wicketstuff.version}</version>
      </dependency>

    </dependencies>
  </dependencyManagement>

  <modules>
    <module>applib</module>
    <module>view</module>
  </modules>

</project>
