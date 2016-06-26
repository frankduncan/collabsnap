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
