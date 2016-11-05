<!DOCTYPE html>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Snap! Build Your Own Blocks</title>
		<link rel="shortcut icon" href="/snap/favicon.ico">
		<script type="text/javascript" src="/snap/morphic.js"></script>
		<script type="text/javascript" src="/snap/widgets.js"></script>
		<script type="text/javascript" src="/snap/blocks.js"></script>
		<script type="text/javascript" src="/snap/threads.js"></script>
		<script type="text/javascript" src="/snap/objects.js"></script>
		<script type="text/javascript" src="/snap/gui.js"></script>
		<script type="text/javascript" src="/snap/paint.js"></script>
		<script type="text/javascript" src="/snap/lists.js"></script>
		<script type="text/javascript" src="/snap/byob.js"></script>
		<script type="text/javascript" src="/snap/tables.js"></script>
		<script type="text/javascript" src="/snap/xml.js"></script>
		<script type="text/javascript" src="/snap/store.js"></script>
		<script type="text/javascript" src="/snap/locale.js"></script>
		<script type="text/javascript" src="/snap/cloud.js"></script>
		<script type="text/javascript" src="/snap/sha512.js"></script>
		<script type="text/javascript" src="/snap/FileSaver.min.js"></script>
		<script type="text/javascript">
			var world;

      var collabsnap_activity = "<%= request.getAttribute("activityName") %>";
      var collabsnap_group = "<%= request.getAttribute("groupName") %>";

			window.onload = function () {
				world = new WorldMorph(document.getElementById('world'));
                world.worldCanvas.focus();

        // Override this one because the snap location is elsewhere.  Love that this was thought of in snap design :)
        IDE_Morph.prototype.resourceURL = function () {
          var args = Array.prototype.slice.call(arguments, 0);

          return "/snap/" + args.join('/');
        };

        var ide_morph = new IDE_Morph();
        ide_morph.openIn(world);
        loop();
        <% if(request.getAttribute("snapFileExists") != null) {%>
          ide_morph.rawOpenProjectString(ide_morph.getURL("/downloadrole/<%= request.getAttribute("activityName") %>/<%= request.getAttribute("roleName") %>.xml"));
        <%}%>

			};
			function loop() {
        requestAnimationFrame(loop);
				world.doOneCycle();
			}
		</script>
	</head>
	<body style="margin: 0;">
		<canvas id="world" tabindex="1" style="position: absolute;" />
	</body>
</html>
