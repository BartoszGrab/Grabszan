package grab.szan.bots;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BotNameGenerator {
    private static List<String> names;
    private static Random rand;
    public static void configure() {
        rand = new Random();
        names = new ArrayList<>();

        names.add("BartekToCwel");
        names.add("SkosnyKrol");
        names.add("GotfrydTheGoat");
        names.add("Noobmaster69");
        names.add("mrPawelPl2004");
        //names.add("bot");
    }

    public static String getRandomName() {
        return names.get(rand.nextInt(names.size()));
    }
}
