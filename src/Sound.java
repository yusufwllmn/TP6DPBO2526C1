import javax.sound.sampled.*;
import java.net.URL;

/**
 * Pembungkus sederhana untuk memutar file audio (format WAV) dari folder assets.
 * Jika file tidak ditemukan, game tetap berjalan tanpa suara (tidak crash).
 */
public class Sound {

    private Clip clip;

    public Sound(String path) {
        try {
            URL url = getClass().getResource(path);
            if (url == null) {
                System.err.println("File audio tidak ditemukan: " + path);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (Exception e) {
            System.err.println("Gagal memuat audio " + path + ": " + e.getMessage());
            clip = null;
        }
    }

    // Putar sekali dari awal (untuk sound effect)
    public void play() {
        if (clip == null) return;
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        clip.start();
    }

    // Putar berulang terus-menerus (untuk BGM)
    public void loop() {
        if (clip == null) return;
        clip.stop();
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        if (clip == null) return;
        clip.stop();
    }

    // Atur volume dalam desibel (contoh: -10f untuk lebih pelan)
    public void setVolume(float decibel) {
        if (clip == null) return;
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gain.setValue(Math.max(gain.getMinimum(), Math.min(gain.getMaximum(), decibel)));
        }
    }
}