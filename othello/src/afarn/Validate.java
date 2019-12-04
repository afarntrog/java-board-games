public class Validate {

    // Accept an int and return validity
    public static boolean int_validator(int upper_bound, int lower_bound, int value) {
        return (value < upper_bound) && (value > lower_bound);
    }
}

// This class is to show me using utility classes with static methods.