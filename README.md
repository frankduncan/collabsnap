An experimental project to create a general purpose library allowing multiple SNAP sessions to communicate with each other via a central server.

# Booting the http server

## Using the latest commit

To boot the server using maven, run:

```bash
mvn jetty:run
```

## Using the released war

You can boot up the [war found on the releases page](https://github.com/frankduncan/collabsnap/releases/download/0.3/collabsnap-server-0.6.war)

# Loading up collabSNAP from jetty

In a local webbrowser, load up ```localhost:8080/snap/snap.html```.  Then from inside snap, load up the xml via File->Import

## Using the latest commit

Then load <projecthome>/src/main/snap/collabsnap.xml or <projecthome>/src/main/snap/topography.xml from your project

## Using the release xml

Optionally, load up the [collabsnap xml](https://github.com/frankduncan/collabsnap/releases/download/0.6/collabsnap.xml) or [topgraphy xml](https://github.com/frankduncan/collabsnap/releases/download/0.6/topography.xml) from the releases page.

# Sample projects

In the resources folder, you'll find sample projects that show how one might use collabsnap.

# Using collabsnap blocks

### collabsnap-ping-server

Use this block to send a simple ping to the server, which should create some kind of output in the jetty log for now.

### collabsnap-poll-server

Use this block to poll server each second for news of a new message.  This will loop over all registered listeners.  It should only be used once per snap session.

### collabsnap-add-listener

Use to add a listener to the list of polling listeners.  The first argument is a filter as created by collabsnap-filter-\*.  The second is a block to run with the responding message.  Returns an id that can be later used to remove.  Duplicate listeners do not get added.

### collabsnap-remove-listener

Removes a listener with the id passed in (which should have been returned by collabsnap-add-listener)

### collabsnap-reset-listeners

Removes all listeners.

### collabsnap-new-sprite-as-sprite

When used in collaboration with a listener that has a sprite message, argument is created as a new sprite.

### collabsnap-new-sprite-as-clone

When used in collaboration with a listener that has a sprite message, argument is created as a clone.

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

# Using topography blocks

### topography-stage-width

Returns the stage width in pixels.

### topography-stage-height

Returns the stage height in pixels.

### topography-stage-to-unit-x

Converts a pixel location in x to a unit location.

### topography-unit-to-stage-x

Converts a unit location to a stage location in x.

### topography-stage-to-unit-y

Converts a pixel location in y to a unit location.

### topography-unit-to-stage-y

Converts a unit location to a stage location in y.

### topography-unit-x-position

Gets the current sprites unit x position

### topography-unit-y-position

Gets the current sprites unit y position
