package pt.tecnico.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import pt.tecnico.dml2yuml.dml2yuml;

import java.net.URL;
import com.github.axet.wget.WGet;
/**
 *
 */
@Mojo(	name = "dml2yuml",
	defaultPhase = LifecyclePhase.PROCESS_SOURCES,
	requiresProject = true)
public class Dml2yumlMojo extends AbstractMojo {

    /**
     * The directory where the .dml files ({@code *.dml}) are located.
     */
    @Parameter(defaultValue = "${basedir}/src/main/dml")
    private File sourceDirectory;

    /**
     * Specify output directory where the .yuml files are generated.
     */
    @Parameter(defaultValue = "${project.build.directory}/")
    private File outputDirectory;

    /**
     * Specify URL to generate images.
     */
    @Parameter(defaultValue = "")
    private String url;

    /**
     * Print multiplicity in associations.
     */
    @Parameter(property = "dml2yuml.multiplicity", defaultValue = "true")
    protected boolean multiplicity;

    /**
     * Print names in associations.
     */
    @Parameter(property = "dml2yuml.names", defaultValue = "true")
    protected boolean names;

    /**
     * Print role names in associations.
     */
    @Parameter(property = "dml2yuml.role", defaultValue = "true")
    protected boolean role;

    /**
     * Print commented attributes in classes.
     */
    @Parameter(property = "dml2yuml.attributes", defaultValue = "true")
    protected boolean attributes;


    public void execute() throws MojoExecutionException
    {
      try {
	if (!multiplicity) dml2yuml.omitMultiplicity();
	if (!names) dml2yuml.omitNames();
	if (!role) dml2yuml.omitRole();
	if (!attributes) dml2yuml.omitAttributes();
	getLog().info( " dml2yuml:" );
	if (sourceDirectory == null) {
	  getLog().warn( "No src/main/dml directory!" );
	  return;
	}
	for (File f: sourceDirectory.listFiles()) {
	  String name = f.getName();
	  getLog().info( "    " + name );
	  if (name.endsWith(".dml")) {
	    if (url == null || url.length() == 0) {
	      String out = name.substring(0, name.length() - 4) + ".yuml";
	      dml2yuml.writer(new PrintWriter(new File(outputDirectory, out), "UTF-8"));
	      dml2yuml.execute(new FileInputStream(f));
	    } else {
	      String out = name.substring(0, name.length() - 4) + ".png";
	      StringWriter str = new StringWriter();
	      dml2yuml.writer(new PrintWriter(str));
	      dml2yuml.execute(new FileInputStream(f));
	      URL cmd = new URL(url+str.toString().replaceAll("[\r]?\n", ", ").replaceAll(" ", "%20"));
	      new WGet(cmd, new File(outputDirectory, out)).download();
	    }
	  }
	}
      } catch (Exception e) { getLog().warn(e); } // getLog().error(e);
    }
}
