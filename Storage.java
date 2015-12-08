import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Storage {
	public static void save() {
		try {
			final File file = new File("./saved.data");
			final String[] strings = { "", "", "", "", "" };
			if (file.exists()) {
				final BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
				String string_192_;
				for (int i_193_ = 0; (string_192_ = bufferedreader.readLine()) != null && i_193_ < 5; i_193_++)
					strings[i_193_] = string_192_;
				bufferedreader.close();
			}
			strings[0] = "";
			strings[1] = RunApp.carfolder.toString(); //car folder
			strings[2] = "";
			strings[3] = "";
			strings[4] = "";

			final BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(file));
			for (int i_195_ = 0; i_195_ < 5; i_195_++) {
				bufferedwriter.write(strings[i_195_]);
				bufferedwriter.newLine();
			}
			bufferedwriter.close();
		} catch (final Exception exception) {
			System.err.println("could not save data ("+exception+")");
		}
	}

	public static void load() {
		try {
			final File file = new File("./saved.data");
			final String[] strings = { "", "", "", "", "" };
			if (file.exists()) {
				final BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
				String string;
				for (int i = 0; (string = bufferedreader.readLine()) != null && i < 5; i++)
					strings[i] = string;
				bufferedreader.close();
			}
			if (!strings[1].equals("")) {
				File c = new File(strings[1]);
				if (c.exists())
					RunApp.carfolder = new File(strings[1]);
				else
					System.err.println("could not load saved data; it does not exist or is invalid");
			} else
				System.err.println("could not load saved data; it does not exist or is invalid");
		} catch (final Exception exception) {
			System.err.println("could not load saved data; it does not exist or is invalid ("+exception+")");
		}
	}
}
