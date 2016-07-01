An experimental project to create a general purpose library allowing multiple SNAP sessions to communicate with each other via a central server.

# Booting the http server

To boot the server using maven, run:

```bash
mvn jetty:run
```

# Loading up SNAP from jetty

In a local webbrowser, load up ```localhost:8080/snap/snap.html```

# Loading the collabsnap SNAP block library

From inside snap, go to File->Import and load up <projecthome>/src/main/snap/collabsnap.cml

## Using collabsnap blocks

### collabsnap-ping-server

Use this block to send a simple ping to the server, which should create some kind of output in the jetty log for now.

### collabsnap-poll

Use this block to poll server each second for news of a new message.

### collabsnap-serialize-sprite

Use this block to get the xml serialization of the passed in sprite, useful with "my[self]" sense block.

### collabsnap-send-to-server

Use this block to send an object to the server.  Most useful with collabsnap-serialize-sprite.
