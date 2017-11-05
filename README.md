# entitylistener
spring scheduler and websocket based auto grid refresh implementation

### Installation

  - install MySQL 5.5 and MySQL workbench
  - restore db dump zip present in resources folder using mysql workbench
  - install eclipse and import current project using import maven project option in others
  - press Alt+F5 for maven build/install
  - download tomcat 8.5 server and add it to eclipse
  - add entitylistener project to server container
  - hit http://loccalhost:<YOUR_PORT>/entitylistener/index.html
  - perform CRUD operations at employee table, UI should reflect the changes.
