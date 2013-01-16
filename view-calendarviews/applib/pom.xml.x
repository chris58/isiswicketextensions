<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">

    <modelVersion>4.0.0</modelVersion>
    <properties>
        <isis.version>1.0.0</isis.version>
        <isis-objectstore-jdo.version>1.0.0</isis-objectstore-jdo.version>
        <isis-viewer-wicket.version>1.0.0</isis-viewer-wicket.version>
        <isis-viewer-restfulobjects.version>1.0.0</isis-viewer-restfulobjects.version>
    </properties>

    <parent>
        <groupId>org.apache.isis.extensions</groupId>
        <artifactId>wicket-view-gmap2</artifactId>
        <version>0.1-SNAPSHOT</version>
    </parent>

    <artifactId>wicket-view-gmap2-applib</artifactId>
    <name>Wicket Viewer: Gmap2 AppLib</name>

    <dependencies>
        <dependency>
            <groupId>org.apache.isis.core</groupId>
            <artifactId>isis</artifactId>
            <version>${isis.version}</version>
            <type>pom</type>
        </dependency>
        <dependency>
            <groupId>org.apache.isis.core</groupId>
            <artifactId>isis-core-applib</artifactId>
            <version>${isis.version}</version>
            <type>jar</type>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.10</version>
            <scope>test</scope>
            <type>jar</type>
        </dependency>
    </dependencies>

</project>
