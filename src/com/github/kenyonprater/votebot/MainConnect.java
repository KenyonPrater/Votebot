package com.github.kenyonprater.votebot;

import java.util.Scanner;

import org.jibble.pircbot.*;

public class MainConnect {
    
    public static void main(String[] args) throws Exception {
        
    	Scanner scan = new Scanner(System.in);
    	System.out.println("channel: ");
    	String ch = scan.next();
    	System.out.println("pwd: ");
    	String pwd = scan.next();
    	
        // Now start our bot up.
        VoteBot bot = new VoteBot(ch);
        
        // Enable debugging output.
        bot.setVerbose(true);
        
        // Connect to the IRC server.
        bot.connect("irc.freenode.net");

        bot.joinChannel(ch, pwd);
        
    }
    
}