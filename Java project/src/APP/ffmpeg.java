package APP;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import console_external.consoleFX;

public class ffmpeg {

	private consoleFX console;

	private ArrayList<String> allowed_formats = new ArrayList<>(Arrays.asList(".mp4", ".mkv"));

	public ffmpeg(consoleFX console) {
		super();
		this.console = console;

	}

	public void encode_current_dir() {

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {

				String current_dir = System.getProperty("user.dir");
				// File folder = new File(current_dir);
				File folder = new File(
						"Z:\\mnt\\vault_omega\\Media\\Failed\\Cars 2 (2011) (1080p BluRay x265 HEVC 10bit AAC 7.1 Tigole)");

				File[] listOfFiles = folder.listFiles();
				for (File file : listOfFiles) {
					if (file.isFile()) {

						String file_name = file.getName();
						String file_path = file.getPath();
						String file_type = getFileExtension(file);
						String file_folder = file.getParent();
						File HEVC_folder = new File(file_folder + File.separator + "HEVC");
						String encoded_file = HEVC_folder.getPath() + File.separator + file_name;

						if (allowed_formats.contains(file_type)) {

							if (HEVC_folder.exists()) {
								console.log("File allready encoded skipping: " + file_name);
							} else {

								if (HEVC_folder.mkdir()) {
									console.log("Encoding file: " + file_name);
									int exitvalue = encode(file_path, encoded_file);
									console.log("exitvalue : " + exitvalue);

								} else {
									console.log("Error could not make HEVC folder!");
								}

							}

						}

					}
				}

			}
		});

		thread.start();
	}

	private int encode(String in, String out) {

		try {
			String command = String
					.format("ffmpeg -hwaccel cuda -i \"%s\" -c:v hevc_nvenc -preset medium -c:a copy \"%s\"", in, out);
			Process p = exex_command(command);
			// start command and wait for exitcode.

			return p.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}

	private String getFileExtension(File file) {
		String name = file.getName();
		int lastIndexOf = name.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return ""; // empty extension
		}
		return name.substring(lastIndexOf);
	}

	private Process exex_command(String command) throws IOException {

		// Get input streams
		Process p = Runtime.getRuntime().exec(command);
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		
		Thread thread = new Thread(new Runnable() {

			public void run() {

				try {

					String s;
					while (true) {
						s = reader.readLine();
						if (s == null) {
							
						}else {
							console.setText(s);
							//console.log(s);
							System.out.println(s);
						}

					}


				} catch (Exception e) {
					e.printStackTrace(System.err);
				}

			}

		});
		thread.start();

		return p;

	}

}
