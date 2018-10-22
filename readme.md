## Laconic API for Apache Velocity
[![Maven](https://img.shields.io/maven-central/v/com.github.dgroup/velocity.svg)](https://mvnrepository.com/artifact/com.github.dgroup/velocity)
[![Javadocs](http://www.javadoc.io/badge/com.github.dgroup/velocity.svg)](http://www.javadoc.io/doc/com.github.dgroup/velocity)
[![License: MIT](https://img.shields.io/github/license/mashape/apistatus.svg)](./license.txt) 
[![Commit activity](https://img.shields.io/github/commit-activity/y/dgroup/velocity.svg?style=flat-square)](https://github.com/dgroup/velocity/graphs/commit-activity)

[![Build Status](https://travis-ci.org/dgroup/velocity.svg?branch=master&style=for-the-badge)](https://travis-ci.org/dgroup/velocity)
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

1. Add mvn dependency
    ```xml
    <dependency>
        <groupId>com.github.dgroup</groupId>
        <artifactId>velocity</artifactId>
        <version>0.1.0</version>
    </dependency>
    ```
2. Define velocity template `query.sql`
    ```sql
    select 1 from dual
    #if ($flag)
    union
    select 2 from dual
    #end
    ```
3. Define instance of velocity template
    ```java
    @Test
        public void transformSql() throws RsException {
            MatcherAssert.assertThat(
                new RsText(
                    "query.sql",
                    new PathOf("src{0}test{0}resources")
                ).compose(
                    new ArgOf("flag", true)
                ),
                Matchers.equalTo("select 1 from dual\nunion\nselect 2 from dual\n")
            );
        }
    ```
    where [RsText](/src/main/java/com/github/dgroup/velocity/rs/RsText.java) represents Apache Velocity resource
    ```java
    package com.github.dgroup.velocity;

    import ...

    /**
     * Velocity resource for text generation (HTML,SQL,XML,etc).
     *
     * Reed more about Apache Velocity at
     *  http://velocity.apache.org/engine/1.7/user-guide.html.
     *
     * @param <T> Type of resource.
     * @since 0.1.0
     */
    public interface Resource<T> {

        /**
         * Transform the velocity template to HTML/SQL/etc using velocity variables.
         * @param args The velocity variables for template.
         * @return Text, XML, JSON, SQL, HTML, etc
         * @throws RsException in case template format error.
         */
        T compose(Arg... args) throws RsException;

        /**
         * Transform the velocity template to HTML/SQL/etc using velocity variables.
         *
         * @param args The velocity variables for template.
         * @return Text, XML, JSON, SQL, HTML, etc
         * @throws RsException in case template format error.
         */
        T compose(Iterable<Arg> args) throws RsException;

        ...
    }

    ```