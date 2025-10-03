public class CustomExceptions {

    public static class FileFormatException extends Exception {
        public FileFormatException(String message) {
            super(message);
        }
    }

    public static class ChecksumException extends Exception {
        public ChecksumException(String message) {
            super(message);
        }
    }

    public static class UserAlreadyExistsException extends Exception {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class UserNotFoundException extends Exception {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    public static class InvalidCommandException extends Exception {
        public InvalidCommandException(String message) {
            super(message);
        }
    }
}
