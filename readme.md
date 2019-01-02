[![Maven](https://img.shields.io/maven-central/v/com.github.dgroup/velocity.svg)](https://mvnrepository.com/artifact/com.github.dgroup/velocity)
[![Javadocs](http://www.javadoc.io/badge/com.github.dgroup/velocity.svg)](http://www.javadoc.io/doc/com.github.dgroup/velocity)
[![License: MIT](https://img.shields.io/github/license/mashape/apistatus.svg)](./license.txt) 
[![Commit activity](https://img.shields.io/github/commit-activity/y/dgroup/velocity.svg?style=flat-square)](https://github.com/dgroup/velocity/graphs/commit-activity)

[![Build Status](https://travis-ci.org/dgroup/laconic-velocity.svg?branch=master&style=for-the-badge)](https://travis-ci.org/dgroup/velocity)
[![0pdd](http://www.0pdd.com/svg?name=dgroup/velocity)](http://www.0pdd.com/p?name=dgroup/velocity)
[![Dependency Status](https://requires.io/github/dgroup/velocity/requirements.svg?branch=master)](https://requires.io/github/dgroup/velocity/requirements/?branch=master)
[![Known Vulnerabilities](https://snyk.io/test/github/dgroup/velocity/badge.svg)](https://snyk.io/org/dgroup/project/58b731a9-6b07-4ccf-9044-ad305ad243e6/?tab=dependencies&vulns=vulnerable)

[![DevOps By Rultor.com](http://www.rultor.com/b/dgroup/velocity)](http://www.rultor.com/p/dgroup/velocity)
[![EO badge](http://www.elegantobjects.org/badge.svg)](http://www.elegantobjects.org/#principles)
[![We recommend IntelliJ IDEA](http://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[![Qulice](https://img.shields.io/badge/qulice-passed-blue.svg)](http://www.qulice.com/)
[![SQ passed](https://sonarcloud.io/api/project_badges/measure?project=com.github.dgroup%3Avelocity&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.github.dgroup%3Avelocity)
[![Codebeat](https://codebeat.co/badges/f61cb4a4-660f-4149-bbc6-8b66fec90941)](https://codebeat.co/projects/github-com-dgroup-velocity-master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/011685357fc44898a8538d3e51d8da70)](https://www.codacy.com/app/dgroup/velocity?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=dgroup/velocity&amp;utm_campaign=Badge_Grade)
[![Codecov](https://codecov.io/gh/dgroup/velocity/branch/master/graph/badge.svg?token=Pqdeao3teI)](https://codecov.io/gh/dgroup/velocity)

**ATTENTION**: We're still in a very early alpha version, the API
may and _will_ change frequently. Please, use it at your own risk,
until we release version 1.0.

Maven:
```xml
<dependency>
    <groupId>com.github.dgroup</groupId>
    <artifactId>velocity</artifactId>
</dependency>
```
Gradle:
```groovy
dependencies {
    compile 'com.github.dgroup:velocity:<version>'
}
```
## Get started
**Generate the text/sql/xml/markdown/json/etc based on velocity [template](/src/main/java/com/github/dgroup/velocity/Template.java).**
 1. Define velocity template `query.sql`
    ```sql
    select 1 from dual
    #if ($flag)
    union
    select 2 from dual
    #end
    ```
    in
    ```
    velocity $ tree
    ...
    |-- src
    |   |-- main
    |   |   |-- ...
    |   |
    |   `-- test
    |       |-- java
    |       |   `-- ...
    |       `-- resources
    |           `-- velocity
    |               |-- ...
    |               |-- query.sql
    |               |-- ...
    ...

    ```
 2. Define instance of velocity template using
    - full path to template
      ```java
      @Test
      public void transformSql() throws TemplateException {
          MatcherAssert.assertThat(
              new Text("query.sql", "src/test/resources/velocity")
                  .compose(
                      new ArgOf("flag", true)
                   ),
              Matchers.equalTo(
                  "select 1 from dual\nunion\nselect 2 from dual\n"
              )
          );
      }
      ```
      See [more](/src/test/java/com/github/dgroup/velocity/template/TextTest.java).
    - hierarchical search
      ```java
      @Test
      public void hierarchical() throws TemplateException {
          MatcherAssert.assertThat(
              new Text("query.sql", "src/test/resources"))
                  .compose(
                      new ArgOf("flag", true)
                  ),
              Matchers.equalTo(
                  "select 1 from dual\nunion\nselect 2 from dual\n"
              )
          );
      }
      ```
      You can also specify the multiple roots ([more](/src/main/java/com/github/dgroup/velocity/template/Text.java#L64)).
    - classpath template
      ```java
      @Test
      public void classpath() throws TemplateException {
          MatcherAssert.assertThat(
              new Text(new RelativePath("velocity/query.sql"))
                  .compose(
                      new ArgOf("flag", true)
                  ),
              Matchers.equalTo(
                  "select 1 from dual\nunion\nselect 2 from dual\n"
              )
          );
      }
      ```
      See [more](/src/test/java/com/github/dgroup/velocity/template/ClasspathTest.java#L124).
