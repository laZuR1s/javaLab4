import repository.ProfileFileRepository;
import service.CheckSumService;
import service.ProfileService;
import ui.ConsoleInterface;

public class Main {
    public static void main(String[] args) {
        ConsoleInterface consoleInterface=new ConsoleInterface(
                new ProfileService(new CheckSumService(), new ProfileFileRepository())
        );
        consoleInterface.start();
    }
}
