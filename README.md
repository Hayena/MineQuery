MineQuery
=========

This is a Scala implementation of the so called MineQuery.
It sends a request to a minecraft server and allows you to show certain statistics.

Usage
=========
```Scala
var query = new MineQuery("127.0.0.1", 25565)
```
Both parameters are optional, first one always refers to 127.0.0.1, the second one to 25565

The MineQuery class has two diffrent methods for obtaining data.
```Scala
query.getStatus
```
This contains the following information:
+ Message of the Day
+ Gamemode
+ Name of the main map
+ Currently online players
+ Maximum allowed players
+ Port the server is running on
+ Hostname the server is running on

```Scala
query.getFullStatus
```
This contains the same information above but also includes:
+ Game ID (Always "Minecraft")
+ Version of server
+ List of Players
