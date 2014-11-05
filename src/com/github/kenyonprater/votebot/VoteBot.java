package com.github.kenyonprater.votebot;

import java.util.Arrays;
import java.util.HashMap;

import org.jibble.pircbot.*;

public class VoteBot extends PircBot {
    public String channel;
    public HashMap<String, Vote> ongoingVotes = new HashMap<String, Vote>();
	
    public VoteBot(String channel) {
    	this.channel = channel;
        this.setName("VoteBot");
    }
    
    public void help(String user, String[] commands)
    {
    	if(commands.length == 0)
    	{
    		this.sendMessage(channel, "Hi " + user + ", I'll send you a PM with all the help stuff.");
    		this.sendMessage(user, "List of commands for VoteBot:");
    		this.sendMessage(user, "@votebot help [command] | get help on a command");
    		this.sendMessage(user, "@votebot callvote name option1 [option2 ... optionN] | call a vote");
    		this.sendMessage(user, "@votebot vote name option | vote");
    		this.sendMessage(user, "@votebot endvote name | ends a vote and publishes result");
    	}
    }
    
    private void empty(String user) {
    	this.sendMessage(channel, "Hi " + user + ", I'm VoteBot. Do @votebot help for more info.");
	}
    
    private void newVote(String user, String[] commands)
    {
    	String name = commands[0];
    	String[] options = Arrays.copyOfRange(commands, 1, commands.length);

    	System.out.println("." + name + ".");
    	Vote v = new Vote(name, options, this, true);
    	ongoingVotes.put(name, v);
    }
    
    private void updateVote(String user, String[] commands)
    {
    	String name = commands[0];
    	String vote = commands[1];
    	System.out.println("." + name + ".");
    	Vote v = ongoingVotes.get(name);
    	v.addVote(user, vote);
    }
    
    private void endVote(String user, String[] commands)
    {
    	String name = commands[0];
    	Vote v = ongoingVotes.get(name);
    	v.endVote();
    	ongoingVotes.remove(name);
    }
    
    public void onMessage(String channel, String sender,
                       String login, String hostname, String message) {
        if (message.toLowerCase().startsWith(("@votebot"))) {
            String[] commands = message.split("\\s+");
            System.out.println(commands[1]);
            if(commands.length <= 1)
            {
            	empty(sender);
            }
            if(commands[1].equals("help"))
            {
            	help(sender, Arrays.copyOfRange(commands, 2, commands.length));
            }
            if(commands[1].equals("callvote"))
            {
            	newVote(sender, Arrays.copyOfRange(commands, 2, commands.length));
            }
            if(commands[1].equals("vote"))
            {
            	updateVote(sender, Arrays.copyOfRange(commands, 2, commands.length));
            }
            if(commands[1].equals("endvote"))
            {
            	endVote(sender, Arrays.copyOfRange(commands, 2, commands.length));
            }
        }
    }

	
}