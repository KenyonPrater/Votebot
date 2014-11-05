package com.github.kenyonprater.votebot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Vote
    {
    	private VoteBot parent;
    	public String name;
    	public List<String> options;
    	public HashMap<String, Integer> voters = new HashMap<String, Integer>();
    	public int[] votetotals;
    	private boolean verbose = false;
    	
    	
    	public Vote(String name, String[] options, VoteBot parentbot, boolean verbose)
    	{
    		this.name = name;
    		this.options = Arrays.asList(options); 
    		this.votetotals = new int[options.length];
    		this.verbose = verbose;
    		for(int i = 0; i < votetotals.length; i++)
    		{
    			votetotals[i] = 0;
    		}
    		for(int i = 0; i < votetotals.length; i++)
    		{
    			System.out.println(votetotals[i]);
    		}
    		this.parent = parentbot;
    		parent.sendMessage(parent.channel, "Vote: " + this.name + " has started! Type '@votebot vote [your vote]' to vote! Options are:");
    		for(int i = 0; i < this.options.size(); i++)
    		{
    			parent.sendMessage(parent.channel, (i+1) + ". " + this.options.get(i));
    		}
    	}
    	
    	public Vote(String name, String[] options, VoteBot parentbot)
    	{
    		this.name = name;
    		this.options = Arrays.asList(options); 
    		this.votetotals = new int[options.length];
    		for(int i = 0; i < votetotals.length; i++)
    		{
    			votetotals[i] = 0;
    		}
    		this.parent = parentbot;
    		parent.sendMessage(parent.channel, "Vote: " + this.name + " has started! Type '@votebot vote [your vote]' to vote! Options are:");
    		for(int i = 0; i < this.options.size(); i++)
    		{
    			parent.sendMessage(parent.channel, i + ". " + this.options.get(i));
    		}
    	}
    	
    	public void addVote(String user, String choice)
    	{
    		System.out.println("yep");
    		if(options.contains(choice))
    		{
    			votetotals[options.indexOf(choice)]++;
    			if (voters.containsKey(user))
    			{
    				if(this.verbose)
    				{
    					parent.sendMessage(parent.channel, user + " has switched their vote from \""
    				                       + options.get(voters.get(user)) + "\" (" + (voters.get(user)+1) + ") "
    							           + " to \"" + options.get((options.indexOf(choice))) + "\" (" + 
    				                       (options.indexOf(choice)) + ")");
    				}
    				votetotals[voters.get(user)]--;
    			}else
    			{
    				if(this.verbose)
    				{
    					parent.sendMessage(parent.channel, user + " voted for \"" + choice + "\" (" + (options.indexOf(choice)+1) + ")");
    				}
    			}
    			voters.put(user, options.indexOf(choice));
    		}
    	}
    	
    	public void endVote()
    	{
    		System.out.println("asdaf");
    		for(int i = 0; i < this.votetotals.length; i++)
    		{
    			System.out.println(i + " " + this.votetotals[i]);
    		}
    		parent.sendMessage(parent.channel, "The vote: " + this.name + " has ended! Results:");
    		int winner = -1;
    		int maxvotes = -1;
    		for (int i = 0; i < this.votetotals.length; i++)
    		{
    			if(this.votetotals[i] > maxvotes)
    			{
    				maxvotes = this.votetotals[i];
    				winner = i;
    			}
    			parent.sendMessage(parent.channel, this.votetotals[i] + " (" + ((double)this.votetotals[i])/voters.size()*100 +"%) - " + options.get(i));
    		}
    		parent.sendMessage(parent.channel, "The winning option is: " + options.get(winner));
    	}
    }