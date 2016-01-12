package helpers;


import java.util.HashMap;

public class WordsAndCategoriesConstants {
    // Categories
    public final static String[] CATEGORY_NAMES = new String[]{"Objects", "Songs", "Animals", "Bg Stars",
            "Cars", "Food", "Movies", "Bg Songs", "Stars"};

    public final static String[] CATEGORY_IMAGE_NAMES = new String[]{"object_category", "music_category", "animal_category",
            "bgstars_category", "cars_category", "food_category",
            "movie_category", "musicbg_category", "stars_category"};


    // Words
    public final static String[] OBJECTS_WORDS = new String[]{"microphone", "piano", "umbrella", "bottle", "neck",
                                                              "pillow", "tail", "saxophone", "condom"};
    public final static String[] SONGS_WORDS = new String[]{"My name is", "O sole mio", "Still DRE", "Satisfaction",
                                                            "Californication", "California Love"};
    public final static String[] ANIMALS_WORDS = new String[]{"frog", "cat", "dog", "snake", "turtle",
            "tasmanian devil", "sloth", "elephant", "chicken"};
    public final static String[] BG_STARS_WORDS = new String[]{"Слави Трифонов", "Стоичков", "Бербатов", "Гонзо", "Криско",
            "Лили Иванова", "Васил Найденов", "Емил Костадинов", "Красимир Балъков"};
    public final static String[] CARS_WORDS = new String[]{"Mercedes", "BMW", "AUDI", "Ferrari", "Volkswagen",
            "Жигули", "Opel", "Chrysler", "Dodge"};
    public final static String[] FOOD_WORDS = new String[]{"pancakes", "steak", "salad", "тирамису", "cake",
            "burger", "кебапче", "мусака", "шкембе чорба"};
    public final static String[] MOVIES_WORDS = new String[]{"Harry Potter and the Goblet of Fire", "The Matrix", "Star Wars - the Force Awakens",
            "Господин за един ден", "Despicable me", "The Godfather", "The Shawshenk Redemption", "Borat", "Ну Погоди"};
    public final static String[] BGSONGS_WORDS = new String[]{"Йовано Йованке", "Нон стоп", "Писна ми", "Ах, морето", "Луди жаби",
                                                              "Назад назад, моме Калино"};
    public final static String[] STARS_WORDS = new String[]{"Messi", "Ronaldo", "Steve Jobs", "Robert De Niro", "Bastian Schweinsteiger",
            "Madonna", "Harrison Ford"};



    public final static HashMap<String, String[]> WORDS_BY_CATEGORYNAME = new HashMap<String, String[]>(){{
        put("Objects", OBJECTS_WORDS);
        put("Songs", SONGS_WORDS);
        put("Animals", ANIMALS_WORDS);
        put("Bg Stars", BG_STARS_WORDS);
        put("Cars", CARS_WORDS);
        put("Food", FOOD_WORDS);
        put("Movies", MOVIES_WORDS);
        put("Bg Songs", BGSONGS_WORDS);
        put("Stars", STARS_WORDS);
    }};

}
