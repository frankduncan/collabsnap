An experimental project to create a general purpose library allowing multiple SNAP sessions to communicate with each other via a central server.

# Booting the http server

## Using the latest commit

To boot the server using maven, run:

```bash
mvn jetty:run
```

## Using the released war

You can boot up the [war found on the releases page](https://github.com/frankduncan/collabsnap/releases/download/0.3/collabsnap-server-0.3.war)

# Loading up collabSNAP from jetty

In a local webbrowser, load up ```localhost:8080/snap/snap.html```.  Then from inside snap, load up the xml via File->Import

## Using the latest commit

Then load <projecthome>/src/main/snap/collabsnap.xml from your project

## Using the release xml

Optionally, load up the [xml from the releases page](https://github.com/frankduncan/collabsnap/releases/download/0.3/collabsnap.xml)

# Sample projects

In the resources folder, you'll find sample projects that show how one might use collabsnap.

# Using collabsnap blocks

### collabsnap-ping-server

Use this block to send a simple ping to the server, which should create some kind of output in the jetty log for now.

### collabsnap-poll-server

Use this block to poll server each second for news of a new message.  The first argument should be an unringified method for deserializing sprites, either as clones or sprites.  The second method is the set of blocks that get executed if the response from the server is a message.  The third method, which is optional, is for adding filters to the polling for sprites.

### collabsnap-new-sprite-as-sprite

When used in collaboration with collabsnap-poll-server, new sprites are created as new sprites.

### collabsnap-new-sprite-as-clone

When used in collaboration with collabsnap-poll-server, new sprites are created as clones.

### collabsnap-filter-variable-equal *name* *value*

Filter method which, when used with collabsnap-poll-server, sends a filter to only return sprites with a variables named *name* equal to *value*

### collabsnap-filter-key-equal *key*

Filter method which, when used with collabsnap-poll-server, sends a filter to only return messages with the key *key*

### collabsnap-serialize-sprite

Use this block to get the xml serialization of the passed in sprite, useful with "my[self]" sense block.

### collabsnap-send-to-server *object*

Use this block to send *object* to the server.  Most useful with collabsnap-serialize-sprite or messages.

### collabsnap-send-myself-to-server

Use this block to send the current sprite to the server.

### collabsnap-id

Returns a GUID for this running instance of snap.  This will then be retained and returned for every further call while the page is still open.

### collabsnap-message-key *message*

Returns the key portion of *message*

### collabsnap-message-value *message*

Returns the value portion of *message*

### collabsnap-make-message *key* *value*

Returns a message made up of *key* and *value* for message passing.
