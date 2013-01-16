<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>
    <properties>
        <isis.version>1.0.0</isis.version>
        <isis-viewer-wicket.version>1.0.0</isis-viewer-wicket.version>
        <wicketstuff.version>6.0.0</wicketstuff.version>
    </properties>

    <parent>
        <groupId>org.apache.isis.extensions</groupId>
        <artifactId>wicket-view-gmap2</artifactId>
        <version>0.1-SNAPSHOT</version>
    </parent>

    <artifactId>wicket-view-gmap2-view</artifactId>
    <name>Wicket Viewer: Gmap2 View</name>

    <build>
        <resources>
            <resource>
                <filtering>false</filtering>
                <directory>src/main/resources</directory>
            </resource>
            <resource>
                <filtering>false</filtering>
                <directory>src/main/java</directory>
                <includes>
                    <include>**</include>
                </includes>
                <excludes>
                    <exclude>**/*.java</exclude>
                </excludes>
            </resource>
        </resources>
    </build>

    <dependencies>
    
        <dependency>
            <groupId>org.apache.isis.viewer</groupId>
            <artifactId>isis-viewer-wicket-impl</artifactId>
            <version>${isis-viewer-wicket.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.isis.core</groupId>
            <artifactId>isis-core-metamodel</artifactId>
            <version>${isis.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.isis.core</groupId>
            <artifactId>isis-core-runtime</artifactId>
            <version>${isis.version}</version>
        </dependency>
        <dependency>
            <groupId>org.wicketstuff</groupId>
            <artifactId>gmap2</artifactId>
            <version>1.4.8.1</version>
        </dependency>
        
        <dependency>
            <groupId>${project.groupId}</groupId>
            <artifactId>wicket-view-gmap2-applib</artifactId>
            <version>${project.version}</version>
        </dependency>
    </dependencies>

</project>
