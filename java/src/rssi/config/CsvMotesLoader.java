package rssi.config;

import net.tinyos.message.MoteIF;
import net.tinyos.packet.BuildSource;
import net.tinyos.packet.PhoenixSource;
import net.tinyos.util.PrintStreamMessenger;

import java.io.FileReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CsvMotesLoader implements MotesLoader {

	public List<MoteIF> loadMotes(String motesFile) {
		BufferedReader motesReader = null;
		try {
        	motesReader = new BufferedReader(new FileReader(motesFile));
        } catch (FileNotFoundException fileNotFound) {
        	System.err.println("File with motes not found: " + fileNotFound.getMessage());
            System.exit(-1);
        }
	
		String moteSource;
		List<MoteIF> motes = new ArrayList<>();
		
        try {
            while ((moteSource = motesReader.readLine()) != null) {
                PhoenixSource phoenix;
                phoenix = BuildSource.makePhoenix(moteSource, PrintStreamMessenger.err);
                motes.add(new MoteIF(phoenix));
            }
        } catch (IOException e) {
            System.err.println("Can not read from: " + motesFile + ". Reason: " + e.getMessage());
            System.exit(-1);
        }

        try {
			motesReader.close();
		} catch (IOException ex) {
			System.err.println("Can not close: " + motesFile);
		}

		return motes;
	}

}