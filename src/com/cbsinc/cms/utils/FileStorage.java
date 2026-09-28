package com.cbsinc.cms.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.activation.MimetypesFileTypeMap;

import org.apache.log4j.Logger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FileStorage {

	private static Logger log = Logger.getLogger(FileStorage.class);
	private static FileStorage fileStorage = new FileStorage();
	//private  String path = System.getProperty("java.io.tmpdir");
	private  String path = "" ;

	private ResourceBundle setupResources = null;

	private FileStorage() {

		if (setupResources == null)
			setupResources = PropertyResourceBundle.getBundle("appconfig");

		path = createPath() ;

		try {

			String fileDirDocker = System.getenv("FILE_DIR");
			if (fileDirDocker != null && !fileDirDocker.isEmpty()) {

				if(isExistFileDir(fileDirDocker) && isDirectoryEmpty( fileDirDocker ) )
				{
					copyFiles(path, fileDirDocker) ;
				}
				log.info("The file_dir variable got from docker enviroment variables. -Djava.io.tmpdir=C:\\temp to change that ");

				path = 	fileDirDocker ;

			} else {

				String fileDir = setupResources.containsKey("file_dir")  ? setupResources.getString("file_dir").trim():null;
				if (fileDir != null && !fileDir.isEmpty()) {
					if(isExistFileDir(fileDir) && isDirectoryEmpty( fileDir ) )
					{
						copyFiles(path, fileDir) ;
					}
					path = 	fileDir ;
					log.info("The file_dir variable was overwritten from appconfig.properties file ");
				} else {
					log.info("The file_dir variable got from system enviroment variables. -Djava.io.tmpdir=C:\\temp to change that ");
				}
			}

		}catch (Throwable e) {
			log.error(e);
		}

		log.info("file_dir:" + path);

	}

	private String createPath()
	{
		String path = this.getClass().getResource("").getPath();
		if(OSUtil.isWindows())  path = path.substring(1, path.indexOf("/WEB-INF/"));
		else path = path.substring(0, path.indexOf("/WEB-INF/"));
		if(path.contains("%20")) path = path.replaceAll("%20", " ") ;
		return path;
	}

	public static FileStorage getInstance() {
		return fileStorage;

	}

	public String getPath() {
		return path;
	}

	public boolean isExistFileDir(String directoryPath )
	{
		boolean result = false ;
		File directory = new File(directoryPath);
		if (!directory.exists()) {
            if (directory.mkdirs()) {
            	result = true ;
            	log.info("File_dir directory created successfully!");
            } else {
            	result = false ;
            	log.info("File_dir Failed to create directory.");
            }
        } else {
        	result = true ;
        	log.info("File_dir directory already exists.");
        }

		return result ;
	}


	void copyFiles(String sourceDirectory   , String targetDirectory  )
	{
		Path sourceDir = Paths.get(sourceDirectory);
        Path targetDir = Paths.get(targetDirectory);

        try {
            Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path targetPath = targetDir.resolve(sourceDir.relativize(dir));
                    if (!Files.exists(targetPath)) {
                        Files.createDirectory(targetPath);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.copy(file, targetDir.resolve(sourceDir.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
                    return FileVisitResult.CONTINUE;
                }
            });

            log.info("Files copied to "+ targetDirectory +" successfully!");
        } catch (Throwable e) {
        	log.error(e);
        }

	}

	boolean isDirectoryEmpty(String directoryPath )
	{
		boolean result = false ;
		Path path = Paths.get(directoryPath);
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
            if (!directoryStream.iterator().hasNext()) {
            	result = true ;
            } else {
            	result = false ;
            }
        } catch (Throwable e) {
        	result = false ;
        	log.error(e);
        }
		return result;
	}


	/**
	 * FIX (path traversal): the four validate*Path helpers used to cut the URL at a
	 * marker ("/files/", "/imgpositions/", ...) and concatenate the remainder onto the
	 * storage root without ever normalising it. A request for
	 * "/files/../../../WEB-INF/web.xml" therefore escaped the storage root. They also
	 * failed OPEN: when the marker was absent, indexOf returned -1, substring(-1) threw,
	 * and the catch block returned the attacker-supplied path unchanged.
	 *
	 * <p>Both problems are fixed here: the candidate path is normalised and then checked
	 * for containment under the storage root, and anything that fails validation raises
	 * IOException instead of falling back to the raw input.</p>
	 *
	 * @param urlAfterWebDomain request URL containing the marker segment
	 * @param marker            the storage sub-directory marker, e.g. "/files/"
	 * @return absolute, normalised path guaranteed to sit under the storage root
	 * @throws IOException if the marker is missing or the path escapes the root
	 */
	private String resolveUnderRoot(String urlAfterWebDomain, String marker) throws IOException {
		if (urlAfterWebDomain == null) {
			throw new IOException("null path requested for " + marker);
		}
		int idx = urlAfterWebDomain.indexOf(marker);
		if (idx < 0) {
			throw new IOException("path does not contain " + marker + ": " + urlAfterWebDomain);
		}
		String relativePath = urlAfterWebDomain.substring(idx);
		// Reject encoded traversal before it reaches the file system.
		String decoded = relativePath.replace('\\', '/');
		if (decoded.contains("..") || decoded.indexOf('\0') >= 0) {
			throw new IOException("traversal attempt rejected: " + urlAfterWebDomain);
		}
		java.nio.file.Path root = java.nio.file.Paths.get(FileStorage.getInstance().getPath())
				.toAbsolutePath().normalize();
		java.nio.file.Path candidate = root
				.resolve(decoded.startsWith("/") ? decoded.substring(1) : decoded)
				.toAbsolutePath().normalize();
		if (!candidate.startsWith(root)) {
			throw new IOException("path escapes storage root: " + urlAfterWebDomain);
		}
		String validPath = candidate.toString();
		log.debug("Fetching " + marker + " from " + validPath);
		return validPath;
	}

	/** Resolve a /files/ URL to a path guaranteed to be inside the storage root. */
	public String validateFilesPath(String urlAfterWebDomain) throws IOException {
		return resolveUnderRoot(urlAfterWebDomain, "/files/");
	}

	/** Resolve an /imgpositions/ URL to a path guaranteed to be inside the storage root. */
	public String validateImgpositionsPath(String urlAfterWebDomain) throws IOException {
		return resolveUnderRoot(urlAfterWebDomain, "/imgpositions/");
	}

	/** Resolve a /big_imgpositions/ URL to a path guaranteed to be inside the storage root. */
	public String validateBigImgpositionsPath(String urlAfterWebDomain) throws IOException {
		return resolveUnderRoot(urlAfterWebDomain, "/big_imgpositions/");
	}

	/** Resolve an /imgcatalog/ URL to a path guaranteed to be inside the storage root. */
	public String validateImgcatalogPath(String urlAfterWebDomain) throws IOException {
		return resolveUnderRoot(urlAfterWebDomain, "/imgcatalog/");
	}

}
