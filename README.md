Action Monitor
==============

The program monitors the inserts, updates and deletes in a database table and notifies all the browsers with a session
established every time the contents of the table change.

Main components used
--------------------

* Maven 3
* Spring Boot
  * Embedded Tomcat
  * Spring Data JPA: Hibernate + H2
  * Spring Integration: Apache ActiveMQ
  * Logging: sl4j + logback (by Spring Boot Logging)
  * Spring Websocket
  * Thymeleaf
* Mockito
* UI: Bootstrap + JQuery + Stomp over SockJS

How to build and run
--------------------

The application can be built using the following maven command:

```
$ mvn package
```

The resulting jar is in the target/ folder. It can be run as a runnable jar:

```
$ java -jar target/action-monitor-1.0-SNAPSHOT.jar
```

The application will be accessible on `http://localhost:8080/`. The user interface is pretty simple; it offers two pages:
* Listener: events will appear hear in colored boxes depending on what kind of changes have been made.
* Management: this page lists the entities in the system. A new one can be inserted using the input field and button.
  Existing ones can be edited or removed using the buttons.

Other details
-------------

There are some unit tests written mainly for the core logic (MagicService and ActionQueueHandler).
It can seem to be a bit over-engineered, in a real life application it would make more sense. However I did not
write tests for the controllers because they are really just invoking the service and putting the results into
the model of the view.

I wanted to make also an integration test for testing the queue, but could not find an elegant way to make assertions
after dequeuing.
