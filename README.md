An experimental project to create a general purpose library allowing multiple SNAP sessions to communicate with each other via a central server.

# Booting the http server

## Using the latest commit

To boot the server using maven, run:

```bash
mvn jetty:run
```

## Using the released war

You can boot up the [war found on the releases page](https://github.com/frankduncan/collabsnap/releases/download/0.1/collabsnap-server-0.1.war)

# Loading up collabSNAP from jetty

In a local webbrowser, load up ```localhost:8080/snap/snap.html```.  Then from inside snap, load up the xml via File->Import

## Using the latest commit

Then load <projecthome>/src/main/snap/collabsnap.xml from your project

## Using the release xml

Optionally, load up the [xml from the releases page](https://github.com/frankduncan/collabsnap/releases/download/0.1/collabsnap.xml)

# Sample projects

In the resources folder, you'll find sample projects that show how one might use collabsnap.

# Using collabsnap blocks

### collabsnap-ping-server

Use this block to send a simple ping to the server, which should create some kind of output in the jetty log for now.

### collabsnap-poll

Use this block to poll server each second for news of a new message.

### collabsnap-serialize-sprite

Use this block to get the xml serialization of the passed in sprite, useful with "my[self]" sense block.

### collabsnap-send-to-server

Use this block to send an object to the server.  Most useful with collabsnap-serialize-sprite.
