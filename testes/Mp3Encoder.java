package testes;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class Mp3Encoder {
	
	private static boolean DEBUG = false;
	private static boolean dumpExceptions=false;
//	private static boolean traceConverters=false;
	private static boolean quiet=false;

	private static final AudioFormat.Encoding	MPEG1L3 = new AudioFormat.Encoding("MPEG1L3");
	private static final AudioFileFormat.Type	MP3 = new AudioFileFormat.Type("MP3", "mp3");

	public Mp3Encoder(){
		
	}

	private static AudioInputStream getInStream( String filename ) throws IOException {
		File	file = new File(filename);
		AudioInputStream	ais = null;
		try {
			ais = AudioSystem.getAudioInputStream(file);
		} catch (Exception e) {
			if (dumpExceptions) {
				e.printStackTrace();
			} else if (!quiet) {
				System.out.println("Error: " + e.getMessage());
			}
		}
		if (ais == null) {
			throw new IOException("Cannot open \"" + filename + "\"");
		}
		return ais;
	}



	public static String stripExtension( String filename ) {
		int	ind = filename.lastIndexOf(".");
		if (ind == -1
		        || ind == filename.length()
		        || filename.lastIndexOf(File.separator) > ind) {
			// when dot is at last position,
			// or a slash is after the dot, there isn't an extension
			return filename;
		}
		return filename.substring(0, ind);
	}



	/* first version. Remains here for documentation how to
	 * get a stream with complete description of the target format.
	 */
	public static AudioInputStream getConvertedStream2(	AudioInputStream sourceStream, AudioFormat.Encoding targetEncoding)	throws Exception {
		AudioFormat sourceFormat = sourceStream.getFormat();
		if (!quiet) {
			System.out.println("Input format: " + sourceFormat);
		}
		// build the output format
		AudioFormat targetFormat = new AudioFormat(
		                               targetEncoding,
		                               sourceFormat.getSampleRate(),
		                               AudioSystem.NOT_SPECIFIED,
		                               sourceFormat.getChannels(),
		                               AudioSystem.NOT_SPECIFIED,
		                               AudioSystem.NOT_SPECIFIED,
		                               false); // endianness doesn't matter
		// construct a converted stream
		AudioInputStream targetStream = null;
		if (!AudioSystem.isConversionSupported(targetFormat, sourceFormat)) {
			if (DEBUG && !quiet) {
				System.out.println("Direct conversion not possible.");
				System.out.println("Trying with intermediate PCM format.");
			}
			AudioFormat intermediateFormat = new AudioFormat(
			                                     AudioFormat.Encoding.PCM_SIGNED,
			                                     sourceFormat.getSampleRate(),
			                                     16,
			                                     sourceFormat.getChannels(),
			                                     2 * sourceFormat.getChannels(), // frameSize
			                                     sourceFormat.getSampleRate(),
			                                     false);
			if (AudioSystem.isConversionSupported(intermediateFormat, sourceFormat)) {
				// intermediate conversion is supported
				sourceStream = AudioSystem.getAudioInputStream(intermediateFormat, sourceStream);
			}
		}
		targetStream = AudioSystem.getAudioInputStream(targetFormat, sourceStream);
		if (targetStream == null) {
			throw new Exception("conversion not supported");
		}
		if (!quiet) {
			if (DEBUG) {
				System.out.println("Got converted AudioInputStream: " + targetStream.getClass().getName());
			}
			System.out.println("Output format: " + targetStream.getFormat());
		}
		return targetStream;
	}



	public static AudioInputStream getConvertedStream(AudioInputStream sourceStream, AudioFormat.Encoding targetEncoding) throws Exception {
		AudioFormat sourceFormat = sourceStream.getFormat();
		if (!quiet) {
			System.out.println("Input format: " + sourceFormat);
		}

		// construct a converted stream
		AudioInputStream targetStream = null;
		if (!AudioSystem.isConversionSupported(targetEncoding, sourceFormat)) {
			if (DEBUG && !quiet) {
				System.out.println("Direct conversion not possible.");
				System.out.println("Trying with intermediate PCM format.");
			}
			AudioFormat intermediateFormat = new AudioFormat(
			                                     AudioFormat.Encoding.PCM_SIGNED,
			                                     sourceFormat.getSampleRate(),
			                                     16,
			                                     sourceFormat.getChannels(),
			                                     2 * sourceFormat.getChannels(), // frameSize
			                                     sourceFormat.getSampleRate(),
			                                     false);
			if (AudioSystem.isConversionSupported(intermediateFormat, sourceFormat)) {
				// intermediate conversion is supported
				sourceStream = AudioSystem.getAudioInputStream(intermediateFormat, sourceStream);
			}
		}
		targetStream = AudioSystem.getAudioInputStream(targetEncoding, sourceStream);
		if (targetStream == null) {
			throw new Exception("conversion not supported");
		}
		if (!quiet) {
			if (DEBUG) {
				System.out.println("Got converted AudioInputStream: " + targetStream.getClass().getName());
			}
			System.out.println("Output format: " + targetStream.getFormat());
		}
		return targetStream;
	}



	public static int writeFile(String inFilename) {
		int writtenBytes = -1;
		try {
			AudioFileFormat.Type targetType = MP3;
			AudioInputStream ais = getInStream(inFilename);
			ais = getConvertedStream(ais, MPEG1L3);

			// construct the target filename
			String outFilename = stripExtension(inFilename) + "." + targetType.getExtension();

			System.out.println(outFilename);
			
			// write the file
			if (!quiet) {
				System.out.println("Writing " + outFilename + "...");
			}
			writtenBytes = AudioSystem.write(ais, targetType, new File(outFilename));
			if (DEBUG && !quiet) {
				System.out.println("Effective parameters of output file:");
				try {
					String version=System.getProperty("tritonus.lame.encoder.version", "");
					if (version!="") {
						System.out.println("  Version      = "+version);
					}
					System.out.println("  Quality      = "+System.getProperty
					                   ("tritonus.lame.effective.quality", "<none>"));
					System.out.println("  Bitrate      = "+System.getProperty
					                   ("tritonus.lame.effective.bitrate", "<none>"));
					System.out.println("  Channel Mode = "+System.getProperty
					                   ("tritonus.lame.effective.chmode", "<none>"));
					System.out.println("  VBR mode     = "+System.getProperty
					                   ("tritonus.lame.effective.vbr", "<none>"));
					System.out.println("  Sample rate  = "+System.getProperty
					                   ("tritonus.lame.effective.samplerate", "<none>"));
					System.out.println("  Encoding     = "+System.getProperty
					                   ("tritonus.lame.effective.encoding", "<none>"));
				} catch (Throwable t1) {}
			}
		} catch (Throwable t) {
			if (dumpExceptions) {
				t.printStackTrace();
			} else if (!quiet) {
				System.out.println("Error: " + t.getMessage());
			}
		}
		return writtenBytes;
	}

	public static void main(String[] args) {
//		System.out.println("sadsad");
		
		writeFile( "C:\\Documents and Settings\\Diego\\Meus documentos\\Desenvolvimento\\workspace\\Equivium\\arquivos\\reforcos\\audio\\bola.wav" );
		
		//try {
		//	System.out.println("Librarypath=" + System.getProperty("java.library.path", ""));
		//} catch (Throwable t) {}
//
//		int firstFileIndex = parseArgs(args);
//		int inputFiles = 0;
//		int success = 0;
//		long totalTime = System.currentTimeMillis();
//		for (int i = firstFileIndex; i < args.length; i++) {
//			long time = System.currentTimeMillis();
//			int bytes = writeFile(args[i]);
//			time = System.currentTimeMillis()-time;
//			inputFiles++;
//			if (bytes >= 0) {
//				if (bytes > 0) {
//					success++;
//				}
//				if (!quiet) {
//					System.out.println("Wrote " + bytes + " bytes in "
//					                   + (time / 60000) + "m " + ((time/1000) % 60) + "s "
//					                   + (time % 1000) + "ms ("
//					                   + (time/1000) + "s).");
//				}
//			}
//		}
//		totalTime = System.currentTimeMillis() - totalTime;
//		if ((DEBUG && quiet) || !quiet) {
//			// this IS displayed in silent DEBUG mode
//			System.out.println("From " + inputFiles + " input file" + (inputFiles == 1 ? "" : "s") + ", "
//			                   + success + " file" + (success == 1 ? " was" : "s were") + " converted successfully in "
//			                   + (totalTime / 60000) + "m " + ((totalTime/1000) % 60) + "s  ("
//			                   + (totalTime/1000) + "s).");
//		}
//		System.exit(0);
	}


}




