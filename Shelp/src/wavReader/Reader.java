
package wavReader;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Reader {

	private String filepath;
	private byte[] entireFileData;
	private int noOfChannels;
	private int SR;
	private int BPS;

	//SR = sampling rate
	public double getSR(){
		ByteBuffer wrapped = ByteBuffer.wrap(Arrays.copyOfRange(entireFileData, 24, 28)); // big-endian by default
		double SR = wrapped.order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
		return SR;
	}

	public Reader(String pFilepath, boolean print_info) throws IOException{
		this.filepath = pFilepath;
		Path path = Paths.get(filepath);
		this.entireFileData = Files.readAllBytes(path);

		//extract format
		String format = new String(Arrays.copyOfRange(entireFileData, 8, 12), "UTF-8");

		//extract number of channels
		noOfChannels = entireFileData[22];
		String noOfChannels_str;
		if (noOfChannels == 2)
			noOfChannels_str = "2 (stereo)";
		else if (noOfChannels == 1)
			noOfChannels_str = "1 (mono)";
		else
			noOfChannels_str = noOfChannels + "(more than 2 channels)";

		//extract sampling rate (SR)
		SR = (int) this.getSR();

		//extract Bit Per Second (BPS/Bit depth)
		BPS = entireFileData[34];

		if (print_info){
			System.out.println("---------------------------------------------------");
			System.out.println("File path:          " + filepath);
			System.out.println("File format:        " + format);
			System.out.println("Number of channels: " + noOfChannels_str);
			System.out.println("Sampling rate:      " + SR);
			System.out.println("Bit depth:          " + BPS);
			System.out.println("---------------------------------------------------");
		}
	}

	public double[] getByteArray (){
		byte[] data_raw = Arrays.copyOfRange(entireFileData, 44, entireFileData.length);
		int totalLength = data_raw.length;

		//declare double array for mono
		int new_length = totalLength/2;
		double[] data_mono = new double[new_length];

		double mono;
		for (int i = 0; 2*i+3 < totalLength; i++){
			mono = (short)((data_raw[2*i+1] & 0xff) << 8) | (data_raw[2*i] & 0xff);
			data_mono[i] = mono;
		}         
		return data_mono;
	}

	public boolean checkFile() throws UnsupportedAudioFileException, IOException {
		File checkSize = new File(filepath);
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(checkSize);
	    AudioFormat format = audioInputStream.getFormat();
	    long audioFileLength = checkSize.length();
	    int frameSize = format.getFrameSize();
	    float frameRate = format.getFrameRate();
	    int durationInSeconds = (int)(audioFileLength / (frameSize * frameRate));
		
	    
		if(noOfChannels == 1 && SR == 44100 && BPS == 16 && durationInSeconds == 5)
			return true;

	    System.out.println("FALSE durationInSeconds = " + durationInSeconds);
		return false;
	}
	
	public boolean checkSplitFiles() throws UnsupportedAudioFileException, IOException {
		File checkSize = new File(filepath);
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(checkSize);
	    AudioFormat format = audioInputStream.getFormat();
	    long audioFileLength = checkSize.length();
	    int frameSize = format.getFrameSize();
	    float frameRate = format.getFrameRate();
	    float durationInSeconds = (audioFileLength / (frameSize * frameRate));
	    
		if(noOfChannels == 1 && SR == 44100 && BPS == 16 && durationInSeconds >= 10)
			return true;

		return false;
	}
}