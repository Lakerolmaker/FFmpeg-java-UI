package APP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import console_external.consoleFX;
import console_external.runnableConsole;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	   
		
		
		consoleFX console = new consoleFX();

		console.displayStandalone(args, new runnableConsole() {

			@Override
			public void run() {
				
				
				ffmpeg fpeg = new ffmpeg(console);
				fpeg.encode_current_dir();
				
				

			}

		});

	}

}
