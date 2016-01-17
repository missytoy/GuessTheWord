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
    public final static String[] SONGS_WORDS = new String[]{"Eminem - My name is", "Dr DRE - Still DRE",
            "Rolling Stones - Satisfaction",
                                                            "Red Hot Chili Peppers - Californication",
            "2pac - California Love", "Eminem - Lose yourself",
            "Eminem - Mockingbird", "Rehanna -rehab", "Jimi Hendrix - Voodoo child",
            "Miley - Can't stop","2pac - Changes", "Justin Timberlake - What goes around comes back around",
            "Eminem - When I'm Gone","Justin Bieber - Love yourself", "Mark Lower -  Bad Boys Cry", "The Weeknd - The Hills"    ,
            "Eminem - Superman","Justin Bieber - Sorry", "Years & Years - Take Shelter", "Years & Years - King",
            "Cassie - Me&u","Redman - put It Down", "2pac - How do you want it",
            "Three days grace - Pain", "Cher -Strong Enough", "Led Zeppelin - Ramble on", "The Weekend - Wicked games",
            "Michael Jackson - Billie Jean","Matt Pokora - Catch Me If You Can", "The Doors - Riders of the storm",
            "Justin T - My Love", "Daniel Powter-Bad Day",
            "Linkin Park - From the inside","Linkin Park - Numb", "Robbie Williams - Feel",
            "Justin Timberlake - Cry Me A River",
            "The Pussycat Dolls - I Don't Need A Man","Robbie Williams - Something Stupid",
            "Robin Schulz - Waves", "Rob Thomas - Lonely No More"



    };
    public final static String[] ANIMALS_WORDS = new String[]{"Frog", "Cat", "Dog", "Snake", "Turtle",
            "Tasmanian Devil", "Sloth", "Elephant", "Chicken",
            "Penguin","Fox","Wolf","Elephant","Bat","Bear","Beetle","Bird","Shark","Bee","Butterfly","Camel","Catfish","Chameleon","Cheetah","Chinchilla","Fish","Cockroach","Cow","Coyote",
            "Crocodile","Deer","Dolphin","Duck","Dragonfly","Eagle","Giraffe","Goose","Pig","Hamster","Hippopotamus","Horse","Monkey","Human","Kangaroo",
            "Jellyfish","Koala","Tortoise","Lion","Lizard","Llama","Mouse","Gorilla","Mule","Octopus","Opossum","Panther",
            "Pelican","Puma","Rabbit","Rat","Panda","Scorpion","Sheep","Tiger","Snail","Snake","Turkey",
            "Parrot","Meerkat","Zebra","Pigeon","Peacock","Bunny","Puppy","Kitten","Donkey","Goat","Ram","Bull","Hedgehog","Guinea pig",
            "Ant","Wasp","Hyena","Lioness","Orangutan","Schimpanse","Spider","Fly","Firefly"};
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
