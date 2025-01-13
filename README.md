# Reproducer showing differences in dependency resolution between 3.x and 4.x

- Build this small extension `./mvnw clean install`
  Use this extension in a project by `.mvn/extensions.xml`:
  ```
  <extensions>
    <extension>
    <groupId>org.example</groupId>
    <artifactId>maven4-deps-reproducer</artifactId>
    <version>1.0-SNAPSHOT</version>
    </extension>
  </extensions>
  ```
- Use Maven 3.9.9 wrapper: `mvn wrapper:wrapper -Dmaven=3.9.9` and run build `./mvnw clean package`
See in stdout the resolved requests and their origin, amongst them see:
```
org.apache:apache:pom:13|Plugin
org.apache:apache:pom:15|Plugin
org.apache:apache:pom:18|Plugin
org.apache:apache:pom:23|Plugin
org.apache:apache:pom:25|Plugin
org.apache:apache:pom:26|Plugin
org.apache:apache:pom:29|Plugin
org.apache:apache:pom:30|Plugin
org.apache:apache:pom:31|Plugin
org.apache:apache:pom:32|Plugin
```
- Use Maven 4.0.0-rc-2 wrapper: `mvn wrapper:wrapper -Dmaven=4.0.0-rc-2` and run build `./mvnw clean package`
See in stdout the resolved requests and their origin, amongst them see:
```
[INFO] [stdout] org.apache:apache:pom:13|ArtifactRequest
[INFO] [stdout] org.apache:apache:pom:15|ArtifactRequest
[INFO] [stdout] org.apache:apache:pom:15|ArtifactRequest
[INFO] [stdout] org.apache:apache:pom:18|ArtifactRequest
[INFO] [stdout] org.apache:apache:pom:23|ArtifactRequest
[INFO] [stdout] org.apache:apache:pom:23|ArtifactRequest
[INFO] [stdout] org.apache:apache:pom:23|ArtifactRequest
[INFO] [stdout] org.apache:apache:pom:25|ArtifactRequest
[INFO] [stdout] org.apache:apache:pom:26|ArtifactRequest
[INFO] [stdout] org.apache:apache:pom:29|ArtifactRequest
[INFO] [stdout] org.apache:apache:pom:30|ArtifactRequest
[INFO] [stdout] org.apache:apache:pom:31|ArtifactRequest
[INFO] [stdout] org.apache:apache:pom:31|ArtifactRequest
[INFO] [stdout] org.apache:apache:pom:31|ArtifactRequest
[INFO] [stdout] org.apache:apache:pom:31|ArtifactRequest
[INFO] [stdout] org.apache:apache:pom:31|ArtifactRequest
[INFO] [stdout] org.apache:apache:pom:32|ArtifactRequest
```

See that with Maven 4 we see more requests, as well as the origin not being from Plugin resolution.
