package helpers;


import java.util.HashMap;

public class WordsAndCategoriesConstants {
    // Categories
    public final static String[] CATEGORY_NAMES = new String[]{
            "Objects", "Songs", "Animals",
            "IT", "Food", "Stars",
            "Cars", "Movies", "Bg Songs", "Bg Stars"};

    public final static String[] CATEGORY_IMAGE_NAMES = new String[]{
            "object_category", "music_category", "animal_category",
            "computer", "food_category", "stars_category",
            "cars_category", "movie_category",
            "musicbg_category", "bgstars_category"};


    // Words
    public final static String[] OBJECTS_WORDS = new String[]{
            "Microphone", "Piano", "Umbrella", "Neck", "Pillow", "Saxophone", "Condom",
            "Table", "Chair", "Glass", "Straw", "Wood", "Water", "Gas", "Fork", "Spoon", "PLate", "Stairs", "Phone", "Word", "Game", "Eye", "Nose",
            "Glasses", "Nail", "Liver", "Forum", "Mummy", "Window", "Ninja", "T-shirt", "Skirt", "Dress", "Jeans", "Lamp", "Bulb", "Button",
            "Curtain", "Carpet", "Snow", "Rain", "Car", "Bus", "Elevator", "Gloves", "Scarf", "Computer", "Laptop", "Roof", "Sofa", "Coffee",
            "Traffic light", "Abs", "Boy", "Girl", "Escalator", "Brick", "Fire", "Flag", "Bottle", "Food", "Door",
            "Price", "Drink", "Song", "Scene", "Theatre", "Cinema", "Movie", "Actor", "Script", "Projector", "Bed", "TV", "Clothes", "Drawer", "Wardrobe",
            "Washing machine", "Stove", "Heater", "Desk", "Teddy bear", "Clock", "Cable", "Mirror", "Toilet", "Hanger", "Blackboard",
            "Pencil", "Notebook", "Ear", "Rubber", "Eraser", "Family", "Folio", "Forehead", "Makeup",
            "Tooth", "Garden", "Pool", "Flower", "Knife", "Box", "Bike", "Bracelet", "Ring", "Necklace", "Hat", "Jacket", "Shoe",
            "Number", "Blanket", "Perfume", "Shampoo", "Soap", "Sink", "Poster", "Picture", "Chat", "Document", "Flashlight", "Money", "Address", "House", "Key", "Locker",};
    public final static String[] SONGS_WORDS = new String[]{"Eminem - My name is", "Dr DRE - Still DRE",
            "Rolling Stones - Satisfaction", "Red Hot Chili Peppers - Californication",
            "2pac - California Love", "Eminem - Lose yourself",
            "Eminem - Mockingbird", "Rehanna -rehab", "Jimi Hendrix - Voodoo child",
            "Miley - Can't stop", "2pac - Changes", "Justin Timberlake - What goes around comes back around",
            "Eminem - When I'm Gone", "Justin Bieber - Love yourself", "Mark Lower -  Bad Boys Cry", "The Weeknd - The Hills",
            "Eminem - Superman", "Justin Bieber - Sorry", "Years & Years - Take Shelter", "Years & Years - King",
            "Cassie - Me&u", "Redman - put It Down", "2pac - How do you want it",
            "Three days grace - Pain", "Cher -Strong Enough", "Led Zeppelin - Ramble on", "The Weekend - Wicked games",
            "Michael Jackson - Billie Jean", "Matt Pokora - Catch Me If You Can", "The Doors - Riders of the storm",
            "Justin T - My Love", "Daniel Powter-Bad Day",
            "Linkin Park - From the inside", "Linkin Park - Numb", "Robbie Williams - Feel",
            "Justin Timberlake - Cry Me A River",
            "The Pussycat Dolls - I Don't Need A Man", "Robbie Williams - Something Stupid",
            "Robin Schulz - Waves", "Rob Thomas - Lonely No More"
    };

    public final static String[] ANIMALS_WORDS = new String[]{"Frog", "Cat", "Dog", "Turtle",
            "Tasmanian Devil", "Sloth", "Chicken",
            "Penguin", "Fox", "Wolf", "Elephant", "Bat", "Bear", "Beetle", "Bird", "Shark", "Bee", "Butterfly", "Camel", "Catfish", "Chameleon", "Cheetah", "Chinchilla", "Fish", "Cockroach", "Cow", "Coyote",
            "Crocodile", "Deer", "Dolphin", "Duck", "Dragonfly", "Eagle", "Giraffe", "Goose", "Pig", "Hamster", "Hippopotamus", "Horse", "Monkey", "Human", "Kangaroo",
            "Jellyfish", "Koala", "Tortoise", "Lion", "Lizard", "Llama", "Mouse", "Gorilla", "Mule", "Octopus", "Opossum", "Panther",
            "Pelican", "Puma", "Rabbit", "Rat", "Panda", "Scorpion", "Sheep", "Tiger", "Snail", "Snake", "Turkey",
            "Parrot", "Meerkat", "Zebra", "Pigeon", "Peacock", "Bunny", "Puppy", "Kitten", "Donkey", "Goat", "Bull", "Hedgehog", "Guinea pig",
            "Ant", "Wasp", "Hyena", "Lioness", "Orangutan", "Schimpanse", "Spider", "Fly", "Firefly"};
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
    public final static String[] STARS_WORDS = new String[]{"Messi", "Ronaldo", "Robert De Niro", "Bastian Schweinsteiger",
            "Madonna", "Harrison Ford"};

    public final static String[] IT_WORDS = new String[]{"Java", "Android", "C#", "Windows", "Console", "Web", "HTML", "CSS",
            "PHP", "Python", "Ruby", "Bug", "C++", "Objective C", "JavaScript", "Program", "StartUp", "IOS", "Linux",
            "Mac", "Compilation", "Database", "Unit test", "Integration test", "MySql", "SQLite", "Quality Code", "MS SQL", "XAML", "XML",
            "Post", "Get", "Put", "404", "201", "501", "301", "Install", "Uninstall", "CMD", "Router", "Firewall", "Firefox", "Edge",
            "Opera", "Safari", "Explorer", "Chrome", "Google", "Video card", "Ram", "CPU", "Motherboard", "Keyboard", "USB",
            "Touch pad", "Monitor", "ROM", "HDD", "SDD", "LAN", "WWW", "HTTP", "HTTPS", "JSON", "AJAX", "Telerik", "Apple", "Microsoft", "Steve Jobs",
            "Bill Gates", "Emulator", "Cloud", "Virtual Machine", "HP", "Lenovo", "Promise", "Query", "SELECT *", "Async", "Integer/int", "String/string", "Char/char",
            "Boolean/bool", "Attribute", "Method", "Class", "OOP", "DSA", "Function", "JQuery", "Angular", "View engine",
            "Fragment", "Intent", "Handlebars", "Delegate", "Server", "Ping", "Password", "Tag", "Github", "Hash", "Queue",
            "BigInteger", "List", "Stack", "Exception", "Debug", "Build", "Design Patterns", "SOLID", "KISS", "YAGNI",
            "Dependency inversion", "Boy scout rule", "Random", "Tree", "Graph", "Algorithm", "Dijkstra", "Source control system", "Binary search",
            "Bit", "Byte", "Ninject", "Automapper", "Reflection", "Node", "MVC", "MVVM", "Web forms", ".NET", "Artificial intelligence",
            "Code", "Validation", "UI", "Data binding", "Web site", "TCP", "IP", "Wi-Fi", "Encryption", "Photoshop", "Graphic Design",
            "Sublime text", "Webstorm", "Jetbrains", "Visual Studio", "Atom", "Digital Signature", "Data mining", "Bitcoin", ".cs", "DDL",
            "DML", "CRUD", ".dll", "Thread", "goto"};


    public final static HashMap<String, String[]> WORDS_BY_CATEGORYNAME = new HashMap<String, String[]>() {{
        put("Objects", OBJECTS_WORDS);
        put("Songs", SONGS_WORDS);
        put("Animals", ANIMALS_WORDS);
        put("IT", IT_WORDS);
        put("Food", FOOD_WORDS);
        put("Cars", CARS_WORDS);
        put("Movies", MOVIES_WORDS);
        put("Stars", STARS_WORDS);
        put("Bg Songs", BGSONGS_WORDS);
        put("Bg Stars", BG_STARS_WORDS);
    }};
}
