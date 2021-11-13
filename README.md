# text-checker

Make basic validations on a variety of text files.

```xml
<build>
  <plugins>
    <plugin>
      <groupId>se.vandmo</groupId>
      <artifactId>text-checker-maven-plugin</artifactId>
      <version>0.22</version>
      <executions>
        <execution>
          <id>check</id>
          <phase>validate</phase>
          <goals>
            <goal>check</goal>
          </goals>
          <configuration>
            <excludes>
              <exclude>dist/</exclude>
            </excludes>
          </configuration>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```
