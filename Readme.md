# Reproducer for native-image issue with yasson ResourceBundle

1. cd /tmp
2. git clone --branch yasson-resourcebundle https://github.com/zakkak/issue-reproducers
3. cd issue-reproducers
4. mvn package
5. native-image -jar target/reproducer-1.0-SNAPSHOT.jar