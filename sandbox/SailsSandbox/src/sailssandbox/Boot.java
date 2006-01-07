package sailssandbox;

import org.opensails.sails.util.DevelopmentBoot;

public class Boot extends DevelopmentBoot {
    public static void main(String[] args) {
        new Boot().startServer();
    }

    @Override
    protected String contextDirectory() {
        return "./war";
    }
}
