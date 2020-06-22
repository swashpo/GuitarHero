/* *****************************************************************************
 *  Name:    Richard Ma
 *  NetID:   rm44
 *  Precept: P03
 *
 *
 *  Description: Uses GuitarString and RingBuffer to play music!
 *
 **************************************************************************** */

public class GuitarHero {
    public static void main(String[] args) {
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        int index;
        int stringNum = 37;
        GuitarString[] hero = new GuitarString[stringNum];  // array of 37 guitarstring objects
        double exp; // exponent constant stored for later

        // sets each GuitarString object to correct frequency
        for (int i = 0; i < stringNum; i++) {
            exp = (i - 24.0) / 12;
            hero[i] = new GuitarString(440 * (Math.pow(2, exp)));
        }

        // takes in notes to be played
        while (true) {
            // get input
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                index = keyboard.indexOf(key); // figure out index of input in keyboard

                if (index >= 0)
                    hero[index].pluck(); // pluck correct gstring object
            }


            // take superposition of all strings by iterating through array and
            // adding samples of each object

            double sample = 0;
            for (int i = 0; i < stringNum; i++) {
                sample += hero[i].sample();
            }
            StdAudio.play(sample);

            for (int i = 0; i < stringNum; i++) {
                hero[i].tic();
            }
        }
    }
}
