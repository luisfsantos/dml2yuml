
  Convert .dml files into yuml (yuml.me) files.

  flat/ hand-compile source files and build .jar

  dml2yuml/ maven based tool

  dml2yuml-maven-plugin/ maven plugin to automate conversion

  example/ maven project with integrated automatic conversion in compilation

  Do 'mvn install' in dml2yuml to generate the tool, then do 'mvn install'
  in dml2yuml-maven-plugin/ to generate the tool based plugin.
  Then use the resulting plugin in a an example/ project with 'mvn compile'.
  Copy the plugin part of the example/pom.xml to your project's pom.xml
  in order to use the plugin.

  When configured with 

	<configuration>
	  <names>false</names>
	  <outputDirectory>info</outputDirectory>
	  <url>http://yuml.me/diagram/scruffy;dir:lr/class/</url>
	</configuration>

  the plugin will generate a .png UML class file in the info/ directory
  using the yuml.me service, otherwise a .yuml text file is generated in
  the target/ directory and its contents should be copied to
  http://yuml.me/diagram/scruffy/class/draw

  (C) reis.santos(at)tecnico.ulisboa.pt 26feb2016
